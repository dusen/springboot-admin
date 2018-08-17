/**
 * @(#)TransactionInquiryOptional.java
 *
 *                                     Copyright (c) 2018 Fast Retailing Corporation. .
 */

package com.fastretailing.dcp.sales.common.entity.optional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.Data;
import lombok.ToString;

/**
 * Transaction inquiry optional entity. .
 */
@Data
@ToString(callSuper = true)
public class TransactionInquiryOptional {

    /** Integrated order id. */
    private String integratedOrderId;

    /** Order barcode number. */
    private String orderBarcodeNumber;

    /** Store code. */
    private String storeCode;

    /** System brand code. */
    private String systemBrandCode;

    /** System business code. */
    private String systemBusinessCode;

    /** System country code. */
    private String systemCountryCode;

    /** Channel code. */
    private String channelCode;

    /** Customer id. */
    private String customerId;

    /** Order confirmation business date. */
    private String orderConfirmationBusinessDate;

    /** Order Confirmation date time. */
    private OffsetDateTime orderConfirmationDateTime;

    /** Data alteration sales linkage type. */
    private Integer dataAlterationSalesLinkageType;

    /** Data alteration backbone linkage type. */
    private Integer dataAlterationBackboneLinkageType;

    /** Data alteration flag. */
    private boolean dataAlterationFlag;

    /** Data alteration user id. */
    private String dataAlterationUserId;

    /** Header order sub Number. */
    private Integer headerOrderSubNumber;

    /** Header sales transaction id. */
    private String headerSalesTransactionId;

    /** Header integrated order id. */
    private String headerIntegratedOrderId;

    /** Header sales transaction sub number. */
    private Integer headerSalesTransactionSubNumber;

    /** Header store code. */
    private String headerStoreCode;

    /** Header data creation date time. */
    private OffsetDateTime headerDataCreationDateTime;

    /** Header data creation business date. */
    private String headerDataCreationBusinessDate;

    /** Header cash register no. */
    private Integer headerCashRegisterNo;

    /** Header receipt no. */
    private String headerReceiptNo;

    /** Header sales linkage type. */
    private Integer headerSalesLinkageType;

    /** Header sales transaction type. */
    private String headerSalesTransactionType;

    /** Header return type. */
    private Integer headerReturnType;

    /** Header system brand code. */
    private String headerSystemBrandCode;

    /** Header system business code. */
    private String headerSystemBusinessCode;

    /** Header system country code. */
    private String headerSystemCountryCode;

    /** Header channel code. */
    private String headerChannelCode;

    /** Header order status. */
    private String headerOrderStatus;

    /** Header order substatus. */
    private String headerOrderSubstatus;

    /** Header order status update date. */
    private String headerOrderStatusUpdateDate;

    /** Header order status last update date time. */
    private OffsetDateTime headerOrderStatusLastUpdateDateTime;

    /** Header order cancelled date time. */
    private OffsetDateTime headerOrderCancelledDateTime;

    /** Header booking store code. */
    private String headerBookingStoreCode;

    /** Header booking store system brand code. */
    private String headerBookingStoreSystemBrandCode;

    /** Header booking store system business code. */
    private String headerBookingStoreSystemBusinessCode;

    /** Header booking store system country code. */
    private String headerBookingStoreSystemCountryCode;

    /** Header payment status. */
    private String headerPaymentStatus;

    /** Header payment store code. */
    private String headerPaymentStoreCode;

    /** Header payment store system brand code. */
    private String headerPaymentStoreSystemBrandCode;

    /** Header payment store system business code. */
    private String headerPaymentStoreSystemBusinessCode;

    /** Header payment store system country code. */
    private String headerPaymentStoreSystemCountryCode;

    /** Header transfer out status. */
    private String headerTransferOutStatus;

    /** Header shipment status. */
    private String headerShipmentStatus;

    /** Header shipment store code. */
    private String headerShipmentStoreCode;

    /** Header shipment store system brand code. */
    private String headerShipmentStoreSystemBrandCode;

