package com.rz.json.org.json;

import org.assertj.core.api.Assertions;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.print.attribute.standard.MediaSize;

import static org.junit.jupiter.api.Assertions.*;

class JsonFromToStringTest {

    private JSONObject getJSONObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "aGenericName");
        jsonObject.put("id", 123);
        return jsonObject;
    }

    private String getJSONPString() {
        return "callback" +
                "(" +
                getJSONObject().toString() +
                ")";
    }

    @Test
    void shouldCreateJSONObjectFromString() {
        // given
        String jsonString = "{\"name\":\"aGenericName\",\"id\":123}";

        // when
        JSONObject actual = new JSONObject(jsonString);

        // then
        JSONObject expected = getJSONObject();
        Assertions.assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void shouldPrintExpectedJSONObjectString() {
        // when
        String actual = getJSONObject().toString();

        // then
        String expected = "{\"name\":\"aGenericName\",\"id\":123}";
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Verify that org.json can't handle a JSONP format")
    void whenParsingAJSONPString_thenThrowAJSONException() {
        // given
        String jsonPString = getJSONPString();

        // when
        Exception actual = assertThrows(JSONException.class, () -> new JSONObject(jsonPString));

        // then
        assertTrue(actual.getMessage().startsWith("A JSONObject text must begin with '{' at 1"));

    }

    @Test
    void shouldParseAJSONPString() {
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
}