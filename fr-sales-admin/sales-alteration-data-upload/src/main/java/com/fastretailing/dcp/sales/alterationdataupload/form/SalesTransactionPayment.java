/**
 * @(#)SalesTransactionPayment.java
 *
 *                                  Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.alterationdataupload.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

/**
 * Sales transaction payment.
 */
@Data
@JsonPropertyOrder({"transaction_id", "order_sub_number", "sales_transaction_id", "tender_group",
        "sales_transaction_sub_number", "tender_id", "tender_sub_number", "payment_sign",
        "tax_included_payment_amount_currency_code", "tax_included_payment_amount_value",
        "discount_value_currency_code", "discount_value", "discount_rate",
        "discount_code_id_corporate_id", "coupon_type",
        "coupon_discount_amount_setting_currency_code", "coupon_discount_amount_setting_value",
        "coupon_min_usage_amount_threshold_currency_code",
        "coupon_min_usage_amount_threshold_value", "coupon_user_id", "card_no",
        "credit_approval_code", "credit_processing_serial_number", "credit_payment_type",
        "credit_payment_count"})
public class SalesTransactionPayment {

    /** Transaction id. */
    @JsonProperty("transaction_id")
    private String transactionId;

    /** Order sub number. */
    @JsonProperty("order_sub_number")
    private String orderSubNumber;

    /** Sales transaction id. */
    @JsonProperty("sales_transaction_id")
    private String salesTransactionId;

    /** Tender group. */
    @JsonProperty("tender_group")
    private String tenderGroup;

    /** Tender id. */
    @JsonProperty("tender_id")
    private String tenderId;

    /** Tender sub number. */
    @JsonProperty("tender_sub_number")
    private String tenderSubNumber;

    /** Payment sign. */
    @JsonProperty("payment_sign")
    private String paymentSign;

    /** Tax included payment amount currency code. */
    @JsonProperty("tax_included_payment_amount_currency_code")
    private String taxIncludedPaymentAmountCurrencyCode;

    /** Tax included payment amount value. */
    @JsonProperty("tax_included_payment_amount_value")
    private String taxIncludedPaymentAmountValue;

    /** Discount value currency code. */
    @JsonProperty("discount_value_currency_code")
    private String discountValueCurrencyCode;

    /** Discount value. */
    @JsonProperty("discount_value")
    private String discountValue;

    /** Discount rate. */
    @JsonProperty("discount_rate")
    private String discountRate;

    /** Discount code id corporate id. */
    @JsonProperty("discount_code_id_corporate_id")
    private String discountCodeIdCorporateId;

    /** Coupon type. */
    @JsonProperty("coupon_type")
    private String couponType;

    /** Coupon discount amount setting currency code. */
    @JsonProperty("coupon_discount_amount_setting_currency_code")
    private String couponDiscountAmountSettingCurrencyCode;

    /** Coupon discount amount setting value. */
    @JsonProperty("coupon_discount_amount_setting_value")
    private String couponDiscountAmountSettingValue;

    /** Coupon min usage amount threshold currency code. */
    @JsonProperty("coupon_min_usage_amount_threshold_currency_code")
    private String couponMinUsageAmountThresholdCurrencyCode;

    /** Coupon min usage amount threshold value. */
    @JsonProperty("coupon_min_usage_amount_threshold_value")
    private String couponMinUsageAmountThresholdValue;

    /** Coupon user id. */
    @JsonProperty("coupon_user_id")
    private String couponUserId;

    /** Card no. */
    @JsonProperty("card_no")
    private String cardNo;

    /** Credit approval code. */
    @JsonProperty("credit_approval_code")
    private String creditApprovalCode;

    /** Credit processing serial number. */
    @JsonProperty("credit_processing_serial_number")
    private String creditProcessingSerialNumber;

    /** Credit payment type. */
    @JsonProperty("credit_payment_type")
    private String creditPaymentType;

    /** Credit payment count. */
    @JsonProperty("credit_payment_count")
    private String creditPaymentCount;

}
