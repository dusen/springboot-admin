/**
 * @(#)SettlementCorrectionHistoryListServiceImpl.java
 *
 *                                                     Copyright (c) 2018 Fast Retailing
 *                                                     Corporation.
 */

package com.fastretailing.dcp.sales.settlementcorrectionhistory.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections4.comparators.ComparatorChain;
import org.apache.commons.collections4.comparators.ReverseComparator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fastretailing.dcp.common.constants.ErrorName;
import com.fastretailing.dcp.common.constants.LogLevel;
import com.fastretailing.dcp.common.exception.BusinessException;
import com.fastretailing.dcp.sales.common.constants.SalesFunctionId;
import com.fastretailing.dcp.sales.common.entity.CommonCodeMaster;
import com.fastretailing.dcp.sales.common.entity.CommonCodeMasterCondition;
import com.fastretailing.dcp.sales.common.entity.optional.SettlementCorrectionHistoryOptional;
import com.fastretailing.dcp.sales.common.entity.optional.SettlementCorrectionHistoryOptionalCondition;
import com.fastretailing.dcp.sales.common.repository.CommonCodeMasterMapper;
import com.fastretailing.dcp.sales.common.repository.optional.SettlementCorrectionHistoryOptionalMapper;
import com.fastretailing.dcp.sales.settlementcorrectionhistory.form.SettlementCorrectionHistory;
import com.fastretailing.dcp.sales.settlementcorrectionhistory.form.SettlementCorrectionHistoryListForm;
import com.fastretailing.dcp.storecommon.screen.message.LocaleMessageSource;
import com.fastretailing.dcp.storecommon.screen.util.ScreenCommonUtility;
import com.fastretailing.dcp.storecommon.type.FunctionType;
import com.fastretailing.dcp.storecommon.type.MessageType;
import com.fastretailing.dcp.storecommon.type.PlatformShortName;
import com.fastretailing.dcp.storecommon.util.CommonUtility;

/**
 * Settlement correction history list serviceImpl.
 */
