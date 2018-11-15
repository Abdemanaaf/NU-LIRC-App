package com.example.abdemanaaf.nulircapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AboutActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private WebView introText;
    private WebView visionMissionText;
    private WebView userFacilitiesText;
    private WebView nuAtGlanceText;
    private WebView advisoryText;
    private WebView collectionText;
    private WebView generalRulesText;

    private ToggleButton intro;
    private ToggleButton visionMission;
    private ToggleButton userFacilities;
    private ToggleButton nuAtGlance;
    private ToggleButton advisory;
    private ToggleButton collection;
    private ToggleButton generalRules;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String userId;

    private TextView mUsername;
    private TextView mEmail;

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

        View header = navigationView.getHeaderView(0);
        mUsername = header.findViewById(R.id.nav_username);
        mEmail = header.findViewById(R.id.nav_email);

        introText = findViewById(R.id.introText);
        visionMissionText = findViewById(R.id.visionMissionText);
        userFacilitiesText = findViewById(R.id.userFacilitiesText);
        nuAtGlanceText = findViewById(R.id.nuAtGlanceText);
        advisoryText = findViewById(R.id.advisoryText);
        collectionText = findViewById(R.id.collectionText);
        generalRulesText = findViewById(R.id.generalRulesText);

        intro = findViewById(R.id.intro);
        visionMission = findViewById(R.id.visionMission);
        userFacilities = findViewById(R.id.userFacilities);
        nuAtGlance = findViewById(R.id.nuAtGlance);
        advisory = findViewById(R.id.advisory);
        collection = findViewById(R.id.collection);
        generalRules = findViewById(R.id.generalRules);

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

        DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = mAuth.getCurrentUser();

        userId = user.getUid();

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        intro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = intro.isChecked();
                if (isChecked)
                    introText.loadUrl("file:///android_asset/introduction_data.html");
                else
                    introText.loadUrl("");
            }
        });

        visionMission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = visionMission.isChecked();
                if (isChecked)
                    visionMissionText.loadUrl("file:///android_asset/introduction_data.html");
                else
                    visionMissionText.loadUrl("");
            }
        });

        userFacilities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = userFacilities.isChecked();
                if (isChecked)
                    userFacilitiesText.loadUrl("file:///android_asset/introduction_data.html");
                else
                    userFacilitiesText.loadUrl("");
            }
        });

        nuAtGlance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = nuAtGlance.isChecked();
                if (isChecked)
                    nuAtGlanceText.loadUrl("file:///android_asset/introduction_data.html");
                else
                    nuAtGlanceText.loadUrl("");
            }
        });

        advisory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = advisory.isChecked();
                if (isChecked)
                    advisoryText.loadUrl("file:///android_asset/introduction_data.html");
                else
                    advisoryText.loadUrl("");
            }
        });

        collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = collection.isChecked();
                if (isChecked)
                    collectionText.loadUrl("file:///android_asset/introduction_data.html");
                else
                    collectionText.loadUrl("");
            }
        });

        generalRules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = generalRules.isChecked();
                if (isChecked)
                    generalRulesText.loadUrl("file:///android_asset/introduction_data.html");
                else
                    generalRulesText.loadUrl("");
            }
        });
    }

    private void showData(DataSnapshot dataSnapshot) {

        for (DataSnapshot ds : dataSnapshot.getChildren()) {

            UserInformation userInfo = new UserInformation();
            userInfo.setName(ds.child(userId).getValue(UserInformation.class).getName());
            userInfo.setEmail(ds.child(userId).getValue(UserInformation.class).getEmail());

            String name = userInfo.getName();
            String email = userInfo.getEmail();

            mUsername.setText(name);
            mEmail.setText(email);
        }
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

        if (id == R.id.action_logout)
            mAuth.signOut();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.home) {
            startActivity(new Intent(AboutActivity.this, QuickLinks.class));
            finish();
        }
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
