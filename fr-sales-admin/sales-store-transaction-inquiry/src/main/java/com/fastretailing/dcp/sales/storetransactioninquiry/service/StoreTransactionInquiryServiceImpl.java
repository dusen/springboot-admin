/**
 * @(#)StoreTransactionInquiryServiceImpl.java
 *
 *                                             Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.storetransactioninquiry.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import com.fastretailing.dcp.sales.common.constants.BusinessItem;
import com.fastretailing.dcp.sales.common.entity.CommonCodeMaster;
import com.fastretailing.dcp.sales.common.entity.CommonCodeMasterCondition;
import com.fastretailing.dcp.sales.common.entity.optional.BusinessCountryStateSettingMasterOptional;
import com.fastretailing.dcp.sales.common.entity.optional.BusinessCountryStateSettingMasterOptionalCondition;
import com.fastretailing.dcp.sales.common.entity.optional.ItemMasterOptional;
import com.fastretailing.dcp.sales.common.entity.optional.ItemMasterOptionalCondition;
import com.fastretailing.dcp.sales.common.entity.optional.SalesReportTransactionDiscountDetailOptional;
import com.fastretailing.dcp.sales.common.entity.optional.SalesReportTransactionHeaderOptional;
import com.fastretailing.dcp.sales.common.entity.optional.SalesReportTransactionItemDetailOptional;
import com.fastretailing.dcp.sales.common.entity.optional.SalesReportTransactionTaxOptional;
import com.fastretailing.dcp.sales.common.entity.optional.SalesReportTransactionTenderOptional;
import com.fastretailing.dcp.sales.common.entity.optional.StoreTransactionInquiryCondition;
import com.fastretailing.dcp.sales.common.entity.optional.StoreTransactionInquiryDetail;
import com.fastretailing.dcp.sales.common.entity.optional.TranslationStoreCodeMasterOptional;
import com.fastretailing.dcp.sales.common.entity.optional.TranslationStoreCodeMasterOptionalCondition;
import com.fastretailing.dcp.sales.common.entity.optional.TranslationTenderMasterOptional;
import com.fastretailing.dcp.sales.common.repository.CommonCodeMasterMapper;
import com.fastretailing.dcp.sales.common.repository.optional.BusinessCountryStateSettingMasterOptionalMapper;
import com.fastretailing.dcp.sales.common.repository.optional.ItemMasterOptionalMapper;
import com.fastretailing.dcp.sales.common.repository.optional.StoreTransactionInquiryOptionalMapper;
import com.fastretailing.dcp.sales.common.repository.optional.TranslationStoreCodeMasterOptionalMapper;
import com.fastretailing.dcp.sales.common.type.TransactionType;
import com.fastretailing.dcp.sales.storetransactioninquiry.component.DropDownItemHelper;
import com.fastretailing.dcp.sales.storetransactioninquiry.constant.DropDownItem;
import com.fastretailing.dcp.sales.storetransactioninquiry.form.StoreTransactionInquiryForm;
import com.fastretailing.dcp.sales.storetransactioninquiry.validatioin.StoreTransactionInquiryValidation;


/**
 * Store transaction inquiry service implements.
 */
@Service
public class StoreTransactionInquiryServiceImpl implements StoreTransactionInquiryService {

    private static final DateTimeFormatter DATE_FORMAT_UUUUMMDD =
            DateTimeFormatter.ofPattern("uuuu/MM/dd").withResolverStyle(ResolverStyle.STRICT);

    /** Translation store code master mapper. */
    @Autowired
    private TranslationStoreCodeMasterOptionalMapper translationStoreCodeMasterMapper;

    /** Common code master mapper. */
    @Autowired
    private CommonCodeMasterMapper commonCodeMasterMapper;

    /** Business country state setting master mapper. */
    @Autowired
    private BusinessCountryStateSettingMasterOptionalMapper businessCountryStateSettingMasterOptionalMapper;

    /** Store transaction inquiry validation class. */
    @Autowired
    private StoreTransactionInquiryValidation storeTransactionInquiryValidation;

    /** Store transaction inquiry optional mapper. */
    @Autowired
    private StoreTransactionInquiryOptionalMapper storeTransactionInquiryOptionalMapper;

    /** Item master mapper. */
    @Autowired
    private ItemMasterOptionalMapper itemMasterMapper;

    /** Field id(system_country_code). */
    private static final String FIELD_ID_SYSTEM_COUNTRY_CODE = "system_country_code";

    /** Field id(system_brand_code). */
    private static final String FIELD_ID_SYSTEM_BRAND_CODE = "system_brand_code";

    /** Field id(transaction_type). */
    private static final String FIELD_ID_TRANSACTION_TYPE = "transaction_type";

    /** Field id(taxation type). */
    private static final String FIELD_ID_TAXATION_TYPE = "TAXATION_TYPE";

    /** Field id(discount type). */
    private static final String FIELD_ID_DISCOUNT_TYPE = "Promotion Type Store Discount type";

    /** Discount type mix match. */
    private static final String DISCOUNT_TYPE_MIX_MATCH = "Mix&Match";

    /** Order by clause(display_order). */
    private static final String ORDER_BY_CLAUSE_DISPLAY_ORDER = "display_order";

    /** Get tender group. */
    private static final String GET_TENDER_GROUP = "getTenderGroup";

    /** Get ims tender group. */
    private static final String GET_IMS_TENDER_GROUP = "getImsTenderGroup";

    /** Get tender id. */
    private static final String GET_TENDER_ID = "getTenderId";

    /** Get ims tender id. */
    private static final String GET_IMS_TENDER_ID = "getImsTenderId";

    /** Get non item code. */
    private static final String GET_NONITEM_CODE = "getNonItemCode";

    /** Get pos item name. */
    private static final String GET_POSITEM_NAME = "getPosItemName";

    /** Get type value. */
    private static final String GET_TYPE_VALUE = "getTypeValue";

    /** Get name1. */
    private static final String GET_NAME1 = "getName1";

    /** Minus mark. */
    private static final String MINUS_MARK = "-";

    /** Money format. */
    private static final String MONEY_FORMAT = "#.#";

    /** Splash mark. */
    private static final String SPLASH_MARK = "/";

    /** Quantity code. */
    private static final String QUANTITY_CODE = "N";

