/**
 * @(#)SalesBrandCountrySettingEditServiceImpl.java
 *
 *                                                  Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salesbrandcountrysettingedit.service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fastretailing.dcp.common.constants.ErrorName;
import com.fastretailing.dcp.common.exception.BusinessException;
import com.fastretailing.dcp.common.util.SystemDateTime;
import com.fastretailing.dcp.sales.common.constants.MessagePrefix;
import com.fastretailing.dcp.sales.common.entity.CommonCodeMaster;
import com.fastretailing.dcp.sales.common.entity.CommonCodeMasterCondition;
import com.fastretailing.dcp.sales.common.entity.CommonCodeMasterKey;
import com.fastretailing.dcp.sales.common.entity.PayOffSummaryMappingMaster;
import com.fastretailing.dcp.sales.common.entity.PayOffSummaryMappingMasterCondition;
import com.fastretailing.dcp.sales.common.entity.optional.BusinessCountrySettingMasterOptional;
import com.fastretailing.dcp.sales.common.entity.optional.BusinessCountrySettingMasterOptionalCondition;
import com.fastretailing.dcp.sales.common.entity.optional.BusinessCountryStateSettingMasterOptional;
import com.fastretailing.dcp.sales.common.entity.optional.BusinessCountryStateSettingMasterOptionalCondition;
import com.fastretailing.dcp.sales.common.entity.optional.TranslationStoreCodeMasterOptional;
import com.fastretailing.dcp.sales.common.entity.optional.TranslationStoreCodeMasterOptionalCondition;
import com.fastretailing.dcp.sales.common.repository.CommonCodeMasterMapper;
import com.fastretailing.dcp.sales.common.repository.PayOffSummaryMappingMasterMapper;
import com.fastretailing.dcp.sales.common.repository.optional.BusinessCountrySettingMasterOptionalMapper;
import com.fastretailing.dcp.sales.common.repository.optional.BusinessCountryStateSettingMasterOptionalMapper;
import com.fastretailing.dcp.sales.common.repository.optional.TranslationStoreCodeMasterOptionalMapper;
import com.fastretailing.dcp.sales.salesbrandcountrysettingedit.common.DisplayOrder;
import com.fastretailing.dcp.sales.salesbrandcountrysettingedit.common.ItemSetting;
import com.fastretailing.dcp.sales.salesbrandcountrysettingedit.form.Brand;
import com.fastretailing.dcp.sales.salesbrandcountrysettingedit.form.BrandCountrySettingEdit;
import com.fastretailing.dcp.sales.salesbrandcountrysettingedit.form.BrandCountrySettingEditForm;
import com.fastretailing.dcp.sales.salesbrandcountrysettingedit.form.Country;
import com.fastretailing.dcp.sales.salesbrandcountrysettingedit.form.SalesIntegrityCheck;
import com.fastretailing.dcp.sales.salesbrandcountrysettingedit.form.StoreSetting;
import com.fastretailing.dcp.sales.salesbrandcountrysettingedit.form.StoreSettingDetail;
import com.fastretailing.dcp.sales.salesbrandcountrysettingedit.form.StoreSettingDetailForm;
import com.fastretailing.dcp.sales.salesbrandcountrysettingedit.form.StoreSettingForm;
import com.fastretailing.dcp.storecommon.screen.message.LocaleMessageSource;
import com.fastretailing.dcp.storecommon.screen.util.ScreenCommonUtility;

/**
 * Sales brand country setting edit service implements.
 *
 */
