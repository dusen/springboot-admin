/**
 * @(#)DeclareIrregularSettlementsServiceHelper.java
 *
 *                                                   Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.declareirregularsettlements.component;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fastretailing.dcp.common.constants.ErrorName;
import com.fastretailing.dcp.common.exception.BusinessException;
import com.fastretailing.dcp.sales.common.constants.BusinessItem;
import com.fastretailing.dcp.sales.common.constants.MessagePrefix;
import com.fastretailing.dcp.sales.common.entity.CommonCodeMaster;
import com.fastretailing.dcp.sales.common.entity.CommonCodeMasterCondition;
import com.fastretailing.dcp.sales.common.entity.optional.BusinessCountryStateSettingMasterOptional;
import com.fastretailing.dcp.sales.common.entity.optional.OpenLogOptional;
import com.fastretailing.dcp.sales.common.entity.optional.PileUpPayOffDataOptional;
import com.fastretailing.dcp.sales.common.entity.optional.SalesReportTransactionHeaderOptional;
import com.fastretailing.dcp.sales.common.entity.optional.TenderPaymentOptional;
import com.fastretailing.dcp.sales.common.entity.optional.TenderPaymentOptionalCondition;
import com.fastretailing.dcp.sales.common.entity.optional.TranslationStoreCodeMasterOptional;
import com.fastretailing.dcp.sales.common.repository.CommonCodeMasterMapper;
import com.fastretailing.dcp.sales.common.repository.optional.BusinessCountryStateSettingMasterOptionalMapper;
import com.fastretailing.dcp.sales.common.repository.optional.OpenLogOptionalMapper;
import com.fastretailing.dcp.sales.common.repository.optional.PileUpPayOffDataOptionalMapper;
import com.fastretailing.dcp.sales.common.repository.optional.SalesReportTransactionHeaderDetailMapper;
import com.fastretailing.dcp.sales.common.repository.optional.TenderPaymentOptionalMapper;
import com.fastretailing.dcp.sales.common.repository.optional.TranslationStoreCodeMasterDetailOptionalMapper;
import com.fastretailing.dcp.sales.common.type.CodeLevel;
import com.fastretailing.dcp.sales.declareirregularsettlements.form.AlterationPayoffDataRequest;
import com.fastretailing.dcp.sales.declareirregularsettlements.form.DeclareIrregularSettlementsForm;
import com.fastretailing.dcp.sales.declareirregularsettlements.form.PayoffInformation;
import com.fastretailing.dcp.sales.declareirregularsettlements.form.TenderPaymentInformation;
import com.fastretailing.dcp.storecommon.screen.message.LocaleMessageSource;
import com.fastretailing.dcp.storecommon.screen.util.ScreenCommonUtility;

/**
 * Declare irregular settlements service helper.
 */
@Component
public class DeclareIrregularSettlementsServiceHelper {

    private static final DateTimeFormatter DATE_FORMAT_UUUUMMDD =
            DateTimeFormatter.ofPattern("uuuuMMdd");

    private static final DateTimeFormatter DATE_FORMAT_UUUUSMMSDD =
            DateTimeFormatter.ofPattern("uuuu/MM/dd").withResolverStyle(ResolverStyle.STRICT);

    /** Payoff code cash. */
    private static final String PAYOFF_CODE_CASH = "CASH";

    /** Payoff code dcash. */
    private static final String PAYOFF_CODE_DCASH = "DCASH";

    /** Payoff code style. */
    private static final String PAYOFF_CODE_E002 = "E002";

    /** Decimal size. */
    private static final String DECIMAL_SIZE = "2";

    /** Initialize integer. */
    private static final String INITIALIZE_INTEGER = "0";

    /** Initialize decimal. */
    private static final String INITIALIZE_DECIMAL = "00";

    /** Locale message source. */
    @Autowired
    private LocaleMessageSource localeMessageSource;

    /** Common code master mapper. */
    @Autowired
    private CommonCodeMasterMapper commonCodeMasterMapper;

    /** Translation store code master optional mapper. */
    @Autowired
    private TranslationStoreCodeMasterDetailOptionalMapper translationStoreCodeMasterDetailOptionalMapper;

