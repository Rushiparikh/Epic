package com.example.rushi.epic_thrillon;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dhaval on 06/12/2017.
 */

public class DbVisit extends SQLiteOpenHelper {
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DbVisitContract.TABLE_NAME + " (" +
                    DbVisitContract._ID + " INTEGER PRIMARY KEY," +
                    DbVisitContract.COLUMN_NAME_TITLE + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DbVisitContract.TABLE_NAME;

   public DbVisit(Context context){
       super(context, DbVisitContract.DATABASE_NAME, null, DbVisitContract.DATABASE_VERSION);

   }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }
}
