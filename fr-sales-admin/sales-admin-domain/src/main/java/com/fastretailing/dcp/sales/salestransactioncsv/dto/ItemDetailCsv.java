/**
 * @(#)ItemDetailCsv.java
 *
 *                        Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salestransactioncsv.dto;

import java.math.BigDecimal;
import com.github.mygreen.supercsv.annotation.CsvBean;
import com.github.mygreen.supercsv.annotation.CsvColumn;
import lombok.Data;

/**
 * Item detail csv.
 */
@Data
@CsvBean(header = true)
public class ItemDetailCsv {

    /** Transaction id. */
    @CsvColumn(number = 1, label = "transaction_id")
    private String transactionId;

    /** Order sub number. */
    @CsvColumn(number = 2, label = "order_sub_number")
    private Integer orderSubNumber;

    /** Sales transaction id. */
    @CsvColumn(number = 3, label = "sales_transaction_id")
    private String salesTransactionId;

    /** Detail sub number. */
    @CsvColumn(number = 4, label = "detail_sub_number")
    private Integer detailSubNumber;

    /** Item detail sub number. */
    @CsvColumn(number = 5, label = "item_detail_sub_number")
    private Integer itemDetailSubNumber;

    /** Sales transaction type. */
    @CsvColumn(number = 6, label = "sales_transaction_type")
    private String salesTransactionType;

    /** System brand code. */
    @CsvColumn(number = 7, label = "system_brand_code")
    private String systemBrandCode;

    /** l2 item code. */
    @CsvColumn(number = 8, label = "l2_item_code")
    private String l2ItemCode;

    /** Display l2 item code. */
    @CsvColumn(number = 9, label = "display_l2_item_code")
    private String displayL2ItemCode;

    /** L2 product name. */
    @CsvColumn(number = 10, label = "l2_product_name")
    private String l2ProductName;

    /** L3 item code. */
    @CsvColumn(number = 11, label = "l3_item_code")
    private String l3ItemCode;

    /** L3 pos product name. */
    @CsvColumn(number = 12, label = "l3_pos_product_name")
    private String l3PosProductName;

    /** Product classification. */
    @CsvColumn(number = 13, label = "product_classification")
    private String productClassification;

    /** Non md type. */
    @CsvColumn(number = 14, label = "non_md_type")
    private String nonMdType;

    /** Non md code. */
    @CsvColumn(number = 15, label = "non_md_code")
    private String nonMdCode;

    /** Service code. */
    @CsvColumn(number = 16, label = "service_code")
    private String serviceCode;

    /** Epc code. */
    @CsvColumn(number = 17, label = "epc_code")
    private String epcCode;

    /** G department code. */
    @CsvColumn(number = 18, label = "g_department_code")
    private String gDepartmentCode;

    /** Major category code. */
    @CsvColumn(number = 19, label = "major_category_code")
    private String majorCategoryCode;

    /** Quantity code. */
    @CsvColumn(number = 20, label = "quantity_code")
    private String quantityCode;

    /** Detail quantity. */
    @CsvColumn(number = 21, label = "detail_quantity")
    private BigDecimal detailQuantity;

    /** Item cost currency code. */
    @CsvColumn(number = 22, label = "item_cost_currency_code")
    private String itemCostCurrencyCode;

    /** Item cost value. */
    @CsvColumn(number = 23, label = "item_cost_value")
    private BigDecimal itemCostValue;

    /** Initial selling price currency code. */
    @CsvColumn(number = 24, label = "initial_selling_price_currency_code")
    private String initialSellingPriceCurrencyCode;

    /** Initial selling price. */
    @CsvColumn(number = 25, label = "initial_selling_price")
    private BigDecimal initialSellingPrice;

    /** B Class price currency code. */
    @CsvColumn(number = 26, label = "bclass_price_currency_code")
    private String bclassPriceCurrencyCode;

    /** B Class price. */
    @CsvColumn(number = 27, label = "bclass_price")
    private BigDecimal bclassPrice;

    /** New price currency code. */
    @CsvColumn(number = 28, label = "new_price_currency_code")
    private String newPriceCurrencyCode;

