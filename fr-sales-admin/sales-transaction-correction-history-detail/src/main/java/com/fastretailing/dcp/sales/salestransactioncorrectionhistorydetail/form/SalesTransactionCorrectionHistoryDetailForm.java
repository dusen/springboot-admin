/**
 * @(#)SalesTransactionCorrectionHistoryDetailForm.java
 *
 *                                                      Copyright (c) 2018 Fast Retailing
 *                                                      Corporation.
 */

package com.fastretailing.dcp.sales.salestransactioncorrectionhistorydetail.form;

import java.util.Map;
import com.fastretailing.dcp.sales.salestransactioncorrectionhistorydetail.form.transactionomport.ImportTransactionImportData;
import com.fastretailing.dcp.storecommon.screen.form.CommonBaseForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Sales transaction correction history detail form.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SalesTransactionCorrectionHistoryDetailForm extends CommonBaseForm {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 2329478864223000372L;


    /**
     * Transaction id.
     */
    private String transactionId;

    /**
     * Sales transaction error id.
     */
    private String salesTransactionErrorId;

    /**
     * Order sub number.
     */
    private Integer orderSubNumber;

    /**
     * Detail sub number.
     */
    private Integer detailSubNumber;

    /**
     * No.
     */
    private String no;

    /**
     * System brand code.
     */
    private String systemBrandCode;

    /**
     * System country code.
     */
    private String systemCountryCode;

    /**
     * Store code.
     */
    private String storeCode;

    /**
     * Store name.
     */
    private String storeName;

    /**
     * Cash register no.
     */
    private String cashRegisterNo;

    /**
     * Receipt no.
     */
    private String receiptNo;

    /**
     * Data creation date.
     */
    private String dataCreationDate;

    /**
     * Business date.
     */
    private String businessDate;

    /**
     * Error contents.
     */
    private String errorContents;

    /**
     * Correction date.
     */
    private String correctionDate;

    /**
     * Corrector code.
     */
    private String correctorCode;

    /**
     * Reflect.
     */
    private String reflect;

    /**
     * Update type.
     */
    private String updateType;

    /**
     * Transaction type.
     */
    private String transactionType;

    /**
     * Employee sales flag map.
     */
    private Map<String, String> employeeSaleFlagMap;

    /**
     * Corporate sales flag map.
     */
    private Map<String, String> corporateSalesFlagMap;

    /**
     * Sales transaction discount flag map.
     */
    private Map<String, String> salesTransactionDiscountFlagMap;

    /**
     * Product classification map.
     */
    private Map<String, String> productClassificationMap;

    /**
     * Currency code map.
     */
    private Map<String, String> currencyCodeMap;

    /**
     * Quantity code map.
     */
    private Map<String, String> quantityCodeMap;

    /**
     * Taxation type map.
     */
    private Map<String, String> taxationTypeMap;

    /**
     * Tax kind map.
     */
    private Map<String, String> taxKindMap;

    /**
     * Payment sign map.
     */
    private Map<String, String> paymentSignMap;

    /**
     * Store discount type map.
     */
    private Map<String, String> storeDiscountTypeMap;

    /**
     * Tax group map.
     */
    private Map<String, String> taxGroupMap;

    /**
     * Amount sign map.
     */
    private Map<String, String> amountSignMap;

    /**
     * Total type map.
     */
    private Map<String, String> totalTypeMap;

    /**
     * Promotion type map.
     */
    private Map<String, String> promotionTypeMap;

    /**
     * Sort item.
     */
    private String sortItem;

    /**
     * Order by clause.
     */
    private Integer orderByClause = 0;

    /**
     * Transaction import data.
     */
    private ImportTransactionImportData transactionImportData;

}
