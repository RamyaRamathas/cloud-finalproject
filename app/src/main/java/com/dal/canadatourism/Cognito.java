package com.dal.canadatourism;

/*
 * Developed by Keivan Kiyanfar on 10/7/18 10:35 PM
 * Last modified 10/7/18 10:35 PM
 * Copyright (c) 2018. All rights reserved.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

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

public class Cognito {
    // ############################################################# Information about Cognito Pool
    private String poolID = "us-east-1_1ryDjtX5Y";
    private String clientID = "41schrgfi48f4fh15irfq5t7j7";
    private String clientSecret = "vvuaqap4scmugf5rpeuickmi7rmgkjb8itdrl6re77q00qmfga1";
    private Regions awsRegion = Regions.US_EAST_1;         // Place your Region
    // ############################################################# End of Information about Cognito Pool
    private CognitoUserPool userPool;
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
            Log.d(TAG, "Sign-up success");
            Toast.makeText(appContext,"Sign-up attempt", Toast.LENGTH_LONG).show();
            // Check if this user (cognitoUser) needs to be confirmed
            if(!userConfirmed) {
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
            Toast.makeText(appContext,"Sign-up failed"+exception, Toast.LENGTH_LONG).show();
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
            Toast.makeText(appContext,"User Confirmed", Toast.LENGTH_LONG).show();

        }

        @Override
        public void onFailure(Exception exception) {
            // User confirmation failed. Check exception for the cause.

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
            //Toast.makeText(appContext,"Sign in success ", Toast.LENGTH_LONG).show();
            System.out.println(userSession.getIdToken().getJWTToken());

            SharedPreferences pref = appContext.getSharedPreferences("login",0); // 0 - for private mode
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("saved",1);
            editor.commit();

            Intent i = new Intent(appContext, BookingActivity.class);
            //Toast.makeText(appContext, ""+userSession.getUsername(), Toast.LENGTH_SHORT).show();
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
            Toast.makeText(appContext, "details", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void getMFACode(final MultiFactorAuthenticationContinuation multiFactorAuthenticationContinuation) {
            //Multi-factor authentication is required; get the verification code from user



            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {

                    if(code.length()==6) {
                        Toast.makeText(appContext, "" + code, Toast.LENGTH_SHORT).show();
                        multiFactorAuthenticationContinuation.setMfaCode(code);
                        multiFactorAuthenticationContinuation.continueTask();
                    }

                }
            }, 12000 );//time in milisecond


        }

        @Override
        public void onFailure(Exception exception) {
            // Sign-in failed, check exception for the cause
            Toast.makeText(appContext,"Sign in Failure"+exception, Toast.LENGTH_LONG).show();
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

}
