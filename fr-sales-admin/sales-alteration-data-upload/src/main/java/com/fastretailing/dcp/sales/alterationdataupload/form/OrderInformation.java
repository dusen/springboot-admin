/**
 * @(#)OrderInformation.java
 *
 *                           Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.alterationdataupload.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

/**
 * Order information.
 */
@Data
@JsonPropertyOrder({"transaction_id", "integrated_order_id", "order_barcode_number", "store_code",
        "system_brand_code", "system_business_code", "system_country_code", "channel_code",
        "update_type", "customer_id", "order_confirmation_business_date",
        "order_confirmation_date_time", "error_check_type", "data_alteration_sales_linkage_type",
        "data_alteration_backbone_linkage_type", "data_alteration_editing_flag",
        "data_alteration_user_id","sales_transaction_error_id"})
public class OrderInformation {

    /** Transaction id. */
    @JsonProperty("transaction_id")
    private String transactionId;

    /** Integrated order id. */
    @JsonProperty("integrated_order_id")
    private String integratedOrderId;

    /** Order bar code number. */
    @JsonProperty("order_barcode_number")
    private String orderBarcodeNumber;

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

    /** Channel code. */
    @JsonProperty("channel_code")
    private String channelCode;

    /** Update type. */
    @JsonProperty("update_type")
    private String updateType;

    /** Customer id. */
    @JsonProperty("customer_id")
    private String customerId;

    /** Order confirmation business date. */
    @JsonProperty("order_confirmation_business_date")
    private String orderConfirmationBusinessDate;

    /** Order confirmation date time. */
    @JsonProperty("order_confirmation_date_time")
    private String orderConfirmationDateTime;

    /** Error check type. */
    @JsonProperty("error_check_type")
    private String errorCheckType;

    /** Data alteration sales linkage type. */
    @JsonProperty("data_alteration_sales_linkage_type")
    private String dataAlterationSalesLinkageType;

    /** Data alteration backbone linkage type. */
    @JsonProperty("data_alteration_backbone_linkage_type")
    private String dataAlterationBackboneLinkageType;

    /** Data alteration editing flag. */
    @JsonProperty("data_alteration_editing_flag")
    private String dataAlterationEditingFlag;

    /** Data alteration user id. */
    @JsonProperty("data_alteration_user_id")
    private String dataAlterationUserId;
    
    /** Sales transaction error id. */
    @JsonProperty("sales_transaction_error_id")
    private String salesTransactionErrorId;

}
