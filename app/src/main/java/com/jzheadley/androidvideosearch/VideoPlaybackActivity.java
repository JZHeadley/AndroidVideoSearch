package com.jzheadley.androidvideosearch;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.VideoView;

import com.jzheadley.androidvideosearch.model.AudioAnalysis;
import com.jzheadley.androidvideosearch.services.TranscribeAudioService;

import butterknife.BindView;
import butterknife.ButterKnife;


public class VideoPlaybackActivity extends AppCompatActivity {


    @BindView(R.id.videoView)
    VideoView videoView;

    // HttpMediaType.AUDIO
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_playback);
        ButterKnife.bind(this);
        Uri videoURI = getIntent().getParcelableExtra("videoUri");
        Double videoTime = Double.parseDouble(getIntent().getStringExtra("videoTime"));
        videoView.setVideoURI(videoURI);
        videoView.start();
        videoView.seekTo(videoTime.intValue());
        TranscribeAudioService transService = new TranscribeAudioService();
        AudioAnalysis analysis = new AudioAnalysis();
        transService.addTranscriptionForAudio(getApplicationContext(), videoURI, analysis);

    }
}