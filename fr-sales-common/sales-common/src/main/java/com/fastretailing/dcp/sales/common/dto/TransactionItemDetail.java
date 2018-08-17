/**
 * @(#)TransactionItemDetail.java
 * 
 *                                Copyright (c) 2018 Fast Retailing Corporation.
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
 * Transaction item detail.
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen",
        date = "2018-05-16T10:34:00.975+09:00")

@Data
public class TransactionItemDetail {
    @JsonProperty("system_brand_code")
    private String systemBrandCode = null;

    @JsonProperty("detail_sub_number")
    private Integer detailSubNumber = null;

    @JsonProperty("detail_list_sales_transaction_type")
    private String detailListSalesTransactionType = null;

    @JsonProperty("l2_item_code")
    private String l2ItemCode = null;

    @JsonProperty("view_l2_item_code")
    private String viewL2ItemCode = null;

    @JsonProperty("l2_product_name")
    private String l2ProductName = null;

    @JsonProperty("l3_item_code")
    private String l3ItemCode = null;

    @JsonProperty("l3_pos_product_name")
    private String l3PosProductName = null;

    @JsonProperty("epc_code")
    private String epcCode = null;

    @JsonProperty("g_department_code")
    private String gdepartmentCode = null;

    @JsonProperty("dept_code")
    private Integer deptCode = null;

    @JsonProperty("quantity_code")
    private String quantityCode = null;

    @JsonProperty("item_qty")
    private Integer itemQty = null;

    @JsonProperty("item_cost")
    private Price itemCost = null;

    @JsonProperty("initial_selling_price")
    private Price initialSellingPrice = null;

    @JsonProperty("b_item_selling_price")
    private Price bItemSellingPrice = null;

    @JsonProperty("item_new_price")
    private Price itemNewPrice = null;

    @JsonProperty("item_unit_price_tax_excluded")
    private Price itemUnitPriceTaxExcluded = null;

    @JsonProperty("item_unit_price_tax_included")
    private Price itemUnitPriceTaxIncluded = null;

    @JsonProperty("item_sales_amt_tax_excluded")
    private Price itemSalesAmtTaxExcluded = null;

    @JsonProperty("item_sales_amt_tax_included")
    private Price itemSalesAmtTaxIncluded = null;

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

    @JsonProperty("customer_id")
    private String customerId = null;

    @JsonProperty("bundle_purchase_qty")
    private Integer bundlePurchaseQty = null;

    @JsonProperty("bundle_purchase_price")
    private Price bundlePurchasePrice = null;

    @JsonProperty("bundle_purchase_index")
    private Integer bundlePurchaseIndex = null;

    @JsonProperty("limited_amount_promotion_count")
    private Integer limitedAmountPromotionCount = null;

    @JsonProperty("calculation_unavailable_type")
    private String calculationUnavailableType = null;

    @JsonProperty("item_mount_discount_type")
    private String itemMountDiscountType = null;

    @JsonProperty("item_discount_amount")
    private Price itemDiscountAmount = null;

    @JsonProperty("set_sales_flag")
    private String setSalesFlag = null;

    @JsonProperty("set_sales_price")
    private Price setSalesPrice = null;

    @JsonProperty("set_sales_detail_index")
    private Integer setSalesDetailIndex = null;

    @JsonProperty("item_detail_number")
    private Integer itemDetailNumber = null;

    @JsonProperty("item_taxation_type")
    private String itemTaxationType = null;

    @JsonProperty("item_tax_kind")
    private String itemTaxKind = null;

    @JsonProperty("item_return_complete_flag")
    private Integer itemReturnCompleteFlag = null;

    @JsonProperty("non_item_detail_list_by_item")
    private List<NonItemDetail> nonItemDetailListByItem = new ArrayList<>();

    @JsonProperty("item_discount_list")
    private List<ItemDiscount> itemDiscountList = new ArrayList<>();

    @JsonProperty("item_tax_detail_list")
    private List<ItemTaxDetail> itemTaxDetailList = new ArrayList<>();

}

