package com.example.moonote;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.moonote.Journal.Entry;
import com.example.moonote.domain.DatabaseHelper;
import com.example.moonote.mapstuff.MapActivity;
import com.example.moonote.middleware.EntryManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DatabaseChangedReceiver.DatabaseChangedListener {
    private EntryManager entryManager;
    private EntryFragment entries;
    private CalendarView calendarView;
    private DatabaseChangedReceiver dbChangeReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        dbChangeReceiver = new DatabaseChangedReceiver(this);
        IntentFilter filter = new IntentFilter(DatabaseChangedReceiver.ACTION_DATABASE_CHANGED);
        this.registerReceiver(dbChangeReceiver, filter);


        entryManager = new EntryManager(this);
        entries = (EntryFragment) getSupportFragmentManager().findFragmentById(R.id.entries);


        calendarView = findViewById(R.id.calendarView);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                updateCurrentEntries(year, month, dayOfMonth);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, EditEntryActivity.class);
            startActivity(intent);
        });

        calendarView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View view) {
                Calendar date = Calendar.getInstance();
                int year = date.get(Calendar.YEAR);
                int month = date.get(Calendar.MONTH);
                int day = date.get(Calendar.DAY_OF_MONTH);
                updateCurrentEntries(year, month, day);
            }

            @Override
            public void onViewDetachedFromWindow(View view) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(dbChangeReceiver);
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

    public void updateCurrentEntries(int year, int month, int dayOfMonth) {
        // Create a Date object for the beginning of the day, get Epoch from that
        Calendar start = Calendar.getInstance();
        start.clear();
        start.set(year, month, dayOfMonth);
        long epochStart = start.getTime().getTime();
        long epochEnd = epochStart + (1000 * 60 * 60 * 24) - 1;
        List<Entry> results = entryManager.runQuery("SELECT * FROM ENTRY WHERE " +
                DatabaseHelper.Entry.DATE + " BETWEEN " + epochStart + " AND " + epochEnd);

        entries.setAdapter(results);
    }


    @Override
    public void onDatabaseChange() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String selectedDate = sdf.format(new Date(calendarView.getDate()));
        String[] splitSelectedDate = selectedDate.split("/");
        int day = Integer.parseInt(splitSelectedDate[0]);
        int month = Integer.parseInt(splitSelectedDate[1]) - 1;
        int year = Integer.parseInt(splitSelectedDate[2]);
        updateCurrentEntries(year, month, day);
    }

    public void launchCalendar(View view) {
        // run when pressing calendar button
    }

    public void launchReports(View view) {
        Intent intent = new Intent(this, SentimentBarGraphActivity.class);
        startActivity(intent);
    }

    public void launchEntries(View view) {
        // run when pressing entries button
    }

    public void launchStats(View view) {
        Intent intent = new Intent(this, ViewPieActivity.class);
        startActivity(intent);
        //run when pressing stats button
    }

    public void launchCalendarView(View view) {
        Log.i("RUNNING", "CALENDAR VIEW");
    }
}