package com.pixelswordgames.fgdz.db;

import android.provider.BaseColumns;

public class BDTables {
    public static final String SQL_CREATE_BOOKS = "CREATE TABLE " + BooksTable.TABLE_NAME + " (" +
            BooksTable.COL_ID + " INTEGER PRIMARY KEY," +
            BooksTable.COL_NAME + " TEXT," +
            BooksTable.COL_IMAGE + " TEXT," +
            BooksTable.COL_AUTHORS + " TEXT," +
            BooksTable.COL_TYPE + " TEXT," +
            BooksTable.COL_PUB + " TEXT," +
            BooksTable.COL_URL + " TEXT)";
    public static final String SQL_DELETE_BOOKS = "DROP TABLE IF EXISTS " + BooksTable.TABLE_NAME;

    public static final String SQL_CREATE_HISTORY = "CREATE TABLE " + HistoryTable.TABLE_NAME + " (" +
            HistoryTable.COL_ID + " INTEGER PRIMARY KEY," +
            HistoryTable.COL_BID + " INTEGER" +
            HistoryTable.COL_NAME + " TEXT," +
            HistoryTable.COL_IMAGE + " TEXT," +
            HistoryTable.COL_AUTHORS + " TEXT," +
            HistoryTable.COL_PUB + " TEXT," +
            HistoryTable.COL_TYPE + " TEXT," +
            HistoryTable.COL_URL + " TEXT," +
            HistoryTable.COL_TIMESTAMP + " default current_timestamp)";
    public static final String SQL_DELETE_HISTORY = "DROP TABLE IF EXISTS " + HistoryTable.TABLE_NAME;

    public static class BooksTable implements BaseColumns {
        public static final String TABLE_NAME = "FavBooks";

        public static final String COL_ID = "id";
        public static final String COL_NAME = "name";
        public static final String COL_IMAGE = "image";
        public static final String COL_AUTHORS = "authors";
        public static final String COL_TYPE = "type";
        public static final String COL_PUB = "publisher";
        public static final String COL_URL = "url";
    }

    public static class HistoryTable implements BaseColumns{
        public static final String TABLE_NAME = "HistoryBooks";
        public static final String COL_ID = "id";
        public static final String COL_BID = "bid";
        public static final String COL_NAME = "name";
        public static final String COL_IMAGE = "image";
        public static final String COL_AUTHORS = "authors";
        public static final String COL_TYPE = "type";
        public static final String COL_PUB = "publisher";
        public static final String COL_URL = "url";
        public static final String COL_TIMESTAMP = "datetime";
    }
}
