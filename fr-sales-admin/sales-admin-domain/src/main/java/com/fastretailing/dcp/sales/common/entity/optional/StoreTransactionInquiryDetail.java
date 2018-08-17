/**
 * @(#)StoreTransactionInquiryDetail.java
 *
 *                                        Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.entity.optional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public final class StoreTransactionInquiryDetail {

    /**
     * Sales transaction id.
     */
    private String salesTransactionId;

    /**
     * Ims linkage date.
     */
    private String imsLinkageDate;

    /**
     * Data creation date time.
     */
    private OffsetDateTime dataCreationDateTime;

    /**
     * Store code.
     */
    private String storeCode;

    /**
     * Cash register no.
     */
    private Integer cashRegisterNo;

    /**
     * Receipt no.
     */
    private String receiptNo;

    /**
     * Operator code.
     */
    private String operatorCode;

    /**
     * Sales transaction type.
     */
    private String salesTransactionType;

    /**
     * Deposit currency code.
     */
    private String depositCurrencyCode;

    /**
     * Deposit value.
     */
    private String depositValue;

    /**
     * Change currency code.
     */
    private String changeCurrencyCode;

    /**
     * Change value.
     */
    private String changeValue;

    /**
     * Detail sub number.
     */
    private Integer detailSubNumber;


    /**
     * L3 item code.
     */
    private String l3ItemCode;

    /**
     * L3 pos product name.
     */
    private String l3PosProductName;

    /**
     * Retail unit price tax currency code.
     */
    private String retailUnitPriceTaxCurrencyCode;

    /**
     * Detail quantity.
     */
    private String detailQuantity;

    /**
     * Retail unit price tax included.
     */
    private String retailUnitPriceTax;

    /**
     * Discount type.
     */
    private String discountType;

    /**
     * Promotion type.
     */
    private String promotionType;

    /**
     *
     * Promotion no.
     *
     */
    private String promotionNo;

    /**
     * Non md code.
     */
    private String nonMdCode;

    /**
     * Taxation type.
     */
    private String taxationType;

    /**
     * Tax group.
     */
    private String taxGroup;

    /**
     * Tax amount currency code.
     */
    private String taxAmountCurrencyCode;

    /**
     * Tax amount value.
     */
    private String taxAmountValue;

    /**
     * Ims tender group.
     */
    private String imsTenderGroup;

    /**
     * Tax included payment amount currency code.
     */
    private String taxIncludedPaymentAmountCurrencyCode;

    /**
     * Discount amount payment tax currency code.
     */
    private String discountAmountTaxCurrencyCode;

    /**
     * Discount amount tax.
     */
    private String discountAmountTax;

    /**
     * Discount amount tax excluded.
     */
    private String discountAmountTaxExcluded;

    /**
     * Store discount type.
     */
    private String storeDiscountType;

    /**
     * Tender id.
     */
    private BigDecimal tenderId;

    /**
     * Tax included payment amount value.
     */
    private String taxIncludedPaymentAmountValue;

    /**
     * Non item code.
     */
    private String nonItemCode;

    /**
     * Pos item name.
     */
    private String posItemName;

}
