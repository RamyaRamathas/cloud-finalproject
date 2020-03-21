/*
 * Developed by Keivan Kiyanfar on 10/9/18 11:38 PM
 * Last modified 10/9/18 11:38 PM
 * Copyright (c) 2018. All rights reserved.
 */

package com.cloud.tourism;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    // ############################################################# View Components
    TextView txtNotAccount, txtCode;     // For creating account
    //TextView txtForgetPass;     // For retrieving password
    Button btnLogin;            // Button for Login
    Button btnMFA;
    EditText etUsername;
    EditText etPassword;
    static public String code="";
    Cognito authentication;

    // ############################################################# End View Components

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login");
        initViewComponents();
        authentication = new Cognito(getApplicationContext());
    }

    private void initViewComponents(){
        txtNotAccount = findViewById(R.id.txtNotAccount);
        txtCode = findViewById(R.id.mfa);
        //txtForgetPass= findViewById(R.id.txtForgetPass);
        btnLogin = findViewById(R.id.btnLogin);
        btnMFA = findViewById(R.id.btnMFA);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);

        txtNotAccount.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Signup.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                code = txtCode.getText().toString();
                Cognito authentication = new Cognito(Login.this);
                authentication.userLogin(etUsername.getText().toString().replace(" ", ""), etPassword.getText().toString(), code);

                btnMFA.setVisibility(View.VISIBLE);
                txtCode.setVisibility(View.VISIBLE);
            }
        });

        btnMFA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                code = txtCode.getText().toString();
                Cognito.code = code;
            }
        });
    }
}
