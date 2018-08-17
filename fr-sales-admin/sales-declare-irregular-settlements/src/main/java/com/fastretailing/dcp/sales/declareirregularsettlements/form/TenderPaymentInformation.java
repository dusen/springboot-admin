/**
 * @(#)TenderPaymentInformation.java
 *
 *                                   Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.declareirregularsettlements.form;

import lombok.Data;

/**
 * Tender payment information.
 *
 */
@Data
public class TenderPaymentInformation {

    /**
     * Tender group.
     */
    private String tenderGroup;

    /**
     * Tender id.
     */
    private String tenderId;

    /**
     * Value.
     */
    private String value;

    /**
     * Currency code.
     */
    private String currencyCode;
}
