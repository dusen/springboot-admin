/**
 * @(#)ImportTransactionItemDetail.java
 *
 *                                      Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salestransactioncorrectionhistorydetail.form.transactionomport;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fastretailing.dcp.common.validation.annotation.Alphanumeric;
import com.fastretailing.dcp.common.validation.annotation.StringDate;
import com.fastretailing.dcp.sales.importtransaction.dto.groups.Correction;
import com.fastretailing.dcp.sales.importtransaction.dto.groups.Insert;
import com.fastretailing.dcp.storecommon.deserialize.OneZeroToBooleanDeserializer;
import com.fastretailing.dcp.storecommon.dto.Price;
import lombok.Data;

/**
 * Import transaction item detail.
 * 
 */
@Data
public class ImportTransactionItemDetail {

    /** Format order status update date. */
    @StringDate(patterns = "yyyy/MM/dd")
    private String formatOrderStatusUpdateDate;

    /** Format order status last update date time. */
    private String formatOrderStatusLastUpdateDateTime;

    /** System brand code. */
    @JsonProperty("system_brand_code")
    @Size(max = 4)
    @Alphanumeric
    private String systemBrandCode;

    /** Detail sub number. */
    @JsonProperty("detail_sub_number")
    @Max(9999)
    private Integer detailSubNumber;

    /** Detail list sales transaction type. */
    @JsonProperty("detail_list_sales_transaction_type")
    @Size(max = 6)
    @Alphanumeric
    @NotBlank(groups = {Insert.class, Correction.class})
    private String detailListSalesTransactionType;

    /** L2 item code. */
    @JsonProperty("l2_item_code")
    @Size(max = 20)
    @Alphanumeric
    private String l2ItemCode;

    /** View_l2_item_code. */
    @JsonProperty("view_l2_item_code")
    @Size(max = 20)
    @Alphanumeric
    private String viewL2ItemCode;

    /** L2 product name. */
    @JsonProperty("l2_product_name")
    @Size(max = 250)
    private String l2ProductName;

    /** L3 item code. */
    @JsonProperty("l3_item_code")
    @Size(max = 25)
    @Alphanumeric
    @NotBlank(groups = {Insert.class, Correction.class})
    private String l3ItemCode;

    /** L3 pos product name. */
    @JsonProperty("l3_pos_product_name")
    @Size(max = 250)
    private String l3PosProductName;

    /** EPC code. */
    @JsonProperty("epc_code")
    @Size(max = 24)
    @Alphanumeric
    private String epcCode;

    /** G department code. */
    @JsonProperty("g_department_code")
    @Size(max = 6)
    @Alphanumeric
    @NotBlank(groups = {Insert.class, Correction.class})
    private String gDepartmentCode;

    /** Department code. */
    @JsonProperty("dept_code")
    @Max(9999)
    private Integer deptCode;

    /** Quantity code. */
    @JsonProperty("quantity_code")
    @Size(max = 1)
    @Alphanumeric
    private String quantityCode;

    /** Item quantity. */
    @JsonProperty("item_qty")
    @Max(9999)
    @NotNull(groups = {Insert.class, Correction.class})
    private Integer itemQty;

    /** Item price. */
    @JsonProperty("item_cost")
    @Valid
    private Price itemCost;

    /** Initial selling price. */
    @JsonProperty("initial_selling_price")
    @Valid
    @NotNull(groups = {Insert.class, Correction.class})
    private Price initialSellingPrice;

    /** B item selling price. */
    @JsonProperty("b_item_selling_price")
    @Valid
    private Price bItemSellingPrice;

    /** Item new price. */
    @JsonProperty("item_new_price")
    @Valid
    private Price itemNewPrice;

    /** Item unit price tax excluded. */
    @JsonProperty("item_unit_price_tax_excluded")
    @Valid
    private Price itemUnitPriceTaxExcluded;

    /** Item unit price tax included. */
    @JsonProperty("item_unit_price_tax_included")
    @Valid
    private Price itemUnitPriceTaxIncluded;

