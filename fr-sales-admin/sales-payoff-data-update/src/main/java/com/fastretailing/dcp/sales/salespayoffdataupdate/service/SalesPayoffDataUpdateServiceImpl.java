/**
 * @(#)SalesPayoffDataUpdateServiceImpl.java
 *
 *                                           Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salespayoffdataupdate.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fastretailing.dcp.common.constants.ErrorName;
import com.fastretailing.dcp.common.exception.BusinessException;
import com.fastretailing.dcp.sales.common.constants.BusinessItem;
import com.fastretailing.dcp.sales.common.constants.MessagePrefix;
import com.fastretailing.dcp.sales.common.entity.optional.AlterationExclusionControlOptionalKey;
import com.fastretailing.dcp.sales.common.entity.optional.BusinessCountryStateSettingMasterOptional;
import com.fastretailing.dcp.sales.common.entity.optional.PayoffDataOptional;
import com.fastretailing.dcp.sales.common.entity.optional.PayoffDataOptionalCondition;
import com.fastretailing.dcp.sales.common.entity.optional.BusinessCountryStateSettingMasterOptionalCondition;
import com.fastretailing.dcp.sales.common.entity.optional.SalesPayoffDataUpdateErrorStatusOptionalCondition;
import com.fastretailing.dcp.sales.common.entity.optional.SalesPayoffDataUpdateOptionalCondition;
import com.fastretailing.dcp.sales.common.entity.optional.SalesPayoffDataUpdateOptionalEntity;
import com.fastretailing.dcp.sales.common.repository.optional.AlterationExclusionControlOptionalMapper;
import com.fastretailing.dcp.sales.common.repository.optional.BusinessCountryStateSettingMasterOptionalMapper;
import com.fastretailing.dcp.sales.common.repository.optional.PayoffDataOptionalMapper;
import com.fastretailing.dcp.sales.common.repository.optional.SalesPayoffDataUpdateOptionalMapper;
import com.fastretailing.dcp.sales.common.resttemplate.client.repository.sales.SalesRestTemplateRepository;
import com.fastretailing.dcp.sales.importtransaction.dto.AlterationPayOffImportData;
import com.fastretailing.dcp.sales.importtransaction.dto.AlterationPayOffImportMultiData;
import com.fastretailing.dcp.sales.importtransaction.dto.PayOffAmount;
import com.fastretailing.dcp.sales.importtransaction.dto.PayOffType;
import com.fastretailing.dcp.sales.salespayoffdataupdate.form.SalesPayoffDataUpdateForm;
import com.fastretailing.dcp.sales.salespayoffdataupdate.form.SalesPayoffDataUpdateListForm;
import com.fastretailing.dcp.storecommon.ResultCode;
import com.fastretailing.dcp.storecommon.dto.CommonStatus;
import com.fastretailing.dcp.storecommon.screen.message.LocaleMessageSource;
import com.fastretailing.dcp.storecommon.screen.util.ScreenCommonUtility;
import io.netty.util.internal.StringUtil;

/**
 * Sales payoff data update service implements.
 */
@Service
public class SalesPayoffDataUpdateServiceImpl implements SalesPayoffDataUpdateService {
    /**
     * Program id.
     */
    private static final String PROGRAM_ID = "SLS1300101";
    /**
     * Date Format YYYYMMDD.
     */
    private static final String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";
    /**
     * Date Format YYYY-MM-DD.
     */
    private static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
    /**
     * Payoff data type.
     */
    private static final String PAY_OFF_DATA_TYPE = "2";
    /**
     * Data alteration type.
     */
    private static final String DATA_ALTERATION_TYPE = "2";
    /**
     * Change line.
     */
    private static final String CHANGE_LINE = "<br>";
    /**
     * Constant payoff amount.
     */
    private static final String PAY_OFF_AMOUNT = "Payoff Amount";
    /**
     * Constant payoff Quantity.
     */
    private static final String PAY_OFF_QUANTITY = "Payoff Quantity";
    /**
     * Sales integrity check.
     */
    private static final String SALES_INTEGRITY_CHECK = "SALESINTEGRITYCHECK";
    /**
     * Pile up payoff data is not same.
     */
    private static final String PILE_UP_PAY_OFF_DATA_NOT_SAME = "1";
    /**
     * Pile up payoff data is same.
     */
    private static final String PILE_UP_PAY_OFF_DATA_SAME = "0";
    /**
     * Payoff data list have data.
     */
    private static final String PAYOFF_DATA_LIST_HAVA_DATA = "1";
    /**
     * Payoff data list no data.
     */
    private static final String PAYOFF_DATA_LIST_NO_DATA = "0";
    /**
     * Locale message source.
     */
    @Autowired
    private LocaleMessageSource localeMessageSource;
    /**
     * Sales payoff data update optional mapper.
     */
    @Autowired
    private SalesPayoffDataUpdateOptionalMapper salesPayoffDataUpdateOptionalMapper;
    /**
     * Pay off data optional mapper.
     */
    @Autowired
    private PayoffDataOptionalMapper payOffDataOptionalMapper;
    /**
     * Business country state setting master mapper.
     */
    @Autowired
    private BusinessCountryStateSettingMasterOptionalMapper businessCountryStateSettingMasterOptionalMapper;
    /**
     * Alteration exclusion control mapper.
     */
    @Autowired
    private AlterationExclusionControlOptionalMapper alterationExclusionControlOptionalMapper;
    /**
     * Sales rest template repository.
     */
    @Autowired
    private SalesRestTemplateRepository salesRestTemplateRepository;

