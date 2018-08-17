/**
 * @(#)RequestHeaderObject.java
 *
 *                              Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.log;

import lombok.Data;
import org.springframework.http.HttpHeaders;
import java.io.Serializable;

/**
 * The information of request.
 *
 * @author Fast Retailing
 * @version $Revision$
 */

@Data
public class RequestHeaderObject implements Serializable {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = -5169474720432327500L;

    /**
     * Method.
     */
    private String method;

    /**
     * Request header.
     */
    private HttpHeaders httpHeaders;

    /**
     * Token.
     */
    private String token;

    /**
     * Query.
     */
    private String query;

    /**
     * Trace id.
     */
    private String traceId;

}
