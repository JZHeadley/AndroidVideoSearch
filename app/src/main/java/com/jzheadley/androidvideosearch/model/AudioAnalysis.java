package com.jzheadley.androidvideosearch.model;

import android.util.Log;

import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechResults;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechTimestamp;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.Transcript;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Created by pjhud on 3/26/2017.
 */

public class AudioAnalysis {
    private static final String TAG = "AudioAnalysis";

    private final ConcurrentSkipListMap<String, ArrayList<Double>> wordTimeMap = new ConcurrentSkipListMap<>();

    private String transcript = "";


    public ConcurrentSkipListMap<String, ArrayList<Double>> getWordTimeMap() {
        return wordTimeMap;
    }

    public String getTranscript() {
        return transcript;
    }

    public void appendTranscript(String newTranscript) {
        transcript = transcript + newTranscript;
    }

    public List<Double> timesForPhrase(String phrase) {
        return null;
    }




}
