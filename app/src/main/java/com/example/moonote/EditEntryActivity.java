package com.example.moonote;

import android.os.Bundle;
import android.text.Editable;
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

import java.util.Calendar;
import java.util.Date;

public class EditEntryActivity extends AppCompatActivity {
    private EditText journalText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_entry);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        journalText = findViewById(R.id.journal_text);

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
        // some way of creating text
        Date today = Calendar.getInstance().getTime();
        Editable text = journalText.getText();
        String formattedText = Html.toHtml(text);
        //depending on sql str
        // make an sql request to add this entry to database
    }
}