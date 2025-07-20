package com.platform.common.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.SneakyThrows;

import java.util.List;
import java.util.Map;


/**
 * JsonUtils is a utility class for serializing and deserializing JSON data.
 * It uses the Jackson library to perform the operations.
 */
public class JsonUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.findAndRegisterModules();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    private JsonUtils() {
        // Private constructor to prevent instantiation
    }

    @SneakyThrows
    public static String serialize(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @SneakyThrows
    public static <T> T deserialize(String json, Class<T> wantedClass) {
        try {
            return objectMapper.readValue(json, wantedClass);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @SneakyThrows
    public static JsonNode deserializeToJsonNode(String json) {
        try {
            return objectMapper.readTree(json);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @SneakyThrows
    public static Map<String, Object> deserializeToMap(String json) {
        try {
            return objectMapper.readValue(json, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @SneakyThrows
    public static <T> List<T> deserializeList(String jsonList, Class<T> wantedClassAsList) {
        try {
            return objectMapper.readValue(jsonList, objectMapper.getTypeFactory().constructCollectionType(List.class, wantedClassAsList));
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @SneakyThrows
    public static List<Map<String, Object>> deserializeList(String jsonList) {
        try {
            return objectMapper.readValue(jsonList, new TypeReference<List<Map<String, Object>>>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @SneakyThrows
    public static <T> Map<String, Object> convertTomap(T value) {
        try {
            return objectMapper.convertValue(value, new TypeReference<Map<String, Object>>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void main(String[] args) {

    }

}
