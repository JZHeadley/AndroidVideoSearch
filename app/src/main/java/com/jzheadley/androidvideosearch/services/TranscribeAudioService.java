package com.jzheadley.androidvideosearch.services;



import android.util.Log;

import com.ibm.watson.developer_cloud.http.HttpMediaType;
import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.RecognizeOptions;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechResults;
import com.jzheadley.androidvideosearch.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by pjhud on 3/25/2017.
 */

public class TranscribeAudioService {
    private static final String TAG = "TranscribeAudioService";

    public void testTranscribe(InputStream ins, File dir) {
        File audioFile = insToFile(ins, dir);

        SpeechToText service = new SpeechToText();
        service.setUsernameAndPassword("<username>", "<password>");

        RecognizeOptions.Builder recOpsBld = new RecognizeOptions.Builder();
        recOpsBld.contentType(HttpMediaType.AUDIO_WAV);


        SpeechResults transcript = service.recognize(audioFile, recOpsBld.build()).execute();
        Log.d(TAG, "testTranscribe: " + transcript.toString());
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

            int read = 0;
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
}
