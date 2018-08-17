/**
 * @(#)SalesPayoffUnmatchListServiceImpl.java
 *
 *                                            Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salespayoffunmatchlist.service;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.fastretailing.dcp.common.api.uri.UriResolver;
import com.fastretailing.dcp.common.constants.ErrorName;
import com.fastretailing.dcp.common.constants.LogLevel;
import com.fastretailing.dcp.common.exception.BusinessException;
import com.fastretailing.dcp.common.model.ResultObject;
import com.fastretailing.dcp.common.util.SystemDateTime;
import com.fastretailing.dcp.sales.common.constants.MessagePrefix;
import com.fastretailing.dcp.sales.common.constants.SalesFunctionId;
import com.fastretailing.dcp.sales.common.entity.AlterationExclusionControl;
import com.fastretailing.dcp.sales.common.entity.AlterationExclusionControlKey;
import com.fastretailing.dcp.sales.common.entity.CommonCodeMaster;
import com.fastretailing.dcp.sales.common.entity.CommonCodeMasterCondition;
import com.fastretailing.dcp.sales.common.entity.CommonCodeMasterCondition.Criteria;
import com.fastretailing.dcp.sales.common.entity.optional.SalesPayoffUnmatchCondition;
import com.fastretailing.dcp.sales.common.entity.optional.SalesPayoffUnmatchOptionalEntity;
import com.fastretailing.dcp.sales.common.repository.AlterationExclusionControlMapper;
import com.fastretailing.dcp.sales.common.repository.CommonCodeMasterMapper;
import com.fastretailing.dcp.sales.common.repository.optional.SalesPayoffUnmatchListOptionalMapper;
import com.fastretailing.dcp.sales.common.resttemplate.client.entity.sales.CallSalesPayoffIntegrityCheckRequest;
import com.fastretailing.dcp.sales.common.resttemplate.client.repository.sales.SalesRestTemplateRepository;
import com.fastretailing.dcp.sales.salespayoffunmatchlist.form.SalesPayoffUnmatch;
import com.fastretailing.dcp.sales.salespayoffunmatchlist.form.SalesPayoffUnmatchListForm;
import com.fastretailing.dcp.storecommon.screen.message.LocaleMessageSource;
import com.fastretailing.dcp.storecommon.type.FunctionType;
import com.fastretailing.dcp.storecommon.type.MessageType;
import com.fastretailing.dcp.storecommon.type.PlatformShortName;
import com.fastretailing.dcp.storecommon.util.CommonUtility;

/**
 * Sales payoff unmatch list service implements.
 */
@Service
public class SalesPayoffUnmatchListServiceImpl implements SalesPayoffUnmatchListService {

    /** Program id. */
    private static final String PROGRAM_ID = "SLS1300105";

    /** Locale message source. */
    @Autowired
    private LocaleMessageSource localeMessageSource;

    /** Common code master mapper. */
    @Autowired
    private CommonCodeMasterMapper commonCodeMasterMapper;

    /** Sales transaction history detail mapper. */
    @Autowired
    private SalesPayoffUnmatchListOptionalMapper salesPayoffUnmatchListOptionalMapper;

    /** Alteration exclusion control mapper. */
    @Autowired
    private AlterationExclusionControlMapper alterationExclusionControlMapper;

    /** Sales rest template repository. */
    @Autowired
    private SalesRestTemplateRepository salesRestTemplateRepository;

    /** Sales payoff unmatch list service. */
    @Autowired
    private SalesPayoffUnmatchListService salesPayoffUnmatchListService;

    /** URI resolver. */
    @Autowired
    private UriResolver uriResolver;

    /** UTC clock component. */
    @Autowired
    private SystemDateTime systemDateTime;

    /** Field id(system_contry_code). */
    private static final String FIELD_ID_SYSTEM_COUNTRY_CODE = "system_country_code";

    /** Field id(system_brand_code). */
    private static final String FIELD_ID_SYSTEM_BRAND_CODE = "system_brand_code";

    /** Field id(error_type). */
    private static final String FIELD_ID_ERROR_TYPE = "error_type";

