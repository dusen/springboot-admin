/**
 * @(#)ExclusionException.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.exception;

import com.fastretailing.dcp.common.model.ResultObject;
import lombok.Getter;
import lombok.Setter;

/**
 * ExclusionException.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
public class ExclusionException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -7749330328058101961L;

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
    public ExclusionException(ResultObject resultObject) {

        this.resultObject = resultObject;
    }
}
