package com.example.android.bipolartracker;

// This class is a database entry object

public class DatabaseEntry {
    private long id;
    private long timestamp;
    private int mood;
    private int anxietyLevel;
    private String notes;

    // NOTHING TO SEE HERE FOLKS JUST A BUNCH OF GETTER AND SETTER METHODS ZZZZZZZZ

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getMood() {
        return mood;
    }

    public void setMood(int mood) {
        this.mood = mood;
    }

    public int getAnxietyLevel() {
        return anxietyLevel;
    }

    public void setAnxietyLevel(int anxietyLevel) {
        this.anxietyLevel = anxietyLevel;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

}
