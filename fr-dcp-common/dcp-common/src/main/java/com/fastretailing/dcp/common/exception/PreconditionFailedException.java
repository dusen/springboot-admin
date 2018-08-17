/**
 * @(#)PreconditionFailedException.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.exception;

import com.fastretailing.dcp.common.model.ResultObject;
import lombok.Getter;
import lombok.Setter;

/**
 * PreconditionFailedException.
 * 
 * @author Fast Retailing
 * @version $Revision$
 */
public class PreconditionFailedException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -3876005866803680231L;
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
    public PreconditionFailedException(ResultObject resultObject) {

        this.resultObject = resultObject;
    }
}
