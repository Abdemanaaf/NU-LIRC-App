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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NetworkActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String userId;

    private TextView mUsername;
    private TextView mEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);
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

        TextView delnet = findViewById(R.id.delnet);
        TextView ndl = findViewById(R.id.ndl);
        TextView indcat = findViewById(R.id.indcat);
        TextView inflibnet = findViewById(R.id.inflibnet);
        TextView infoport = findViewById(R.id.infoport);
        TextView icssr = findViewById(R.id.icssr);
        TextView ess = findViewById(R.id.ess);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser() == null) {
                    Intent signUpIntent = new Intent(NetworkActivity.this, LoginPage.class);
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

        delnet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://164.100.247.30/";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        ndl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://ndl.iitkgp.ac.in/index.php";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        indcat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://indcat.inflibnet.ac.in/";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        inflibnet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.inflibnet.ac.in/publication/newsletter.php";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        infoport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://infoport.inflibnet.ac.in/";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        icssr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://www.icssrdataservice.in/index.php";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        ess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.inflibnet.ac.in/ess/";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
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
            startActivity(new Intent(NetworkActivity.this, QuickLinks.class));
            finish();
        }
        if (id == R.id.navE_Resources) {
            startActivity(new Intent(NetworkActivity.this, EResourcesActivity.class));
            finish();
        }
        if (id == R.id.navServices) {
            startActivity(new Intent(NetworkActivity.this, ServicesActivity.class));
            finish();
        }
        if (id == R.id.navOnlineLearning) {
            startActivity(new Intent(NetworkActivity.this, OnlineLearning.class));
            finish();
        }
        if (id == R.id.navOpenAccess) {
            startActivity(new Intent(NetworkActivity.this, OpenAccess.class));
            finish();
        }
        if (id == R.id.navNetwork) {
            startActivity(new Intent(NetworkActivity.this, NetworkActivity.class));
            finish();
        }
        if (id == R.id.navAbout) {
            startActivity(new Intent(NetworkActivity.this, AboutActivity.class));
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
