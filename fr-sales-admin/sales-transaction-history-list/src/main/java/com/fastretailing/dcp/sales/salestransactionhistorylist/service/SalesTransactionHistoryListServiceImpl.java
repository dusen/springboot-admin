/**
 * @(#)SalesTransactionHistoryListServiceImpl.java
 *
 *                                                 Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salestransactionhistorylist.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.TimeZone;
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
import com.fastretailing.dcp.sales.common.entity.optional.SalesTransactionHistoryDetail;
import com.fastretailing.dcp.sales.common.entity.optional.SalesTransactionHistoryDetailCondition;
import com.fastretailing.dcp.sales.common.repository.CommonCodeMasterMapper;
import com.fastretailing.dcp.sales.common.repository.optional.SalesTransactionHistoryDetailOptionalMapper;
import com.fastretailing.dcp.sales.common.repository.optional.StoreGeneralPurposeMasterOptionalMapper;
import com.fastretailing.dcp.sales.common.type.DataAlterationBackboneLinkageType;
import com.fastretailing.dcp.sales.common.type.DataAlterationFlag;
import com.fastretailing.dcp.sales.common.type.DataAlterationSalesLinkageType;
import com.fastretailing.dcp.sales.common.type.HistoryType;
import com.fastretailing.dcp.sales.common.type.SalesLinkageType;
import com.fastretailing.dcp.sales.common.type.UpdateType;
import com.fastretailing.dcp.sales.salestransactionhistorylist.form.SalesTransactionHistory;
import com.fastretailing.dcp.sales.salestransactionhistorylist.form.SalesTransactionHistoryListForm;
import com.fastretailing.dcp.sales.salestransactionhistorylist.form.SelectItem;
import com.fastretailing.dcp.storecommon.screen.message.LocaleMessageSource;
import com.fastretailing.dcp.storecommon.screen.util.ScreenCommonUtility;
import com.fastretailing.dcp.storecommon.type.FunctionType;
import com.fastretailing.dcp.storecommon.type.MessageType;
import com.fastretailing.dcp.storecommon.type.PlatformShortName;
import com.fastretailing.dcp.storecommon.util.CommonUtility;

/**
 * Sales transaction history list service implements.
 */
@Service
public class SalesTransactionHistoryListServiceImpl implements SalesTransactionHistoryListService {

    /** Date format(uuuu/MM/dd). */
    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("uuuu/MM/dd").withResolverStyle(ResolverStyle.STRICT);

    /** Locale message source. */
    @Autowired
    private LocaleMessageSource localeMessageSource;

    /** Common code master mapper. */
    @Autowired
    private CommonCodeMasterMapper commonCodeMasterMapper;

    /** Sales transaction history detail mapper. */
    @Autowired
    private SalesTransactionHistoryDetailOptionalMapper salesTransactionHistoryDetailOptionalMapper;

    /** Store general purpose master optional mapper. */
    @Autowired
    private StoreGeneralPurposeMasterOptionalMapper storeGeneralPurposeMasterOptionalMapper;

    /** Date time format(uuuu/MM/dd HH:mm:ss). */
    private static final DateTimeFormatter DATE_TIME_FORMAT =
            DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");

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

    /** Update type display word(delete). */
    private static final String UPDATE_TYPE_DISPLAY_WORD_DELETE = "Delete";

    /** Update type display word(insert). */
    private static final String UPDATE_TYPE_DISPLAY_WORD_INSERT = "Insert";

    /** Update type display word(correction). */
    private static final String UPDATE_TYPE_DISPLAY_WORD_CORRECTION = "Correction";

    /** Reflect display word(all). */
    private static final String REFLECT_DISPLAY_WORD_ALL = "ALL";

    /** Reflect display word(only on receipt). */
    private static final String REFLECT_DISPLAY_WORD_ONLY_ON_RECEIPT = "only on Receipt";

    /** Reflect display word(only on ims linkage). */
    private static final String REFLECT_DISPLAY_WORD_ONLY_ON_IMS_LINKAGE = "only on IMS Linkage";

    /** Zone id is utc. */
    private static final String ZONE_ID_UTC = "UTC";

