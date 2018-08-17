/**
 * @(#)TransactionImport.java
 *
 *                            Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.importtransaction.dto;

import java.time.OffsetDateTime;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fastretailing.dcp.storecommon.deserialize.OneZeroToBooleanDeserializer;
import lombok.Data;

/**
 * Transaction import.
 */
@Data
public class TransactionImport {
    /** Update type. */
    @JsonProperty("update_type")
    private String updateType;

    /** Error check type. */
    @JsonProperty("error_check_type")
    private Integer errorCheckType;

    /** Data alteration sales linkage type. */
    @JsonProperty("data_alteration_sales_linkage_type")
    private Integer dataAlterationSalesLinkageType;

    /** Data alteration backbone linkage type. */
    @JsonProperty("data_alteration_backbone_linkage_type")
    private Integer dataAlterationBackboneLinkageType;

    /** Sales transaction error id. */
    @JsonProperty("sales_transaction_error_id")
    private String salesTransactionErrorId;

    /** Integrated order id. */
    @JsonProperty("integrated_order_id")
    private String integratedOrderId;

    /** Order bar code number. */
    @JsonProperty("order_barcode_number")
    private String orderBarcodeNumber;

    /** Channel code. */
    @JsonProperty("channel_code")
    private String channelCode;

    /** Store code. */
    @JsonProperty("store_code")
    private String storeCode;

    /** System brand code. */
    @JsonProperty("system_brand_code")
    private String systemBrandCode;

    /** System business code. */
    @JsonProperty("system_business_code")
    private String systemBusinessCode;

    /** System country code. */
    @JsonProperty("system_country_code")
    private String systemCountryCode;

    /** Customer id. */
    @JsonProperty("customer_id")
    private String customerId;

    /** Order confirmation business date. */
    @JsonProperty("order_confirmation_business_date")
    private String orderConfirmationBusinessDate;

    /** Order confirmation date time. */
    @JsonProperty("order_confirmation_date_time")
    private OffsetDateTime orderConfirmationDateTime;

    /** Data correction editing flag. */
    @JsonProperty("data_correction_editing_flag")
    @JsonDeserialize(using = OneZeroToBooleanDeserializer.class)
    private Boolean dataCorrectionEditingFlag;

    /** Data correction user id. */
    @JsonProperty("data_correction_user_id")
    private String dataCorrectionUserId;

    /** Transaction list. */
    @JsonProperty("transaction_list")
    private List<Transaction> transactionList;
}


