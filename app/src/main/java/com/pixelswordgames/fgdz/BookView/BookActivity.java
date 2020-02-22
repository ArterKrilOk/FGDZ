package com.pixelswordgames.fgdz.BookView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pixelswordgames.fgdz.POJO.Book;
import com.pixelswordgames.fgdz.POJO.TaskGroup;
import com.pixelswordgames.fgdz.Parser.SiteParser;
import com.pixelswordgames.fgdz.R;
import com.pixelswordgames.fgdz.db.DbLab;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class BookActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView nameView, authorsView, typeView, infoView;
    private RecyclerView tasksView;
    private  ProgressBar progressBar;
    private ImageView starView, sourceView;

    private SiteParser parser;
    private Book book;
    private GVAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        book = new Book();
        parser = new SiteParser(this);

        book.setUrl(getIntent().getStringExtra("bookUrl"));
        book.setName(getIntent().getStringExtra("bookName"));
        book.setImageUrl(getIntent().getStringExtra("bookImage"));
        book.setAuthors(getIntent().getStringExtra("bookAuthors"));
        book.setType(getIntent().getStringExtra("bookType"));
        book.setPublisher(getIntent().getStringExtra("bookPub"));

        DbLab.get(this).addHistoryBook(book);

        imageView = ((ImageView)findViewById(R.id.cImageView));
        sourceView = ((ImageView)findViewById(R.id.cSourceView));
        nameView = ((TextView)findViewById(R.id.cNameView));
        typeView = ((TextView)findViewById(R.id.cTypeView));
        progressBar = ((ProgressBar)findViewById(R.id.cTasksProgressBar));
        authorsView = ((TextView)findViewById(R.id.cAuthorsView));
        tasksView = ((RecyclerView)findViewById(R.id.cTasksView));
        starView = ((ImageView)findViewById(R.id.cStarView));
        infoView  = ((TextView)findViewById(R.id.cInfoView));

        adapter = new GVAdapter(this);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        tasksView.setLayoutManager(llm);
        tasksView.addItemDecoration(new DividerItemDecoration(tasksView.getContext(), llm.getOrientation()));
        tasksView.setHasFixedSize(true);
        tasksView.setAdapter(adapter);
        nameView.setText(book.getName());
        authorsView.setText(book.getAuthors());
        typeView.setText(book.getType());
        checkIsFavorite();
        Glide
                .with(getApplicationContext())
                .load(book.getImageUrl())
                .into(imageView);

        loadBookTasks();

        starView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!DbLab.get(getApplicationContext()).isFavorite(book)) {
                    DbLab.get(getApplicationContext()).addFavoriteBook(book);
                    Toasty.success(BookActivity.this, book.getName(), Toast.LENGTH_SHORT).show();
                } else {
                    DbLab.get(getApplicationContext()).deleteFavoriteBook(book);
                    Toasty.error(BookActivity.this,book.getName(),Toast.LENGTH_SHORT).show();
                }
                checkIsFavorite();
            }
        });

        sourceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(book.getUrl()));
                startActivity(i);
            }
        });
    }

    void checkIsFavorite(){
        starView.setImageResource(DbLab.get(this).isFavorite(book)? R.drawable.ic_star_24dp : R.drawable.ic_star_border_24dp);
    }

    private void loadBookTasks(){
        progressBar.setVisibility(View.VISIBLE);
        parser.loadTask(book.getUrl(), new SiteParser.OnTasksLoadCompleteListener() {
            @Override
            public void onSuccess(List<TaskGroup> groups) {
                progressBar.setVisibility(View.GONE);
                if(groups.size() < 3)
                    adapter.setShowTasks(true);
                adapter.setGroups(groups);
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
