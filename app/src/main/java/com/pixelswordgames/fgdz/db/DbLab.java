package com.pixelswordgames.fgdz.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;

import com.pixelswordgames.fgdz.POJO.Book;

import java.util.ArrayList;
import java.util.List;

import static com.pixelswordgames.fgdz.db.BDTables.BooksTable;

public class DbLab {
    private static DbLab dbLab;
    
    private Context context;
    private SQLiteDatabase database;

    public static DbLab get(Context context){
        return  new DbLab(context);
    }

    private DbLab(Context context){
        this.context = context.getApplicationContext();
        database = new RookDbHelper(this.context).getWritableDatabase();
    }

    public void updateFavoriteBook(Book book){
        ContentValues values = getFavoriteBookContentValues(book);
        
        database.update(BooksTable.TABLE_NAME,values,BooksTable.COL_ID + " = ?",new String[]{book.getId()+""});
    }
    
    public void addFavoriteBook(Book book){
        ContentValues values = getFavoriteBookContentValues(book);
        
        database.insert(BooksTable.TABLE_NAME,null, values);
    }

    public void addHistoryBook(Book book){
        ContentValues values = getHistoryBookContentValues(book);

        database.insert(BDTables.HistoryTable.TABLE_NAME,null,values);
    }
    
    public void deleteFavoriteBook(Book book){
        ContentValues values = getFavoriteBookContentValues(book);

        database.delete(BooksTable.TABLE_NAME,BooksTable.COL_ID + " = ?",new String[]{book.getId()+""});
    }

    public List<Book> getHistoryBooks(){
        List<Book> books = new ArrayList<>();
        HistoryBookCursorWrapper cursor = queryHistoryBooks(null,null, BooksTable.COL_NAME);

        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                books.add(cursor.getBook());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return books;
    }

    public List<Book> getFavoriteBooks(){
        List<Book> books = new ArrayList<>();
        FavBookCursorWrapper cursor = queryFavoriteBooks(null,null, BooksTable.COL_NAME);

        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                books.add(cursor.getBook());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return books;
    }

    public boolean isFavorite(Book book){
        FavBookCursorWrapper cursor = queryFavoriteBooks(BooksTable.COL_ID + " = ?",new String[]{book.getId()+""}, null);

        try{
            if(cursor.getCount() > 0)
                return true;
        } finally {
            cursor.close();
        }

        return false;
    }


    private static ContentValues getFavoriteBookContentValues(Book book){
        ContentValues values = new ContentValues();
        values.put(BooksTable.COL_ID, book.getId());
        values.put(BooksTable.COL_NAME, book.getName());
        values.put(BooksTable.COL_IMAGE, book.getImageUrl());
        values.put(BooksTable.COL_AUTHORS, book.getAuthors());
        values.put(BooksTable.COL_PUB,book.getPublisher());
        values.put(BooksTable.COL_TYPE,book.getType());
        values.put(BooksTable.COL_URL, book.getUrl());
        return values;
    }

    private static ContentValues getHistoryBookContentValues(Book book){
        ContentValues values = new ContentValues();
        values.put(BDTables.HistoryTable.COL_BID, book.getId());
        values.put(BDTables.HistoryTable.COL_NAME, book.getName());
        values.put(BDTables.HistoryTable.COL_IMAGE, book.getImageUrl());
        values.put(BDTables.HistoryTable.COL_AUTHORS, book.getAuthors());
        values.put(BDTables.HistoryTable.COL_TYPE,book.getType());
        values.put(BDTables.HistoryTable.COL_PUB,book.getPublisher());
        values.put(BDTables.HistoryTable.COL_URL, book.getUrl());
        return values;
    }

    private FavBookCursorWrapper queryFavoriteBooks(String whereClause, String[] whereArgs, String orderBy){
        Cursor cursor = database.query(
                BooksTable.TABLE_NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                orderBy
        );

        return new FavBookCursorWrapper(cursor);
    }

    private HistoryBookCursorWrapper queryHistoryBooks(String whereClause, String[] whereArgs, String orderBy){
        Cursor cursor = database.query(
                BDTables.HistoryTable.TABLE_NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                orderBy
        );

        return new HistoryBookCursorWrapper(cursor);
    }
    
    private class FavBookCursorWrapper extends CursorWrapper{

        public FavBookCursorWrapper(Cursor cursor) {
            super(cursor);
        }

        public Book getBook(){
            String name = getString(getColumnIndex(BooksTable.COL_NAME));
            String imageUrl = getString(getColumnIndex(BooksTable.COL_IMAGE));
            String authors = getString(getColumnIndex(BooksTable.COL_AUTHORS));
            String url = getString(getColumnIndex(BooksTable.COL_URL));
            String type = getString(getColumnIndex(BooksTable.COL_TYPE));
            String pub = getString(getColumnIndex(BooksTable.COL_PUB));

            Book book = new Book();
            book.setName(name);
            book.setType(type);
            book.setPublisher(pub);
            book.setImageUrl(imageUrl);
            book.setAuthors(authors);
            book.setUrl(url);

            return book;
        }
    }

    private class HistoryBookCursorWrapper extends CursorWrapper{

        public HistoryBookCursorWrapper(Cursor cursor) {
            super(cursor);
        }

        public Book getBook(){
            String name = getString(getColumnIndex(BDTables.HistoryTable.COL_NAME));
            String imageUrl = getString(getColumnIndex(BDTables.HistoryTable.COL_IMAGE));
            String authors = getString(getColumnIndex(BDTables.HistoryTable.COL_AUTHORS));
            String url = getString(getColumnIndex(BDTables.HistoryTable.COL_URL));
            String type = getString(getColumnIndex(BDTables.HistoryTable.COL_TYPE));
            String pub = getString(getColumnIndex(BDTables.HistoryTable.COL_PUB));

            Book book = new Book();
            book.setName(name);
            book.setImageUrl(imageUrl);
            book.setType(type);
            book.setPublisher(pub);
            book.setAuthors(authors);
            book.setUrl(url);

            return book;
        }
    }
}
