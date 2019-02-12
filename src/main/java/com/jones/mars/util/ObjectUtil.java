package com.jones.mars.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by jones on 18-10-30.
 */
public class ObjectUtil {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static String toJsonSting(Object o){
        try {
            return MAPPER.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
