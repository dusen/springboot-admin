/**
 * @(#)ImportNonItemDetail.java
 *
 *                              Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salestransactioncorrectionhistorydetail.form.transactionomport;

import java.time.OffsetDateTime;
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
import com.fastretailing.dcp.common.validation.annotation.Alphanumeric;
import com.fastretailing.dcp.common.validation.annotation.StringDate;
import com.fastretailing.dcp.sales.importtransaction.dto.groups.Correction;
import com.fastretailing.dcp.sales.importtransaction.dto.groups.Insert;
import com.fastretailing.dcp.storecommon.dto.Price;
import lombok.Data;

/**
 * Import non item detail.
 * 
 */
@Data
public class ImportNonItemDetail {

    /** Format order status update date. */
    private String formatOrderStatusUpdateDate;

    /** Format order status last update date time. */
    private String formatOrderStatusLastUpdateDateTime;

    /** Non item detail number. */
    @JsonProperty("non_item_detail_number")
    @Max(9999)
    private Integer nonItemDetailNumber;

    /** Non md detail list sales transaction type. */
    @JsonProperty("non_md_detail_list_sales_transaction_type")
    @Size(max = 6)
    @Alphanumeric
    @NotBlank(groups = {Insert.class, Correction.class})
    private String nonMdDetailListSalesTransactionType;

    /** nonItemDetailSalesLinkageType. */
    private Integer nonItemDetailSalesLinkageType;

    /** Item detail sub number. */
    @JsonProperty("item_detail_sub_number")
    @Max(9999)
    private Integer itemDetailSubNumber;

    /** Detail sub number. */
    @JsonProperty("detail_sub_number")
    @Max(9999)
    private Integer detailSubNumber;

    /** Non md type. */
    @JsonProperty("non_md_type")
    @Size(max = 6)
    @Pattern(regexp = "[a-zA-Z0-9-_]*")
    private String nonMdType;

    /** Non-Item Code. */
    @JsonProperty("non_item_code")
    @Size(max = 25)
    @Alphanumeric
    @NotBlank(groups = {Insert.class, Correction.class})
    private String nonItemCode;

    /** Service code. */
    @JsonProperty("service_code")
    @Size(max = 50)
    @Pattern(regexp = "[a-zA-Z0-9-_]*")
    private String serviceCode;

    /** Pos_non item name. */
    @JsonProperty("pos_non_item_name")
    @Size(max = 250)
    private String posNonItemName;

    /** Quantity code. */
    @JsonProperty("quantity_code")
    @Size(max = 1)
    @Alphanumeric
    private String quantityCode;

    /** Non item quantity. */
    @JsonProperty("non_item_qty")
    @Max(9999)
    @NotNull(groups = {Insert.class, Correction.class})
    private Integer nonItemQty;

    /** Non item unit price tax excluded. */
    @JsonProperty("non_item_unit_price_tax_excluded")
    @Valid
    private Price nonItemUnitPriceTaxExcluded;

    /** Non item unit price tax included. */
    @JsonProperty("non_item_unit_price_tax_included")
    @Valid
    private Price nonItemUnitPriceTaxIncluded;

    /** Non item sales amount tax excluded. */
    @JsonProperty("non_item_sales_amt_tax_excluded")
    @Valid
    private Price nonItemSalesAmtTaxExcluded;

    /** Non item sales amount tax included. */
    @JsonProperty("non_item_sales_amt_tax_included")
    @Valid
    private Price nonItemSalesAmtTaxIncluded;

    /** Non item new price. */
    @JsonProperty("non_item_new_price")
    @Valid
    private Price nonItemNewPrice;

    /** Non calculation non item type. */
    @JsonProperty("non_calculation_non_item_type")
    @Size(max = 10)
    @Alphanumeric
    private String nonCalculationNonItemType;

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

    /** Non item taxation type. */
    @JsonProperty("non_item_taxation_type")
    @Size(max = 1)
    @Alphanumeric
    @NotBlank(groups = {Insert.class, Correction.class})
    private String nonItemTaxationType;

    /** Non item tax kind. */
    @JsonProperty("non_item_tax_kind")
    @Size(max = 10)
    @Alphanumeric
    private String nonItemTaxKind;

    /** Non item info. */
    @JsonProperty("non_item_info")
    @Valid
    private ImportNonItemInfo nonItemInfo;

    /** Non item discount detail list. */
    @JsonProperty("non_item_discount_detail_list")
    private List<ImportNonItemDiscountDetail> nonItemDiscountDetailList;

    /** Non item tax detail list. */
    @JsonProperty("non_item_tax_detail_list")
    private List<ImportNonItemTaxDetail> nonItemTaxDetailList;

}
