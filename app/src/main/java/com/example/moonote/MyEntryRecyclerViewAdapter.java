package com.example.moonote;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.moonote.Journal.Entry;
import com.example.moonote.dummy.DummyContent.DummyItem;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyEntryRecyclerViewAdapter extends RecyclerView.Adapter<MyEntryRecyclerViewAdapter.ViewHolder> {

    private final List<Entry> mValues;

    public MyEntryRecyclerViewAdapter(List<Entry> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        long timestamp = mValues.get(position).getDate();
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTimeInMillis(timestamp);
        String time = DateFormat.getTimeInstance().format(calendar.getTime());

        holder.mItem = mValues.get(position);
        holder.txtDateTime.setText(time);
        holder.txtEntryPreview.setText(mValues.get(position).getBody());
        holder.txtSentiment.setText(mValues.get(position).getSentiment().toString());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView txtDateTime;
        public final TextView txtEntryPreview;
        public final TextView txtSentiment;
        public Entry mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            txtDateTime = (TextView) view.findViewById(R.id.txtDateTime);
            txtEntryPreview = (TextView) view.findViewById(R.id.txtEntryPreview);
            txtSentiment = (TextView) view.findViewById(R.id.txtSentiment);
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}