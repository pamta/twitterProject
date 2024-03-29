package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;

public class User {

    //attributes
    public String name;
    public long uid;
    public String screenName;
    public String profileImageUrl;

    //desirialize the JSON
    public static User fromJson(JSONObject json) throws JSONException {
        User user = new User();

        //extract and fill the values
        user.name = json.getString("name");
        user.uid = json.getLong("id");
        user.screenName = json.getString("screen_name");
        //android handles https requests, not http
        user.profileImageUrl = json.getString("profile_image_url_https");

        return user;
    }
}
