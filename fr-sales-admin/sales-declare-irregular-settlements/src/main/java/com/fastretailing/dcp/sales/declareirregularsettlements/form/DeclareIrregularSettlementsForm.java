/**
 * @(#)DeclareIrregularSettlementsForm.java
 *
 *                                          Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.declareirregularsettlements.form;

import java.util.List;
import java.util.Map;
import org.hibernate.validator.constraints.NotEmpty;
import com.fastretailing.dcp.common.validation.annotation.Alphanumeric;
import com.fastretailing.dcp.common.validation.annotation.Number;
import com.fastretailing.dcp.storecommon.screen.form.CommonBaseForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Form data class to declare irregular settlements.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class DeclareIrregularSettlementsForm extends CommonBaseForm {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = -1825825471137377062L;

    /**
     * Settlement date.
     */
    @NotEmpty(message = "{declare-irregular-settlements.settlementDateLabel}"
            + "{w.declare-irregular-settlements.validation.empty}")
    private String settlementDate;

    /**
     * System brand code.
     */
    @NotEmpty(message = "{declare-irregular-settlements.brandLabel}"
            + "{w.declare-irregular-settlements.validation.empty}")
    private String systemBrandCode;

    /**
     * System brand name.
     */
    private String systemBrandName;

    /**
     * Brand code map.
     */
    private Map<String, String> brandCodeMap;

    /**
     * System country code.
     */
    @NotEmpty(message = "{declare-irregular-settlements.countryLabel}"
            + "{w.declare-irregular-settlements.validation.empty}")
    private String systemCountryCode;

    /**
     * System country Name.
     */
    private String systemCountryName;

    /**
     * Country code map.
     */
    private Map<String, String> countryCodeMap;

    /**
     * Store code.
     */
    private String storeCode;

    /**
     * Display store code.
     */
    @NotEmpty(message = "{declare-irregular-settlements.storeLabel}"
            + "{w.declare-irregular-settlements.validation.empty}")
    @Alphanumeric(message = "{declare-irregular-settlements.storeLabel}"
            + "{w.declare-irregular-settlements.validation.alphanumeric}")
    private String displayStoreCode;

    /**
     * Cash register number.
     */
    @NotEmpty(message = "{declare-irregular-settlements.cashRegisterNoLabel}"
            + "{w.declare-irregular-settlements.validation.empty}")
    @Number(message = "{declare-irregular-settlements.cashRegisterNoLabel}"
            + "{w.declare-irregular-settlements.validation.number}")
    private String cashRegisterNo;

    /**
     * Change fund integer.
     */
    @NotEmpty(message = "{declare-irregular-settlements.changeFundLabel}"
            + "{w.declare-irregular-settlements.validation.empty}")
    @Number(message = "{declare-irregular-settlements.changeFundLabel}"
            + "{w.declare-irregular-settlements.validation.number}")
    private String changeFundInteger;

    /**
     * Change fund decimal.
     */
    @Number(message = "{declare-irregular-settlements.changeFundLabel}"
            + "{w.declare-irregular-settlements.validation.number}")
    private String changeFundDecimal;

    /**
     * Closing balance integer.
     */
    @NotEmpty(message = "{declare-irregular-settlements.closingBalanceLabel}"
            + "{w.declare-irregular-settlements.validation.empty}")
    @Number(message = "{declare-irregular-settlements.closingBalanceLabel}"
            + "{w.declare-irregular-settlements.validation.number}")
    private String closingBalanceInteger;

    /**
     * Closing balance decimal.
     */
    @Number(message = "{declare-irregular-settlements.closingBalanceLabel}"
            + "{w.declare-irregular-settlements.validation.number}")
    private String closingBalanceDecimal;

    /**
     * Required balance.
     */
    private String requiredBalance;

    /**
     * Sales cash variance.
     */
    private String valueDiff;

    /**
     * Tender payment information list.
     */
    private List<TenderPaymentInformation> tenderPaymentInformationList;

    /**
     * End receipt number.
     */
    private String endReceiptNo;

    /**
     * Decimal size.
     */
    private String decimalSize;

    /**
     * Cash payment total.
     */
    private String cashPaymentTotal;

    /**
     * Store flag.
     */
    private boolean storeFlag;

    /**
     * List have flag.
     */
    private boolean listHaveFlag;

    /**
     * Message display flag.
     */
    private boolean messageDisplayFlag;

    /**
     * Normal message.
     */
    private String normalMessage;

    /**
     * Clear button.
     */
    private String clearButton;

    /**
     * Confirm button.
     */
    private String confirmButton;
}
