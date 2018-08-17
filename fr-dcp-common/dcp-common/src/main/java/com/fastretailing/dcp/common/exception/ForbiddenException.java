/**
 * @(#)ForbiddenException.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.exception;

import com.fastretailing.dcp.common.model.ResultObject;
import lombok.Getter;
import lombok.Setter;

/**
 * ForbiddenException.
 * 
 * @author Fast Retailing
 * @version $Revision$
 */
public class ForbiddenException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -3665065160979241730L;
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
    public ForbiddenException(ResultObject resultObject) {

        this.resultObject = resultObject;
    }

    /**
     * Constructor for specify a message.
     * <p>
     * add a message.
     * </p>
     * 
     * @param message result message
     */
    public ForbiddenException(String message) {
        super(message);
    }
}
