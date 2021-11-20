package com.rz.json.org.json;

import org.assertj.core.api.Assertions;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JsonFromToStringTest {

    private JSONObject getJSONObject() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "aGenericName");
        jsonObject.put("id", 123);
        return jsonObject;
    }

    private String getJSONPString() throws JSONException {
        return "callback" +
                "(" +
                getJSONObject() +
                ")";
    }

    @Test
    void shouldCreateJSONObjectFromString() throws JSONException {
        // given
        String jsonString = "{\"name\":\"aGenericName\",\"id\":123}";

        // when
        JSONObject actual = new JSONObject(jsonString);

        // then
        JSONObject expected = getJSONObject();
        Assertions.assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void shouldPrintExpectedJSONObjectString() throws JSONException {
        // when
        String actual = getJSONObject().toString();

        // then
        String expected = "{\"name\":\"aGenericName\",\"id\":123}";
        assertEquals(expected, actual);
    }


    @Test
    void shouldParseAJSONPString() throws JSONException {
        // given
        String jsonPString = getJSONPString();

        // when
        JSONTokener jsonTokener = new JSONTokener(jsonPString);
        jsonTokener.nextValue(); // skip the callback object
        JSONObject actual = new JSONObject(jsonTokener.nextValue().toString());

        // then
        JSONObject expected = getJSONObject();
        Assertions.assertThat(actual).usingRecursiveComparison().isEqualTo(expected);

    }

    @Test
    void test() throws JSONException {
        // given
        String jsonPString = getJSONObject().toString();

        // when
        JSONTokener jsonTokener = new JSONTokener(jsonPString);
        jsonTokener.nextValue(); // skip the callback object
        JSONObject actual = new JSONObject(jsonTokener.nextValue().toString());

        // then
        JSONObject expected = getJSONObject();
        Assertions.assertThat(actual).usingRecursiveComparison().isEqualTo(expected);

    }
}