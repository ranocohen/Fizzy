package com.kilr.fizzy;

import com.kilr.fizzy.models.Friend;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by rancohen on 9/3/15.
 */
public class FriendsList {
    private static FriendsList mInstance = null;

    private static HashMap<String, Friend> friendsList;

    private FriendsList(){

    }

    public static FriendsList getInstance(){
        if(mInstance == null)
        {
            mInstance = new FriendsList();
            friendsList = new HashMap<>();
        }
        return mInstance;
    }

    public static void addFriend(Friend friend) {
        friendsList.put(friend.getUserId(), friend);
    }

}