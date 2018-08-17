/**
 * @(#)JsonUtility.java
 * 
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * provides functionality for reading and writing JSON.
 * 
 * @author Fast Retailing
 * @version $Revision$
 */
@Slf4j
public class JsonUtility {

    /**
     * The ObjectMapper instance.
     */
    private static ObjectMapper mapper;
    
    static {
        mapper = new ObjectMapper().findAndRegisterModules();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    /**
     * Serialize any Java value as a JSON String.
     * 
     * @param value any java value
     * @return The JSON content string
     * @throws JsonProcessingException any exception encountered when processing (parsing,
     *         generating) JSON content
     */
    public static String toJson(Object value) throws JsonProcessingException {
        try {
            return mapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            // shouldn't really happen,but is declared as possibility so
            log.error("Failed to serialize Java value[{}] as a JSON String.", value);
            log.error("An exception occurred.", e);
            throw e;
        }
    }

    /**
     * Deserialize JSON content from given JSON content String.
     * @param content The JSON content string
     * @param valueType the target java type
     * @return the java object
     */
    public static <T> T toObject(String content, Class<T> valueType) throws IOException {
        try {
            return mapper.readValue(content, valueType);
        } catch (IOException e) {
            log.error("Can not deserialize JSON content from given JSON content String");
            throw e;
        }
    }
}
