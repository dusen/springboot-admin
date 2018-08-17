/**
 * @(#)JsonParseToCurrencyException.java
 *
 *                                       Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.exception;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fastretailing.dcp.common.model.ResultObject;
import lombok.Getter;

/**
 * Exception class that throws if conversion to Boolean fails.
 */
public class JsonParseToCurrencyException extends JsonParseException {

    /**
     * Serial id.
     */
    private static final long serialVersionUID = 7981201987022752160L;

    /**
     * Result object with exception information.
     */
    @Getter
    private ResultObject resultObject;

    /**
     * Exception when conversion to Currency fails.
     * 
     * @param resultObject Result object with exception information.
     * @param parser Parser for loading JSON content.
     * @param message Exception message.
     */
    public JsonParseToCurrencyException(ResultObject resultObject, JsonParser parser,
            String message) {
        this(resultObject, parser, message, null, null);
    }

    /**
     * Exception when conversion to Currency fails.
     * 
     * @param resultObject Result object with exception information.
     * @param parser Parser for loading JSON content.
     * @param message Exception message.
     * @param root Exception that occurred.
     */
    public JsonParseToCurrencyException(ResultObject resultObject, JsonParser parser,
            String message, Throwable root) {
        this(resultObject, parser, message, null, root);
    }

    /**
     * Exception when conversion to Currency fails.
     * 
     * @param resultObject Result object with exception information.
     * @param parser Parser for loading JSON content.
     * @param message Exception message.
     * @param location Location information of a JSON event in an input source.
     */
    public JsonParseToCurrencyException(ResultObject resultObject, JsonParser parser,
            String message, JsonLocation location) {
        this(resultObject, parser, message, location, null);
    }

    /**
     * Exception when conversion to Currency fails.
     * 
     * @param resultObject Result object with exception information.
     * @param parser Parser for loading JSON content.
     * @param message Exception message.
     * @param location Location information of a JSON event in an input source.
     * @param root Exception that occurred.
     */
    public JsonParseToCurrencyException(ResultObject resultObject, JsonParser parser,
            String message, JsonLocation location, Throwable root) {
        super(parser, message, location, root);
        this.resultObject = resultObject;
    }

}
