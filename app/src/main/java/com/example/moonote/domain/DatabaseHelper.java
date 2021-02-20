/*********************************************/
/* program: DatabaseHelper.java              */
/* author: Yathavan                          */
/* purpose: Database management              */
/* project: UofT Hackathon 2021              */
/*********************************************/

package com.example.moonote.domain;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

public class DatabaseHelper extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "moonote.db";
    private static final int DATABASE_VERSION = 1;

    public class Entry
    {
        // ENTRY TABLE
        public static final String TABLE_NAME = "ENTRY";
        public static final String ID = "_id";
        public static final String BODY = "BODY";
        public static final String DATE = "DATE";
        public static final String SENTIMENT = "SENTIMENT";
        public static final String LATITUDE = "LATITUDE";
        public static final String LONGITUDE = "LONGITUDE";
    }

    private static DatabaseHelper instance = null;

    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DatabaseHelper getInstance(Context context)
    {
        return instance == null ? new DatabaseHelper(context) : instance;
    }
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            db.disableWriteAheadLogging();
        }
}

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        // ENTRY TABLE
        String create_entry_db = "CREATE TABLE "+ Entry.TABLE_NAME +" ("+Entry.ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Entry.BODY + " TEXT, " +
                Entry.DATE + " NUMERIC, " +
                Entry.SENTIMENT + " REAL, " +
                Entry.LATITUDE + " REAL, " +
                Entry.LONGITUDE + " REAL);";

        sqLiteDatabase.execSQL(create_entry_db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {

    }
}
