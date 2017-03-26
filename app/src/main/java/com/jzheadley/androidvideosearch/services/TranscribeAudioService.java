package com.jzheadley.androidvideosearch.services;


import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.ibm.watson.developer_cloud.http.HttpMediaType;
import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.RecognizeOptions;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechResults;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechTimestamp;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.Transcript;
import com.ibm.watson.developer_cloud.speech_to_text.v1.websocket.RecognizeCallback;
import com.jzheadley.androidvideosearch.R;
import com.jzheadley.androidvideosearch.model.AudioAnalysis;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListMap;

public class TranscribeAudioService {
    private static final String TAG = "TranscribeAudioService";


    /** Example usage:
     *  AudioAnalysis analysis = new AudioAnalysis();
     *  transcribeService.addTranscriptionForAudio(getContentResolver().openInputStream(videoURI), analysis);
     *  analysis.timesForPhrase(phraseString);
     */
    public void addTranscriptionForAudio(InputStream ins, AudioAnalysis analysis) {

        Log.d(TAG, "testTranscribe: CALLED");

        TranscribeThread transThread = new TranscribeThread(ins, analysis);
        transThread.run();

    }

    private File insToFile(InputStream ins, File dir) {
        OutputStream outputStream = null;

        File f = null;

        try {
            // read this file into InputStream
            //inputStream = new FileInputStream("/Users/mkyong/Downloads/holder.js");

            // write the inputStream to a FileOutputStream
            f = new File(dir, "testAudioFile");

            outputStream =
                    new FileOutputStream(f);

            int read;
            byte[] bytes = new byte[1024];

            while ((read = ins.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }

            System.out.println("Done!");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ins != null) {
                try {
                    ins.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    // outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
        return f;
    }

    private class TranscribeThread extends Thread {
        InputStream ins;
        AudioAnalysis audioAnal;

        public TranscribeThread(InputStream inputStream, AudioAnalysis analysis) {
            ins = inputStream;
            audioAnal = analysis;
        }

        @Override
        public void run() {
            Log.d(TAG, "run: RUNNING TRANS THREAD");
            SpeechToText service = new SpeechToText();
            //service.setUsernameAndPassword("f47ff4a4-6cab-4996-9902-f9fa987f0ba3", "eR3ZouirbNdT");
            service.setUsernameAndPassword("fe439343-c64a-4f1b-b03c-2b004f5a7620", "qhMuQEuZuv1N");

            RecognizeOptions.Builder recOpsBld = new RecognizeOptions.Builder();
            recOpsBld.contentType(HttpMediaType.AUDIO_WAV);
            recOpsBld.timestamps(true);


            //SpeechResults transcript = service.recognize(aFile, recOpsBld.build()).execute();
            service.recognizeUsingWebSocket(ins, recOpsBld.build(), new RecognizeCallback() {
                @Override
                public void onTranscription(SpeechResults speechResults) {
                    Log.d(TAG, "onTranscription: " + speechResults.toString());
                    interpretResults(speechResults);
                }

                @Override
                public void onConnected() {

                }

                @Override
                public void onError(Exception e) {

                }

                @Override
                public void onDisconnected() {

                }

                @Override
                public void onInactivityTimeout(RuntimeException runtimeException) {

                }

                @Override
                public void onListening() {

                }
            });
            //Log.d(TAG, "testTranscribe: " + transcript.toString());
        }

        private void interpretResults(SpeechResults speechResults) {
            Log.d(TAG, "interpretResults() called with: speechResults = [" + speechResults + "]");
            String text = "";
            ConcurrentSkipListMap<String, ArrayList<Double>> wordTimeMap = audioAnal.getWordTimeMap();

            try {
                Transcript transcript = speechResults.getResults().get(0);
                text = transcript.getAlternatives().get(0).getTranscript();
                audioAnal.appendTranscript(text);
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
        }


        public void end() throws IOException {
            //aFile.close();
        }
    }
}
