/**
 * @(#)ItemDetail.java
 *
 *                     Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.alterationdataupload.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

/**
 * Item detail.
 */
@Data
@JsonPropertyOrder({"transaction_id", "order_sub_number", "sales_transaction_id",
        "detail_sub_number", "item_detail_sub_number", "sales_transaction_type",
        "system_brand_code", "l2_item_code", "display_l2_item_code", "l2_product_name",
        "l3_item_code", "l3_pos_product_name", "product_classification", "non_md_type",
        "non_md_code", "service_code", "epc_code", "g_department_code", "major_category_code",
        "quantity_code", "detail_quantity", "item_cost_currency_code", "item_cost_value",
        "initial_selling_price_currency_code", "initial_selling_price",
        "bclass_price_currency_code", "bclass_price", "new_price_currency_code", "new_price",
        "retail_unit_price_tax_excluded_currency_code", "retail_unit_price_tax_excluded",
        "retail_unit_price_tax_included_currency_code", "retail_unit_price_tax_included",
        "sales_amount_tax_excluded_currency_code", "sales_amount_tax_excluded",
        "sales_amount_tax_included_currency_code", "sales_amount_tax_included",
        "calculation_unavailable_type", "bundle_purchase_applicable_quantity",
        "bundle_purchase_applicable_price_currency_code", "bundle_purchase_applicable_price",
        "bundle_purchase_index", "limited_amount_promotion_count", "store_item_discount_type",
        "store_item_discount_currency_code", "store_item_discount_setting",
        "store_bundle_sale_flag", "store_bundle_sale_price_currency_code",
        "store_bundle_sale_price", "set_sales_detail_index", "taxation_type", "tax_system_type"})
public class ItemDetail {

    /** Transaction id. */
    @JsonProperty("transaction_id")
    private String transactionId;

    /** Order sub number. */
    @JsonProperty("order_sub_number")
    private String orderSubNumber;

    /** Sales transaction id. */
    @JsonProperty("sales_transaction_id")
    private String salesTransactionId;

    /** Detail sub number. */
    @JsonProperty("detail_sub_number")
    private String detailSubNumber;

    /** Item detail sub number. */
    @JsonProperty("item_detail_sub_number")
    private String itemDetailSubNumber;

    /** Sales transaction type. */
    @JsonProperty("sales_transaction_type")
    private String salesTransactionType;

    /** System brand code. */
    @JsonProperty("system_brand_code")
    private String systemBrandCode;

    /** Level 2 item code. */
    @JsonProperty("l2_item_code")
    private String l2ItemCode;

    /** Display level 2 item code. */
    @JsonProperty("display_l2_item_code")
    private String displayL2ItemCode;

    /** Level 2 product name. */
    @JsonProperty("l2_product_name")
    private String l2ProductName;

    /** Level 3 item code. */
    @JsonProperty("l3_item_code")
    private String l3ItemCode;

    /** Level 3 pos product name. */
    @JsonProperty("l3_pos_product_name")
    private String l3PosProductName;

    /** Product classification. */
    @JsonProperty("product_classification")
    private String productClassification;

    /** Non md type. */
    @JsonProperty("non_md_type")
    private String nonMdType;

    /** Non md code. */
    @JsonProperty("non_md_code")
    private String nonMdCode;

    /** Service code. */
    @JsonProperty("service_code")
    private String serviceCode;

    /** Epc code. */
    @JsonProperty("epc_code")
    private String epcCode;

    /** G department code. */
    @JsonProperty("g_department_code")
    private String gDepartmentCode;

    /** Major category code. */
    @JsonProperty("major_category_code")
    private String majorCategoryCode;

    /** Quantity code. */
    @JsonProperty("quantity_code")
    private String quantityCode;

    /** Detail quantity. */
    @JsonProperty("detail_quantity")
    private String detailQuantity;

    /** Item cost currency code. */
    @JsonProperty("item_cost_currency_code")
    private String itemCostCurrencyCode;

    /** Item cost value. */
    @JsonProperty("item_cost_value")
    private String itemCostValue;

