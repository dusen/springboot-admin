/**
 * @(#)TrueFalseToBooleanDeserializer.java
 *
 *                                         Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.deserialize;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;

/**
 * Class that converts string T / F to Boolean.
 */
public class TrueFalseToBooleanDeserializer extends CustomizeDeserializer {

    /**
     * Returns "T" which is a character string meaning true.
     * 
     * @return "T".
     */
    @Override
    public String getTrueValue() {
        return "T";
    }

    /**
     * Returns "F" which is a character string meaning false.
     * 
     * @return "F".
     */
    @Override
    public String getFalseValue() {
        return "F";
    }

    /**
     * Convert string to Boolean. It is true if it is "T", it is converted to false if it is "F". If
     * an invalid value is entered, throw an exception {@link JsonParseToBooleanException} with
     * error information.
     * 
     * @param parser Parser for loading JSON content.
     * @param context Context for the process of deserialization a single root-level value.
     * 
     * @throws IOException Thrown when some input / output exception occurs.
     * @throws JsonProcessingException Thrown when an exception occurs in JSON content processing
     *         (analysis, generation) that is not a pure I / O problem.
     * 
     * @return True for "T", false for "F".
     */
    @Override
    public Boolean deserialize(JsonParser parser, DeserializationContext context)
            throws IOException, JsonProcessingException {
        return super.deserialize(parser, context);
    }

}