    /** Drop down item helper. */
    @Autowired
    private DropDownItemHelper dropDownItemHelper;

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreTransactionInquiryForm displayInitPage(
            StoreTransactionInquiryForm storeTransactionInquiryForm) {
        // Get translation store code data.
        TranslationStoreCodeMasterOptionalCondition translationStoreCodeMasterCondition =
                new TranslationStoreCodeMasterOptionalCondition();
        translationStoreCodeMasterCondition.createCriteria()
                .andStoreCodeEqualTo(storeTransactionInquiryForm.getStoreCode());
        List<TranslationStoreCodeMasterOptional> translationStoreCodeMaster =
                translationStoreCodeMasterMapper
                        .selectByCondition(translationStoreCodeMasterCondition);
        storeTransactionInquiryValidation
                .checkTranslationStoreCodeMaster(translationStoreCodeMaster);
        getInitialInformation(storeTransactionInquiryForm);
        getInitialList(storeTransactionInquiryForm);

        // Set non item master condition.
        StoreTransactionInquiryCondition nonItemMasterCondition =
                new StoreTransactionInquiryCondition();
        nonItemMasterCondition
                .setSystemCountryCode(translationStoreCodeMaster.get(0).getSystemCountryCode());
        nonItemMasterCondition
                .setSystemBrandCode(translationStoreCodeMaster.get(0).getSystemBrandCode());

        // Get non merchandise item list.
        List<StoreTransactionInquiryDetail> nonMerchandiseItemList =
                storeTransactionInquiryOptionalMapper
                        .selectByCountryBrandCode(nonItemMasterCondition);
        storeTransactionInquiryValidation.checkNonItemMaster(nonMerchandiseItemList);
        storeTransactionInquiryForm.setNonMerchandiseItemList(dropDownItemHelper
                .createDropDownList(nonMerchandiseItemList, GET_NONITEM_CODE, GET_POSITEM_NAME));

        // Get discount type list.
        CommonCodeMasterCondition commonCodeMasterCondition = new CommonCodeMasterCondition();
        commonCodeMasterCondition.createCriteria().andTypeIdEqualTo(FIELD_ID_DISCOUNT_TYPE);
        commonCodeMasterCondition.setOrderByClause(ORDER_BY_CLAUSE_DISPLAY_ORDER);
        List<CommonCodeMaster> discountTypeList =
                commonCodeMasterMapper.selectByCondition(commonCodeMasterCondition);
        storeTransactionInquiryValidation.checkCommonCodeMaster(discountTypeList);
        storeTransactionInquiryForm.setDiscountTypeMap(discountTypeList.stream().collect(
                Collectors.toMap(CommonCodeMaster::getTypeValue, CommonCodeMaster::getName1)));
        storeTransactionInquiryForm
                .setSystemBrandCode(translationStoreCodeMaster.get(0).getSystemBrandCode());
        storeTransactionInquiryForm
                .setSystemCountryCode(translationStoreCodeMaster.get(0).getSystemCountryCode());
        List<SalesReportTransactionHeaderOptional> headerDetailList =
                getHeaderDetailList(storeTransactionInquiryForm);
        SalesReportTransactionHeaderOptional currentSalesReportTransactionHeader =
                headerDetailList.get(0);
        storeTransactionInquiryForm.setHeaderDetail(
                createSalesReportTransactionHeaderForInitial(currentSalesReportTransactionHeader));
        return storeTransactionInquiryForm;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreTransactionInquiryForm getStoreTransactionInquiryList(
            StoreTransactionInquiryForm storeTransactionInquiryForm) {
        // Check input value.
        storeTransactionInquiryValidation.checkInputValue(storeTransactionInquiryForm);
        // Get store code.
        TranslationStoreCodeMasterOptionalCondition translationStoreCodeMasterCondition =
                new TranslationStoreCodeMasterOptionalCondition();
        translationStoreCodeMasterCondition.createCriteria()
                .andViewStoreCodeEqualTo(storeTransactionInquiryForm.getStoreCode());
        List<TranslationStoreCodeMasterOptional> storeCodeList = translationStoreCodeMasterMapper
                .selectByCondition(translationStoreCodeMasterCondition);
        storeTransactionInquiryValidation.checkTranslationStoreCodeMaster(storeCodeList);
        // Get tax type.
        BusinessCountryStateSettingMasterOptionalCondition businessCountryStateCondition =
                new BusinessCountryStateSettingMasterOptionalCondition();
        businessCountryStateCondition.createCriteria()
                .andSystemBrandCodeEqualTo(storeTransactionInquiryForm.getSystemBrandCode())
                .andSystemCountryCodeEqualTo(storeTransactionInquiryForm.getSystemCountryCode())
                .andStoreCodeEqualTo(storeCodeList.get(0).getStoreCode())
                .andCodeL1EqualTo(BusinessItem.CODE_L1_TAXTYPE)
                .andCodeL2EqualTo(BusinessItem.CODE_L1_TAXTYPE)
                .andCodeL3EqualTo(BusinessItem.CODE_L3_RTLOG);
        List<BusinessCountryStateSettingMasterOptional> taxTypeList =
                businessCountryStateSettingMasterOptionalMapper
                        .selectByCondition(businessCountryStateCondition);
        storeTransactionInquiryValidation.checkBusinessCountryStateSettingMaster(taxTypeList);
        String taxType = taxTypeList.get(0).getCodeValue();
        if (BusinessItem.TAX_TYPE_VAT.equals(taxType)) {
            storeTransactionInquiryForm.setTaxTypeFlag(true);
        } else {
            storeTransactionInquiryForm.setTaxTypeFlag(false);
        }
        storeTransactionInquiryForm.setTaxType(taxType);
        // Get code value.
        businessCountryStateCondition.clear();
        businessCountryStateCondition.createCriteria()
                .andCodeL1EqualTo(BusinessItem.CODE_L1_DECIMAL)
                .andCodeL2EqualTo(BusinessItem.CODE_L1_DECIMAL)
                .andCodeL3EqualTo(BusinessItem.CODE_L3_FRACTION);
        List<BusinessCountryStateSettingMasterOptional> codeValueList =
                businessCountryStateSettingMasterOptionalMapper
                        .selectByCondition(businessCountryStateCondition);
        storeTransactionInquiryValidation.checkBusinessCountryStateSettingMaster(codeValueList);
        String decimalSet = codeValueList.get(0).getCodeValue();
        storeTransactionInquiryForm.setDecimalFormat(decimalSet);
        // Get transaction count.
        storeTransactionInquiryForm.setNumberOfTransaction(
                storeTransactionInquiryOptionalMapper.selectTransactionIdCount());
        createDetailList(storeTransactionInquiryForm);
        return storeTransactionInquiryForm;

    }

    /**
     * Get header and deposit change detail list.
     * 
     * @param storeTransactionInquiryForm Store transaction inquiry form.
     * @return List sales report transaction header detail.
     */
    private List<SalesReportTransactionHeaderOptional> getHeaderDetailList(
            StoreTransactionInquiryForm storeTransactionInquiryForm) {
        StoreTransactionInquiryCondition storeTransactionInquiryCondition =
                new StoreTransactionInquiryCondition();
        storeTransactionInquiryCondition
                .setSystemBrandCode(storeTransactionInquiryForm.getSystemBrandCode());
        storeTransactionInquiryCondition
                .setSystemCountryCode(storeTransactionInquiryForm.getSystemCountryCode());
        storeTransactionInquiryCondition.setStoreCode(storeTransactionInquiryForm.getStoreCode());
        storeTransactionInquiryCondition
                .setSalesTransactionType(storeTransactionInquiryForm.getSalesTransactionType());
        storeTransactionInquiryCondition
                .setCashRegisterNo(storeTransactionInquiryForm.getCashRegisterNo());
        if (StringUtils.isNotEmpty(storeTransactionInquiryForm.getBusinessDate())) {
            storeTransactionInquiryCondition.setBusinessDate(
                    StringUtils.remove(storeTransactionInquiryForm.getBusinessDate(), SPLASH_MARK));
        }
        if (StringUtils.isNotEmpty(storeTransactionInquiryForm.getDataCreationDateFrom())) {
            storeTransactionInquiryCondition.setDataCreationDateTimeFrom(
                    LocalDate
                            .parse(storeTransactionInquiryForm.getDataCreationDateFrom(),
                                    DATE_FORMAT_UUUUMMDD)
                            .atTime(0, 0, 0));
        }
        if (StringUtils.isNotEmpty(storeTransactionInquiryForm.getDataCreationDateTo())) {
            storeTransactionInquiryCondition.setDataCreationDateTimeTo(
                    LocalDate
                            .parse(storeTransactionInquiryForm.getDataCreationDateTo(),
                                    DATE_FORMAT_UUUUMMDD)
                            .atTime(0, 0, 0));
        }
        storeTransactionInquiryCondition.setCasherCode(storeTransactionInquiryForm.getCasherCode());
        storeTransactionInquiryCondition
                .setMembershipId(storeTransactionInquiryForm.getMembershipId());
        storeTransactionInquiryCondition
                .setReceiptNoFrom(storeTransactionInquiryForm.getReceiptNoFrom());
        storeTransactionInquiryCondition
                .setReceiptNoTo(storeTransactionInquiryForm.getReceiptNoTo());
        storeTransactionInquiryCondition
                .setDepositAmountFrom(storeTransactionInquiryForm.getDepositAmountFrom());
        storeTransactionInquiryCondition
                .setDepositAmountTo(storeTransactionInquiryForm.getDepositAmountTo());
        storeTransactionInquiryCondition
                .setChangeAmountFrom(storeTransactionInquiryForm.getChangeAmountFrom());
        storeTransactionInquiryCondition
                .setChangeAmountTo(storeTransactionInquiryForm.getChangeAmountTo());
        List<SalesReportTransactionHeaderOptional> headerDetailList =
                storeTransactionInquiryOptionalMapper.selectSalesReportTransactionHeaderByCondition(
                        storeTransactionInquiryCondition);
        storeTransactionInquiryValidation.checkStoreTransactionInquiryDetail(headerDetailList);
        return headerDetailList;
    }

    /**
     * Get item detail list.
     * 
     * @param salesReportTransactionHeader Sales report transaction header optional.
     * @param storeTransactionInquiryForm Store transaction inquiry form.
     * @return List sales report transaction detail.
     */
    private List<SalesReportTransactionItemDetailOptional> getItemDetailList(
            SalesReportTransactionHeaderOptional salesReportTransactionHeader,
            StoreTransactionInquiryForm storeTransactionInquiryForm) {
        StoreTransactionInquiryCondition storeTransactionInquiryCondition =
                new StoreTransactionInquiryCondition();
        storeTransactionInquiryCondition
                .setTransactionId(salesReportTransactionHeader.getSalesTransactionId());
        storeTransactionInquiryCondition
                .setTaxationType(storeTransactionInquiryForm.getTaxationType());
        storeTransactionInquiryCondition
                .setNonMerchandiseItem(storeTransactionInquiryForm.getNonMerchandiseItemCode());
        // Convert L1 item code to L3 item code.
        ItemMasterOptionalCondition itemMasterCondition = new ItemMasterOptionalCondition();
        if (StringUtils.isNotEmpty(storeTransactionInquiryForm.getItemCode())) {
            itemMasterCondition.createCriteria()
                    .andViewItemCodeEqualTo(storeTransactionInquiryForm.getItemCode());
            List<ItemMasterOptional> l3ItemCodeList =
                    itemMasterMapper.selectByCondition(itemMasterCondition);
            storeTransactionInquiryCondition.setItemCode(l3ItemCodeList.get(0).getItemCode());
        }
        List<SalesReportTransactionItemDetailOptional> itemDetailList =
                storeTransactionInquiryOptionalMapper.selectSalesReportTransactionDetailByCondition(
                        storeTransactionInquiryCondition);
        return itemDetailList;
    }

    /**
     * Get discount detail list.
     * 
     * @param salesReportTransactionHeader Sales report transaction header optional.
     * @param storeTransactionInquiryForm Store transaction inquiry form.
     * @return List sales report transaction discount.
     */
    private List<SalesReportTransactionDiscountDetailOptional> getDiscountDetailList(
            SalesReportTransactionHeaderOptional salesReportTransactionHeader,
            StoreTransactionInquiryForm storeTransactionInquiryForm) {
        StoreTransactionInquiryCondition storeTransactionInquiryCondition =
                new StoreTransactionInquiryCondition();
        storeTransactionInquiryCondition
                .setTransactionId(salesReportTransactionHeader.getSalesTransactionId());
        // Discount Type.
        storeTransactionInquiryCondition
                .setDiscountType(storeTransactionInquiryForm.getDiscountType());

        List<SalesReportTransactionDiscountDetailOptional> discountDetailList =
                storeTransactionInquiryOptionalMapper
                        .selectSalesReportTransactionDiscountDetailByCondition(
                                storeTransactionInquiryCondition);
        return discountDetailList;
    }

    /**
     * Get tax detail list.
     * 
     * @param salesReportTransactionHeader Sales report transaction header optional.
     * @return List sales report transaction tax.
     */
    private List<SalesReportTransactionTaxOptional> getTaxDetailList(
            SalesReportTransactionHeaderOptional salesReportTransactionHeader) {

        StoreTransactionInquiryCondition storeTransactionInquiryCondition =
                new StoreTransactionInquiryCondition();
        storeTransactionInquiryCondition
                .setTransactionId(salesReportTransactionHeader.getSalesTransactionId());
        List<SalesReportTransactionTaxOptional> taxDetailList =
                storeTransactionInquiryOptionalMapper
                        .selectSalesReportTransactionTaxDetailByCondition(
                                storeTransactionInquiryCondition);
        return taxDetailList;
    }

    /**
     * Get payment detail list.
     * 
     * @param storeTransactionInquiryDetailList Store transaction inquiry detail list.
     * @param storeTransactionInquiryForm Store transaction inquiry form.
     * @return List sales report transaction detail.
     */
    private List<SalesReportTransactionTenderOptional> getPaymentDetailList(
            SalesReportTransactionHeaderOptional salesReportTransactionHeader,
            StoreTransactionInquiryForm storeTransactionInquiryForm) {
        StoreTransactionInquiryCondition storeTransactionInquiryCondition =
                new StoreTransactionInquiryCondition();
        storeTransactionInquiryCondition
                .setTransactionId(salesReportTransactionHeader.getSalesTransactionId());
        storeTransactionInquiryCondition
                .setPaymentTenderGroup(storeTransactionInquiryForm.getPaymentTenderGroup());
        storeTransactionInquiryCondition
                .setPaymentTenderId(storeTransactionInquiryForm.getPaymentTenderId());
        if (StringUtils.isNotEmpty(storeTransactionInquiryForm.getPaymentAmountFrom())
                && StringUtils.isNotEmpty(storeTransactionInquiryForm.getPaymentAmountTo())) {
            storeTransactionInquiryCondition.setPaymentAmountFrom(
                    new BigDecimal(storeTransactionInquiryForm.getPaymentAmountFrom()));
            storeTransactionInquiryCondition.setPaymentAmountTo(
                    new BigDecimal(storeTransactionInquiryForm.getPaymentAmountTo()));
        }
        List<SalesReportTransactionTenderOptional> paymentDetailList =
                storeTransactionInquiryOptionalMapper
                        .selectSalesReportTransactionTenderDetailByCondition(
                                storeTransactionInquiryCondition);
        return paymentDetailList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DropDownItem> changeBrand(StoreTransactionInquiryForm storeTransactionInquiryForm,
            BindingResult bindingResult, Model model) {
        StoreTransactionInquiryCondition storeTransactionInquiryCondition =
                new StoreTransactionInquiryCondition();
        storeTransactionInquiryCondition
                .setSystemBrandCode(storeTransactionInquiryForm.getSystemBrandCode());
        storeTransactionInquiryCondition
                .setSystemCountryCode(storeTransactionInquiryForm.getSystemCountryCode());
        // Get non merchandise item list.
        List<StoreTransactionInquiryDetail> nonMerchandiseItemList =
                storeTransactionInquiryOptionalMapper
                        .selectByCountryBrandCode(storeTransactionInquiryCondition);
        storeTransactionInquiryForm.setNonMerchandiseItemList(dropDownItemHelper
                .createDropDownList(nonMerchandiseItemList, GET_NONITEM_CODE, GET_POSITEM_NAME));
        List<DropDownItem> dropdownItemList = new ArrayList<>();
        // Add value to list.
        dropdownItemList.addAll(dropDownItemHelper.createDropDownList(nonMerchandiseItemList,
                GET_NONITEM_CODE, GET_POSITEM_NAME));
        return dropdownItemList;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreTransactionInquiryForm getPreviousItemDetail(
            StoreTransactionInquiryForm storeTransactionInquiryForm) {

        int countIndex = storeTransactionInquiryForm.getCountIndex();
        countIndex--;
        // Create header List.
        List<SalesReportTransactionHeaderOptional> headerDetailList =
                getHeaderDetailList(storeTransactionInquiryForm);
        SalesReportTransactionHeaderOptional currentSalesReportTransactionHeader =
                headerDetailList.get(countIndex);
        // Create item list.
        List<SalesReportTransactionItemDetailOptional> itemDetailList =
                getItemDetailList(currentSalesReportTransactionHeader, storeTransactionInquiryForm);
        // Create discount list
        List<SalesReportTransactionDiscountDetailOptional> discountDetailList =
                getDiscountDetailList(currentSalesReportTransactionHeader,
                        storeTransactionInquiryForm);
        // Create tax list.
        List<SalesReportTransactionTaxOptional> taxDetailList =
                getTaxDetailList(currentSalesReportTransactionHeader);
        // Create payment list.
        List<SalesReportTransactionTenderOptional> paymentDetailList = getPaymentDetailList(
                currentSalesReportTransactionHeader, storeTransactionInquiryForm);
        storeTransactionInquiryForm.setHeaderDetail(createSalesReportTransactionHeader(
                currentSalesReportTransactionHeader, storeTransactionInquiryForm));
        storeTransactionInquiryForm
                .setItemList(createItemDetailList(itemDetailList, storeTransactionInquiryForm));
        storeTransactionInquiryForm.setDiscountList(
                createDiscountDetailList(discountDetailList, storeTransactionInquiryForm));
        storeTransactionInquiryForm
                .setTaxList(createTaxDetailList(taxDetailList, storeTransactionInquiryForm));
        storeTransactionInquiryForm.setPaymentList(
                createPaymentDetailList(paymentDetailList, storeTransactionInquiryForm));
        storeTransactionInquiryForm.setCountIndex(countIndex);
        storeTransactionInquiryForm.setPreviousNextFlag(true);
        return storeTransactionInquiryForm;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreTransactionInquiryForm getNextItemDetail(
            StoreTransactionInquiryForm storeTransactionInquiryForm) {

        int countIndex = storeTransactionInquiryForm.getCountIndex();
        storeTransactionInquiryForm.setPreviousNextFlag(true);
        countIndex++;
        // Create header List.
        List<SalesReportTransactionHeaderOptional> headerDetailList =
                getHeaderDetailList(storeTransactionInquiryForm);
        SalesReportTransactionHeaderOptional currentSalesReportTransactionHeader =
                headerDetailList.get(countIndex);
        // Create item list.
        List<SalesReportTransactionItemDetailOptional> itemDetailList =
                getItemDetailList(currentSalesReportTransactionHeader, storeTransactionInquiryForm);
        // Create discount list
        List<SalesReportTransactionDiscountDetailOptional> discountDetailList =
                getDiscountDetailList(currentSalesReportTransactionHeader,
                        storeTransactionInquiryForm);
        // Create tax list.
        List<SalesReportTransactionTaxOptional> taxDetailList =
                getTaxDetailList(currentSalesReportTransactionHeader);
        // Create payment list.
        List<SalesReportTransactionTenderOptional> paymentDetailList = getPaymentDetailList(
                currentSalesReportTransactionHeader, storeTransactionInquiryForm);
        storeTransactionInquiryForm.setHeaderDetail(createSalesReportTransactionHeader(
                currentSalesReportTransactionHeader, storeTransactionInquiryForm));
        storeTransactionInquiryForm
                .setItemList(createItemDetailList(itemDetailList, storeTransactionInquiryForm));
        storeTransactionInquiryForm
                .setItemList(createItemDetailList(itemDetailList, storeTransactionInquiryForm));
        storeTransactionInquiryForm.setDiscountList(
                createDiscountDetailList(discountDetailList, storeTransactionInquiryForm));
        storeTransactionInquiryForm
                .setTaxList(createTaxDetailList(taxDetailList, storeTransactionInquiryForm));
        storeTransactionInquiryForm.setPaymentList(
                createPaymentDetailList(paymentDetailList, storeTransactionInquiryForm));
        storeTransactionInquiryForm.setCountIndex(countIndex);
        if (headerDetailList.size() - 1 <= countIndex) {
            storeTransactionInquiryForm.setPreviousNextFlag(false);
        }
        return storeTransactionInquiryForm;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DropDownItem> changeStoreForTenderGroup(
            StoreTransactionInquiryForm storeTransactionInquiryForm, BindingResult bindingResult,
            Model model) {
        StoreTransactionInquiryCondition storeTransactionInquiryCondition =
                new StoreTransactionInquiryCondition();
        storeTransactionInquiryCondition.setStoreCode(storeTransactionInquiryForm.getStoreCode());

        // Get payment tender group list.
        List<TranslationTenderMasterOptional> paymentTenderGroupList =
                storeTransactionInquiryOptionalMapper
                        .selectPaymentTenderGroup(storeTransactionInquiryCondition);
        storeTransactionInquiryForm.setPaymentTenderGroupList(dropDownItemHelper.createDropDownList(
                paymentTenderGroupList, GET_TENDER_GROUP, GET_IMS_TENDER_GROUP));

        List<DropDownItem> dropDownItemList = new ArrayList<>();
        // Add value to list.
        dropDownItemList.addAll(dropDownItemHelper.createDropDownList(paymentTenderGroupList,
                GET_TENDER_GROUP, GET_IMS_TENDER_GROUP));
        return dropDownItemList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DropDownItem> changeStoreForTenderId(
            StoreTransactionInquiryForm storeTransactionInquiryForm, BindingResult bindingResult,
            Model model) {
        StoreTransactionInquiryCondition storeTransactionInquiryCondition =
                new StoreTransactionInquiryCondition();
        storeTransactionInquiryCondition.setStoreCode(storeTransactionInquiryForm.getStoreCode());
        // Get payment tender id list.
        List<TranslationTenderMasterOptional> paymentTenderIdList =
                storeTransactionInquiryOptionalMapper
                        .selectPaymentTenderId(storeTransactionInquiryCondition);
        storeTransactionInquiryForm.setPaymentTenderIdList(dropDownItemHelper
                .createDropDownList(paymentTenderIdList, GET_TENDER_ID, GET_IMS_TENDER_ID));
        List<DropDownItem> dropDownItemList = new ArrayList<>();
        // Add value to list.
        dropDownItemList.addAll(dropDownItemHelper.createDropDownList(paymentTenderIdList,
                GET_TENDER_ID, GET_IMS_TENDER_ID));
        return dropDownItemList;
    }

    /**
     * Create sales report transaction header.
     * 
     * @param salesReportTransactionHeader Sales report transaction header optional.
     * @param storeTransactionInquiryForm Store transaction inquiry form.
     * @return Store transaction inquiry detail.
     */
    private StoreTransactionInquiryDetail createSalesReportTransactionHeader(
            SalesReportTransactionHeaderOptional salesReportTransactionHeader,
            StoreTransactionInquiryForm storeTransactionInquiryForm) {
        StoreTransactionInquiryDetail storeTransactionInquiryDetailForHeader =
                new StoreTransactionInquiryDetail();
        storeTransactionInquiryDetailForHeader
                .setImsLinkageDate(salesReportTransactionHeader.getImsLinkageDate());
        storeTransactionInquiryDetailForHeader
                .setDataCreationDateTime(salesReportTransactionHeader.getDataCreationDateTime());
        storeTransactionInquiryDetailForHeader
                .setStoreCode(salesReportTransactionHeader.getStoreCode());
        storeTransactionInquiryDetailForHeader
                .setCashRegisterNo(salesReportTransactionHeader.getCashRegisterNo());
        storeTransactionInquiryDetailForHeader
                .setReceiptNo(salesReportTransactionHeader.getReceiptNo());
        storeTransactionInquiryDetailForHeader
                .setOperatorCode(salesReportTransactionHeader.getOperatorCode());
        storeTransactionInquiryDetailForHeader
                .setSalesTransactionType(salesReportTransactionHeader.getSalesTransactionType());
        storeTransactionInquiryDetailForHeader
                .setChangeCurrencyCode(salesReportTransactionHeader.getChangeCurrencyCode());
        storeTransactionInquiryDetailForHeader
                .setDepositCurrencyCode(salesReportTransactionHeader.getDepositCurrencyCode());
        StringBuilder minusMarkForDeposit = new StringBuilder(MINUS_MARK);
        StringBuilder minusMarkForChange = new StringBuilder(MINUS_MARK);
        DecimalFormat decimalFormat = new DecimalFormat(MONEY_FORMAT);
        decimalFormat.setMinimumFractionDigits(
                Integer.parseInt(storeTransactionInquiryForm.getDecimalFormat()));
        if (TransactionType.RETURN.getTransactionType()
                .equals(salesReportTransactionHeader.getSalesTransactionType())
                || TransactionType.PVOID.getTransactionType()
                        .equals(salesReportTransactionHeader.getSalesTransactionType())) {
            storeTransactionInquiryDetailForHeader.setDepositValue(minusMarkForDeposit
                    .append((decimalFormat.format(salesReportTransactionHeader.getDepositValue())))
                    .toString());
            storeTransactionInquiryDetailForHeader.setChangeValue(minusMarkForChange
                    .append((decimalFormat.format(salesReportTransactionHeader.getChangeValue())))
                    .toString());
        } else {
            storeTransactionInquiryDetailForHeader.setDepositValue(
                    decimalFormat.format(salesReportTransactionHeader.getDepositValue()));
            storeTransactionInquiryDetailForHeader.setChangeValue(
                    decimalFormat.format(salesReportTransactionHeader.getChangeValue()));
        }
        return storeTransactionInquiryDetailForHeader;
    }

    /**
     * Create sales report transaction header for initial.
     * 
     * @param salesReportTransactionHeader Sales report transaction header optional.
     * @return Store transaction inquiry detail.
     */
    private StoreTransactionInquiryDetail createSalesReportTransactionHeaderForInitial(
            SalesReportTransactionHeaderOptional salesReportTransactionHeader) {
        StoreTransactionInquiryDetail storeTransactionInquiryDetailForHeader =
                new StoreTransactionInquiryDetail();
        storeTransactionInquiryDetailForHeader
                .setImsLinkageDate(salesReportTransactionHeader.getImsLinkageDate());
        storeTransactionInquiryDetailForHeader
                .setDataCreationDateTime(salesReportTransactionHeader.getDataCreationDateTime());
        storeTransactionInquiryDetailForHeader
                .setStoreCode(salesReportTransactionHeader.getStoreCode());
        storeTransactionInquiryDetailForHeader
                .setCashRegisterNo(salesReportTransactionHeader.getCashRegisterNo());
        storeTransactionInquiryDetailForHeader
                .setReceiptNo(salesReportTransactionHeader.getReceiptNo());
        storeTransactionInquiryDetailForHeader
                .setOperatorCode(salesReportTransactionHeader.getOperatorCode());
        storeTransactionInquiryDetailForHeader
                .setSalesTransactionType(salesReportTransactionHeader.getSalesTransactionType());
        if (salesReportTransactionHeader.getDepositValue() != null) {
            storeTransactionInquiryDetailForHeader
                    .setDepositValue(salesReportTransactionHeader.getDepositValue().toString());
        }
        if (salesReportTransactionHeader.getChangeValue() != null) {
            storeTransactionInquiryDetailForHeader
                    .setChangeValue(salesReportTransactionHeader.getChangeValue().toString());
        }
        return storeTransactionInquiryDetailForHeader;
    }

    /**
     * Create item detail list.
     * 
     * @param itemDetailList Item detail list.
     * @param storeTransactionInquiryForm Store transaction inquiry form.
     * @return Store transaction inquiry detail list.
     */
    private List<StoreTransactionInquiryDetail> createItemDetailList(
            List<SalesReportTransactionItemDetailOptional> itemDetailList,
            StoreTransactionInquiryForm storeTransactionInquiryForm) {
        return itemDetailList.stream().map(salesReportTransactionDetail -> {
            StoreTransactionInquiryDetail storeTransactionInquiryDetail =
                    new StoreTransactionInquiryDetail();
            storeTransactionInquiryDetail
                    .setDetailSubNumber(salesReportTransactionDetail.getDetailSubNumber());
            storeTransactionInquiryDetail.setNonMdCode(salesReportTransactionDetail.getNonMdCode());
            storeTransactionInquiryDetail
                    .setL3PosProductName(salesReportTransactionDetail.getL3PosProductName());
            StringBuilder minusMark = new StringBuilder(MINUS_MARK);
            DecimalFormat decimalFormat = new DecimalFormat(MONEY_FORMAT);
            decimalFormat.setMinimumFractionDigits(
                    Integer.parseInt(storeTransactionInquiryForm.getDecimalFormat()));
            if (QUANTITY_CODE.equals(salesReportTransactionDetail.getQuantityCode())) {
                storeTransactionInquiryDetail.setDetailQuantity(
                        minusMark.append(salesReportTransactionDetail.getDetailQuantity())
                                .toString());
            } else {
                storeTransactionInquiryDetail.setDetailQuantity(
                        salesReportTransactionDetail.getDetailQuantity().toString());
            }
            if (storeTransactionInquiryForm.getTaxTypeFlag() == true) {
                storeTransactionInquiryDetail.setRetailUnitPriceTaxCurrencyCode(
                        salesReportTransactionDetail.getRetailUnitPriceTaxIncludedCurrencyCode());
                storeTransactionInquiryDetail.setRetailUnitPriceTax(decimalFormat
                        .format(salesReportTransactionDetail.getRetailUnitPriceTaxIncluded()
                                .multiply(salesReportTransactionDetail.getDetailQuantity())));
            } else {
                storeTransactionInquiryDetail.setRetailUnitPriceTaxCurrencyCode(
                        salesReportTransactionDetail.getRetailUnitPriceTaxExcludedCurrencyCode());
                storeTransactionInquiryDetail.setRetailUnitPriceTax(decimalFormat
                        .format(salesReportTransactionDetail.getRetailUnitPriceTaxExcluded()
                                .multiply(salesReportTransactionDetail.getDetailQuantity())));
            }
            return storeTransactionInquiryDetail;
        }).collect(Collectors.toList());
    }

    /**
     * Create discount detail list.
     * 
     * @param discountDetailList Discount detail list.
     * @param storeTransactionInquiryForm Store transaction inquiry form.
     * @return Store transaction inquiry detail list.
     */
    private List<StoreTransactionInquiryDetail> createDiscountDetailList(
            List<SalesReportTransactionDiscountDetailOptional> discountDetailList,
            StoreTransactionInquiryForm storeTransactionInquiryForm) {
        return discountDetailList.stream().map(salesReportTransactionDiscount -> {
            StoreTransactionInquiryDetail storeTransactionInquiryDetail =
                    new StoreTransactionInquiryDetail();
            if (StringUtils.equals(
                    salesReportTransactionDiscount.getPromotionType()
                            + salesReportTransactionDiscount.getStoreDiscountType(),
                    BusinessItem.DISCOUNT_TYPE_MIX_MATCH)) {
                storeTransactionInquiryDetail.setDiscountType(DISCOUNT_TYPE_MIX_MATCH);
            }
            storeTransactionInquiryDetail
                    .setDetailSubNumber(salesReportTransactionDiscount.getDetailSubNumber());
            DecimalFormat decimalFormat = new DecimalFormat(MONEY_FORMAT);
            decimalFormat.setMinimumFractionDigits(
                    Integer.parseInt(storeTransactionInquiryForm.getDecimalFormat()));
            if (storeTransactionInquiryForm.getTaxTypeFlag() == true) {
                storeTransactionInquiryDetail.setDiscountAmountTaxCurrencyCode(
                        salesReportTransactionDiscount.getDiscountAmountTaxIncludedCurrencyCode());
                storeTransactionInquiryDetail.setDiscountAmountTax(decimalFormat
                        .format(salesReportTransactionDiscount.getDiscountAmountTaxIncluded()
                                .multiply(new BigDecimal(
                                        salesReportTransactionDiscount.getDiscountQuantity())))
                        .toString());
            } else {
                storeTransactionInquiryDetail.setDiscountAmountTaxCurrencyCode(
                        salesReportTransactionDiscount.getDiscountAmountTaxExcludedCurrencyCode());
                storeTransactionInquiryDetail.setDiscountAmountTax(decimalFormat
                        .format(salesReportTransactionDiscount.getDiscountAmountTaxExcluded()
                                .multiply(new BigDecimal(
                                        salesReportTransactionDiscount.getDiscountQuantity())))
                        .toString());
            }
            return storeTransactionInquiryDetail;
        }).collect(Collectors.toList());
    }

    /**
     * Create tax detail list.
     * 
     * @param taxDetailList Tax detail list.
     * @param storeTransactionInquiryForm Store transaction inquiry form.
     * @return Store transaction inquiry detail list.
     */
    private List<StoreTransactionInquiryDetail> createTaxDetailList(
            List<SalesReportTransactionTaxOptional> taxDetailList,
            StoreTransactionInquiryForm storeTransactionInquiryForm) {
        return taxDetailList.stream().map(salesReportTransactionDetail -> {
            StoreTransactionInquiryDetail storeTransactionInquiryDetail =
                    new StoreTransactionInquiryDetail();
            storeTransactionInquiryDetail.setTaxGroup(salesReportTransactionDetail.getTaxGroup());
            storeTransactionInquiryDetail.setTaxAmountCurrencyCode(
                    salesReportTransactionDetail.getTaxAmountCurrencyCode());
            StringBuilder minusMark = new StringBuilder(MINUS_MARK);
            DecimalFormat decimalFormat = new DecimalFormat(MONEY_FORMAT);
            decimalFormat.setMinimumFractionDigits(
                    Integer.parseInt(storeTransactionInquiryForm.getDecimalFormat()));
            if (QUANTITY_CODE.equals(salesReportTransactionDetail.getTaxAmountSign())) {
                storeTransactionInquiryDetail.setTaxAmountValue(minusMark
                        .append(decimalFormat
                                .format(salesReportTransactionDetail.getTaxAmountValue()))
                        .toString());
            } else {
                storeTransactionInquiryDetail.setTaxAmountValue(
                        decimalFormat.format(salesReportTransactionDetail.getTaxAmountValue())
                                .toString());
            }
            return storeTransactionInquiryDetail;
        }).collect(Collectors.toList());
    }

    /**
     * Create Payment detail list.
     * 
     * @param paymentDetailList Payment detail list.
     * @param storeTransactionInquiryForm Store transaction inquiry form.
     * @return Store transaction inquiry detail list.
     */
    private List<StoreTransactionInquiryDetail> createPaymentDetailList(
            List<SalesReportTransactionTenderOptional> paymentDetailList,
            StoreTransactionInquiryForm storeTransactionInquiryForm) {
        return paymentDetailList.stream().map(salesReportTransactionDetail -> {
            StoreTransactionInquiryDetail storeTransactionInquiryDetail =
                    new StoreTransactionInquiryDetail();
            storeTransactionInquiryDetail
                    .setImsTenderGroup(salesReportTransactionDetail.getTenderGroup());
            storeTransactionInquiryDetail.setTaxIncludedPaymentAmountCurrencyCode(
                    salesReportTransactionDetail.getTaxIncludedPaymentAmountCurrencyCode());
            StringBuilder minusMark = new StringBuilder(MINUS_MARK);
            DecimalFormat decimalFormat = new DecimalFormat(MONEY_FORMAT);
            decimalFormat.setMinimumFractionDigits(
                    Integer.parseInt(storeTransactionInquiryForm.getDecimalFormat()));
            if (QUANTITY_CODE.equals(salesReportTransactionDetail.getPaymentSign())) {
                storeTransactionInquiryDetail.setTaxAmountValue(minusMark
                        .append((decimalFormat.format(
                                salesReportTransactionDetail.getTaxIncludedPaymentAmountValue())))
                        .toString());
            } else {
                storeTransactionInquiryDetail.setTaxAmountValue(decimalFormat
                        .format(salesReportTransactionDetail.getTaxIncludedPaymentAmountValue())
                        .toString());
            }
            return storeTransactionInquiryDetail;
        }).collect(Collectors.toList());
    }

    /**
     * Create detail list.
     * 
     * @param storeTransactionInquiryForm Store transaction inquiry form.
     */
    private void createDetailList(StoreTransactionInquiryForm storeTransactionInquiryForm) {
        // Create header List.
        List<SalesReportTransactionHeaderOptional> headerDetailList =
                getHeaderDetailList(storeTransactionInquiryForm);
        storeTransactionInquiryValidation.checkStoreTransactionInquiryDetail(headerDetailList);
        SalesReportTransactionHeaderOptional currentSalesReportTransactionHeader =
                headerDetailList.get(storeTransactionInquiryForm.getCountIndex());
        if (headerDetailList.size() > 1) {
            storeTransactionInquiryForm.setPreviousNextFlag(true);
        }
        // Create item list.
        List<SalesReportTransactionItemDetailOptional> itemDetailList =
                getItemDetailList(currentSalesReportTransactionHeader, storeTransactionInquiryForm);
        // Create discount list
        List<SalesReportTransactionDiscountDetailOptional> discountDetailList =
                getDiscountDetailList(currentSalesReportTransactionHeader,
                        storeTransactionInquiryForm);
        // Create tax list.
        List<SalesReportTransactionTaxOptional> taxDetailList =
                getTaxDetailList(currentSalesReportTransactionHeader);
        // Create payment list.
        List<SalesReportTransactionTenderOptional> paymentDetailList = getPaymentDetailList(
                currentSalesReportTransactionHeader, storeTransactionInquiryForm);
        storeTransactionInquiryForm.setHeaderDetailList(headerDetailList);
        storeTransactionInquiryForm.setHeaderDetail(createSalesReportTransactionHeader(
                currentSalesReportTransactionHeader, storeTransactionInquiryForm));
        storeTransactionInquiryForm
                .setItemList(createItemDetailList(itemDetailList, storeTransactionInquiryForm));
        storeTransactionInquiryForm.setDiscountList(
                createDiscountDetailList(discountDetailList, storeTransactionInquiryForm));
        storeTransactionInquiryForm
                .setTaxList(createTaxDetailList(taxDetailList, storeTransactionInquiryForm));
        storeTransactionInquiryForm.setPaymentList(
                createPaymentDetailList(paymentDetailList, storeTransactionInquiryForm));
    }

    /**
     * Get initial information.
     * 
     * @param storeTransactionInquiryForm Store transaction inquiry form.
     */
    private void getInitialInformation(StoreTransactionInquiryForm storeTransactionInquiryForm) {
        // Create common code master condition.
        CommonCodeMasterCondition commonCodeMasterCondition = new CommonCodeMasterCondition();
        commonCodeMasterCondition.createCriteria().andTypeIdEqualTo(FIELD_ID_SYSTEM_BRAND_CODE);
        commonCodeMasterCondition.setOrderByClause(ORDER_BY_CLAUSE_DISPLAY_ORDER);
        // Get system brand list.
        List<CommonCodeMaster> systemBrandList =
                commonCodeMasterMapper.selectByCondition(commonCodeMasterCondition);
        storeTransactionInquiryValidation.checkCommonCodeMaster(systemBrandList);
        storeTransactionInquiryForm.setBrandList(
                dropDownItemHelper.createDropDownList(systemBrandList, GET_TYPE_VALUE, GET_NAME1));
        commonCodeMasterCondition.clear();
        commonCodeMasterCondition.createCriteria().andTypeIdEqualTo(FIELD_ID_SYSTEM_COUNTRY_CODE);
        commonCodeMasterCondition.setOrderByClause(ORDER_BY_CLAUSE_DISPLAY_ORDER);

        // Get system country list.
        List<CommonCodeMaster> systemCountryList =
                commonCodeMasterMapper.selectByCondition(commonCodeMasterCondition);
        storeTransactionInquiryValidation.checkCommonCodeMaster(systemCountryList);
        storeTransactionInquiryForm.setCountryList(dropDownItemHelper
                .createDropDownList(systemCountryList, GET_TYPE_VALUE, GET_NAME1));
        commonCodeMasterCondition.clear();
        commonCodeMasterCondition.createCriteria().andTypeIdEqualTo(FIELD_ID_TRANSACTION_TYPE);
        commonCodeMasterCondition.setOrderByClause(ORDER_BY_CLAUSE_DISPLAY_ORDER);

        // Get transaction type list.
        List<CommonCodeMaster> transactionTypeList =
                commonCodeMasterMapper.selectByCondition(commonCodeMasterCondition);
        storeTransactionInquiryValidation.checkCommonCodeMaster(transactionTypeList);
        storeTransactionInquiryForm.setSalesTransactionTypeMap(transactionTypeList.stream().collect(
                Collectors.toMap(CommonCodeMaster::getTypeValue, CommonCodeMaster::getName1)));

    }

    /**
     * Get initial list.
     * 
     * @param storeTransactionInquiryForm Store transaction inquiry form.
     */
    private void getInitialList(StoreTransactionInquiryForm storeTransactionInquiryForm) {
        StoreTransactionInquiryCondition storeTransactionInquiryCondition =
                new StoreTransactionInquiryCondition();
        storeTransactionInquiryCondition
                .setStoreCode(storeTransactionInquiryForm.getLoginStoreCode());
        // Get payment tender group list.
        List<TranslationTenderMasterOptional> paymentTenderGroupList =
                storeTransactionInquiryOptionalMapper
                        .selectPaymentTenderGroup(storeTransactionInquiryCondition);
        storeTransactionInquiryValidation.checkTranslationTenderMaster(paymentTenderGroupList);
        storeTransactionInquiryForm.setPaymentTenderGroupList(dropDownItemHelper.createDropDownList(
                paymentTenderGroupList, GET_TENDER_GROUP, GET_IMS_TENDER_GROUP));

        // Get payment tender id list.
        List<TranslationTenderMasterOptional> paymentTenderIdList =
                storeTransactionInquiryOptionalMapper
                        .selectPaymentTenderId(storeTransactionInquiryCondition);
        storeTransactionInquiryValidation.checkTranslationTenderMaster(paymentTenderIdList);
        storeTransactionInquiryForm.setPaymentTenderIdList(dropDownItemHelper
                .createDropDownList(paymentTenderIdList, GET_TENDER_ID, GET_IMS_TENDER_ID));

        // Get taxation type list.
        CommonCodeMasterCondition commonCodeMasterCondition = new CommonCodeMasterCondition();
        commonCodeMasterCondition.createCriteria().andTypeIdEqualTo(FIELD_ID_TAXATION_TYPE);
        commonCodeMasterCondition.setOrderByClause(ORDER_BY_CLAUSE_DISPLAY_ORDER);
        List<CommonCodeMaster> taxationTypeList =
                commonCodeMasterMapper.selectByCondition(commonCodeMasterCondition);
        storeTransactionInquiryValidation.checkCommonCodeMaster(taxationTypeList);
        storeTransactionInquiryForm.setTaxationTypeMap(taxationTypeList.stream().collect(
                Collectors.toMap(CommonCodeMaster::getTypeValue, CommonCodeMaster::getName1)));
    }

}
