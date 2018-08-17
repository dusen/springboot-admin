/**
 * @(#)SalesTransactionHistoryDetailCondition.java
 *
 *                                                 Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.entity.optional;

import java.time.LocalDateTime;
import lombok.Data;

/**
 * Sales transaction history detail condition.
 */
@Data
public class SalesTransactionHistoryDetailCondition {

    /**
     * Order history type.
     */
    private Integer orderHistoryType;

    /**
     * Header history type.
     */
    private Integer headerHistoryType;

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
     * Cash register no.
     */
    private Integer cashRegisterNo;

    /**
     * Receipt no.
     */
    private String receiptNo;

    /**
     * Sales transaction type.
     */
    private String salesTransactionType;

    /**
     * Data creation date time from.
     */
    private LocalDateTime dataCreationDateTimeFrom;

    /**
     * Data creation date time to.
     */
    private LocalDateTime dataCreationDateTimeTo;

    /**
     * Order status update date from.
     */
    private String orderStatusUpdateDateFrom;

    /**
     * Order status update date to.
     */
    private String orderStatusUpdateDateTo;

    /**
     * Data alteration user id.
     */
    private String dataAlterationUserId;

    /**
     * Update date time from.
     */
    private LocalDateTime updateDatetimeFrom;

    /**
     * Update date time to.
     */
    private LocalDateTime updateDatetimeTo;

    /**
     * Error type.
     */
    private String errorType;

    /**
     * Sales linkage type.
     */
    private Integer salesLinkageType;

    /**
     * Data alteration status type.
     */
    private String dataAlterationStatusType;

}
