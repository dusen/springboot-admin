/**
 * @(#)StoreSettingDetailAddForm.java
 *
 *                                    Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salesbrandcountrysettingedit.form;


import com.fastretailing.dcp.storecommon.screen.form.CommonBaseForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Store setting detail add form.
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class StoreSettingDetailAddForm extends CommonBaseForm {

    /**
     * Serial version uid.
     */
    private static final long serialVersionUID = -2523484983358627598L;

    /**
     * Store code.
     */
    private String storeCode;

    /**
     * Code level 1.
     */
    private String codeL1;

    /**
     * Code level 2.
     */
    private String codeL2;

    /**
     * Code level 3.
     */
    private String codeL3;

    /**
     * Code value.
     */
    private String codeValue;

    /**
     * Temporary data.
     */
    private String temporaryData;

}
