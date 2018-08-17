/**
 * @(#)DetailError.java
 *
 *                      Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.form;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Detail error information.
 *
 */
@Data
@Slf4j
public class DetailError implements Serializable {

    /** Serial version uid. */
    private static final long serialVersionUID = 596763896912178828L;
    /** Error code. */
    private String errorCode;
    /** Error field list. */
    private List<String> errorFieldList;
    /** Error message title. */
    private String errorMessageTitle;
    /** Error message. */
    private String errorMessage;
    /** Message type. */
    private String messageType;
    
    /**
     * Convert detail error data to a json string.
     * @return Json String.
     */
    public String toJsonString() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            log.error("error in convert detail error data to a json string.");
            return null;
        }
    }
}
