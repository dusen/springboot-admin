/**
 * @(#)StoreSettingForm.java
 *
 *                           Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salesbrandcountrysettingedit.form;

import java.util.List;
import com.fastretailing.dcp.storecommon.screen.form.CommonBaseForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Store setting form.
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class StoreSettingForm extends CommonBaseForm {

    /**
     * Serial version uid.
     */
    private static final long serialVersionUID = -7087082004686407410L;

    /**
     * Store brand code.
     */
    private String storeBrandCode;

    /**
     * Brand name.
     */
    private String brandName;

    /**
     * Store country code.
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
     * Store code.
     */
    private String storeCode;

    /**
     * View store code.
     */
    private String viewStoreCode;

    /**
     * State code.
     */
    private String stateCode;

    /**
     * Store name.
     */
    private String storeName;

    /**
     * Code level 1.
     */
    private String codeL1;

    /**
     * Count.
     */
    private int count;

    /**
     * Page data.
     */
    private String pageData;

    /**
     * Store setting list.
     */
    private List<StoreSetting> storeSettingList;
}
