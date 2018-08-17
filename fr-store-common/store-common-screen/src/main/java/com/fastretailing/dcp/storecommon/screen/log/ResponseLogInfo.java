/**
 * @(#)ResponseLogInfo.java
 *
 *                          Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.log;

import java.io.Serializable;
import lombok.Data;

/**
 * Log information of response.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Data
public class ResponseLogInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Status code.
     */
    private int statusCode;

    /**
     * Response time.
     */
    private long responseTime;

    /**
     * Response.
     */
    private Object response;

    /**
     * Time.
     */
    private String time;
}
