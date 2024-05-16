package ru.oxymo.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T readValueFromString(String contentString, Class<T> classType) throws JsonProcessingException {
        return objectMapper.readValue(contentString, classType);
    }

    public static <T> T readValueFromString(String contentString, TypeReference<T> typeReference) throws JsonProcessingException {
        return objectMapper.readValue(contentString, typeReference);
    }

    public static <T> String writeValueToString(T object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    public static <T> String writeValueToFormattedString(T object) throws JsonProcessingException {
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
    }
}