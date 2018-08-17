/**
 * @(#)SalesTransactionErrorListForm.java
 *
 *                                        Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salestransactioncorrection.form.errorlist;

import java.util.List;
import java.util.Map;
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
    private String systemBrandCode;

    /**
     * Brand list.
     */
    private List<SelectItem> brandList;

    /**
     * System country code.
     */
    private String systemCountryCode;

    /**
     * Country list.
     */
    private List<SelectItem> countryList;

    /**
     * Store code.
     */
    private String storeCode;

    /**
     * Cash register no.
     */
    private String cashRegisterNo;

    /**
     * Receipt no.
     */
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
