package com.kilr.fizzy;

import com.kilr.fizzy.models.Friend;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by rancohen on 9/3/15.
 */
public class FriendsList {
    private static FriendsList mInstance = null;

    private static ArrayList<Friend> friendsList;

    private FriendsList(){

    }

    public static FriendsList getInstance(){
        if(mInstance == null)
        {
            mInstance = new FriendsList();
            friendsList = new ArrayList<>();
        }
        return mInstance;
    }

    public static void addFriend(Friend friend) {
        friendsList.add(friend);
    }


    public static ArrayList<Friend> getFriendsList() {
        return friendsList;
    }

}