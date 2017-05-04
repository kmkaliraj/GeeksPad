package com.example.sreer.geekspad.model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by kalirajkalimuthu on 4/8/17.
 */

@IgnoreExtraProperties
public class ChatUser {
    public String firsname;
    public String email;

    public ChatUser() {

    }
    public ChatUser(String firstname, String email) {
        this.firsname = firstname;
        this.email = email;
    }
}