/**
 * @(#)PlatformShortName.java
 *
 *                            Copyright (c) 2018 Fast Retailing Corporation.
 */
package com.fastretailing.dcp.storecommon;

/**
 * Platform short name for debug id.
 */
public enum PlatformShortName {

    /** Price. */
    PRICE("PRC"),
    /** Sales. */
    SALES("SLS"),
    /** Store inventory. */
    STOREINVENTORY("SIV"),
    /** Store admin. */
    STOREADMIN("SAD"),
    /** Employee. */
    EMPLOYEE("EMP"),
    /** Edge. */
    EDGE("EDG");

    /**
     * Platform short name.
     */
    private String platformShortName;

    /**
     * Constructor.
     * 
     * @param platformShortName Platform short name.
     */
    PlatformShortName(String platformShortName) {
        this.platformShortName = platformShortName;
    }

    /**
     * Get Platform short name.
     * 
     * @return Platform short name.
     */
    @Override
    public String toString() {
        return this.platformShortName;
    }
}
