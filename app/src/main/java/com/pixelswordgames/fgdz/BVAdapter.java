package com.pixelswordgames.fgdz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pixelswordgames.fgdz.POJO.Book;

import java.util.ArrayList;
import java.util.List;

public class BVAdapter extends RecyclerView.Adapter<BVAdapter.BookViewHolder> {

    public static class BookViewHolder extends RecyclerView.ViewHolder{

        ImageView bookImage;
        TextView nameView, authorsView, typeView;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);

            bookImage = ((ImageView)itemView.findViewById(R.id.bImageView));
            nameView = ((TextView)itemView.findViewById(R.id.bNameView));
            authorsView = ((TextView)itemView.findViewById(R.id.bAuthorsView));
            typeView = ((TextView)itemView.findViewById(R.id.bTypeView));
        }

        void setContent(Book book, Context context){
            Glide
                    .with(context)
                    .load(book.getImageUrl())
                    .into(bookImage);
            nameView.setText(book.getName());
            authorsView.setText(context.getString(R.string.b_list_authors) + book.getAuthors());
            typeView.setText(book.getType());
        }
    }

    private Context context;
    private List<Book> books;

    public BVAdapter(Context context){
        this.context = context;

        books = new ArrayList<>();
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item, parent, false);
        BookViewHolder bvh = new BookViewHolder(view);
        return bvh;
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        holder.setContent(books.get(position), context);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public void setBooks(List<Book> books){
        this.books = books;
        notifyDataSetChanged();
    }

    public void setNull(){
        books = new ArrayList<>();
        notifyDataSetChanged();
    }

    public Book getBook(int position){
        return books.get(position);
    }

    public Book removeBook(int position){
        Book book = books.remove(position);
        notifyItemRemoved(position);
        return book;
    }
}
