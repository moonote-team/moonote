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

public class DatabaseHelper extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "ENTRY";
    public static final String ID = "";
    public static final String DESCRIPTION = "";
    public static final String TIME = "";

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
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        String create = "CREATE TABLE ENTRY (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "BODY TEXT, " +
                "DATE NUMERIC, " +
                "SENTIMENT REAL, " +
                "LATITUDE REAL, " +
                "LONGITUDE REAL);";
        sqLiteDatabase.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {

    }
}
