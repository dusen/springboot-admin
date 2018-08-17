/**
 * @(#)StoreSetting.java
 *
 *                       Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salesbrandcountrysettingedit.form;

import com.fastretailing.dcp.storecommon.screen.form.CommonBaseForm;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * Store setting.
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class StoreSetting extends CommonBaseForm {

    /**
     * Serial version uid.
     */
    private static final long serialVersionUID = 2521949868503748902L;

    /**
     * Setting item.
     */
    private String settingItem;

    /**
     * Status.
     */
    private String status;

    /**
     * Status color.
     */
    private String statusColor;
}
