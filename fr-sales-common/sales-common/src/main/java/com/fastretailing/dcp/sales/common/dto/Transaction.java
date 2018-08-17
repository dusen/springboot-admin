/**
 * @(#)Transaction.java
 * 
 *                      Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.dto;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fastretailing.dcp.storecommon.dto.Price;
import lombok.Data;

/**
 * Transaction.
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen",
        date = "2018-05-16T10:34:00.975+09:00")

@Data
public class Transaction {
    @JsonProperty("transaction_serial_number")
    private Integer transactionSerialNumber = null;

    @JsonProperty("integrated_order_id")
    private String integratedOrderId = null;

    @JsonProperty("order_sub_number")
    private Integer orderSubNumber = null;

    @JsonProperty("sales_transaction_id")
    private String salesTransactionId = null;

    @JsonProperty("token_code")
    private String tokenCode = null;

    @JsonProperty("transaction_type")
    private String transactionType = null;

    @JsonProperty("sales_linkage_type")
    private Integer salesLinkageType = null;

    @JsonProperty("return_type")
    private Integer returnType = null;

    @JsonProperty("system_brand_code")
    private String systemBrandCode = null;

    @JsonProperty("system_business_code")
    private String systemBusinessCode = null;

    @JsonProperty("system_country_code")
    private String systemCountryCode = null;

    @JsonProperty("store_code")
    private String storeCode = null;

    @JsonProperty("channel_code")
    private String channelCode = null;

    @JsonProperty("data_creation_business_date")
    private String dataCreationBusinessDate = null;

    @JsonProperty("data_creation_date_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssX",
            timezone = "UTC")
    private OffsetDateTime dataCreationDateTime = null;

    @JsonProperty("order_status_update_date")
    private String orderStatusUpdateDate = null;

    @JsonProperty("order_status_last_update_date_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssX",
            timezone = "UTC")
    private OffsetDateTime orderStatusLastUpdateDateTime = null;

    @JsonProperty("cash_register_no")
    private Integer cashRegisterNo = null;

    @JsonProperty("receipt_no")
    private String receiptNo = null;

    @JsonProperty("order_number_for_store_payment")
    private String orderNumberForStorePayment = null;

    @JsonProperty("advance_received_store_code")
    private String advanceReceivedStoreCode = null;

    @JsonProperty("advance_received_store_system_brand_code")
    private String advanceReceivedStoreSystemBrandCode = null;

    @JsonProperty("advance_received_store_system_business_code")
    private String advanceReceivedStoreSystemBusinessCode = null;

    @JsonProperty("advance_received_store_system_country_code")
    private String advanceReceivedStoreSystemCountryCode = null;

    @JsonProperty("operator_code")
    private String operatorCode = null;

    @JsonProperty("original_transaction_id")
    private String originalTransactionId = null;

    @JsonProperty("original_receipt_no")
    private String originalReceiptNo = null;

    @JsonProperty("original_cash_register_no")
    private Integer originalCashRegisterNo = null;

    @JsonProperty("deposit")
    private Price deposit = null;

    @JsonProperty("change")
    private Price change = null;

    @JsonProperty("receipt_no_for_credit_card_cancellation")
    private String receiptNoForCreditCardCancellation = null;

    @JsonProperty("receipt_no_for_credit_card")
    private String receiptNoForCreditCard = null;

    @JsonProperty("payment_store_code")
    private String paymentStoreCode = null;

    @JsonProperty("payment_store_system_brand_code")
    private String paymentStoreSystemBrandCode = null;

    @JsonProperty("payment_store_system_business_code")
    private String paymentStoreSystemBusinessCode = null;

    @JsonProperty("payment_store_system_country_code")
    private String paymentStoreSystemCountryCode = null;

    @JsonProperty("receipt_issued_flag")
    private Integer receiptIssuedFlag = null;

    @JsonProperty("processing_company_code")
    private String processingCompanyCode = null;

    @JsonProperty("order_cancellation_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssX",
            timezone = "UTC")
    private OffsetDateTime orderCancellationDate = null;

    @JsonProperty("order_status")
    private String orderStatus = null;

    @JsonProperty("order_substatus")
    private String orderSubstatus = null;

    @JsonProperty("payment_status")
    private String paymentStatus = null;

    @JsonProperty("shipment_status")
    private String shipmentStatus = null;

    @JsonProperty("receiving_status")
    private String receivingStatus = null;

    @JsonProperty("transfer_out_status")
    private String transferOutStatus = null;

    @JsonProperty("booking_store_code")
    private String bookingStoreCode = null;

    @JsonProperty("booking_store_system_brand_code")
    private String bookingStoreSystemBrandCode = null;

    @JsonProperty("booking_store_system_business_code")
    private String bookingStoreSystemBusinessCode = null;

    @JsonProperty("booking_store_system_country_code")
    private String bookingStoreSystemCountryCode = null;

    @JsonProperty("shipment_store_code")
    private String shipmentStoreCode = null;

    @JsonProperty("shipment_store_system_brand_code")
    private String shipmentStoreSystemBrandCode = null;

    @JsonProperty("shipment_store_system_business_code")
    private String shipmentStoreSystemBusinessCode = null;

    @JsonProperty("shipment_store_system_country_code")
    private String shipmentStoreSystemCountryCode = null;

    @JsonProperty("receipt_store_code")
    private String receiptStoreCode = null;

    @JsonProperty("receipt_store_system_brand_code")
    private String receiptStoreSystemBrandCode = null;

    @JsonProperty("receipt_store_system_business_code")
    private String receiptStoreSystemBusinessCode = null;

    @JsonProperty("receipt_store_system_country_code")
    private String receiptStoreSystemCountryCode = null;

    @JsonProperty("customer_id")
    private String customerId = null;

    @JsonProperty("customer_type")
    private String customerType = null;

    @JsonProperty("corporate_id")
    private String corporateId = null;

    @JsonProperty("sales_transaction_discount_flag")
    private String salesTransactionDiscountFlag = null;

    @JsonProperty("sales_transaction_discount_type")
    private String salesTransactionDiscountType = null;

    @JsonProperty("sales_transaction_discount_amount_rate")
    private Price salesTransactionDiscountAmountRate = null;

    @JsonProperty("consistency_sales_flag")
    private Integer consistencySalesFlag = null;

    @JsonProperty("employee_sale_flag")
    private Integer employeeSaleFlag = null;

    @JsonProperty("e_receipt_target_flag")
    private Integer ereceiptTargetFlag = null;

    @JsonProperty("return_complete_flag")
    private Integer returnCompleteFlag = null;

    @JsonProperty("cancelled_flag")
    private Integer cancelledFlag = null;

    @JsonProperty("transaction_item_detail_list")
    private List<TransactionItemDetail> transactionItemDetailList = new ArrayList<>();

    @JsonProperty("non_item_detail_list")
    private List<NonItemDetail> nonItemDetailList = new ArrayList<>();

    @JsonProperty("sales_transaction_tender_list")
    private List<SalesTransactionTender> salesTransactionTenderList = new ArrayList<>();

    @JsonProperty("sales_transaction_tax_detail_list")
    private List<SalesTransactionTaxDetail> salesTransactionTaxDetailList = new ArrayList<>();

    @JsonProperty("sales_transaction_total_list")
    private List<SalesTransactionTotal> salesTransactionTotalList = new ArrayList<>();
}

