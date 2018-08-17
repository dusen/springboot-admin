/**
 * @(#)AlterationDataUploadServiceImpl.java
 *
 *                                          Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.alterationdataupload.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fastretailing.dcp.common.constants.ErrorName;
import com.fastretailing.dcp.common.exception.BusinessException;
import com.fastretailing.dcp.common.util.SystemDateTime;
import com.fastretailing.dcp.sales.alterationdataupload.common.Constants;
import com.fastretailing.dcp.sales.alterationdataupload.form.ItemDetail;
import com.fastretailing.dcp.sales.alterationdataupload.form.ItemDetailDiscount;
import com.fastretailing.dcp.sales.alterationdataupload.form.ItemDetailTax;
import com.fastretailing.dcp.sales.alterationdataupload.form.OrderInformation;
import com.fastretailing.dcp.sales.alterationdataupload.form.PayOffDataForm;
import com.fastretailing.dcp.sales.alterationdataupload.form.PayOffDataForms;
import com.fastretailing.dcp.sales.alterationdataupload.form.PayOffType;
import com.fastretailing.dcp.sales.alterationdataupload.form.SalesTransactionHeader;
import com.fastretailing.dcp.sales.alterationdataupload.form.SalesTransactionPayment;
import com.fastretailing.dcp.sales.alterationdataupload.form.SalesTransactionTax;
import com.fastretailing.dcp.sales.alterationdataupload.form.SalesTransactionTotalCsv;
import com.fastretailing.dcp.sales.common.constants.CodeConstants;
import com.fastretailing.dcp.sales.common.constants.MessagePrefix;
import com.fastretailing.dcp.sales.common.entity.AlterationExclusionControl;
import com.fastretailing.dcp.sales.common.entity.AlterationExclusionControlKey;
import com.fastretailing.dcp.sales.common.entity.optional.PayoffDataOptional;
import com.fastretailing.dcp.sales.common.entity.optional.SalesTransactionErrorDetail;
import com.fastretailing.dcp.sales.common.repository.AlterationExclusionControlMapper;
import com.fastretailing.dcp.sales.common.repository.optional.PayoffDataOptionalMapper;
import com.fastretailing.dcp.sales.common.repository.optional.SalesTransactionErrorDetailOptionalMapper;
import com.fastretailing.dcp.sales.common.repository.optional.TranslationStoreCodeMasterOptionalMapper;
import com.fastretailing.dcp.sales.common.resttemplate.client.repository.sales.SalesRestTemplateRepository;
import com.fastretailing.dcp.sales.importtransaction.component.CheckTransactionDataService;
import com.fastretailing.dcp.sales.importtransaction.dto.AlterationPayOffImportMultiData;
import com.fastretailing.dcp.sales.importtransaction.dto.CreateTransactionImportData;
import com.fastretailing.dcp.sales.importtransaction.dto.ItemDiscount;
import com.fastretailing.dcp.sales.importtransaction.dto.ItemTaxDetail;
import com.fastretailing.dcp.sales.importtransaction.dto.SalesTransactionTender;
import com.fastretailing.dcp.sales.importtransaction.dto.SalesTransactionTotal;
import com.fastretailing.dcp.sales.importtransaction.dto.TenderInfo;
import com.fastretailing.dcp.sales.importtransaction.dto.Transaction;
import com.fastretailing.dcp.sales.importtransaction.dto.TransactionImportData;
import com.fastretailing.dcp.sales.importtransaction.dto.TransactionItemDetail;
import com.fastretailing.dcp.sales.importtransaction.exception.TransactionConsistencyErrorException;
import com.fastretailing.dcp.storecommon.ResultCode;
import com.fastretailing.dcp.storecommon.dto.CommonStatus;
import com.fastretailing.dcp.storecommon.dto.Price;
import com.fastretailing.dcp.storecommon.screen.message.LocaleMessageSource;
import com.fastretailing.dcp.storecommon.screen.util.ScreenCommonUtility;
import com.fastretailing.dcp.storecommon.util.StringUtility;

/**
 * Class for alteration data upload service implement.
 *
 */
@Service
public class AlterationDataUploadServiceImpl implements AlterationDataUploadService {
    /**
     * Error check for not null item check.
     */
    private static final String ERROR_TYPE_NOT_NULL_ITEM = "01";
    /**
     * Error check for business date check.
     */
    private static final String ERROR_TYPE_BUSINESS_DATE = "07";
    /**
     * Error check for correlation check.
     */
    private static final String ERROR_TYPE_CORRELATION = "02";
    /**
     * Error check for tender id check.
     */
    private static final String ERROR_TYPE_TENDER_ID = "09";
    /**
     * Error check for total amount check.
     */
    private static final String ERROR_TYPE_TOTAL_AMOUNT = "10";
    /**
     * Program id.
     */
    private static final String PROGRAM_ID = "SLS1300107";
    /**
     * Payoff data type.
     */
    private static final String PAY_OFF_DATA_TYPE = "2";
    /**
     * Data alteration type delete.
     */
    private static final String DATA_ALTERATION_TYPE_DELETE = "2";
    /**
     * Data alteration type upload.
     */
    private static final String DATA_ALTERATION_TYPE_UPLOAD = "3";
    /**
     * Exclusion valid time.
     */
    private static final String EXCLUSION_VALID_TIME = "60";
    /**
     * Update type correction.
     */
    private static final String UPDATE_TYPE_CORRECTION = "CORRECTION";
    /**
     * Update type correction.
     */
    private static final String UPDATE_TYPE_CORRECTION_IOEXCEPTION = "CORRECTION_IOEXCEPTION";
    /**
     * Update type correction.
     */
    private static final String UPDATE_TYPE_CORRECTION_GET_RESULT = "CORRECTION_GETRESULT";
    /**
     * Update type delete.
     */
    private static final String UPDATE_TYPE_DELETE = "DELETE";
    /**
     * Return type success.
     */
    private static final String RETURN_TYPE_SUCCESS = "success";
    /**
     * Integrity check error.
     */
    private static final String INTEGRITY_CHECK_ERROR = "integrityCheckError";
    /**
     * Revised by another user.
     */
    private static final String REVISED_BY_ANOTHER_USER = "revisedByAnotherUser";
    /**
     * Correction.
     */
    private static final String CORRECTION = "CORRECTION";

    /**
     * Delete.
     */
    private static final String DELETE = "DELETE";
    /**
     * Translation store code master mapper.
     */
    @Autowired
    private TranslationStoreCodeMasterOptionalMapper translationStoreCodeMasterOptionalMapper;
    /**
     * Alteration exclusion control mapper.
     */
    @Autowired
    private AlterationExclusionControlMapper alterationExclusionControlOptionalMapper;
    /**
     * Pay off data optional mapper.
     */
    @Autowired
    private PayoffDataOptionalMapper payOffDataOptionalMapper;
    /**
     * Sale transaction error detail mapper.
     */
    @Autowired
    private SalesTransactionErrorDetailOptionalMapper salesTransactionErrorDetailOptionalMapper;
    /** Component for getting message by locale. */
    @Autowired
    private LocaleMessageSource localeMessageSource;
    /**
     * The service for check transaction data.
     */
    @Autowired
    private CheckTransactionDataService dataCheckerService;

    /** Sales rest template repository. */
    @Autowired
    private SalesRestTemplateRepository salesRestTemplateRepository;

    /** Sales rest template repository. */
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

    /** Alteration data upload service helper. */
    @Autowired
    private AlterationDataUploadServiceHelper alterationDataUploadServiceHelper;

