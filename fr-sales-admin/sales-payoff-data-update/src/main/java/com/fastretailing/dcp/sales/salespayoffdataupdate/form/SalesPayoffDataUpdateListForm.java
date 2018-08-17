/**
 * @(#)SalesPayoffDataUpdateListForm.java
 *
 *                                        Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salespayoffdataupdate.form;

import com.fastretailing.dcp.sales.common.entity.optional.SalesPayoffDataUpdateOptionalEntity;
import com.fastretailing.dcp.storecommon.screen.form.CommonBaseForm;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * Sales payoff data update list form.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SalesPayoffDataUpdateListForm extends CommonBaseForm {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = -6956121790443500136L;

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
     * Payoff date.
     */
    private String payoffDate;

    /**
     * Cash register number.
     */
    private String cashRegisterNumber;

    /**
     * Integrity Check Type.
     */
    private String integrityCheckType;

    /**
     * Brand name.
     */
    private String brandName;

    /**
     * Country name.
     */
    private String countryName;

    /**
     * Store name.
     */
    private String storeName;

    /**
     * Error content.
     */
    private String errorContent;

    /**
     * Sales transaction error status.
     */
    private String salesTransactionErrorStatus;

    /**
     * Sales payoff data update optional entity list.
     */
    private List<SalesPayoffDataUpdateOptionalEntity> salesPayoffDataList;

    /**
     * Sales payoff data update optional entity list have data or not.
     */
    private String salesPayoffDataListHaveDataFlag;

}
