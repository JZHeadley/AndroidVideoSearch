package com.jzheadley.androidvideosearch;

import android.content.Context;

import java.io.File;

import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.EncodingAttributes;


public class Utils {
    public static File extractAudio(Context context, File file) throws EncoderException {
        File target = new File(context.getFilesDir(), "extractedAudio.flac");
        AudioAttributes audioAttributes = new AudioAttributes();
        //audioAttributes.setCodec("libmp3lame");
        audioAttributes.setCodec("flac");
        audioAttributes.setBitRate(128000);
        audioAttributes.setChannels(2);
        audioAttributes.setSamplingRate(44100);
        EncodingAttributes encodingAttributes = new EncodingAttributes();
        encodingAttributes.setFormat("flac");
        encodingAttributes.setAudioAttributes(audioAttributes);

        Encoder encoder = new Encoder();
        encoder.encode(file, target, encodingAttributes);
        return target;
    }

}
