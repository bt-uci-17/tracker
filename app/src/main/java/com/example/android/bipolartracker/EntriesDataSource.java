package com.example.android.bipolartracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.Settings;

import java.util.ArrayList;
import java.util.List;


// This class is the interface for the entry database

// THIS CLASS ADAPTED FROM http://www.vogella.com/tutorials/AndroidSQLite/article.html
public class EntriesDataSource {

    // Database fields
    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;
    private String[] allColumns = {SQLiteHelper.ID_COLUMN_NAME,
            SQLiteHelper.TIMESTAMP_COLUMN_NAME,
            SQLiteHelper.MOOD_COLUMN_NAME,
            SQLiteHelper.ANXIETYLEVEL_COLUMN_NAME,
            SQLiteHelper.NOTES_COLUMN_NAME};

    public EntriesDataSource(Context context) {
        dbHelper = new SQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public DatabaseEntry createDatabaseEntry(int mood, int anxietyLevel, String notes) {

        // It's timestamp time
        long timestamp = System.currentTimeMillis();

        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.TIMESTAMP_COLUMN_NAME, timestamp);
        values.put(SQLiteHelper.MOOD_COLUMN_NAME, mood);
        values.put(SQLiteHelper.ANXIETYLEVEL_COLUMN_NAME, anxietyLevel);
        values.put(SQLiteHelper.NOTES_COLUMN_NAME, notes);

        long insertId = database.insert(SQLiteHelper.TABLE_NAME, null, values);

        // NULL NULL NULL NULL NULL FOR SOME REASON (???)
        Cursor cursor = database.query(SQLiteHelper.TABLE_NAME, allColumns, SQLiteHelper.ID_COLUMN_NAME + " = " + insertId, null, null, null, null, null);

        cursor.moveToFirst();
        DatabaseEntry newDatabaseEntry = cursorToDatabaseEntry(cursor);
        cursor.close();

        return newDatabaseEntry;
    }

    public void deleteDatabaseEntry(DatabaseEntry databaseEntry) {
        long id = databaseEntry.getId();
        database.delete(SQLiteHelper.TABLE_NAME, SQLiteHelper.ID_COLUMN_NAME + " = " + id, null);
        System.out.println("Deleted entry with id: " + id);
    }

    public List<DatabaseEntry> getAllDatabaseEntries() {
        List<DatabaseEntry> databaseEntries = new ArrayList<DatabaseEntry>();

        Cursor cursor = database.query(SQLiteHelper.TABLE_NAME, allColumns, null, null, null, null, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            DatabaseEntry databaseEntry = cursorToDatabaseEntry(cursor);
            databaseEntries.add(databaseEntry);
            cursor.moveToNext();
        }

        cursor.close();
        return databaseEntries;
    }

    public void resetDatabase() {
        // WARNING !!!!!!!!!!!!!!!!!!!! THIS RESETS ALL YOUR DATA
        database = dbHelper.getWritableDatabase();
        database.delete(SQLiteHelper.TABLE_NAME, null, null);
        this.dbHelper.onCreate(this.database);
        database.close();
    }

    private DatabaseEntry cursorToDatabaseEntry(Cursor cursor) {
        DatabaseEntry databaseEntry = new DatabaseEntry();
        databaseEntry.setId(cursor.getLong(0));
        databaseEntry.setTimestamp(cursor.getLong(1));
        databaseEntry.setMood(cursor.getInt(2));
        databaseEntry.setAnxietyLevel(cursor.getInt(3));
        databaseEntry.setNotes(cursor.getString(4));
        return databaseEntry;
    }
}
