package com.jzheadley.androidvideosearch;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.jzheadley.androidvideosearch.model.AudioAnalysis;
import com.jzheadley.androidvideosearch.services.TranscribeAudioService;

import java.io.File;
import java.io.InputStream;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private static final int ACTION_TAKE_VIDEO = 3;
    private static final String TAG = "MainActivity";
    private static final int PICK_VIDEO_REQUEST = 2;

    private Uri videoUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[] {android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    @OnClick(R.id.record_btn)
    public void recordVideo() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        videoUri = getContentResolver()
                .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new ContentValues(1));
        takeVideoIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION
                | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri);
        startActivityForResult(takeVideoIntent, ACTION_TAKE_VIDEO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ACTION_TAKE_VIDEO:
                Log.d(TAG, "onActivityResult: " + resultCode);
                if (resultCode == RESULT_OK) {
                    Intent intent = new Intent(this, SearchActivity.class);
                    intent.putExtra("videoUri", data.getData());
                    startActivity(intent);
                }
                break;
            case PICK_VIDEO_REQUEST:
                Log.d(TAG, "onActivityResult: video chosen" + resultCode);
                if (resultCode == RESULT_OK) {
                    Log.d(TAG, "onActivityResult: " + data.getData());

                    Intent intent = new Intent(this, SearchActivity.class);
                    intent.putExtra("videoUri", data.getData());
                    startActivity(intent);
                }
        }
    }

    @OnClick(R.id.PaulsButton)
    public void testButton() {
        TranscribeAudioService transService = new TranscribeAudioService();

        int id = getResources().getIdentifier("test", "raw", getPackageName());
        InputStream ins = getResources().openRawResource(id);
        File videoFile = transService.insToFile(ins, getApplicationContext().getFilesDir());


        //transService.transcribeInputStream(ins, new AudioAnalysis());
        transService.addTranscriptionForAudio(getApplicationContext(), Uri.fromFile(videoFile), new AudioAnalysis());
    }

    @OnClick(R.id.galleryButton)
    public void pickFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Video"), PICK_VIDEO_REQUEST);
    }
}
