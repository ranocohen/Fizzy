package com.kilr.fizzy;

import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.kilr.fizzy.models.Friend;
import com.parse.FindCallback;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import timber.log.Timber;

public class SendMessageActivity extends AppCompatActivity {
    EditText edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Compose");
        toolbar.setTitleTextColor(Color.WHITE);

        final ActionBar ab = getSupportActionBar();
        if(ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
        edit = (EditText) findViewById(R.id.edit);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_send_message, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            sendMsg();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void sendMsg() {
                HashMap<String,Object> map = new HashMap<>();

        if(MainActivity.currentLocation != null) {
            ParseGeoPoint loc = new ParseGeoPoint(MainActivity.currentLocation.getLatitude(),
                    MainActivity.currentLocation.getLongitude());

            map.put("body", edit.getText().toString());
            map.put("location", loc);
            ParseCloud.callFunctionInBackground("add_message", map, new FunctionCallback<Object>() {
                @Override
                public void done(Object o, ParseException e) {
                    if (e != null) {
                        Timber.e(e.getMessage());
                    } else if (o != null) {
                        Timber.i(o.toString());
                        Toast.makeText(SendMessageActivity.this, "Sent!", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    if (o == null) {
                        Timber.i("NULL SHIT");
                    }
                }
            });
        }


    }
}
