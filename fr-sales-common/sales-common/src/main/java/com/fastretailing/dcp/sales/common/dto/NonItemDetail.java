/**
 * @(#)NonItemDetail.java
 * 
 *                     Copyright (c) 2018 Fast Retailing Corporation.
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
 * Non item detail.
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen",
        date = "2018-05-16T10:34:00.975+09:00")

@Data
public class NonItemDetail {
    @JsonProperty("non_md_detail_list_sales_transaction_type")
    private String nonMdDetailListSalesTransactionType = null;

    @JsonProperty("item_detail_sub_number")
    private Integer itemDetailSubNumber = null;

    @JsonProperty("detail_sub_number")
    private Integer detailSubNumber = null;

    @JsonProperty("non_md_type")
    private String nonMdType = null;

    @JsonProperty("non_item_code")
    private String nonItemCode = null;

    @JsonProperty("service_code")
    private String serviceCode = null;

    @JsonProperty("pos_non_item_name")
    private String posNonItemName = null;

    @JsonProperty("quantity_code")
    private String quantityCode = null;

    @JsonProperty("non_item_qty")
    private Integer nonItemQty = null;

    @JsonProperty("non_item_unit_price_tax_excluded")
    private Price nonItemUnitPriceTaxExcluded = null;

    @JsonProperty("non_item_unit_price_tax_included")
    private Price nonItemUnitPriceTaxIncluded = null;

    @JsonProperty("non_item_sales_amt_tax_excluded")
    private Price nonItemSalesAmtTaxExcluded = null;

    @JsonProperty("non_item_sales_amt_tax_included")
    private Price nonItemSalesAmtTaxIncluded = null;

    @JsonProperty("non_item_new_price")
    private Price nonItemNewPrice = null;

    @JsonProperty("non_calculation_non_item_type")
    private String nonCalculationNonItemType = null;

    @JsonProperty("order_status_update_date")
    private String orderStatusUpdateDate = null;

    @JsonProperty("order_status_last_update_date_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssX",
    timezone = "UTC")
    private OffsetDateTime orderStatusLastUpdateDateTime = null;

    @JsonProperty("order_status")
    private String orderStatus = null;

    @JsonProperty("order_substatus")
    private String orderSubstatus = null;

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

    @JsonProperty("contribution_sales_representative")
    private String contributionSalesRepresentative = null;

    @JsonProperty("non_item_detail_number")
    private Integer nonItemDetailNumber = null;

    @JsonProperty("non_item_taxation_type")
    private String nonItemTaxationType = null;

    @JsonProperty("non_item_tax_kind")
    private String nonItemTaxKind = null;

    @JsonProperty("non_item_return_complete_flag")
    private Integer nonItemReturnCompleteFlag = null;

    @JsonProperty("non_item_info")
    private NonItemInformation nonItemInfo = null;

    @JsonProperty("non_item_discount_detail_list")
    private List<NonItemDiscountDetail> nonItemDiscountDetailList = new ArrayList<NonItemDiscountDetail>();

    @JsonProperty("non_item_tax_detail_list")
    private List<NonItemTaxDetail> nonItemTaxDetailList = new ArrayList<NonItemTaxDetail>();

}