    /** Item sales amount tax excluded. */
    @JsonProperty("item_sales_amt_tax_excluded")
    @Valid
    private Price itemSalesAmtTaxExcluded;

    /** Item sales amount tax included. */
    @JsonProperty("item_sales_amt_tax_included")
    @Valid
    private Price itemSalesAmtTaxIncluded;

    /** Order status update date. */
    @JsonProperty("order_status_update_date")
    @Size(max = 10)
    @StringDate(patterns = "yyyy-MM-dd")
    private String orderStatusUpdateDate;

    /** Order status last update date time. */
    @JsonProperty("order_status_last_update_date_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssX",
            timezone = "UTC")
    @Past
    private OffsetDateTime orderStatusLastUpdateDateTime;

    /** Order status. */
    @JsonProperty("order_status")
    @Size(max = 50)
    @Pattern(regexp = "[a-zA-Z0-9-_]*")
    private String orderStatus;

    /** Order sub status. */
    @JsonProperty("order_substatus")
    @Size(max = 50)
    @Pattern(regexp = "[a-zA-Z0-9-_]*")
    private String orderSubstatus;

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

    /** Contribution sales representative. */
    @JsonProperty("contribution_sales_representative")
    @Size(max = 10)
    @Alphanumeric
    private String contributionSalesRepresentative;

    /** Customer ID. */
    @JsonProperty("customer_id")
    @Size(max = 30)
    @Pattern(regexp = "[a-zA-Z0-9-_]*")
    private String customerId;

    /** Bundle purchase quantity. */
    @JsonProperty("bundle_purchase_qty")
    @Max(99)
    private Integer bundlePurchaseQty;

    /** Bundle purchase price. */
    @JsonProperty("bundle_purchase_price")
    @Valid
    private Price bundlePurchasePrice;

    /** Bundle purchase index. */
    @JsonProperty("bundle_purchase_index")
    @Max(999)
    private Integer bundlePurchaseIndex;

    /** Limited amount promotion count. */
    @JsonProperty("limited_amount_promotion_count")
    @Max(99)
    private Integer limitedAmountPromotionCount;

    /** Calculation unavailable type. */
    @JsonProperty("calculation_unavailable_type")
    @Size(max = 10)
    @Alphanumeric
    private String calculationUnavailableType;

    /** Item mount discount type. */
    @JsonProperty("item_mount_discount_type")
    @Size(max = 10)
    @Alphanumeric
    private String itemMountDiscountType;

    /** Item discount amount. */
    @JsonProperty("item_discount_amount")
    @Valid
    private Price itemDiscountAmount;

    /** Set sales flag. */
    @JsonProperty("set_sales_flag")
    @JsonDeserialize(using = OneZeroToBooleanDeserializer.class)
    private Boolean bundleSalesFlag;

    /** Set sales price. */
    @JsonProperty("set_sales_price")
    @Valid
    private Price bundleSalesPrice;

    /** Set sales detail index. */
    @JsonProperty("set_sales_detail_index")
    @Max(999)
    private Integer bundleSalesDetailIndex;

    /** Item detail number. */
    @JsonProperty("item_detail_number")
    @Max(9999)
    private Integer itemDetailNumber;

    /** Item taxation type. */
    @JsonProperty("item_taxation_type")
    @Size(max = 1)
    @Alphanumeric
    @NotBlank(groups = {Insert.class, Correction.class})
    private String itemTaxationType;

    /** Item tax kind. */
    @JsonProperty("item_tax_kind")
    @Size(max = 10)
    @Alphanumeric
    private String itemTaxKind;

    /** Non item detail list by item. */
    @JsonProperty("non_item_detail_list_by_item")
    private List<ImportNonItemDetail> nonItemDetailListByItem = new ArrayList<>();

    /** Item discount list. */
    @JsonProperty("item_discount_list")
    private List<ImportItemDiscount> itemDiscountList;

    /** Item tax detail list. */
    @JsonProperty("item_tax_detail_list")
    private List<ImportItemTaxDetail> itemTaxDetailList;

}
