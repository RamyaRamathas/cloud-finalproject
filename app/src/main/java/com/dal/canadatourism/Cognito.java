package com.dal.canadatourism;

/*
 * Developed by Keivan Kiyanfar on 10/7/18 10:35 PM
 * Last modified 10/7/18 10:35 PM
 * Copyright (c) 2018. All rights reserved.
 */

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler;
import com.amazonaws.regions.Regions;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_WORLD_WRITEABLE;

public class Cognito {
    // ############################################################# Information about Cognito Pool
    private String poolID = "us-east-1_1ryDjtX5Y";
    private String clientID = "41schrgfi48f4fh15irfq5t7j7";
    private String clientSecret = "vvuaqap4scmugf5rpeuickmi7rmgkjb8itdrl6re77q00qmfga1";
    private Regions awsRegion = Regions.US_EAST_1;         // Place your Region
    // ############################################################# End of Information about Cognito Pool
    public static CognitoUserPool userPool;
    private CognitoUserAttributes userAttributes;       // Used for adding attributes to the user
    private Context appContext;
    public static CognitoUser cognitoUser;
    private String userPassword;                        // Used for Login
    public static String code;

    public static int del = 1000;

    public Cognito(Context context){

        appContext = context;
        userPool = new CognitoUserPool(context, this.poolID, this.clientID, this.clientSecret, this.awsRegion);
        userAttributes = new CognitoUserAttributes();
    }

    public void signUpInBackground(String userId, String password){

        userPool.signUpInBackground(userId, password, this.userAttributes, null, signUpCallback);
        //userPool.signUp(userId, password, this.userAttributes, null, signUpCallback);
    }

    SignUpHandler signUpCallback = new SignUpHandler() {
        @Override
        public void onSuccess(CognitoUser cognitoUser, boolean userConfirmed, CognitoUserCodeDeliveryDetails cognitoUserCodeDeliveryDetails) {
            // Sign-up was successful
            Log.d(TAG, "Sign-up attempt");
            //Toast.makeText(appContext,"Sign-up attempt", Toast.LENGTH_LONG).show();
            // Check if this user (cognitoUser) needs to be confirmed
            if(!userConfirmed) {
                Toast.makeText(appContext,"Verification code sent!", Toast.LENGTH_SHORT).show();

                LayoutInflater lin = LayoutInflater.from(appContext);
                View mfa_verif_view = lin.inflate(R.layout.verification_code_signup, null);

                AlertDialog.Builder alert = new AlertDialog.Builder(appContext);

                // set mfa_verification_code.xml to alert
                alert.setView(mfa_verif_view);

                final EditText mfa_code = (EditText) mfa_verif_view.findViewById(R.id.ver_code);

                // set dialog message
                alert.setCancelable(false).setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                code = mfa_code.getText().toString();
                                Cognito.this.confirmUser(Signup.userId,code);
                                // Log.i(TAG, "in dialog()..."+code+": "+mfa_code);

                            }
                        })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialogBox = alert.create();

                // show it
                alertDialogBox.show();

