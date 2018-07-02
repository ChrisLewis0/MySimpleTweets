package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;

public class User {

    // list out attributes
    public String name;
    public long uid;
    public String screenName;
    public String profileImageUrl;

    // deserialize JSON
    public static User fromJSON(JSONObject jsonObject) throws JSONException {
        // creates user object
        User user = new User();
        // extract and fill values
        user.name = jsonObject.getString("name");
        user.uid = jsonObject.getLong("id");
        user.screenName = jsonObject.getString("screen_name");
        user.profileImageUrl = jsonObject.getString("profile_image_url");
        // returns user
        return user;
    }

}
