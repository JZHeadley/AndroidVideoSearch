package com.jzheadley.androidvideosearch;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.jzheadley.androidvideosearch.model.AudioAnalysis;
import com.jzheadley.androidvideosearch.services.TranscribeAudioService;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity {
    private static final String TAG = "SearchActivity";
    @BindView(R.id.search_et)
    EditText searchEditText;
    private Uri videoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        videoUri = (Uri) getIntent().getExtras().get("videoUri");
        ButterKnife.bind(this);

        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    Log.d(TAG, "onEditorAction: " + searchEditText.getText().toString());
                    AudioAnalysis analysis = new AudioAnalysis();
                    TranscribeAudioService transcribeService = new TranscribeAudioService();
                    transcribeService.addTranscriptionForAudio(videoUri, analysis);
                    analysis.timesForPhrase(searchEditText.getText().toString());
                    return true;
                }
                return false;
            }
        });
    }


}
