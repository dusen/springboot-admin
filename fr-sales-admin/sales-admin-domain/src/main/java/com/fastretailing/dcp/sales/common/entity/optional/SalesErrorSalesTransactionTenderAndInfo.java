package com.fastretailing.dcp.sales.common.entity.optional;

import java.math.BigDecimal;
import lombok.Data;

/**
 * Sales error sales transaction detail and info.
 */
@Data
public class SalesErrorSalesTransactionTenderAndInfo {

    private String transactionId;

    private Integer orderSubNumber;

    private String salesTransactionId;

    private String tenderGroup;

    private String tenderId;
    
    private Integer tenderSubNumber;

    private String paymentSign;

    private String taxIncludedPaymentAmountCurrencyCode;
    
    private BigDecimal taxIncludedPaymentAmountValue;
    
    private String discountValueCurrencyCode;
    
    private BigDecimal discountValue;
    
    private BigDecimal discountRate;
    
    private String discountCodeIdCorporateId;
    
    private String couponType;
    
    private String couponDiscountAmountSettingCurrencyCode;
    
    private BigDecimal couponDiscountAmountSettingValue;
    
    private String couponMinUsageAmountThresholdCurrencyCode;
    
    private BigDecimal couponMinUsageAmountThresholdValue;

    private String couponUserId;
    
    private String cardNo;
    
    private String creditApprovalCode;
    
    private String creditProcessingSerialNumber;
    
    private String creditPaymentType;
    
    private Integer creditPaymentCount;
    
    private String creditAffiliatedStoreNumber;
    
}
