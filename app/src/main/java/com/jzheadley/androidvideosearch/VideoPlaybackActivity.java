package com.jzheadley.androidvideosearch;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;


public class VideoPlaybackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_playback);

        Uri videoURI = getIntent().getParcelableExtra("videoUri");
        MediaPlayer player = new MediaPlayer();
        try {
            player.setDataSource(getApplicationContext(), videoURI);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}