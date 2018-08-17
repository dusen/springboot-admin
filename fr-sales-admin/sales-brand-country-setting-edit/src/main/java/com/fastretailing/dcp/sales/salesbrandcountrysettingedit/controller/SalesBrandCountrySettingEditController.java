/**
 * @(#)SalesBrandCountrySettingEditController.java
 *
 *                                                 Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salesbrandcountrysettingedit.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fastretailing.dcp.common.exception.SystemException;
import com.fastretailing.dcp.sales.common.constants.MessagePrefix;
import com.fastretailing.dcp.sales.common.entity.optional.TranslationStoreCodeMasterOptional;
import com.fastretailing.dcp.sales.salesbrandcountrysettingedit.common.DisplayOrder;
import com.fastretailing.dcp.sales.salesbrandcountrysettingedit.common.ItemSetting;
import com.fastretailing.dcp.sales.salesbrandcountrysettingedit.form.Brand;
import com.fastretailing.dcp.sales.salesbrandcountrysettingedit.form.BrandCountrySettingEdit;
import com.fastretailing.dcp.sales.salesbrandcountrysettingedit.form.BrandCountrySettingEditForm;
import com.fastretailing.dcp.sales.salesbrandcountrysettingedit.form.Country;
import com.fastretailing.dcp.sales.salesbrandcountrysettingedit.form.SalesIntegrityCheck;
import com.fastretailing.dcp.sales.salesbrandcountrysettingedit.form.StoreSetting;
import com.fastretailing.dcp.sales.salesbrandcountrysettingedit.form.StoreSettingDetail;
import com.fastretailing.dcp.sales.salesbrandcountrysettingedit.form.StoreSettingDetailAddForm;
import com.fastretailing.dcp.sales.salesbrandcountrysettingedit.form.StoreSettingDetailForm;
import com.fastretailing.dcp.sales.salesbrandcountrysettingedit.form.StoreSettingForm;
import com.fastretailing.dcp.sales.salesbrandcountrysettingedit.service.SalesBrandCountrySettingEditService;
import com.fastretailing.dcp.storecommon.screen.exception.ScreenException;
import com.fastretailing.dcp.storecommon.screen.form.CommonBaseForm;
import com.fastretailing.dcp.storecommon.screen.form.DetailError;
import com.fastretailing.dcp.storecommon.screen.form.FormBuilder;
import com.fastretailing.dcp.storecommon.screen.message.LocaleMessageSource;
import com.fastretailing.dcp.storecommon.screen.message.MessageType;

/**
 * The controller class for brand country setting edit page.
 */
@Controller
@RequestMapping(path = "/{brand}/{region}/screen/brand-country-setting-edit")
public class SalesBrandCountrySettingEditController {

    /** Brand country setting edit page template. */
    private static final String BRAND_COUNTRY_SETTING_EDIT_PAGE = "brand-country-setting-edit";

    /** Brand country setting edit child page template. */
    private static final String BRAND_COUNTRY_SETTING_EDIT_CHILD_PAGE =
            "brand-country-setting-edit-ajaxData";

    /** Store setting list page template. */
    private static final String STORE_SETTING_LIST_PAGE = "store-setting-list";

    /** Store setting detail linkage timing page template. */
    private static final String STORE_SETTING_DETAIL_LINKAGE_PAGE =
            "store-setting-detail-linkage-timing";

    /** Store setting detail sales report page template. */
    private static final String STORE_SETTING_DETAIL_SALES_REPORT_PAGE =
            "store-setting-detail-sales-report";

    /** Store setting detail pay off page template. */
    private static final String STORE_SETTING_DETAIL_PAY_OFF_PAGE = "store-setting-detail-pay-off";

    /** Store setting detail business date page template. */
    private static final String STORE_SETTING_DETAIL_BUSINESS_DATE_PAGE =
            "store-setting-detail-business-date";

    /** Store setting detail tax type page template. */
    private static final String STORE_SETTING_DETAIL_TAX_TYPE_PAGE =
            "store-setting-detail-tax-type";

    /** Store setting detail sales integrity check page page template. */
    private static final String STORE_SETTING_DETAIL_SALES_INTEGRITY_CHECK_PAGE =
            "store-setting-detail-sales-integrity-check";

    /** Store setting detail sales integrity check type page template. */
    private static final String STORE_SETTING_DETAIL_SALES_INTEGRITY_CHECK_TYPE_PAGE =
            "store-setting-detail-sales-integrity-check-type";

    /** Store setting detail decimal page template. */
    private static final String STORE_SETTING_DETAIL_DEMICAL_PAGE = "store-setting-detail-decimal";

    /** Store setting detail linkage setting timing add pop up page template. */
    private static final String STORE_SETTING_DETAIL_LINKAGE_TIMING_PAGE =
            "store-setting-detail-linkage-timing-add-popup";

    /** Store setting detail linkage setting timing add pop up page template. */
    private static final String STORE_SETTING_DETAIL_SALES_INTEGRITY_CHECK_POPUP_PAGE =
            "store-setting-detail-sales-integrity-check-add-popup";

    /** Store setting detail sales integrity check type add pop up page template. */
    private static final String STORE_SETTING_DETAIL_SALES_INTEGRITY_CHECK_TYPE_POPUP_PAGE =
            "store-setting-detail-sales-integrity-check-type-add-popup";

    /** Store setting form. */
    private static final String STORE_SETTING_FORM = "storeSettingForm";

    /** Brand country setting edit form. */
    private static final String BRAND_COUNTRY_SETTING_EDIT_FORM = "brandCountrySettingEditForm";

    /** Store setting detail form. */
    private static final String STORE_SETTING_DETAIL_FORM = "storeSettingDetailForm";

    /** Store setting detail add form. */
    private static final String STORE_SETTING_DETAIL_ADD_FORM = "storeSettingDetailAddForm";

    /** Brand list page template. */
    private static final String BRAND_LIST = "brandList";

    /** Country list page template. */
    private static final String COUNTRY_LIST = "countryList";

    /** Order status list page template. */
    private static final String ORDER_STATUS_LIST = "orderStatusList";

