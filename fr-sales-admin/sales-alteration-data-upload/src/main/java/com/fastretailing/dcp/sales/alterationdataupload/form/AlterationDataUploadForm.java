/**
 * @(#)AlterationDataUploadForm.java
 *
 *                                   Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.alterationdataupload.form;

import com.fastretailing.dcp.storecommon.screen.form.CommonBaseForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Form data class to alteration data upload.
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class AlterationDataUploadForm extends CommonBaseForm {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = -7679047805865058866L;

    /**
     * Alteration data type.
     */
    private String alterationDataType;


}
