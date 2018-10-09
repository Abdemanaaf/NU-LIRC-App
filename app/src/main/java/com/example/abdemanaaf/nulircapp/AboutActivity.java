package com.example.abdemanaaf.nulircapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.InputStream;

public class AboutActivity extends AppCompatActivity {

    private TextView introText;
    private TextView visionMissionText;
    private TextView userFacilitiesText;
    private TextView nuAtGlanceText;
    private TextView advisoryText;
    private TextView collectionText;
    private TextView generalRulesText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        introText = findViewById(R.id.introText);
        visionMissionText = findViewById(R.id.visionMissionText);
        userFacilitiesText = findViewById(R.id.userFacilitiesText);
        nuAtGlanceText = findViewById(R.id.nuAtGlanceText);
        advisoryText = findViewById(R.id.advisoryText);
        collectionText = findViewById(R.id.collectionText);
        generalRulesText = findViewById(R.id.generalRulesText);

        final ToggleButton intro = findViewById(R.id.intro);
        final ToggleButton visionMission = findViewById(R.id.visionMission);
        final ToggleButton userFacilities = findViewById(R.id.userFacilities);
        final ToggleButton nuAtGlance = findViewById(R.id.nuAtGlance);
        final ToggleButton advisory = findViewById(R.id.advisory);
        final ToggleButton collection = findViewById(R.id.collection);
        final ToggleButton generalRules = findViewById(R.id.generalRules);

        intro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = intro.isChecked();
                if (isChecked)
                    getData("introduction_data", introText);
                else
                    introText.setText("");
            }
        });

        visionMission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = visionMission.isChecked();
                if (isChecked)
                    getData("vision_mission_data", visionMissionText);
                else
                    visionMissionText.setText("");
            }
        });

        userFacilities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = userFacilities.isChecked();
                if (isChecked)
                    getData("user_facilities_data", userFacilitiesText);
                else
                    userFacilitiesText.setText("");
            }
        });

        nuAtGlance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = nuAtGlance.isChecked();
                if (isChecked)
                    getData("nu_at_glance_data", nuAtGlanceText);
                else
                    nuAtGlanceText.setText("");
            }
        });

        advisory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = advisory.isChecked();
                if (isChecked)
                    getData("advisory_data", advisoryText);
                else
                    advisoryText.setText("");
            }
        });

        collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = collection.isChecked();
                if (isChecked)
                    getData("collection_data", collectionText);
                else
                    collectionText.setText("");
            }
        });

        generalRules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = generalRules.isChecked();
                if (isChecked)
                    getData("general_rules_data", generalRulesText);
                else
                    generalRulesText.setText("");
            }
        });
    }

    private void getData(String filename, TextView textView) {

        String text = "";
        try {

            InputStream inputStream = getAssets().open(filename);
            int size = inputStream.available();
            byte[] buffer = new byte[size];

            inputStream.read(buffer);
            inputStream.close();

            text = new String (buffer);

        } catch (Exception e) {
            Toast.makeText(this, "Error Extracting Data", Toast.LENGTH_SHORT).show();
        }

        textView.setText(text);
    }


}
