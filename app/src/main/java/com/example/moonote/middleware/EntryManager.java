/************************************************/
/* program: EntryManager.java                   */
/* author: Yathavan                             */
/* purpose: Perform CRUD operations to/from DB  */
/* project: UofT Hackathon 2021                 */
/************************************************/

package com.example.moonote.middleware;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.moonote.Journal.Entry;
import com.example.moonote.domain.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class EntryManager {
    private DatabaseHelper databaseHelper;

    public EntryManager(Context context) {
        databaseHelper = DatabaseHelper.getInstance(context);
    }

    public List<Entry> getAllEntries() {
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + DatabaseHelper.Entry.TABLE_NAME, null);
        List<Entry> entries = new ArrayList<>();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Entry entry = new Entry(
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.Entry.BODY)),
                        (long) cursor.getColumnIndex(DatabaseHelper.Entry.DATE)
                );
                entries.add(entry);
                cursor.moveToNext();
            }
        }

        cursor.close();
        return entries;
    }

    public void addEntry(Entry entry) {
        ContentValues newEntry = new ContentValues();
        newEntry.put(DatabaseHelper.Entry.BODY, entry.getBody());
        newEntry.put(DatabaseHelper.Entry.DATE, entry.getDate().toString());
        newEntry.put(DatabaseHelper.Entry.ID, entry.get_id().toString());

        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        database.insert(DatabaseHelper.Entry.TABLE_NAME, null, newEntry);
    }

    public void updateItem(Entry entry) {
        ContentValues updateEntry = new ContentValues();
        updateEntry.put(DatabaseHelper.Entry.BODY, entry.getBody());
        updateEntry.put(DatabaseHelper.Entry.DATE, entry.getDate().toString());
        updateEntry.put(DatabaseHelper.Entry.ID, entry.get_id().toString());


        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        String[] args = new String[]{
                String.valueOf(entry.get_id())
        };

        database.update(DatabaseHelper.Entry.TABLE_NAME, updateEntry, DatabaseHelper.Entry.ID + "=?", args);
    }

    public Entry getEntryByID(long id) {
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + DatabaseHelper.Entry.TABLE_NAME + " WHERE " + DatabaseHelper.Entry.ID + " = " + id, null);

        Entry entry = null;

        if (cursor.moveToFirst()) {
            entry = new Entry(
                    (long) cursor.getColumnIndex(DatabaseHelper.Entry.ID),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.Entry.BODY)),
                    (long) cursor.getColumnIndex(DatabaseHelper.Entry.DATE)
            );
        }

        cursor.close();
        return entry;
    }
}
