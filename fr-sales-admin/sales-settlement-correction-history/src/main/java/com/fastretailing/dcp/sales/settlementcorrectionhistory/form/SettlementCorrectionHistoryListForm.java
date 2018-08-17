/**
 * @(#)SettlementCorrectionHistoryListForm.java
 *
 *                                              Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.settlementcorrectionhistory.form;

import java.util.List;
import java.util.Map;
import org.hibernate.validator.constraints.NotEmpty;
import com.fastretailing.dcp.common.validation.annotation.Alphanumeric;
import com.fastretailing.dcp.common.validation.annotation.Number;
import com.fastretailing.dcp.storecommon.screen.form.CommonBaseForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Settlement correction history list form.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SettlementCorrectionHistoryListForm extends CommonBaseForm {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 372327914284349168L;

    /**
     * System brand code.
     */
    @NotEmpty(message = "{sales-settlement-correction-history-list.brandLabel}"
            + "{w.sales-settlement-correction-history-list.validation.empty}")
    private String systemBrandCode;

    /**
     * System brand code and name map.
     */
    private Map<String, String> systemBrandCodeMap;

    /**
     * System country code.
     */
    @NotEmpty(message = "{sales-settlement-correction-history-list.countryLabel}"
            + "{w.sales-settlement-correction-history-list.validation.empty}")
    private String systemCountryCode;

    /**
     * System country code and name map.
     */
    private Map<String, String> systemCountryCodeMap;

    /**
     * View store code.
     */
    @NotEmpty(message = "{sales-settlement-correction-history-list.storeLabel}"
            + "{w.sales-settlement-correction-history-list.validation.empty}")
    @Alphanumeric(message = "{sales-settlement-correction-history-list.storeLabel}"
            + "{w.sales-settlement-correction-history-list.validation.alphanumeric}")
    private String viewStoreCode;

    /**
     * Payoff date from.
     */
    private String payoffDateFrom;

    /**
     * Payoff date to.
     */
    private String payoffDateTo;

    /**
     * Cash register no.
     */

    @Number(message = "{sales-settlement-correction-history-list.cashRegisterNoLabel}"
            + "{w.sales-settlement-correction-history-list.validation.number}")
    private String cashRegisterNo;

    /**
     * Corrector code.
     */
    @Alphanumeric(message = "{sales-settlement-correction-history-list.correctorLabel}"
            + "{w.sales-settlement-correction-history-list.validation.alphanumeric}")
    private String corrector;

    /**
     * Correction date from.
     */
    private String correctionDateFrom;

    /**
     * Correction date to.
     */
    private String correctionDateTo;

    /**
     * Sales settlement correction history list.
     */
    private List<SettlementCorrectionHistory> settlementCorrectionHistoryList;

    /**
     * Sort item.
     */
    private String sortItem;

    /**
     * Order by clause.
     */
    private Integer orderByClause = 0;

}
