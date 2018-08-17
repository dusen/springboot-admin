/**
 * @(#)StoreSettingDetail.java
 *
 *                             Copyright (c) 2018 Fast Retailing Corporation.
 */


package com.fastretailing.dcp.sales.salesbrandcountrysettingedit.form;

import com.fastretailing.dcp.storecommon.screen.form.CommonBaseForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Store setting detail.
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class StoreSettingDetail extends CommonBaseForm {

    /**
     * Serial version uid.
     */
    private static final long serialVersionUID = 6731570596662276877L;

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
     * Variable type.
     */
    private String variableType;

    /**
     * Update date time.
     */
    private String updateDatetime;

    /**
     * Add color.
     */
    private int addColor;
}
