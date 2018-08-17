/**
 * @(#)BusinessCountrySettingMasterOptionalCondition.java
 *
 *                                                        Copyright (c) 2018 Fast Retailing
 *                                                        Corporation.
 */

package com.fastretailing.dcp.sales.common.entity.optional;

import lombok.Data;

/**
 * Business country setting master optional condition.
 *
 */
@Data
public class BusinessCountrySettingMasterOptionalCondition {

    /**
     * System brand code.
     */
    private String systemBrandCode;

    /**
     * System country code.
     */
    private String systemCountryCode;

    /**
     * Store code.
     */
    private String storeCode;
}
