package com.fastretailing.dcp.sales.common.entity.optional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.Data;

/**
 * Sales error sales transaction detail and info.
 */
@Data
public class SalesErrorSalesTransactionDetailAndInfo {

    private String transactionId;

    private Integer orderSubNumber;

    private String salesTransactionId;

    private Integer detailSubNumber;

    private Integer itemDetailSubNumber;

    private String salesTransactionType;

    private String systemBrandCode;

    private String l2ItemCode;

    private String displayL2ItemCode;

    private String l2ProductName;

    private String l3ItemCode;

    private String l3PosProductName;

    private String productClassification;

    private String nonMdType;

    private String nonMdCode;

    private String serviceCode;

    private String epcCode;

    private String gDepartmentCode;

    private String majorCategoryCode;

    private String quantityCode;

    private BigDecimal detailQuantity;

    private String itemCostCurrencyCode;

    private BigDecimal itemCostValue;

    private String initialSellingPriceCurrencyCode;

    private BigDecimal initialSellingPrice;

    private String bclassPriceCurrencyCode;

    private BigDecimal bclassPrice;

    private String newPriceCurrencyCode;

    private BigDecimal newPrice;

    private String retailUnitPriceTaxExcludedCurrencyCode;

    private BigDecimal retailUnitPriceTaxExcluded;

    private String retailUnitPriceTaxIncludedCurrencyCode;

    private BigDecimal retailUnitPriceTaxIncluded;

    private String salesAmountTaxExcludedCurrencyCode;

    private BigDecimal salesAmountTaxExcluded;

    private String salesAmountTaxIncludedCurrencyCode;

    private BigDecimal salesAmountTaxIncluded;

    private String calculationUnavailableType;

    private String orderStatusUpdateDate;

    private OffsetDateTime orderStatusLastUpdateDateTime;

    private String orderStatus;

    private String orderSubstatus;

    private String bookingStoreCode;

    private String bookingStoreSystemBrandCode;

    private String bookingStoreSystemBusinessCode;

    private String bookingStoreSystemCountryCode;

    private String shipmentStoreCode;

    private String shipmentStoreSystemBrandCode;

    private String shipmentStoreSystemBusinessCode;

    private String shipmentStoreSystemCountryCode;

    private String receiptStoreCode;

    private String receiptStoreSystemBrandCode;

    private String receiptStoreSystemBusinessCode;

    private String receiptStoreSystemCountryCode;

    private String contributionSalesRepresentative;

    private String customerId;

    private BigDecimal bundlePurchaseApplicableQuantity;

    private String bundlePurchaseApplicablePriceCurrencyCode;

    private BigDecimal bundlePurchaseApplicablePrice;

    private Integer bundlePurchaseIndex;

    private Integer limitedAmountPromotionCount;

    private String storeItemDiscountType;

    private String storeItemDiscountCurrencyCode;

    private BigDecimal storeItemDiscountSetting;

    private boolean storeBundleSaleFlag;

    private String storeBundleSalePriceCurrencyCode;

    private BigDecimal storeBundleSalePrice;

    private Integer setSalesDetailIndex;

    private String taxationType;

    private String taxSystemType;

    private String keyCode;

    private String codeValue1;

    private String codeValue2;

    private String codeValue3;

    private String codeValue4;

    private String name1;

    private String name2;

    private String name3;

    private String name4;
}
