package com.example.moonote;

import android.os.Bundle;
import android.text.Html;
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
    private EditText journalText;
    private EntryManager entryManager;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_entry);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        journalText = findViewById(R.id.journal_text);
        entryManager = new EntryManager(this);

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
                saveEntry();
                return true;
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadEntry() {
        // call this in OnCreate()
        //get given relevant info for the sql query
//        String formattedText;
//        Spanned text = Html.fromHtml(formattedText);
//        journalText.setText(text);
    }

    private void saveEntry() {
        Time currentTime = new Time(Calendar.getInstance().getTime().getTime());
        String formattedText = Html.toHtml(journalText.getText());
        Entry thisEntry = new Entry(formattedText, currentTime);
        entryManager.addEntry(thisEntry);
    }
}