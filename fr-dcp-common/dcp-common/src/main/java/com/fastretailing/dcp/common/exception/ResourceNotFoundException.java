/**
 * @(#)ResourceNotFoundException.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.exception;


import com.fastretailing.dcp.common.model.ResultObject;
import lombok.Getter;
import lombok.Setter;

/**
 * exception of resource not found.
 * 
 * @author Fast Retailing
 * @version $Revision$
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 566787150554034963L;
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
    public ResourceNotFoundException(ResultObject resultObject) {

        this.resultObject = resultObject;
    }
}
