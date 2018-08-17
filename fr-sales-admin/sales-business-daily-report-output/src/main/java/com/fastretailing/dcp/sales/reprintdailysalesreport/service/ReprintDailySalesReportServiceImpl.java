/**
 * @(#)ReprintDailySalesReportServiceImpl.java
 *
 *                                             Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.reprintdailysalesreport.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fastretailing.dcp.common.constants.ErrorName;
import com.fastretailing.dcp.common.constants.LogLevel;
import com.fastretailing.dcp.common.exception.BusinessException;
import com.fastretailing.dcp.sales.common.constants.SalesFunctionId;
import com.fastretailing.dcp.sales.common.entity.CommonCodeMaster;
import com.fastretailing.dcp.sales.common.entity.CommonCodeMasterCondition;
import com.fastretailing.dcp.sales.common.entity.CommonCodeMasterKey;
import com.fastretailing.dcp.sales.common.entity.optional.SalesreportManagementOptional;
import com.fastretailing.dcp.sales.common.entity.optional.StoreControlMasterOptional;
import com.fastretailing.dcp.sales.common.entity.optional.TranslationStoreCodeMasterOptional;
import com.fastretailing.dcp.sales.common.entity.optional.TranslationStoreCodeMasterOptionalCondition;
import com.fastretailing.dcp.sales.common.repository.CommonCodeMasterMapper;
import com.fastretailing.dcp.sales.common.repository.optional.SalesreportManagementOptionalMapper;
import com.fastretailing.dcp.sales.common.repository.optional.StoreControlMasterOptionalMapper;
import com.fastretailing.dcp.sales.common.repository.optional.TranslationStoreCodeMasterOptionalMapper;
import com.fastretailing.dcp.sales.reprintdailysalesreport.form.ReprintDailySalesReportForm;
import com.fastretailing.dcp.storecommon.screen.message.LocaleMessageSource;
import com.fastretailing.dcp.storecommon.screen.util.ScreenCommonUtility;
import com.fastretailing.dcp.storecommon.type.FunctionType;
import com.fastretailing.dcp.storecommon.type.MessageType;
import com.fastretailing.dcp.storecommon.type.PlatformShortName;
import com.fastretailing.dcp.storecommon.util.CommonUtility;

/**
 * Sales reprint daily sales report service implements.
 */
@Service
public class ReprintDailySalesReportServiceImpl implements ReprintDailySalesReportService {

    private static final DateTimeFormatter DATE_FORMAT_UUUUSMMSDD =
            DateTimeFormatter.ofPattern("uuuu/MM/dd").withResolverStyle(ResolverStyle.STRICT);

    private static final DateTimeFormatter DATE_FORMAT_UUUUMMDD =
            DateTimeFormatter.ofPattern("uuuuMMdd");

    /** Locale message source. */
    @Autowired
    private LocaleMessageSource localeMessageSource;

    /** Common code master mapper. */
    @Autowired
    private CommonCodeMasterMapper commonCodeMasterMapper;

    /** Translation store code master mapper. */
    @Autowired
    private TranslationStoreCodeMasterOptionalMapper translationStoreCodeMasterOptionalMapper;

    /** Salesreport management mapper. */
    @Autowired
    private SalesreportManagementOptionalMapper salesreportManagementOptionalMapper;

    /** Store control master mapper. */
    @Autowired
    private StoreControlMasterOptionalMapper storeControlMasterOptionalMapper;

    /** Field id(system_country_code). */
    private static final String FIELD_ID_SYSTEM_COUNTRY_CODE = "system_country_code";

    /** Field id(system_brand_code). */
    private static final String FIELD_ID_SYSTEM_BRAND_CODE = "system_brand_code";

    /** Order by clause(display_order asc). */
    private static final String ORDER_BY_CLAUSE_TYPE_ID = "display_order asc";

