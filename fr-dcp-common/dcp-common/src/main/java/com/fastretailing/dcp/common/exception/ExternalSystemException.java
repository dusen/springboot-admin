/**
 * @(#)ExternalSystemException.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.exception;

import com.fastretailing.dcp.common.model.ResultObject;
import lombok.Getter;
import lombok.Setter;

/**
 * external system exception.
 * 
 * @author Fast Retailing
 * @version $Revision$
 */
public class ExternalSystemException extends RuntimeException {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 5338043392252369122L;

    /**
     * error json object.
     */
    @Getter
    @Setter
    private ResultObject resultObject;

    /**
     * Constructor with error json object.
     *
     * @param resultObject error json object
     */
    public ExternalSystemException(ResultObject resultObject) {

        this.resultObject = resultObject;
    }
}