    /**
     * Get sales payoff data initialize form.
     *
     * @param salesPayoffDataUpdateForm Form of payoff data.
     * @return Sales payoff data list form data.
     */
    @Override
    public SalesPayoffDataUpdateListForm getInitializeInformation(
            SalesPayoffDataUpdateForm salesPayoffDataUpdateForm) {

        SalesPayoffDataUpdateErrorStatusOptionalCondition salesPayoffDataUpdateErrorStatusOptionalCondition =
                new SalesPayoffDataUpdateErrorStatusOptionalCondition();
        salesPayoffDataUpdateErrorStatusOptionalCondition
                .setStoreCode(salesPayoffDataUpdateForm.getStoreCode());
        salesPayoffDataUpdateErrorStatusOptionalCondition
                .setSystemBrandCode(salesPayoffDataUpdateForm.getBrandName());
        salesPayoffDataUpdateErrorStatusOptionalCondition
                .setSystemCountryCode(salesPayoffDataUpdateForm.getCountryName());
        salesPayoffDataUpdateErrorStatusOptionalCondition.setCashRegisterNo(
                Integer.valueOf(salesPayoffDataUpdateForm.getCashRegisterNumber()));

        int sum = salesPayoffDataUpdateOptionalMapper.selectSalesPayoffDataUpdateErrorStatus(
                salesPayoffDataUpdateErrorStatusOptionalCondition);
        SalesPayoffDataUpdateListForm salesPayoffDataUpdateListForm =
                new SalesPayoffDataUpdateListForm();
        if (sum == 0) {
            salesPayoffDataUpdateListForm.setSalesTransactionErrorStatus(
                    localeMessageSource.getMessage("sales-payoff-data-update.status.normal"));
        } else {
            salesPayoffDataUpdateListForm.setSalesTransactionErrorStatus(
                    localeMessageSource.getMessage("sales-payoff-data-update.status.error"));
        }

        SalesPayoffDataUpdateOptionalCondition salesPayoffDataUpdateOptionalCondition =
                new SalesPayoffDataUpdateOptionalCondition();
        salesPayoffDataUpdateOptionalCondition
                .setStoreCode(salesPayoffDataUpdateForm.getStoreCode());
        salesPayoffDataUpdateOptionalCondition.setPayoffDate(
                stringDateFormat(salesPayoffDataUpdateForm.getPayoffDate(), DATE_FORMAT_YYYYMMDD));
        salesPayoffDataUpdateOptionalCondition.setCashRegisterNo(
                Integer.valueOf(salesPayoffDataUpdateForm.getCashRegisterNumber()));

        List<SalesPayoffDataUpdateOptionalEntity> salesPayoffDataList =
                salesPayoffDataUpdateOptionalMapper
                        .selectSalesPayoffDataUpdateList(salesPayoffDataUpdateOptionalCondition);
        if (salesPayoffDataList.size() > 0) {
            salesPayoffDataUpdateListForm
                    .setSalesPayoffDataListHaveDataFlag(PAYOFF_DATA_LIST_HAVA_DATA);
            for (int i = 0; i < salesPayoffDataList.size(); i++) {
                SalesPayoffDataUpdateOptionalEntity salesPayoffDataUpdateOptionalEntity =
                        salesPayoffDataList.get(i);
                // todo 「補足(精算区分枝番名称）取得」を参照、
                // salesPayoffDataUpdateOptionalEntity.setPayoffTypeName("typename");
                // salesPayoffDataUpdateOptionalEntity.setPayoffTypeSubNumberName("subtypename");
                if (!salesPayoffDataUpdateOptionalEntity.getPayoffAmount()
                        .equals(salesPayoffDataUpdateOptionalEntity.getPileUpPayoffAmount())) {
                    salesPayoffDataUpdateOptionalEntity
                            .setPileUpPayoffDataSameDecideFlag(PILE_UP_PAY_OFF_DATA_NOT_SAME);
                }
                if (!salesPayoffDataUpdateOptionalEntity.getPayoffQuantity()
                        .equals(salesPayoffDataUpdateOptionalEntity.getPileUpPayoffQuantity())) {
                    salesPayoffDataUpdateOptionalEntity
                            .setPileUpPayoffDataSameDecideFlag(PILE_UP_PAY_OFF_DATA_NOT_SAME);
                }
            }
        } else {
            salesPayoffDataUpdateListForm
                    .setSalesPayoffDataListHaveDataFlag(PAYOFF_DATA_LIST_NO_DATA);
        }
        salesPayoffDataUpdateListForm.setSalesPayoffDataList(salesPayoffDataList);

        return salesPayoffDataUpdateListForm;
    }

