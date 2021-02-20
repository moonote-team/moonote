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
    private EditText journalText;
    private EntryManager entryManager;
    private long entryID;


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
            entryID = extras.getLong(KEY_ENTRY_ID);
            loadEntry(entryID, entryManager);
        } else {
            entryID = new Time(Calendar.getInstance().getTime().getTime()).getTime();
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
                Log.i("MENU ITEM", "ACTION BUTTON");
                Toast.makeText(this, "RUNNING SAVE", Toast.LENGTH_SHORT).show();
                saveEntry(entryManager);
                return true;
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadEntry(long id, EntryManager manager) {
        Entry entry = manager.getEntryByID(id);
        if (entry != null) {
            journalText.setText(entry.getBody());
        }
    }

    private void saveEntry(EntryManager manager) {
//        https://stackoverflow.com/questions/18056814/how-can-i-capture-the-formatting-of-my-edittext-text-so-that-bold-words-show-as
        Time currentTime = new Time(Calendar.getInstance().getTime().getTime());
        String plainText = journalText.getText().toString();

        Entry entry = manager.getEntryByID(entryID);
        if (entry == null) {
            entry = new Entry(entryID, plainText, currentTime.getTime());
            manager.addEntry(entry);
            Log.i("ADDING ENTRY", String.format("entryID: %d, text: %s, epoch, %d", entry.get_id(), entry.getBody(), entry.getDate()));
        } else {
            //Case where we already have this Entry in the database
            // Think time should be the date of most recent editing
            entry = new Entry(entryID, plainText, currentTime.getTime());
            manager.updateItem(entry);
            Log.i("UPDATING ENTRY ENTRY", String.format("New Entry value: entryID: %d, text: %s, epoch, %d", entry.get_id(), entry.getBody(), entry.getDate()));
        }


    }
}