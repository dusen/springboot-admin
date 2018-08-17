package com.fastretailing.dcp.sales.importtransaction.component;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Function;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import com.fastretailing.dcp.sales.common.constants.DBItem;
import com.fastretailing.dcp.sales.common.type.ErrorType;
import com.fastretailing.dcp.sales.common.type.RtlogType;
import com.fastretailing.dcp.sales.common.type.TaxSystemType;
import com.fastretailing.dcp.sales.common.type.TransactionCheckResultType;
import com.fastretailing.dcp.sales.common.type.TransactionType;
import com.fastretailing.dcp.sales.common.type.UpdateType;
import com.fastretailing.dcp.sales.importtransaction.converter.TransactionDataConverter;
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
import com.fastretailing.dcp.sales.importtransaction.entity.BusinessCountryStateSettingMaster;
import com.fastretailing.dcp.sales.importtransaction.entity.BusinessCountryStateSettingMasterCondition;
import com.fastretailing.dcp.sales.importtransaction.entity.BusinessCountryStateSettingMasterCondition.Criteria;
import com.fastretailing.dcp.sales.importtransaction.entity.BusinessCountryStateSettingMasterCondition.Criterion;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionErrorDetail;
import com.fastretailing.dcp.sales.importtransaction.entity.StoreControlMaster;
import com.fastretailing.dcp.sales.importtransaction.entity.StoreGeneralPurposeMaster;
import com.fastretailing.dcp.sales.importtransaction.entity.StoreMaster;
import com.fastretailing.dcp.sales.importtransaction.entity.TranslationTenderMaster;
import com.fastretailing.dcp.sales.importtransaction.repository.BusinessCountryStateSettingMasterMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.PayOffDataMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.SalesTransactionErrorDetailMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.StoreControlMasterMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.StoreGeneralPurposeMasterMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.StoreMasterMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.TranslationTenderMasterMapper;
import com.fastretailing.dcp.storecommon.dto.Price;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CheckTransactionDataServiceImplTest {

    private static final String ITEM_ID_CODE_VALUE = "code_value";

    private static final String ITEM_ID_BALANCE_CHECK_TOTAL_AMOUNT = "total_amount";

    private static final String ITEM_ID_BALANCE_CHECK_TOTAL_DISCOUNT_AMOUNT =
            "total_discount_amount";

    private static final String ITEM_ID_BALANCE_CHECK_TOTAL_PAYMENT_AMOUNT = "total_payment_amount";

    private static final String ITEM_ID_CONSISTENCY_CHECK_BUSINESS_DATE = "business_date";

    private static final String ITEM_ID_CONSISTENCY_CHECK_TENDER_CODE = "tender_id";

    private static final String ITEM_ID_CONSISTENCY_CHECK_BUSINESS_END_OF_DATE =
            "business_end_of_date";

    private static final String ITEM_ID_CONSISTENCY_CHECK_AFTER_STORE_CLOSE_DATE = "close_date";

    private static final String ITEM_ID_CONSISTENCY_CHECK_BEFORE_STORE_OPEN_DATE = "open_date";

    private static final String CODE_L2_OPEN_DAYS = "BEFORESTOREOPENING";

    private static final String CODE_L2_CLOSE_DAYS = "AFTERSTORECLOSING";

    private static final String CODE_L2_BASE_DATE_TIME_INFORMATION = "DATEEXCEEDING";

    private static final String CODE_L2_BUSINESS_END_OF_DAYS = "CLOSEDBUSINESSDATE";

    private static final String CODE_L2_TAX_TYPE = "TAXTYPE";

    private static final String ITEM_ID_DATA_CREATION_BUSINESS_DATE =
            "data_creation_business_date ";

    private static final String ITEM_ID_ORDER_STATUS_UPDATE_DATE = "order_status_update_date";

    private static final String ITEM_ID_ORDER_CONFIRMATION_BUSINESS_DATE =
            "order_confirmation_business_date";

    private static final String TRANSACTION_STORE_CODE = "2";

    private static final String SALES_TRANSACTION_ID = "2";

    private static final String INTEGRATED_ORDER_ID = "1";

    private static final String IMPORT_DATA_STORE_CODE = "1";

    @MockBean
    private SalesTransactionErrorDetailMapper salesTransactionErrorDetailEntityMapper;

    @MockBean
    private StoreControlMasterMapper storeControlEntityMapper;

    @MockBean
    private BusinessCountryStateSettingMasterMapper businessCountryStateSettingEntityMapper;

    @MockBean
    private StoreGeneralPurposeMasterMapper storeGeneralPurposeEntityMapper;

    @MockBean
    private StoreMasterMapper storeEntityMapper;


    @MockBean
    private PayOffDataMapper payOffDateEntityMapper;

    @MockBean
    private TranslationTenderMasterMapper translationTenderMasterMapper;

    @Autowired
    private CheckTransactionDataService dataCheckerService;

    @Autowired
    private BusinessChecker businessChecker;

    @Autowired
    private ValidationChecker validationChecker;

    @MockBean
    private InitialHelper initialHelper;

    /** Transaction data converter. */
    @Autowired
    private TransactionDataConverter transactionDataConverter;

    private TransactionImportData transactionImportData = new TransactionImportData();
    private String userId;
    private String salesTransactionErrorId;

    /**
     * setUp.
     * 
     * @throws Exception .
     */
    @Before
    public void setUp() throws Exception {

        setTransactionImportData(transactionImportData);
        MockitoAnnotations.initMocks(this);

        userId = "testUser";
        salesTransactionErrorId = "";
        TranslationTenderMaster tenderMaster = new TranslationTenderMaster();
        tenderMaster.setTenderGroup("tendergroup");
        tenderMaster.setTenderId("1");
        when(initialHelper.initialPosImportData(any(), any(), any())).thenReturn(true);
        when(initialHelper.findTranslationTenderMasterFirst(any(), any()))
                .thenReturn(Optional.of(tenderMaster));
        when(translationTenderMasterMapper.countByCondition(any())).thenReturn(1L);
    }

    @Test
    public void testValidationLevel1Required() {
        transactionImportData.setChannelCode(null);
        salesTransactionErrorId = "testValidationLevel1Required";

        when(salesTransactionErrorDetailEntityMapper.insertSelective(anyObject())).thenReturn(1);

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(1L);
        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.VALIDATION_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorItemValue1(), equalTo(null));

        assertThat(argument.getValue().getErrorItemId1(), equalTo("channel_code"));
    }

    @Test
    public void testValidationLevel1RequiredEmpty() {
        transactionImportData.setChannelCode("");
        salesTransactionErrorId = "testValidationLevel1Required";

        when(salesTransactionErrorDetailEntityMapper.insertSelective(anyObject())).thenReturn(1);

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.VALIDATION_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorItemValue1(), equalTo(""));

        assertThat(argument.getValue().getErrorItemId1(), equalTo("channel_code"));
    }

    @Test
    public void testItemDetailIncludedTaxNullAddition() {
        transactionImportData.getTransactionList().forEach(
                transaction -> transaction.getTransactionItemDetailList().forEach(itemDetail -> {
                    itemDetail.setItemTaxKind(TaxSystemType.IN.toString());
                    itemDetail.setItemUnitPriceTaxIncluded(null);
                }));
        salesTransactionErrorId = "testValidationLevel1Required";

        when(salesTransactionErrorDetailEntityMapper.insertSelective(anyObject())).thenReturn(1);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.VALIDATION_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorItemValue1(), equalTo(null));

        assertThat(argument.getValue().getErrorItemId1(), equalTo("item_unit_price_tax_included"));
    }

    @Test
    public void testTransactionTypeOtherAddition() {
        transactionImportData.getTransactionList()
                .forEach(transaction -> transaction.setTransactionType("other"));
        salesTransactionErrorId = "testValidationLevel1Required";

        when(salesTransactionErrorDetailEntityMapper.insertSelective(anyObject())).thenReturn(1);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.VALIDATION_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorItemValue1(), equalTo("other"));

        assertThat(argument.getValue().getErrorItemId1(), equalTo("transaction_type"));
    }

    @Test
    public void testItemDetailTransactionTypeOtherAddition() {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.setTransactionType(TransactionType.PVOID.getTransactionType());
            transaction.getTransactionItemDetailList().forEach(itemDetail -> {
                itemDetail.setDetailListSalesTransactionType("other");
            });
        });
        salesTransactionErrorId = "testValidationLevel1Required";

        when(salesTransactionErrorDetailEntityMapper.insertSelective(anyObject())).thenReturn(1);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.VALIDATION_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorItemValue1(), equalTo("other"));

        assertThat(argument.getValue().getErrorItemId1(),
                equalTo("detail_list_sales_transaction_type"));
    }

    @Test
    public void testItemDetailQuantityCodeOtherAddition() {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.setTransactionType(TransactionType.PVOID.getTransactionType());
            transaction.getTransactionItemDetailList().forEach(itemDetail -> {
                itemDetail.setQuantityCode("c");
            });
        });
        salesTransactionErrorId = "testValidationLevel1Required";

        when(salesTransactionErrorDetailEntityMapper.insertSelective(anyObject())).thenReturn(1);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.VALIDATION_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorItemValue1(), equalTo("c"));

        assertThat(argument.getValue().getErrorItemId1(), equalTo("quantity_code"));
    }

    @Test
    public void testNonItemDetailTransactionTypeOtherAddition() {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.setTransactionType(TransactionType.SALE.getTransactionType());
            transaction.getTransactionItemDetailList().forEach(itemDetail -> {
                itemDetail.setDetailListSalesTransactionType(
                        TransactionType.RETURN.getTransactionType());
            });
            transaction.getNonItemDetailList().forEach(non -> {
                non.setNonMdDetailListSalesTransactionType("other");
            });
        });
        salesTransactionErrorId = "testValidationLevel1Required";

        when(salesTransactionErrorDetailEntityMapper.insertSelective(anyObject())).thenReturn(1);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.VALIDATION_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorItemValue1(), equalTo("other"));

        assertThat(argument.getValue().getErrorItemId1(),
                equalTo("non_md_detail_list_sales_transaction_type"));
    }

    @Test
    public void testNonItemDetailQuantityCodeOtherAddition() {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getTransactionItemDetailList().forEach(itemDetail -> {
                itemDetail.setQuantityCode("N");
            });
            transaction.getNonItemDetailList().forEach(non -> {
                non.setQuantityCode("a");
            });
        });
        salesTransactionErrorId = "testValidationLevel1Required";

        when(salesTransactionErrorDetailEntityMapper.insertSelective(anyObject())).thenReturn(1);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.VALIDATION_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorItemValue1(), equalTo("a"));

        assertThat(argument.getValue().getErrorItemId1(), equalTo("quantity_code"));
    }

    @Test
    public void testNonItemDetailListByItemQuantityCodeOtherAddition() {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getTransactionItemDetailList().forEach(itemDetail -> {
                itemDetail.setQuantityCode("");
                itemDetail.getNonItemDetailListByItem().forEach(non -> {
                    non.setQuantityCode("a");
                });
            });
        });
        salesTransactionErrorId = "testValidationLevel1Required";

        when(salesTransactionErrorDetailEntityMapper.insertSelective(anyObject())).thenReturn(1);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.VALIDATION_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorItemValue1(), equalTo("a"));

        assertThat(argument.getValue().getErrorItemId1(), equalTo("quantity_code"));
    }

    @Test
    public void testItemDetailDiscountQuantityCodeOtherAddition() {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getTransactionItemDetailList().forEach(itemDetail -> {
                itemDetail.getItemDiscountList().forEach(discount -> {
                    discount.setItemQuantityCode("a");
                });
                itemDetail.getItemTaxDetailList().forEach(tax -> {
                    tax.setItemTaxAmountSign("P");
                });
            });
        });
        salesTransactionErrorId = "testValidationLevel1Required";

        when(salesTransactionErrorDetailEntityMapper.insertSelective(anyObject())).thenReturn(1);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.VALIDATION_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorItemValue1(), equalTo("a"));

        assertThat(argument.getValue().getErrorItemId1(), equalTo("item_quantity_code"));
    }

    @Test
    public void testItemDetailTaxAmountSignOtherAddition() {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getTransactionItemDetailList().forEach(itemDetail -> {
                itemDetail.getItemDiscountList().forEach(discount -> {
                    discount.setItemQuantityCode("N");
                });
                itemDetail.getItemTaxDetailList().forEach(tax -> {
                    tax.setItemTaxAmountSign("a");
                });
            });
        });
        salesTransactionErrorId = "testValidationLevel1Required";

        when(salesTransactionErrorDetailEntityMapper.insertSelective(anyObject())).thenReturn(1);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.VALIDATION_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorItemValue1(), equalTo("a"));

        assertThat(argument.getValue().getErrorItemId1(), equalTo("item_tax_amount_sign"));
    }

    @Test
    public void testNonItemDetailListByItemTaxAmountSignOtherAddition() {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getTransactionItemDetailList().forEach(itemDetail -> {
                itemDetail.getNonItemDetailListByItem().forEach(non -> {
                    non.getNonItemDiscountDetailList().forEach(discount -> {
                        discount.setNonItemQuantityCode("N");
                    });
                    non.getNonItemTaxDetailList().forEach(tax -> {
                        tax.setNonItemTaxAmountSign("a");
                    });
                });
            });
        });
        salesTransactionErrorId = "testValidationLevel1Required";

        when(salesTransactionErrorDetailEntityMapper.insertSelective(anyObject())).thenReturn(1);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.VALIDATION_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorItemValue1(), equalTo("a"));

        assertThat(argument.getValue().getErrorItemId1(), equalTo("non_item_tax_amount_sign"));
    }

    @Test
    public void testNonItemDetailListByItemDiscountQuantityCodeOtherAddition() {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getTransactionItemDetailList().forEach(itemDetail -> {
                itemDetail.getNonItemDetailListByItem().forEach(non -> {
                    non.getNonItemDiscountDetailList().forEach(discount -> {
                        discount.setNonItemQuantityCode("a");
                    });
                    non.getNonItemTaxDetailList().forEach(tax -> {
                        tax.setNonItemTaxAmountSign("N");
                    });
                });
            });
        });
        salesTransactionErrorId = "testValidationLevel1Required";

        when(salesTransactionErrorDetailEntityMapper.insertSelective(anyObject())).thenReturn(1);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.VALIDATION_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorItemValue1(), equalTo("a"));

        assertThat(argument.getValue().getErrorItemId1(), equalTo("non_item_quantity_code"));
    }

    @Test
    public void testEmptyTransactionPassAddition() {
        transactionImportData.setTransactionList(new ArrayList<>());
        salesTransactionErrorId = "testValidationLevel1Required";

        when(salesTransactionErrorDetailEntityMapper.insertSelective(anyObject())).thenReturn(1);

        boolean result = validationChecker.checkAdditionalValidation(transactionImportData,
                new SalesTransactionErrorDetail());

        assertThat(result).isEqualTo(true);

    }

    @Test
    public void testEmptyInTransactionPassAddition() {
        transactionImportData.getTransactionList().forEach(tran -> {
            tran.setNonItemDetailList(new ArrayList<>());
            tran.setSalesTransactionTenderList(new ArrayList<>());
        });
        salesTransactionErrorId = "testValidationLevel1Required";

        when(salesTransactionErrorDetailEntityMapper.insertSelective(anyObject())).thenReturn(1);

        boolean result = validationChecker.checkAdditionalValidation(transactionImportData,
                new SalesTransactionErrorDetail());

        assertThat(result).isEqualTo(true);

    }

    @Test
    public void testTenderInfoNullPassAddition() {
        transactionImportData.getTransactionList().forEach(tran -> {
            tran.setNonItemDetailList(new ArrayList<>());
            tran.getSalesTransactionTenderList().forEach(tender -> {
                tender.setTenderGroupId("CREDIT");
                tender.setTenderInfoList(null);
            });
        });
        salesTransactionErrorId = "testValidationLevel1Required";

        when(salesTransactionErrorDetailEntityMapper.insertSelective(anyObject())).thenReturn(1);

        boolean result = validationChecker.checkAdditionalValidation(transactionImportData,
                new SalesTransactionErrorDetail());

        assertThat(result).isEqualTo(true);

    }

    @Test
    public void testTenderInfoCardNoExistPassAddition() {
        transactionImportData.getTransactionList().forEach(tran -> {
            tran.getNonItemDetailList().forEach(non -> {
                non.setNonItemDiscountDetailList(new ArrayList<>());
            });
            tran.getSalesTransactionTenderList().forEach(tender -> {
                tender.setTenderGroupId("CREDIT");
                tender.getTenderInfoList().setCardNo("cardno0123");
            });
        });
        salesTransactionErrorId = "testValidationLevel1Required";

        when(salesTransactionErrorDetailEntityMapper.insertSelective(anyObject())).thenReturn(1);

        boolean result = validationChecker.checkAdditionalValidation(transactionImportData,
                new SalesTransactionErrorDetail());

        assertThat(result).isEqualTo(true);

    }

    @Test
    public void testItemDetailExcludedTaxNullAddition() {
        transactionImportData.getTransactionList().forEach(
                transaction -> transaction.getTransactionItemDetailList().forEach(itemDetail -> {
                    itemDetail.setItemTaxKind(TaxSystemType.OUT.toString());
                    itemDetail.setItemSalesAmtTaxExcluded(null);
                    itemDetail.setItemUnitPriceTaxExcluded(null);
                }));
        salesTransactionErrorId = "testValidationLevel1Required";

        when(salesTransactionErrorDetailEntityMapper.insertSelective(anyObject())).thenReturn(1);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.VALIDATION_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorItemValue1(), equalTo(null));

        assertThat(argument.getValue().getErrorItemId1(), equalTo("item_unit_price_tax_excluded"));
    }

    @Test
    public void testItemDetailExcludedTaxNotNullAddition() {
        transactionImportData.getTransactionList().forEach(
                transaction -> transaction.getTransactionItemDetailList().forEach(itemDetail -> {
                    itemDetail.setItemTaxKind(TaxSystemType.OUT.toString());
                }));
        salesTransactionErrorId = "testValidationLevel1Required";

        when(salesTransactionErrorDetailEntityMapper.insertSelective(anyObject())).thenReturn(1);

        boolean result = validationChecker.checkAdditionalValidation(transactionImportData,
                new SalesTransactionErrorDetail());
        assertThat(result).isEqualTo(true);
    }

    @Test
    public void testItemDetailNullTaxKindAddition() {
        transactionImportData.getTransactionList().forEach(
                transaction -> transaction.getTransactionItemDetailList().forEach(itemDetail -> {
                    itemDetail.setItemTaxKind(null);
                }));
        salesTransactionErrorId = "testValidationLevel1Required";

        when(salesTransactionErrorDetailEntityMapper.insertSelective(anyObject())).thenReturn(1);

        boolean result = validationChecker.checkAdditionalValidation(transactionImportData,
                new SalesTransactionErrorDetail());
        assertThat(result).isEqualTo(true);
    }

    @Test
    public void testItemDetailDiscountIncludedTaxNullAddition() {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getSalesTransactionTenderList().forEach(tender -> {
                tender.setTenderInfoList(null);
            });
            transaction.getTransactionItemDetailList().forEach(itemDetail -> {
                itemDetail.setItemTaxKind(TaxSystemType.IN.toString());
                itemDetail.getNonItemDetailListByItem().forEach(nonItemDetail -> {
                    nonItemDetail.setNonItemInfo(null);
                });
                itemDetail.getItemDiscountList().forEach(discount -> {
                    discount.setItemDiscountAmtTaxIncluded(null);
                });
            });
        });
        salesTransactionErrorId = "testValidationLevel1Required";

        when(salesTransactionErrorDetailEntityMapper.insertSelective(anyObject())).thenReturn(1);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.VALIDATION_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorItemValue1(), equalTo(null));

        assertThat(argument.getValue().getErrorItemId1(),
                equalTo("item_discount_amt_tax_included"));
    }

    @Test
    public void testNonItemDetailDiscountIncludedTaxNullAddition() {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getTransactionItemDetailList().forEach(itemDetail -> {
                itemDetail.setItemDiscountList(null);
                itemDetail.setItemTaxDetailList(null);
                itemDetail.setNonItemDetailListByItem(null);
            });
            transaction.getNonItemDetailList().forEach(nonItemDetail -> {
                nonItemDetail.setNonItemTaxKind(TaxSystemType.IN.toString());
                nonItemDetail.getNonItemDiscountDetailList().forEach(discount -> {
                    discount.setNonItemDiscountAmtTaxIncluded(null);
                });
            });
        });
        salesTransactionErrorId = "testValidationLevel1Required";

        when(salesTransactionErrorDetailEntityMapper.insertSelective(anyObject())).thenReturn(1);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.VALIDATION_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorItemValue1(), equalTo(null));

        assertThat(argument.getValue().getErrorItemId1(),
                equalTo("non_item_discount_amt_tax_included"));
    }

    @Test
    public void testNonItemDetailIncludedTaxNullAddition() {
        transactionImportData.getTransactionList().forEach(
                transaction -> transaction.getNonItemDetailList().forEach(nonItemDetail -> {
                    nonItemDetail.setNonItemTaxKind(TaxSystemType.IN.toString());
                    nonItemDetail.setNonItemUnitPriceTaxIncluded(null);
                }));
        salesTransactionErrorId = "testValidationLevel1Required";

        when(salesTransactionErrorDetailEntityMapper.insertSelective(anyObject())).thenReturn(1);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.VALIDATION_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorItemValue1(), equalTo(null));

        assertThat(argument.getValue().getErrorItemId1(),
                equalTo("non_item_unit_price_tax_included"));
    }

    @Test
    public void testTenderCashNumberEmptyAddition() {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getSalesTransactionTenderList().forEach(tender -> {
                tender.setTenderGroupId("CREDIT");
                tender.getTenderInfoList().setCardNo(null);
            });
        });
        salesTransactionErrorId = "testValidationLevel1Required";

        when(salesTransactionErrorDetailEntityMapper.insertSelective(anyObject())).thenReturn(1);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.VALIDATION_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorItemValue1(), equalTo(null));

        assertThat(argument.getValue().getErrorItemId1(), equalTo("card_no"));
    }

    @Test
    public void testValidationLevel1Length() {
        transactionImportData.setChannelCode("1234567891aa");
        salesTransactionErrorId = "testValidationLevel1Length";

        when(salesTransactionErrorDetailEntityMapper.insertSelective(anyObject())).thenReturn(1);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.VALIDATION_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorItemId1(), equalTo("channel_code"));

        assertThat(argument.getValue().getErrorItemValue1(), equalTo("1234567891aa"));
    }

    @Test
    public void testValidationLevel1Pattern() {
        transactionImportData.setOrderConfirmationBusinessDate("20180208");
        salesTransactionErrorId = "testValidationLevel1Pattern";

        when(salesTransactionErrorDetailEntityMapper.insertSelective(anyObject())).thenReturn(1);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.VALIDATION_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorItemValue1(), equalTo("20180208"));

        assertThat(argument.getValue().getErrorItemId1(),
                equalTo("order_confirmation_business_date"));
    }

    @Test
    public void testValidationLevel2Length() {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.setSystemBrandCode("12345");
        });
        salesTransactionErrorId = "testValidationLevel2Length";

        when(salesTransactionErrorDetailEntityMapper.insertSelective(anyObject())).thenReturn(1);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.VALIDATION_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorItemId1(), equalTo("system_brand_code"));

        assertThat(argument.getValue().getErrorItemValue1(), equalTo("12345"));
    }

    @Test
    public void testValidationLevel2TransactionIntegratedOrderIdLength() {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.setIntegratedOrderId("Ａ");
        });
        salesTransactionErrorId = "testValidationLevel2Length";

        when(salesTransactionErrorDetailEntityMapper.insertSelective(anyObject())).thenReturn(1);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.VALIDATION_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorItemId1(), equalTo("integrated_order_id"));

        assertThat(argument.getValue().getErrorItemValue1(), equalTo("Ａ"));

        assertThat(argument.getValue().getIntegratedOrderId(), equalTo(null));
    }

    @Test
    public void testValidationLevel2TransactionSalesTransactionIdOrderIdLength() {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.setSalesTransactionId("Ａ");
        });
        salesTransactionErrorId = "testValidationLevel2TransactionSalesTransactionIdOrderIdLength";

        when(salesTransactionErrorDetailEntityMapper.insertSelective(anyObject())).thenReturn(1);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.VALIDATION_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorItemId1(), equalTo("sales_transaction_id"));

        assertThat(argument.getValue().getErrorItemValue1(), equalTo("Ａ"));

        assertThat(argument.getValue().getSalesTransactionId(), equalTo(null));
    }

    @Test
    public void testValidationLevel2TransactionSystemBusinessCodeLength() {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.setSystemBusinessCode("Ａ");
        });
        salesTransactionErrorId = "testValidationLevel2TransactionSystemBusinessCodeLength";

        when(salesTransactionErrorDetailEntityMapper.insertSelective(anyObject())).thenReturn(1);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.VALIDATION_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorItemId1(), equalTo("system_business_code"));

        assertThat(argument.getValue().getErrorItemValue1(), equalTo("Ａ"));

        assertThat(argument.getValue().getSystemBusinessCode(), equalTo(null));
    }

    @Test
    public void testValidationLevel2TransactionSystemCountryCodeLength() {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.setSystemCountryCode("Ａ");
        });
        salesTransactionErrorId = "testValidationLevel2TransactionSystemCountryCodeLength";

        when(salesTransactionErrorDetailEntityMapper.insertSelective(anyObject())).thenReturn(1);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.VALIDATION_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorItemId1(), equalTo("system_country_code"));

        assertThat(argument.getValue().getErrorItemValue1(), equalTo("Ａ"));

        assertThat(argument.getValue().getSystemCountryCode(), equalTo(null));
    }

    @Test
    public void testValidationLevel2TransactionStoreCodeLength() {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.setStoreCode("Ａ");
        });
        salesTransactionErrorId = "testValidationLevel2TransactionStoreCodeLength";

        when(salesTransactionErrorDetailEntityMapper.insertSelective(anyObject())).thenReturn(1);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.VALIDATION_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorItemId1(), equalTo("store_code"));

        assertThat(argument.getValue().getErrorItemValue1(), equalTo("Ａ"));

        assertThat(argument.getValue().getStoreCode(), equalTo(null));
    }

    @Test
    public void testValidationLevel2Required() {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.setCashRegisterNo(null);
        });
        salesTransactionErrorId = "testValidationLevel2Required";

        when(salesTransactionErrorDetailEntityMapper.insertSelective(anyObject())).thenReturn(1);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.VALIDATION_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorItemValue1(), equalTo(null));

        assertThat(argument.getValue().getErrorItemId1(), equalTo("cash_register_no"));
    }

    @Test
    public void testValidationLevel2Pattern() {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.setDataCreationBusinessDate("2018/02/08");
        });
        salesTransactionErrorId = "testValidationLevel2Pattern";

        when(salesTransactionErrorDetailEntityMapper.insertSelective(anyObject())).thenReturn(1);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.VALIDATION_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorItemId1(), equalTo("data_creation_business_date"));

        assertThat(argument.getValue().getErrorItemValue1(), equalTo("2018/02/08"));
    }

    @Test
    public void testValidationLevel3Length() {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getTransactionItemDetailList().forEach(transactionItemDetailList -> {
                transactionItemDetailList.setSystemBrandCode("54321");
            });
        });
        salesTransactionErrorId = "testValidationLevel3Length";

        when(salesTransactionErrorDetailEntityMapper.insertSelective(anyObject())).thenReturn(1);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.VALIDATION_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorItemId1(), equalTo("system_brand_code"));

        assertThat(argument.getValue().getErrorItemValue1(), equalTo("54321"));
    }

    @Test
    public void testValidationLevel3Pattern() {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getTransactionItemDetailList().forEach(transactionItemDetailList -> {
                transactionItemDetailList.setOrderStatusUpdateDate("2018.02.08");
            });
        });
        salesTransactionErrorId = "testValidationLevel3Pattern";

        when(salesTransactionErrorDetailEntityMapper.insertSelective(anyObject())).thenReturn(1);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.VALIDATION_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorItemValue1(), equalTo("2018.02.08"));

        assertThat(argument.getValue().getErrorItemId1(), equalTo("order_status_update_date"));
    }

    @Test
    public void testValidationLevel4Length() {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getTransactionItemDetailList().forEach(transactionItemDetailList -> {
                transactionItemDetailList.getNonItemDetailListByItem().forEach(nonItemDetail -> {
                    nonItemDetail.setNonMdType("1234567");
                });
            });
        });
        salesTransactionErrorId = "testValidationLevel4Length";

        when(salesTransactionErrorDetailEntityMapper.insertSelective(anyObject())).thenReturn(1);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.VALIDATION_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorItemId1(), equalTo("non_md_type"));

        assertThat(argument.getValue().getErrorItemValue1(), equalTo("1234567"));
    }

    @Test
    public void testValidationLevel4Pattern() {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getTransactionItemDetailList().forEach(transactionItemDetailList -> {
                transactionItemDetailList.getNonItemDetailListByItem().forEach(nonItemDetail -> {
                    nonItemDetail.setOrderStatusUpdateDate("2018.02.08");
                });
            });
        });
        salesTransactionErrorId = "testValidationLevel4Pattern";

        when(salesTransactionErrorDetailEntityMapper.insertSelective(anyObject())).thenReturn(1);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.VALIDATION_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorItemValue1(), equalTo("2018.02.08"));

        assertThat(argument.getValue().getErrorItemId1(), equalTo("order_status_update_date"));
    }

    @Test
    public void testValidationLevel5Length() {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getTransactionItemDetailList().forEach(transactionItemDetailList -> {
                transactionItemDetailList.getNonItemDetailListByItem().forEach(nonItemDetail -> {
                    nonItemDetail.getNonItemInfo().setItemDetailSubNumber(12345);
                });
            });
        });
        salesTransactionErrorId = "testValidationLevel5Length";

        when(salesTransactionErrorDetailEntityMapper.insertSelective(anyObject())).thenReturn(1);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.VALIDATION_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorItemValue1(), equalTo("12345"));

        assertThat(argument.getValue().getErrorItemId1(), equalTo("item_detail_sub_number"));
    }

    @Test
    public void testValidationLevel5Pattern() {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getTransactionItemDetailList().forEach(transactionItemDetailList -> {
                transactionItemDetailList.getNonItemDetailListByItem().forEach(NonItemDetail -> {
                    NonItemDetail.getNonItemInfo().setCodeValue1("2018/02/0８");
                });
            });
        });
        salesTransactionErrorId = "testValidationLevel5Pattern";

        when(salesTransactionErrorDetailEntityMapper.insertSelective(anyObject())).thenReturn(1);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.VALIDATION_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorItemId1(), equalTo("code_value_1"));

        assertThat(argument.getValue().getErrorItemValue1(), equalTo("2018/02/0８"));
    }

    @Test
    public void testValidationLevel5NonItemDiscountDetailPatternError() {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getTransactionItemDetailList().forEach(transactionItemDetailList -> {
                transactionItemDetailList.getNonItemDetailListByItem().forEach(NonItemDetail -> {
                    NonItemDetail.getNonItemDiscountDetailList().forEach(nonItemDiscountDetail -> {
                        nonItemDiscountDetail.setNonItemPromotionType("ＡAaa");
                    });
                });
            });
        });
        salesTransactionErrorId = "testValidationLevel5NonItemDiscountDetailPatternError";

        when(salesTransactionErrorDetailEntityMapper.insertSelective(anyObject())).thenReturn(1);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.VALIDATION_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorItemId1(), equalTo("non_item_promotion_type"));

        assertThat(argument.getValue().getErrorItemValue1(), equalTo("ＡAaa"));
    }

    @Test
    public void testValidationLevel4NonItemDiscountDetailPatternError() {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getNonItemDetailList().forEach(nonItemDetail -> {
                nonItemDetail.getNonItemDiscountDetailList().forEach(nonItemDiscountDetail -> {
                    nonItemDiscountDetail.setNonItemPromotionType("Ａ");
                });
            });
        });
        salesTransactionErrorId = "testValidationLevel4NonItemDiscountDetailPatternError";

        when(salesTransactionErrorDetailEntityMapper.insertSelective(anyObject())).thenReturn(1);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.VALIDATION_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorItemId1(), equalTo("non_item_promotion_type"));

        assertThat(argument.getValue().getErrorItemValue1(), equalTo("Ａ"));
    }

    @Test
    public void testValidationLevel4NonItemTaxDetailPatternError() {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getNonItemDetailList().forEach(nonItemDetail -> {
                nonItemDetail.getNonItemTaxDetailList().forEach(nonItemTaxDetail -> {
                    nonItemTaxDetail.setNonItemTaxType("Ａ");
                });
            });
        });
        salesTransactionErrorId = "testValidationLevel4NonItemTaxDetailPatternError";

        when(salesTransactionErrorDetailEntityMapper.insertSelective(anyObject())).thenReturn(1);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.VALIDATION_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorItemId1(), equalTo("non_item_tax_type"));

        assertThat(argument.getValue().getErrorItemValue1(), equalTo("Ａ"));
    }

    @Test
    public void testValidationLevel5NonItemTaxDetailPatternError() {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getTransactionItemDetailList().forEach(transactionItemDetailList -> {
                transactionItemDetailList.getNonItemDetailListByItem().forEach(NonItemDetail -> {
                    NonItemDetail.getNonItemTaxDetailList().forEach(nonItemTaxDetail -> {
                        nonItemTaxDetail.setNonItemTaxAmountSign("Ａ");
                    });
                });
            });
        });
        salesTransactionErrorId = "testValidationLevel5NonItemTaxDetailPatternError";

        when(salesTransactionErrorDetailEntityMapper.insertSelective(anyObject())).thenReturn(1);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.VALIDATION_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorItemId1(), equalTo("non_item_tax_amount_sign"));

        assertThat(argument.getValue().getErrorItemValue1(), equalTo("Ａ"));
    }

    @Test
    public void testValidationLevel4ItemDiscountPatternError() {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getTransactionItemDetailList().forEach(transactionItemDetailList -> {
                transactionItemDetailList.getItemDiscountList().forEach(itemDiscount -> {
                    itemDiscount.setItemPromotionType("Ａ");
                });
            });
        });
        salesTransactionErrorId = "testValidationLevel5NonItemTaxDetaiPatternError";

        when(salesTransactionErrorDetailEntityMapper.insertSelective(anyObject())).thenReturn(1);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.VALIDATION_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorItemId1(), equalTo("item_promotion_type"));

        assertThat(argument.getValue().getErrorItemValue1(), equalTo("Ａ"));
    }

    @Test
    public void testValidationLevel4ItemTaxPatternError() {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getTransactionItemDetailList().forEach(transactionItemDetailList -> {
                transactionItemDetailList.getItemTaxDetailList().forEach(itemTaxDetail -> {
                    itemTaxDetail.setItemTaxType("Ａ");
                });
            });
        });
        salesTransactionErrorId = "testValidationLevel5NonItemTaxDetaiPatternError";

        when(salesTransactionErrorDetailEntityMapper.insertSelective(anyObject())).thenReturn(1);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.VALIDATION_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorItemId1(), equalTo("item_tax_type"));

        assertThat(argument.getValue().getErrorItemValue1(), equalTo("Ａ"));
    }

    @Test
    public void testValidationLevel3NonItemDetailPatternError() {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getNonItemDetailList().forEach(nonItemDetail -> {
                nonItemDetail.setNonMdType("Ａ");
            });
        });
        salesTransactionErrorId = "testValidationLevel3NonItemDetailPatternError";

        when(salesTransactionErrorDetailEntityMapper.insertSelective(anyObject())).thenReturn(1);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.VALIDATION_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorItemId1(), equalTo("non_md_type"));

        assertThat(argument.getValue().getErrorItemValue1(), equalTo("Ａ"));
    }

    @Test
    public void testValidationLevel4NonItemInfoPatternError() {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getNonItemDetailList().forEach(nonItemDetail -> {
                nonItemDetail.getNonItemInfo().setKeyCode("Ａ");
            });
        });
        salesTransactionErrorId = "testValidationLevel4NonItemInfoPatternError";

        when(salesTransactionErrorDetailEntityMapper.insertSelective(anyObject())).thenReturn(1);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.VALIDATION_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorItemId1(), equalTo("key_code"));

        assertThat(argument.getValue().getErrorItemValue1(), equalTo("Ａ"));
    }

    @Test
    public void testValidationLevel3SalesTransactionTenderPatternError() {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getSalesTransactionTenderList().forEach(tender -> {
                tender.setTenderGroupId("Ａ");
            });
        });
        salesTransactionErrorId = "testValidationLevel4NonItemInfoPatternError";

        when(salesTransactionErrorDetailEntityMapper.insertSelective(anyObject())).thenReturn(1);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.VALIDATION_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorItemId1(), equalTo("tender_group_id"));

        assertThat(argument.getValue().getErrorItemValue1(), equalTo("Ａ"));
    }

    @Test
    public void testValidationLevel4TenderInfoPatternError() {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getSalesTransactionTenderList().forEach(tender -> {
                tender.getTenderInfoList().setCouponType("Ａ");
            });
        });
        salesTransactionErrorId = "testValidationLevel4TenderInfoPatternError";

        when(salesTransactionErrorDetailEntityMapper.insertSelective(anyObject())).thenReturn(1);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.VALIDATION_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorItemId1(), equalTo("coupon_type"));

        assertThat(argument.getValue().getErrorItemValue1(), equalTo("Ａ"));
    }

    @Test
    public void testValidationLevel3TransactionTaxDetailPatternError() {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getSalesTransactionTaxDetailList().forEach(tax -> {
                tax.setTaxGroup("Ａ");
            });
        });
        salesTransactionErrorId = "testValidationLevel3TransactionTaxDetailPatternError";

        when(salesTransactionErrorDetailEntityMapper.insertSelective(anyObject())).thenReturn(1);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.VALIDATION_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorItemId1(), equalTo("tax_group"));

        assertThat(argument.getValue().getErrorItemValue1(), equalTo("Ａ"));
    }

    @Test
    public void testValidationLevel3TransactionTotalPatternError() {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getSalesTransactionTotalList().forEach(total -> {
                total.setTotalType("Ａ");
            });
        });
        salesTransactionErrorId = "testValidationLevel3TransactionTaxDetailPatternError";

        when(salesTransactionErrorDetailEntityMapper.insertSelective(anyObject())).thenReturn(1);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.VALIDATION_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorItemId1(), equalTo("total_type"));

        assertThat(argument.getValue().getErrorItemValue1(), equalTo("Ａ"));
    }

    @Test
    public void testBusinessDateUpdateTimeNullCheck() {
        salesTransactionErrorId = "testBusinessDateUpdateTimeNullCheck";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeEntityMapper.selectByPrimaryKey(anyString())).thenReturn(getMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        transactionImportData.setUpdateType("DELETE");

        transactionImportData.setOrderConfirmationDateTime(null);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.NORMAL);
    }

    @Test
    public void testBusinessNonItemDetailListEmptyPass() {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.setNonItemDetailList(new ArrayList<>());
        });
        salesTransactionErrorId = "testBusinessDateUpdateTimeNullCheck";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeEntityMapper.selectByPrimaryKey(anyString())).thenReturn(getMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        transactionImportData.setUpdateType("DELETE");

        boolean result = businessChecker.checkDataRelation(transactionImportData,
                new SalesTransactionErrorDetail());

        assertThat(result).isEqualTo(true);
    }

    @Test
    public void testBusinessItemDetailListEmptyPass() {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.setTransactionItemDetailList(new ArrayList<>());
        });
        salesTransactionErrorId = "testBusinessDateUpdateTimeNullCheck";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeEntityMapper.selectByPrimaryKey(anyString())).thenReturn(getMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        transactionImportData.setUpdateType("DELETE");

        transactionDataConverter.convertTransactionImportData(transactionImportData);
        boolean result =
                businessChecker.checkDate(transactionImportData, new SalesTransactionErrorDetail());

        assertThat(result).isEqualTo(true);
    }

    @Test
    public void testMStoreControlCheckBusinessErrorOne() {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.setOrderSubstatus(null);
        });
        salesTransactionErrorId = "testPassAllCheck";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(storeControlEntityMapper.selectByPrimaryKey(anyString())).thenReturn(null);

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeEntityMapper.selectByPrimaryKey(anyString())).thenReturn(getMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.BUSINESS_ERROR);
    }

    @Test
    public void testMStoreControlCheckBusinessErrorTwo() {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.setOrderSubstatus(null);
        });
        salesTransactionErrorId = "testPassAllCheck";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        StoreControlMaster storeControl = getMStoreControl();
        storeControl.setBusinessDate("");
        when(storeControlEntityMapper.selectByPrimaryKey(anyString())).thenReturn(storeControl);

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeEntityMapper.selectByPrimaryKey(anyString())).thenReturn(getMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.BUSINESS_ERROR);
    }

    @Test
    public void testMStoreControlCheckBusinessErrorThree() {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.setOrderSubstatus(null);
        });
        salesTransactionErrorId = "testPassAllCheck";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        StoreControlMaster storeControl = getMStoreControl();
        storeControl.setBusinessEndOfDate("");
        when(storeControlEntityMapper.selectByPrimaryKey(anyString())).thenReturn(storeControl);

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeEntityMapper.selectByPrimaryKey(anyString())).thenReturn(getMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.BUSINESS_ERROR);
    }

    @Test
    public void testPassAllStoreOpenSpecificationDaysCheck() {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.setOrderSubstatus(null);
        });
        salesTransactionErrorId = "testPassAllStoreOpenSpecificationDaysCheck";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValueForStoreOpenSpecificationDaysLevel1);

        when(storeEntityMapper.selectByPrimaryKey(anyString())).thenReturn(getMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.NORMAL);
    }

    @Test
    public void testPassAllStoreOpenSpecificationDaysCheckLevel2() {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.setOrderSubstatus(null);
        });
        salesTransactionErrorId = "testPassAllStoreOpenSpecificationDaysCheck";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValueForStoreOpenSpecificationDaysLevel2);

        when(storeEntityMapper.selectByPrimaryKey(anyString())).thenReturn(getMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.NORMAL);
    }

    @Test
    public void testPassAllCheck() {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.setOrderSubstatus(null);
        });
        salesTransactionErrorId = "testPassAllCheck";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeEntityMapper.selectByPrimaryKey(anyString())).thenReturn(getMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.NORMAL);
    }

    @Test
    public void testPassAllCheckNullNonItemTaxKind() {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.setOrderSubstatus(null);
            transaction.getNonItemDetailList().forEach(nonItemDetail -> {
                nonItemDetail.setNonItemTaxKind(null);
            });
        });
        salesTransactionErrorId = "testPassAllCheck";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeEntityMapper.selectByPrimaryKey(anyString())).thenReturn(getMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.NORMAL);
    }

    @Test
    public void testPassAllCheckNullItemTaxKind() {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.setOrderSubstatus(null);
            transaction.getTransactionItemDetailList().forEach(item -> {
                item.setItemTaxKind(null);
            });
        });
        salesTransactionErrorId = "testPassAllCheck";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeEntityMapper.selectByPrimaryKey(anyString())).thenReturn(getMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.NORMAL);
    }

    @Test
    public void testPassCodeValueOff() {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.setOrderSubstatus(null);
        });
        salesTransactionErrorId = "testPassAllCheck";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        StoreControlMaster storeControlEntity = getMStoreControl();
        storeControlEntity.setStoreCode("9");

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(storeControlEntity);

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeEntityMapper.selectByPrimaryKey(anyString())).thenReturn(getMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.NORMAL);
    }

    @Test
    public void testDiscountAmountTaxExcludedEmpty1() {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.setOrderSubstatus(null);
            transaction.setTransactionItemDetailList(new ArrayList<>());
        });
        salesTransactionErrorId = "testPassAllCheck";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        StoreControlMaster storeControlEntity = getMStoreControl();

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(storeControlEntity);

        List<BusinessCountryStateSettingMaster> mbList = new ArrayList<>();
        BusinessCountryStateSettingMaster ent = new BusinessCountryStateSettingMaster();
        ent.setCodeValue("9");
        mbList.add(ent);
        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .thenReturn(mbList);

        when(storeEntityMapper.selectByPrimaryKey(anyString())).thenReturn(getMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.NORMAL);
    }

    @Test
    public void testDiscountAmountTaxExcludedNonItemEmpty() {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.setOrderSubstatus(null);
            transaction.getTransactionItemDetailList().forEach(item -> {
                item.setNonItemDetailListByItem(new ArrayList<>());
            });
            transaction.setNonItemDetailList(new ArrayList<>());
        });
        salesTransactionErrorId = "testPassAllCheck";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        StoreControlMaster storeControlEntity = getMStoreControl();

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(storeControlEntity);

        List<BusinessCountryStateSettingMaster> mbList = new ArrayList<>();
        BusinessCountryStateSettingMaster ent = new BusinessCountryStateSettingMaster();
        ent.setCodeValue("9");
        mbList.add(ent);
        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .thenReturn(mbList);

        when(storeEntityMapper.selectByPrimaryKey(anyString())).thenReturn(getMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.NORMAL);
    }

    @Test
    public void testValidationUpdatePassAllCheck() {
        transactionImportData.setUpdateType(UpdateType.UPDATE.getUpdateType());
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.setOrderSubstatus(null);
            transaction.getTransactionItemDetailList().forEach(item -> {
                item.getNonItemDetailListByItem().forEach(non -> {
                    non.setNonItemDiscountDetailList(new ArrayList<>());
                    non.setNonItemTaxDetailList(new ArrayList<>());
                });
            });
            transaction.getNonItemDetailList().forEach(non -> {
                non.setNonItemInfo(null);
                non.setNonItemDiscountDetailList(new ArrayList<>());
                non.setNonItemTaxDetailList(new ArrayList<>());
            });
            transaction.setSalesTransactionTaxDetailList(new ArrayList<>());
            transaction.setSalesTransactionTotalList(new ArrayList<>());
            transaction.setSalesTransactionTenderList(new ArrayList<>());
        });
        salesTransactionErrorId = "testPassAllCheck";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeEntityMapper.selectByPrimaryKey(anyString())).thenReturn(getMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        boolean result = validationChecker.checkValidation(transactionImportData,
                new SalesTransactionErrorDetail(), false);

        assertThat(result).isEqualTo(true);
    }

    @Test
    public void testPassAllCheckEmptyOrderConfirmationBusinessDate() {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.setOrderSubstatus(null);
        });
        transactionImportData.setOrderConfirmationBusinessDate("");
        salesTransactionErrorId = "testPassAllCheck";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeEntityMapper.selectByPrimaryKey(anyString())).thenReturn(getMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.NORMAL);
    }

    @Test
    public void testBusinessPassCheckEmptyNonItemDetailList() {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.setOrderSubstatus(null);
            transaction.setDataCreationDateTime(null);
            transaction.setOrderStatusLastUpdateDateTime(null);
            transaction.setNonItemDetailList(new ArrayList<>());
        });
        salesTransactionErrorId = "testPassAllCheck";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeEntityMapper.selectByPrimaryKey(anyString())).thenReturn(getMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        transactionDataConverter.convertTransactionImportData(transactionImportData);
        boolean result =
                businessChecker.checkDate(transactionImportData, new SalesTransactionErrorDetail());

        assertThat(result).isEqualTo(true);
    }

    @Test
    public void testBalancePassCheckSignCodeNull() {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.setOrderSubstatus(null);
            transaction.getTransactionItemDetailList().forEach(tran -> {
                tran.getNonItemDetailListByItem().forEach(non -> {
                    non.setQuantityCode(null);
                });
            });
        });
        salesTransactionErrorId = "testPassAllCheck";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeEntityMapper.selectByPrimaryKey(anyString())).thenReturn(getMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        transactionDataConverter.convertTransactionImportData(transactionImportData);
        boolean result = businessChecker.checkAmountBalance(transactionImportData,
                new SalesTransactionErrorDetail());

        assertThat(result).isEqualTo(true);
    }

    @Test
    public void testBalancePassCheckEmptyTransaction() {
        transactionImportData.setTransactionList(new ArrayList<>());
        salesTransactionErrorId = "testPassAllCheck";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeEntityMapper.selectByPrimaryKey(anyString())).thenReturn(getMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        transactionDataConverter.convertTransactionImportData(transactionImportData);
        boolean result = businessChecker.checkAmountBalance(transactionImportData,
                new SalesTransactionErrorDetail());

        assertThat(result).isEqualTo(true);
    }

    @Test
    public void testBalanceCheckEmptyTenderList() {
        transactionImportData.getTransactionList().forEach(tran -> {
            tran.setSalesTransactionTenderList(new ArrayList<>());
        });
        salesTransactionErrorId = "testPassAllCheck";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeEntityMapper.selectByPrimaryKey(anyString())).thenReturn(getMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        transactionDataConverter.convertTransactionImportData(transactionImportData);
        boolean result = businessChecker.checkAmountBalance(transactionImportData,
                new SalesTransactionErrorDetail());

        assertThat(result).isEqualTo(false);
    }

    @Test
    public void testBalanceCheckEmptyItemList() {
        transactionImportData.getTransactionList().forEach(tran -> {
            tran.setSalesTransactionTenderList(new ArrayList<>());
        });
        salesTransactionErrorId = "testPassAllCheck";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeEntityMapper.selectByPrimaryKey(anyString())).thenReturn(getMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        transactionDataConverter.convertTransactionImportData(transactionImportData);
        boolean result = businessChecker.checkAmountBalance(transactionImportData,
                new SalesTransactionErrorDetail());

        assertThat(result).isEqualTo(false);
    }

    @Test
    public void testDiscountAmountTaxExcludedEmpty2() {
        transactionImportData.getTransactionList().forEach(tran -> {
            tran.setNonItemDetailList(new ArrayList<>());
            tran.setTransactionItemDetailList(new ArrayList<>());
        });
        salesTransactionErrorId = "testPassAllCheck";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .thenReturn(new ArrayList<>());

        when(storeEntityMapper.selectByPrimaryKey(anyString())).thenReturn(getMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        transactionDataConverter.convertTransactionImportData(transactionImportData);
        boolean result = businessChecker.checkAmountBalance(transactionImportData,
                new SalesTransactionErrorDetail());

        assertThat(result).isEqualTo(false);
    }

    @Test
    public void testBalancePassCheckEmptyItemDetailNonItemList() {
        transactionImportData.getTransactionList().forEach(tran -> {
            tran.setTransactionItemDetailList(new ArrayList<>());
            tran.setNonItemDetailList(new ArrayList<>());
        });
        salesTransactionErrorId = "testPassAllCheck";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeEntityMapper.selectByPrimaryKey(anyString())).thenReturn(getMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        transactionDataConverter.convertTransactionImportData(transactionImportData);
        boolean result = businessChecker.checkAmountBalance(transactionImportData,
                new SalesTransactionErrorDetail());

        assertThat(result).isEqualTo(false);
    }

    @Test
    public void testBalancePassCheckEmptyNonItemList() {
        transactionImportData.getTransactionList().forEach(tran -> {
            tran.getTransactionItemDetailList().forEach(item -> {
                item.setNonItemDetailListByItem(new ArrayList<>());
            });
        });
        salesTransactionErrorId = "testPassAllCheck";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeEntityMapper.selectByPrimaryKey(anyString())).thenReturn(getMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        transactionDataConverter.convertTransactionImportData(transactionImportData);
        boolean result = businessChecker.checkAmountBalance(transactionImportData,
                new SalesTransactionErrorDetail());

        assertThat(result).isEqualTo(false);
    }

    @Test
    public void testBalancePassCheckTaxExcludedEmpty() {
        transactionImportData.getTransactionList().forEach(tran -> {
            tran.setTransactionItemDetailList(new ArrayList<>());
        });
        salesTransactionErrorId = "testPassAllCheck";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeEntityMapper.selectByPrimaryKey(anyString())).thenReturn(getMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        transactionDataConverter.convertTransactionImportData(transactionImportData);
        boolean result = businessChecker.checkAmountBalance(transactionImportData,
                new SalesTransactionErrorDetail());

        assertThat(result).isEqualTo(false);
    }

    @Test
    public void testBusinessPassCheckEmptyItemDetailList() {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.setOrderSubstatus(null);
            transaction.setTransactionItemDetailList(new ArrayList<>());
            transaction.getNonItemDetailList().forEach(non -> {
                non.setOrderStatusLastUpdateDateTime(null);
            });
        });
        salesTransactionErrorId = "testPassAllCheck";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeEntityMapper.selectByPrimaryKey(anyString())).thenReturn(getMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        transactionDataConverter.convertTransactionImportData(transactionImportData);
        boolean result =
                businessChecker.checkDate(transactionImportData, new SalesTransactionErrorDetail());

        assertThat(result).isEqualTo(true);
    }

    @Test
    public void testBusinessPassCheckNonItemDetailLevel3() {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.setOrderSubstatus(null);
            transaction.getTransactionItemDetailList().forEach(item -> {
                item.setOrderStatusLastUpdateDateTime(null);
                item.setNonItemDetailListByItem(new ArrayList<>());
            });
        });
        salesTransactionErrorId = "testPassAllCheck";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(storeControlEntityMapper.selectByPrimaryKey(any())).thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeEntityMapper.selectByPrimaryKey(anyString())).thenReturn(getMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);
        transactionDataConverter.convertTransactionImportData(transactionImportData);
        boolean result =
                businessChecker.checkDate(transactionImportData, new SalesTransactionErrorDetail());

        assertThat(result).isEqualTo(true);
    }

    @Test
    public void testBusinessPassCheckItemDetailDateEmpty() {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.setOrderSubstatus(null);
            transaction.getTransactionItemDetailList().forEach(item -> {
                item.getNonItemDetailListByItem().forEach(non -> {
                    non.setOrderStatusLastUpdateDateTime(null);
                });
            });
        });
        salesTransactionErrorId = "testPassAllCheck";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(storeControlEntityMapper.selectByPrimaryKey(any())).thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeEntityMapper.selectByPrimaryKey(anyString())).thenReturn(getMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);
        transactionDataConverter.convertTransactionImportData(transactionImportData);
        boolean result =
                businessChecker.checkDate(transactionImportData, new SalesTransactionErrorDetail());

        assertThat(result).isEqualTo(true);
    }

    @Test
    public void testBusinessPassCheckItemDetailDate() {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.setOrderSubstatus(null);
            transaction.getTransactionItemDetailList().forEach(item -> {
                item.setOrderStatusLastUpdateDateTime(null);
                item.setNonItemDetailListByItem(new ArrayList<>());
            });
        });
        salesTransactionErrorId = "testPassAllCheck";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeEntityMapper.selectByPrimaryKey(anyString())).thenReturn(getMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        transactionDataConverter.convertTransactionImportData(transactionImportData);
        boolean result =
                businessChecker.checkDate(transactionImportData, new SalesTransactionErrorDetail());

        assertThat(result).isEqualTo(true);
    }

    @Test
    public void testBusinessPassAllNullOrderConfirmationDateTime() {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.setOrderSubstatus(null);
        });
        transactionImportData.setOrderConfirmationDateTime(null);
        transactionImportData.setOrderConfirmationBusinessDate("");
        salesTransactionErrorId = "testPassAllCheck";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeEntityMapper.selectByPrimaryKey(anyString())).thenReturn(getMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        transactionDataConverter.convertTransactionImportData(transactionImportData);
        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        boolean result =
                businessChecker.checkDate(transactionImportData, new SalesTransactionErrorDetail());

        assertThat(result).isEqualTo(true);
    }

    @Test
    public void testBusinessPassAllCheckEmptyTransaction() {
        transactionImportData.setTransactionList(new ArrayList<>());
        salesTransactionErrorId = "testPassAllCheck";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeEntityMapper.selectByPrimaryKey(anyString())).thenReturn(getMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        boolean result = businessChecker.checkDataRelation(transactionImportData,
                new SalesTransactionErrorDetail());

        assertThat(result).isEqualTo(true);
    }

    @Test
    public void testPassAllCheckCalculateWithSign() {
        salesTransactionErrorId = "testPassAllCheckCalculateWithSign";
        transactionImportData.getTransactionList().forEach(transaction -> {

            transaction.getSalesTransactionTenderList().forEach(tender -> {
                tender.setPaymentSign("N");
                tender.setTaxIncludedPaymentAmount(getPriceByValue(49));
            });
            transaction.getSalesTransactionTaxDetailList().forEach(tax -> {
                tax.setTaxAmountSign("N");
                tax.setTaxAmount(getPriceByValue(50));
            });
        });

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getBalanceDetailCheckCodeValue);

        when(storeEntityMapper.selectByPrimaryKey(anyString())).thenReturn(getMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.NORMAL);
    }

    @Test
    public void testPassAllCheckDateNull() {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.setDataCreationBusinessDate(null);
        });
        salesTransactionErrorId = "testPassAllCheck";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeEntityMapper.selectByPrimaryKey(anyString())).thenReturn(getMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(1L);
        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.NORMAL);
    }

    @Test
    public void testPassAllCheckDateNulltest() {
        transactionImportData.setTransactionList(null);
        transactionImportData.setUpdateType("Default");
        salesTransactionErrorId = "testPassAllCheck";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeEntityMapper.selectByPrimaryKey(anyString())).thenReturn(getMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.NORMAL);
    }

    @Test
    public void testPassAllCheckDefault() {
        salesTransactionErrorId = "testPassAllCheck";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeEntityMapper.selectByPrimaryKey(anyString())).thenReturn(getMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        transactionImportData.setUpdateType("Default");

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.NORMAL);
    }

    @Test
    public void testPassAllCheckIsNotStore() {
        salesTransactionErrorId = "testPassAllCheck";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeEntityMapper.selectByPrimaryKey(anyString())).thenReturn(getMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        transactionImportData.setUpdateType("INSERT");

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.NORMAL);
    }

    @Test
    public void testPassAllCheckReturn() {
        salesTransactionErrorId = "testPassAllCheck";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeEntityMapper.selectByPrimaryKey(anyString())).thenReturn(getMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.setTransactionType("RETURN");
        });

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.NORMAL);
    }

    @Test
    public void testPassAllCheckCorrection() {
        salesTransactionErrorId = "testPassAllCheck";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeEntityMapper.selectByPrimaryKey(anyString())).thenReturn(getMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        transactionImportData.setUpdateType("CORRECTION");

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.NORMAL);
    }

    @Test
    public void testPassAllCheckUpdate() {
        salesTransactionErrorId = "testPassAllCheck";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeEntityMapper.selectByPrimaryKey(anyString())).thenReturn(getMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        transactionImportData.setUpdateType("UPDATE");

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.NORMAL);
    }

    @Test
    public void testPassAllCheckDelete() {
        salesTransactionErrorId = "testPassAllCheck";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeEntityMapper.selectByPrimaryKey(anyString())).thenReturn(getMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        transactionImportData.setUpdateType("DELETE");

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.NORMAL);
    }

    @Test
    public void testPassAllCheckCloseDateNull() {
        salesTransactionErrorId = "testPassAllCheckCloseDateNull";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeEntityMapper.selectByPrimaryKey(anyString())).thenReturn(getMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.NORMAL);
    }

    @Test
    public void testDataRelationError() {
        salesTransactionErrorId = "testDataRelationError";

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.setTransactionItemDetailList(null);
            transaction.setNonItemDetailList(null);
        });

        when(salesTransactionErrorDetailEntityMapper.insertSelective(anyObject())).thenReturn(1);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.BUSINESS_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorType(),
                equalTo(ErrorType.RELATION_ERROR.getErrorType()));
    }

    @Test
    public void testCheckBusinessDateStoreControlDataError() {
        salesTransactionErrorId = "testCheckBusinessDateStoreControlDataError";

        when(storeControlEntityMapper.selectByPrimaryKey(anyString())).thenReturn(null);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.BUSINESS_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorType(),
                equalTo(ErrorType.BUSINESS_ERROR.getErrorType()));

        assertThat(argument.getValue().getErrorItemValue1(),
                equalTo(DBItem.TABLE_NAME_M_STORE_CONTROL));
    }

    @Test
    public void testCheckUpdateTimeLevel1Error() {
        salesTransactionErrorId = "testCheckUpdateTimeLevel1Error";

        when(storeControlEntityMapper.selectByPrimaryKey(IMPORT_DATA_STORE_CODE))
                .thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        transactionImportData.setOrderConfirmationDateTime(
                OffsetDateTime.of(2018, 1, 9, 8, 0, 0, 0, ZoneOffset.of("Z")));

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.BUSINESS_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorType(),
                equalTo(ErrorType.UPDATE_BUSINESS_DATE_ERROR.getErrorType()));

        assertThat(argument.getValue().getErrorItemId1(),
                equalTo(ITEM_ID_CONSISTENCY_CHECK_BUSINESS_DATE));

        assertThat(argument.getValue().getErrorItemValue1(), equalTo("20171231"));

        assertThat(argument.getValue().getErrorItemId2(), equalTo(ITEM_ID_CODE_VALUE));

        assertThat(argument.getValue().getErrorItemValue2(), equalTo("5"));

        assertThat(argument.getValue().getErrorItemId3(),
                equalTo(ITEM_ID_ORDER_STATUS_UPDATE_DATE));

        assertThat(argument.getValue().getErrorItemValue3(), equalTo("2018-01-09T08:00:00"));
    }

    private List<StoreGeneralPurposeMaster> getZoneId() {
        List<StoreGeneralPurposeMaster> rentities = new ArrayList<>();
        StoreGeneralPurposeMaster mentity = new StoreGeneralPurposeMaster();
        mentity.setCode("Z");
        rentities.add(mentity);
        return rentities;
    }

    @Test
    public void testCheckBusinessDateLevel2StoreControlDataError() {
        salesTransactionErrorId = "testCheckBusinessDateLevel2StoreControlDataError";

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeControlEntityMapper.selectByPrimaryKey("1")).thenReturn(getMStoreControl());

        when(storeControlEntityMapper.selectByPrimaryKey(TRANSACTION_STORE_CODE)).thenReturn(null);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.BUSINESS_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getKeyInfo2(), equalTo(SALES_TRANSACTION_ID));

        assertThat(argument.getValue().getErrorType(),
                equalTo(ErrorType.BUSINESS_ERROR.getErrorType()));

        assertThat(argument.getValue().getErrorItemValue1(),
                equalTo(DBItem.TABLE_NAME_M_STORE_CONTROL));
    }

    @Test
    public void testCheckUpdateTimeLevel2TransactionError() {
        salesTransactionErrorId = "testCheckUpdateTimeLevel2TransactionError";

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.setDataCreationDateTime(
                    OffsetDateTime.of(2018, 1, 1, 8, 0, 0, 0, ZoneOffset.of("Z")));
        });

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.BUSINESS_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorType(),
                equalTo(ErrorType.UPDATE_BUSINESS_DATE_ERROR.getErrorType()));

        assertThat(argument.getValue().getErrorItemId1(),
                equalTo(ITEM_ID_CONSISTENCY_CHECK_BUSINESS_DATE));

        assertThat(argument.getValue().getErrorItemValue1(), equalTo("20171231"));

        assertThat(argument.getValue().getErrorItemId2(), equalTo(ITEM_ID_CODE_VALUE));

        assertThat(argument.getValue().getErrorItemValue2(), equalTo("5"));

        assertThat(argument.getValue().getErrorItemId3(),
                equalTo(ITEM_ID_ORDER_STATUS_UPDATE_DATE));

        assertThat(argument.getValue().getErrorItemValue3(), equalTo("2018-01-01T08:00:00"));
    }

    @Test
    public void testCheckUpdateTimeLevel2TransactionError2() {
        salesTransactionErrorId = "testCheckUpdateTimeLevel2TransactionError2";

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.setOrderStatusLastUpdateDateTime(
                    OffsetDateTime.of(2018, 1, 1, 8, 0, 0, 0, ZoneOffset.of("Z")));
        });

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.BUSINESS_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorType(),
                equalTo(ErrorType.UPDATE_BUSINESS_DATE_ERROR.getErrorType()));

        assertThat(argument.getValue().getKeyInfo2(), equalTo(SALES_TRANSACTION_ID));

        assertThat(argument.getValue().getErrorItemId1(),
                equalTo(ITEM_ID_CONSISTENCY_CHECK_BUSINESS_DATE));

        assertThat(argument.getValue().getErrorItemValue1(), equalTo("20171231"));

        assertThat(argument.getValue().getErrorItemId2(), equalTo(ITEM_ID_CODE_VALUE));

        assertThat(argument.getValue().getErrorItemValue2(), equalTo("5"));

        assertThat(argument.getValue().getErrorItemId3(),
                equalTo(ITEM_ID_ORDER_STATUS_UPDATE_DATE));

        assertThat(argument.getValue().getErrorItemValue3(), equalTo("2018-01-01T08:00:00"));
    }

    @Test
    public void testCheckUpdateTimeLevel3TransactionItemDetailError() {
        salesTransactionErrorId = "testCheckUpdateTimeLevel3TransactionItemDetailError";

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        transactionImportData.getTransactionList().forEach(transaction -> {
            for (TransactionItemDetail transactionItemDetail : transaction
                    .getTransactionItemDetailList()) {
                transactionItemDetail.setOrderStatusLastUpdateDateTime(
                        OffsetDateTime.of(2018, 1, 1, 8, 0, 0, 0, ZoneOffset.of("Z")));
            }
        });

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.BUSINESS_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorType(),
                equalTo(ErrorType.UPDATE_BUSINESS_DATE_ERROR.getErrorType()));

        assertThat(argument.getValue().getKeyInfo3(), equalTo("3"));

        assertThat(argument.getValue().getErrorItemId1(),
                equalTo(ITEM_ID_CONSISTENCY_CHECK_BUSINESS_DATE));

        assertThat(argument.getValue().getErrorItemValue1(), equalTo("20171231"));

        assertThat(argument.getValue().getErrorItemId2(), equalTo(ITEM_ID_CODE_VALUE));

        assertThat(argument.getValue().getErrorItemValue2(), equalTo("5"));

        assertThat(argument.getValue().getErrorItemId3(),
                equalTo(ITEM_ID_ORDER_STATUS_UPDATE_DATE));

        assertThat(argument.getValue().getErrorItemValue3(), equalTo("2018-01-01T08:00:00"));
    }

    @Test
    public void testCheckUpdateTimeLevel4NonItemDetailError() {
        salesTransactionErrorId = "testCheckUpdateTimeLevel4NonItemDetailError";

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getTransactionItemDetailList().forEach(transactionItemDetail -> {
                transactionItemDetail.getNonItemDetailListByItem().forEach(nonItemDetail -> {
                    nonItemDetail.setOrderStatusLastUpdateDateTime(
                            OffsetDateTime.of(2018, 1, 1, 8, 0, 0, 0, ZoneOffset.of("Z")));
                });
            });
        });

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.BUSINESS_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorType(),
                equalTo(ErrorType.UPDATE_BUSINESS_DATE_ERROR.getErrorType()));

        assertThat(argument.getValue().getKeyInfo4(), equalTo("4"));

        assertThat(argument.getValue().getErrorItemId1(),
                equalTo(ITEM_ID_CONSISTENCY_CHECK_BUSINESS_DATE));

        assertThat(argument.getValue().getErrorItemValue1(), equalTo("20171231"));

        assertThat(argument.getValue().getErrorItemId2(), equalTo(ITEM_ID_CODE_VALUE));

        assertThat(argument.getValue().getErrorItemValue2(), equalTo("5"));

        assertThat(argument.getValue().getErrorItemId3(),
                equalTo(ITEM_ID_ORDER_STATUS_UPDATE_DATE));

        assertThat(argument.getValue().getErrorItemValue3(), equalTo("2018-01-01T08:00:00"));
    }

    @Test
    public void testCheckUpdateTimeLevel3NonItemDetailError() {
        salesTransactionErrorId = "testCheckUpdateTimeLevel3NonItemDetailError";

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        transactionImportData.getTransactionList().forEach(transaction -> {
            for (NonItemDetail nonItemDetail : transaction.getNonItemDetailList()) {
                nonItemDetail.setOrderStatusLastUpdateDateTime(
                        OffsetDateTime.of(2018, 1, 1, 8, 0, 0, 0, ZoneOffset.of("Z")));
            }
        });

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.BUSINESS_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorType(),
                equalTo(ErrorType.UPDATE_BUSINESS_DATE_ERROR.getErrorType()));

        assertThat(argument.getValue().getKeyInfo3(), equalTo("3"));

        assertThat(argument.getValue().getErrorItemId1(),
                equalTo(ITEM_ID_CONSISTENCY_CHECK_BUSINESS_DATE));

        assertThat(argument.getValue().getErrorItemValue1(), equalTo("20171231"));

        assertThat(argument.getValue().getErrorItemId2(), equalTo(ITEM_ID_CODE_VALUE));

        assertThat(argument.getValue().getErrorItemValue2(), equalTo("5"));

        assertThat(argument.getValue().getErrorItemId3(),
                equalTo(ITEM_ID_ORDER_STATUS_UPDATE_DATE));

        assertThat(argument.getValue().getErrorItemValue3(), equalTo("2018-01-01T08:00:00"));
    }

    @Test
    public void testCheckFutureLevel2TransactionError() {
        salesTransactionErrorId = "testCheckFutureLevel2TransactionError";

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.setDataCreationBusinessDate("2018-01-12");
        });

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.BUSINESS_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorType(),
                equalTo(ErrorType.BUSINESS_DATE_ERROR.getErrorType()));

        assertThat(argument.getValue().getKeyInfo2(), equalTo(SALES_TRANSACTION_ID));

        assertThat(argument.getValue().getErrorItemId1(),
                equalTo(ITEM_ID_CONSISTENCY_CHECK_BUSINESS_DATE));

        assertThat(argument.getValue().getErrorItemValue1(), equalTo("20180110"));

        assertThat(argument.getValue().getErrorItemId2(),
                equalTo(ITEM_ID_DATA_CREATION_BUSINESS_DATE));

        assertThat(argument.getValue().getErrorItemValue2(), equalTo("20180112"));
    }

    @Test
    public void testCheckFutureLevel2TransactionError2() {
        salesTransactionErrorId = "testCheckFutureLevel2TransactionError2";

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.setOrderStatusUpdateDate("2018-01-12");
        });

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.BUSINESS_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorType(),
                equalTo(ErrorType.BUSINESS_DATE_ERROR.getErrorType()));

        assertThat(argument.getValue().getKeyInfo2(), equalTo(SALES_TRANSACTION_ID));

        assertThat(argument.getValue().getErrorItemId1(),
                equalTo(ITEM_ID_CONSISTENCY_CHECK_BUSINESS_DATE));

        assertThat(argument.getValue().getErrorItemValue1(), equalTo("20180110"));

        assertThat(argument.getValue().getErrorItemId2(),
                equalTo(ITEM_ID_ORDER_STATUS_UPDATE_DATE));

        assertThat(argument.getValue().getErrorItemValue2(), equalTo("20180112"));
    }

    @Test
    public void testCheckFutureLevel3TransactionError() {
        salesTransactionErrorId = "testCheckFutureLevel3TransactionError";

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getTransactionItemDetailList().forEach(transactionItemDetail -> {
                transactionItemDetail.setOrderStatusUpdateDate("2018-01-12");
            });
        });

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.BUSINESS_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorType(),
                equalTo(ErrorType.BUSINESS_DATE_ERROR.getErrorType()));

        assertThat(argument.getValue().getKeyInfo3(), equalTo("3"));

        assertThat(argument.getValue().getErrorItemId1(),
                equalTo(ITEM_ID_CONSISTENCY_CHECK_BUSINESS_DATE));

        assertThat(argument.getValue().getErrorItemValue1(), equalTo("20180110"));

        assertThat(argument.getValue().getErrorItemId2(),
                equalTo(ITEM_ID_ORDER_STATUS_UPDATE_DATE));

        assertThat(argument.getValue().getErrorItemValue2(), equalTo("20180112"));
    }

    @Test
    public void testCheckFutureLevel4TransactionError() {
        salesTransactionErrorId = "testCheckFutureLevel4TransactionError";

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getTransactionItemDetailList().forEach(transactionItemDetail -> {
                transactionItemDetail.getNonItemDetailListByItem().forEach(nonItemDetail -> {
                    nonItemDetail.setOrderStatusUpdateDate("2018-01-12");
                });
            });
        });

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.BUSINESS_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorType(),
                equalTo(ErrorType.BUSINESS_DATE_ERROR.getErrorType()));

        assertThat(argument.getValue().getKeyInfo4(), equalTo("4"));

        assertThat(argument.getValue().getErrorItemId1(),
                equalTo(ITEM_ID_CONSISTENCY_CHECK_BUSINESS_DATE));

        assertThat(argument.getValue().getErrorItemValue1(), equalTo("20180110"));

        assertThat(argument.getValue().getErrorItemId2(),
                equalTo(ITEM_ID_ORDER_STATUS_UPDATE_DATE));

        assertThat(argument.getValue().getErrorItemValue2(), equalTo("20180112"));
    }

    @Test
    public void testCheckFutureLevel3TransactionError2() {
        salesTransactionErrorId = "testCheckFutureLevel3TransactionError2";

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getNonItemDetailList().forEach(nonItemDetail -> {
                nonItemDetail.setOrderStatusUpdateDate("2018-01-12");
            });
        });

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.BUSINESS_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorType(),
                equalTo(ErrorType.BUSINESS_DATE_ERROR.getErrorType()));

        assertThat(argument.getValue().getKeyInfo3(), equalTo("3"));

        assertThat(argument.getValue().getErrorItemId1(),
                equalTo(ITEM_ID_CONSISTENCY_CHECK_BUSINESS_DATE));

        assertThat(argument.getValue().getErrorItemValue1(), equalTo("20180110"));

        assertThat(argument.getValue().getErrorItemId2(),
                equalTo(ITEM_ID_ORDER_STATUS_UPDATE_DATE));

        assertThat(argument.getValue().getErrorItemValue2(), equalTo("20180112"));
    }

    @Test
    public void testCheckProcessCloseDateLevel2TransactionError() {
        salesTransactionErrorId = "testCheckProcessCloseDateLevel2TransactionError";

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getProcessMStoreControl());

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.setOrderStatusLastUpdateDateTime(
                    OffsetDateTime.of(2017, 12, 29, 8, 0, 0, 0, ZoneOffset.of("Z")));
            transaction.setOrderStatusUpdateDate("2017-12-29");
        });

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.BUSINESS_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorType(),
                equalTo(ErrorType.CLOSE_PROCESS_COMPLETED_DATE_ERROR.getErrorType()));

        assertThat(argument.getValue().getKeyInfo2(), equalTo(SALES_TRANSACTION_ID));

        assertThat(argument.getValue().getErrorItemId1(),
                equalTo(ITEM_ID_CONSISTENCY_CHECK_BUSINESS_END_OF_DATE));

        assertThat(argument.getValue().getErrorItemValue1(), equalTo("20171230"));

        assertThat(argument.getValue().getErrorItemId2(),
                equalTo(ITEM_ID_ORDER_STATUS_UPDATE_DATE));

        assertThat(argument.getValue().getErrorItemValue2(), equalTo("20171229"));
    }

    @Test
    public void testCheckProcessCloseDateLevel3TransactionError() {
        salesTransactionErrorId = "testCheckProcessCloseDateLevel3TransactionError";

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getProcessMStoreControl());

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getTransactionItemDetailList().forEach(transactionItemDetail -> {
                transactionItemDetail.setOrderStatusLastUpdateDateTime(
                        OffsetDateTime.of(2017, 12, 29, 8, 0, 0, 0, ZoneOffset.of("Z")));
                transactionItemDetail.setOrderStatusUpdateDate("2017-12-29");
            });
        });

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.BUSINESS_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorType(),
                equalTo(ErrorType.CLOSE_PROCESS_COMPLETED_DATE_ERROR.getErrorType()));

        assertThat(argument.getValue().getKeyInfo3(), equalTo("3"));

        assertThat(argument.getValue().getErrorItemId1(),
                equalTo(ITEM_ID_CONSISTENCY_CHECK_BUSINESS_END_OF_DATE));

        assertThat(argument.getValue().getErrorItemValue1(), equalTo("20171230"));

        assertThat(argument.getValue().getErrorItemId2(),
                equalTo(ITEM_ID_ORDER_STATUS_UPDATE_DATE));

        assertThat(argument.getValue().getErrorItemValue2(), equalTo("20171229"));
    }

    @Test
    public void testCheckProcessCloseDateLevel4TransactionError() {
        salesTransactionErrorId = "testCheckProcessCloseDateLevel4TransactionError";

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getProcessMStoreControl());

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getTransactionItemDetailList().forEach(transactionItemDetail -> {
                transactionItemDetail.getNonItemDetailListByItem().forEach(nonItemDetail -> {
                    nonItemDetail.setOrderStatusLastUpdateDateTime(
                            OffsetDateTime.of(2017, 12, 29, 8, 0, 0, 0, ZoneOffset.of("Z")));
                    nonItemDetail.setOrderStatusUpdateDate("2017-12-29");
                });
            });
        });

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.BUSINESS_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorType(),
                equalTo(ErrorType.CLOSE_PROCESS_COMPLETED_DATE_ERROR.getErrorType()));

        assertThat(argument.getValue().getKeyInfo4(), equalTo("4"));

        assertThat(argument.getValue().getErrorItemId1(),
                equalTo(ITEM_ID_CONSISTENCY_CHECK_BUSINESS_END_OF_DATE));

        assertThat(argument.getValue().getErrorItemValue1(), equalTo("20171230"));

        assertThat(argument.getValue().getErrorItemId2(),
                equalTo(ITEM_ID_ORDER_STATUS_UPDATE_DATE));

        assertThat(argument.getValue().getErrorItemValue2(), equalTo("20171229"));
    }

    @Test
    public void testCheckProcessCloseDateLevel3TransactionError2() {
        salesTransactionErrorId = "testCheckProcessCloseDateLevel3TransactionError2";

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getProcessMStoreControl());

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getNonItemDetailList().forEach(nonItemDetail -> {
                nonItemDetail.setOrderStatusLastUpdateDateTime(
                        OffsetDateTime.of(2017, 12, 29, 8, 0, 0, 0, ZoneOffset.of("Z")));
                nonItemDetail.setOrderStatusUpdateDate("2017-12-29");
            });
        });

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.BUSINESS_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorType(),
                equalTo(ErrorType.CLOSE_PROCESS_COMPLETED_DATE_ERROR.getErrorType()));

        assertThat(argument.getValue().getKeyInfo3(), equalTo("3"));

        assertThat(argument.getValue().getErrorItemId1(),
                equalTo(ITEM_ID_CONSISTENCY_CHECK_BUSINESS_END_OF_DATE));

        assertThat(argument.getValue().getErrorItemValue1(), equalTo("20171230"));

        assertThat(argument.getValue().getErrorItemId2(),
                equalTo(ITEM_ID_ORDER_STATUS_UPDATE_DATE));

        assertThat(argument.getValue().getErrorItemValue2(), equalTo("20171229"));
    }

    @Test
    public void testCheckStoreOpenDateLevel1StoreOpenDateError() {
        salesTransactionErrorId = "testCheckStoreOpenDateLevel1StoreOpenDateError";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeEntityMapper.selectByPrimaryKey(anyString())).thenReturn(null);

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.BUSINESS_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorType(),
                equalTo(ErrorType.BUSINESS_ERROR.getErrorType()));

        assertThat(argument.getValue().getErrorItemValue1(), equalTo(DBItem.TABLE_NAME_M_STORE));

    }

    @Test
    public void testCheckStoreOpenDateLevel1StoreOpenDateNullError() {
        salesTransactionErrorId = "testCheckStoreOpenDateLevel1StoreOpenDateNullError";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeEntityMapper.selectByPrimaryKey(anyString())).thenReturn(getMStoreEntityEmpty());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.BUSINESS_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorType(),
                equalTo(ErrorType.BUSINESS_ERROR.getErrorType()));

        assertThat(argument.getValue().getErrorItemValue1(), equalTo(DBItem.TABLE_NAME_M_STORE));

    }

    @Test
    public void testCheckStoreOpenDateLevel2StoreOpenDateError() {
        salesTransactionErrorId = "testCheckStoreOpenDateLevel2StoreOpenDateError";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeEntityMapper.selectByPrimaryKey("1")).thenReturn(getMStore());

        when(storeEntityMapper.selectByPrimaryKey(TRANSACTION_STORE_CODE)).thenReturn(null);

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.BUSINESS_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorType(),
                equalTo(ErrorType.BUSINESS_ERROR.getErrorType()));

        assertThat(argument.getValue().getKeyInfo2(), equalTo(SALES_TRANSACTION_ID));

        assertThat(argument.getValue().getErrorItemValue1(), equalTo(DBItem.TABLE_NAME_M_STORE));

    }

    @Test
    public void testCheckStoreOpenDateLevel1Error() {
        salesTransactionErrorId = "testCheckStoreOpenDateLevel1Error";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeEntityMapper.selectByPrimaryKey(anyString())).thenReturn(getMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        transactionImportData.setOrderConfirmationBusinessDate("2017-12-29");
        transactionImportData.setOrderConfirmationDateTime(
                OffsetDateTime.of(2017, 12, 29, 8, 0, 0, 0, ZoneOffset.of("Z")));

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.BUSINESS_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorType(),
                equalTo(ErrorType.BEFORE_OPEN_DATE_ERROR.getErrorType()));

        assertThat(argument.getValue().getErrorItemId1(),
                equalTo(ITEM_ID_CONSISTENCY_CHECK_BEFORE_STORE_OPEN_DATE));

        assertThat(argument.getValue().getErrorItemValue1(), equalTo("20180103"));

        assertThat(argument.getValue().getErrorItemId2(),
                equalTo(ITEM_ID_ORDER_CONFIRMATION_BUSINESS_DATE));

        assertThat(argument.getValue().getErrorItemValue2(), equalTo("20171229"));

    }

    @Test
    public void testCheckStoreOpenDateLevel2Error() {
        salesTransactionErrorId = "testCheckStoreOpenDateLevel2Error";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeEntityMapper.selectByPrimaryKey(anyString())).thenReturn(getMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.setDataCreationBusinessDate("2017-12-29");
            transaction.setDataCreationDateTime(
                    OffsetDateTime.of(2017, 12, 29, 8, 0, 0, 0, ZoneOffset.of("Z")));
        });

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.BUSINESS_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorType(),
                equalTo(ErrorType.BEFORE_OPEN_DATE_ERROR.getErrorType()));

        assertThat(argument.getValue().getKeyInfo2(), equalTo(SALES_TRANSACTION_ID));

        assertThat(argument.getValue().getErrorItemId1(),
                equalTo(ITEM_ID_CONSISTENCY_CHECK_BEFORE_STORE_OPEN_DATE));

        assertThat(argument.getValue().getErrorItemValue1(), equalTo("20180103"));

        assertThat(argument.getValue().getErrorItemId2(),
                equalTo(ITEM_ID_DATA_CREATION_BUSINESS_DATE));

        assertThat(argument.getValue().getErrorItemValue2(), equalTo("20171229"));

    }

    @Test
    public void testCheckStoreOpenDateLevel2Error2() {
        salesTransactionErrorId = "testCheckStoreOpenDateLevel2Error2";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeEntityMapper.selectByPrimaryKey(anyString())).thenReturn(getMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.setOrderStatusUpdateDate("2017-12-29");
            transaction.setOrderStatusLastUpdateDateTime(
                    OffsetDateTime.of(2017, 12, 29, 8, 0, 0, 0, ZoneOffset.of("Z")));
        });

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.BUSINESS_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorType(),
                equalTo(ErrorType.BEFORE_OPEN_DATE_ERROR.getErrorType()));

        assertThat(argument.getValue().getKeyInfo2(), equalTo(SALES_TRANSACTION_ID));

        assertThat(argument.getValue().getKeyInfo3(), equalTo(null));

        assertThat(argument.getValue().getErrorItemId1(),
                equalTo(ITEM_ID_CONSISTENCY_CHECK_BEFORE_STORE_OPEN_DATE));

        assertThat(argument.getValue().getErrorItemValue1(), equalTo("20180103"));

        assertThat(argument.getValue().getErrorItemId2(),
                equalTo(ITEM_ID_ORDER_STATUS_UPDATE_DATE));

        assertThat(argument.getValue().getErrorItemValue2(), equalTo("20171229"));

    }

    @Test
    public void testCheckStoreOpenDateLevel3Error() {
        salesTransactionErrorId = "testCheckStoreOpenDateLevel3Error";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeEntityMapper.selectByPrimaryKey(anyString())).thenReturn(getMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getTransactionItemDetailList().forEach(transactionItemDetail -> {
                transactionItemDetail.setOrderStatusUpdateDate("2017-12-29");
                transactionItemDetail.setOrderStatusLastUpdateDateTime(
                        OffsetDateTime.of(2017, 12, 29, 8, 0, 0, 0, ZoneOffset.of("Z")));
            });
        });

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.BUSINESS_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorType(),
                equalTo(ErrorType.BEFORE_OPEN_DATE_ERROR.getErrorType()));

        assertThat(argument.getValue().getKeyInfo3(), equalTo("3"));

        assertThat(argument.getValue().getKeyInfo4(), equalTo(null));

        assertThat(argument.getValue().getErrorItemId1(),
                equalTo(ITEM_ID_CONSISTENCY_CHECK_BEFORE_STORE_OPEN_DATE));

        assertThat(argument.getValue().getErrorItemValue1(), equalTo("20180103"));

        assertThat(argument.getValue().getErrorItemId2(),
                equalTo(ITEM_ID_ORDER_STATUS_UPDATE_DATE));

        assertThat(argument.getValue().getErrorItemValue2(), equalTo("20171229"));

    }

    @Test
    public void testCheckStoreOpenDateLevel3Error2() {
        salesTransactionErrorId = "testCheckStoreOpenDateLevel3Error2";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeEntityMapper.selectByPrimaryKey(anyString())).thenReturn(getMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getNonItemDetailList().forEach(nonItemDetail -> {
                nonItemDetail.setOrderStatusUpdateDate("2017-12-29");
                nonItemDetail.setOrderStatusLastUpdateDateTime(
                        OffsetDateTime.of(2017, 12, 29, 8, 0, 0, 0, ZoneOffset.of("Z")));
            });
        });

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.BUSINESS_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorType(),
                equalTo(ErrorType.BEFORE_OPEN_DATE_ERROR.getErrorType()));

        assertThat(argument.getValue().getKeyInfo3(), equalTo("3"));

        assertThat(argument.getValue().getKeyInfo4(), equalTo(null));

        assertThat(argument.getValue().getErrorItemId1(),
                equalTo(ITEM_ID_CONSISTENCY_CHECK_BEFORE_STORE_OPEN_DATE));

        assertThat(argument.getValue().getErrorItemValue1(), equalTo("20180103"));

        assertThat(argument.getValue().getErrorItemId2(),
                equalTo(ITEM_ID_ORDER_STATUS_UPDATE_DATE));

        assertThat(argument.getValue().getErrorItemValue2(), equalTo("20171229"));

    }

    @Test
    public void testCheckStoreOpenDateLevel4Error() {
        salesTransactionErrorId = "testCheckStoreOpenDateLevel4Error";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeEntityMapper.selectByPrimaryKey(anyString())).thenReturn(getMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getTransactionItemDetailList().forEach(transactionItemDetail -> {
                transactionItemDetail.getNonItemDetailListByItem().forEach(nonItemDetail -> {
                    nonItemDetail.setOrderStatusUpdateDate("2017-12-29");
                    nonItemDetail.setOrderStatusLastUpdateDateTime(
                            OffsetDateTime.of(2017, 12, 29, 8, 0, 0, 0, ZoneOffset.of("Z")));
                });
            });
        });

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.BUSINESS_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorType(),
                equalTo(ErrorType.BEFORE_OPEN_DATE_ERROR.getErrorType()));

        assertThat(argument.getValue().getKeyInfo4(), equalTo("4"));

        assertThat(argument.getValue().getKeyInfo5(), equalTo(null));

        assertThat(argument.getValue().getErrorItemId1(),
                equalTo(ITEM_ID_CONSISTENCY_CHECK_BEFORE_STORE_OPEN_DATE));

        assertThat(argument.getValue().getErrorItemValue1(), equalTo("20180103"));

        assertThat(argument.getValue().getErrorItemId2(),
                equalTo(ITEM_ID_ORDER_STATUS_UPDATE_DATE));

        assertThat(argument.getValue().getErrorItemValue2(), equalTo("20171229"));

    }

    @Test
    public void testCheckStoreCloseDateLevel1StoreOpenDateError() {
        salesTransactionErrorId = "testCheckStoreCloseDateLevel1StoreOpenDateError";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getClosedDateEmptyMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.NORMAL);

    }

    @Test
    public void testCheckStoreCloseDateLevel2StoreCloseDateError() {
        salesTransactionErrorId = "testCheckStoreCloseDateLevel2StoreCloseDateError";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeEntityMapper.selectByPrimaryKey(TRANSACTION_STORE_CODE))
                .thenReturn(getClosedDateEmptyMStore());

        when(storeEntityMapper.selectByPrimaryKey(IMPORT_DATA_STORE_CODE)).thenReturn(getMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.NORMAL);

    }

    @Test
    public void testCheckStoreCloseDateLevel1Error() {
        salesTransactionErrorId = "testCheckStoreCloseDateLevel1Error";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getCloseDateCheckMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        transactionImportData.setOrderConfirmationBusinessDate("2018-01-08");
        transactionImportData.setOrderConfirmationDateTime(
                OffsetDateTime.of(2018, 1, 8, 8, 0, 0, 0, ZoneOffset.of("Z")));

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.BUSINESS_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorType(),
                equalTo(ErrorType.AFTER_CLOSE_DATE_ERROR.getErrorType()));

        assertThat(argument.getValue().getErrorItemId1(),
                equalTo(ITEM_ID_CONSISTENCY_CHECK_AFTER_STORE_CLOSE_DATE));

        assertThat(argument.getValue().getErrorItemValue1(), equalTo("20180104"));

        assertThat(argument.getValue().getErrorItemId2(),
                equalTo(ITEM_ID_ORDER_CONFIRMATION_BUSINESS_DATE));

        assertThat(argument.getValue().getErrorItemValue2(), equalTo("20180108"));

    }

    @Test
    public void testCheckStoreCloseDateLevel2Error() {
        salesTransactionErrorId = "testCheckStoreCloseDateLevel2Error";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getCloseDateCheckMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.setOrderStatusUpdateDate("2018-01-08");
            transaction.setOrderStatusLastUpdateDateTime(
                    OffsetDateTime.of(2018, 1, 8, 8, 0, 0, 0, ZoneOffset.of("Z")));
        });

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.BUSINESS_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorType(),
                equalTo(ErrorType.AFTER_CLOSE_DATE_ERROR.getErrorType()));

        assertThat(argument.getValue().getKeyInfo2(), equalTo(SALES_TRANSACTION_ID));

        assertThat(argument.getValue().getKeyInfo3(), equalTo(null));

        assertThat(argument.getValue().getErrorItemId1(),
                equalTo(ITEM_ID_CONSISTENCY_CHECK_AFTER_STORE_CLOSE_DATE));

        assertThat(argument.getValue().getErrorItemValue1(), equalTo("20180104"));

        assertThat(argument.getValue().getErrorItemId2(),
                equalTo(ITEM_ID_ORDER_STATUS_UPDATE_DATE));

        assertThat(argument.getValue().getErrorItemValue2(), equalTo("20180108"));

    }

    @Test
    public void testCheckStoreCloseDateLevel3Error() {
        salesTransactionErrorId = "testCheckStoreCloseDateLevel3Error";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getCloseDateCheckMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getTransactionItemDetailList().forEach(transactionItemDetail -> {
                transactionItemDetail.setOrderStatusUpdateDate("2018-01-08");
                transactionItemDetail.setOrderStatusLastUpdateDateTime(
                        OffsetDateTime.of(2018, 1, 8, 8, 0, 0, 0, ZoneOffset.of("Z")));
            });

        });

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.BUSINESS_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorType(),
                equalTo(ErrorType.AFTER_CLOSE_DATE_ERROR.getErrorType()));

        assertThat(argument.getValue().getKeyInfo3(), equalTo("3"));

        assertThat(argument.getValue().getKeyInfo4(), equalTo(null));

        assertThat(argument.getValue().getErrorItemId1(),
                equalTo(ITEM_ID_CONSISTENCY_CHECK_AFTER_STORE_CLOSE_DATE));

        assertThat(argument.getValue().getErrorItemValue1(), equalTo("20180104"));

        assertThat(argument.getValue().getErrorItemId2(),
                equalTo(ITEM_ID_ORDER_STATUS_UPDATE_DATE));

        assertThat(argument.getValue().getErrorItemValue2(), equalTo("20180108"));

    }

    @Test
    public void testCheckStoreCloseDateLevel3Error2() {
        salesTransactionErrorId = "testCheckStoreCloseDateLevel3Error2";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getCloseDateCheckMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getNonItemDetailList().forEach(nonItemDetail -> {
                nonItemDetail.setOrderStatusUpdateDate("2018-01-08");
                nonItemDetail.setOrderStatusLastUpdateDateTime(
                        OffsetDateTime.of(2018, 1, 8, 8, 0, 0, 0, ZoneOffset.of("Z")));
            });

        });

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.BUSINESS_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorType(),
                equalTo(ErrorType.AFTER_CLOSE_DATE_ERROR.getErrorType()));

        assertThat(argument.getValue().getKeyInfo3(), equalTo("3"));

        assertThat(argument.getValue().getKeyInfo4(), equalTo(null));

        assertThat(argument.getValue().getErrorItemId1(),
                equalTo(ITEM_ID_CONSISTENCY_CHECK_AFTER_STORE_CLOSE_DATE));

        assertThat(argument.getValue().getErrorItemValue1(), equalTo("20180104"));

        assertThat(argument.getValue().getErrorItemId2(),
                equalTo(ITEM_ID_ORDER_STATUS_UPDATE_DATE));

        assertThat(argument.getValue().getErrorItemValue2(), equalTo("20180108"));

    }

    @Test
    public void testCheckStoreCloseDateLevel4Error() {
        salesTransactionErrorId = "testCheckStoreCloseDateLevel4Error";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getCloseDateCheckMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getTransactionItemDetailList().forEach(transactionItemDetail -> {
                transactionItemDetail.getNonItemDetailListByItem().forEach(nonItemDetail -> {
                    nonItemDetail.setOrderStatusUpdateDate("2018-01-08");
                    nonItemDetail.setOrderStatusLastUpdateDateTime(
                            OffsetDateTime.of(2018, 1, 8, 8, 0, 0, 0, ZoneOffset.of("Z")));
                });
            });

        });

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.BUSINESS_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorType(),
                equalTo(ErrorType.AFTER_CLOSE_DATE_ERROR.getErrorType()));

        assertThat(argument.getValue().getKeyInfo4(), equalTo("4"));

        assertThat(argument.getValue().getKeyInfo5(), equalTo(null));

        assertThat(argument.getValue().getErrorItemId1(),
                equalTo(ITEM_ID_CONSISTENCY_CHECK_AFTER_STORE_CLOSE_DATE));

        assertThat(argument.getValue().getErrorItemValue1(), equalTo("20180104"));

        assertThat(argument.getValue().getErrorItemId2(),
                equalTo(ITEM_ID_ORDER_STATUS_UPDATE_DATE));

        assertThat(argument.getValue().getErrorItemValue2(), equalTo("20180108"));

    }

    @Test
    public void testCheckTenderMasterDataExistError() {
        salesTransactionErrorId = "testCheckTenderMasterDataExistError";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(translationTenderMasterMapper.countByCondition(anyObject())).thenReturn(0L);

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeEntityMapper.selectByPrimaryKey(anyString())).thenReturn(getMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.BUSINESS_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorType(),
                equalTo(ErrorType.TENDER_ERROR.getErrorType()));

        assertThat(argument.getValue().getKeyInfo3(), equalTo("3"));

        assertThat(argument.getValue().getKeyInfo4(), equalTo(null));

        assertThat(argument.getValue().getErrorItemId1(),
                equalTo(ITEM_ID_CONSISTENCY_CHECK_TENDER_CODE));

        assertThat(argument.getValue().getErrorItemValue1(), equalTo("1"));
    }

    @Test
    public void testCheckBalanceDetailVat() {
        salesTransactionErrorId = "testCheckTenderMasterDataExistError";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeEntityMapper.selectByPrimaryKey(anyString())).thenReturn(getMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getTransactionItemDetailList().forEach(transactionItemDetail -> {
                transactionItemDetail.getItemDiscountList().forEach(itemDiscount -> {
                    itemDiscount.setItemDiscountQty(0);
                });
            });
        });

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.BUSINESS_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorType(),
                equalTo(ErrorType.AMOUNT_BALANCE_ERROR.getErrorType()));

        assertThat(argument.getValue().getErrorItemId1(),
                equalTo(ITEM_ID_BALANCE_CHECK_TOTAL_AMOUNT));

        assertThat(argument.getValue().getErrorItemValue1(), equalTo("3"));

        assertThat(argument.getValue().getErrorItemId2(),
                equalTo(ITEM_ID_BALANCE_CHECK_TOTAL_DISCOUNT_AMOUNT));

        assertThat(argument.getValue().getErrorItemValue2(), equalTo("1"));

        assertThat(argument.getValue().getErrorItemId4(),
                equalTo(ITEM_ID_BALANCE_CHECK_TOTAL_PAYMENT_AMOUNT));

        assertThat(argument.getValue().getErrorItemValue4(), equalTo("1"));
    }

    @Test
    public void testCheckBalanceDetailNotVat() {
        salesTransactionErrorId = "testCheckBalanceDetailNotVat";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getBalanceDetailCheckCodeValue);

        when(storeEntityMapper.selectByPrimaryKey(anyString())).thenReturn(getMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getTransactionItemDetailList().forEach(transactionItemDetail -> {
                transactionItemDetail.getItemDiscountList().forEach(itemDiscount -> {
                    itemDiscount.setItemDiscountQty(3);
                });
            });
        });

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.BUSINESS_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorType(),
                equalTo(ErrorType.AMOUNT_BALANCE_ERROR.getErrorType()));

        assertThat(argument.getValue().getErrorItemId1(),
                equalTo(ITEM_ID_BALANCE_CHECK_TOTAL_AMOUNT));

        assertThat(argument.getValue().getErrorItemValue1(), equalTo("3"));

        assertThat(argument.getValue().getErrorItemId2(),
                equalTo(ITEM_ID_BALANCE_CHECK_TOTAL_DISCOUNT_AMOUNT));

        assertThat(argument.getValue().getErrorItemValue2(), equalTo("4"));

        assertThat(argument.getValue().getErrorItemId4(),
                equalTo(ITEM_ID_BALANCE_CHECK_TOTAL_PAYMENT_AMOUNT));

        assertThat(argument.getValue().getErrorItemValue4(), equalTo("1"));
    }

    @Test
    public void testCheckBalanceDetailSalesLinkageTypeOn() {
        salesTransactionErrorId = "testCheckBalanceDetailSalesLinkageTypeOn";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getBalanceDetailCheckCodeValue);

        when(storeEntityMapper.selectByPrimaryKey(anyString())).thenReturn(getMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.setSalesLinkageType(1);
            transaction.getTransactionItemDetailList().forEach(transactionItemDetail -> {
                transactionItemDetail.getItemDiscountList().forEach(itemDiscount -> {
                    itemDiscount.setItemDiscountQty(3);
                });
            });
        });

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.NORMAL);
    }

    @Test
    public void testBlackBoxPassAllCheck() {
        salesTransactionErrorId = "testBlackBoxPassAllCheck";

        setBlackBoxTransactionImportData(transactionImportData);

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeEntityMapper.selectByPrimaryKey(anyString())).thenReturn(getMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.NORMAL);
    }

    @Test
    public void testSalesTransactionTaxDetailSignNAddition() {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getSalesTransactionTaxDetailList().forEach(tax -> {
                tax.setTaxAmountSign("N");
            });
        });
        salesTransactionErrorId = "testPassAllCheck";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeEntityMapper.selectByPrimaryKey(anyString())).thenReturn(getMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.NORMAL);
    }

    @Test
    public void testSalesTransactionTaxDetailSignOtherAddition() {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getSalesTransactionTaxDetailList().forEach(tax -> {
                tax.setTaxAmountSign("A");
            });
        });
        salesTransactionErrorId = "testPassAllCheck";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeEntityMapper.selectByPrimaryKey(anyString())).thenReturn(getMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.VALIDATION_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorType(),
                equalTo(ErrorType.VALIDATION_ERROR.getErrorType()));

        assertThat(argument.getValue().getErrorItemId1(), equalTo("tax_amount_sign"));

        assertThat(argument.getValue().getErrorItemValue1(), equalTo("A"));
    }

    @Test
    public void testSalesTransactionTenderSignOtherAddition() {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getSalesTransactionTenderList().forEach(tender -> {
                tender.setPaymentSign("A");
            });
        });
        salesTransactionErrorId = "testPassAllCheck";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeEntityMapper.selectByPrimaryKey(anyString())).thenReturn(getMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.VALIDATION_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorType(),
                equalTo(ErrorType.VALIDATION_ERROR.getErrorType()));

        assertThat(argument.getValue().getErrorItemId1(), equalTo("payment_sign"));

        assertThat(argument.getValue().getErrorItemValue1(), equalTo("A"));
    }

    @Test
    public void testNonitemdetaillistQuantityCodeOtherAddition() {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getNonItemDetailList().forEach(nonItem -> {
                nonItem.setQuantityCode("A");
            });
        });
        salesTransactionErrorId = "testNonitemdetaillistQuantityCodeOtherAddition";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeEntityMapper.selectByPrimaryKey(anyString())).thenReturn(getMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.VALIDATION_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorType(),
                equalTo(ErrorType.VALIDATION_ERROR.getErrorType()));

        assertThat(argument.getValue().getErrorItemId1(), equalTo("quantity_code"));

        assertThat(argument.getValue().getErrorItemValue1(), equalTo("A"));
    }

    @Test
    public void testNonitemdetailDiscountListQuantityCodeOtherAddition() {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getNonItemDetailList().forEach(nonItem -> {
                nonItem.getNonItemDiscountDetailList().forEach(discount -> {
                    discount.setNonItemQuantityCode("A");
                });
            });
        });
        salesTransactionErrorId = "testNonitemdetaillistQuantityCodeOtherAddition";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeEntityMapper.selectByPrimaryKey(anyString())).thenReturn(getMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.VALIDATION_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorType(),
                equalTo(ErrorType.VALIDATION_ERROR.getErrorType()));

        assertThat(argument.getValue().getErrorItemId1(), equalTo("non_item_quantity_code"));

        assertThat(argument.getValue().getErrorItemValue1(), equalTo("A"));
    }

    @Test
    public void testNonitemdetailTaxListQuantityCodeOtherAddition() {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getNonItemDetailList().forEach(nonItem -> {
                nonItem.getNonItemTaxDetailList().forEach(tax -> {
                    tax.setNonItemTaxAmountSign("A");
                });
            });
        });
        salesTransactionErrorId = "testNonitemdetaillistQuantityCodeOtherAddition";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeEntityMapper.selectByPrimaryKey(anyString())).thenReturn(getMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.VALIDATION_ERROR);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);

        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());

        assertThat(argument.getValue().getErrorType(),
                equalTo(ErrorType.VALIDATION_ERROR.getErrorType()));

        assertThat(argument.getValue().getErrorItemId1(), equalTo("non_item_tax_amount_sign"));

        assertThat(argument.getValue().getErrorItemValue1(), equalTo("A"));
    }

    @Test
    public void testPassNullTaxList() {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.setSalesTransactionTaxDetailList(null);
            transaction.getTransactionItemDetailList().forEach(item -> {
                item.setItemTaxDetailList(null);
                item.getNonItemDetailListByItem().forEach(non -> {
                    non.setNonItemTaxDetailList(null);
                });
            });
            transaction.getNonItemDetailList().forEach(non -> {
                non.setNonItemTaxDetailList(null);
            });
        });
        salesTransactionErrorId = "testPassAllCheck";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeEntityMapper.selectByPrimaryKey(anyString())).thenReturn(getMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.NORMAL);
    }

    @Test
    public void testPassNullTotalAmount() {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getSalesTransactionTotalList().forEach(total -> {
                total.setTotalAmountTaxExcluded(null);
                total.setTotalAmountTaxIncluded(null);
            });
        });
        salesTransactionErrorId = "testPassAllCheck";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeEntityMapper.selectByPrimaryKey(anyString())).thenReturn(getMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.NORMAL);
    }

    @Test
    public void testStoreCodeNull() {
        transactionImportData.setUpdateType(UpdateType.INSERT.getUpdateType());
        transactionImportData.setStoreCode(null);
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.setOrderSubstatus(null);
        });
        salesTransactionErrorId = "testNullStoreCode";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeEntityMapper.selectByPrimaryKey(anyString())).thenReturn(getMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.VALIDATION_ERROR);
    }

    @Test
    public void testPvoidAllPass() {
        transactionImportData.setUpdateType(null);
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.setOrderSubstatus(null);
            transaction.setTransactionType(TransactionType.PVOID.getTransactionType());
        });
        salesTransactionErrorId = "testPassAllCheck";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeEntityMapper.selectByPrimaryKey(anyString())).thenReturn(getMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.NORMAL);
    }

    @Test
    public void testReturnAllPass() {
        transactionImportData.setUpdateType(null);
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.setOrderSubstatus(null);
            transaction.setTransactionType(TransactionType.RETURN.getTransactionType());
        });
        salesTransactionErrorId = "testPassAllCheck";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeEntityMapper.selectByPrimaryKey(anyString())).thenReturn(getMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.NORMAL);
    }

    @Test
    public void testFirstTransactionNotExisted() {
        transactionImportData.setUpdateType(null);
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.setOrderSubstatus(null);
            transaction.setTransactionType("other");
        });
        salesTransactionErrorId = "transactionTypeOther";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeEntityMapper.selectByPrimaryKey(anyString())).thenReturn(getMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.VALIDATION_ERROR);
    }

    @Test
    public void testInitialPosImportDataFailed() {
        transactionImportData.setUpdateType(UpdateType.INSERT.getUpdateType());
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.setOrderSubstatus(null);
        });
        salesTransactionErrorId = "businessError";

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeEntityMapper.selectByPrimaryKey(anyString())).thenReturn(getMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        when(initialHelper.initialPosImportData(any(), any(), any())).thenReturn(false);

        when(initialHelper.getInitialStoreCode(any())).thenReturn("001");

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        assertThat(result).isEqualTo(TransactionCheckResultType.BUSINESS_ERROR);
    }

    @Test
    public void testTranslationTenderMasterNotExisted() {
        transactionImportData.setUpdateType(null);
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.setOrderSubstatus(null);
            transaction.getNonItemDetailList().forEach(non -> {
                non.setNonItemSalesAmtTaxIncluded(null);
            });
            transaction.getTransactionItemDetailList().forEach(itemDetail -> {
                itemDetail.setItemSalesAmtTaxExcluded(null);
            });
        });
        salesTransactionErrorId = "testPassAllCheck";
        when(initialHelper.findTranslationTenderMasterFirst(any(), any()))
                .thenReturn(Optional.empty());

        when(payOffDateEntityMapper.countByCondition(anyObject())).thenReturn(1L);

        when(storeControlEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(getMStoreControl());

        when(businessCountryStateSettingEntityMapper.selectByCondition(anyObject()))
                .then(getCodeValue);

        when(storeEntityMapper.selectByPrimaryKey(anyString())).thenReturn(getMStore());

        when(storeGeneralPurposeEntityMapper.selectByCondition(anyObject()))
                .thenReturn(getZoneId());

        when(storeGeneralPurposeEntityMapper.countByCondition(anyObject())).thenReturn(0L);

        when(initialHelper.getInitialStoreCode(any())).thenReturn("001");

        TransactionCheckResultType result = dataCheckerService
                .checkTransactionData(transactionImportData, userId, salesTransactionErrorId);

        ArgumentCaptor<SalesTransactionErrorDetail> argument =
                ArgumentCaptor.forClass(SalesTransactionErrorDetail.class);
        verify(salesTransactionErrorDetailEntityMapper, times(1))
                .insertSelective(argument.capture());
        assertThat(result).isEqualTo(TransactionCheckResultType.BUSINESS_ERROR);
        assertEquals(argument.getValue().getErrorType(), ErrorType.TENDER_ERROR.getErrorType());
        assertEquals(argument.getValue().getErrorItemId1(), "tender_id");
        assertEquals(argument.getValue().getSystemBrandCode(), "1");
        assertEquals(argument.getValue().getSystemBusinessCode(), "0");
        assertEquals(argument.getValue().getSystemCountryCode(), "1");
        assertEquals(argument.getValue().getErrorItemValue1(),
                transactionImportData.getTransactionList()
                        .get(0)
                        .getSalesTransactionTenderList()
                        .get(0)
                        .getTenderId());
    }

    private StoreControlMaster getProcessMStoreControl() {
        StoreControlMaster mstoreControlEntity = new StoreControlMaster();

        mstoreControlEntity.setBusinessDate("20180110");
        mstoreControlEntity.setBusinessEndOfDate("20171230");
        return mstoreControlEntity;
    }

    private StoreMaster getMStore() {
        StoreMaster mstoreEntity = new StoreMaster();
        LocalDateTime storeOpenDate = LocalDateTime.of(2018, 1, 3, 0, 0);
        mstoreEntity.setOpenDate(storeOpenDate);
        LocalDateTime storeCloseDate = LocalDateTime.of(2018, 2, 1, 0, 0);
        mstoreEntity.setCloseDate(storeCloseDate);
        return mstoreEntity;
    }

    private StoreMaster getMStoreEntityEmpty() {
        StoreMaster mstoreEntity = new StoreMaster();
        LocalDateTime storeCloseDate = LocalDateTime.of(2018, 2, 1, 0, 0);
        mstoreEntity.setCloseDate(storeCloseDate);
        return mstoreEntity;
    }

    private StoreMaster getCloseDateCheckMStore() {
        StoreMaster mstoreEntity = new StoreMaster();
        LocalDateTime storeOpenDate = LocalDateTime.of(2018, 1, 3, 0, 0);
        mstoreEntity.setOpenDate(storeOpenDate);
        LocalDateTime storeCloseDate = LocalDateTime.of(2018, 1, 4, 0, 0);
        mstoreEntity.setCloseDate(storeCloseDate);
        return mstoreEntity;
    }

    private StoreMaster getClosedDateEmptyMStore() {
        StoreMaster mstoreEntity = new StoreMaster();
        LocalDateTime storeOpenDate = LocalDateTime.of(2018, 1, 3, 0, 0);
        mstoreEntity.setOpenDate(storeOpenDate);
        return mstoreEntity;
    }

    Function<String, List<BusinessCountryStateSettingMaster>> getList = cv -> {
        List<BusinessCountryStateSettingMaster> bcssEntityList1 = new ArrayList<>();
        bcssEntityList1.add(setCodeValue(cv));
        return bcssEntityList1;
    };

    Answer<?> getCodeValue = invocation -> {
        BusinessCountryStateSettingMasterCondition example =
                (BusinessCountryStateSettingMasterCondition) invocation.getArguments()[0];
        for (Criteria criteria : example.getOredCriteria()) {
            for (Criterion cn : criteria.getAllCriteria()) {
                if (CODE_L2_TAX_TYPE.equals(cn.getValue())) {
                    return getList.apply(RtlogType.VALUE_ADDED_TAX.getRtlogType());
                }
                if (CODE_L2_BUSINESS_END_OF_DAYS.equals(cn.getValue())) {
                    return getList.apply("0");
                }
                if (CODE_L2_BASE_DATE_TIME_INFORMATION.equals(cn.getValue())) {
                    return getList.apply("5");
                }
                if (CODE_L2_OPEN_DAYS.equals(cn.getValue())) {
                    return getList.apply("4");
                }
            }
        }
        return getList.apply("2");
    };

    Answer<?> getCodeValueForStoreOpenSpecificationDaysLevel1 = invocation -> {
        BusinessCountryStateSettingMasterCondition example =
                (BusinessCountryStateSettingMasterCondition) invocation.getArguments()[0];
        boolean level1 = true;
        for (Criteria criteria : example.getOredCriteria()) {
            for (Criterion cn : criteria.getAllCriteria()) {
                if ("2".equals(cn.getValue())) {
                    level1 = false;
                }
            }
        }
        for (Criteria criteria : example.getOredCriteria()) {
            for (Criterion cn : criteria.getAllCriteria()) {
                if (CODE_L2_TAX_TYPE.equals(cn.getValue())) {
                    return getList.apply(RtlogType.VALUE_ADDED_TAX.getRtlogType());
                }
                if (CODE_L2_BUSINESS_END_OF_DAYS.equals(cn.getValue())) {
                    return getList.apply("0");
                }
                if (CODE_L2_BASE_DATE_TIME_INFORMATION.equals(cn.getValue())) {
                    return getList.apply("5");
                }
                if (CODE_L2_OPEN_DAYS.equals(cn.getValue())) {
                    if (level1) {
                        return getList.apply(null);
                    }
                }
            }
        }
        return getList.apply("2");
    };

    Answer<?> getCodeValueForStoreOpenSpecificationDaysLevel2 = invocation -> {
        BusinessCountryStateSettingMasterCondition example =
                (BusinessCountryStateSettingMasterCondition) invocation.getArguments()[0];
        boolean level2 = true;
        for (Criteria criteria : example.getOredCriteria()) {
            for (Criterion cn : criteria.getAllCriteria()) {
                if ("1".equals(cn.getValue())) {
                    level2 = false;
                }
            }
        }
        for (Criteria criteria : example.getOredCriteria()) {
            for (Criterion cn : criteria.getAllCriteria()) {
                if (CODE_L2_TAX_TYPE.equals(cn.getValue())) {
                    return getList.apply(RtlogType.VALUE_ADDED_TAX.getRtlogType());
                }
                if (CODE_L2_BUSINESS_END_OF_DAYS.equals(cn.getValue())) {
                    return getList.apply("0");
                }
                if (CODE_L2_BASE_DATE_TIME_INFORMATION.equals(cn.getValue())) {
                    return getList.apply("5");
                }
                if (CODE_L2_OPEN_DAYS.equals(cn.getValue())) {
                    if (level2) {
                        return getList.apply(null);
                    }
                    return getList.apply("4");
                }
            }
        }
        return getList.apply("2");
    };

    Answer<?> getBalanceDetailCheckCodeValue = invocation -> {
        BusinessCountryStateSettingMasterCondition example =
                (BusinessCountryStateSettingMasterCondition) invocation.getArguments()[0];
        for (Criteria criteria : example.getOredCriteria()) {
            for (Criterion cn : criteria.getAllCriteria()) {
                if (CODE_L2_TAX_TYPE.equals(cn.getValue())) {
                    return getList.apply(RtlogType.SALES_TAX.getRtlogType());
                }
                if (CODE_L2_BUSINESS_END_OF_DAYS.equals(cn.getValue())) {
                    return getList.apply("0");
                }
                if (CODE_L2_BASE_DATE_TIME_INFORMATION.equals(cn.getValue())) {
                    return getList.apply("5");
                }
                if (CODE_L2_OPEN_DAYS.equals(cn.getValue())) {
                    return getList.apply("4");
                }
            }
        }
        return getList.apply("2");
    };

    private StoreControlMaster getMStoreControl() {
        StoreControlMaster mstoreControlEntity = new StoreControlMaster();
        mstoreControlEntity.setBusinessDate("20180110");
        mstoreControlEntity.setBusinessEndOfDate("20180201");
        return mstoreControlEntity;
    }

    private BusinessCountryStateSettingMaster setCodeValue(String codeValue) {
        BusinessCountryStateSettingMaster e = new BusinessCountryStateSettingMaster();
        e.setCodeValue(codeValue);
        return e;
    }

    private void setTransactionImportData(TransactionImportData importData) {
        importData.setChannelCode("1");
        importData.setCustomerId("1");
        importData.setDataAlterationBackboneLinkageType(0);
        importData.setDataAlterationSalesLinkageType(0);
        importData.setDataCorrectionEditingFlag(false);
        importData.setDataCorrectionUserId("1");
        importData.setErrorCheckType(0);
        importData.setIntegratedOrderId(INTEGRATED_ORDER_ID);
        importData.setOrderBarcodeNumber("1");
        importData.setOrderConfirmationBusinessDate("2017-12-31");
        importData.setOrderConfirmationDateTime(
                OffsetDateTime.of(2017, 12, 31, 8, 0, 0, 0, ZoneOffset.of("Z")));
        importData.setOrderConfirmationDateTime(
                OffsetDateTime.of(2017, 12, 31, 8, 0, 0, 0, ZoneOffset.of("Z")));
        importData.setSalesTransactionErrorId("1");
        importData.setStoreCode(IMPORT_DATA_STORE_CODE);
        importData.setSystemBrandCode("1");
        importData.setSystemBusinessCode("0");
        importData.setSystemCountryCode("1");
        List<Transaction> transactionList = new ArrayList<>();
        Transaction transaction = new Transaction();
        setTransaction(transaction);
        transactionList.add(transaction);
        importData.setTransactionList(transactionList);
        importData.setUpdateType("INSERT");
    }

    private void setTransaction(Transaction transaction) {
        transaction.setTransactionSerialNumber(0);
        transaction.setIntegratedOrderId("1");
        transaction.setOrderSubNumber(2);
        transaction.setSalesTransactionId(SALES_TRANSACTION_ID);
        transaction.setTokenCode("1");
        transaction.setTransactionType("SALE");
        transaction.setReturnType(0);
        transaction.setSystemBrandCode("1");
        transaction.setSystemBusinessCode("0");
        transaction.setSystemCountryCode("1");
        transaction.setStoreCode(TRANSACTION_STORE_CODE);
        transaction.setSalesLinkageType(0);
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
        TransactionItemDetail transactionItemDetail = new TransactionItemDetail();
        setTransactionItemDetail(transactionItemDetail);
        transactionItemDetailList.add(transactionItemDetail);
        transaction.setTransactionItemDetailList(transactionItemDetailList);

        List<NonItemDetail> nonItemDetailList = new ArrayList<>();
        NonItemDetail nonItemDetail = new NonItemDetail();
        setNonItemDetailTwo(nonItemDetail);
        nonItemDetailList.add(nonItemDetail);
        transaction.setNonItemDetailList(nonItemDetailList);

        List<SalesTransactionTender> salesTransactionTenderList = new ArrayList<>();
        SalesTransactionTender salesTransactionTender = new SalesTransactionTender();
        setSalesTransactionTender(salesTransactionTender);
        salesTransactionTenderList.add(salesTransactionTender);
        transaction.setSalesTransactionTenderList(salesTransactionTenderList);

        List<SalesTransactionTaxDetail> salesTransactionTaxDetailList = new ArrayList<>();
        SalesTransactionTaxDetail salesTransactionTaxDetail = new SalesTransactionTaxDetail();
        setSalesTransactionTaxDetail(salesTransactionTaxDetail);
        salesTransactionTaxDetailList.add(salesTransactionTaxDetail);
        transaction.setSalesTransactionTaxDetailList(salesTransactionTaxDetailList);

        List<SalesTransactionTotal> salesTransactionTotalList = new ArrayList<>();
        SalesTransactionTotal salesTransactionTotal = new SalesTransactionTotal();
        setSalesTransactionTotal(salesTransactionTotal);
        salesTransactionTotalList.add(salesTransactionTotal);
        transaction.setSalesTransactionTotalList(salesTransactionTotalList);
    }

    private void setTransactionItemDetail(TransactionItemDetail entity) {
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
        entity.setItemMountDiscountType("1");
        entity.setItemDiscountAmount(getPrice());
        entity.setBundleSalesFlag(false);
        entity.setBundleSalesPrice(getPrice());
        entity.setBundleSalesDetailIndex(0);
        entity.setItemDetailNumber(0);
        entity.setItemTaxationType("1");
        entity.setItemTaxKind("1");
        List<NonItemDetail> nonItemDetailList = new ArrayList<>();
        NonItemDetail nonItemDetail = new NonItemDetail();
        setNonItemDetailOne(nonItemDetail);
        nonItemDetailList.add(nonItemDetail);
        entity.setNonItemDetailListByItem(nonItemDetailList);

        List<ItemDiscount> itemDiscountList = new ArrayList<>();
        ItemDiscount itemDiscount = new ItemDiscount();
        setItemDiscount(itemDiscount);
        itemDiscountList.add(itemDiscount);
        entity.setItemDiscountList(itemDiscountList);

        List<ItemTaxDetail> itemTaxDetailList = new ArrayList<>();
        ItemTaxDetail itemTaxDetail = new ItemTaxDetail();
        setItemTaxDetail(itemTaxDetail);
        itemTaxDetailList.add(itemTaxDetail);
        entity.setItemTaxDetailList(itemTaxDetailList);
    }

    private void setNonItemDetailOne(NonItemDetail entity) {
        entity.setNonItemDetailNumber(1);
        entity.setNonMdDetailListSalesTransactionType("SALE");
        entity.setNonItemDetailSalesLinkageType(1);
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
        entity.setItemDetailSubNumber(0);
        entity.setNonItemTaxationType("1");
        entity.setNonItemTaxKind("1");
        NonItemInfo nonItemInfo = new NonItemInfo();
        setNonItemInfoOne(nonItemInfo);
        entity.setNonItemInfo(nonItemInfo);

        List<NonItemDiscountDetail> nonItemDiscountDetailList = new ArrayList<>();
        NonItemDiscountDetail nonItemDiscountDetail = new NonItemDiscountDetail();
        setNonItemDiscountDetailOne(nonItemDiscountDetail);
        nonItemDiscountDetailList.add(nonItemDiscountDetail);
        entity.setNonItemDiscountDetailList(nonItemDiscountDetailList);

        List<NonItemTaxDetail> nonItemTaxDetailList = new ArrayList<>();
        NonItemTaxDetail nonItemTaxDetail = new NonItemTaxDetail();
        setNonItemTaxDetailOne(nonItemTaxDetail);
        nonItemTaxDetailList.add(nonItemTaxDetail);
        entity.setNonItemTaxDetailList(nonItemTaxDetailList);
    }

    private void setNonItemInfoOne(NonItemInfo entity) {
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
    }

    private void setNonItemDiscountDetailOne(NonItemDiscountDetail entity) {
        entity.setNonItemDiscountSubNumber(0);
        entity.setNonItemDiscountDetailSubNumber(0);
        entity.setNonItemPromotionType("1");
        entity.setNonItemStoreDiscountType("1");
        entity.setNonItemQuantityCode("P");
        entity.setNonItemDiscountQty(0);
        entity.setNonItemDiscountAmtTaxExcluded(getPrice());
        entity.setNonItemDiscountAmtTaxIncluded(getPrice());
    }

    private void setNonItemTaxDetailOne(NonItemTaxDetail entity) {
        entity.setNonItemTaxDetailSubNumber(0);
        entity.setNonItemTaxSubNumber(0);
        entity.setNonItemTaxType("1");
        entity.setNonItemTaxName("1");
        entity.setNonItemTaxAmountSign("P");
        entity.setNonItemTaxAmt(getPrice());
        entity.setNonItemTaxRate(new BigDecimal("0"));
    }

    private void setItemDiscount(ItemDiscount entity) {
        entity.setItemDiscountDetailSubNumber(0);
        entity.setItemDiscountSubNumber(0);
        entity.setItemPromotionType("1");
        entity.setItemPromotionNumber("0");
        entity.setItemStoreDiscountType("1");
        entity.setItemQuantityCode("P");
        entity.setItemDiscountQty(1);
        entity.setItemDiscountAmtTaxExcluded(getPrice());
        entity.setItemDiscountAmtTaxIncluded(getPrice());

    }

    private void setItemTaxDetail(ItemTaxDetail entity) {
        entity.setItemTaxDetailSubNumber(1);
        entity.setItemTaxSubNumber(1);
        entity.setItemTaxType("1");
        entity.setItemTaxName("1");
        entity.setItemTaxAmountSign("P");
        entity.setItemTaxAmt(getPrice());
        entity.setItemTaxRate(new BigDecimal("0"));

    }

    private void setNonItemDetailTwo(NonItemDetail entity) {
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

        NonItemInfo nonItemInfo = new NonItemInfo();
        setNonItemInfoTwo(nonItemInfo);
        entity.setNonItemInfo(nonItemInfo);

        List<NonItemDiscountDetail> nonItemDiscountDetailList = new ArrayList<>();
        NonItemDiscountDetail nonItemDiscountDetail = new NonItemDiscountDetail();
        setNonItemDiscountDetailTwo(nonItemDiscountDetail);
        nonItemDiscountDetailList.add(nonItemDiscountDetail);
        entity.setNonItemDiscountDetailList(nonItemDiscountDetailList);

        List<NonItemTaxDetail> nonItemTaxDetailList = new ArrayList<>();
        NonItemTaxDetail nonItemTaxDetail = new NonItemTaxDetail();
        setNonItemTaxDetailTwo(nonItemTaxDetail);
        nonItemTaxDetailList.add(nonItemTaxDetail);
        entity.setNonItemTaxDetailList(nonItemTaxDetailList);

    }

    private void setNonItemInfoTwo(NonItemInfo entity) {
        entity.setDetailSubNumber(5);
        entity.setItemDetailSubNumber(1);
        entity.setKeyCode("1");
        entity.setCodeValue1("1");
        entity.setCodeValue2("1");
        entity.setCodeValue3("1");
        entity.setCodeValue4("1");
        entity.setName1("1");
        entity.setName2("1");

    }

    private void setNonItemDiscountDetailTwo(NonItemDiscountDetail entity) {
        entity.setNonItemDiscountSubNumber(1);
        entity.setNonItemDiscountDetailSubNumber(1);
        entity.setNonItemPromotionType("1");
        entity.setNonItemStoreDiscountType("1");
        entity.setNonItemQuantityCode("P");
        entity.setNonItemDiscountQty(1);
        entity.setNonItemDiscountAmtTaxExcluded(getPrice());
        entity.setNonItemDiscountAmtTaxIncluded(getPrice());

    }

    private void setNonItemTaxDetailTwo(NonItemTaxDetail entity) {
        entity.setNonItemTaxDetailSubNumber(1);
        entity.setNonItemTaxType("1");
        entity.setNonItemTaxName("1");
        entity.setNonItemTaxAmountSign("P");
        entity.setNonItemTaxAmt(getPrice());
        entity.setNonItemTaxRate(new BigDecimal("0"));

    }

    private void setSalesTransactionTender(SalesTransactionTender entity) {
        entity.setTenderSubNumber(3);
        entity.setTenderGroupId("1");
        entity.setTenderId("1");
        entity.setPaymentSign("P");
        entity.setTaxIncludedPaymentAmount(getPrice());
        TenderInfo tenderInfo = new TenderInfo();
        setTenderInfo(tenderInfo);
        entity.setTenderInfoList(tenderInfo);

    }

    private void setTenderInfo(TenderInfo entity) {
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

    }

    private void setSalesTransactionTaxDetail(SalesTransactionTaxDetail entity) {
        entity.setTaxSubNumber(1);
        entity.setTaxGroup("1");
        entity.setTaxAmountSign("P");
        entity.setTaxAmount(getPrice());
        entity.setTaxRate(new BigDecimal("0"));

    }

    private void setSalesTransactionTotal(SalesTransactionTotal entity) {
        entity.setTotalAmountSubNumber(1);
        entity.setTotalType("1");
        entity.setTotalAmountTaxExcluded(getPrice());
        entity.setTotalAmountTaxIncluded(getPrice());
        entity.setConsumptionTaxRate(new BigDecimal("0"));
        entity.setSalesTransactionInformation1("1");
        entity.setSalesTransactionInformation2("1");
        entity.setSalesTransactionInformation3("1");

    }

    private Price getPrice() {
        Price price = new Price();
        price.setCurrencyCode(Currency.getInstance(Locale.JAPAN));
        price.setValue(new BigDecimal("1"));
        return price;
    }

    private Price getPriceByValue(int value) {
        Price price = new Price();
        price.setCurrencyCode(Currency.getInstance(Locale.JAPAN));
        price.setValue(new BigDecimal(value));
        return price;
    }

    private void setBlackBoxItemDiscount(ItemDiscount itemDiscount) {
        itemDiscount.setItemDiscountDetailSubNumber(9999);
        itemDiscount.setItemDiscountSubNumber(9999);
        itemDiscount.setItemPromotionType("0356");
        itemDiscount.setItemPromotionNumber("0066302331");
        itemDiscount.setItemStoreDiscountType("75");
        itemDiscount.setItemQuantityCode("P");
        itemDiscount.setItemDiscountQty(99);
        itemDiscount.setItemDiscountAmtTaxExcluded(getPrice());
        itemDiscount.setItemDiscountAmtTaxIncluded(getPrice());
    }

    private void setBlackBoxItemTaxDetail(ItemTaxDetail itemTaxDetail) {
        itemTaxDetail.setItemTaxDetailSubNumber(9999);
        itemTaxDetail.setItemTaxSubNumber(9999);
        itemTaxDetail.setItemTaxType("7639576039");
        itemTaxDetail.setItemTaxName(
                "563850467356902371294321711474712192475392864019091115807409596888968059442025262821716940634087677795453847457394598802");
        itemTaxDetail.setItemTaxAmountSign("P");
        itemTaxDetail.setItemTaxAmt(getPrice());
        itemTaxDetail.setItemTaxRate(new BigDecimal("1"));
    }

    private void setBlackBoxNonItemDetail(NonItemDetail nonItemDetail) {
        nonItemDetail.setNonMdDetailListSalesTransactionType("SALE");
        nonItemDetail.setItemDetailSubNumber(9999);
        nonItemDetail.setDetailSubNumber(9999);
        nonItemDetail.setNonMdType("137996");
        nonItemDetail.setNonItemCode("3124977172116534146330564");
        nonItemDetail.setServiceCode("22681");
        nonItemDetail.setPosNonItemName(
                "9417078140426931255892089559743578860119370185124642544264468378809148949323125122291107635078645340056748232967707830932703827528286170330597409415646756199219626785878938971278874719811789519210756049376142823875676897026012344943572688657633934724");
        nonItemDetail.setQuantityCode("P");
        nonItemDetail.setNonItemQty(9999);
        nonItemDetail.setNonItemUnitPriceTaxExcluded(getPrice());
        nonItemDetail.setNonItemUnitPriceTaxIncluded(getPrice());
        nonItemDetail.setNonItemSalesAmtTaxExcluded(getPrice());
        nonItemDetail.setNonItemSalesAmtTaxIncluded(getPrice());
        nonItemDetail.setNonItemNewPrice(getPrice());
        nonItemDetail.setNonCalculationNonItemType("7582358576");

        nonItemDetail.setOrderStatusUpdateDate("2017-12-31");

        nonItemDetail.setOrderStatusLastUpdateDateTime(
                OffsetDateTime.of(2017, 12, 31, 8, 0, 0, 0, ZoneOffset.of("Z")));
        nonItemDetail.setOrderStatus("05634430248095866023446506667354006795856967203838");
        nonItemDetail.setOrderSubstatus("67088440390228738504889653783412496217236315317594");
        nonItemDetail.setBookingStoreCode("5788356453");
        nonItemDetail.setBookingStoreSystemBrandCode("1076");
        nonItemDetail.setBookingStoreSystemBusinessCode("2880");
        nonItemDetail.setBookingStoreSystemCountryCode("372");
        nonItemDetail.setShipmentStoreCode("6968866117");
        nonItemDetail.setShipmentStoreSystemBrandCode("6132");
        nonItemDetail.setShipmentStoreSystemBusinessCode("3464");
        nonItemDetail.setShipmentStoreSystemCountryCode("202");
        nonItemDetail.setReceiptStoreCode("9234223152");
        nonItemDetail.setReceiptStoreSystemBrandCode("8610");
        nonItemDetail.setReceiptStoreSystemBusinessCode("4054");
        nonItemDetail.setReceiptStoreSystemCountryCode("048");
        nonItemDetail.setContributionSalesRepresentative("5090824052");
        nonItemDetail.setNonItemDetailNumber(9999);
        nonItemDetail.setNonItemTaxationType("9");
        nonItemDetail.setNonItemTaxKind("7145341337");
        NonItemInfo nonItemInfo = new NonItemInfo();
        setBlackBoxNonItemInfo(nonItemInfo);
        nonItemDetail.setNonItemInfo(nonItemInfo);
        List<NonItemDiscountDetail> nonItemDiscountDetailList = new ArrayList<>();
        NonItemDiscountDetail nonItemDiscountDetail = new NonItemDiscountDetail();
        setBlackBoxNonItemDiscountDetail(nonItemDiscountDetail);
        nonItemDiscountDetailList.add(nonItemDiscountDetail);
        nonItemDetail.setNonItemDiscountDetailList(nonItemDiscountDetailList);
        List<NonItemTaxDetail> nonItemTaxDetailList = new ArrayList<>();
        NonItemTaxDetail nonItemTaxDetail = new NonItemTaxDetail();
        setBlackBoxNonItemTaxDetail(nonItemTaxDetail);
        nonItemTaxDetailList.add(nonItemTaxDetail);
        nonItemDetail.setNonItemTaxDetailList(nonItemTaxDetailList);
    }

    private void setBlackBoxNonItemDiscountDetail(NonItemDiscountDetail nonItemDiscountDetail) {
        nonItemDiscountDetail.setNonItemDiscountDetailSubNumber(9999);
        nonItemDiscountDetail.setNonItemDiscountSubNumber(9999);
        nonItemDiscountDetail.setNonItemPromotionType("7429");
        nonItemDiscountDetail.setNonItemPromotionNumber("781266");
        nonItemDiscountDetail.setNonItemStoreDiscountType("69");
        nonItemDiscountDetail.setNonItemQuantityCode("P");
        nonItemDiscountDetail.setNonItemDiscountQty(99);
        nonItemDiscountDetail.setNonItemDiscountAmtTaxExcluded(getPrice());
        nonItemDiscountDetail.setNonItemDiscountAmtTaxIncluded(getPrice());
    }

    private void setBlackBoxNonItemInfo(NonItemInfo nonItemInfo) {
        nonItemInfo.setDetailSubNumber(9999);
        nonItemInfo.setItemDetailSubNumber(9999);
        nonItemInfo.setKeyCode("31284897438656500359");
        nonItemInfo.setCodeValue1("8150893158554596362266101");
        nonItemInfo.setCodeValue2("4070198061740544342198154");
        nonItemInfo.setCodeValue3("3758520251137093543045655");
        nonItemInfo.setCodeValue4("0230200533651882895897556");
        nonItemInfo.setName1(
                "1603943174907331944935934579499755761068108846336049964753321032089609775057031219931727641002771107481005063410335303606238085205488933473289863866072972916505513723932641836985304346391187720000557631688369931117965283704567113288303104604669862373");
        nonItemInfo.setName2(
                "5854678700248556429785247987443985587623052750768625098308327928960461046015785613421721143157397674508325184903086653813695628588631473236029878421143092338342002259517004023373763676504892730187623021139249345162413193884382540807564688186659206514");
        nonItemInfo.setName3(
                "6300774025043437587364776188010231498629518562085226884876230225258671475556796823335693062063929855898090536764768911736106628593078287352484783420776183378968472984940805709265833704307123353810353882489730158142113647280196111300246304866007453893");
        nonItemInfo.setName4(
                "2976060601585144123157741821357725753786801325185521913475036329415656326798417564396823171629509540621347508425771032611587195384167705617853522088285949848792449488872271375939079984626448812729289002607757312410382536789590360948977517700250054353");
    }

    private void setBlackBoxNonItemTaxDetail(NonItemTaxDetail nonItemTaxDetail) {
        nonItemTaxDetail.setNonItemTaxDetailSubNumber(9999);
        nonItemTaxDetail.setNonItemTaxDiscountSubNumber(9999);
        nonItemTaxDetail.setNonItemTaxSubNumber(9999);
        nonItemTaxDetail.setNonItemTaxType("0363442864");
        nonItemTaxDetail.setNonItemTaxName(
                "345813259496332702808005007444681104104393261735332455696951344073066621726601833578304547759476158730505616949448760300");
        nonItemTaxDetail.setNonItemTaxAmountSign("P");
        nonItemTaxDetail.setNonItemTaxAmt(getPrice());
        nonItemTaxDetail.setNonItemTaxRate(new BigDecimal("1"));
    }

    private void setBlackBoxSalesTransactionTaxDetail(
            SalesTransactionTaxDetail salesTransactionTaxDetail) {
        salesTransactionTaxDetail.setTaxSubNumber(9999);
        salesTransactionTaxDetail.setTaxGroup("2665357320");
        salesTransactionTaxDetail.setTaxAmountSign("P");
        salesTransactionTaxDetail.setTaxAmount(getPrice());
        salesTransactionTaxDetail.setTaxRate(new BigDecimal("1"));
    }

    private void setBlackBoxSalesTransactionTender(SalesTransactionTender salesTransactionTender) {
        salesTransactionTender.setTenderSubNumber(9999);
        salesTransactionTender.setTenderGroupId("755936");
        salesTransactionTender.setTenderId("41777501526499488023554257449285318651765769836702");
        salesTransactionTender.setPaymentSign("P");
        Price price = new Price();
        price.setCurrencyCode(Currency.getInstance(Locale.JAPAN));
        price.setValue(new BigDecimal("29700"));
        salesTransactionTender.setTaxIncludedPaymentAmount(price);
        TenderInfo tenderInfo = new TenderInfo();
        setBlackBoxTenderInfo(tenderInfo);
        salesTransactionTender.setTenderInfoList(tenderInfo);
    }

    private void setBlackBoxSalesTransactionTotal(SalesTransactionTotal salesTransactionTotal) {
        salesTransactionTotal.setTotalAmountSubNumber(9999);
        salesTransactionTotal.setTotalType("7149837594");
        salesTransactionTotal.setTotalAmountTaxExcluded(getPrice());
        salesTransactionTotal.setTotalAmountTaxIncluded(getPrice());
        salesTransactionTotal.setConsumptionTaxRate(new BigDecimal("1"));
        salesTransactionTotal.setSalesTransactionInformation1(
                "2877799943279394287805493248748266450806598431364013721811929894703768681345052414964534954185490755710819743495950257393103182671070051000058669944934633414874529820019548967983770164288797179251384928274867787566218238398765382007367419269755284209");
        salesTransactionTotal.setSalesTransactionInformation2(
                "6864257132375769459848395386230231410857470527144751914519875475585770881643748258109792252948610145445289850439058956724298294223265139152608159994635847481393520550804625019579765612112618915928251492130331422648585304217239030939092136078611835221");
        salesTransactionTotal.setSalesTransactionInformation3(
                "7920398938432133975914189673976707008051299991783384500143365086119146498538676590482500380639536311559840496582867002790240196776158677917931832673788201012627395864738805866044209200530804905482901670547827408281835027342217507110910837706861447467");
    }

    private void setBlackBoxTenderInfo(TenderInfo tenderInfo) {
        tenderInfo.setDiscountAmount(getPrice());
        tenderInfo.setDiscountRate(new BigDecimal("1"));
        tenderInfo.setDiscountCodeIdCorporateId("877150524176491816627540347077");
        tenderInfo.setCouponType("671817");
        tenderInfo.setCouponDiscountAmountSetting(getPrice());
        tenderInfo.setCouponMinUsageAmountThreshold(getPrice());
        tenderInfo.setCouponUserId("360534271776728830192776767537");
        tenderInfo.setCardNo("624177730007885454919075828118");
        tenderInfo.setCreditApprovalCode("762372260487737630460601730438");
        tenderInfo.setCreditProcessingSerialNumber("121437441850270093294372936848");
        tenderInfo.setCreditPaymentType("683351796869192872054214794091");
        tenderInfo.setCreditPaymentCount(99999);
        tenderInfo.setCreditAffiliatedStoreNumber("179359636617974795915817285441");
    }

    private void setBlackBoxTransaction(Transaction transaction) {
        transaction.setTransactionSerialNumber(9999);
        transaction.setIntegratedOrderId("787138393691170425085763622");
        transaction.setOrderSubNumber(9999);
        transaction.setSalesTransactionId("076675830272262261876829688023");
        transaction.setTokenCode("398508053069353022996853276639");
        transaction.setTransactionType("SALE");
        transaction.setSalesLinkageType(9);
        transaction.setReturnType(9);
        transaction.setSystemBrandCode("3817");
        transaction.setSystemBusinessCode("6173");
        transaction.setSystemCountryCode("603");
        transaction.setStoreCode("2231607102");
        transaction.setChannelCode("8024101778");

        transaction.setDataCreationBusinessDate("2017-12-31");

        transaction.setDataCreationDateTime(
                OffsetDateTime.of(2017, 12, 31, 8, 0, 0, 0, ZoneOffset.of("Z")));

        transaction.setOrderStatusUpdateDate("2017-12-31");

        transaction.setOrderStatusLastUpdateDateTime(
                OffsetDateTime.of(2017, 12, 31, 8, 0, 0, 0, ZoneOffset.of("Z")));
        transaction.setCashRegisterNo(999);
        transaction.setReceiptNo("8755");
        transaction.setOrderNumberForStorePayment("738753276480533753245574469");
        transaction.setAdvanceReceivedStoreCode("8379666617");
        transaction.setAdvanceReceivedStoreSystemBrandCode("4325");
        transaction.setAdvanceReceivedStoreSystemBusinessCode("3463");
        transaction.setAdvanceReceivedStoreSystemCountryCode("190");
        transaction.setOperatorCode("2409892762");
        transaction.setOriginalTransactionId("206040746826906436270313076157");
        transaction.setOriginalReceiptNo("1892");
        transaction.setOriginalCashRegisterNo(999);
        transaction.setDeposit(getPrice());
        transaction.setChange(getPrice());
        transaction.setReceiptNoForCreditCardCancellation("94174922224");
        transaction.setReceiptNoForCreditCard("618653511854619100044721826298");
        transaction.setPaymentStoreCode("6421230359");
        transaction.setPaymentStoreSystemBrandCode("9500");
        transaction.setPaymentStoreSystemBusinessCode("8940");
        transaction.setPaymentStoreSystemCountryCode("660");
        transaction.setReceiptIssuedFlag(true);
        transaction.setProcessingCompanyCode("52937371985754686047");

        transaction.setOrderCancellationDate(
                OffsetDateTime.of(2017, 12, 31, 8, 0, 0, 0, ZoneOffset.of("Z")));
        transaction.setOrderStatus("23382831686502538909660855147284406558803214838798");
        transaction.setOrderSubstatus("24915143431455836334467538722322722841531296909493");
        transaction.setPaymentStatus("50756799182741809854834792011014642113922002192301");
        transaction.setShipmentStatus("50737976888090958107231407686846384792274523184395");
        transaction.setReceivingStatus("17849237815478147151766814167511479693501558856475");
        transaction.setTransferOutStatus("30458595664148871910663308566495917260481627800940");
        transaction.setBookingStoreCode("6019727006");
        transaction.setBookingStoreSystemBrandCode("3893");
        transaction.setBookingStoreSystemBusinessCode("3299");
        transaction.setBookingStoreSystemCountryCode("596");
        transaction.setShipmentStoreCode("2481533849");
        transaction.setShipmentStoreSystemBrandCode("8731");
        transaction.setShipmentStoreSystemBusinessCode("1934");
        transaction.setShipmentStoreSystemCountryCode("497");
        transaction.setReceiptStoreCode("4285925227");
        transaction.setReceiptStoreSystemBrandCode("7077");
        transaction.setReceiptStoreSystemBusinessCode("2214");
        transaction.setReceiptStoreSystemCountryCode("417");
        transaction.setCustomerId("995847816360984725483936563955");
        transaction.setCorporateId("95039500441563508683");
        transaction.setSalesTransactionDiscountFlag(true);
        transaction.setSalesTransactionDiscountAmountRate(getPrice());
        transaction.setConsistencySalesFlag(true);
        transaction.setEmployeeSaleFlag(true);
        transaction.setEReceiptTargetFlag(true);
        List<TransactionItemDetail> transactionItemDetailList = new ArrayList<>();
        TransactionItemDetail transactionItemDetail = new TransactionItemDetail();
        setBlackBoxTransactionItemDetail(transactionItemDetail);
        transactionItemDetailList.add(transactionItemDetail);
        transaction.setTransactionItemDetailList(transactionItemDetailList);
        List<NonItemDetail> nonItemDetailList = new ArrayList<>();
        NonItemDetail nonItemDetail = new NonItemDetail();
        setBlackBoxNonItemDetail(nonItemDetail);
        nonItemDetailList.add(nonItemDetail);
        transaction.setNonItemDetailList(nonItemDetailList);
        List<SalesTransactionTender> salesTransactionTenderList = new ArrayList<>();
        SalesTransactionTender salesTransactionTender = new SalesTransactionTender();
        setBlackBoxSalesTransactionTender(salesTransactionTender);
        salesTransactionTenderList.add(salesTransactionTender);
        transaction.setSalesTransactionTenderList(salesTransactionTenderList);
        List<SalesTransactionTaxDetail> salesTransactionTaxDetailList = new ArrayList<>();
        SalesTransactionTaxDetail salesTransactionTaxDetail = new SalesTransactionTaxDetail();
        setBlackBoxSalesTransactionTaxDetail(salesTransactionTaxDetail);
        salesTransactionTaxDetailList.add(salesTransactionTaxDetail);
        transaction.setSalesTransactionTaxDetailList(salesTransactionTaxDetailList);
        List<SalesTransactionTotal> salesTransactionTotalList = new ArrayList<>();
        SalesTransactionTotal salesTransactionTotal = new SalesTransactionTotal();
        setBlackBoxSalesTransactionTotal(salesTransactionTotal);
        salesTransactionTotalList.add(salesTransactionTotal);
        transaction.setSalesTransactionTotalList(salesTransactionTotalList);
    }

    private void setBlackBoxTransactionImportData(TransactionImportData transactionImportData) {
        transactionImportData.setUpdateType(UpdateType.INSERT.getUpdateType());
        transactionImportData.setErrorCheckType(0);
        transactionImportData.setDataAlterationSalesLinkageType(9);
        transactionImportData.setDataAlterationBackboneLinkageType(9);
        transactionImportData.setSalesTransactionErrorId(
                "93555775330464821249207658760716512341234567890-123456789");
        transactionImportData.setIntegratedOrderId("723716277880394158705668113");
        transactionImportData.setOrderBarcodeNumber("67438042022559955676996");
        transactionImportData.setChannelCode("5074314753");
        transactionImportData.setStoreCode("2840339076");
        transactionImportData.setSystemBrandCode("5124");
        transactionImportData.setSystemBusinessCode("8501");
        transactionImportData.setSystemCountryCode("086");
        transactionImportData.setCustomerId("777731686013639958480558966603");

        transactionImportData.setOrderConfirmationBusinessDate("2017-12-31");

        transactionImportData.setOrderConfirmationDateTime(
                OffsetDateTime.of(2017, 12, 31, 8, 0, 0, 0, ZoneOffset.of("Z")));
        transactionImportData.setDataCorrectionEditingFlag(true);
        transactionImportData.setDataCorrectionUserId("189400030486456837987109586804");
        List<Transaction> transactionList = new ArrayList<>();
        Transaction transaction = new Transaction();
        setBlackBoxTransaction(transaction);
        transactionList.add(transaction);
        transactionImportData.setTransactionList(transactionList);
    }

    private void setBlackBoxTransactionItemDetail(TransactionItemDetail transactionItemDetail) {
        transactionItemDetail.setSystemBrandCode("5193");
        transactionItemDetail.setDetailSubNumber(9999);
        transactionItemDetail.setDetailListSalesTransactionType("RETURN");
        transactionItemDetail.setL2ItemCode("15939649345985308612");
        transactionItemDetail.setViewL2ItemCode("93579988405395791402");
        transactionItemDetail.setL2ProductName(
                "6409490552952390456010005502081880755474726058749551914661190494438313833154777337203988009438065888420093014774266832275507295771167625833424540277573215245468886630340422314861770526359632867894074143761700596952413052336100622354661436595246844569");
        transactionItemDetail.setL3ItemCode("0949266093859596920304399");
        transactionItemDetail.setL3PosProductName(
                "8362220985954077043412227210611987559962932508876284890853847269535429732092814376539099705470232128799766025350854157704697137688872765200472518601786954171484878682740953543089142447367808136133802469745310328016159465484708221549119833668276000524");
        transactionItemDetail.setEpcCode("327421539199470447547284");
        transactionItemDetail.setGDepartmentCode("711175");
        transactionItemDetail.setDeptCode(9999);
        transactionItemDetail.setQuantityCode("P");
        transactionItemDetail.setItemQty(9999);
        transactionItemDetail.setItemCost(getPrice());
        transactionItemDetail.setInitialSellingPrice(getPrice());
        transactionItemDetail.setBItemSellingPrice(getPrice());
        transactionItemDetail.setItemNewPrice(getPrice());
        transactionItemDetail.setItemUnitPriceTaxExcluded(getPrice());
        transactionItemDetail.setItemUnitPriceTaxIncluded(getPrice());
        transactionItemDetail.setItemSalesAmtTaxExcluded(getPrice());
        transactionItemDetail.setItemSalesAmtTaxIncluded(getPrice());

        transactionItemDetail.setOrderStatusUpdateDate("2017-12-31");

        transactionItemDetail.setOrderStatusLastUpdateDateTime(
                OffsetDateTime.of(2017, 12, 31, 8, 0, 0, 0, ZoneOffset.of("Z")));
        transactionItemDetail.setOrderStatus("84651012032876803183352693572788666379244950146875");
        transactionItemDetail
                .setOrderSubstatus("12112664186312352338391842123359991376410549144843");
        transactionItemDetail.setBookingStoreCode("9179228767");
        transactionItemDetail.setBookingStoreSystemBrandCode("6665");
        transactionItemDetail.setBookingStoreSystemBusinessCode("8241");
        transactionItemDetail.setBookingStoreSystemCountryCode("870");
        transactionItemDetail.setShipmentStoreCode("7322914905");
        transactionItemDetail.setShipmentStoreSystemBrandCode("3493");
        transactionItemDetail.setShipmentStoreSystemBusinessCode("4923");
        transactionItemDetail.setShipmentStoreSystemCountryCode("206");
        transactionItemDetail.setReceiptStoreCode("0682654200");
        transactionItemDetail.setReceiptStoreSystemBrandCode("1837");
        transactionItemDetail.setReceiptStoreSystemBusinessCode("2945");
        transactionItemDetail.setReceiptStoreSystemCountryCode("678");
        transactionItemDetail.setContributionSalesRepresentative("4566343581");
        transactionItemDetail.setCustomerId("998486375484155995973023112289");
        transactionItemDetail.setBundlePurchaseQty(99);
        transactionItemDetail.setBundlePurchasePrice(getPrice());
        transactionItemDetail.setBundlePurchaseIndex(999);
        transactionItemDetail.setLimitedAmountPromotionCount(99);
        transactionItemDetail.setCalculationUnavailableType("7786412195");
        transactionItemDetail.setItemMountDiscountType("4670210355");
        transactionItemDetail.setItemDiscountAmount(getPrice());
        transactionItemDetail.setBundleSalesFlag(true);
        transactionItemDetail.setBundleSalesPrice(getPrice());
        transactionItemDetail.setBundleSalesDetailIndex(999);
        transactionItemDetail.setItemDetailNumber(9999);
        transactionItemDetail.setItemTaxationType("9");
        transactionItemDetail.setItemTaxKind("8170147686");
        List<NonItemDetail> nonItemDetailListByItem = new ArrayList<>();
        NonItemDetail nonItemDetailByItem = new NonItemDetail();
        setBlackBoxNonItemDetail(nonItemDetailByItem);
        nonItemDetailListByItem.add(nonItemDetailByItem);
        transactionItemDetail.setNonItemDetailListByItem(nonItemDetailListByItem);
        List<ItemDiscount> itemDiscountList = new ArrayList<>();
        ItemDiscount itemDiscount = new ItemDiscount();
        setBlackBoxItemDiscount(itemDiscount);
        itemDiscountList.add(itemDiscount);
        transactionItemDetail.setItemDiscountList(itemDiscountList);
        List<ItemTaxDetail> itemTaxDetailList = new ArrayList<>();
        ItemTaxDetail itemTaxDetail = new ItemTaxDetail();
        setBlackBoxItemTaxDetail(itemTaxDetail);
        itemTaxDetailList.add(itemTaxDetail);
        transactionItemDetail.setItemTaxDetailList(itemTaxDetailList);
    }
}