    /**
     * Update sales payoff data.
     *
     * @param salesPayoffDataUpdateListForm Form of payoff data update list.
     * @return Sales payoff data list form data.
     */
    @Override
    public SalesPayoffDataUpdateListForm updatePayoffData(
            SalesPayoffDataUpdateListForm salesPayoffDataUpdateListForm) {
        List<SalesPayoffDataUpdateOptionalEntity> salesPayoffDataUpdateOptionalEntityList =
                salesPayoffDataUpdateListForm.getSalesPayoffDataList();

        String message = StringUtils.EMPTY;
        for (int i = 0; i < salesPayoffDataUpdateOptionalEntityList.size(); i++) {
            SalesPayoffDataUpdateOptionalEntity salesPayoffDataUpdateOptionalEntity =
                    salesPayoffDataUpdateOptionalEntityList.get(i);
            if (!salesPayoffDataUpdateOptionalEntity.getPayoffAmount()
                    .equals(salesPayoffDataUpdateOptionalEntity.getPileUpPayoffAmount())) {
                salesPayoffDataUpdateOptionalEntity
                        .setPileUpPayoffDataSameDecideFlag(PILE_UP_PAY_OFF_DATA_NOT_SAME);
                message = message
                        + localeMessageSource
                                .getMessage(MessagePrefix.E_SLS_66000174,
                                        new String[] {salesPayoffDataUpdateOptionalEntity
                                                .getPayoffTypeCode(), PAY_OFF_AMOUNT})
                        + CHANGE_LINE;
            }
            if (!salesPayoffDataUpdateOptionalEntity.getPayoffQuantity()
                    .equals(salesPayoffDataUpdateOptionalEntity.getPileUpPayoffQuantity())) {
                salesPayoffDataUpdateOptionalEntity
                        .setPileUpPayoffDataSameDecideFlag(PILE_UP_PAY_OFF_DATA_NOT_SAME);
                message = message
                        + localeMessageSource
                                .getMessage(MessagePrefix.E_SLS_66000174,
                                        new String[] {salesPayoffDataUpdateOptionalEntity
                                                .getPayoffTypeCode(), PAY_OFF_QUANTITY})
                        + CHANGE_LINE;
            }
            if (salesPayoffDataUpdateOptionalEntity.getPayoffAmount()
                    .equals(salesPayoffDataUpdateOptionalEntity.getPileUpPayoffAmount())
                    && salesPayoffDataUpdateOptionalEntity.getPayoffQuantity().equals(
                            salesPayoffDataUpdateOptionalEntity.getPileUpPayoffQuantity())) {
                salesPayoffDataUpdateOptionalEntity
                        .setPileUpPayoffDataSameDecideFlag(PILE_UP_PAY_OFF_DATA_SAME);
            }
        }

        if (!message.isEmpty()) {
            salesPayoffDataUpdateListForm.setDetailError(MessagePrefix.E_SLS_66000174, null,
                    message, null);
            return salesPayoffDataUpdateListForm;
        }

        String storeCode = salesPayoffDataUpdateListForm.getStoreCode();
        BigDecimal pileUpPayoffDataTotal = BigDecimal.ZERO;
        BigDecimal payoffDataTotal = BigDecimal.ZERO;
        for (int i = 0; i < salesPayoffDataUpdateOptionalEntityList.size(); i++) {
            SalesPayoffDataUpdateOptionalEntity salesPayoffDataUpdateOptionalEntity =
                    salesPayoffDataUpdateOptionalEntityList.get(i);

            BusinessCountryStateSettingMasterOptionalCondition businessCountryStateSettingMasterCondition =
                    new BusinessCountryStateSettingMasterOptionalCondition();
            businessCountryStateSettingMasterCondition.createCriteria()
                    .andCodeL1EqualTo(SALES_INTEGRITY_CHECK)
                    .andCodeL3EqualTo(salesPayoffDataUpdateOptionalEntity.getPayoffTypeCode())
                    .andStoreCodeEqualTo(storeCode);

            List<BusinessCountryStateSettingMasterOptional> businessCountryStateSettingMasterList =
                    businessCountryStateSettingMasterOptionalMapper
                            .selectByCondition(businessCountryStateSettingMasterCondition);
            BusinessCountryStateSettingMasterOptional businessCountryStateSettingMasterOptional =
                    businessCountryStateSettingMasterList.get(0);
            if (BusinessItem.SIGN_CODE_POSITIVE
                    .equals(businessCountryStateSettingMasterOptional.getCodeValue())) {
                pileUpPayoffDataTotal = pileUpPayoffDataTotal
                        .add(salesPayoffDataUpdateOptionalEntity.getPileUpPayoffAmount());
                payoffDataTotal =
                        payoffDataTotal.add(salesPayoffDataUpdateOptionalEntity.getPayoffAmount());
            } else if (BusinessItem.SIGN_CODE_NEGATIVE
                    .equals(businessCountryStateSettingMasterOptional.getCodeValue())) {
                pileUpPayoffDataTotal = pileUpPayoffDataTotal
                        .subtract(salesPayoffDataUpdateOptionalEntity.getPileUpPayoffAmount());
                payoffDataTotal = payoffDataTotal
                        .subtract(salesPayoffDataUpdateOptionalEntity.getPayoffAmount());
            }
        }
        if (!pileUpPayoffDataTotal.equals(payoffDataTotal)) {
            salesPayoffDataUpdateListForm.setDetailError(MessagePrefix.E_SLS_66000175, null,
                    localeMessageSource.getMessage(MessagePrefix.E_SLS_66000175), null);

            return salesPayoffDataUpdateListForm;
        }


        PayoffDataOptional payOffDatatable = new PayoffDataOptional();
        OffsetDateTime nowDateTime = OffsetDateTime.now();

        payOffDatatable.setDataAlterationStatus(PAY_OFF_DATA_TYPE);
        payOffDatatable.setCreateUserId(PROGRAM_ID);
        payOffDatatable.setCreateDatetime(nowDateTime);
        payOffDatatable.setCreateProgramId(PROGRAM_ID);
        payOffDatatable.setUpdateUserId(PROGRAM_ID);
        payOffDatatable.setUpdateDatetime(nowDateTime);
        payOffDatatable.setUpdateProgramId(PROGRAM_ID);

        PayoffDataOptionalCondition payOffDataCondition = new PayoffDataOptionalCondition();
        payOffDataCondition.createCriteria()
                .andStoreCodeEqualTo(salesPayoffDataUpdateListForm.getStoreCode())
                .andPayoffDateEqualTo(stringDateFormat(
                        salesPayoffDataUpdateListForm.getPayoffDate(), DATE_FORMAT_YYYYMMDD))
                .andCashRegisterNoEqualTo(
                        Integer.valueOf(salesPayoffDataUpdateListForm.getCashRegisterNumber()));

        payOffDataOptionalMapper.updateByConditionSelective(payOffDatatable, payOffDataCondition);

        // Call transaction api.
        CommonStatus commonstatus = null;
        AlterationPayOffImportMultiData alterationPayOffImportMultiData =
                new AlterationPayOffImportMultiData();
        ArrayList<AlterationPayOffImportData> payoffDataFormList = new ArrayList<>();
        AlterationPayOffImportData alterationPayOffImportData = new AlterationPayOffImportData();
        alterationPayOffImportData.setStoreCode(salesPayoffDataUpdateListForm.getStoreCode());
        alterationPayOffImportData.setCashRegisterNumber(
                Integer.valueOf(salesPayoffDataUpdateListForm.getCashRegisterNumber()));
        alterationPayOffImportData.setPayOffDate(stringDateFormat(
                salesPayoffDataUpdateListForm.getPayoffDate(), DATE_FORMAT_YYYY_MM_DD));
        alterationPayOffImportData.setDataCorrectionUserCode(PROGRAM_ID);
        List<PayOffType> payOffTypeList = new ArrayList<>();
        List<SalesPayoffDataUpdateOptionalEntity> salesPayoffDataList =
                salesPayoffDataUpdateListForm.getSalesPayoffDataList();
        if (salesPayoffDataList.size() > 0) {
            salesPayoffDataUpdateListForm
                    .setSalesPayoffDataListHaveDataFlag(PAYOFF_DATA_LIST_HAVA_DATA);
            for (int i = 0; i < salesPayoffDataList.size(); i++) {
                SalesPayoffDataUpdateOptionalEntity salesPayoffDataUpdateOptionalEntity =
                        salesPayoffDataList.get(i);
                PayOffType payOffType = new PayOffType();
                payOffType
                        .setPayoffTypeCode(salesPayoffDataUpdateOptionalEntity.getPayoffTypeCode());
                payOffType.setPayOffTypeSubNumberCode(
                        salesPayoffDataUpdateOptionalEntity.getPayoffTypeSubNumberCode());
                payOffType
                        .setPayoffQuantity(salesPayoffDataUpdateOptionalEntity.getPayoffQuantity());
                PayOffAmount payOffAmount = new PayOffAmount();
                payOffAmount.setValue(salesPayoffDataUpdateOptionalEntity.getPayoffAmount());
                payOffType.setPayOffAmount(payOffAmount);
                payOffTypeList.add(payOffType);
            }
        }
        alterationPayOffImportData.setPayOffTypeList(payOffTypeList);
        payoffDataFormList.add(alterationPayOffImportData);
        alterationPayOffImportMultiData.setPayoffDataFormList(payoffDataFormList);

        try {
            commonstatus = salesRestTemplateRepository
                    .callAlterationPayOffData(alterationPayOffImportMultiData);
        } catch (IllegalArgumentException e) {
            throwBusinessException(MessagePrefix.E_SLS_66000186, MessagePrefix.E_SLS_66000186,
                    null);
        }
        if (commonstatus.getResultCode().equals(ResultCode.ABNORMAL)) {
            throwBusinessException(MessagePrefix.E_SLS_66000186, MessagePrefix.E_SLS_66000186,
                    null);
        }

        AlterationExclusionControlOptionalKey alterationExclusionControlOptionalKey =
                new AlterationExclusionControlOptionalKey();
        alterationExclusionControlOptionalKey
                .setStoreCode(salesPayoffDataUpdateListForm.getStoreCode());
        alterationExclusionControlOptionalKey.setBusinessDate(stringDateFormat(
                salesPayoffDataUpdateListForm.getPayoffDate(), DATE_FORMAT_YYYYMMDD));
        alterationExclusionControlOptionalKey.setCashRegisterNo(
                Integer.valueOf(salesPayoffDataUpdateListForm.getCashRegisterNumber()));
        alterationExclusionControlOptionalKey.setDataAlterationType(DATA_ALTERATION_TYPE);
        alterationExclusionControlOptionalMapper
                .deleteByPrimaryKey(alterationExclusionControlOptionalKey);

        return salesPayoffDataUpdateListForm;
    }

    /**
     * Return back.
     * 
     * @param salesPayoffDataUpdateListForm Form of payoff data update list.
     */
    @Override
    public void returnBack(SalesPayoffDataUpdateListForm salesPayoffDataUpdateListForm) {

        AlterationExclusionControlOptionalKey alterationExclusionControlOptionalKey =
                new AlterationExclusionControlOptionalKey();
        alterationExclusionControlOptionalKey
                .setStoreCode(salesPayoffDataUpdateListForm.getStoreCode());
        alterationExclusionControlOptionalKey
                .setBusinessDate(salesPayoffDataUpdateListForm.getPayoffDate());
        alterationExclusionControlOptionalKey.setCashRegisterNo(
                Integer.valueOf(salesPayoffDataUpdateListForm.getCashRegisterNumber()));
        alterationExclusionControlOptionalKey.setDataAlterationType(DATA_ALTERATION_TYPE);
        alterationExclusionControlOptionalMapper
                .deleteByPrimaryKey(alterationExclusionControlOptionalKey);
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

    /**
     * String date format.
     * 
     * @param stringDate String date.
     * @return String format date.
     */
    private String stringDateFormat(String stringDate, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        try {
            return new SimpleDateFormat(pattern).format(format.parse(stringDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}

