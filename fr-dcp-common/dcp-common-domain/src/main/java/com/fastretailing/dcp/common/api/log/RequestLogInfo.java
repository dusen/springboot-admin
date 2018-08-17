/**
 * @(#)RequestLogInfo.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.api.log;

import java.io.Serializable;

import lombok.Data;

/**
 * RequestLogInfo.
 * log information of request
 *
 * @author Fast Retailing
 * @version $Revision$
 */

@Data
public class RequestLogInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * request info.
     */
    private RequestInfo req = new RequestInfo();

    /**
     * request id.
     */
    private String requestId;
}
