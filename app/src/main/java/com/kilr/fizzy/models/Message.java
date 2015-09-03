package com.kilr.fizzy.models;

import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.Date;

/**
 * Created by idanakav on 9/3/15.
 */
@ParseClassName("Message")

    public class Message extends ParseObject {

    public Message() {}


    boolean isPublic;
    String body;
    ParseGeoPoint location;
    boolean viewed;
    ParseRelation<ParseUser> to;
    ParseRelation<ParseUser> from;
    Date createdAt;

    public boolean isPublic() {
        return getBoolean("isPublic");
    }

    public void setIsPublic(boolean isPublic) {
        put("isPublic",isPublic);
    }

    public String getBody() {
        return getString("body");
    }

    public void setBody(String body) {
        put("body",body);
    }

    public ParseGeoPoint getLocation() {
       return getParseGeoPoint("location");
    }

    public void setLocation(ParseGeoPoint location) {
        put("location",location);
    }

    public boolean isViewed() {
       return getBoolean("viewed");
    }

    public void setViewed(boolean viewed) {
        put("viewed",viewed);
    }

    public ParseUser getTo() {
        return getParseUser("to");
    }

    public void setTo(ParseRelation<ParseUser> to) {
        put("to",to);
    }

    public ParseUser getFrom() {
        return getParseUser("from");
    }

    public void setFrom(ParseRelation<ParseUser> from) {
        put("from",from);
    }

    @Override
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }






}
