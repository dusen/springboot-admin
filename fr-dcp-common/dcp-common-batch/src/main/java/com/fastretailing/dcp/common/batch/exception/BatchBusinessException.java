/**
 * @(#)BatchBusinessException.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.batch.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.batch.core.ExitStatus;

/**
 * Exception to inform you that it has detected a violation of business rules.<br>
 *
 * @author Fast Retailing
 * @version $Revision$
 */
public class BatchBusinessException extends RuntimeException {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -2043190094594685232L;

    /**
     * exit Code.
     */
    @Getter
    @Setter
    private ExitStatus exitStatus = null;

    /**
     * Constructs a new business exception.
     */
    public BatchBusinessException() {
        super();
    }

    /**
     * Constructs a new business exception with the specified detail message.
     *
     * @param message the detail message
     */
    public BatchBusinessException(String message) {
        super(message);
    }

    /**
     * Constructs a new business exception with the specified detail message and job exit code.
     *
     * @param message the detail message
     * @param exitStatus the exit Code
     */
    public BatchBusinessException(String message, ExitStatus exitStatus) {
        super(message);
        this.exitStatus = exitStatus;
    }
}
