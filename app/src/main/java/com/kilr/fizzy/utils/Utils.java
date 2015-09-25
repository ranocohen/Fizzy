package com.kilr.fizzy.utils;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by rancohen on 9/25/15.
 */
public class Utils {

    public static LatLng geoPointFromLocation(Location loc) {
        return new LatLng(loc.getLatitude(), loc.getLongitude());
    }

}
