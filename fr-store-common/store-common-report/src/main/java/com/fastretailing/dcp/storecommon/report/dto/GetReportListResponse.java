/**
 * @(#)GetReportListResponse.java
 * 
 *                                Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.report.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fastretailing.dcp.common.model.ResultObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Request class for get report list.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GetReportListResponse extends ResultObject implements Serializable {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 4358043290262261491L;
    /**
     * Report list.
     */
    @JsonProperty("report_data")
    private List<ReportData> reportData = new ArrayList<>();

}
