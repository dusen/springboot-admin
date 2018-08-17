/**
 * @(#)PdfReportGenerator.java
 * 
 *                             Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.report.api.client;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import com.fastretailing.dcp.common.hmac.annotation.HmacAuthentication;
import com.fastretailing.dcp.storecommon.report.ReportCommonException;
import com.fastretailing.dcp.storecommon.report.api.entity.ReportMaster;
import com.fastretailing.dcp.storecommon.report.constant.ReportUrlPath;
import com.fastretailing.dcp.storecommon.report.dto.GeneratePdfReportRequest;
import com.fastretailing.dcp.storecommon.report.dto.GenerateReportResponse;
import com.fastretailing.dcp.storecommon.report.type.ReportType;

/**
 * Component class for generate SVF PDF.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Component
public class PdfReportGenerator extends ReportGeneratorBase<GeneratePdfReportRequest> {

    /*
     * {@inheritDoc}
     */
    @HmacAuthentication(value = "report")
    @Override
    public GenerateReportResponse generateReport(GeneratePdfReportRequest pdfRequest)
            throws ReportCommonException, BindException {
        return super.generateReport(pdfRequest);
    }

    /*
     * {@inheritDoc}
     */
    @Override
    protected String getReportType() {
        return ReportType.PDF.getValue();
    }

    /*
     * {@inheritDoc}
     */
    @Override
    protected String getResourePath() {
        return ReportUrlPath.REQUEST_CREATE_PDF_URL_PATH;
    }

    /*
     * {@inheritDoc}
     */
    @Override
    protected GeneratePdfReportRequest getRequestByType(GeneratePdfReportRequest request,
            ReportMaster reportMaster) {

        GeneratePdfReportRequest pdfRequest = new GeneratePdfReportRequest();

        createReportRequestBase(pdfRequest, request, reportMaster);

        pdfRequest.setAsyncSetting(request.getAsyncSetting());
        pdfRequest.setSvfPdfSectionList(request.getSvfPdfSectionList());

        return pdfRequest;
    }
}
