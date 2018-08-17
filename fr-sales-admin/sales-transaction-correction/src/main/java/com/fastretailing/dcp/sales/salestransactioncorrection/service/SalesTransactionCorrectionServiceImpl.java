/**
 * @(#)SalesTransactionCorrectionServiceImpl.java
 *
 *                                                Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salestransactioncorrection.service;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fastretailing.dcp.common.constants.LogLevel;
import com.fastretailing.dcp.common.exception.BusinessException;
import com.fastretailing.dcp.common.exception.SystemException;
import com.fastretailing.dcp.common.model.ResultObject;
import com.fastretailing.dcp.sales.common.constants.MessagePrefix;
import com.fastretailing.dcp.sales.common.constants.SalesFunctionId;
import com.fastretailing.dcp.sales.common.entity.AlterationExclusionControlCondition;
import com.fastretailing.dcp.sales.common.entity.CommonCodeMaster;
import com.fastretailing.dcp.sales.common.entity.CommonCodeMasterCondition;
import com.fastretailing.dcp.sales.common.entity.optional.ErrorEvacuationSalesOrderInformationOptional;
import com.fastretailing.dcp.sales.common.entity.optional.ErrorEvacuationSalesOrderInformationOptionalCondition;
import com.fastretailing.dcp.sales.common.entity.optional.ErrorEvacuationSalesTransactionHeaderDetailOptional;
import com.fastretailing.dcp.sales.common.entity.optional.SalesTransactionHistoryOptional;
import com.fastretailing.dcp.sales.common.entity.optional.SalesTransactionHistoryOptionalCondition;
import com.fastretailing.dcp.sales.common.repository.AlterationExclusionControlMapper;
import com.fastretailing.dcp.sales.common.repository.CommonCodeMasterMapper;
import com.fastretailing.dcp.sales.common.repository.optional.ErrorEvacuationSalesOrderInformationOptionalMapper;
import com.fastretailing.dcp.sales.common.repository.optional.ErrorEvacuationSalesTransactionHeaderDetailOptionalMapper;
import com.fastretailing.dcp.sales.common.repository.optional.SalesErrorSalesTransactionOptionalMapper;
import com.fastretailing.dcp.sales.common.repository.optional.SalesTransactionErrorDetailOptionalMapper;
import com.fastretailing.dcp.sales.common.repository.optional.SalesTransactionHistoryOptionalMapper;
import com.fastretailing.dcp.sales.common.repository.optional.TenderAndTransformMasterOptionalMapper;
import com.fastretailing.dcp.sales.common.resttemplate.client.repository.sales.SalesRestTemplateRepository;
import com.fastretailing.dcp.sales.common.type.TransactionCheckResultType;
import com.fastretailing.dcp.sales.common.type.UpdateType;
import com.fastretailing.dcp.sales.importtransaction.component.CheckTransactionDataService;
import com.fastretailing.dcp.sales.importtransaction.dto.CreateTransactionImportData;
import com.fastretailing.dcp.sales.importtransaction.dto.TransactionImportData;
import com.fastretailing.dcp.sales.salestransactioncorrection.common.DeleteOptionType;
import com.fastretailing.dcp.sales.salestransactioncorrection.common.UploadOptionType;
import com.fastretailing.dcp.sales.salestransactioncorrection.convert.SalesTransactionCorrectionConvert;
import com.fastretailing.dcp.sales.salestransactioncorrection.form.EvacuatedSalesInformation;
import com.fastretailing.dcp.sales.salestransactioncorrection.form.SalesTransactionCorrectionForm;
import com.fastretailing.dcp.sales.salestransactioncorrection.form.transactionimport.ImportTransactionImportData;
import com.fastretailing.dcp.storecommon.ResultCode;
import com.fastretailing.dcp.storecommon.dto.CommonStatus;
import com.fastretailing.dcp.storecommon.i18n.InternationalizationComponent;
import com.fastretailing.dcp.storecommon.screen.message.LocaleMessageSource;
import com.fastretailing.dcp.storecommon.type.FunctionType;
import com.fastretailing.dcp.storecommon.type.MessageType;
import com.fastretailing.dcp.storecommon.type.PlatformShortName;
import com.fastretailing.dcp.storecommon.util.CommonUtility;
import lombok.extern.slf4j.Slf4j;

/**
 * Sales transaction correction service implements.
 */
@Slf4j
@Service
public class SalesTransactionCorrectionServiceImpl implements SalesTransactionCorrectionService {

    /** Model mapper. */
    @Autowired
    private ModelMapper modelMapper;

    /** Sales transaction history optional mapper. */
    @Autowired
    private SalesTransactionHistoryOptionalMapper salesTransactionHistoryOptionalMapper;

    /** Error evacuation sales transaction header detail mapper. */
    @Autowired
    private ErrorEvacuationSalesTransactionHeaderDetailOptionalMapper errorEvacuationHeaderDetailMapper;

    /** Check transaction data service. */
    @Autowired
    private CheckTransactionDataService dataCheckerService;

    /** Locale message source. */
    @Autowired
    private LocaleMessageSource localeMessageSource;

    /** Object mapper. */
    @Autowired
    private ObjectMapper objectMapper;

    /** Sales rest template repository. */
    @Autowired
    private SalesRestTemplateRepository salesRestTemplateRepository;

    /** Sales transaction error detail optional mapper. */
    @Autowired
    private SalesTransactionErrorDetailOptionalMapper salesTransactionErrorDetailOptionalMapper;

    /** Error evacuation sales order information mapper. */
    @Autowired
    private ErrorEvacuationSalesOrderInformationOptionalMapper errorEvacuationSalesOrderInformationOptionMapper;

