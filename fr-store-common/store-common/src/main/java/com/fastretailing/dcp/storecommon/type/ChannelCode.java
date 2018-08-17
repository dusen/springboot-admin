/**
 * @(#)ChannelCode.java
 *
 *                      Copyright (c) 2018 Fast Retailing Corporation.
 */
package com.fastretailing.dcp.storecommon.type;

import lombok.Getter;

/**
 * Channel code.
 */
public enum ChannelCode {

    /** Personal computer. */
    PC("PC"),
    /** Smart phone. */
    SMART_PHONE("SP"),
    /** FeaturePhone. */
    FEATURE_PHONE("FP"),
    /** General sustitute order. */
    GENRERAL_SUSTITUTE_ORDER("COMMON"),
    /** Customized order. */
    CUSTOMIZED_ORDER("CUSTOMIZE"),
    /** Corporate order. */
    CORPORATE_ORDER("CORPORATE"),
    /** POS. */
    POS("POS"),
    /** Mobile POS. */
    MOBILE_POS("MOBILEPOS");

    /**
     * Message type.
     */
    @Getter
    private String value;

    /**
     * Constructor.
     * 
     * @param value Channel code.
     */
    ChannelCode(String value) {
        this.value = value;
    }
}