    /** Business country state setting master optional mapper. */
    @Autowired
    private BusinessCountryStateSettingMasterOptionalMapper businessCountryStateSettingMasterOptionalMapper;

    /** Sales report transaction header optional mapper. */
    @Autowired
    private SalesReportTransactionHeaderDetailMapper salesReportTransactionHeaderDetailMapper;

    /** Open log optional mapper. */
    @Autowired
    private OpenLogOptionalMapper openLogOptionalMapper;

    /** Tender payment optional mapper. */
    @Autowired
    private TenderPaymentOptionalMapper tenderPaymentOptionalMapper;

    /** Pile up pay off data optional mapper. */
    @Autowired
    private PileUpPayOffDataOptionalMapper pileUpPayOffDataOptionalMapper;

    /**
     * Get common code master map.
     * 
     * @param typeId Type id.
     * @return Common code master map.
     */
    public Map<String, String> getCommonCodeMasterMap(String typeId) {

        CommonCodeMasterCondition condition = new CommonCodeMasterCondition();
        condition.createCriteria().andTypeIdEqualTo(typeId);
        List<CommonCodeMaster> commonCodeMasterList =
                commonCodeMasterMapper.selectByCondition(condition);
        if (CollectionUtils.isEmpty(commonCodeMasterList)) {
            throwBusinessException(null, MessagePrefix.E_SLS_66000128_NO_DATA_EXISTS);
        }

        return commonCodeMasterList.stream().collect(
                Collectors.toMap(CommonCodeMaster::getTypeValue, CommonCodeMaster::getName1,
                        (master1, master2) -> master1, LinkedHashMap::new));
    }

    /**
     * Check map by key.
     * 
     * @param map System code map.
     * @param key The primary key.
     */
    public void checkMapByKey(Map<String, String> map, String key) {

        String value = map.get(key);

        if (value == null) {
            throwBusinessException(null, MessagePrefix.E_SLS_66000128_NO_DATA_EXISTS);
        }
    }

    /**
     * Get translation store code master optional.
     * 
     * @param storeCode Store code.
     * @return Translation store code master optional.
     */
    public TranslationStoreCodeMasterOptional getTranslationStoreCodeMasterOptional(
            String storeCode) {

        TranslationStoreCodeMasterOptional translationStoreCodeMasterOptional =
                translationStoreCodeMasterDetailOptionalMapper.selectByPrimaryKey(storeCode);
        if (translationStoreCodeMasterOptional == null) {
            throwBusinessException(null, MessagePrefix.E_SLS_66000131_NO_DATA_EXISTS);
        }

        return translationStoreCodeMasterOptional;
    }

    /**
     * Set initialize form.
     * 
     * @param declareIrregularSettlementsForm Declare irregular settlements form.
     * @param translationStoreCodeMasterOptional Translation store code master optional.
     * @param brandCodeMap Brand code map.
     * @param countryCodeMap Country code map.
     */
    public void setInitializeForm(DeclareIrregularSettlementsForm declareIrregularSettlementsForm,
            TranslationStoreCodeMasterOptional translationStoreCodeMasterOptional,
            Map<String, String> brandCodeMap, Map<String, String> countryCodeMap) {

        // Set base information to form.
        setBaseInformationToForm(declareIrregularSettlementsForm,
                translationStoreCodeMasterOptional, brandCodeMap, countryCodeMap);
        // Clear screen body area.
        clearScreenBodyArea(declareIrregularSettlementsForm, true);
    }

