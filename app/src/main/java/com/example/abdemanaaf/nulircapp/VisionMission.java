package com.example.abdemanaaf.nulircapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.InputStream;

public class VisionMission extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vision_mission);

        TextView visionMissionText = findViewById(R.id.visionMissionText);
        String text = "";

        try {

            InputStream inputStream = getAssets().open("vision_Mission_data");
            int size = inputStream.available();
            byte[] buffer = new byte[size];

            inputStream.read(buffer);
            inputStream.close();

            text = new String (buffer);

        } catch (Exception e) {

        }

        visionMissionText.setText(text);
    }
}
