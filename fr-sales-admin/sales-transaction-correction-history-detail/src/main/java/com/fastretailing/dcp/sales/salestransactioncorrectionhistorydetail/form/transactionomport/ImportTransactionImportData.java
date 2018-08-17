/**
 * @(#)ImportTransactionImportData.java
 *
 *                                      Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salestransactioncorrectionhistorydetail.form.transactionomport;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
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
import com.fastretailing.dcp.sales.importtransaction.dto.groups.BeforeValidate;
import com.fastretailing.dcp.sales.importtransaction.dto.groups.Correction;
import com.fastretailing.dcp.sales.importtransaction.dto.groups.Delete;
import com.fastretailing.dcp.sales.importtransaction.dto.groups.Insert;
import com.fastretailing.dcp.sales.importtransaction.dto.groups.PosInsert;
import com.fastretailing.dcp.sales.importtransaction.dto.groups.Update;
import com.fastretailing.dcp.storecommon.deserialize.OneZeroToBooleanDeserializer;
import lombok.Data;

/**
 * Import Transaction import data.
 * 
 */
@Data
public class ImportTransactionImportData {

    /** Format order confirmation business date. */
    private String formatOrderConfirmationBusinessDate;

    /** Format order confirmation business date time. */
    private String formatOrderConfirmationDateTime;

    /** Update type. */
    @JsonProperty("update_type")
    @NotBlank(groups = {Insert.class, Update.class, Correction.class, Delete.class})
    @Size(max = 10)
    @Alphanumeric
    private String updateType;

    /** Error check type. */
    @JsonProperty("error_check_type")
    @NotNull(groups = {Insert.class, Update.class, Correction.class})
    @Max(9)
    private Integer errorCheckType;

    /** Data alteration sales linkage type. */
    @JsonProperty("data_alteration_sales_linkage_type")
    @Max(9)
    private Integer dataAlterationSalesLinkageType;

    /** Data alteration backbone linkage type. */
    @JsonProperty("data_alteration_backbone_linkage_type")
    @Max(9)
    private Integer dataAlterationBackboneLinkageType;

    /** Sales transaction error id. */
    @JsonProperty("sales_transaction_error_id")
    @NotBlank(groups = {Correction.class, Delete.class})
    @Size(max = 57)
    @Pattern(regexp = "[a-zA-Z0-9-_]*")
    private String salesTransactionErrorId;

    /** Integrated order id. */
    @JsonProperty("integrated_order_id")
    @NotBlank(groups = {Insert.class, Update.class, Correction.class})
    @Size(max = 27)
    @Pattern(regexp = "[a-zA-Z0-9-_]*")
    private String integratedOrderId;

    /** Order bar code number. */
    @JsonProperty("order_barcode_number")
    @NotBlank(groups = {Insert.class, Correction.class, PosInsert.class})
    @Size(max = 23)
    @Alphanumeric
    private String orderBarcodeNumber;

    /** Channel code. */
    @JsonProperty("channel_code")
    @NotBlank(groups = {Insert.class, Correction.class})
    @Size(max = 10)
    @Pattern(regexp = "[a-zA-Z0-9-_]*")
    private String channelCode;

    /** Store code. */
    @JsonProperty("store_code")
    @NotBlank(groups = {Insert.class, Correction.class, BeforeValidate.class})
    @Size(max = 10)
    @Alphanumeric
    private String storeCode;

    /** System brand code. */
    @JsonProperty("system_brand_code")
    @NotBlank(groups = {Insert.class, Correction.class})
    @Size(max = 4)
    @Alphanumeric
    private String systemBrandCode;

    /** System business code. */
    @JsonProperty("system_business_code")
    @NotBlank(groups = {Insert.class, Correction.class})
    @Size(max = 4)
    @Alphanumeric
    private String systemBusinessCode;

    /** System country code. */
    @JsonProperty("system_country_code")
    @NotBlank(groups = {Insert.class, Correction.class})
    @Size(max = 3)
    @Alphanumeric
    private String systemCountryCode;

    /** Customer ID. */
    @JsonProperty("customer_id")
    @Size(max = 30)
    @Pattern(regexp = "[a-zA-Z0-9-_]*")
    private String customerId;

    /** Order confirmation business date. */
    @JsonProperty("order_confirmation_business_date")
    @Size(max = 10)
    @StringDate(patterns = "yyyy-MM-dd")
    private String orderConfirmationBusinessDate;

    /** Order confirmation date time. */
    @JsonProperty("order_confirmation_date_time")
    @NotNull(groups = {Insert.class, Correction.class})
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssX",
            timezone = "UTC")
    @Past
    private OffsetDateTime orderConfirmationDateTime;

    /** Data correction editing flag. */
    @JsonProperty("data_correction_editing_flag")
    @JsonDeserialize(using = OneZeroToBooleanDeserializer.class)
    private Boolean dataCorrectionEditingFlag;

    /** Data correction user ID. */
    @JsonProperty("data_correction_user_id")
    @NotBlank(groups = {Correction.class, Delete.class})
    @Size(max = 30)
    @Alphanumeric
    private String dataCorrectionUserId;

    /** Transaction list. */
    @JsonProperty("transaction_list")
    @NotEmpty(groups = {Insert.class, Update.class, Correction.class, PosInsert.class})
    private List<ImportTransaction> transactionList = new ArrayList<>();

}
