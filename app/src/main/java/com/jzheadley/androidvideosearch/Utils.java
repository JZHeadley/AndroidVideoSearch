package com.jzheadley.androidvideosearch;

import android.content.Context;
import android.util.Log;

import org.apache.tools.ant.taskdefs.optional.j2ee.WebLogicHotDeploymentTool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.EncoderProgressListener;
import it.sauronsoftware.jave.EncodingAttributes;
import it.sauronsoftware.jave.FFMPEGLocator;
import it.sauronsoftware.jave.MultimediaInfo;


public class Utils {
    private static final String TAG = "Utils";

    public static File extractAudio(Context context, File file) throws EncoderException {
        runStuff3();
        runStuff2();
        runStuff();

        File target = new File(context.getFilesDir(), "extractedAudio.flac");
        //target.getParentFile().mkdirs();
        try {
            //target.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(target);
            outputStream.close();
        } catch (Exception e) {
            Log.e(TAG, "extractAudio: " + e.getMessage(), e);
        }


        EncoderProgressListener listen = new EncoderProgressListener() {
            @Override
            public void sourceInfo(MultimediaInfo multimediaInfo) {
                Log.d(TAG, "LISTENER sourceInfo: " + multimediaInfo.toString());
                //multimediaInfo.getAudio().getDecoder();
            }

            @Override
            public void progress(int i) {
                Log.d(TAG, "LISTENER progress: " + i);
            }

            @Override
            public void message(String s) {
                Log.d(TAG, "LISTENER message: " + s);
            }
        };


        AudioAttributes audioAttributes = new AudioAttributes();
        audioAttributes.setCodec("libmp3lame");
        //audioAttributes.setCodec("flac");
        //audioAttributes.setCodec(AudioAttributes.DIRECT_STREAM_COPY);
        //audioAttributes.setBitRate(128000);
        //audioAttributes.setChannels(2);
        //audioAttributes.setSamplingRate(44100);
        EncodingAttributes encodingAttributes = new EncodingAttributes();
        encodingAttributes.setFormat("mp3");
        encodingAttributes.setAudioAttributes(audioAttributes);

        if (!target.isFile()) {
            Log.e(TAG, "testButton: ", new Exception());
        }


        FFMPEGLocator locator = new FFMPEGLocator() {
            @Override
            protected String getFFMPEGExecutablePath() {
                return MainActivity.ffmpegBin.getPath();
            }
        };

        Log.d(TAG, "extractAudio: LOCATOR: " + MainActivity.ffmpegBin.getPath());


        Encoder encoder = new Encoder(locator);
        Log.d(TAG, "extractAudio: FileSize: " + file.length());
        encoder.encode(file, target, encodingAttributes, listen);
        return target;
    }

    public static String runStuff() {
        String output = "";
        Log.d(TAG, "runStuff: CALLED");
        try {
            String[] command = new String[] {"/system/bin/ls", "-al",
                    MainActivity.ffmpegBin.getParentFile().getPath() //"/data/local"
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
        } catch (Exception e) {
            Log.e(TAG, "runCmd: ", e);
        }
        return output;
    }


    public static String runStuff2() {
        String output = "";
        Log.d(TAG, "runStuff2: CALLED");
        try {
            String[] command = new String[] {"/system/bin/chmod", "+x",
                    MainActivity.ffmpegBin.getPath() //"/data/local"
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
        } catch (Exception e) {
            Log.e(TAG, "runCmd: ", e);
        }
        return output;
    }

    public static String runStuff3() {
        String output = "";
        Log.d(TAG, "runStuff2: CALLED");
        try {
            String[] command = new String[] {"/system/bin/touch",
                    MainActivity.ffmpegBin.getParentFile().getPath() + "extractedAudio.flac"
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
        } catch (Exception e) {
            Log.e(TAG, "runCmd: ", e);
        }
        return output;
    }
}