    /** Header shipment store system business code. */
    private String headerShipmentStoreSystemBusinessCode;

    /** Header shipment store system country code. */
    private String headerShipmentStoreSystemCountryCode;

    /** Header receiving status. */
    private String headerReceivingStatus;

    /** Header receipt store code. */
    private String headerReceiptStoreCode;

    /** Header receipt store system brand code. */
    private String headerReceiptStoreSystemBrandCode;

    /** Header receipt store system business code. */
    private String headerReceiptStoreSystemBusinessCode;

    /** Header receipt store system country code. */
    private String headerReceiptStoreSystemCountryCode;

    /** Header customer id. */
    private String headerCustomerId;

    /** Header customer type. */
    private String headerCustomerType;

    /** Header order number for store payment. */
    private String headerOrderNumberForStorePayment;

    /** Header advance received store code. */
    private String headerAdvanceReceivedStoreCode;

    /** Header advance received store system brand code. */
    private String headerAdvanceReceivedStoreSystemBrandCode;

    /** Header advance received store system business code. */
    private String headerAdvanceReceivedStoreSystemBusinessCode;

    /** Header advance received store system country code. */
    private String headerAdvanceReceivedStoreSystemCountryCode;

    /** Header operator code. */
    private String headerOperatorCode;

    /** Header original transaction id. */
    private String headerOriginalTransactionId;

    /** Header original cash register no. */
    private Integer headerOriginalCashRegisterNo;

    /** Header original receipt no. */
    private String headerOriginalReceiptNo;

    /** Header deposit currency code. */
    private String headerDepositCurrencyCode;

    /** Header deposit value. */
    private BigDecimal headerDepositValue;

    /** Header change currency code. */
    private String headerChangeCurrencyCode;

    /** Header change value. */
    private BigDecimal headerChangeValue;

    /** Header receipt no for credit card cancellation. */
    private String headerReceiptNoForCreditCardCancellation;

    /** Header receipt no for credit card. */
    private String headerReceiptNoForCreditCard;

    /** Header receipt issued flag. */
    private boolean headerReceiptIssuedFlag;

    /** Header E receipt target flag. */
    private boolean headerEReceiptTargetFlag;

    /** Header processing company code. */
    private String headerProcessingCompanyCode;

    /** Header employee sale flag. */
    private boolean headerEmployeeSaleFlag;

    /** Header consistency sales flag. */
    private boolean headerConsistencySalesFlag;

    /** Header corporate id. */
    private String headerCorporateId;

    /** Header sales transaction discount flag. */
    private boolean headerSalesTransactionDiscountFlag;

    /** Header sales transaction discount type. */
    private String headerSalesTransactionDiscountType;

    /** Header sales transaction discount amount rate currency code. */
    private String headerSalesTransactionDiscountAmountRateCurrencyCode;

    /** Header sales transaction discount amount rate. */
    private BigDecimal headerSalesTransactionDiscountAmountRate;

    /** Header token code. */
    private String headerTokenCode;

    /** Header return complete flag. */
    private boolean headerReturnCompleteFlag;

    /** Header cancelled flag. */
    private boolean headerCancelledFlag;

    /** Detail order sub number. */
    private Integer detailOrderSubNumber;

    /** Detail sales transaction id. */
    private String detailSalesTransactionId;

    /** Detail detail sub number. */
    private Integer detailDetailSubNumber;

    /** Detail item detail sub number. */
    private Integer detailItemDetailSubNumber;

    /** Detail sales transaction type. */
    private String detailSalesTransactionType;

    /** Detail system brand code. */
    private String detailSystemBrandCode;

    /** Detail system business code. */
    private String detailSystemBusinessCode;

    /** Detail system country code. */
    private String detailSystemCountryCode;

    /** Detail L2 item code. */
    private String detailL2ItemCode;

    /** Detail display L2 item code. */
    private String detailDisplayL2ItemCode;

    /** Detail L2 product name. */
    private String detailL2ProductName;

    /** Detail L3 item code. */
    private String detailL3ItemCode;

