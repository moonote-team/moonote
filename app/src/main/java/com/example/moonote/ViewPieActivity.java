package com.example.moonote;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moonote.results.Pie;

public class ViewPieActivity extends AppCompatActivity {
    Pie pieFragment = new Pie();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pie);
    }
}