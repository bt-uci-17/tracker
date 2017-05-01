package com.example.android.bipolartracker;

import java.util.HashMap;
import java.util.Map;

// A quick and dirty little Likert scale of a class
public class MoodMap {
    public String getMapping(String key) {
        Map<String, String> moodMap = new HashMap<String, String>();

        moodMap.put("1", "Very Depressed");
        moodMap.put("2", "Depressed");
        moodMap.put("3", "Moderately Depressed");
        moodMap.put("4", "Mildly Depressed");
        moodMap.put("5", "Neutral");
        moodMap.put("6", "Mildly Manic");
        moodMap.put("7", "Moderately Manic");
        moodMap.put("8", "Manic");
        moodMap.put("9", "Very Manic");

        return moodMap.get(key);
    }
}
