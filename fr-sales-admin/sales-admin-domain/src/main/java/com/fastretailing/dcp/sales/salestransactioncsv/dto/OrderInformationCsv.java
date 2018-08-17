/**
 * @(#)OrderInformationCsv.java
 *
 *                              Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salestransactioncsv.dto;

import java.time.OffsetDateTime;
import com.github.mygreen.supercsv.annotation.CsvBean;
import com.github.mygreen.supercsv.annotation.CsvColumn;
import lombok.Data;

/**
 * Order information csv.
 */
@Data
@CsvBean(header = true)
public class OrderInformationCsv {

    /** Transaction id. */
    @CsvColumn(number = 1, label = "transaction_id")
    private String transactionId;

    /** Integrated order id. */
    @CsvColumn(number = 2, label = "integrated_order_id")
    private String integratedOrderId;

    /** Order bar code number. */
    @CsvColumn(number = 3, label = "order_barcode_number")
    private String orderBarcodeNumber;

    /** Store code. */
    @CsvColumn(number = 4, label = "store_code")
    private String storeCode;

    /** System brand code. */
    @CsvColumn(number = 5, label = "system_brand_code")
    private String systemBrandCode;

    /** System business code. */
    @CsvColumn(number = 6, label = "system_business_code")
    private String systemBusinessCode;

    /** System country code. */
    @CsvColumn(number = 7, label = "system_country_code")
    private String systemCountryCode;

    /** Channel code. */
    @CsvColumn(number = 8, label = "channel_code")
    private String channelCode;

    /** Update type. */
    @CsvColumn(number = 9, label = "update_type")
    private String updateType;

    /** Customer id. */
    @CsvColumn(number = 10, label = "customer_id")
    private String customerId;

    /** Order confirmation business date. */
    @CsvColumn(number = 11, label = "order_confirmation_business_date")
    private String orderConfirmationBusinessDate;

    /** Order confirmation date time. */
    @CsvColumn(number = 12, label = "order_confirmation_date_time")
    private OffsetDateTime orderConfirmationDateTime;

    /** Error check type. */
    @CsvColumn(number = 13, label = "error_check_type")
    private Integer errorCheckType;

    /** Data alteration sales linkage type. */
    @CsvColumn(number = 14, label = "data_alteration_sales_linkage_type")
    private Integer dataAlterationSalesLinkageType;

    /** Data alteration backbone linkage type. */
    @CsvColumn(number = 15, label = "data_alteration_backbone_linkage_type")
    private Integer dataAlterationBackboneLinkageType;

    /** Data alteration edition flag. */
    @CsvColumn(number = 16, label = "data_alteration_editing_flag")
    private boolean dataAlterationEditingFlag;

    /** Data alteration user id. */
    @CsvColumn(number = 17, label = "data_alteration_user_id")
    private String dataAlterationUserId;

    /** Sales transaction error id. */
    @CsvColumn(number = 18, label = "sales_transaction_error_id")
    private String salesTransactionErrorId;
}
