/**
 * @(#)DropDownItem.java
 *
 *                       Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.storetransactioninquiry.constant;

import lombok.Data;

/**
 * Drop down item class.
 */
@Data
public class DropDownItem {
    /** Key of drop down list. */
    private String key;

    /** Value of drop down list. */
    private String value;

    /**
     * Constructor with non parameter.
     */
    public DropDownItem() {}

    /**
     * Constructor with 2 parameter.
     * 
     * @param key The value used for set to variable key.
     * @param value The value used for set to variable value.
     */
    public DropDownItem(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
