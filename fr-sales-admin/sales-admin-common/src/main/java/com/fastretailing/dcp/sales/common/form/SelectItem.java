/**
 * @(#)SelectItem.java
 *
 *                     Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.form;

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
     * Select item name.
     */
    private String name;

    /**
     * Select item value.
     */
    private String value;
}
