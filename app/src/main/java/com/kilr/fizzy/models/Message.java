package com.kilr.fizzy.models;


import com.google.android.gms.maps.model.LatLng;

/**
 * Created by idanakav on 9/3/15.
 */

    public class Message {

    public Message() {}

    boolean isPublic;
    String body;
    LatLng location;
    boolean viewed;
    User to;
    User from;
    //Date createdAt;


    public boolean isPublic() {
        return isPublic;
    }

    public void setIsPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public boolean isViewed() {
        return viewed;
    }

    public void setViewed(boolean viewed) {
        this.viewed = viewed;
    }

    public User getTo() {
        return to;
    }

    public void setTo(User to) {
        this.to = to;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }







}
