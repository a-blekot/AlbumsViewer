package com.anadi.albumsviewerapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.anadi.albumsviewerapp.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

import timber.log.Timber;

public class FacebookLoginActivity extends AppCompatActivity {
    private static final String EMAIL = "email";
    private static final String USER_PHOTOS = "user_photos";

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

        if (isLoggedIn()) {
            navigateToAlbumsViewerActivity();
        }

        LoginButton mLoginButton = findViewById(R.id.fb_login_button);

        // Set the initial permissions to request from the user while logging in
        mLoginButton.setPermissions(Arrays.asList(EMAIL, USER_PHOTOS));

        mCallbackManager = CallbackManager.Factory.create();

        // Register a callback to respond to the user
        mLoginButton.registerCallback(
                mCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        navigateToAlbumsViewerActivity();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(FacebookLoginActivity.this, R.string.login_again,
                                Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException e) {
                        Toast.makeText(FacebookLoginActivity.this, R.string.login_error,
                                Toast.LENGTH_LONG).show();
                        Timber.d(e);
                        Timber.d(Arrays.toString(e.getStackTrace()));
                    }
                });
    }

    private boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return (accessToken != null) && !accessToken.isExpired();
    }

    private void navigateToAlbumsViewerActivity() {
        Intent intent = new Intent(this, AlbumsViewerActivity.class);
        startActivity(intent);
        finish();
    }
}