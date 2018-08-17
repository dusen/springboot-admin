/**
 * @(#)SalesTransactionErrorDetail.java
 *
 *                                      Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.entity.optional;

import java.time.OffsetDateTime;
import lombok.Data;
import lombok.ToString;

/**
 * Sales transaction error detail.
 */
@Data
@ToString(callSuper = true)
public class SalesTransactionErrorDetail {

    /**
     * Sales transaction error id.
     */
    private String salesTransactionErrorId;

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
    private String storeCode;

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
     * Status.
     */
    private String status;

    /**
     * Data alteration status type.
     */
    private String dataAlterationStatusType;

    /**
     * Update date time.
     */
    private OffsetDateTime updateDatetime;

    /**
     * Update user id.
     */
    private String updateUserId;

    /**
     * Update program id.
     */
    private String updateProgramId;

}