    /**
     * Set base information to form.
     * 
     * @param declareIrregularSettlementsForm Declare irregular settlements form.
     * @param translationStoreCodeMasterOptional Translation store code master optional.
     * @param brandCodeMap Brand code map.
     * @param countryCodeMap Country code map.
     */
    public void setBaseInformationToForm(
            DeclareIrregularSettlementsForm declareIrregularSettlementsForm,
            TranslationStoreCodeMasterOptional translationStoreCodeMasterOptional,
            Map<String, String> brandCodeMap, Map<String, String> countryCodeMap) {

        if (StringUtils.isNotEmpty(declareIrregularSettlementsForm.getStoreCode())) {

            declareIrregularSettlementsForm
                    .setDisplayStoreCode(translationStoreCodeMasterOptional.getViewStoreCode());
            declareIrregularSettlementsForm
                    .setSystemBrandCode(translationStoreCodeMasterOptional.getSystemBrandCode());
            declareIrregularSettlementsForm.setSystemBrandName(
                    brandCodeMap.get(translationStoreCodeMasterOptional.getSystemBrandCode()));
            declareIrregularSettlementsForm.setSystemCountryCode(
                    translationStoreCodeMasterOptional.getSystemCountryCode());
            declareIrregularSettlementsForm.setSystemCountryName(
                    countryCodeMap.get(translationStoreCodeMasterOptional.getSystemCountryCode()));
            declareIrregularSettlementsForm.setStoreFlag(true);
        } else {
            declareIrregularSettlementsForm.setStoreFlag(false);
        }

        declareIrregularSettlementsForm.setBrandCodeMap(brandCodeMap);
        declareIrregularSettlementsForm.setCountryCodeMap(countryCodeMap);
    }

    /**
     * Clear screen body area.
     * 
     * @param declareIrregularSettlementsForm Declare irregular settlements form.
     * @param initializeFlag Initialize flag.
     */
    public void clearScreenBodyArea(DeclareIrregularSettlementsForm declareIrregularSettlementsForm,
            boolean initializeFlag) {

        if (initializeFlag) {

            declareIrregularSettlementsForm.setChangeFundInteger(INITIALIZE_INTEGER);
            declareIrregularSettlementsForm.setChangeFundDecimal(INITIALIZE_DECIMAL);
            declareIrregularSettlementsForm.setClosingBalanceInteger(INITIALIZE_INTEGER);
            declareIrregularSettlementsForm.setClosingBalanceDecimal(INITIALIZE_DECIMAL);
        }

        declareIrregularSettlementsForm.setDecimalSize(DECIMAL_SIZE);
        declareIrregularSettlementsForm.setListHaveFlag(false);
        declareIrregularSettlementsForm.setMessageDisplayFlag(false);
        declareIrregularSettlementsForm.setNormalMessage("");
        declareIrregularSettlementsForm.setRequiredBalance("");
        declareIrregularSettlementsForm.setValueDiff("");
        declareIrregularSettlementsForm.setEndReceiptNo("");
        declareIrregularSettlementsForm.setTenderPaymentInformationList(new ArrayList<>());
        declareIrregularSettlementsForm.setCashPaymentTotal("");
    }

    /**
     * Get store code.
     * 
     * @param declareIrregularSettlementsForm Declare irregular settlements form.
     * @return Store code.
     */
    public String getStoreCode(DeclareIrregularSettlementsForm declareIrregularSettlementsForm) {

        if (StringUtils.isEmpty(declareIrregularSettlementsForm.getStoreCode())) {

            TranslationStoreCodeMasterOptional condition = new TranslationStoreCodeMasterOptional();
            condition.setViewStoreCode(declareIrregularSettlementsForm.getDisplayStoreCode());
            condition.setSystemBrandCode(declareIrregularSettlementsForm.getSystemBrandCode());
            condition.setSystemCountryCode(declareIrregularSettlementsForm.getSystemCountryCode());

            List<TranslationStoreCodeMasterOptional> translationStoreCodeMasterOptionalList =
                    translationStoreCodeMasterDetailOptionalMapper
                            .selectByBusinessPrimaryKey(condition);

            if (CollectionUtils.isEmpty(translationStoreCodeMasterOptionalList)) {
                throwBusinessException(declareIrregularSettlementsForm,
                        MessagePrefix.E_SLS_66000131_NO_DATA_EXISTS);
            }

            return translationStoreCodeMasterOptionalList.get(0).getStoreCode();
        } else {
            return declareIrregularSettlementsForm.getStoreCode();
        }
    }

