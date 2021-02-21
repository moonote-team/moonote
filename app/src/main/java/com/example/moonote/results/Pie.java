package com.example.moonote.results;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.moonote.Journal.Entry;
import com.example.moonote.R;
import com.example.moonote.middleware.EntryManager;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

public class Pie extends Fragment
{
    private final Context myContext = this.getContext();
    EntryManager entryManager = new EntryManager(myContext);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View pie_chart_view = inflater.inflate(R.layout.results_piechart, container, false);

        PieChart pieChart = pie_chart_view.findViewById(R.id.piechart);

        List<Entry> entries = entryManager.getAllEntries();
        List<PieEntry> data_entries = new ArrayList<PieEntry>();

        for (Entry entry : entries)
        {
            data_entries.add(new PieEntry(entry.getDate(), (float)entry.getSentiment()));
        }

        PieDataSet dataSet = new PieDataSet(data_entries, "MoodChart");

        dataSet.setColor(Color.BLUE);

        dataSet.setValueTextColor(Color.WHITE);

        PieData pieData = new PieData(dataSet);
        pieChart.setData(pieData);

        pieChart.invalidate();

        return pie_chart_view;
    }
}
