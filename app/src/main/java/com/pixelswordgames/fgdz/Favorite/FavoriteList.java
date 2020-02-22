package com.pixelswordgames.fgdz.Favorite;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.pixelswordgames.fgdz.R;
import com.pixelswordgames.fgdz.RecyclerItemClickListener;
import com.pixelswordgames.fgdz.db.DbLab;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class FavoriteList extends Fragment {

    private RecyclerView booksList;
    private TextView infoView;
    private Context context;

    private BVAdapter adapter;
    private List<Book> bookList;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.books_list, container, false);
        context = view.getContext();

        bookList = new ArrayList<>();
        adapter = new BVAdapter(context);

        booksList = ((RecyclerView)view.findViewById(R.id.booksListView));
        infoView = ((TextView)view.findViewById(R.id.listInfo));

        booksList.setHasFixedSize(true);
        booksList.setLayoutManager(new LinearLayoutManager(context));
        booksList.setAdapter(adapter);

        List<Book> books = DbLab.get(getActivity()).getFavoriteBooks();
        if(books != null && books.size() > 0) {
            adapter.setBooks(books);
            infoView.setVisibility(View.GONE);
        } else {
            infoView.setVisibility(View.VISIBLE);
            infoView.setText(R.string.error_list_empty);
        }

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
                Book book = adapter.removeBook(position);
                DbLab.get(getActivity()).deleteFavoriteBook(book);
                Toasty.error(context,book.getName(),Toast.LENGTH_LONG).show();
                if(adapter.getItemCount() == 0) {
                    infoView.setText(R.string.error_list_empty);
                    infoView.setVisibility(View.VISIBLE);
                } else
                    infoView.setVisibility(View.GONE);
            }
        }));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if(bookList != DbLab.get(getActivity()).getFavoriteBooks()){
            adapter.setBooks(DbLab.get(getActivity()).getFavoriteBooks());
        }
    }
}