    /**
     * Get decimal size.
     * 
     * @param declareIrregularSettlementsForm Declare irregular settlements form.
     * @param storeCode Store code.
     * @return Decimal size.
     */
    public String getDecimalSize(DeclareIrregularSettlementsForm declareIrregularSettlementsForm,
            String storeCode) {

        BusinessCountryStateSettingMasterOptional condition =
                new BusinessCountryStateSettingMasterOptional();
        condition.setStoreCode(storeCode);
        condition.setCodeL1(CodeLevel.DECIMAL.getValue());
        condition.setCodeL2(CodeLevel.DECIMAL.getValue());
        condition.setCodeL3(CodeLevel.FRACTION.getValue());

        List<BusinessCountryStateSettingMasterOptional> businessCountryStateSettingMasterList =
                businessCountryStateSettingMasterOptionalMapper
                        .selectByBusinessPrimaryKey(condition);

        if (CollectionUtils.isEmpty(businessCountryStateSettingMasterList)) {
            throwBusinessException(declareIrregularSettlementsForm,
                    MessagePrefix.E_SLS_66000162_NO_DATA_EXISTS);
        }

        return businessCountryStateSettingMasterList.get(0).getCodeValue();
    }

    /**
     * Check payoff complete.
     * 
     * @param declareIrregularSettlementsForm Declare irregular settlements form.
     * @param storeCode Store code.
     * @return Check result.
     */
    public boolean checkPayoffComplete(
            DeclareIrregularSettlementsForm declareIrregularSettlementsForm, String storeCode) {

        String settlementDate = declareIrregularSettlementsForm.getSettlementDate();
        String businessDate = convertDateFormat(settlementDate);

        OpenLogOptional primaryKey = new OpenLogOptional();
        primaryKey.setStoreCode(storeCode);
        primaryKey.setBusinessDate(businessDate);
        primaryKey.setCashRegisterNo(
                Integer.parseInt(declareIrregularSettlementsForm.getCashRegisterNo()));
        OpenLogOptional openLogOptional = openLogOptionalMapper.selectByPrimaryKey(primaryKey);

        if (openLogOptional == null) {

            declareIrregularSettlementsForm.setMessageDisplayFlag(true);
            declareIrregularSettlementsForm.setNormalMessage(
                    localeMessageSource.getMessage(MessagePrefix.E_SLS_66000134_NO_DATA_EXISTS));

            return false;
        }

        boolean payoffFlag = openLogOptional.isPayoffFlag();

        if (payoffFlag) {

            declareIrregularSettlementsForm.setMessageDisplayFlag(true);
            declareIrregularSettlementsForm.setNormalMessage(
                    localeMessageSource.getMessage(MessagePrefix.E_SLS_66000145_PAYOFF_COMPLETE));
            return false;
        }

        return true;
    }

    /**
     * Get max receipt number.
     * 
     * @param declareIrregularSettlementsForm Declare irregular settlements form.
     * @param storeCode Store code.
     * @return Max receipt number.
     */
    public Integer getMaxReceiptNo(DeclareIrregularSettlementsForm declareIrregularSettlementsForm,
            String storeCode) {

        SalesReportTransactionHeaderOptional condition = new SalesReportTransactionHeaderOptional();
        condition.setStoreCode(storeCode);
        condition.setImsLinkageDate(
                convertDateFormat(declareIrregularSettlementsForm.getSettlementDate()));
        condition.setCashRegisterNo(
                Integer.parseInt(declareIrregularSettlementsForm.getCashRegisterNo()));

        return salesReportTransactionHeaderDetailMapper.selectMaxReceiptNumber(condition);
    }

    /**
     * Get tender payment data list.
     * 
     * @param declareIrregularSettlementsForm Declare irregular settlements form.
     * @param storeCode Store code.
     * @return Tender payment optional list.
     */
    public List<TenderPaymentOptional> getTenderPaymentOptionalList(
            DeclareIrregularSettlementsForm declareIrregularSettlementsForm, String storeCode) {

        TenderPaymentOptionalCondition condition = new TenderPaymentOptionalCondition();
        condition.setStoreCode(storeCode);
        condition.setPayoffDate(
                convertDateFormat(declareIrregularSettlementsForm.getSettlementDate()));
        condition.setCashRegisterNo(
                Integer.parseInt(declareIrregularSettlementsForm.getCashRegisterNo()));

        List<TenderPaymentOptional> tenderPaymentOptionalList =
                tenderPaymentOptionalMapper.selectTenderPaymentDataList(condition);

        return tenderPaymentOptionalList;
    }

