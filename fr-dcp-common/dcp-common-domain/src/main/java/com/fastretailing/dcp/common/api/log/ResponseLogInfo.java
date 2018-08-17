/**
 * @(#)ResponseLogInfo.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.api.log;

import lombok.Data;

import javax.annotation.Nullable;
import java.io.Serializable;

/**
 * ResponseLogInfo.
 * log information of response
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Data
public class ResponseLogInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * response header.
     */
    private String headers;

    /**
     * status code.
     */
    private int statusCode;

    /**
     * body.
     */
    @Nullable
    private String body;
}
