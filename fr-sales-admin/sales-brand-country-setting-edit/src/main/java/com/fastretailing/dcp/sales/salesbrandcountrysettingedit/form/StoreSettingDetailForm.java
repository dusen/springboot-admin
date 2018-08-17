/**
 * @(#)StoreSettingDetailForm.java
 *
 *                                 Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salesbrandcountrysettingedit.form;

import java.util.List;
import com.fastretailing.dcp.storecommon.screen.form.CommonBaseForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Store setting detail form.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class StoreSettingDetailForm extends CommonBaseForm {

    /**
     * Serial version uid.
     */
    private static final long serialVersionUID = 7751046709757881714L;

    /**
     * Brand.
     */
    private String storeBrandCode;

    /**
     * Brand name.
     */
    private String brandName;

    /**
     * Country.
     */
    private String storeCountryCode;

    /**
     * Country name.
     */
    private String countryName;

    /**
     * Business code.
     */
    private String businessCode;

    /**
     * Store.
     */
    private String storeCode;

    /**
     * Store name.
     */
    private String storeName;

    /**
     * View store code.
     */
    private String viewStoreCode;

    /**
     * State code.
     */
    private String stateCode;

    /**
     * Code level 1.
     */
    private String codeL1;

    /**
     * Code level 3.
     */
    private String codeL3;

    /**
     * Check value.
     */
    private String checkValue;

    /**
     * Code value.
     */
    private String codeValue;

    /**
     * Page data.
     */
    private String pageData;

    /**
     * Linkage timing variable type.
     */
    private String linkageTimingVariableType;

    /**
     * Linkage timing page data.
     */
    private String linkageTimingPageData;
    
    /** 
     * Linkage unit update datetime.
     * */
    private String linkageUnitUpdateDatetime;

    /**
     * Count.
     */
    private int count;

    /**
     * Brand country setting edit list.
     */
    private List<StoreSettingDetail> brandCountrySettingEditList;
}
