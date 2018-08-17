/**
 * @(#)BusinessException.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.exception;

import com.fastretailing.dcp.common.model.ResultObject;
import lombok.Getter;
import lombok.Setter;

/**
 * business exception.
 * 
 * @author Fast Retailing
 * @version $Revision$
 */
public class BusinessException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 7434740146898631357L;
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
    public BusinessException(ResultObject resultObject) {

        this.resultObject = resultObject;
    }
}
