/**
 * @(#)StoreTransactionInquiryValidation.java
 *
 *                                            Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.storetransactioninquiry.validatioin;

import java.math.BigDecimal;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fastretailing.dcp.common.constants.ErrorName;
import com.fastretailing.dcp.common.exception.BusinessException;
import com.fastretailing.dcp.sales.common.constants.MessagePrefix;
import com.fastretailing.dcp.sales.common.entity.CommonCodeMaster;
import com.fastretailing.dcp.sales.common.entity.optional.BusinessCountryStateSettingMasterOptional;
import com.fastretailing.dcp.sales.common.entity.optional.SalesReportTransactionHeaderOptional;
import com.fastretailing.dcp.sales.common.entity.optional.StoreTransactionInquiryDetail;
import com.fastretailing.dcp.sales.common.entity.optional.TranslationStoreCodeMasterOptional;
import com.fastretailing.dcp.sales.common.entity.optional.TranslationTenderMasterOptional;
import com.fastretailing.dcp.sales.storetransactioninquiry.form.StoreTransactionInquiryForm;
import com.fastretailing.dcp.storecommon.screen.message.LocaleMessageSource;
import com.fastretailing.dcp.storecommon.screen.util.ScreenCommonUtility;

/**
 * Store transaction inquiry validation class.
 */
@Component
public class StoreTransactionInquiryValidation {

    /** Locale message source. */
    @Autowired
    private LocaleMessageSource localeMessageSource;

    /**
     * Check translation store code master.
     * 
     * @param translationStoreCodeMasterList Translation store code master list.
     */
    public void checkTranslationStoreCodeMaster(
            List<TranslationStoreCodeMasterOptional> translationStoreCodeMasterList) {
        if (CollectionUtils.isEmpty(translationStoreCodeMasterList)) {
            throwBusinessException(MessagePrefix.E_SLS_66000131_NO_TRANSLATION_STORE_CODE);
        }
    }

    /**
     * Check common code master.
     * 
     * @param commonCodeMasterList Common code master list.
     */
    public void checkCommonCodeMaster(List<CommonCodeMaster> commonCodeMasterList) {
        if (CollectionUtils.isEmpty(commonCodeMasterList)) {
            throwBusinessException(MessagePrefix.E_SLS_66000128_NO_COMMON_CODE);
        }

    }

    /**
     * Check translation tender master.
     * 
     * @param translationTenderMasterList Translation tender master list.
     */
    public void checkTranslationTenderMaster(
            List<TranslationTenderMasterOptional> translationTenderMasterList) {
        if (CollectionUtils.isEmpty(translationTenderMasterList)) {
            throwBusinessException(MessagePrefix.E_SLS_66000170_NO_TRANSLATION_TENDER);
        }
    }

    /**
     * Check non item master.
     * 
     * @param nonItemMasterList Non item master list.
     */
    public void checkNonItemMaster(List<StoreTransactionInquiryDetail> nonItemMasterList) {
        if (CollectionUtils.isEmpty(nonItemMasterList)) {
            throwBusinessException(MessagePrefix.E_SLS_66000153_NO_NON_ITEM);
        }
    }

    /**
     * Check business country state setting master.
     * 
     * @param codeValueList Code value list.
     */
    public void checkBusinessCountryStateSettingMaster(
            List<BusinessCountryStateSettingMasterOptional> codeValueList) {
        if (CollectionUtils.isEmpty(codeValueList)) {
            throwBusinessException(MessagePrefix.E_SLS_66000162_NO_BUSINESS_COUNTRY_STATE_SETTING);
        }

    }

    /**
     * Check store transaction inquiry detail.
     * 
     * @param headerDetailList Header detail list.
     */
    public void checkStoreTransactionInquiryDetail(
            List<SalesReportTransactionHeaderOptional> headerDetailList) {
        if (CollectionUtils.isEmpty(headerDetailList)) {
            throwBusinessException(MessagePrefix.E_SLS_66000134_NO_DATA);
        }
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
     * Check input value.
     * 
     * @param storeTransactionInquiryForm Store transaction inquiry form.
     */
    public void checkInputValue(StoreTransactionInquiryForm storeTransactionInquiryForm) {
        if (StringUtils.isNotEmpty(storeTransactionInquiryForm.getPaymentAmountFrom())
                && StringUtils.isNotEmpty(storeTransactionInquiryForm.getPaymentAmountTo())) {
            BigDecimal paymentAmountFrom =
                    new BigDecimal(storeTransactionInquiryForm.getPaymentAmountFrom());
            BigDecimal paymentAmountTo =
                    new BigDecimal(storeTransactionInquiryForm.getPaymentAmountTo());
            if (paymentAmountFrom.compareTo(paymentAmountTo) == 1) {
                throwBusinessException(MessagePrefix.E_SLS_66000151_RANGE_ERROR);
            }
        }
        if (StringUtils.isNotEmpty(storeTransactionInquiryForm.getChangeAmountFrom())
                && StringUtils.isNotEmpty(storeTransactionInquiryForm.getChangeAmountTo())) {
            BigDecimal changeAmountFrom =
                    new BigDecimal(storeTransactionInquiryForm.getChangeAmountFrom());
            BigDecimal changeAmountTo =
                    new BigDecimal(storeTransactionInquiryForm.getChangeAmountTo());
            if (changeAmountFrom.compareTo(changeAmountTo) == 1) {
                throwBusinessException(MessagePrefix.E_SLS_66000151_RANGE_ERROR);
            }
        }
        if (StringUtils.isNotEmpty(storeTransactionInquiryForm.getDepositAmountFrom())
                && StringUtils.isNotEmpty(storeTransactionInquiryForm.getDepositAmountTo())) {
            BigDecimal depositAmountFrom =
                    new BigDecimal(storeTransactionInquiryForm.getDepositAmountFrom());
            BigDecimal depositAmountTo =
                    new BigDecimal(storeTransactionInquiryForm.getDepositAmountTo());
            if (depositAmountFrom.compareTo(depositAmountTo) == 1) {
                throwBusinessException(MessagePrefix.E_SLS_66000151_RANGE_ERROR);
            }
        }
        if (StringUtils.isNotEmpty(storeTransactionInquiryForm.getReceiptNoFrom())
                && StringUtils.isNotEmpty(storeTransactionInquiryForm.getReceiptNoTo())) {
            if (storeTransactionInquiryForm.getReceiptNoFrom()
                    .compareTo(storeTransactionInquiryForm.getReceiptNoTo()) > 0) {
                throwBusinessException(MessagePrefix.E_SLS_66000151_RANGE_ERROR);
            }
        }
    }
}
