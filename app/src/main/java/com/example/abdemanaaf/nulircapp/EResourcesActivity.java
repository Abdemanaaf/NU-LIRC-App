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

public class EResourcesActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView refText;
    private TextView libLoanText;
    private TextView reproFacText;
    private TextView userOrienText;
    private TextView knowledgeText;
    private TextView sugamyaText;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eresources);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        refText = findViewById(R.id.refText);
        libLoanText = findViewById(R.id.libLoanText);
        reproFacText = findViewById(R.id.reproFacText);
        userOrienText = findViewById(R.id.userOrienText);
        knowledgeText = findViewById(R.id.knowledgeText);
        sugamyaText = findViewById(R.id.sugamyaText);

        final ToggleButton ref = findViewById(R.id.ref);
        final ToggleButton libLoan = findViewById(R.id.libLoan);
        final ToggleButton reproFac = findViewById(R.id.reproFac);
        final ToggleButton userOrien = findViewById(R.id.userOrien);
        final ToggleButton knowledge = findViewById(R.id.knowledge);
        final ToggleButton sugamya = findViewById(R.id.sugamya);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser() == null) {
                    Intent signUpIntent = new Intent(EResourcesActivity.this, LoginPage.class);
                    signUpIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(signUpIntent);
                }

            }
        };

        ref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = ref.isChecked();
                if (isChecked)
                    getData("reference_data", refText);
                else
                    refText.setText("");
            }
        });

        libLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = libLoan.isChecked();
                if (isChecked)
                    getData("inter_library_loan_data", libLoanText);
                else
                    libLoanText.setText("");
            }
        });

        reproFac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = reproFac.isChecked();
                if (isChecked)
                    getData("reprographic_facilities_data", reproFacText);
                else
                    reproFacText.setText("");
            }
        });

        userOrien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = userOrien.isChecked();
                if (isChecked)
                    getData("users_orientation_programmes_data", userOrienText);
                else
                    userOrienText.setText("");
            }
        });

        knowledge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = knowledge.isChecked();
                if (isChecked)
                    getData("knowledge_dissemination_cell_data", knowledgeText);
                else
                    knowledgeText.setText("");
            }
        });

        sugamya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = sugamya.isChecked();
                if (isChecked)
                    getData("sugamya_pustakalaya", sugamyaText);
                else
                    sugamyaText.setText("");
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

        if (id == R.id.action_logout)
            mAuth.signOut();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.navE_Resources) {
            startActivity(new Intent(EResourcesActivity.this, EResourcesActivity.class));
            finish();
        }
        if (id == R.id.navServices) {
            startActivity(new Intent(EResourcesActivity.this, ServicesActivity.class));
            finish();
        }
        if (id == R.id.navOnlineLearning) {
            startActivity(new Intent(EResourcesActivity.this, OnlineLearning.class));
            finish();
        }
        if (id == R.id.navOpenAccess) {
            startActivity(new Intent(EResourcesActivity.this, OpenAccess.class));
            finish();
        }
        if (id == R.id.navNetwork) {
            startActivity(new Intent(EResourcesActivity.this, NetworkActivity.class));
            finish();
        }
        if (id == R.id.navAbout) {
            startActivity(new Intent(EResourcesActivity.this, AboutActivity.class));
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
