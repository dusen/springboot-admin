/**
 * @(#)ExcelDetailRecord.java
 * 
 *                            Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.report.dto.excel;

import java.io.Serializable;
import java.util.List;
import org.hibernate.validator.constraints.NotEmpty;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This DTO class to handle Excel details record.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExcelDetailRecord implements Serializable {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 519087905074777481L;

    /**
     * Detail List.
     */
    @JsonProperty("detail_data_list")
    @NotEmpty
    private List<ExcelDetailData> detailList;

}
