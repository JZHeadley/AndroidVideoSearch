package com.jzheadley.androidvideosearch;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.VideoView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private static final int ACTION_TAKE_VIDEO = 3;
    private static final String TAG = "MainActivity";
    private static final int PICK_VIDEO_REQUEST = 2;
    @BindView(R.id.videoView)
    VideoView videoView;
    private Uri videoUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.record_btn)
    public void recordVideo() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        File mediaFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/VideoSearch.mp4");
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

                    handleCameraVideo(data);
                }
                break;
        }
    }

    private void handleCameraVideo(Intent intent) {
        Log.d(TAG, "handleCameraVideo: Got Result");
        videoView.setVideoURI(intent.getData());
        Log.d(TAG, "handleCameraVideo: " + intent.getData());
        videoView.setVisibility(View.VISIBLE);
        videoView.start();
    }

    @OnClick(R.id.galleryButton)
    public void pickFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Video"), PICK_VIDEO_REQUEST);
    }

    @OnClick(R.id.videoView)
    public void startPlayback() {
        if (videoView.isPlaying()) {
            Log.i(TAG, "startPlayback: Pausing video");
            videoView.pause();
        } else {
            Log.i(TAG, "startPlayback: Playing video");
            videoView.start();
        }
    }


}
