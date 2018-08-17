package com.fastretailing.dcp.sales.importtransaction.component;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import com.fastretailing.dcp.sales.common.constants.DBItem;
import com.fastretailing.dcp.sales.common.type.ErrorType;
import com.fastretailing.dcp.sales.common.type.TransactionType;
import com.fastretailing.dcp.sales.importtransaction.dto.ItemDiscount;
import com.fastretailing.dcp.sales.importtransaction.dto.ItemTaxDetail;
import com.fastretailing.dcp.sales.importtransaction.dto.NonItemDetail;
import com.fastretailing.dcp.sales.importtransaction.dto.NonItemDiscountDetail;
import com.fastretailing.dcp.sales.importtransaction.dto.NonItemInfo;
import com.fastretailing.dcp.sales.importtransaction.dto.NonItemTaxDetail;
import com.fastretailing.dcp.sales.importtransaction.dto.SalesTransactionTaxDetail;
import com.fastretailing.dcp.sales.importtransaction.dto.SalesTransactionTender;
import com.fastretailing.dcp.sales.importtransaction.dto.SalesTransactionTotal;
import com.fastretailing.dcp.sales.importtransaction.dto.TenderInfo;
import com.fastretailing.dcp.sales.importtransaction.dto.Transaction;
import com.fastretailing.dcp.sales.importtransaction.dto.TransactionImportData;
import com.fastretailing.dcp.sales.importtransaction.dto.TransactionItemDetail;
import com.fastretailing.dcp.sales.importtransaction.entity.ItemMaster;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesOrderInformation;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionErrorDetail;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionHeader;
import com.fastretailing.dcp.sales.importtransaction.entity.StoreMaster;
import com.fastretailing.dcp.sales.importtransaction.entity.TranslationStoreCodeMaster;
import com.fastretailing.dcp.sales.importtransaction.entity.TranslationTenderMaster;
import com.fastretailing.dcp.sales.importtransaction.repository.ItemMasterMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.SalesOrderInformationMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.SalesTransactionHeaderMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.StoreMasterMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.TranslationStoreCodeMasterMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.TranslationTenderMasterMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.BusinessReceiverCommonCodeMasterMapper;
import com.fastretailing.dcp.storecommon.dto.Price;

@SpringBootTest
@RunWith(SpringRunner.class)
public class InitialHelperTest {

    /** Table name m item. */
    private static final String TABLE_NAME_M_ITEM = "m_item";
    /** Item id table id. */
    private static final String ITEM_ID_TABLE_ID = "table_name";
    /** Item id store code. */
    private static final String ITEM_ID_STORE_CODE = "store_code";
    /**
     * Component for operating DB operations on the store master table.
     */
    @MockBean
    private StoreMasterMapper storeMasterMapper;

    /**
     * Component for operating DB operations on the translation store code master table.
     */
    @MockBean
    private TranslationStoreCodeMasterMapper translationStoreCodeMasterMapper;

    /**
     * Component for operating DB operations on the item master table.
     */
    @MockBean
    private ItemMasterMapper itemMasterMapper;

    /**
     * Component for operating DB operations on the sales transaction header table.
     */
    @MockBean
    private SalesTransactionHeaderMapper salesTransactionHeaderMapper;
    /**
     * Component for operating DB operations on the sales order information table.
     */
    @MockBean
    private SalesOrderInformationMapper salesOrderInformationMapper;
    /**
     * Component for operating DB operations on the sales transaction tender master table.
     */
    @MockBean
    private TranslationTenderMasterMapper translationTenderMasterMapper;
    /**
     * Common code master mapper.
     */
    @MockBean
    private BusinessReceiverCommonCodeMasterMapper commonCodeMasterMapper;
    /** Business checker. */
    @MockBean
    private BusinessChecker businessChecker;

    @Autowired
    private InitialHelper initialHelper;

    private static final String TRANSACTION_STORE_CODE = "1111";

    private static final String SALES_TRANSACTION_ID = "transaction10000002";

    private static final String INTEGRATED_ORDER_ID = "integrated100000001";

    List<TranslationTenderMaster> translationTenderMasterList = new ArrayList<>();
    private SalesTransactionTender tender = new SalesTransactionTender();
    TranslationTenderMaster translationTenderMaster = new TranslationTenderMaster();
    private String storeCode = "1111";
    private TransactionImportData initialImportData;
    private Transaction firstTransaction;
    private SalesTransactionErrorDetail salesTransactionErrorDetailEntity;

