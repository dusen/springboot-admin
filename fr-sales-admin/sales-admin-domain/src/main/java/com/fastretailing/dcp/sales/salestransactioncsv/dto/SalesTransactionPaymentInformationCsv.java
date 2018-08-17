/**
 * @(#)SalesTransactionPaymentInformationCsv.java
 *
 *                                                Copyright (c) 2018 Fast Retailing Corporation.
 */


package com.fastretailing.dcp.sales.salestransactioncsv.dto;

import java.math.BigDecimal;
import com.github.mygreen.supercsv.annotation.CsvBean;
import com.github.mygreen.supercsv.annotation.CsvColumn;
import lombok.Data;

/**
 * Sales transaction information payment.
 */
@Data
@CsvBean(header = true)
public class SalesTransactionPaymentInformationCsv {

    /** Transaction id. */
    @CsvColumn(number = 1, label = "transaction_id")
    private String transactionId;

    /** Order sub number. */
    @CsvColumn(number = 2, label = "order_sub_number")
    private Integer orderSubNumber;

    /** Sales transaction id. */
    @CsvColumn(number = 3, label = "sales_transaction_id")
    private String salesTransactionId;

    /** Tender group. */
    @CsvColumn(number = 4, label = "tender_group")
    private String tenderGroup;

    /** Tender id. */
    @CsvColumn(number = 5, label = "tender_id")
    private String tenderId;

    /** Tender sub number. */
    @CsvColumn(number = 6, label = "tender_sub_number")
    private Integer tenderSubNumber;

    /** Payment sign. */
    @CsvColumn(number = 7, label = "payment_sign")
    private String paymentSign;

    /** Tax included payment amount currency code. */
    @CsvColumn(number = 8, label = "tax_included_payment_amount_currency_code")
    private String taxIncludedPaymentAmountCurrencyCode;

    /** Tax included payment amount value. */
    @CsvColumn(number = 9, label = "tax_included_payment_amount_value")
    private BigDecimal taxIncludedPaymentAmountValue;

    /** Discount value currency code. */
    @CsvColumn(number = 10, label = "discount_value_currency_code")
    private String discountValueCurrencyCode;

    /** Discount value. */
    @CsvColumn(number = 11, label = "discount_value")
    private BigDecimal discountValue;

    /** Discount rate. */
    @CsvColumn(number = 12, label = "discount_rate")
    private BigDecimal discountRate;

    /** Discount code id corporate id. */
    @CsvColumn(number = 13, label = "discount_code_id_corporate_id")
    private String discountCodeIdCorporateId;

    /** Coupon type. */
    @CsvColumn(number = 14, label = "coupon_type")
    private String couponType;

    /** Coupon discount amount setting currency code. */
    @CsvColumn(number = 15, label = "coupon_discount_amount_setting_currency_code")
    private String couponDiscountAmountSettingCurrencyCode;

    /** Coupon discount amount setting value. */
    @CsvColumn(number = 16, label = "coupon_discount_amount_setting_value")
    private BigDecimal couponDiscountAmountSettingValue;

    /** Coupon min usage amount threshold value. */
    @CsvColumn(number = 17, label = "coupon_min_usage_amount_threshold_currency_code")
    private String couponMinUsageAmountThresholdCurrencyCode;

    /** Coupon min usage amount threshold value. */
    @CsvColumn(number = 18, label = "coupon_min_usage_amount_threshold_value")
    private BigDecimal couponMinUsageAmountThresholdValue;

    /** Coupon user id. */
    @CsvColumn(number = 19, label = "coupon_user_id")
    private String couponUserId;

    /** Card no. */
    @CsvColumn(number = 20, label = "card_no")
    private String cardNo;

    /** Credit approval code. */
    @CsvColumn(number = 21, label = "credit_approval_code")
    private String creditApprovalCode;

    /** Credit processing serial number. */
    @CsvColumn(number = 22, label = "credit_processing_serial_number")
    private String creditProcessingSerialNumber;

    /** Credit payment type. */
    @CsvColumn(number = 23, label = "credit_payment_type")
    private String creditPaymentType;

    /** Credit payment count. */
    @CsvColumn(number = 24, label = "credit_payment_count")
    private Integer creditPaymentCount;

}
