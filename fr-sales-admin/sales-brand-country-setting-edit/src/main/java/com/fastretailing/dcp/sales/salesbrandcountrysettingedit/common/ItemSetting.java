/**
 * @(#)ItemSetting.java
 *
 *                      Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salesbrandcountrysettingedit.common;

import lombok.Getter;

/**
 * Item setting.
 *
 */
public enum ItemSetting {

    /** Linkage timing. */
    LINKAGE_TIMING("LINKAGETIMING"),
    /** Sales report. */
    SALES_REPORT("SALESREPORT"),
    /** Pay off data. */
    PAY_OFF_DATA("PAYOFFDATA"),
    /** Business date check. */
    BUSINESS_DATE_CHECK("BUSINESSDATECHECK"),
    /** Tax type. */
    TAX_TYPE("TAXTYPE"),
    /** Sales integrity check. */
    SALES_INTEGRITY_CHECK("SALESINTEGRITYCHECK"),
    /** Sales integrity check type. */
    SALES_INTEGRITY_CHECK_TYPE("SALESINTEGRITYCHECKTYPE"),
    /** Decimal. */
    DECIMAL("DECIMAL");
    /**
     * String representation of the item setting.
     */
    @Getter
    private String value;

    /**
     * Sets the string representation of the item setting.
     * 
     * @param value Item setting.
     */
    private ItemSetting(String value) {
        this.value = value;
    }
}
