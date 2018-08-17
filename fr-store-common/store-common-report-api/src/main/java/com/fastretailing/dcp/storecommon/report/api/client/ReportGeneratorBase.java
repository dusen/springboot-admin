/**
 * @(#)ReportGeneratorBase.java
 * 
 *                              Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.report.api.client;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.client.RestTemplate;
import com.fastretailing.dcp.common.api.threadlocal.RequestPathVariableHolder;
import com.fastretailing.dcp.common.exception.SystemException;
import com.fastretailing.dcp.common.model.ResultObject;
import com.fastretailing.dcp.storecommon.message.SystemMessageSource;
import com.fastretailing.dcp.storecommon.report.ReportCommonException;
import com.fastretailing.dcp.storecommon.report.api.config.RestUrlProperty;
import com.fastretailing.dcp.storecommon.report.api.entity.ReportCreateStatus;
import com.fastretailing.dcp.storecommon.report.api.entity.ReportMaster;
import com.fastretailing.dcp.storecommon.report.api.repository.ReportCreateStatusMapper;
import com.fastretailing.dcp.storecommon.report.api.repository.ReportMasterMapper;
import com.fastretailing.dcp.storecommon.report.api.util.ReportReceiptNumberComposer;
import com.fastretailing.dcp.storecommon.report.api.util.StoreLocalDateTimeConverter;
import com.fastretailing.dcp.storecommon.report.config.CreateReportBaseCheckGroup;
import com.fastretailing.dcp.storecommon.report.constant.ReportConstants;
import com.fastretailing.dcp.storecommon.report.constant.ReportMessagesConstants;
import com.fastretailing.dcp.storecommon.report.dto.GenerateReportRequestBase;
import com.fastretailing.dcp.storecommon.report.dto.GenerateReportResponse;
import com.fastretailing.dcp.storecommon.report.type.AutoPrintStatus;
import com.fastretailing.dcp.storecommon.report.type.ReportStatusCode;
import com.fastretailing.dcp.storecommon.util.DateUtility;
import lombok.extern.slf4j.Slf4j;

/**
 * Abstract class for create report.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Slf4j
@Component
abstract class ReportGeneratorBase<R extends GenerateReportRequestBase> {

    /** CRLF. */
    protected static final String MESSAGE_CRLF = "\r\n";

    /** SLASH. */
    protected static final String SLASH = "/";

    /**
     * Default update result.
     */
    protected static final int DEFAULT_UPDATE_RESULT = -1;

    /** Rest template. */
    @Autowired
    private RestTemplate restTemplate;

    /**
     * Locale message source.
     */
    @Autowired
    private SystemMessageSource systemMessageSource;

    /**
     * Rest url property.
     */
    @Autowired
    protected RestUrlProperty restUrlProperty;

    /**
     * Component for operating DB operations on the m_report_master.
     */
    @Autowired
    protected ReportMasterMapper reportMasterMapper;

    /**
     * Component for operating DB operations on the t_report_create_status.
     */
    @Autowired
    private ReportCreateStatusMapper reportCreateStatusMapper;

    /**
     * Component for composing report number.
     */
    @Autowired
    private ReportReceiptNumberComposer receiptNumberComposer;

    /**
     * Component for converting system datetime to store local.
     */
    @Autowired
    private StoreLocalDateTimeConverter storeLocalDateTimeConvert;

    /**
     * Validator for bean validate.
     */
    @Autowired
    private LocalValidatorFactoryBean validator;

    /**
     * Calls Report Api to generate report.
     * 
     * @param request Request object for generating report.
     * @return Report creation result.
     */
    protected GenerateReportResponse generateReport(R request)
            throws ReportCommonException, BindException {

        // Check input parameters.
        BindingResult errors = new DataBinder(request).getBindingResult();

        validator.validate(request, errors, CreateReportBaseCheckGroup.class);

        if (errors.hasErrors()) {
            throw new BindException(errors);
        }

        ReportMaster reportMaster = reportMasterMapper.select(request.getReportId(),
                getReportType(), request.getCountryCode());

        if (reportMaster == null) {
            throw new ReportCommonException(systemMessageSource.getMessage(
                    ReportMessagesConstants.ERROR_NO_REPORT_MASTER_DATA,
                    new Object[] {request.getReportId()}));
        }

        R generateReportRequest = getRequestByType(request, reportMaster);

        // Register report create status.
        int insertResult = registerReportCreateStatus(generateReportRequest, reportMaster);
        if (insertResult == 0) {
            throw new ReportCommonException("error in inserting report's create status.");
        }

        return callGenerateReportApi(generateReportRequest);
    }

    /**
     * Create Report Request.
     * 
     * @param request Input entity of generate PDF.
     * @return Output entity of generate PDF.
     * @throws ReportCommonException Exception throws when error happened.
     */
    private GenerateReportResponse callGenerateReportApi(R generateReportRequest)
            throws ReportCommonException {

        Map<String, Object> pathVariables = new HashMap<>();
        pathVariables.put(ReportConstants.PATH_VARIABLE_VERSION, restUrlProperty.getVersion());
        pathVariables.put(ReportConstants.PATH_VARIABLE_BRAND,
                RequestPathVariableHolder.getBrand());
        pathVariables.put(ReportConstants.PATH_VARIABLE_REGION,
                RequestPathVariableHolder.getRegion());
        pathVariables.put(ReportConstants.PATH_VARIABLE_STORE_CODE,
                generateReportRequest.getStoreCode());

        ResponseEntity<GenerateReportResponse> responseEntity =
                restTemplate.postForEntity(restUrlProperty.getReportApiHost() + getResourePath(),
                        generateReportRequest, GenerateReportResponse.class, pathVariables);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {

            GenerateReportResponse generateReportResponse = responseEntity.getBody();

            int updateResult = updateReportCreateStatus(generateReportResponse.getReceiptNumber(),
                    generateReportResponse.getGenerateReportResult(),
                    generateReportResponse.getCreatedReportBucketName(),
                    generateReportResponse.getCreatedReportKeyName());
            if (updateResult == 0) {
                throw new ReportCommonException("failed to update report's status to "
                        + generateReportResponse.getGenerateReportResult());
            }

            // Get Response Object for Report.
            return generateReportResponse;
        } else if (responseEntity.getStatusCode().is4xxClientError()) {

            int updateResult = updateReportCreateStatus(generateReportRequest.getReceiptNumber(),
                    ReportStatusCode.FAILURE.getValue(), null, null);
            if (updateResult == 0) {
                throw new ReportCommonException("failed to update report's status to failure.");
            }

            ResultObject resultObject = responseEntity.getBody();
            throw new ReportCommonException("4xx error in calling api to generate report.",
                    resultObject);
        } else {
            log.error("error in calling api to generate report. {}",
                    responseEntity.getStatusCode());
            throw new SystemException();
        }
    }

    /**
     * Get report type.
     * 
     * @return report type.
     */
    protected abstract String getReportType();

    /**
     * Get resourPath.
     * 
     * @return resource path.
     */
    protected abstract String getResourePath();

    /**
     * Maps values to api request dto.
     * 
     * @param request Request dto.
     * @param reportMaster Report master data.
     * @return request map to.
     */
    protected abstract R getRequestByType(R request, ReportMaster reportMaster);

    /**
     * Create common base item for request.
     * 
     * @param requestTo Setting item value to.
     * @param mapFrom Getting item value from.
     * @param masterDto Report master data.
     */
    protected void createReportRequestBase(R requestTo, R mapFrom, ReportMaster masterDto) {

        requestTo.setCountryCode(mapFrom.getCountryCode());
        requestTo.setCreatedReportBucketName(mapFrom.getCreatedReportBucketName());
        requestTo.setCreateReportBusinessDay(mapFrom.getCreateReportBusinessDay());
        requestTo.setReportId(mapFrom.getReportId());
        requestTo.setStoreCode(mapFrom.getStoreCode());
        requestTo.setStoreName(mapFrom.getStoreName());
        requestTo.setSystemId(mapFrom.getSystemId());
        requestTo.setViewStoreCode(mapFrom.getViewStoreCode());
        requestTo.setCreateReportDateTime(
                storeLocalDateTimeConvert.getStoreLocalDateTime(mapFrom.getStoreCode()));

        requestTo.setReceiptNumber(receiptNumberComposer.compose(mapFrom.getSystemId(),
                mapFrom.getStoreCode(), mapFrom.getReportId()));

        requestTo.setDeleteReportBusinessDay(mapFrom.getCreateReportBusinessDay()
                .plusDays(masterDto.getReportPreservationPeriod()));

        requestTo.setReportFormBucketName(masterDto.getReportFormBucketName());
        requestTo.setReportFormKeyName(masterDto.getReportFormKeyName());
        requestTo.setReportTitle(masterDto.getReportTitle());

    }

    /**
     * Register report create status.
     * 
     * @param request Create report request dto.
     * @param masterDto Report master dto.
     * 
     * @return Count of registered.
     */
    private int registerReportCreateStatus(R request, ReportMaster masterDto) {
        ReportCreateStatus reportCreateStatus = new ReportCreateStatus();

        reportCreateStatus.setReceiptNumber(request.getReceiptNumber());
        reportCreateStatus.setReportId(request.getReportId());
        reportCreateStatus.setReportType(getReportType());
        reportCreateStatus.setStoreCode(request.getStoreCode());
        reportCreateStatus.setCreateReportStatus(ReportStatusCode.RECEIPT.getValue());
        reportCreateStatus.setAutoPrintStatus(AutoPrintStatus.UNPROCESSED.getValue());
        reportCreateStatus
                .setCreateReportBusinessDay(request.getCreateReportBusinessDay().toLocalDate());
        reportCreateStatus
                .setDeleteReportBusinessDay(request.getDeleteReportBusinessDay().toLocalDate());
        reportCreateStatus.setPrinterName(masterDto.getPrinterName());
        reportCreateStatus.setOuterCommandExecuteFlag(masterDto.getOuterCommandExecuteFlag());
        reportCreateStatus.setCreateDatetime(DateUtility.getZonedDateTimeUtc().toLocalDateTime());

        return reportCreateStatusMapper.insertReportCreateStatus(reportCreateStatus);
    }

    /**
     * Update report create status.
     * 
     * @param receiptNumber Receipt number.
     * @param status Status.
     * @param createdReportKeyName Created report keyName.
     * @return Count of updated.
     */
    private int updateReportCreateStatus(String receiptNumber, Integer status,
            String createdReportBucketName, String createdReportKeyName) {

        ReportCreateStatus reportCreateStatus = new ReportCreateStatus();

        reportCreateStatus.setReceiptNumber(receiptNumber);
        reportCreateStatus.setCreateReportStatus(status);
        reportCreateStatus.setCreatedReportBucketName(createdReportBucketName);
        reportCreateStatus.setCreatedReportKeyName(createdReportKeyName);
        reportCreateStatus.setUpdateDatetime(DateUtility.getZonedDateTimeUtc().toLocalDateTime());

        return reportCreateStatusMapper.updateCreateStatus(reportCreateStatus);
    }
}
