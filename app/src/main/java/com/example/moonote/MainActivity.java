package com.example.moonote;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.moonote.Journal.Entry;
import com.example.moonote.domain.DatabaseHelper;
import com.example.moonote.middleware.EntryManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EntryManager entryManager;
    private EntryFragment entries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, EditEntryActivity.class);
            startActivity(intent);
        });

        CalendarView calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                // Create a Date object for the beginning of the day, get Epoch from that
                Calendar start = Calendar.getInstance();
                start.clear();
                start.set(year, month, dayOfMonth);
                long epochStart = start.getTime().getTime();
                long epochEnd = epochStart + (1000 * 60 * 60 * 24) - 1;

                // Then, make SQL request for all entries between these two Epochs. Render them somehow
                List<Entry> results = entryManager.runQuery("SELECT * FROM ENTRY WHERE " +
                        DatabaseHelper.Entry.DATE + " BETWEEN " + epochStart + " AND " + epochEnd);

                entries = (EntryFragment) getSupportFragmentManager().findFragmentById(R.id.entries);
                entries.setAdapter(results);
            }
        });

        entryManager = new EntryManager(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}