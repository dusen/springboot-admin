/**
 * @(#)SalesTransactionHeader.java
 *
 *                                 Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.alterationdataupload.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

/**
 * Sales transaction header.
 */
@Data
@JsonPropertyOrder({"transaction_id", "order_sub_number", "sales_transaction_id",
        "integrated_order_id", "sales_transaction_sub_number", "store_code",
        "data_creation_date_time", "data_creation_business_date", "cash_register_no", "receipt_no",
        "sales_linkage_type", "sales_transaction_type", "return_type", "system_brand_code",
        "system_business_code", "system_country_code", "channel_code", "order_status",
        "order_status", "order_substatus", "order_status_update_date",
        "order_status_last_update_date_time", "customer_id", "order_number_for_store_payment",
        "advance_received_store_code", "advance_received_store_system_brand_code",
        "advance_received_store_system_business_code", "advance_received_store_system_country_code",
        "operator_code", "original_transaction_id", "original_cash_register_no",
        "original_receipt_no", "deposit_currency_code", "deposit_value", "change_currency_code",
        "change_value", "receipt_no_for_credit_card_cancellation", "receipt_no_for_credit_card",
        "employee_sale_flag", "consistency_sales_flag", "corporate_id",
        "sales_transaction_discount_flag", "sales_transaction_discount_amount_rate_currency_code",
        "sales_transaction_discount_amount_rate"})
public class SalesTransactionHeader {

    /** Transaction id. */
    @JsonProperty("transaction_id")
    private String transactionId;

    /** Order sub number. */
    @JsonProperty("order_sub_number")
    private String orderSubNumber;

    /** Sales transaction id. */
    @JsonProperty("sales_transaction_id")
    private String salesTransactionId;

    /** Integrated order id. */
    @JsonProperty("integrated_order_id")
    private String integratedOrderId;

    /** Sales transaction sub number. */
    @JsonProperty("sales_transaction_sub_number")
    private String salesTransactionSubNumber;

    /** Store code. */
    @JsonProperty("store_code")
    private String storeCode;

    /** Data creation date time. */
    @JsonProperty("data_creation_date_time")
    private String dataCreationDateTime;

    /** Data creation business date. */
    @JsonProperty("data_creation_business_date")
    private String dataCreationBusinessDate;

    /** Cash register no. */
    @JsonProperty("cash_register_no")
    private String cashRegisterNo;

    /** Receipt no. */
    @JsonProperty("receipt_no")
    private String receiptNo;

    /** Sales linkage type. */
    @JsonProperty("sales_linkage_type")
    private String salesLinkageType;

    /** Sales transaction type. */
    @JsonProperty("sales_transaction_type")
    private String salesTransactionType;

    /** Return type. */
    @JsonProperty("return_type")
    private String returnType;

    /** System brand code. */
    @JsonProperty("system_brand_code")
    private String systemBrandCode;

    /** System business code. */
    @JsonProperty("system_business_code")
    private String systemBusinessCode;

    /** System country code. */
    @JsonProperty("system_country_code")
    private String systemCountryCode;

    /** Channel code. */
    @JsonProperty("channel_code")
    private String channelCode;

    /** Order status. */
    @JsonProperty("order_status")
    private String orderStatus;

    /** Order sub status. */
    @JsonProperty("order_substatus")
    private String orderSubStatus;

    /** Order status update date. */
    @JsonProperty("order_status_update_date")
    private String orderStatusUpdateDate;

    /** Order status last update date time. */
    @JsonProperty("order_status_last_update_date_time")
    private String orderStatusLastUpdateDateTime;

    /** Customer id. */
    @JsonProperty("customer_id")
    private String customerId;

    /** Order number for store payment. */
    @JsonProperty("order_number_for_store_payment")
    private String orderNumberForStorePayment;

    /** Advance received store code. */
    @JsonProperty("advance_received_store_code")
    private String advanceReceivedStoreCode;

    /** Advance received store system brand code. */
    @JsonProperty("advance_received_store_system_brand_code")
    private String advanceReceivedStoreSystemBrandCode;

    /** Advance received store system business code. */
    @JsonProperty("advance_received_store_system_business_code")
    private String advanceReceivedStoreSystemBusinessCode;

    /** Advance received store system country code. */
    @JsonProperty("advance_received_store_system_country_code")
    private String advanceReceivedStoreSystemCountryCode;

    /** Operator code. */
    @JsonProperty("operator_code")
    private String operatorCode;

    /** original transaction id. */
    @JsonProperty("original_transaction_id")
    private String originalTransactionId;

    /** Original cash register no. */
    @JsonProperty("original_cash_register_no")
    private String originalCashRegisterNo;

    /** original receipt no. */
    @JsonProperty("original_receipt_no")
    private String originalReceiptNo;

    /** Deposit currency code. */
    @JsonProperty("deposit_currency_code")
    private String depositCurrencyCode;

    /** Deposit value. */
    @JsonProperty("deposit_value")
    private String depositValue;

    /** Change currency code. */
    @JsonProperty("change_currency_code")
    private String changeCurrencyCode;

    /** Change value. */
    @JsonProperty("change_value")
    private String changeValue;

    /** Receipt no for credit card cancellation. */
    @JsonProperty("receipt_no_for_credit_card_cancellation")
    private String receiptNoForCreditCardCancellation;

    /** Receipt no for credit card. */
    @JsonProperty("receipt_no_for_credit_card")
    private String receiptNoForCreditCard;

    /** Employee sale flag. */
    @JsonProperty("employee_sale_flag")
    private String employeeSaleFlag;

    /** Consistency sales flag. */
    @JsonProperty("consistency_sales_flag")
    private String consistencySalesFlag;

    /** Corporate id. */
    @JsonProperty("corporate_id")
    private String corporateId;

    /** Sales transaction discount flag. */
    @JsonProperty("sales_transaction_discount_flag")
    private String salesTransactionDiscountFlag;

    /** Sales transaction discount amount rate currency code. */
    @JsonProperty("sales_transaction_discount_amount_rate_currency_code")
    private String salesTransactionDiscountAmountRateCurrencyCode;

    /** Sales transaction discount amount_rate. */
    @JsonProperty("sales_transaction_discount_amount_rate")
    private String salesTransactionDiscountAmountRate;

}
