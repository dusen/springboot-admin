/**
 * @(#)StoreTransactionInquiryCondition.java
 *
 *                                           Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.entity.optional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

/**
 * Store transaction inquiry condition.
 */
@Data
public class StoreTransactionInquiryCondition {

    /**
     * Transaction id.
     */
    private String transactionId;

    /**
     * System brand code.
     */
    private String systemBrandCode;

    /**
     * System country code.
     */
    private String systemCountryCode;

    /**
     * Store code.
     */
    private String storeCode;

    /**
     * View store code.
     */
    private String viewStoreCode;


    /**
     * Business date.
     */
    private String businessDate;

    /**
     * Cash register no.
     */
    private Integer cashRegisterNo;

    /**
     * Data creation date time from.
     */
    private LocalDateTime dataCreationDateTimeFrom;

    /**
     * Data creation date time to.
     */
    private LocalDateTime dataCreationDateTimeTo;

    /**
     * Sales transaction type.
     */
    private String salesTransactionType;


    /**
     * Casher code.
     */
    private String casherCode;

    /**
     * Membership id.
     */
    private String membershipId;

    /**
     * Receipt no from.
     */
    private String receiptNoFrom;

    /**
     * Receipt no to.
     */
    private String receiptNoTo;

    /**
     * Payment tender group.
     */
    private String paymentTenderGroup;

    /**
     * Payment tender id.
     */
    private Integer paymentTenderId;

    /**
     * Payment amount from.
     */
    private BigDecimal paymentAmountFrom;

    /**
     * Payment amount to.
     */
    private BigDecimal paymentAmountTo;

    /**
     * Deposit amount from.
     */
    private String depositAmountFrom;

    /**
     * Deposit amount to.
     */
    private String depositAmountTo;

    /**
     * Change amount from.
     */
    private String changeAmountFrom;

    /**
     * Change amount to.
     */
    private String changeAmountTo;

    /**
     * Taxation type.
     */
    private String taxationType;

    /**
     * Non merchandise item list.
     */
    private List<String> nonMerchandiseItemList;

    /**
     * Non merchandise item.
     */
    private String nonMerchandiseItem;

    /**
     * Item code.
     */
    private String itemCode;

    /**
     * Discount type list.
     */
    private List<String> discountTypeList;

    /**
     * Discount type.
     */
    private String discountType;


}
