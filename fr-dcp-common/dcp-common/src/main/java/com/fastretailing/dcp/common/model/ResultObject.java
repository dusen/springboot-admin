/**
 * @(#)ResultObject.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.model;

import com.fastretailing.dcp.common.constants.ErrorName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * the resultObject of exception handler.
 * @author Fast Retailing
 * @version $Revision$
 */
@Data
@NoArgsConstructor
public class ResultObject implements Serializable {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -5343161780856026927L;

    /**
     * summary of error info.
     */
    private ErrorName name;

    /**
     * message id for debug.
     */
    private String debugId;

    /**
     * error message.
     */
    private String message;

    /**
     * information link.
     */
    private String informationLink;

    /**
     * links.
     */
    private List<String> links;

    /**
     * error details.
     */
    private List<Detail> details;

    /**
     * constructor of ResultObject with params : name, debugId, message.
     */
    public ResultObject(ErrorName name, String debugId, String message) {
        this.name = name;
        this.debugId = debugId;
        this.message = message;
    }
}
