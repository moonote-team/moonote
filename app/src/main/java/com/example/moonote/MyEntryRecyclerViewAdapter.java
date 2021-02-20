package com.example.moonote;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moonote.Journal.Entry;
import com.example.moonote.dummy.DummyContent.DummyItem;
import com.example.moonote.middleware.EntryManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem}.
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

        holder.mItem = mValues.get(position);
        holder.txtDateTime.setText(time);
//        holder.txtEntryPreview.setText(mValues.get(position).getBody());
        holder.txtSentiment.setText(mValues.get(position).getSentiment().toString());
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