    @Before
    public void setUp() {
        tender.setTenderGroupId("1002");
        tender.setTenderId("1001");
        translationTenderMaster.setTenderId("1001");
        translationTenderMaster.setTenderGroup("1002");
        translationTenderMasterList.add(translationTenderMaster);
        TranslationTenderMaster translationTenderMaster2 = new TranslationTenderMaster();
        translationTenderMaster2.setTenderId("1001");
        translationTenderMaster2.setTenderGroup("1002");
        translationTenderMasterList.add(translationTenderMaster2);
        when(translationTenderMasterMapper.selectByCondition(any()))
                .thenReturn(translationTenderMasterList);

        initialImportData = createTransactionImportData();
        firstTransaction = getTransaction();
        salesTransactionErrorDetailEntity = createSalesTransactionErrorDetail();

        StoreMaster storeMaster = new StoreMaster();
        storeMaster.setStoreCode(storeCode);
        storeMaster.setCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        when(storeMasterMapper.selectByPrimaryKey(anyString())).thenReturn(storeMaster);

        TranslationStoreCodeMaster translationStoreCodeMaster = new TranslationStoreCodeMaster();
        translationStoreCodeMaster.setSystemBusinessCode("buc");
        translationStoreCodeMaster.setSystemBrandCode("brc");
        translationStoreCodeMaster.setSystemCountryCode("scc");
        translationStoreCodeMaster.setStoreCode(storeCode);
        when(translationStoreCodeMasterMapper.selectByPrimaryKey(anyString()))
                .thenReturn(translationStoreCodeMaster);

        ItemMaster itemMaster = new ItemMaster();
        itemMaster.setMajorCategoryCode("001");
        when(itemMasterMapper.selectByPrimaryKey(any())).thenReturn(itemMaster);

        List<SalesTransactionHeader> salesTransactionHeaderList = new ArrayList<>();
        SalesTransactionHeader salesTransactionHeader = new SalesTransactionHeader();
        salesTransactionHeader.setIntegratedOrderId("headerIntegratedId002");
        salesTransactionHeader.setOriginalTransactionId("headerOriginalId0001");
        salesTransactionHeader
                .setChangeCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        salesTransactionHeader
                .setDepositCurrencyCode(Currency.getInstance(Locale.JAPAN).getCurrencyCode());
        salesTransactionHeaderList.add(salesTransactionHeader);
        when(salesTransactionHeaderMapper.selectByCondition(any()))
                .thenReturn(salesTransactionHeaderList);

        SalesOrderInformation salesOrderInformation = new SalesOrderInformation();
        salesOrderInformation.setStoreCode(storeCode);
        salesOrderInformation.setIntegratedOrderId("integer001");
        salesOrderInformation.setCustomerId("customerid001");
        salesOrderInformation.setChannelCode("original1");
        salesOrderInformation.setSystemBrandCode("originalBrc");
        salesOrderInformation.setSystemBusinessCode("originalBuc");
        salesOrderInformation.setSystemCountryCode("originalScc");
        salesOrderInformation.setOrderConfirmationBusinessDate("2017-12-31");
        salesOrderInformation.setOrderConfirmationDateTime(
                OffsetDateTime.of(2017, 12, 31, 8, 0, 0, 0, ZoneOffset.of("Z")).toLocalDateTime());
        when(salesOrderInformationMapper.selectByPrimaryKey(anyString()))
                .thenReturn(salesOrderInformation);

        doNothing().when(businessChecker).insertSalesTransactionErrorBusiness(any(), any(), any(),
                any());

        when(commonCodeMasterMapper.selectTypeName(any(), any())).thenAnswer(new Answer<String>() {
            public String answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                if ("system_brand_code".equals(args[0])) {
                    return "brc";
                } else if ("system_business_code".equals(args[0])) {
                    return "buc";
                } else {
                    return "scc";
                }
            }
        });
    }

    @Test
    public void testFindTranslationTenderMasterFirstExisted() {
        Optional<TranslationTenderMaster> masterOptional =
                initialHelper.findTranslationTenderMasterFirst(tender, storeCode);
        assertThat(masterOptional.isPresent(), is(true));
        assertEquals(translationTenderMaster, masterOptional.get());
    }

    @Test
    public void testFindTranslationTenderMasterFirstNotExisted() {
        when(translationTenderMasterMapper.selectByCondition(any())).thenReturn(new ArrayList<>());
        assertThat(initialHelper.findTranslationTenderMasterFirst(tender, storeCode).isPresent(),
                is(false));
    }

    @Test
    public void testInitialSale() {
        firstTransaction.setTransactionType(TransactionType.SALE.getTransactionType());
        assertThat(initialHelper.initialPosImportData(initialImportData, firstTransaction,
                salesTransactionErrorDetailEntity), is(true));
    }

    @Test
    public void testInitialReturn() {
        firstTransaction.setTransactionType(TransactionType.RETURN.getTransactionType());
        assertThat(initialHelper.initialPosImportData(initialImportData, firstTransaction,
                salesTransactionErrorDetailEntity), is(true));
    }

    @Test
    public void testInitialPvoid() {
        firstTransaction.setTransactionType(TransactionType.PVOID.getTransactionType());
        assertThat(initialHelper.initialPosImportData(initialImportData, firstTransaction,
                salesTransactionErrorDetailEntity), is(true));
    }

    @Test
    public void testInitialOther() {
        firstTransaction.setTransactionType("other");
        assertThat(initialHelper.initialPosImportData(initialImportData, firstTransaction,
                salesTransactionErrorDetailEntity), is(true));
    }

    @Test
    public void testInitialSaleNull() {
        setNullInImportData(initialImportData);
        initialImportData.setStoreCode(null);
        firstTransaction.setTransactionType(TransactionType.SALE.getTransactionType());
        assertThat(initialHelper.initialPosImportData(initialImportData, firstTransaction,
                salesTransactionErrorDetailEntity), is(true));
        TransactionImportData expected = createTransactionImportData();
        setExpectedForSaleAndReturn(expected);
        expected.getTransactionList().forEach(tra -> {
            tra.setOrderStatus("SHIPMENT_CONFIRMED");
        });
        assertThat(expected).isEqualToComparingFieldByField(initialImportData);
    }

    @Test
    public void testInitialReturnNull() {
        setNullInImportData(initialImportData);
        firstTransaction.setTransactionType(TransactionType.RETURN.getTransactionType());
        assertThat(initialHelper.initialPosImportData(initialImportData, firstTransaction,
                salesTransactionErrorDetailEntity), is(true));
        TransactionImportData expected = createTransactionImportData();
        setExpectedForSaleAndReturn(expected);
        expected.getTransactionList().forEach(tra -> {
            tra.setOrderStatus("ORDER_CANCELED");
        });
        assertThat(expected).isEqualToComparingFieldByField(initialImportData);
    }

    @Test
    public void testInitialPvoidNull() {
        setNullInImportData(initialImportData);
        initialImportData.setStoreCode(null);
        firstTransaction.setTransactionType(TransactionType.PVOID.getTransactionType());
        assertThat(initialHelper.initialPosImportData(initialImportData, firstTransaction,
                salesTransactionErrorDetailEntity), is(true));
        TransactionImportData expected = createTransactionImportData();
        setNullInImportData(expected);
        setExpectedForPvoid(expected);
        assertThat(expected).isEqualToComparingFieldByField(initialImportData);
    }

    @Test
    public void testInitialPvoidNullPrice() {
        setNullInImportData(initialImportData);
        initialImportData.getTransactionList().forEach(tra -> {
            tra.setDeposit(null);
        });
        firstTransaction.setTransactionType(TransactionType.PVOID.getTransactionType());
        assertThat(initialHelper.initialPosImportData(initialImportData, firstTransaction,
                salesTransactionErrorDetailEntity), is(true));
    }

    @Test
    public void testInitialPvoidEmptyHeader() {
        when(salesTransactionHeaderMapper.selectByCondition(any())).thenReturn(new ArrayList<>());
        SalesTransactionErrorDetail expect = createSalesTransactionErrorDetail();
        expect.setErrorType(ErrorType.BUSINESS_ERROR.getErrorType());
        expect.setErrorItemId1(ITEM_ID_TABLE_ID);
        expect.setErrorItemValue1(DBItem.TABLE_NAME_T_SALES_TRANSACTION_HEADER);
        expect.setErrorItemId2(ITEM_ID_STORE_CODE);
        expect.setErrorItemValue2(storeCode);
        firstTransaction.setTransactionType(TransactionType.PVOID.getTransactionType());
        assertThat(initialHelper.initialPosImportData(initialImportData, firstTransaction,
                salesTransactionErrorDetailEntity), is(false));
        assertEquals(expect.toString(), salesTransactionErrorDetailEntity.toString());
    }

    @Test
    public void testInitialPvoidEmptyOrderInformation() {
        when(salesOrderInformationMapper.selectByPrimaryKey(anyString())).thenReturn(null);
        SalesTransactionErrorDetail expect = createSalesTransactionErrorDetail();
        expect.setErrorType(ErrorType.BUSINESS_ERROR.getErrorType());
        expect.setErrorItemId1(ITEM_ID_TABLE_ID);
        expect.setErrorItemValue1(DBItem.TABLE_NAME_T_SALES_ORDER_INFORMATION);
        expect.setErrorItemId2(ITEM_ID_STORE_CODE);
        expect.setErrorItemValue2(storeCode);
        firstTransaction.setTransactionType(TransactionType.PVOID.getTransactionType());
        assertThat(initialHelper.initialPosImportData(initialImportData, firstTransaction,
                salesTransactionErrorDetailEntity), is(false));
        assertEquals(expect.toString(), salesTransactionErrorDetailEntity.toString());
    }

    @Test
    public void testInitialPvoidEmptyStoreMaster() {
        when(storeMasterMapper.selectByPrimaryKey(anyString())).thenReturn(null);
        SalesTransactionErrorDetail expect = createSalesTransactionErrorDetail();
        expect.setErrorType(ErrorType.BUSINESS_ERROR.getErrorType());
        expect.setErrorItemId1(ITEM_ID_TABLE_ID);
        expect.setErrorItemValue1(DBItem.TABLE_NAME_M_STORE);
        expect.setErrorItemId2(ITEM_ID_STORE_CODE);
        expect.setErrorItemValue2(storeCode);
        firstTransaction.setTransactionType(TransactionType.PVOID.getTransactionType());
        assertThat(initialHelper.initialPosImportData(initialImportData, firstTransaction,
                salesTransactionErrorDetailEntity), is(false));
        assertEquals(expect.toString(), salesTransactionErrorDetailEntity.toString());
    }

    @Test
    public void testInitialPvoidEmptyTranslationStoreCodeMaster() {
        when(translationStoreCodeMasterMapper.selectByPrimaryKey(anyString())).thenReturn(null);
        SalesTransactionErrorDetail expect = createSalesTransactionErrorDetail();
        expect.setErrorType(ErrorType.BUSINESS_ERROR.getErrorType());
        expect.setErrorItemId1(ITEM_ID_TABLE_ID);
        expect.setErrorItemValue1(DBItem.TABLE_NAME_M_TRANS_STORE_CODE_TABLE);
        expect.setErrorItemId2(ITEM_ID_STORE_CODE);
        expect.setErrorItemValue2(storeCode);
        firstTransaction.setTransactionType(TransactionType.PVOID.getTransactionType());
        assertThat(initialHelper.initialPosImportData(initialImportData, firstTransaction,
                salesTransactionErrorDetailEntity), is(false));
        assertEquals(expect.toString(), salesTransactionErrorDetailEntity.toString());
    }

    @Test
    public void testInitialSaleEmptyTranslationStoreCodeMaster() {
        when(translationStoreCodeMasterMapper.selectByPrimaryKey(anyString())).thenReturn(null);
        SalesTransactionErrorDetail expect = createSalesTransactionErrorDetail();
        expect.setErrorType(ErrorType.BUSINESS_ERROR.getErrorType());
        expect.setErrorItemId1(ITEM_ID_TABLE_ID);
        expect.setErrorItemValue1(DBItem.TABLE_NAME_M_TRANS_STORE_CODE_TABLE);
        expect.setErrorItemId2(ITEM_ID_STORE_CODE);
        expect.setErrorItemValue2(storeCode);
        firstTransaction.setTransactionType(TransactionType.SALE.getTransactionType());
        assertThat(initialHelper.initialPosImportData(initialImportData, firstTransaction,
                salesTransactionErrorDetailEntity), is(false));
        assertEquals(expect.toString(), salesTransactionErrorDetailEntity.toString());
    }

    @Test
    public void testInitialSaleEmptyStoreMaster() {
        when(storeMasterMapper.selectByPrimaryKey(anyString())).thenReturn(null);
        SalesTransactionErrorDetail expect = createSalesTransactionErrorDetail();
        expect.setErrorType(ErrorType.BUSINESS_ERROR.getErrorType());
        expect.setErrorItemId1(ITEM_ID_TABLE_ID);
        expect.setErrorItemValue1(DBItem.TABLE_NAME_M_STORE);
        expect.setErrorItemId2(ITEM_ID_STORE_CODE);
        expect.setErrorItemValue2(storeCode);
        firstTransaction.setTransactionType(TransactionType.SALE.getTransactionType());
        assertThat(initialHelper.initialPosImportData(initialImportData, firstTransaction,
                salesTransactionErrorDetailEntity), is(false));
        assertEquals(expect.toString(), salesTransactionErrorDetailEntity.toString());
    }

    @Test
    public void testInitialSaleEmptyItemMaster() {
        when(itemMasterMapper.selectByPrimaryKey(any())).thenReturn(null);
        SalesTransactionErrorDetail expect = createSalesTransactionErrorDetail();
        expect.setErrorType(ErrorType.BUSINESS_ERROR.getErrorType());
        expect.setErrorItemId1(ITEM_ID_TABLE_ID);
        expect.setErrorItemValue1(TABLE_NAME_M_ITEM);
        expect.setErrorItemId2(ITEM_ID_STORE_CODE);
        expect.setErrorItemValue2(storeCode);
        firstTransaction.setTransactionType(TransactionType.SALE.getTransactionType());
        assertThat(initialHelper.initialPosImportData(initialImportData, firstTransaction,
                salesTransactionErrorDetailEntity), is(false));
        assertEquals(expect.toString(), salesTransactionErrorDetailEntity.toString());
    }

    @Test
    public void testInitialReturnEmptyItemMaster() {
        when(itemMasterMapper.selectByPrimaryKey(any())).thenReturn(null);
        SalesTransactionErrorDetail expect = createSalesTransactionErrorDetail();
        expect.setErrorType(ErrorType.BUSINESS_ERROR.getErrorType());
        expect.setErrorItemId1(ITEM_ID_TABLE_ID);
        expect.setErrorItemValue1(TABLE_NAME_M_ITEM);
        expect.setErrorItemId2(ITEM_ID_STORE_CODE);
        expect.setErrorItemValue2(storeCode);
        firstTransaction.setTransactionType(TransactionType.RETURN.getTransactionType());
        assertThat(initialHelper.initialPosImportData(initialImportData, firstTransaction,
                salesTransactionErrorDetailEntity), is(false));
        assertEquals(expect.toString(), salesTransactionErrorDetailEntity.toString());
    }

    private void setNullInImportData(TransactionImportData importData) {
        importData.setUpdateType(null);
        importData.setErrorCheckType(null);
        importData.setDataAlterationBackboneLinkageType(null);
        importData.setDataAlterationSalesLinkageType(null);
        importData.setIntegratedOrderId(null);
        importData.setChannelCode(null);
        importData.setStoreCode(storeCode);
        importData.setSystemBrandCode(null);
        importData.setSystemBusinessCode(null);
        importData.setSystemCountryCode(null);
        importData.setCustomerId(null);
        importData.setOrderConfirmationBusinessDate(null);
        importData.setOrderConfirmationDateTime(null);
        importData.setDataCorrectionEditingFlag(null);

        importData.getTransactionList().forEach(transaction -> {
            transaction.setIntegratedOrderId(null);
            transaction.setOrderSubNumber(null);
            transaction.setSalesTransactionId(null);
            transaction.setSalesLinkageType(null);
            transaction.setSystemBrandCode(null);
            transaction.setSystemBusinessCode(null);
            transaction.setSystemCountryCode(null);
            transaction.setOriginalTransactionId(null);
            transaction.getDeposit().setCurrencyCode(null);
            transaction.getChange().setCurrencyCode(null);
            transaction.setReceiptIssuedFlag(null);
            transaction.setOrderStatus(null);
            transaction.setSalesTransactionDiscountFlag(null);
            transaction.getSalesTransactionDiscountAmountRate().setCurrencyCode(null);
            transaction.setConsistencySalesFlag(null);
            transaction.setEmployeeSaleFlag(null);
            transaction.setEReceiptTargetFlag(null);
            transaction.getTransactionItemDetailList().forEach(itemDetail -> {
                itemDetail.setDeptCode(null);
                itemDetail.getItemCost().setCurrencyCode(null);
                itemDetail.getItemCost().setValue(null);
                itemDetail.getInitialSellingPrice().setCurrencyCode(null);
                itemDetail.getBItemSellingPrice().setCurrencyCode(null);
                itemDetail.getItemNewPrice().setCurrencyCode(null);
                itemDetail.getItemUnitPriceTaxExcluded().setCurrencyCode(null);
                itemDetail.getItemUnitPriceTaxIncluded().setCurrencyCode(null);
                itemDetail.getItemSalesAmtTaxExcluded().setCurrencyCode(null);
                itemDetail.getItemSalesAmtTaxIncluded().setCurrencyCode(null);
                itemDetail.getBundlePurchasePrice().setCurrencyCode(null);
                itemDetail.getItemDiscountAmount().setCurrencyCode(null);
                itemDetail.setBundleSalesFlag(null);
                itemDetail.getBundleSalesPrice().setCurrencyCode(null);
                itemDetail.getNonItemDetailListByItem().forEach(non -> {
                    non.getNonItemUnitPriceTaxExcluded().setCurrencyCode(null);
                    non.getNonItemUnitPriceTaxIncluded().setCurrencyCode(null);
                    non.getNonItemSalesAmtTaxExcluded().setCurrencyCode(null);
                    non.getNonItemSalesAmtTaxIncluded().setCurrencyCode(null);
                    non.getNonItemNewPrice().setCurrencyCode(null);
                    non.getNonItemDiscountDetailList().forEach(nonDiscount -> {
                        nonDiscount.getNonItemDiscountAmtTaxExcluded().setCurrencyCode(null);
                        nonDiscount.getNonItemDiscountAmtTaxIncluded().setCurrencyCode(null);
                    });
                    non.getNonItemTaxDetailList().forEach(nonTax -> {
                        nonTax.getNonItemTaxAmt().setCurrencyCode(null);
                    });
                });
                itemDetail.getItemDiscountList().forEach(discount -> {
                    discount.getItemDiscountAmtTaxExcluded().setCurrencyCode(null);
                    discount.getItemDiscountAmtTaxIncluded().setCurrencyCode(null);
                });
                itemDetail.getItemTaxDetailList().forEach(tax -> {
                    tax.getItemTaxAmt().setCurrencyCode(null);
                });
            });
            transaction.getNonItemDetailList().forEach(nonItemDetail -> {
                nonItemDetail.getNonItemUnitPriceTaxExcluded().setCurrencyCode(null);
                nonItemDetail.getNonItemUnitPriceTaxIncluded().setCurrencyCode(null);
                nonItemDetail.getNonItemSalesAmtTaxExcluded().setCurrencyCode(null);
                nonItemDetail.getNonItemSalesAmtTaxIncluded().setCurrencyCode(null);
                nonItemDetail.getNonItemNewPrice().setCurrencyCode(null);
                nonItemDetail.getNonItemDiscountDetailList().forEach(nonDiscount -> {
                    nonDiscount.getNonItemDiscountAmtTaxExcluded().setCurrencyCode(null);
                    nonDiscount.getNonItemDiscountAmtTaxIncluded().setCurrencyCode(null);
                });
                nonItemDetail.getNonItemTaxDetailList().forEach(nonTax -> {
                    nonTax.getNonItemTaxAmt().setCurrencyCode(null);
                });
            });
            transaction.getSalesTransactionTenderList().forEach(tender -> {
                tender.getTaxIncludedPaymentAmount().setCurrencyCode(null);
                TenderInfo tenderInfo = tender.getTenderInfoList();
                tenderInfo.getDiscountAmount().setCurrencyCode(null);
                tenderInfo.getCouponDiscountAmountSetting().setCurrencyCode(null);
                tenderInfo.getCouponMinUsageAmountThreshold().setCurrencyCode(null);
            });
            transaction.getSalesTransactionTaxDetailList().forEach(tax -> {
                tax.getTaxAmount().setCurrencyCode(null);
            });
            transaction.getSalesTransactionTotalList().forEach(total -> {
                total.getTotalAmountTaxExcluded().setCurrencyCode(null);
                total.getTotalAmountTaxIncluded().setCurrencyCode(null);
            });
        });
    }

    private SalesTransactionErrorDetail createSalesTransactionErrorDetail() {
        SalesTransactionErrorDetail errorDetail = new SalesTransactionErrorDetail();
        return errorDetail;
    }

    private TransactionImportData createTransactionImportData() {
        TransactionImportData importData = new TransactionImportData();
        importData.setChannelCode("1");
        importData.setCustomerId("1");
        importData.setDataAlterationBackboneLinkageType(0);
        importData.setDataAlterationSalesLinkageType(0);
        importData.setDataCorrectionEditingFlag(false);
        importData.setDataCorrectionUserId("1");
        importData.setErrorCheckType(1);
        importData.setIntegratedOrderId(INTEGRATED_ORDER_ID);
        importData.setOrderBarcodeNumber("1");
        importData.setOrderConfirmationBusinessDate("2017-12-31");
        importData.setOrderConfirmationDateTime(
                OffsetDateTime.of(2017, 12, 31, 8, 0, 0, 0, ZoneOffset.of("Z")));
        importData.setSalesTransactionErrorId("");
        importData.setStoreCode(storeCode);
        importData.setSystemBrandCode("1");
        importData.setSystemBusinessCode("0");
        importData.setSystemCountryCode("1");
        List<Transaction> transactionList = new ArrayList<>();
        Transaction transaction = getTransaction();
        transactionList.add(transaction);
        importData.setTransactionList(transactionList);
        importData.setUpdateType("INSERT");

        return importData;
    }

    private Transaction getTransaction() {
        Transaction transaction = new Transaction();
        transaction.setTransactionSerialNumber(0);
        transaction.setIntegratedOrderId(INTEGRATED_ORDER_ID);
        transaction.setOrderSubNumber(12);
        transaction.setSalesTransactionId(SALES_TRANSACTION_ID);
        transaction.setTokenCode("1");
        transaction.setTransactionType(TransactionType.SALE.getTransactionType());
        transaction.setReturnType(0);
        transaction.setSystemBrandCode("1");
        transaction.setSystemBusinessCode("0");
        transaction.setSystemCountryCode("1");
        transaction.setStoreCode(TRANSACTION_STORE_CODE);
        transaction.setSalesLinkageType(1);

        transaction.setChannelCode("1");
        transaction.setDataCreationBusinessDate("2017-12-31");
        transaction.setDataCreationDateTime(
                OffsetDateTime.of(2017, 12, 31, 8, 0, 0, 0, ZoneOffset.of("Z")));
        transaction.setOrderStatusUpdateDate("2017-12-31");
        transaction.setOrderStatusLastUpdateDateTime(
                OffsetDateTime.of(2017, 12, 31, 8, 0, 0, 0, ZoneOffset.of("Z")));
        transaction.setCashRegisterNo(0);
        transaction.setReceiptNo("1");
        transaction.setOrderNumberForStorePayment("1");
        transaction.setAdvanceReceivedStoreCode("1");
        transaction.setAdvanceReceivedStoreSystemBrandCode("1");
        transaction.setAdvanceReceivedStoreSystemBusinessCode("0");
        transaction.setAdvanceReceivedStoreSystemCountryCode("1");
        transaction.setOperatorCode("1");
        transaction.setOriginalTransactionId("1");

        transaction.setOriginalReceiptNo("1");
        transaction.setOriginalCashRegisterNo(0);
        transaction.setDeposit(getPrice());
        transaction.setChange(getPrice());
        transaction.setReceiptNoForCreditCardCancellation("1");
        transaction.setReceiptNoForCreditCard("1");
        transaction.setPaymentStoreCode("1");
        transaction.setPaymentStoreSystemBrandCode("1");
        transaction.setPaymentStoreSystemBusinessCode("0");
        transaction.setPaymentStoreSystemCountryCode("1");
        transaction.setReceiptIssuedFlag(false);
        transaction.setProcessingCompanyCode("1");
        transaction.setOrderCancellationDate(
                OffsetDateTime.of(2017, 12, 31, 8, 0, 0, 0, ZoneOffset.of("Z")));
        transaction.setOrderStatus("1");
        transaction.setOrderSubstatus("1");
        transaction.setPaymentStatus("1");
        transaction.setShipmentStatus("1");
        transaction.setReceivingStatus("1");
        transaction.setTransferOutStatus("1");
        transaction.setBookingStoreCode("1");
        transaction.setBookingStoreSystemBrandCode("1");
        transaction.setBookingStoreSystemBusinessCode("0");
        transaction.setBookingStoreSystemCountryCode("1");
        transaction.setShipmentStoreCode("1");
        transaction.setShipmentStoreSystemBrandCode("1");
        transaction.setShipmentStoreSystemBusinessCode("0");
        transaction.setShipmentStoreSystemCountryCode("1");
        transaction.setReceiptStoreCode("1");
        transaction.setReceiptStoreSystemBrandCode("1");
        transaction.setReceiptStoreSystemBusinessCode("0");
        transaction.setReceiptStoreSystemCountryCode("1");
        transaction.setCustomerId("1");
        transaction.setCorporateId("1");
        transaction.setSalesTransactionDiscountFlag(false);
        transaction.setSalesTransactionDiscountAmountRate(getPrice());
        transaction.setConsistencySalesFlag(false);
        transaction.setEmployeeSaleFlag(false);
        transaction.setEReceiptTargetFlag(false);

        List<TransactionItemDetail> transactionItemDetailList = new ArrayList<>();
        TransactionItemDetail transactionItemDetail = getTransactionItemDetail();
        transactionItemDetailList.add(transactionItemDetail);
        transaction.setTransactionItemDetailList(transactionItemDetailList);

        List<NonItemDetail> nonItemDetailList = new ArrayList<>();
        NonItemDetail nonItemDetail = getNonItemDetailTwo();
        nonItemDetailList.add(nonItemDetail);
        transaction.setNonItemDetailList(nonItemDetailList);

        List<SalesTransactionTender> salesTransactionTenderList = new ArrayList<>();
        SalesTransactionTender salesTransactionTender = getSalesTransactionTender();
        salesTransactionTenderList.add(salesTransactionTender);
        transaction.setSalesTransactionTenderList(salesTransactionTenderList);

        List<SalesTransactionTaxDetail> salesTransactionTaxDetailList = new ArrayList<>();
        SalesTransactionTaxDetail salesTransactionTaxDetail = getSalesTransactionTaxDetail();
        salesTransactionTaxDetailList.add(salesTransactionTaxDetail);
        transaction.setSalesTransactionTaxDetailList(salesTransactionTaxDetailList);

        List<SalesTransactionTotal> salesTransactionTotalList = new ArrayList<>();
        SalesTransactionTotal salesTransactionTotal = getSalesTransactionTotal();
        salesTransactionTotalList.add(salesTransactionTotal);
        transaction.setSalesTransactionTotalList(salesTransactionTotalList);

        return transaction;
    }

    private TransactionItemDetail getTransactionItemDetail() {
        TransactionItemDetail entity = new TransactionItemDetail();

        entity.setSystemBrandCode("1");
        entity.setDetailSubNumber(3);
        entity.setDetailListSalesTransactionType("SALE");
        entity.setL2ItemCode("1");
        entity.setViewL2ItemCode("1");
        entity.setL2ProductName("1");
        entity.setL3ItemCode("1");
        entity.setL3PosProductName("1");

        entity.setEpcCode("1");
        entity.setGDepartmentCode("1");
        entity.setDeptCode(0);
        entity.setQuantityCode("P");

        entity.setItemQty(1);
        entity.setItemCost(getPrice());
        entity.setInitialSellingPrice(getPrice());
        entity.setBItemSellingPrice(getPrice());
        entity.setItemNewPrice(getPrice());
        entity.setItemUnitPriceTaxExcluded(getPrice());
        entity.setItemUnitPriceTaxIncluded(getPrice());
        entity.setItemSalesAmtTaxExcluded(getPrice());
        entity.setItemSalesAmtTaxIncluded(getPrice());
        entity.setOrderStatusUpdateDate("2017-12-31");
        entity.setOrderStatusLastUpdateDateTime(
                OffsetDateTime.of(2017, 12, 31, 8, 0, 0, 0, ZoneOffset.of("Z")));
        entity.setOrderStatus("1");
        entity.setOrderSubstatus("1");
        entity.setBookingStoreCode("1");
        entity.setBookingStoreSystemBrandCode("1");
        entity.setBookingStoreSystemBusinessCode("0");
        entity.setBookingStoreSystemCountryCode("1");
        entity.setShipmentStoreCode("1");
        entity.setShipmentStoreSystemBrandCode("1");
        entity.setShipmentStoreSystemBusinessCode("0");
        entity.setShipmentStoreSystemCountryCode("1");
        entity.setReceiptStoreCode("1");
        entity.setReceiptStoreSystemBrandCode("1");
        entity.setReceiptStoreSystemBusinessCode("0");
        entity.setReceiptStoreSystemCountryCode("1");
        entity.setContributionSalesRepresentative("1");
        entity.setCustomerId("1");
        entity.setBundlePurchaseQty(0);
        entity.setBundlePurchasePrice(getPrice());
        entity.setBundlePurchaseIndex(0);
        entity.setLimitedAmountPromotionCount(0);
        entity.setCalculationUnavailableType("0");

        entity.setItemDiscountAmount(getPrice());
        entity.setBundleSalesFlag(false);
        entity.setBundleSalesPrice(getPrice());
        entity.setBundleSalesDetailIndex(0);
        entity.setItemDetailNumber(0);
        entity.setItemTaxationType("1");
        entity.setItemTaxKind("1");

        List<NonItemDetail> nonItemDetailList = new ArrayList<>();
        NonItemDetail nonItemDetail = getNonItemDetailOne();
        nonItemDetailList.add(nonItemDetail);
        entity.setNonItemDetailListByItem(nonItemDetailList);

        List<ItemDiscount> itemDiscountList = new ArrayList<>();
        ItemDiscount itemDiscount = getItemDiscount();
        itemDiscountList.add(itemDiscount);
        entity.setItemDiscountList(itemDiscountList);

        List<ItemTaxDetail> itemTaxDetailList = new ArrayList<>();
        ItemTaxDetail itemTaxDetail = getItemTaxDetail();
        itemTaxDetailList.add(itemTaxDetail);
        entity.setItemTaxDetailList(itemTaxDetailList);

        return entity;
    }

    private NonItemDetail getNonItemDetailOne() {
        NonItemDetail entity = new NonItemDetail();

        entity.setDetailSubNumber(4);
        entity.setNonMdType("1");
        entity.setNonItemCode("1");
        entity.setServiceCode("1");
        entity.setPosNonItemName("1");

        entity.setQuantityCode("P");
        entity.setNonItemQty(1);
        entity.setNonItemUnitPriceTaxExcluded(getPrice());
        entity.setNonItemUnitPriceTaxIncluded(getPrice());
        entity.setNonItemSalesAmtTaxExcluded(getPrice());
        entity.setNonItemSalesAmtTaxIncluded(getPrice());
        entity.setNonItemNewPrice(getPrice());
        entity.setNonCalculationNonItemType("0");
        entity.setOrderStatusUpdateDate("2017-12-31");
        entity.setOrderStatusLastUpdateDateTime(
                OffsetDateTime.of(2017, 12, 31, 8, 0, 0, 0, ZoneOffset.of("Z")));
        entity.setOrderStatus("1");
        entity.setOrderSubstatus("1");
        entity.setBookingStoreCode("1");
        entity.setBookingStoreSystemBrandCode("1");
        entity.setBookingStoreSystemBusinessCode("0");
        entity.setBookingStoreSystemCountryCode("1");
        entity.setShipmentStoreCode("1");
        entity.setShipmentStoreSystemBrandCode("1");
        entity.setShipmentStoreSystemBusinessCode("0");
        entity.setShipmentStoreSystemCountryCode("1");
        entity.setReceiptStoreCode("1");
        entity.setReceiptStoreSystemBrandCode("1");
        entity.setReceiptStoreSystemBusinessCode("0");
        entity.setReceiptStoreSystemCountryCode("1");
        entity.setContributionSalesRepresentative("1");
        entity.setNonItemTaxationType("1");
        entity.setNonItemTaxKind("1");
        entity.setNonMdDetailListSalesTransactionType("SALE");
        NonItemInfo nonItemInfo = getNonItemInfoOne();
        entity.setNonItemInfo(nonItemInfo);

        List<NonItemDiscountDetail> nonItemDiscountDetailList = new ArrayList<>();
        NonItemDiscountDetail nonItemDiscountDetail = getNonItemDiscountDetailOne();
        nonItemDiscountDetailList.add(nonItemDiscountDetail);
        entity.setNonItemDiscountDetailList(nonItemDiscountDetailList);

        List<NonItemTaxDetail> nonItemTaxDetailList = new ArrayList<>();
        NonItemTaxDetail nonItemTaxDetail = getNonItemTaxDetailOne();
        nonItemTaxDetailList.add(nonItemTaxDetail);
        entity.setNonItemTaxDetailList(nonItemTaxDetailList);

        return entity;
    }

    private NonItemInfo getNonItemInfoOne() {
        NonItemInfo entity = new NonItemInfo();

        entity.setDetailSubNumber(5);
        entity.setItemDetailSubNumber(5);
        entity.setKeyCode("1");
        entity.setCodeValue1("1");
        entity.setCodeValue2("1");
        entity.setCodeValue3("1");
        entity.setCodeValue4("1");
        entity.setName1("1");
        entity.setName2("1");
        entity.setName3("1");
        entity.setName4("1");
        return entity;
    }

    private NonItemDiscountDetail getNonItemDiscountDetailOne() {
        NonItemDiscountDetail entity = new NonItemDiscountDetail();

        entity.setNonItemDiscountSubNumber(0);
        entity.setNonItemDiscountDetailSubNumber(0);
        entity.setNonItemPromotionType("11");
        entity.setNonItemStoreDiscountType("1");
        entity.setNonItemQuantityCode("P");
        entity.setNonItemDiscountQty(0);
        entity.setNonItemDiscountAmtTaxExcluded(getPrice());
        entity.setNonItemDiscountAmtTaxIncluded(getPrice());
        return entity;
    }

    private NonItemTaxDetail getNonItemTaxDetailOne() {
        NonItemTaxDetail entity = new NonItemTaxDetail();

        entity.setNonItemTaxDetailSubNumber(1);
        entity.setNonItemTaxSubNumber(2);
        entity.setNonItemTaxType("3");
        entity.setNonItemTaxName("1");
        entity.setNonItemTaxAmountSign("P");
        entity.setNonItemTaxAmt(getPrice());
        entity.setNonItemTaxRate(new BigDecimal("0"));
        return entity;
    }

    private ItemDiscount getItemDiscount() {
        ItemDiscount entity = new ItemDiscount();

        entity.setItemDiscountDetailSubNumber(81);
        entity.setItemDiscountSubNumber(82);
        entity.setItemPromotionType("21");
        entity.setItemPromotionNumber("0");
        entity.setItemStoreDiscountType("1");
        entity.setItemQuantityCode("P");
        entity.setItemDiscountQty(1);
        entity.setItemDiscountAmtTaxExcluded(getPrice());
        entity.setItemDiscountAmtTaxIncluded(getPrice());
        return entity;

    }

    private ItemTaxDetail getItemTaxDetail() {
        ItemTaxDetail entity = new ItemTaxDetail();

        entity.setItemTaxDetailSubNumber(110);
        entity.setItemTaxSubNumber(10);
        entity.setItemTaxType("4");
        entity.setItemTaxName("1");
        entity.setItemTaxAmountSign("P");
        entity.setItemTaxAmt(getPrice());
        entity.setItemTaxRate(new BigDecimal("0"));
        return entity;

    }

    private NonItemDetail getNonItemDetailTwo() {
        NonItemDetail entity = new NonItemDetail();

        entity.setNonItemDetailNumber(3);
        entity.setNonMdDetailListSalesTransactionType("SALE");
        entity.setNonItemDetailSalesLinkageType(1);
        entity.setNonMdType("1");
        entity.setNonItemCode("1");
        entity.setServiceCode("1");
        entity.setPosNonItemName("1");

        entity.setQuantityCode("P");
        entity.setNonItemQty(1);
        entity.setNonItemUnitPriceTaxExcluded(getPrice());
        entity.setNonItemUnitPriceTaxIncluded(getPrice());
        entity.setNonItemSalesAmtTaxExcluded(getPrice());
        entity.setNonItemSalesAmtTaxIncluded(getPrice());
        entity.setNonItemNewPrice(getPrice());
        entity.setNonCalculationNonItemType("1");
        entity.setOrderStatusUpdateDate("2017-12-31");
        entity.setOrderStatusLastUpdateDateTime(
                OffsetDateTime.of(2017, 12, 31, 8, 0, 0, 0, ZoneOffset.of("Z")));
        entity.setOrderStatus("1");
        entity.setOrderSubstatus("1");
        entity.setBookingStoreCode("1");
        entity.setBookingStoreSystemBrandCode("1");
        entity.setBookingStoreSystemBusinessCode("1");
        entity.setBookingStoreSystemCountryCode("1");
        entity.setShipmentStoreCode("1");
        entity.setShipmentStoreSystemBrandCode("1");
        entity.setShipmentStoreSystemBusinessCode("1");
        entity.setShipmentStoreSystemCountryCode("1");
        entity.setReceiptStoreCode("1");
        entity.setReceiptStoreSystemBrandCode("1");
        entity.setReceiptStoreSystemBusinessCode("1");
        entity.setReceiptStoreSystemCountryCode("1");
        entity.setContributionSalesRepresentative("1");
        entity.setItemDetailSubNumber(1);
        entity.setNonItemTaxationType("1");
        entity.setNonItemTaxKind("1");

        entity.setNonItemInfo(getNonItemInfoTwo());

        List<NonItemDiscountDetail> nonItemDiscountDetailList = new ArrayList<>();
        nonItemDiscountDetailList.add(getNonItemDiscountDetailTwo());
        entity.setNonItemDiscountDetailList(nonItemDiscountDetailList);

        List<NonItemTaxDetail> nonItemTaxDetailList = new ArrayList<>();
        nonItemTaxDetailList.add(getNonItemTaxDetailTwo());
        entity.setNonItemTaxDetailList(nonItemTaxDetailList);
        return entity;

    }

    private NonItemInfo getNonItemInfoTwo() {
        NonItemInfo entity = new NonItemInfo();

        entity.setDetailSubNumber(5);
        entity.setItemDetailSubNumber(1);
        entity.setKeyCode("1");
        entity.setCodeValue1("1");
        entity.setCodeValue2("1");
        entity.setCodeValue3("1");
        entity.setCodeValue4("1");
        entity.setName1("1");
        entity.setName2("1");
        return entity;
    }

    private NonItemDiscountDetail getNonItemDiscountDetailTwo() {
        NonItemDiscountDetail entity = new NonItemDiscountDetail();

        entity.setNonItemDiscountSubNumber(1);

        entity.setNonItemDiscountDetailSubNumber(1);
        entity.setNonItemPromotionType("01");

        entity.setNonItemStoreDiscountType("1");
        entity.setNonItemQuantityCode("P");
        entity.setNonItemDiscountQty(1);
        entity.setNonItemDiscountAmtTaxExcluded(getPrice());
        entity.setNonItemDiscountAmtTaxIncluded(getPrice());
        return entity;
    }

    private NonItemTaxDetail getNonItemTaxDetailTwo() {

        NonItemTaxDetail entity = new NonItemTaxDetail();
        entity.setNonItemTaxDetailSubNumber(33);

        entity.setNonItemTaxType("2");
        entity.setNonItemTaxName("1");
        entity.setNonItemTaxAmountSign("P");
        entity.setNonItemTaxAmt(getPrice());
        entity.setNonItemTaxRate(new BigDecimal("0"));
        return entity;
    }

    private SalesTransactionTender getSalesTransactionTender() {
        SalesTransactionTender entity = new SalesTransactionTender();

        entity.setTenderSubNumber(3);
        entity.setTenderGroupId("1");
        entity.setTenderId("1");
        entity.setPaymentSign("P");
        entity.setTaxIncludedPaymentAmount(getPrice());
        TenderInfo tenderInfo = getTenderInfo();
        entity.setTenderInfoList(tenderInfo);
        return entity;

    }

    private TenderInfo getTenderInfo() {
        TenderInfo entity = new TenderInfo();

        entity.setDiscountAmount(getPrice());
        entity.setDiscountRate(new BigDecimal("0"));
        entity.setDiscountCodeIdCorporateId("1");

        entity.setCouponType("1");
        entity.setCouponDiscountAmountSetting(getPrice());
        entity.setCouponMinUsageAmountThreshold(getPrice());
        entity.setCouponUserId("1");
        entity.setCardNo("1");
        entity.setCreditApprovalCode("1");
        entity.setCreditProcessingSerialNumber("1");
        entity.setCreditPaymentType("1");
        entity.setCreditPaymentCount(1);
        entity.setCreditAffiliatedStoreNumber("1");
        return entity;

    }

    private SalesTransactionTaxDetail getSalesTransactionTaxDetail() {
        SalesTransactionTaxDetail entity = new SalesTransactionTaxDetail();

        entity.setTaxSubNumber(11);

        entity.setTaxGroup("22");
        entity.setTaxAmountSign("P");
        entity.setTaxAmount(getPrice());
        entity.setTaxRate(new BigDecimal("0"));

        return entity;

    }

    private SalesTransactionTotal getSalesTransactionTotal() {
        SalesTransactionTotal entity = new SalesTransactionTotal();

        entity.setTotalAmountSubNumber(1);
        entity.setTotalType("1");
        entity.setTotalAmountTaxExcluded(getPrice());
        entity.setTotalAmountTaxIncluded(getPrice());
        entity.setConsumptionTaxRate(new BigDecimal("0"));
        entity.setSalesTransactionInformation1("1");
        entity.setSalesTransactionInformation2("1");
        entity.setSalesTransactionInformation3("1");
        return entity;
    }

    private Price getPrice() {
        Price price = new Price();
        price.setCurrencyCode(Currency.getInstance(Locale.JAPAN));
        price.setValue(BigDecimal.ONE);
        return price;
    }

    private void setExpectedForPvoid(TransactionImportData importData) {
        importData.setIntegratedOrderId("integer001");
        importData.setCustomerId("customerid001");
        importData.setUpdateType("INSERT");
        importData.setErrorCheckType(0);
        importData.setDataAlterationBackboneLinkageType(0);
        importData.setDataAlterationSalesLinkageType(0);
        importData.setChannelCode("original1");
        importData.setStoreCode(storeCode);
        importData.setSystemBrandCode("originalBrc");
        importData.setSystemBusinessCode("originalBuc");
        importData.setSystemCountryCode("originalScc");
        importData.setOrderConfirmationBusinessDate("2017-12-31");
        importData.setOrderConfirmationDateTime(
                OffsetDateTime.of(2017, 12, 31, 8, 0, 0, 0, ZoneOffset.of("Z")));
        importData.setDataCorrectionEditingFlag(false);
        importData.getTransactionList().forEach(tra -> {
            tra.setOrderStatus("SHIPMENT_CONFIRMED");
            tra.setSalesTransactionId("uc10011111712310800-0000001-01");
            tra.setIntegratedOrderId("headerIntegratedId002");
            tra.setOrderSubNumber(2);
            tra.setSalesLinkageType(0);
            tra.setSystemBrandCode("brc");
            tra.setSystemBusinessCode("buc");
            tra.setSystemCountryCode("scc");
            tra.setOriginalTransactionId("headerOriginalId0001");
            tra.setSalesLinkageType(0);
            tra.setDeposit(getPrice());
            tra.setChange(getPrice());
            tra.setReceiptIssuedFlag(false);
            tra.setOrderStatus("SHIPMENT_CONFIRMED");
            tra.setSalesTransactionDiscountFlag(false);
            tra.setSalesTransactionDiscountAmountRate(getPrice());
            tra.setConsistencySalesFlag(false);
            tra.setEmployeeSaleFlag(false);
            tra.setEReceiptTargetFlag(false);
        });
    }

    private void setExpectedForSaleAndReturn(TransactionImportData importData) {
        importData.setIntegratedOrderId("transaction10000");
        importData.setCustomerId("1");
        importData.setUpdateType("INSERT");
        importData.setErrorCheckType(0);
        importData.setDataAlterationBackboneLinkageType(0);
        importData.setDataAlterationSalesLinkageType(0);
        importData.setChannelCode("1");
        importData.setStoreCode(storeCode);
        importData.setOrderConfirmationBusinessDate("2017-12-31");
        importData.setOrderConfirmationDateTime(
                OffsetDateTime.of(2017, 12, 31, 8, 0, 0, 0, ZoneOffset.of("Z")));
        importData.setDataCorrectionEditingFlag(false);
        importData.setSystemBrandCode("brc");
        importData.setSystemBusinessCode("buc");
        importData.setSystemCountryCode("scc");
        importData.getTransactionList().forEach(tra -> {
            tra.setSalesTransactionId("uc10011111712310800-0000001-01");
            tra.setIntegratedOrderId("uc10011111712310800-0000001");
            tra.setSystemBrandCode("brc");
            tra.setSystemBusinessCode("buc");
            tra.setSystemCountryCode("scc");
            tra.setOrderSubNumber(1);
            tra.setOriginalTransactionId(null);
            tra.setSalesLinkageType(0);
            tra.setDeposit(getPrice());
            tra.setChange(getPrice());
            tra.setReceiptIssuedFlag(false);
            tra.setSalesTransactionDiscountFlag(false);
            tra.setSalesTransactionDiscountAmountRate(getPrice());
            tra.setConsistencySalesFlag(false);
            tra.setEmployeeSaleFlag(false);
            tra.setEReceiptTargetFlag(false);
            tra.getTransactionItemDetailList().forEach(item -> {
                item.setDeptCode(1);
                item.setItemCost(getPrice());
                item.getItemCost().setValue(BigDecimal.ZERO);
                item.setInitialSellingPrice(getPrice());
                item.setBItemSellingPrice(getPrice());
                item.setItemNewPrice(getPrice());
                item.setItemUnitPriceTaxExcluded(getPrice());
                item.setItemUnitPriceTaxIncluded(getPrice());
                item.setItemSalesAmtTaxExcluded(getPrice());
                item.setItemSalesAmtTaxIncluded(getPrice());
                item.setBundlePurchasePrice(getPrice());
                item.setItemDiscountAmount(getPrice());
                item.setBundleSalesFlag(false);
                item.setBundleSalesPrice(getPrice());
                item.getNonItemDetailListByItem().forEach(non -> {
                    non.setNonItemUnitPriceTaxExcluded(getPrice());
                    non.setNonItemUnitPriceTaxIncluded(getPrice());
                    non.setNonItemSalesAmtTaxExcluded(getPrice());
                    non.setNonItemSalesAmtTaxIncluded(getPrice());
                    non.setNonItemNewPrice(getPrice());
                    non.getNonItemDiscountDetailList().forEach(nonDiscount -> {
                        nonDiscount.setNonItemDiscountAmtTaxExcluded(getPrice());
                        nonDiscount.setNonItemDiscountAmtTaxIncluded(getPrice());
                    });
                    non.getNonItemTaxDetailList().forEach(nonTax -> {
                        nonTax.setNonItemTaxAmt(getPrice());
                    });
                });
                item.getItemDiscountList().forEach(discount -> {
                    discount.setItemDiscountAmtTaxExcluded(getPrice());
                    discount.setItemDiscountAmtTaxIncluded(getPrice());
                });
                item.getItemTaxDetailList().forEach(tax -> {
                    tax.setItemTaxAmt(getPrice());
                });
            });
            tra.getNonItemDetailList().forEach(non -> {
                non.setNonItemUnitPriceTaxExcluded(getPrice());
                non.setNonItemUnitPriceTaxIncluded(getPrice());
                non.setNonItemSalesAmtTaxExcluded(getPrice());
                non.setNonItemSalesAmtTaxIncluded(getPrice());
                non.setNonItemNewPrice(getPrice());
                non.getNonItemDiscountDetailList().forEach(nonDiscount -> {
                    nonDiscount.setNonItemDiscountAmtTaxExcluded(getPrice());
                    nonDiscount.setNonItemDiscountAmtTaxIncluded(getPrice());
                });
                non.getNonItemTaxDetailList().forEach(nonTax -> {
                    nonTax.setNonItemTaxAmt(getPrice());
                });
            });
            tra.getSalesTransactionTenderList().forEach(tender -> {
                tender.setTaxIncludedPaymentAmount(getPrice());
                tender.getTenderInfoList().setDiscountAmount(getPrice());
                tender.getTenderInfoList().setCouponDiscountAmountSetting(getPrice());
                tender.getTenderInfoList().setCouponMinUsageAmountThreshold(getPrice());
            });
            tra.getSalesTransactionTaxDetailList().forEach(tax -> {
                tax.setTaxAmount(getPrice());
            });
            tra.getSalesTransactionTotalList().forEach(total -> {
                total.setTotalAmountTaxExcluded(getPrice());
                total.setTotalAmountTaxIncluded(getPrice());
            });
        });
    }
}