    /** Message id of business error. */
    private static final String MESSAGE_ID_SLS_66000128 = CommonUtility.createMessageId(
            LogLevel.ERROR, PlatformShortName.SALES, MessageType.BUSINESS_ERROR,
            FunctionType.SCREEN, SalesFunctionId.SALES_COMMON_CODE_MASTER);

    /**
     * {@inheritDoc}
     */
    @Override
    public SalesTransactionHistoryListForm getInitializeInformation(
            SalesTransactionHistoryListForm salesTransactionHistoryListForm) {

        CommonCodeMasterCondition commonCodeMasterCondition = new CommonCodeMasterCondition();
        commonCodeMasterCondition.createCriteria().andTypeIdEqualTo(FIELD_ID_SYSTEM_COUNTRY_CODE);
        commonCodeMasterCondition.setOrderByClause(ORDER_BY_CLAUSE_TYPE_ID);

        // Get system country list.
        List<CommonCodeMaster> systemCountryList =
                commonCodeMasterMapper.selectByCondition(commonCodeMasterCondition);

        if (CollectionUtils.isEmpty(systemCountryList)) {
            throwBusinessException(MESSAGE_ID_SLS_66000128);
        }

        salesTransactionHistoryListForm
                .setCountryList(systemCountryList.stream().map(commonMaster -> {
                    SelectItem selectItem = new SelectItem();
                    selectItem.setValue(commonMaster.getTypeValue());
                    selectItem.setName(commonMaster.getName1());
                    return selectItem;
                }).collect(Collectors.toList()));

        commonCodeMasterCondition.clear();
        commonCodeMasterCondition.createCriteria().andTypeIdEqualTo(FIELD_ID_SYSTEM_BRAND_CODE);
        commonCodeMasterCondition.setOrderByClause(ORDER_BY_CLAUSE_TYPE_ID);

        // Get system brand list.
        List<CommonCodeMaster> systemBrandList =
                commonCodeMasterMapper.selectByCondition(commonCodeMasterCondition);

        if (CollectionUtils.isEmpty(systemBrandList)) {
            throwBusinessException(MESSAGE_ID_SLS_66000128);
        }

        salesTransactionHistoryListForm.setBrandList(systemBrandList.stream().map(commonMaster -> {
            SelectItem selectItem = new SelectItem();
            selectItem.setValue(commonMaster.getTypeValue());
            selectItem.setName(commonMaster.getName1());
            return selectItem;
        }).collect(Collectors.toList()));

        commonCodeMasterCondition.clear();
        commonCodeMasterCondition.createCriteria().andTypeIdEqualTo(FIELD_ID_TRANSACTION_TYPE);
        commonCodeMasterCondition.setOrderByClause(ORDER_BY_CLAUSE_TYPE_ID);

        // Get transaction type list.
        List<CommonCodeMaster> transactionTypeList =
                commonCodeMasterMapper.selectByCondition(commonCodeMasterCondition);

        if (CollectionUtils.isEmpty(transactionTypeList)) {
            throwBusinessException(MESSAGE_ID_SLS_66000128);
        }

        salesTransactionHistoryListForm
                .setSalesTransactionTypeMap(transactionTypeList.stream()
                        .collect(Collectors.toMap(CommonCodeMaster::getTypeValue,
                                CommonCodeMaster::getName1, (master1, master2) -> master1,
                                LinkedHashMap::new)));

        commonCodeMasterCondition.clear();
        commonCodeMasterCondition.createCriteria().andTypeIdEqualTo(FIELD_ID_ERROR_TYPE);
        commonCodeMasterCondition.setOrderByClause(ORDER_BY_CLAUSE_TYPE_ID);

        // Get error contents list.
        List<CommonCodeMaster> errorContentsList =
                commonCodeMasterMapper.selectByCondition(commonCodeMasterCondition);

        if (CollectionUtils.isEmpty(errorContentsList)) {
            throwBusinessException(MESSAGE_ID_SLS_66000128);
        }

        salesTransactionHistoryListForm.setErrorContentsMap(errorContentsList.stream().collect(
                Collectors.toMap(CommonCodeMaster::getTypeValue, CommonCodeMaster::getName1,
                        (master1, master2) -> master1, LinkedHashMap::new)));

        return salesTransactionHistoryListForm;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SalesTransactionHistoryListForm getSalesTransactionHistoryList(
            SalesTransactionHistoryListForm salesTransactionHistoryListForm) {

        // Get time zone for this action.
        List<String> timeZoneList = storeGeneralPurposeMasterOptionalMapper
                .selectTimeZoneByViewStoreCode(salesTransactionHistoryListForm.getStoreCode());
        if (CollectionUtils.isEmpty(timeZoneList)) {
            return salesTransactionHistoryListForm;
        }

        // Get sales transaction history detail list.
        List<SalesTransactionHistoryDetail> salesTransactionHistoryDetailList =
                salesTransactionHistoryDetailOptionalMapper
                        .selectSalesTransactionHistoryDetailByCondition(
                                createSalesTransactionHistoryDetailCondition(
                                        salesTransactionHistoryListForm, timeZoneList.get(0)));

        salesTransactionHistoryListForm
                .setSalesTransactionHistoryList(salesTransactionHistoryDetailList.stream()
                        .map(salesTransactionHistoryDetail -> createSalesTransactionHistoryDetail(
                                salesTransactionHistoryDetail, timeZoneList.get(0)))
                        .collect(Collectors.toList()));

        return salesTransactionHistoryListForm;
    }

    /**
     * Create sales transaction history.
     *
     * @param salesTransactionHistoryDetail Sales transaction history detail.
     * @param timeZone Time zone id.
     * @return Object of sales transaction history.
     */
    private SalesTransactionHistory createSalesTransactionHistoryDetail(
            SalesTransactionHistoryDetail salesTransactionHistoryDetail, String timeZone) {

        // Get System brand name and system country name.
        CommonCodeMasterCondition commonCodeMasterCondition = new CommonCodeMasterCondition();
        commonCodeMasterCondition.createCriteria()
                .andTypeIdEqualTo(FIELD_ID_SYSTEM_BRAND_CODE)
                .andTypeValueEqualTo(salesTransactionHistoryDetail.getSystemBrandCode());

        String systemBrandName = commonCodeMasterMapper.selectByCondition(commonCodeMasterCondition)
                .get(0)
                .getName1();

        commonCodeMasterCondition.clear();
        commonCodeMasterCondition.createCriteria()
                .andTypeIdEqualTo(FIELD_ID_SYSTEM_COUNTRY_CODE)
                .andTypeValueEqualTo(salesTransactionHistoryDetail.getSystemCountryCode());
        String systemCountryName =
                commonCodeMasterMapper.selectByCondition(commonCodeMasterCondition)
                        .get(0)
                        .getName1();

        SalesTransactionHistory salesTransactionHistory = new SalesTransactionHistory();
        salesTransactionHistory.setBrandCode(systemBrandName);
        salesTransactionHistory.setCountryCode(systemCountryName);
        salesTransactionHistory.setSalesTransactionErrorId(
                salesTransactionHistoryDetail.getSalesTransactionErrorId());
        salesTransactionHistory.setStoreCode(salesTransactionHistoryDetail.getViewStoreCode());
        salesTransactionHistory.setStoreName(salesTransactionHistoryDetail.getStoreName());
        salesTransactionHistory
                .setCashRegisterNo(salesTransactionHistoryDetail.getCashRegisterNo());
        salesTransactionHistory.setReceiptNo(salesTransactionHistoryDetail.getReceiptNo());
        salesTransactionHistory
                .setSalesTransactionType(salesTransactionHistoryDetail.getSalesTransactionType());

        salesTransactionHistory
                .setDataCreationDate(salesTransactionHistoryDetail.getDataCreationDateTime()
                        .atZoneSameInstant(ZoneId.of(timeZone))
                        .toLocalDateTime()
                        .format(DATE_TIME_FORMAT));
        salesTransactionHistory
                .setBusinessDate(salesTransactionHistoryDetail.getOrderStatusUpdateDate());
        salesTransactionHistory.setErrorContents(salesTransactionHistoryDetailOptionalMapper
                .selectErrorContentsName(salesTransactionHistoryDetail));
        salesTransactionHistory
                .setCorrectorCode(salesTransactionHistoryDetail.getDataAlterationUserId());
        salesTransactionHistory.setCorrectionDate(
                salesTransactionHistoryDetail.getUpdateDateTime().format(DATE_TIME_FORMAT));
        salesTransactionHistory.setReflect(decideReflectDisplayWord(
                salesTransactionHistoryDetail.getDataAlterationSalesLinkageType(),
                salesTransactionHistoryDetail.getDataAlterationBackboneLinkageType()));
        salesTransactionHistory.setUpdateType(
                decideUpdateTypeDisplayWord(salesTransactionHistoryDetail.getHistoryType(),
                        salesTransactionHistoryDetail.getUpdateType()));

        return salesTransactionHistory;
    }

    /**
     * Create update type display word.
     *
     * @param historyType History type.
     * @param updateType Update type.
     * @return Update type display word.
     */
    private String decideUpdateTypeDisplayWord(Integer historyType, String updateType) {

        if (!HistoryType.AFTER.is(historyType)) {
            return UPDATE_TYPE_DISPLAY_WORD_DELETE;
        }
        if (UpdateType.INSERT.is(updateType)) {
            return UPDATE_TYPE_DISPLAY_WORD_INSERT;
        }
        return UPDATE_TYPE_DISPLAY_WORD_CORRECTION;

    }

    /**
     * Create reflect display word.
     *
     * @param dataAlterationSalesLinkageType Data alteration sales linkage type.
     * @param dataAlterationBackboneLinkageType Data alteration backbone linkage type.
     * @return Reflect display word.
     */
    private String decideReflectDisplayWord(Integer dataAlterationSalesLinkageType,
            Integer dataAlterationBackboneLinkageType) {

        if (DataAlterationSalesLinkageType.OFF.is(dataAlterationSalesLinkageType)) {
            if (DataAlterationBackboneLinkageType.OFF.is(dataAlterationBackboneLinkageType)) {
                return REFLECT_DISPLAY_WORD_ALL;
            }
            return REFLECT_DISPLAY_WORD_ONLY_ON_RECEIPT;
        }
        return REFLECT_DISPLAY_WORD_ONLY_ON_IMS_LINKAGE;

    }


    /**
     * Create sales transaction history detail condition.
     *
     * @param salesTransactionHistoryListForm Sales transaction history list form.
     * @param timeZone Time zone id.
     * @return Object of sales transaction history condition.
     */
    private SalesTransactionHistoryDetailCondition createSalesTransactionHistoryDetailCondition(
            SalesTransactionHistoryListForm salesTransactionHistoryListForm, String timeZone) {

        SalesTransactionHistoryDetailCondition salesTransactionHistoryDetailCondition =
                new SalesTransactionHistoryDetailCondition();
        salesTransactionHistoryDetailCondition.setOrderHistoryType(HistoryType.AFTER.getValue());
        salesTransactionHistoryDetailCondition.setHeaderHistoryType(HistoryType.AFTER.getValue());
        salesTransactionHistoryDetailCondition
                .setSystemBrandCode(salesTransactionHistoryListForm.getSystemBrandCode());
        salesTransactionHistoryDetailCondition
                .setSystemCountryCode(salesTransactionHistoryListForm.getSystemCountryCode());
        salesTransactionHistoryDetailCondition
                .setViewStoreCode(salesTransactionHistoryListForm.getStoreCode());
        salesTransactionHistoryDetailCondition
                .setReceiptNo(salesTransactionHistoryListForm.getReceiptNo());
        if (StringUtils.isNotEmpty(salesTransactionHistoryListForm.getCashRegisterNo())) {
            salesTransactionHistoryDetailCondition.setCashRegisterNo(
                    Integer.valueOf(salesTransactionHistoryListForm.getCashRegisterNo()));
        }
        salesTransactionHistoryDetailCondition
                .setSalesTransactionType(salesTransactionHistoryListForm.getSalesTransactionType());
        if (StringUtils.isNotEmpty(salesTransactionHistoryListForm.getDataCreationDateFrom())) {

            LocalDateTime dataCreationDateTime = changeTimeZone(
                    parseDate(salesTransactionHistoryListForm.getDataCreationDateFrom()).atTime(0,
                            0, 0),
                    TimeZone.getTimeZone(timeZone), TimeZone.getTimeZone(ZONE_ID_UTC));
            salesTransactionHistoryDetailCondition
                    .setDataCreationDateTimeFrom(dataCreationDateTime);

        }
        if (StringUtils.isNotEmpty(salesTransactionHistoryListForm.getDataCreationDateFrom())) {
            LocalDateTime dataCreationDateTo = changeTimeZone(
                    parseDate(salesTransactionHistoryListForm.getDataCreationDateTo()).atTime(23,
                            59, 59),
                    TimeZone.getTimeZone(timeZone), TimeZone.getTimeZone(ZONE_ID_UTC));
            salesTransactionHistoryDetailCondition.setDataCreationDateTimeTo(dataCreationDateTo);
        }
        salesTransactionHistoryDetailCondition.setOrderStatusUpdateDateFrom(
                salesTransactionHistoryListForm.getBusinessDateFrom());
        salesTransactionHistoryDetailCondition
                .setOrderStatusUpdateDateTo(salesTransactionHistoryListForm.getBusinessDateTo());
        salesTransactionHistoryDetailCondition
                .setDataAlterationUserId(salesTransactionHistoryListForm.getCorrectorCode());
        if (StringUtils.isNotEmpty(salesTransactionHistoryListForm.getCorrectionDateFrom())) {
            salesTransactionHistoryDetailCondition.setUpdateDatetimeFrom(
                    parseDate(salesTransactionHistoryListForm.getCorrectionDateFrom()).atTime(0, 0,
                            0));
        }
        if (StringUtils.isNotEmpty(salesTransactionHistoryListForm.getCorrectionDateTo())) {
            salesTransactionHistoryDetailCondition.setUpdateDatetimeTo(
                    parseDate(salesTransactionHistoryListForm.getCorrectionDateTo()).atTime(0, 0,
                            0));
        }
        salesTransactionHistoryDetailCondition
                .setErrorType(salesTransactionHistoryListForm.getErrorContents());
        salesTransactionHistoryDetailCondition
                .setSalesLinkageType(SalesLinkageType.SALES_LINKAGE.getValue());
        salesTransactionHistoryDetailCondition
                .setDataAlterationStatusType(String.valueOf(DataAlterationFlag.ON.getValue()));

        return salesTransactionHistoryDetailCondition;
    }

    /**
     * Based on the specified date and time, the conversion source time zone ID, and the converted
     * time zone ID, the date and time are converted from the conversion source with the converted
     * time zone ID.
     * 
     * @param localDateTime a specified localDateTime from.
     * @param fromTimeZone a specified time zone from.
     * @param toTimeZone a specified time zone to.
     * @return {@link LocalDateTime} when success, null otherwise.
     */
    private LocalDateTime changeTimeZone(LocalDateTime localDateTime, TimeZone fromTimeZone,
            TimeZone toTimeZone) {
        ZonedDateTime orginZonedDateTime = localDateTime.atZone(fromTimeZone.toZoneId());
        return orginZonedDateTime.toInstant().atZone(toTimeZone.toZoneId()).toLocalDateTime();
    }

    /**
     * Convert the specified text to a LocalDate with the specified format pattern.
     * 
     * @param dateString specified date string.
     * @return {@link LocalDate} when success, null otherwise
     */
    private LocalDate parseDate(String dateString) {
        return LocalDate.parse(dateString, DATE_FORMAT);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SalesTransactionHistoryListForm getSortedSalesTransactionHistoryList(
            SalesTransactionHistoryListForm salesTransactionHistoryListForm) {

        ComparatorChain<SalesTransactionHistory> comparator = new ComparatorChain<>();
        if (salesTransactionHistoryListForm.getOrderByClause() == 1) {
            comparator.addComparator(new BeanComparator<>(
                    salesTransactionHistoryListForm.getSortItem(), new ReverseComparator<>()));
        } else {
            comparator.addComparator(
                    new BeanComparator<>(salesTransactionHistoryListForm.getSortItem()));
        }
        List<SalesTransactionHistory> salesTransactionHistoryList =
                salesTransactionHistoryListForm.getSalesTransactionHistoryList();
        Collections.sort(salesTransactionHistoryList, comparator);
        salesTransactionHistoryListForm.setSalesTransactionHistoryList(salesTransactionHistoryList);

        return salesTransactionHistoryListForm;
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
