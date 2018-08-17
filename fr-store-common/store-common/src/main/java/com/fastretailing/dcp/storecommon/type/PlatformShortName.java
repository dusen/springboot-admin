/**
 * @(#)PlatformShortName.java
 *
 *                            Copyright (c) 2018 Fast Retailing Corporation.
 */
package com.fastretailing.dcp.storecommon.type;

import lombok.Getter;

/**
 * Platform short name for debug id.
 */
public enum PlatformShortName {

    /** Price. */
    PRICE("PRC"),
    /** Sales. */
    SALES("SLS"),
    /** Store inventory. */
    STORE_INVENTORY("SIV"),
    /** Store admin. */
    STORE_ADMIN("SAD"),
    /** Employee. */
    EMPLOYEE("EMP"),
    /** Edge. */
    EDGE("EDG");

    /**
     * Platform short name.
     */
    @Getter
    private String value;

    /**
     * Constructor.
     * 
     * @param platformShortName Platform short name.
     */
    PlatformShortName(String platformShortName) {
        this.value = platformShortName;
    }

    /**
     * Check same value.
     *
     * @param value Check value.
     * @return Compare result.
     */
    public boolean is(String value) {
        return this.value.equals(value);
    }
}
