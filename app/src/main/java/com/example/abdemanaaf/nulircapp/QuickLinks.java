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
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class QuickLinks extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String userId;

    private TextView mUsername;
    private TextView mEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_links);
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

        setLIRCTimings();

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser() == null) {
                    Intent signUpIntent = new Intent(QuickLinks.this, LoginPage.class);
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

        TextView issueReturn = findViewById(R.id.issueReturn);
        issueReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(QuickLinks.this, IssueReturn.class));
            }
        });

        TextView contactLib = findViewById(R.id.contactLib);
        contactLib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(QuickLinks.this, ContactLibrarian.class));
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

    private void setLIRCTimings() {

        TextView monToFri = findViewById(R.id.monFriLibTime);
        monToFri.setText(R.string.mon_fri_lib_time);

        TextView sat = findViewById(R.id.satLibTime);
        sat.setText(R.string.sat_lib_time);

        TextView sun = findViewById(R.id.sunLibTime);
        sun.setText(R.string.sun_lib_time);

        TextView sat_ex = findViewById(R.id.satExTime);
        sat_ex.setText(R.string.sat_ex_time);

        TextView sun_ex = findViewById(R.id.sunExTime);
        sun_ex.setText(R.string.sun_ex_time);

        TextView sat_circ = findViewById(R.id.monToSatCircTime);
        sat_circ.setText(R.string.mon_sat_circ_time);

        TextView sun_circ = findViewById(R.id.sunCircTime);
        sun_circ.setText(R.string.sun_circ_time);

        TextView closed = findViewById(R.id.closed);
        closed.setText(R.string.closed);

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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.home) {
            startActivity(new Intent(QuickLinks.this, QuickLinks.class));
            finish();
        }
        if (id == R.id.navE_Resources) {
            startActivity(new Intent(QuickLinks.this, EResourcesActivity.class));
        }
        if (id == R.id.navServices) {
            startActivity(new Intent(QuickLinks.this, ServicesActivity.class));
        }
        if (id == R.id.navOnlineLearning) {
            startActivity(new Intent(QuickLinks.this, OnlineLearning.class));
        }
        if (id == R.id.navOpenAccess) {
            startActivity(new Intent(QuickLinks.this, OpenAccess.class));
        }
        if (id == R.id.navNetwork) {
            startActivity(new Intent(QuickLinks.this, NetworkActivity.class));
        }
        if (id == R.id.navAbout) {
            startActivity(new Intent(QuickLinks.this, AboutActivity.class));
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
