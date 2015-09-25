package com.kilr.fizzy;

import android.app.Dialog;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.kilr.fizzy.fragments.PublicMessagesRecyclerListFragment;
import com.kilr.fizzy.models.Message;
import com.kilr.fizzy.models.User;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by idanakav on 9/3/15.
 */
public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, AppBarLayout.OnOffsetChangedListener, LocationListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    public static String TAG = MainActivity.class.toString();

    private final static int radius = 50;

    private static String MESSAGE_FRAGMENT = "message_fragment";

    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    /*
     * Constants for location update parameters
     */
    private static final int MILLISECONDS_PER_SECOND = 1000;

    private static final int UPDATE_INTERVAL_IN_SECONDS = 10;

    // A fast interval ceiling
    private static final int FAST_CEILING_IN_SECONDS = 1;

    // Update interval in milliseconds
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = MILLISECONDS_PER_SECOND
            * UPDATE_INTERVAL_IN_SECONDS;

    // A fast ceiling of update intervals, used when the app is visible
    private static final long FAST_INTERVAL_CEILING_IN_MILLISECONDS = MILLISECONDS_PER_SECOND
            * FAST_CEILING_IN_SECONDS;
    /*
     * Constants for handling location results
     */
    // Conversion from feet to meters
    private static final float METERS_PER_FEET = 0.3048f;

    // Conversion from kilometers to meters
    private static final int METERS_PER_KILOMETER = 1000;

    // Initial offset for calculating the map bounds
    private static final double OFFSET_CALCULATION_INIT_DIFF = 1.0;

    // Accuracy for calculating the map bounds
    private static final float OFFSET_CALCULATION_ACCURACY = 0.01f;

    // Maximum results returned from a Parse query
    private static final int MAX_POST_SEARCH_RESULTS = 20;

    // Maximum post search radius for map in kilometers
    private static final int MAX_POST_SEARCH_DISTANCE = 100;


    //GUI
    private AppBarLayout mAppBarLayout;
    LatLng centerDummy = new LatLng(32.066158, 34.777819);

    private LocationRequest locationRequest; // A request to connect to Location Services
    private GoogleApiClient locationClient; // Stores the current instantiation of the location client in this object
    public static Location lastLocation;
    public static Location currentLocation;
    private boolean hasSetUpInitialLocation;
    private GoogleMap map;
    private MapView mapView;
    private CoordinatorLayout coordinatorLayout;
    private FloatingActionButton mFab;
    private ArrayList<Message> mMessages = new ArrayList();
    private HashMap<String, User> mUsers = new HashMap<>();

    FrameLayout test;


    public ArrayList<Message> getmMessages() {
        return mMessages;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // LOCATION HANDLE
        // Create a new global location parameters object
        locationRequest = LocationRequest.create();
        // Set the update interval
        locationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        // Use high accuracy
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        // Set the interval ceiling to one minute
        locationRequest.setFastestInterval(FAST_INTERVAL_CEILING_IN_MILLISECONDS);

        // Create a new location client, using the enclosing class to handle callbacks.
        locationClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        locationClient.connect();


        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.toolbar_title));
        toolbar.setTitleTextColor(Color.WHITE);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.main_appbar);

        mAppBarLayout.addOnOffsetChangedListener(this);

        setSupportActionBar(toolbar);
        if (savedInstanceState == null) {
            PublicMessagesRecyclerListFragment fragment = new PublicMessagesRecyclerListFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content, fragment, MESSAGE_FRAGMENT)
                    .commit();
        }

        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.setClickable(false);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator);

        CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);
        mapView.getMap().animateCamera(zoom);

        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SendMessageActivity.class));
            }
        });
        mapView.getMap().getUiSettings().setScrollGesturesEnabled(false);
        mapView.getMap().getUiSettings().setCompassEnabled(false);
        mapView.getMap().getUiSettings().setZoomControlsEnabled(false);

        mapView.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;
        fetchNearMessages();
    }

    private void fetchNearMessages() {

        //// TODO: 9/25/15 add new one
//        ParseQuery<Message> query = ParseQuery.getQuery(Message.class);
//        query.findInBackground(new FindCallback<Message>() {
//            public void done(List<Message> messages, ParseException e) {
//                if (e == null) {
//                    for (Message msg : messages) {
//                        map.addMarker(new MarkerOptions()
//                                .icon(BitmapDescriptorFactory
//                                        .fromResource(R.drawable.ic_chat))
//                                .position(new LatLng(msg.getLocation().getLatitude(), msg.getLocation().getLongitude()))
//                                .title(msg.getBody()));
//                        mMessages.add(msg);
//
//                    }
//
//                    HashSet<String> uniqueIds = new HashSet<String>();
//
//                    for (int i = 0; i < mMessages.size(); i++) {
//                        uniqueIds.add(mMessages.get(i).getFrom().getObjectId());
//                    }
//                    ParseQuery<ParseUser> query = ParseUser.getQuery();
//                    query.whereContainedIn("objectId", Arrays.asList(uniqueIds.toArray(new String[1])));
//                    List<ParseUser> results = null;
//                    try {
//                        results = query.find();
//                        int i = 0;
//                    } catch (ParseException e1) {
//                        e1.printStackTrace();
//                    } finally {
//                        Log.d("fuck", results.toString());
//                        for (int i = 0; i < results.size(); i++) {
//                            mUsers.put(results.get(i).getObjectId(), results.get(i));
//                        }
//                    }
//
////                    //TODO update adapter
//                    PublicMessagesRecyclerListFragment pmrlf = (PublicMessagesRecyclerListFragment) getSupportFragmentManager().findFragmentByTag(MESSAGE_FRAGMENT);
//                    pmrlf.setData(mMessages, mUsers);
//
//                } else {
//
//                    Timber.d("Error");
//                }
//            }
//        });
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        //TODO animate current location while map changes

    }

    @Override
    public void onLocationChanged(Location location) {

        currentLocation = location;

        lastLocation = location;
        LatLng myLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        if (!hasSetUpInitialLocation) {
            // Zoom to the current location.
            LatLng latlng = new LatLng(location.getLatitude(), location.getLongitude());
            LatLngBounds bounds = LatLngBounds.builder().include(latlng).build();
            mapView.getMap().animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 16));

            hasSetUpInitialLocation = true;
        }
        // Update map radius indicator
        updateCircle(myLatLng);
    }

    @Override
    public void onConnected(Bundle bundle) {
        currentLocation = getLocation();

        startPeriodicUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // Google Play services can resolve some errors it detects. If the error has a resolution, try
        // sending an Intent to start a Google Play services activity that can resolve error.
        if (connectionResult.hasResolution()) {
            try {

                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);

            } catch (IntentSender.SendIntentException e) {

            }
        } else {
            // If no resolution is available, display a dialog to the user with the error.
            //showErrorDialog(connectionResult.getErrorCode());
        }
    }

    private void updateCircle(LatLng myLatLng) {
        //TODO update marker
    }

    private Location getLocation() {
        // If Google Play Services is available
        if (servicesConnected()) {
            // Get the current location
            return LocationServices.FusedLocationApi.getLastLocation(locationClient);
        } else {
            return null;
        }
    }

    private boolean servicesConnected() {
        // Check that Google Play services is available
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        // If Google Play services is available
        if (ConnectionResult.SUCCESS == resultCode) {
            // Continue
            return true;
            // Google Play services was not available for some reason
        } else {
            // Display an error dialog
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(resultCode, this, 0);
            if (dialog != null) {
                Log.d(TAG, "failed to connect");
            }
            return false;
        }
    }


//    In response to a request to start updates, send a request to Location Services

    private void startPeriodicUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(
                locationClient, locationRequest, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            startActivity(new Intent(this, ARActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
