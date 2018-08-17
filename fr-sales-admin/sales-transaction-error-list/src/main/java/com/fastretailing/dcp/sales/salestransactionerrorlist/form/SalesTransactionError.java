/**
 * @(#)SalesTransactionError.java
 *
 *                                Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salestransactionerrorlist.form;

import lombok.Data;

/**
 * Sales transaction error.
 */
@Data
public class SalesTransactionError {

    /**
     * Sales transaction error id.
     */
    private String salesTransactionErrorId;

    /**
     * Checked 0:unchecked,1:checked.
     */
    private Integer checkFlag;

    /**
     * No.
     */
    private String no;

    /**
     * Brand code.
     */
    private String brandCode;

    /**
     * Country code.
     */
    private String countryCode;

    /**
     * Real Store code.
     */
    private String realStoreCode;

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
     * Status.
     */
    private String status;

}
