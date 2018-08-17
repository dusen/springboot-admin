/**
 * @(#)MdcKey.java
 *
 *                 Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.log;

import lombok.Getter;

/**
 * MDC key's enum.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
public enum MdcKey {

    /**
     * Amazon's trace-id.
     */
    AMAZON_TRACE_ID("X-Amzn-Trace-Id"),

    /**
     * Host name.
     */
    HOST_NAME("hostname"),

    /**
     * Request id.
     */
    REQUEST_ID("request_id"),

    /**
     * Attribute service.
     */
    ATTRIBUTE_SERVICE("service"),

    /**
     * User id.
     */
    USER_ID("user_id");

    /**
     * Key.
     */
    @Getter
    private String key;

    /**
     * Constructor.
     * 
     * @param key Key.
     */
    MdcKey(String key) {
        this.key = key;
    }

    /**
     * Returns the key.
     * 
     * @return key
     */
    @Override
    public String toString() {
        return this.key;
    }

}
