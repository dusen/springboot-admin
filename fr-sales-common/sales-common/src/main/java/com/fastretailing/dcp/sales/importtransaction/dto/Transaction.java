/**
 * @(#)Transaction.java
 *
 *                      Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.importtransaction.dto;

import java.time.OffsetDateTime;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fastretailing.dcp.common.validation.annotation.Alphanumeric;
import com.fastretailing.dcp.common.validation.annotation.StringDate;
import com.fastretailing.dcp.sales.importtransaction.dto.groups.Correction;
import com.fastretailing.dcp.sales.importtransaction.dto.groups.Insert;
import com.fastretailing.dcp.sales.importtransaction.dto.groups.PosInsert;
import com.fastretailing.dcp.sales.importtransaction.dto.groups.Update;
import com.fastretailing.dcp.storecommon.deserialize.OneZeroToBooleanDeserializer;
import com.fastretailing.dcp.storecommon.dto.Price;
import lombok.Data;

/**
 * Transaction.
 * 
 */
@Data
public class Transaction {

    /** Transaction serial number. */
    @JsonProperty("transaction_serial_number")
    @Max(9999)
    private Integer transactionSerialNumber;

    /** Integrated order id. */
    @JsonProperty("integrated_order_id")
    @NotBlank(groups = {Insert.class, Update.class, Correction.class})
    @Size(max = 27)
    @Pattern(regexp = "[a-zA-Z0-9-_]*")
    private String integratedOrderId;

    /** Order sub number. */
    @JsonProperty("order_sub_number")
    @NotNull(groups = {Insert.class, Update.class, Correction.class})
    @Max(9999)
    private Integer orderSubNumber;

    /** Sales transaction id. */
    @JsonProperty("sales_transaction_id")
    @NotBlank(groups = {Insert.class, Update.class, Correction.class})
    @Size(max = 30)
    @Pattern(regexp = "[a-zA-Z0-9-_]*")
    private String salesTransactionId;

    /** Token code. */
    @JsonProperty("token_code")
    @Size(max = 30)
    @Pattern(regexp = "[a-zA-Z0-9-_]*")
    private String tokenCode;

    /** Transaction type. */
    @JsonProperty("transaction_type")
    @NotBlank(groups = {Insert.class, Correction.class, PosInsert.class})
    @Size(max = 6)
    @Alphanumeric
    private String transactionType;

    /** Sales linkage type. */
    @JsonProperty("sales_linkage_type")
    @NotNull(groups = {Insert.class, Correction.class})
    @Max(9)
    private Integer salesLinkageType;

    /** Return type. */
    @JsonProperty("return_type")
    @Max(9)
    private Integer returnType;

    /** System brand code. */
    @JsonProperty("system_brand_code")
    @Size(max = 4)
    @Alphanumeric
    private String systemBrandCode;

    /** System business code. */
    @JsonProperty("system_business_code")
    @Size(max = 4)
    @Alphanumeric
    private String systemBusinessCode;

    /** System country code. */
    @JsonProperty("system_country_code")
    @Size(max = 3)
    @Alphanumeric
    private String systemCountryCode;

    /** Store code. */
    @JsonProperty("store_code")
    @NotBlank(groups = {Insert.class, Correction.class, PosInsert.class})
    @Size(max = 10)
    @Alphanumeric
    private String storeCode;

    /** Channel code. */
    @JsonProperty("channel_code")
    @NotBlank(groups = {Insert.class, Correction.class, PosInsert.class})
    @Size(max = 10)
    @Pattern(regexp = "[a-zA-Z0-9-_]*")
    private String channelCode;

    /** Data creation business day. */
    @JsonProperty("data_creation_business_date")
    @NotBlank(groups = {PosInsert.class})
    @Size(max = 10)
    @StringDate(patterns = "yyyy-MM-dd")
    private String dataCreationBusinessDate;

