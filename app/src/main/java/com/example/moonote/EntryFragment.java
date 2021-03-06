package com.example.moonote;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moonote.Journal.Entry;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class EntryFragment extends Fragment implements MyEntryRecyclerViewAdapter.OnViewEntryListener {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static RecyclerView recyclerView;
    private static List<Entry> mItems;
    // TODO: Customize parameters
    private int mColumnCount = 1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EntryFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static EntryFragment newInstance(int columnCount) {
        EntryFragment fragment = new EntryFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

//    public static void addItem(Entry entry) {
//        mItems.add(entry);
//        recyclerView.setAdapter(new MyEntryRecyclerViewAdapter(mItems, this));
//    }

    public void setAdapter(List<Entry> items) {
        mItems = items;
        recyclerView.setAdapter(new MyEntryRecyclerViewAdapter(mItems, this, getActivity()));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mItems = new ArrayList<>();

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            //recyclerView.setAdapter(new MyEntryRecyclerViewAdapter(DummyContent.ITEMS));
        }

        recyclerView.setAdapter(new MyEntryRecyclerViewAdapter(mItems, this, getActivity()));

        return view;
    }

    @Override
    public void onEntryClick(Entry entry) {
        Intent intent = new Intent(getActivity(), EditEntryActivity.class);
        intent.putExtra(EditEntryActivity.KEY_ENTRY_ID, entry.get_id());
        startActivity(intent);
    }
}