                // This user must be confirmed and a confirmation code was sent to the user
                // cognitoUserCodeDeliveryDetails will indicate where the confirmation code was sent
                // Get the confirmation code from user
            }
            else {
                Toast.makeText(appContext,"Error: User Confirmed before", Toast.LENGTH_LONG).show();
                // The user has already been confirmed
            }
        }
        @Override
        public void onFailure(Exception exception) {
            Toast.makeText(appContext,"Sign-up failed! Possible reasons: \n1. Invalid value in either of the fields\n2. Email address already registered\n3. Username exists", Toast.LENGTH_LONG).show();
            Log.d(TAG, "Sign-up failed: " + exception);
        }
    };

    public void confirmUser(String userId, String code){
        CognitoUser cognitoUser =  userPool.getUser(userId);
        cognitoUser.confirmSignUpInBackground(code,false, confirmationCallback);

        //cognitoUser.confirmSignUp(code,false, confirmationCallback);
    }
    // Callback handler for confirmSignUp API
    GenericHandler confirmationCallback = new GenericHandler() {

        @Override
        public void onSuccess() {
            // User was successfully confirmed
            Toast.makeText(appContext,"User Confirmed!", Toast.LENGTH_LONG).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    Intent i = new Intent(appContext, HomePage.class);
                    //Toast.makeText(appContext, ""+userSession.getUsername(), Toast.LENGTH_SHORT).show();
                    appContext.startActivity(i);
                }
            }, 1000 );//time in milisecond

        }

        @Override
        public void onFailure(Exception exception) {
            // User confirmation failed. Check exception for the cause.
            Toast.makeText(appContext,"Sign up failed! Invalid code or the email already exists", Toast.LENGTH_LONG).show();
        }
    };

    public void addAttribute(String key, String value){
        userAttributes.addAttribute(key, value);
    }

    public void userLogin(String userId, String password, String code){
        cognitoUser =  userPool.getUser(userId);
        this.userPassword = password;
        this.code = code;
        //Toast.makeText(appContext, ""+userId+" "+userPassword, Toast.LENGTH_SHORT).show();
        cognitoUser.getSessionInBackground(authenticationHandler);
    }
    // Callback handler for the sign-in process
    AuthenticationHandler authenticationHandler = new AuthenticationHandler() {
        @Override
        public void authenticationChallenge(ChallengeContinuation continuation) {
            //Toast.makeText(appContext, "challenge"+continuation.toString(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onSuccess(CognitoUserSession userSession, CognitoDevice newDevice) {
            Toast.makeText(appContext,"Sign in success ", Toast.LENGTH_LONG).show();
            System.out.println(userSession.getIdToken().getJWTToken());

            SharedPreferences pref = appContext.getSharedPreferences("login",MODE_WORLD_WRITEABLE); // 0 - for private mode
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("saved",1);
            editor.putString("token",userSession.getIdToken().getJWTToken());
            editor.commit();

            Intent i = new Intent(appContext, BookingActivity.class);
            //Toast.makeText(appContext, "Welcome "+userSession.getUsername(), Toast.LENGTH_SHORT).show();
            i.putExtra("token",userSession.getIdToken().getJWTToken());
            appContext.startActivity(i);
        }

        @Override
        public void getAuthenticationDetails(AuthenticationContinuation authenticationContinuation, String userId) {
            // The API needs user sign-in credentials to continue
            //Toast.makeText(appContext, ""+userId+" "+userPassword, Toast.LENGTH_SHORT).show();
            AuthenticationDetails authenticationDetails = new AuthenticationDetails(userId, userPassword, null);
            // Pass the user sign-in credentials to the continuation
            authenticationContinuation.setAuthenticationDetails(authenticationDetails);
            // Allow the sign-in to continue
            authenticationContinuation.continueTask();
            //Toast.makeText(appContext, ""+getClientID(), Toast.LENGTH_SHORT).show();
            //Toast.makeText(appContext, "details", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void getMFACode(final MultiFactorAuthenticationContinuation multiFactorAuthenticationContinuation) {
            //Multi-factor authentication is required; get the verification code from user

            LayoutInflater lin = LayoutInflater.from(appContext);
            View mfa_verif_view = lin.inflate(R.layout.verification_code_login, null);

            AlertDialog.Builder alert = new AlertDialog.Builder(appContext);

            // set mfa_verification_code.xml to alert
            alert.setView(mfa_verif_view);

            final EditText mfa_code = (EditText) mfa_verif_view.findViewById(R.id.ver_code);

            // set dialog message
            alert.setCancelable(false).setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            code = mfa_code.getText().toString();
                            // Log.i(TAG, "in dialog()..."+code+": "+mfa_code);
                            multiFactorAuthenticationContinuation.setMfaCode(code);
                            multiFactorAuthenticationContinuation.continueTask();
                        }
                    })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

            // create alert dialog
            AlertDialog alertDialogBox = alert.create();

            // show it
            alertDialogBox.show();

//            new Handler().postDelayed(new Runnable() {
//
//                @Override
//                public void run() {
//
//                    if(code.length()==6) {
//                        Toast.makeText(appContext, "" + code, Toast.LENGTH_SHORT).show();
//                        multiFactorAuthenticationContinuation.setMfaCode(code);
//                        multiFactorAuthenticationContinuation.continueTask();
//                    }
//
//                }
//            }, 12000 );//time in milisecond


        }

        @Override
        public void onFailure(Exception exception) {
            // Sign-in failed, check exception for the cause
            Toast.makeText(appContext,"Sign in Failure! Wrong password or OTP!", Toast.LENGTH_LONG).show();
        }
    };

    public String getPoolID() {
        return poolID;
    }

    public void setPoolID(String poolID) {
        this.poolID = poolID;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public Regions getAwsRegion() {
        return awsRegion;
    }

    public void setAwsRegion(Regions awsRegion) {
        this.awsRegion = awsRegion;
    }

    public static CognitoUserPool getPool() {
        return userPool;
    }
}