    /** Data creation date and time. */
    @JsonProperty("data_creation_date_time")
    @NotNull(groups = {Insert.class, Correction.class, PosInsert.class})
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssX",
            timezone = "UTC")
    @Past
    private OffsetDateTime dataCreationDateTime;

    /** Order status update date. */
    @JsonProperty("order_status_update_date")
    @NotBlank(groups = {Update.class, PosInsert.class})
    @Size(max = 10)
    @StringDate(patterns = "yyyy-MM-dd")
    private String orderStatusUpdateDate;

    /** Order status last update date time. */
    @JsonProperty("order_status_last_update_date_time")
    @NotNull(groups = {Insert.class, Update.class, Correction.class, PosInsert.class})
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssX",
            timezone = "UTC")
    @Past
    private OffsetDateTime orderStatusLastUpdateDateTime;

    /** Cash register No.. */
    @JsonProperty("cash_register_no")
    @NotNull(groups = {Insert.class, Correction.class, PosInsert.class})
    @Max(999)
    private Integer cashRegisterNo;

    /** Receipt No.. */
    @JsonProperty("receipt_no")
    @NotBlank(groups = {Insert.class, Correction.class, PosInsert.class})
    @Size(max = 4)
    @Alphanumeric
    private String receiptNo;

    /** Order number for store payment. */
    @JsonProperty("order_number_for_store_payment")
    @Size(max = 27)
    @Pattern(regexp = "[a-zA-Z0-9-_]*")
    private String orderNumberForStorePayment;

    /** Advance received store code. */
    @JsonProperty("advance_received_store_code")
    @Size(max = 10)
    @Alphanumeric
    private String advanceReceivedStoreCode;

    /** Advance received store system brand code. */
    @JsonProperty("advance_received_store_system_brand_code")
    @Size(max = 4)
    @Alphanumeric
    private String advanceReceivedStoreSystemBrandCode;

    /** Advance received store system business code. */
    @JsonProperty("advance_received_store_system_business_code")
    @Size(max = 4)
    @Alphanumeric
    private String advanceReceivedStoreSystemBusinessCode;

    /** Advance received store system country code. */
    @JsonProperty("advance_received_store_system_country_code")
    @Size(max = 3)
    @Alphanumeric
    private String advanceReceivedStoreSystemCountryCode;

    /** Operator code. */
    @JsonProperty("operator_code")
    @NotBlank(groups = {Insert.class, Correction.class, PosInsert.class})
    @Size(max = 10)
    @Alphanumeric
    private String operatorCode;

    /** Original transaction ID. */
    @JsonProperty("original_transaction_id")
    @Size(max = 30)
    @Pattern(regexp = "[a-zA-Z0-9-_]*")
    private String originalTransactionId;

    /** Original receipt no.. */
    @JsonProperty("original_receipt_no")
    @Size(max = 4)
    @Alphanumeric
    private String originalReceiptNo;

    /** Original cash register no.. */
    @JsonProperty("original_cash_register_no")
    @Max(999)
    private Integer originalCashRegisterNo;

    /** Deposit. */
    @JsonProperty("deposit")
    @Valid
    private Price deposit;

    /** Change. */
    @JsonProperty("change")
    @Valid
    private Price change;

    /** Receipt No for credit card cancellation. */
    @JsonProperty("receipt_no_for_credit_card_cancellation")
    @Size(max = 11)
    @Pattern(regexp = "[a-zA-Z0-9-_]*")
    private String receiptNoForCreditCardCancellation;

    /** Receipt No for credit card. */
    @JsonProperty("receipt_no_for_credit_card")
    @Size(max = 30)
    @Pattern(regexp = "[a-zA-Z0-9-_]*")
    private String receiptNoForCreditCard;

    /** Payment store code. */
    @JsonProperty("payment_store_code")
    @Size(max = 10)
    @Alphanumeric
    private String paymentStoreCode;

    /** Payment store system brand code. */
    @JsonProperty("payment_store_system_brand_code")
    @Size(max = 4)
    @Alphanumeric
    private String paymentStoreSystemBrandCode;

    /** Payment store system business code. */
    @JsonProperty("payment_store_system_business_code")
    @Size(max = 4)
    @Alphanumeric
    private String paymentStoreSystemBusinessCode;

    /** Payment store system country code. */
    @JsonProperty("payment_store_system_country_code")
    @Size(max = 3)
    @Alphanumeric
    private String paymentStoreSystemCountryCode;

    /** Receipt issued flag. */
    @JsonProperty("receipt_issued_flag")
    @JsonDeserialize(using = OneZeroToBooleanDeserializer.class)
    private Boolean receiptIssuedFlag;

    /** Processing company code. */
    @JsonProperty("processing_company_code")
    @Size(max = 20)
    @Pattern(regexp = "[a-zA-Z0-9-_]*")
    private String processingCompanyCode;

    /** Order cancellation date. */
    @JsonProperty("order_cancellation_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssX",
            timezone = "UTC")
    private OffsetDateTime orderCancellationDate;

    /** Order status. */
    @JsonProperty("order_status")
    @NotBlank(groups = {Insert.class, Update.class, Correction.class})
    @Size(max = 50)
    @Pattern(regexp = "[a-zA-Z0-9-_]*")
    private String orderStatus;

    /** Order sub status. */
    @JsonProperty("order_substatus")
    @Size(max = 50)
    @Pattern(regexp = "[a-zA-Z0-9-_]*")
    private String orderSubstatus;

    /** Payment status. */
    @JsonProperty("payment_status")
    @Size(max = 50)
    @Pattern(regexp = "[a-zA-Z0-9-_]*")
    private String paymentStatus;

    /** Shipment status. */
    @JsonProperty("shipment_status")
    @Size(max = 50)
    @Pattern(regexp = "[a-zA-Z0-9-_]*")
    private String shipmentStatus;

    /** Receiving status. */
    @JsonProperty("receiving_status")
    @Size(max = 50)
    @Pattern(regexp = "[a-zA-Z0-9-_]*")
    private String receivingStatus;

    /** Transfer out status. */
    @JsonProperty("transfer_out_status")
    @Size(max = 50)
    @Pattern(regexp = "[a-zA-Z0-9-_]*")
    private String transferOutStatus;

    /** Booking store code. */
    @JsonProperty("booking_store_code")
    @Size(max = 10)
    @Alphanumeric
    private String bookingStoreCode;

    /** Booking store system brand code. */
    @JsonProperty("booking_store_system_brand_code")
    @Size(max = 4)
    @Alphanumeric
    private String bookingStoreSystemBrandCode;

    /** Booking store system business code. */
    @JsonProperty("booking_store_system_business_code")
    @Size(max = 4)
    @Alphanumeric
    private String bookingStoreSystemBusinessCode;

    /** Booking store system country code. */
    @JsonProperty("booking_store_system_country_code")
    @Size(max = 3)
    @Alphanumeric
    private String bookingStoreSystemCountryCode;

    /** Shipment store code. */
    @JsonProperty("shipment_store_code")
    @Size(max = 10)
    @Alphanumeric
    private String shipmentStoreCode;

    /** Shipment store system brand code. */
    @JsonProperty("shipment_store_system_brand_code")
    @Size(max = 4)
    @Alphanumeric
    private String shipmentStoreSystemBrandCode;

    /** Shipment store system business code. */
    @JsonProperty("shipment_store_system_business_code")
    @Size(max = 4)
    @Alphanumeric
    private String shipmentStoreSystemBusinessCode;

    /** Shipment store system country code. */
    @JsonProperty("shipment_store_system_country_code")
    @Size(max = 3)
    @Alphanumeric
    private String shipmentStoreSystemCountryCode;

    /** Receipt store code. */
    @JsonProperty("receipt_store_code")
    @Size(max = 10)
    @Alphanumeric
    private String receiptStoreCode;

    /** Receipt store system brand code. */
    @JsonProperty("receipt_store_system_brand_code")
    @Size(max = 4)
    @Alphanumeric
    private String receiptStoreSystemBrandCode;

    /** Receipt store system business code. */
    @JsonProperty("receipt_store_system_business_code")
    @Size(max = 4)
    @Alphanumeric
    private String receiptStoreSystemBusinessCode;

    /** Receipt store system country code. */
    @JsonProperty("receipt_store_system_country_code")
    @Size(max = 3)
    @Alphanumeric
    private String receiptStoreSystemCountryCode;

    /** Customer ID. */
    @JsonProperty("customer_id")
    @Size(max = 30)
    @Pattern(regexp = "[a-zA-Z0-9-_]*")
    private String customerId;

    /** Customer type. */
    @JsonProperty("customer_type")
    @Size(max = 1)
    @Alphanumeric
    private String customerType;

    /** Corporate ID. */
    @JsonProperty("corporate_id")
    @Size(max = 20)
    @Pattern(regexp = "[a-zA-Z0-9-_]*")
    private String corporateId;

    /** Sales transaction discount flag. */
    @JsonProperty("sales_transaction_discount_flag")
    @JsonDeserialize(using = OneZeroToBooleanDeserializer.class)
    private Boolean salesTransactionDiscountFlag;

    /** Sales transaction discount type. */
    @JsonProperty("sales_transaction_discount_type")
    @Size(max = 1)
    @Alphanumeric
    private String salesTransactionDiscountType;

    /** Sales transaction discount amount rate. */
    @JsonProperty("sales_transaction_discount_amount_rate")
    @Valid
    private Price salesTransactionDiscountAmountRate;

    /** Consistency sales flag. */
    @JsonProperty("consistency_sales_flag")
    @JsonDeserialize(using = OneZeroToBooleanDeserializer.class)
    @NotNull(groups = {Insert.class, Correction.class})
    private Boolean consistencySalesFlag;

    /** Employee sale flag. */
    @JsonProperty("employee_sale_flag")
    @JsonDeserialize(using = OneZeroToBooleanDeserializer.class)
    @NotNull(groups = {Insert.class, Correction.class})
    private Boolean employeeSaleFlag;

    /** E receipt target flag. */
    @JsonProperty("e_receipt_target_flag")
    @JsonDeserialize(using = OneZeroToBooleanDeserializer.class)
    @NotNull(groups = {Insert.class, Correction.class})
    private Boolean eReceiptTargetFlag;

    /** Transaction item detail list. */
    @JsonProperty("transaction_item_detail_list")
    private List<TransactionItemDetail> transactionItemDetailList;

    /** Non item detail list. */
    @JsonProperty("non_item_detail_list")
    private List<NonItemDetail> nonItemDetailList;

    /** Sales transaction tender list. */
    @JsonProperty("sales_transaction_tender_list")
    @NotEmpty(groups = {Insert.class, Correction.class, PosInsert.class})
    private List<SalesTransactionTender> salesTransactionTenderList;

    /** Sales transaction tender list. */
    @JsonProperty("sales_transaction_tax_detail_list")
    private List<SalesTransactionTaxDetail> salesTransactionTaxDetailList;

    /** Sales transaction total list. */
    @JsonProperty("sales_transaction_total_list")
    @NotEmpty(groups = {Insert.class, Correction.class, PosInsert.class})
    private List<SalesTransactionTotal> salesTransactionTotalList;

}
