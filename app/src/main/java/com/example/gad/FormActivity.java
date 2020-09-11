package com.example.gad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.gad.remote.APIService;
import com.example.gad.remote.data.ApiUtils;

public class FormActivity extends AppCompatActivity {

    private APIService mAPIService;
    private EditText mFirstName;
    private EditText mLastName;
    private EditText mEmailAddress;
    private EditText mProjectLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        mFirstName = (EditText) findViewById(R.id.firstName);
        mLastName = (EditText) findViewById(R.id.lastName);
        mEmailAddress = (EditText) findViewById(R.id.emailAddress);
        mProjectLink = (EditText) findViewById(R.id.webAddress);
        Button submitButton = (Button) findViewById(R.id.submit_button);

        mAPIService = ApiUtils.getAPIService();

        /*submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = mFirstName.getText().toString().trim();
                String lastName = mLastName.getText().toString().trim();
                String emailAddress = mEmailAddress.getText().toString().trim();
                String projectLink = mProjectLink.getText().toString().trim();

                if (!TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(lastName) &&
                        !TextUtils.isEmpty(emailAddress) &&!TextUtils.isEmpty(projectLink)) {
//                    savePost(firstName, lastName, emailAddress, projectLink);
                }
            }
        });*/
    }
}