    /** UTC clock component. */
    @Autowired
    private SystemDateTime systemDateTime;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public boolean handleTransactionZip(MultipartFile multipartFile, String loginUserId) {

        List<CreateTransactionImportData> transactionImportDataList = null;
        try {
            transactionImportDataList = csvRead(multipartFile);
        } catch (Exception e) {
            throwBusinessException(MessagePrefix.E_SLS_66000125, MessagePrefix.E_SLS_66000125,
                    new Object[] {e.getLocalizedMessage()});
        }

        for (int i = 0; i < transactionImportDataList.size(); i++) {
            CreateTransactionImportData createTransactionImportData =
                    transactionImportDataList.get(i);
            TransactionImportData transactionImportData = new TransactionImportData();
            modelMapper.map(createTransactionImportData, transactionImportData);
            try {
                // Check transaction data.
                dataCheckerService.checkTransactionData(transactionImportData, loginUserId,
                        transactionImportData.getSalesTransactionErrorId());
            } catch (TransactionConsistencyErrorException e) {
                String errorType = e.getEntity().getErrorType();
                String transactionId = e.getEntity().getSalesTransactionId();
                if (errorType == ERROR_TYPE_NOT_NULL_ITEM) {
                    throwBusinessException(MessagePrefix.E_SLS_66000181,
                            MessagePrefix.E_SLS_66000181, new Object[] {transactionId});
                } else if (errorType == ERROR_TYPE_BUSINESS_DATE) {
                    throwBusinessException(MessagePrefix.E_SLS_66000182,
                            MessagePrefix.E_SLS_66000182, new Object[] {transactionId});
                } else if (errorType == ERROR_TYPE_CORRELATION) {
                    throwBusinessException(MessagePrefix.E_SLS_66000183,
                            MessagePrefix.E_SLS_66000183, new Object[] {transactionId});
                } else if (errorType == ERROR_TYPE_TENDER_ID) {
                    throwBusinessException(MessagePrefix.E_SLS_66000184,
                            MessagePrefix.E_SLS_66000184, new Object[] {transactionId});
                } else if (errorType == ERROR_TYPE_TOTAL_AMOUNT) {
                    throwBusinessException(MessagePrefix.E_SLS_66000185,
                            MessagePrefix.E_SLS_66000185, new Object[] {transactionId});
                } else {
                    throwBusinessException(MessagePrefix.E_SLS_66000179,
                            MessagePrefix.E_SLS_66000179, new Object[] {transactionId});
                }
            }
            SalesTransactionErrorDetail salesTransactionErrorDetail =
                    new SalesTransactionErrorDetail();
            if (CORRECTION.equals(transactionImportData.getUpdateType())) {
                salesTransactionErrorDetail
                        .setDataAlterationStatusType(DATA_ALTERATION_TYPE_UPLOAD);
            } else if (DELETE.equals(transactionImportData.getUpdateType())) {
                salesTransactionErrorDetail
                        .setDataAlterationStatusType(DATA_ALTERATION_TYPE_DELETE);
            }
            salesTransactionErrorDetail.setUpdateUserId(loginUserId);
            OffsetDateTime nowDateTime = OffsetDateTime.now();
            salesTransactionErrorDetail.setUpdateDatetime(nowDateTime);
            salesTransactionErrorDetail.setUpdateProgramId(PROGRAM_ID);
            salesTransactionErrorDetail
                    .setSalesTransactionErrorId(transactionImportData.getSalesTransactionErrorId());
            salesTransactionErrorDetailOptionalMapper
                    .updateByPrimaryKeySelective(salesTransactionErrorDetail);
            // Call transaction api.
            CommonStatus commonstatus = null;
            try {
                commonstatus = salesRestTemplateRepository
                        .callTransactionImport(createTransactionImportData);
            } catch (IllegalArgumentException e) {
                throwBusinessException(MessagePrefix.E_SLS_66000125, MessagePrefix.E_SLS_66000125,
                        new Object[] {UPDATE_TYPE_CORRECTION});
            }
            if (commonstatus.getResultCode().equals(ResultCode.ABNORMAL)) { // Create result
                throwBusinessException(MessagePrefix.E_SLS_66000125, MessagePrefix.E_SLS_66000125,
                        new Object[] {UPDATE_TYPE_CORRECTION_GET_RESULT});
            }
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public boolean handleTransactionJson(MultipartFile multipartFile, String loginUserId) {
        CreateTransactionImportData createTransactionImportData = null;
        try {
            createTransactionImportData = objectMapper.readValue(
                    IOUtils.toString(multipartFile.getInputStream(), "UTF-8"),
                    CreateTransactionImportData.class);
        } catch (IOException e) {
            throwBusinessException(MessagePrefix.E_SLS_66000125, MessagePrefix.E_SLS_66000125,
                    new Object[] {UPDATE_TYPE_CORRECTION_IOEXCEPTION});
        }

        TransactionImportData transactionImportData = new CreateTransactionImportData();
        modelMapper.map(createTransactionImportData, transactionImportData);
        try {
            // Check transaction data.
            dataCheckerService.checkTransactionData(transactionImportData, loginUserId,
                    transactionImportData.getSalesTransactionErrorId());
        } catch (TransactionConsistencyErrorException e) {
            String errorType = e.getEntity().getErrorType();
            String transactionId = e.getEntity().getSalesTransactionId();
            if (errorType == ERROR_TYPE_NOT_NULL_ITEM) {
                throwBusinessException(MessagePrefix.E_SLS_66000181, MessagePrefix.E_SLS_66000181,
                        new Object[] {transactionId});
            } else if (errorType == ERROR_TYPE_BUSINESS_DATE) {
                throwBusinessException(MessagePrefix.E_SLS_66000182, MessagePrefix.E_SLS_66000182,
                        new Object[] {transactionId});
            } else if (errorType == ERROR_TYPE_CORRELATION) {
                throwBusinessException(MessagePrefix.E_SLS_66000183, MessagePrefix.E_SLS_66000183,
                        new Object[] {transactionId});
            } else if (errorType == ERROR_TYPE_TENDER_ID) {
                throwBusinessException(MessagePrefix.E_SLS_66000184, MessagePrefix.E_SLS_66000184,
                        new Object[] {transactionId});
            } else if (errorType == ERROR_TYPE_TOTAL_AMOUNT) {
                throwBusinessException(MessagePrefix.E_SLS_66000185, MessagePrefix.E_SLS_66000185,
                        new Object[] {transactionId});
            } else {
                throwBusinessException(MessagePrefix.E_SLS_66000179, MessagePrefix.E_SLS_66000179,
                        new Object[] {transactionId});
            }
        }
        SalesTransactionErrorDetail salesTransactionErrorDetailOptional =
                new SalesTransactionErrorDetail();
        if (UPDATE_TYPE_CORRECTION.equals(transactionImportData.getUpdateType())) {
            salesTransactionErrorDetailOptional
                    .setDataAlterationStatusType(DATA_ALTERATION_TYPE_UPLOAD);
        } else if (UPDATE_TYPE_DELETE.equals(transactionImportData.getUpdateType())) {
            salesTransactionErrorDetailOptional
                    .setDataAlterationStatusType(DATA_ALTERATION_TYPE_DELETE);
        }
        salesTransactionErrorDetailOptional.setUpdateUserId(loginUserId);
        OffsetDateTime nowDateTime = OffsetDateTime.now();
        salesTransactionErrorDetailOptional.setUpdateDatetime(nowDateTime);
        salesTransactionErrorDetailOptional.setUpdateProgramId(PROGRAM_ID);
        salesTransactionErrorDetailOptional
                .setSalesTransactionErrorId(transactionImportData.getSalesTransactionErrorId());
        salesTransactionErrorDetailOptionalMapper
                .updateByPrimaryKeySelective(salesTransactionErrorDetailOptional);

        // Call transaction api.
        CommonStatus commonstatus = null;
        try {
            commonstatus =
                    salesRestTemplateRepository.callTransactionImport(createTransactionImportData);
        } catch (IllegalArgumentException e) {
            throwBusinessException(MessagePrefix.E_SLS_66000125, MessagePrefix.E_SLS_66000125,
                    new Object[] {UPDATE_TYPE_CORRECTION});
        }
        if (commonstatus.getResultCode().equals(ResultCode.ABNORMAL)) {
            throwBusinessException(MessagePrefix.E_SLS_66000125, MessagePrefix.E_SLS_66000125,
                    new Object[] {UPDATE_TYPE_CORRECTION_GET_RESULT});
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public String handlePayoffJson(MultipartFile multipartFile, String loginUserId) {
        try {
            PayOffDataForms payOffDataForms = objectMapper.readValue(
                    IOUtils.toString(multipartFile.getInputStream(), "UTF-8"),
                    PayOffDataForms.class);
            AlterationPayOffImportMultiData alterationPayOffImportMultiData = objectMapper
                    .readValue(IOUtils.toString(multipartFile.getInputStream(), "UTF-8"),
                            AlterationPayOffImportMultiData.class);
            ArrayList<PayOffDataForm> payoffdatalist = payOffDataForms.getPayoffDataFormList();
            for (int i = 0; i < payoffdatalist.size(); i++) {
                PayOffDataForm payOffDataForm = payoffdatalist.get(i);
                String businessDate =
                        StringUtils.remove(payOffDataForm.getPayOffDate(), CodeConstants.HYPHEN);
                // Check translation store code master optional.
                if (Objects.isNull(translationStoreCodeMasterOptionalMapper
                        .selectByPrimaryKey(payOffDataForm.getStoreCode()))) {
                    return INTEGRITY_CHECK_ERROR;
                }

                AlterationExclusionControlKey alterationExclusionControlOptionalKey =
                        new AlterationExclusionControlKey();
                alterationExclusionControlOptionalKey.setStoreCode(payOffDataForm.getStoreCode());
                alterationExclusionControlOptionalKey.setBusinessDate(businessDate);
                alterationExclusionControlOptionalKey
                        .setCashRegisterNo(payOffDataForm.getCashRegisterNo());
                alterationExclusionControlOptionalKey
                        .setDataAlterationType(DATA_ALTERATION_TYPE_DELETE);
                AlterationExclusionControl alterationExclusionControlOptional =
                        alterationExclusionControlOptionalMapper
                                .selectByPrimaryKey(alterationExclusionControlOptionalKey);
                if (alterationExclusionControlOptional == null) {
                    // Insert into alteration exclusion control.
                    AlterationExclusionControl alterationExclusionControlForInsert =
                            new AlterationExclusionControl();
                    OffsetDateTime nowDateTime = OffsetDateTime.now();
                    alterationExclusionControlForInsert.setStoreCode(payOffDataForm.getStoreCode());
                    alterationExclusionControlForInsert.setBusinessDate(businessDate);
                    alterationExclusionControlForInsert
                            .setCashRegisterNo(payOffDataForm.getCashRegisterNo());
                    alterationExclusionControlForInsert.setDataAlterationType(PAY_OFF_DATA_TYPE);
                    alterationExclusionControlForInsert.setExclusionValidTime(EXCLUSION_VALID_TIME);
                    alterationExclusionControlForInsert.setCreateUserId(loginUserId);
                    alterationExclusionControlForInsert.setCreateDatetime(nowDateTime);
                    alterationExclusionControlForInsert.setCreateProgramId(PROGRAM_ID);
                    alterationExclusionControlForInsert.setUpdateUserId(loginUserId);
                    alterationExclusionControlForInsert.setUpdateDatetime(nowDateTime);
                    alterationExclusionControlForInsert.setUpdateProgramId(PROGRAM_ID);
                    alterationExclusionControlOptionalMapper
                            .insert(alterationExclusionControlForInsert);
                } else {
                    // Check user isn't the same.
                    if (loginUserId.equals(alterationExclusionControlOptional.getCreateUserId())) {
                        OffsetDateTime nowDateTime = systemDateTime.now();

                        AlterationExclusionControl alterationExclusionControlForUpdate =
                                new AlterationExclusionControl();
                        alterationExclusionControlForUpdate
                                .setStoreCode(payOffDataForm.getStoreCode());
                        alterationExclusionControlForUpdate.setBusinessDate(businessDate);
                        alterationExclusionControlForUpdate
                                .setCashRegisterNo(payOffDataForm.getCashRegisterNo());
                        alterationExclusionControlForUpdate
                                .setDataAlterationType(PAY_OFF_DATA_TYPE);
                        alterationExclusionControlForUpdate.setCreateUserId(loginUserId);
                        alterationExclusionControlForUpdate.setCreateDatetime(nowDateTime);
                        alterationExclusionControlForUpdate.setCreateProgramId(PROGRAM_ID);
                        alterationExclusionControlForUpdate.setUpdateUserId(loginUserId);
                        alterationExclusionControlForUpdate.setUpdateDatetime(nowDateTime);
                        alterationExclusionControlForUpdate.setUpdateProgramId(PROGRAM_ID);
                        alterationExclusionControlOptionalMapper
                                .updateByPrimaryKeySelective(alterationExclusionControlForUpdate);
                    } else {
                        // Get now date time.
                        OffsetDateTime nowDateTime = systemDateTime.now();
                        // Get update date time.
                        OffsetDateTime updateDatetime =
                                alterationExclusionControlOptional.getUpdateDatetime();
                        // Compare to exclusion valid time.
                        if (nowDateTime.compareTo(updateDatetime.plusMinutes(Long.valueOf(
                                alterationExclusionControlOptional.getExclusionValidTime()))) < 0) {
                            return REVISED_BY_ANOTHER_USER;
                        }
                        OffsetDateTime nowDateTimeUpdate = systemDateTime.now();

                        AlterationExclusionControl alterationExclusionControlForUpdate =
                                new AlterationExclusionControl();
                        alterationExclusionControlForUpdate
                                .setStoreCode(payOffDataForm.getStoreCode());
                        alterationExclusionControlForUpdate.setBusinessDate(businessDate);
                        alterationExclusionControlForUpdate
                                .setCashRegisterNo(payOffDataForm.getCashRegisterNo());
                        alterationExclusionControlForUpdate
                                .setDataAlterationType(PAY_OFF_DATA_TYPE);
                        alterationExclusionControlForUpdate.setCreateUserId(loginUserId);
                        alterationExclusionControlForUpdate.setCreateDatetime(nowDateTimeUpdate);
                        alterationExclusionControlForUpdate.setCreateProgramId(PROGRAM_ID);
                        alterationExclusionControlForUpdate.setUpdateUserId(loginUserId);
                        alterationExclusionControlForUpdate.setUpdateDatetime(nowDateTime);
                        alterationExclusionControlForUpdate.setUpdateProgramId(PROGRAM_ID);
                        alterationExclusionControlOptionalMapper
                                .updateByPrimaryKeySelective(alterationExclusionControlForUpdate);

                    }
                }

                // Update payoff data table.
                ArrayList<PayOffType> payofftypelist = payOffDataForm.getPayOffTypeList();
                payofftypelist.forEach(payofftype -> {
                    PayoffDataOptional payOffDataOptional = new PayoffDataOptional();
                    OffsetDateTime nowDateTime = OffsetDateTime.now();
                    payOffDataOptional.setDataAlterationStatus(PAY_OFF_DATA_TYPE);
                    payOffDataOptional.setUpdateUserId(loginUserId);
                    payOffDataOptional.setUpdateDatetime(nowDateTime);
                    payOffDataOptional.setUpdateProgramId(PROGRAM_ID);
                    payOffDataOptional.setStoreCode(payOffDataForm.getStoreCode());
                    payOffDataOptional.setPayoffDate(businessDate);
                    payOffDataOptional.setCashRegisterNo(payOffDataForm.getCashRegisterNo());
                    payOffDataOptional.setPayoffTypeCode(payofftype.getPayoffTypeCode());
                    payOffDataOptional
                            .setPayoffTypeSubNumberCode(payofftype.getPayoffTypeSubNumberCode());
                    payOffDataOptionalMapper.updateByPrimaryKeySelective(payOffDataOptional);
                });
            }

            // Call alteration pay off data api.
            CommonStatus commonstatus = salesRestTemplateRepository
                    .callAlterationPayOffData(alterationPayOffImportMultiData);
            if (commonstatus.getResultCode().equals(ResultCode.ABNORMAL)) {
                throwBusinessException(MessagePrefix.E_SLS_66000126, MessagePrefix.E_SLS_66000126,
                        new Object[] {UPDATE_TYPE_CORRECTION});
            }
        } catch (Exception e) {
            throwBusinessException(MessagePrefix.E_SLS_66000126, MessagePrefix.E_SLS_66000126,
                    new Object[] {UPDATE_TYPE_CORRECTION});
        }
        return RETURN_TYPE_SUCCESS;
    }

    /**
     * Read csv file.
     * 
     * @param multipartFile Multipart file.
     * @return List JSON format data transfered from csv file.
     * @throws Exception that occurred.
     */
    private List<CreateTransactionImportData> csvRead(MultipartFile multipartFile)
            throws IOException {

        List<OrderInformation> orderInformationList = new ArrayList<>();
        List<SalesTransactionHeader> salesTransactionHeaderList = new ArrayList<>();
        List<ItemDetail> itemDetailList = new ArrayList<>();
        List<ItemDetailDiscount> itemDetailDiscountList = new ArrayList<>();
        List<ItemDetailTax> itemDetailTaxList = new ArrayList<>();
        List<SalesTransactionTax> salesTransactionTaxList = new ArrayList<>();
        List<SalesTransactionPayment> salesTransactionPaymentList = new ArrayList<>();
        List<SalesTransactionTotalCsv> salesTransactionTotalList = new ArrayList<>();

        try (ZipInputStream zipInputStream = new ZipInputStream(multipartFile.getInputStream())) {
            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                String fileName = entry.getName();
                if (Constants.ORDER_INFORMATION_FILE_NAME_CSV.equals(fileName)) {
                    orderInformationList = alterationDataUploadServiceHelper
                            .convertCsvToList(zipInputStream, OrderInformation.class);
                } else if (Constants.SALES_TRANSACTION_HEADER_FILE_NAME_CSV.equals(fileName)) {
                    salesTransactionHeaderList = alterationDataUploadServiceHelper
                            .convertCsvToList(zipInputStream, SalesTransactionHeader.class);
                } else if (Constants.ITEM_DETAIL_FILE_NAME_CSV.equals(fileName)) {
                    itemDetailList = alterationDataUploadServiceHelper
                            .convertCsvToList(zipInputStream, ItemDetail.class);
                } else if (Constants.ITEM_DETAIL_DISCOUNT_FILE_NAME_CSV.equals(fileName)) {
                    itemDetailDiscountList = alterationDataUploadServiceHelper
                            .convertCsvToList(zipInputStream, ItemDetailDiscount.class);
                } else if (Constants.ITEM_DETAIL_TAX_FILE_NAME_CSV.equals(fileName)) {
                    itemDetailTaxList = alterationDataUploadServiceHelper
                            .convertCsvToList(zipInputStream, ItemDetailTax.class);
                } else if (Constants.SALES_TRANSACTION_TAX_FILE_NAME_CSV.equals(fileName)) {
                    salesTransactionTaxList = alterationDataUploadServiceHelper
                            .convertCsvToList(zipInputStream, SalesTransactionTax.class);
                } else if (Constants.SALES_TRANSACTION_PAYMENT_FILE_NAME_CSV.equals(fileName)) {
                    salesTransactionPaymentList = alterationDataUploadServiceHelper
                            .convertCsvToList(zipInputStream, SalesTransactionPayment.class);
                } else if (Constants.SALES_TRANSACTION_TOTAL_FILE_NAME_CSV.equals(fileName)) {
                    salesTransactionTotalList = alterationDataUploadServiceHelper
                            .convertCsvToList(zipInputStream, SalesTransactionTotalCsv.class);
                }
                zipInputStream.closeEntry();
            }
            zipInputStream.close();
        }

        List<CreateTransactionImportData> transactionImportDataList = new ArrayList<>();
        for (int i = 0; i < orderInformationList.size(); i++) {
            OrderInformation orderInformation = orderInformationList.get(i);
            CreateTransactionImportData transactionImportData =
                    setTransactionImportData(orderInformation);
            String transactionId = transactionImportData.getSalesTransactionErrorId();
            List<Transaction> transactionList = new ArrayList<>();
            for (int j = 0; j < salesTransactionHeaderList.size(); j++) {

                SalesTransactionHeader salesTransactionHeader = salesTransactionHeaderList.get(j);
                if (transactionId.equals(salesTransactionHeader.getTransactionId())) {
                    Transaction transaction = setTransaction(salesTransactionHeader);
                    String orderSubNumber = salesTransactionHeader.getOrderSubNumber();
                    String salesTransactionId = salesTransactionHeader.getSalesTransactionId();
                    List<TransactionItemDetail> transactionItemDetailList = new ArrayList<>();
                    for (int k = 0; k < itemDetailList.size(); k++) {
                        ItemDetail itemDetail = itemDetailList.get(k);
                        if (transactionId.equals(itemDetail.getTransactionId())
                                && orderSubNumber.equals(itemDetail.getOrderSubNumber())
                                && salesTransactionId.equals(itemDetail.getSalesTransactionId())) {

                            TransactionItemDetail transactionItemDetail =
                                    setTransactionItemDetail(itemDetail);
                            String detailSubNumber = itemDetail.getDetailSubNumber();
                            List<ItemDiscount> itemDiscountList = new ArrayList<>();
                            for (int l = 0; l < itemDetailDiscountList.size(); l++) {
                                ItemDetailDiscount itemDetailDiscount =
                                        itemDetailDiscountList.get(l);
                                if (transactionId.equals(itemDetailDiscount.getTransactionId())
                                        && orderSubNumber
                                                .equals(itemDetailDiscount.getOrderSubNumber())
                                        && salesTransactionId
                                                .equals(itemDetailDiscount.getSalesTransactionId())
                                        && detailSubNumber
                                                .equals(itemDetailDiscount.getDetailSubNumber())) {
                                    ItemDiscount itemDiscount =
                                            setItemDiscountData(itemDetailDiscount);
                                    itemDiscountList.add(itemDiscount);
                                }
                            }
                            transactionItemDetail.setItemDiscountList(itemDiscountList);

                            List<ItemTaxDetail> itemTaxDetailList = new ArrayList<>();
                            if ("0".equals(detailSubNumber)) {
                                for (int m = 0; m < salesTransactionTaxList.size(); m++) {
                                    SalesTransactionTax salesTransactionTax =
                                            salesTransactionTaxList.get(m);
                                    if (transactionId.equals(salesTransactionTax.getTransactionId())
                                            && orderSubNumber
                                                    .equals(salesTransactionTax.getOrderSubNumber())
                                            && salesTransactionId.equals(
                                                    salesTransactionTax.getSalesTransactionId())
                                            && detailSubNumber.equals(
                                                    salesTransactionTax.getDetailSubNumber())) {
                                        ItemTaxDetail itemTaxDetail =
                                                setItemTaxDetail(salesTransactionTax);
                                        itemTaxDetailList.add(itemTaxDetail);
                                    }
                                }
                            } else {
                                for (int m = 0; m < itemDetailTaxList.size(); m++) {
                                    ItemDetailTax itemDetailTax = itemDetailTaxList.get(m);
                                    if (transactionId.equals(itemDetailTax.getTransactionId())
                                            && orderSubNumber
                                                    .equals(itemDetailTax.getOrderSubNumber())
                                            && salesTransactionId
                                                    .equals(itemDetailTax.getSalesTransactionId())
                                            && detailSubNumber
                                                    .equals(itemDetailTax.getDetailSubNumber())) {
                                        ItemTaxDetail itemTaxDetail =
                                                setItemTaxDetailData(itemDetailTax);
                                        itemTaxDetailList.add(itemTaxDetail);
                                    }
                                }
                            }
                            transactionItemDetail.setItemTaxDetailList(itemTaxDetailList);
                            transactionItemDetailList.add(transactionItemDetail);
                        }
                    }
                    transaction.setTransactionItemDetailList(transactionItemDetailList);
                    transaction.setSalesTransactionTenderList(salesTransactionPaymentList.stream()
                            .filter(salesTransactionPayment -> transactionId
                                    .equals(salesTransactionPayment.getTransactionId())
                                    && orderSubNumber
                                            .equals(salesTransactionPayment.getOrderSubNumber())
                                    && salesTransactionId.equals(
                                            salesTransactionPayment.getSalesTransactionId()))
                            .map(salesTransactionPayment -> setSalesTransactionTenderData(
                                    salesTransactionPayment))
                            .collect(Collectors.toList()));
                    transaction.setSalesTransactionTotalList(salesTransactionTotalList.stream()
                            .filter(salesTransactionTotalCsv -> transactionId
                                    .equals(salesTransactionTotalCsv.getTransactionId())
                                    && orderSubNumber
                                            .equals(salesTransactionTotalCsv.getOrderSubNumber())
                                    && salesTransactionId.equals(
                                            salesTransactionTotalCsv.getSalesTransactionId()))
                            .map(salesTransactionTotalCsv -> setSalesTransactionTotalData(
                                    salesTransactionTotalCsv))
                            .collect(Collectors.toList()));
                    transactionList.add(transaction);
                }
            }

            transactionImportData.setTransactionList(transactionList);
            transactionImportDataList.add(transactionImportData);
        }
        return transactionImportDataList;
    }

    /**
     * Set transaction.
     * 
     * @param salesTransactionHeader Sales transaction header.
     * @return Transaction data.
     */
    private Transaction setTransaction(SalesTransactionHeader salesTransactionHeader) {
        Transaction transaction = new Transaction();
        if (StringUtils.isNotEmpty(salesTransactionHeader.getOrderSubNumber())) {
            transaction
                    .setOrderSubNumber(Integer.valueOf(salesTransactionHeader.getOrderSubNumber()));
        }
        transaction.setSalesTransactionId(salesTransactionHeader.getSalesTransactionId());
        transaction.setIntegratedOrderId(salesTransactionHeader.getIntegratedOrderId());
        if (StringUtils.isNotEmpty(salesTransactionHeader.getSalesTransactionSubNumber())) {
            transaction.setTransactionSerialNumber(
                    Integer.valueOf(salesTransactionHeader.getSalesTransactionSubNumber()));
        }
        transaction.setStoreCode(salesTransactionHeader.getStoreCode());
        if (StringUtils.isNotEmpty(salesTransactionHeader.getDataCreationDateTime())) {
            transaction.setDataCreationDateTime(
                    OffsetDateTime.parse(salesTransactionHeader.getDataCreationDateTime(),
                            DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        }
        transaction
                .setDataCreationBusinessDate(salesTransactionHeader.getDataCreationBusinessDate());
        if (StringUtils.isNotEmpty(salesTransactionHeader.getCashRegisterNo())) {
            transaction
                    .setCashRegisterNo(Integer.valueOf(salesTransactionHeader.getCashRegisterNo()));
        }
        transaction.setReceiptNo(salesTransactionHeader.getReceiptNo());
        if (StringUtils.isNotEmpty(salesTransactionHeader.getSalesLinkageType())) {
            transaction.setSalesLinkageType(
                    Integer.valueOf(salesTransactionHeader.getSalesLinkageType()));
        }
        transaction.setTransactionType(salesTransactionHeader.getSalesTransactionType());
        if (StringUtils.isNotEmpty(salesTransactionHeader.getReturnType())) {
            transaction.setReturnType(Integer.valueOf(salesTransactionHeader.getReturnType()));
        }
        transaction.setSystemBrandCode(salesTransactionHeader.getSystemBrandCode());
        transaction.setSystemBusinessCode(salesTransactionHeader.getSystemBrandCode());
        transaction.setSystemCountryCode(salesTransactionHeader.getSystemCountryCode());
        transaction.setChannelCode(salesTransactionHeader.getChannelCode());
        transaction.setOrderStatus(salesTransactionHeader.getOrderStatus());
        transaction.setOrderSubstatus(salesTransactionHeader.getOrderSubStatus());
        transaction.setOrderStatusUpdateDate(salesTransactionHeader.getOrderStatusUpdateDate());
        if (StringUtils.isNotEmpty(salesTransactionHeader.getOrderStatusLastUpdateDateTime())) {
            transaction.setOrderStatusLastUpdateDateTime(
                    OffsetDateTime.parse(salesTransactionHeader.getOrderStatusLastUpdateDateTime(),
                            DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        }
        transaction.setCustomerId(salesTransactionHeader.getCustomerId());
        transaction.setOrderNumberForStorePayment(
                salesTransactionHeader.getOrderNumberForStorePayment());
        transaction
                .setAdvanceReceivedStoreCode(salesTransactionHeader.getAdvanceReceivedStoreCode());
        transaction.setAdvanceReceivedStoreSystemBrandCode(
                salesTransactionHeader.getAdvanceReceivedStoreSystemBrandCode());
        transaction.setAdvanceReceivedStoreSystemBusinessCode(
                salesTransactionHeader.getAdvanceReceivedStoreSystemBusinessCode());
        transaction.setAdvanceReceivedStoreSystemCountryCode(
                salesTransactionHeader.getAdvanceReceivedStoreSystemCountryCode());
        transaction.setOperatorCode(salesTransactionHeader.getOperatorCode());
        transaction.setOriginalTransactionId(salesTransactionHeader.getOriginalTransactionId());
        if (StringUtils.isNotEmpty(salesTransactionHeader.getOriginalCashRegisterNo())) {
            transaction.setOriginalCashRegisterNo(
                    Integer.valueOf(salesTransactionHeader.getOriginalCashRegisterNo()));
        }
        transaction.setOriginalReceiptNo(salesTransactionHeader.getOriginalReceiptNo());
        Price depositPice = new Price();
        if (StringUtils.isNotEmpty(salesTransactionHeader.getDepositCurrencyCode())) {
            depositPice.setCurrencyCode(
                    Currency.getInstance(salesTransactionHeader.getDepositCurrencyCode()));
        }
        if (StringUtils.isNotEmpty(salesTransactionHeader.getDepositValue())) {
            depositPice.setValue(new BigDecimal(salesTransactionHeader.getDepositValue()));
        }
        transaction.setDeposit(depositPice);
        Price changePice = new Price();
        if (StringUtils.isNotEmpty(salesTransactionHeader.getChangeCurrencyCode())) {
            changePice.setCurrencyCode(
                    Currency.getInstance(salesTransactionHeader.getChangeCurrencyCode()));
        }
        if (StringUtils.isNotEmpty(salesTransactionHeader.getChangeValue())) {
            changePice.setValue(new BigDecimal(salesTransactionHeader.getChangeValue()));
        }
        transaction.setChange(changePice);
        transaction.setReceiptNoForCreditCardCancellation(
                salesTransactionHeader.getReceiptNoForCreditCardCancellation());
        transaction.setReceiptNoForCreditCard(salesTransactionHeader.getReceiptNoForCreditCard());
        if (StringUtils.isNotEmpty(salesTransactionHeader.getEmployeeSaleFlag())) {
            transaction.setEmployeeSaleFlag(
                    Boolean.valueOf(salesTransactionHeader.getEmployeeSaleFlag()));
        }
        if (StringUtils.isNotEmpty(salesTransactionHeader.getConsistencySalesFlag())) {
            transaction.setConsistencySalesFlag(
                    Boolean.valueOf(salesTransactionHeader.getConsistencySalesFlag()));
        }
        transaction.setCorporateId(salesTransactionHeader.getCorporateId());
        if (StringUtils.isNotEmpty(salesTransactionHeader.getSalesTransactionDiscountFlag())) {
            transaction.setSalesTransactionDiscountFlag(
                    Boolean.valueOf(salesTransactionHeader.getSalesTransactionDiscountFlag()));
        }
        Price salesTransactionDiscountAmountRatePice = new Price();
        if (StringUtils.isNotEmpty(
                salesTransactionHeader.getSalesTransactionDiscountAmountRateCurrencyCode())) {
            salesTransactionDiscountAmountRatePice.setCurrencyCode(Currency.getInstance(
                    salesTransactionHeader.getSalesTransactionDiscountAmountRateCurrencyCode()));
        }
        if (StringUtils
                .isNotEmpty(salesTransactionHeader.getSalesTransactionDiscountAmountRate())) {
            salesTransactionDiscountAmountRatePice.setValue(
                    new BigDecimal(salesTransactionHeader.getSalesTransactionDiscountAmountRate()));
        }
        transaction.setSalesTransactionDiscountAmountRate(salesTransactionDiscountAmountRatePice);
        return transaction;
    }

    /**
     * Set transaction item detail.
     * 
     * @param itemDetail Item detail.
     * @return Transaction item detail.
     */
    private TransactionItemDetail setTransactionItemDetail(ItemDetail itemDetail) {
        TransactionItemDetail transactionItemDetail = new TransactionItemDetail();
        if (StringUtils.isNotEmpty(itemDetail.getDetailSubNumber())) {
            transactionItemDetail
                    .setDetailSubNumber(Integer.valueOf(itemDetail.getDetailSubNumber()));
        }
        transactionItemDetail
                .setDetailListSalesTransactionType(itemDetail.getItemDetailSubNumber());
        transactionItemDetail
                .setDetailListSalesTransactionType(itemDetail.getSalesTransactionType());
        transactionItemDetail.setSystemBrandCode(itemDetail.getSystemBrandCode());
        transactionItemDetail.setL2ItemCode(itemDetail.getL2ItemCode());
        transactionItemDetail.setViewL2ItemCode(itemDetail.getDisplayL2ItemCode());
        transactionItemDetail.setL2ProductName(itemDetail.getL2ProductName());
        transactionItemDetail.setL3ItemCode(itemDetail.getL3ItemCode());
        transactionItemDetail.setL3PosProductName(itemDetail.getL3PosProductName());
        transactionItemDetail.setEpcCode(itemDetail.getEpcCode());
        transactionItemDetail.setGDepartmentCode(itemDetail.getGDepartmentCode());
        if (StringUtils.isNotEmpty(itemDetail.getMajorCategoryCode())) {
            transactionItemDetail.setDeptCode(Integer.valueOf(itemDetail.getMajorCategoryCode()));
        }
        transactionItemDetail.setQuantityCode(itemDetail.getQuantityCode());
        if (StringUtils.isNotEmpty(itemDetail.getDetailQuantity())) {
            transactionItemDetail.setItemQty(Integer.valueOf(itemDetail.getDetailQuantity()));
        }
        Price itemCostPrice = new Price();
        if (StringUtils.isNotEmpty(itemDetail.getItemCostCurrencyCode())) {
            itemCostPrice
                    .setCurrencyCode(Currency.getInstance(itemDetail.getItemCostCurrencyCode()));
        }
        if (StringUtils.isNotEmpty(itemDetail.getItemCostValue())) {
            itemCostPrice.setValue(new BigDecimal(itemDetail.getItemCostValue()));
        }
        transactionItemDetail.setItemCost(itemCostPrice);

        Price initialSellingCostPrice = new Price();
        if (StringUtils.isNotEmpty(itemDetail.getInitialSellingPriceCurrencyCode())) {
            initialSellingCostPrice.setCurrencyCode(
                    Currency.getInstance(itemDetail.getInitialSellingPriceCurrencyCode()));
        }
        if (StringUtils.isNotEmpty(itemDetail.getInitialSellingPrice())) {
            initialSellingCostPrice.setValue(new BigDecimal(itemDetail.getInitialSellingPrice()));
        }
        transactionItemDetail.setInitialSellingPrice(initialSellingCostPrice);

        Price bitemSellingPrice = new Price();
        if (StringUtils.isNotEmpty(itemDetail.getBclassPriceCurrencyCode())) {
            bitemSellingPrice
                    .setCurrencyCode(Currency.getInstance(itemDetail.getBclassPriceCurrencyCode()));
        }
        if (StringUtils.isNotEmpty(itemDetail.getBclassPrice())) {
            bitemSellingPrice.setValue(new BigDecimal(itemDetail.getBclassPrice()));
        }
        transactionItemDetail.setBItemSellingPrice(bitemSellingPrice);

        Price itemNewPrice = new Price();
        if (StringUtils.isNotEmpty(itemDetail.getNewPriceCurrencyCode())) {
            itemNewPrice
                    .setCurrencyCode(Currency.getInstance(itemDetail.getNewPriceCurrencyCode()));
        }
        if (StringUtils.isNotEmpty(itemDetail.getNewPrice())) {
            itemNewPrice.setValue(new BigDecimal(itemDetail.getNewPrice()));
        }
        transactionItemDetail.setItemNewPrice(itemNewPrice);

        Price itemUnitPriceTaxExcludedPrice = new Price();
        if (StringUtils.isNotEmpty(itemDetail.getRetailUnitPriceTaxExcludedCurrencyCode())) {
            itemUnitPriceTaxExcludedPrice.setCurrencyCode(
                    Currency.getInstance(itemDetail.getRetailUnitPriceTaxExcludedCurrencyCode()));
        }
        if (StringUtils.isNotEmpty(itemDetail.getRetailUnitPriceTaxExcluded())) {
            itemUnitPriceTaxExcludedPrice
                    .setValue(new BigDecimal(itemDetail.getRetailUnitPriceTaxExcluded()));
        }
        transactionItemDetail.setItemUnitPriceTaxExcluded(itemUnitPriceTaxExcludedPrice);

        Price itemUnitPriceTaxIncludedPrice = new Price();
        if (StringUtils.isNotEmpty(itemDetail.getRetailUnitPriceTaxIncludedCurrencyCode())) {
            itemUnitPriceTaxIncludedPrice.setCurrencyCode(
                    Currency.getInstance(itemDetail.getRetailUnitPriceTaxIncludedCurrencyCode()));
        }
        if (StringUtils.isNotEmpty(itemDetail.getRetailUnitPriceTaxIncluded())) {
            itemUnitPriceTaxIncludedPrice
                    .setValue(new BigDecimal(itemDetail.getRetailUnitPriceTaxIncluded()));
        }
        transactionItemDetail.setItemUnitPriceTaxIncluded(itemUnitPriceTaxIncludedPrice);

        Price itemSalesAmtTaxExcludedPrice = new Price();
        if (StringUtils.isNotEmpty(itemDetail.getSalesAmountTaxExcludedCurrencyCode())) {
            itemSalesAmtTaxExcludedPrice.setCurrencyCode(
                    Currency.getInstance(itemDetail.getSalesAmountTaxExcludedCurrencyCode()));
        }
        if (StringUtils.isNotEmpty(itemDetail.getSalesAmountTaxExcluded())) {
            itemSalesAmtTaxExcludedPrice
                    .setValue(new BigDecimal(itemDetail.getSalesAmountTaxExcluded()));
        }
        transactionItemDetail.setItemSalesAmtTaxExcluded(itemSalesAmtTaxExcludedPrice);

        Price itemSalesAmtTaxIncludedPrice = new Price();
        if (StringUtils.isNotEmpty(itemDetail.getSalesAmountTaxIncludedCurrencyCode())) {
            itemSalesAmtTaxIncludedPrice.setCurrencyCode(
                    Currency.getInstance(itemDetail.getSalesAmountTaxIncludedCurrencyCode()));
        }
        if (StringUtils.isNotEmpty(itemDetail.getSalesAmountTaxIncluded())) {
            itemSalesAmtTaxIncludedPrice
                    .setValue(new BigDecimal(itemDetail.getSalesAmountTaxIncluded()));
        }
        transactionItemDetail.setItemSalesAmtTaxIncluded(itemSalesAmtTaxIncludedPrice);

        transactionItemDetail
                .setCalculationUnavailableType(itemDetail.getCalculationUnavailableType());
        if (StringUtils.isNotEmpty(itemDetail.getBundlePurchaseApplicableQuantity())) {
            transactionItemDetail.setBundlePurchaseQty(
                    Integer.valueOf(itemDetail.getBundlePurchaseApplicableQuantity()));
        }
        Price bundlePurchasePrice = new Price();
        if (StringUtils.isNotEmpty(itemDetail.getBundlePurchaseApplicablePriceCurrencyCode())) {
            bundlePurchasePrice.setCurrencyCode(Currency
                    .getInstance(itemDetail.getBundlePurchaseApplicablePriceCurrencyCode()));
        }
        if (StringUtils.isNotEmpty(itemDetail.getBundlePurchaseApplicablePrice())) {
            bundlePurchasePrice
                    .setValue(new BigDecimal(itemDetail.getBundlePurchaseApplicablePrice()));
        }
        transactionItemDetail.setBundlePurchasePrice(bundlePurchasePrice);
        if (StringUtils.isNotEmpty(itemDetail.getBundlePurchaseIndex())) {
            transactionItemDetail
                    .setBundlePurchaseIndex(Integer.valueOf(itemDetail.getBundlePurchaseIndex()));
        }
        if (StringUtils.isNotEmpty(itemDetail.getLimitedAmountPromotionCount())) {
            transactionItemDetail.setLimitedAmountPromotionCount(
                    Integer.valueOf(itemDetail.getLimitedAmountPromotionCount()));
        }
        transactionItemDetail.setItemMountDiscountType(itemDetail.getStoreItemDiscountType());

        Price itemDiscountAmountPrice = new Price();
        if (StringUtils.isNotEmpty(itemDetail.getStoreItemDiscountCurrencyCode())) {
            itemDiscountAmountPrice.setCurrencyCode(
                    Currency.getInstance(itemDetail.getStoreItemDiscountCurrencyCode()));
        }
        if (StringUtils.isNotEmpty(itemDetail.getStoreItemDiscountSetting())) {
            itemDiscountAmountPrice
                    .setValue(new BigDecimal(itemDetail.getStoreItemDiscountSetting()));
        }
        transactionItemDetail.setItemDiscountAmount(itemDiscountAmountPrice);
        if (StringUtils.isNotEmpty(itemDetail.getStoreBundleSaleFlag())) {
            transactionItemDetail.setBundleSalesFlag(
                    StringUtility.changeStringToBoolean(itemDetail.getStoreBundleSaleFlag()));
        }
        Price bundleSalesPrice = new Price();
        if (StringUtils.isNotEmpty(itemDetail.getStoreBundleSalePriceCurrencyCode())) {
            bundleSalesPrice.setCurrencyCode(
                    Currency.getInstance(itemDetail.getStoreBundleSalePriceCurrencyCode()));
        }
        if (StringUtils.isNotEmpty(itemDetail.getStoreBundleSalePrice())) {
            bundleSalesPrice.setValue(new BigDecimal(itemDetail.getStoreBundleSalePrice()));
        }
        transactionItemDetail.setBundleSalesPrice(bundleSalesPrice);
        if (StringUtils.isNotEmpty(itemDetail.getSalesDetailIndex())) {
            transactionItemDetail
                    .setBundleSalesDetailIndex(Integer.valueOf(itemDetail.getSalesDetailIndex()));
        }
        transactionItemDetail.setItemTaxationType(itemDetail.getTaxationType());
        transactionItemDetail.setItemTaxKind(itemDetail.getTaxSystemType());
        return transactionItemDetail;
    }

    /**
     * Set item discount data.
     * 
     * @param itemDetailDiscount Item detail discount.
     * @return Item discount.
     */
    private ItemDiscount setItemDiscountData(ItemDetailDiscount itemDetailDiscount) {
        ItemDiscount itemDiscount = new ItemDiscount();
        itemDiscount.setItemPromotionType(itemDetailDiscount.getPromotionType());
        itemDiscount.setItemPromotionNumber(itemDetailDiscount.getPromotionNo());
        itemDiscount.setItemStoreDiscountType(itemDetailDiscount.getStoreDiscountType());
        if (StringUtils.isNotEmpty(itemDetailDiscount.getItemDiscountSubNumber())) {
            itemDiscount.setItemDiscountDetailSubNumber(
                    Integer.valueOf(itemDetailDiscount.getItemDiscountSubNumber()));
        }
        itemDiscount.setItemQuantityCode(itemDetailDiscount.getQuantityCode());

        Price itemDiscountAmtTaxExcludedPrice = new Price();
        if (StringUtils.isNotEmpty(itemDetailDiscount.getDiscountAmountTaxExcludedCurrencyCode())) {
            itemDiscountAmtTaxExcludedPrice.setCurrencyCode(Currency
                    .getInstance(itemDetailDiscount.getDiscountAmountTaxExcludedCurrencyCode()));
        }
        if (StringUtils.isNotEmpty(itemDetailDiscount.getDiscountAmountTaxExcluded())) {
            itemDiscountAmtTaxExcludedPrice
                    .setValue(new BigDecimal(itemDetailDiscount.getDiscountAmountTaxExcluded()));
        }
        itemDiscount.setItemDiscountAmtTaxExcluded(itemDiscountAmtTaxExcludedPrice);

        Price itemDiscountAmtTaxIncludedPrice = new Price();
        if (StringUtils.isNotEmpty(itemDetailDiscount.getDiscountAmountTaxIncludedCurrencyCode())) {
            itemDiscountAmtTaxIncludedPrice.setCurrencyCode(Currency
                    .getInstance(itemDetailDiscount.getDiscountAmountTaxIncludedCurrencyCode()));
        }
        if (StringUtils.isNotEmpty(itemDetailDiscount.getDiscountAmountTaxIncluded())) {
            itemDiscountAmtTaxIncludedPrice
                    .setValue(new BigDecimal(itemDetailDiscount.getDiscountAmountTaxIncluded()));
        }
        itemDiscount.setItemDiscountAmtTaxIncluded(itemDiscountAmtTaxIncludedPrice);
        return itemDiscount;
    }

    /**
     * Set item tax detail.
     * 
     * @param salesTransactionTax Sales transaction tax.
     * @return Item tax detail.
     */
    private ItemTaxDetail setItemTaxDetail(SalesTransactionTax salesTransactionTax) {
        ItemTaxDetail itemTaxDetail = new ItemTaxDetail();
        if (StringUtils.isNotEmpty(salesTransactionTax.getTaxSubNumber())) {
            itemTaxDetail
                    .setItemTaxSubNumber(Integer.valueOf(salesTransactionTax.getTaxSubNumber()));
        }
        if (StringUtils.isNotEmpty(salesTransactionTax.getTaxAmountSign())) {
            itemTaxDetail.setItemTaxAmountSign(salesTransactionTax.getTaxAmountSign());
        }
        Price itemTaxAmtPrice = new Price();
        if (StringUtils.isNotEmpty(salesTransactionTax.getTaxAmountCurrencyCode())) {
            itemTaxAmtPrice.setCurrencyCode(
                    Currency.getInstance(salesTransactionTax.getTaxAmountCurrencyCode()));
        }
        if (StringUtils.isNotEmpty(salesTransactionTax.getTaxAmountValue())) {
            itemTaxAmtPrice.setValue(new BigDecimal(salesTransactionTax.getTaxAmountValue()));
        }
        itemTaxDetail.setItemTaxAmt(itemTaxAmtPrice);
        if (StringUtils.isNotEmpty(salesTransactionTax.getTaxRate())) {
            itemTaxDetail.setItemTaxRate(new BigDecimal(salesTransactionTax.getTaxRate()));
        }
        itemTaxDetail.setItemTaxName(salesTransactionTax.getTaxName());
        return itemTaxDetail;
    }

    /**
     * Set item tax detail data.
     * 
     * @param itemDetailTax Item detail tax.
     * @return Item tax detail data.
     */
    private ItemTaxDetail setItemTaxDetailData(ItemDetailTax itemDetailTax) {
        ItemTaxDetail itemTaxDetail = new ItemTaxDetail();
        if (StringUtils.isNotEmpty(itemDetailTax.getTaxSubNumber())) {
            itemTaxDetail.setItemTaxSubNumber(Integer.valueOf(itemDetailTax.getTaxSubNumber()));
        }
        itemTaxDetail.setItemTaxAmountSign(itemDetailTax.getTaxAmountSign());
        Price itemTaxAmtPrice = new Price();
        if (StringUtils.isNotEmpty(itemDetailTax.getTaxAmountCurrencyCode())) {
            itemTaxAmtPrice.setCurrencyCode(
                    Currency.getInstance(itemDetailTax.getTaxAmountCurrencyCode()));
        }
        if (StringUtils.isNotEmpty(itemDetailTax.getTaxAmountValue())) {
            itemTaxAmtPrice.setValue(new BigDecimal(itemDetailTax.getTaxAmountValue()));
        }
        itemTaxDetail.setItemTaxAmt(itemTaxAmtPrice);
        if (StringUtils.isNotEmpty(itemDetailTax.getTaxRate())) {
            itemTaxDetail.setItemTaxRate(new BigDecimal(itemDetailTax.getTaxRate()));
        }
        itemTaxDetail.setItemTaxName(itemDetailTax.getTaxName());
        return itemTaxDetail;
    }

    /**
     * Set sales transaction tender data.
     * 
     * @param salesTransactionPayment Sales transaction payment.
     * @return Sales transaction tender data.
     */
    private SalesTransactionTender setSalesTransactionTenderData(
            SalesTransactionPayment salesTransactionPayment) {
        SalesTransactionTender salesTransactionTender = new SalesTransactionTender();
        salesTransactionTender.setTenderGroupId(salesTransactionPayment.getTenderGroup());
        salesTransactionTender.setTenderId(salesTransactionPayment.getTenderId());
        if (StringUtils.isNotEmpty(salesTransactionPayment.getTenderSubNumber())) {
            salesTransactionTender.setTenderSubNumber(
                    Integer.valueOf(salesTransactionPayment.getTenderSubNumber()));
        }
        salesTransactionTender.setPaymentSign(salesTransactionPayment.getPaymentSign());

        Price taxIncludedPaymentAmountPrice = new Price();
        if (StringUtils
                .isNotEmpty(salesTransactionPayment.getTaxIncludedPaymentAmountCurrencyCode())) {
            taxIncludedPaymentAmountPrice.setCurrencyCode(Currency.getInstance(
                    salesTransactionPayment.getTaxIncludedPaymentAmountCurrencyCode()));
        }
        if (StringUtils.isNotEmpty(salesTransactionPayment.getTaxIncludedPaymentAmountValue())) {
            taxIncludedPaymentAmountPrice.setValue(
                    new BigDecimal(salesTransactionPayment.getTaxIncludedPaymentAmountValue()));
        }
        salesTransactionTender.setTaxIncludedPaymentAmount(taxIncludedPaymentAmountPrice);

        TenderInfo tenderInfoList = new TenderInfo();
        Price discountAmountPrice = new Price();
        if (StringUtils.isNotEmpty(salesTransactionPayment.getDiscountValueCurrencyCode())) {
            discountAmountPrice.setCurrencyCode(
                    Currency.getInstance(salesTransactionPayment.getDiscountValueCurrencyCode()));
        }
        if (StringUtils.isNotEmpty(salesTransactionPayment.getDiscountValue())) {
            discountAmountPrice
                    .setValue(new BigDecimal(salesTransactionPayment.getDiscountValue()));
        }
        tenderInfoList.setDiscountAmount(discountAmountPrice);
        if (StringUtils.isNotEmpty(salesTransactionPayment.getDiscountRate())) {
            tenderInfoList
                    .setDiscountRate(new BigDecimal(salesTransactionPayment.getDiscountRate()));
        }
        tenderInfoList.setDiscountCodeIdCorporateId(
                salesTransactionPayment.getDiscountCodeIdCorporateId());
        tenderInfoList.setCouponType(salesTransactionPayment.getCouponType());

        Price couponDiscountAmountSettingPrice = new Price();
        if (StringUtils
                .isNotEmpty(salesTransactionPayment.getCouponDiscountAmountSettingCurrencyCode())) {
            couponDiscountAmountSettingPrice.setCurrencyCode(Currency.getInstance(
                    salesTransactionPayment.getCouponDiscountAmountSettingCurrencyCode()));
        }
        if (StringUtils.isNotEmpty(salesTransactionPayment.getCouponDiscountAmountSettingValue())) {
            couponDiscountAmountSettingPrice.setValue(
                    new BigDecimal(salesTransactionPayment.getCouponDiscountAmountSettingValue()));
        }
        tenderInfoList.setCouponDiscountAmountSetting(couponDiscountAmountSettingPrice);

        Price couponMinUsageAmountThresholdPrice = new Price();
        if (StringUtils.isNotEmpty(
                salesTransactionPayment.getCouponMinUsageAmountThresholdCurrencyCode())) {
            couponMinUsageAmountThresholdPrice.setCurrencyCode(Currency.getInstance(
                    salesTransactionPayment.getCouponMinUsageAmountThresholdCurrencyCode()));
        }
        if (StringUtils
                .isNotEmpty(salesTransactionPayment.getCouponMinUsageAmountThresholdValue())) {
            couponMinUsageAmountThresholdPrice.setValue(new BigDecimal(
                    salesTransactionPayment.getCouponMinUsageAmountThresholdValue()));
        }
        tenderInfoList.setCouponMinUsageAmountThreshold(couponMinUsageAmountThresholdPrice);

        tenderInfoList.setCouponUserId(salesTransactionPayment.getCouponUserId());
        tenderInfoList.setCardNo(salesTransactionPayment.getCardNo());
        tenderInfoList.setCreditApprovalCode(salesTransactionPayment.getCreditApprovalCode());
        tenderInfoList.setCreditProcessingSerialNumber(
                salesTransactionPayment.getCreditProcessingSerialNumber());
        tenderInfoList.setCreditPaymentType(salesTransactionPayment.getCreditPaymentType());
        if (StringUtils.isNotEmpty(salesTransactionPayment.getCreditPaymentCount())) {
            tenderInfoList.setCreditPaymentCount(
                    Integer.valueOf(salesTransactionPayment.getCreditPaymentCount()));
        }
        salesTransactionTender.setTenderInfoList(tenderInfoList);
        return salesTransactionTender;
    }

    /**
     * Set sales transaction total data.
     * 
     * @param salesTransactionTotalCsv Sales transaction total csv.
     * @return Sales transaction total data.
     */
    private SalesTransactionTotal setSalesTransactionTotalData(
            SalesTransactionTotalCsv salesTransactionTotalCsv) {
        SalesTransactionTotal salesTransactionTotal = new SalesTransactionTotal();
        salesTransactionTotal.setTotalType(salesTransactionTotalCsv.getTotalType());
        if (StringUtils.isNotEmpty(salesTransactionTotalCsv.getTotalAmountSubNumber())) {
            salesTransactionTotal.setTotalAmountSubNumber(
                    Integer.valueOf(salesTransactionTotalCsv.getTotalAmountSubNumber()));
        }
        Price totalAmountTaxExcludedPrice = new Price();
        if (StringUtils
                .isNotEmpty(salesTransactionTotalCsv.getTotalAmountTaxExcludedCurrencyCode())) {
            totalAmountTaxExcludedPrice.setCurrencyCode(Currency
                    .getInstance(salesTransactionTotalCsv.getTotalAmountTaxExcludedCurrencyCode()));
        }
        if (StringUtils.isNotEmpty(salesTransactionTotalCsv.getTotalAmountTaxExcludedValue())) {
            totalAmountTaxExcludedPrice.setValue(
                    new BigDecimal(salesTransactionTotalCsv.getTotalAmountTaxExcludedValue()));
        }
        salesTransactionTotal.setTotalAmountTaxExcluded(totalAmountTaxExcludedPrice);

        Price totalAmountTaxIncludedPrice = new Price();
        if (StringUtils
                .isNotEmpty(salesTransactionTotalCsv.getTotalAmountTaxIncludedCurrencyCode())) {
            totalAmountTaxIncludedPrice.setCurrencyCode(Currency
                    .getInstance(salesTransactionTotalCsv.getTotalAmountTaxIncludedCurrencyCode()));
        }
        if (StringUtils.isNotEmpty(salesTransactionTotalCsv.getTotalAmountTaxIncludedValue())) {
            totalAmountTaxIncludedPrice.setValue(
                    new BigDecimal(salesTransactionTotalCsv.getTotalAmountTaxIncludedValue()));
        }
        salesTransactionTotal.setTotalAmountTaxIncluded(totalAmountTaxIncludedPrice);
        return salesTransactionTotal;
    }

    /**
     * Set transaction import data.
     * 
     * @param orderInformation Order information.
     * @return Create transaction import data.
     */
    private CreateTransactionImportData setTransactionImportData(
            OrderInformation orderInformation) {
        CreateTransactionImportData transactionImportData = new CreateTransactionImportData();
        transactionImportData.setIntegratedOrderId(orderInformation.getIntegratedOrderId());
        transactionImportData.setOrderBarcodeNumber(orderInformation.getOrderBarcodeNumber());
        transactionImportData.setStoreCode(orderInformation.getStoreCode());
        transactionImportData.setSystemBrandCode(orderInformation.getSystemBrandCode());
        transactionImportData.setSystemBusinessCode(orderInformation.getSystemBrandCode());
        transactionImportData.setSystemCountryCode(orderInformation.getSystemCountryCode());
        transactionImportData.setChannelCode(orderInformation.getChannelCode());
        transactionImportData.setUpdateType(orderInformation.getUpdateType());
        transactionImportData.setCustomerId(orderInformation.getCustomerId());
        transactionImportData.setOrderConfirmationBusinessDate(
                orderInformation.getOrderConfirmationBusinessDate());
        if (StringUtils.isNotEmpty(orderInformation.getOrderConfirmationDateTime())) {
            transactionImportData.setOrderConfirmationDateTime(
                    OffsetDateTime.parse(orderInformation.getOrderConfirmationDateTime(),
                            DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        }
        if (StringUtils.isNotEmpty(orderInformation.getErrorCheckType())) {
            transactionImportData
                    .setErrorCheckType(Integer.valueOf(orderInformation.getErrorCheckType()));
        }
        if (StringUtils.isNotEmpty(orderInformation.getDataAlterationSalesLinkageType())) {
            transactionImportData.setDataAlterationSalesLinkageType(
                    Integer.valueOf(orderInformation.getDataAlterationSalesLinkageType()));
        }
        if (StringUtils.isNotEmpty(orderInformation.getDataAlterationBackboneLinkageType())) {
            transactionImportData.setDataAlterationBackboneLinkageType(
                    Integer.valueOf(orderInformation.getDataAlterationBackboneLinkageType()));
        }
        if (StringUtils.isNotEmpty(orderInformation.getDataAlterationEditingFlag())) {
            transactionImportData.setDataCorrectionEditingFlag(StringUtility
                    .changeStringToBoolean(orderInformation.getDataAlterationEditingFlag()));
        }
        transactionImportData.setDataCorrectionUserId(orderInformation.getDataAlterationUserId());
        String transactionId = orderInformation.getTransactionId();
        transactionImportData.setSalesTransactionErrorId(transactionId);
        return transactionImportData;
    }


    /**
     * Throw business exception.
     *
     * @param debugId Debug id.
     * @param messageId Message id.
     * @param arguments The array of variables to set in the message variable part.
     */
    private void throwBusinessException(String debugId, String messageId, Object[] arguments) {
        throw new BusinessException(ScreenCommonUtility.createResultObject(localeMessageSource,
                ErrorName.Business.BUSINESS_CHECK_ERROR, debugId, messageId, arguments));
    }
}
