/**
 * @(#)SalesPayoffUnmatchOptionalEntity.java
 *
 *                                           Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.entity.optional;

import java.time.OffsetDateTime;
import lombok.Data;

/**
 * Sales payoff unmatch detail.
 */
@Data
public class SalesPayoffUnmatchOptionalEntity {

    /**
     * System brand code.
     */
    private String systemBrandCode;

    /**
     * System brand code.
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
     * store code.
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
     * Payoff date.
     */
    private String payoffDate;

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
     * Integrity check type.
     */
    private String integrityCheckType;

    /**
     * Data alteration status name.
     */
    private String dataAlterationStatusName;

    /**
     * Error contents.
     */
    private String errorContents;

    /**
     * Update type.
     */
    private String updateType;

    /**
     * History type.
     */
    private Integer historyType;

}
