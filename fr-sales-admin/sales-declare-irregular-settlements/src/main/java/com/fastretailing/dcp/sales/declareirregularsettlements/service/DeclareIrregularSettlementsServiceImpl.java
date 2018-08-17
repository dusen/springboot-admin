/**
 * @(#)DeclareIrregularSettlementsServiceImpl.java
 *
 *                                                 Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.declareirregularsettlements.service;

import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fastretailing.dcp.sales.common.entity.optional.TenderPaymentOptional;
import com.fastretailing.dcp.sales.common.entity.optional.TranslationStoreCodeMasterOptional;
import com.fastretailing.dcp.sales.declareirregularsettlements.component.DeclareIrregularSettlementsServiceHelper;
import com.fastretailing.dcp.sales.declareirregularsettlements.form.DeclareIrregularSettlementsForm;

/**
 * Declare irregular settlements service implements.
 */
@Service
public class DeclareIrregularSettlementsServiceImpl implements DeclareIrregularSettlementsService {

    /**
     * Field id(system_brand_code).
     */
    private static final String FIELD_ID_SYSTEM_BRAND_CODE = "system_brand_code";

    /**
     * Field id(system_country_code).
     */
    private static final String FIELD_ID_SYSTEM_COUNTRY_CODE = "system_country_code";

    /**
     * Declare irregular settlements service helper.
     */
    @Autowired
    private DeclareIrregularSettlementsServiceHelper declareIrregularSettlementsServiceHelper;

    /**
     * {@inheritDoc}
     */
    @Override
    public DeclareIrregularSettlementsForm initialize(
            DeclareIrregularSettlementsForm declareIrregularSettlementsForm) {

        // Get common code master map about brand code.
        Map<String, String> brandCodeMap = declareIrregularSettlementsServiceHelper
                .getCommonCodeMasterMap(FIELD_ID_SYSTEM_BRAND_CODE);
        // Get common code master map about country code.
        Map<String, String> countryCodeMap = declareIrregularSettlementsServiceHelper
                .getCommonCodeMasterMap(FIELD_ID_SYSTEM_COUNTRY_CODE);

        TranslationStoreCodeMasterOptional translationStoreCodeMasterOptional = null;
        if (StringUtils.isNotEmpty(declareIrregularSettlementsForm.getStoreCode())) {

            // Get translation store code master optional.
            translationStoreCodeMasterOptional =
                    declareIrregularSettlementsServiceHelper.getTranslationStoreCodeMasterOptional(
                            declareIrregularSettlementsForm.getStoreCode());
            // Check brand code map by key.
            declareIrregularSettlementsServiceHelper.checkMapByKey(brandCodeMap,
                    translationStoreCodeMasterOptional.getSystemBrandCode());
            // Check country code map by key.
            declareIrregularSettlementsServiceHelper.checkMapByKey(countryCodeMap,
                    translationStoreCodeMasterOptional.getSystemCountryCode());
        }

        // Set initialize form.
        declareIrregularSettlementsServiceHelper.setInitializeForm(declareIrregularSettlementsForm,
                translationStoreCodeMasterOptional, brandCodeMap, countryCodeMap);

        return declareIrregularSettlementsForm;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DeclareIrregularSettlementsForm search(
            DeclareIrregularSettlementsForm declareIrregularSettlementsForm) {

        declareIrregularSettlementsServiceHelper
                .clearScreenBodyArea(declareIrregularSettlementsForm, false);

        // Get store code.
        String storeCode = declareIrregularSettlementsServiceHelper
                .getStoreCode(declareIrregularSettlementsForm);
        // Get decimal size.
        String decimalSize = declareIrregularSettlementsServiceHelper
                .getDecimalSize(declareIrregularSettlementsForm, storeCode);
        // Check payoff complete.
        boolean checkResult = declareIrregularSettlementsServiceHelper
                .checkPayoffComplete(declareIrregularSettlementsForm, storeCode);
        if (!checkResult) {

            declareIrregularSettlementsServiceHelper
                    .clearScreenBodyLeftArea(declareIrregularSettlementsForm);
            return declareIrregularSettlementsForm;
        }

        // Get max receipt number.
        Integer maxReceiptNumber = declareIrregularSettlementsServiceHelper
                .getMaxReceiptNo(declareIrregularSettlementsForm, storeCode);
        if (maxReceiptNumber == null) {

            declareIrregularSettlementsServiceHelper
                    .clearScreenBodyLeftArea(declareIrregularSettlementsForm);
            return declareIrregularSettlementsForm;
        }

        // Get tender payment data list.
        List<TenderPaymentOptional> tenderPaymentOptionalList =
                declareIrregularSettlementsServiceHelper
                        .getTenderPaymentOptionalList(declareIrregularSettlementsForm, storeCode);

        // Set search result to form.
        declareIrregularSettlementsServiceHelper.setSearchResultToForm(
                declareIrregularSettlementsForm, storeCode, decimalSize, maxReceiptNumber,
                tenderPaymentOptionalList);
        return declareIrregularSettlementsForm;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void calculation(DeclareIrregularSettlementsForm declareIrregularSettlementsForm) {

        declareIrregularSettlementsServiceHelper.calculation(declareIrregularSettlementsForm);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBaseInformation(
            DeclareIrregularSettlementsForm declareIrregularSettlementsForm) {

        // Get common code master map about brand code.
        Map<String, String> brandCodeMap = declareIrregularSettlementsServiceHelper
                .getCommonCodeMasterMap(FIELD_ID_SYSTEM_BRAND_CODE);
        // Get common code master map about country code.
        Map<String, String> countryCodeMap = declareIrregularSettlementsServiceHelper
                .getCommonCodeMasterMap(FIELD_ID_SYSTEM_COUNTRY_CODE);

        declareIrregularSettlementsForm.setBrandCodeMap(brandCodeMap);
        declareIrregularSettlementsForm.setCountryCodeMap(countryCodeMap);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void confirm(DeclareIrregularSettlementsForm declareIrregularSettlementsForm) {

        declareIrregularSettlementsServiceHelper.confirm(declareIrregularSettlementsForm);
    }
}