    /** Output list page template. */
    private static final String OUTPUT_LIST = "outputList";

    /** Sign column list page template. */
    private static final String SIGN_COLUMN_LIST = "signColumnList";

    /** Rtlog list page template. */
    private static final String RTLOG_LIST = "rtlogList";

    /** Pay off list page template. */
    private static final String PAY_OFF_LIST = "payOffList";

    /** Quantity list page template. */
    private static final String QUANTITY_LIST = "quantityList";

    /** Code l2. */
    private static final String CODE_L2 = "codeL2";

    /** Code l2 order status. */
    private static final String CODE_L2_ORDER_STATUS = "ORDERSTATUS";

    /** Code l2 target type. */
    private static final String CODE_L2_TARGET_TYPE = "TARGETTYPE";

    /** Mapping pattern name. */
    private static final String MAPPING_PATTERN_NAME = "mappingPatternName";

    /** Linkage unit. */
    private static final String LINKAGE_UNIT = "LINKAGEUNIT";

    /** Sales linkage unit. */
    private static final String SALES_LINKAGE_UNIT = "SALESLINKAGEUNIT";

    /**
     * System brand code type id.
     */
    private static final String BRAND_TYPE_ID = "system_brand_code";

    /**
     * Temporary data.
     */
    private static final String TEMPORARY_DATA = "temporaryData";

    /**
     * Default store header.
     */
    private static final String DEFAULT_STORE_HEADER = "999";

    /**
     * Default country code.
     */
    private static final String DEFAULT_COUNTRY_CODE = "000";

    /**
     * Add color value.
     */
    private static final int ADD_COLOR = 1;

    /**
     * System country code type id.
     */
    private static final String COUNTRY_TYPE_ID = "system_country_code";

    /**
     * Order status type id.
     */
    private static final String ORDER_STATUS_TYPE_ID = "ORDERSTATUS";

    /**
     * Output type id.
     */
    private static final String OUTPUT_TYPE_ID = "Sign_Column_printing_on_Sales_Report";

    /**
     * Pay off type id.
     */
    private static final String PAY_OFF_TYPE_ID = "PAY_OFF_CODE";

    /**
     * Rtlog type id.
     */
    private static final String RTLOG_TYPE_ID = "RTLOG";

    /**
     * Mapping sub patternL3 type id.
     */
    private static final String MAPPING_SUB_PATTERN_TYPE_ID = "MAPPING_SUB_PATTERN_L3";

    /**
     * Quantity type id.
     */
    private static final String QUANTITY_TYPE_ID = "quantity_code";

    /** Code l2 date exceeding. */
    private static final String CODE_L2_DATE_EXCEEDING = "DATEEXCEEDING";
    /** Code l2 before store opening. */
    private static final String CODE_L2_BEFORE_STORE_OPENING = "BEFORESTOREOPENING";
    /** Code l2 after store closing. */
    private static final String CODE_L2_AFTER_STORE_CLOSING = "AFTERSTORECLOSING";
    /** Code l2 closed business date. */
    private static final String CODE_L2_CLOSED_BUSINESS_DATE = "CLOSEDBUSINESSDATE";

    @Autowired
    private SalesBrandCountrySettingEditService brandCountrySettingEditService;

    /** Local message source. */
    @Autowired
    private LocaleMessageSource localeMessageSource;

    /**
     * Get brand country edit initialize page.
     * 
     * @param commonForm Common base form.
     * @param model The model attribute for this page.
     * @return Brand country setting edit page.
     */
    @RequestMapping(path = "")
    public String displayInitPage(CommonBaseForm commonForm, Model model) {

        List<Brand> brandList = brandCountrySettingEditService.getBrandList();
        model.addAttribute(BRAND_LIST, brandList);

        List<Country> countryList = brandCountrySettingEditService.getCountryList();
        model.addAttribute(COUNTRY_LIST, countryList);

        BrandCountrySettingEditForm brandCountrySettingEditForm = new BrandCountrySettingEditForm();
        model.addAttribute(BRAND_COUNTRY_SETTING_EDIT_FORM, brandCountrySettingEditForm);

        return BRAND_COUNTRY_SETTING_EDIT_PAGE;
    }

    /**
     * Get back list brand country edit search page.
     * 
     * @param storeSettingDetailForm Form for the store setting detail page.
     * @param model The model attribute for this page.
     * @return Brand country setting edit page.
     */
    @PostMapping(path = "/backListSearch")
    public String displayBackDetailSearchPage(StoreSettingDetailForm storeSettingDetailForm,
            Model model) {

        BrandCountrySettingEditForm brandCountrySettingEditForm = new BrandCountrySettingEditForm();
        brandCountrySettingEditForm.setViewStoreCode(storeSettingDetailForm.getViewStoreCode());
        brandCountrySettingEditForm.setBrand(storeSettingDetailForm.getStoreBrandCode());
        brandCountrySettingEditForm.setCountry(storeSettingDetailForm.getStoreCountryCode());
        List<BrandCountrySettingEdit> storeList = brandCountrySettingEditService
                .getBrandCountrySettingEditList(brandCountrySettingEditForm);

        brandCountrySettingEditForm.setBrandCountrySettingEditList(storeList);
        model.addAttribute(BRAND_COUNTRY_SETTING_EDIT_FORM, brandCountrySettingEditForm);
        return BRAND_COUNTRY_SETTING_EDIT_PAGE;
    }

    /**
     * Get back brand country edit search page.
     * 
     * @param storeSettingForm Form for the store setting page.
     * @param model The model attribute for this page.
     * @return Brand country setting edit page.
     */
    @PostMapping(path = "/backSearch")
    public String displayBackSearchPage(StoreSettingForm storeSettingForm, Model model) {

        BrandCountrySettingEditForm brandCountrySettingEditForm = new BrandCountrySettingEditForm();
        brandCountrySettingEditForm.setViewStoreCode(storeSettingForm.getViewStoreCode());
        brandCountrySettingEditForm.setBrand(storeSettingForm.getStoreBrandCode());
        brandCountrySettingEditForm.setCountry(storeSettingForm.getStoreCountryCode());
        List<BrandCountrySettingEdit> storeList = brandCountrySettingEditService
                .getBrandCountrySettingEditList(brandCountrySettingEditForm);

        brandCountrySettingEditForm.setBrandCountrySettingEditList(storeList);
        model.addAttribute(BRAND_COUNTRY_SETTING_EDIT_FORM, brandCountrySettingEditForm);
        return BRAND_COUNTRY_SETTING_EDIT_PAGE;
    }

