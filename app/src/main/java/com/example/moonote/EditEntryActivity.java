package com.example.moonote;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.moonote.Journal.Entry;
import com.example.moonote.middleware.EntryManager;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.services.language.v1.CloudNaturalLanguage;
import com.google.api.services.language.v1.CloudNaturalLanguageRequestInitializer;
import com.google.api.services.language.v1.model.AnnotateTextRequest;
import com.google.api.services.language.v1.model.AnnotateTextResponse;
import com.google.api.services.language.v1.model.Document;
import com.google.api.services.language.v1.model.Features;
import com.google.api.services.language.v1.model.Sentiment;

import java.io.IOException;
import java.sql.Time;
import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


public class EditEntryActivity extends AppCompatActivity {
    public static final String KEY_ENTRY_ID = "com.example.moonote.KEY_ENTRY_ID";
    public static final int EDIT_ENTRY_REQUEST_CODE = 2;
    private final int INVALID_ID = -1;
    private EditText journalText;
    private EntryManager entryManager;
    private int entryID;

    private CloudNaturalLanguage naturalLanguageService;
    private String apiKey;
    private ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_entry);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        journalText = findViewById(R.id.journal_text);
        entryManager = new EntryManager(this);
        apiKey = getResources().getString(R.string.api_key);
        naturalLanguageService = new CloudNaturalLanguage.Builder(
                AndroidHttp.newCompatibleTransport(),
                new AndroidJsonFactory(),
                null
        ).setCloudNaturalLanguageRequestInitializer(
                new CloudNaturalLanguageRequestInitializer(apiKey)
        ).build();
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
            Entry finalEntry = entry;
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    Sentiment sentiment = makeAnnotateRequest(plainText);
                    double score = sentiment.getScore();
                    Log.i("SCORE FROM ADDING", String.valueOf(score));
                    finalEntry.setSentiment(score);
                    manager.addEntry(finalEntry);
                }
            });
        } else {
            entry.setBody(plainText);
            Entry finalizedEntry = entry;
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    Sentiment sentiment = makeAnnotateRequest(plainText);
                    double score = sentiment.getScore();
                    Log.i("SCORE FROM UPDATE", String.valueOf(score));
                    finalizedEntry.setSentiment(score);
                    manager.updateItem(finalizedEntry);
                }
            });
        }
    }

    public Sentiment makeAnnotateRequest(String plainText) {
        Document document = new Document();
        document.setType("PLAIN_TEXT");
        document.setLanguage("en-US");
        document.setContent(plainText);

        Features features = new Features();
        features.setExtractEntities(true);
        features.setExtractDocumentSentiment(true);
        features.setExtractSyntax(true);

        AnnotateTextRequest request = new AnnotateTextRequest();
        request.setDocument(document);
        request.setFeatures(features);

        AnnotateTextResponse response = null;
        try {
            response = naturalLanguageService.documents().annotateText(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (response != null) {
            Sentiment sent = response.getDocumentSentiment();
            return sent;
        }

        return null;
    }
}