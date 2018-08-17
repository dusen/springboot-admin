/**
 * @(#)GenerateExcelReportRequest.java
 * 
 *                                     Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.report.dto;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fastretailing.dcp.storecommon.report.dto.excel.ExcelCellFormatting;
import com.fastretailing.dcp.storecommon.report.dto.excel.ExcelCommonData;
import com.fastretailing.dcp.storecommon.report.dto.excel.ExcelDetailDefine;
import com.fastretailing.dcp.storecommon.report.dto.excel.ExcelDetailRecord;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Implement Excel Order Request class to generate.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GenerateExcelReportRequest extends GenerateReportRequestBase implements Serializable {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = -9027856301492930774L;

    /**
     * Common Data List.
     */
    @JsonProperty("common_data_list")
    private List<ExcelCommonData> commonDataList;

    /**
     * Detail Define List.
     */
    @JsonProperty("detail_define_list")
    private List<ExcelDetailDefine> detailDefineList;

    /**
     * Detail Record List.
     */
    @JsonProperty("detail_record_list")
    private List<ExcelDetailRecord> detailRecordList;

    /**
     * Excel Cell Formatting List.
     */
    @JsonProperty("cell_formatting_list")
    private List<ExcelCellFormatting> cellFormattingList;
}