    /**
     * Get brand country edit search page.
     * 
     * @param brandCountrySettingEditForm Form for the brand country setting edit page.
     * @param model The model attribute for this page.
     * @return Brand country setting edit page.
     */
    @PostMapping(path = "/search")
    public String displaySearchPage(
            @Validated BrandCountrySettingEditForm brandCountrySettingEditForm,
            BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            throw new ScreenException(bindingResult, BRAND_COUNTRY_SETTING_EDIT_FORM,
                    BRAND_COUNTRY_SETTING_EDIT_PAGE, model.asMap());
        }
        List<BrandCountrySettingEdit> storeList = brandCountrySettingEditService
                .getBrandCountrySettingEditList(brandCountrySettingEditForm);

        brandCountrySettingEditForm.setBrandCountrySettingEditList(storeList);
        model.addAttribute(BRAND_COUNTRY_SETTING_EDIT_FORM, brandCountrySettingEditForm);
        return BRAND_COUNTRY_SETTING_EDIT_CHILD_PAGE;
    }

    /**
     * Get back list store setting page.
     * 
     * @param storeSettingDetailForm Form the store setting detail page.
     * @param model The model attribute for store setting page.
     * @return store setting page.
     */
    @RequestMapping(path = "/backList")
    public String displayBackListPage(StoreSettingDetailForm storeSettingDetailForm, Model model) {

        BrandCountrySettingEditForm brandCountrySettingEditForm = new BrandCountrySettingEditForm();
        brandCountrySettingEditForm.setStoreBrandCode(storeSettingDetailForm.getStoreBrandCode());
        brandCountrySettingEditForm.setBusinessCode(storeSettingDetailForm.getBusinessCode());
        brandCountrySettingEditForm
                .setStoreCountryCode(storeSettingDetailForm.getStoreCountryCode());
        brandCountrySettingEditForm.setStateCode(storeSettingDetailForm.getStateCode());
        brandCountrySettingEditForm.setStoreCode(storeSettingDetailForm.getStoreCode());
        brandCountrySettingEditService.addBrandCountrySettinglist(brandCountrySettingEditForm);
        StoreSettingForm storeSettingForm =
                FormBuilder.build(StoreSettingForm.class, brandCountrySettingEditForm);
        storeSettingForm.setStoreBrandCode(brandCountrySettingEditForm.getStoreBrandCode());
        storeSettingForm.setBusinessCode(brandCountrySettingEditForm.getBusinessCode());
        storeSettingForm.setStoreCountryCode(brandCountrySettingEditForm.getStoreCountryCode());
        storeSettingForm.setStateCode(brandCountrySettingEditForm.getStateCode());
        storeSettingForm.setStoreCode(brandCountrySettingEditForm.getStoreCode());
        int count = brandCountrySettingEditService.countStoreSettingList(storeSettingForm);
        storeSettingForm.setCount(count);
        String brandName = brandCountrySettingEditService
                .getBrandAndCountry(storeSettingForm.getStoreBrandCode(), BRAND_TYPE_ID);
        if (!storeSettingForm.getStoreCountryCode().equals(DEFAULT_COUNTRY_CODE)) {

            String countryName = brandCountrySettingEditService
                    .getBrandAndCountry(storeSettingForm.getStoreCountryCode(), COUNTRY_TYPE_ID);

            storeSettingForm.setCountryName(countryName);
        }
        storeSettingForm.setBrandName(brandName);
        List<StoreSetting> storeSettingList =
                brandCountrySettingEditService.getStoreSettingList(storeSettingForm);
        String pageData = getReadValue(storeSettingList);
        storeSettingForm.setPageData(pageData);
        storeSettingForm.setStoreSettingList(storeSettingList);
        if (!storeSettingForm.getStoreCode().startsWith(DEFAULT_STORE_HEADER)) {
            TranslationStoreCodeMasterOptional translationStoreCodeMaster =
                    brandCountrySettingEditService
                            .getStoreInformation(brandCountrySettingEditForm.getStoreCode());
            String storeName = translationStoreCodeMaster.getStoreName();
            String viewStoreCode = translationStoreCodeMaster.getViewStoreCode();
            storeSettingForm.setViewStoreCode(viewStoreCode);
            storeSettingForm.setStoreName(storeName);
        }
        model.addAttribute(STORE_SETTING_FORM, storeSettingForm);

        return STORE_SETTING_LIST_PAGE;
    }

    /**
     * Get store setting page.
     * 
     * @param brandCountrySettingEditForm Form from the previous page.
     * @param model The model attribute for store setting page.
     * @return store setting page.
     */
    @RequestMapping(path = "/list")
    public String displayListPage(BrandCountrySettingEditForm brandCountrySettingEditForm,
            Model model) {

        brandCountrySettingEditService.addBrandCountrySettinglist(brandCountrySettingEditForm);
        StoreSettingForm storeSettingForm =
                FormBuilder.build(StoreSettingForm.class, brandCountrySettingEditForm);
        storeSettingForm.setStoreBrandCode(brandCountrySettingEditForm.getStoreBrandCode());
        storeSettingForm.setBusinessCode(brandCountrySettingEditForm.getBusinessCode());
        storeSettingForm.setStoreCountryCode(brandCountrySettingEditForm.getStoreCountryCode());
        storeSettingForm.setStateCode(brandCountrySettingEditForm.getStateCode());
        storeSettingForm.setStoreCode(brandCountrySettingEditForm.getStoreCode());
        int count = brandCountrySettingEditService.countStoreSettingList(storeSettingForm);
        storeSettingForm.setCount(count);
        String brandName = brandCountrySettingEditService
                .getBrandAndCountry(storeSettingForm.getStoreBrandCode(), BRAND_TYPE_ID);
        if (!storeSettingForm.getStoreCountryCode().equals(DEFAULT_COUNTRY_CODE)) {

            String countryName = brandCountrySettingEditService
                    .getBrandAndCountry(storeSettingForm.getStoreCountryCode(), COUNTRY_TYPE_ID);

            storeSettingForm.setCountryName(countryName);
        }
        storeSettingForm.setBrandName(brandName);
        List<StoreSetting> storeSettingList =
                brandCountrySettingEditService.getStoreSettingList(storeSettingForm);
        String pageData = getReadValue(storeSettingList);
        storeSettingForm.setPageData(pageData);
        storeSettingForm.setStoreSettingList(storeSettingList);
        if (!storeSettingForm.getStoreCode().startsWith(DEFAULT_STORE_HEADER)) {
            TranslationStoreCodeMasterOptional translationStoreCodeMaster =
                    brandCountrySettingEditService
                            .getStoreInformation(brandCountrySettingEditForm.getStoreCode());
            String storeName = translationStoreCodeMaster.getStoreName();
            String viewStoreCode = translationStoreCodeMaster.getViewStoreCode();
            storeSettingForm.setViewStoreCode(viewStoreCode);
            storeSettingForm.setStoreName(storeName);
        }
        model.addAttribute(STORE_SETTING_FORM, storeSettingForm);

        return STORE_SETTING_LIST_PAGE;
    }

    /**
     * Delete page data.
     * 
     * @param storeSettingForm Form for store setting page.
     * @return Store setting page.
     */
    @RequestMapping(path = "/delete")
    public String deletePopUp(StoreSettingForm storeSettingForm) {

        List<StoreSetting> storeList = getEditList(storeSettingForm.getPageData());
        storeSettingForm.setStoreSettingList(storeList);
        if (storeSettingForm.getStoreCode().startsWith(DEFAULT_STORE_HEADER)) {
            storeSettingForm.setDetailError(
                    getErrorMessage(MessagePrefix.E_SLS_66000113_DEFAULT_SETTING_EXIT,
                            MessageType.ERROR.getType()));
            return STORE_SETTING_LIST_PAGE;
        }
        if (storeSettingForm.getCount() == 0) {
            storeSettingForm.setDetailError(getErrorMessage(
                    MessagePrefix.E_SLS_66000114_NO_RECORD_EXIT, MessageType.ERROR.getType()));
            return STORE_SETTING_LIST_PAGE;
        }
        storeSettingForm.setDetailError(getErrorMessage(MessagePrefix.I_SLS_06000103_DELETE_CONFIRM,
                MessageType.INFORMATION.getType()));
        return STORE_SETTING_LIST_PAGE;
    }

    /**
     * Get delete message pop up.
     * 
     * @param storeSettingForm Form for store setting form.
     * @param model The model attribute for store setting page.
     * @return Store setting page.
     */
    @RequestMapping(path = "/after")
    public String deleteStoreInformation(StoreSettingForm storeSettingForm, Model model) {

        int count = brandCountrySettingEditService.deleteStoreSettingList(storeSettingForm);
        if (count == 0) {
            storeSettingForm.setDetailError(
                    getErrorMessage(MessagePrefix.E_SLS_66000115_HAS_DELETED_BY_OTHERS,
                            MessageType.ERROR.getType()));
        }
        return STORE_SETTING_LIST_PAGE;
    }

    /**
     * Get store setting detail page.
     * 
     * @param storeSettingForm Form for store setting form.
     * @param model The model attribute for store setting page.
     * @return Store setting page.
     */
    @RequestMapping(path = "/detail")
    public String displayStoreSettingDetail(StoreSettingForm storeSettingForm, Model model) {

        StoreSettingDetailForm storeSettingDetailForm =
                FormBuilder.build(StoreSettingDetailForm.class, storeSettingForm);
        storeSettingDetailForm.setStoreBrandCode(storeSettingForm.getStoreBrandCode());
        storeSettingDetailForm.setBrandName(storeSettingForm.getBrandName());
        storeSettingDetailForm.setStoreCountryCode(storeSettingForm.getStoreCountryCode());
        storeSettingDetailForm.setCountryName(storeSettingForm.getCountryName());
        storeSettingDetailForm.setStoreCode(storeSettingForm.getStoreCode());
        storeSettingDetailForm.setViewStoreCode(storeSettingForm.getViewStoreCode());
        storeSettingDetailForm.setStoreName(storeSettingForm.getStoreName());
        storeSettingDetailForm.setBusinessCode(storeSettingForm.getBusinessCode());
        storeSettingDetailForm.setStateCode(storeSettingForm.getStateCode());
        storeSettingDetailForm.setCodeL1(storeSettingForm.getCodeL1());
        int count = brandCountrySettingEditService
                .countBusinessSettingDetailList(storeSettingDetailForm);
        storeSettingDetailForm.setCount(count);
        if (count == 0) {
            List<StoreSettingDetail> defaultList =
                    brandCountrySettingEditService.getDetailDefaultList(storeSettingDetailForm);
            storeSettingDetailForm.setBrandCountrySettingEditList(defaultList);
        } else {
            List<StoreSettingDetail> detailList =
                    brandCountrySettingEditService.getDetailList(storeSettingDetailForm);
            storeSettingDetailForm.setBrandCountrySettingEditList(detailList);
        }
        if (ItemSetting.BUSINESS_DATE_CHECK.getValue().equals(storeSettingDetailForm.getCodeL1())) {
            // Sort list for display.
            Collections.sort(storeSettingDetailForm.getBrandCountrySettingEditList(),
                    new Comparator<StoreSettingDetail>() {
                        @Override
                        public int compare(StoreSettingDetail detail1, StoreSettingDetail detail2) {
                            return getBusinessDateCodeL2SortValue(detail1.getCodeL2())
                                    - getBusinessDateCodeL2SortValue(detail2.getCodeL2());
                        }
                    });
        }
        String pageData = getReadValue(storeSettingDetailForm.getBrandCountrySettingEditList());
        storeSettingDetailForm.setPageData(pageData);
        if (storeSettingDetailForm.getCodeL1().equals(ItemSetting.LINKAGE_TIMING.getValue())) {
            storeSettingDetailForm.getBrandCountrySettingEditList()
                    .stream()
                    .filter(detail -> LINKAGE_UNIT.equals(detail.getCodeL3()))
                    .findFirst()
                    .ifPresent(detail -> {
                        storeSettingDetailForm.setCheckValue(detail.getCodeValue());
                        storeSettingDetailForm
                                .setLinkageTimingVariableType(detail.getVariableType());
                        storeSettingDetailForm
                                .setLinkageUnitUpdateDatetime(detail.getUpdateDatetime());
                        storeSettingDetailForm.getBrandCountrySettingEditList().remove(detail);
                    });
            model.addAttribute(STORE_SETTING_DETAIL_FORM, storeSettingDetailForm);
            List<String> orderStatusList =
                    brandCountrySettingEditService.getOrderStatusList(ORDER_STATUS_TYPE_ID);
            model.addAttribute(ORDER_STATUS_LIST, orderStatusList);
            return STORE_SETTING_DETAIL_LINKAGE_PAGE;
        } else if (storeSettingDetailForm.getCodeL1().equals(ItemSetting.SALES_REPORT.getValue())) {
            model.addAttribute(STORE_SETTING_DETAIL_FORM, storeSettingDetailForm);
            List<String> outputlist =
                    brandCountrySettingEditService.getOrderStatusList(OUTPUT_TYPE_ID);
            model.addAttribute(OUTPUT_LIST, outputlist);
            List<String> signColumnList =
                    brandCountrySettingEditService.getOrderStatusList(PAY_OFF_TYPE_ID);
            model.addAttribute(SIGN_COLUMN_LIST, signColumnList);
            return STORE_SETTING_DETAIL_SALES_REPORT_PAGE;
        } else if (storeSettingDetailForm.getCodeL1().equals(ItemSetting.PAY_OFF_DATA.getValue())) {
            model.addAttribute(STORE_SETTING_DETAIL_FORM, storeSettingDetailForm);
            String mappingPatternName = brandCountrySettingEditService.getMappingPatternName();
            model.addAttribute(MAPPING_PATTERN_NAME, mappingPatternName);
            return STORE_SETTING_DETAIL_PAY_OFF_PAGE;
        } else if (storeSettingDetailForm.getCodeL1()
                .equals(ItemSetting.BUSINESS_DATE_CHECK.getValue())) {
            model.addAttribute(STORE_SETTING_DETAIL_FORM, storeSettingDetailForm);
            return STORE_SETTING_DETAIL_BUSINESS_DATE_PAGE;
        } else if (storeSettingDetailForm.getCodeL1().equals(ItemSetting.TAX_TYPE.getValue())) {
            model.addAttribute(STORE_SETTING_DETAIL_FORM, storeSettingDetailForm);
            List<String> rtloglist =
                    brandCountrySettingEditService.getOrderStatusList(RTLOG_TYPE_ID);
            model.addAttribute(RTLOG_LIST, rtloglist);
            return STORE_SETTING_DETAIL_TAX_TYPE_PAGE;
        } else if (storeSettingDetailForm.getCodeL1()
                .equals(ItemSetting.SALES_INTEGRITY_CHECK.getValue())) {
            model.addAttribute(STORE_SETTING_DETAIL_FORM, storeSettingDetailForm);
            List<String> payOffList =
                    brandCountrySettingEditService.getOrderStatusList(PAY_OFF_TYPE_ID);
            model.addAttribute(PAY_OFF_LIST, payOffList);
            List<String> quantityList =
                    brandCountrySettingEditService.getOrderStatusList(QUANTITY_TYPE_ID);
            model.addAttribute(QUANTITY_LIST, quantityList);
            return STORE_SETTING_DETAIL_SALES_INTEGRITY_CHECK_PAGE;
        } else if (storeSettingDetailForm.getCodeL1()
                .equals(ItemSetting.SALES_INTEGRITY_CHECK_TYPE.getValue())) {
            model.addAttribute(STORE_SETTING_DETAIL_FORM, storeSettingDetailForm);
            List<String> payOffList =
                    brandCountrySettingEditService.getOrderStatusList(PAY_OFF_TYPE_ID);
            model.addAttribute(PAY_OFF_LIST, payOffList);
            return STORE_SETTING_DETAIL_SALES_INTEGRITY_CHECK_TYPE_PAGE;
        } else if (storeSettingDetailForm.getCodeL1().equals(ItemSetting.DECIMAL.getValue())) {
            model.addAttribute(STORE_SETTING_DETAIL_FORM, storeSettingDetailForm);
            return STORE_SETTING_DETAIL_DEMICAL_PAGE;
        } else {
            return null;
        }
    }

    /**
     * Get store setting detail detail add pop up.
     * 
     * @param storeSettingDetailForm Form for store setting detail form.
     * @param model The model attribute for store setting page.
     * @return Store setting detail add pop up page.
     */
    @RequestMapping(path = "/add")
    public String displayAddStoreSettingDetail(StoreSettingDetailForm storeSettingDetailForm,
            Model model) {
        StoreSettingDetailAddForm storeSettingDetailAddForm =
                FormBuilder.build(StoreSettingDetailAddForm.class, storeSettingDetailForm);
        storeSettingDetailAddForm.setCodeL1(storeSettingDetailForm.getCodeL1());
        storeSettingDetailAddForm.setStoreCode(storeSettingDetailForm.getStoreCode());
        FormBuilder.setTemporaryData(TEMPORARY_DATA, storeSettingDetailForm,
                storeSettingDetailAddForm);
        model.addAttribute(STORE_SETTING_DETAIL_ADD_FORM, storeSettingDetailAddForm);
        if (storeSettingDetailForm.getCodeL1().equals(ItemSetting.LINKAGE_TIMING.getValue())) {
            List<String> orderStatusList =
                    brandCountrySettingEditService.getOrderStatusList(ORDER_STATUS_TYPE_ID);
            model.addAttribute(ORDER_STATUS_LIST, orderStatusList);
            model.addAttribute(CODE_L2, CODE_L2_ORDER_STATUS);
            return STORE_SETTING_DETAIL_LINKAGE_TIMING_PAGE;
        } else if (storeSettingDetailForm.getCodeL1()
                .equals(ItemSetting.SALES_INTEGRITY_CHECK.getValue())) {
            List<SalesIntegrityCheck> payOffList = brandCountrySettingEditService
                    .getSalesIntegrityCheckList(MAPPING_SUB_PATTERN_TYPE_ID);
            model.addAttribute(PAY_OFF_LIST, payOffList);
            List<String> quantityList =
                    brandCountrySettingEditService.getOrderStatusList(QUANTITY_TYPE_ID);
            model.addAttribute(QUANTITY_LIST, quantityList);
            return STORE_SETTING_DETAIL_SALES_INTEGRITY_CHECK_POPUP_PAGE;
        } else if (storeSettingDetailForm.getCodeL1()
                .equals(ItemSetting.SALES_INTEGRITY_CHECK_TYPE.getValue())) {
            List<String> payOffList =
                    brandCountrySettingEditService.getOrderStatusList(PAY_OFF_TYPE_ID);
            model.addAttribute(PAY_OFF_LIST, payOffList);
            model.addAttribute(CODE_L2, CODE_L2_TARGET_TYPE);
            return STORE_SETTING_DETAIL_SALES_INTEGRITY_CHECK_TYPE_POPUP_PAGE;
        } else {
            return null;
        }
    }

    /**
     * Get store setting detail page after add.
     * 
     * @param storeSettingDetailAddForm Form for the store setting detail page.
     * @param model The model attribute for store setting detail page.
     * @return Store setting detail page.
     */
    @RequestMapping(path = "/back")
    public String addStoreSettingDetail(StoreSettingDetailAddForm storeSettingDetailAddForm,
            Model model) {
        StoreSettingDetail storeSettingDetail = new StoreSettingDetail();
        storeSettingDetail.setCodeL1(storeSettingDetailAddForm.getCodeL1());
        if (storeSettingDetailAddForm.getCodeL2() != null) {
            storeSettingDetail.setCodeL2(storeSettingDetailAddForm.getCodeL2());
        }
        storeSettingDetail.setCodeL3(storeSettingDetailAddForm.getCodeL3());
        storeSettingDetail.setCodeValue(storeSettingDetailAddForm.getCodeValue());
        storeSettingDetail.setAddColor(ADD_COLOR);
        StoreSettingDetailForm storeSettingDetailForm = FormBuilder.getTemporaryData(TEMPORARY_DATA,
                StoreSettingDetailForm.class, storeSettingDetailAddForm);
        List<StoreSettingDetail> detailList =
                storeSettingDetailForm.getBrandCountrySettingEditList();
        detailList.add(storeSettingDetail);
        storeSettingDetailForm.setBrandCountrySettingEditList(detailList);
        model.addAttribute(STORE_SETTING_DETAIL_FORM, storeSettingDetailForm);
        if (storeSettingDetail.getCodeL1().equals(ItemSetting.LINKAGE_TIMING.getValue())) {
            return STORE_SETTING_DETAIL_LINKAGE_PAGE;
        } else if (storeSettingDetail.getCodeL1()
                .equals(ItemSetting.SALES_INTEGRITY_CHECK.getValue())) {
            return STORE_SETTING_DETAIL_SALES_INTEGRITY_CHECK_PAGE;
        } else if (storeSettingDetail.getCodeL1()
                .equals(ItemSetting.SALES_INTEGRITY_CHECK_TYPE.getValue())) {
            return STORE_SETTING_DETAIL_SALES_INTEGRITY_CHECK_TYPE_PAGE;
        } else {
            return null;
        }

    }

    /**
     * Check store setting detail page has double value.
     * 
     * @param storeSettingDetailForm Form for the store setting detail page.
     * @param model The model attribute for store setting detail page.
     * @return Store setting detail page.
     */
    @RequestMapping(path = "/doubleCheck")
    public String doubleCheck(StoreSettingDetailForm storeSettingDetailForm, Model model) {
        List<StoreSettingDetail> list = storeSettingDetailForm.getBrandCountrySettingEditList();
        if (list == null) {
            list = getEditDetailList(storeSettingDetailForm.getPageData());
        }
        boolean isDataDuplicate = false;

        final String codeL1 = storeSettingDetailForm.getCodeL1();
        if (ItemSetting.LINKAGE_TIMING.getValue().equals(codeL1)
                || ItemSetting.SALES_INTEGRITY_CHECK.getValue().equals(codeL1)
                || ItemSetting.SALES_INTEGRITY_CHECK_TYPE.getValue().equals(codeL1)) {
            for (int i = 0; i < list.size(); i++) {
                for (int j = 0; j < i; j++) {
                    if (list.get(i).getCodeL3().equals(list.get(j).getCodeL3())) {
                        isDataDuplicate = true;
                        break;
                    }
                }
            }
        }
        if (isDataDuplicate) {
            storeSettingDetailForm.setDetailError(getErrorMessage(
                    MessagePrefix.E_SLS_66000116_DATA_DOUBLE_CHECK, MessageType.ERROR.getType()));
        } else {
            storeSettingDetailForm.setDetailError(
                    getErrorMessage(MessagePrefix.I_SLS_06000112_DATA_REGISTRATION_CONFIRM,
                            MessageType.INFORMATION.getType()));
        }
        model.addAttribute(STORE_SETTING_DETAIL_FORM, storeSettingDetailForm);
        if (storeSettingDetailForm.getCodeL1().equals(ItemSetting.LINKAGE_TIMING.getValue())) {
            List<String> orderStatusList =
                    brandCountrySettingEditService.getOrderStatusList(ORDER_STATUS_TYPE_ID);
            model.addAttribute(ORDER_STATUS_LIST, orderStatusList);
            return STORE_SETTING_DETAIL_LINKAGE_PAGE;
        } else if (storeSettingDetailForm.getCodeL1().equals(ItemSetting.SALES_REPORT.getValue())) {
            List<String> outputlist =
                    brandCountrySettingEditService.getOrderStatusList(OUTPUT_TYPE_ID);
            model.addAttribute(OUTPUT_LIST, outputlist);
            List<String> signColumnList =
                    brandCountrySettingEditService.getOrderStatusList(PAY_OFF_TYPE_ID);
            model.addAttribute(SIGN_COLUMN_LIST, signColumnList);
            return STORE_SETTING_DETAIL_SALES_REPORT_PAGE;
        } else if (storeSettingDetailForm.getCodeL1().equals(ItemSetting.PAY_OFF_DATA.getValue())) {
            String mappingPatternName = brandCountrySettingEditService.getMappingPatternName();
            model.addAttribute(MAPPING_PATTERN_NAME, mappingPatternName);
            return STORE_SETTING_DETAIL_PAY_OFF_PAGE;
        } else if (storeSettingDetailForm.getCodeL1()
                .equals(ItemSetting.BUSINESS_DATE_CHECK.getValue())) {
            return STORE_SETTING_DETAIL_BUSINESS_DATE_PAGE;
        } else if (storeSettingDetailForm.getCodeL1().equals(ItemSetting.TAX_TYPE.getValue())) {
            List<String> rtloglist =
                    brandCountrySettingEditService.getOrderStatusList(RTLOG_TYPE_ID);
            model.addAttribute(RTLOG_LIST, rtloglist);
            return STORE_SETTING_DETAIL_TAX_TYPE_PAGE;
        } else if (storeSettingDetailForm.getCodeL1()
                .equals(ItemSetting.SALES_INTEGRITY_CHECK.getValue())) {
            List<String> payOffList =
                    brandCountrySettingEditService.getOrderStatusList(PAY_OFF_TYPE_ID);
            model.addAttribute(PAY_OFF_LIST, payOffList);
            List<String> quantityList =
                    brandCountrySettingEditService.getOrderStatusList(QUANTITY_TYPE_ID);
            model.addAttribute(QUANTITY_LIST, quantityList);
            return STORE_SETTING_DETAIL_SALES_INTEGRITY_CHECK_PAGE;
        } else if (storeSettingDetailForm.getCodeL1()
                .equals(ItemSetting.SALES_INTEGRITY_CHECK_TYPE.getValue())) {
            List<String> payOffList =
                    brandCountrySettingEditService.getOrderStatusList(PAY_OFF_TYPE_ID);
            model.addAttribute(PAY_OFF_LIST, payOffList);
            return STORE_SETTING_DETAIL_SALES_INTEGRITY_CHECK_TYPE_PAGE;
        } else if (storeSettingDetailForm.getCodeL1().equals(ItemSetting.DECIMAL.getValue())) {
            return STORE_SETTING_DETAIL_DEMICAL_PAGE;
        } else {
            return null;
        }
    }

    /**
     * Update or insert store setting detail data to the database.
     * 
     * @param storeSettingDetailForm Form for the store setting detail page.
     * @param model The model attribute for store setting detail page.
     * @return Store setting detail page.
     */
    @RequestMapping(path = "/register")
    public String registerStoreSettingDetail(StoreSettingDetailForm storeSettingDetailForm,
            Model model) {
        if (ItemSetting.LINKAGE_TIMING.getValue().equals(storeSettingDetailForm.getCodeL1())) {
            StoreSettingDetail storeSettingDetailAddition = new StoreSettingDetail();
            storeSettingDetailAddition.setCodeL1(ItemSetting.LINKAGE_TIMING.getValue());
            storeSettingDetailAddition.setCodeL2(SALES_LINKAGE_UNIT);
            storeSettingDetailAddition.setCodeL3(LINKAGE_UNIT);
            storeSettingDetailAddition.setCodeValue(storeSettingDetailForm.getCheckValue());
            storeSettingDetailAddition
                    .setVariableType(storeSettingDetailForm.getLinkageTimingVariableType());
            storeSettingDetailAddition
                    .setUpdateDatetime(storeSettingDetailForm.getLinkageUnitUpdateDatetime());
            storeSettingDetailForm.getBrandCountrySettingEditList().add(storeSettingDetailAddition);
        }
        int count = 0;
        if (storeSettingDetailForm.getCount() == 0) {
            count = brandCountrySettingEditService.insertDefaultDetail(storeSettingDetailForm);
        } else {
            count = brandCountrySettingEditService.updateDetail(storeSettingDetailForm);
            if (count == 0) {
                storeSettingDetailForm.setDetailError(
                        getErrorMessage(MessagePrefix.E_SLS_66000115_UPDATE_BUT_DELETED,
                                MessageType.ERROR.getType()));
            } else {
                if (storeSettingDetailForm.getBrandCountrySettingEditList()
                        .size() > storeSettingDetailForm.getCount()) {
                    count = brandCountrySettingEditService.insertAddDetail(storeSettingDetailForm);
                }
            }
        }

        if (ItemSetting.LINKAGE_TIMING.getValue().equals(storeSettingDetailForm.getCodeL1())) {
            storeSettingDetailForm.getBrandCountrySettingEditList()
                    .stream()
                    .filter(detail -> LINKAGE_UNIT.equals(detail.getCodeL3()))
                    .findFirst()
                    .ifPresent(detail -> {
                        storeSettingDetailForm.setCheckValue(detail.getCodeValue());
                        storeSettingDetailForm
                                .setLinkageTimingVariableType(detail.getVariableType());
                        storeSettingDetailForm.getBrandCountrySettingEditList().remove(detail);
                    });
            model.addAttribute(STORE_SETTING_DETAIL_FORM, storeSettingDetailForm);
            List<String> orderStatusList =
                    brandCountrySettingEditService.getOrderStatusList(ORDER_STATUS_TYPE_ID);
            model.addAttribute(ORDER_STATUS_LIST, orderStatusList);
            return STORE_SETTING_DETAIL_LINKAGE_PAGE;
        } else if (ItemSetting.SALES_REPORT.getValue().equals(storeSettingDetailForm.getCodeL1())) {
            model.addAttribute(STORE_SETTING_DETAIL_FORM, storeSettingDetailForm);
            List<String> outputlist =
                    brandCountrySettingEditService.getOrderStatusList(OUTPUT_TYPE_ID);
            model.addAttribute(OUTPUT_LIST, outputlist);
            return STORE_SETTING_DETAIL_SALES_REPORT_PAGE;
        } else if (ItemSetting.PAY_OFF_DATA.getValue().equals(storeSettingDetailForm.getCodeL1())) {
            model.addAttribute(STORE_SETTING_DETAIL_FORM, storeSettingDetailForm);
            String mappingPatternName = brandCountrySettingEditService.getMappingPatternName();
            model.addAttribute(MAPPING_PATTERN_NAME, mappingPatternName);
            return STORE_SETTING_DETAIL_PAY_OFF_PAGE;
        } else if (ItemSetting.BUSINESS_DATE_CHECK.getValue()
                .equals(storeSettingDetailForm.getCodeL1())) {
            model.addAttribute(STORE_SETTING_DETAIL_FORM, storeSettingDetailForm);
            return STORE_SETTING_DETAIL_BUSINESS_DATE_PAGE;
        } else if (ItemSetting.TAX_TYPE.getValue().equals(storeSettingDetailForm.getCodeL1())) {
            model.addAttribute(STORE_SETTING_DETAIL_FORM, storeSettingDetailForm);
            List<String> rtloglist =
                    brandCountrySettingEditService.getOrderStatusList(RTLOG_TYPE_ID);
            model.addAttribute(RTLOG_LIST, rtloglist);
            return STORE_SETTING_DETAIL_TAX_TYPE_PAGE;
        } else if (ItemSetting.SALES_INTEGRITY_CHECK.getValue()
                .equals(storeSettingDetailForm.getCodeL1())) {
            model.addAttribute(STORE_SETTING_DETAIL_FORM, storeSettingDetailForm);
            List<String> payOffList =
                    brandCountrySettingEditService.getOrderStatusList(PAY_OFF_TYPE_ID);
            model.addAttribute(PAY_OFF_LIST, payOffList);
            List<String> quantityList =
                    brandCountrySettingEditService.getOrderStatusList(QUANTITY_TYPE_ID);
            model.addAttribute(QUANTITY_LIST, quantityList);
            return STORE_SETTING_DETAIL_SALES_INTEGRITY_CHECK_PAGE;
        } else if (ItemSetting.SALES_INTEGRITY_CHECK_TYPE.getValue()
                .equals(storeSettingDetailForm.getCodeL1())) {
            model.addAttribute(STORE_SETTING_DETAIL_FORM, storeSettingDetailForm);
            List<String> payOffList =
                    brandCountrySettingEditService.getOrderStatusList(PAY_OFF_TYPE_ID);
            model.addAttribute(PAY_OFF_LIST, payOffList);
            return STORE_SETTING_DETAIL_SALES_INTEGRITY_CHECK_TYPE_PAGE;
        } else if (ItemSetting.DECIMAL.getValue().equals(storeSettingDetailForm.getCodeL1())) {
            model.addAttribute(STORE_SETTING_DETAIL_FORM, storeSettingDetailForm);
            return STORE_SETTING_DETAIL_DEMICAL_PAGE;
        } else {
            return null;
        }
    }

    /**
     * Get detail error.
     * 
     * @param messageId Message id.
     * @param messageType Message type.
     * @return Detail error.
     */
    private DetailError getErrorMessage(String messageId, String messageType) {
        DetailError detailError = new DetailError();
        detailError.setErrorCode(messageId);
        detailError.setErrorMessage(localeMessageSource.getMessage(messageId));
        detailError.setMessageType(messageType);
        return detailError;
    }

    /**
     * Get list from String.
     * 
     * @param <T> Generic class.
     * @param pageData pageData from the page.
     * @return beanList List from page.
     */
    private <T> List<T> getEditDetailList(String pageData) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<T> beanList = new ArrayList<>();
        try {
            beanList = objectMapper.readValue(pageData,
                    new TypeReference<List<StoreSettingDetail>>() {});
        } catch (JsonMappingException e) {
            throw new SystemException("error in convert String to list", e);
        } catch (IOException e) {
            throw new SystemException("error in convert String to list", e);
        }
        return beanList;
    }

    /**
     * Get list from String.
     * 
     * @param <T> Generic class.
     * @param pageData pageData from the page.
     * @return beanList List from page.
     */
    private <T> List<T> getEditList(String pageData) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<T> beanList = new ArrayList<>();
        try {
            beanList = objectMapper.readValue(pageData, new TypeReference<List<StoreSetting>>() {});
        } catch (JsonMappingException e) {
            throw new SystemException("error in convert String to list", e);
        } catch (IOException e) {
            throw new SystemException("error in convert list to String", e);
        }
        return beanList;
    }

    /**
     * Read list to String.
     * 
     * @param <T> Generic class.
     * @param list List from form.
     * @return pageData List to String.
     */
    private <T> String getReadValue(List<T> list) {
        ObjectMapper objectMapper = new ObjectMapper();
        String pageData = null;
        try {
            pageData = objectMapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            throw new SystemException("error in convert list to String", e);
        }
        return pageData;
    }

    /**
     * Get business date code l2 value for sort.
     *
     * @param code L2 code.
     * @return value Value for sort.
     */
    private int getBusinessDateCodeL2SortValue(String code) {
        switch (code) {
            case CODE_L2_DATE_EXCEEDING:
                return DisplayOrder.DISPLAY_ORDER_ONE.getValue();
            case CODE_L2_BEFORE_STORE_OPENING:
                return DisplayOrder.DISPLAY_ORDER_TWO.getValue();
            case CODE_L2_AFTER_STORE_CLOSING:
                return DisplayOrder.DISPLAY_ORDER_THREE.getValue();
            case CODE_L2_CLOSED_BUSINESS_DATE:
                return DisplayOrder.DISPLAY_ORDER_FOUR.getValue();
            default:
                return DisplayOrder.DISPLAY_ORDER_FIVE.getValue();
        }
    }

}
