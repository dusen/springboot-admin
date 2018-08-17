/**
 * @(#)SalesPayoffSharedData.java
 * 
 *                     Copyright (c) 2018 Fast Retailing Corporation.
 */
package com.fastretailing.dcp.sales.common.dto;

import lombok.Data;

/**
 * Sales payoff shared data.
 * Used for both [sales payoff unmatch list] screen and [sales payoff data update] screen.
 */
@Data
public class SalesPayoffSharedData{
    /**
     * System brand code. (condition)
     */
    private String systemBrandCodeCondition;

    /**
     * System brand name. (condition)
     */
    private String systemBrandNameCondition;

    /**
     * System country code. (condition)
     */
    private String systemCountryCodeCondition;

    /**
     * System country name. (condition)
     */
    private String systemCountryNameCondition;

    /**
     * Store code. (condition)
     */
    private String storeCodeCondition;

    /**
     * Payoff date from. (condition)
     */
    private String payoffDateFrom;

    /**
     * Payoff date to. (condition)
     */
    private String payoffDateTo;

    /**
     * Cash register no. (condition)
     */
    private String cashRegisterNoCondition;

    /**
     * Error content code. (condition)
     */
    private String errorContentCodeCondition;

    /**
     * Error content name. (condition)
     */
    private String errorContentNameCondition;

    
    
    /**
     * System brand code.
     */
    private String systemBrandCode;

    /**
     * System brand name.
     */
    private String systemBrandName;

    /**
     * System country code.
     */
    private String systemCountryCode;

    /**
     * System country name.
     */
    private String systemCountryName;

    /**
     * Store code.
     */
    private String storeCode;

    /**
     * Store name.
     */
    private String storeName;

    /**
     * Payoff date.
     */
    private String payoffDate;

    /**
     * Cash register no.
     */
    private String cashRegisterNo;

    /**
     * Integrity Check Type.
     */
    private String integrityCheckType;

    /**
     * Error contents.
     */
    private String errorContents;
}
