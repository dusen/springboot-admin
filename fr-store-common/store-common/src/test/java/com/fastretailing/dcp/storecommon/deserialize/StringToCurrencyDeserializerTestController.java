/**
 * @(#)StringToCurrencyDeserializerTestController.java
 *
 *                                                      Copyright (c) 2018 Fast Retailing
 *                                                      Corporation.
 */

package com.fastretailing.dcp.storecommon.deserialize;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.fastretailing.dcp.common.model.ResultObject;
import com.fastretailing.dcp.storecommon.helper.ApiExceptionHandlingHelper;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller class for Boolean conversion test of character string.
 */
@RestController
@RequestMapping("/{brand}/{region}/{language}/currencytest")
@Slf4j
public class StringToCurrencyDeserializerTestController {

    /**
     * Test method for returning the response at normal time when receiving the request. At normal
     * termination, return status code 200.
     * 
     * @param booleanData Boolean data obtained by request.
     * 
     * @return Boolean data converted from a string.
     */
    @PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public CurrencyData post(@RequestBody @Validated CurrencyData currencyData) {
        log.debug("server data={}", currencyData);
        return currencyData;
    }

    /**
     * When an exception occurs in the Boolean conversion processing of the character string, an
     * error response is returned.
     * 
     * @param e Exception thrown when converting from string to Boolean.
     * 
     * @return Response object with exception information.
     */
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<ResultObject> handleException(HttpMessageNotReadableException e) {
        return ApiExceptionHandlingHelper.handleHttpMessageNotReadableException(e);
    }

}
