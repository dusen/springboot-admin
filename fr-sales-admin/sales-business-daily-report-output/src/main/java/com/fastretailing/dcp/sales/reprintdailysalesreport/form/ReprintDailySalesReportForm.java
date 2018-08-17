/**
 * @(#)ReprintDailySalesReportForm.java
 *
 *                                      Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.reprintdailysalesreport.form;

import java.util.List;
import org.hibernate.validator.constraints.NotEmpty;
import com.fastretailing.dcp.common.validation.annotation.Alphanumeric;
import com.fastretailing.dcp.storecommon.screen.form.CommonBaseForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Reprint daily sales report form.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ReprintDailySalesReportForm extends CommonBaseForm {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = -6956121790443500136L;

    /**
     * System brand code.
     */
    @NotEmpty(message = "{sales-reprint-daily-sales-report.brandLabel}"
            + "{w.sales-reprint-daily-sales-report.validation.empty}")
    @Alphanumeric(message = "{sales-reprint-daily-sales-report.brandLabel}"
            + "{w.sales-reprint-daily-sales-report.validation.alphanumeric}")
    private String systemBrandCode;

    /**
     * Brand list.
     */
    private List<String> brandList;

    /**
     * System country code.
     */
    @NotEmpty(message = "{sales-reprint-daily-sales-report.countryLabel}"
            + "{w.sales-reprint-daily-sales-report.validation.empty}")
    @Alphanumeric(message = "{sales-reprint-daily-sales-report.countryLabel}"
            + "{w.sales-reprint-daily-sales-report.validation.alphanumeric}")
    private String systemCountryCode;

    /**
     * Country list.
     */
    private List<String> countryList;

    /**
     * Display store code.
     */
    @NotEmpty(message = "{sales-reprint-daily-sales-report.storeLabel}"
            + "{w.sales-reprint-daily-sales-report.validation.empty}")
    @Alphanumeric(message = "{sales-reprint-daily-sales-report.storeLabel}"
            + "{w.sales-reprint-daily-sales-report.validation.alphanumeric}")
    private String displayStoreCode;

    /**
     * Store code.
     */
    private String storeCode;

    /**
     * System brand name.
     */
    private String systemBrandName;

    /**
     * System country name.
     */
    private String systemCountryName;

    /**
     * Business date.
     */
    @NotEmpty(message = "{sales-reprint-daily-sales-report.businessDateLabel}"
            + "{w.sales-reprint-daily-sales-report.validation.empty}")
    private String businessDate;

    /**
     * Store flag.
     */
    private boolean storeFlag;

    /**
     * Business date flag.
     */
    private boolean businessDateFlag;
}