    /** Field id(integrity_check_type). */
    private static final String FIELD_ID_INTEGRITY_CHECK_TYPE = "integrity_check_type";

    /** Order by clause(display_order asc). */
    private static final String ORDER_BY_CLAUSE_TYPE_ID = "display_order asc";

    /** sales-settlement-unmatch-fix page. */
    private static final String SALES_SETTLEMENT_UNMATCH_FIX = "sales-settlement-unmatch-fix";

    /** Message id of business error. */
    private static final String MESSAGE_ID_SLS1300105 = CommonUtility.createMessageId(
            LogLevel.ERROR, PlatformShortName.SALES, MessageType.BUSINESS_ERROR,
            FunctionType.SCREEN, SalesFunctionId.SALES_COMMON_CODE_MASTER);

    /**
     * Data alteration type.
     */
    private static final String DATA_ALTERATION_TYPE = "2";

    private static final String EXCLUSION_VALID_TIME = "60";

    private static final String INTEGRITY_CHECK_TYPE_VALUE_02 = "2";
    private static final String INTEGRITY_CHECK_TYPE_VALUE_03 = "3";

    private final String DATE_DELIMITER = "/";
    private final String EMPTY_STRING = "";

    private final String API_CALL_REQUEST_PARAMETER_STORE_CODE = null;
    private final String API_CALL_REQUEST_PARAMETER_BUSINESS_DATE = null;
    private final String API_CALL_REQUEST_PARAMETER_EXECUTION_TYPE = "1";


