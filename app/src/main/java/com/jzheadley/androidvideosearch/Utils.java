package com.jzheadley.androidvideosearch;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStreamReader;

import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.EncodingAttributes;
import it.sauronsoftware.jave.FFMPEGLocator;


public class Utils {
    private static final String TAG = "Utils";

    public static File extractAudio(Context context, File file) throws EncoderException {
        File target = new File(context.getFilesDir(), "extractedAudio.flac");
        //target.mkdirs();
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


        FFMPEGLocator locator = new FFMPEGLocator() {
            @Override
            protected String getFFMPEGExecutablePath() {
                return MainActivity.ffmpegBin.getPath().toString();
            }
        };

        Log.d(TAG, "extractAudio: LOCATOR: " + MainActivity.ffmpegBin.getPath().toString());
        runStuff2();
        runStuff();

        Encoder encoder = new Encoder(locator);
        encoder.encode(file, target, encodingAttributes);
        return target;
    }

    public static String runStuff() {
        String output = "";
        Log.d(TAG, "runStuff: CALLED");
        try {
            String[] command = new String[]{"/system/bin/ls", "-l",
                    //"/data/data/com.example.foo/files/ffmpeg"
                            MainActivity.ffmpegBin.getPath().toString() //"/data/local"
                    };
            /*String[] command = new String[]{"/system/bin/echo",
                    "hello!"
            };*/
            /*String[] command = new String[]{"/system/bin/chmod", "+x",
                            "/data/local/ffm"
                    };*/
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            int read;

            //String output = "";

            String line;
            while ((line = reader.readLine()) != null) {
                output.concat(line + "\n");
                Log.d("myApp", "[[output]]:" + line);
            }
            reader.close();
            process.waitFor();
        }catch (Exception e ) {
            Log.e(TAG, "runCmd: ", e);
        }
        return output;
    }

    public static String runStuff2() {
        String output = "";
        Log.d(TAG, "runStuff2: CALLED");
        try {
            //String[] command = new String[]{"/system/bin/touch", "/data/local/touchTest"};
            String[] command = new String[]{"/system/bin/chmod", "+x",
                    //"/data/data/com.example.foo/files/ffmpeg"
                    MainActivity.ffmpegBin.getPath().toString() //"/data/local"
            };

            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            int read;

            //String output = "";

            String line;
            while ((line = reader.readLine()) != null) {
                output.concat(line + "\n");
                Log.d("myApp", "[[output]]:" + line);
            }
            reader.close();
            process.waitFor();
        }catch (Exception e ) {
            Log.e(TAG, "runCmd: ", e);
        }
        return output;
    }
}
