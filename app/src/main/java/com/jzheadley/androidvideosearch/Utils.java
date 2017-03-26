package com.jzheadley.androidvideosearch;

import android.content.Context;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.File;

import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.EncodingAttributes;
import it.sauronsoftware.jave.FFMPEGLocator;


public class Utils {
    private static final String TAG = "Utils";

    public static File extractAudio(Context context, File file) throws EncoderException {
        File target = new File(context.getFilesDir(), "extractedAudio.flac");
        target.mkdirs();
        AudioAttributes audioAttributes = new AudioAttributes();
        //audioAttributes.setCodec("libmp3lame");
        audioAttributes.setCodec("flac");
        audioAttributes.setBitRate(128000);
        audioAttributes.setChannels(2);
        audioAttributes.setSamplingRate(44100);
        EncodingAttributes encodingAttributes = new EncodingAttributes();
        encodingAttributes.setFormat("flac");
        encodingAttributes.setAudioAttributes(audioAttributes);

        if (!target.isFile()) {
            Log.e(TAG, "testButton: ", new Exception());
        }

        int id = context.getResources().getIdentifier("ffm", "raw", context.getPackageName());
        //InputStream ins = getResources().openRawResource(id);
        //context.getResources().res

        FFMPEGLocator locator = new FFMPEGLocator() {
            @Override
            protected String getFFMPEGExecutablePath() {

            }
        }

        Encoder encoder = new Encoder();
        encoder.encode(file, target, encodingAttributes);
        return target;
    }

}
