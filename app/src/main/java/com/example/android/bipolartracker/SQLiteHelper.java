package com.example.android.bipolartracker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// This class contains all the field names for the database and also creates it if it doesn't exist

// THIS CLASS ADAPTED FROM http://www.vogella.com/tutorials/AndroidSQLite/article.html
public class SQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "Entries";
    public static final String ID_COLUMN_NAME = "_id";
    public static final String TIMESTAMP_COLUMN_NAME = "time_stamp";
    public static final String MOOD_COLUMN_NAME = "mood";
    public static final String ANXIETYLEVEL_COLUMN_NAME = "anxiety_level";
    public static final String NOTES_COLUMN_NAME = "notes";

    private static final String DATABASE_NAME = "bipolar.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE = "create table if not exists " + TABLE_NAME + "( "
            + ID_COLUMN_NAME + " integer primary key autoincrement not null, "
            + TIMESTAMP_COLUMN_NAME + " integer not null, "
            + MOOD_COLUMN_NAME + " integer not null, "
            + ANXIETYLEVEL_COLUMN_NAME + " integer not null, "
            + NOTES_COLUMN_NAME + " text"
            + ");";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        //database.execSQL("DROP TABLE " + TABLE_NAME);
        database.execSQL(DATABASE_CREATE);
    }

    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {

    }
}
