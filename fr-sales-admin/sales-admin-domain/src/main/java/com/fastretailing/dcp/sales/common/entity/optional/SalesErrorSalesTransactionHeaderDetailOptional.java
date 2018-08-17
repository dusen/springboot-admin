/**
 * @(#)SalesErrorSalesTransactionHeaderDetailOptional.java
 *
 *                                                         Copyright (c) 2018 Fast Retailing
 *                                                         Corporation.
 */
package com.fastretailing.dcp.sales.common.entity.optional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.Data;

/**
 * Sales error sales transaction header detail optional.
 */
@Data
public class SalesErrorSalesTransactionHeaderDetailOptional {

    /**
     * Sales linkage type.
     */
    private String salesLinkageType;

    /**
     * Sales transaction type.
     */
    private String salesTransactionType;

    /**
     * Store code.
     */
    private String storeCode;

    /**
     * Cash register number.
     */
    private Integer cashRegisterNo;

    /**
     * Receipt number.
     */
    private String receiptNo;

    /**
     * Data creation business date.
     */
    private String dataCreationBusinessDate;

    /**
     * Data creation date time.
     */
    private OffsetDateTime dataCreationDateTime;

    /**
     * Order status update date.
     */
    private String orderStatusUpdateDate;

    /**
     * Order status last update date time.
     */
    private OffsetDateTime orderStatusLastUpdateDateTime;

    /**
     * Employee sales flag.
     */
    private String employeeSaleFlag;

    /**
     * Consistency sales flag.
     */
    private String consistencySalesFlag;

    /**
     * Corporate id.
     */
    private String corporateId;

    /**
     * Sales transaction discount flag.
     */
    private String salesTransactionDiscountFlag;

    /**
     * Country code.
     */
    private String countryCode;

    /**
     * Sales transaction discount amount rate.
     */
    private BigDecimal salesTransactionDiscountAmountRate;

    /**
     * Sales transaction discount amount rate currency code.
     */
    private String salesTransactionDiscountAmountRateCurrencyCode;

    /**
     * Sales error sales transaction detail list.
     */
    private List<SalesErrorSalesTransactionItemDetailOptional> salesErrorSalesTransactionDetailList;

    /**
     * Sales error sales transaction tax list.
     */
    private List<SalesErrorSalesTransactionTaxDetailOptional> salesErrorSalesTransactionTaxList;

    /**
     * Sales error sales transaction tender list.
     */
    private List<SalesErrorSalesTransactionTenderDetailOptional> salesErrorSalesTransactionTenderList;

    /**
     * Sales error sales transaction total amount list.
     */
    private List<SalesErrorSalesTransactionTotalAmountDetailOptional> salesErrorSalesTransactionTotalAmountList;

}