    /** Initial selling price currency code. */
    @JsonProperty("initial_selling_price_currency_code")
    private String initialSellingPriceCurrencyCode;

    /** Initial selling price. */
    @JsonProperty("initial_selling_price")
    private String initialSellingPrice;

    /** Bclass price currency code. */
    @JsonProperty("bclass_price_currency_code")
    private String bclassPriceCurrencyCode;

    /** Bclass price. */
    @JsonProperty("bclass_price")
    private String bclassPrice;

    /** New price currency code. */
    @JsonProperty("new_price_currency_code")
    private String newPriceCurrencyCode;

    /** New price. */
    @JsonProperty("new_price")
    private String newPrice;

    /** Retail unit price tax excluded currency code. */
    @JsonProperty("retail_unit_price_tax_excluded_currency_code")
    private String retailUnitPriceTaxExcludedCurrencyCode;

    /** Retail unit price tax excluded. */
    @JsonProperty("retail_unit_price_tax_excluded")
    private String retailUnitPriceTaxExcluded;

    /** Retail unit price tax included currency code. */
    @JsonProperty("retail_unit_price_tax_included_currency_code")
    private String retailUnitPriceTaxIncludedCurrencyCode;

    /** Retail unit price tax included. */
    @JsonProperty("retail_unit_price_tax_included")
    private String retailUnitPriceTaxIncluded;

    /** Sales amount tax excluded currency code. */
    @JsonProperty("sales_amount_tax_excluded_currency_code")
    private String salesAmountTaxExcludedCurrencyCode;

    /** Sales amount tax excluded. */
    @JsonProperty("sales_amount_tax_excluded")
    private String salesAmountTaxExcluded;

    /** Sales amount tax included currency code. */
    @JsonProperty("sales_amount_tax_included_currency_code")
    private String salesAmountTaxIncludedCurrencyCode;

    /** Sales amount tax included. */
    @JsonProperty("sales_amount_tax_included")
    private String salesAmountTaxIncluded;

    /** Calculation unavailable type. */
    @JsonProperty("calculation_unavailable_type")
    private String calculationUnavailableType;

    /** Bundle purchase applicable quantity. */
    @JsonProperty("bundle_purchase_applicable_quantity")
    private String bundlePurchaseApplicableQuantity;

    /** Bundle purchase applicable price currency code. */
    @JsonProperty("bundle_purchase_applicable_price_currency_code")
    private String bundlePurchaseApplicablePriceCurrencyCode;

    /** Bundle purchase applicable price. */
    @JsonProperty("bundle_purchase_applicable_price")
    private String bundlePurchaseApplicablePrice;

    /** Bundle purchase index. */
    @JsonProperty("bundle_purchase_index")
    private String bundlePurchaseIndex;

    /** Limited amount promotion count. */
    @JsonProperty("limited_amount_promotion_count")
    private String limitedAmountPromotionCount;

    /** Store item discount type. */
    @JsonProperty("store_item_discount_type")
    private String storeItemDiscountType;

    /** Store item discount currency code. */
    @JsonProperty("store_item_discount_currency_code")
    private String storeItemDiscountCurrencyCode;

    /** Store item discount setting. */
    @JsonProperty("store_item_discount_setting")
    private String storeItemDiscountSetting;

    /** Store bundle sale flag. */
    @JsonProperty("store_bundle_sale_flag")
    private String storeBundleSaleFlag;

    /** Store bundle sale price currency code. */
    @JsonProperty("store_bundle_sale_price_currency_code")
    private String storeBundleSalePriceCurrencyCode;

    /** Store bundle sale price. */
    @JsonProperty("store_bundle_sale_price")
    private String storeBundleSalePrice;

    /** Set sales detail index. */
    @JsonProperty("set_sales_detail_index")
    private String salesDetailIndex;

    /** Taxation type. */
    @JsonProperty("taxation_type")
    private String taxationType;

    /** Tax system type. */
    @JsonProperty("tax_system_type")
    private String taxSystemType;
}
