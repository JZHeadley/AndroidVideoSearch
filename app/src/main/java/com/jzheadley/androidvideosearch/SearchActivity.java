package com.jzheadley.androidvideosearch;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.jzheadley.androidvideosearch.model.AudioAnalysis;
import com.jzheadley.androidvideosearch.services.TranscribeAudioService;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity {
    private static final String TAG = "SearchActivity";
    @BindView(R.id.search_et)
    EditText searchEditText;
    @BindView(R.id.search_results_list)
    RecyclerView searchResultsList;
    private Uri videoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        videoUri = (Uri) getIntent().getExtras().get("videoUri");
        ButterKnife.bind(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        searchResultsList.setLayoutManager(linearLayoutManager);
        final AudioAnalysis analysis = new AudioAnalysis();
        final TranscribeAudioService transcribeService = new TranscribeAudioService();
        transcribeService.addTranscriptionForAudio(this, videoUri, analysis);

        final SearchAdapter searchAdapter = new SearchAdapter(analysis.timesForPhrase(""), videoUri);
        searchResultsList.setAdapter(searchAdapter);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d(TAG, "afterTextChanged: Updating list of results");
                searchAdapter.updateList(analysis.timesForPhrase(editable.toString()));

            }
        });

    }


}