    /**
     * Set search result to form.
     * 
     * @param declareIrregularSettlementsForm Declare irregular settlements form.
     * @param storeCode Store code.
     * @param decimalSize Decimal size.
     * @param maxReceiptNumber Max receipt number.
     * @param tenderPaymentOptionalList Tender payment optional list.
     */
    public void setSearchResultToForm(
            DeclareIrregularSettlementsForm declareIrregularSettlementsForm, String storeCode,
            String decimalSize, Integer maxReceiptNumber,
            List<TenderPaymentOptional> tenderPaymentOptionalList) {

        if (CollectionUtils.isNotEmpty(tenderPaymentOptionalList)) {

            BigDecimal cashPaymentTotal = BigDecimal.ZERO;

            List<TenderPaymentInformation> tenderPaymentInformationList = new ArrayList<>();

            for (TenderPaymentOptional tenderPaymentOptional : tenderPaymentOptionalList) {

                TenderPaymentInformation tenderPaymentInformation = new TenderPaymentInformation();
                tenderPaymentInformation.setTenderGroup(tenderPaymentOptional.getPayoffTypeCode());
                tenderPaymentInformation.setTenderId(tenderPaymentOptional.getTenderName());
                tenderPaymentInformation
                        .setCurrencyCode(tenderPaymentOptional.getPayoffAmountCurrencyCode());

                BigDecimal payoffAmount = tenderPaymentOptional.getPayoffAmount();
                if (payoffAmount != null) {
                    payoffAmount = payoffAmount.setScale(Integer.parseInt(decimalSize),
                            BigDecimal.ROUND_HALF_UP);
                    tenderPaymentInformation.setValue(formatBigDecimal(payoffAmount));

                    if (PAYOFF_CODE_CASH.equals(tenderPaymentOptional.getPayoffTypeCode())) {
                        cashPaymentTotal = cashPaymentTotal.add(payoffAmount);
                    }
                }

                tenderPaymentInformationList.add(tenderPaymentInformation);
            }

            declareIrregularSettlementsForm
                    .setTenderPaymentInformationList(tenderPaymentInformationList);
            declareIrregularSettlementsForm.setEndReceiptNo(String.valueOf(maxReceiptNumber));
            declareIrregularSettlementsForm.setCashPaymentTotal(cashPaymentTotal.toPlainString());
            declareIrregularSettlementsForm.setDecimalSize(decimalSize);
            declareIrregularSettlementsForm.setListHaveFlag(true);

            // Calculation the declare irregular settlements form.
            calculation(declareIrregularSettlementsForm);
        } else {
            clearScreenBodyLeftArea(declareIrregularSettlementsForm);
        }
    }

    /**
     * Calculation the declare irregular settlements form.
     * 
     * @param declareIrregularSettlementsForm Declare irregular settlements form.
     */
    public void calculation(DeclareIrregularSettlementsForm declareIrregularSettlementsForm) {

        if (CollectionUtils
                .isEmpty(declareIrregularSettlementsForm.getTenderPaymentInformationList())) {
            return;
        }

        int decimalSize = Integer.parseInt(declareIrregularSettlementsForm.getDecimalSize());

        String changeFundInteger = declareIrregularSettlementsForm.getChangeFundInteger();
        String changeFundDecimal = declareIrregularSettlementsForm.getChangeFundDecimal();
        // Get change fund.
        BigDecimal changeFund =
                getNumberByIntegerAndDecimal(changeFundInteger, changeFundDecimal, decimalSize);

        String closingBalanceInteger = declareIrregularSettlementsForm.getClosingBalanceInteger();
        String closingBalanceDecimal = declareIrregularSettlementsForm.getClosingBalanceDecimal();
        // Get closing balance.
        BigDecimal closingBalance = getNumberByIntegerAndDecimal(closingBalanceInteger,
                closingBalanceDecimal, decimalSize);

        String cashPaymentTotal = declareIrregularSettlementsForm.getCashPaymentTotal();

        BigDecimal requiredBalance = changeFund.add(new BigDecimal(cashPaymentTotal));
        BigDecimal valueDiff = closingBalance.subtract(requiredBalance);

        // Change the data format.
        declareIrregularSettlementsForm.setRequiredBalance(formatBigDecimal(requiredBalance));
        declareIrregularSettlementsForm.setValueDiff(formatBigDecimal(valueDiff));
        declareIrregularSettlementsForm.setChangeFundDecimal(getDecimal(changeFund, decimalSize));
        declareIrregularSettlementsForm
                .setClosingBalanceDecimal(getDecimal(closingBalance, decimalSize));
    }

