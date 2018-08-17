/**
 * @(#)BrandCountrySettingEditForm.java
 *
 *                                      Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salesbrandcountrysettingedit.form;

import java.util.List;
import com.fastretailing.dcp.storecommon.screen.form.CommonBaseForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Brand country setting edit form.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BrandCountrySettingEditForm extends CommonBaseForm {

    /**
     * Serial version uid.
     */
    private static final long serialVersionUID = -7522475063806267535L;

    /**
     * Brand.
     */
    private String brand;
    
    /**
     * Brand name.
     */
    private String brandName;

    /**
     * Country.
     */
    private String country;

    /**
     * Store Code.
     */
    private String storeCode;

    /**
     * Business code.
     */
    private String businessCode;
    
    /**
     * View store code.
     */
    private String viewStoreCode;
    
    /**
     * Store brand code.
     */
    private String storeBrandCode;
    
    /**
     * Store country code.
     */
    private String storeCountryCode;

    /**
     * State code.
     */
    private String stateCode;

    /**
     * Brand country setting edit list.
     */
    private List<BrandCountrySettingEdit> brandCountrySettingEditList;
}
