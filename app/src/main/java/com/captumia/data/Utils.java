package com.captumia.data;

import com.fasterxml.jackson.databind.JsonNode;
import com.utils.framework.strings.Strings;

class Utils {
    public static JsonNode getFieldItem(JsonNode fields, String key) {
        JsonNode array = fields.get(key);
        if (array != null && array.size() > 0) {
            JsonNode value = array.get(0);
            if (value == null) {
                return null;
            }

            return value;
        }

        return null;
    }

    public static String getStringField(JsonNode fields, String key) {
        JsonNode node = getFieldItem(fields, key);
        if (node == null) {
            return null;
        }

        String text = node.asText();
        if (Strings.isEmpty(text)) {
            return null;
        }

        return text;
    }

    public static int getIntField(JsonNode fields, String key, int defaultValue) {
        JsonNode node = getFieldItem(fields, key);
        if (node == null) {
            return defaultValue;
        }

        if (node.isTextual()) {
            String asText = node.asText();
            if (Strings.isEmpty(asText)) {
                return defaultValue;
            }

            return Integer.valueOf(asText);
        }

        return node.asInt();
    }
}