    /**
     * Clear screen body left area.
     * 
     * @param declareIrregularSettlementsForm Declare irregular settlements form.
     */
    public void clearScreenBodyLeftArea(
            DeclareIrregularSettlementsForm declareIrregularSettlementsForm) {

        declareIrregularSettlementsForm.setChangeFundInteger("");
        declareIrregularSettlementsForm.setChangeFundDecimal("");
        declareIrregularSettlementsForm.setClosingBalanceInteger("");
        declareIrregularSettlementsForm.setClosingBalanceDecimal("");
        declareIrregularSettlementsForm.setRequiredBalance("");
        declareIrregularSettlementsForm.setValueDiff("");
        declareIrregularSettlementsForm.setEndReceiptNo("");
    }

    /**
     * Function confirm button.
     * 
     * @param declareIrregularSettlementsForm Declare irregular settlements form.
     */
    public void confirm(DeclareIrregularSettlementsForm declareIrregularSettlementsForm) {

        PileUpPayOffDataOptional condition = new PileUpPayOffDataOptional();
        condition.setStoreCode(declareIrregularSettlementsForm.getStoreCode());
        condition.setPayoffDate(
                convertDateFormat(declareIrregularSettlementsForm.getSettlementDate()));
        condition.setCashRegisterNo(
                Integer.parseInt(declareIrregularSettlementsForm.getCashRegisterNo()));

        List<PileUpPayOffDataOptional> pileUpPayOffDataOptionalList = pileUpPayOffDataOptionalMapper
                .selectDeclareIrregularSettlementsPileUpPayOffDataOptional(condition);

        if (CollectionUtils.isNotEmpty(pileUpPayOffDataOptionalList)) {
            return;
        }

        AlterationPayoffDataRequest alterationPayoffDataRequest = new AlterationPayoffDataRequest();

        String storeCode = getStoreCode(declareIrregularSettlementsForm);
        alterationPayoffDataRequest.setStoreCode(storeCode);
        alterationPayoffDataRequest
                .setCashRegisterNo(declareIrregularSettlementsForm.getCashRegisterNo());
        alterationPayoffDataRequest
                .setPayoffDate(declareIrregularSettlementsForm.getSettlementDate());
        alterationPayoffDataRequest
                .setLoginUserId(declareIrregularSettlementsForm.getLoginUserId());

        List<PayoffInformation> payoffInformationList =
                pileUpPayOffDataOptionalList.stream().map(pileUpPayOffDataOptional -> {
                    PayoffInformation payoffInformation = new PayoffInformation();

                    String payoffTypeCode = pileUpPayOffDataOptional.getPayoffTypeCode();
                    payoffInformation.setPayoffTypeCode(payoffTypeCode);

                    if (PAYOFF_CODE_DCASH.equals(payoffTypeCode)) {
                        payoffInformation.setPayoffTypeSubNumberCode(PAYOFF_CODE_CASH);
                        payoffInformation.setPayoffAmount(new BigDecimal(
                                declareIrregularSettlementsForm.getRequiredBalance()));
                        payoffInformation.setPayoffQuantity(BigDecimal.ZERO);

                        if (pileUpPayOffDataOptional.getPayoffAmount()
                                .compareTo(BigDecimal.ZERO) > 0) {
                            payoffInformation.setQuantityCode(BusinessItem.SIGN_CODE_POSITIVE);
                        } else {
                            payoffInformation.setQuantityCode(BusinessItem.SIGN_CODE_NEGATIVE);
                        }
                    } else if (PAYOFF_CODE_E002.equals(payoffTypeCode)) {

                        payoffInformation.setPayoffTypeSubNumberCode("");
                        payoffInformation.setPayoffAmount(
                                new BigDecimal(declareIrregularSettlementsForm.getValueDiff()));
                    } else {
                        payoffInformation.setPayoffTypeSubNumberCode(
                                pileUpPayOffDataOptional.getPayoffTypeSubNumberCode());
                        payoffInformation
                                .setPayoffAmount(pileUpPayOffDataOptional.getPayoffAmount());
                        payoffInformation
                                .setPayoffQuantity(pileUpPayOffDataOptional.getPayoffQuantity());
                    }

                    if (pileUpPayOffDataOptional.getPayoffAmount().compareTo(BigDecimal.ZERO) > 0) {
                        payoffInformation.setAmountCode(BusinessItem.SIGN_CODE_POSITIVE);
                    } else {
                        payoffInformation.setAmountCode(BusinessItem.SIGN_CODE_NEGATIVE);
                    }

                    payoffInformation.setPayoffAmountCurrencyCode(
                            pileUpPayOffDataOptional.getPayoffAmountCurrencyCode());

                    return payoffInformation;
                }).collect(Collectors.toList());

        alterationPayoffDataRequest.setPayoffInformationList(payoffInformationList);

        // TODO call alteration payoff data api, but api isn't realization.
    }

