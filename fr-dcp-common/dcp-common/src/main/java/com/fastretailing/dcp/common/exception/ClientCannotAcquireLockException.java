/**
 * @(#)ClientCannotAcquireLockException.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.exception;

import org.springframework.dao.CannotAcquireLockException;
import com.fastretailing.dcp.common.model.ResultObject;
import lombok.Getter;
import lombok.Setter;

/**
 * Acquire lock failure exception.
 * 
 * @author Fast Retailing
 * @version $Revision$
 */
public class ClientCannotAcquireLockException extends CannotAcquireLockException {

    /**
     *
     */
    private static final long serialVersionUID = -6902885474444440022L;
    /**
     * error json object.
     */
    @Getter
    @Setter
    private ResultObject resultObject;

    /**
     * Constructor for ClientCannotAcquireLockException.
     * 
     * @param resultObject ResultObject
     * @param cause the root cause from the data access API in use
     */
    public ClientCannotAcquireLockException(ResultObject resultObject, Throwable cause) {

        super(resultObject.getMessage(), cause);
        this.resultObject = resultObject;
    }
}
