package com.anadi.albumsviewerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

import timber.log.Timber;

public class FacebookLoginActivity extends AppCompatActivity {
    private static final String EMAIL = "email";
    private static final String USER_POSTS = "user_posts";
    private static final String USER_PHOTOS = "user_photos";
//    private static final String AUTH_TYPE = "rerequest";
    private static final String AUTH_TYPE = "request";

    private CallbackManager mCallbackManager;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_login);
        mCallbackManager = CallbackManager.Factory.create();

        LoginButton mLoginButton = findViewById(R.id.login_button);

        // Set the initial permissions to request from the user while logging in
        mLoginButton.setPermissions(Arrays.asList(EMAIL, USER_PHOTOS));
//        mLoginButton.setAuthType(AUTH_TYPE);

        // Register a callback to respond to the user
        mLoginButton.registerCallback(
                mCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        // TODO
                        loginResult.getAccessToken();

                        Timber.d("loginResult %s", loginResult.toString());
                        setResult(RESULT_OK);
                        finish();
                    }

                    @Override
                    public void onCancel() {
                        Timber.d("loginResult CANCEL");
                        setResult(RESULT_CANCELED);
                        finish();
                    }

                    @Override
                    public void onError(FacebookException e) {
                        // Handle exception
                        // TODO

                        Timber.d("FacebookException %s", e.getMessage());
                        e.printStackTrace();

                        setResult(RESULT_CANCELED);
                        finish();
                    }
                });
    }

    //    Then you can later perform the actual login,
//    such as in a custom button's OnClickListener:
    private void login() {
        LoginManager.getInstance().logInWithReadPermissions(
                this, Arrays.asList("public_profile"));
    }

//        If you have any issues fetching user data, enable HTTP request
//        logging by adding this code before your app requests user data:

//        FacebookSdk.addLoggingBehavior(LoggingBehavior.REQUESTS);

//        This will log details about HTTP requests and response to the console log.


    /*Your app can only have one person at a time logged in,
    and LoginManager sets the current AccessToken and Profile for that person.
    The FacebookSDK saves this data in shared preferences and
    sets at the beginning of the session.

    You can see if a person is already logged in by checking

        AccessToken.getCurrentAccessToken() and
        Profile.getCurrentProfile().
    */
}