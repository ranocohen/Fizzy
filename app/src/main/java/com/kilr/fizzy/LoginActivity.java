package com.kilr.fizzy;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.facebook.GraphResponse;
import com.parse.FunctionCallback;
import com.parse.LogInCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseGeoPoint;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import timber.log.Timber;

public class LoginActivity extends AppCompatActivity {

    private Dialog progressDialog;
    public static final String TAG = LoginActivity.class.getCanonicalName();
    private EditText edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ParseUser.logOut();
        //startActivity(new Intent(this, IntroActivity.class));
        // Check if there is a currently logged in user
        // and it's linked to a Facebook account.
        ParseUser currentUser = ParseUser.getCurrentUser();
        if ((currentUser != null) && ParseFacebookUtils.isLinked(currentUser)) {
            // Go to the user info activity
            showMainActivity();
        }
        edit = (EditText) findViewById(R.id.editText);
        Button b = (Button) findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMsg();

            }
        });

        Button b2 = (Button) findViewById(R.id.button2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                action2();
            }
        });

    }

    private void action2() {
        HashMap<String,Object> map = new HashMap<>();
        ParseCloud.callFunctionInBackground("push_test", map, new FunctionCallback<Object>() {
            @Override
            public void done(Object o, ParseException e) {
                if (e != null) {
                    Timber.e(e.getMessage());
                } else if (o != null) {
                    Toast.makeText(LoginActivity.this,o.toString(), Toast.LENGTH_SHORT).show();
                }

                if (o == null) {
                    Timber.i("NULL SHIT");
                }
            }
        });
    }

    private void sendMsg() {

/*                if (e != null) {
                    Timber.e(e.getMessage());
                } else if (o != null) {
                    Timber.i(o.toString());
                }*/

/*        ParseObject msg = new ParseObject("Messages");
        ParseGeoPoint geoPoint = new ParseGeoPoint(51.5033630,-0.1276250);
        msg.put("body",edit.getText().toString());
        msg.put("location",geoPoint);
        msg.put("from",ParseUser.getCurrentUser());
        msg.put("isPublic",true);
        msg.put("viewed",false);
        msg.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "sent", Toast.LENGTH_SHORT).show();

                }
            }
        });*/
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
    }

    public void onLoginClick(View v) {
        progressDialog = ProgressDialog.show(LoginActivity.this, "", "Logging in...", true);

        List<String> permissions = Arrays.asList("public_profile", "email");
        // NOTE: for extended permissions, like "user_about_me", your app must be reviewed by the Facebook team
        // (https://developers.facebook.com/docs/facebook-login/permissions/)

        ParseFacebookUtils.logInWithReadPermissionsInBackground(this, permissions, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException err) {
                progressDialog.dismiss();
                if (user == null) {
                    Log.d(TAG, "Uh oh. The user cancelled the Facebook login.");
                } else if (user.isNew()) {
                    Log.d(TAG, "User signed up and logged in through Facebook!");
                    saveInParse(user, ParseInstallation.getCurrentInstallation());
                    showMainActivity();
                    AccessToken.getCurrentAccessToken();
                } else {
                    saveInParse(user, ParseInstallation.getCurrentInstallation());
                    Log.d(TAG, "User logged in through Facebook!");
                    showMainActivity();
                    AccessToken.getCurrentAccessToken();
                }
            }
        });


    }

//    public void getInfo(ParseUser user, AccessToken accessToken) {
//
//
//        GraphRequest request = GraphRequest.newMeRequest(
//                user.getSessionToken(),
//                new GraphRequest.GraphJSONObjectCallback() {
//                    @Override
//                    public void onCompleted(
//                            JSONObject object,
//                            GraphResponse response) {
//                        // Application code
//                    }
//                });
//    }


    public void saveInParse(ParseUser user,ParseInstallation installation) {
        user.setEmail("idanakav@gmail.com");
        installation = ParseInstallation.getCurrentInstallation();
        installation.put("User",ParseUser.getCurrentUser());
        ParseInstallation.getCurrentInstallation().saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Timber.e(e.getMessage());
                } else {
                    Timber.i("Sent!");

                }
            }
        });
        user.saveInBackground();
    }

    private void showMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