    /**
     * {@inheritDoc}
     */
    @Override
    public SalesPayoffUnmatchListForm getInitializeInformation(
            SalesPayoffUnmatchListForm salesPayoffUnmatchListForm) {

        CommonCodeMasterCondition commonCodeMasterCondition = new CommonCodeMasterCondition();
        commonCodeMasterCondition.createCriteria().andTypeIdEqualTo(FIELD_ID_SYSTEM_COUNTRY_CODE);
        commonCodeMasterCondition.setOrderByClause(ORDER_BY_CLAUSE_TYPE_ID);

        // Get system country list.
        List<CommonCodeMaster> systemCountryList =
                commonCodeMasterMapper.selectByCondition(commonCodeMasterCondition);

        // Create result object.
        if (CollectionUtils.isEmpty(systemCountryList)) {
            throwBusinessException(MESSAGE_ID_SLS1300105,
                    localeMessageSource.getMessage(MESSAGE_ID_SLS1300105));
        }

        salesPayoffUnmatchListForm.setCountryListMap(systemCountryList.stream().collect(
                Collectors.toMap(CommonCodeMaster::getName1, CommonCodeMaster::getTypeValue,
                        (master1, master2) -> master1, LinkedHashMap::new)));

        commonCodeMasterCondition.clear();
        commonCodeMasterCondition.createCriteria().andTypeIdEqualTo(FIELD_ID_SYSTEM_BRAND_CODE);
        commonCodeMasterCondition.setOrderByClause(ORDER_BY_CLAUSE_TYPE_ID);

        // Get system brand list.
        List<CommonCodeMaster> systemBrandList =
                commonCodeMasterMapper.selectByCondition(commonCodeMasterCondition);

        if (CollectionUtils.isEmpty(systemBrandList)) {
            throwBusinessException(MESSAGE_ID_SLS1300105,
                    localeMessageSource.getMessage(MESSAGE_ID_SLS1300105));
        }

        salesPayoffUnmatchListForm.setBrandListMap(systemBrandList.stream().collect(
                Collectors.toMap(CommonCodeMaster::getName1, CommonCodeMaster::getTypeValue,
                        (master1, master2) -> master1, LinkedHashMap::new)));

        commonCodeMasterCondition.clear();

        Criteria criteria = commonCodeMasterCondition.createCriteria()
                .andTypeIdEqualTo(FIELD_ID_INTEGRITY_CHECK_TYPE);
        criteria.andTypeValueIn(
                Arrays.asList(INTEGRITY_CHECK_TYPE_VALUE_02, INTEGRITY_CHECK_TYPE_VALUE_03));
        commonCodeMasterCondition.setOrderByClause(ORDER_BY_CLAUSE_TYPE_ID);

        // Get error contents list.
        List<CommonCodeMaster> integrityCheckType =
                commonCodeMasterMapper.selectByCondition(commonCodeMasterCondition);

        if (CollectionUtils.isEmpty(integrityCheckType)) {
            throwBusinessException(MESSAGE_ID_SLS1300105,
                    localeMessageSource.getMessage(MESSAGE_ID_SLS1300105));
        }

        salesPayoffUnmatchListForm.setErrorContentsMap(integrityCheckType.stream().collect(
                Collectors.toMap(CommonCodeMaster::getTypeValue, CommonCodeMaster::getName1,
                        (master1, master2) -> master1, LinkedHashMap::new)));

        return salesPayoffUnmatchListForm;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SalesPayoffUnmatchListForm getSalesPayoffUnmatchList(
            SalesPayoffUnmatchListForm salesPayoffUnmatchListForm) {

        // Get payoff unmatch list detail list.
        List<SalesPayoffUnmatchOptionalEntity> salesPayoffUnmatchList =
                salesPayoffUnmatchListOptionalMapper.selectSalesPayoffUnmatchOptionalByCondition(
                        createSalesPayoffUnmatchListCondition(salesPayoffUnmatchListForm));

        salesPayoffUnmatchListForm.setSalesPayoffUnmatchList(salesPayoffUnmatchList.stream()
                .map(salesPayoffUnmatchOptionalEntity -> createSalesPayoffUnmatch(
                        salesPayoffUnmatchOptionalEntity))
                .collect(Collectors.toList()));

        return salesPayoffUnmatchListForm;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public SalesPayoffUnmatchListForm pressAudit(
            SalesPayoffUnmatchListForm salesPayoffUnmatchListForm) {

        CallSalesPayoffIntegrityCheckRequest request = new CallSalesPayoffIntegrityCheckRequest(
                API_CALL_REQUEST_PARAMETER_STORE_CODE, API_CALL_REQUEST_PARAMETER_BUSINESS_DATE,
                API_CALL_REQUEST_PARAMETER_EXECUTION_TYPE);

        // Call batch api.
        ResponseEntity<ResultObject> response =
                salesRestTemplateRepository.callSalesPayoffIntegrityCheck(request);

        ResultObject resultObject = response.getBody();
        if ((resultObject.getName().equals(ErrorName.Business.BUSINESS_CHECK_ERROR))
                && (resultObject.getDetails() != null)) {
            resultObject.getDetails().forEach(detail -> {
                if (detail.getIssue() == MessagePrefix.SLS0600204_E_DETAILS_ISSUE) {
                    throwBusinessException(MessagePrefix.E_SLS_66000149,
                            MessagePrefix.E_SLS_66000149);
                }
            });
        }

        salesPayoffUnmatchListForm = getSalesPayoffUnmatchList(salesPayoffUnmatchListForm);

        // Display list page.
        return salesPayoffUnmatchListForm;
    }

    /**
     * Create sales payoff unmatch.
     *
     * @param SalesPayoffUnmatchOptionalEntity Sales payoff unmatch optional entity.
     * @return Sales payoff unmatch data.
     */
    private SalesPayoffUnmatch createSalesPayoffUnmatch(
            SalesPayoffUnmatchOptionalEntity salesPayoffUnmatchEntity) {

        SalesPayoffUnmatch salesPayoffUnmatch = new SalesPayoffUnmatch();
        salesPayoffUnmatch.setBrandName(salesPayoffUnmatchEntity.getSystemBrandName());
        salesPayoffUnmatch.setBrandCode(salesPayoffUnmatchEntity.getSystemBrandCode());
        salesPayoffUnmatch.setCountryCode(salesPayoffUnmatchEntity.getSystemCountryCode());
        salesPayoffUnmatch.setCountryName(salesPayoffUnmatchEntity.getSystemCountryName());
        salesPayoffUnmatch.setStoreCode(salesPayoffUnmatchEntity.getStoreCode());
        salesPayoffUnmatch.setViewStoreCode(salesPayoffUnmatchEntity.getViewStoreCode());
        salesPayoffUnmatch.setStoreName(salesPayoffUnmatchEntity.getStoreName());
        salesPayoffUnmatch.setCashRegisterNo(salesPayoffUnmatchEntity.getCashRegisterNo());
        salesPayoffUnmatch.setPayoffDate(salesPayoffUnmatchEntity.getPayoffDate());

        salesPayoffUnmatch.setErrorContents(salesPayoffUnmatchEntity.getErrorContents());

        salesPayoffUnmatch.setDataAlterationStatusName(
                salesPayoffUnmatchEntity.getDataAlterationStatusName());

        return salesPayoffUnmatch;
    }

    /**
     * Create sales transaction history detail condition.
     *
     * @param salesTransactionHistoryListForm Sales transaction history list form.
     * @return Object of sales transaction history condition.
     */
    private SalesPayoffUnmatchCondition createSalesPayoffUnmatchListCondition(
            SalesPayoffUnmatchListForm salesPayoffUnmatchListForm) {

        SalesPayoffUnmatchCondition salesPayoffUnmatchCondition = new SalesPayoffUnmatchCondition();

        if (StringUtils.isNotEmpty(salesPayoffUnmatchListForm.getSystemBrandCode())) {
            salesPayoffUnmatchCondition
                    .setSystemBrandCode(salesPayoffUnmatchListForm.getSystemBrandCode());
        }

        if (StringUtils.isNoneEmpty(salesPayoffUnmatchListForm.getSystemCountryCode())) {
            salesPayoffUnmatchCondition
                    .setSystemCountryCode(salesPayoffUnmatchListForm.getSystemCountryCode());
        }

        if (StringUtils.isNoneEmpty(salesPayoffUnmatchListForm.getViewStoreCode())) {
            salesPayoffUnmatchCondition
                    .setViewStoreCode(salesPayoffUnmatchListForm.getViewStoreCode());
        }

        if (StringUtils.isNotEmpty(salesPayoffUnmatchListForm.getCashRegisterNo())) {
            salesPayoffUnmatchCondition.setCashRegisterNo(
                    Integer.valueOf(salesPayoffUnmatchListForm.getCashRegisterNo()));
        }

        if (StringUtils.isNotEmpty(salesPayoffUnmatchListForm.getPayoffDateFrom())) {
            salesPayoffUnmatchCondition
                    .setPayoffDateFrom(salesPayoffUnmatchListForm.getPayoffDateFrom()
                            .replaceAll(DATE_DELIMITER, EMPTY_STRING));
        }

        if (StringUtils.isNotEmpty(salesPayoffUnmatchListForm.getPayoffDateTo())) {
            salesPayoffUnmatchCondition.setPayoffDateTo(salesPayoffUnmatchListForm.getPayoffDateTo()
                    .replaceAll(DATE_DELIMITER, EMPTY_STRING));
        }

        if (StringUtils.isNotEmpty(salesPayoffUnmatchListForm.getErrorContentsCode())) {
            salesPayoffUnmatchCondition
                    .setIntegrityCheckType(salesPayoffUnmatchListForm.getErrorContentsCode());
        }

        return salesPayoffUnmatchCondition;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AlterationExclusionControl checkAlterationExclusionControl(
            SalesPayoffUnmatchListForm salesPayoffUnmatchListForm) {

        SalesPayoffUnmatch salesPayoffUnmatch =
                salesPayoffUnmatchListForm.getSalesPayoffUnmatchList()
                        .get(salesPayoffUnmatchListForm.getIndexValue());

        AlterationExclusionControlKey alterationExclusionControlKey =
                new AlterationExclusionControlKey();
        alterationExclusionControlKey.setStoreCode(salesPayoffUnmatch.getStoreCode());
        alterationExclusionControlKey.setBusinessDate(salesPayoffUnmatch.getPayoffDate());
        alterationExclusionControlKey
                .setCashRegisterNo(Integer.valueOf(salesPayoffUnmatch.getCashRegisterNo()));
        alterationExclusionControlKey.setDataAlterationType(DATA_ALTERATION_TYPE);

        AlterationExclusionControl alterationExclusionControl =
                alterationExclusionControlMapper.selectByPrimaryKey(alterationExclusionControlKey);

        return alterationExclusionControl;
    }

    /**
     * Insert alteration exclusion control.
     * 
     * @param salesPayoffUnmatchListForm Sales payoff unmatch list form.
     */
    @Override
    public void insertAlterationExclusionControl(
            SalesPayoffUnmatchListForm salesPayoffUnmatchListForm) {

        SalesPayoffUnmatch salesPayoffUnmatch =
                salesPayoffUnmatchListForm.getSalesPayoffUnmatchList()
                        .get(salesPayoffUnmatchListForm.getIndexValue());
        AlterationExclusionControl alterationExclusionControl = new AlterationExclusionControl();
        alterationExclusionControl.setStoreCode(salesPayoffUnmatch.getStoreCode());
        alterationExclusionControl.setBusinessDate(salesPayoffUnmatch.getPayoffDate());
        alterationExclusionControl
                .setCashRegisterNo(Integer.valueOf(salesPayoffUnmatch.getCashRegisterNo()));
        alterationExclusionControl.setDataAlterationType(DATA_ALTERATION_TYPE);
        alterationExclusionControl.setExclusionValidTime(EXCLUSION_VALID_TIME);
        alterationExclusionControl.setCreateUserId(salesPayoffUnmatchListForm.getLoginUserId());
        // Get now date time.
        OffsetDateTime nowDateTime = systemDateTime.now();
        alterationExclusionControl.setCreateDatetime(nowDateTime);
        alterationExclusionControl.setCreateProgramId(PROGRAM_ID);
        alterationExclusionControl.setUpdateUserId(salesPayoffUnmatchListForm.getLoginUserId());
        alterationExclusionControl.setUpdateDatetime(nowDateTime);
        alterationExclusionControl.setUpdateProgramId(PROGRAM_ID);

        alterationExclusionControlMapper.insertSelective(alterationExclusionControl);

        return;
    }

    /**
     * Update alteration exclusion control.
     * 
     * @param salesPayoffUnmatchListForm Sales payoff unmatch list form.
     */
    @Override
    public void updateAlterationExclusionControl(
            SalesPayoffUnmatchListForm salesPayoffUnmatchListForm) {

        SalesPayoffUnmatch salesPayoffUnmatch =
                salesPayoffUnmatchListForm.getSalesPayoffUnmatchList()
                        .get(salesPayoffUnmatchListForm.getIndexValue());
        AlterationExclusionControl alterationExclusionControl = new AlterationExclusionControl();
        alterationExclusionControl.setStoreCode(salesPayoffUnmatch.getStoreCode());
        alterationExclusionControl.setBusinessDate(salesPayoffUnmatch.getPayoffDate());
        alterationExclusionControl
                .setCashRegisterNo(Integer.valueOf(salesPayoffUnmatch.getCashRegisterNo()));
        alterationExclusionControl.setDataAlterationType(DATA_ALTERATION_TYPE);
        alterationExclusionControl.setCreateUserId(salesPayoffUnmatchListForm.getLoginUserId());
        // Get now date time.
        OffsetDateTime nowDateTime = systemDateTime.now();
        alterationExclusionControl.setCreateDatetime(nowDateTime);
        alterationExclusionControl.setCreateProgramId(PROGRAM_ID);
        alterationExclusionControl.setUpdateUserId(salesPayoffUnmatchListForm.getLoginUserId());
        alterationExclusionControl.setUpdateDatetime(nowDateTime);
        alterationExclusionControl.setUpdateProgramId(PROGRAM_ID);

        alterationExclusionControlMapper.updateByPrimaryKeySelective(alterationExclusionControl);
    }


    /**
     * Throw business exception.
     * 
     * @param debugId debug id to set for result object.
     * @param message Message to set for result object.
     */
    private void throwBusinessException(String debugId, String message) {
        ResultObject resultObject = new ResultObject();
        resultObject.setDebugId(debugId);
        resultObject.setMessage(message);
        throw new BusinessException(resultObject);
    }
}
