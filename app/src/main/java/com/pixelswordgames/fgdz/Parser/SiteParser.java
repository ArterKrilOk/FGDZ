package com.pixelswordgames.fgdz.Parser;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import androidx.core.util.Pair;

import com.pixelswordgames.fgdz.BackEnd.ImageDownloader;
import com.pixelswordgames.fgdz.POJO.Book;
import com.pixelswordgames.fgdz.POJO.Decision;
import com.pixelswordgames.fgdz.POJO.Image;
import com.pixelswordgames.fgdz.POJO.Task;
import com.pixelswordgames.fgdz.POJO.TaskGroup;
import com.pixelswordgames.fgdz.R;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class SiteParser {
    public static final int ERROR_LIST_NULL = 1;
    public static final int CONNECTION_ERROR = 2;
    public static final int CANCELLED = 3;
    public static final int URL_ERROR = 4;
    public static final int SUBJECT_EMPTY_ERROR = 5;
    public static final int NO_CONDITIONS = 6;


    private static final String BASE_URL = "https://gdz.ltd";

    private Context context;
    private OnTasksLoadCompleteListener onTasksLoadCompleteListener;
    private OnBooksLoadCompleteListener onBooksLoadComplete;
    private OnDecisionCompleteListener onDecisionCompleteListener;
    private BookLoadTask bookLoadTask;
    private TaskLoadTask taskLoadTask;
    private DecisionLoadTask decisionLoadTask;

    public SiteParser(Context context){
        this.context = context;
    }

    public void loadBooks(int stYear, String lesson, OnBooksLoadCompleteListener onBooksLoadCompleteListener){
        if(bookLoadTask != null)
            bookLoadTask.cancel(true);
        bookLoadTask = new BookLoadTask();
        onBooksLoadComplete = onBooksLoadCompleteListener;
        if(!lesson.isEmpty()) {
            String url = BASE_URL + "/" + stYear + "-class/" + lesson;
            bookLoadTask.execute(url);
        } else onBooksLoadComplete.onFail(SUBJECT_EMPTY_ERROR);
    }

    public void loadTask(String url, OnTasksLoadCompleteListener onTasksLoadCompleteListener){
        if(taskLoadTask != null)
            taskLoadTask.cancel(true);
        taskLoadTask = new TaskLoadTask();
        this.onTasksLoadCompleteListener = onTasksLoadCompleteListener;
        taskLoadTask.execute(url);
    }

    public void loadDecision(String url, OnDecisionCompleteListener onDecisionCompleteListener){
        if(decisionLoadTask != null)
            decisionLoadTask.cancel(true);
        decisionLoadTask = new DecisionLoadTask();
        this.onDecisionCompleteListener = onDecisionCompleteListener;
        decisionLoadTask.execute(url);
    }

    private class DecisionLoadTask extends AsyncTask<String, Void, Pair<Integer, Decision>>{
        @Override
        protected Pair<Integer, Decision> doInBackground(String... urls) {
            Decision decision = new Decision(urls[0]);

            String condUrl = BASE_URL + urls[0].replace("exercise", "conditions");
            String ex1 = BASE_URL + urls[0];
            String ex2 = BASE_URL  + urls[0].replace("exercise", "exercise2");
            String ex3 = BASE_URL  + urls[0].replace("exercise", "exercise3");

            Pair<String,Bitmap> condBtm = getValidImg(condUrl);
            Pair<String,Bitmap> ex1Btm = getValidImg(ex1);
            Pair<String,Bitmap> ex2Btm = getValidImg(ex2);
            Pair<String,Bitmap> ex3Btm = getValidImg(ex3);

            if(condBtm != null)
                decision.addImage(new Image(condBtm.first,context.getString(R.string.dec_condition), condBtm.second));
            if(ex1Btm != null)
                decision.addImage(new Image(ex1Btm.first,context.getString(R.string.dec_decision1), ex1Btm.second));
            if(ex2Btm != null)
                decision.addImage(new Image(ex2Btm.first,context.getString(R.string.dec_decision2), ex2Btm.second));
            if(ex3Btm != null)
                decision.addImage(new Image(ex3Btm.first,context.getString(R.string.dec_decision3), ex3Btm.second));
            return new Pair<>(0,decision);

        }

        private Pair<String,Bitmap> getValidImg(String baseUrl){
            Bitmap btm = ImageDownloader.with(context).getBitmap(baseUrl + ".png");
            if(btm == null)
                return null;
            if(btm.getWidth() > 300 || btm.getHeight() > 300)
                return new Pair<> (baseUrl + ".png",btm);
            btm = ImageDownloader.with(context).getBitmap(baseUrl + ".jpg");
            if(btm == null)
                return null;
            if(btm.getWidth() > 300 || btm.getHeight() > 300)
                return new Pair<> (baseUrl + ".jpg",btm);
            btm = ImageDownloader.with(context).getBitmap(baseUrl + ".jpeg");
            if(btm == null)
                return null;
            if(btm.getWidth() > 300 || btm.getHeight() > 300)
                return new Pair<> (baseUrl + ".jpeg",btm);
            return null;
        }

        @Override
        protected void onPostExecute(Pair<Integer, Decision> data) {
            super.onPostExecute(data);

            Decision decision = data.second;
            int errorCode = data.first;

            if(decision != null && decision.getImages().size() > 0)
                onDecisionCompleteListener.onSuccess(decision);
            else if(decision != null)
                onDecisionCompleteListener.onFail(NO_CONDITIONS);
            else
                onDecisionCompleteListener.onFail(errorCode);
        }
    }

    private class TaskLoadTask extends AsyncTask<String, Void, Pair<Integer, List<TaskGroup>>>{
        @Override
        protected Pair<Integer, List<TaskGroup>> doInBackground(String... urls) {
            try{
                List<TaskGroup> groups = new ArrayList<>();

                Element tasksList = Jsoup
                        .connect(urls[0])
                        .get()
                        .body()
                        .select("div#exercise")
                        .first();



                TaskGroup group = new TaskGroup();
                for(Element el : tasksList.children()) {
                    if(el.hasClass("section")){
                        if(group.getTasks().size() > 0)
                            groups.add(group);
                        group = new TaskGroup(el.text());
                    }
                    if(el.hasClass("image_load")){
                        Task task = new Task();
                        task.setName(el.text());
                        task.setUrl(el.attr("img"));
                        group.addTask(task);
                    }
                }
                groups.add(group);

                return new Pair<>(0,groups);
            } catch (HttpStatusException e){
                e.printStackTrace();
                return new Pair<>(CONNECTION_ERROR,null);
            } catch (NullPointerException e){
                e.printStackTrace();
                return new Pair<>(CONNECTION_ERROR,null);
            } catch (IOException e) {
                e.printStackTrace();
                return new Pair<>(CONNECTION_ERROR,null);
            }
        }

        @Override
        protected void onPostExecute(Pair<Integer, List<TaskGroup>> data) {
            super.onPostExecute(data);

            List<TaskGroup> groups = data.second;
            int errorCode = data.first;

            if(groups != null && groups.size() > 0 && errorCode == 0)
                onTasksLoadCompleteListener.onSuccess(groups);
            else if (errorCode == 0)
                onTasksLoadCompleteListener.onFail(ERROR_LIST_NULL);
            else
                onTasksLoadCompleteListener.onFail(errorCode);
        }
    }

    private class BookLoadTask extends AsyncTask<String, Void, Pair<Integer, List<Book>>>{
        @Override
        protected Pair<Integer, List<Book>> doInBackground(String... urls) {
            try {
                List<Book> books = new ArrayList<>();

                Element booksList = Jsoup
                        .connect(urls[0])
                        .get()
                        .body()
                        .select("div#subject.full")
                        .first();

                for(Element a : booksList.select("a.book")){
                    Book book = new Book();
                    book.setUrl(BASE_URL + a.attr("href"));
                    book.setImageUrl(BASE_URL + "/" + a.select("div.title").select("img").attr("src"));
                    book.setName(a.select("div.name").first().text());
                    book.setAuthors(a.select("div.authors").first().text());
                    book.setType(a.select("div.type").first().text());
                    book.setPublisher(a.select("div.publisher_book").first().text());
                    books.add(book);
                }

                return new Pair<>(0,books);

            }catch (HttpStatusException e){
                e.printStackTrace();
                return new Pair<>(CONNECTION_ERROR,null);
            } catch (NullPointerException e){
                e.printStackTrace();
                return new Pair<>(SUBJECT_EMPTY_ERROR,null);
            } catch (IOException e) {
                e.printStackTrace();
                return new Pair<>(CONNECTION_ERROR,null);
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
           // onBooksLoadComplete.onFail(CANCELLED);
        }

        @Override
        protected void onPostExecute(Pair<Integer, List<Book>> data) {
            super.onPostExecute(data);

            List<Book> books = data.second;
            int errorCode = data.first;

            if(books != null && books.size() > 0 && errorCode == 0)
                onBooksLoadComplete.onSuccess(books);
            else if (errorCode == 0)
                onBooksLoadComplete.onFail(ERROR_LIST_NULL);
            else
                onBooksLoadComplete.onFail(errorCode);
        }
    }

    public interface OnBooksLoadCompleteListener{
        void onSuccess(List<Book> books);

        void onFail(int errorCode);
    }

    public interface OnTasksLoadCompleteListener{
        void onSuccess(List<TaskGroup> tasks);

        void onFail(int errorCode);
    }

    public interface OnDecisionCompleteListener{
        void onSuccess(Decision decision);

        void onFail(int errorCode);
    }
}
