/**
 * @(#)SalesTransactionErrorListServiceImpl.java
 *
 *                                               Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salestransactionerrorlist.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fastretailing.dcp.common.constants.ErrorName;
import com.fastretailing.dcp.common.constants.LogLevel;
import com.fastretailing.dcp.common.exception.BusinessException;
import com.fastretailing.dcp.common.util.SystemDateTime;
import com.fastretailing.dcp.sales.common.constants.CodeConstants;
import com.fastretailing.dcp.sales.common.constants.MessagePrefix;
import com.fastretailing.dcp.sales.common.constants.SalesFunctionId;
import com.fastretailing.dcp.sales.common.entity.AlterationExclusionControl;
import com.fastretailing.dcp.sales.common.entity.AlterationExclusionControlKey;
import com.fastretailing.dcp.sales.common.entity.CommonCodeMaster;
import com.fastretailing.dcp.sales.common.entity.CommonCodeMasterCondition;
import com.fastretailing.dcp.sales.common.entity.CommonCodeMasterKey;
import com.fastretailing.dcp.sales.common.entity.optional.ErrorEvacuationSalesOrderInformationOptional;
import com.fastretailing.dcp.sales.common.entity.optional.SalesTransactionErrorDetail;
import com.fastretailing.dcp.sales.common.entity.optional.SalesTransactionErrorDetailCondition;
import com.fastretailing.dcp.sales.common.entity.optional.SalesTransactionHistoryOptional;
import com.fastretailing.dcp.sales.common.repository.AlterationExclusionControlMapper;
import com.fastretailing.dcp.sales.common.repository.CommonCodeMasterMapper;
import com.fastretailing.dcp.sales.common.repository.optional.SalesTransactionErrorDetailOptionalMapper;
import com.fastretailing.dcp.sales.common.repository.optional.SalesTransactionHistoryOptionalMapper;
import com.fastretailing.dcp.sales.common.resttemplate.client.repository.sales.SalesRestTemplateRepository;
import com.fastretailing.dcp.sales.common.type.TransactionCheckResultType;
import com.fastretailing.dcp.sales.common.type.UpdateType;
import com.fastretailing.dcp.sales.importtransaction.component.CheckTransactionDataService;
import com.fastretailing.dcp.sales.importtransaction.dto.CreateTransactionImportData;
import com.fastretailing.dcp.sales.importtransaction.dto.TransactionImportData;
import com.fastretailing.dcp.sales.salestransactioncsv.dto.CsvFileList;
import com.fastretailing.dcp.sales.salestransactioncsv.dto.ItemDetailCsv;
import com.fastretailing.dcp.sales.salestransactioncsv.dto.ItemDetailDiscountCsv;
import com.fastretailing.dcp.sales.salestransactioncsv.dto.ItemDetailTaxCsv;
import com.fastretailing.dcp.sales.salestransactioncsv.dto.OrderInformationCsv;
import com.fastretailing.dcp.sales.salestransactioncsv.dto.SalesTransactionHeaderCsv;
import com.fastretailing.dcp.sales.salestransactioncsv.dto.SalesTransactionPaymentInformationCsv;
import com.fastretailing.dcp.sales.salestransactioncsv.dto.SalesTransactionTaxCsv;
import com.fastretailing.dcp.sales.salestransactioncsv.dto.SalesTransactionTotalCsv;
import com.fastretailing.dcp.sales.salestransactioncsv.dto.TableCommonItem;
import com.fastretailing.dcp.sales.salestransactionerrorlist.common.TransactionErrorDetailCheckFlag;
import com.fastretailing.dcp.sales.salestransactionerrorlist.component.SalesTransactionErrorListServiceHelper;
import com.fastretailing.dcp.sales.salestransactionerrorlist.form.SalesTransactionError;
import com.fastretailing.dcp.sales.salestransactionerrorlist.form.SalesTransactionErrorListForm;
import com.fastretailing.dcp.sales.salestransactionerrorlist.form.SelectItem;
import com.fastretailing.dcp.sales.salestransactionerrorlist.utils.CsvUtility;
import com.fastretailing.dcp.storecommon.ResultCode;
import com.fastretailing.dcp.storecommon.dto.CommonStatus;
import com.fastretailing.dcp.storecommon.screen.message.LocaleMessageSource;
import com.fastretailing.dcp.storecommon.screen.util.ScreenCommonUtility;
import com.fastretailing.dcp.storecommon.type.FunctionType;
import com.fastretailing.dcp.storecommon.type.MessageType;
import com.fastretailing.dcp.storecommon.type.PlatformShortName;
import com.fastretailing.dcp.storecommon.util.CommonUtility;
import lombok.extern.slf4j.Slf4j;

/**
 * Sales transaction error list service implements.
 */
@Slf4j
@Service
public class SalesTransactionErrorListServiceImpl implements SalesTransactionErrorListService {

