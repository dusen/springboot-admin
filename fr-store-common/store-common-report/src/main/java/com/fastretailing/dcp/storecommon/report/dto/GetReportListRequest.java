/**
 * @(#)GetReportListRequest.java
 * 
 *                               Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.report.dto;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Request class for Watch Auto Print Report.
 */
@Data
public class GetReportListRequest implements Serializable {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = -3078738906081909687L;

    /**
     * Store_code.
     */
    @JsonProperty("store_code")
    @NotBlank
    @Size(max = 10)
    private String storeCode;

    /**
     * Business day from.
     */
    @JsonProperty("business_day_from")
    private OffsetDateTime businessDayFrom;

    /**
     * Business day to.
     */
    @JsonProperty("business_day_to")
    private OffsetDateTime businessDayTo;

    /**
     * Receipt number list.
     */
    @JsonProperty("receipt_number_list")
    private List<String> receiptNumberList;

    /**
     * Report id list.
     */
    @JsonProperty("report_id_list")
    private List<String> reportIdList;

    /**
     * Create report datetime.
     */
    @JsonProperty("create_report_datetime")
    private OffsetDateTime createReportDatetime;
}
