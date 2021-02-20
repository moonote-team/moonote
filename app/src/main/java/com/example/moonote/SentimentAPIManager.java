package com.example.moonote;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class SentimentAPIManager {
    private Context context;
    private RequestQueue queue;
    private final String endpoint =
            "https://language.googleapis.com/v1beta2/documents:annotateText";

    public SentimentAPIManager(Context context) {
        queue = Volley.newRequestQueue(context);
    }
}