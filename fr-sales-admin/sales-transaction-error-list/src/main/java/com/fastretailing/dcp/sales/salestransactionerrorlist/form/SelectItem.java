/**
 * @(#)SelectItem.java
 *
 *                                          Copyright (c) 2018 Fast Retailing Corporation.
 */
package com.fastretailing.dcp.sales.salestransactionerrorlist.form;

import lombok.Data;
import lombok.NoArgsConstructor;

/** 
 * Select item.
 *
 */
@Data
@NoArgsConstructor
public class SelectItem {
    /**
     * Object's name.
     */
    private String name;
    
    /**
     * Object's value.
     */
    private String value;
}
