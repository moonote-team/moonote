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
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


public class EditEntryActivity extends AppCompatActivity {
    private EditText journalText;
    private EntryManager entryManager;
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_entry_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save) {
            Log.i("MENU ITEM", "ACTION BUTTON");
            Toast.makeText(this, "RUNNING SAVE", Toast.LENGTH_SHORT).show();
            saveEntry();
            return true;
        }
        else if (id == R.id.action_settings)
            return true;

        return super.onOptionsItemSelected(item);
    }

    private void loadEntry() {
        // Just testing on the first entry that exists
        List<Entry> entries = entryManager.getAllEntries();
        if (entries.isEmpty()) {
            // return;
        } else {
            //PLACEHOLDER
            Entry entry = entries.get(0);
            String plainText = entry.getBody();
            journalText.setText(plainText);
        }
        // call this in OnCreate()
        //get given relevant info for the sql query
//        String formattedText;
//        Spanned text = Html.fromHtml(formattedText);
//        journalText.setText(text);
    }

    private void saveEntry() {
//        https://stackoverflow.com/questions/18056814/how-can-i-capture-the-formatting-of-my-edittext-text-so-that-bold-words-show-as
        String plainText = journalText.getText().toString();
        Long time = Calendar.getInstance().getTimeInMillis();
        Entry thisEntry = new Entry(plainText, time);

        executor.submit(new Runnable() {
            @Override
            public void run() {
                Sentiment sentiment = makeAnnotateRequest(plainText);
                double score = sentiment.getScore();
                thisEntry.setSentiment(score);
            }
        });

        entryManager.addEntry(thisEntry);
        EntryFragment.addItem(thisEntry);
        finish();
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