    /** Tender and transform master option mapper. */
    @Autowired
    private TenderAndTransformMasterOptionalMapper tenderAndTransformMasterOptionalMapper;
    
    /** Sales error sales transaction mapper */
    @Autowired
    private SalesErrorSalesTransactionOptionalMapper salesErrorSalesTransactionOptionalMapper;

    /** Component for getting format by country code. */
    @Autowired
    private InternationalizationComponent internationalizationComponent;

    /** Common code master mapper. */
    @Autowired
    private CommonCodeMasterMapper commonCodeMasterMapper;

    /** Alteration exclusion control mapper. */
    @Autowired
    private AlterationExclusionControlMapper alterationExclusionControlMapper;

    /** Field id(employee_discount_flag). */
    private static final String FIELD_ID_EMPLOYEE_DISCOUNT_FLAG = "EMPLOYEE_DISCOUNT_FLAG";

    /** Field id(consistency_sales_flag). */
    private static final String FIELD_ID_CONSISTENCY_SALES_FLAG = "consistency_sales_flag";

    /** Field id(sales_transaction_discount_flag). */
    private static final String FIELD_ID_SALES_TRANSACTION_DISCOUNT_FLAG =
            "sales_transaction_discount_flag";

    /** Field id(product_classification). */
    private static final String FIELD_ID_PRODUCT_CLASSIFICATION = "PRODUCT_CLASSIFICATION";

    /** Field id(currency_code). */
    private static final String FIELD_ID_CURRENCY_CODE = "currency_code";

    private static final String PROMOTION_TYPE = "promotion_type";

    /** Field id(quantity_code). */
    private static final String FIELD_ID_QUANTITY_CODE = "quantity_code";

    /** Field id(taxation_type). */
    private static final String FIELD_ID_TAXATION_TYPE = "TAXATION_TYPE";

    /** Field id(tax_kind). */
    private static final String FIELD_ID_TAX_KIND = "TAX_KIND";

    /** Field id(store_discount_type). */
    private static final String FIELD_ID_STORE_DISCOUNT_TYPE = "STORE_DISCOUNT_TYPE";

    /** Field id(tax_group). */
    private static final String FIELD_ID_TAX_GROUP = "tax_group";

    /** Field id(amount_sign). */
    private static final String FIELD_ID_AMOUNT_SIGN = "amount_sign";

    /** Field id(payment_sign). */
    private static final String FIELD_ID_PAYMENT_SIGN = "payment_sign";

    /** Field id(total_type). */
    private static final String FIELD_ID_TOTAL_TYPE = "total_type";

    /** Order by clause(display_order asc). */
    private static final String ORDER_BY_CLAUSE_TYPE_ID = "display_order asc";

    /** Transaction id desc. */
    private static final String TRANSACTION_ID_DESC = "transaction_id desc";

    /** Error check type true. */
    private static final Integer ERROR_CHECK_TYPE_TRUE = 1;

    /** Data alteration sales linkage type false. */
    private static final Integer DATA_ALTERATION_SALES_LINKAGE_TYPE_FALSE = 0;

    /** Data alteration sales linkage type true. */
    private static final Integer DATA_ALTERATION_SALES_LINKAGE_TYPE_TRUE = 1;

    /** Data alteration backbone linkage type false. */
    private static final Integer DATA_ALTERATION_BACKBONE_LINKAGE_TYPE_FALSE = 0;

    /** Data alteration backbone linkage type true. */
    private static final Integer DATA_ALTERATION_BACKBONE_LINKAGE_TYPE_TRUE = 1;

    /** Message id of business error. */
    private static final String MESSAGE_ID_SLS_66000128 = CommonUtility.createMessageId(
            LogLevel.ERROR, PlatformShortName.SALES, MessageType.BUSINESS_ERROR,
            FunctionType.SCREEN, SalesFunctionId.SALES_COMMON_CODE_MASTER);

    /** Data alterations type. */
    private static final String DATA_ALTERATION_TYPE = "1";

    /** Data alterations status type delete. */
    private static final String DATA_ALTERATIONS_STATUS_TYPE_DELETE = "2";

    /** Data alterations status type upload. */
    private static final String DATA_ALTERATIONS_STATUS_TYPE_UPLOAD = "3";

    /** Json mapping error. */
    private static final String JSON_MAPPING_ERROR = "error in convert list to String";

    /** Sales transaction correction convert. */
    @Autowired
    private SalesTransactionCorrectionConvert salesTransactionCorrectionConvert;