    /**
     * Get decimal.
     * 
     * @param value Change Fund value.
     * @param decimalSize Decimal size.
     * @return Decimal.
     */
    public String getDecimal(BigDecimal value, int decimalSize) {

        if (decimalSize == 0) {
            return "";
        } else {
            return value.toPlainString().substring(value.toPlainString().indexOf(".") + 1);
        }
    }

    /**
     * Get number by integer and decimal.
     * 
     * @param valueInteger Value integer.
     * @param valueDecimal Value decimal.
     * @param decimalSize Decimal size.
     * @return Number.
     */
    public BigDecimal getNumberByIntegerAndDecimal(String valueInteger, String valueDecimal,
            int decimalSize) {

        if (decimalSize == 0) {
            return new BigDecimal(valueInteger);
        } else {

            if (StringUtils.isEmpty(valueDecimal)) {
                return new BigDecimal(valueInteger).setScale(decimalSize);
            } else {
                return new BigDecimal(valueInteger + "." + valueDecimal).setScale(decimalSize);
            }
        }
    }

    /**
     * Convert date format.
     * 
     * @param date Date.
     * @return Date.
     */
    public String convertDateFormat(String date) {
        return LocalDate.parse(date, DATE_FORMAT_UUUUSMMSDD).format(DATE_FORMAT_UUUUMMDD);
    }

    /**
     * Convert the specified big decimal value to a text number with the specified locale format.
     * 
     * @param number Number.
     * @return Formatted text.
     */
    public String formatBigDecimal(BigDecimal number) {

        if (number != null) {

            String value = number.toPlainString();
            int index = value.indexOf(".");
            if (index < 0) {
                NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.JAPAN);
                return numberFormat.format(number.doubleValue());
            } else {
                String valueInteger = value.substring(0, index);
                String valueDecimal = value.substring(index + 1);
                NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.JAPAN);
                return numberFormat.format(Double.valueOf(valueInteger)) + "." + valueDecimal;
            }
        } else {
            return "";
        }
    }

    /**
     * Throw business exception.
     * 
     * @param declareIrregularSettlementsForm Declare irregular settlements form.
     * @param messageId Message id.
     */
    private void throwBusinessException(
            DeclareIrregularSettlementsForm declareIrregularSettlementsForm, String messageId) {

        if (declareIrregularSettlementsForm != null) {
            clearScreenBodyArea(declareIrregularSettlementsForm, false);
            clearScreenBodyLeftArea(declareIrregularSettlementsForm);
        }
        throw new BusinessException(ScreenCommonUtility.createResultObject(localeMessageSource,
                ErrorName.Business.BUSINESS_CHECK_ERROR, messageId, messageId));
    }
}
