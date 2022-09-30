package org.goravski.restaurantVoting.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.goravski.restaurantVoting.json.RestaurantVotingDataObjectMapper.getMapper;


public class JsonUtil {

    public static <T> List<T> readValues(String json, Class<T> clazz) {
        ObjectReader reader = getMapper().readerFor(clazz);
        try {
            return reader.<T>readValues(json).readAll();
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid read array from JSON:\n'" + json + "'", e);
        }
    }

    public static <T> T readValue(String json, Class<T> clazz) {
        try {
            return getMapper().readValue(json, clazz);
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid read from JSON:\n'" + json + "'", e);
        }
    }

    public static <T> String writeValue(T obj) {
        try {
            return getMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Invalid write to JSON:\n'" + obj + "'", e);
        }
    }

    public static <T> String writeFromObjectIgnoreFields(T obj, String... ignoreFields) {
        try {
            Map<String, Object> map = getMapper().convertValue(obj, new TypeReference<Map<String, Object>>() {
            });
            Stream.of(ignoreFields).forEach(map::remove);
            return getMapper().writeValueAsString(map);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("invalid write ti JSON: \n'" + obj + "'", e);
        }
    }

    public static <T> String writeFromJsonIgnoreFields(String json, Class<T> clazz, String... ignoreFields) {
        List <String> newJson = new ArrayList<>();
        for (T obj : readValues(json, clazz)){
            newJson.add(writeFromObjectIgnoreFields(obj, ignoreFields));
        }
        return newJson.toString().replaceAll(" ", "");
    }

    public static <T> String writeFromSomeObjectsIgnoreFields(List <T> objs, String... ignoreFields) {
        List <String> objJson = new ArrayList<>();
        for (T obj : objs){
            objJson.add(writeFromObjectIgnoreFields(obj, ignoreFields));
        }
        return objJson.toString().replaceAll(" ", "");
    }
}