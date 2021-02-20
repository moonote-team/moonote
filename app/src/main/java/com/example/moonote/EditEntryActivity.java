package com.example.moonote;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.moonote.Journal.Entry;
import com.example.moonote.middleware.EntryManager;

import java.sql.Time;
import java.util.Calendar;


public class EditEntryActivity extends AppCompatActivity {
    public static final String KEY_ENTRY_ID = "com.example.moonote.KEY_ENTRY_ID";
    private final int INVALID_ID = -1;
    private EditText journalText;
    private EntryManager entryManager;
    private int entryID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_entry);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        journalText = findViewById(R.id.journal_text);
        entryManager = new EntryManager(this);
        // Assume you get passed the times
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            entryID = extras.getInt(KEY_ENTRY_ID);
            loadEntry(entryID, entryManager);
        } else {
            entryID = INVALID_ID;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_entry_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                saveEntry(entryManager);
                finish();
                return true;
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadEntry(int id, EntryManager manager) {
        Entry entry = manager.getEntryByID(id);
        if (entry != null) {
            journalText.setText(entry.getBody());
        }
        // call this in OnCreate()
        //get given relevant info for the sql query
//        String formattedText;
//        Spanned text = Html.fromHtml(formattedText);
//        journalText.setText(text);
    }

    private void saveEntry(EntryManager manager) {
//        https://stackoverflow.com/questions/18056814/how-can-i-capture-the-formatting-of-my-edittext-text-so-that-bold-words-show-as
        Time currentTime = new Time(Calendar.getInstance().getTime().getTime());
        String plainText = journalText.getText().toString();

        Entry entry = manager.getEntryByID(entryID);
        if (entry == null) {
            entry = new Entry(plainText, currentTime.getTime());
            manager.addEntry(entry);
        } else {
            entry.setBody(plainText);
            manager.updateItem(entry);
        }
    }
}