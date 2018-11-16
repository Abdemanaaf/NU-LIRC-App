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

public class EResourcesActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String userId;

    private TextView mUsername;
    private TextView mEmail;

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

        View header = navigationView.getHeaderView(0);
        mUsername = header.findViewById(R.id.nav_username);
        mEmail = header.findViewById(R.id.nav_email);

        TextView accessEng = findViewById(R.id.accessEng);
        TextView acm = findViewById(R.id.acm);
        TextView capitaline = findViewById(R.id.capitaline);
        TextView prowess = findViewById(R.id.prowess);
        TextView icra = findViewById(R.id.icra);
        TextView ieee = findViewById(R.id.ieee);
        TextView jstor = findViewById(R.id.jstor);
        TextView jccc = findViewById(R.id.jccc);
        TextView mathSciNet = findViewById(R.id.mathSciNet);
        TextView scienceDirect = findViewById(R.id.scienceDirect);

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

        accessEng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.accessengineeringlibrary.com/";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        acm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://dl.acm.org/";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        capitaline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.capitaline.com/";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        prowess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://prowessiq.cmie.com/";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        icra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.icra.in/?ReportCategory=";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        ieee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://ieeexplore.ieee.org/Xplore/home.jsp";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        jstor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.jstor.org/";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        jccc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://jgateplus.com/search/";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        mathSciNet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://mathscinet.ams.org/mathscinet/";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        scienceDirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.sciencedirect.com/browse/journals-and-books";
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
            startActivity(new Intent(EResourcesActivity.this, QuickLinks.class));
            finish();
        }
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
