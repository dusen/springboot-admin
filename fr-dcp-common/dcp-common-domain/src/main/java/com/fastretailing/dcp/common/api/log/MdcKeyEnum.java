/**
 * @(#)MdcKeyEnum.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.api.log;

import lombok.Getter;

/**
 * MDC key's enum.<br>
 *
 * @author Fast Retailing
 * @version $Revision$
 */
public enum MdcKeyEnum {

    /**
     * Amazon's trace-id.<br>
     */
    AMAZON_TRACE_ID("X-Amzn-Trace-Id"),

    /**
     * Host name.<br>
     */
    HOST_NAME("hostname"),

    /**
     * Request id.<br>
     */
    REQUEST_ID("request_id"),

    /**
     * Attribute service.<br>
     */
    ATTRIBUTE_SERVICE("service"),

    /**
     * Task-Seq (X-Edf-Task-Seq).<br>
     */
    TASK_SEQ("Task-Seq"),

    /**
     * endpoint.<br>
     */
    ENDPOINT("endpoint"),

    /**
     * member_id (X-FR-front-memberid).<br>
     */
    MEMBER_ID("member_id"),

    /**
     * user_id (X-FR-admin-userid).<br>
     */
    USER_ID("user_id");

    /**
     * Key.
     */
    @Getter
    private String key;

    /**
     * Constructor.
     * @param key key
     */
    MdcKeyEnum(String key) {
        this.key = key;
    }

    /**
     * Returns the key.
     * @return key
     */
    @Override
    public String toString() {
        return this.key;
    }

}
