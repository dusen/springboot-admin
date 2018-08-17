/**
 * @(#)OpenLogOptional.java
 *
 *                          Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.entity.optional;

import java.time.OffsetDateTime;
import lombok.Data;

/**
 * Open log optional.
 */
@Data
public class OpenLogOptional {

    /**
     * Store code.
     */
    private String storeCode;

    /**
     * Business date.
     */
    private String businessDate;

    /**
     * Cash register no.
     */
    private Integer cashRegisterNo;

    /**
     * Payoff flag.
     */
    private boolean payoffFlag;

    /**
     * System brand code.
     */
    private String systemBrandCode;

    /**
     * System business code.
     */
    private String systemBusinessCode;

    /**
     * System country code.
     */
    private String systemCountryCode;

    /**
     * Create user id.
     */
    private String createUserId;

    /**
     * Create datetime.
     */
    private OffsetDateTime createDatetime;

    /**
     * Create program id.
     */
    private String createProgramId;

    /**
     * Update user id.
     */
    private String updateUserId;

    /**
     * Update datetime.
     */
    private OffsetDateTime updateDatetime;

    /**
     * Update program id.
     */
    private String updateProgramId;
}