@Service
public class SettlementCorrectionHistoryListServiceImpl
        implements SettlementCorrectionHistoryListService {

    /** Date format(uuuu/MM/dd). */
    private static final DateTimeFormatter DATE_FORMAT_UUUUHMMHDD =
            DateTimeFormatter.ofPattern("uuuu/MM/dd");

    /** Date format(uuuuMMdd). */
    private static final DateTimeFormatter DATE_FORMAT_UUUUMMDD =
            DateTimeFormatter.ofPattern("uuuuMMdd");

    /** Locale message source. */
    @Autowired
    private LocaleMessageSource localeMessageSource;

    /** Common code master mapper. */
    @Autowired
    private CommonCodeMasterMapper commonCodeMasterMapper;

    /** Settlement correction history optional mapper. */
    @Autowired
    private SettlementCorrectionHistoryOptionalMapper settlementCorrectionHistoryOptionalMapper;

    /** Amount quantity different flag. */
    private static final String AMOUNT_QUANTITY_DIFFERENT_FLAG_1 = "1";

    /** Amount quantity not different flag. */
    private static final String AMOUNT_QUANTITY_DIFFERENT_FLAG_0 = "0";

    /** Field id(system_country_code). */
    private static final String FIELD_ID_SYSTEM_COUNTRY_CODE = "system_country_code";

    /** Field id(system_brand_code). */
    private static final String FIELD_ID_SYSTEM_BRAND_CODE = "system_brand_code";

    /** Order by clause(type_id asc). */
    private static final String ORDER_BY_CLAUSE_TYPE_ID = "type_id ASC";

    /** Message id of business error. */
    private static final String MESSAGE_ID_SLS_66000128 = CommonUtility.createMessageId(
            LogLevel.ERROR, PlatformShortName.SALES, MessageType.BUSINESS_ERROR,
            FunctionType.SCREEN, SalesFunctionId.SALES_COMMON_CODE_MASTER);

    /**
     * {@inheritDoc}
     */
    @Override
    public SettlementCorrectionHistoryListForm getInitializeInformation(
            SettlementCorrectionHistoryListForm settlementCorrectionHistoryListForm) {

        CommonCodeMasterCondition commonCodeMasterCondition = new CommonCodeMasterCondition();

        // Get system brand list.
        commonCodeMasterCondition.createCriteria().andTypeIdEqualTo(FIELD_ID_SYSTEM_BRAND_CODE);
        commonCodeMasterCondition.setOrderByClause(ORDER_BY_CLAUSE_TYPE_ID);

        List<CommonCodeMaster> systemBrandList =
                commonCodeMasterMapper.selectByCondition(commonCodeMasterCondition);

        if (CollectionUtils.isEmpty(systemBrandList)) {
            throwBusinessException(MESSAGE_ID_SLS_66000128);
        }

        settlementCorrectionHistoryListForm.setSystemBrandCodeMap(systemBrandList.stream().collect(
                Collectors.toMap(CommonCodeMaster::getTypeValue, CommonCodeMaster::getName1,
                        (master1, master2) -> master1, LinkedHashMap::new)));

        // Get system country list.
        commonCodeMasterCondition.clear();
        commonCodeMasterCondition.createCriteria().andTypeIdEqualTo(FIELD_ID_SYSTEM_COUNTRY_CODE);
        commonCodeMasterCondition.setOrderByClause(ORDER_BY_CLAUSE_TYPE_ID);


        List<CommonCodeMaster> systemCountryList =
                commonCodeMasterMapper.selectByCondition(commonCodeMasterCondition);

        if (CollectionUtils.isEmpty(systemCountryList)) {
            throwBusinessException(MESSAGE_ID_SLS_66000128);
        }

        settlementCorrectionHistoryListForm
                .setSystemCountryCodeMap(systemCountryList.stream()
                        .collect(Collectors.toMap(CommonCodeMaster::getTypeValue,
                                CommonCodeMaster::getName1, (master1, master2) -> master1,
                                LinkedHashMap::new)));

        return settlementCorrectionHistoryListForm;
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

    /**
     * {@inheritDoc}
     */
    @Override
    public SettlementCorrectionHistoryListForm getSettlementCorrectionHistoryList(
            SettlementCorrectionHistoryListForm settlementCorrectionHistoryListForm) {

        // Get store code and store name by view store code.
        List<SettlementCorrectionHistoryOptional> storeCodeAndNameList =
                settlementCorrectionHistoryOptionalMapper.selectStoreCodeAndNameByViewStoreCode(
                        settlementCorrectionHistoryListForm.getViewStoreCode());

        if (CollectionUtils.isEmpty(storeCodeAndNameList)) {
            settlementCorrectionHistoryListForm.setSettlementCorrectionHistoryList(null);
            return settlementCorrectionHistoryListForm;
        }

        // Get settlement correction history optional list from alteration history table.
        List<SettlementCorrectionHistoryOptional> settlementCorrectionHistoryOptionalList =
                settlementCorrectionHistoryOptionalMapper
                        .selectSettlementCorrectionHistoryListByCondition(
                                createSettlementCorrectionHistoryCondition(
                                        storeCodeAndNameList.get(0).getStoreCode(),
                                        settlementCorrectionHistoryListForm));

        if (CollectionUtils.isEmpty(settlementCorrectionHistoryOptionalList)) {
            settlementCorrectionHistoryListForm.setSettlementCorrectionHistoryList(null);
            return settlementCorrectionHistoryListForm;
        }

        // Get System brand name and system country name.
        CommonCodeMasterCondition commonCodeMasterCondition = new CommonCodeMasterCondition();

        // Get system brand name.
        commonCodeMasterCondition.createCriteria()
                .andTypeIdEqualTo(FIELD_ID_SYSTEM_BRAND_CODE)
                .andTypeValueEqualTo(settlementCorrectionHistoryListForm.getSystemBrandCode());

        String systemBrandName = commonCodeMasterMapper.selectByCondition(commonCodeMasterCondition)
                .get(0)
                .getName1();

        // Get system country name.
        commonCodeMasterCondition.clear();
        commonCodeMasterCondition.createCriteria()
                .andTypeIdEqualTo(FIELD_ID_SYSTEM_COUNTRY_CODE)
                .andTypeValueEqualTo(settlementCorrectionHistoryListForm.getSystemCountryCode());

        String systemCountryName =
                commonCodeMasterMapper.selectByCondition(commonCodeMasterCondition)
                        .get(0)
                        .getName1();


        // Set view store code and store name for settlement correction history detail list.
        settlementCorrectionHistoryOptionalList.stream()
                .forEach(settlementCorrectionHistoryDetail -> {
                    settlementCorrectionHistoryDetail.setSystemBrandCode(systemBrandName);
                    settlementCorrectionHistoryDetail.setSystemCountryCode(systemCountryName);
                    settlementCorrectionHistoryDetail.setViewStoreCode(
                            settlementCorrectionHistoryListForm.getViewStoreCode());
                    settlementCorrectionHistoryDetail
                            .setStoreName(storeCodeAndNameList.get(0).getStoreName());
                });

        settlementCorrectionHistoryListForm
                .setSettlementCorrectionHistoryList(settlementCorrectionHistoryOptionalList.stream()
                        .map(settlementCorrectionHistoryDetail -> createSettlementCorrectionHistoryDetail(
                                settlementCorrectionHistoryDetail))
                        .collect(Collectors.toList()));

        return settlementCorrectionHistoryListForm;
    }

    /**
     * Create settlement correction history condition.
     *
     * @param storeCode Store code.
     * @param settlementCorrectionHistoryListForm Settlement correction history list form.
     * @return Object of settlement correction history condition.
     */
    private SettlementCorrectionHistoryOptionalCondition createSettlementCorrectionHistoryCondition(
            String storeCode,
            SettlementCorrectionHistoryListForm settlementCorrectionHistoryListForm) {

        SettlementCorrectionHistoryOptionalCondition settlementCorrectionHistoryCondition =
                new SettlementCorrectionHistoryOptionalCondition();

        // Set not null items: store code, system brand code, system country code for condition.
        settlementCorrectionHistoryCondition.setStoreCode(storeCode);
        settlementCorrectionHistoryCondition
                .setSystemBrandCode(settlementCorrectionHistoryListForm.getSystemBrandCode());
        settlementCorrectionHistoryCondition
                .setSystemCountryCode(settlementCorrectionHistoryListForm.getSystemCountryCode());

        // Set items for condition if the item has value from html.
        if (StringUtils.isNotEmpty(settlementCorrectionHistoryListForm.getPayoffDateFrom())) {
            settlementCorrectionHistoryCondition.setPayoffDateFrom(
                    parseDate(settlementCorrectionHistoryListForm.getPayoffDateFrom()));
        }
        if (StringUtils.isNotEmpty(settlementCorrectionHistoryListForm.getPayoffDateTo())) {
            settlementCorrectionHistoryCondition.setPayoffDateTo(
                    parseDate(settlementCorrectionHistoryListForm.getPayoffDateTo()));
        }
        if (StringUtils.isNotEmpty(settlementCorrectionHistoryListForm.getCashRegisterNo())) {
            settlementCorrectionHistoryCondition.setCashRegisterNo(
                    Integer.valueOf(settlementCorrectionHistoryListForm.getCashRegisterNo()));
        }
        settlementCorrectionHistoryCondition
                .setCorrector(settlementCorrectionHistoryListForm.getCorrector());

        if (StringUtils.isNotEmpty(settlementCorrectionHistoryListForm.getCorrectionDateFrom())) {
            settlementCorrectionHistoryCondition.setCorrectionDateFrom(
                    parseDate(settlementCorrectionHistoryListForm.getCorrectionDateFrom()));
        }
        if (StringUtils.isNotEmpty(settlementCorrectionHistoryListForm.getCorrectionDateTo())) {
            settlementCorrectionHistoryCondition.setCorrectionDateTo(
                    parseDate(settlementCorrectionHistoryListForm.getCorrectionDateTo()));
        }

        return settlementCorrectionHistoryCondition;
    }

    /**
     * Convert the specified text to a LocalDate with the specified format pattern.
     * 
     * @param dateString specified date string.
     * @return {@link LocalDateTime} when success, null otherwise
     */
    private LocalDateTime parseDate(String dateString) {

        DateTimeFormatter dateTimeFormatter =
                DATE_FORMAT_UUUUHMMHDD.withResolverStyle(ResolverStyle.STRICT);
        return LocalDate.parse(dateString, dateTimeFormatter).atTime(0, 0, 0);
    }

    /**
     * Create settlement correction history detail.
     *
     * @param settlementCorrectionHistoryDetail Settlement correction history detail.
     * @return Object of settlement correction history list.
     */
    private SettlementCorrectionHistory createSettlementCorrectionHistoryDetail(
            SettlementCorrectionHistoryOptional settlementCorrectionHistoryOptional) {

        SettlementCorrectionHistory settlementCorrectionHistory = new SettlementCorrectionHistory();
        settlementCorrectionHistory
                .setBrandCode(settlementCorrectionHistoryOptional.getSystemBrandCode());
        settlementCorrectionHistory
                .setCountryCode(settlementCorrectionHistoryOptional.getSystemCountryCode());
        settlementCorrectionHistory
                .setViewStoreCode(settlementCorrectionHistoryOptional.getViewStoreCode());
        settlementCorrectionHistory
                .setStoreName(settlementCorrectionHistoryOptional.getStoreName());
        settlementCorrectionHistory.setPayoffDate(LocalDate
                .parse(settlementCorrectionHistoryOptional.getPayoffDate(), DATE_FORMAT_UUUUMMDD)
                .format(DATE_FORMAT_UUUUHMMHDD));
        settlementCorrectionHistory.setCashRegisterNo(
                settlementCorrectionHistoryOptional.getCashRegisterNo().toString());
        settlementCorrectionHistory
                .setPayoffTypeCode(settlementCorrectionHistoryOptional.getPayoffTypeCode());
        settlementCorrectionHistory
                .setPayoffTypeCodeName(settlementCorrectionHistoryOptional.getPayoffTypeName());
        settlementCorrectionHistory.setPayoffTypeSubNumber(
                settlementCorrectionHistoryOptional.getPayoffTypeSubNumberCode());
        settlementCorrectionHistory.setPayoffTypeSubNumberName(
                settlementCorrectionHistoryOptional.getPayoffTypeSubNumberName());
        settlementCorrectionHistory.setPayoffAmountBefore(
                settlementCorrectionHistoryOptional.getPayoffAmountBefore().toString());
        settlementCorrectionHistory.setPayoffAmountAfter(
                settlementCorrectionHistoryOptional.getPayoffAmountAfter().toString());
        settlementCorrectionHistory.setPayoffQuantityBefore(
                settlementCorrectionHistoryOptional.getPayoffQuantityBefore().toString());
        settlementCorrectionHistory.setPayoffQuantityAfter(
                settlementCorrectionHistoryOptional.getPayoffQuantityAfter().toString());
        settlementCorrectionHistory
                .setCorrector(settlementCorrectionHistoryOptional.getUpdateUserId());
        settlementCorrectionHistory
                .setCorrectionDate(settlementCorrectionHistoryOptional.getUpdateDateTime()
                        .format(DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss")));

        if (settlementCorrectionHistoryOptional.getPayoffAmountBefore()
                .compareTo(settlementCorrectionHistoryOptional.getPayoffAmountAfter()) == 0) {
            settlementCorrectionHistory.setAmountDiffFlag(AMOUNT_QUANTITY_DIFFERENT_FLAG_0);
        } else {
            settlementCorrectionHistory.setAmountDiffFlag(AMOUNT_QUANTITY_DIFFERENT_FLAG_1);
        }

        if (settlementCorrectionHistoryOptional.getPayoffQuantityBefore()
                .compareTo(settlementCorrectionHistoryOptional.getPayoffQuantityAfter()) == 0) {
            settlementCorrectionHistory.setQuantityDiffFlag(AMOUNT_QUANTITY_DIFFERENT_FLAG_0);
        } else {
            settlementCorrectionHistory.setQuantityDiffFlag(AMOUNT_QUANTITY_DIFFERENT_FLAG_1);
        }

        return settlementCorrectionHistory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SettlementCorrectionHistoryListForm getSortedSettlementCorrectionHistoryList(
            SettlementCorrectionHistoryListForm settlementCorrectionHistoryListForm) {

        ComparatorChain<SettlementCorrectionHistory> comparator = new ComparatorChain<>();

        if (settlementCorrectionHistoryListForm.getOrderByClause() == 1) {
            comparator.addComparator(new BeanComparator<>(
                    settlementCorrectionHistoryListForm.getSortItem(), new ReverseComparator<>()));
        } else {
            comparator.addComparator(
                    new BeanComparator<>(settlementCorrectionHistoryListForm.getSortItem()));
        }

        List<SettlementCorrectionHistory> settlementCorrectionHistoryList =
                settlementCorrectionHistoryListForm.getSettlementCorrectionHistoryList();
        Collections.sort(settlementCorrectionHistoryList, comparator);
        settlementCorrectionHistoryListForm
                .setSettlementCorrectionHistoryList(settlementCorrectionHistoryList);

        return settlementCorrectionHistoryListForm;
    }

}