    /** Detail L3 pos product name. */
    private String detailL3PosProductName;

    /** Detail product classification. */
    private String detailProductClassification;

    /** Detail non md type. */
    private String detailNonMdType;

    /** Detail non md code. */
    private String detailNonMdCode;

    /** Detail service code. */
    private String detailServiceCode;

    /** Detail epc code. */
    private String detailEpcCode;

    /** Detail G department code. */
    private String detailGDepartmentCode;

    /** Detail major category code. */
    private String detailMajorCategoryCode;

    /** Detail quantity code. */
    private String detailQuantityCode;

    /** Detail detail quantity. */
    private Integer detailDetailQuantity;

    /** Detail item cost currency code. */
    private String detailItemCostCurrencyCode;

    /** Detail item cost value. */
    private BigDecimal detailItemCostValue;

    /** Detail initial selling price currency code. */
    private String detailInitialSellingPriceCurrencyCode;

    /** Detail initial selling price. */
    private BigDecimal detailInitialSellingPrice;

    /** Detail B class price currency code. */
    private String detailBclassPriceCurrencyCode;

    /** Detail B class price. */
    private BigDecimal detailBclassPrice;

    /** Detail new price currency code. */
    private String detailNewPriceCurrencyCode;

    /** Detail new price. */
    private BigDecimal detailNewPrice;

    /** Detail retail unit price tax excluded currency code. */
    private String detailRetailUnitPriceTaxExcludedCurrencyCode;

    /** Detail retail unit price tax excluded. */
    private BigDecimal detailRetailUnitPriceTaxExcluded;

    /** Detail retail unit price tax included currency code. */
    private String detailRetailUnitPriceTaxIncludedCurrencyCode;

    /** Detail retail unit price tax included. */
    private BigDecimal detailRetailUnitPriceTaxIncluded;

    /** Detail sales amount tax excluded currency code. */
    private String detailSalesAmountTaxExcludedCurrencyCode;

    /** Detail sales amount tax excluded. */
    private BigDecimal detailSalesAmountTaxExcluded;

    /** Detail sales amount tax included currency code. */
    private String detailSalesAmountTaxIncludedCurrencyCode;

    /** Detail sales amount tax included. */
    private BigDecimal detailSalesAmountTaxIncluded;

    /** Detail calculation unavailable type. */
    private String detailCalculationUnavailableType;

    /** Detail order status update date. */
    private String detailOrderStatusUpdateDate;

    /** Detail order status last update date time. */
    private OffsetDateTime detailOrderStatusLastUpdateDateTime;

    /** Detail order status. */
    private String detailOrderStatus;

    /** Detail order sub status. */
    private String detailOrderSubstatus;

    /** Detail booking store Code. */
    private String detailBookingStoreCode;

    /** Detail booking store system brand code. */
    private String detailBookingStoreSystemBrandCode;

    /** Detail booking store system business code. */
    private String detailBookingStoreSystemBusinessCode;

    /** Detail booking store system country code. */
    private String detailBookingStoreSystemCountryCode;

    /** Detail shipment store Code. */
    private String detailShipmentStoreCode;

    /** Detail shipment store system brand code. */
    private String detailShipmentStoreSystemBrandCode;

    /** Detail shipment store system business code. */
    private String detailShipmentStoreSystemBusinessCode;

    /** Detail shipment store system country code. */
    private String detailShipmentStoreSystemCountryCode;

    /** Detail receipt store Code. */
    private String detailReceiptStoreCode;

    /** Detail receipt store system brand code. */
    private String detailReceiptStoreSystemBrandCode;

    /** Detail receipt store system business code. */
    private String detailReceiptStoreSystemBusinessCode;

    /** Detail receipt store system country code. */
    private String detailReceiptStoreSystemCountryCode;

    /** Detail contribution sales representative. */
    private String detailContributionSalesRepresentative;

    /** Detail customer id. */
    private String detailCustomerId;

    /** Detail bundle purchase applicable quantity. */
    private Integer detailBundlePurchaseApplicableQuantity;

