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
import com.facebook.GraphRequest;
import com.facebook.login.LoginManager;
import com.facebook.GraphResponse;
import com.google.android.gms.appdatasearch.GetRecentContextCall;
import com.kilr.fizzy.models.Friend;
import com.parse.FunctionCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseGeoPoint;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONArray;
import org.json.JSONException;
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
                    Toast.makeText(LoginActivity.this, o.toString(), Toast.LENGTH_SHORT).show();
                }

                if (o == null) {
                    Timber.i("NULL SHIT");
                }
            }
        });
    }

    private void sendMsg() {



          /*  HashMap<String,Object> map = new HashMap<>();
        map.put("body","Hello Ken");
        map.put("location", new ParseGeoPoint(currentLocation.getLatitude(),currentLocation.getLongitude()));
        ParseCloud.callFunctionInBackground("add_message", map, new FunctionCallback<Object>() {
            @Override
            public void done(Object o, ParseException e) {
                if (e != null) {
                    Timber.e(e.getMessage());
                } else if (o != null) {
                    Timber.i(o.toString());
                }

                if (o == null) {
                    Timber.i("NULL SHIT");
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

        List<String> permissions = Arrays.asList("public_profile", "email", "user_friends");
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
                    saveInParse(AccessToken.getCurrentAccessToken(), user, ParseInstallation.getCurrentInstallation());
                    showMainActivity();
                    AccessToken.getCurrentAccessToken();
                    getFriendsList(AccessToken.getCurrentAccessToken());
                } else {
                    saveInParse(AccessToken.getCurrentAccessToken(),user, ParseInstallation.getCurrentInstallation());
                    Log.d(TAG, "User logged in through Facebook!");
                    showMainActivity();
                    AccessToken.getCurrentAccessToken();
                    getFriendsList(AccessToken.getCurrentAccessToken());
                }
            }
        });


    }

    public void getFriendsList(AccessToken accessToken) {

        GraphRequest requesttest = GraphRequest.newGraphPathRequest(accessToken, "/me/taggable_friends", new GraphRequest.Callback() {
            @Override
            public void onCompleted(GraphResponse graphResponse) {
                JSONObject request = graphResponse.getJSONObject();
                try {
                    JSONArray data = request.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject user = data.getJSONObject(i);
                        String imageUrl = user.getJSONObject("picture").getJSONObject("data").getString("url");
                        String userId = data.getJSONObject(i).getString("id");
                        String userName = data.getJSONObject(i).getString("name");
                        Friend friend = new Friend(userName, userId, imageUrl);
                        FriendsList fl = FriendsList.getInstance();
                        fl.addFriend(friend);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        requesttest.executeAsync();

        GraphRequest request = GraphRequest.newMyFriendsRequest(accessToken, new GraphRequest.GraphJSONArrayCallback() {
            @Override
            public void onCompleted(JSONArray jsonArray, GraphResponse graphResponse) {
                graphResponse.getRequest();
            }
        });

        request.executeAsync();

//        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
//            @Override
//            public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {
//
//            }
//        });

    }


    public void saveInParse(AccessToken token,final ParseUser user, final ParseInstallation installation) {


        GraphRequest request = GraphRequest.newMeRequest(token
                ,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        String email = object.optString("email");
                        Timber.i("got email %s",email);
                        user.put("email",email);
                        user.put("installation",installation);
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
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "email");
        request.setParameters(parameters);
        request.executeAsync();


    }

    private void showMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
