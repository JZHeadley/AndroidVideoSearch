package com.jzheadley.androidvideosearch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Paul's Button
        Button PaulsButton = (Button) findViewById(R.id.PaulsButton);
        PaulsButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //insert code here
            }
        });

        //Record Button
        Button recordButton = (Button) findViewById(R.id.recordButton);
        recordButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //insert code here
            }
        });


        //Gallery Button
        Button galleryButton = (Button) findViewById(R.id.galleryButton);
        galleryButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //insert code here
            }
        });

    }
}
