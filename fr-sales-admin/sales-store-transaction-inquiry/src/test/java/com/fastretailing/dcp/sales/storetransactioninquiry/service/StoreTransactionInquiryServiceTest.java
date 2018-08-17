/**
 * @(#)StoreTransactionInquiryServiceTest.java
 *
 *                                             Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.storetransactioninquiry.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;
import java.beans.PropertyEditor;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import com.fastretailing.dcp.common.exception.BusinessException;
import com.fastretailing.dcp.sales.common.constants.MessagePrefix;
import com.fastretailing.dcp.sales.common.entity.CommonCodeMaster;
import com.fastretailing.dcp.sales.common.entity.NonItemMaster;
import com.fastretailing.dcp.sales.common.entity.optional.BusinessCountryStateSettingMasterOptional;
import com.fastretailing.dcp.sales.common.entity.optional.ItemMasterOptional;
import com.fastretailing.dcp.sales.common.entity.optional.SalesReportTransactionDiscountDetailOptional;
import com.fastretailing.dcp.sales.common.entity.optional.SalesReportTransactionHeaderOptional;
import com.fastretailing.dcp.sales.common.entity.optional.SalesReportTransactionItemDetailOptional;
import com.fastretailing.dcp.sales.common.entity.optional.SalesReportTransactionTaxOptional;
import com.fastretailing.dcp.sales.common.entity.optional.SalesReportTransactionTenderOptional;
import com.fastretailing.dcp.sales.common.entity.optional.StoreTransactionInquiryDetail;
import com.fastretailing.dcp.sales.common.entity.optional.TranslationStoreCodeMasterOptional;
import com.fastretailing.dcp.sales.common.entity.optional.TranslationTenderMasterOptional;
import com.fastretailing.dcp.sales.common.repository.CommonCodeMasterMapper;
import com.fastretailing.dcp.sales.common.repository.NonItemMasterMapper;
import com.fastretailing.dcp.sales.common.repository.optional.BusinessCountryStateSettingMasterOptionalMapper;
import com.fastretailing.dcp.sales.common.repository.optional.ItemMasterOptionalMapper;
import com.fastretailing.dcp.sales.common.repository.optional.StoreTransactionInquiryOptionalMapper;
import com.fastretailing.dcp.sales.common.repository.optional.TranslationStoreCodeMasterOptionalMapper;
import com.fastretailing.dcp.sales.storetransactioninquiry.constant.DropDownItem;
import com.fastretailing.dcp.sales.storetransactioninquiry.form.StoreTransactionInquiryForm;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StoreTransactionInquiryServiceTest {

    /** Store transaction inquiry service. */
    @SpyBean
    private StoreTransactionInquiryService storeTransactionInquiryService;

    /** Common code master mapper. */
    @MockBean
    private CommonCodeMasterMapper commonCodeMasterMapper;

    /** Translation store code master optional mapper. */
    @MockBean
    private TranslationStoreCodeMasterOptionalMapper translationStoreCodeMasterOptionalMapper;

    /** Non item master mapper. */
    @MockBean
    private NonItemMasterMapper nonItemMasterMapper;

    /** Model. */
    private Model model;

    /** Binding result. */
    private BindingResult bindingResult;

    /** Business country state setting master optional mapper. */
    @MockBean
    private BusinessCountryStateSettingMasterOptionalMapper businessCountryStateSettingMasterOptionalMapper;

    /** Store transaction inquiry optional mapper. */
    @MockBean
    private StoreTransactionInquiryOptionalMapper storeTransactionInquiryOptionalMapper;

    /** Item master mapper. */
    @MockBean
    private ItemMasterOptionalMapper itemMasterMapper;

    /** Business exception. */
    private BusinessException businessException;

    /** Store transaction inquiry form. */
    private StoreTransactionInquiryForm storeTransactionInquiryForm;

    /**
     * Setup data before test.
     */
    @Before
    public void setUp() throws Exception {
        storeTransactionInquiryForm = new StoreTransactionInquiryForm();
        storeTransactionInquiryForm.setStoreCode("112326");
        storeTransactionInquiryForm.setSystemBrandCode("UQ");
        storeTransactionInquiryForm.setSystemCountryCode("UK");
        model = new Model() {
            @Override
            public Model mergeAttributes(Map<String, ?> attributes) {

                return null;
            }

            @Override
            public boolean containsAttribute(String attributeName) {

                return false;
            }

            @Override
            public Map<String, Object> asMap() {
                return null;
            }

            @Override
            public Model addAttribute(String attributeName, Object attributeValue) {

                return null;
            }

            @Override
            public Model addAttribute(Object attributeValue) {
                return null;
            }

            @Override
            public Model addAllAttributes(Map<String, ?> attributes) {

                return null;
            }

            @Override
            public Model addAllAttributes(Collection<?> attributeValues) {

                return null;
            }
        };
        bindingResult = new BindingResult() {
            @Override
            public void setNestedPath(String nestedPath) {}

            @Override
            public void rejectValue(String field, String errorCode, Object[] errorArgs,
                    String defaultMessage) {}

            @Override
            public void rejectValue(String field, String errorCode, String defaultMessage) {}

            @Override
            public void rejectValue(String field, String errorCode) {}

            @Override
            public void reject(String errorCode, Object[] errorArgs, String defaultMessage) {}

            @Override
            public void reject(String errorCode, String defaultMessage) {

            }

            @Override
            public void reject(String errorCode) {

            }

            @Override
            public void pushNestedPath(String subPath) {


            }

            @Override
            public void popNestedPath() throws IllegalStateException {

            }

            @Override
            public boolean hasGlobalErrors() {

                return false;
            }

            @Override
            public boolean hasFieldErrors(String field) {

                return false;
            }

            @Override
            public boolean hasFieldErrors() {

                return false;
            }

            @Override
            public boolean hasErrors() {

                return false;
            }

            @Override
            public String getObjectName() {

                return null;
            }

            @Override
            public String getNestedPath() {

                return null;
            }

            @Override
            public List<ObjectError> getGlobalErrors() {

                return null;
            }

            @Override
            public int getGlobalErrorCount() {

                return 0;
            }

            @Override
            public ObjectError getGlobalError() {

                return null;
            }

            @Override
            public Object getFieldValue(String field) {

                return null;
            }

            @Override
            public Class<?> getFieldType(String field) {

                return null;
            }

            @Override
            public List<FieldError> getFieldErrors(String field) {

                return null;
            }

            @Override
            public List<FieldError> getFieldErrors() {

                return null;
            }

            @Override
            public int getFieldErrorCount(String field) {

                return 0;
            }

            @Override
            public int getFieldErrorCount() {

                return 0;
            }

            @Override
            public FieldError getFieldError(String field) {

                return null;
            }

            @Override
            public FieldError getFieldError() {

                return null;
            }

            @Override
            public int getErrorCount() {

                return 0;
            }

            @Override
            public List<ObjectError> getAllErrors() {

                return null;
            }

            @Override
            public void addAllErrors(Errors errors) {


            }

            @Override
            public String[] resolveMessageCodes(String errorCode, String field) {

                return null;
            }

            @Override
            public String[] resolveMessageCodes(String errorCode) {

                return null;
            }

            @Override
            public void recordSuppressedField(String field) {


            }

            @Override
            public Object getTarget() {

                return null;
            }

            @Override
            public String[] getSuppressedFields() {

                return null;
            }

            @Override
            public Object getRawFieldValue(String field) {

                return null;
            }

            @Override
            public PropertyEditorRegistry getPropertyEditorRegistry() {

                return null;
            }

            @Override
            public Map<String, Object> getModel() {

                return null;
            }

            @Override
            public PropertyEditor findEditor(String field, Class<?> valueType) {

                return null;
            }

            @Override
            public void addError(ObjectError error) {

            }
        };
    }

    /**
     * Test case of get initialize information success.
     */
    @Test
    public void testGetInitializeInformationSuccess() {
        TranslationStoreCodeMasterOptional translationStoreCodeMasterOptional =
                new TranslationStoreCodeMasterOptional();
        translationStoreCodeMasterOptional.setStoreCode("112326");
        translationStoreCodeMasterOptional.setSystemBrandCode("UQ");
        translationStoreCodeMasterOptional.setSystemCountryCode("UK");
        List<TranslationStoreCodeMasterOptional> brand = new ArrayList<>();
        brand.add(translationStoreCodeMasterOptional);
        when(translationStoreCodeMasterOptionalMapper.selectByCondition(anyObject()))
                .thenReturn(brand);
        CommonCodeMaster countryCodeMaster = new CommonCodeMaster();
        countryCodeMaster.setTypeId("system_country_code");
        countryCodeMaster.setTypeValue("017");
        countryCodeMaster.setName1("JP");
        List<CommonCodeMaster> systemCountryList = new ArrayList<>();
        systemCountryList.add(countryCodeMaster);

        CommonCodeMaster brandCodeMaster = new CommonCodeMaster();
        brandCodeMaster.setTypeId("system_brand_code");
        brandCodeMaster.setTypeValue("019");
        brandCodeMaster.setName1("1JP");
        List<CommonCodeMaster> systemBrandList = new ArrayList<>();
        systemBrandList.add(brandCodeMaster);

        CommonCodeMaster transactionTypeMaster = new CommonCodeMaster();
        transactionTypeMaster.setTypeId("transaction_type");
        transactionTypeMaster.setTypeValue("018");
        transactionTypeMaster.setName1("ABC");
        List<CommonCodeMaster> transactionTypeList = new ArrayList<>();
        transactionTypeList.add(transactionTypeMaster);

        CommonCodeMaster taxationType = new CommonCodeMaster();
        taxationType.setTypeId("TAXATION_TYPE");
        taxationType.setTypeValue("1");
        taxationType.setName1("Y");
        List<CommonCodeMaster> taxationTypeList = new ArrayList<>();
        taxationTypeList.add(taxationType);

        CommonCodeMaster discountType = new CommonCodeMaster();
        discountType.setTypeId("Promotion Type Store Discount type");
        discountType.setTypeValue("1");
        discountType.setName1("Mix&Match");
        List<CommonCodeMaster> discountTypeList = new ArrayList<>();
        discountTypeList.add(discountType);

        TranslationTenderMasterOptional tenderGroupMaster = new TranslationTenderMasterOptional();
        tenderGroupMaster.setTenderGroup("COUPON");
        tenderGroupMaster.setImsTenderGroup("IMS");
        List<TranslationTenderMasterOptional> tenderGroupList = new ArrayList<>();
        tenderGroupList.add(tenderGroupMaster);

        TranslationTenderMasterOptional tenderIdMaster = new TranslationTenderMasterOptional();
        tenderIdMaster.setTenderId("ID");
        tenderIdMaster.setImsTenderId(123);
        List<TranslationTenderMasterOptional> tenderIdList = new ArrayList<>();
        tenderIdList.add(tenderIdMaster);
        StoreTransactionInquiryDetail storeTransactionInquiryDetail =
                new StoreTransactionInquiryDetail();
        storeTransactionInquiryDetail.setNonItemCode("Code123");
        storeTransactionInquiryDetail.setPosItemName("name");
        // Get non merchandise item list.
        List<StoreTransactionInquiryDetail> nonMerchandiseItemList = new ArrayList<>();
        nonMerchandiseItemList.add(storeTransactionInquiryDetail);
        when(storeTransactionInquiryOptionalMapper.selectByCountryBrandCode(anyObject()))
                .thenReturn(nonMerchandiseItemList);
        when(storeTransactionInquiryOptionalMapper.selectPaymentTenderGroup(anyObject()))
                .thenReturn(tenderGroupList);
        when(storeTransactionInquiryOptionalMapper.selectPaymentTenderId(anyObject()))
                .thenReturn(tenderIdList);
        when(commonCodeMasterMapper.selectByCondition(anyObject())).thenReturn(systemCountryList)
                .thenReturn(systemBrandList)
                .thenReturn(transactionTypeList)
                .thenReturn(taxationTypeList)
                .thenReturn(discountTypeList);
        SalesReportTransactionHeaderOptional salesReportTransactionHeaderOptional =
                new SalesReportTransactionHeaderOptional();
        salesReportTransactionHeaderOptional.setImsLinkageDate("20180621");
        salesReportTransactionHeaderOptional.setDepositValue(new BigDecimal(500.00));
        salesReportTransactionHeaderOptional.setChangeValue(new BigDecimal(1500.00));
        List<SalesReportTransactionHeaderOptional> headerDetailList = new ArrayList<>();
        headerDetailList.add(salesReportTransactionHeaderOptional);
        storeTransactionInquiryForm.setHeaderDetailList(headerDetailList);
        when(storeTransactionInquiryOptionalMapper
                .selectSalesReportTransactionHeaderByCondition(anyObject()))
                        .thenReturn(headerDetailList);
        storeTransactionInquiryForm =
                storeTransactionInquiryService.displayInitPage(storeTransactionInquiryForm);

        assertEquals(1, storeTransactionInquiryForm.getCountryList().size());
        assertEquals(1, storeTransactionInquiryForm.getBrandList().size());

    }

    /**
     * Test case of get initialize information transaction type list null.
     */
    @Test(expected = BusinessException.class)
    public void testGetInitializeInformationTransactionTypeListNull() {

        TranslationStoreCodeMasterOptional translationStoreCodeMasterOptional =
                new TranslationStoreCodeMasterOptional();
        translationStoreCodeMasterOptional.setStoreCode("112326");
        translationStoreCodeMasterOptional.setSystemBrandCode("UQ");
        translationStoreCodeMasterOptional.setSystemCountryCode("UK");
        List<TranslationStoreCodeMasterOptional> brand = new ArrayList<>();
        brand.add(translationStoreCodeMasterOptional);
        when(translationStoreCodeMasterOptionalMapper.selectByCondition(anyObject()))
                .thenReturn(brand);

        CommonCodeMaster countryCodeMaster = new CommonCodeMaster();
        countryCodeMaster.setTypeId("system_country_code");
        countryCodeMaster.setTypeValue("017");
        countryCodeMaster.setName1("JP");
        List<CommonCodeMaster> systemCountryList = new ArrayList<>();
        systemCountryList.add(countryCodeMaster);

        CommonCodeMaster brandCodeMaster = new CommonCodeMaster();
        brandCodeMaster.setTypeId("system_brand_code");
        brandCodeMaster.setTypeValue("019");
        brandCodeMaster.setName1("1JP");
        List<CommonCodeMaster> systemBrandList = new ArrayList<>();
        systemBrandList.add(brandCodeMaster);
        when(commonCodeMasterMapper.selectByCondition(anyObject())).thenReturn(systemCountryList)
                .thenReturn(systemBrandList)
                .thenReturn(null);
        storeTransactionInquiryService.displayInitPage(storeTransactionInquiryForm);

    }

    /**
     * Test case of translation store code null.
     */
    @Test
    public void testTranslationStoreCodeMasterIsNull() {
        when(translationStoreCodeMasterOptionalMapper.selectByCondition(anyObject()))
                .thenReturn(null);
        try {
            storeTransactionInquiryService.displayInitPage(storeTransactionInquiryForm);
        } catch (BusinessException e) {
            businessException = e;
        }
        assertEquals(businessException.getResultObject().getDebugId(),
                MessagePrefix.E_SLS_66000131_NO_TRANSLATION_STORE_CODE);
    }

    /**
     * Test case of get initialize information brand list null.
     */
    @Test(expected = BusinessException.class)
    public void testGetInitializeInformationBrandListNull() {

        CommonCodeMaster countryCodeMaster = new CommonCodeMaster();
        countryCodeMaster.setTypeId("system_country_code");
        countryCodeMaster.setTypeValue("017");
        countryCodeMaster.setName1("JP");
        List<CommonCodeMaster> systemCountryList = new ArrayList<>();
        systemCountryList.add(countryCodeMaster);
        when(commonCodeMasterMapper.selectByCondition(anyObject())).thenReturn(systemCountryList)
                .thenReturn(null);
        storeTransactionInquiryService.displayInitPage(storeTransactionInquiryForm);

    }

    /**
     * Test case of get initialize information country list null.
     */
    @Test(expected = BusinessException.class)
    public void testGetInitializeInformationCountryListNull() {
        when(commonCodeMasterMapper.selectByCondition(anyObject())).thenReturn(null);
        storeTransactionInquiryService.displayInitPage(storeTransactionInquiryForm);
    }

    /**
     * Test case of name1 null.
     */
    @Test(expected = BusinessException.class)
    public void testName1Null() {
        CommonCodeMaster countryCodeMaster = new CommonCodeMaster();
        countryCodeMaster.setTypeId("system_country_code");
        countryCodeMaster.setTypeValue("017");
        countryCodeMaster.setName1("");
        List<CommonCodeMaster> systemCountryList = new ArrayList<>();
        systemCountryList.add(countryCodeMaster);
        when(commonCodeMasterMapper.selectByCondition(anyObject())).thenReturn(systemCountryList)
                .thenReturn(null);
        storeTransactionInquiryService.displayInitPage(storeTransactionInquiryForm);

    }

    /**
     * Test case of pos name null.
     */
    @Test
    public void testPosNameIsNull() {
        TranslationStoreCodeMasterOptional translationStoreCodeMasterOptional =
                new TranslationStoreCodeMasterOptional();
        translationStoreCodeMasterOptional.setStoreCode("112326");
        translationStoreCodeMasterOptional.setSystemBrandCode("UQ");
        translationStoreCodeMasterOptional.setSystemCountryCode("UK");
        List<TranslationStoreCodeMasterOptional> brand = new ArrayList<>();
        brand.add(translationStoreCodeMasterOptional);
        when(translationStoreCodeMasterOptionalMapper.selectByCondition(anyObject()))
                .thenReturn(brand);
        CommonCodeMaster countryCodeMaster = new CommonCodeMaster();
        countryCodeMaster.setTypeId("system_country_code");
        countryCodeMaster.setTypeValue("017");
        countryCodeMaster.setName1("JP");
        List<CommonCodeMaster> systemCountryList = new ArrayList<>();
        systemCountryList.add(countryCodeMaster);

        CommonCodeMaster brandCodeMaster = new CommonCodeMaster();
        brandCodeMaster.setTypeId("system_brand_code");
        brandCodeMaster.setTypeValue("019");
        brandCodeMaster.setName1("1JP");
        List<CommonCodeMaster> systemBrandList = new ArrayList<>();
        systemBrandList.add(brandCodeMaster);

        CommonCodeMaster transactionTypeMaster = new CommonCodeMaster();
        transactionTypeMaster.setTypeId("transaction_type");
        transactionTypeMaster.setTypeValue("018");
        transactionTypeMaster.setName1("ABC");
        List<CommonCodeMaster> transactionTypeList = new ArrayList<>();
        transactionTypeList.add(transactionTypeMaster);

        CommonCodeMaster taxationType = new CommonCodeMaster();
        taxationType.setTypeId("TAXATION_TYPE");
        taxationType.setTypeValue("1");
        taxationType.setName1("Y");
        List<CommonCodeMaster> taxationTypeList = new ArrayList<>();
        taxationTypeList.add(taxationType);

        CommonCodeMaster discountType = new CommonCodeMaster();
        discountType.setTypeId("Promotion Type Store Discount type");
        discountType.setTypeValue("1");
        discountType.setName1("Mix&Match");
        List<CommonCodeMaster> discountTypeList = new ArrayList<>();
        discountTypeList.add(discountType);

        TranslationTenderMasterOptional tenderGroupMaster = new TranslationTenderMasterOptional();
        tenderGroupMaster.setTenderGroup("COUPON");
        tenderGroupMaster.setImsTenderGroup("IMS");
        List<TranslationTenderMasterOptional> tenderGroupList = new ArrayList<>();
        tenderGroupList.add(tenderGroupMaster);

        TranslationTenderMasterOptional tenderIdMaster = new TranslationTenderMasterOptional();
        tenderIdMaster.setTenderId("ID");
        tenderIdMaster.setImsTenderId(123);
        List<TranslationTenderMasterOptional> tenderIdList = new ArrayList<>();
        tenderIdList.add(tenderIdMaster);
        StoreTransactionInquiryDetail storeTransactionInquiryDetail =
                new StoreTransactionInquiryDetail();
        storeTransactionInquiryDetail.setNonItemCode("Code123");
        storeTransactionInquiryDetail.setPosItemName(null);
        // Get non merchandise item list.
        List<StoreTransactionInquiryDetail> nonMerchandiseItemList = new ArrayList<>();
        nonMerchandiseItemList.add(storeTransactionInquiryDetail);
        when(storeTransactionInquiryOptionalMapper.selectByCountryBrandCode(anyObject()))
                .thenReturn(null);
        when(storeTransactionInquiryOptionalMapper.selectPaymentTenderGroup(anyObject()))
                .thenReturn(tenderGroupList);
        when(storeTransactionInquiryOptionalMapper.selectPaymentTenderId(anyObject()))
                .thenReturn(tenderIdList);
        when(commonCodeMasterMapper.selectByCondition(anyObject())).thenReturn(systemCountryList)
                .thenReturn(systemBrandList)
                .thenReturn(transactionTypeList)
                .thenReturn(taxationTypeList)
                .thenReturn(discountTypeList);
        List<SalesReportTransactionHeaderOptional> headerDetailList = new ArrayList<>();
        SalesReportTransactionHeaderOptional salesReportTransactionHeaderOptional =
                new SalesReportTransactionHeaderOptional();
        salesReportTransactionHeaderOptional.setImsLinkageDate("20180621");
        headerDetailList.add(salesReportTransactionHeaderOptional);
        storeTransactionInquiryForm.setHeaderDetailList(headerDetailList);
        when(storeTransactionInquiryOptionalMapper
                .selectSalesReportTransactionHeaderByCondition(anyObject()))
                        .thenReturn(headerDetailList);
        try {
            storeTransactionInquiryForm =
                    storeTransactionInquiryService.displayInitPage(storeTransactionInquiryForm);
        } catch (BusinessException e) {
            businessException = e;
        }
        assertEquals(businessException.getResultObject().getDebugId(),
                MessagePrefix.E_SLS_66000153_NO_NON_ITEM);

    }

    /**
     * Test case of business country state setting.
     */
    @Test
    public void testBusinessCountryStateSettingIsNull() {
        TranslationStoreCodeMasterOptional translationStoreCodeMasterOptional =
                new TranslationStoreCodeMasterOptional();
        translationStoreCodeMasterOptional.setStoreCode("112326");
        translationStoreCodeMasterOptional.setSystemBrandCode("UQ");
        translationStoreCodeMasterOptional.setSystemCountryCode("UK");
        List<TranslationStoreCodeMasterOptional> brand = new ArrayList<>();
        brand.add(translationStoreCodeMasterOptional);
        when(translationStoreCodeMasterOptionalMapper.selectByCondition(anyObject()))
                .thenReturn(brand);
        CommonCodeMaster countryCodeMaster = new CommonCodeMaster();
        countryCodeMaster.setTypeId("system_country_code");
        countryCodeMaster.setTypeValue("017");
        countryCodeMaster.setName1("JP");
        List<CommonCodeMaster> systemCountryList = new ArrayList<>();
        systemCountryList.add(countryCodeMaster);

        CommonCodeMaster brandCodeMaster = new CommonCodeMaster();
        brandCodeMaster.setTypeId("system_brand_code");
        brandCodeMaster.setTypeValue("019");
        brandCodeMaster.setName1("1JP");
        List<CommonCodeMaster> systemBrandList = new ArrayList<>();
        systemBrandList.add(brandCodeMaster);

        CommonCodeMaster transactionTypeMaster = new CommonCodeMaster();
        transactionTypeMaster.setTypeId("transaction_type");
        transactionTypeMaster.setTypeValue("018");
        transactionTypeMaster.setName1("ABC");
        List<CommonCodeMaster> transactionTypeList = new ArrayList<>();
        transactionTypeList.add(transactionTypeMaster);

        CommonCodeMaster taxationType = new CommonCodeMaster();
        taxationType.setTypeId("TAXATION_TYPE");
        taxationType.setTypeValue("1");
        taxationType.setName1("Y");
        List<CommonCodeMaster> taxationTypeList = new ArrayList<>();
        taxationTypeList.add(taxationType);

        CommonCodeMaster discountType = new CommonCodeMaster();
        discountType.setTypeId("Promotion Type Store Discount type");
        discountType.setTypeValue("1");
        discountType.setName1("Mix&Match");
        List<CommonCodeMaster> discountTypeList = new ArrayList<>();
        discountTypeList.add(discountType);

        TranslationTenderMasterOptional tenderGroupMaster = new TranslationTenderMasterOptional();
        tenderGroupMaster.setTenderGroup("COUPON");
        tenderGroupMaster.setImsTenderGroup("IMS");
        List<TranslationTenderMasterOptional> tenderGroupList = new ArrayList<>();
        tenderGroupList.add(tenderGroupMaster);

        TranslationTenderMasterOptional tenderIdMaster = new TranslationTenderMasterOptional();
        tenderIdMaster.setTenderId("ID");
        tenderIdMaster.setImsTenderId(123);
        List<TranslationTenderMasterOptional> tenderIdList = new ArrayList<>();
        tenderIdList.add(tenderIdMaster);
        NonItemMaster nonItemMaster = new NonItemMaster();
        nonItemMaster.setSystemBrandCode("UQ");
        nonItemMaster.setSystemCountryCode("UK");
        nonItemMaster.setNonItemCode("Code123");
        nonItemMaster.setPosItemName("name");
        List<NonItemMaster> nonItemMasterList = new ArrayList<>();
        nonItemMasterList.add(nonItemMaster);
        BusinessCountryStateSettingMasterOptional taxType =
                new BusinessCountryStateSettingMasterOptional();
        taxType.setCodeValue("2");
        List<BusinessCountryStateSettingMasterOptional> taxTypeList = new ArrayList<>();
        taxTypeList.add(taxType);
        when(nonItemMasterMapper.selectByCondition(anyObject())).thenReturn(nonItemMasterList);
        when(storeTransactionInquiryOptionalMapper.selectPaymentTenderGroup(anyObject()))
                .thenReturn(tenderGroupList);
        when(storeTransactionInquiryOptionalMapper.selectPaymentTenderId(anyObject()))
                .thenReturn(tenderIdList);
        when(commonCodeMasterMapper.selectByCondition(anyObject())).thenReturn(systemCountryList)
                .thenReturn(systemBrandList)
                .thenReturn(transactionTypeList)
                .thenReturn(taxationTypeList)
                .thenReturn(discountTypeList);
        when(businessCountryStateSettingMasterOptionalMapper.selectByCondition(anyObject()))
                .thenReturn(null);
        try {
            storeTransactionInquiryForm = storeTransactionInquiryService
                    .getStoreTransactionInquiryList(storeTransactionInquiryForm);
        } catch (BusinessException e) {
            businessException = e;
        }
        assertEquals(businessException.getResultObject().getDebugId(),
                MessagePrefix.E_SLS_66000162_NO_BUSINESS_COUNTRY_STATE_SETTING);
    }

    /**
     * Test case of common code master.
     */
    @Test
    public void testCommonCodeMasterIsNull() {
        TranslationStoreCodeMasterOptional translationStoreCodeMasterOptional =
                new TranslationStoreCodeMasterOptional();
        translationStoreCodeMasterOptional.setStoreCode("112326");
        translationStoreCodeMasterOptional.setSystemBrandCode("UQ");
        translationStoreCodeMasterOptional.setSystemCountryCode("UK");
        List<TranslationStoreCodeMasterOptional> brand = new ArrayList<>();
        brand.add(translationStoreCodeMasterOptional);
        when(translationStoreCodeMasterOptionalMapper.selectByCondition(anyObject()))
                .thenReturn(brand);
        CommonCodeMaster countryCodeMaster = new CommonCodeMaster();
        countryCodeMaster.setTypeId("system_country_code");
        countryCodeMaster.setTypeValue("017");
        countryCodeMaster.setName1("JP");
        List<CommonCodeMaster> systemCountryList = new ArrayList<>();
        systemCountryList.add(countryCodeMaster);

        TranslationTenderMasterOptional tenderGroupMaster = new TranslationTenderMasterOptional();
        tenderGroupMaster.setTenderGroup("COUPON");
        tenderGroupMaster.setImsTenderGroup("IMS");
        List<TranslationTenderMasterOptional> tenderGroupList = new ArrayList<>();
        tenderGroupList.add(tenderGroupMaster);

        TranslationTenderMasterOptional tenderIdMaster = new TranslationTenderMasterOptional();
        tenderIdMaster.setTenderId("ID");
        tenderIdMaster.setImsTenderId(123);
        List<TranslationTenderMasterOptional> tenderIdList = new ArrayList<>();
        tenderIdList.add(tenderIdMaster);
        NonItemMaster nonItemMaster = new NonItemMaster();
        nonItemMaster.setSystemBrandCode("UQ");
        nonItemMaster.setSystemCountryCode("UK");
        nonItemMaster.setNonItemCode("Code123");
        nonItemMaster.setPosItemName("name");
        List<NonItemMaster> nonItemMasterList = new ArrayList<>();
        nonItemMasterList.add(nonItemMaster);
        BusinessCountryStateSettingMasterOptional taxType =
                new BusinessCountryStateSettingMasterOptional();
        taxType.setCodeValue("2");
        List<BusinessCountryStateSettingMasterOptional> taxTypeList = new ArrayList<>();
        taxTypeList.add(taxType);
        when(nonItemMasterMapper.selectByCondition(anyObject())).thenReturn(nonItemMasterList);
        when(storeTransactionInquiryOptionalMapper.selectPaymentTenderGroup(anyObject()))
                .thenReturn(tenderGroupList);
        when(commonCodeMasterMapper.selectByCondition(anyObject())).thenReturn(null);
        when(businessCountryStateSettingMasterOptionalMapper.selectByCondition(anyObject()))
                .thenReturn(taxTypeList);
        try {
            storeTransactionInquiryForm =
                    storeTransactionInquiryService.displayInitPage(storeTransactionInquiryForm);
        } catch (BusinessException e) {
            businessException = e;
        }
        assertEquals(businessException.getResultObject().getDebugId(),
                MessagePrefix.E_SLS_66000128_NO_COMMON_CODE);
    }

    /**
     * Test case of Translation tender master.
     */
    @Test
    public void testTranslationTenderMasterOptionalIsNull() {
        TranslationStoreCodeMasterOptional translationStoreCodeMasterOptional =
                new TranslationStoreCodeMasterOptional();
        translationStoreCodeMasterOptional.setStoreCode("112326");
        translationStoreCodeMasterOptional.setSystemBrandCode("UQ");
        translationStoreCodeMasterOptional.setSystemCountryCode("UK");
        List<TranslationStoreCodeMasterOptional> brand = new ArrayList<>();
        brand.add(translationStoreCodeMasterOptional);
        when(translationStoreCodeMasterOptionalMapper.selectByCondition(anyObject()))
                .thenReturn(brand);
        CommonCodeMaster countryCodeMaster = new CommonCodeMaster();
        countryCodeMaster.setTypeId("system_country_code");
        countryCodeMaster.setTypeValue("017");
        countryCodeMaster.setName1("JP");
        List<CommonCodeMaster> systemCountryList = new ArrayList<>();
        systemCountryList.add(countryCodeMaster);

        TranslationTenderMasterOptional tenderGroupMaster = new TranslationTenderMasterOptional();
        tenderGroupMaster.setTenderGroup("COUPON");
        tenderGroupMaster.setImsTenderGroup("IMS");
        List<TranslationTenderMasterOptional> tenderGroupList = new ArrayList<>();
        tenderGroupList.add(tenderGroupMaster);

        TranslationTenderMasterOptional tenderIdMaster = new TranslationTenderMasterOptional();
        tenderIdMaster.setTenderId("ID");
        tenderIdMaster.setImsTenderId(123);
        List<TranslationTenderMasterOptional> tenderIdList = new ArrayList<>();
        tenderIdList.add(tenderIdMaster);
        NonItemMaster nonItemMaster = new NonItemMaster();
        nonItemMaster.setSystemBrandCode("UQ");
        nonItemMaster.setSystemCountryCode("UK");
        nonItemMaster.setNonItemCode("Code123");
        nonItemMaster.setPosItemName("name");
        List<NonItemMaster> nonItemMasterList = new ArrayList<>();
        nonItemMasterList.add(nonItemMaster);
        when(nonItemMasterMapper.selectByCondition(anyObject())).thenReturn(nonItemMasterList);
        when(commonCodeMasterMapper.selectByCondition(anyObject())).thenReturn(systemCountryList);
        when(storeTransactionInquiryOptionalMapper.selectPaymentTenderGroup(anyObject()))
                .thenReturn(null);
        try {
            storeTransactionInquiryForm =
                    storeTransactionInquiryService.displayInitPage(storeTransactionInquiryForm);
        } catch (BusinessException e) {
            businessException = e;
        }
        assertEquals(businessException.getResultObject().getDebugId(),
                MessagePrefix.E_SLS_66000170_NO_TRANSLATION_TENDER);
    }

    /**
     * Test case of get search success.
     */
    @Test
    public void testSearchSuccess() {
        TranslationStoreCodeMasterOptional translationStoreCodeMasterOptional =
                new TranslationStoreCodeMasterOptional();
        translationStoreCodeMasterOptional.setStoreCode("112326");
        translationStoreCodeMasterOptional.setSystemBrandCode("UQ");
        translationStoreCodeMasterOptional.setSystemCountryCode("UK");
        List<TranslationStoreCodeMasterOptional> brand = new ArrayList<>();
        brand.add(translationStoreCodeMasterOptional);
        when(translationStoreCodeMasterOptionalMapper.selectByCondition(anyObject()))
                .thenReturn(brand);
        CommonCodeMaster countryCodeMaster = new CommonCodeMaster();
        countryCodeMaster.setTypeId("system_country_code");
        countryCodeMaster.setTypeValue("017");
        countryCodeMaster.setName1("JP");
        List<CommonCodeMaster> systemCountryList = new ArrayList<>();
        systemCountryList.add(countryCodeMaster);

        CommonCodeMaster brandCodeMaster = new CommonCodeMaster();
        brandCodeMaster.setTypeId("system_brand_code");
        brandCodeMaster.setTypeValue("019");
        brandCodeMaster.setName1("1JP");
        List<CommonCodeMaster> systemBrandList = new ArrayList<>();
        systemBrandList.add(brandCodeMaster);

        CommonCodeMaster transactionTypeMaster = new CommonCodeMaster();
        transactionTypeMaster.setTypeId("transaction_type");
        transactionTypeMaster.setTypeValue("018");
        transactionTypeMaster.setName1("ABC");
        List<CommonCodeMaster> transactionTypeList = new ArrayList<>();
        transactionTypeList.add(transactionTypeMaster);

        CommonCodeMaster taxationType = new CommonCodeMaster();
        taxationType.setTypeId("TAXATION_TYPE");
        taxationType.setTypeValue("1");
        taxationType.setName1("Y");
        List<CommonCodeMaster> taxationTypeList = new ArrayList<>();
        taxationTypeList.add(taxationType);

        CommonCodeMaster discountType = new CommonCodeMaster();
        discountType.setTypeId("Promotion Type Store Discount type");
        discountType.setTypeValue("1");
        discountType.setName1("Mix&Match");
        List<CommonCodeMaster> discountTypeList = new ArrayList<>();
        discountTypeList.add(discountType);

        TranslationTenderMasterOptional tenderGroupMaster = new TranslationTenderMasterOptional();
        tenderGroupMaster.setTenderGroup("COUPON");
        tenderGroupMaster.setImsTenderGroup("IMS");
        List<TranslationTenderMasterOptional> tenderGroupList = new ArrayList<>();
        tenderGroupList.add(tenderGroupMaster);

        TranslationTenderMasterOptional tenderIdMaster = new TranslationTenderMasterOptional();
        tenderIdMaster.setTenderId("ID");
        tenderIdMaster.setImsTenderId(123);
        List<TranslationTenderMasterOptional> tenderIdList = new ArrayList<>();
        tenderIdList.add(tenderIdMaster);
        NonItemMaster nonItemMaster = new NonItemMaster();
        nonItemMaster.setSystemBrandCode("UQ");
        nonItemMaster.setSystemCountryCode("UK");
        nonItemMaster.setNonItemCode("Code123");
        nonItemMaster.setPosItemName("name");
        List<NonItemMaster> nonItemMasterList = new ArrayList<>();
        nonItemMasterList.add(nonItemMaster);
        BusinessCountryStateSettingMasterOptional taxType =
                new BusinessCountryStateSettingMasterOptional();
        taxType.setCodeValue("2");
        ItemMasterOptional itemMaster = new ItemMasterOptional();
        itemMaster.setViewItemCode("112326");
        List<ItemMasterOptional> itemMasterList = new ArrayList<>();
        itemMasterList.add(itemMaster);
        List<BusinessCountryStateSettingMasterOptional> taxTypeList = new ArrayList<>();
        taxTypeList.add(taxType);
        when(nonItemMasterMapper.selectByCondition(anyObject())).thenReturn(nonItemMasterList);
        when(itemMasterMapper.selectByCondition(anyObject())).thenReturn(itemMasterList);
        when(storeTransactionInquiryOptionalMapper.selectPaymentTenderGroup(anyObject()))
                .thenReturn(tenderGroupList);
        when(storeTransactionInquiryOptionalMapper.selectPaymentTenderId(anyObject()))
                .thenReturn(tenderIdList);
        when(commonCodeMasterMapper.selectByCondition(anyObject())).thenReturn(systemCountryList)
                .thenReturn(systemBrandList)
                .thenReturn(transactionTypeList)
                .thenReturn(taxationTypeList)
                .thenReturn(discountTypeList);
        when(businessCountryStateSettingMasterOptionalMapper.selectByCondition(anyObject()))
                .thenReturn(taxTypeList);
        SalesReportTransactionHeaderOptional salesReportTransactionHeaderOptional =
                new SalesReportTransactionHeaderOptional();
        salesReportTransactionHeaderOptional.setDepositValue(new BigDecimal(3000));
        salesReportTransactionHeaderOptional.setChangeValue(new BigDecimal(5000));
        List<SalesReportTransactionHeaderOptional> headerDetailList = new ArrayList<>();
        headerDetailList.add(salesReportTransactionHeaderOptional);
        storeTransactionInquiryForm.setBusinessDate("20180615");
        storeTransactionInquiryForm.setHeaderDetailList(headerDetailList);
        storeTransactionInquiryForm.setItemCode("itemcodename");
        SalesReportTransactionItemDetailOptional salesReportTransactionItemDetailOptional =
                new SalesReportTransactionItemDetailOptional();
        salesReportTransactionItemDetailOptional.setL3ItemCode("L3Code");
        salesReportTransactionItemDetailOptional.setL3PosProductName("L3Pos");
        salesReportTransactionItemDetailOptional.setDetailSubNumber(2);
        salesReportTransactionItemDetailOptional.setDetailQuantity(new BigDecimal(1000));
        salesReportTransactionItemDetailOptional.setRetailUnitPriceTaxExcluded(new BigDecimal(800));
        salesReportTransactionItemDetailOptional.setRetailUnitPriceTaxIncluded(new BigDecimal(500));
        salesReportTransactionItemDetailOptional.setQuantityCode("JPY");
        List<SalesReportTransactionItemDetailOptional> itemDetailList = new ArrayList<>();
        itemDetailList.add(salesReportTransactionItemDetailOptional);
        SalesReportTransactionDiscountDetailOptional discountDetail =
                new SalesReportTransactionDiscountDetailOptional();
        discountDetail.setPromotionType("1000");
        discountDetail.setStoreDiscountType("00");
        discountDetail.setDetailSubNumber(2);
        discountDetail.setDiscountAmountTaxExcludedCurrencyCode("JPY");
        discountDetail.setDiscountAmountTaxExcluded(new BigDecimal(5000.000));
        discountDetail.setDiscountAmountTaxIncludedCurrencyCode("CAD");
        discountDetail.setDiscountAmountTaxIncluded(new BigDecimal(3000.000));
        discountDetail.setDiscountQuantity(50);
        List<SalesReportTransactionDiscountDetailOptional> discountDetailList = new ArrayList<>();
        discountDetailList.add(discountDetail);
        SalesReportTransactionTaxOptional salesReportTransactionTaxOptional =
                new SalesReportTransactionTaxOptional();
        salesReportTransactionTaxOptional.setTaxAmountCurrencyCode("JPY");
        salesReportTransactionTaxOptional.setTaxGroup("2");
        salesReportTransactionTaxOptional.setTaxAmountSign("N");
        salesReportTransactionTaxOptional.setTaxAmountValue(new BigDecimal(1000.000));
        List<SalesReportTransactionTaxOptional> taxDetailList = new ArrayList<>();
        taxDetailList.add(salesReportTransactionTaxOptional);
        SalesReportTransactionTenderOptional salesReportTransactionTenderOptional =
                new SalesReportTransactionTenderOptional();
        salesReportTransactionTenderOptional.setTenderGroup("2");
        salesReportTransactionTenderOptional.setPaymentSign("N");
        salesReportTransactionTenderOptional.setTaxIncludedPaymentAmountCurrencyCode("CAD");
        salesReportTransactionTenderOptional
                .setTaxIncludedPaymentAmountValue(new BigDecimal(6000.000));
        List<SalesReportTransactionTenderOptional> tenderDetailList = new ArrayList<>();
        tenderDetailList.add(salesReportTransactionTenderOptional);
        when(storeTransactionInquiryOptionalMapper
                .selectSalesReportTransactionHeaderByCondition(anyObject()))
                        .thenReturn(headerDetailList);
        when(storeTransactionInquiryOptionalMapper
                .selectSalesReportTransactionDetailByCondition(anyObject()))
                        .thenReturn(itemDetailList);
        when(storeTransactionInquiryOptionalMapper
                .selectSalesReportTransactionDiscountDetailByCondition(anyObject()))
                        .thenReturn(discountDetailList);
        when(storeTransactionInquiryOptionalMapper
                .selectSalesReportTransactionTaxDetailByCondition(anyObject()))
                        .thenReturn(taxDetailList);
        when(storeTransactionInquiryOptionalMapper
                .selectSalesReportTransactionTenderDetailByCondition(anyObject()))
                        .thenReturn(tenderDetailList);
        storeTransactionInquiryForm = storeTransactionInquiryService
                .getStoreTransactionInquiryList(storeTransactionInquiryForm);
    }

    /**
     * Test case of no data.
     */
    @Test
    public void testSearchNoData() {
        TranslationStoreCodeMasterOptional translationStoreCodeMasterOptional =
                new TranslationStoreCodeMasterOptional();
        translationStoreCodeMasterOptional.setStoreCode("112326");
        translationStoreCodeMasterOptional.setSystemBrandCode("UQ");
        translationStoreCodeMasterOptional.setSystemCountryCode("UK");
        List<TranslationStoreCodeMasterOptional> brand = new ArrayList<>();
        brand.add(translationStoreCodeMasterOptional);
        when(translationStoreCodeMasterOptionalMapper.selectByCondition(anyObject()))
                .thenReturn(brand);
        CommonCodeMaster countryCodeMaster = new CommonCodeMaster();
        countryCodeMaster.setTypeId("system_country_code");
        countryCodeMaster.setTypeValue("017");
        countryCodeMaster.setName1("JP");
        List<CommonCodeMaster> systemCountryList = new ArrayList<>();
        systemCountryList.add(countryCodeMaster);

        TranslationTenderMasterOptional tenderGroupMaster = new TranslationTenderMasterOptional();
        tenderGroupMaster.setTenderGroup("COUPON");
        tenderGroupMaster.setImsTenderGroup("IMS");
        List<TranslationTenderMasterOptional> tenderGroupList = new ArrayList<>();
        tenderGroupList.add(tenderGroupMaster);

        TranslationTenderMasterOptional tenderIdMaster = new TranslationTenderMasterOptional();
        tenderIdMaster.setTenderId("ID");
        tenderIdMaster.setImsTenderId(123);
        List<TranslationTenderMasterOptional> tenderIdList = new ArrayList<>();
        tenderIdList.add(tenderIdMaster);
        NonItemMaster nonItemMaster = new NonItemMaster();
        nonItemMaster.setSystemBrandCode("UQ");
        nonItemMaster.setSystemCountryCode("UK");
        nonItemMaster.setNonItemCode("Code123");
        nonItemMaster.setPosItemName("name");
        List<NonItemMaster> nonItemMasterList = new ArrayList<>();
        nonItemMasterList.add(nonItemMaster);
        BusinessCountryStateSettingMasterOptional taxType =
                new BusinessCountryStateSettingMasterOptional();
        taxType.setCodeValue("2");
        List<BusinessCountryStateSettingMasterOptional> taxTypeList = new ArrayList<>();
        taxTypeList.add(taxType);
        when(nonItemMasterMapper.selectByCondition(anyObject())).thenReturn(nonItemMasterList);
        when(storeTransactionInquiryOptionalMapper.selectPaymentTenderGroup(anyObject()))
                .thenReturn(tenderGroupList);
        when(storeTransactionInquiryOptionalMapper.selectPaymentTenderId(anyObject()))
                .thenReturn(tenderIdList);
        when(commonCodeMasterMapper.selectByCondition(anyObject())).thenReturn(systemCountryList);
        when(businessCountryStateSettingMasterOptionalMapper.selectByCondition(anyObject()))
                .thenReturn(taxTypeList);
        SalesReportTransactionHeaderOptional salesReportTransactionHeaderOptional =
                new SalesReportTransactionHeaderOptional();
        salesReportTransactionHeaderOptional.setImsLinkageDate("20180621");
        salesReportTransactionHeaderOptional.setDepositValue(new BigDecimal(3000));
        salesReportTransactionHeaderOptional.setChangeValue(new BigDecimal(5000));
        List<SalesReportTransactionHeaderOptional> headerDetailList = new ArrayList<>();
        headerDetailList.add(salesReportTransactionHeaderOptional);
        storeTransactionInquiryForm.setHeaderDetailList(headerDetailList);
        when(storeTransactionInquiryOptionalMapper
                .selectSalesReportTransactionHeaderByCondition(anyObject())).thenReturn(null);
        try {
            storeTransactionInquiryForm = storeTransactionInquiryService
                    .getStoreTransactionInquiryList(storeTransactionInquiryForm);
        } catch (BusinessException e) {
            businessException = e;
        }
        assertEquals(businessException.getResultObject().getDebugId(),
                MessagePrefix.E_SLS_66000134_NO_DATA);
    }

    /**
     * Test case of check value.
     */
    @Test
    public void testCheckValue() {
        TranslationStoreCodeMasterOptional translationStoreCodeMasterOptional =
                new TranslationStoreCodeMasterOptional();
        translationStoreCodeMasterOptional.setStoreCode("112326");
        translationStoreCodeMasterOptional.setSystemBrandCode("UQ");
        translationStoreCodeMasterOptional.setSystemCountryCode("UK");
        List<TranslationStoreCodeMasterOptional> brand = new ArrayList<>();
        brand.add(translationStoreCodeMasterOptional);
        when(translationStoreCodeMasterOptionalMapper.selectByCondition(anyObject()))
                .thenReturn(brand);
        CommonCodeMaster countryCodeMaster = new CommonCodeMaster();
        countryCodeMaster.setTypeId("system_country_code");
        countryCodeMaster.setTypeValue("017");
        countryCodeMaster.setName1("JP");
        List<CommonCodeMaster> systemCountryList = new ArrayList<>();
        systemCountryList.add(countryCodeMaster);

        TranslationTenderMasterOptional tenderGroupMaster = new TranslationTenderMasterOptional();
        tenderGroupMaster.setTenderGroup("COUPON");
        tenderGroupMaster.setImsTenderGroup("IMS");
        List<TranslationTenderMasterOptional> tenderGroupList = new ArrayList<>();
        tenderGroupList.add(tenderGroupMaster);

        TranslationTenderMasterOptional tenderIdMaster = new TranslationTenderMasterOptional();
        tenderIdMaster.setTenderId("ID");
        tenderIdMaster.setImsTenderId(123);
        List<TranslationTenderMasterOptional> tenderIdList = new ArrayList<>();
        tenderIdList.add(tenderIdMaster);
        NonItemMaster nonItemMaster = new NonItemMaster();
        nonItemMaster.setSystemBrandCode("UQ");
        nonItemMaster.setSystemCountryCode("UK");
        nonItemMaster.setNonItemCode("Code123");
        nonItemMaster.setPosItemName("name");
        List<NonItemMaster> nonItemMasterList = new ArrayList<>();
        nonItemMasterList.add(nonItemMaster);
        BusinessCountryStateSettingMasterOptional taxType =
                new BusinessCountryStateSettingMasterOptional();
        taxType.setCodeValue("2");
        List<BusinessCountryStateSettingMasterOptional> taxTypeList = new ArrayList<>();
        taxTypeList.add(taxType);
        when(nonItemMasterMapper.selectByCondition(anyObject())).thenReturn(nonItemMasterList);
        when(storeTransactionInquiryOptionalMapper.selectPaymentTenderGroup(anyObject()))
                .thenReturn(tenderGroupList);
        when(storeTransactionInquiryOptionalMapper.selectPaymentTenderId(anyObject()))
                .thenReturn(tenderIdList);
        when(commonCodeMasterMapper.selectByCondition(anyObject())).thenReturn(systemCountryList);
        when(businessCountryStateSettingMasterOptionalMapper.selectByCondition(anyObject()))
                .thenReturn(taxTypeList);
        SalesReportTransactionHeaderOptional salesReportTransactionHeaderOptional =
                new SalesReportTransactionHeaderOptional();
        salesReportTransactionHeaderOptional.setImsLinkageDate("20180621");
        salesReportTransactionHeaderOptional.setDepositValue(new BigDecimal(3000));
        salesReportTransactionHeaderOptional.setChangeValue(new BigDecimal(5000));
        List<SalesReportTransactionHeaderOptional> headerDetailList = new ArrayList<>();
        headerDetailList.add(salesReportTransactionHeaderOptional);
        storeTransactionInquiryForm.setPaymentAmountFrom("5000");
        storeTransactionInquiryForm.setPaymentAmountTo("100");
        storeTransactionInquiryForm.setHeaderDetailList(headerDetailList);
        when(storeTransactionInquiryOptionalMapper
                .selectSalesReportTransactionHeaderByCondition(anyObject()))
                        .thenReturn(headerDetailList);
        try {
            storeTransactionInquiryForm = storeTransactionInquiryService
                    .getStoreTransactionInquiryList(storeTransactionInquiryForm);
        } catch (BusinessException e) {
            businessException = e;
        }
        assertEquals(businessException.getResultObject().getDebugId(),
                MessagePrefix.E_SLS_66000151_RANGE_ERROR);
    }

    /**
     * Test case of ChangeBrand.
     */
    @Test
    public void testChangeBrand() {
        TranslationStoreCodeMasterOptional translationStoreCodeMasterOptional =
                new TranslationStoreCodeMasterOptional();
        translationStoreCodeMasterOptional.setStoreCode("112326");
        translationStoreCodeMasterOptional.setSystemBrandCode("UQ");
        translationStoreCodeMasterOptional.setSystemCountryCode("UK");
        List<TranslationStoreCodeMasterOptional> brand = new ArrayList<>();
        brand.add(translationStoreCodeMasterOptional);
        when(translationStoreCodeMasterOptionalMapper.selectByCondition(anyObject()))
                .thenReturn(brand);
        CommonCodeMaster countryCodeMaster = new CommonCodeMaster();
        countryCodeMaster.setTypeId("system_country_code");
        countryCodeMaster.setTypeValue("017");
        countryCodeMaster.setName1("JP");
        List<CommonCodeMaster> systemCountryList = new ArrayList<>();
        systemCountryList.add(countryCodeMaster);

        CommonCodeMaster brandCodeMaster = new CommonCodeMaster();
        brandCodeMaster.setTypeId("system_brand_code");
        brandCodeMaster.setTypeValue("019");
        brandCodeMaster.setName1("1JP");
        List<CommonCodeMaster> systemBrandList = new ArrayList<>();
        systemBrandList.add(brandCodeMaster);

        CommonCodeMaster transactionTypeMaster = new CommonCodeMaster();
        transactionTypeMaster.setTypeId("transaction_type");
        transactionTypeMaster.setTypeValue("018");
        transactionTypeMaster.setName1("ABC");
        List<CommonCodeMaster> transactionTypeList = new ArrayList<>();
        transactionTypeList.add(transactionTypeMaster);

        CommonCodeMaster taxationType = new CommonCodeMaster();
        taxationType.setTypeId("TAXATION_TYPE");
        taxationType.setTypeValue("1");
        taxationType.setName1("Y");
        List<CommonCodeMaster> taxationTypeList = new ArrayList<>();
        taxationTypeList.add(taxationType);

        CommonCodeMaster discountType = new CommonCodeMaster();
        discountType.setTypeId("Promotion Type Store Discount type");
        discountType.setTypeValue("1");
        discountType.setName1("Mix&Match");
        List<CommonCodeMaster> discountTypeList = new ArrayList<>();
        discountTypeList.add(discountType);

        TranslationTenderMasterOptional tenderGroupMaster = new TranslationTenderMasterOptional();
        tenderGroupMaster.setTenderGroup("COUPON");
        tenderGroupMaster.setImsTenderGroup("IMS");
        List<TranslationTenderMasterOptional> tenderGroupList = new ArrayList<>();
        tenderGroupList.add(tenderGroupMaster);

        TranslationTenderMasterOptional tenderIdMaster = new TranslationTenderMasterOptional();
        tenderIdMaster.setTenderId("ID");
        tenderIdMaster.setImsTenderId(123);
        List<TranslationTenderMasterOptional> tenderIdList = new ArrayList<>();
        tenderIdList.add(tenderIdMaster);
        NonItemMaster nonItemMaster = new NonItemMaster();
        nonItemMaster.setSystemBrandCode("UQ");
        nonItemMaster.setSystemCountryCode("UK");
        nonItemMaster.setNonItemCode("Code123");
        nonItemMaster.setPosItemName("name");
        List<NonItemMaster> nonItemMasterList = new ArrayList<>();
        nonItemMasterList.add(nonItemMaster);
        BusinessCountryStateSettingMasterOptional taxType =
                new BusinessCountryStateSettingMasterOptional();
        taxType.setCodeValue("2");
        List<BusinessCountryStateSettingMasterOptional> taxTypeList = new ArrayList<>();
        taxTypeList.add(taxType);
        when(nonItemMasterMapper.selectByCondition(anyObject())).thenReturn(nonItemMasterList);
        when(storeTransactionInquiryOptionalMapper.selectPaymentTenderGroup(anyObject()))
                .thenReturn(tenderGroupList);
        when(storeTransactionInquiryOptionalMapper.selectPaymentTenderId(anyObject()))
                .thenReturn(tenderIdList);
        when(commonCodeMasterMapper.selectByCondition(anyObject())).thenReturn(systemCountryList)
                .thenReturn(systemBrandList)
                .thenReturn(transactionTypeList)
                .thenReturn(taxationTypeList)
                .thenReturn(discountTypeList);
        when(businessCountryStateSettingMasterOptionalMapper.selectByCondition(anyObject()))
                .thenReturn(taxTypeList);
        SalesReportTransactionHeaderOptional salesReportTransactionHeaderOptional =
                new SalesReportTransactionHeaderOptional();
        salesReportTransactionHeaderOptional.setDepositValue(new BigDecimal(3000));
        salesReportTransactionHeaderOptional.setChangeValue(new BigDecimal(5000));
        List<SalesReportTransactionHeaderOptional> headerDetailList = new ArrayList<>();
        headerDetailList.add(salesReportTransactionHeaderOptional);
        storeTransactionInquiryForm.setBusinessDate("20180615");
        storeTransactionInquiryForm.setHeaderDetailList(headerDetailList);
        SalesReportTransactionItemDetailOptional salesReportTransactionItemDetailOptional =
                new SalesReportTransactionItemDetailOptional();
        salesReportTransactionItemDetailOptional.setL3ItemCode("L3Code");
        salesReportTransactionItemDetailOptional.setL3PosProductName("L3Pos");
        salesReportTransactionItemDetailOptional.setDetailSubNumber(2);
        salesReportTransactionItemDetailOptional.setDetailQuantity(new BigDecimal(1000));
        salesReportTransactionItemDetailOptional.setRetailUnitPriceTaxExcluded(new BigDecimal(800));
        salesReportTransactionItemDetailOptional.setRetailUnitPriceTaxIncluded(new BigDecimal(500));
        salesReportTransactionItemDetailOptional.setQuantityCode("JPY");
        List<SalesReportTransactionItemDetailOptional> itemDetailList = new ArrayList<>();
        itemDetailList.add(salesReportTransactionItemDetailOptional);
        SalesReportTransactionDiscountDetailOptional discountDetail =
                new SalesReportTransactionDiscountDetailOptional();
        discountDetail.setPromotionType("1000");
        discountDetail.setStoreDiscountType("00");
        discountDetail.setDetailSubNumber(2);
        discountDetail.setDiscountAmountTaxExcludedCurrencyCode("JPY");
        discountDetail.setDiscountAmountTaxExcluded(new BigDecimal(5000.000));
        discountDetail.setDiscountAmountTaxIncludedCurrencyCode("CAD");
        discountDetail.setDiscountAmountTaxIncluded(new BigDecimal(3000.000));
        discountDetail.setDiscountQuantity(50);
        List<SalesReportTransactionDiscountDetailOptional> discountDetailList = new ArrayList<>();
        discountDetailList.add(discountDetail);
        SalesReportTransactionTaxOptional salesReportTransactionTaxOptional =
                new SalesReportTransactionTaxOptional();
        salesReportTransactionTaxOptional.setTaxAmountCurrencyCode("JPY");
        salesReportTransactionTaxOptional.setTaxGroup("2");
        salesReportTransactionTaxOptional.setTaxAmountSign("N");
        salesReportTransactionTaxOptional.setTaxAmountValue(new BigDecimal(1000.000));
        List<SalesReportTransactionTaxOptional> taxDetailList = new ArrayList<>();
        taxDetailList.add(salesReportTransactionTaxOptional);
        SalesReportTransactionTenderOptional salesReportTransactionTenderOptional =
                new SalesReportTransactionTenderOptional();
        salesReportTransactionTenderOptional.setTenderGroup("2");
        salesReportTransactionTenderOptional.setPaymentSign("N");
        salesReportTransactionTenderOptional.setTaxIncludedPaymentAmountCurrencyCode("CAD");
        salesReportTransactionTenderOptional
                .setTaxIncludedPaymentAmountValue(new BigDecimal(6000.000));
        List<SalesReportTransactionTenderOptional> tenderDetailList = new ArrayList<>();
        tenderDetailList.add(salesReportTransactionTenderOptional);
        when(storeTransactionInquiryOptionalMapper
                .selectSalesReportTransactionHeaderByCondition(anyObject()))
                        .thenReturn(headerDetailList);
        when(storeTransactionInquiryOptionalMapper
                .selectSalesReportTransactionDetailByCondition(anyObject()))
                        .thenReturn(itemDetailList);
        when(storeTransactionInquiryOptionalMapper
                .selectSalesReportTransactionDiscountDetailByCondition(anyObject()))
                        .thenReturn(discountDetailList);
        when(storeTransactionInquiryOptionalMapper
                .selectSalesReportTransactionTaxDetailByCondition(anyObject()))
                        .thenReturn(taxDetailList);
        when(storeTransactionInquiryOptionalMapper
                .selectSalesReportTransactionTenderDetailByCondition(anyObject()))
                        .thenReturn(tenderDetailList);
        StoreTransactionInquiryDetail storeTransactionInquiryDetail =
                new StoreTransactionInquiryDetail();
        storeTransactionInquiryDetail.setPosItemName("name");
        List<StoreTransactionInquiryDetail> nonMerchandiseItemList = new ArrayList<>();
        nonMerchandiseItemList.add(storeTransactionInquiryDetail);
        when(storeTransactionInquiryOptionalMapper.selectByCountryBrandCode(anyObject()))
                .thenReturn(nonMerchandiseItemList);
        List<DropDownItem> itemList = storeTransactionInquiryService
                .changeBrand(storeTransactionInquiryForm, bindingResult, model);
        assertEquals(1, itemList.size());
    }

    /**
     * Test case of code N.
     */
    @Test
    public void testCodeN() {
        TranslationStoreCodeMasterOptional translationStoreCodeMasterOptional =
                new TranslationStoreCodeMasterOptional();
        translationStoreCodeMasterOptional.setStoreCode("112326");
        translationStoreCodeMasterOptional.setSystemBrandCode("UQ");
        translationStoreCodeMasterOptional.setSystemCountryCode("UK");
        List<TranslationStoreCodeMasterOptional> brand = new ArrayList<>();
        brand.add(translationStoreCodeMasterOptional);
        when(translationStoreCodeMasterOptionalMapper.selectByCondition(anyObject()))
                .thenReturn(brand);
        CommonCodeMaster countryCodeMaster = new CommonCodeMaster();
        countryCodeMaster.setTypeId("system_country_code");
        countryCodeMaster.setTypeValue("017");
        countryCodeMaster.setName1("JP");
        List<CommonCodeMaster> systemCountryList = new ArrayList<>();
        systemCountryList.add(countryCodeMaster);

        CommonCodeMaster brandCodeMaster = new CommonCodeMaster();
        brandCodeMaster.setTypeId("system_brand_code");
        brandCodeMaster.setTypeValue("019");
        brandCodeMaster.setName1("1JP");
        List<CommonCodeMaster> systemBrandList = new ArrayList<>();
        systemBrandList.add(brandCodeMaster);

        CommonCodeMaster transactionTypeMaster = new CommonCodeMaster();
        transactionTypeMaster.setTypeId("transaction_type");
        transactionTypeMaster.setTypeValue("018");
        transactionTypeMaster.setName1("ABC");
        List<CommonCodeMaster> transactionTypeList = new ArrayList<>();
        transactionTypeList.add(transactionTypeMaster);

        CommonCodeMaster taxationType = new CommonCodeMaster();
        taxationType.setTypeId("TAXATION_TYPE");
        taxationType.setTypeValue("1");
        taxationType.setName1("Y");
        List<CommonCodeMaster> taxationTypeList = new ArrayList<>();
        taxationTypeList.add(taxationType);

        CommonCodeMaster discountType = new CommonCodeMaster();
        discountType.setTypeId("Promotion Type Store Discount type");
        discountType.setTypeValue("1");
        discountType.setName1("Mix&Match");
        List<CommonCodeMaster> discountTypeList = new ArrayList<>();
        discountTypeList.add(discountType);

        TranslationTenderMasterOptional tenderGroupMaster = new TranslationTenderMasterOptional();
        tenderGroupMaster.setTenderGroup("COUPON");
        tenderGroupMaster.setImsTenderGroup("IMS");
        List<TranslationTenderMasterOptional> tenderGroupList = new ArrayList<>();
        tenderGroupList.add(tenderGroupMaster);

        TranslationTenderMasterOptional tenderIdMaster = new TranslationTenderMasterOptional();
        tenderIdMaster.setTenderId("ID");
        tenderIdMaster.setImsTenderId(123);
        List<TranslationTenderMasterOptional> tenderIdList = new ArrayList<>();
        tenderIdList.add(tenderIdMaster);
        NonItemMaster nonItemMaster = new NonItemMaster();
        nonItemMaster.setSystemBrandCode("UQ");
        nonItemMaster.setSystemCountryCode("UK");
        nonItemMaster.setNonItemCode("Code123");
        nonItemMaster.setPosItemName("name");
        List<NonItemMaster> nonItemMasterList = new ArrayList<>();
        nonItemMasterList.add(nonItemMaster);
        BusinessCountryStateSettingMasterOptional taxType =
                new BusinessCountryStateSettingMasterOptional();
        taxType.setCodeValue("2");
        List<BusinessCountryStateSettingMasterOptional> taxTypeList = new ArrayList<>();
        taxTypeList.add(taxType);
        when(nonItemMasterMapper.selectByCondition(anyObject())).thenReturn(nonItemMasterList);
        when(storeTransactionInquiryOptionalMapper.selectPaymentTenderGroup(anyObject()))
                .thenReturn(tenderGroupList);
        when(storeTransactionInquiryOptionalMapper.selectPaymentTenderId(anyObject()))
                .thenReturn(tenderIdList);
        when(commonCodeMasterMapper.selectByCondition(anyObject())).thenReturn(systemCountryList)
                .thenReturn(systemBrandList)
                .thenReturn(transactionTypeList)
                .thenReturn(taxationTypeList)
                .thenReturn(discountTypeList);
        when(businessCountryStateSettingMasterOptionalMapper.selectByCondition(anyObject()))
                .thenReturn(taxTypeList);
        SalesReportTransactionHeaderOptional salesReportTransactionHeaderOptional =
                new SalesReportTransactionHeaderOptional();
        salesReportTransactionHeaderOptional.setDepositValue(new BigDecimal(3000));
        salesReportTransactionHeaderOptional.setChangeValue(new BigDecimal(5000));
        List<SalesReportTransactionHeaderOptional> headerDetailList = new ArrayList<>();
        headerDetailList.add(salesReportTransactionHeaderOptional);
        storeTransactionInquiryForm.setBusinessDate("20180615");
        storeTransactionInquiryForm.setHeaderDetailList(headerDetailList);
        storeTransactionInquiryForm.setTaxTypeFlag(true);
        storeTransactionInquiryForm.setDecimalFormat("2");
        SalesReportTransactionItemDetailOptional salesReportTransactionItemDetailOptional =
                new SalesReportTransactionItemDetailOptional();
        salesReportTransactionItemDetailOptional.setL3ItemCode("L3Code");
        salesReportTransactionItemDetailOptional.setL3PosProductName("L3Pos");
        salesReportTransactionItemDetailOptional.setDetailSubNumber(2);
        salesReportTransactionItemDetailOptional.setDetailQuantity(new BigDecimal(1000));
        salesReportTransactionItemDetailOptional.setRetailUnitPriceTaxExcluded(new BigDecimal(800));
        salesReportTransactionItemDetailOptional.setRetailUnitPriceTaxIncluded(new BigDecimal(500));
        salesReportTransactionItemDetailOptional.setQuantityCode("N");
        List<SalesReportTransactionItemDetailOptional> itemDetailList = new ArrayList<>();
        itemDetailList.add(salesReportTransactionItemDetailOptional);
        SalesReportTransactionDiscountDetailOptional discountDetail =
                new SalesReportTransactionDiscountDetailOptional();
        discountDetail.setPromotionType("1000");
        discountDetail.setStoreDiscountType("00");
        discountDetail.setDetailSubNumber(2);
        discountDetail.setDiscountAmountTaxExcludedCurrencyCode("JPY");
        discountDetail.setDiscountAmountTaxExcluded(new BigDecimal(5000.000));
        discountDetail.setDiscountAmountTaxIncludedCurrencyCode("CAD");
        discountDetail.setDiscountAmountTaxIncluded(new BigDecimal(3000.000));
        discountDetail.setDiscountQuantity(50);
        List<SalesReportTransactionDiscountDetailOptional> discountDetailList = new ArrayList<>();
        discountDetailList.add(discountDetail);
        SalesReportTransactionTaxOptional salesReportTransactionTaxOptional =
                new SalesReportTransactionTaxOptional();
        salesReportTransactionTaxOptional.setTaxAmountCurrencyCode("JPY");
        salesReportTransactionTaxOptional.setTaxGroup("2");
        salesReportTransactionTaxOptional.setTaxAmountSign("N");
        salesReportTransactionTaxOptional.setTaxAmountValue(new BigDecimal(2000.000));
        List<SalesReportTransactionTaxOptional> taxDetailList = new ArrayList<>();
        taxDetailList.add(salesReportTransactionTaxOptional);
        SalesReportTransactionTenderOptional salesReportTransactionTenderOptional =
                new SalesReportTransactionTenderOptional();
        salesReportTransactionTenderOptional.setTenderGroup("2");
        salesReportTransactionTenderOptional.setPaymentSign("N");

        salesReportTransactionTenderOptional.setTaxIncludedPaymentAmountCurrencyCode("CAD");
        salesReportTransactionTenderOptional
                .setTaxIncludedPaymentAmountValue(new BigDecimal(6000.000));
        List<SalesReportTransactionTenderOptional> tenderDetailList = new ArrayList<>();
        tenderDetailList.add(salesReportTransactionTenderOptional);
        when(storeTransactionInquiryOptionalMapper
                .selectSalesReportTransactionHeaderByCondition(anyObject()))
                        .thenReturn(headerDetailList);
        when(storeTransactionInquiryOptionalMapper
                .selectSalesReportTransactionDetailByCondition(anyObject()))
                        .thenReturn(itemDetailList);
        when(storeTransactionInquiryOptionalMapper
                .selectSalesReportTransactionDiscountDetailByCondition(anyObject()))
                        .thenReturn(discountDetailList);
        when(storeTransactionInquiryOptionalMapper
                .selectSalesReportTransactionTaxDetailByCondition(anyObject()))
                        .thenReturn(taxDetailList);
        when(storeTransactionInquiryOptionalMapper
                .selectSalesReportTransactionTenderDetailByCondition(anyObject()))
                        .thenReturn(tenderDetailList);
        storeTransactionInquiryForm = storeTransactionInquiryService
                .getStoreTransactionInquiryList(storeTransactionInquiryForm);
    }

    /**
     * Test case of code Y.
     */
    @Test
    public void testCodeY() {
        TranslationStoreCodeMasterOptional translationStoreCodeMasterOptional =
                new TranslationStoreCodeMasterOptional();
        translationStoreCodeMasterOptional.setStoreCode("112326");
        translationStoreCodeMasterOptional.setSystemBrandCode("UQ");
        translationStoreCodeMasterOptional.setSystemCountryCode("UK");
        List<TranslationStoreCodeMasterOptional> brand = new ArrayList<>();
        brand.add(translationStoreCodeMasterOptional);
        when(translationStoreCodeMasterOptionalMapper.selectByCondition(anyObject()))
                .thenReturn(brand);
        CommonCodeMaster countryCodeMaster = new CommonCodeMaster();
        countryCodeMaster.setTypeId("system_country_code");
        countryCodeMaster.setTypeValue("017");
        countryCodeMaster.setName1("JP");
        List<CommonCodeMaster> systemCountryList = new ArrayList<>();
        systemCountryList.add(countryCodeMaster);

        CommonCodeMaster brandCodeMaster = new CommonCodeMaster();
        brandCodeMaster.setTypeId("system_brand_code");
        brandCodeMaster.setTypeValue("019");
        brandCodeMaster.setName1("1JP");
        List<CommonCodeMaster> systemBrandList = new ArrayList<>();
        systemBrandList.add(brandCodeMaster);

        CommonCodeMaster transactionTypeMaster = new CommonCodeMaster();
        transactionTypeMaster.setTypeId("transaction_type");
        transactionTypeMaster.setTypeValue("018");
        transactionTypeMaster.setName1("ABC");
        List<CommonCodeMaster> transactionTypeList = new ArrayList<>();
        transactionTypeList.add(transactionTypeMaster);

        CommonCodeMaster taxationType = new CommonCodeMaster();
        taxationType.setTypeId("TAXATION_TYPE");
        taxationType.setTypeValue("1");
        taxationType.setName1("Y");
        List<CommonCodeMaster> taxationTypeList = new ArrayList<>();
        taxationTypeList.add(taxationType);

        CommonCodeMaster discountType = new CommonCodeMaster();
        discountType.setTypeId("Promotion Type Store Discount type");
        discountType.setTypeValue("1");
        discountType.setName1("Mix&Match");
        List<CommonCodeMaster> discountTypeList = new ArrayList<>();
        discountTypeList.add(discountType);

        TranslationTenderMasterOptional tenderGroupMaster = new TranslationTenderMasterOptional();
        tenderGroupMaster.setTenderGroup("COUPON");
        tenderGroupMaster.setImsTenderGroup("IMS");
        List<TranslationTenderMasterOptional> tenderGroupList = new ArrayList<>();
        tenderGroupList.add(tenderGroupMaster);

        TranslationTenderMasterOptional tenderIdMaster = new TranslationTenderMasterOptional();
        tenderIdMaster.setTenderId("ID");
        tenderIdMaster.setImsTenderId(123);
        List<TranslationTenderMasterOptional> tenderIdList = new ArrayList<>();
        tenderIdList.add(tenderIdMaster);
        NonItemMaster nonItemMaster = new NonItemMaster();
        nonItemMaster.setSystemBrandCode("UQ");
        nonItemMaster.setSystemCountryCode("UK");
        nonItemMaster.setNonItemCode("Code123");
        nonItemMaster.setPosItemName("name");
        List<NonItemMaster> nonItemMasterList = new ArrayList<>();
        nonItemMasterList.add(nonItemMaster);
        BusinessCountryStateSettingMasterOptional taxType =
                new BusinessCountryStateSettingMasterOptional();
        taxType.setCodeValue("2");
        List<BusinessCountryStateSettingMasterOptional> taxTypeList = new ArrayList<>();
        taxTypeList.add(taxType);
        when(nonItemMasterMapper.selectByCondition(anyObject())).thenReturn(nonItemMasterList);
        when(storeTransactionInquiryOptionalMapper.selectPaymentTenderGroup(anyObject()))
                .thenReturn(tenderGroupList);
        when(storeTransactionInquiryOptionalMapper.selectPaymentTenderId(anyObject()))
                .thenReturn(tenderIdList);
        when(commonCodeMasterMapper.selectByCondition(anyObject())).thenReturn(systemCountryList)
                .thenReturn(systemBrandList)
                .thenReturn(transactionTypeList)
                .thenReturn(taxationTypeList)
                .thenReturn(discountTypeList);
        when(businessCountryStateSettingMasterOptionalMapper.selectByCondition(anyObject()))
                .thenReturn(taxTypeList);
        SalesReportTransactionHeaderOptional salesReportTransactionHeaderOptional =
                new SalesReportTransactionHeaderOptional();
        salesReportTransactionHeaderOptional.setDepositValue(new BigDecimal(3000));
        salesReportTransactionHeaderOptional.setChangeValue(new BigDecimal(5000));
        salesReportTransactionHeaderOptional.setSalesTransactionType("PVOID");
        List<SalesReportTransactionHeaderOptional> headerDetailList = new ArrayList<>();
        headerDetailList.add(salesReportTransactionHeaderOptional);
        storeTransactionInquiryForm.setBusinessDate("20180615");
        storeTransactionInquiryForm.setHeaderDetailList(headerDetailList);
        storeTransactionInquiryForm.setTaxTypeFlag(true);
        SalesReportTransactionItemDetailOptional salesReportTransactionItemDetailOptional =
                new SalesReportTransactionItemDetailOptional();
        salesReportTransactionItemDetailOptional.setL3ItemCode("L3Code");
        salesReportTransactionItemDetailOptional.setL3PosProductName("L3Pos");
        salesReportTransactionItemDetailOptional.setDetailSubNumber(2);
        salesReportTransactionItemDetailOptional.setDetailQuantity(new BigDecimal(1000));
        salesReportTransactionItemDetailOptional.setRetailUnitPriceTaxExcluded(new BigDecimal(800));
        salesReportTransactionItemDetailOptional.setRetailUnitPriceTaxIncluded(new BigDecimal(500));
        salesReportTransactionItemDetailOptional.setQuantityCode("N");
        List<SalesReportTransactionItemDetailOptional> itemDetailList = new ArrayList<>();
        itemDetailList.add(salesReportTransactionItemDetailOptional);
        SalesReportTransactionDiscountDetailOptional discountDetail =
                new SalesReportTransactionDiscountDetailOptional();
        discountDetail.setPromotionType("1000");
        discountDetail.setStoreDiscountType("00");
        discountDetail.setDetailSubNumber(2);
        discountDetail.setDiscountAmountTaxExcludedCurrencyCode("JPY");
        discountDetail.setDiscountAmountTaxExcluded(new BigDecimal(5000.000));
        discountDetail.setDiscountAmountTaxIncludedCurrencyCode("CAD");
        discountDetail.setDiscountAmountTaxIncluded(new BigDecimal(3000.000));
        discountDetail.setDiscountQuantity(50);
        List<SalesReportTransactionDiscountDetailOptional> discountDetailList = new ArrayList<>();
        discountDetailList.add(discountDetail);
        SalesReportTransactionTaxOptional salesReportTransactionTaxOptional =
                new SalesReportTransactionTaxOptional();
        salesReportTransactionTaxOptional.setTaxAmountCurrencyCode("JPY");
        salesReportTransactionTaxOptional.setTaxGroup("2");
        salesReportTransactionTaxOptional.setTaxAmountSign("Y");
        salesReportTransactionTaxOptional.setTaxAmountValue(new BigDecimal(2000.000));
        List<SalesReportTransactionTaxOptional> taxDetailList = new ArrayList<>();
        taxDetailList.add(salesReportTransactionTaxOptional);
        SalesReportTransactionTenderOptional salesReportTransactionTenderOptional =
                new SalesReportTransactionTenderOptional();
        salesReportTransactionTenderOptional.setTenderGroup("2");
        salesReportTransactionTenderOptional.setPaymentSign("Y");
        salesReportTransactionTenderOptional.setTaxIncludedPaymentAmountCurrencyCode("CAD");
        salesReportTransactionTenderOptional
                .setTaxIncludedPaymentAmountValue(new BigDecimal(6000.000));
        List<SalesReportTransactionTenderOptional> tenderDetailList = new ArrayList<>();
        tenderDetailList.add(salesReportTransactionTenderOptional);
        when(storeTransactionInquiryOptionalMapper
                .selectSalesReportTransactionHeaderByCondition(anyObject()))
                        .thenReturn(headerDetailList);
        when(storeTransactionInquiryOptionalMapper
                .selectSalesReportTransactionDetailByCondition(anyObject()))
                        .thenReturn(itemDetailList);
        when(storeTransactionInquiryOptionalMapper
                .selectSalesReportTransactionDiscountDetailByCondition(anyObject()))
                        .thenReturn(discountDetailList);
        when(storeTransactionInquiryOptionalMapper
                .selectSalesReportTransactionTaxDetailByCondition(anyObject()))
                        .thenReturn(taxDetailList);
        when(storeTransactionInquiryOptionalMapper
                .selectSalesReportTransactionTenderDetailByCondition(anyObject()))
                        .thenReturn(tenderDetailList);
        storeTransactionInquiryForm = storeTransactionInquiryService
                .getStoreTransactionInquiryList(storeTransactionInquiryForm);
    }

    /**
     * Test case of get previous detail.
     */
    @Test
    public void testGetPreviousDetail() {
        TranslationStoreCodeMasterOptional translationStoreCodeMasterOptional =
                new TranslationStoreCodeMasterOptional();
        translationStoreCodeMasterOptional.setStoreCode("112326");
        translationStoreCodeMasterOptional.setSystemBrandCode("UQ");
        translationStoreCodeMasterOptional.setSystemCountryCode("UK");
        List<TranslationStoreCodeMasterOptional> brand = new ArrayList<>();
        brand.add(translationStoreCodeMasterOptional);
        when(translationStoreCodeMasterOptionalMapper.selectByCondition(anyObject()))
                .thenReturn(brand);
        CommonCodeMaster countryCodeMaster = new CommonCodeMaster();
        countryCodeMaster.setTypeId("system_country_code");
        countryCodeMaster.setTypeValue("017");
        countryCodeMaster.setName1("JP");
        List<CommonCodeMaster> systemCountryList = new ArrayList<>();
        systemCountryList.add(countryCodeMaster);

        CommonCodeMaster brandCodeMaster = new CommonCodeMaster();
        brandCodeMaster.setTypeId("system_brand_code");
        brandCodeMaster.setTypeValue("019");
        brandCodeMaster.setName1("1JP");
        List<CommonCodeMaster> systemBrandList = new ArrayList<>();
        systemBrandList.add(brandCodeMaster);

        CommonCodeMaster transactionTypeMaster = new CommonCodeMaster();
        transactionTypeMaster.setTypeId("transaction_type");
        transactionTypeMaster.setTypeValue("018");
        transactionTypeMaster.setName1("ABC");
        List<CommonCodeMaster> transactionTypeList = new ArrayList<>();
        transactionTypeList.add(transactionTypeMaster);

        CommonCodeMaster taxationType = new CommonCodeMaster();
        taxationType.setTypeId("TAXATION_TYPE");
        taxationType.setTypeValue("1");
        taxationType.setName1("Y");
        List<CommonCodeMaster> taxationTypeList = new ArrayList<>();
        taxationTypeList.add(taxationType);

        CommonCodeMaster discountType = new CommonCodeMaster();
        discountType.setTypeId("Promotion Type Store Discount type");
        discountType.setTypeValue("1");
        discountType.setName1("Mix&Match");
        List<CommonCodeMaster> discountTypeList = new ArrayList<>();
        discountTypeList.add(discountType);

        TranslationTenderMasterOptional tenderGroupMaster = new TranslationTenderMasterOptional();
        tenderGroupMaster.setTenderGroup("COUPON");
        tenderGroupMaster.setImsTenderGroup("IMS");
        List<TranslationTenderMasterOptional> tenderGroupList = new ArrayList<>();
        tenderGroupList.add(tenderGroupMaster);

        TranslationTenderMasterOptional tenderIdMaster = new TranslationTenderMasterOptional();
        tenderIdMaster.setTenderId("ID");
        tenderIdMaster.setImsTenderId(123);
        List<TranslationTenderMasterOptional> tenderIdList = new ArrayList<>();
        tenderIdList.add(tenderIdMaster);
        NonItemMaster nonItemMaster = new NonItemMaster();
        nonItemMaster.setSystemBrandCode("UQ");
        nonItemMaster.setSystemCountryCode("UK");
        nonItemMaster.setNonItemCode("Code123");
        nonItemMaster.setPosItemName("name");
        List<NonItemMaster> nonItemMasterList = new ArrayList<>();
        nonItemMasterList.add(nonItemMaster);
        BusinessCountryStateSettingMasterOptional taxType =
                new BusinessCountryStateSettingMasterOptional();
        taxType.setCodeValue("2");
        List<BusinessCountryStateSettingMasterOptional> taxTypeList = new ArrayList<>();
        taxTypeList.add(taxType);
        when(nonItemMasterMapper.selectByCondition(anyObject())).thenReturn(nonItemMasterList);
        when(storeTransactionInquiryOptionalMapper.selectPaymentTenderGroup(anyObject()))
                .thenReturn(tenderGroupList);
        when(storeTransactionInquiryOptionalMapper.selectPaymentTenderId(anyObject()))
                .thenReturn(tenderIdList);
        when(commonCodeMasterMapper.selectByCondition(anyObject())).thenReturn(systemCountryList)
                .thenReturn(systemBrandList)
                .thenReturn(transactionTypeList)
                .thenReturn(taxationTypeList)
                .thenReturn(discountTypeList);
        when(businessCountryStateSettingMasterOptionalMapper.selectByCondition(anyObject()))
                .thenReturn(taxTypeList);
        SalesReportTransactionHeaderOptional salesReportTransactionHeaderT1 =
                new SalesReportTransactionHeaderOptional();
        salesReportTransactionHeaderT1.setSalesTransactionId("T10001");
        salesReportTransactionHeaderT1.setDepositValue(new BigDecimal(3000));
        salesReportTransactionHeaderT1.setChangeValue(new BigDecimal(5000));
        salesReportTransactionHeaderT1.setSalesTransactionType("PVOID");
        List<SalesReportTransactionHeaderOptional> headerDetailList1 = new ArrayList<>();
        headerDetailList1.add(salesReportTransactionHeaderT1);
        SalesReportTransactionHeaderOptional salesReportTransactionHeaderT2 =
                new SalesReportTransactionHeaderOptional();
        salesReportTransactionHeaderT2.setSalesTransactionId("T10001");
        salesReportTransactionHeaderT2.setDepositValue(new BigDecimal(3000));
        salesReportTransactionHeaderT2.setChangeValue(new BigDecimal(5000));
        salesReportTransactionHeaderT2.setSalesTransactionType("RETURN");
        List<SalesReportTransactionHeaderOptional> headerDetailList = new ArrayList<>();
        headerDetailList.add(salesReportTransactionHeaderT2);
        storeTransactionInquiryForm.setBusinessDate("20180615");
        storeTransactionInquiryForm.setHeaderDetailList(headerDetailList);
        storeTransactionInquiryForm.setTaxTypeFlag(true);
        storeTransactionInquiryForm.setCountIndex(1);
        storeTransactionInquiryForm.setDecimalFormat("2");
        SalesReportTransactionItemDetailOptional salesReportTransactionItemDetailOptional =
                new SalesReportTransactionItemDetailOptional();
        salesReportTransactionItemDetailOptional.setL3ItemCode("L3Code");
        salesReportTransactionItemDetailOptional.setL3PosProductName("L3Pos");
        salesReportTransactionItemDetailOptional.setDetailSubNumber(2);
        salesReportTransactionItemDetailOptional.setDetailQuantity(new BigDecimal(1000));
        salesReportTransactionItemDetailOptional.setRetailUnitPriceTaxExcluded(new BigDecimal(800));
        salesReportTransactionItemDetailOptional.setRetailUnitPriceTaxIncluded(new BigDecimal(500));
        salesReportTransactionItemDetailOptional.setQuantityCode("N");
        List<SalesReportTransactionItemDetailOptional> itemDetailList = new ArrayList<>();
        itemDetailList.add(salesReportTransactionItemDetailOptional);
        SalesReportTransactionDiscountDetailOptional discountDetail =
                new SalesReportTransactionDiscountDetailOptional();
        discountDetail.setPromotionType("1000");
        discountDetail.setStoreDiscountType("00");
        discountDetail.setDetailSubNumber(2);
        discountDetail.setDiscountAmountTaxExcludedCurrencyCode("JPY");
        discountDetail.setDiscountAmountTaxExcluded(new BigDecimal(5000.000));
        discountDetail.setDiscountAmountTaxIncludedCurrencyCode("CAD");
        discountDetail.setDiscountAmountTaxIncluded(new BigDecimal(3000.000));
        discountDetail.setDiscountQuantity(50);
        List<SalesReportTransactionDiscountDetailOptional> discountDetailList = new ArrayList<>();
        discountDetailList.add(discountDetail);
        SalesReportTransactionTaxOptional salesReportTransactionTaxOptional =
                new SalesReportTransactionTaxOptional();
        salesReportTransactionTaxOptional.setTaxAmountCurrencyCode("JPY");
        salesReportTransactionTaxOptional.setTaxGroup("2");
        salesReportTransactionTaxOptional.setTaxAmountSign("Y");
        salesReportTransactionTaxOptional.setTaxAmountValue(new BigDecimal(2000.000));
        List<SalesReportTransactionTaxOptional> taxDetailList = new ArrayList<>();
        taxDetailList.add(salesReportTransactionTaxOptional);
        SalesReportTransactionTenderOptional salesReportTransactionTenderOptional =
                new SalesReportTransactionTenderOptional();
        salesReportTransactionTenderOptional.setTenderGroup("2");
        salesReportTransactionTenderOptional.setPaymentSign("Y");
        salesReportTransactionTenderOptional.setTaxIncludedPaymentAmountCurrencyCode("CAD");
        salesReportTransactionTenderOptional
                .setTaxIncludedPaymentAmountValue(new BigDecimal(6000.000));
        List<SalesReportTransactionTenderOptional> tenderDetailList = new ArrayList<>();
        tenderDetailList.add(salesReportTransactionTenderOptional);
        when(storeTransactionInquiryOptionalMapper
                .selectSalesReportTransactionHeaderByCondition(anyObject()))
                        .thenReturn(headerDetailList);
        when(storeTransactionInquiryOptionalMapper
                .selectSalesReportTransactionDetailByCondition(anyObject()))
                        .thenReturn(itemDetailList);
        when(storeTransactionInquiryOptionalMapper
                .selectSalesReportTransactionDiscountDetailByCondition(anyObject()))
                        .thenReturn(discountDetailList);
        when(storeTransactionInquiryOptionalMapper
                .selectSalesReportTransactionTaxDetailByCondition(anyObject()))
                        .thenReturn(taxDetailList);
        when(storeTransactionInquiryOptionalMapper
                .selectSalesReportTransactionTenderDetailByCondition(anyObject()))
                        .thenReturn(tenderDetailList);
        storeTransactionInquiryForm =
                storeTransactionInquiryService.getPreviousItemDetail(storeTransactionInquiryForm);
    }

    /**
     * Test case of get next detail.
     */
    @Test
    public void testGetNextDetail() {
        TranslationStoreCodeMasterOptional translationStoreCodeMasterOptional =
                new TranslationStoreCodeMasterOptional();
        translationStoreCodeMasterOptional.setStoreCode("112326");
        translationStoreCodeMasterOptional.setSystemBrandCode("UQ");
        translationStoreCodeMasterOptional.setSystemCountryCode("UK");
        List<TranslationStoreCodeMasterOptional> brand = new ArrayList<>();
        brand.add(translationStoreCodeMasterOptional);
        when(translationStoreCodeMasterOptionalMapper.selectByCondition(anyObject()))
                .thenReturn(brand);
        CommonCodeMaster countryCodeMaster = new CommonCodeMaster();
        countryCodeMaster.setTypeId("system_country_code");
        countryCodeMaster.setTypeValue("017");
        countryCodeMaster.setName1("JP");
        List<CommonCodeMaster> systemCountryList = new ArrayList<>();
        systemCountryList.add(countryCodeMaster);

        CommonCodeMaster brandCodeMaster = new CommonCodeMaster();
        brandCodeMaster.setTypeId("system_brand_code");
        brandCodeMaster.setTypeValue("019");
        brandCodeMaster.setName1("1JP");
        List<CommonCodeMaster> systemBrandList = new ArrayList<>();
        systemBrandList.add(brandCodeMaster);

        CommonCodeMaster transactionTypeMaster = new CommonCodeMaster();
        transactionTypeMaster.setTypeId("transaction_type");
        transactionTypeMaster.setTypeValue("018");
        transactionTypeMaster.setName1("ABC");
        List<CommonCodeMaster> transactionTypeList = new ArrayList<>();
        transactionTypeList.add(transactionTypeMaster);

        CommonCodeMaster taxationType = new CommonCodeMaster();
        taxationType.setTypeId("TAXATION_TYPE");
        taxationType.setTypeValue("1");
        taxationType.setName1("Y");
        List<CommonCodeMaster> taxationTypeList = new ArrayList<>();
        taxationTypeList.add(taxationType);

        CommonCodeMaster discountType = new CommonCodeMaster();
        discountType.setTypeId("Promotion Type Store Discount type");
        discountType.setTypeValue("1");
        discountType.setName1("Mix&Match");
        List<CommonCodeMaster> discountTypeList = new ArrayList<>();
        discountTypeList.add(discountType);

        TranslationTenderMasterOptional tenderGroupMaster = new TranslationTenderMasterOptional();
        tenderGroupMaster.setTenderGroup("COUPON");
        tenderGroupMaster.setImsTenderGroup("IMS");
        List<TranslationTenderMasterOptional> tenderGroupList = new ArrayList<>();
        tenderGroupList.add(tenderGroupMaster);

        TranslationTenderMasterOptional tenderIdMaster = new TranslationTenderMasterOptional();
        tenderIdMaster.setTenderId("ID");
        tenderIdMaster.setImsTenderId(123);
        List<TranslationTenderMasterOptional> tenderIdList = new ArrayList<>();
        tenderIdList.add(tenderIdMaster);
        NonItemMaster nonItemMaster = new NonItemMaster();
        nonItemMaster.setSystemBrandCode("UQ");
        nonItemMaster.setSystemCountryCode("UK");
        nonItemMaster.setNonItemCode("Code123");
        nonItemMaster.setPosItemName("name");
        List<NonItemMaster> nonItemMasterList = new ArrayList<>();
        nonItemMasterList.add(nonItemMaster);
        BusinessCountryStateSettingMasterOptional taxType =
                new BusinessCountryStateSettingMasterOptional();
        taxType.setCodeValue("2");
        List<BusinessCountryStateSettingMasterOptional> taxTypeList = new ArrayList<>();
        taxTypeList.add(taxType);
        when(nonItemMasterMapper.selectByCondition(anyObject())).thenReturn(nonItemMasterList);
        when(storeTransactionInquiryOptionalMapper.selectPaymentTenderGroup(anyObject()))
                .thenReturn(tenderGroupList);
        when(storeTransactionInquiryOptionalMapper.selectPaymentTenderId(anyObject()))
                .thenReturn(tenderIdList);
        when(commonCodeMasterMapper.selectByCondition(anyObject())).thenReturn(systemCountryList)
                .thenReturn(systemBrandList)
                .thenReturn(transactionTypeList)
                .thenReturn(taxationTypeList)
                .thenReturn(discountTypeList);
        when(businessCountryStateSettingMasterOptionalMapper.selectByCondition(anyObject()))
                .thenReturn(taxTypeList);
        SalesReportTransactionHeaderOptional salesReportTransactionHeaderT1 =
                new SalesReportTransactionHeaderOptional();
        salesReportTransactionHeaderT1.setSalesTransactionId("T10001");
        salesReportTransactionHeaderT1.setDepositValue(new BigDecimal(3000));
        salesReportTransactionHeaderT1.setChangeValue(new BigDecimal(5000));
        salesReportTransactionHeaderT1.setSalesTransactionType("PVOID");
        SalesReportTransactionHeaderOptional salesReportTransactionHeaderT2 =
                new SalesReportTransactionHeaderOptional();
        salesReportTransactionHeaderT2.setSalesTransactionId("T10001");
        salesReportTransactionHeaderT2.setDepositValue(new BigDecimal(3000));
        salesReportTransactionHeaderT2.setChangeValue(new BigDecimal(5000));
        salesReportTransactionHeaderT2.setSalesTransactionType("RETURN");
        List<SalesReportTransactionHeaderOptional> headerDetailList = new ArrayList<>();
        headerDetailList.add(salesReportTransactionHeaderT2);
        headerDetailList.add(salesReportTransactionHeaderT1);
        storeTransactionInquiryForm.setBusinessDate("20180615");
        storeTransactionInquiryForm.setHeaderDetailList(headerDetailList);
        storeTransactionInquiryForm.setTaxTypeFlag(true);
        storeTransactionInquiryForm.setCountIndex(0);
        storeTransactionInquiryForm.setDecimalFormat("2");
        storeTransactionInquiryForm.setPreviousNextFlag(true);
        storeTransactionInquiryForm.setPaymentAmountFrom("1000");
        storeTransactionInquiryForm.setPaymentAmountTo("2000");
        SalesReportTransactionItemDetailOptional salesReportTransactionItemDetailOptional =
                new SalesReportTransactionItemDetailOptional();
        salesReportTransactionItemDetailOptional.setL3ItemCode("L3Code");
        salesReportTransactionItemDetailOptional.setL3PosProductName("L3Pos");
        salesReportTransactionItemDetailOptional.setDetailSubNumber(2);
        salesReportTransactionItemDetailOptional.setDetailQuantity(new BigDecimal(1000));
        salesReportTransactionItemDetailOptional.setRetailUnitPriceTaxExcluded(new BigDecimal(800));
        salesReportTransactionItemDetailOptional.setRetailUnitPriceTaxIncluded(new BigDecimal(500));
        salesReportTransactionItemDetailOptional.setQuantityCode("N");
        List<SalesReportTransactionItemDetailOptional> itemDetailList = new ArrayList<>();
        itemDetailList.add(salesReportTransactionItemDetailOptional);
        SalesReportTransactionDiscountDetailOptional discountDetail =
                new SalesReportTransactionDiscountDetailOptional();
        discountDetail.setPromotionType("1000");
        discountDetail.setStoreDiscountType("00");
        discountDetail.setDetailSubNumber(2);
        discountDetail.setDiscountAmountTaxExcludedCurrencyCode("JPY");
        discountDetail.setDiscountAmountTaxExcluded(new BigDecimal(5000.000));
        discountDetail.setDiscountAmountTaxIncludedCurrencyCode("CAD");
        discountDetail.setDiscountAmountTaxIncluded(new BigDecimal(3000.000));
        discountDetail.setDiscountQuantity(50);
        List<SalesReportTransactionDiscountDetailOptional> discountDetailList = new ArrayList<>();
        discountDetailList.add(discountDetail);
        SalesReportTransactionTaxOptional salesReportTransactionTaxOptional =
                new SalesReportTransactionTaxOptional();
        salesReportTransactionTaxOptional.setTaxAmountCurrencyCode("JPY");
        salesReportTransactionTaxOptional.setTaxGroup("2");
        salesReportTransactionTaxOptional.setTaxAmountSign("Y");
        salesReportTransactionTaxOptional.setTaxAmountValue(new BigDecimal(2000.000));
        List<SalesReportTransactionTaxOptional> taxDetailList = new ArrayList<>();
        taxDetailList.add(salesReportTransactionTaxOptional);
        SalesReportTransactionTenderOptional salesReportTransactionTenderOptional =
                new SalesReportTransactionTenderOptional();
        salesReportTransactionTenderOptional.setTenderGroup("2");
        salesReportTransactionTenderOptional.setPaymentSign("Y");
        salesReportTransactionTenderOptional.setTaxIncludedPaymentAmountCurrencyCode("CAD");
        salesReportTransactionTenderOptional
                .setTaxIncludedPaymentAmountValue(new BigDecimal(6000.000));
        List<SalesReportTransactionTenderOptional> tenderDetailList = new ArrayList<>();
        tenderDetailList.add(salesReportTransactionTenderOptional);
        when(storeTransactionInquiryOptionalMapper
                .selectSalesReportTransactionHeaderByCondition(anyObject()))
                        .thenReturn(headerDetailList);
        when(storeTransactionInquiryOptionalMapper
                .selectSalesReportTransactionDetailByCondition(anyObject()))
                        .thenReturn(itemDetailList);
        when(storeTransactionInquiryOptionalMapper
                .selectSalesReportTransactionDiscountDetailByCondition(anyObject()))
                        .thenReturn(discountDetailList);
        when(storeTransactionInquiryOptionalMapper
                .selectSalesReportTransactionTaxDetailByCondition(anyObject()))
                        .thenReturn(taxDetailList);
        when(storeTransactionInquiryOptionalMapper
                .selectSalesReportTransactionTenderDetailByCondition(anyObject()))
                        .thenReturn(tenderDetailList);
        storeTransactionInquiryForm =
                storeTransactionInquiryService.getNextItemDetail(storeTransactionInquiryForm);
    }

    /**
     * Test case of change store for tender group.
     */
    @Test
    public void testChangeStore() {
        TranslationStoreCodeMasterOptional translationStoreCodeMasterOptional =
                new TranslationStoreCodeMasterOptional();
        translationStoreCodeMasterOptional.setStoreCode("112326");
        translationStoreCodeMasterOptional.setSystemBrandCode("UQ");
        translationStoreCodeMasterOptional.setSystemCountryCode("UK");
        List<TranslationStoreCodeMasterOptional> brand = new ArrayList<>();
        brand.add(translationStoreCodeMasterOptional);
        when(translationStoreCodeMasterOptionalMapper.selectByCondition(anyObject()))
                .thenReturn(brand);
        CommonCodeMaster countryCodeMaster = new CommonCodeMaster();
        countryCodeMaster.setTypeId("system_country_code");
        countryCodeMaster.setTypeValue("017");
        countryCodeMaster.setName1("JP");
        List<CommonCodeMaster> systemCountryList = new ArrayList<>();
        systemCountryList.add(countryCodeMaster);

        CommonCodeMaster brandCodeMaster = new CommonCodeMaster();
        brandCodeMaster.setTypeId("system_brand_code");
        brandCodeMaster.setTypeValue("019");
        brandCodeMaster.setName1("1JP");
        List<CommonCodeMaster> systemBrandList = new ArrayList<>();
        systemBrandList.add(brandCodeMaster);

        CommonCodeMaster transactionTypeMaster = new CommonCodeMaster();
        transactionTypeMaster.setTypeId("transaction_type");
        transactionTypeMaster.setTypeValue("018");
        transactionTypeMaster.setName1("ABC");
        List<CommonCodeMaster> transactionTypeList = new ArrayList<>();
        transactionTypeList.add(transactionTypeMaster);

        CommonCodeMaster taxationType = new CommonCodeMaster();
        taxationType.setTypeId("TAXATION_TYPE");
        taxationType.setTypeValue("1");
        taxationType.setName1("Y");
        List<CommonCodeMaster> taxationTypeList = new ArrayList<>();
        taxationTypeList.add(taxationType);

        CommonCodeMaster discountType = new CommonCodeMaster();
        discountType.setTypeId("Promotion Type Store Discount type");
        discountType.setTypeValue("1");
        discountType.setName1("Mix&Match");
        List<CommonCodeMaster> discountTypeList = new ArrayList<>();
        discountTypeList.add(discountType);

        TranslationTenderMasterOptional tenderGroupMaster = new TranslationTenderMasterOptional();
        tenderGroupMaster.setTenderGroup("COUPON");
        tenderGroupMaster.setImsTenderGroup("IMS");
        List<TranslationTenderMasterOptional> tenderGroupList = new ArrayList<>();
        tenderGroupList.add(tenderGroupMaster);

        TranslationTenderMasterOptional tenderIdMaster = new TranslationTenderMasterOptional();
        tenderIdMaster.setTenderId("ID");
        tenderIdMaster.setImsTenderId(123);
        List<TranslationTenderMasterOptional> tenderIdList = new ArrayList<>();
        tenderIdList.add(tenderIdMaster);
        StoreTransactionInquiryDetail storeTransactionInquiryDetail =
                new StoreTransactionInquiryDetail();
        storeTransactionInquiryDetail.setNonItemCode("Code123");
        storeTransactionInquiryDetail.setPosItemName("name");
        // Get non merchandise item list.
        List<StoreTransactionInquiryDetail> nonMerchandiseItemList = new ArrayList<>();
        nonMerchandiseItemList.add(storeTransactionInquiryDetail);
        when(storeTransactionInquiryOptionalMapper.selectByCountryBrandCode(anyObject()))
                .thenReturn(nonMerchandiseItemList);
        when(storeTransactionInquiryOptionalMapper.selectPaymentTenderGroup(anyObject()))
                .thenReturn(tenderGroupList);
        when(storeTransactionInquiryOptionalMapper.selectPaymentTenderId(anyObject()))
                .thenReturn(tenderIdList);
        when(commonCodeMasterMapper.selectByCondition(anyObject())).thenReturn(systemCountryList)
                .thenReturn(systemBrandList)
                .thenReturn(transactionTypeList)
                .thenReturn(taxationTypeList)
                .thenReturn(discountTypeList);
        List<SalesReportTransactionHeaderOptional> headerDetailList = new ArrayList<>();
        SalesReportTransactionHeaderOptional salesReportTransactionHeaderOptional =
                new SalesReportTransactionHeaderOptional();
        salesReportTransactionHeaderOptional.setImsLinkageDate("20180621");
        salesReportTransactionHeaderOptional.setDepositValue(new BigDecimal(500.00));
        salesReportTransactionHeaderOptional.setChangeValue(new BigDecimal(1500.00));
        headerDetailList.add(salesReportTransactionHeaderOptional);
        storeTransactionInquiryForm.setHeaderDetailList(headerDetailList);
        when(storeTransactionInquiryOptionalMapper
                .selectSalesReportTransactionHeaderByCondition(anyObject()))
                        .thenReturn(headerDetailList);
        List<DropDownItem> itemListForTenderGroup = storeTransactionInquiryService
                .changeStoreForTenderGroup(storeTransactionInquiryForm, bindingResult, model);
        assertEquals(1, itemListForTenderGroup.size());
        List<DropDownItem> itemListForTenderId = storeTransactionInquiryService
                .changeStoreForTenderId(storeTransactionInquiryForm, bindingResult, model);
        assertEquals(1, itemListForTenderId.size());

    }
}
