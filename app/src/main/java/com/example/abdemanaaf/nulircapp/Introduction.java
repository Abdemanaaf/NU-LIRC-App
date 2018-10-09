package com.example.abdemanaaf.nulircapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.io.InputStream;

public class Introduction extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);

        TextView introText = findViewById(R.id.introText);
        String text = "";

        try {

            InputStream inputStream = getAssets().open("introduction_data");
            int size = inputStream.available();
            byte[] buffer = new byte[size];

            inputStream.read(buffer);
            inputStream.close();

            text = new String (buffer);

        } catch (Exception e) {

        }

        introText.setText(text);


    }
}
