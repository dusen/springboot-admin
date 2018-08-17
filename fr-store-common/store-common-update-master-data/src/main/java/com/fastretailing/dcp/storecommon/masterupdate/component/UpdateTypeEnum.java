/**
 * @(#)UpdateTypeEnum.java
 *
 *                         Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.masterupdate.component;

import lombok.Getter;

/**
 * Update type.
 */
public enum UpdateTypeEnum {

    /**
     * Truncate table.
     */
    ALL("0"),
    /**
     * Separate process.
     */
    DIFFERENCE("1");

    /**
     * Get update type.
     */
    @Getter
    private String value;

    /**
     * Constructor.
     * 
     * @param value Update type.
     */
    private UpdateTypeEnum(String value) {
        this.value = value;
    }
}
