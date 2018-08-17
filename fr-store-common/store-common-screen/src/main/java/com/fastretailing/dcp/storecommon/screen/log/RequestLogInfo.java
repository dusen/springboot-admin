/**
 * @(#)RequestLogInfo.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.log;

import java.io.Serializable;

import lombok.Data;

/**
 * Log information of request.
 *
 * @author Fast Retailing
 * @version $Revision$
 */

@Data
public class RequestLogInfo implements Serializable {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 7390813050621093452L;

    /**
     * Request info.
     */
    private RequestHeaderObject requestInfo;

    /**
     * Request id.
     */
    private String requestId;
}
