/**
 * @(#)SalesTransactionErrorDetailSubForm.java
 *
 *                                             Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salestransactioncorrection.form;

import com.fastretailing.dcp.storecommon.screen.form.CommonBaseForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Sales transaction error detail sub form.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SalesTransactionErrorDetailSubForm extends CommonBaseForm {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 2329478864223000372L;

    /**
     * Sales transaction error id.
     */
    private String salesTransactionErrorId  = "1721123261801221000";

    /**
     * Transaction id.
     */
    private String transactionId;

    /**
     * Number.
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
     * Store code.
     */
    private String storeCode;

    /**
     * Store name.
     */
    private String storeName;

    /**
     * Cash register number.
     */
    private String cashRegisterNo;

    /**
     * Receipt number.
     */
    private String receiptNo;

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
     * Correction date.
     */
    private String correctionDate;

    /**
     * Transaction type.
     */
    private String transactionType;

    /**
     * Error detail.
     */
    private String errorDetail;

}
