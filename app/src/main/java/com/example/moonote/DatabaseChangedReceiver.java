package com.example.moonote;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class DatabaseChangedReceiver extends BroadcastReceiver {
    public static String ACTION_DATABASE_CHANGED = "com.example.moonote.DatabaseChangedReceiver";
    private DatabaseChangedListener listener;

    public DatabaseChangedReceiver(DatabaseChangedListener listener) {
        this.listener = listener;

    }


    @Override
    public void onReceive(Context context, Intent intent) {
        listener.onDatabaseChange();
    }

    public interface DatabaseChangedListener {
        public void onDatabaseChange();

    }
}