package com.pixelswordgames.fgdz.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.pixelswordgames.fgdz.db.BDTables.SQL_CREATE_BOOKS;
import static com.pixelswordgames.fgdz.db.BDTables.SQL_CREATE_HISTORY;
import static com.pixelswordgames.fgdz.db.BDTables.SQL_DELETE_BOOKS;
import static com.pixelswordgames.fgdz.db.BDTables.SQL_DELETE_HISTORY;

public class RookDbHelper extends SQLiteOpenHelper {
    public static final int DB_VERSION = 4;
    public static final String DB_NAME = "RookDb.db";


    public RookDbHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_HISTORY);
        db.execSQL(SQL_CREATE_BOOKS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(SQL_DELETE_BOOKS);
        db.execSQL(SQL_DELETE_HISTORY);

        onCreate(db);
    }
}
