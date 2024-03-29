package com.kilr.fizzy.models;

import android.location.Location;

import java.util.List;

/**
 * Nearby place with the name (used for maps and display) and the
 * distance to that place, calculated against our current {@link Location}
 *
 */
public class NearbyPlace
{
    private float distance;
    private String name;

    public NearbyPlace(float distance, String name) {
        this.distance = distance;
        this.name = name;
    }

    public float getDistance() {
        return distance;
    }

    public String getName() {
        return name;
    }
}
