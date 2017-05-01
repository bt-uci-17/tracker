package com.example.android.bipolartracker;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

// This and all the other RecyclerView code was adapted from http://www.androidhive.info/2016/01/android-working-with-recycler-view/
public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.MyViewHolder> {

    private List<DatabaseEntry> databaseEntryList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView dateTextView;
        public TextView moodTextView;
        public TextView anxietyTextView;
        public TextView notesTextView;

        public MyViewHolder(View view) {
            super(view);
            dateTextView = (TextView) view.findViewById(R.id.dateTextView);
            moodTextView = (TextView) view.findViewById(R.id.moodTextView);
            anxietyTextView = (TextView) view.findViewById(R.id.anxietyTextView);
            notesTextView = (TextView) view.findViewById(R.id.notesTextView);
        }
    }

    public EntryAdapter(List<DatabaseEntry> databaseEntryList) {
        this.databaseEntryList = databaseEntryList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        DatabaseEntry entry = databaseEntryList.get(position);

        long timestamp = entry.getTimestamp(); // Milliseconds!

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy HH:mm", Locale.US);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));

        String date = simpleDateFormat.format(timestamp);

        String moodKey = entry.getMood() + "";
        MoodMap moodMap = new MoodMap();

        String mood = "Mood: " + entry.getMood() + " (" + moodMap.getMapping(moodKey) + ")";
        String anxietyLevel = "Anxiety: " + entry.getAnxietyLevel();

        String notes = "";
        if (!entry.getNotes().equals("")) {
            notes = "Notes: " + entry.getNotes();
        }

        holder.dateTextView.setText(date);
        holder.moodTextView.setText(mood);
        holder.anxietyTextView.setText(anxietyLevel);
        holder.notesTextView.setText(notes);
    }

    @Override
    public int getItemCount() {
        return databaseEntryList.size();
    }

}
