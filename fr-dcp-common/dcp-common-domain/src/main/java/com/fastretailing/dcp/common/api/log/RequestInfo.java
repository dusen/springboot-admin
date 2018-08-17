/**
 * @(#)RequestInfo.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.api.log;

import lombok.Data;

import javax.annotation.Nullable;
import java.io.Serializable;

/**
 * RequestInfo.
 * the information of request
 *
 * @author Fast Retailing
 * @version $Revision$
 */

@Data
public class RequestInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * method.
     */
    private String method;

    /**
     * request header.
     */
    private String headers;

    /**
     * query.
     */
    @Nullable
    private String query;

    /**
     * trace id.
     */
    private String traceId;

    /**
     * body.
     */
    private String body;

    /**
     * url.
     */
    private String url;

    /**
     * originalUrl.
     */
    private String originalUrl;

    /**
     * httpVersion.
     */
    private String httpVersion;
}
