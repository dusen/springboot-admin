package com.fastretailing.dcp.sales.importtransaction.component;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
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
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import com.fastretailing.dcp.sales.common.type.ErrorType;
import com.fastretailing.dcp.sales.importtransaction.converter.CoreTableDataConverter;
import com.fastretailing.dcp.sales.importtransaction.converter.ErrorEvacuationTableDataConverter;
import com.fastretailing.dcp.sales.importtransaction.dto.ItemDiscount;
import com.fastretailing.dcp.sales.importtransaction.dto.ItemTaxDetail;
import com.fastretailing.dcp.sales.importtransaction.dto.NonItemDetail;
import com.fastretailing.dcp.sales.importtransaction.dto.NonItemDiscountDetail;
import com.fastretailing.dcp.sales.importtransaction.dto.NonItemInfo;
import com.fastretailing.dcp.sales.importtransaction.dto.NonItemTaxDetail;
import com.fastretailing.dcp.sales.importtransaction.dto.SalesTransactionTaxDetail;
import com.fastretailing.dcp.sales.importtransaction.dto.SalesTransactionTender;
import com.fastretailing.dcp.sales.importtransaction.dto.SalesTransactionTotal;
import com.fastretailing.dcp.sales.importtransaction.dto.TableCommonItem;
import com.fastretailing.dcp.sales.importtransaction.dto.TenderInfo;
import com.fastretailing.dcp.sales.importtransaction.dto.Transaction;
import com.fastretailing.dcp.sales.importtransaction.dto.TransactionImportData;
import com.fastretailing.dcp.sales.importtransaction.dto.TransactionItemDetail;
import com.fastretailing.dcp.sales.importtransaction.entity.ErrorEvacuationSalesTransactionDetail;
import com.fastretailing.dcp.sales.importtransaction.entity.ErrorEvacuationSalesTransactionHeader;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesOrderInformation;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionDetail;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionDetailCondition;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionErrorDetail;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionHeader;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionHeaderCondition;
import com.fastretailing.dcp.sales.importtransaction.exception.ErrorEvacuationException;
import com.fastretailing.dcp.sales.importtransaction.exception.InvalidDataException;
import com.fastretailing.dcp.sales.importtransaction.repository.ErrorEvacuationSalesTransactionDetailMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.ErrorEvacuationSalesTransactionHeaderMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.SalesErrorSalesOrderInformationMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.SalesOrderInformationMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.SalesTransactionDetailMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.SalesTransactionErrorDetailMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.SalesTransactionHeaderMapper;
import com.fastretailing.dcp.storecommon.dto.Price;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UpdateSalesCoreServiceTest {

    @SpyBean
    private UpdateSalesCoreService transactionUpdateService;

    @MockBean
    private SalesErrorSalesOrderInformationMapper salesErrorOrderInformationEntityMapper;

    @MockBean
    private SalesTransactionDetailMapper salesTransactionDetailEntityMapper;

    @MockBean
    private SalesTransactionHeaderMapper salesTransactionHeaderEntityMapper;

    @MockBean
    private SalesOrderInformationMapper salesOrderInformationEntityMapper;

    @MockBean
    private ErrorEvacuationSalesTransactionDetailMapper errorEvacuationSalesTransactionDetailEntityMapper;

    @MockBean
    private ErrorEvacuationSalesTransactionHeaderMapper errorEvacuationSalesTransactionHeaderEntityMapper;

    /**
     * Component for operating DB operations on the sales transaction error detail table.
     */
    @MockBean
    private SalesTransactionErrorDetailMapper salesTransactionErrorDetailEntityMapper;

    @MockBean
    private ErrorEvacuationTableDataConverter errorEvacuationTableDataConverter;

    @MockBean
    private CoreTableDataConverter coreTableDataConverter;

    @Autowired
    private ModelMapper modelMapper;

    private TransactionImportData transactionImportData = new TransactionImportData();

    private String userId = "user01";

    private String transactionErrorId = "tranErrorId000001";

    private String[] ignoreFields = {"updateDatetime", "createDatetime"};

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        setTransactionImportData(transactionImportData);
    }

    @Test
    public void testSalesErrorOrderInformationListNotEmpty() throws Exception {
        List<SalesTransactionErrorDetail> salesTransactionErrorDetailEntityList = new ArrayList<>();
        SalesTransactionErrorDetail errorDetail = new SalesTransactionErrorDetail();
        salesTransactionErrorDetailEntityList.add(errorDetail);

        when(salesTransactionErrorDetailEntityMapper.selectByCondition(Mockito.any()))
                .thenReturn(salesTransactionErrorDetailEntityList);
        when(errorEvacuationSalesTransactionHeaderEntityMapper
                .insertSelective(Mockito.any(ErrorEvacuationSalesTransactionHeader.class)))
                        .thenReturn(1);
        when(errorEvacuationSalesTransactionDetailEntityMapper
                .insertSelective(Mockito.any(ErrorEvacuationSalesTransactionDetail.class)))
                        .thenReturn(1);

        when(errorEvacuationTableDataConverter
                .convertTErrorEvacuationSalesTransactionHeaderEntityForInsert(
                        Mockito.any(Transaction.class), Mockito.anyString(), Mockito.anyString(),
                        Mockito.anyString()))
                                .thenReturn(new ErrorEvacuationSalesTransactionHeader());
        when(errorEvacuationTableDataConverter
                .convertTErrorEvacuationSalesTransactionDetailEntityForInsertOutside(
                        Mockito.any(NonItemDetail.class), Mockito.anyString(), Mockito.anyString(),
                        Mockito.anyInt(), Mockito.anyString(), Mockito.anyString(),
                        Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString()))
                                .thenReturn(new ErrorEvacuationSalesTransactionDetail());
        when(errorEvacuationTableDataConverter
                .convertTErrorEvacuationSalesTransactionDetailEntityForInsert(
                        Mockito.any(TransactionItemDetail.class), Mockito.anyString(),
                        Mockito.anyString(), Mockito.anyInt(), Mockito.anyString(),
                        Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt()))
                                .thenReturn(new ErrorEvacuationSalesTransactionDetail());
        when(coreTableDataConverter.convertTSalesTransactionDetailEntityForInsert(
                Mockito.any(TransactionItemDetail.class), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt()))
                        .thenReturn(new SalesTransactionDetail());
        when(coreTableDataConverter.convertTSalesTransactionHeaderEntityForInsert(
                Mockito.any(Transaction.class), Mockito.anyString(), Mockito.anyInt()))
                        .thenReturn(new SalesTransactionHeader());
        when(coreTableDataConverter.convertTSalesTransactionDetailEntityForInsertOutside(
                Mockito.any(NonItemDetail.class), Mockito.anyString(), Mockito.anyInt(),
                Mockito.anyString(), Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt()))
                        .thenReturn(new SalesTransactionDetail());

        try {
            transactionUpdateService.updateSalesCoreTable(transactionImportData, userId,
                    transactionErrorId);
        } catch (ErrorEvacuationException e) {
            e.printStackTrace();
        }

        ArgumentCaptor<ErrorEvacuationSalesTransactionDetail> evacuationDetailArgument =
                ArgumentCaptor.forClass(ErrorEvacuationSalesTransactionDetail.class);
        ArgumentCaptor<ErrorEvacuationSalesTransactionHeader> evacuationHeaderArgument =
                ArgumentCaptor.forClass(ErrorEvacuationSalesTransactionHeader.class);

        ArgumentCaptor<Transaction> evacuationHeaderArgumentForInsert =
                ArgumentCaptor.forClass(Transaction.class);
        ArgumentCaptor<NonItemDetail> detailArgumentForInsertOutside =
                ArgumentCaptor.forClass(NonItemDetail.class);
        ArgumentCaptor<TransactionItemDetail> evacuationDetailArgumentForInsert =
                ArgumentCaptor.forClass(TransactionItemDetail.class);
        ArgumentCaptor<Integer> detailCount = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> nonDetailCount = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> itemDetailCount = ArgumentCaptor.forClass(Integer.class);

        verify(errorEvacuationTableDataConverter,
                times(transactionImportData.getTransactionList().size()))
                        .convertTErrorEvacuationSalesTransactionHeaderEntityForInsert(
                                evacuationHeaderArgumentForInsert.capture(), anyString(),
                                anyString(), anyString());
        verify(errorEvacuationTableDataConverter,
                times(transactionImportData.getTransactionList().stream().mapToInt(tran -> {
                    return tran.getTransactionItemDetailList().stream().mapToInt(itemDetail -> {
                        return itemDetail.getNonItemDetailListByItem().size();
                    }).sum() + tran.getNonItemDetailList().size();
                }).sum())).convertTErrorEvacuationSalesTransactionDetailEntityForInsertOutside(
                        detailArgumentForInsertOutside.capture(), anyString(), anyString(),
                        anyInt(), Mockito.anyString(), anyString(), detailCount.capture(),
                        nonDetailCount.capture(), anyString());
        verify(errorEvacuationTableDataConverter,
                times(transactionImportData.getTransactionList().stream().mapToInt(tran -> {
                    return tran.getTransactionItemDetailList().size();
                }).sum())).convertTErrorEvacuationSalesTransactionDetailEntityForInsert(
                        evacuationDetailArgumentForInsert.capture(), anyString(), anyString(),
                        anyInt(), anyString(), anyString(), detailCount.capture(),
                        itemDetailCount.capture());

        int headerMapperCallTime = transactionImportData.getTransactionList().size();
        verify(errorEvacuationSalesTransactionHeaderEntityMapper, times(headerMapperCallTime))
                .insertSelective(evacuationHeaderArgument.capture());

        int detailMapperCallTime =
                transactionImportData.getTransactionList().stream().mapToInt(transactionTmp -> {
                    return transactionTmp.getTransactionItemDetailList()
                            .stream()
                            .mapToInt(itemDetail -> {
                                return 1 + itemDetail.getNonItemDetailListByItem().size();
                            })
                            .sum() + transactionTmp.getNonItemDetailList().size();
                }).sum();
        verify(errorEvacuationSalesTransactionDetailEntityMapper, times(detailMapperCallTime))
                .insertSelective(evacuationDetailArgument.capture());

        List<Transaction> evacuationHeaderArgumentForInsertList =
                evacuationHeaderArgumentForInsert.getAllValues();
        List<NonItemDetail> detailArgumentForInsertOutsideList =
                detailArgumentForInsertOutside.getAllValues();
        List<TransactionItemDetail> evacuationDetailArgumentForInsertList =
                evacuationDetailArgumentForInsert.getAllValues();
        List<Integer> detailCountList = detailCount.getAllValues();
        List<Integer> itemDetailCountList = itemDetailCount.getAllValues();
        List<Integer> nonDetailCountList = nonDetailCount.getAllValues();
        int[] detailListCount = {0};
        int[] itemListCount = {0};
        int[] nonListCount = {0};
        AtomicInteger detailCountAtomic = new AtomicInteger(0);
        transactionImportData.getTransactionList().forEach(transactionTmp -> {
            assertThat(evacuationHeaderArgumentForInsertList).contains(transactionTmp);
            AtomicInteger itemDetailCountAtomic = new AtomicInteger(0);
            transactionTmp.getTransactionItemDetailList().forEach(itemDetail -> {
                assertThat(evacuationDetailArgumentForInsertList).contains(itemDetail);
                assertThat(detailCountList.get(detailListCount[0]++)
                        .equals(detailCountAtomic.incrementAndGet()));
                assertThat(itemDetailCountList.get(itemListCount[0])
                        .equals(itemDetailCountAtomic.incrementAndGet()));

                itemDetail.getNonItemDetailListByItem().forEach(nonItemDetail -> {
                    assertThat(detailArgumentForInsertOutsideList).contains(nonItemDetail);
                    assertThat(detailCountList.get(detailListCount[0]++)
                            .equals(detailCountAtomic.incrementAndGet()));
                    assertThat(itemDetailCountList.get(itemListCount[0])
                            .equals(itemDetailCountAtomic.get()));
                });
                itemListCount[0]++;
            });
            AtomicInteger nonItemDetailCountAtomic = new AtomicInteger(0);
            transactionTmp.getNonItemDetailList().forEach(nonItemDetail -> {
                nonDetailCountList.get(nonListCount[0]++)
                        .equals(nonItemDetailCountAtomic.incrementAndGet());
                assertThat(detailArgumentForInsertOutsideList).contains(nonItemDetail);
            });
        });

    }

    @Test
    public void testSalesErrorOrderInformationListEmpty() throws ErrorEvacuationException , Exception {
        transactionImportData.setSalesTransactionErrorId(null);

        SalesOrderInformation orderInformationEntity = new SalesOrderInformation();
        orderInformationEntity.setIntegratedOrderId(transactionImportData.getIntegratedOrderId());
        orderInformationEntity.setOrderBarcodeNumber("11");
        orderInformationEntity.setStoreCode("12");
        orderInformationEntity.setSystemBrandCode("13");
        orderInformationEntity.setSystemCountryCode("14");
        when(salesOrderInformationEntityMapper.selectByPrimaryKey(anyString()))
                .thenReturn(orderInformationEntity);
        when(salesTransactionDetailEntityMapper.updateByConditionSelective(
                Mockito.any(SalesTransactionDetail.class),
                Mockito.any(SalesTransactionDetailCondition.class))).thenReturn(1);
        when(salesTransactionHeaderEntityMapper.updateByConditionSelective(
                Mockito.any(SalesTransactionHeader.class),
                Mockito.any(SalesTransactionHeaderCondition.class))).thenReturn(1);

        when(coreTableDataConverter
                .convertTSalesTransactionHeaderEntityForUpdate(any(Transaction.class), anyString()))
                        .thenReturn(new SalesTransactionHeader());
        when(coreTableDataConverter.convertTSalesTransactionDetailEntityForUpdate(
                Mockito.any(TransactionItemDetail.class), Mockito.anyString(), Mockito.anyString()))
                        .thenReturn(new SalesTransactionDetail());
        when(coreTableDataConverter.convertTSalesTransactionDetailOutsideEntityForUpdate(
                any(NonItemDetail.class), Mockito.anyString(), Mockito.anyString()))
                        .thenReturn(new SalesTransactionDetail());

        transactionUpdateService.updateSalesCoreTable(transactionImportData, userId,
                transactionErrorId);

        ArgumentCaptor<TransactionItemDetail> detailArgumentForInsert =
                ArgumentCaptor.forClass(TransactionItemDetail.class);
        ArgumentCaptor<Transaction> headerArgumentForInsert =
                ArgumentCaptor.forClass(Transaction.class);
        ArgumentCaptor<NonItemDetail> nonDetailArgumentForInsertOutside =
                ArgumentCaptor.forClass(NonItemDetail.class);

        verify(coreTableDataConverter,
                times(transactionImportData.getTransactionList().stream().mapToInt(tran -> {
                    return tran.getTransactionItemDetailList().size();
                }).sum())).convertTSalesTransactionDetailEntityForUpdate(
                        detailArgumentForInsert.capture(), anyString(), anyString());
        verify(coreTableDataConverter, times(transactionImportData.getTransactionList().size()))
                .convertTSalesTransactionHeaderEntityForUpdate(headerArgumentForInsert.capture(),
                        anyString());
        verify(coreTableDataConverter,
                times(transactionImportData.getTransactionList().stream().mapToInt(tran -> {
                    return tran.getTransactionItemDetailList().stream().mapToInt(itemDetail -> {
                        return itemDetail.getNonItemDetailListByItem().size();
                    }).sum() + tran.getNonItemDetailList().size();
                }).sum())).convertTSalesTransactionDetailOutsideEntityForUpdate(
                        nonDetailArgumentForInsertOutside.capture(), anyString(), anyString());

        List<TransactionItemDetail> detailArgumentForInsertList =
                detailArgumentForInsert.getAllValues();
        List<Transaction> headerArgumentForInsertList = headerArgumentForInsert.getAllValues();
        List<NonItemDetail> nonDetailArgumentForInsertOutsideList =
                nonDetailArgumentForInsertOutside.getAllValues();
        transactionImportData.getTransactionList().forEach(transactionTmp -> {
            assertThat(headerArgumentForInsertList).contains(transactionTmp);
            transactionTmp.getTransactionItemDetailList().forEach(itemDetail -> {
                assertThat(detailArgumentForInsertList).contains(itemDetail);

                itemDetail.getNonItemDetailListByItem().forEach(nonItemDetail -> {
                    assertThat(nonDetailArgumentForInsertOutsideList).contains(nonItemDetail);
                });
            });
            transactionTmp.getNonItemDetailList().forEach(nonItemDetail -> {
                assertThat(nonDetailArgumentForInsertOutsideList).contains(nonItemDetail);
            });
        });
    }

    @Test
    public void testSalesTransactionErrorIdNull() throws ErrorEvacuationException, Exception {
        transactionImportData.setIntegratedOrderId("io001");
        transactionImportData.setSalesTransactionErrorId("SalesErrorId");
        try {
            transactionUpdateService.updateSalesCoreTable(transactionImportData, userId,
                    transactionErrorId);
            Assert.fail();
        } catch (InvalidDataException invalidDataException) {
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity =
                    new SalesTransactionErrorDetail();
            convertTSalesTransactionErrorDetailEntityForUpdateType(transactionImportData,
                    salesTransactionErrorDetailEntity,
                    transactionImportData.getSalesTransactionErrorId(), userId);
            InvalidDataException invalidDataExceptionExcepted =
                    new InvalidDataException(salesTransactionErrorDetailEntity);

            assertThat(invalidDataExceptionExcepted.getEntity())
                    .isEqualToIgnoringGivenFields(invalidDataException.getEntity(), ignoreFields);
        }

    }

    @Test
    public void testSalesOrderInformationEntityNull() throws ErrorEvacuationException, Exception {
        transactionImportData.setIntegratedOrderId("io001");
        transactionImportData.setSalesTransactionErrorId("tranErrorId000001");
        try {
            transactionUpdateService.updateSalesCoreTable(transactionImportData, userId,
                    transactionErrorId);
            Assert.fail();
        } catch (InvalidDataException invalidDataException) {
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity =
                    new SalesTransactionErrorDetail();
            convertTSalesTransactionErrorDetailEntityForUpdateType(transactionImportData,
                    salesTransactionErrorDetailEntity,
                    transactionImportData.getSalesTransactionErrorId(), userId);
            InvalidDataException invalidDataExceptionExcepted =
                    new InvalidDataException(salesTransactionErrorDetailEntity);

            assertThat(invalidDataExceptionExcepted.getEntity())
                    .isEqualToIgnoringGivenFields(invalidDataException.getEntity(), ignoreFields);
        }

    }

    private void setTransactionImportData(TransactionImportData importData) {
        importData.setChannelCode("1");

        importData.setCustomerId("3");
        importData.setDataAlterationBackboneLinkageType(1);
        importData.setDataAlterationSalesLinkageType(1);
        importData.setDataCorrectionEditingFlag(true);
        importData.setDataCorrectionUserId("");
        importData.setErrorCheckType(1);
        importData.setIntegratedOrderId("4");
        importData.setOrderBarcodeNumber("5");
        importData.setOrderConfirmationBusinessDate("");
        importData.setOrderConfirmationDateTime(
                OffsetDateTime.of(2017, 12, 31, 8, 0, 0, 0, ZoneOffset.of("Z")));
        importData.setSalesTransactionErrorId("6");
        importData.setStoreCode("7");
        importData.setSystemBrandCode("8");
        importData.setSystemBusinessCode("1");
        importData.setSystemCountryCode("9");
        List<Transaction> transactionList = new ArrayList<>();
        Transaction transaction = new Transaction();
        setTransaction(transaction);
        Transaction transaction1 = new Transaction();
        setTransaction(transaction1);
        Transaction transaction2 = new Transaction();
        setTransaction(transaction2);
        transactionList.add(transaction);
        transactionList.add(transaction1);
        transactionList.add(transaction2);
        importData.setTransactionList(transactionList);
        importData.setUpdateType("");
    }

    private void setTransaction(Transaction transaction) {
        transaction.setTransactionSerialNumber(1);
        transaction.setIntegratedOrderId("2");
        transaction.setOrderSubNumber(1);
        transaction.setSalesTransactionId("transaction0001");
        transaction.setTokenCode("3");
        transaction.setTransactionType("4");
        transaction.setReturnType(1);
        transaction.setSystemBrandCode("5");
        transaction.setSystemBusinessCode("1");
        transaction.setSystemCountryCode("6");
        transaction.setStoreCode("7");

        transaction.setChannelCode("9");
        transaction.setDataCreationBusinessDate("");
        transaction.setDataCreationDateTime(
                OffsetDateTime.of(2017, 12, 31, 8, 0, 0, 0, ZoneOffset.of("Z")));
        transaction.setOrderStatusUpdateDate("");
        transaction.setOrderStatusLastUpdateDateTime(
                OffsetDateTime.of(2017, 12, 31, 8, 0, 0, 0, ZoneOffset.of("Z")));
        transaction.setCashRegisterNo(1);
        transaction.setReceiptNo("");
        transaction.setOrderNumberForStorePayment("");
        transaction.setAdvanceReceivedStoreCode("");
        transaction.setAdvanceReceivedStoreSystemBrandCode("");
        transaction.setAdvanceReceivedStoreSystemBusinessCode("1");
        transaction.setAdvanceReceivedStoreSystemCountryCode("");
        transaction.setOperatorCode("");
        transaction.setOriginalTransactionId("");

        transaction.setOriginalReceiptNo("9");
        transaction.setOriginalCashRegisterNo(1);
        transaction.setDeposit(getPrice());
        transaction.setChange(getPrice());
        transaction.setReceiptNoForCreditCardCancellation("");
        transaction.setReceiptNoForCreditCard("");
        transaction.setPaymentStoreCode("");
        transaction.setPaymentStoreSystemBrandCode("");
        transaction.setPaymentStoreSystemBusinessCode("1");
        transaction.setPaymentStoreSystemCountryCode("");
        transaction.setReceiptIssuedFlag(true);
        transaction.setProcessingCompanyCode("");
        transaction.setOrderCancellationDate(
                OffsetDateTime.of(2017, 12, 31, 8, 0, 0, 0, ZoneOffset.of("Z")));
        transaction.setOrderStatus("");
        transaction.setOrderSubstatus("");
        transaction.setPaymentStatus("");
        transaction.setShipmentStatus("");
        transaction.setReceivingStatus("");
        transaction.setTransferOutStatus("");
        transaction.setBookingStoreCode("");
        transaction.setBookingStoreSystemBrandCode("");
        transaction.setBookingStoreSystemBusinessCode("1");
        transaction.setBookingStoreSystemCountryCode("");
        transaction.setShipmentStoreCode("");
        transaction.setShipmentStoreSystemBrandCode("");
        transaction.setShipmentStoreSystemBusinessCode("1");
        transaction.setShipmentStoreSystemCountryCode("");
        transaction.setReceiptStoreCode("");
        transaction.setReceiptStoreSystemBrandCode("");
        transaction.setReceiptStoreSystemBusinessCode("1");
        transaction.setReceiptStoreSystemCountryCode("");
        transaction.setCustomerId("");
        transaction.setCorporateId("");
        transaction.setSalesTransactionDiscountFlag(true);
        transaction.setSalesTransactionDiscountAmountRate(getPrice());
        transaction.setConsistencySalesFlag(true);
        transaction.setEmployeeSaleFlag(true);
        transaction.setEReceiptTargetFlag(true);

        List<TransactionItemDetail> transactionItemDetailList = new ArrayList<>();
        TransactionItemDetail transactionItemDetail = new TransactionItemDetail();
        setTransactionItemDetail(transactionItemDetail);
        TransactionItemDetail transactionItemDetail2 = new TransactionItemDetail();
        setTransactionItemDetail(transactionItemDetail2);
        TransactionItemDetail transactionItemDetail3 = new TransactionItemDetail();
        setTransactionItemDetail(transactionItemDetail3);
        transactionItemDetailList.add(transactionItemDetail);
        transactionItemDetailList.add(transactionItemDetail2);
        transactionItemDetailList.add(transactionItemDetail3);
        transaction.setTransactionItemDetailList(transactionItemDetailList);

        List<NonItemDetail> nonItemDetailList = new ArrayList<>();
        NonItemDetail nonItemDetail = new NonItemDetail();
        setNonItemDetailTwo(nonItemDetail);
        NonItemDetail nonItemDetail2 = new NonItemDetail();
        setNonItemDetailTwo(nonItemDetail2);
        NonItemDetail nonItemDetail3 = new NonItemDetail();
        setNonItemDetailTwo(nonItemDetail3);
        nonItemDetailList.add(nonItemDetail3);
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

    void setTransactionItemDetail(TransactionItemDetail entity) {

        entity.setSystemBrandCode("");
        entity.setDetailSubNumber(1);
        entity.setDetailListSalesTransactionType("");
        entity.setL2ItemCode("");
        entity.setViewL2ItemCode("");
        entity.setL2ProductName("");
        entity.setL3ItemCode("");
        entity.setL3PosProductName("");

        entity.setEpcCode("");
        entity.setGDepartmentCode("");
        entity.setDeptCode(1);
        entity.setQuantityCode("");

        entity.setItemCost(getPrice());
        entity.setInitialSellingPrice(getPrice());
        entity.setBItemSellingPrice(getPrice());
        entity.setItemNewPrice(getPrice());
        entity.setItemUnitPriceTaxExcluded(getPrice());
        entity.setItemUnitPriceTaxIncluded(getPrice());
        entity.setItemSalesAmtTaxExcluded(getPrice());
        entity.setItemSalesAmtTaxIncluded(getPrice());
        entity.setOrderStatusUpdateDate("");
        entity.setOrderStatusLastUpdateDateTime(
                OffsetDateTime.of(2017, 12, 31, 8, 0, 0, 0, ZoneOffset.of("Z")));
        entity.setOrderStatus("");
        entity.setOrderSubstatus("");
        entity.setBookingStoreCode("");
        entity.setBookingStoreSystemBrandCode("");
        entity.setBookingStoreSystemBusinessCode("1");
        entity.setBookingStoreSystemCountryCode("");
        entity.setShipmentStoreCode("");
        entity.setShipmentStoreSystemBrandCode("");
        entity.setShipmentStoreSystemBusinessCode("1");
        entity.setShipmentStoreSystemCountryCode("");
        entity.setReceiptStoreCode("");
        entity.setReceiptStoreSystemBrandCode("");
        entity.setReceiptStoreSystemBusinessCode("1");
        entity.setReceiptStoreSystemCountryCode("");
        entity.setContributionSalesRepresentative("");
        entity.setCustomerId("");
        entity.setBundlePurchaseQty(1);
        entity.setBundlePurchasePrice(getPrice());
        entity.setBundlePurchaseIndex(1);
        entity.setLimitedAmountPromotionCount(1);
        entity.setCalculationUnavailableType("1");

        entity.setItemDiscountAmount(getPrice());
        entity.setBundleSalesFlag(true);
        entity.setBundleSalesPrice(getPrice());
        entity.setBundleSalesDetailIndex(1);
        entity.setItemDetailNumber(1);
        entity.setItemTaxationType("8");
        entity.setItemTaxKind("");

        List<NonItemDetail> nonItemDetailList = new ArrayList<>();
        NonItemDetail nonItemDetail = new NonItemDetail();
        setNonItemDetailOne(nonItemDetail);
        nonItemDetailList.add(nonItemDetail);
        NonItemDetail nonItemDetail2 = new NonItemDetail();
        setNonItemDetailOne(nonItemDetail2);
        nonItemDetailList.add(nonItemDetail2);
        NonItemDetail nonItemDetail3 = new NonItemDetail();
        setNonItemDetailOne(nonItemDetail3);
        nonItemDetailList.add(nonItemDetail3);
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

        entity.setDetailSubNumber(321);
        entity.setItemDetailSubNumber(222);
        entity.setNonMdType("");
        entity.setNonItemCode("");
        entity.setServiceCode("");
        entity.setPosNonItemName("");

        entity.setQuantityCode("");
        entity.setNonItemQty(1);
        entity.setNonItemUnitPriceTaxExcluded(getPrice());
        entity.setNonItemUnitPriceTaxIncluded(getPrice());
        entity.setNonItemSalesAmtTaxExcluded(getPrice());
        entity.setNonItemSalesAmtTaxIncluded(getPrice());
        entity.setNonItemNewPrice(getPrice());
        entity.setNonCalculationNonItemType("1");
        entity.setOrderStatusUpdateDate("");
        entity.setOrderStatusLastUpdateDateTime(
                OffsetDateTime.of(2017, 12, 31, 8, 0, 0, 0, ZoneOffset.of("Z")));
        entity.setOrderStatus("");
        entity.setOrderSubstatus("");
        entity.setBookingStoreCode("");
        entity.setBookingStoreSystemBrandCode("");
        entity.setBookingStoreSystemBusinessCode("1");
        entity.setBookingStoreSystemCountryCode("");
        entity.setShipmentStoreCode("");
        entity.setShipmentStoreSystemBrandCode("");
        entity.setShipmentStoreSystemBusinessCode("1");
        entity.setShipmentStoreSystemCountryCode("");
        entity.setReceiptStoreCode("");
        entity.setReceiptStoreSystemBrandCode("");
        entity.setReceiptStoreSystemBusinessCode("1");
        entity.setReceiptStoreSystemCountryCode("");
        entity.setContributionSalesRepresentative("");
        entity.setNonItemTaxationType("7");
        entity.setNonItemTaxKind("");
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

        entity.setDetailSubNumber(1);
        entity.setItemDetailSubNumber(1);
        entity.setKeyCode("");
        entity.setCodeValue1("");
        entity.setCodeValue2("");
        entity.setCodeValue3("");
        entity.setCodeValue4("");
        entity.setName1("");
        entity.setName2("");
        entity.setName3("");
        entity.setName4("");
    }

    private void setNonItemDiscountDetailOne(NonItemDiscountDetail entity) {

        entity.setNonItemDiscountDetailSubNumber(1);

        entity.setNonItemPromotionType("");
        entity.setNonItemStoreDiscountType("");
        entity.setNonItemQuantityCode("");
        entity.setNonItemDiscountQty(1);
        entity.setNonItemDiscountAmtTaxExcluded(getPrice());
        entity.setNonItemDiscountAmtTaxIncluded(getPrice());
    }

    private void setNonItemTaxDetailOne(NonItemTaxDetail entity) {

        entity.setNonItemTaxDetailSubNumber(1);
        entity.setNonItemTaxSubNumber(1);

        entity.setNonItemTaxType("");
        entity.setNonItemTaxName("");
        entity.setNonItemTaxAmountSign("");
        entity.setNonItemTaxAmt(getPrice());
        entity.setNonItemTaxRate(new BigDecimal(1));
    }

    private void setItemDiscount(ItemDiscount entity) {

        entity.setItemDiscountDetailSubNumber(1);
        entity.setItemDiscountSubNumber(1);
        entity.setItemPromotionType("");
        entity.setItemPromotionNumber("1");
        entity.setItemStoreDiscountType("");
        entity.setItemQuantityCode("");
        entity.setItemDiscountQty(1);
        entity.setItemDiscountAmtTaxExcluded(getPrice());
        entity.setItemDiscountAmtTaxIncluded(getPrice());

    }

    private void setItemTaxDetail(ItemTaxDetail entity) {

        entity.setItemTaxDetailSubNumber(1);
        entity.setItemTaxSubNumber(1);
        entity.setItemTaxType("");
        entity.setItemTaxName("");
        entity.setItemTaxAmountSign("");
        entity.setItemTaxAmt(getPrice());
        entity.setItemTaxRate(new BigDecimal(1));

    }

    private void setNonItemDetailTwo(NonItemDetail entity) {

        entity.setDetailSubNumber(123);
        entity.setNonItemDetailNumber(1);
        entity.setNonMdDetailListSalesTransactionType("");
        entity.setNonItemDetailSalesLinkageType(1);
        entity.setNonMdType("");
        entity.setNonItemCode("");
        entity.setServiceCode("");
        entity.setPosNonItemName("");

        entity.setQuantityCode("");
        entity.setNonItemQty(1);
        entity.setNonItemUnitPriceTaxExcluded(getPrice());
        entity.setNonItemUnitPriceTaxIncluded(getPrice());
        entity.setNonItemSalesAmtTaxExcluded(getPrice());
        entity.setNonItemSalesAmtTaxIncluded(getPrice());
        entity.setNonItemNewPrice(getPrice());
        entity.setNonCalculationNonItemType("1");
        entity.setOrderStatusUpdateDate("");
        entity.setOrderStatusLastUpdateDateTime(
                OffsetDateTime.of(2017, 12, 31, 8, 0, 0, 0, ZoneOffset.of("Z")));
        entity.setOrderStatus("");
        entity.setOrderSubstatus("");
        entity.setBookingStoreCode("");
        entity.setBookingStoreSystemBrandCode("");
        entity.setBookingStoreSystemBusinessCode("1");
        entity.setBookingStoreSystemCountryCode("");
        entity.setShipmentStoreCode("");
        entity.setShipmentStoreSystemBrandCode("");
        entity.setShipmentStoreSystemBusinessCode("1");
        entity.setShipmentStoreSystemCountryCode("");
        entity.setReceiptStoreCode("");
        entity.setReceiptStoreSystemBrandCode("");
        entity.setReceiptStoreSystemBusinessCode("1");
        entity.setReceiptStoreSystemCountryCode("");
        entity.setContributionSalesRepresentative("");
        entity.setItemDetailSubNumber(1);
        entity.setNonItemTaxationType("");
        entity.setNonItemTaxKind("");

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

        entity.setDetailSubNumber(1);
        entity.setItemDetailSubNumber(1);
        entity.setKeyCode("");
        entity.setCodeValue1("");
        entity.setCodeValue2("");
        entity.setCodeValue3("");
        entity.setCodeValue4("");
        entity.setName1("");
        entity.setName2("");

    }

    private void setNonItemDiscountDetailTwo(NonItemDiscountDetail entity) {

        entity.setNonItemDiscountSubNumber(1);
        entity.setNonItemPromotionType("");

        entity.setNonItemStoreDiscountType("");
        entity.setNonItemQuantityCode("");
        entity.setNonItemDiscountQty(1);
        entity.setNonItemDiscountAmtTaxExcluded(getPrice());
        entity.setNonItemDiscountAmtTaxIncluded(getPrice());

    }

    private void setNonItemTaxDetailTwo(NonItemTaxDetail entity) {

        entity.setNonItemTaxDetailSubNumber(1);

        entity.setNonItemTaxType("");
        entity.setNonItemTaxName("");
        entity.setNonItemTaxAmountSign("");
        entity.setNonItemTaxAmt(getPrice());
        entity.setNonItemTaxRate(new BigDecimal(1));

    }

    private void setSalesTransactionTender(SalesTransactionTender entity) {

        entity.setTenderSubNumber(1);
        entity.setTenderGroupId("");
        entity.setTenderId("1");
        entity.setPaymentSign("");
        entity.setTaxIncludedPaymentAmount(getPrice());
        TenderInfo tenderInfo = new TenderInfo();
        setTenderInfo(tenderInfo);
        entity.setTenderInfoList(tenderInfo);

    }

    private void setTenderInfo(TenderInfo entity) {

        entity.setDiscountAmount(getPrice());
        entity.setDiscountRate(new BigDecimal(1));
        entity.setDiscountCodeIdCorporateId("");

        entity.setCouponDiscountAmountSetting(getPrice());
        entity.setCouponType("");
        entity.setCouponDiscountAmountSetting(getPrice());
        entity.setCouponMinUsageAmountThreshold(getPrice());
        entity.setCouponUserId("");
        entity.setCardNo("");
        entity.setCreditApprovalCode("");
        entity.setCreditProcessingSerialNumber("");
        entity.setCreditPaymentType("");
        entity.setCreditPaymentCount(1);
        entity.setCreditAffiliatedStoreNumber("");

    }

    private void setSalesTransactionTaxDetail(SalesTransactionTaxDetail entity) {

        entity.setTaxSubNumber(1);

        entity.setTaxGroup("");
        entity.setTaxAmountSign("");
        entity.setTaxAmount(getPrice());

    }

    private void setSalesTransactionTotal(SalesTransactionTotal entity) {

        entity.setTotalAmountSubNumber(1);
        entity.setTotalType("1");
        entity.setTotalAmountTaxExcluded(getPrice());
        entity.setTotalAmountTaxIncluded(getPrice());
        entity.setConsumptionTaxRate(new BigDecimal(1));
        entity.setSalesTransactionInformation1("");
        entity.setSalesTransactionInformation2("");
        entity.setSalesTransactionInformation3("");

    }

    private Price getPrice() {
        Price price = new Price();
        price.setCurrencyCode(Currency.getInstance(Locale.JAPAN));
        price.setValue(new BigDecimal(1));
        return price;
    }

    private void convertTSalesTransactionErrorDetailEntityForUpdateType(
            TransactionImportData transactionImportData,
            SalesTransactionErrorDetail salesTransactionErrorDetailEntity,
            String salesTransactionErrorId, String userId) {
        final String PROGRAM_SFCID = "SLS0300101";
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.now();

        tableCommonItem.setCreateUserId(userId);
        tableCommonItem.setCreateProgramId(PROGRAM_SFCID);
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId(userId);
        tableCommonItem.setUpdateProgramId(PROGRAM_SFCID);
        tableCommonItem.setUpdateDatetime(nowDateTime);
        modelMapper.map(tableCommonItem, salesTransactionErrorDetailEntity);
        modelMapper.map(transactionImportData, salesTransactionErrorDetailEntity);
        salesTransactionErrorDetailEntity.setSalesTransactionErrorId(salesTransactionErrorId);
        salesTransactionErrorDetailEntity.setErrorType(ErrorType.BUSINESS_ERROR.getErrorType());
        salesTransactionErrorDetailEntity.setErrorItemId1("integrated_order_id");
        salesTransactionErrorDetailEntity.setErrorItemValue1(transactionImportData.getIntegratedOrderId());
    }
}
