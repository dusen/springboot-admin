/**
 * @(#)SalesTransactionHistory.java
 *
 *                                  Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salestransactionhistorylist.form;

import lombok.Data;

/**
 * Sales transaction history.
 */
@Data
public class SalesTransactionHistory {

    /**
     * No.
     */
    private String no;

    /**
     * Brand code.
     */
    private String brandCode;

    /**
     * Checked 0:unchecked,1:checked.
     */
    private Integer checkFlag;

    /**
     * Sales transaction error id.
     */
    private String salesTransactionErrorId;

    /**
     * Country code.
     */
    private String countryCode;

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
     * Business date.
     */
    private String businessDate;

    /**
     * Error contents.
     */
    private String errorContents;

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
