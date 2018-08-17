/**
 * @(#)SalesPayoffUnmatch.java
 *
 *                             Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salespayoffunmatchlist.form;

import lombok.Data;

/**
 * Sales payoff unmatch.
 */
@Data
public class SalesPayoffUnmatch {

    /**
     * No.
     */
    private String no;

    /**
     * Brand code.
     */
    private String brandCode;

    /**
     * Brand name.
     */
    private String brandName;

    /**
     * Country code.
     */
    private String countryCode;

    /**
     * Country name.
     */
    private String countryName;

    /**
     * Store code. (view)
     */
    private String viewStoreCode;

    /**
     * Store code.
     */
    private String storeCode;

    /**
     * Store name.
     */
    private String storeName;

    /**
     * Cash register no.
     */
    private String cashRegisterNo;

    /**
     * Receipt no.
     */
    private String receiptNo;

    /**
     * Sales transaction type.
     */
    private String salesTransactionType;

    /**
     * Data creation date.
     */
    private String dataCreationDate;

    /**
     * Payoff date.
     */
    private String payoffDate;

    /**
     * Error contents.
     */
    private String errorContents;

    /**
     * data alteration status name.
     */
    private String dataAlterationStatusName;

    /**
     * Corrector code.
     */
    private String correctorCode;

    /**
     * Correction date.
     */
    private String correctionDate;

    /**
     * Reflect.
     */
    private String reflect;

    /**
     * Update type.
     */
    private String updateType;
}
