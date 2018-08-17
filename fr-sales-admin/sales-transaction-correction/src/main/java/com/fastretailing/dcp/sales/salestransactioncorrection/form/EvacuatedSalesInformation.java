/**
 * @(#)EvacuatedSalesInformation.java
 *
 *                                    Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salestransactioncorrection.form;

import lombok.Data;

/**
 * Evacuated sales information.
 */
@Data
public class EvacuatedSalesInformation {

    /**
     * Store.
     */
    private String store;

    /**
     * Register number.
     */
    private String registerNo;

    /**
     * Receipt number.
     */
    private String receiptNo;

    /**
     * Update type.
     */
    private String updateType;

    /**
     * Sales linkage type.
     */
    private String salesLinkageType;

    /**
     * Data creation business date.
     */
    private String dataCreationBusinessDate;

    /**
     * Data creation date time.
     */
    private String dataCreationDateTime;

    /**
     * Order status.
     */
    private String orderStatus;

    /**
     * Order sub status.
     */
    private String orderSubStatus;

    /**
     * Order status update date.
     */
    private String orderStatusUpdateDate;

    /**
     * Order status last update date time.
     */
    private String orderStatusLastUpdateDateTime;

    /**
     * Employee sale flag.
     */
    private String employeeSaleFlag;

    /**
     * Corporate sale flag.
     */
    private String corporateSaleFlag;

    /**
     * Corporate id.
     */
    private String corporateId;

    /**
     * Sales transaction discount flag.
     */
    private String salesTransactionDiscountFlag;

    /**
     * Sales transaction discount amount rate.
     */
    private String salesTransactionDiscountAmountRate;

}
