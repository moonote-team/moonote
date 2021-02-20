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

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class EntryManager
{
    private DatabaseHelper databaseHelper;

    public EntryManager(Context context)
    {
        databaseHelper = DatabaseHelper.getInstance(context);
    }

    public List<Entry> getEntries()
    {
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME, null);
        List<Entry> entries = new ArrayList<>();

        if (cursor.moveToFirst())
        {
            while (cursor.isAfterLast())
            {
                Entry entry = new Entry(
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.DESCRIPTION)),
                    Time.valueOf(cursor.getString(cursor.getColumnIndex(DatabaseHelper.TIME)))
                );
                entries.add(entry);
                cursor.moveToNext();
            }
        }

        cursor.close();
        return entries;
    }

    public void addEntry(Entry entry)
    {
        ContentValues newEntry = new ContentValues();
        newEntry.put(DatabaseHelper.DESCRIPTION, entry.getDescription());
        newEntry.put(DatabaseHelper.TIME, entry.getTime().toString());

        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        database.insert(DatabaseHelper.TABLE_NAME, null, newEntry);
    }

    public void updateItem(Entry entry)
    {
        ContentValues updateEntry = new ContentValues();
        updateEntry.put(DatabaseHelper.DESCRIPTION, entry.getDescription());
        updateEntry.put(DatabaseHelper.TIME, entry.getTime().toString());

        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        String[] args = new String[] {
                String.valueOf(entry.getTime())
        };

        // update here
    }
}
