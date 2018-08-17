/**
 * @(#)UpdateReportStatusResponse.java
 * 
 *                                     Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.report.dto;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fastretailing.dcp.common.model.ResultObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Report status response class to update.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateReportStatusResponse extends ResultObject implements Serializable {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 1866648336660839904L;

    /**
     * Status update result.
     */
    @JsonProperty("status_update_result")
    private int statusUpdateResult;

}
