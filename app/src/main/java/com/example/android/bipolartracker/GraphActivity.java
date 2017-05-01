package com.example.android.bipolartracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class GraphActivity extends AppCompatActivity {

    //private List<DatabaseEntry> databaseEntryList = new ArrayList<>();
    private EntryAdapter entryAdapter;

    private EntriesDataSource dataSource;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        drawDisplay();
    }

    private void drawDisplay() {
        recyclerView = (RecyclerView) findViewById(R.id.entriesRecyclerView);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dataSource = new EntriesDataSource(this);
        dataSource.open();

        List<DatabaseEntry> databaseEntries = dataSource.getAllDatabaseEntries();

        GraphView graph = (GraphView) findViewById(R.id.graph);
        graph.removeAllSeries();
        LineGraphSeries<DataPoint> moodSeries = new LineGraphSeries<>();
        LineGraphSeries<DataPoint> anxietySeries = new LineGraphSeries<>();

        for (int i = 0; i < databaseEntries.size(); i++) {
            DatabaseEntry databaseEntry = databaseEntries.get(i);

            long timestamp = databaseEntry.getTimestamp();
            int mood = databaseEntry.getMood();
            int anxietyLevel = databaseEntry.getAnxietyLevel();

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(timestamp);
            Date date = calendar.getTime();

            DataPoint moodDataPoint = new DataPoint(date, mood);
            DataPoint anxietyDataPoint = new DataPoint(date, anxietyLevel);

            moodSeries.appendData(moodDataPoint,false,databaseEntries.size());
            anxietySeries.appendData(anxietyDataPoint,false,databaseEntries.size());
        }
        graph.addSeries(moodSeries);

        graph.addSeries(anxietySeries);
        anxietySeries.setColor(Color.RED);
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this));
        graph.getGridLabelRenderer().setNumHorizontalLabels(3); // only 4 because of the space

        graph.getViewport().setScalable(true);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(1);
        graph.getViewport().setMaxY(9);

        graph.setTitle("Mood and Anxiety Levels");

        moodSeries.setTitle("Mood (Depressed / Manic)");
        anxietySeries.setTitle("Anxiety Level");
        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.BOTTOM);

        entryAdapter = new EntryAdapter(databaseEntries);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(entryAdapter);

        // Cause the graph to refresh itself
        graph.invalidate();

        // Cause the recyclerview to refresh itself
        recyclerView.invalidate();
    }

    // http://stackoverflow.com/questions/15191550/android-create-a-simple-menu-programmatically
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, 1, Menu.NONE, "Clear Database");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1: // menu item 1 (clear database)
                showDatabaseAlertDialog();
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // http://stackoverflow.com/questions/2115758/how-do-i-display-an-alert-dialog-on-android
    public void showDatabaseAlertDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Delete database")
                .setMessage("Are you sure you want to delete and re-create the entire database?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        dataSource.resetDatabase();
                        drawDisplay();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
