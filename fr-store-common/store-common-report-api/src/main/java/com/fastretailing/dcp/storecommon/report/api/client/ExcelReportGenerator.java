/**
 * @(#)ExcelReportGenerator.java
 * 
 *                               Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.report.api.client;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import com.fastretailing.dcp.common.hmac.annotation.HmacAuthentication;
import com.fastretailing.dcp.storecommon.report.ReportCommonException;
import com.fastretailing.dcp.storecommon.report.api.entity.ReportMaster;
import com.fastretailing.dcp.storecommon.report.constant.ReportUrlPath;
import com.fastretailing.dcp.storecommon.report.dto.GenerateExcelReportRequest;
import com.fastretailing.dcp.storecommon.report.dto.GenerateReportResponse;
import com.fastretailing.dcp.storecommon.report.type.ReportType;

/**
 * Component class for generate Excel.
 * 
 * @author Fast Retailing
 * @version $Revision$
 */
@Component
public class ExcelReportGenerator extends ReportGeneratorBase<GenerateExcelReportRequest> {

    /*
     * {@inheritDoc}
     */
    @HmacAuthentication(value = "report")
    @Override
    public GenerateReportResponse generateReport(GenerateExcelReportRequest excelRequest)
            throws ReportCommonException, BindException {
        return super.generateReport(excelRequest);
    }

    /*
     * {@inheritDoc}
     */
    @Override
    protected String getReportType() {
        return ReportType.EXCEL.getValue();
    }

    /*
     * {@inheritDoc}
     */
    @Override
    protected String getResourePath() {
        return ReportUrlPath.REQUEST_CREATE_EXCEL_URL_PATH;
    }

    /*
     * {@inheritDoc}
     */
    @Override
    protected GenerateExcelReportRequest getRequestByType(GenerateExcelReportRequest request,
            ReportMaster masterDto) {

        GenerateExcelReportRequest excelRequest = new GenerateExcelReportRequest();

        createReportRequestBase(excelRequest, request, masterDto);

        excelRequest.setCommonDataList(request.getCommonDataList());
        excelRequest.setCellFormattingList(request.getCellFormattingList());
        excelRequest.setDetailDefineList(request.getDetailDefineList());
        excelRequest.setDetailRecordList(request.getDetailRecordList());

        return excelRequest;
    }
}
