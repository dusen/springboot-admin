/**
 * @(#)SalesTransactionHeaderCsv.java
 *
 *                                    Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salestransactioncsv.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import com.github.mygreen.supercsv.annotation.CsvBean;
import com.github.mygreen.supercsv.annotation.CsvColumn;
import lombok.Data;

/**
 * Sales transaction header csv.
 */
@Data
@CsvBean(header = true)
public class SalesTransactionHeaderCsv {

    /** Transaction id. */
    @CsvColumn(number = 1, label = "transaction_id")
    private String transactionId;

    /** Order sub number. */
    @CsvColumn(number = 2, label = "order_sub_number")
    private Integer orderSubNumber;

    /** Sales transaction id. */
    @CsvColumn(number = 3, label = "sales_transaction_id")
    private String salesTransactionId;

    /** Integrated order id. */
    @CsvColumn(number = 4, label = "integrated_order_id")
    private String integratedOrderId;

    /** Sales Transaction sub number. */
    @CsvColumn(number = 5, label = "sales_transaction_sub_number")
    private Integer salesTransactionSubNumber;

    /** Store code. */
    @CsvColumn(number = 6, label = "store_code")
    private String storeCode;

    /** Data creation date time. */
    @CsvColumn(number = 7, label = "data_creation_date_time")
    private OffsetDateTime dataCreationDateTime;

    /** Data creation business date. */
    @CsvColumn(number = 8, label = "data_creation_business_date")
    private String dataCreationBusinessDate;

    /** Cash register no. */
    @CsvColumn(number = 9, label = "cash_register_no")
    private Integer cashRegisterNo;

    /** Receipt no. */
    @CsvColumn(number = 10, label = "receipt_no")
    private String receiptNo;

    /** Sales linkage type. */
    @CsvColumn(number = 11, label = "sales_linkage_type")
    private Integer salesLinkageType;

    /** Sales transaction type. */
    @CsvColumn(number = 12, label = "sales_transaction_type")
    private String salesTransactionType;

    /** Return type. */
    @CsvColumn(number = 13, label = "return_type")
    private Integer returnType;

    /** System brand code. */
    @CsvColumn(number = 14, label = "system_brand_code")
    private String systemBrandCode;

    /** System business code. */
    @CsvColumn(number = 15, label = "system_business_code")
    private String systemBusinessCode;

    /** System country code. */
    @CsvColumn(number = 16, label = "system_country_code")
    private String systemCountryCode;

    /** Channel code. */
    @CsvColumn(number = 17, label = "channel_code")
    private String channelCode;

    /** Order status. */
    @CsvColumn(number = 18, label = "order_status")
    private String orderStatus;

    /** Order sub status. */
    @CsvColumn(number = 19, label = "order_substatus")
    private String orderSubstatus;

    /** Order status update date. */
    @CsvColumn(number = 20, label = "order_status_update_date")
    private String orderStatusUpdateDate;

    /** Order status update date time. */
    @CsvColumn(number = 21, label = "order_status_last_update_date_time")
    private OffsetDateTime orderStatusLastUpdateDateTime;

    /** Customer id. */
    @CsvColumn(number = 22, label = "customer_id")
    private String customerId;

    /** Order number for store payment. */
    @CsvColumn(number = 23, label = "order_number_for_store_payment")
    private String orderNumberForStorePayment;

    /** Advance received store system brand code. */
    @CsvColumn(number = 24, label = "advance_received_store_code")
    private String advanceReceivedStoreCode;

    /** Advance received store system brand code. */
    @CsvColumn(number = 25, label = "advance_received_store_system_brand_code")
    private String advanceReceivedStoreSystemBrandCode;

    /** Advance received store system business code. */
    @CsvColumn(number = 26, label = "advance_received_store_system_business_code")
    private String advanceReceivedStoreSystemBusinessCode;

    /** Advance received store system country code. */
    @CsvColumn(number = 27, label = "advance_received_store_system_country_code")
    private String advanceReceivedStoreSystemCountryCode;

    /** Operator code. */
    @CsvColumn(number = 28, label = "operator_code")
    private String operatorCode;

    /** Original transaction id. */
    @CsvColumn(number = 29, label = "original_transaction_id")
    private String originalTransactionId;

    /** originalCashRegisterNo. */
    @CsvColumn(number = 30, label = "original_cash_register_no")
    private Integer originalCashRegisterNo;

    /** Original receipt no. */
    @CsvColumn(number = 31, label = "original_receipt_no")
    private String originalReceiptNo;

    /** Deposit currency code. */
    @CsvColumn(number = 32, label = "deposit_currency_code")
    private String depositCurrencyCode;

    /** Deposit value. */
    @CsvColumn(number = 33, label = "deposit_value")
    private BigDecimal depositValue;

    /** Change currency code. */
    @CsvColumn(number = 34, label = "change_currency_code")
    private String changeCurrencyCode;

    /** Change value. */
    @CsvColumn(number = 35, label = "change_value")
    private BigDecimal changeValue;

    /** Receipt no for credit card cancellation. */
    @CsvColumn(number = 36, label = "receipt_no_for_credit_card_cancellation")
    private String receiptNoForCreditCardCancellation;

    /** Receipt no for credit card. */
    @CsvColumn(number = 37, label = "receipt_no_for_credit_card")
    private String receiptNoForCreditCard;

    /** Employee Sale flag. */
    @CsvColumn(number = 38, label = "employee_sale_flag")
    private boolean employeeSaleFlag;

    /** Consistency sales flag. */
    @CsvColumn(number = 39, label = "consistency_sales_flag")
    private boolean consistencySalesFlag;

    /** Corporate id. */
    @CsvColumn(number = 40, label = "corporate_id")
    private String corporateId;

    /** Sales transaction discount flag. */
    @CsvColumn(number = 41, label = "sales_transaction_discount_flag")
    private boolean salesTransactionDiscountFlag;

    /** Sales transaction discount amount rate currency code. */
    @CsvColumn(number = 42, label = "sales_transaction_discount_amount_rate_currency_code")
    private String salesTransactionDiscountAmountRateCurrencyCode;

    /** Sales transaction discount amount rate. */
    @CsvColumn(number = 43, label = "sales_transaction_discount_amount_rate")
    private BigDecimal salesTransactionDiscountAmountRate;

}
