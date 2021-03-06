package com.example.abdemanaaf.nulircapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class SignUpPage extends AppCompatActivity {

    private EditText mName;
    private EditText mUsername;
    private EditText mPassword;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl("https://nu-lirc-app.firebaseio.com/").child("Users");

        mName = findViewById(R.id.name_sign_up);
        mUsername = findViewById(R.id.username_sign_up);
        mPassword = findViewById(R.id.password_sign_up);

        mProgress = new ProgressDialog(this);

        Button createButton = findViewById(R.id.createButton);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRegister();
            }
        });

        TextView haveAccount = findViewById(R.id.haveAccount);
        haveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpPage.this, LoginPage.class));
                finish();
            }
        });
    }

    private void startRegister() {

        final String email = mUsername.getText().toString().trim();
        String password = mPassword.getText().toString().trim();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mUsername.setError("enter a valid email address");
        } else {
            mUsername.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            mPassword.setError("between 6 and 10 alphanumeric characters");
        } else {
            mPassword.setError(null);
        }

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {

            mProgress.setMessage("Signing Up...");
            mProgress.show();

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(SignUpPage.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {

                        mProgress.dismiss();
                        Toast.makeText(SignUpPage.this, "Sign Up Complete", Toast.LENGTH_SHORT).show();

                        Intent loginPageIntent = new Intent(SignUpPage.this, LoginPage.class);
                        loginPageIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        saveToDatabase();

                        startActivity(loginPageIntent);
                        finish();

                    } else {
                        mProgress.dismiss();
                        Toast.makeText(SignUpPage.this, "Error Signing Up", Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }

    }

    private void saveToDatabase() {

        final String name = mName.getText().toString().trim();
        final String email = mUsername.getText().toString().trim();

        if (name.isEmpty() || name.length() < 3) {
            mName.setError("at least 3 characters");
        } else {
            mName.setError(null);
        }

        String user_id = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email)) {

            DatabaseReference currentUser = mDatabase.child(user_id);
            currentUser.child("name").setValue(name);
            currentUser.child("email").setValue(email);

        } else {
            Toast.makeText(SignUpPage.this, "Fill all the Fields", Toast.LENGTH_SHORT).show();
        }

    }
}
