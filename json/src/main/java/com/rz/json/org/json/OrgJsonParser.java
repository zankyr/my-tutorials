package com.rz.json.org.json;

import org.json.JSONObject;

import java.util.Map;

public class OrgJsonParser {

    private OrgJsonParser() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static JSONObject createJSONObject(String jsonString) {
        return new JSONObject(jsonString);
    }
}
