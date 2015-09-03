package com.kilr.fizzy.models;

/**
 * Created by rancohen on 9/3/15.
 */
public class Friend {

    //static String imageUrl "https://graph.facebook.com/\%s/picture?type=large";
    String name;
    String userId;
    String userImageURL;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserImageURL() {
        return userImageURL;
    }

    public void setUserImageURL(String userImageURL) {
        this.userImageURL = userImageURL;
    }

    public Friend(String name, String userId, String userImageURL) {
        this.name = name;
        this.userId = userId;
        this.userImageURL = userImageURL;
    }
}
