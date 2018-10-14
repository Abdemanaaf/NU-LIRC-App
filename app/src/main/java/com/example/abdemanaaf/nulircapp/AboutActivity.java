package com.example.abdemanaaf.nulircapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseAuth;

import java.io.InputStream;

public class AboutActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView introText;
    private TextView visionMissionText;
    private TextView userFacilitiesText;
    private TextView nuAtGlanceText;
    private TextView advisoryText;
    private TextView collectionText;
    private TextView generalRulesText;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser() == null) {
                    Intent signUpIntent = new Intent(AboutActivity.this, LoginPage.class);
                    signUpIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(signUpIntent);
                }

            }
        };

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

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.quick_links, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings)
            startActivity(new Intent(AboutActivity.this, SettingsActivity.class));

        if (id == R.id.action_logout)
            mAuth.signOut();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.navE_Resources) {
            startActivity(new Intent(AboutActivity.this, EResourcesActivity.class));
            finish();
        }
        if (id == R.id.navServices) {
            startActivity(new Intent(AboutActivity.this, ServicesActivity.class));
            finish();
        }
        if (id == R.id.navOnlineLearning) {
            startActivity(new Intent(AboutActivity.this, OnlineLearning.class));
            finish();
        }
        if (id == R.id.navOpenAccess) {
            startActivity(new Intent(AboutActivity.this, OpenAccess.class));
            finish();
        }
        if (id == R.id.navNetwork) {
            startActivity(new Intent(AboutActivity.this, NetworkActivity.class));
            finish();
        }
        if (id == R.id.navAbout) {
            startActivity(new Intent(AboutActivity.this, AboutActivity.class));
            finish();
        }
        if (id == R.id.bookReqForm) {
            String url =
                    "https://docs.google.com/forms/d/e/1FAIpQLSddAOawl7LkVOZ1eyJHTGDqvKVBxqNIMqMECQ2Gd8mY_k5pxA/viewform";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        }
        if (id == R.id.navFeedback) {
            String url =
                    "https://docs.google.com/forms/d/e/1FAIpQLSepsmj3L19Ts6rP6X0h_Try7jrCvRylv3d4kyON4xDr6V1bLg/viewform";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
