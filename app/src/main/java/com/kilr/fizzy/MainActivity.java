package com.kilr.fizzy;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.kilr.fizzy.fragments.PublicMessagesRecyclerListFragment;

/**
 * Created by idanakav on 9/3/15.
 */
public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, AppBarLayout.OnOffsetChangedListener {

    private AppBarLayout mAppBarLayout;
    MapFragment mapFragment;
    LatLng centerDummy = new LatLng(32.066158, 34.777819);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.main_appbar);

        mAppBarLayout.addOnOffsetChangedListener(this);

        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            PublicMessagesRecyclerListFragment fragment = new PublicMessagesRecyclerListFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content, fragment)
                    .commit();
        }

        mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        CameraUpdate center = CameraUpdateFactory.newLatLng(centerDummy);
        mapFragment.getMap().moveCamera(center);

        CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);
        mapFragment.getMap().animateCamera(zoom);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //TODO center current location
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        //TODO animate current location while map changes
    }
}