    /**
     * {@inheritDoc}
     */
    @Override
    public SalesTransactionCorrectionForm getSalesTransactionHeaderInformation(
            SalesTransactionCorrectionForm salesTransactionCorrectionForm) {

        // Set common code information.
        salesTransactionCorrectionForm
                .setEmployeeSaleFlagMap(getSaleFlagMap(FIELD_ID_EMPLOYEE_DISCOUNT_FLAG));
        salesTransactionCorrectionForm
                .setCorporateSalesFlagMap(getSaleFlagMap(FIELD_ID_CONSISTENCY_SALES_FLAG));
        salesTransactionCorrectionForm.setSalesTransactionDiscountFlagMap(
                getSaleFlagMap(FIELD_ID_SALES_TRANSACTION_DISCOUNT_FLAG));

        salesTransactionCorrectionForm
                .setProductClassificationMap(getSaleFlagMap(FIELD_ID_PRODUCT_CLASSIFICATION));

        salesTransactionCorrectionForm.setCurrencyCodeMap(getSaleFlagMap(FIELD_ID_CURRENCY_CODE));

        salesTransactionCorrectionForm.setQuantityCodeMap(getSaleFlagMap(FIELD_ID_QUANTITY_CODE));

        salesTransactionCorrectionForm.setTaxationTypeMap(getSaleFlagMap(FIELD_ID_TAXATION_TYPE));

        salesTransactionCorrectionForm.setTaxKindMap(getSaleFlagMap(FIELD_ID_TAX_KIND));

        salesTransactionCorrectionForm.setPaymentSignMap(getSaleFlagMap(FIELD_ID_PAYMENT_SIGN));

        salesTransactionCorrectionForm
                .setStoreDiscountTypeMap(getSaleFlagMap(FIELD_ID_STORE_DISCOUNT_TYPE));

        salesTransactionCorrectionForm.setPromotionTypeMap(getSaleFlagMap(PROMOTION_TYPE));

        salesTransactionCorrectionForm.setTaxGroupMap(getSaleFlagMap(FIELD_ID_TAX_GROUP));

        salesTransactionCorrectionForm.setAmountSignMap(getSaleFlagMap(FIELD_ID_AMOUNT_SIGN));

        salesTransactionCorrectionForm.setTotalTypeMap(getSaleFlagMap(FIELD_ID_TOTAL_TYPE));

        String salesTransactionErrorId =
                salesTransactionCorrectionForm.getSalesTransactionErrorId();

        TransactionImportData transactionImportData = salesErrorSalesTransactionOptionalMapper
                .selectByErrorTransactionId(salesTransactionErrorId);

        if (transactionImportData == null) {
            return salesTransactionCorrectionForm;
        }
        
        ImportTransactionImportData importTransactionImportData = new ImportTransactionImportData();
        modelMapper.map(transactionImportData, importTransactionImportData);
        salesTransactionCorrectionForm.setTransactionImportData(salesTransactionCorrectionConvert
                .convertTransactionImportDataForm(importTransactionImportData, true));

        salesTransactionCorrectionForm
                .setTenderAndTransformInformationOptionalList(tenderAndTransformMasterOptionalMapper
                        .getTenderInfoByStoreCode(transactionImportData.getStoreCode()));

        // Get error evacuation sales transaction header detail list.
        List<ErrorEvacuationSalesTransactionHeaderDetailOptional> errorEvacuationHeaderList =
                errorEvacuationHeaderDetailMapper
                        .selectErrorEvacuationHeaderDetailBySalesTransactionId(
                                salesTransactionErrorId);
        if (CollectionUtils.isEmpty(errorEvacuationHeaderList)) {
            return salesTransactionCorrectionForm;
        }

        salesTransactionCorrectionForm.setEvacuatedSalesInformationList(
                errorEvacuationHeaderList.stream().map(errorEvacuationSalesTransactionHeader -> {
                    return createEvacuatedSalesInformation(errorEvacuationSalesTransactionHeader,
                            salesTransactionCorrectionForm);
                }).collect(Collectors.toList()));

        return salesTransactionCorrectionForm;
    }

    /**
     * Get sales flag map.
     *
     * @param fieldId Field id.
     * @return Result map.
     */
    private Map<String, String> getSaleFlagMap(String fieldId) {

        CommonCodeMasterCondition commonCodeMasterCondition = new CommonCodeMasterCondition();
        commonCodeMasterCondition.createCriteria().andTypeIdEqualTo(fieldId);
        commonCodeMasterCondition.setOrderByClause(ORDER_BY_CLAUSE_TYPE_ID);

        // Get consistency sales flag list.
        List<CommonCodeMaster> salesFlaglist =
                commonCodeMasterMapper.selectByCondition(commonCodeMasterCondition);

        // Create result object.
        ResultObject resultObject = new ResultObject();
        resultObject.setDebugId(MESSAGE_ID_SLS_66000128);
        resultObject.setMessage(localeMessageSource.getMessage(MESSAGE_ID_SLS_66000128));

        if (CollectionUtils.isEmpty(salesFlaglist)) {
            throw new BusinessException(resultObject);
        }

        return salesFlaglist.stream().collect(Collectors.toMap(CommonCodeMaster::getTypeValue,
                CommonCodeMaster::getName1, (master1, master2) -> master1, LinkedHashMap::new));
    }

