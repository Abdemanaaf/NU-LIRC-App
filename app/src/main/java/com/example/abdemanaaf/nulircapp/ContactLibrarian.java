package com.example.abdemanaaf.nulircapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ContactLibrarian extends AppCompatActivity {

    EditText mName;
    EditText mEmail;
    EditText mQueryTitle;
    EditText mQueryDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_librarian);

        mName = findViewById(R.id.contactName);
        mEmail = findViewById(R.id.contactEmail);
        mQueryTitle = findViewById(R.id.queryTitle);
        mQueryDesc = findViewById(R.id.queryDesc);

        Button submitQuery = findViewById(R.id.submitQuery);
        submitQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });

    }

    private void sendEmail() {

        String name = mName.getText().toString();
        String email = mEmail.getText().toString();
        String queryTitle = mQueryTitle.getText().toString();
        String queryDesc = mQueryDesc.getText().toString();

        String[] mailTo = {"vinay.kainthola@niituniversity.in"};

        String message = queryDesc + "\n\n" + "From," + "\n" + name + "\n" + email;

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, mailTo[0]);
        intent.putExtra(Intent.EXTRA_SUBJECT, queryTitle);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
