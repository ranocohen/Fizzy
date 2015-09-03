package com.kilr.fizzy;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.kilr.fizzy.models.Message;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;

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
        ParseObject.registerSubclass(Message.class);
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "W37V8wslrioALql4uUGFJqxKwiRIhWLw3adCaR7l", "Wjjf2byorlAH9uGRsLfYyjH8X2SfGyjICEXz0mUn");
        ParseFacebookUtils.initialize(this);

    }


}