    /**
     * Create evacuated sales information.
     *
     * @param errorEvacuationTransactionHeader Error evacuation sales transaction header.
     * @param salesTransactionCorrectionForm Sales transaction correction form.
     * @return Evacuation sales information.
     */
    private EvacuatedSalesInformation createEvacuatedSalesInformation(
            ErrorEvacuationSalesTransactionHeaderDetailOptional errorEvacuationTransactionHeader,
            SalesTransactionCorrectionForm salesTransactionCorrectionForm) {

        EvacuatedSalesInformation evacuatedSalesInformation = new EvacuatedSalesInformation();
        evacuatedSalesInformation.setStore(errorEvacuationTransactionHeader.getStoreCode());
        evacuatedSalesInformation.setRegisterNo(
                String.valueOf(errorEvacuationTransactionHeader.getCashRegisterNo()));
        evacuatedSalesInformation.setReceiptNo(errorEvacuationTransactionHeader.getReceiptNo());
        evacuatedSalesInformation.setUpdateType(errorEvacuationTransactionHeader.getUpdateType());
        evacuatedSalesInformation
                .setSalesLinkageType(errorEvacuationTransactionHeader.getSalesLinkageType());
        evacuatedSalesInformation.setDataCreationBusinessDate(
                errorEvacuationTransactionHeader.getDataCreationBusinessDate());
        evacuatedSalesInformation.setDataCreationDateTime(
                salesTransactionCorrectionConvert.formatOffsetDateTimeToString(
                        errorEvacuationTransactionHeader.getDataCreationDateTime()));
        evacuatedSalesInformation.setOrderStatus(errorEvacuationTransactionHeader.getOrderStatus());
        evacuatedSalesInformation
                .setOrderSubStatus(errorEvacuationTransactionHeader.getOrderSubstatus());
        evacuatedSalesInformation.setOrderStatusUpdateDate(
                errorEvacuationTransactionHeader.getOrderStatusUpdateDate());
        evacuatedSalesInformation.setOrderStatusLastUpdateDateTime(
                salesTransactionCorrectionConvert.formatOffsetDateTimeToString(
                        errorEvacuationTransactionHeader.getOrderStatusLastUpdateDateTime()));
        evacuatedSalesInformation
                .setEmployeeSaleFlag(errorEvacuationTransactionHeader.getEmployeeSaleFlag());
        evacuatedSalesInformation
                .setCorporateSaleFlag(errorEvacuationTransactionHeader.getConsistencySalesFlag());
        evacuatedSalesInformation.setCorporateId(errorEvacuationTransactionHeader.getCorporateId());
        evacuatedSalesInformation.setSalesTransactionDiscountFlag(
                errorEvacuationTransactionHeader.getSalesTransactionDiscountFlag());
        if (errorEvacuationTransactionHeader.getSalesTransactionDiscountAmountRate() != null) {
            evacuatedSalesInformation
                    .setSalesTransactionDiscountAmountRate(internationalizationComponent
                            .formatNumber(salesTransactionCorrectionForm.getCountryCode(),
                                    errorEvacuationTransactionHeader
                                            .getSalesTransactionDiscountAmountRate()));
        }
        return evacuatedSalesInformation;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void upload(SalesTransactionCorrectionForm salesTransactionCorrectionForm) {

        ImportTransactionImportData transactionImportData =
                salesTransactionCorrectionConvert.convertTransactionImportDataForm(
                        salesTransactionCorrectionForm.getTransactionImportData(), false);

        if (UploadOptionType.REFLECT_ON_ALL.is(salesTransactionCorrectionForm.getUploadOption())) {
            // Reflect on all.
            reportTransactionByReflectOnAll(transactionImportData,
                    salesTransactionCorrectionForm.getSalesTransactionErrorId(),
                    salesTransactionCorrectionForm.getLoginUserId());

        } else if (UploadOptionType.REFLECT_ONLY_ON_IMS_LINKAGE
                .is(salesTransactionCorrectionForm.getUploadOption())) {
            // Reflect only on ims linkage.
            reportTransactionByReflectOnlyOnIMSLinkage(transactionImportData,
                    salesTransactionCorrectionForm.getSalesTransactionErrorId(),
                    salesTransactionCorrectionForm.getLoginUserId());
        } else if (UploadOptionType.UPLOAD_WITHOUT_CORRECTION
                .is(salesTransactionCorrectionForm.getUploadOption())) {
            // Upload without correction.
            reportTransactionByUploadWithoutCorrection(transactionImportData,
                    salesTransactionCorrectionForm.getSalesTransactionErrorId(),
                    salesTransactionCorrectionForm.getLoginUserId());
        } else {
            // Reflect only on receipt.
            reportTransactionByReflectOnlyOnReceipt(transactionImportData,
                    salesTransactionCorrectionForm.getSalesTransactionErrorId(),
                    salesTransactionCorrectionForm.getLoginUserId());
        }
    }

    /**
     * Report transaction by reflect on all.
     * 
     * @param transactionImportData Transaction import data.
     * @param transactionId Transaction id.
     * @param loginUserId Login user id.
     */
    public void reportTransactionByReflectOnAll(ImportTransactionImportData transactionImportData,
            String transactionId, String loginUserId) {
        TransactionImportData outTransactionImportData = new TransactionImportData();
        modelMapper.map(transactionImportData, outTransactionImportData);
        outTransactionImportData.setUpdateType(UpdateType.CORRECTION.getUpdateType());
        outTransactionImportData.setErrorCheckType(ERROR_CHECK_TYPE_TRUE);
        outTransactionImportData
                .setDataAlterationSalesLinkageType(DATA_ALTERATION_SALES_LINKAGE_TYPE_FALSE);
        outTransactionImportData
                .setDataAlterationBackboneLinkageType(DATA_ALTERATION_BACKBONE_LINKAGE_TYPE_FALSE);
        outTransactionImportData.setDataCorrectionUserId(loginUserId);
        // Error check.
        callTransactionErrorCheck(outTransactionImportData, loginUserId, transactionId);
        CreateTransactionImportData createTransactionImportData = new CreateTransactionImportData();
        modelMapper.map(outTransactionImportData, createTransactionImportData);
        callTransactionApi(createTransactionImportData, transactionId, true);
        List<String> transactionIdList = getTransactionIdList(transactionId);
        transactionIdList.forEach(transaction -> {
            CreateTransactionImportData inCreateTransactionImportData =
                    new CreateTransactionImportData();
            modelMapper.map(transactionImportData, inCreateTransactionImportData);
            inCreateTransactionImportData.setErrorCheckType(ERROR_CHECK_TYPE_TRUE);
            inCreateTransactionImportData
                    .setDataAlterationSalesLinkageType(DATA_ALTERATION_SALES_LINKAGE_TYPE_FALSE);
            inCreateTransactionImportData.setDataAlterationBackboneLinkageType(
                    DATA_ALTERATION_BACKBONE_LINKAGE_TYPE_FALSE);
            callTransactionApi(inCreateTransactionImportData, transactionId, true);
        });
    }

    /**
     * Report transaction by reflect only on receipt.
     * 
     * @param transactionImportData Transaction import data.
     * @param transactionId Transaction id.
     * @param loginUserId Login user id.
     */
    public void reportTransactionByReflectOnlyOnReceipt(
            ImportTransactionImportData transactionImportData, String transactionId,
            String loginUserId) {

        TransactionImportData insertTransactionImportData = new TransactionImportData();
        modelMapper.map(transactionImportData, insertTransactionImportData);
        insertTransactionImportData.setUpdateType(UpdateType.INSERT.getUpdateType());
        insertTransactionImportData.setErrorCheckType(ERROR_CHECK_TYPE_TRUE);
        insertTransactionImportData
                .setDataAlterationSalesLinkageType(DATA_ALTERATION_SALES_LINKAGE_TYPE_FALSE);
        insertTransactionImportData
                .setDataAlterationBackboneLinkageType(DATA_ALTERATION_BACKBONE_LINKAGE_TYPE_TRUE);
        insertTransactionImportData.setDataCorrectionUserId(loginUserId);
        callTransactionErrorCheck(insertTransactionImportData, loginUserId, transactionId);

        CreateTransactionImportData createTransactionImportData = new CreateTransactionImportData();
        modelMapper.map(insertTransactionImportData, createTransactionImportData);
        callTransactionApi(createTransactionImportData, transactionId, true);

        TransactionImportData correctionTransactionImportData = new TransactionImportData();
        modelMapper.map(transactionImportData, correctionTransactionImportData);
        correctionTransactionImportData.setUpdateType(UpdateType.CORRECTION.getUpdateType());
        correctionTransactionImportData.setErrorCheckType(ERROR_CHECK_TYPE_TRUE);
        correctionTransactionImportData
                .setDataAlterationSalesLinkageType(DATA_ALTERATION_SALES_LINKAGE_TYPE_TRUE);
        correctionTransactionImportData
                .setDataAlterationBackboneLinkageType(DATA_ALTERATION_BACKBONE_LINKAGE_TYPE_FALSE);
        correctionTransactionImportData.setDataCorrectionUserId(loginUserId);
        callTransactionErrorCheck(correctionTransactionImportData, loginUserId, transactionId);

        CreateTransactionImportData correctionCreateTransactionImportData =
                new CreateTransactionImportData();
        modelMapper.map(correctionTransactionImportData, correctionCreateTransactionImportData);
        callTransactionApi(correctionCreateTransactionImportData, transactionId, true);

        List<String> transactionIdList = getTransactionIdList(transactionId);

        transactionIdList.forEach(transaction -> {
            CreateTransactionImportData inCreateTransactionImportData =
                    new CreateTransactionImportData();
            modelMapper.map(transactionImportData, inCreateTransactionImportData);

            inCreateTransactionImportData.setErrorCheckType(ERROR_CHECK_TYPE_TRUE);
            inCreateTransactionImportData
                    .setDataAlterationSalesLinkageType(DATA_ALTERATION_SALES_LINKAGE_TYPE_FALSE);
            inCreateTransactionImportData.setDataAlterationBackboneLinkageType(
                    DATA_ALTERATION_BACKBONE_LINKAGE_TYPE_FALSE);
            callTransactionApi(inCreateTransactionImportData, transactionId, true);
        });
    }

    /**
     * Report transaction by upload without correction.
     * 
     * @param transactionImportData Transaction import data.
     * @param transactionId Transaction id.
     * @param loginUserId Login user id.
     */
    public void reportTransactionByUploadWithoutCorrection(
            ImportTransactionImportData transactionImportData, String transactionId,
            String loginUserId) {
        TransactionImportData outTransactionImportData = new TransactionImportData();
        modelMapper.map(transactionImportData, outTransactionImportData);
        outTransactionImportData.setUpdateType(UpdateType.INSERT.getUpdateType());
        outTransactionImportData.setErrorCheckType(ERROR_CHECK_TYPE_TRUE);
        outTransactionImportData
                .setDataAlterationSalesLinkageType(DATA_ALTERATION_SALES_LINKAGE_TYPE_FALSE);
        outTransactionImportData
                .setDataAlterationBackboneLinkageType(DATA_ALTERATION_BACKBONE_LINKAGE_TYPE_FALSE);
        outTransactionImportData.setDataCorrectionUserId(loginUserId);
        // Error check.
        callTransactionErrorCheck(outTransactionImportData, loginUserId, transactionId);
        CreateTransactionImportData createTransactionImportData = new CreateTransactionImportData();
        modelMapper.map(outTransactionImportData, createTransactionImportData);
        callTransactionApi(createTransactionImportData, transactionId, true);
        List<String> transactionIdList = getTransactionIdList(transactionId);
        transactionIdList.forEach(transaction -> {
            CreateTransactionImportData inCreateTransactionImportData =
                    new CreateTransactionImportData();
            modelMapper.map(transactionImportData, inCreateTransactionImportData);
            inCreateTransactionImportData.setErrorCheckType(ERROR_CHECK_TYPE_TRUE);
            inCreateTransactionImportData
                    .setDataAlterationSalesLinkageType(DATA_ALTERATION_SALES_LINKAGE_TYPE_FALSE);
            inCreateTransactionImportData.setDataAlterationBackboneLinkageType(
                    DATA_ALTERATION_BACKBONE_LINKAGE_TYPE_FALSE);
            callTransactionApi(inCreateTransactionImportData, transactionId, true);
        });
    }

    /**
     * Report transaction by reflect only on ims linkage.
     * 
     * @param transactionImportData Transaction import data.
     * @param transactionId Transaction id.
     * @param loginUserId Login user id.
     */
    public void reportTransactionByReflectOnlyOnIMSLinkage(
            ImportTransactionImportData transactionImportData, String transactionId,
            String loginUserId) {

        TransactionImportData insertTransactionImportData = new TransactionImportData();
        modelMapper.map(transactionImportData, insertTransactionImportData);
        insertTransactionImportData.setUpdateType(UpdateType.INSERT.getUpdateType());
        insertTransactionImportData.setErrorCheckType(ERROR_CHECK_TYPE_TRUE);
        insertTransactionImportData
                .setDataAlterationSalesLinkageType(DATA_ALTERATION_SALES_LINKAGE_TYPE_TRUE);
        insertTransactionImportData
                .setDataAlterationBackboneLinkageType(DATA_ALTERATION_BACKBONE_LINKAGE_TYPE_FALSE);
        insertTransactionImportData.setDataCorrectionUserId(loginUserId);
        callTransactionErrorCheck(insertTransactionImportData, loginUserId, transactionId);

        CreateTransactionImportData createTransactionImportData = new CreateTransactionImportData();
        modelMapper.map(insertTransactionImportData, createTransactionImportData);
        callTransactionApi(createTransactionImportData, transactionId, true);

        TransactionImportData correctionTransactionImportData = new TransactionImportData();
        modelMapper.map(transactionImportData, correctionTransactionImportData);
        correctionTransactionImportData.setUpdateType(UpdateType.CORRECTION.getUpdateType());

        correctionTransactionImportData.setErrorCheckType(ERROR_CHECK_TYPE_TRUE);
        correctionTransactionImportData
                .setDataAlterationSalesLinkageType(DATA_ALTERATION_SALES_LINKAGE_TYPE_FALSE);
        correctionTransactionImportData
                .setDataAlterationBackboneLinkageType(DATA_ALTERATION_BACKBONE_LINKAGE_TYPE_TRUE);
        correctionTransactionImportData.setDataCorrectionUserId(loginUserId);
        callTransactionErrorCheck(correctionTransactionImportData, loginUserId, transactionId);

        CreateTransactionImportData correctionCreateTransactionImportData =
                new CreateTransactionImportData();
        modelMapper.map(correctionTransactionImportData, correctionCreateTransactionImportData);
        callTransactionApi(correctionCreateTransactionImportData, transactionId, true);
        List<String> transactionIdList = getTransactionIdList(transactionId);

        transactionIdList.forEach(transaction -> {
            CreateTransactionImportData inCreateTransactionImportData =
                    new CreateTransactionImportData();
            modelMapper.map(transactionImportData, inCreateTransactionImportData);
            inCreateTransactionImportData.setErrorCheckType(ERROR_CHECK_TYPE_TRUE);
            inCreateTransactionImportData
                    .setDataAlterationSalesLinkageType(DATA_ALTERATION_SALES_LINKAGE_TYPE_FALSE);
            inCreateTransactionImportData.setDataAlterationBackboneLinkageType(
                    DATA_ALTERATION_BACKBONE_LINKAGE_TYPE_FALSE);
            callTransactionApi(inCreateTransactionImportData, transactionId, true);
        });
    }

    /**
     * Call transaction error check.
     * 
     * @param transactionImportData Transaction import data.
     * @param loginUserId Login user id.
     * @param transactionId Transaction id.
     */
    public void callTransactionErrorCheck(TransactionImportData transactionImportData,
            String loginUserId, String transactionId) {
        // Error check.
        TransactionCheckResultType processFlag = dataCheckerService
                .checkTransactionData(transactionImportData, loginUserId, transactionId);
        if (!processFlag.equals(TransactionCheckResultType.NORMAL)) {
            ResultObject resultObject = new ResultObject();
            resultObject.setDebugId(MessagePrefix.E_SLS_66000107_CHACK_ERROR);
            resultObject.setMessage(
                    localeMessageSource.getMessage(MessagePrefix.E_SLS_66000107_CHACK_ERROR));
            throw new BusinessException(resultObject);
        }
    }

    /**
     * Call transaction import data.
     * 
     * @param createTransactionImportData Create transaction import data.
     * @param transactionId Transaction id.
     * @param isUpload Is upload.
     */
    public void callTransactionApi(CreateTransactionImportData createTransactionImportData,
            String transactionId, boolean isUpload) {
        CommonStatus commonstatus =
                salesRestTemplateRepository.callTransactionImport(createTransactionImportData);
        if (commonstatus.getResultCode().equals(ResultCode.NORMAL)) {
            if (isUpload) {
                salesTransactionErrorDetailOptionalMapper.updateDataAlterationStatusType(
                        DATA_ALTERATIONS_STATUS_TYPE_UPLOAD, transactionId);
            } else {
                salesTransactionErrorDetailOptionalMapper.updateDataAlterationStatusType(
                        DATA_ALTERATIONS_STATUS_TYPE_DELETE, transactionId);
            }
        } else {
            ResultObject resultObject = new ResultObject();
            if (isUpload) {
                resultObject.setDebugId(MessagePrefix.E_SLS_66000112_DATA_UPLOAD_FAILED);
                resultObject.setMessage(localeMessageSource
                        .getMessage(MessagePrefix.E_SLS_66000112_DATA_UPLOAD_FAILED));
            } else {
                resultObject.setDebugId(MessagePrefix.E_SLS_66000105_DATA_DELETE_FAILED);
                resultObject.setMessage(localeMessageSource
                        .getMessage(MessagePrefix.E_SLS_66000105_DATA_DELETE_FAILED));
            }
            throw new BusinessException(resultObject);
        }
    }

    /**
     * Get transaction id list.
     * 
     * @param transactionErrorId Transaction error id.
     * @return List transaction id.
     */
    public List<String> getTransactionIdList(String transactionErrorId) {
        ErrorEvacuationSalesOrderInformationOptionalCondition condition =
                new ErrorEvacuationSalesOrderInformationOptionalCondition();
        condition.setOrderByClause(TRANSACTION_ID_DESC);
        condition.createCriteria().andSalesTransactionErrorIdEqualTo(transactionErrorId);
        List<ErrorEvacuationSalesOrderInformationOptional> errorEvacuationSalesOrderInformationOptional =
                errorEvacuationSalesOrderInformationOptionMapper.selectByCondition(condition);
        List<String> transactionIdList = errorEvacuationSalesOrderInformationOptional.stream()
                .map(entity -> entity.getSalesTransactionErrorId())
                .collect(Collectors.toList());
        return transactionIdList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void audit(SalesTransactionCorrectionForm salesTransactionCorrectionForm) {
        ImportTransactionImportData transactionImportData =
                salesTransactionCorrectionConvert.convertTransactionImportDataForm(
                        salesTransactionCorrectionForm.getTransactionImportData(), false);
        reportTransactionByReflectOnAll(transactionImportData,
                salesTransactionCorrectionForm.getSalesTransactionErrorId(),
                salesTransactionCorrectionForm.getLoginUserId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(SalesTransactionCorrectionForm salesTransactionCorrectionForm) {
        ImportTransactionImportData transactionImportData =
                salesTransactionCorrectionConvert.convertTransactionImportDataForm(
                        salesTransactionCorrectionForm.getTransactionImportData(), false);
        TransactionImportData insertTransactionImportData = new TransactionImportData();
        modelMapper.map(transactionImportData, insertTransactionImportData);
        insertTransactionImportData.setUpdateType(UpdateType.DELETE.getUpdateType());
        insertTransactionImportData.setErrorCheckType(ERROR_CHECK_TYPE_TRUE);
        insertTransactionImportData
                .setDataAlterationSalesLinkageType(DATA_ALTERATION_SALES_LINKAGE_TYPE_FALSE);
        insertTransactionImportData
                .setDataAlterationBackboneLinkageType(DATA_ALTERATION_BACKBONE_LINKAGE_TYPE_FALSE);
        insertTransactionImportData
                .setDataCorrectionUserId(salesTransactionCorrectionForm.getLoginUserId());
        callTransactionErrorCheck(insertTransactionImportData,
                salesTransactionCorrectionForm.getLoginUserId(),
                salesTransactionCorrectionForm.getSalesTransactionErrorId());

        CreateTransactionImportData createTransactionImportData = new CreateTransactionImportData();
        modelMapper.map(insertTransactionImportData, createTransactionImportData);
        callTransactionApi(createTransactionImportData,
                salesTransactionCorrectionForm.getSalesTransactionErrorId(), false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteByOption(SalesTransactionCorrectionForm salesTransactionCorrectionForm) {
        ImportTransactionImportData transactionImportData =
                salesTransactionCorrectionConvert.convertTransactionImportDataForm(
                        salesTransactionCorrectionForm.getTransactionImportData(), false);

        if (DeleteOptionType.REFLECT_ON_ALL.is(salesTransactionCorrectionForm.getDeleteOption())) {
            // Reflect on all.
            reportTransactionByReflectOnAllByDelete(transactionImportData,
                    salesTransactionCorrectionForm.getSalesTransactionErrorId(),
                    salesTransactionCorrectionForm.getLoginUserId());
        } else if (DeleteOptionType.REFLECT_ONLY_ON_IMS_LINKAGE
                .is(salesTransactionCorrectionForm.getDeleteOption())) {
            // Reflect only on ims linkage.
            reportTransactionByReflectOnlyOnIMSLinkageByDelete(transactionImportData,
                    salesTransactionCorrectionForm.getSalesTransactionErrorId(),
                    salesTransactionCorrectionForm.getLoginUserId());
        } else {
            // Reflect only on receipt.
            reportTransactionByReflectOnlyOnReceiptByDelete(transactionImportData,
                    salesTransactionCorrectionForm.getSalesTransactionErrorId(),
                    salesTransactionCorrectionForm.getLoginUserId());
        }
    }

    /**
     * Report transaction by reflect on all.
     * 
     * @param transactionImportData Transaction import data.
     * @param transactionId Transaction id.
     * @param loginUserId Login user id.
     */
    public void reportTransactionByReflectOnAllByDelete(
            ImportTransactionImportData transactionImportData, String transactionId,
            String loginUserId) {
        TransactionImportData insertTransactionImportData = new TransactionImportData();
        modelMapper.map(transactionImportData, insertTransactionImportData);
        insertTransactionImportData.setUpdateType(UpdateType.DELETE.getUpdateType());
        insertTransactionImportData.setErrorCheckType(ERROR_CHECK_TYPE_TRUE);
        insertTransactionImportData
                .setDataAlterationSalesLinkageType(DATA_ALTERATION_SALES_LINKAGE_TYPE_FALSE);
        insertTransactionImportData
                .setDataAlterationBackboneLinkageType(DATA_ALTERATION_BACKBONE_LINKAGE_TYPE_FALSE);
        insertTransactionImportData.setDataCorrectionUserId(loginUserId);
        callTransactionErrorCheck(insertTransactionImportData, loginUserId, transactionId);

        CreateTransactionImportData createTransactionImportData = new CreateTransactionImportData();
        modelMapper.map(insertTransactionImportData, createTransactionImportData);
        callTransactionApi(createTransactionImportData, transactionId, false);
    }

    /**
     * Report transaction by reflect only on ims linkage by delete.
     * 
     * @param transactionImportData Transaction import data.
     * @param transactionId Transaction id.
     * @param loginUserId Login user id.
     */
    public void reportTransactionByReflectOnlyOnIMSLinkageByDelete(
            ImportTransactionImportData transactionImportData, String transactionId,
            String loginUserId) {

        TransactionImportData insertTransactionImportData = new TransactionImportData();
        modelMapper.map(transactionImportData, insertTransactionImportData);
        insertTransactionImportData.setUpdateType(UpdateType.INSERT.getUpdateType());
        insertTransactionImportData.setErrorCheckType(ERROR_CHECK_TYPE_TRUE);
        insertTransactionImportData
                .setDataAlterationSalesLinkageType(DATA_ALTERATION_SALES_LINKAGE_TYPE_TRUE);
        insertTransactionImportData
                .setDataAlterationBackboneLinkageType(DATA_ALTERATION_BACKBONE_LINKAGE_TYPE_FALSE);
        insertTransactionImportData.setDataCorrectionUserId(loginUserId);
        callTransactionErrorCheck(insertTransactionImportData, loginUserId, transactionId);

        CreateTransactionImportData createTransactionImportData = new CreateTransactionImportData();
        modelMapper.map(insertTransactionImportData, createTransactionImportData);
        callTransactionApi(createTransactionImportData, transactionId, false);

        List<String> transactionIdList = getTransactionIdList(transactionId);

        transactionIdList.forEach(transaction -> {
            CreateTransactionImportData inCreateTransactionImportData =
                    new CreateTransactionImportData();
            modelMapper.map(transactionImportData, inCreateTransactionImportData);
            inCreateTransactionImportData.setErrorCheckType(ERROR_CHECK_TYPE_TRUE);
            inCreateTransactionImportData
                    .setDataAlterationSalesLinkageType(DATA_ALTERATION_SALES_LINKAGE_TYPE_TRUE);
            inCreateTransactionImportData.setDataAlterationBackboneLinkageType(
                    DATA_ALTERATION_BACKBONE_LINKAGE_TYPE_FALSE);
            callTransactionApi(inCreateTransactionImportData, transactionId, false);
        });
    }

    /**
     * Report transaction by reflect only on receipt by delete.
     * 
     * @param transactionImportData Transaction import data.
     * @param transactionId Transaction id.
     * @param loginUserId Login user id.
     */
    public void reportTransactionByReflectOnlyOnReceiptByDelete(
            ImportTransactionImportData transactionImportData, String transactionId,
            String loginUserId) {

        TransactionImportData insertTransactionImportData = new TransactionImportData();
        modelMapper.map(transactionImportData, insertTransactionImportData);
        insertTransactionImportData.setUpdateType(UpdateType.INSERT.getUpdateType());
        insertTransactionImportData.setErrorCheckType(ERROR_CHECK_TYPE_TRUE);
        insertTransactionImportData
                .setDataAlterationSalesLinkageType(DATA_ALTERATION_SALES_LINKAGE_TYPE_FALSE);
        insertTransactionImportData
                .setDataAlterationBackboneLinkageType(DATA_ALTERATION_BACKBONE_LINKAGE_TYPE_TRUE);
        insertTransactionImportData.setDataCorrectionUserId(loginUserId);
        callTransactionErrorCheck(insertTransactionImportData, loginUserId, transactionId);

        CreateTransactionImportData createTransactionImportData = new CreateTransactionImportData();
        modelMapper.map(insertTransactionImportData, createTransactionImportData);
        callTransactionApi(createTransactionImportData, transactionId, false);

        List<String> transactionIdList = getTransactionIdList(transactionId);

        transactionIdList.forEach(transaction -> {
            CreateTransactionImportData inCreateTransactionImportData =
                    new CreateTransactionImportData();
            modelMapper.map(transactionImportData, inCreateTransactionImportData);
            inCreateTransactionImportData.setErrorCheckType(ERROR_CHECK_TYPE_TRUE);
            inCreateTransactionImportData
                    .setDataAlterationSalesLinkageType(DATA_ALTERATION_SALES_LINKAGE_TYPE_FALSE);
            inCreateTransactionImportData.setDataAlterationBackboneLinkageType(
                    DATA_ALTERATION_BACKBONE_LINKAGE_TYPE_TRUE);
            callTransactionApi(inCreateTransactionImportData, transactionId, false);
        });
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void back(SalesTransactionCorrectionForm salesTransactionCorrectionForm) {
        AlterationExclusionControlCondition condition = new AlterationExclusionControlCondition();
        String businessDate = null;
        if (StringUtils.isNotEmpty(salesTransactionCorrectionForm.getBusinessDate())) {
            businessDate = salesTransactionCorrectionForm.getBusinessDate();
        } else {
            businessDate = salesTransactionCorrectionForm.getDataCreationDate();
        }
        condition.createCriteria()
                .andBusinessDateEqualTo(businessDate)
                .andCashRegisterNoEqualTo(
                        Integer.parseInt(salesTransactionCorrectionForm.getCashRegisterNo()))
                .andDataAlterationTypeEqualTo(DATA_ALTERATION_TYPE)
                .andStoreCodeEqualTo(salesTransactionCorrectionForm.getStoreCode());
        alterationExclusionControlMapper.deleteByCondition(condition);

    }
}
