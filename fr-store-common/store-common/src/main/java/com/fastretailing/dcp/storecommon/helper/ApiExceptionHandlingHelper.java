/**
 * @(#)ApiExceptionHandlingHelper.java
 *
 *                                     Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.helper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import com.fastretailing.dcp.common.model.ResultObject;
import com.fastretailing.dcp.storecommon.exception.JsonParseToBooleanException;
import lombok.extern.slf4j.Slf4j;

/**
 * Helper class when an exception occurs in the API.
 */
@Slf4j
public class ApiExceptionHandlingHelper {

    /**
     * When an exception occurs in the return from a character string to Boolean, response
     * information is acquired from an exception and returned.
     * 
     * @param e Exception thrown when converting from string to Boolean.
     * 
     * @return Response object with exception information.
     */
    public static ResponseEntity<ResultObject> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException e) {

        log.error("Invoking @HttpMessageNotReadableException method.", e);

        if (e.contains(JsonParseToBooleanException.class)) {
            JsonParseToBooleanException targetException =
                    JsonParseToBooleanException.class.cast(e.getRootCause());
            return new ResponseEntity<>(targetException.getResultObject(), HttpStatus.BAD_REQUEST);
        } else {
            throw e;
        }

    }

}
