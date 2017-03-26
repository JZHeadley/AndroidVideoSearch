package com.jzheadley.androidvideosearch;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.VideoView;

import com.jzheadley.androidvideosearch.services.TranscribeAudioService;

import java.io.FileNotFoundException;

import butterknife.BindView;
import butterknife.ButterKnife;


public class VideoPlaybackActivity extends AppCompatActivity {


    @BindView(R.id.videoView)
    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_playback);
        ButterKnife.bind(this);
        Uri videoURI = getIntent().getParcelableExtra("videoUri");
        MediaPlayer player = new MediaPlayer();
        videoView.setVideoURI(videoURI);
        videoView.start();
        TranscribeAudioService transService = new TranscribeAudioService();
        try {
            transService.testTranscribe(getContentResolver().openInputStream(videoURI), getApplicationContext().getFilesDir());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}