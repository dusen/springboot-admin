/**
 * @(#)TransactionImportData.java
 * 
 *                                Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.dto;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Transaction import data.
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen",
        date = "2018-05-16T10:34:00.975+09:00")

@Data
public class TransactionImportData {
    @JsonProperty("update_type")
    private String updateType = null;

    @JsonProperty("error_check_type")
    private Integer errorCheckType = null;

    @JsonProperty("data_alteration_sales_linkage_type")
    private Integer dataAlterationSalesLinkageType = null;

    @JsonProperty("data_alteration_backbone_linkage_type")
    private Integer dataAlterationBackboneLinkageType = null;

    @JsonProperty("sales_transaction_error_id")
    private String salesTransactionErrorId = null;

    @JsonProperty("integrated_order_id")
    private String integratedOrderId = null;

    @JsonProperty("order_barcode_number")
    private String orderBarcodeNumber = null;

    @JsonProperty("channel_code")
    private String channelCode = null;

    @JsonProperty("store_code")
    private String storeCode = null;

    @JsonProperty("system_brand_code")
    private String systemBrandCode = null;

    @JsonProperty("system_business_code")
    private String systemBusinessCode = null;

    @JsonProperty("system_country_code")
    private String systemCountryCode = null;

    @JsonProperty("customer_id")
    private String customerId = null;

    @JsonProperty("order_confirmation_business_date")
    private String orderConfirmationBusinessDate = null;

    @JsonProperty("order_confirmation_date_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssX",
            timezone = "UTC")
    private OffsetDateTime orderConfirmationDateTime = null;

    @JsonProperty("data_correction_editing_flag")
    private Integer dataCorrectionEditingFlag = null;

    @JsonProperty("data_correction_user_id")
    private String dataCorrectionUserId = null;

    @JsonProperty("transaction_list")
    private List<Transaction> transactionList = new ArrayList<>();

}