    /** Detail bundle purchase applicable price currency code. */
    private String detailBundlePurchaseApplicablePriceCurrencyCode;

    /** Detail bundle purchase applicable price. */
    private BigDecimal detailBundlePurchaseApplicablePrice;

    /** Detail bundle purchase index. */
    private Integer detailBundlePurchaseIndex;

    /** Detail limited amount promotion count. */
    private Integer detailLimitedAmountPromotionCount;

    /** Detail store item discount type. */
    private String detailStoreItemDiscountType;

    /** Detail store item discount currency code. */
    private String detailStoreItemDiscountCurrencyCode;

    /** Detail store item discount setting. */
    private BigDecimal detailStoreItemDiscountSetting;

    /** Detail store bundle sale flag. */
    private boolean detailStoreBundleSaleFlag;

    /** Detail store bundle sale price currency code. */
    private String detailStoreBundleSalePriceCurrencyCode;

    /** Detail store bundle sale price. */
    private BigDecimal detailStoreBundleSalePrice;

    /** Detail set sales detail index. */
    private Integer detailSetSalesDetailIndex;

    /** Detail taxation type. */
    private String detailTaxationType;

    /** Detail tax system type. */
    private String detailTaxSystemType;

    /** Detail return complete flag. */
    private boolean detailReturnCompleteFlag;

    /** Detail information order sub number. */
    private Integer detailInfoOrderSubNumber;

    /** Detail information sales transaction id. */
    private String detailInfoSalesTransactionId;

    /** Detail information detail sub number. */
    private Integer detailInfoDetailSubNumber;

    /** Detail information item detail sub number. */
    private Integer detailInfoItemDetailSubNumber;

    /** Detail information key code. */
    private String detailInfoKeyCode;

    /** Detail information code value 1. */
    private String detailInfoCodeValue1;

    /** Detail information code value 2. */
    private String detailInfoCodeValue2;

    /** Detail information code value 3. */
    private String detailInfoCodeValue3;

    /** Detail information code value 4. */
    private String detailInfoCodeValue4;

    /** Detail information name 1. */
    private String detailInfoName1;

    /** Detail information name 2. */
    private String detailInfoName2;

    /** Detail information name 3. */
    private String detailInfoName3;

    /** Detail information name 4. */
    private String detailInfoName4;

    /** Discount order sub number. */
    private Integer discountOrderSubNumber;

    /** Discount sales transaction id. */
    private String discountSalesTransactionId;

    /** Discount detail sub number. */
    private Integer discountDetailSubNumber;

    /** Discount promotion type. */
    private String discountPromotionType;

    /** Discount promotion no. */
    private String discountPromotionNo;

    /** Discount store discount type. */
    private String discountStoreDiscountType;

    /** Discount item discount sub number. */
    private Integer discountItemDiscountSubNumber;

    /** Discount quantity code. */
    private String discountQuantityCode;

    /** Discount discount quantity. */
    private Integer discountDiscountQuantity;

    /** Discount discount amount tax excluded currency code. */
    private String discountDiscountAmountTaxExcludedCurrencyCode;

    /** Discount discount amount tax excluded. */
    private BigDecimal discountDiscountAmountTaxExcluded;

    /** Discount discount amount tax included currency code. */
    private String discountDiscountAmountTaxIncludedCurrencyCode;

    /** Discount discount amount tax included. */
    private BigDecimal discountDiscountAmountTaxIncluded;

    /** Tax order sub number. */
    private Integer taxOrderSubNumber;

    /** Tax sales transaction id. */
    private String taxSalesTransactionId;

    /** Tax detail sub number. */
    private Integer taxDetailSubNumber;

    /** Tax tax group. */
    private String taxTaxGroup;

    /** Tax tax name. */
    private String taxTaxName;

    /** Tax tax sub number. */
    private Integer taxTaxSubNumber;

    /** Tax tax amount sign. */
    private String taxTaxAmountSign;

