package com.example.moonote;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.services.language.v1.CloudNaturalLanguage;
import com.google.api.services.language.v1.CloudNaturalLanguageRequestInitializer;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SentimentAPIManager {
//    private Context context;
//    private RequestQueue queue;
//    private final String endpoint;
//    private final String api_key;

    public SentimentAPIManager(Context context) {
//        queue = Volley.newRequestQueue(context);
//        endpoint = "https://language.googleapis.com/v1beta2/documents:annotateText";
//        api_key = context.getResources().getString(R.string.api_key);
    }

    public void annotateText(String text, Response.Listener<JSONObject> listener) {
//        // Form the document part of the request
//        Map<String, Object> document = new HashMap<>();
//        document.put("type", "PLAIN_TEXT");
//        document.put("content", text);
//        // Form the features part of the request
//        Map<String, Object> features = new HashMap<>();
//        features.put("extractSyntax", true);
//        features.put("extractEntities", true);
//        features.put("extractDocumentSentiment", true);
//        features.put("extractEntitySentiment", true);
//        features.put("classifyText", true);
//        // Form the root of the request
//        Map<String, Object> root = new HashMap<>();
//        root.put("document", document);
//        root.put("features", features);
//        root.put("encodingType", "NONE");
//        // Form the JSON request object
//        JSONObject reqBody = new JSONObject(root);
//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, endpoint,
//                reqBody, listener, null);
//
//        queue.add(jsonObjectRequest);
    }
}