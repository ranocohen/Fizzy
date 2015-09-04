package com.kilr.fizzy;

import android.location.Location;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kilr.fizzy.fragments.PublicMessagesRecyclerListFragment;
import com.kilr.fizzy.models.Message;
import com.kilr.fizzy.sensors.HeadTracker;
import com.kilr.fizzy.sensors.HeadTransform;
import com.kilr.fizzy.widget.DirectionalTextViewContainer;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class ARActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;
    private HeadTracker mHeadTracker;
    private HeadTransform mHeadTransform;
    private Handler mTrackingHandler = new Handler();
    private boolean mIsTracking = false;
    private float[] mEulerAngles = new float[3];
    private ArrayList<Message> mMessages;
     DirectionalTextViewContainer mDirectionalTextViewContainer;

    private Runnable updateDirectionalTextView = new Runnable() {
        @Override
        public void run() {
            mDirectionalTextViewContainer.updateView(Math.toDegrees(mEulerAngles[1]));
        }
    };
    private Location mLastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar);
        mMessages = new ArrayList<>();
        initSensors();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mGoogleApiClient.connect();
        mDirectionalTextViewContainer = (DirectionalTextViewContainer) findViewById(R.id.directional_text_view_container);




    }

    private void initSensors()
    {
        mHeadTracker = HeadTracker.createFromContext(this);
        mHeadTransform = new HeadTransform();
    }


    private void startTracking()
    {
        mIsTracking = true;

        mTrackingHandler.post(new Runnable() {
            @Override
            public void run() {
                if (!mIsTracking) return;

                mHeadTracker.getLastHeadView(mHeadTransform.getHeadView(), 0);
                mHeadTransform.getEulerAngles(mEulerAngles, 0);
                Log.i("euler", String.format("Euler %f %f %f ", mEulerAngles[0], mEulerAngles[1], mEulerAngles[2]));
                runOnUiThread(updateDirectionalTextView);

                mTrackingHandler.postDelayed(this, 100);
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();

        mIsTracking = false;
        mHeadTracker.stopTracking();
        //drawing
        mDirectionalTextViewContainer.stopDrawing();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //sensors
        mDirectionalTextViewContainer.startDrawing();
    }

    @Override
    public void onConnected(Bundle bundle) {
        init();
    }



    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    private void init() {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        ParseQuery<Message> query = ParseQuery.getQuery(Message.class);
        query.findInBackground(new FindCallback<Message>() {
            public void done(List<Message> messages, ParseException e) {
                if (e == null) {
                    for (Message msg : messages) {

                        mMessages.add(msg);
                    }
                    startTracking();
                    mDirectionalTextViewContainer.updateMessages(mMessages, mLastLocation);

                } else {

                    Timber.d("Error");
                }
            }
        });
    }
}
