package com.kilr.fizzy;

import android.app.Application;

import com.facebook.FacebookSdk;

import timber.log.Timber;

/**
 * Created by idanakav on 9/3/15.
 */
public class FizzyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());
        FacebookSdk.sdkInitialize(getApplicationContext());
        //Parse.initialize(this, "W37V8wslrioALql4uUGFJqxKwiRIhWLw3adCaR7l", "Wjjf2byorlAH9uGRsLfYyjH8X2SfGyjICEXz0mUn");


    }


}
