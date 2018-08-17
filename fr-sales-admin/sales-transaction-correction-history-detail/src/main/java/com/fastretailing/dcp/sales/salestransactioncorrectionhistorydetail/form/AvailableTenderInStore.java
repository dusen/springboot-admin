/**
 * @(#)AvailableTenderInStore.java
 *
 *                                 Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salestransactioncorrectionhistorydetail.form;

import java.time.OffsetDateTime;
import lombok.Data;

/**
 * Available tender in store.
 */
@Data
public class AvailableTenderInStore {

    /**
     * Store code.
     */
    private String storeCode;

    /**
     * Register no.
     */
    private String registerNo;

    /**
     * Receipt no.
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
    private OffsetDateTime dataCreationBusinessDate;

    /**
     * Data creation date time.
     */
    private OffsetDateTime dataCreationDateTime;

    /**
     * Order status.
     */
    private String orderStatus;

    /**
     * Order sub status.
     */
    private String orderSubStatus;

    /**
     * Oder status update date.
     */
    private OffsetDateTime orderStatusUpdateDate;

    /**
     * Order status last update date time.
     */
    private OffsetDateTime orderStatusLastUpdateDateTime;

    /**
     * Employee sale flag.
     */
    private Boolean employeeSaleFlag;

    /**
     * Corporate sale flag.
     */
    private Boolean corporateSaleFlag;

    /**
     * Corporate id.
     */
    private String corporateId;

    /**
     * Sales transaction discount flag.
     */
    private Boolean salesTransactionDiscountFlag;

    /**
     * Sales transaction discount amount rate.
     */
    private String salesTransactionDiscountAmountRate;

}
