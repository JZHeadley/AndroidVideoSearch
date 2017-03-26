package com.jzheadley.androidvideosearch.services;

import android.util.Log;

import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechResults;
import com.ibm.watson.developer_cloud.speech_to_text.v1.websocket.RecognizeCallback;

public class STTCallback implements RecognizeCallback {

    private static final String TAG = "STTCallback";

    /**
     * Speech has been received.
     */
    @Override
    public void onTranscription(SpeechResults speechResults) {
        Log.d(TAG, "onTranscription:" + speechResults.toString());
    }

    @Override
    public void onConnected() {
        Log.d(TAG, "Connected to watson");
    }

    @Override
    public void onError(Exception exception) {
        Log.d(TAG, "onError() called with: exception = [" + exception + "]");
        Log.e(TAG, "onError: ", exception);
    }

    @Override
    public void onDisconnected() {
        Log.d(TAG, "onDisconnected: Disconnected from watson");
    }

    @Override
    public void onInactivityTimeout(RuntimeException runtimeException) {

    }

    @Override
    public void onListening() {

    }
}
