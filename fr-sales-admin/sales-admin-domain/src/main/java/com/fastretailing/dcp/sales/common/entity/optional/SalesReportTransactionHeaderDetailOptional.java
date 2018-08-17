/**
 * @(#)SalesReportTransactionHeaderDetailOptional.java
 *
 *                                                     Copyright (c) 2018 Fast Retailing
 *                                                     Corporation.
 */
package com.fastretailing.dcp.sales.common.entity.optional;

import java.time.OffsetDateTime;
import lombok.Data;

@Data
public class SalesReportTransactionHeaderDetailOptional {

    /** Sales transaction id. */
    private String salesTransactionId;

    /** Store code. */
    private String storeCode;

    /** Sales transaction type. */
    private String salesTransactionType;

    /** System brand code. */
    private String systemBrandCode;

    /** System business code. */
    private String systemBusinessCode;

    /** System country code. */
    private String systemCountryCode;

    /** Order status last update date time. */
    private OffsetDateTime orderStatusLastUpdateDateTime;

    /** Order status last update date time. */
    private String orderNumberForStorePayment;

    /** Original transaction id. */
    private String originalTransactionId;

    /** Ims linkage date. */
    private String imsLinkageDate;

    /** Department summary flag. */
    private boolean departmentSummaryFlag;

    /** Sales transaction type flag. */
    private String salesTransactionTypeFlag;

    /** Create user id. */
    private String createUserId;

    /** Create datetime. */
    private OffsetDateTime createDatetime;

    /** Create program id. */
    private String createProgramId;

    /** Update user id. */
    private String updateUserId;

    /** Update datetime. */
    private OffsetDateTime updateDatetime;

    /** Update program id. */
    private String updateProgramId;
}