    /** New price. */
    @CsvColumn(number = 29, label = "new_price")
    private BigDecimal newPrice;

    /** Retail unit price tax excluded currency code. */
    @CsvColumn(number = 30, label = "retail_unit_price_tax_excluded_currency_code")
    private String retailUnitPriceTaxExcludedCurrencyCode;

    /** Retail unit price tax excluded. */
    @CsvColumn(number = 31, label = "retail_unit_price_tax_excluded")
    private BigDecimal retailUnitPriceTaxExcluded;

    /** Retail unit price tax included currency code. */
    @CsvColumn(number = 32, label = "retail_unit_price_tax_included_currency_code")
    private String retailUnitPriceTaxIncludedCurrencyCode;

    /** Retail unit price tax included. */
    @CsvColumn(number = 33, label = "retail_unit_price_tax_included")
    private BigDecimal retailUnitPriceTaxIncluded;

    /** Sales amount tax excluded currency code. */
    @CsvColumn(number = 34, label = "sales_amount_tax_excluded_currency_code")
    private String salesAmountTaxExcludedCurrencyCode;

    /** Sales amount tax excluded. */
    @CsvColumn(number = 35, label = "sales_amount_tax_excluded")
    private BigDecimal salesAmountTaxExcluded;

    /** Sales amount tax included currency code. */
    @CsvColumn(number = 36, label = "sales_amount_tax_included_currency_code")
    private String salesAmountTaxIncludedCurrencyCode;

    /** Sales amount tax included. */
    @CsvColumn(number = 37, label = "sales_amount_tax_included")
    private BigDecimal salesAmountTaxIncluded;

    /** Calculation unavailable type. */
    @CsvColumn(number = 38, label = "calculation_unavailable_type")
    private String calculationUnavailableType;

    /** Bundle purchase applicable. */
    @CsvColumn(number = 39, label = "bundle_purchase_applicable_quantity")
    private BigDecimal bundlePurchaseApplicableQuantity;

    /** Bundle purchase applicable price currency code. */
    @CsvColumn(number = 40, label = "bundle_purchase_applicable_price_currency_code")
    private String bundlePurchaseApplicablePriceCurrencyCode;

    /** Bundle purchase applicable price. */
    @CsvColumn(number = 41, label = "bundle_purchase_applicable_price")
    private BigDecimal bundlePurchaseApplicablePrice;

    /** bundle purchase index. */
    @CsvColumn(number = 42, label = "bundle_purchase_index")
    private Integer bundlePurchaseIndex;

    /** Limited amount promotion count. */
    @CsvColumn(number = 43, label = "limited_amount_promotion_count")
    private Integer limitedAmountPromotionCount;

    /** Store item discount type. */
    @CsvColumn(number = 44, label = "store_item_discount_type")
    private String storeItemDiscountType;

    /** Store item discount currency code. */
    @CsvColumn(number = 45, label = "store_item_discount_currency_code")
    private String storeItemDiscountCurrencyCode;

    /** Store item discount setting. */
    @CsvColumn(number = 46, label = "store_item_discount_setting")
    private BigDecimal storeItemDiscountSetting;

    /** Store bundle sale flag. */
    @CsvColumn(number = 47, label = "store_bundle_sale_flag")
    private boolean storeBundleSaleFlag;

    /** Store bundle sale price currency code. */
    @CsvColumn(number = 48, label = "store_bundle_sale_price_currency_code")
    private String storeBundleSalePriceCurrencyCode;

    /** Store bundle sale price. */
    @CsvColumn(number = 49, label = "store_bundle_sale_price")
    private BigDecimal storeBundleSalePrice;

    /** Set sales detail index. */
    @CsvColumn(number = 50, label = "set_sales_detail_index")
    private Integer setSalesDetailIndex;

    /** Taxation type. */
    @CsvColumn(number = 51, label = "taxation_type")
    private String taxationType;

    /** Tax system type. */
    @CsvColumn(number = 52, label = "tax_system_type")
    private String taxSystemType;

}
