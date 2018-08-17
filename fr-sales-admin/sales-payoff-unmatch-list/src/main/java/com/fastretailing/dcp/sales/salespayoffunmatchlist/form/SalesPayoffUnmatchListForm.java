/**
 * @(#)SalesPayoffUnmatchListForm.java
 *
 *                                     Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salespayoffunmatchlist.form;

import java.util.List;
import java.util.Map;
import com.fastretailing.dcp.common.validation.annotation.Number;
import com.fastretailing.dcp.storecommon.screen.form.CommonBaseForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Sales payoff unmatch list form.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SalesPayoffUnmatchListForm extends CommonBaseForm {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = -6956121790443500136L;

    /**
     * Index value.
     */
    private Integer indexValue;

    /**
     * System brand code.
     */
    private String systemBrandCode;

    /**
     * System brand name.
     */
    private String systemBrandName;

    /**
     * Brand list.
     */
    private List<String> brandList;

    /**
     * Brand list map.
     */
    private Map<String, String> brandListMap;

    /**
     * System country code.
     */
    private String systemCountryCode;

    /**
     * System country name.
     */
    private String systemCountryName;

    /**
     * Country list map.
     */
    private Map<String, String> countryListMap;

    /**
     * Store code.
     */
    private String storeCode;

    /**
     * view store code.
     */
    private String viewStoreCode;

    /**
     * Cash register no.
     */
    @Number(message = "{sales-payoff-unmatch-list.cashRegisterNoLabel}"
            + "{w.sales-payoff-unmatch-list.validation.number}")
    private String cashRegisterNo;


    /**
     * Payoff date from.
     */
    private String payoffDateFrom;

    /**
     * Payoff date to.
     */
    private String payoffDateTo;

    /**
     * Payoff date.
     */
    private String payoffDate;


    /**
     * Error contents name.
     */
    private String errorContentsName;

    /**
     * Error contents code.
     */
    private String errorContentsCode;

    /**
     * Error contents map.
     */
    private Map<String, String> errorContentsMap;

    /**
     * Sales payoff unmatch list.
     */
    private List<SalesPayoffUnmatch> salesPayoffUnmatchList;

    /**
     * Sort item.
     */
    private String sortItem;

    /**
     * Order by clause.
     */
    private Integer orderByClause = 0;

}
