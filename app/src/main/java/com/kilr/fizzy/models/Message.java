package com.kilr.fizzy.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by idanakav on 9/3/15.
 */
@ParseClassName("Message")

    public class Message extends ParseObject {

    public Message() {}

    boolean isPublic;
    String from;

}
