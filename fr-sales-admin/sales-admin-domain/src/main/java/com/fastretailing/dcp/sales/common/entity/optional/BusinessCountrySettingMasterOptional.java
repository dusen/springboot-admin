/**
 * @(#)BusinessCountrySettingMasterOptional.java
 *
 *                                               Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.entity.optional;

import lombok.Data;

/**
 * Business country setting master optional.
 *
 */
@Data
public class BusinessCountrySettingMasterOptional {

    /**
     * Store code.
     */
    private String storeCode;

    /**
     * System brand code.
     */
    private String systemBrandCode;

    /**
     * System country code.
     */
    private String systemCountryCode;

    /**
     * System business code.
     */
    private String systemBusinessCode;

    /**
     * State code.
     */
    private String stateCode;

    /**
     * View store code.
     */
    private String viewStoreCode;

    /**
     * Store name.
     */
    private String storeName;

    /**
     * Code level 1.
     */
    private String codeL1;

    /**
     * Code level 2.
     */
    private String codeL2;

    /**
     * Code level 3.
     */
    private String codeL3;

    /**
     * Code value.
     */
    private String codeValue;

    /**
     * Variable type.
     */
    private String variableType;

    /**
     * Display order.
     */
    private int displayOrder;
}
