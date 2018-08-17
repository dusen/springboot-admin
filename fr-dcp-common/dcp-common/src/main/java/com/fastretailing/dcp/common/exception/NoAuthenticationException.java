/**
 * @(#)NoAuthenticationException.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.exception;

import com.fastretailing.dcp.common.model.ResultObject;
import lombok.Getter;
import lombok.Setter;

/**
 * no authentication exception.
 * 
 * @author Fast Retailing
 * @version $Revision$
 */
public class NoAuthenticationException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 8054820293661426544L;
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
    public NoAuthenticationException(ResultObject resultObject) {

        this.resultObject = resultObject;
    }
}
