package com.example.android.bipolartracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SeekBar moodSeekBar = null;
    private int moodSeekBarStatus;

    private SeekBar anxietySeekBar = null;
    private int anxietySeekBarStatus;

    private String notes = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        moodSeekBar = (SeekBar) findViewById(R.id.moodSeekBar);
        moodSeekBarStatus = moodSeekBar.getProgress();

        anxietySeekBar = (SeekBar) findViewById(R.id.anxietySeekBar);
        anxietySeekBarStatus = anxietySeekBar.getProgress();

        moodSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                moodSeekBarStatus = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                MoodMap moodMap = new MoodMap();
                Toast.makeText(MainActivity.this, moodMap.getMapping((moodSeekBarStatus + 1) + ""), Toast.LENGTH_SHORT).show();
            }
        });

        anxietySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                anxietySeekBarStatus = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    // http://stackoverflow.com/questions/15191550/android-create-a-simple-menu-programmatically
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, 1, Menu.NONE, "View Graph");
        menu.add(Menu.NONE, 2, Menu.NONE, "Clear Form");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                startActivity(new Intent(this, GraphActivity.class));
                return true;

            case 2:
                resetForm();
                return true;

            default:
                return false;
        }
    }

    public void addToDatabaseAndResetForm(View view) {
        EditText notesEditText = (EditText) findViewById(R.id.notesEditText);
        notes = notesEditText.getText().toString();

        EntriesDataSource entriesDataSource = new EntriesDataSource(this);
        entriesDataSource.open();

        entriesDataSource.createDatabaseEntry(moodSeekBarStatus + 1, anxietySeekBarStatus + 1, notes);

        entriesDataSource.close();

        Toast.makeText(MainActivity.this, "Added your entry to the database!", Toast.LENGTH_SHORT).show();

        resetForm();
    }

    private void resetForm() {
        moodSeekBarStatus = 4;
        moodSeekBar.setProgress(moodSeekBarStatus);

        anxietySeekBarStatus = 0;
        anxietySeekBar.setProgress(anxietySeekBarStatus);

        notes = "";
        EditText notesEditText = (EditText) findViewById(R.id.notesEditText);
        notesEditText.setText(notes);
    }
}
