package com.jzheadley.androidvideosearch.model;

import android.util.Log;

import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechResults;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechTimestamp;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.Transcript;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


public class AudioAnalysis {
    private static final String TAG = "AudioAnalysis";

    private ConcurrentHashMap<String, ArrayList<Double>> wordTimeMap = new ConcurrentHashMap<>();


    public List<Double> timesForPhrase(String phrase) {
        return null;
    }

    public void interpretResults(SpeechResults speechResults) {
        Log.d(TAG, "interpretResults() called with: speechResults = [" + speechResults + "]");
        String text;

        try {
            Transcript transcript = speechResults.getResults().get(0);
            text = transcript.getAlternatives().get(0).getTranscript();
            Log.d(TAG, "interpretResults: text: " + text);

            List<SpeechTimestamp> tStamps = transcript.getAlternatives().get(0).getTimestamps();
            for (SpeechTimestamp ts : tStamps) {
                ArrayList<Double> times = wordTimeMap.get(ts.getWord());
                if (times == null) {
                    times = new ArrayList<>();
                    wordTimeMap.put(ts.getWord(), times);
                }
                times.add(ts.getStartTime());
            }


            for (String word : wordTimeMap.keySet()) {
                Log.d(TAG, "interpretResults: Word/time: " + word + "/" + wordTimeMap.get(word).toString());
            }


        } catch (Exception ex) {
            Log.e(TAG, "interpretResults: caught", ex);
        }
        Log.d(TAG, "interpretResults: " + speechResults);
    }

}
