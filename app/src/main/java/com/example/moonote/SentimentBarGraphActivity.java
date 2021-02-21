package com.example.moonote;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moonote.Journal.Entry;
import com.example.moonote.middleware.EntryManager;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class SentimentBarGraphActivity extends AppCompatActivity {
    BarChart barChart;
    private EntryManager manager;
    private TextView negativeText, neutralText, positiveText;
    private int negativePercent, neutralPercent, positivePercent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sentiment_bar_graph);
        barChart = findViewById(R.id.sentiment_bargraph);
        manager = new EntryManager(this);
        initDataset();
        initBarChart();
        showBarChart();

    }


    private void initBarDataSet(BarDataSet barDataSet) {
        barDataSet.setDrawValues(false);

    }

    private void initBarChart() {
        barChart.setDrawGridBackground(false);
        barChart.setDrawBorders(false);
        Description description = new Description();
        description.setEnabled(false);
        barChart.setDescription(description);
        barChart.animateY(1000);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
//        xAxis.setDrawLabels(false);
        barChart.getAxisRight().setDrawLabels(false);
        String[] labels = {"Negative Entries:" + negativePercent + "%", "Neutral Entries:" + neutralPercent + "%", "Positive Entries:" + positivePercent + "%"};
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        barChart.getLegend().setEnabled(false);

    }

    private void initDataset() {

        List<Entry> journalEntries = manager.getAllEntries();
        int numOfNegatives = 0;
        int numOfNeutrals = 0;
        int numOfPositives = 0;

        for (Entry journalEntry : journalEntries) {
            double sentiment = journalEntry.getSentiment();

            boolean isNegative = (CategorizationBound.NEGATIVE_LOWER_BOUND_INCLUSIVE.bound <= sentiment && sentiment <= CategorizationBound.NEGATIVE_HIGHER_BOUND_INCLUSIVE.bound);
            boolean isNeutral = (CategorizationBound.NEUTRAL_LOWER_BOUND_EXCLUSIVE.bound < sentiment && sentiment < CategorizationBound.NEUTRAL_UPPER_BOUND_EXCLUSIVE.bound);
            boolean isPositive = (CategorizationBound.POSITVE_LOWER_BOUND_INCLUSIVE.bound <= sentiment && sentiment <= CategorizationBound.POSITIVE_UPPER_BOUND_INCLUSIVE.bound);

            if (isNegative) {
                numOfNegatives++;
            } else if (isNeutral) {
                numOfNeutrals++;
            } else if (isPositive) {
                numOfPositives++;
            } else {
                Log.e("Sentiment Graph", "wasn't categorized as negative, neutral or positive");
            }
        }
        int sum = numOfNegatives + numOfNeutrals + numOfPositives;
        negativePercent = Math.round(100 * (((float) numOfNegatives) / sum));
        neutralPercent = Math.round(100 * (((float) numOfNeutrals) / sum));
        positivePercent = Math.round(100 * (((float) numOfPositives) / sum));
    }

    private void showBarChart() {
        String title = "Title";

        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(0, negativePercent));
        barEntries.add(new BarEntry(1, neutralPercent));
        barEntries.add(new BarEntry(2, positivePercent));

        BarDataSet barDataSet = new BarDataSet(barEntries, title);
        initBarDataSet(barDataSet);

        BarData data = new BarData(barDataSet);
        barChart.setData(data);
        barChart.invalidate();


    }


    private enum CategorizationBound {
        NEGATIVE_LOWER_BOUND_INCLUSIVE(-1.0), NEGATIVE_HIGHER_BOUND_INCLUSIVE(-0.4),
        NEUTRAL_LOWER_BOUND_EXCLUSIVE(-0.4), NEUTRAL_UPPER_BOUND_EXCLUSIVE(0.4),
        POSITVE_LOWER_BOUND_INCLUSIVE(0.4), POSITIVE_UPPER_BOUND_INCLUSIVE(1.0);
        private final double bound;

        private CategorizationBound(double bound) {
            this.bound = bound;
        }

    }
}