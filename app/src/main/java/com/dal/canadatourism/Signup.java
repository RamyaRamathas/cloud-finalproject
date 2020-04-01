package com.dal.canadatourism;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Signup extends AppCompatActivity {

    // ############################################################# View Components
    EditText etUsername;        // Enter Username
    EditText etEmail;           // Enter Email
    EditText etMobile;          // Enter Mobile
    EditText etPass;            // Enter Password
    EditText etRepeatPass;      // Repeat Password
    EditText etConfCode;        // Enter Confirmation Code

    Button btnSignUp;           // Sending data to Cognito for registering new user
    Button btnVerify;
    // ############################################################# End View Components

    // ############################################################# Cognito connection
    Cognito authentication;
    public static String userId;
    // ############################################################# End Cognito connection

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        authentication = new Cognito(getApplicationContext());
        initViewComponents();
    }

    private void initViewComponents(){
        etUsername = findViewById(R.id.etUsername);
        etEmail= findViewById(R.id.etEmail);
        etMobile = findViewById(R.id.etMobile);
        etPass = findViewById(R.id.etPass);
        etRepeatPass = findViewById(R.id.etRepeatPass);
        etConfCode = findViewById(R.id.etConfCode);

        btnSignUp = findViewById(R.id.btnSignUp);
        btnVerify = findViewById(R.id.btnVerify);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etPass.getText().toString().endsWith(etRepeatPass.getText().toString())){
                    if(etMobile.getText().toString().replace(" ", "").length()!=12){
                        Toast.makeText(Signup.this, "Invalid phone number! Please include the country code.", Toast.LENGTH_SHORT).show();
                    }else {
                        Cognito authentication = new Cognito(Signup.this);
                        userId = etUsername.getText().toString().replace(" ", "");
                        authentication.addAttribute("name", userId);
                        authentication.addAttribute("phone_number", etMobile.getText().toString().replace(" ", ""));
                        authentication.addAttribute("email", etEmail.getText().toString().replace(" ", ""));
                        authentication.signUpInBackground(userId, etPass.getText().toString());

                        //etConfCode.setVisibility(View.VISIBLE);
                        //btnVerify.setVisibility(View.VISIBLE);
                    }
                }
                else{

                }
            }
        });

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //authentication.confirmUser(userId, etConfCode.getText().toString().replace(" ", ""));
                //finish();
            }
        });

    }
}