    /** Date format(uuuuMMdd). */
    private static final DateTimeFormatter DATE_FORMAT_UUUUMMDD =
            DateTimeFormatter.ofPattern("uuuuMMdd");

    /** Date format(uuuu/MM/dd). */
    private static final DateTimeFormatter DATE_FORMAT_UUUUHMMHDD =
            DateTimeFormatter.ofPattern("uuuu/MM/dd").withResolverStyle(ResolverStyle.STRICT);

    /** Locale message source. */
    @Autowired
    private LocaleMessageSource localeMessageSource;

    /** Common code master mapper. */
    @Autowired
    private CommonCodeMasterMapper commonCodeMasterMapper;

    /** Sales transaction error detail mapper. */
    @Autowired
    private SalesTransactionErrorDetailOptionalMapper salesTransactionErrorDetailOptionalMapper;

    /** Ateration exclusion control mapper. */
    @Autowired
    private AlterationExclusionControlMapper alterationExclusionControlMapper;

    /** Sales transaction history mapper. */
    @Autowired
    private SalesTransactionHistoryOptionalMapper salesTransactionHistoryOptionalMapper;

    /** Object mapper. */
    @Autowired
    private ObjectMapper objectMapper;

    /** Sales transaction error list service helper. */
    @Autowired
    private SalesTransactionErrorListServiceHelper salesTransactionErrorListServiceHelper;

    /** Sales rest template repository. */
    @Autowired
    private SalesRestTemplateRepository salesRestTemplateRepository;

    /** Sales rest template repository. */
    @Autowired
    private ModelMapper modelMapper;

    /** UTC clock component. */
    @Autowired
    private SystemDateTime systemDateTime;

    /** Field id(system_country_code). */
    private static final String FIELD_ID_SYSTEM_COUNTRY_CODE = "system_country_code_screen";

    /** Field id(system_brand_code). */
    private static final String FIELD_ID_SYSTEM_BRAND_CODE = "system_brand_code_screen";

    /** Field id(transaction_type). */
    private static final String FIELD_ID_TRANSACTION_TYPE = "transaction_type";

    /** Field id(error_type). */
    private static final String FIELD_ID_ERROR_TYPE = "error_type";

    /** Order by clause(display_order asc). */
    private static final String ORDER_BY_CLAUSE_TYPE_ID = "display_order asc";

    /** Status type deleting code. */
    private static final String STATUS_TYPE_DELETING_CODE = "3";

    /** Status type deleting name. */
    private static final String STATUS_TYPE_DELETING_NAME = "Deleting";

    /** Status type uploading code. */
    private static final String STATUS_TYPE_UPLOADING_CODE = "4";

    /** Status type uploading name. */
    private static final String STATUS_TYPE_UPLOADING_NAME = "Uploading";

    /** Data alterations status type upload. */
    private static final String DATA_ALTERATIONS_STATUS_TYPE_UPLOAD = "3";

    /** Data alterations status type delete. */
    private static final String DATA_ALTERATIONS_STATUS_TYPE_DELETE = "2";

    /** Data alterations type. */
    private static final String DATA_ALTERATION_TYPE = "1";

    /** Exclusion valid time. */
    private static final String EXCLUSION_VALID_TIME = "60";

    /** Check transaction data service. */
    @Autowired
    private CheckTransactionDataService dataCheckerService;

    /** Message id of business error. */
    private static final String MESSAGE_ID_SLS_66000128 = CommonUtility.createMessageId(
            LogLevel.ERROR, PlatformShortName.SALES, MessageType.BUSINESS_ERROR,
            FunctionType.SCREEN, SalesFunctionId.SALES_COMMON_CODE_MASTER);

