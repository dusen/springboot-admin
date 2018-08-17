/**
 * @(#)RestTemplateUtility.java
 *
 *                              Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.util;

import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import com.fastretailing.dcp.common.constants.ErrorName;
import com.fastretailing.dcp.common.constants.LogLevel;
import com.fastretailing.dcp.common.exception.BusinessException;
import com.fastretailing.dcp.common.exception.SystemException;
import com.fastretailing.dcp.common.model.ResultObject;
import com.fastretailing.dcp.common.util.ResultObjectUtility;
import com.fastretailing.dcp.sales.common.resttemplate.constant.MessageKey;
import com.fastretailing.dcp.sales.common.resttemplate.constant.SerialNumber;

/**
 * RestTemplate utility.
 */
public class RestTemplateUtility {

    /**
     * RestTemplate post for entity.
     * 
     * @param restTemplate RestTemplate instance.
     * @param uri Uri string.
     * @param request Request object.
     * @param responseType Post for entity response type.
     * @param parameterMap Uri variable parameter map.
     * @return ResponseEntity response generic class.
     */
    public static <T> ResponseEntity<T> postForEntity(RestTemplate restTemplate, String uri,
            Object request, Class<T> responseType, Map<String, String> parameterMap) {
        ResponseEntity<T> responseEntity =
                restTemplate.postForEntity(uri, request, responseType, parameterMap);

        return handleResponseEntity(responseEntity);
    }

    /**
     * RestTemplate get for entity.
     * 
     * @param restTemplate RestTemplate instance.
     * @param uri Uri string.
     * @param responseType Get for entity response type.
     * @param parameterMap Uri variable parameter map.
     * @return ResponseEntity response generic class.
     */
    public static <T> ResponseEntity<T> getForEntity(RestTemplate restTemplate, String uri,
            Class<T> responseType, Map<String, String> parameterMap) {
        ResponseEntity<T> responseEntity =
                restTemplate.getForEntity(uri, responseType, parameterMap);

        return handleResponseEntity(responseEntity);
    }

    /**
     * Handle response entity.
     * 
     * @param responseEntity Response entity.
     * @return ResponseEntity generic class.
     */
    private static <T> ResponseEntity<T> handleResponseEntity(ResponseEntity<T> responseEntity) {
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity;
        } else if (responseEntity.getStatusCode().is4xxClientError()) {
            final ResultObject resultObject = ResultObjectUtility.ResultObjectBuilder.getBuilder()
                    .withName(ErrorName.Business.BUSINESS_CHECK_ERROR)
                    .withDebugId(LogLevel.ERROR, SerialNumber.SERIAL_NUMBER_69900001)
                    .withMessage(MessageKey.BUSINESS_ERROR_MESSAGE_KEY)
                    .build();
            throw new BusinessException(resultObject);
        } else {
            throw new SystemException();
        }
    }
}
