/**
 * @(#)SalesTransactionErrorDetailCondition.java
 *
 *                                               Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.entity.optional;

import java.time.LocalDateTime;
import lombok.Data;

/**
 * Sales transaction error detail condition.
 */
@Data
public class SalesTransactionErrorDetailCondition {

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
     * Business date from.
     */
    private String businessDateFrom;

    /**
     * Business date to.
     */
    private String businessDateTo;

    /**
     * Error type.
     */
    private String errorType;

}
