/**
 * @(#)OneZeroToBooleanDeserializer.java
 *
 *                                       Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.deserialize;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;

/**
 * Class that converts string 1 / 0 to Boolean.
 */
public class OneZeroToBooleanDeserializer extends CustomizeDeserializer {

    /**
     * Returns "1" which is a character string meaning true.
     * 
     * @return "1".
     */
    @Override
    public String getTrueValue() {
        return "1";
    }

    /**
     * Returns "0" which is a character string meaning false.
     * 
     * @return "0".
     */
    @Override
    public String getFalseValue() {
        return "0";
    }

    /**
     * Convert string to Boolean. It is true if it is "1", it is converted to false if it is "0". If
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
     * @return True for "1", false for "0".
     */
    @Override
    public Boolean deserialize(JsonParser parser, DeserializationContext context)
            throws IOException, JsonProcessingException {
        return super.deserialize(parser, context);
    }

}
