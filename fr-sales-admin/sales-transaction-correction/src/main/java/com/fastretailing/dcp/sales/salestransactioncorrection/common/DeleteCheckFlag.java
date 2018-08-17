/**
 * @(#)DeleteCheckFlag.java
 *
 *                          Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salestransactioncorrection.common;

import org.apache.commons.lang3.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Delete check flag.
 */
@AllArgsConstructor
public enum DeleteCheckFlag {

    /** Normal. */
    NORMAL("0"),
    /** Deleted. */
    DELETED("1");

    /**
     * Delete check flag.
     */
    @Getter
    private String value;

    /**
     * Check same value.
     *
     * @param value Delete check flag.
     * @return Compare result.
     */
    public boolean is(String value) {
        if (StringUtils.isEmpty(value)) {
            return false;
        }
        return value.equals(this.value);
    }

}
