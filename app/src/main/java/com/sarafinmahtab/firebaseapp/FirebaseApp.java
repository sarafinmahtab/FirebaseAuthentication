package com.sarafinmahtab.firebaseapp;

import android.app.Application;

import com.firebase.client.Firebase;

public class FirebaseApp extends Application {

    @Override
    public void onCreate() {
        Firebase.setAndroidContext(this);
        super.onCreate();
    }
}
