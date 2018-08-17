/**
 * @(#)SalesTransactionHistoryDetail.java
 *
 *                                        Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.entity.optional;

import java.time.OffsetDateTime;
import lombok.Data;
import lombok.ToString;

/**
 * Sales transaction history detail.
 */
@Data
@ToString(callSuper = true)
public class SalesTransactionHistoryDetail {

    /**
     * System brand code.
     */
    private String systemBrandCode;

    /**
     * System country code.
     */
    private String systemCountryCode;

    /**
     * View store code.
     */
    private String viewStoreCode;

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
     * Data creation date time.
     */
    private OffsetDateTime dataCreationDateTime;

    /**
     * Order status update date.
     */
    private String orderStatusUpdateDate;

    /**
     * Error type.
     */
    private String errorType;

    /**
     * Data alteration user id.
     */
    private String dataAlterationUserId;

    /**
     * Update date time.
     */
    private OffsetDateTime updateDateTime;

    /**
     * Data alteration sales linkage type.
     */
    private Integer dataAlterationSalesLinkageType;

    /**
     * Data alteration backbone linkage type.
     */
    private Integer dataAlterationBackboneLinkageType;

    /**
     * Update type.
     */
    private String updateType;

    /**
     * History type.
     */
    private Integer historyType;

    /**
     * Sales transaction error id.
     */
    private String salesTransactionErrorId;

}
