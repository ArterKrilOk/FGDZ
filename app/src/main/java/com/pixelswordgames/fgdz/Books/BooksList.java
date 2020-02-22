package com.pixelswordgames.fgdz.Books;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pixelswordgames.fgdz.BVAdapter;
import com.pixelswordgames.fgdz.BookView.BookActivity;
import com.pixelswordgames.fgdz.POJO.Book;
import com.pixelswordgames.fgdz.Parser.SiteParser;
import com.pixelswordgames.fgdz.Parser.Subjects;
import com.pixelswordgames.fgdz.R;
import com.pixelswordgames.fgdz.RecyclerItemClickListener;
import com.pixelswordgames.fgdz.db.DbLab;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

import static com.pixelswordgames.fgdz.Parser.Subjects.MAIN_SUBJECTS;

public class BooksList extends Fragment {

    private RecyclerView booksList;
    private TextView infoView;
    private ProgressBar progressBar;
    private Context context;
    private Spinner subjectView, classView;

    private BVAdapter adapter;
    private SiteParser parser;
    private List<Book> bookList;
    private Subjects subjects;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.books_search, container, false);

        context = view.getContext();
        parser = new SiteParser(context);
        adapter = new BVAdapter(context);
        subjects = new Subjects(context);

        booksList = ((RecyclerView)view.findViewById(R.id.sBookList));
        infoView = ((TextView)view.findViewById(R.id.sListInfo));
        progressBar = ((ProgressBar)view.findViewById(R.id.sProgressBar));
        subjectView = ((Spinner)view.findViewById(R.id.bSubjectSpinner));
        classView = ((Spinner)view.findViewById(R.id.bClassSpinner));

        booksList.setLayoutManager(new LinearLayoutManager(context));
        booksList.setHasFixedSize(false);
        booksList.setAdapter(adapter);

        ArrayAdapter<?> cAdapter = ArrayAdapter.createFromResource(context, R.array.stClasses, android.R.layout.simple_spinner_item);
        cAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classView.setAdapter(cAdapter);

        ArrayAdapter<?> sAdapter = ArrayAdapter.createFromResource(context, R.array.mainSub, android.R.layout.simple_spinner_item);
        sAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subjectView.setAdapter(sAdapter);

        if(bookList != null) {
            progressBar.setVisibility(View.GONE);
            adapter.setBooks(bookList);
        }

        classView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                subjects.setCurClass(i+1);
                //updateSubSpinner(subjects);

                loadBooks(subjects.getCurClass(),subjects.getCurSub());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        subjectView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                subjects.setCurSub(MAIN_SUBJECTS[i]);

                loadBooks(subjects.getCurClass(),subjects.getCurSub());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        booksList.addOnItemTouchListener(new RecyclerItemClickListener(context, booksList, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Book book = adapter.getBook(position);

                Intent intent = new Intent(context, BookActivity.class);
                intent.putExtra("bookName",book.getName());
                intent.putExtra("bookUrl",book.getUrl());
                intent.putExtra("bookImage",book.getImageUrl());
                intent.putExtra("bookAuthors",book.getAuthors());
                intent.putExtra("bookType",book.getType());
                intent.putExtra("bookPub",book.getPublisher());

                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {
                Book book = adapter.getBook(position);
                DbLab.get(getActivity()).addFavoriteBook(book);
                Toasty.success(context,book.getName(), Toast.LENGTH_LONG).show();
            }
        }));

        return view;
    }

    void updateSubSpinner(Subjects subjects){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context,android.R.layout.simple_spinner_item,subjects.getSubNames());
        subjectView.setAdapter(adapter);
        subjectView.setSelection(0);
    }

    void loadBooks(int stYear, String lesson){
        bookList = new ArrayList<>();
        adapter.setNull();
        infoView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        parser.loadBooks(stYear,lesson, new SiteParser.OnBooksLoadCompleteListener() {
            @Override
            public void onSuccess(List<Book> books) {
                infoView.setVisibility(View.GONE);
                adapter.setBooks(books);
                bookList = books;
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFail(int errorCode) {
                progressBar.setVisibility(View.GONE);
                showError(errorCode);
            }
        });
    }

    private void showError(int errorCode){
        infoView.setVisibility(View.VISIBLE);
        switch (errorCode){
            case SiteParser.CONNECTION_ERROR:
                infoView.setText(R.string.error_connection);
                break;
            case SiteParser.NO_CONDITIONS:
                infoView.setText(R.string.error_no_decisions);
                break;
            case SiteParser.ERROR_LIST_NULL:
                infoView.setText(R.string.error_list_empty);
                break;
            case SiteParser.SUBJECT_EMPTY_ERROR:
                infoView.setText(R.string.error_no_subject);
                break;
        }
    }
}
