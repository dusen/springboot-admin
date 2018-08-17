/**
 * @(#)SalesTransactionErrorListForm.java
 *
 *                                        Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salestransactionerrorlist.form;

import java.util.List;
import java.util.Map;
import org.hibernate.validator.constraints.NotEmpty;
import com.fastretailing.dcp.common.validation.annotation.Alphanumeric;
import com.fastretailing.dcp.common.validation.annotation.Number;
import com.fastretailing.dcp.storecommon.screen.form.CommonBaseForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Sales transaction sales-transaction-error-list list form.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SalesTransactionErrorListForm extends CommonBaseForm {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = -6956121790443500136L;

    /**
     * System brand code.
     */
    @NotEmpty(message = "{sales-transaction-error-list.brandLabel}"
            + "{w.sales-transaction-error-list.validation.empty}")
    @Alphanumeric(message = "{sales-transaction-error-list.brandLabel}"
            + "{w.sales-transaction-error-list.validation.alphanumeric}")
    private String systemBrandCode;

    /**
     * Brand list.
     */
    private List<SelectItem> brandList;

    /**
     * System country code.
     */
    @NotEmpty(message = "{sales-transaction-error-list.countryLabel}"
            + "{w.sales-transaction-error-list.validation.empty}")
    @Alphanumeric(message = "{sales-transaction-error-list.countryLabel}"
            + "{w.sales-transaction-error-list.validation.alphanumeric}")
    private String systemCountryCode;

    /**
     * Country list.
     */
    private List<SelectItem> countryList;

    /**
     * Store code.
     */
    @NotEmpty(message = "{sales-transaction-error-list.storeLabel}"
            + "{w.sales-transaction-error-list.validation.empty}")
    @Alphanumeric(message = "{sales-transaction-error-list.storeLabel}"
            + "{w.sales-transaction-error-list.validation.alphanumeric}")
    private String storeCode;

    /**
     * Cash register no.
     */
    @Number(message = "{sales-transaction-error-list.cashRegisterNoLabel}"
            + "{w.sales-transaction-error-list.validation.number}")
    private String cashRegisterNo;

    /**
     * Receipt no.
     */
    @Alphanumeric(message = "{sales-transaction-error-list-list.receiptNoLabel}"
            + "{w.sales-transaction-error-list.validation.alphanumeric}")
    private String receiptNo;

    /**
     * Sales transaction type.
     */
    private String salesTransactionType;

    /**
     * Sales transaction type map.
     */
    private Map<String, String> salesTransactionTypeMap;

    /**
     * Data creation date from.
     */
    private String dataCreationDateFrom;

    /**
     * Data creation date to.
     */
    private String dataCreationDateTo;

    /**
     * Business date from.
     */
    private String businessDateFrom;

    /**
     * Business date to.
     */
    private String businessDateTo;

    /**
     * Error contents.
     */
    private String errorContents;

    /**
     * Error contents map.
     */
    private Map<String, String> errorContentsMap;

    /**
     * Sales transaction error list.
     */
    private List<SalesTransactionError> salesTransactionErrorList;

    /**
     * Sort item.
     */
    private String sortItem;

    /**
     * Order by clause.
     */
    private Integer orderByClause = 0;

}