    /**
     * {@inheritDoc}
     */
    @Override
    public SalesTransactionErrorListForm getInitializeInformation(
            SalesTransactionErrorListForm salesTransactionErrorListForm) {

        CommonCodeMasterCondition commonCodeMasterCondition = new CommonCodeMasterCondition();
        // Get system country list.
        commonCodeMasterCondition.createCriteria().andTypeIdEqualTo(FIELD_ID_SYSTEM_COUNTRY_CODE);
        commonCodeMasterCondition.setOrderByClause(ORDER_BY_CLAUSE_TYPE_ID);
        List<CommonCodeMaster> systemCountryList =
                commonCodeMasterMapper.selectByCondition(commonCodeMasterCondition);

        if (CollectionUtils.isEmpty(systemCountryList)) {
            throwBusinessException(MESSAGE_ID_SLS_66000128);
        }
        salesTransactionErrorListForm
                .setCountryList(systemCountryList.stream().map(commonMaster -> {
                    SelectItem selectItem = new SelectItem();
                    selectItem.setValue(commonMaster.getTypeValue());
                    selectItem.setName(commonMaster.getName1());
                    return selectItem;
                }).collect(Collectors.toList()));
        commonCodeMasterCondition.clear();

        // Get system brand list.
        commonCodeMasterCondition.createCriteria().andTypeIdEqualTo(FIELD_ID_SYSTEM_BRAND_CODE);
        commonCodeMasterCondition.setOrderByClause(ORDER_BY_CLAUSE_TYPE_ID);
        List<CommonCodeMaster> systemBrandList =
                commonCodeMasterMapper.selectByCondition(commonCodeMasterCondition);
        if (CollectionUtils.isEmpty(systemBrandList)) {
            throwBusinessException(MESSAGE_ID_SLS_66000128);
        }
        salesTransactionErrorListForm.setBrandList(systemBrandList.stream().map(commonMaster -> {
            SelectItem selectItem = new SelectItem();
            selectItem.setValue(commonMaster.getTypeValue());
            selectItem.setName(commonMaster.getName1());
            return selectItem;
        }).collect(Collectors.toList()));
        commonCodeMasterCondition.clear();

        // Get transaction type list.
        commonCodeMasterCondition.createCriteria().andTypeIdEqualTo(FIELD_ID_TRANSACTION_TYPE);
        commonCodeMasterCondition.setOrderByClause(ORDER_BY_CLAUSE_TYPE_ID);
        List<CommonCodeMaster> transactionTypeList =
                commonCodeMasterMapper.selectByCondition(commonCodeMasterCondition);
        if (CollectionUtils.isEmpty(transactionTypeList)) {
            throwBusinessException(MESSAGE_ID_SLS_66000128);
        }
        salesTransactionErrorListForm
                .setSalesTransactionTypeMap(transactionTypeList.stream()
                        .collect(Collectors.toMap(CommonCodeMaster::getTypeValue,
                                CommonCodeMaster::getName1, (master1, master2) -> master1,
                                LinkedHashMap::new)));
        commonCodeMasterCondition.clear();

        // Get error contents list.
        commonCodeMasterCondition.createCriteria().andTypeIdEqualTo(FIELD_ID_ERROR_TYPE);
        commonCodeMasterCondition.setOrderByClause(ORDER_BY_CLAUSE_TYPE_ID);
        List<CommonCodeMaster> errorContentsList =
                commonCodeMasterMapper.selectByCondition(commonCodeMasterCondition);

        if (CollectionUtils.isEmpty(errorContentsList)) {
            throwBusinessException(MESSAGE_ID_SLS_66000128);
        }
        salesTransactionErrorListForm.setErrorContentsMap(errorContentsList.stream().collect(
                Collectors.toMap(CommonCodeMaster::getTypeValue, CommonCodeMaster::getName1,
                        (master1, master2) -> master1, LinkedHashMap::new)));
        // Return response Form.
        return salesTransactionErrorListForm;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SalesTransactionErrorListForm getSalesTransactionErrorList(
            SalesTransactionErrorListForm salesTransactionErrorListForm) {
        // Get sales transaction error detail list.
        List<SalesTransactionErrorDetail> salesTransactionErrorDetailList =
                salesTransactionErrorDetailOptionalMapper
                        .selectSalesTransactionErrorDetailByCondition(
                                createSalesTransactionErrorDetailCondition(
                                        salesTransactionErrorListForm));
        salesTransactionErrorListForm
                .setSalesTransactionErrorList(salesTransactionErrorDetailList.stream()
                        .map(salesTransactionErrorDetail -> createSalesTransactionErrorDetail(
                                salesTransactionErrorDetail))
                        .collect(Collectors.toList()));

        return salesTransactionErrorListForm;
    }

    /**
     * Create sales transaction error detail.
     *
     * @param salesTransactionErrorDetail Sales transaction error detail.
     * @return Object of sales transaction error.
     */
    private SalesTransactionError createSalesTransactionErrorDetail(
            SalesTransactionErrorDetail salesTransactionErrorDetail) {
        SalesTransactionError salesTransactionError = new SalesTransactionError();
        salesTransactionError.setSalesTransactionErrorId(
                salesTransactionErrorDetail.getSalesTransactionErrorId());
        salesTransactionError.setBrandCode(salesTransactionErrorDetail.getSystemBrandCode());
        salesTransactionError.setCountryCode(salesTransactionErrorDetail.getSystemCountryCode());
        salesTransactionError.setStoreCode(salesTransactionErrorDetail.getViewStoreCode());
        salesTransactionError.setRealStoreCode(salesTransactionErrorDetail.getStoreCode());
        salesTransactionError.setStoreName(salesTransactionErrorDetail.getStoreName());
        salesTransactionError.setCashRegisterNo(salesTransactionErrorDetail.getCashRegisterNo());
        salesTransactionError.setReceiptNo(salesTransactionErrorDetail.getReceiptNo());
        salesTransactionError.setSalesTransactionType(getCommonTypeNameByKey(
                FIELD_ID_TRANSACTION_TYPE, salesTransactionErrorDetail.getSalesTransactionType()));
        List<String> timeZoneId = salesTransactionErrorDetailOptionalMapper
                .selectTimeZoneByStoreCode(salesTransactionErrorDetail.getStoreCode());
        if (salesTransactionErrorDetail.getDataCreationDateTime() != null) {
            salesTransactionError.setDataCreationDate(salesTransactionErrorDetail
                    .getDataCreationDateTime()
                    .atZoneSameInstant(ZoneId
                            .of(CollectionUtils.isEmpty(timeZoneId) ? "UTC" : timeZoneId.get(0)))
                    .format(DATE_FORMAT_UUUUMMDD));
        }
        salesTransactionError
                .setBusinessDate(salesTransactionErrorDetail.getOrderStatusUpdateDate());

        salesTransactionError.setErrorContents(getCommonTypeNameByKey(FIELD_ID_ERROR_TYPE,
                salesTransactionErrorDetail.getErrorType()));
        if (STATUS_TYPE_DELETING_CODE.equals(salesTransactionErrorDetail.getStatus())) {
            salesTransactionError.setStatus(STATUS_TYPE_DELETING_NAME);
        } else if (STATUS_TYPE_UPLOADING_CODE.equals(salesTransactionErrorDetail.getStatus())) {
            salesTransactionError.setStatus(STATUS_TYPE_UPLOADING_NAME);
        } else {
            salesTransactionError.setStatus("");
        }
        return salesTransactionError;
    }

    /**
     * Get common type name by key.
     * 
     * @param typeId Type id.
     * @param typeValue Type value.
     * @return Type name.
     */
    public String getCommonTypeNameByKey(String typeId, String typeValue) {
        if (StringUtils.isEmpty(typeValue)) {
            return null;
        }
        String commonTypeName = null;
        CommonCodeMasterKey masterkey = new CommonCodeMasterKey();
        masterkey.setTypeId(typeId);
        masterkey.setTypeValue(typeValue);
        CommonCodeMaster commonMaster = commonCodeMasterMapper.selectByPrimaryKey(masterkey);
        if (commonMaster != null) {
            commonTypeName = commonMaster.getName1();
        }
        return commonTypeName;
    }

    /**
     * Create sales transaction error detail condition.
     *
     * @param salesTransactionErrorListForm Sales transaction error list form.
     * @return Object of sales transaction error detail condition.
     */
    private SalesTransactionErrorDetailCondition createSalesTransactionErrorDetailCondition(
            SalesTransactionErrorListForm salesTransactionErrorListForm) {

        SalesTransactionErrorDetailCondition salesTransactionErrorDetailCondition =
                new SalesTransactionErrorDetailCondition();
        salesTransactionErrorDetailCondition
                .setSystemBrandCode(salesTransactionErrorListForm.getSystemBrandCode());
        salesTransactionErrorDetailCondition
                .setSystemCountryCode(salesTransactionErrorListForm.getSystemCountryCode());
        salesTransactionErrorDetailCondition
                .setViewStoreCode(salesTransactionErrorListForm.getStoreCode());
        salesTransactionErrorDetailCondition
                .setReceiptNo(salesTransactionErrorListForm.getReceiptNo());
        if (StringUtils.isNotEmpty(salesTransactionErrorListForm.getCashRegisterNo())) {
            salesTransactionErrorDetailCondition.setCashRegisterNo(
                    Integer.valueOf(salesTransactionErrorListForm.getCashRegisterNo()));
        }
        salesTransactionErrorDetailCondition
                .setSalesTransactionType(salesTransactionErrorListForm.getSalesTransactionType());
        if (StringUtils.isNotEmpty(salesTransactionErrorListForm.getDataCreationDateFrom())) {
            salesTransactionErrorDetailCondition.setDataCreationDateTimeFrom(
                    parseDate(salesTransactionErrorListForm.getDataCreationDateFrom()));
        }
        if (StringUtils.isNotEmpty(salesTransactionErrorListForm.getDataCreationDateTo())) {
            salesTransactionErrorDetailCondition.setDataCreationDateTimeTo(
                    parseDate(salesTransactionErrorListForm.getDataCreationDateTo()));
        }
        salesTransactionErrorDetailCondition
                .setBusinessDateFrom(salesTransactionErrorListForm.getBusinessDateFrom());
        salesTransactionErrorDetailCondition
                .setBusinessDateTo(salesTransactionErrorListForm.getBusinessDateTo());
        salesTransactionErrorDetailCondition
                .setErrorType(salesTransactionErrorListForm.getErrorContents());
        return salesTransactionErrorDetailCondition;
    }

    /**
     * Convert the specified text to a LocalDateTime with the specified format pattern.
     * 
     * @param dateString specified date string.
     * @return {@link LocalDateTime} when success, null otherwise
     */
    private LocalDateTime parseDate(String dateString) {
        return LocalDate.parse(dateString, DATE_FORMAT_UUUUHMMHDD).atTime(0, 0, 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void numberLinkAccess(SalesTransactionErrorListForm salesTransactionErrorListForm) {
        List<SalesTransactionError> salesTransactionErrors = salesTransactionErrorListForm
                .getSalesTransactionErrorList()
                .stream()
                .filter(error -> TransactionErrorDetailCheckFlag.CHECKED.is(error.getCheckFlag()))
                .collect(Collectors.toList());
        SalesTransactionError salesTransactionError = salesTransactionErrors.get(0);
        AlterationExclusionControlKey contorlKey = new AlterationExclusionControlKey();
        contorlKey.setBusinessDate(salesTransactionError.getBusinessDate());
        contorlKey.setStoreCode(salesTransactionError.getStoreCode());
        contorlKey.setCashRegisterNo(Integer.valueOf(salesTransactionError.getCashRegisterNo()));
        contorlKey.setDataAlterationType(DATA_ALTERATION_TYPE);
        AlterationExclusionControl alterationExclusionControl =
                alterationExclusionControlMapper.selectByPrimaryKey(contorlKey);

        AlterationExclusionControl control = new AlterationExclusionControl();
        if (alterationExclusionControl == null) {
            // Insert alteration exclusion control.
            if (salesTransactionError.getBusinessDate() != null) {
                control.setBusinessDate(salesTransactionError.getBusinessDate());
            } else {
                control.setBusinessDate(
                        systemDateTime.now().toLocalDate().format(DATE_FORMAT_UUUUMMDD));
            }
            control.setStoreCode(salesTransactionError.getStoreCode());
            control.setCashRegisterNo(Integer.valueOf(salesTransactionError.getCashRegisterNo()));
            control.setDataAlterationType(DATA_ALTERATION_TYPE);
            control.setExclusionValidTime(EXCLUSION_VALID_TIME);
            TableCommonItem tableCommonItem = salesTransactionErrorListServiceHelper
                    .createTableCommonItemForInsert(salesTransactionErrorListForm.getLoginUserId());
            modelMapper.map(tableCommonItem, control);
            alterationExclusionControlMapper.insertSelective(control);
        } else {
            if (salesTransactionErrorListForm.getLoginUserId()
                    .equals(alterationExclusionControl.getCreateUserId())) {
                control.setUpdateDatetime(systemDateTime.now());
                alterationExclusionControlMapper.updateByPrimaryKey(control);
            } else {
                if (systemDateTime.now()
                        .minusMinutes((Integer
                                .parseInt(alterationExclusionControl.getExclusionValidTime())))
                        .compareTo(alterationExclusionControl.getUpdateDatetime()) < 0) {
                    salesTransactionErrorListServiceHelper
                            .throwBusinessException(MessagePrefix.E_SLS_66000101_UPDATEING);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(SalesTransactionErrorListForm salesTransactionErrorListForm) {
        List<SalesTransactionError> salesTransactionErrors = salesTransactionErrorListForm
                .getSalesTransactionErrorList()
                .stream()
                .filter(error -> TransactionErrorDetailCheckFlag.CHECKED.is(error.getCheckFlag()))
                .collect(Collectors.toList());
        salesTransactionErrors.forEach(salesTransactionError -> {
            SalesTransactionHistoryOptional salesTransactionHistory =
                    salesTransactionHistoryOptionalMapper
                            .selectByPrimaryKey(salesTransactionError.getSalesTransactionErrorId());
            if (salesTransactionHistory != null) {
                CreateTransactionImportData transactionImportData = null;
                try {
                    transactionImportData = objectMapper.readValue(
                            salesTransactionHistory.getSalesTransactionData(),
                            CreateTransactionImportData.class);
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
                transactionImportData.setUpdateType(UpdateType.DELETE.getUpdateType());
                transactionImportData.setErrorCheckType(1);
                transactionImportData.setDataAlterationSalesLinkageType(0);
                transactionImportData.setDataAlterationBackboneLinkageType(0);
                transactionImportData.setSalesTransactionErrorId(
                        salesTransactionError.getSalesTransactionErrorId());
                transactionImportData
                        .setDataCorrectionUserId(salesTransactionErrorListForm.getLoginUserId());
                CommonStatus commonstatus =
                        salesRestTemplateRepository.callTransactionImport(transactionImportData);
                if (commonstatus.getResultCode().equals(ResultCode.NORMAL)) {
                    salesTransactionErrorDetailOptionalMapper.updateDataAlterationStatusType(
                            DATA_ALTERATIONS_STATUS_TYPE_DELETE,
                            salesTransactionError.getSalesTransactionErrorId());
                } else {
                    salesTransactionErrorListServiceHelper.throwBusinessException(
                            MessagePrefix.E_SLS_66000105_DATA_DELETE_FAILED);
                }
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void upload(SalesTransactionErrorListForm salesTransactionErrorListForm) {
        List<SalesTransactionError> salesTransactionErrors = salesTransactionErrorListForm
                .getSalesTransactionErrorList()
                .stream()
                .filter(error -> TransactionErrorDetailCheckFlag.CHECKED.is(error.getCheckFlag()))
                .collect(Collectors.toList());
        salesTransactionErrors.forEach(salesTransactionError -> {
            SalesTransactionHistoryOptional salesTransactionHistory =
                    salesTransactionHistoryOptionalMapper
                            .selectByPrimaryKey(salesTransactionError.getSalesTransactionErrorId());
            if (salesTransactionHistory != null) {
                CreateTransactionImportData transactionImportData = null;
                TransactionImportData realransactionImportData = null;
                try {
                    transactionImportData = objectMapper.readValue(
                            salesTransactionHistory.getSalesTransactionData(),
                            CreateTransactionImportData.class);
                    realransactionImportData = objectMapper.readValue(
                            salesTransactionHistory.getSalesTransactionData(),
                            TransactionImportData.class);
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
                realransactionImportData.setUpdateType(UpdateType.INSERT.getUpdateType());
                realransactionImportData.setErrorCheckType(1);
                realransactionImportData.setDataAlterationSalesLinkageType(0);
                realransactionImportData.setDataAlterationBackboneLinkageType(0);
                realransactionImportData
                        .setDataCorrectionUserId(salesTransactionErrorListForm.getLoginUserId());
                // Error check.
                TransactionCheckResultType processFlag = dataCheckerService.checkTransactionData(
                        realransactionImportData, salesTransactionHistory.getCreateUserId(),
                        salesTransactionHistory.getTransactionId());
                if (!processFlag.equals(TransactionCheckResultType.NORMAL)) {
                    salesTransactionErrorListServiceHelper
                            .throwBusinessException(MessagePrefix.E_SLS_66000107_CHACK_ERROR);
                }
                transactionImportData.setUpdateType(UpdateType.INSERT.getUpdateType());
                transactionImportData.setErrorCheckType(1);
                transactionImportData.setDataAlterationSalesLinkageType(0);
                transactionImportData.setDataAlterationBackboneLinkageType(0);
                transactionImportData.setSalesTransactionErrorId(
                        salesTransactionError.getSalesTransactionErrorId());
                transactionImportData
                        .setDataCorrectionUserId(salesTransactionErrorListForm.getLoginUserId());
                CommonStatus commonstatus =
                        salesRestTemplateRepository.callTransactionImport(transactionImportData);
                if (commonstatus.getResultCode().equals(ResultCode.NORMAL)) {
                    salesTransactionErrorDetailOptionalMapper.updateDataAlterationStatusType(
                            DATA_ALTERATIONS_STATUS_TYPE_UPLOAD,
                            salesTransactionError.getSalesTransactionErrorId());
                } else {
                    salesTransactionErrorListServiceHelper.throwBusinessException(
                            MessagePrefix.E_SLS_66000112_DATA_UPLOAD_FAILED);
                }
            }
        });
    }

    /**
     * Get csv file list.
     * 
     * @param salesTransactionErrorListForm Sales transaction error list form.
     * @return Csv file list.
     */
    public CsvFileList getCsvFileList(SalesTransactionErrorListForm salesTransactionErrorListForm) {
        List<SalesTransactionError> salesTransactionErrors = salesTransactionErrorListForm
                .getSalesTransactionErrorList()
                .stream()
                .filter(error -> TransactionErrorDetailCheckFlag.CHECKED.is(error.getCheckFlag()))
                .collect(Collectors.toList());

        List<OrderInformationCsv> mainOrderInformationCsvList = new ArrayList<>();
        List<SalesTransactionHeaderCsv> mainSalesTransactionHeaderCsvList = new ArrayList<>();
        List<ItemDetailCsv> mainItemDetailCsvList = new ArrayList<>();
        List<ItemDetailDiscountCsv> mainItemDetailDiscountCsvList = new ArrayList<>();
        List<ItemDetailTaxCsv> mainItemDetailTaxCsvList = new ArrayList<>();
        List<SalesTransactionTaxCsv> mainSalesTransactionTaxCsvList = new ArrayList<>();
        List<SalesTransactionPaymentInformationCsv> mainSalesTransactionPaymentCsvList =
                new ArrayList<>();
        List<SalesTransactionTotalCsv> mainsalesTransactionTotalCsvList = new ArrayList<>();

        salesTransactionErrors.forEach(salesTransactionError -> {
            String salesTransactionErrorId = salesTransactionError.getSalesTransactionErrorId();
            mainOrderInformationCsvList.addAll(salesTransactionErrorListServiceHelper
                    .getErrorOrderInformationList(salesTransactionErrorId));
            mainSalesTransactionHeaderCsvList.addAll(salesTransactionErrorListServiceHelper
                    .getErrorSalesTransactionHeaderList(salesTransactionErrorId));
            mainItemDetailCsvList.addAll(salesTransactionErrorListServiceHelper
                    .getSalesErrorSalesTransactionDetailAndInformationList(
                            salesTransactionErrorId));
            mainItemDetailDiscountCsvList.addAll(salesTransactionErrorListServiceHelper
                    .getSalesErrorSalesTransactionDiscountList(salesTransactionErrorId));
            mainItemDetailTaxCsvList.addAll(salesTransactionErrorListServiceHelper
                    .getSalesErrorSalesTransactionTaxList(salesTransactionErrorId));
            mainSalesTransactionTaxCsvList.addAll(salesTransactionErrorListServiceHelper
                    .getTransactionTaxList(salesTransactionErrorId));
            mainSalesTransactionPaymentCsvList.addAll(salesTransactionErrorListServiceHelper
                    .getSalesErrorSalesTransactionTenderAndInformationList(
                            salesTransactionErrorId));
            mainsalesTransactionTotalCsvList.addAll(salesTransactionErrorListServiceHelper
                    .getTransactionTotalList(salesTransactionErrorId));

            List<ErrorEvacuationSalesOrderInformationOptional> errorEvacuationSalesOrderInformations =
                    salesTransactionErrorListServiceHelper
                            .getErrorEvacuationSalesOrderInformationCondition(
                                    salesTransactionErrorId);
            if (errorEvacuationSalesOrderInformations != null) {
                mainOrderInformationCsvList
                        .addAll(errorEvacuationSalesOrderInformations.stream().map(information -> {
                            OrderInformationCsv orderInformationCSV = new OrderInformationCsv();
                            modelMapper.map(information, orderInformationCSV);
                            return orderInformationCSV;
                        }).collect(Collectors.toList()));

                errorEvacuationSalesOrderInformations.forEach(information -> {
                    String evacuationSalesTransactionErrorId =
                            information.getSalesTransactionErrorId();
                    List<SalesTransactionHeaderCsv> evacuationSalesTransactionHeaderCsvList =
                            salesTransactionErrorListServiceHelper
                                    .getErrorEvacuationSalesTransactionHeaderList(
                                            evacuationSalesTransactionErrorId);
                    mainSalesTransactionHeaderCsvList
                            .addAll(evacuationSalesTransactionHeaderCsvList);
                    List<ItemDetailCsv> evacuationItemDetailCsvList =
                            salesTransactionErrorListServiceHelper
                                    .getErrorEvacuationSalesTransactionDetailAndInformationList(
                                            evacuationSalesTransactionErrorId);
                    mainItemDetailCsvList.addAll(evacuationItemDetailCsvList);
                    List<ItemDetailDiscountCsv> evacuationItemDetailDiscountCsvList =
                            salesTransactionErrorListServiceHelper
                                    .getErrorEvacuationSalesTransactionDiscountList(
                                            evacuationSalesTransactionErrorId);
                    mainItemDetailDiscountCsvList.addAll(evacuationItemDetailDiscountCsvList);
                    List<ItemDetailTaxCsv> evacuationItemDetailTaxCsvList =
                            salesTransactionErrorListServiceHelper
                                    .getErrorEvacuationSalesTransactionTaxList(
                                            evacuationSalesTransactionErrorId);
                    mainItemDetailTaxCsvList.addAll(evacuationItemDetailTaxCsvList);
                    List<SalesTransactionTaxCsv> evacuationSalesTransactionTaxCsvList =
                            salesTransactionErrorListServiceHelper
                                    .getErrorEvacuationTransactionTaxList(
                                            evacuationSalesTransactionErrorId);
                    mainSalesTransactionTaxCsvList.addAll(evacuationSalesTransactionTaxCsvList);
                    List<SalesTransactionPaymentInformationCsv> evacuationSalesTransactionPaymentCsvList =
                            salesTransactionErrorListServiceHelper
                                    .getErrorEvacuationSalesTransactionTenderAndInformationList(
                                            evacuationSalesTransactionErrorId);
                    mainSalesTransactionPaymentCsvList
                            .addAll(evacuationSalesTransactionPaymentCsvList);
                    List<SalesTransactionTotalCsv> evacuationSalesTransactionTotalCsvList =
                            salesTransactionErrorListServiceHelper
                                    .getErrorEvacuationTransactionTotalList(
                                            salesTransactionErrorId);
                    mainsalesTransactionTotalCsvList.addAll(evacuationSalesTransactionTotalCsvList);
                });
            }
        });

        CsvFileList csvFileList = new CsvFileList();
        csvFileList.setItemDetailCsvList(mainItemDetailCsvList);
        csvFileList.setItemDetailDiscountCsvList(mainItemDetailDiscountCsvList);
        csvFileList.setItemDetailTaxCsvList(mainItemDetailTaxCsvList);
        csvFileList.setOrderInformationCsvList(mainOrderInformationCsvList);
        csvFileList.setSalesTransactionHeaderCsvList(mainSalesTransactionHeaderCsvList);
        csvFileList.setSalesTransactionPaymentCsvList(mainSalesTransactionPaymentCsvList);
        csvFileList.setSalesTransactionTaxCsvList(mainSalesTransactionTaxCsvList);
        csvFileList.setSalesTransactionTotalCsvList(mainsalesTransactionTotalCsvList);
        return csvFileList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public File download(SalesTransactionErrorListForm salesTransactionErrorListForm) {
        CsvFileList csvFileList = getCsvFileList(salesTransactionErrorListForm);
        File zipFile = null;
        try {
            File itemDetail =
                    File.createTempFile(SalesTransactionErrorListServiceHelper.ITEM_DETAIL_CSV_NAME,
                            CodeConstants.FILE_EXTENSION_CSV);
            CsvUtility.writeAll(itemDetail, csvFileList.getItemDetailCsvList());

            File itemdetailDiscount = File.createTempFile(
                    SalesTransactionErrorListServiceHelper.ITEM_DETAIL_DISCOUNT_CSV_NAME,
                    CodeConstants.FILE_EXTENSION_CSV);
            CsvUtility.writeAll(itemdetailDiscount, csvFileList.getItemDetailDiscountCsvList());

            File itemDetailTax = File.createTempFile(
                    SalesTransactionErrorListServiceHelper.ITEM_DETAIL_TAX_CSV_NAME,
                    CodeConstants.FILE_EXTENSION_CSV);
            CsvUtility.writeAll(itemDetailTax, csvFileList.getItemDetailTaxCsvList());

            File orderInformation = File.createTempFile(
                    SalesTransactionErrorListServiceHelper.ORDER_INFORMATION_CSV_NAME,
                    CodeConstants.FILE_EXTENSION_CSV);
            CsvUtility.writeAll(orderInformation, csvFileList.getOrderInformationCsvList());

            File salesTransactionHeader = File.createTempFile(
                    SalesTransactionErrorListServiceHelper.SALES_TRANSACTION_HEADER_CSV_NAME,
                    CodeConstants.FILE_EXTENSION_CSV);
            CsvUtility.writeAll(salesTransactionHeader,
                    csvFileList.getSalesTransactionHeaderCsvList());

            File salesTransactionPayment = File.createTempFile(
                    SalesTransactionErrorListServiceHelper.SALES_TRANSACTION_PAYMENT_CSV_NAME,
                    CodeConstants.FILE_EXTENSION_CSV);
            CsvUtility.writeAll(salesTransactionPayment,
                    csvFileList.getSalesTransactionPaymentCsvList());

            File salesTransactionTax = File.createTempFile(
                    SalesTransactionErrorListServiceHelper.SALES_TRANSACTION_TAX_CSV_NAME,
                    CodeConstants.FILE_EXTENSION_CSV);
            CsvUtility.writeAll(salesTransactionTax, csvFileList.getSalesTransactionTaxCsvList());

            File salesTransactionTotal = File.createTempFile(
                    SalesTransactionErrorListServiceHelper.SALES_TRANSACTION_TOTAL_CSV_NAME,
                    CodeConstants.FILE_EXTENSION_CSV);
            CsvUtility.writeAll(salesTransactionTotal,
                    csvFileList.getSalesTransactionTotalCsvList());

            File[] files = new File[] {itemDetail, itemdetailDiscount, itemDetailTax,
                    orderInformation, salesTransactionHeader, salesTransactionPayment,
                    salesTransactionTax, salesTransactionTotal};

            zipFile = File.createTempFile(SalesTransactionErrorListServiceHelper.ZIP_FILE_NAME,
                    CodeConstants.FILE_EXTENSION_ZIP);
            salesTransactionErrorListServiceHelper.zipFiles(files, zipFile);
        } catch (IOException e) {
            log.error(e.getMessage());
            salesTransactionErrorListServiceHelper
                    .throwBusinessException(MessagePrefix.E_SLS_66000103_DOWN_FAILED);
        }
        return zipFile;
    }

    /**
     * Throw business exception.
     *
     * @param messageId Message id.
     */
    private void throwBusinessException(String messageId) {
        throw new BusinessException(ScreenCommonUtility.createResultObject(localeMessageSource,
                ErrorName.Business.BUSINESS_CHECK_ERROR, messageId, messageId));
    }
}