    /** Tax tax amount currency code. */
    private String taxTaxAmountCurrencyCode;

    /** Tax tax amount value. */
    private BigDecimal taxTaxAmountValue;

    /** Tax tax rate. */
    private BigDecimal taxTaxRate;

    /** Tender order sub number. */
    private Integer tenderOrderSubNumber;

    /** Tender sales transaction id. */
    private String tenderSalesTransactionId;

    /** Tender tender group. */
    private String tenderTenderGroup;

    /** Tender tender id. */
    private String tenderTenderId;

    /** Tender tender sub number. */
    private Integer tenderTenderSubNumber;

    /** Tender payment sign. */
    private String tenderPaymentSign;

    /** Tender tax included payment amount currency code. */
    private String tenderTaxIncludedPaymentAmountCurrencyCode;

    /** Tender tax included payment amount value. */
    private BigDecimal tenderTaxIncludedPaymentAmountValue;

    /** Tender information order sub number. */
    private Integer tenderInfoOrderSubNumber;

    /** Tender information sales transaction id. */
    private String tenderInfoSalesTransactionId;

    /** Tender information tender group. */
    private String tenderInfoTenderGroup;

    /** Tender information tender id. */
    private String tenderInfoTenderId;

    /** Tender information tender sub number. */
    private Integer tenderInfoTenderSubNumber;

    /** Tender information discount value currency code. */
    private String tenderInfoDiscountValueCurrencyCode;

    /** Tender information discount value. */
    private BigDecimal tenderInfoDiscountValue;

    /** Tender information discount rate. */
    private BigDecimal tenderInfoDiscountRate;

    /** Tender information discount code id corporate id. */
    private String tenderInfoDiscountCodeIdCorporateId;

    /** Tender information coupon type. */
    private String tenderInfoCouponType;

    /** Tender information coupon discount amount setting currency code. */
    private String tenderInfoCouponDiscountAmountSettingCurrencyCode;

    /** Tender information coupon discount amount setting value. */
    private BigDecimal tenderInfoCouponDiscountAmountSettingValue;

    /** Tender information coupon min usage amount threshold currency code. */
    private String tenderInfoCouponMinUsageAmountThresholdCurrencyCode;

    /** Tender information coupon min usage amount threshold value. */
    private BigDecimal tenderInfoCouponMinUsageAmountThresholdValue;

    /** Tender information coupon user id. */
    private String tenderInfoCouponUserId;

    /** Tender information card no. */
    private String tenderInfoCardNo;

    /** Tender information credit approval code. */
    private String tenderInfoCreditApprovalCode;

    /** Tender information credit processing serial number. */
    private String tenderInfoCreditProcessingSerialNumber;

    /** Tender information credit payment type. */
    private String tenderInfoCreditPaymentType;

    /** Tender information credit payment count. */
    private Integer tenderInfoCreditPaymentCount;

    /** Tender information credit affiliated store number. */
    private String tenderInfoCreditAffiliatedStoreNumber;

    /** Total order sub number. */
    private Integer totalOrderSubNumber;

    /** Total sales transaction id. */
    private String totalSalesTransactionId;

    /** Total total type. */
    private String totalTotalType;

    /** Total total amount sub number. */
    private Integer totalTotalAmountSubNumber;

    /** Total total amount tax excluded currency code. */
    private String totalTotalAmountTaxExcludedCurrencyCode;

    /** Total total amount tax excluded value. */
    private BigDecimal totalTotalAmountTaxExcludedValue;

    /** Total total amount tax included currency code. */
    private String totalTotalAmountTaxIncludedCurrencyCode;

    /** Total total amount tax included value. */
    private BigDecimal totalTotalAmountTaxIncludedValue;

    /** Total tax rate. */
    private BigDecimal totalTaxRate;

    /** Total sales transaction information 1. */
    private String totalSalesTransactionInformation1;

    /** Total sales transaction information 2. */
    private String totalSalesTransactionInformation2;

    /** Total sales transaction information 3. */
    private String totalSalesTransactionInformation3;
}