@Service
public class SalesBrandCountrySettingEditServiceImpl
        implements SalesBrandCountrySettingEditService {

    /** Date time formatter(uuuu-MM-dd HH:mm:ss.SSSSSSXXXXX). */
    private static final DateTimeFormatter DATA_TIME_FORMAT =
            DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.SSSSSSXXXXX")
                    .withResolverStyle(ResolverStyle.STRICT);

    /** Business country setting master optional mapper. */
    @Autowired
    private BusinessCountrySettingMasterOptionalMapper businessCountrySettingMasterOptionalMapper;

    /** Pay off summary mapping master mapper. */
    @Autowired
    private PayOffSummaryMappingMasterMapper payOffSummaryMappingMasterMapper;

    /** Business country State setting master optional mapper. */
    @Autowired
    private BusinessCountryStateSettingMasterOptionalMapper businessCountryStateSettingMasterOptionalMapper;

    /** Common code master mapper. */
    @Autowired
    private CommonCodeMasterMapper commonCodeMasterMapper;

    /** Translation store code master optional mapper. */
    @Autowired
    private TranslationStoreCodeMasterOptionalMapper translationStoreCodeMasterMapperOptional;

    /** System brand code type id. */
    private static final String BRAND_TYPE_ID = "system_brand_code";

    /** System country code type id. */
    private static final String COUNTRY_TYPE_ID = "system_country_code";

    /** Default store header. */
    private static final String DEFAULT_STORE_HEADER = "999";

    /** Default country code. */
    private static final String DEFAULT_COUNTRY_CODE = "000";

    /** Completed status color. */
    private static final String COMPLETED_STATUS_COLOR = "0";

    /** Not completed status color. */
    private static final String NOT_COMPLETED_STATUS_COLOR = "1";

    /** Not set status color. */
    private static final String NOT_SET_STATUS_COLOR = "2";

    /** Linkage timing code level 2. */
    private static final String LINKAGE_TIMING_CODEL2 = "ORDERSTATUS";

    /** Sales integrity check code level 2. */
    private static final String SALES_INTEGRITY_CHECK_CODEL2 = "ITEMSALES";

    /** Sales integrity check type code level 2. */
    private static final String SALES_INTEGRITY_CHECK_TYPE_CODEL2 = "TARGETTYPE";

    /** Complete count. */
    private static final int COMPLETE_COUNT = 8;

    /** Not set count. */
    private static final int NOT_SET_COUNT = 0;

    /** Variable type. */
    private static final String VARIABLE_TYPE = "0";

    /** Add code value. */
    private static final String ADD_CODE_VALUE = "0";

    /** Mapping pattern. */
    private static final int MAPPING_PATTERN = 1;

    /** Program id for this page. */
    private static final String PROGRAM_ID = "SLS1400101";

    /** Local message source. */
    @Autowired
    private LocaleMessageSource localeMessageSource;

    /** UTC clock component. */
    @Autowired
    private SystemDateTime systemDateTime;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<StoreSetting> getStoreSettingList(StoreSettingForm storeSettingForm) {

        List<String> storeList = getStatusList(storeSettingForm);
        List<StoreSetting> storeSettingList = new ArrayList<>();
        String completeMessage =
                localeMessageSource.getMessage("brand-country-setting-edit.completedStatus");
        String notSetMessage =
                localeMessageSource.getMessage("brand-country-setting-edit.notSetStatus");

        for (ItemSetting str : ItemSetting.values()) {
            StoreSetting store = new StoreSetting();
            store.setSettingItem(str.getValue());
            if (storeList.contains(str.getValue())) {
                store.setStatus(completeMessage);
                store.setStatusColor(COMPLETED_STATUS_COLOR);
            } else {
                store.setStatus(notSetMessage);
                store.setStatusColor(NOT_COMPLETED_STATUS_COLOR);
            }
            storeSettingList.add(store);
        }
        return storeSettingList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int countStoreSettingList(StoreSettingForm storeSettingForm) {

        return getStatusList(storeSettingForm).size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int deleteStoreSettingList(StoreSettingForm storeSettingForm) {
        BusinessCountryStateSettingMasterOptionalCondition condition =
                new BusinessCountryStateSettingMasterOptionalCondition();
        condition.createCriteria()
                .andStoreCodeEqualTo(storeSettingForm.getStoreCode())
                .andSystemBrandCodeEqualTo(storeSettingForm.getStoreBrandCode())
                .andSystemCountryCodeEqualTo(storeSettingForm.getStoreCountryCode());
        return businessCountryStateSettingMasterOptionalMapper.deleteByCondition(condition);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBrandAndCountry(String code, String typeId) {

        CommonCodeMasterKey commonCodeMasterKey = new CommonCodeMasterKey();
        commonCodeMasterKey.setTypeId(typeId);
        commonCodeMasterKey.setTypeValue(code);

        CommonCodeMaster commonCodeMaster =
                commonCodeMasterMapper.selectByPrimaryKey(commonCodeMasterKey);
        if (commonCodeMaster == null) {
            throwBusinessException(MessagePrefix.E_SLS_66000128_NO_COMMON_CODE);
        }
        return commonCodeMaster.getName1();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Brand> getBrandList() {
        List<CommonCodeMaster> list = getBrandAndCountryList(BRAND_TYPE_ID);
        if (CollectionUtils.isEmpty(list)) {
            throwBusinessException(MessagePrefix.E_SLS_66000128_NO_COMMON_CODE);
        }
        return list.stream().map(commonCodemaster -> {
            Brand brand = new Brand();
            brand.setBrand(commonCodemaster.getTypeValue());
            brand.setBrandName(commonCodemaster.getName1());
            return brand;
        }).collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Country> getCountryList() {
        List<CommonCodeMaster> list = getBrandAndCountryList(COUNTRY_TYPE_ID);
        if (CollectionUtils.isEmpty(list)) {
            throwBusinessException(MessagePrefix.E_SLS_66000128_NO_COMMON_CODE);
        }
        return list.stream().map(commonCodemaster -> {
            Country country = new Country();
            country.setCountry(commonCodemaster.getTypeValue());
            country.setCountryName(commonCodemaster.getName1());
            return country;
        }).collect(Collectors.toList());
    }


    /**
     * get common code master list.
     * 
     * @param typeId Type id.
     * @return Common code master list.
     */
    private List<CommonCodeMaster> getBrandAndCountryList(String typeId) {

        CommonCodeMasterCondition condition = new CommonCodeMasterCondition();
        condition.createCriteria().andTypeIdEqualTo(typeId);
        List<CommonCodeMaster> list = commonCodeMasterMapper.selectByCondition(condition);
        if (CollectionUtils.isEmpty(list)) {
            throwBusinessException(MessagePrefix.E_SLS_66000128_NO_COMMON_CODE);
        }
        return list;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<SalesIntegrityCheck> getSalesIntegrityCheckList(String typeId) {
        List<CommonCodeMaster> commonList = getBrandAndCountryList(typeId);
        if (CollectionUtils.isEmpty(commonList)) {
            throwBusinessException(MessagePrefix.E_SLS_66000128_NO_COMMON_CODE);
        }
        return commonList.stream().map(commonCodeMaster -> {
            SalesIntegrityCheck salesIntegrityCheck = new SalesIntegrityCheck();
            salesIntegrityCheck.setCodeL2(commonCodeMaster.getName1());
            salesIntegrityCheck.setCodeL3(commonCodeMaster.getTypeValue());
            return salesIntegrityCheck;
        }).collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TranslationStoreCodeMasterOptional getStoreInformation(String storeCode) {
        TranslationStoreCodeMasterOptional translationStoreCodeMaster =
                translationStoreCodeMasterMapperOptional.selectByPrimaryKey(storeCode);
        if (translationStoreCodeMaster == null) {
            throwBusinessException(MessagePrefix.E_SLS_66000131_NO_TRANSLATION_STORE_CODE);
        }
        return translationStoreCodeMaster;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BrandCountrySettingEdit> getBrandCountrySettingEditList(
            BrandCountrySettingEditForm brandCountrySettingEditForm) {
        BusinessCountrySettingMasterOptionalCondition condition =
                createBusinessCondition(brandCountrySettingEditForm);
        List<BusinessCountrySettingMasterOptional> brandCountrySettingMasterList =
                businessCountrySettingMasterOptionalMapper.selectStoreList(condition);
        if (CollectionUtils.isEmpty(brandCountrySettingMasterList)) {
            throwBusinessException(MessagePrefix.E_SLS_66000162_NO_BUSINESS_COUNTRY_STATE_SETTING);
        }

        return brandCountrySettingMasterList.stream().map(businessCountrySettingMasterOptional -> {

            BrandCountrySettingEdit brandCountrySettingEdit = new BrandCountrySettingEdit();
            String brandCode = businessCountrySettingMasterOptional.getSystemBrandCode();
            brandCountrySettingEdit.setBrand(brandCode);

            String brandName = getBrandAndCountry(brandCode, BRAND_TYPE_ID);
            brandCountrySettingEdit.setBrandName(brandName);

            String countryCode = businessCountrySettingMasterOptional.getSystemCountryCode();
            brandCountrySettingEdit.setCountry(countryCode);

            String detailStoreCode = businessCountrySettingMasterOptional.getStoreCode();
            brandCountrySettingEdit.setStore(detailStoreCode);
            brandCountrySettingEdit
                    .setBusinessCode(businessCountrySettingMasterOptional.getSystemBusinessCode());
            brandCountrySettingEdit
                    .setStateCode(businessCountrySettingMasterOptional.getStateCode());
            if (detailStoreCode.startsWith(DEFAULT_STORE_HEADER)) {
                if (countryCode.equals(DEFAULT_COUNTRY_CODE)) {
                    brandCountrySettingEdit.setCountryName(null);
                } else {
                    String countryName = getBrandAndCountry(countryCode, COUNTRY_TYPE_ID);
                    brandCountrySettingEdit.setCountryName(countryName);
                }
                brandCountrySettingEdit.setViewStoreCode(null);
                brandCountrySettingEdit.setStoreName(localeMessageSource
                        .getMessage("brand-country-setting-edit.defaultStoreName"));
            } else {
                String countryName = getBrandAndCountry(countryCode, COUNTRY_TYPE_ID);
                brandCountrySettingEdit.setCountryName(countryName);
                brandCountrySettingEdit
                        .setViewStoreCode(businessCountrySettingMasterOptional.getViewStoreCode());
                brandCountrySettingEdit
                        .setStoreName(businessCountrySettingMasterOptional.getStoreName());
            }
            int count =
                    businessCountrySettingMasterOptionalMapper.countByCondition(detailStoreCode);
            if (count == NOT_SET_COUNT) {
                brandCountrySettingEdit.setStatus(
                        localeMessageSource.getMessage("brand-country-setting-edit.notSetStatus"));
                brandCountrySettingEdit.setStatusColor(NOT_SET_STATUS_COLOR);
            } else if (count == COMPLETE_COUNT) {
                brandCountrySettingEdit.setStatus(localeMessageSource
                        .getMessage("brand-country-setting-edit.completedStatus"));
                brandCountrySettingEdit.setStatusColor(COMPLETED_STATUS_COLOR);
            } else {
                brandCountrySettingEdit.setStatus(localeMessageSource
                        .getMessage("brand-country-setting-edit.notCompletedStatus"));
                brandCountrySettingEdit.setStatusColor(NOT_COMPLETED_STATUS_COLOR);
            }
            return brandCountrySettingEdit;
        }).collect(Collectors.toList());
    }

    /**
     * Get business country setting master condition.
     * 
     * @param brandCountrySettingEditForm Form for the brand country setting edit page.
     * @return Business country setting master condition.
     */
    private BusinessCountrySettingMasterOptionalCondition createBusinessCondition(
            BrandCountrySettingEditForm brandCountrySettingEditForm) {
        BusinessCountrySettingMasterOptionalCondition condition =
                new BusinessCountrySettingMasterOptionalCondition();
        if (brandCountrySettingEditForm.getViewStoreCode() != null
                && brandCountrySettingEditForm.getViewStoreCode() != "") {
            String viewStoreCode = brandCountrySettingEditForm.getViewStoreCode();
            TranslationStoreCodeMasterOptionalCondition transCondition =
                    new TranslationStoreCodeMasterOptionalCondition();
            transCondition.createCriteria().andViewStoreCodeEqualTo(viewStoreCode);
            List<TranslationStoreCodeMasterOptional> transList =
                    translationStoreCodeMasterMapperOptional.selectByCondition(transCondition);
            if (CollectionUtils.isEmpty(transList)) {
                throwBusinessException(MessagePrefix.E_SLS_66000131_NO_TRANSLATION_STORE_CODE);
            }
            String storeCode = transList.get(0).getStoreCode();
            condition.setStoreCode(storeCode);
        }
        condition.setSystemBrandCode(brandCountrySettingEditForm.getBrand());
        condition.setSystemCountryCode(brandCountrySettingEditForm.getCountry());
        return condition;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int addBrandCountrySettinglist(BrandCountrySettingEditForm brandCountrySettingEditForm) {
        BusinessCountrySettingMasterOptionalCondition condition =
                new BusinessCountrySettingMasterOptionalCondition();
        condition.setStoreCode(brandCountrySettingEditForm.getStoreCode());
        condition.setSystemBrandCode(brandCountrySettingEditForm.getStoreBrandCode());
        condition.setSystemCountryCode(brandCountrySettingEditForm.getStoreCountryCode());
        List<BusinessCountrySettingMasterOptional> list =
                businessCountrySettingMasterOptionalMapper.selectStoreCodeL1(condition);
        int count = 0;
        for (BusinessCountrySettingMasterOptional businessCountrySettingMasterOptional : list) {
            BusinessCountryStateSettingMasterOptional businessCountryStateSettingMasterOptional =
                    new BusinessCountryStateSettingMasterOptional();
            businessCountryStateSettingMasterOptional
                    .setSystemBrandCode(brandCountrySettingEditForm.getStoreBrandCode());
            businessCountryStateSettingMasterOptional
                    .setSystemBusinessCode(brandCountrySettingEditForm.getBusinessCode());
            businessCountryStateSettingMasterOptional
                    .setSystemCountryCode(brandCountrySettingEditForm.getStoreCountryCode());
            businessCountryStateSettingMasterOptional
                    .setStateCode(brandCountrySettingEditForm.getStateCode());
            businessCountryStateSettingMasterOptional
                    .setStoreCode(brandCountrySettingEditForm.getStoreCode());
            businessCountryStateSettingMasterOptional
                    .setCodeL1(businessCountrySettingMasterOptional.getCodeL1());
            businessCountryStateSettingMasterOptional
                    .setCodeL2(businessCountrySettingMasterOptional.getCodeL2());
            businessCountryStateSettingMasterOptional
                    .setCodeL3(businessCountrySettingMasterOptional.getCodeL3());
            businessCountryStateSettingMasterOptional
                    .setCodeValue(businessCountrySettingMasterOptional.getCodeValue());
            businessCountryStateSettingMasterOptional
                    .setVariableType(businessCountrySettingMasterOptional.getVariableType());
            businessCountryStateSettingMasterOptional
                    .setDisplayOrder(businessCountrySettingMasterOptional.getDisplayOrder());
            businessCountryStateSettingMasterOptional
                    .setCreateUserId(brandCountrySettingEditForm.getLoginUserId());
            businessCountryStateSettingMasterOptional
                    .setUpdateUserId(brandCountrySettingEditForm.getLoginUserId());
            businessCountryStateSettingMasterOptional.setCreateProgramId(PROGRAM_ID);
            businessCountryStateSettingMasterOptional.setUpdateProgramId(PROGRAM_ID);
            OffsetDateTime createDatetime = systemDateTime.now();
            businessCountryStateSettingMasterOptional.setCreateDatetime(createDatetime);
            businessCountryStateSettingMasterOptional.setUpdateDatetime(createDatetime);
            count += businessCountryStateSettingMasterOptionalMapper
                    .insert(businessCountryStateSettingMasterOptional);
        }
        return count;
    }

    /**
     * Get codeL1 list.
     * 
     * @param storeSettingForm Form for the store setting page.
     * @return CodeL1 list.
     */
    private List<String> getStatusList(StoreSettingForm storeSettingForm) {
        BusinessCountrySettingMasterOptionalCondition condition =
                new BusinessCountrySettingMasterOptionalCondition();
        condition.setStoreCode(storeSettingForm.getStoreCode());
        condition.setSystemBrandCode(storeSettingForm.getStoreBrandCode());
        condition.setSystemCountryCode(storeSettingForm.getStoreCountryCode());
        List<String> storeSettingList =
                businessCountrySettingMasterOptionalMapper.selectCodeL1List(condition);

        if (CollectionUtils.isEmpty(storeSettingList)) {
            throwBusinessException(MessagePrefix.E_SLS_66000162_NO_BUSINESS_COUNTRY_STATE_SETTING);
        }
        return storeSettingList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int countBusinessSettingDetailList(StoreSettingDetailForm storeSettingDetailForm) {

        BusinessCountryStateSettingMasterOptionalCondition condition =
                new BusinessCountryStateSettingMasterOptionalCondition();
        condition.createCriteria()
                .andSystemBrandCodeEqualTo(storeSettingDetailForm.getStoreBrandCode())
                .andSystemCountryCodeEqualTo(storeSettingDetailForm.getStoreCountryCode())
                .andStoreCodeEqualTo(storeSettingDetailForm.getStoreCode())
                .andCodeL1EqualTo(storeSettingDetailForm.getCodeL1());

        return (int) businessCountryStateSettingMasterOptionalMapper.countByCondition(condition);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<StoreSettingDetail> getDetailDefaultList(
            StoreSettingDetailForm storeSettingDetailForm) {

        BusinessCountryStateSettingMasterOptionalCondition condition;
        if (storeSettingDetailForm.getStoreCountryCode().equals(DEFAULT_COUNTRY_CODE)) {
            String brandDefaultStoreCode = DEFAULT_STORE_HEADER
                    + storeSettingDetailForm.getBrandCode() + DEFAULT_COUNTRY_CODE;
            condition =
                    setCondition(storeSettingDetailForm.getStoreBrandCode(), DEFAULT_COUNTRY_CODE,
                            brandDefaultStoreCode, storeSettingDetailForm.getCodeL1());
        } else {
            String defaultStoreCode =
                    DEFAULT_STORE_HEADER + storeSettingDetailForm.getStoreBrandCode()
                            + storeSettingDetailForm.getStoreCountryCode();
            condition = setCondition(storeSettingDetailForm.getStoreBrandCode(),
                    storeSettingDetailForm.getStoreCountryCode(), defaultStoreCode,
                    storeSettingDetailForm.getCodeL1());
        }
        return getDetailCommonList(condition);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<StoreSettingDetail> getDetailList(StoreSettingDetailForm storeSettingDetailForm) {
        BusinessCountryStateSettingMasterOptionalCondition condition =
                setCondition(storeSettingDetailForm.getStoreBrandCode(),
                        storeSettingDetailForm.getStoreCountryCode(),
                        storeSettingDetailForm.getStoreCode(), storeSettingDetailForm.getCodeL1());
        return getDetailCommonList(condition);
    }

    /**
     * Get business country state setting master optional condition.
     * 
     * @param brandCode System brand code.
     * @param countryCode System country code.
     * @param storeCode Store code.
     * @param codeL1 CodeL1.
     * @return Business country state setting master optional condition.
     */
    private BusinessCountryStateSettingMasterOptionalCondition setCondition(String brandCode,
            String countryCode, String storeCode, String codeL1) {
        BusinessCountryStateSettingMasterOptionalCondition condition =
                new BusinessCountryStateSettingMasterOptionalCondition();
        condition.createCriteria()
                .andSystemBrandCodeEqualTo(brandCode)
                .andSystemCountryCodeEqualTo(countryCode)
                .andStoreCodeEqualTo(storeCode)
                .andCodeL1EqualTo(codeL1);
        return condition;

    }

    /**
     * Get store setting detail list.
     * 
     * @param condition Business country state setting master optional condition.
     * @return Store setting detail list.
     */
    private List<StoreSettingDetail> getDetailCommonList(
            BusinessCountryStateSettingMasterOptionalCondition condition) {
        List<BusinessCountryStateSettingMasterOptional> storeDetailList =
                businessCountryStateSettingMasterOptionalMapper.selectByCondition(condition);
        if (CollectionUtils.isEmpty(storeDetailList)) {
            throwBusinessException(MessagePrefix.E_SLS_66000162_NO_BUSINESS_COUNTRY_STATE_SETTING);
        }
        return storeDetailList.stream().map(businessCountryStateSettingMasterOptional -> {
            StoreSettingDetail storeSettingDetail = new StoreSettingDetail();
            storeSettingDetail.setCodeL1(businessCountryStateSettingMasterOptional.getCodeL1());
            storeSettingDetail.setCodeL2(businessCountryStateSettingMasterOptional.getCodeL2());
            storeSettingDetail.setCodeL3(businessCountryStateSettingMasterOptional.getCodeL3());
            storeSettingDetail
                    .setCodeValue(businessCountryStateSettingMasterOptional.getCodeValue());
            storeSettingDetail
                    .setVariableType(businessCountryStateSettingMasterOptional.getVariableType());
            storeSettingDetail
                    .setUpdateDatetime(businessCountryStateSettingMasterOptional.getUpdateDatetime()
                            .format(DATA_TIME_FORMAT));
            return storeSettingDetail;
        }).collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getOrderStatusList(String typeCode) {
        CommonCodeMasterCondition condition = new CommonCodeMasterCondition();
        condition.createCriteria().andTypeIdEqualTo(typeCode);
        List<CommonCodeMaster> orderStatusList =
                commonCodeMasterMapper.selectByCondition(condition);
        if (CollectionUtils.isEmpty(orderStatusList)) {
            throwBusinessException(MessagePrefix.E_SLS_66000128_NO_COMMON_CODE);
        }
        return orderStatusList.stream()
                .map(commonCodeMasterMapper -> commonCodeMasterMapper.getTypeValue())
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMappingPatternName() {
        PayOffSummaryMappingMasterCondition condition = new PayOffSummaryMappingMasterCondition();
        condition.createCriteria().andMappingPatternEqualTo(MAPPING_PATTERN);
        List<PayOffSummaryMappingMaster> payOffList =
                payOffSummaryMappingMasterMapper.selectByCondition(condition);
        if (CollectionUtils.isEmpty(payOffList)) {
            throwBusinessException(MessagePrefix.E_SLS_66000163_NO_PAY_OFF_SUMMARY_MAPPING__EXIT);
        }
        return payOffList.get(0).getMappingPtnName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int insertDefaultDetail(StoreSettingDetailForm storeSettingDetailForm) {
        int count = 0;
        List<StoreSettingDetail> list = storeSettingDetailForm.getBrandCountrySettingEditList();
        for (StoreSettingDetail storeSettingDetail : list) {
            BusinessCountryStateSettingMasterOptional businessMaster =
                    new BusinessCountryStateSettingMasterOptional();
            businessMaster.setSystemBrandCode(storeSettingDetailForm.getStoreBrandCode());
            businessMaster.setSystemCountryCode(storeSettingDetailForm.getStoreCountryCode());
            businessMaster.setSystemBusinessCode(storeSettingDetailForm.getBusinessCode());
            businessMaster.setStoreCode(storeSettingDetailForm.getStoreCode());
            businessMaster.setStateCode(storeSettingDetailForm.getStateCode());
            businessMaster.setCodeL1(storeSettingDetail.getCodeL1());
            businessMaster.setCodeL2(storeSettingDetail.getCodeL2());
            businessMaster.setCodeL3(storeSettingDetail.getCodeL3());
            businessMaster.setCodeValue(storeSettingDetail.getCodeValue());
            if (storeSettingDetailForm.getCodeL1().equals(ItemSetting.LINKAGE_TIMING.getValue())) {
                businessMaster.setDisplayOrder(DisplayOrder.DISPLAY_ORDER_ONE.getValue());
            } else if (storeSettingDetailForm.getCodeL1()
                    .equals(ItemSetting.SALES_REPORT.getValue())) {
                businessMaster.setDisplayOrder(DisplayOrder.DISPLAY_ORDER_TWO.getValue());
            } else if (storeSettingDetailForm.getCodeL1()
                    .equals(ItemSetting.PAY_OFF_DATA.getValue())) {
                businessMaster.setDisplayOrder(DisplayOrder.DISPLAY_ORDER_THREE.getValue());
            } else if (storeSettingDetailForm.getCodeL1()
                    .equals(ItemSetting.BUSINESS_DATE_CHECK.getValue())) {
                businessMaster.setDisplayOrder(DisplayOrder.DISPLAY_ORDER_FOUR.getValue());
            } else if (storeSettingDetailForm.getCodeL1().equals(ItemSetting.TAX_TYPE.getValue())) {
                businessMaster.setDisplayOrder(DisplayOrder.DISPLAY_ORDER_FIVE.getValue());
            } else if (storeSettingDetailForm.getCodeL1()
                    .equals(ItemSetting.SALES_INTEGRITY_CHECK.getValue())) {
                businessMaster.setDisplayOrder(DisplayOrder.DISPLAY_ORDER_SIX.getValue());
            } else if (storeSettingDetailForm.getCodeL1()
                    .equals(ItemSetting.SALES_INTEGRITY_CHECK_TYPE.getValue())) {
                businessMaster.setDisplayOrder(DisplayOrder.DISPLAY_ORDER_SEVEN.getValue());
            } else if (storeSettingDetailForm.getCodeL1().equals(ItemSetting.DECIMAL.getValue())) {
                businessMaster.setDisplayOrder(DisplayOrder.DISPLAY_ORDER_EIGHT.getValue());
            }
            businessMaster.setVariableType(VARIABLE_TYPE);
            businessMaster.setCreateUserId(storeSettingDetailForm.getLoginUserId());
            businessMaster.setUpdateUserId(storeSettingDetailForm.getLoginUserId());
            OffsetDateTime createDatetime = systemDateTime.now();
            businessMaster.setCreateDatetime(createDatetime);
            businessMaster.setUpdateDatetime(createDatetime);
            businessMaster.setCreateProgramId(PROGRAM_ID);
            businessMaster.setUpdateProgramId(PROGRAM_ID);
            count += businessCountryStateSettingMasterOptionalMapper.insert(businessMaster);
        }
        return count;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int updateDetail(StoreSettingDetailForm storeSettingDetailForm) {
        int count = 0;
        List<StoreSettingDetail> list = storeSettingDetailForm.getBrandCountrySettingEditList();
        for (int i = 0; i < list.size(); i++) {
            StoreSettingDetail storeSettingDetail = list.get(i);
            if (StringUtils.isNotBlank(storeSettingDetail.getUpdateDatetime())) {
                BusinessCountryStateSettingMasterOptionalCondition condition =
                        new BusinessCountryStateSettingMasterOptionalCondition();
                condition.createCriteria()
                        .andSystemBrandCodeEqualTo(storeSettingDetailForm.getStoreBrandCode())
                        .andSystemCountryCodeEqualTo(storeSettingDetailForm.getStoreCountryCode())
                        .andStoreCodeEqualTo(storeSettingDetailForm.getStoreCode())
                        .andVariableTypeEqualTo(VARIABLE_TYPE)
                        .andCodeL1EqualTo(storeSettingDetail.getCodeL1())
                        .andCodeL2EqualTo(storeSettingDetail.getCodeL2())
                        .andCodeL3EqualTo(storeSettingDetail.getCodeL3())
                        .andUpdateDatetimeEqualTo(OffsetDateTime
                                .parse(storeSettingDetail.getUpdateDatetime(), DATA_TIME_FORMAT));
                BusinessCountryStateSettingMasterOptional businessMaster =
                        new BusinessCountryStateSettingMasterOptional();
                businessMaster.setCodeL3(storeSettingDetail.getCodeL3());
                businessMaster.setCodeValue(storeSettingDetail.getCodeValue());
                businessMaster.setUpdateProgramId(PROGRAM_ID);
                businessMaster.setUpdateUserId(storeSettingDetailForm.getLoginUserId());
                businessMaster.setUpdateDatetime(systemDateTime.now());
                count += businessCountryStateSettingMasterOptionalMapper
                        .updateByConditionSelective(businessMaster, condition);
            }
        }
        return count;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int insertAddDetail(StoreSettingDetailForm storeSettingDetailForm) {
        int count = 0;
        List<StoreSettingDetail> list = storeSettingDetailForm.getBrandCountrySettingEditList();
        for (int i = 0; i < list.size(); i++) {
            StoreSettingDetail storeSettingDetail = list.get(i);
            if (StringUtils.isEmpty(storeSettingDetail.getUpdateDatetime())) {
                BusinessCountryStateSettingMasterOptional businessMaster =
                        new BusinessCountryStateSettingMasterOptional();
                businessMaster.setSystemBrandCode(storeSettingDetailForm.getStoreBrandCode());
                businessMaster.setSystemCountryCode(storeSettingDetailForm.getStoreCountryCode());
                businessMaster.setSystemBusinessCode(storeSettingDetailForm.getBusinessCode());
                businessMaster.setStoreCode(storeSettingDetailForm.getStoreCode());
                businessMaster.setStateCode(storeSettingDetailForm.getStateCode());
                businessMaster.setCodeL1(storeSettingDetail.getCodeL1());
                businessMaster.setCodeL3(storeSettingDetail.getCodeL3());
                businessMaster.setCodeValue(ADD_CODE_VALUE);
                if (storeSettingDetailForm.getCodeL1()
                        .equals(ItemSetting.LINKAGE_TIMING.getValue())) {
                    businessMaster.setCodeL2(LINKAGE_TIMING_CODEL2);
                    businessMaster.setDisplayOrder(DisplayOrder.DISPLAY_ORDER_ONE.getValue());
                } else if (storeSettingDetailForm.getCodeL1()
                        .equals(ItemSetting.SALES_INTEGRITY_CHECK.getValue())) {
                    businessMaster.setCodeL2(SALES_INTEGRITY_CHECK_CODEL2);
                    businessMaster.setDisplayOrder(DisplayOrder.DISPLAY_ORDER_SIX.getValue());
                } else if (storeSettingDetailForm.getCodeL1()
                        .equals(ItemSetting.SALES_INTEGRITY_CHECK_TYPE.getValue())) {
                    businessMaster.setCodeL2(SALES_INTEGRITY_CHECK_TYPE_CODEL2);
                    businessMaster.setDisplayOrder(DisplayOrder.DISPLAY_ORDER_SEVEN.getValue());
                }
                businessMaster.setVariableType(VARIABLE_TYPE);
                businessMaster.setCreateUserId(storeSettingDetailForm.getLoginUserId());
                businessMaster.setUpdateUserId(storeSettingDetailForm.getLoginUserId());
                LocalDateTime dateTime = LocalDateTime.now();
                OffsetDateTime createDatetime = OffsetDateTime.of(dateTime, ZoneOffset.UTC);
                businessMaster.setCreateDatetime(createDatetime);
                businessMaster.setUpdateDatetime(createDatetime);
                businessMaster.setCreateProgramId(PROGRAM_ID);
                businessMaster.setUpdateProgramId(PROGRAM_ID);
                count += businessCountryStateSettingMasterOptionalMapper.insert(businessMaster);
            }
        }
        return count;

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
