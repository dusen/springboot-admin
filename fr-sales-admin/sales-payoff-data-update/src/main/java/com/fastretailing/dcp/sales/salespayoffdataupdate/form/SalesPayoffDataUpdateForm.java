/**
 * @(#)SalesPayoffDataUpdateForm.java
 *
 *                                    Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salespayoffdataupdate.form;

import lombok.Data;

/**
 * Sales payoff data update form.
 */
@Data
public class SalesPayoffDataUpdateForm {

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

    /**
     * Payoff date.
     */
    private String payoffDate;

    /**
     * Cash register number.
     */
    private String cashRegisterNumber;

    /**
     * Integrity Check Type.
     */
    private String integrityCheckType;

    /**
     * Brand name.
     */
    private String brandName;

    /**
     * Country name.
     */
    private String countryName;

    /**
     * Store name.
     */
    private String storeName;

    /**
     * Error content.
     */
    private String errorContent;
}
