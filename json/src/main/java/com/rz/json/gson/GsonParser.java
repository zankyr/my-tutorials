package com.rz.json.gson;

import com.google.gson.Gson;
import com.rz.json.model.User;

public class GsonParser {
    public static User parseUser(String json) {
        return new Gson().fromJson(json, User.class);
    }

    public static String createJson(User user) {
        return new Gson().toJson(user);
    }
}
