package com.rz.json.gson;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JsonFromToStringTest {
    private JsonObject getJsonObject() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", "aGenericName");
        jsonObject.addProperty("id", 123);

        return jsonObject;
    }

    private String getJSONPString() {
        return "callback" +
                "(" +
                getJsonObject() +
                ")";
    }

    @Test
    void shouldCreateJSONObjectFromString() {
        // given
        String jsonString = "{\"name\":\"aGenericName\",\"id\":123}";

        // when
        JsonObject actual = JsonParser.parseString(jsonString).getAsJsonObject();

        // then
        assertTrue(actual.isJsonObject());
        assertEquals("aGenericName", actual.get("name").getAsString());
        assertEquals(123, actual.get("id").getAsInt());
    }

    @Test
    void shouldPrintExpectedJSONObjectString() {
        // when
        String actual = getJsonObject().toString();

        // then
        String expected = "{\"name\":\"aGenericName\",\"id\":123}";
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Verify that Gson can't handle a JSONP format")
    void whenParsingAJSONPString_thenThrowAJSONException() {
        // given
        String jsonPString = getJSONPString();

        // when
        Exception actual = assertThrows(JsonSyntaxException.class, () ->  JsonParser.parseString(jsonPString));


        // then
        assertTrue(actual.getMessage().startsWith("com.google.gson.stream.MalformedJsonException: Use JsonReader.setLenient(true) to accept malformed JSON"));



    }
}