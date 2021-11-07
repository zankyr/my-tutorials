package com.rz.json.jackson;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JsonFromToStringTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private JsonNode getJsonNode() {
        JsonNode jsonNode = objectMapper.createObjectNode();

        ObjectNode objectNode = (ObjectNode) jsonNode;
        objectNode.put("name", "aGenericName");
        objectNode.put("id", 123);

        return jsonNode;
    }

    private String getJSONPString() {
        return "callback" +
                "(" +
                getJsonNode().toString() +
                ")";
    }

    @Test
    void shouldCreateJSONObjectFromString() {
        // given
        String jsonString = "{\"name\":\"aGenericName\",\"id\":123}";

        // when
        JsonNode actual = null;
        try {
            actual = objectMapper.readTree(jsonString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        // then
        JsonNode expected = getJsonNode();
        Assertions.assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void shouldPrintExpectedJSONObjectString() {
        // when
        String actual = getJsonNode().toString();

        // then
        String expected = "{\"name\":\"aGenericName\",\"id\":123}";
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Verify that Jackson can't handle a JSONP format")
    void whenParsingAJSONPString_thenThrowAJSONException() {
        // given
        String jsonPString = getJSONPString();

        // when
        Exception actual = assertThrows(JsonParseException.class, () -> objectMapper.readTree(jsonPString));


        // then
        assertTrue(actual.getMessage().startsWith("Unrecognized token 'callback': was expecting (JSON String, Number, Array, Object or token 'null', 'true' or 'false')\n"));


    }

    @Test
    void name() throws JsonProcessingException {
        // given
        String jsonPString = getJSONPString();

        // when
        JSONPObject jsonpObject = new JSONPObject("callback", jsonPString);

        // then
        System.out.println(objectMapper.writeValueAsString(jsonpObject));


    }
}