    /** Message id of business error. */
    private static final String MESSAGE_ID_SLS_66000128 = CommonUtility.createMessageId(
            LogLevel.ERROR, PlatformShortName.SALES, MessageType.BUSINESS_ERROR,
            FunctionType.SCREEN, SalesFunctionId.SALES_COMMON_CODE_MASTER);

    /** Message id of business error. */
    private static final String MESSAGE_ID_SLS_66000131 = CommonUtility.createMessageId(
            LogLevel.ERROR, PlatformShortName.SALES, MessageType.BUSINESS_ERROR,
            FunctionType.SCREEN, SalesFunctionId.SALES_TRANSLATION_STORE_CODE_MASTER);

    /** Message id of business error. */
    private static final String MESSAGE_ID_SLS_66000134 = CommonUtility.createMessageId(
            LogLevel.ERROR, PlatformShortName.SALES, MessageType.BUSINESS_ERROR,
            FunctionType.SCREEN, SalesFunctionId.SALES_REPORT_MANAGEMENT_MASTER);

    /**
     * {@inheritDoc}
     */
    @Override
    public ReprintDailySalesReportForm getInitializeInformation(
            ReprintDailySalesReportForm reprintDailySalesReportForm) {

        // Get system brand list.
        CommonCodeMasterCondition commonCodeMasterCondition = createCommonCodeMasterConditionBySort(
                FIELD_ID_SYSTEM_BRAND_CODE, ORDER_BY_CLAUSE_TYPE_ID);
        List<CommonCodeMaster> systemBrandList =
                commonCodeMasterMapper.selectByCondition(commonCodeMasterCondition);

        if (CollectionUtils.isEmpty(systemBrandList)) {
            throwBusinessException(MESSAGE_ID_SLS_66000128);
        }
        reprintDailySalesReportForm
                .setBrandList(systemBrandList.stream().map(CommonCodeMaster::getName1).collect(
                        Collectors.toList()));

        // Get system country list.
        commonCodeMasterCondition.clear();
        commonCodeMasterCondition = createCommonCodeMasterConditionBySort(
                FIELD_ID_SYSTEM_COUNTRY_CODE, ORDER_BY_CLAUSE_TYPE_ID);
        List<CommonCodeMaster> systemCountryList =
                commonCodeMasterMapper.selectByCondition(commonCodeMasterCondition);

        if (CollectionUtils.isEmpty(systemCountryList)) {
            throwBusinessException(MESSAGE_ID_SLS_66000128);
        }
        reprintDailySalesReportForm
                .setCountryList(systemCountryList.stream().map(CommonCodeMaster::getName1).collect(
                        Collectors.toList()));

        if (StringUtils.isNotEmpty(reprintDailySalesReportForm.getStoreCode())) {

            seletTranslationStoreCodeMaster(reprintDailySalesReportForm);
            StoreControlMasterOptional storeControlMasterOptional = storeControlMasterOptionalMapper
                    .selectByPrimaryKey(reprintDailySalesReportForm.getStoreCode());

            LocalDate temporaryBusinessDate = LocalDate
                    .parse(storeControlMasterOptional.getBusinessDate(), DATE_FORMAT_UUUUMMDD);
            String businessDate = temporaryBusinessDate.format(DATE_FORMAT_UUUUSMMSDD);

            reprintDailySalesReportForm.setBusinessDate(businessDate);
            reprintDailySalesReportForm.setBusinessDateFlag(true);
            if (Objects.isNull(storeControlMasterOptional)) {
                seletTranslationStoreCodeMaster(reprintDailySalesReportForm);
            }
        } else {
            reprintDailySalesReportForm.setStoreFlag(false);
            reprintDailySalesReportForm.setBusinessDateFlag(false);
        }

        return reprintDailySalesReportForm;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReprintDailySalesReportForm getReprintDailySalesReportList(
            ReprintDailySalesReportForm reprintDailySalesReportForm) {

        if (StringUtils.isEmpty(reprintDailySalesReportForm.getStoreCode())) {

            TranslationStoreCodeMasterOptionalCondition translationStoreCodeMasterOptionalCondition =
                    new TranslationStoreCodeMasterOptionalCondition();
            translationStoreCodeMasterOptionalCondition.createCriteria()
                    .andViewStoreCodeEqualTo(reprintDailySalesReportForm.getDisplayStoreCode())
                    .andSystemBrandCodeEqualTo(reprintDailySalesReportForm.getSystemBrandCode())
                    .andSystemCountryCodeEqualTo(
                            reprintDailySalesReportForm.getSystemCountryCode());

        }

        LocalDate temporaryBusinessDate = LocalDate
                .parse(reprintDailySalesReportForm.getBusinessDate(), DATE_FORMAT_UUUUSMMSDD);
        String businessDate = temporaryBusinessDate.format(DATE_FORMAT_UUUUMMDD);

        SalesreportManagementOptional salesreportManagementOptional =
                salesreportManagementOptionalMapper.selectByPrimaryKey(
                        reprintDailySalesReportForm.getDisplayStoreCode(), businessDate);

        if (Objects.isNull(salesreportManagementOptional)) {
            throwBusinessException(MESSAGE_ID_SLS_66000134);
        }

        // TODO ISSUE x call svf api .

        return reprintDailySalesReportForm;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReprintDailySalesReportForm printReprintDailySalesReportList(
            ReprintDailySalesReportForm reprintDailySalesReportForm) {

        // Get system brand list.
        CommonCodeMasterCondition commonCodeMasterCondition = createCommonCodeMasterCondition(
                FIELD_ID_SYSTEM_BRAND_CODE, reprintDailySalesReportForm.getSystemBrandCode());

        List<CommonCodeMaster> systemBrandList =
                commonCodeMasterMapper.selectByCondition(commonCodeMasterCondition);
        if (CollectionUtils.isEmpty(systemBrandList)) {
            throwBusinessException(MESSAGE_ID_SLS_66000128);
        }

        // Get system country list.
        CommonCodeMasterCondition commonCountryCodeMasterCondition =
                createCommonCodeMasterCondition(FIELD_ID_SYSTEM_COUNTRY_CODE,
                        reprintDailySalesReportForm.getSystemCountryCode());

        List<CommonCodeMaster> systemCountryList =
                commonCodeMasterMapper.selectByCondition(commonCountryCodeMasterCondition);
        if (CollectionUtils.isEmpty(systemCountryList)) {
            throwBusinessException(MESSAGE_ID_SLS_66000128);
        }

        TranslationStoreCodeMasterOptionalCondition translationStoreCodeMasterOptionalCondition =
                new TranslationStoreCodeMasterOptionalCondition();
        translationStoreCodeMasterOptionalCondition.createCriteria()
                .andViewStoreCodeEqualTo(reprintDailySalesReportForm.getDisplayStoreCode())
                .andSystemBrandCodeEqualTo(systemBrandList.get(0).getTypeValue())
                .andSystemCountryCodeEqualTo(systemCountryList.get(0).getTypeValue());

        LocalDate temporaryBusinessDate = LocalDate
                .parse(reprintDailySalesReportForm.getBusinessDate(), DATE_FORMAT_UUUUSMMSDD);
        String businessDate = temporaryBusinessDate.format(DATE_FORMAT_UUUUMMDD);

        SalesreportManagementOptional salesreportManagementOptional =
                salesreportManagementOptionalMapper.selectByPrimaryKey(
                        reprintDailySalesReportForm.getDisplayStoreCode(), businessDate);

        if (Objects.isNull(salesreportManagementOptional)) {
            throwBusinessException(MESSAGE_ID_SLS_66000134);
        }

        // TODO ISSUE x call print api .

        return reprintDailySalesReportForm;
    }

    /**
     * Get the data from tables.
     *
     * @param reprintDailySalesReportForm Reprint daily sales report form.
     */
    private void seletTranslationStoreCodeMaster(
            ReprintDailySalesReportForm reprintDailySalesReportForm) {

        TranslationStoreCodeMasterOptional translationStoreCodeMasterOptional =
                translationStoreCodeMasterOptionalMapper
                        .selectByPrimaryKey(reprintDailySalesReportForm.getStoreCode());

        if (Objects.isNull(translationStoreCodeMasterOptional)) {
            throwBusinessException(MESSAGE_ID_SLS_66000131);
        }
        reprintDailySalesReportForm
                .setDisplayStoreCode(translationStoreCodeMasterOptional.getViewStoreCode());

        CommonCodeMasterKey commonBrandCodeMasterKey =
                createCommonCodeMasterKey(FIELD_ID_SYSTEM_BRAND_CODE,
                        translationStoreCodeMasterOptional.getSystemBrandCode());
        CommonCodeMaster commonBrandCodeMaster =
                commonCodeMasterMapper.selectByPrimaryKey(commonBrandCodeMasterKey);

        if (Objects.isNull(commonBrandCodeMaster)) {
            throwBusinessException(MESSAGE_ID_SLS_66000128);
        }
        reprintDailySalesReportForm.setSystemBrandName(commonBrandCodeMaster.getName1());
        reprintDailySalesReportForm.setSystemBrandCode(commonBrandCodeMaster.getName1());

        CommonCodeMasterKey commonCountryCodeMasterKey =
                createCommonCodeMasterKey(FIELD_ID_SYSTEM_COUNTRY_CODE,
                        translationStoreCodeMasterOptional.getSystemCountryCode());
        commonCountryCodeMasterKey.setTypeId(FIELD_ID_SYSTEM_COUNTRY_CODE);
        CommonCodeMaster commonCountryCodeMaster =
                commonCodeMasterMapper.selectByPrimaryKey(commonCountryCodeMasterKey);
        if (Objects.isNull(commonCountryCodeMaster)) {
            throwBusinessException(MESSAGE_ID_SLS_66000128);
        }
        reprintDailySalesReportForm.setSystemCountryName(commonCountryCodeMaster.getName1());
        reprintDailySalesReportForm.setSystemCountryCode(commonCountryCodeMaster.getName1());
        reprintDailySalesReportForm.setStoreFlag(true);
    }

    /**
     * Create the common code master condition by sort.
     *
     * @param typeId Type id.
     * @param orderByClause Order by clause.
     * @return Common code master condition.
     */
    private CommonCodeMasterCondition createCommonCodeMasterConditionBySort(String typeId,
            String orderByClause) {

        CommonCodeMasterCondition commonCodeMasterCondition = new CommonCodeMasterCondition();
        commonCodeMasterCondition.createCriteria().andTypeIdEqualTo(typeId);
        commonCodeMasterCondition.setOrderByClause(orderByClause);
        return commonCodeMasterCondition;
    }

    /**
     * Create the common code master condition object.
     *
     * @param typeId Type id.
     * @param name Name 1.
     * @return Common code master condition.
     */
    private CommonCodeMasterCondition createCommonCodeMasterCondition(String typeId, String name) {

        CommonCodeMasterCondition commonCodeMasterCondition = new CommonCodeMasterCondition();
        commonCodeMasterCondition.createCriteria().andTypeIdEqualTo(typeId).andName1EqualTo(name);
        return commonCodeMasterCondition;
    }

    /**
     * Create the common code master key.
     *
     * @param typeId Type id.
     * @param typeValue Type value.
     * @return Common code master key.
     */
    private CommonCodeMasterKey createCommonCodeMasterKey(String typeId, String typeValue) {

        CommonCodeMasterKey commonCodeMasterKey = new CommonCodeMasterKey();
        commonCodeMasterKey.setTypeId(typeId);
        commonCodeMasterKey.setTypeValue(typeValue);
        return commonCodeMasterKey;
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
