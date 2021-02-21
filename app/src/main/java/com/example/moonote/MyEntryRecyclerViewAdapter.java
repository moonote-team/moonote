package com.example.moonote;

import android.app.AlertDialog;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moonote.Journal.Entry;
import com.example.moonote.middleware.EntryManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * TODO: Replace the implementation with code for your data type.
 */
public class MyEntryRecyclerViewAdapter extends RecyclerView.Adapter<MyEntryRecyclerViewAdapter.ViewHolder> {

    private final List<Entry> mValues;
    private OnViewEntryListener onViewEntryListener;

    public MyEntryRecyclerViewAdapter(List<Entry> items, OnViewEntryListener onViewEntryListener) {
        this.onViewEntryListener = onViewEntryListener;
        mValues = items;
    }

    @NonNull
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
        Date date = calendar.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
        String time = simpleDateFormat.format(date);
        Double sentiment = mValues.get(position).getSentiment();
        String approxSentiment = String.format(Locale.getDefault(), "%.3f", sentiment);

        holder.mItem = mValues.get(position);
        holder.txtDateTime.setText(time);
//        holder.txtEntryPreview.setText(mValues.get(position).getBody());
        holder.txtSentiment.setText(approxSentiment);

        int textColor;
        if (sentiment > 0.0) {
            textColor = Color.GREEN;
        } else if (sentiment < 0.0) {
            textColor = Color.RED;
        } else {
            textColor = Color.BLACK;
        }

        String mood;
        if (sentiment >= 8.0) mood = "Excellent";
        else if (sentiment >= 6.0) mood = "Great";
        else if (sentiment >= 3.0) mood = "Good";
        else if (sentiment >= 1.5) mood = "Decent";
        else if (sentiment >= -1.5) mood = "Average";
        else if (sentiment >= -3.0) mood = "Grumpy";
        else if (sentiment >= -6.0) mood = "Bad";
        else if (sentiment >= -8.0) mood = "Terrible";
        else if (sentiment >= -10.0) mood = "Very Worrysome";
        else mood = "Undefined";
        holder.txtSentiment.append(" (" + mood + ")");

        holder.txtDateTime.setTextColor(textColor);
        holder.txtSentiment.setTextColor(textColor);

        holder.btnEdit.setOnClickListener(view -> {
            // line below is how we launch new activity
            onViewEntryListener.onEntryClick(holder.mItem);
        });
        holder.btnDelete.setOnClickListener(view -> {
            // Pop up saying are you sure?????
            // Do the deletion if user selects yes

            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setMessage("Are you sure you want to delete?");
            builder.setNegativeButton("Yeah", (dialogInterface, i) ->
            {
                // delete entry
                EntryManager entryManager = new EntryManager(view.getContext());
                entryManager.deleteEntry(mValues.get(position).get_id());
            });
            builder.setPositiveButton("Nah", (dialogInterface, i) ->
            {
                // exit dialog here
                dialogInterface.dismiss();
            });
            builder.create().show();

        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public interface OnViewEntryListener {
        void onEntryClick(Entry entry);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView txtDateTime;
        //        public final TextView txtEntryPreview;
        public final TextView txtSentiment;
        public final ImageButton btnEdit;
        public final ImageButton btnDelete;
        public Entry mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            txtDateTime = (TextView) view.findViewById(R.id.txtDateTime);
//            txtEntryPreview = (TextView) view.findViewById(R.id.txtEntryPreview);
            txtSentiment = (TextView) view.findViewById(R.id.txtSentiment);
            btnEdit = (ImageButton) view.findViewById(R.id.btnEdit);
            btnDelete = (ImageButton) view.findViewById(R.id.btnDelete);
        }

        @Override
        public String toString() {
            return super.toString();
        }

    }
}