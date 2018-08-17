package com.fastretailing.dcp.sales.importtransaction.component;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
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
import com.fastretailing.dcp.sales.importtransaction.dto.TenderInfo;
import com.fastretailing.dcp.sales.importtransaction.dto.Transaction;
import com.fastretailing.dcp.sales.importtransaction.dto.TransactionImportData;
import com.fastretailing.dcp.sales.importtransaction.dto.TransactionItemDetail;
import com.fastretailing.dcp.sales.importtransaction.entity.ErrorEvacuationSalesOrderInformation;
import com.fastretailing.dcp.sales.importtransaction.entity.ErrorEvacuationSalesTransactionDetail;
import com.fastretailing.dcp.sales.importtransaction.entity.ErrorEvacuationSalesTransactionDetailInfo;
import com.fastretailing.dcp.sales.importtransaction.entity.ErrorEvacuationSalesTransactionDiscount;
import com.fastretailing.dcp.sales.importtransaction.entity.ErrorEvacuationSalesTransactionHeader;
import com.fastretailing.dcp.sales.importtransaction.entity.ErrorEvacuationSalesTransactionTax;
import com.fastretailing.dcp.sales.importtransaction.entity.ErrorEvacuationSalesTransactionTender;
import com.fastretailing.dcp.sales.importtransaction.entity.ErrorEvacuationSalesTransactionTenderInfo;
import com.fastretailing.dcp.sales.importtransaction.entity.ErrorEvacuationSalesTransactionTotalAmount;
import com.fastretailing.dcp.sales.importtransaction.repository.ErrorEvacuationSalesOrderInformationMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.ErrorEvacuationSalesTransactionDetailInfoMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.ErrorEvacuationSalesTransactionDetailMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.ErrorEvacuationSalesTransactionDiscountMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.ErrorEvacuationSalesTransactionHeaderMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.ErrorEvacuationSalesTransactionTaxMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.ErrorEvacuationSalesTransactionTenderInfoMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.ErrorEvacuationSalesTransactionTenderMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.ErrorEvacuationSalesTransactionTotalAmountMapper;
import com.fastretailing.dcp.storecommon.dto.Price;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class InsertErrorEvacuationServiceTest {

    @SpyBean
    InsertErrorEvacuationService insertErrorTableService;

    @MockBean
    private ErrorEvacuationTableDataConverter errorTableDataConverter;

    @MockBean
    private ErrorEvacuationSalesOrderInformationMapper salesErrorSalesOrderInformationEntityMapper;

    @MockBean
    private ErrorEvacuationSalesTransactionHeaderMapper salesErrorSalesTransactionHeaderEntityMapper;

    @MockBean
    private ErrorEvacuationSalesTransactionDetailMapper salesErrorSalesTransactionDetailEntityMapper;

    @MockBean
    private ErrorEvacuationSalesTransactionDetailInfoMapper salesErrorSalesTransactionDetailInfoEntityMapper;

    @MockBean
    private ErrorEvacuationSalesTransactionDiscountMapper salesErrorSalesTransactionDiscountEntityMapper;

    @MockBean
    private ErrorEvacuationSalesTransactionTaxMapper salesErrorSalesTransactionTaxEntityMapper;

    @MockBean
    private ErrorEvacuationSalesTransactionTenderMapper salesErrorSalesTransactionTenderEntityMapper;

    @MockBean
    private ErrorEvacuationSalesTransactionTenderInfoMapper salesErrorSalesTransactionTenderInfoEntityMapper;

    @MockBean
    private ErrorEvacuationSalesTransactionTotalAmountMapper salesErrorSalesTransactionTotalAmountEntityMapper;

    private TransactionImportData transactionImportData = new TransactionImportData();

    private String userId = "uid0001";

    private String salesTransactionErrorId = "seid00001";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        setTransactionImportData(transactionImportData);
    }

    @Test
    public void testSuccess() throws Exception {
        when(salesErrorSalesOrderInformationEntityMapper
                .insertSelective(any(ErrorEvacuationSalesOrderInformation.class))).thenReturn(1);
        when(salesErrorSalesTransactionHeaderEntityMapper
                .insertSelective(any(ErrorEvacuationSalesTransactionHeader.class))).thenReturn(1);
        when(salesErrorSalesTransactionDetailEntityMapper
                .insertSelective(any(ErrorEvacuationSalesTransactionDetail.class))).thenReturn(1);
        when(salesErrorSalesTransactionDetailInfoEntityMapper
                .insertSelective(any(ErrorEvacuationSalesTransactionDetailInfo.class)))
                        .thenReturn(1);
        when(salesErrorSalesTransactionDiscountEntityMapper
                .insertSelective(any(ErrorEvacuationSalesTransactionDiscount.class))).thenReturn(1);
        when(salesErrorSalesTransactionTaxEntityMapper
                .insertSelective(any(ErrorEvacuationSalesTransactionTax.class))).thenReturn(1);
        when(salesErrorSalesTransactionTenderEntityMapper
                .insertSelective(any(ErrorEvacuationSalesTransactionTender.class))).thenReturn(1);
        when(salesErrorSalesTransactionTenderInfoEntityMapper
                .insertSelective(any(ErrorEvacuationSalesTransactionTenderInfo.class)))
                        .thenReturn(1);
        when(salesErrorSalesTransactionTotalAmountEntityMapper
                .insertSelective(any(ErrorEvacuationSalesTransactionTotalAmount.class)))
                        .thenReturn(1);

        when(errorTableDataConverter.convertTErrorEvacuationSalesOrderInformationEntityForInsert(
                transactionImportData, userId, salesTransactionErrorId))
                        .thenReturn(new ErrorEvacuationSalesOrderInformation());
        when(errorTableDataConverter.convertTErrorEvacuationSalesTransactionHeaderEntityForInsert(
                any(Transaction.class), anyString(), anyString(), anyString()))
                        .thenReturn(new ErrorEvacuationSalesTransactionHeader());
        when(errorTableDataConverter.convertTErrorEvacuationSalesTransactionDetailEntityForInsert(
                any(TransactionItemDetail.class), anyString(), anyString(), anyInt(), anyString(),
                anyString(), anyInt(), anyInt()))
                        .thenReturn(new ErrorEvacuationSalesTransactionDetail());
        when(errorTableDataConverter
                .convertTErrorEvacuationSalesTransactionDetailEntityForInsertOutside(
                        any(NonItemDetail.class), anyString(), anyString(), anyInt(), anyString(),
                        anyString(), anyInt(), anyInt(), anyString()))
                                .thenReturn(new ErrorEvacuationSalesTransactionDetail());
        when(errorTableDataConverter
                .convertTErrorEvacuationSalesTransactionDetailInfoEntityForInsert(
                        any(NonItemInfo.class), anyString(), anyString(), anyInt(), anyString(),
                        anyInt(), anyInt(), anyString()))
                                .thenReturn(new ErrorEvacuationSalesTransactionDetailInfo());
        when(errorTableDataConverter.convertTErrorEvacuationSalesTransactionTenderEntityForInsert(
                any(SalesTransactionTender.class), anyString(), anyString(), anyInt(), anyString(),
                anyInt(), anyString())).thenReturn(new ErrorEvacuationSalesTransactionTender());
        when(errorTableDataConverter
                .convertTErrorEvacuationSalesTransactionTenderInfoEntityForInsert(
                        any(TenderInfo.class), anyString(), anyString(), anyInt(), anyString(),
                        anyString(), anyString(), anyString(), anyInt()))
                                .thenReturn(new ErrorEvacuationSalesTransactionTenderInfo());
        when(errorTableDataConverter
                .convertTErrorEvacuationSalesTransactionTaxEntityForInsertTransaction(
                        any(SalesTransactionTaxDetail.class), anyString(), anyString(), anyInt(),
                        anyString(), anyInt(), anyInt(), anyString()))
                                .thenReturn(new ErrorEvacuationSalesTransactionTax());
        when(errorTableDataConverter
                .convertTErrorEvacuationSalesTransactionTotalAmountEntityForInsert(
                        any(SalesTransactionTotal.class), anyString(), anyString(), anyInt(),
                        anyString(), anyInt(), anyString()))
                                .thenReturn(new ErrorEvacuationSalesTransactionTotalAmount());
        when(errorTableDataConverter
                .convertTErrorEvacuationSalesTransactionDiscountEntityForInsertNon(
                        any(NonItemDiscountDetail.class), anyString(), anyString(), anyInt(),
                        anyString(), anyInt(), anyInt(), anyString()))
                                .thenReturn(new ErrorEvacuationSalesTransactionDiscount());
        when(errorTableDataConverter.convertTErrorEvacuationSalesTransactionTaxEntityForInsertNon(
                any(NonItemTaxDetail.class), anyString(), anyString(), anyInt(), anyString(),
                anyInt(), anyInt(), anyString()))
                        .thenReturn(new ErrorEvacuationSalesTransactionTax());
        when(errorTableDataConverter.convertTErrorEvacuationSalesTransactionDiscountEntityForInsert(
                any(ItemDiscount.class), anyString(), anyString(), anyInt(), anyString(), anyInt(),
                anyInt(), anyString())).thenReturn(new ErrorEvacuationSalesTransactionDiscount());
        when(errorTableDataConverter.convertTErrorEvacuationSalesTransactionTaxEntityForInsert(
                any(ItemTaxDetail.class), anyString(), anyString(), anyInt(), anyString(), anyInt(),
                anyInt(), anyString())).thenReturn(new ErrorEvacuationSalesTransactionTax());


        insertErrorTableService.insertErrorEvacuationTable(transactionImportData,
                salesTransactionErrorId, userId);

        ArgumentCaptor<Transaction> transactionArgumentCaptor =
                ArgumentCaptor.forClass(Transaction.class);
        ArgumentCaptor<TransactionItemDetail> transactionItemDetailArgumentCaptor =
                ArgumentCaptor.forClass(TransactionItemDetail.class);
        ArgumentCaptor<NonItemDetail> nonItemDetailArgumentCaptor =
                ArgumentCaptor.forClass(NonItemDetail.class);
        ArgumentCaptor<NonItemInfo> nonItemInfoArgumentCaptor =
                ArgumentCaptor.forClass(NonItemInfo.class);
        ArgumentCaptor<SalesTransactionTender> salesTransactionTenderArgumentCaptor =
                ArgumentCaptor.forClass(SalesTransactionTender.class);
        ArgumentCaptor<TenderInfo> tenderInfoArgumentCaptor =
                ArgumentCaptor.forClass(TenderInfo.class);
        ArgumentCaptor<SalesTransactionTaxDetail> salesTransactionTaxDetailArgumentCaptor =
                ArgumentCaptor.forClass(SalesTransactionTaxDetail.class);
        ArgumentCaptor<SalesTransactionTotal> salesTransactionTotalArgumentCaptor =
                ArgumentCaptor.forClass(SalesTransactionTotal.class);
        ArgumentCaptor<NonItemDiscountDetail> nonItemDiscountDetailArgumentCaptor =
                ArgumentCaptor.forClass(NonItemDiscountDetail.class);
        ArgumentCaptor<NonItemTaxDetail> nonItemTaxDetailArgumentCaptor =
                ArgumentCaptor.forClass(NonItemTaxDetail.class);
        ArgumentCaptor<ItemDiscount> itemDiscountArgumentCaptor =
                ArgumentCaptor.forClass(ItemDiscount.class);
        ArgumentCaptor<ItemTaxDetail> itemTaxDetailArgumentCaptor =
                ArgumentCaptor.forClass(ItemTaxDetail.class);

        verify(errorTableDataConverter, times(1))
                .convertTErrorEvacuationSalesOrderInformationEntityForInsert(transactionImportData,
                        userId, salesTransactionErrorId);
        verify(errorTableDataConverter, times(transactionImportData.getTransactionList().size()))
                .convertTErrorEvacuationSalesTransactionHeaderEntityForInsert(
                        transactionArgumentCaptor.capture(), anyString(), anyString(), anyString());
        verify(errorTableDataConverter,
                times(transactionImportData.getTransactionList().stream().mapToInt(tran -> {
                    return tran.getTransactionItemDetailList().size();
                }).sum())).convertTErrorEvacuationSalesTransactionDetailEntityForInsert(
                        transactionItemDetailArgumentCaptor.capture(), anyString(), anyString(),
                        anyInt(), anyString(), anyString(), anyInt(), anyInt());
        verify(errorTableDataConverter,
                times(transactionImportData.getTransactionList().stream().mapToInt(tran -> {
                    return tran.getTransactionItemDetailList()
                            .stream()
                            .mapToInt(item -> item.getNonItemDetailListByItem().size())
                            .sum() + tran.getNonItemDetailList().size();
                }).sum())).convertTErrorEvacuationSalesTransactionDetailEntityForInsertOutside(
                        nonItemDetailArgumentCaptor.capture(), anyString(), anyString(), anyInt(),
                        anyString(), anyString(), anyInt(), anyInt(), anyString());
        verify(errorTableDataConverter,
                times(transactionImportData.getTransactionList().stream().mapToInt(tran -> {
                    return tran.getTransactionItemDetailList().stream().mapToInt(itemDetail -> {
                        return itemDetail.getNonItemDetailListByItem().size();
                    }).sum() + tran.getNonItemDetailList().size();
                }).sum())).convertTErrorEvacuationSalesTransactionDetailInfoEntityForInsert(
                        nonItemInfoArgumentCaptor.capture(), anyString(), anyString(), anyInt(),
                        anyString(), anyInt(), anyInt(), anyString());
        verify(errorTableDataConverter,
                times(transactionImportData.getTransactionList().stream().mapToInt(tran -> {
                    return tran.getSalesTransactionTenderList().size();
                }).sum())).convertTErrorEvacuationSalesTransactionTenderEntityForInsert(
                        salesTransactionTenderArgumentCaptor.capture(), anyString(), anyString(),
                        anyInt(), anyString(), anyInt(), anyString());
        verify(errorTableDataConverter,
                times(transactionImportData.getTransactionList().stream().mapToInt(tran -> {
                    return tran.getSalesTransactionTenderList().stream().mapToInt(tender -> {
                        return 1;
                    }).sum();
                }).sum())).convertTErrorEvacuationSalesTransactionTenderInfoEntityForInsert(
                        tenderInfoArgumentCaptor.capture(), anyString(), anyString(), anyInt(),
                        anyString(), anyString(), anyString(), anyString(), anyInt());
        verify(errorTableDataConverter,
                times(transactionImportData.getTransactionList().stream().mapToInt(tran -> {
                    return tran.getSalesTransactionTaxDetailList().size();
                }).sum())).convertTErrorEvacuationSalesTransactionTaxEntityForInsertTransaction(
                        salesTransactionTaxDetailArgumentCaptor.capture(), anyString(), anyString(),
                        anyInt(), anyString(), anyInt(), anyInt(), anyString());
        verify(errorTableDataConverter,
                times(transactionImportData.getTransactionList().stream().mapToInt(tran -> {
                    return tran.getSalesTransactionTotalList().size();
                }).sum())).convertTErrorEvacuationSalesTransactionTotalAmountEntityForInsert(
                        salesTransactionTotalArgumentCaptor.capture(), anyString(), anyString(),
                        anyInt(), anyString(), anyInt(), anyString());
        verify(errorTableDataConverter,
                times(transactionImportData.getTransactionList().stream().mapToInt(tran -> {
                    return tran.getTransactionItemDetailList().stream().mapToInt(itemDetail -> {
                        return itemDetail.getNonItemDetailListByItem()
                                .stream()
                                .mapToInt(nonItemDetail -> {
                                    return nonItemDetail.getNonItemDiscountDetailList().size();
                                })
                                .sum();
                    }).sum() + tran.getNonItemDetailList().stream().mapToInt(nonItemDetail -> {
                        return nonItemDetail.getNonItemDiscountDetailList().size();
                    }).sum();
                }).sum())).convertTErrorEvacuationSalesTransactionDiscountEntityForInsertNon(
                        nonItemDiscountDetailArgumentCaptor.capture(), anyString(), anyString(),
                        anyInt(), anyString(), anyInt(), anyInt(), anyString());
        verify(errorTableDataConverter,
                times(transactionImportData.getTransactionList().stream().mapToInt(tran -> {
                    return tran.getTransactionItemDetailList().stream().mapToInt(itemDetail -> {
                        return itemDetail.getNonItemDetailListByItem()
                                .stream()
                                .mapToInt(nonItemDetail -> {
                                    return nonItemDetail.getNonItemTaxDetailList().size();
                                })
                                .sum();
                    }).sum() + tran.getNonItemDetailList().stream().mapToInt(nonItemDetail -> {
                        return nonItemDetail.getNonItemTaxDetailList().size();
                    }).sum();
                }).sum())).convertTErrorEvacuationSalesTransactionTaxEntityForInsertNon(
                        nonItemTaxDetailArgumentCaptor.capture(), anyString(), anyString(),
                        anyInt(), anyString(), anyInt(), anyInt(), anyString());
        verify(errorTableDataConverter,
                times(transactionImportData.getTransactionList().stream().mapToInt(tran -> {
                    return tran.getTransactionItemDetailList().stream().mapToInt(itemDetail -> {
                        return itemDetail.getItemDiscountList().size();
                    }).sum();
                }).sum())).convertTErrorEvacuationSalesTransactionDiscountEntityForInsert(
                        itemDiscountArgumentCaptor.capture(), anyString(), anyString(), anyInt(),
                        anyString(), anyInt(), anyInt(), anyString());
        verify(errorTableDataConverter,
                times(transactionImportData.getTransactionList().stream().mapToInt(tran -> {
                    return tran.getTransactionItemDetailList().stream().mapToInt(itemDetail -> {
                        return itemDetail.getItemTaxDetailList().size();
                    }).sum();
                }).sum())).convertTErrorEvacuationSalesTransactionTaxEntityForInsert(
                        itemTaxDetailArgumentCaptor.capture(), anyString(), anyString(), anyInt(),
                        anyString(), anyInt(), anyInt(), anyString());

        transactionImportData.getTransactionList().forEach(transactionTmp -> {
            assertThat(transactionArgumentCaptor.getAllValues()).contains(transactionTmp);
            transactionTmp.getTransactionItemDetailList().forEach(itemDetail -> {
                assertThat(transactionItemDetailArgumentCaptor.getAllValues()).contains(itemDetail);
                itemDetail.getNonItemDetailListByItem().forEach(nonItemDetail -> {
                    assertThat(nonItemDetailArgumentCaptor.getAllValues()).contains(nonItemDetail);
                });
                itemDetail.getItemTaxDetailList().forEach(taxDetail -> {
                    assertThat(itemTaxDetailArgumentCaptor.getAllValues()).contains(taxDetail);
                });
                itemDetail.getItemDiscountList().forEach(discount -> {
                    assertThat(itemDiscountArgumentCaptor.getAllValues()).contains(discount);
                });
            });
            transactionTmp.getNonItemDetailList().forEach(nonItemDetail -> {
                assertThat(nonItemDetailArgumentCaptor.getAllValues()).contains(nonItemDetail);
                nonItemDetail.getNonItemDiscountDetailList().forEach(discount -> {
                    assertThat(nonItemDiscountDetailArgumentCaptor.getAllValues())
                            .contains(discount);
                });
                nonItemDetail.getNonItemTaxDetailList().forEach(nonTax -> {
                    assertThat(nonItemTaxDetailArgumentCaptor.getAllValues()).contains(nonTax);
                });
            });
            transactionTmp.getSalesTransactionTaxDetailList().forEach(taxDetail -> {
                assertThat(salesTransactionTaxDetailArgumentCaptor.getAllValues())
                        .contains(taxDetail);
            });
            transactionTmp.getSalesTransactionTenderList().forEach(tender -> {
                assertThat(salesTransactionTenderArgumentCaptor.getAllValues()).contains(tender);
                assertThat(tenderInfoArgumentCaptor.getAllValues())
                        .contains(tender.getTenderInfoList());
            });
            transactionTmp.getSalesTransactionTotalList().forEach(total -> {
                assertThat(salesTransactionTotalArgumentCaptor.getAllValues()).contains(total);
            });
        });
    }

    @Test
    public void testEmptyTransaction() throws Exception {
        transactionImportData.setTransactionList(new ArrayList<>());
        when(salesErrorSalesOrderInformationEntityMapper
                .insertSelective(any(ErrorEvacuationSalesOrderInformation.class))).thenReturn(1);
        when(salesErrorSalesTransactionHeaderEntityMapper
                .insertSelective(any(ErrorEvacuationSalesTransactionHeader.class))).thenReturn(1);
        when(salesErrorSalesTransactionDetailEntityMapper
                .insertSelective(any(ErrorEvacuationSalesTransactionDetail.class))).thenReturn(1);
        when(salesErrorSalesTransactionDetailInfoEntityMapper
                .insertSelective(any(ErrorEvacuationSalesTransactionDetailInfo.class)))
                        .thenReturn(1);
        when(salesErrorSalesTransactionDiscountEntityMapper
                .insertSelective(any(ErrorEvacuationSalesTransactionDiscount.class))).thenReturn(1);
        when(salesErrorSalesTransactionTaxEntityMapper
                .insertSelective(any(ErrorEvacuationSalesTransactionTax.class))).thenReturn(1);
        when(salesErrorSalesTransactionTenderEntityMapper
                .insertSelective(any(ErrorEvacuationSalesTransactionTender.class))).thenReturn(1);
        when(salesErrorSalesTransactionTenderInfoEntityMapper
                .insertSelective(any(ErrorEvacuationSalesTransactionTenderInfo.class)))
                        .thenReturn(1);
        when(salesErrorSalesTransactionTotalAmountEntityMapper
                .insertSelective(any(ErrorEvacuationSalesTransactionTotalAmount.class)))
                        .thenReturn(1);

        when(errorTableDataConverter.convertTErrorEvacuationSalesOrderInformationEntityForInsert(
                transactionImportData, userId, salesTransactionErrorId))
                        .thenReturn(new ErrorEvacuationSalesOrderInformation());
        when(errorTableDataConverter.convertTErrorEvacuationSalesTransactionHeaderEntityForInsert(
                any(Transaction.class), anyString(), anyString(), anyString()))
                        .thenReturn(new ErrorEvacuationSalesTransactionHeader());
        when(errorTableDataConverter.convertTErrorEvacuationSalesTransactionDetailEntityForInsert(
                any(TransactionItemDetail.class), anyString(), anyString(), anyInt(), anyString(),
                anyString(), anyInt(), anyInt()))
                        .thenReturn(new ErrorEvacuationSalesTransactionDetail());
        when(errorTableDataConverter
                .convertTErrorEvacuationSalesTransactionDetailEntityForInsertOutside(
                        any(NonItemDetail.class), anyString(), anyString(), anyInt(), anyString(),
                        anyString(), anyInt(), anyInt(), anyString()))
                                .thenReturn(new ErrorEvacuationSalesTransactionDetail());
        when(errorTableDataConverter
                .convertTErrorEvacuationSalesTransactionDetailInfoEntityForInsert(
                        any(NonItemInfo.class), anyString(), anyString(), anyInt(), anyString(),
                        anyInt(), anyInt(), anyString()))
                                .thenReturn(new ErrorEvacuationSalesTransactionDetailInfo());
        when(errorTableDataConverter.convertTErrorEvacuationSalesTransactionTenderEntityForInsert(
                any(SalesTransactionTender.class), anyString(), anyString(), anyInt(), anyString(),
                anyInt(), anyString())).thenReturn(new ErrorEvacuationSalesTransactionTender());
        when(errorTableDataConverter
                .convertTErrorEvacuationSalesTransactionTenderInfoEntityForInsert(
                        any(TenderInfo.class), anyString(), anyString(), anyInt(), anyString(),
                        anyString(), anyString(), anyString(), anyInt()))
                                .thenReturn(new ErrorEvacuationSalesTransactionTenderInfo());
        when(errorTableDataConverter
                .convertTErrorEvacuationSalesTransactionTaxEntityForInsertTransaction(
                        any(SalesTransactionTaxDetail.class), anyString(), anyString(), anyInt(),
                        anyString(), anyInt(), anyInt(), anyString()))
                                .thenReturn(new ErrorEvacuationSalesTransactionTax());
        when(errorTableDataConverter
                .convertTErrorEvacuationSalesTransactionTotalAmountEntityForInsert(
                        any(SalesTransactionTotal.class), anyString(), anyString(), anyInt(),
                        anyString(), anyInt(), anyString()))
                                .thenReturn(new ErrorEvacuationSalesTransactionTotalAmount());
        when(errorTableDataConverter
                .convertTErrorEvacuationSalesTransactionDiscountEntityForInsertNon(
                        any(NonItemDiscountDetail.class), anyString(), anyString(), anyInt(),
                        anyString(), anyInt(), anyInt(), anyString()))
                                .thenReturn(new ErrorEvacuationSalesTransactionDiscount());
        when(errorTableDataConverter.convertTErrorEvacuationSalesTransactionTaxEntityForInsertNon(
                any(NonItemTaxDetail.class), anyString(), anyString(), anyInt(), anyString(),
                anyInt(), anyInt(), anyString()))
                        .thenReturn(new ErrorEvacuationSalesTransactionTax());
        when(errorTableDataConverter.convertTErrorEvacuationSalesTransactionDiscountEntityForInsert(
                any(ItemDiscount.class), anyString(), anyString(), anyInt(), anyString(), anyInt(),
                anyInt(), anyString())).thenReturn(new ErrorEvacuationSalesTransactionDiscount());
        when(errorTableDataConverter.convertTErrorEvacuationSalesTransactionTaxEntityForInsert(
                any(ItemTaxDetail.class), anyString(), anyString(), anyInt(), anyString(), anyInt(),
                anyInt(), anyString())).thenReturn(new ErrorEvacuationSalesTransactionTax());


        insertErrorTableService.insertErrorEvacuationTable(transactionImportData,
                salesTransactionErrorId, userId);

        ArgumentCaptor<Transaction> transactionArgumentCaptor =
                ArgumentCaptor.forClass(Transaction.class);
        ArgumentCaptor<TransactionItemDetail> transactionItemDetailArgumentCaptor =
                ArgumentCaptor.forClass(TransactionItemDetail.class);
        ArgumentCaptor<NonItemDetail> nonItemDetailArgumentCaptor =
                ArgumentCaptor.forClass(NonItemDetail.class);
        ArgumentCaptor<NonItemInfo> nonItemInfoArgumentCaptor =
                ArgumentCaptor.forClass(NonItemInfo.class);
        ArgumentCaptor<SalesTransactionTender> salesTransactionTenderArgumentCaptor =
                ArgumentCaptor.forClass(SalesTransactionTender.class);
        ArgumentCaptor<TenderInfo> tenderInfoArgumentCaptor =
                ArgumentCaptor.forClass(TenderInfo.class);
        ArgumentCaptor<SalesTransactionTaxDetail> salesTransactionTaxDetailArgumentCaptor =
                ArgumentCaptor.forClass(SalesTransactionTaxDetail.class);
        ArgumentCaptor<SalesTransactionTotal> salesTransactionTotalArgumentCaptor =
                ArgumentCaptor.forClass(SalesTransactionTotal.class);
        ArgumentCaptor<NonItemDiscountDetail> nonItemDiscountDetailArgumentCaptor =
                ArgumentCaptor.forClass(NonItemDiscountDetail.class);
        ArgumentCaptor<NonItemTaxDetail> nonItemTaxDetailArgumentCaptor =
                ArgumentCaptor.forClass(NonItemTaxDetail.class);
        ArgumentCaptor<ItemDiscount> itemDiscountArgumentCaptor =
                ArgumentCaptor.forClass(ItemDiscount.class);
        ArgumentCaptor<ItemTaxDetail> itemTaxDetailArgumentCaptor =
                ArgumentCaptor.forClass(ItemTaxDetail.class);

        verify(errorTableDataConverter, times(1))
                .convertTErrorEvacuationSalesOrderInformationEntityForInsert(transactionImportData,
                        userId, salesTransactionErrorId);
        verify(errorTableDataConverter, times(transactionImportData.getTransactionList().size()))
                .convertTErrorEvacuationSalesTransactionHeaderEntityForInsert(
                        transactionArgumentCaptor.capture(), anyString(), anyString(), anyString());
        verify(errorTableDataConverter, times(0))
                .convertTErrorEvacuationSalesTransactionDetailEntityForInsert(
                        transactionItemDetailArgumentCaptor.capture(), anyString(), anyString(),
                        anyInt(), anyString(), anyString(), anyInt(), anyInt());
        verify(errorTableDataConverter, times(0))
                .convertTErrorEvacuationSalesTransactionDetailEntityForInsertOutside(
                        nonItemDetailArgumentCaptor.capture(), anyString(), anyString(), anyInt(),
                        anyString(), anyString(), anyInt(), anyInt(), anyString());
        verify(errorTableDataConverter, times(0))
                .convertTErrorEvacuationSalesTransactionDetailInfoEntityForInsert(
                        nonItemInfoArgumentCaptor.capture(), anyString(), anyString(), anyInt(),
                        anyString(), anyInt(), anyInt(), anyString());
        verify(errorTableDataConverter, times(0))
                .convertTErrorEvacuationSalesTransactionTenderEntityForInsert(
                        salesTransactionTenderArgumentCaptor.capture(), anyString(), anyString(),
                        anyInt(), anyString(), anyInt(), anyString());
        verify(errorTableDataConverter, times(0))
                .convertTErrorEvacuationSalesTransactionTenderInfoEntityForInsert(
                        tenderInfoArgumentCaptor.capture(), anyString(), anyString(), anyInt(),
                        anyString(), anyString(), anyString(), anyString(), anyInt());
        verify(errorTableDataConverter, times(0))
                .convertTErrorEvacuationSalesTransactionTaxEntityForInsertTransaction(
                        salesTransactionTaxDetailArgumentCaptor.capture(), anyString(), anyString(),
                        anyInt(), anyString(), anyInt(), anyInt(), anyString());
        verify(errorTableDataConverter, times(0))
                .convertTErrorEvacuationSalesTransactionTotalAmountEntityForInsert(
                        salesTransactionTotalArgumentCaptor.capture(), anyString(), anyString(),
                        anyInt(), anyString(), anyInt(), anyString());
        verify(errorTableDataConverter, times(0))
                .convertTErrorEvacuationSalesTransactionDiscountEntityForInsertNon(
                        nonItemDiscountDetailArgumentCaptor.capture(), anyString(), anyString(),
                        anyInt(), anyString(), anyInt(), anyInt(), anyString());
        verify(errorTableDataConverter, times(0))
                .convertTErrorEvacuationSalesTransactionTaxEntityForInsertNon(
                        nonItemTaxDetailArgumentCaptor.capture(), anyString(), anyString(),
                        anyInt(), anyString(), anyInt(), anyInt(), anyString());
        verify(errorTableDataConverter, times(0))
                .convertTErrorEvacuationSalesTransactionDiscountEntityForInsert(
                        itemDiscountArgumentCaptor.capture(), anyString(), anyString(), anyInt(),
                        anyString(), anyInt(), anyInt(), anyString());
        verify(errorTableDataConverter, times(0))
                .convertTErrorEvacuationSalesTransactionTaxEntityForInsert(
                        itemTaxDetailArgumentCaptor.capture(), anyString(), anyString(), anyInt(),
                        anyString(), anyInt(), anyInt(), anyString());
    }

    @Test
    public void testEmptyInTransaction() throws Exception {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.setNonItemDetailList(new ArrayList<>());
            transaction.setSalesTransactionTaxDetailList(new ArrayList<>());
            transaction.setSalesTransactionTenderList(new ArrayList<>());
            transaction.setSalesTransactionTotalList(new ArrayList<>());
            transaction.setTransactionItemDetailList(new ArrayList<>());
        });
        when(salesErrorSalesOrderInformationEntityMapper
                .insertSelective(any(ErrorEvacuationSalesOrderInformation.class))).thenReturn(1);
        when(salesErrorSalesTransactionHeaderEntityMapper
                .insertSelective(any(ErrorEvacuationSalesTransactionHeader.class))).thenReturn(1);
        when(salesErrorSalesTransactionDetailEntityMapper
                .insertSelective(any(ErrorEvacuationSalesTransactionDetail.class))).thenReturn(1);
        when(salesErrorSalesTransactionDetailInfoEntityMapper
                .insertSelective(any(ErrorEvacuationSalesTransactionDetailInfo.class)))
                        .thenReturn(1);
        when(salesErrorSalesTransactionDiscountEntityMapper
                .insertSelective(any(ErrorEvacuationSalesTransactionDiscount.class))).thenReturn(1);
        when(salesErrorSalesTransactionTaxEntityMapper
                .insertSelective(any(ErrorEvacuationSalesTransactionTax.class))).thenReturn(1);
        when(salesErrorSalesTransactionTenderEntityMapper
                .insertSelective(any(ErrorEvacuationSalesTransactionTender.class))).thenReturn(1);
        when(salesErrorSalesTransactionTenderInfoEntityMapper
                .insertSelective(any(ErrorEvacuationSalesTransactionTenderInfo.class)))
                        .thenReturn(1);
        when(salesErrorSalesTransactionTotalAmountEntityMapper
                .insertSelective(any(ErrorEvacuationSalesTransactionTotalAmount.class)))
                        .thenReturn(1);

        when(errorTableDataConverter.convertTErrorEvacuationSalesOrderInformationEntityForInsert(
                transactionImportData, userId, salesTransactionErrorId))
                        .thenReturn(new ErrorEvacuationSalesOrderInformation());
        when(errorTableDataConverter.convertTErrorEvacuationSalesTransactionHeaderEntityForInsert(
                any(Transaction.class), anyString(), anyString(), anyString()))
                        .thenReturn(new ErrorEvacuationSalesTransactionHeader());
        when(errorTableDataConverter.convertTErrorEvacuationSalesTransactionDetailEntityForInsert(
                any(TransactionItemDetail.class), anyString(), anyString(), anyInt(), anyString(),
                anyString(), anyInt(), anyInt()))
                        .thenReturn(new ErrorEvacuationSalesTransactionDetail());
        when(errorTableDataConverter
                .convertTErrorEvacuationSalesTransactionDetailEntityForInsertOutside(
                        any(NonItemDetail.class), anyString(), anyString(), anyInt(), anyString(),
                        anyString(), anyInt(), anyInt(), anyString()))
                                .thenReturn(new ErrorEvacuationSalesTransactionDetail());
        when(errorTableDataConverter
                .convertTErrorEvacuationSalesTransactionDetailInfoEntityForInsert(
                        any(NonItemInfo.class), anyString(), anyString(), anyInt(), anyString(),
                        anyInt(), anyInt(), anyString()))
                                .thenReturn(new ErrorEvacuationSalesTransactionDetailInfo());
        when(errorTableDataConverter.convertTErrorEvacuationSalesTransactionTenderEntityForInsert(
                any(SalesTransactionTender.class), anyString(), anyString(), anyInt(), anyString(),
                anyInt(), anyString())).thenReturn(new ErrorEvacuationSalesTransactionTender());
        when(errorTableDataConverter
                .convertTErrorEvacuationSalesTransactionTenderInfoEntityForInsert(
                        any(TenderInfo.class), anyString(), anyString(), anyInt(), anyString(),
                        anyString(), anyString(), anyString(), anyInt()))
                                .thenReturn(new ErrorEvacuationSalesTransactionTenderInfo());
        when(errorTableDataConverter
                .convertTErrorEvacuationSalesTransactionTaxEntityForInsertTransaction(
                        any(SalesTransactionTaxDetail.class), anyString(), anyString(), anyInt(),
                        anyString(), anyInt(), anyInt(), anyString()))
                                .thenReturn(new ErrorEvacuationSalesTransactionTax());
        when(errorTableDataConverter
                .convertTErrorEvacuationSalesTransactionTotalAmountEntityForInsert(
                        any(SalesTransactionTotal.class), anyString(), anyString(), anyInt(),
                        anyString(), anyInt(), anyString()))
                                .thenReturn(new ErrorEvacuationSalesTransactionTotalAmount());
        when(errorTableDataConverter
                .convertTErrorEvacuationSalesTransactionDiscountEntityForInsertNon(
                        any(NonItemDiscountDetail.class), anyString(), anyString(), anyInt(),
                        anyString(), anyInt(), anyInt(), anyString()))
                                .thenReturn(new ErrorEvacuationSalesTransactionDiscount());
        when(errorTableDataConverter.convertTErrorEvacuationSalesTransactionTaxEntityForInsertNon(
                any(NonItemTaxDetail.class), anyString(), anyString(), anyInt(), anyString(),
                anyInt(), anyInt(), anyString()))
                        .thenReturn(new ErrorEvacuationSalesTransactionTax());
        when(errorTableDataConverter.convertTErrorEvacuationSalesTransactionDiscountEntityForInsert(
                any(ItemDiscount.class), anyString(), anyString(), anyInt(), anyString(), anyInt(),
                anyInt(), anyString())).thenReturn(new ErrorEvacuationSalesTransactionDiscount());
        when(errorTableDataConverter.convertTErrorEvacuationSalesTransactionTaxEntityForInsert(
                any(ItemTaxDetail.class), anyString(), anyString(), anyInt(), anyString(), anyInt(),
                anyInt(), anyString())).thenReturn(new ErrorEvacuationSalesTransactionTax());


        insertErrorTableService.insertErrorEvacuationTable(transactionImportData,
                salesTransactionErrorId, userId);

        ArgumentCaptor<Transaction> transactionArgumentCaptor =
                ArgumentCaptor.forClass(Transaction.class);
        ArgumentCaptor<TransactionItemDetail> transactionItemDetailArgumentCaptor =
                ArgumentCaptor.forClass(TransactionItemDetail.class);
        ArgumentCaptor<NonItemDetail> nonItemDetailArgumentCaptor =
                ArgumentCaptor.forClass(NonItemDetail.class);
        ArgumentCaptor<NonItemInfo> nonItemInfoArgumentCaptor =
                ArgumentCaptor.forClass(NonItemInfo.class);
        ArgumentCaptor<SalesTransactionTender> salesTransactionTenderArgumentCaptor =
                ArgumentCaptor.forClass(SalesTransactionTender.class);
        ArgumentCaptor<TenderInfo> tenderInfoArgumentCaptor =
                ArgumentCaptor.forClass(TenderInfo.class);
        ArgumentCaptor<SalesTransactionTaxDetail> salesTransactionTaxDetailArgumentCaptor =
                ArgumentCaptor.forClass(SalesTransactionTaxDetail.class);
        ArgumentCaptor<SalesTransactionTotal> salesTransactionTotalArgumentCaptor =
                ArgumentCaptor.forClass(SalesTransactionTotal.class);
        ArgumentCaptor<NonItemDiscountDetail> nonItemDiscountDetailArgumentCaptor =
                ArgumentCaptor.forClass(NonItemDiscountDetail.class);
        ArgumentCaptor<NonItemTaxDetail> nonItemTaxDetailArgumentCaptor =
                ArgumentCaptor.forClass(NonItemTaxDetail.class);
        ArgumentCaptor<ItemDiscount> itemDiscountArgumentCaptor =
                ArgumentCaptor.forClass(ItemDiscount.class);
        ArgumentCaptor<ItemTaxDetail> itemTaxDetailArgumentCaptor =
                ArgumentCaptor.forClass(ItemTaxDetail.class);

        verify(errorTableDataConverter, times(1))
                .convertTErrorEvacuationSalesOrderInformationEntityForInsert(transactionImportData,
                        userId, salesTransactionErrorId);
        verify(errorTableDataConverter, times(transactionImportData.getTransactionList().size()))
                .convertTErrorEvacuationSalesTransactionHeaderEntityForInsert(
                        transactionArgumentCaptor.capture(), anyString(), anyString(), anyString());
        verify(errorTableDataConverter,
                times(transactionImportData.getTransactionList().stream().mapToInt(tran -> {
                    return tran.getTransactionItemDetailList().size();
                }).sum())).convertTErrorEvacuationSalesTransactionDetailEntityForInsert(
                        transactionItemDetailArgumentCaptor.capture(), anyString(), anyString(),
                        anyInt(), anyString(), anyString(), anyInt(), anyInt());
        verify(errorTableDataConverter,
                times(transactionImportData.getTransactionList().stream().mapToInt(tran -> {
                    return tran.getTransactionItemDetailList()
                            .stream()
                            .mapToInt(item -> item.getNonItemDetailListByItem().size())
                            .sum() + tran.getNonItemDetailList().size();
                }).sum())).convertTErrorEvacuationSalesTransactionDetailEntityForInsertOutside(
                        nonItemDetailArgumentCaptor.capture(), anyString(), anyString(), anyInt(),
                        anyString(), anyString(), anyInt(), anyInt(), anyString());
        verify(errorTableDataConverter,
                times(transactionImportData.getTransactionList().stream().mapToInt(tran -> {
                    return tran.getNonItemDetailList().size();
                }).sum())).convertTErrorEvacuationSalesTransactionDetailInfoEntityForInsert(
                        nonItemInfoArgumentCaptor.capture(), anyString(), anyString(), anyInt(),
                        anyString(), anyInt(), anyInt(), anyString());
        verify(errorTableDataConverter,
                times(transactionImportData.getTransactionList().stream().mapToInt(tran -> {
                    return tran.getSalesTransactionTenderList().size();
                }).sum())).convertTErrorEvacuationSalesTransactionTenderEntityForInsert(
                        salesTransactionTenderArgumentCaptor.capture(), anyString(), anyString(),
                        anyInt(), anyString(), anyInt(), anyString());
        verify(errorTableDataConverter, times(0))
                .convertTErrorEvacuationSalesTransactionTenderInfoEntityForInsert(
                        tenderInfoArgumentCaptor.capture(), anyString(), anyString(), anyInt(),
                        anyString(), anyString(), anyString(), anyString(), anyInt());
        verify(errorTableDataConverter,
                times(transactionImportData.getTransactionList().stream().mapToInt(tran -> {
                    return tran.getSalesTransactionTaxDetailList().size();
                }).sum())).convertTErrorEvacuationSalesTransactionTaxEntityForInsertTransaction(
                        salesTransactionTaxDetailArgumentCaptor.capture(), anyString(), anyString(),
                        anyInt(), anyString(), anyInt(), anyInt(), anyString());
        verify(errorTableDataConverter,
                times(transactionImportData.getTransactionList().stream().mapToInt(tran -> {
                    return tran.getSalesTransactionTotalList().size();
                }).sum())).convertTErrorEvacuationSalesTransactionTotalAmountEntityForInsert(
                        salesTransactionTotalArgumentCaptor.capture(), anyString(), anyString(),
                        anyInt(), anyString(), anyInt(), anyString());
        verify(errorTableDataConverter, times(0))
                .convertTErrorEvacuationSalesTransactionDiscountEntityForInsertNon(
                        nonItemDiscountDetailArgumentCaptor.capture(), anyString(), anyString(),
                        anyInt(), anyString(), anyInt(), anyInt(), anyString());
        verify(errorTableDataConverter, times(0))
                .convertTErrorEvacuationSalesTransactionTaxEntityForInsertNon(
                        nonItemTaxDetailArgumentCaptor.capture(), anyString(), anyString(),
                        anyInt(), anyString(), anyInt(), anyInt(), anyString());
        verify(errorTableDataConverter, times(0))
                .convertTErrorEvacuationSalesTransactionDiscountEntityForInsert(
                        itemDiscountArgumentCaptor.capture(), anyString(), anyString(), anyInt(),
                        anyString(), anyInt(), anyInt(), anyString());
        verify(errorTableDataConverter, times(0))
                .convertTErrorEvacuationSalesTransactionTaxEntityForInsert(
                        itemTaxDetailArgumentCaptor.capture(), anyString(), anyString(), anyInt(),
                        anyString(), anyInt(), anyInt(), anyString());

        transactionImportData.getTransactionList().forEach(transactionTmp -> {
            assertThat(transactionArgumentCaptor.getAllValues()).contains(transactionTmp);
        });
    }

    @Test
    public void testEmptyNonItemDetail() throws Exception {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getTransactionItemDetailList().forEach(itemDetail -> {
                itemDetail.setNonItemDetailListByItem(null);
            });
        });
        when(salesErrorSalesOrderInformationEntityMapper
                .insertSelective(any(ErrorEvacuationSalesOrderInformation.class))).thenReturn(1);
        when(salesErrorSalesTransactionHeaderEntityMapper
                .insertSelective(any(ErrorEvacuationSalesTransactionHeader.class))).thenReturn(1);
        when(salesErrorSalesTransactionDetailEntityMapper
                .insertSelective(any(ErrorEvacuationSalesTransactionDetail.class))).thenReturn(1);
        when(salesErrorSalesTransactionDetailInfoEntityMapper
                .insertSelective(any(ErrorEvacuationSalesTransactionDetailInfo.class)))
                        .thenReturn(1);
        when(salesErrorSalesTransactionDiscountEntityMapper
                .insertSelective(any(ErrorEvacuationSalesTransactionDiscount.class))).thenReturn(1);
        when(salesErrorSalesTransactionTaxEntityMapper
                .insertSelective(any(ErrorEvacuationSalesTransactionTax.class))).thenReturn(1);
        when(salesErrorSalesTransactionTenderEntityMapper
                .insertSelective(any(ErrorEvacuationSalesTransactionTender.class))).thenReturn(1);
        when(salesErrorSalesTransactionTenderInfoEntityMapper
                .insertSelective(any(ErrorEvacuationSalesTransactionTenderInfo.class)))
                        .thenReturn(1);
        when(salesErrorSalesTransactionTotalAmountEntityMapper
                .insertSelective(any(ErrorEvacuationSalesTransactionTotalAmount.class)))
                        .thenReturn(1);

        when(errorTableDataConverter.convertTErrorEvacuationSalesOrderInformationEntityForInsert(
                transactionImportData, userId, salesTransactionErrorId))
                        .thenReturn(new ErrorEvacuationSalesOrderInformation());
        when(errorTableDataConverter.convertTErrorEvacuationSalesTransactionHeaderEntityForInsert(
                any(Transaction.class), anyString(), anyString(), anyString()))
                        .thenReturn(new ErrorEvacuationSalesTransactionHeader());
        when(errorTableDataConverter.convertTErrorEvacuationSalesTransactionDetailEntityForInsert(
                any(TransactionItemDetail.class), anyString(), anyString(), anyInt(), anyString(),
                anyString(), anyInt(), anyInt()))
                        .thenReturn(new ErrorEvacuationSalesTransactionDetail());
        when(errorTableDataConverter
                .convertTErrorEvacuationSalesTransactionDetailEntityForInsertOutside(
                        any(NonItemDetail.class), anyString(), anyString(), anyInt(), anyString(),
                        anyString(), anyInt(), anyInt(), anyString()))
                                .thenReturn(new ErrorEvacuationSalesTransactionDetail());
        when(errorTableDataConverter
                .convertTErrorEvacuationSalesTransactionDetailInfoEntityForInsert(
                        any(NonItemInfo.class), anyString(), anyString(), anyInt(), anyString(),
                        anyInt(), anyInt(), anyString()))
                                .thenReturn(new ErrorEvacuationSalesTransactionDetailInfo());
        when(errorTableDataConverter.convertTErrorEvacuationSalesTransactionTenderEntityForInsert(
                any(SalesTransactionTender.class), anyString(), anyString(), anyInt(), anyString(),
                anyInt(), anyString())).thenReturn(new ErrorEvacuationSalesTransactionTender());
        when(errorTableDataConverter
                .convertTErrorEvacuationSalesTransactionTenderInfoEntityForInsert(
                        any(TenderInfo.class), anyString(), anyString(), anyInt(), anyString(),
                        anyString(), anyString(), anyString(), anyInt()))
                                .thenReturn(new ErrorEvacuationSalesTransactionTenderInfo());
        when(errorTableDataConverter
                .convertTErrorEvacuationSalesTransactionTaxEntityForInsertTransaction(
                        any(SalesTransactionTaxDetail.class), anyString(), anyString(), anyInt(),
                        anyString(), anyInt(), anyInt(), anyString()))
                                .thenReturn(new ErrorEvacuationSalesTransactionTax());
        when(errorTableDataConverter
                .convertTErrorEvacuationSalesTransactionTotalAmountEntityForInsert(
                        any(SalesTransactionTotal.class), anyString(), anyString(), anyInt(),
                        anyString(), anyInt(), anyString()))
                                .thenReturn(new ErrorEvacuationSalesTransactionTotalAmount());
        when(errorTableDataConverter
                .convertTErrorEvacuationSalesTransactionDiscountEntityForInsertNon(
                        any(NonItemDiscountDetail.class), anyString(), anyString(), anyInt(),
                        anyString(), anyInt(), anyInt(), anyString()))
                                .thenReturn(new ErrorEvacuationSalesTransactionDiscount());
        when(errorTableDataConverter.convertTErrorEvacuationSalesTransactionTaxEntityForInsertNon(
                any(NonItemTaxDetail.class), anyString(), anyString(), anyInt(), anyString(),
                anyInt(), anyInt(), anyString()))
                        .thenReturn(new ErrorEvacuationSalesTransactionTax());
        when(errorTableDataConverter.convertTErrorEvacuationSalesTransactionDiscountEntityForInsert(
                any(ItemDiscount.class), anyString(), anyString(), anyInt(), anyString(), anyInt(),
                anyInt(), anyString())).thenReturn(new ErrorEvacuationSalesTransactionDiscount());
        when(errorTableDataConverter.convertTErrorEvacuationSalesTransactionTaxEntityForInsert(
                any(ItemTaxDetail.class), anyString(), anyString(), anyInt(), anyString(), anyInt(),
                anyInt(), anyString())).thenReturn(new ErrorEvacuationSalesTransactionTax());


        insertErrorTableService.insertErrorEvacuationTable(transactionImportData,
                salesTransactionErrorId, userId);

        ArgumentCaptor<Transaction> transactionArgumentCaptor =
                ArgumentCaptor.forClass(Transaction.class);
        ArgumentCaptor<TransactionItemDetail> transactionItemDetailArgumentCaptor =
                ArgumentCaptor.forClass(TransactionItemDetail.class);
        ArgumentCaptor<NonItemDetail> nonItemDetailArgumentCaptor =
                ArgumentCaptor.forClass(NonItemDetail.class);
        ArgumentCaptor<NonItemInfo> nonItemInfoArgumentCaptor =
                ArgumentCaptor.forClass(NonItemInfo.class);
        ArgumentCaptor<SalesTransactionTender> salesTransactionTenderArgumentCaptor =
                ArgumentCaptor.forClass(SalesTransactionTender.class);
        ArgumentCaptor<TenderInfo> tenderInfoArgumentCaptor =
                ArgumentCaptor.forClass(TenderInfo.class);
        ArgumentCaptor<SalesTransactionTaxDetail> salesTransactionTaxDetailArgumentCaptor =
                ArgumentCaptor.forClass(SalesTransactionTaxDetail.class);
        ArgumentCaptor<SalesTransactionTotal> salesTransactionTotalArgumentCaptor =
                ArgumentCaptor.forClass(SalesTransactionTotal.class);
        ArgumentCaptor<NonItemDiscountDetail> nonItemDiscountDetailArgumentCaptor =
                ArgumentCaptor.forClass(NonItemDiscountDetail.class);
        ArgumentCaptor<NonItemTaxDetail> nonItemTaxDetailArgumentCaptor =
                ArgumentCaptor.forClass(NonItemTaxDetail.class);
        ArgumentCaptor<ItemDiscount> itemDiscountArgumentCaptor =
                ArgumentCaptor.forClass(ItemDiscount.class);
        ArgumentCaptor<ItemTaxDetail> itemTaxDetailArgumentCaptor =
                ArgumentCaptor.forClass(ItemTaxDetail.class);

        verify(errorTableDataConverter, times(1))
                .convertTErrorEvacuationSalesOrderInformationEntityForInsert(transactionImportData,
                        userId, salesTransactionErrorId);
        verify(errorTableDataConverter, times(transactionImportData.getTransactionList().size()))
                .convertTErrorEvacuationSalesTransactionHeaderEntityForInsert(
                        transactionArgumentCaptor.capture(), anyString(), anyString(), anyString());
        verify(errorTableDataConverter,
                times(transactionImportData.getTransactionList().stream().mapToInt(tran -> {
                    return tran.getTransactionItemDetailList().size();
                }).sum())).convertTErrorEvacuationSalesTransactionDetailEntityForInsert(
                        transactionItemDetailArgumentCaptor.capture(), anyString(), anyString(),
                        anyInt(), anyString(), anyString(), anyInt(), anyInt());
        verify(errorTableDataConverter,
                times(transactionImportData.getTransactionList().stream().mapToInt(tran -> {
                    return tran.getNonItemDetailList().size();
                }).sum())).convertTErrorEvacuationSalesTransactionDetailEntityForInsertOutside(
                        nonItemDetailArgumentCaptor.capture(), anyString(), anyString(), anyInt(),
                        anyString(), anyString(), anyInt(), anyInt(), anyString());
        verify(errorTableDataConverter,
                times(transactionImportData.getTransactionList().stream().mapToInt(tran -> {
                    return tran.getNonItemDetailList().size();
                }).sum())).convertTErrorEvacuationSalesTransactionDetailInfoEntityForInsert(
                        nonItemInfoArgumentCaptor.capture(), anyString(), anyString(), anyInt(),
                        anyString(), anyInt(), anyInt(), anyString());
        verify(errorTableDataConverter,
                times(transactionImportData.getTransactionList().stream().mapToInt(tran -> {
                    return tran.getSalesTransactionTenderList().size();
                }).sum())).convertTErrorEvacuationSalesTransactionTenderEntityForInsert(
                        salesTransactionTenderArgumentCaptor.capture(), anyString(), anyString(),
                        anyInt(), anyString(), anyInt(), anyString());
        verify(errorTableDataConverter,
                times(transactionImportData.getTransactionList().stream().mapToInt(tran -> {
                    return tran.getSalesTransactionTenderList().stream().mapToInt(tender -> {
                        return 1;
                    }).sum();
                }).sum())).convertTErrorEvacuationSalesTransactionTenderInfoEntityForInsert(
                        tenderInfoArgumentCaptor.capture(), anyString(), anyString(), anyInt(),
                        anyString(), anyString(), anyString(), anyString(), anyInt());
        verify(errorTableDataConverter,
                times(transactionImportData.getTransactionList().stream().mapToInt(tran -> {
                    return tran.getSalesTransactionTaxDetailList().size();
                }).sum())).convertTErrorEvacuationSalesTransactionTaxEntityForInsertTransaction(
                        salesTransactionTaxDetailArgumentCaptor.capture(), anyString(), anyString(),
                        anyInt(), anyString(), anyInt(), anyInt(), anyString());
        verify(errorTableDataConverter,
                times(transactionImportData.getTransactionList().stream().mapToInt(tran -> {
                    return tran.getSalesTransactionTotalList().size();
                }).sum())).convertTErrorEvacuationSalesTransactionTotalAmountEntityForInsert(
                        salesTransactionTotalArgumentCaptor.capture(), anyString(), anyString(),
                        anyInt(), anyString(), anyInt(), anyString());
        verify(errorTableDataConverter,
                times(transactionImportData.getTransactionList().stream().mapToInt(tran -> {
                    return tran.getNonItemDetailList().stream().mapToInt(nonItemDetail -> {
                        return nonItemDetail.getNonItemDiscountDetailList().size();
                    }).sum();
                }).sum())).convertTErrorEvacuationSalesTransactionDiscountEntityForInsertNon(
                        nonItemDiscountDetailArgumentCaptor.capture(), anyString(), anyString(),
                        anyInt(), anyString(), anyInt(), anyInt(), anyString());
        verify(errorTableDataConverter,
                times(transactionImportData.getTransactionList().stream().mapToInt(tran -> {
                    return tran.getNonItemDetailList().stream().mapToInt(nonItemDetail -> {
                        return nonItemDetail.getNonItemTaxDetailList().size();
                    }).sum();
                }).sum())).convertTErrorEvacuationSalesTransactionTaxEntityForInsertNon(
                        nonItemTaxDetailArgumentCaptor.capture(), anyString(), anyString(),
                        anyInt(), anyString(), anyInt(), anyInt(), anyString());
        verify(errorTableDataConverter,
                times(transactionImportData.getTransactionList().stream().mapToInt(tran -> {
                    return tran.getTransactionItemDetailList().stream().mapToInt(itemDetail -> {
                        return itemDetail.getItemDiscountList().size();
                    }).sum();
                }).sum())).convertTErrorEvacuationSalesTransactionDiscountEntityForInsert(
                        itemDiscountArgumentCaptor.capture(), anyString(), anyString(), anyInt(),
                        anyString(), anyInt(), anyInt(), anyString());
        verify(errorTableDataConverter,
                times(transactionImportData.getTransactionList().stream().mapToInt(tran -> {
                    return tran.getTransactionItemDetailList().stream().mapToInt(itemDetail -> {
                        return itemDetail.getItemTaxDetailList().size();
                    }).sum();
                }).sum())).convertTErrorEvacuationSalesTransactionTaxEntityForInsert(
                        itemTaxDetailArgumentCaptor.capture(), anyString(), anyString(), anyInt(),
                        anyString(), anyInt(), anyInt(), anyString());

        transactionImportData.getTransactionList().forEach(transactionTmp -> {
            assertThat(transactionArgumentCaptor.getAllValues()).contains(transactionTmp);
            transactionTmp.getTransactionItemDetailList().forEach(itemDetail -> {
                assertThat(transactionItemDetailArgumentCaptor.getAllValues()).contains(itemDetail);
                itemDetail.getItemTaxDetailList().forEach(taxDetail -> {
                    assertThat(itemTaxDetailArgumentCaptor.getAllValues()).contains(taxDetail);
                });
                itemDetail.getItemDiscountList().forEach(discount -> {
                    assertThat(itemDiscountArgumentCaptor.getAllValues()).contains(discount);
                });
            });
            transactionTmp.getNonItemDetailList().forEach(nonItemDetail -> {
                assertThat(nonItemDetailArgumentCaptor.getAllValues()).contains(nonItemDetail);
                nonItemDetail.getNonItemDiscountDetailList().forEach(discount -> {
                    assertThat(nonItemDiscountDetailArgumentCaptor.getAllValues())
                            .contains(discount);
                });
                nonItemDetail.getNonItemTaxDetailList().forEach(nonTax -> {
                    assertThat(nonItemTaxDetailArgumentCaptor.getAllValues()).contains(nonTax);
                });
            });
            transactionTmp.getSalesTransactionTaxDetailList().forEach(taxDetail -> {
                assertThat(salesTransactionTaxDetailArgumentCaptor.getAllValues())
                        .contains(taxDetail);
            });
            transactionTmp.getSalesTransactionTenderList().forEach(tender -> {
                assertThat(salesTransactionTenderArgumentCaptor.getAllValues()).contains(tender);
                assertThat(tenderInfoArgumentCaptor.getAllValues())
                        .contains(tender.getTenderInfoList());
            });
            transactionTmp.getSalesTransactionTotalList().forEach(total -> {
                assertThat(salesTransactionTotalArgumentCaptor.getAllValues()).contains(total);
            });
        });
    }

    @Test
    public void testEmptyInItemDetailNonItemDetail() throws Exception {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getTransactionItemDetailList().forEach(itemDetail -> {
                itemDetail.setItemDiscountList(new ArrayList<>());
                itemDetail.setItemTaxDetailList(new ArrayList<>());
                itemDetail.getNonItemDetailListByItem().forEach(non -> {
                    non.setNonItemDiscountDetailList(new ArrayList<>());
                    non.setNonItemTaxDetailList(new ArrayList<>());
                    non.setNonItemInfo(null);
                });
            });
            transaction.getNonItemDetailList().forEach(non -> {
                non.setNonItemDiscountDetailList(new ArrayList<>());
                non.setNonItemTaxDetailList(new ArrayList<>());
                non.setNonItemInfo(null);
            });
            transaction.getSalesTransactionTenderList().forEach(tender -> {
                tender.setTenderInfoList(null);
            });
        });
        when(salesErrorSalesOrderInformationEntityMapper
                .insertSelective(any(ErrorEvacuationSalesOrderInformation.class))).thenReturn(1);
        when(salesErrorSalesTransactionHeaderEntityMapper
                .insertSelective(any(ErrorEvacuationSalesTransactionHeader.class))).thenReturn(1);
        when(salesErrorSalesTransactionDetailEntityMapper
                .insertSelective(any(ErrorEvacuationSalesTransactionDetail.class))).thenReturn(1);
        when(salesErrorSalesTransactionDetailInfoEntityMapper
                .insertSelective(any(ErrorEvacuationSalesTransactionDetailInfo.class)))
                        .thenReturn(1);
        when(salesErrorSalesTransactionDiscountEntityMapper
                .insertSelective(any(ErrorEvacuationSalesTransactionDiscount.class))).thenReturn(1);
        when(salesErrorSalesTransactionTaxEntityMapper
                .insertSelective(any(ErrorEvacuationSalesTransactionTax.class))).thenReturn(1);
        when(salesErrorSalesTransactionTenderEntityMapper
                .insertSelective(any(ErrorEvacuationSalesTransactionTender.class))).thenReturn(1);
        when(salesErrorSalesTransactionTenderInfoEntityMapper
                .insertSelective(any(ErrorEvacuationSalesTransactionTenderInfo.class)))
                        .thenReturn(1);
        when(salesErrorSalesTransactionTotalAmountEntityMapper
                .insertSelective(any(ErrorEvacuationSalesTransactionTotalAmount.class)))
                        .thenReturn(1);

        when(errorTableDataConverter.convertTErrorEvacuationSalesOrderInformationEntityForInsert(
                transactionImportData, userId, salesTransactionErrorId))
                        .thenReturn(new ErrorEvacuationSalesOrderInformation());
        when(errorTableDataConverter.convertTErrorEvacuationSalesTransactionHeaderEntityForInsert(
                any(Transaction.class), anyString(), anyString(), anyString()))
                        .thenReturn(new ErrorEvacuationSalesTransactionHeader());
        when(errorTableDataConverter.convertTErrorEvacuationSalesTransactionDetailEntityForInsert(
                any(TransactionItemDetail.class), anyString(), anyString(), anyInt(), anyString(),
                anyString(), anyInt(), anyInt()))
                        .thenReturn(new ErrorEvacuationSalesTransactionDetail());
        when(errorTableDataConverter
                .convertTErrorEvacuationSalesTransactionDetailEntityForInsertOutside(
                        any(NonItemDetail.class), anyString(), anyString(), anyInt(), anyString(),
                        anyString(), anyInt(), anyInt(), anyString()))
                                .thenReturn(new ErrorEvacuationSalesTransactionDetail());
        when(errorTableDataConverter
                .convertTErrorEvacuationSalesTransactionDetailInfoEntityForInsert(
                        any(NonItemInfo.class), anyString(), anyString(), anyInt(), anyString(),
                        anyInt(), anyInt(), anyString()))
                                .thenReturn(new ErrorEvacuationSalesTransactionDetailInfo());
        when(errorTableDataConverter.convertTErrorEvacuationSalesTransactionTenderEntityForInsert(
                any(SalesTransactionTender.class), anyString(), anyString(), anyInt(), anyString(),
                anyInt(), anyString())).thenReturn(new ErrorEvacuationSalesTransactionTender());
        when(errorTableDataConverter
                .convertTErrorEvacuationSalesTransactionTenderInfoEntityForInsert(
                        any(TenderInfo.class), anyString(), anyString(), anyInt(), anyString(),
                        anyString(), anyString(), anyString(), anyInt()))
                                .thenReturn(new ErrorEvacuationSalesTransactionTenderInfo());
        when(errorTableDataConverter
                .convertTErrorEvacuationSalesTransactionTaxEntityForInsertTransaction(
                        any(SalesTransactionTaxDetail.class), anyString(), anyString(), anyInt(),
                        anyString(), anyInt(), anyInt(), anyString()))
                                .thenReturn(new ErrorEvacuationSalesTransactionTax());
        when(errorTableDataConverter
                .convertTErrorEvacuationSalesTransactionTotalAmountEntityForInsert(
                        any(SalesTransactionTotal.class), anyString(), anyString(), anyInt(),
                        anyString(), anyInt(), anyString()))
                                .thenReturn(new ErrorEvacuationSalesTransactionTotalAmount());
        when(errorTableDataConverter
                .convertTErrorEvacuationSalesTransactionDiscountEntityForInsertNon(
                        any(NonItemDiscountDetail.class), anyString(), anyString(), anyInt(),
                        anyString(), anyInt(), anyInt(), anyString()))
                                .thenReturn(new ErrorEvacuationSalesTransactionDiscount());
        when(errorTableDataConverter.convertTErrorEvacuationSalesTransactionTaxEntityForInsertNon(
                any(NonItemTaxDetail.class), anyString(), anyString(), anyInt(), anyString(),
                anyInt(), anyInt(), anyString()))
                        .thenReturn(new ErrorEvacuationSalesTransactionTax());
        when(errorTableDataConverter.convertTErrorEvacuationSalesTransactionDiscountEntityForInsert(
                any(ItemDiscount.class), anyString(), anyString(), anyInt(), anyString(), anyInt(),
                anyInt(), anyString())).thenReturn(new ErrorEvacuationSalesTransactionDiscount());
        when(errorTableDataConverter.convertTErrorEvacuationSalesTransactionTaxEntityForInsert(
                any(ItemTaxDetail.class), anyString(), anyString(), anyInt(), anyString(), anyInt(),
                anyInt(), anyString())).thenReturn(new ErrorEvacuationSalesTransactionTax());


        insertErrorTableService.insertErrorEvacuationTable(transactionImportData,
                salesTransactionErrorId, userId);

        ArgumentCaptor<Transaction> transactionArgumentCaptor =
                ArgumentCaptor.forClass(Transaction.class);
        ArgumentCaptor<TransactionItemDetail> transactionItemDetailArgumentCaptor =
                ArgumentCaptor.forClass(TransactionItemDetail.class);
        ArgumentCaptor<NonItemDetail> nonItemDetailArgumentCaptor =
                ArgumentCaptor.forClass(NonItemDetail.class);
        ArgumentCaptor<NonItemInfo> nonItemInfoArgumentCaptor =
                ArgumentCaptor.forClass(NonItemInfo.class);
        ArgumentCaptor<SalesTransactionTender> salesTransactionTenderArgumentCaptor =
                ArgumentCaptor.forClass(SalesTransactionTender.class);
        ArgumentCaptor<TenderInfo> tenderInfoArgumentCaptor =
                ArgumentCaptor.forClass(TenderInfo.class);
        ArgumentCaptor<SalesTransactionTaxDetail> salesTransactionTaxDetailArgumentCaptor =
                ArgumentCaptor.forClass(SalesTransactionTaxDetail.class);
        ArgumentCaptor<SalesTransactionTotal> salesTransactionTotalArgumentCaptor =
                ArgumentCaptor.forClass(SalesTransactionTotal.class);
        ArgumentCaptor<NonItemDiscountDetail> nonItemDiscountDetailArgumentCaptor =
                ArgumentCaptor.forClass(NonItemDiscountDetail.class);
        ArgumentCaptor<NonItemTaxDetail> nonItemTaxDetailArgumentCaptor =
                ArgumentCaptor.forClass(NonItemTaxDetail.class);
        ArgumentCaptor<ItemDiscount> itemDiscountArgumentCaptor =
                ArgumentCaptor.forClass(ItemDiscount.class);
        ArgumentCaptor<ItemTaxDetail> itemTaxDetailArgumentCaptor =
                ArgumentCaptor.forClass(ItemTaxDetail.class);

        verify(errorTableDataConverter, times(1))
                .convertTErrorEvacuationSalesOrderInformationEntityForInsert(transactionImportData,
                        userId, salesTransactionErrorId);
        verify(errorTableDataConverter, times(transactionImportData.getTransactionList().size()))
                .convertTErrorEvacuationSalesTransactionHeaderEntityForInsert(
                        transactionArgumentCaptor.capture(), anyString(), anyString(), anyString());
        verify(errorTableDataConverter,
                times(transactionImportData.getTransactionList().stream().mapToInt(tran -> {
                    return tran.getTransactionItemDetailList().size();
                }).sum())).convertTErrorEvacuationSalesTransactionDetailEntityForInsert(
                        transactionItemDetailArgumentCaptor.capture(), anyString(), anyString(),
                        anyInt(), anyString(), anyString(), anyInt(), anyInt());
        verify(errorTableDataConverter,
                times(transactionImportData.getTransactionList().stream().mapToInt(tran -> {
                    return tran.getTransactionItemDetailList()
                            .stream()
                            .mapToInt(item -> item.getNonItemDetailListByItem().size())
                            .sum() + tran.getNonItemDetailList().size();
                }).sum())).convertTErrorEvacuationSalesTransactionDetailEntityForInsertOutside(
                        nonItemDetailArgumentCaptor.capture(), anyString(), anyString(), anyInt(),
                        anyString(), anyString(), anyInt(), anyInt(), anyString());
        verify(errorTableDataConverter, times(0))
                .convertTErrorEvacuationSalesTransactionDetailInfoEntityForInsert(
                        nonItemInfoArgumentCaptor.capture(), anyString(), anyString(), anyInt(),
                        anyString(), anyInt(), anyInt(), anyString());
        verify(errorTableDataConverter,
                times(transactionImportData.getTransactionList().stream().mapToInt(tran -> {
                    return tran.getSalesTransactionTenderList().size();
                }).sum())).convertTErrorEvacuationSalesTransactionTenderEntityForInsert(
                        salesTransactionTenderArgumentCaptor.capture(), anyString(), anyString(),
                        anyInt(), anyString(), anyInt(), anyString());
        verify(errorTableDataConverter, times(0))
                .convertTErrorEvacuationSalesTransactionTenderInfoEntityForInsert(
                        tenderInfoArgumentCaptor.capture(), anyString(), anyString(), anyInt(),
                        anyString(), anyString(), anyString(), anyString(), anyInt());
        verify(errorTableDataConverter,
                times(transactionImportData.getTransactionList().stream().mapToInt(tran -> {
                    return tran.getSalesTransactionTaxDetailList().size();
                }).sum())).convertTErrorEvacuationSalesTransactionTaxEntityForInsertTransaction(
                        salesTransactionTaxDetailArgumentCaptor.capture(), anyString(), anyString(),
                        anyInt(), anyString(), anyInt(), anyInt(), anyString());
        verify(errorTableDataConverter,
                times(transactionImportData.getTransactionList().stream().mapToInt(tran -> {
                    return tran.getSalesTransactionTotalList().size();
                }).sum())).convertTErrorEvacuationSalesTransactionTotalAmountEntityForInsert(
                        salesTransactionTotalArgumentCaptor.capture(), anyString(), anyString(),
                        anyInt(), anyString(), anyInt(), anyString());
        verify(errorTableDataConverter,
                times(transactionImportData.getTransactionList().stream().mapToInt(tran -> {
                    return tran.getTransactionItemDetailList().stream().mapToInt(itemDetail -> {
                        return itemDetail.getNonItemDetailListByItem()
                                .stream()
                                .mapToInt(nonItemDetail -> {
                                    return nonItemDetail.getNonItemDiscountDetailList().size();
                                })
                                .sum();
                    }).sum() + tran.getNonItemDetailList().stream().mapToInt(nonItemDetail -> {
                        return nonItemDetail.getNonItemDiscountDetailList().size();
                    }).sum();
                }).sum())).convertTErrorEvacuationSalesTransactionDiscountEntityForInsertNon(
                        nonItemDiscountDetailArgumentCaptor.capture(), anyString(), anyString(),
                        anyInt(), anyString(), anyInt(), anyInt(), anyString());
        verify(errorTableDataConverter,
                times(transactionImportData.getTransactionList().stream().mapToInt(tran -> {
                    return tran.getTransactionItemDetailList().stream().mapToInt(itemDetail -> {
                        return itemDetail.getNonItemDetailListByItem()
                                .stream()
                                .mapToInt(nonItemDetail -> {
                                    return nonItemDetail.getNonItemTaxDetailList().size();
                                })
                                .sum();
                    }).sum() + tran.getNonItemDetailList().stream().mapToInt(nonItemDetail -> {
                        return nonItemDetail.getNonItemTaxDetailList().size();
                    }).sum();
                }).sum())).convertTErrorEvacuationSalesTransactionTaxEntityForInsertNon(
                        nonItemTaxDetailArgumentCaptor.capture(), anyString(), anyString(),
                        anyInt(), anyString(), anyInt(), anyInt(), anyString());
        verify(errorTableDataConverter,
                times(transactionImportData.getTransactionList().stream().mapToInt(tran -> {
                    return tran.getTransactionItemDetailList().stream().mapToInt(itemDetail -> {
                        return itemDetail.getItemDiscountList().size();
                    }).sum();
                }).sum())).convertTErrorEvacuationSalesTransactionDiscountEntityForInsert(
                        itemDiscountArgumentCaptor.capture(), anyString(), anyString(), anyInt(),
                        anyString(), anyInt(), anyInt(), anyString());
        verify(errorTableDataConverter,
                times(transactionImportData.getTransactionList().stream().mapToInt(tran -> {
                    return tran.getTransactionItemDetailList().stream().mapToInt(itemDetail -> {
                        return itemDetail.getItemTaxDetailList().size();
                    }).sum();
                }).sum())).convertTErrorEvacuationSalesTransactionTaxEntityForInsert(
                        itemTaxDetailArgumentCaptor.capture(), anyString(), anyString(), anyInt(),
                        anyString(), anyInt(), anyInt(), anyString());

        transactionImportData.getTransactionList().forEach(transactionTmp -> {
            assertThat(transactionArgumentCaptor.getAllValues()).contains(transactionTmp);
            transactionTmp.getTransactionItemDetailList().forEach(itemDetail -> {
                assertThat(transactionItemDetailArgumentCaptor.getAllValues()).contains(itemDetail);
                itemDetail.getNonItemDetailListByItem().forEach(nonItemDetail -> {
                    assertThat(nonItemDetailArgumentCaptor.getAllValues()).contains(nonItemDetail);
                });
            });
            transactionTmp.getNonItemDetailList().forEach(nonItemDetail -> {
                assertThat(nonItemDetailArgumentCaptor.getAllValues()).contains(nonItemDetail);
            });
            transactionTmp.getSalesTransactionTaxDetailList().forEach(taxDetail -> {
                assertThat(salesTransactionTaxDetailArgumentCaptor.getAllValues())
                        .contains(taxDetail);
            });
            transactionTmp.getSalesTransactionTotalList().forEach(total -> {
                assertThat(salesTransactionTotalArgumentCaptor.getAllValues()).contains(total);
            });
        });
    }

    @Test
    public void testNonItemInfoNull() {
        transactionImportData.getTransactionList().forEach(tran -> {
            tran.getTransactionItemDetailList().forEach(itemDetail -> {
                itemDetail.getNonItemDetailListByItem().forEach(nonItemDetail -> {
                    nonItemDetail.setNonItemInfo(null);
                });
            });
        });

        when(salesErrorSalesOrderInformationEntityMapper
                .insertSelective(any(ErrorEvacuationSalesOrderInformation.class))).thenReturn(1);
        when(salesErrorSalesTransactionHeaderEntityMapper
                .insertSelective(any(ErrorEvacuationSalesTransactionHeader.class))).thenReturn(1);
        when(salesErrorSalesTransactionDetailEntityMapper
                .insertSelective(any(ErrorEvacuationSalesTransactionDetail.class))).thenReturn(1);
        when(salesErrorSalesTransactionDetailInfoEntityMapper
                .insertSelective(any(ErrorEvacuationSalesTransactionDetailInfo.class)))
                        .thenReturn(1);
        when(salesErrorSalesTransactionDiscountEntityMapper
                .insertSelective(any(ErrorEvacuationSalesTransactionDiscount.class))).thenReturn(1);
        when(salesErrorSalesTransactionTaxEntityMapper
                .insertSelective(any(ErrorEvacuationSalesTransactionTax.class))).thenReturn(1);
        when(salesErrorSalesTransactionTenderEntityMapper
                .insertSelective(any(ErrorEvacuationSalesTransactionTender.class))).thenReturn(1);
        when(salesErrorSalesTransactionTenderInfoEntityMapper
                .insertSelective(any(ErrorEvacuationSalesTransactionTenderInfo.class)))
                        .thenReturn(1);
        when(salesErrorSalesTransactionTotalAmountEntityMapper
                .insertSelective(any(ErrorEvacuationSalesTransactionTotalAmount.class)))
                        .thenReturn(1);

        when(errorTableDataConverter.convertTErrorEvacuationSalesOrderInformationEntityForInsert(
                transactionImportData, userId, salesTransactionErrorId))
                        .thenReturn(new ErrorEvacuationSalesOrderInformation());
        when(errorTableDataConverter.convertTErrorEvacuationSalesTransactionHeaderEntityForInsert(
                any(Transaction.class), anyString(), anyString(), anyString()))
                        .thenReturn(new ErrorEvacuationSalesTransactionHeader());
        when(errorTableDataConverter.convertTErrorEvacuationSalesTransactionDetailEntityForInsert(
                any(TransactionItemDetail.class), anyString(), anyString(), anyInt(), anyString(),
                anyString(), anyInt(), anyInt()))
                        .thenReturn(new ErrorEvacuationSalesTransactionDetail());
        when(errorTableDataConverter
                .convertTErrorEvacuationSalesTransactionDetailEntityForInsertOutside(
                        any(NonItemDetail.class), anyString(), anyString(), anyInt(), anyString(),
                        anyString(), anyInt(), anyInt(), anyString()))
                                .thenReturn(new ErrorEvacuationSalesTransactionDetail());
        when(errorTableDataConverter
                .convertTErrorEvacuationSalesTransactionDetailInfoEntityForInsert(
                        any(NonItemInfo.class), anyString(), anyString(), anyInt(), anyString(),
                        anyInt(), anyInt(), anyString()))
                                .thenReturn(new ErrorEvacuationSalesTransactionDetailInfo());
        when(errorTableDataConverter.convertTErrorEvacuationSalesTransactionTenderEntityForInsert(
                any(SalesTransactionTender.class), anyString(), anyString(), anyInt(), anyString(),
                anyInt(), anyString())).thenReturn(new ErrorEvacuationSalesTransactionTender());
        when(errorTableDataConverter
                .convertTErrorEvacuationSalesTransactionTenderInfoEntityForInsert(
                        any(TenderInfo.class), anyString(), anyString(), anyInt(), anyString(),
                        anyString(), anyString(), anyString(), anyInt()))
                                .thenReturn(new ErrorEvacuationSalesTransactionTenderInfo());
        when(errorTableDataConverter
                .convertTErrorEvacuationSalesTransactionTaxEntityForInsertTransaction(
                        any(SalesTransactionTaxDetail.class), anyString(), anyString(), anyInt(),
                        anyString(), anyInt(), anyInt(), anyString()))
                                .thenReturn(new ErrorEvacuationSalesTransactionTax());
        when(errorTableDataConverter
                .convertTErrorEvacuationSalesTransactionTotalAmountEntityForInsert(
                        any(SalesTransactionTotal.class), anyString(), anyString(), anyInt(),
                        anyString(), anyInt(), anyString()))
                                .thenReturn(new ErrorEvacuationSalesTransactionTotalAmount());
        when(errorTableDataConverter
                .convertTErrorEvacuationSalesTransactionDiscountEntityForInsertNon(
                        any(NonItemDiscountDetail.class), anyString(), anyString(), anyInt(),
                        anyString(), anyInt(), anyInt(), anyString()))
                                .thenReturn(new ErrorEvacuationSalesTransactionDiscount());
        when(errorTableDataConverter.convertTErrorEvacuationSalesTransactionTaxEntityForInsertNon(
                any(NonItemTaxDetail.class), anyString(), anyString(), anyInt(), anyString(),
                anyInt(), anyInt(), anyString()))
                        .thenReturn(new ErrorEvacuationSalesTransactionTax());
        when(errorTableDataConverter.convertTErrorEvacuationSalesTransactionDiscountEntityForInsert(
                any(ItemDiscount.class), anyString(), anyString(), anyInt(), anyString(), anyInt(),
                anyInt(), anyString())).thenReturn(new ErrorEvacuationSalesTransactionDiscount());
        when(errorTableDataConverter.convertTErrorEvacuationSalesTransactionTaxEntityForInsert(
                any(ItemTaxDetail.class), anyString(), anyString(), anyInt(), anyString(), anyInt(),
                anyInt(), anyString())).thenReturn(new ErrorEvacuationSalesTransactionTax());


        insertErrorTableService.insertErrorEvacuationTable(transactionImportData,
                salesTransactionErrorId, userId);

        ArgumentCaptor<Transaction> transactionArgumentCaptor =
                ArgumentCaptor.forClass(Transaction.class);
        ArgumentCaptor<TransactionItemDetail> transactionItemDetailArgumentCaptor =
                ArgumentCaptor.forClass(TransactionItemDetail.class);
        ArgumentCaptor<NonItemDetail> nonItemDetailArgumentCaptor =
                ArgumentCaptor.forClass(NonItemDetail.class);
        ArgumentCaptor<NonItemInfo> nonItemInfoArgumentCaptor =
                ArgumentCaptor.forClass(NonItemInfo.class);
        ArgumentCaptor<SalesTransactionTender> salesTransactionTenderArgumentCaptor =
                ArgumentCaptor.forClass(SalesTransactionTender.class);
        ArgumentCaptor<TenderInfo> tenderInfoArgumentCaptor =
                ArgumentCaptor.forClass(TenderInfo.class);
        ArgumentCaptor<SalesTransactionTaxDetail> salesTransactionTaxDetailArgumentCaptor =
                ArgumentCaptor.forClass(SalesTransactionTaxDetail.class);
        ArgumentCaptor<SalesTransactionTotal> salesTransactionTotalArgumentCaptor =
                ArgumentCaptor.forClass(SalesTransactionTotal.class);
        ArgumentCaptor<NonItemDiscountDetail> nonItemDiscountDetailArgumentCaptor =
                ArgumentCaptor.forClass(NonItemDiscountDetail.class);
        ArgumentCaptor<NonItemTaxDetail> nonItemTaxDetailArgumentCaptor =
                ArgumentCaptor.forClass(NonItemTaxDetail.class);
        ArgumentCaptor<ItemDiscount> itemDiscountArgumentCaptor =
                ArgumentCaptor.forClass(ItemDiscount.class);
        ArgumentCaptor<ItemTaxDetail> itemTaxDetailArgumentCaptor =
                ArgumentCaptor.forClass(ItemTaxDetail.class);

        verify(errorTableDataConverter, times(1))
                .convertTErrorEvacuationSalesOrderInformationEntityForInsert(transactionImportData,
                        userId, salesTransactionErrorId);
        verify(errorTableDataConverter, times(transactionImportData.getTransactionList().size()))
                .convertTErrorEvacuationSalesTransactionHeaderEntityForInsert(
                        transactionArgumentCaptor.capture(), anyString(), anyString(), anyString());
        verify(errorTableDataConverter,
                times(transactionImportData.getTransactionList().stream().mapToInt(tran -> {
                    return tran.getTransactionItemDetailList().size();
                }).sum())).convertTErrorEvacuationSalesTransactionDetailEntityForInsert(
                        transactionItemDetailArgumentCaptor.capture(), anyString(), anyString(),
                        anyInt(), anyString(), anyString(), anyInt(), anyInt());
        verify(errorTableDataConverter,
                times(transactionImportData.getTransactionList().stream().mapToInt(tran -> {
                    return tran.getTransactionItemDetailList()
                            .stream()
                            .mapToInt(item -> item.getNonItemDetailListByItem().size())
                            .sum() + tran.getNonItemDetailList().size();
                }).sum())).convertTErrorEvacuationSalesTransactionDetailEntityForInsertOutside(
                        nonItemDetailArgumentCaptor.capture(), anyString(), anyString(), anyInt(),
                        anyString(), anyString(), anyInt(), anyInt(), anyString());
        verify(errorTableDataConverter,
                times(transactionImportData.getTransactionList().stream().mapToInt(tran -> {
                    return tran.getNonItemDetailList().size();
                }).sum())).convertTErrorEvacuationSalesTransactionDetailInfoEntityForInsert(
                        nonItemInfoArgumentCaptor.capture(), anyString(), anyString(), anyInt(),
                        anyString(), anyInt(), anyInt(), anyString());
        verify(errorTableDataConverter,
                times(transactionImportData.getTransactionList().stream().mapToInt(tran -> {
                    return tran.getSalesTransactionTenderList().size();
                }).sum())).convertTErrorEvacuationSalesTransactionTenderEntityForInsert(
                        salesTransactionTenderArgumentCaptor.capture(), anyString(), anyString(),
                        anyInt(), anyString(), anyInt(), anyString());
        verify(errorTableDataConverter,
                times(transactionImportData.getTransactionList().stream().mapToInt(tran -> {
                    return tran.getSalesTransactionTenderList().stream().mapToInt(tender -> {
                        return 1;
                    }).sum();
                }).sum())).convertTErrorEvacuationSalesTransactionTenderInfoEntityForInsert(
                        tenderInfoArgumentCaptor.capture(), anyString(), anyString(), anyInt(),
                        anyString(), anyString(), anyString(), anyString(), anyInt());
        verify(errorTableDataConverter,
                times(transactionImportData.getTransactionList().stream().mapToInt(tran -> {
                    return tran.getSalesTransactionTaxDetailList().size();
                }).sum())).convertTErrorEvacuationSalesTransactionTaxEntityForInsertTransaction(
                        salesTransactionTaxDetailArgumentCaptor.capture(), anyString(), anyString(),
                        anyInt(), anyString(), anyInt(), anyInt(), anyString());
        verify(errorTableDataConverter,
                times(transactionImportData.getTransactionList().stream().mapToInt(tran -> {
                    return tran.getSalesTransactionTotalList().size();
                }).sum())).convertTErrorEvacuationSalesTransactionTotalAmountEntityForInsert(
                        salesTransactionTotalArgumentCaptor.capture(), anyString(), anyString(),
                        anyInt(), anyString(), anyInt(), anyString());
        verify(errorTableDataConverter,
                times(transactionImportData.getTransactionList().stream().mapToInt(tran -> {
                    return tran.getTransactionItemDetailList().stream().mapToInt(itemDetail -> {
                        return itemDetail.getNonItemDetailListByItem()
                                .stream()
                                .mapToInt(nonItemDetail -> {
                                    return nonItemDetail.getNonItemDiscountDetailList().size();
                                })
                                .sum();
                    }).sum() + tran.getNonItemDetailList().stream().mapToInt(nonItemDetail -> {
                        return nonItemDetail.getNonItemDiscountDetailList().size();
                    }).sum();
                }).sum())).convertTErrorEvacuationSalesTransactionDiscountEntityForInsertNon(
                        nonItemDiscountDetailArgumentCaptor.capture(), anyString(), anyString(),
                        anyInt(), anyString(), anyInt(), anyInt(), anyString());
        verify(errorTableDataConverter,
                times(transactionImportData.getTransactionList().stream().mapToInt(tran -> {
                    return tran.getTransactionItemDetailList().stream().mapToInt(itemDetail -> {
                        return itemDetail.getNonItemDetailListByItem()
                                .stream()
                                .mapToInt(nonItemDetail -> {
                                    return nonItemDetail.getNonItemTaxDetailList().size();
                                })
                                .sum();
                    }).sum() + tran.getNonItemDetailList().stream().mapToInt(nonItemDetail -> {
                        return nonItemDetail.getNonItemTaxDetailList().size();
                    }).sum();
                }).sum())).convertTErrorEvacuationSalesTransactionTaxEntityForInsertNon(
                        nonItemTaxDetailArgumentCaptor.capture(), anyString(), anyString(),
                        anyInt(), anyString(), anyInt(), anyInt(), anyString());
        verify(errorTableDataConverter,
                times(transactionImportData.getTransactionList().stream().mapToInt(tran -> {
                    return tran.getTransactionItemDetailList().stream().mapToInt(itemDetail -> {
                        return itemDetail.getItemDiscountList().size();
                    }).sum();
                }).sum())).convertTErrorEvacuationSalesTransactionDiscountEntityForInsert(
                        itemDiscountArgumentCaptor.capture(), anyString(), anyString(), anyInt(),
                        anyString(), anyInt(), anyInt(), anyString());
        verify(errorTableDataConverter,
                times(transactionImportData.getTransactionList().stream().mapToInt(tran -> {
                    return tran.getTransactionItemDetailList().stream().mapToInt(itemDetail -> {
                        return itemDetail.getItemTaxDetailList().size();
                    }).sum();
                }).sum())).convertTErrorEvacuationSalesTransactionTaxEntityForInsert(
                        itemTaxDetailArgumentCaptor.capture(), anyString(), anyString(), anyInt(),
                        anyString(), anyInt(), anyInt(), anyString());

        transactionImportData.getTransactionList().forEach(transactionTmp -> {
            assertThat(transactionArgumentCaptor.getAllValues()).contains(transactionTmp);
            transactionTmp.getTransactionItemDetailList().forEach(itemDetail -> {
                assertThat(transactionItemDetailArgumentCaptor.getAllValues()).contains(itemDetail);
                itemDetail.getNonItemDetailListByItem().forEach(nonItemDetail -> {
                    assertThat(nonItemDetailArgumentCaptor.getAllValues()).contains(nonItemDetail);
                });
                itemDetail.getItemTaxDetailList().forEach(taxDetail -> {
                    assertThat(itemTaxDetailArgumentCaptor.getAllValues()).contains(taxDetail);
                });
                itemDetail.getItemDiscountList().forEach(discount -> {
                    assertThat(itemDiscountArgumentCaptor.getAllValues()).contains(discount);
                });
            });
            transactionTmp.getNonItemDetailList().forEach(nonItemDetail -> {
                assertThat(nonItemDetailArgumentCaptor.getAllValues()).contains(nonItemDetail);
                nonItemDetail.getNonItemDiscountDetailList().forEach(discount -> {
                    assertThat(nonItemDiscountDetailArgumentCaptor.getAllValues())
                            .contains(discount);
                });
                nonItemDetail.getNonItemTaxDetailList().forEach(nonTax -> {
                    assertThat(nonItemTaxDetailArgumentCaptor.getAllValues()).contains(nonTax);
                });
            });
            transactionTmp.getSalesTransactionTaxDetailList().forEach(taxDetail -> {
                assertThat(salesTransactionTaxDetailArgumentCaptor.getAllValues())
                        .contains(taxDetail);
            });
            transactionTmp.getSalesTransactionTenderList().forEach(tender -> {
                assertThat(salesTransactionTenderArgumentCaptor.getAllValues()).contains(tender);
                assertThat(tenderInfoArgumentCaptor.getAllValues())
                        .contains(tender.getTenderInfoList());
            });
            transactionTmp.getSalesTransactionTotalList().forEach(total -> {
                assertThat(salesTransactionTotalArgumentCaptor.getAllValues()).contains(total);
            });
        });
    }

    @Test
    public void testNonItemInfoNullOutside() {
        transactionImportData.getTransactionList().forEach(tran -> {
            tran.getNonItemDetailList().forEach(nonItemDetail -> {
                nonItemDetail.setNonItemInfo(null);
            });
        });

        when(salesErrorSalesOrderInformationEntityMapper
                .insertSelective(any(ErrorEvacuationSalesOrderInformation.class))).thenReturn(1);
        when(salesErrorSalesTransactionHeaderEntityMapper
                .insertSelective(any(ErrorEvacuationSalesTransactionHeader.class))).thenReturn(1);
        when(salesErrorSalesTransactionDetailEntityMapper
                .insertSelective(any(ErrorEvacuationSalesTransactionDetail.class))).thenReturn(1);
        when(salesErrorSalesTransactionDetailInfoEntityMapper
                .insertSelective(any(ErrorEvacuationSalesTransactionDetailInfo.class)))
                        .thenReturn(1);
        when(salesErrorSalesTransactionDiscountEntityMapper
                .insertSelective(any(ErrorEvacuationSalesTransactionDiscount.class))).thenReturn(1);
        when(salesErrorSalesTransactionTaxEntityMapper
                .insertSelective(any(ErrorEvacuationSalesTransactionTax.class))).thenReturn(1);
        when(salesErrorSalesTransactionTenderEntityMapper
                .insertSelective(any(ErrorEvacuationSalesTransactionTender.class))).thenReturn(1);
        when(salesErrorSalesTransactionTenderInfoEntityMapper
                .insertSelective(any(ErrorEvacuationSalesTransactionTenderInfo.class)))
                        .thenReturn(1);
        when(salesErrorSalesTransactionTotalAmountEntityMapper
                .insertSelective(any(ErrorEvacuationSalesTransactionTotalAmount.class)))
                        .thenReturn(1);
        when(salesErrorSalesOrderInformationEntityMapper
                .insertSelective(any(ErrorEvacuationSalesOrderInformation.class))).thenReturn(1);
        when(salesErrorSalesTransactionHeaderEntityMapper
                .insertSelective(any(ErrorEvacuationSalesTransactionHeader.class))).thenReturn(1);
        when(salesErrorSalesTransactionDetailEntityMapper
                .insertSelective(any(ErrorEvacuationSalesTransactionDetail.class))).thenReturn(1);
        when(salesErrorSalesTransactionDetailInfoEntityMapper
                .insertSelective(any(ErrorEvacuationSalesTransactionDetailInfo.class)))
                        .thenReturn(1);
        when(salesErrorSalesTransactionDiscountEntityMapper
                .insertSelective(any(ErrorEvacuationSalesTransactionDiscount.class))).thenReturn(1);
        when(salesErrorSalesTransactionTaxEntityMapper
                .insertSelective(any(ErrorEvacuationSalesTransactionTax.class))).thenReturn(1);
        when(salesErrorSalesTransactionTenderEntityMapper
                .insertSelective(any(ErrorEvacuationSalesTransactionTender.class))).thenReturn(1);
        when(salesErrorSalesTransactionTenderInfoEntityMapper
                .insertSelective(any(ErrorEvacuationSalesTransactionTenderInfo.class)))
                        .thenReturn(1);
        when(salesErrorSalesTransactionTotalAmountEntityMapper
                .insertSelective(any(ErrorEvacuationSalesTransactionTotalAmount.class)))
                        .thenReturn(1);

        when(errorTableDataConverter.convertTErrorEvacuationSalesOrderInformationEntityForInsert(
                transactionImportData, userId, salesTransactionErrorId))
                        .thenReturn(new ErrorEvacuationSalesOrderInformation());
        when(errorTableDataConverter.convertTErrorEvacuationSalesTransactionHeaderEntityForInsert(
                any(Transaction.class), anyString(), anyString(), anyString()))
                        .thenReturn(new ErrorEvacuationSalesTransactionHeader());
        when(errorTableDataConverter.convertTErrorEvacuationSalesTransactionDetailEntityForInsert(
                any(TransactionItemDetail.class), anyString(), anyString(), anyInt(), anyString(),
                anyString(), anyInt(), anyInt()))
                        .thenReturn(new ErrorEvacuationSalesTransactionDetail());
        when(errorTableDataConverter
                .convertTErrorEvacuationSalesTransactionDetailEntityForInsertOutside(
                        any(NonItemDetail.class), anyString(), anyString(), anyInt(), anyString(),
                        anyString(), anyInt(), anyInt(), anyString()))
                                .thenReturn(new ErrorEvacuationSalesTransactionDetail());
        when(errorTableDataConverter
                .convertTErrorEvacuationSalesTransactionDetailInfoEntityForInsert(
                        any(NonItemInfo.class), anyString(), anyString(), anyInt(), anyString(),
                        anyInt(), anyInt(), anyString()))
                                .thenReturn(new ErrorEvacuationSalesTransactionDetailInfo());
        when(errorTableDataConverter.convertTErrorEvacuationSalesTransactionTenderEntityForInsert(
                any(SalesTransactionTender.class), anyString(), anyString(), anyInt(), anyString(),
                anyInt(), anyString())).thenReturn(new ErrorEvacuationSalesTransactionTender());
        when(errorTableDataConverter
                .convertTErrorEvacuationSalesTransactionTenderInfoEntityForInsert(
                        any(TenderInfo.class), anyString(), anyString(), anyInt(), anyString(),
                        anyString(), anyString(), anyString(), anyInt()))
                                .thenReturn(new ErrorEvacuationSalesTransactionTenderInfo());
        when(errorTableDataConverter
                .convertTErrorEvacuationSalesTransactionTaxEntityForInsertTransaction(
                        any(SalesTransactionTaxDetail.class), anyString(), anyString(), anyInt(),
                        anyString(), anyInt(), anyInt(), anyString()))
                                .thenReturn(new ErrorEvacuationSalesTransactionTax());
        when(errorTableDataConverter
                .convertTErrorEvacuationSalesTransactionTotalAmountEntityForInsert(
                        any(SalesTransactionTotal.class), anyString(), anyString(), anyInt(),
                        anyString(), anyInt(), anyString()))
                                .thenReturn(new ErrorEvacuationSalesTransactionTotalAmount());
        when(errorTableDataConverter
                .convertTErrorEvacuationSalesTransactionDiscountEntityForInsertNon(
                        any(NonItemDiscountDetail.class), anyString(), anyString(), anyInt(),
                        anyString(), anyInt(), anyInt(), anyString()))
                                .thenReturn(new ErrorEvacuationSalesTransactionDiscount());
        when(errorTableDataConverter.convertTErrorEvacuationSalesTransactionTaxEntityForInsertNon(
                any(NonItemTaxDetail.class), anyString(), anyString(), anyInt(), anyString(),
                anyInt(), anyInt(), anyString()))
                        .thenReturn(new ErrorEvacuationSalesTransactionTax());
        when(errorTableDataConverter.convertTErrorEvacuationSalesTransactionDiscountEntityForInsert(
                any(ItemDiscount.class), anyString(), anyString(), anyInt(), anyString(), anyInt(),
                anyInt(), anyString())).thenReturn(new ErrorEvacuationSalesTransactionDiscount());
        when(errorTableDataConverter.convertTErrorEvacuationSalesTransactionTaxEntityForInsert(
                any(ItemTaxDetail.class), anyString(), anyString(), anyInt(), anyString(), anyInt(),
                anyInt(), anyString())).thenReturn(new ErrorEvacuationSalesTransactionTax());


        insertErrorTableService.insertErrorEvacuationTable(transactionImportData,
                salesTransactionErrorId, userId);

        ArgumentCaptor<Transaction> transactionArgumentCaptor =
                ArgumentCaptor.forClass(Transaction.class);
        ArgumentCaptor<TransactionItemDetail> transactionItemDetailArgumentCaptor =
                ArgumentCaptor.forClass(TransactionItemDetail.class);
        ArgumentCaptor<NonItemDetail> nonItemDetailArgumentCaptor =
                ArgumentCaptor.forClass(NonItemDetail.class);
        ArgumentCaptor<NonItemInfo> nonItemInfoArgumentCaptor =
                ArgumentCaptor.forClass(NonItemInfo.class);
        ArgumentCaptor<SalesTransactionTender> salesTransactionTenderArgumentCaptor =
                ArgumentCaptor.forClass(SalesTransactionTender.class);
        ArgumentCaptor<TenderInfo> tenderInfoArgumentCaptor =
                ArgumentCaptor.forClass(TenderInfo.class);
        ArgumentCaptor<SalesTransactionTaxDetail> salesTransactionTaxDetailArgumentCaptor =
                ArgumentCaptor.forClass(SalesTransactionTaxDetail.class);
        ArgumentCaptor<SalesTransactionTotal> salesTransactionTotalArgumentCaptor =
                ArgumentCaptor.forClass(SalesTransactionTotal.class);
        ArgumentCaptor<NonItemDiscountDetail> nonItemDiscountDetailArgumentCaptor =
                ArgumentCaptor.forClass(NonItemDiscountDetail.class);
        ArgumentCaptor<NonItemTaxDetail> nonItemTaxDetailArgumentCaptor =
                ArgumentCaptor.forClass(NonItemTaxDetail.class);
        ArgumentCaptor<ItemDiscount> itemDiscountArgumentCaptor =
                ArgumentCaptor.forClass(ItemDiscount.class);
        ArgumentCaptor<ItemTaxDetail> itemTaxDetailArgumentCaptor =
                ArgumentCaptor.forClass(ItemTaxDetail.class);

        verify(errorTableDataConverter, times(1))
                .convertTErrorEvacuationSalesOrderInformationEntityForInsert(transactionImportData,
                        userId, salesTransactionErrorId);
        verify(errorTableDataConverter, times(transactionImportData.getTransactionList().size()))
                .convertTErrorEvacuationSalesTransactionHeaderEntityForInsert(
                        transactionArgumentCaptor.capture(), anyString(), anyString(), anyString());
        verify(errorTableDataConverter,
                times(transactionImportData.getTransactionList().stream().mapToInt(tran -> {
                    return tran.getTransactionItemDetailList().size();
                }).sum())).convertTErrorEvacuationSalesTransactionDetailEntityForInsert(
                        transactionItemDetailArgumentCaptor.capture(), anyString(), anyString(),
                        anyInt(), anyString(), anyString(), anyInt(), anyInt());
        verify(errorTableDataConverter,
                times(transactionImportData.getTransactionList().stream().mapToInt(tran -> {
                    return tran.getTransactionItemDetailList()
                            .stream()
                            .mapToInt(item -> item.getNonItemDetailListByItem().size())
                            .sum() + tran.getNonItemDetailList().size();
                }).sum())).convertTErrorEvacuationSalesTransactionDetailEntityForInsertOutside(
                        nonItemDetailArgumentCaptor.capture(), anyString(), anyString(), anyInt(),
                        anyString(), anyString(), anyInt(), anyInt(), anyString());
        verify(errorTableDataConverter,
                times(transactionImportData.getTransactionList().stream().mapToInt(tran -> {
                    return tran.getTransactionItemDetailList().stream().mapToInt(itemDetail -> {
                        return itemDetail.getNonItemDetailListByItem().size();
                    }).sum();
                }).sum())).convertTErrorEvacuationSalesTransactionDetailInfoEntityForInsert(
                        nonItemInfoArgumentCaptor.capture(), anyString(), anyString(), anyInt(),
                        anyString(), anyInt(), anyInt(), anyString());
        verify(errorTableDataConverter,
                times(transactionImportData.getTransactionList().stream().mapToInt(tran -> {
                    return tran.getSalesTransactionTenderList().size();
                }).sum())).convertTErrorEvacuationSalesTransactionTenderEntityForInsert(
                        salesTransactionTenderArgumentCaptor.capture(), anyString(), anyString(),
                        anyInt(), anyString(), anyInt(), anyString());
        verify(errorTableDataConverter,
                times(transactionImportData.getTransactionList().stream().mapToInt(tran -> {
                    return tran.getSalesTransactionTenderList().stream().mapToInt(tender -> {
                        return 1;
                    }).sum();
                }).sum())).convertTErrorEvacuationSalesTransactionTenderInfoEntityForInsert(
                        tenderInfoArgumentCaptor.capture(), anyString(), anyString(), anyInt(),
                        anyString(), anyString(), anyString(), anyString(), anyInt());
        verify(errorTableDataConverter,
                times(transactionImportData.getTransactionList().stream().mapToInt(tran -> {
                    return tran.getSalesTransactionTaxDetailList().size();
                }).sum())).convertTErrorEvacuationSalesTransactionTaxEntityForInsertTransaction(
                        salesTransactionTaxDetailArgumentCaptor.capture(), anyString(), anyString(),
                        anyInt(), anyString(), anyInt(), anyInt(), anyString());
        verify(errorTableDataConverter,
                times(transactionImportData.getTransactionList().stream().mapToInt(tran -> {
                    return tran.getSalesTransactionTotalList().size();
                }).sum())).convertTErrorEvacuationSalesTransactionTotalAmountEntityForInsert(
                        salesTransactionTotalArgumentCaptor.capture(), anyString(), anyString(),
                        anyInt(), anyString(), anyInt(), anyString());
        verify(errorTableDataConverter,
                times(transactionImportData.getTransactionList().stream().mapToInt(tran -> {
                    return tran.getTransactionItemDetailList().stream().mapToInt(itemDetail -> {
                        return itemDetail.getNonItemDetailListByItem()
                                .stream()
                                .mapToInt(nonItemDetail -> {
                                    return nonItemDetail.getNonItemDiscountDetailList().size();
                                })
                                .sum();
                    }).sum() + tran.getNonItemDetailList().stream().mapToInt(nonItemDetail -> {
                        return nonItemDetail.getNonItemDiscountDetailList().size();
                    }).sum();
                }).sum())).convertTErrorEvacuationSalesTransactionDiscountEntityForInsertNon(
                        nonItemDiscountDetailArgumentCaptor.capture(), anyString(), anyString(),
                        anyInt(), anyString(), anyInt(), anyInt(), anyString());
        verify(errorTableDataConverter,
                times(transactionImportData.getTransactionList().stream().mapToInt(tran -> {
                    return tran.getTransactionItemDetailList().stream().mapToInt(itemDetail -> {
                        return itemDetail.getNonItemDetailListByItem()
                                .stream()
                                .mapToInt(nonItemDetail -> {
                                    return nonItemDetail.getNonItemTaxDetailList().size();
                                })
                                .sum();
                    }).sum() + tran.getNonItemDetailList().stream().mapToInt(nonItemDetail -> {
                        return nonItemDetail.getNonItemTaxDetailList().size();
                    }).sum();
                }).sum())).convertTErrorEvacuationSalesTransactionTaxEntityForInsertNon(
                        nonItemTaxDetailArgumentCaptor.capture(), anyString(), anyString(),
                        anyInt(), anyString(), anyInt(), anyInt(), anyString());
        verify(errorTableDataConverter,
                times(transactionImportData.getTransactionList().stream().mapToInt(tran -> {
                    return tran.getTransactionItemDetailList().stream().mapToInt(itemDetail -> {
                        return itemDetail.getItemDiscountList().size();
                    }).sum();
                }).sum())).convertTErrorEvacuationSalesTransactionDiscountEntityForInsert(
                        itemDiscountArgumentCaptor.capture(), anyString(), anyString(), anyInt(),
                        anyString(), anyInt(), anyInt(), anyString());
        verify(errorTableDataConverter,
                times(transactionImportData.getTransactionList().stream().mapToInt(tran -> {
                    return tran.getTransactionItemDetailList().stream().mapToInt(itemDetail -> {
                        return itemDetail.getItemTaxDetailList().size();
                    }).sum();
                }).sum())).convertTErrorEvacuationSalesTransactionTaxEntityForInsert(
                        itemTaxDetailArgumentCaptor.capture(), anyString(), anyString(), anyInt(),
                        anyString(), anyInt(), anyInt(), anyString());

        transactionImportData.getTransactionList().forEach(transactionTmp -> {
            assertThat(transactionArgumentCaptor.getAllValues()).contains(transactionTmp);
            transactionTmp.getTransactionItemDetailList().forEach(itemDetail -> {
                assertThat(transactionItemDetailArgumentCaptor.getAllValues()).contains(itemDetail);
                itemDetail.getNonItemDetailListByItem().forEach(nonItemDetail -> {
                    assertThat(nonItemDetailArgumentCaptor.getAllValues()).contains(nonItemDetail);
                });
                itemDetail.getItemTaxDetailList().forEach(taxDetail -> {
                    assertThat(itemTaxDetailArgumentCaptor.getAllValues()).contains(taxDetail);
                });
                itemDetail.getItemDiscountList().forEach(discount -> {
                    assertThat(itemDiscountArgumentCaptor.getAllValues()).contains(discount);
                });
            });
            transactionTmp.getNonItemDetailList().forEach(nonItemDetail -> {
                assertThat(nonItemDetailArgumentCaptor.getAllValues()).contains(nonItemDetail);
                nonItemDetail.getNonItemDiscountDetailList().forEach(discount -> {
                    assertThat(nonItemDiscountDetailArgumentCaptor.getAllValues())
                            .contains(discount);
                });
                nonItemDetail.getNonItemTaxDetailList().forEach(nonTax -> {
                    assertThat(nonItemTaxDetailArgumentCaptor.getAllValues()).contains(nonTax);
                });
            });
            transactionTmp.getSalesTransactionTaxDetailList().forEach(taxDetail -> {
                assertThat(salesTransactionTaxDetailArgumentCaptor.getAllValues())
                        .contains(taxDetail);
            });
            transactionTmp.getSalesTransactionTenderList().forEach(tender -> {
                assertThat(salesTransactionTenderArgumentCaptor.getAllValues()).contains(tender);
                assertThat(tenderInfoArgumentCaptor.getAllValues())
                        .contains(tender.getTenderInfoList());
            });
            transactionTmp.getSalesTransactionTotalList().forEach(total -> {
                assertThat(salesTransactionTotalArgumentCaptor.getAllValues()).contains(total);
            });
        });
    }

    private void setTransactionImportData(TransactionImportData importData) {
        String key = "3";
        importData.setChannelCode("1");
        importData.setCustomerId("3");
        importData.setDataAlterationBackboneLinkageType(1);
        importData.setDataAlterationSalesLinkageType(1);
        importData.setDataCorrectionEditingFlag(true);
        importData.setDataCorrectionUserId(key);
        importData.setErrorCheckType(1);
        importData.setIntegratedOrderId("4");
        importData.setOrderBarcodeNumber("5");
        importData.setOrderConfirmationBusinessDate(key);
        importData.setOrderConfirmationDateTime(
                OffsetDateTime.of(2017, 12, 31, 8, 0, 0, 0, ZoneOffset.of("Z")));
        importData.setSalesTransactionErrorId("6");
        importData.setStoreCode("7");
        importData.setSystemBrandCode("8");
        importData.setSystemBusinessCode("1");
        importData.setSystemCountryCode("9");
        List<Transaction> transactionList = new ArrayList<>();
        Transaction transaction = new Transaction();
        setTransaction(transaction, "0");
        Transaction transaction1 = new Transaction();
        setTransaction(transaction1, "1");
        Transaction transaction2 = new Transaction();
        setTransaction(transaction2, "2");
        transactionList.add(transaction);
        transactionList.add(transaction1);
        transactionList.add(transaction2);
        importData.setTransactionList(transactionList);
        importData.setUpdateType(key);
    }

    private void setTransaction(Transaction transaction, String key) {
        transaction.setTransactionSerialNumber(1);
        transaction.setIntegratedOrderId("2" + key);
        transaction.setOrderSubNumber(1);
        transaction.setSalesTransactionId("transaction0001" + key);
        transaction.setTokenCode("3" + key);
        transaction.setTransactionType("4" + key);
        transaction.setReturnType(1);
        transaction.setSystemBrandCode("5" + key);
        transaction.setSystemBusinessCode("1" + key);
        transaction.setSystemCountryCode("6" + key);
        transaction.setStoreCode("7");
        transaction.setChannelCode("9");
        transaction.setDataCreationBusinessDate(key);
        transaction.setDataCreationDateTime(
                OffsetDateTime.of(2017, 12, 31, 8, 0, 0, 0, ZoneOffset.of("Z")));
        transaction.setOrderStatusUpdateDate(key);
        transaction.setOrderStatusLastUpdateDateTime(
                OffsetDateTime.of(2017, 12, 31, 8, 0, 0, 0, ZoneOffset.of("Z")));
        transaction.setCashRegisterNo(1);
        transaction.setReceiptNo(key);
        transaction.setOrderNumberForStorePayment(key);
        transaction.setAdvanceReceivedStoreCode(key);
        transaction.setAdvanceReceivedStoreSystemBrandCode(key);
        transaction.setAdvanceReceivedStoreSystemBusinessCode("1");
        transaction.setAdvanceReceivedStoreSystemCountryCode(key);
        transaction.setOperatorCode(key);
        transaction.setOriginalTransactionId(key);
        transaction.setOriginalReceiptNo("9");
        transaction.setOriginalCashRegisterNo(1);
        transaction.setDeposit(getPrice("1"));
        transaction.setChange(getPrice("2"));
        transaction.setReceiptNoForCreditCardCancellation(key);
        transaction.setReceiptNoForCreditCard(key);
        transaction.setPaymentStoreCode(key);
        transaction.setPaymentStoreSystemBrandCode(key);
        transaction.setPaymentStoreSystemBusinessCode("1");
        transaction.setPaymentStoreSystemCountryCode(key);
        transaction.setReceiptIssuedFlag(true);
        transaction.setProcessingCompanyCode(key);
        transaction.setOrderCancellationDate(
                OffsetDateTime.of(2017, 12, 31, 8, 0, 0, 0, ZoneOffset.of("Z")));
        transaction.setOrderStatus(key);
        transaction.setOrderSubstatus(key);
        transaction.setPaymentStatus(key);
        transaction.setShipmentStatus(key);
        transaction.setReceivingStatus(key);
        transaction.setTransferOutStatus(key);
        transaction.setBookingStoreCode(key);
        transaction.setBookingStoreSystemBrandCode(key);
        transaction.setBookingStoreSystemBusinessCode("1");
        transaction.setBookingStoreSystemCountryCode(key);
        transaction.setShipmentStoreCode(key);
        transaction.setShipmentStoreSystemBrandCode(key);
        transaction.setShipmentStoreSystemBusinessCode("1");
        transaction.setShipmentStoreSystemCountryCode(key);
        transaction.setReceiptStoreCode(key);
        transaction.setReceiptStoreSystemBrandCode(key);
        transaction.setReceiptStoreSystemBusinessCode("1");
        transaction.setReceiptStoreSystemCountryCode(key);
        transaction.setCustomerId(key);
        transaction.setCorporateId(key);
        transaction.setSalesTransactionDiscountFlag(true);
        transaction.setSalesTransactionDiscountAmountRate(getPrice("3"));
        transaction.setConsistencySalesFlag(true);
        transaction.setEmployeeSaleFlag(true);
        transaction.setEReceiptTargetFlag(true);

        List<TransactionItemDetail> transactionItemDetailList = new ArrayList<>();
        TransactionItemDetail transactionItemDetail = new TransactionItemDetail();
        setTransactionItemDetail(transactionItemDetail, "9");
        TransactionItemDetail transactionItemDetail2 = new TransactionItemDetail();
        setTransactionItemDetail(transactionItemDetail2, "8");
        TransactionItemDetail transactionItemDetail3 = new TransactionItemDetail();
        setTransactionItemDetail(transactionItemDetail3, "7");
        transactionItemDetailList.add(transactionItemDetail);
        transactionItemDetailList.add(transactionItemDetail2);
        transactionItemDetailList.add(transactionItemDetail3);
        transaction.setTransactionItemDetailList(transactionItemDetailList);

        List<NonItemDetail> nonItemDetailList = new ArrayList<>();
        NonItemDetail nonItemDetail = new NonItemDetail();
        setNonItemDetailTwo(nonItemDetail, "6");
        NonItemDetail nonItemDetail2 = new NonItemDetail();
        setNonItemDetailTwo(nonItemDetail2, "5");
        NonItemDetail nonItemDetail3 = new NonItemDetail();
        setNonItemDetailTwo(nonItemDetail3, "4");
        nonItemDetailList.add(nonItemDetail3);
        transaction.setNonItemDetailList(nonItemDetailList);

        List<SalesTransactionTender> salesTransactionTenderList = new ArrayList<>();
        SalesTransactionTender salesTransactionTender = new SalesTransactionTender();
        setSalesTransactionTender(salesTransactionTender, "3");
        salesTransactionTenderList.add(salesTransactionTender);
        transaction.setSalesTransactionTenderList(salesTransactionTenderList);

        List<SalesTransactionTaxDetail> salesTransactionTaxDetailList = new ArrayList<>();
        SalesTransactionTaxDetail salesTransactionTaxDetail = new SalesTransactionTaxDetail();
        setSalesTransactionTaxDetail(salesTransactionTaxDetail, "2");
        salesTransactionTaxDetailList.add(salesTransactionTaxDetail);
        transaction.setSalesTransactionTaxDetailList(salesTransactionTaxDetailList);

        List<SalesTransactionTotal> salesTransactionTotalList = new ArrayList<>();
        SalesTransactionTotal salesTransactionTotal = new SalesTransactionTotal();
        setSalesTransactionTotal(salesTransactionTotal, "1");
        salesTransactionTotalList.add(salesTransactionTotal);
        transaction.setSalesTransactionTotalList(salesTransactionTotalList);
    }

    void setTransactionItemDetail(TransactionItemDetail entity, String key) {
        entity.setSystemBrandCode(key);
        entity.setDetailSubNumber(1);
        entity.setDetailListSalesTransactionType(key);
        entity.setL2ItemCode(key);
        entity.setViewL2ItemCode(key);
        entity.setL2ProductName(key);
        entity.setL3ItemCode(key);
        entity.setL3PosProductName(key);
        entity.setEpcCode(key);
        entity.setGDepartmentCode(key);
        entity.setDeptCode(1);
        entity.setQuantityCode(key);
        entity.setItemCost(getPrice("1"));
        entity.setInitialSellingPrice(getPrice("2"));
        entity.setBItemSellingPrice(getPrice("3"));
        entity.setItemNewPrice(getPrice("4"));
        entity.setItemUnitPriceTaxExcluded(getPrice("6"));
        entity.setItemUnitPriceTaxIncluded(getPrice("5"));
        entity.setItemSalesAmtTaxExcluded(getPrice("8"));
        entity.setItemSalesAmtTaxIncluded(getPrice("9"));
        entity.setOrderStatusUpdateDate(key);
        entity.setOrderStatusLastUpdateDateTime(
                OffsetDateTime.of(2017, 12, 31, 8, 0, 0, 0, ZoneOffset.of("Z")));
        entity.setOrderStatus(key);
        entity.setOrderSubstatus(key);
        entity.setBookingStoreCode(key);
        entity.setBookingStoreSystemBrandCode(key);
        entity.setBookingStoreSystemBusinessCode("1");
        entity.setBookingStoreSystemCountryCode(key);
        entity.setShipmentStoreCode(key);
        entity.setShipmentStoreSystemBrandCode(key);
        entity.setShipmentStoreSystemBusinessCode("1");
        entity.setShipmentStoreSystemCountryCode(key);
        entity.setReceiptStoreCode(key);
        entity.setReceiptStoreSystemBrandCode(key);
        entity.setReceiptStoreSystemBusinessCode("1");
        entity.setReceiptStoreSystemCountryCode(key);
        entity.setContributionSalesRepresentative(key);
        entity.setCustomerId(key);
        entity.setBundlePurchaseQty(1);
        entity.setBundlePurchasePrice(getPrice("13"));
        entity.setBundlePurchaseIndex(1);
        entity.setLimitedAmountPromotionCount(1);
        entity.setCalculationUnavailableType("1");
        entity.setItemDiscountAmount(getPrice("11"));
        entity.setBundleSalesFlag(true);
        entity.setBundleSalesPrice(getPrice("12"));
        entity.setBundleSalesDetailIndex(1);
        entity.setItemDetailNumber(1);
        entity.setItemTaxationType("8");
        entity.setItemTaxKind(key);
        List<NonItemDetail> nonItemDetailList = new ArrayList<>();
        NonItemDetail nonItemDetail = new NonItemDetail();
        setNonItemDetailOne(nonItemDetail, "0");
        nonItemDetailList.add(nonItemDetail);
        NonItemDetail nonItemDetail2 = new NonItemDetail();
        setNonItemDetailOne(nonItemDetail2, "1");
        nonItemDetailList.add(nonItemDetail2);
        NonItemDetail nonItemDetail3 = new NonItemDetail();
        setNonItemDetailOne(nonItemDetail3, "8");
        nonItemDetailList.add(nonItemDetail3);
        entity.setNonItemDetailListByItem(nonItemDetailList);

        List<ItemDiscount> itemDiscountList = new ArrayList<>();
        ItemDiscount itemDiscount = new ItemDiscount();
        setItemDiscount(itemDiscount, "2");
        itemDiscountList.add(itemDiscount);
        entity.setItemDiscountList(itemDiscountList);

        List<ItemTaxDetail> itemTaxDetailList = new ArrayList<>();
        ItemTaxDetail itemTaxDetail = new ItemTaxDetail();
        setItemTaxDetail(itemTaxDetail, "1");
        itemTaxDetailList.add(itemTaxDetail);
        entity.setItemTaxDetailList(itemTaxDetailList);
    }

    private void setNonItemDetailOne(NonItemDetail entity, String key) {
        entity.setDetailSubNumber(321);
        entity.setItemDetailSubNumber(222);
        entity.setNonMdType(key);
        entity.setNonItemCode(key);
        entity.setServiceCode(key);
        entity.setPosNonItemName(key);
        entity.setQuantityCode(key);
        entity.setNonItemQty(1);
        entity.setNonItemUnitPriceTaxExcluded(getPrice("6"));
        entity.setNonItemUnitPriceTaxIncluded(getPrice("7"));
        entity.setNonItemSalesAmtTaxExcluded(getPrice("3"));
        entity.setNonItemSalesAmtTaxIncluded(getPrice("2"));
        entity.setNonItemNewPrice(getPrice("0"));
        entity.setNonCalculationNonItemType("1");
        entity.setOrderStatusUpdateDate(key);
        entity.setOrderStatusLastUpdateDateTime(
                OffsetDateTime.of(2017, 12, 31, 8, 0, 0, 0, ZoneOffset.of("Z")));
        entity.setOrderStatus(key);
        entity.setOrderSubstatus(key);
        entity.setBookingStoreCode(key);
        entity.setBookingStoreSystemBrandCode(key);
        entity.setBookingStoreSystemBusinessCode("1");
        entity.setBookingStoreSystemCountryCode(key);
        entity.setShipmentStoreCode(key);
        entity.setShipmentStoreSystemBrandCode(key);
        entity.setShipmentStoreSystemBusinessCode("1");
        entity.setShipmentStoreSystemCountryCode(key);
        entity.setReceiptStoreCode(key);
        entity.setReceiptStoreSystemBrandCode(key);
        entity.setReceiptStoreSystemBusinessCode("1");
        entity.setReceiptStoreSystemCountryCode(key);
        entity.setContributionSalesRepresentative(key);
        entity.setNonItemTaxationType("7");
        entity.setNonItemTaxKind(key);
        NonItemInfo nonItemInfo = new NonItemInfo();
        setNonItemInfoOne(nonItemInfo, "6");
        entity.setNonItemInfo(nonItemInfo);

        List<NonItemDiscountDetail> nonItemDiscountDetailList = new ArrayList<>();
        NonItemDiscountDetail nonItemDiscountDetail = new NonItemDiscountDetail();
        setNonItemDiscountDetailOne(nonItemDiscountDetail, "3");
        nonItemDiscountDetailList.add(nonItemDiscountDetail);
        entity.setNonItemDiscountDetailList(nonItemDiscountDetailList);

        List<NonItemTaxDetail> nonItemTaxDetailList = new ArrayList<>();
        NonItemTaxDetail nonItemTaxDetail = new NonItemTaxDetail();
        setNonItemTaxDetailOne(nonItemTaxDetail, "1");
        nonItemTaxDetailList.add(nonItemTaxDetail);
        entity.setNonItemTaxDetailList(nonItemTaxDetailList);
    }

    private void setNonItemInfoOne(NonItemInfo entity, String key) {
        entity.setDetailSubNumber(1);
        entity.setItemDetailSubNumber(1);
        entity.setKeyCode(key);
        entity.setCodeValue1(key);
        entity.setCodeValue2(key);
        entity.setCodeValue3(key);
        entity.setCodeValue4(key);
        entity.setName1(key);
        entity.setName2(key);
        entity.setName3(key);
        entity.setName4(key);
    }

    private void setNonItemDiscountDetailOne(NonItemDiscountDetail entity, String key) {
        entity.setNonItemDiscountSubNumber(1);
        entity.setNonItemPromotionType(key);
        entity.setNonItemStoreDiscountType(key);
        entity.setNonItemQuantityCode(key);
        entity.setNonItemDiscountQty(1);
        entity.setNonItemDiscountAmtTaxExcluded(getPrice(""));
        entity.setNonItemDiscountAmtTaxIncluded(getPrice("2"));
    }

    private void setNonItemTaxDetailOne(NonItemTaxDetail entity, String key) {
        entity.setNonItemTaxDetailSubNumber(1);
        entity.setNonItemTaxSubNumber(2);
        entity.setNonItemTaxType(key);
        entity.setNonItemTaxName(key);
        entity.setNonItemTaxAmountSign(key);
        entity.setNonItemTaxAmt(getPrice("1"));
        entity.setNonItemTaxRate(new BigDecimal(1));
    }

    private void setItemDiscount(ItemDiscount entity, String key) {
        entity.setItemDiscountDetailSubNumber(1);
        entity.setItemDiscountSubNumber(1);
        entity.setItemPromotionType(key);
        entity.setItemPromotionNumber("1");
        entity.setItemStoreDiscountType(key);
        entity.setItemQuantityCode(key);
        entity.setItemDiscountQty(1);
        entity.setItemDiscountAmtTaxExcluded(getPrice("2"));
        entity.setItemDiscountAmtTaxIncluded(getPrice("3"));

    }

    private void setItemTaxDetail(ItemTaxDetail entity, String key) {
        entity.setItemTaxDetailSubNumber(1);
        entity.setItemTaxSubNumber(1);
        entity.setItemTaxType(key);
        entity.setItemTaxName(key);
        entity.setItemTaxAmountSign(key);
        entity.setItemTaxAmt(getPrice("3"));
        entity.setItemTaxRate(new BigDecimal(1));

    }

    private void setNonItemDetailTwo(NonItemDetail entity, String key) {
        entity.setDetailSubNumber(123);
        entity.setNonItemDetailNumber(1);
        entity.setNonMdDetailListSalesTransactionType(key);
        entity.setNonItemDetailSalesLinkageType(1);
        entity.setNonMdType(key);
        entity.setNonItemCode(key);
        entity.setServiceCode(key);
        entity.setPosNonItemName(key);
        entity.setQuantityCode(key);
        entity.setNonItemQty(1);
        entity.setNonItemUnitPriceTaxExcluded(getPrice("5"));
        entity.setNonItemUnitPriceTaxIncluded(getPrice("4"));
        entity.setNonItemSalesAmtTaxExcluded(getPrice("3"));
        entity.setNonItemSalesAmtTaxIncluded(getPrice("1"));
        entity.setNonItemNewPrice(getPrice("2"));
        entity.setNonCalculationNonItemType("1");
        entity.setOrderStatusUpdateDate(key);
        entity.setOrderStatusLastUpdateDateTime(
                OffsetDateTime.of(2017, 12, 31, 8, 0, 0, 0, ZoneOffset.of("Z")));
        entity.setOrderStatus(key);
        entity.setOrderSubstatus(key);
        entity.setBookingStoreCode(key);
        entity.setBookingStoreSystemBrandCode(key);
        entity.setBookingStoreSystemBusinessCode("1");
        entity.setBookingStoreSystemCountryCode(key);
        entity.setShipmentStoreCode(key);
        entity.setShipmentStoreSystemBrandCode(key);
        entity.setShipmentStoreSystemBusinessCode("1");
        entity.setShipmentStoreSystemCountryCode(key);
        entity.setReceiptStoreCode(key);
        entity.setReceiptStoreSystemBrandCode(key);
        entity.setReceiptStoreSystemBusinessCode("1");
        entity.setReceiptStoreSystemCountryCode(key);
        entity.setContributionSalesRepresentative(key);
        entity.setItemDetailSubNumber(1);
        entity.setNonItemTaxationType(key);
        entity.setNonItemTaxKind(key);

        NonItemInfo nonItemInfo = new NonItemInfo();
        setNonItemInfoTwo(nonItemInfo, "2");
        entity.setNonItemInfo(nonItemInfo);

        List<NonItemDiscountDetail> nonItemDiscountDetailList = new ArrayList<>();
        NonItemDiscountDetail nonItemDiscountDetail = new NonItemDiscountDetail();
        setNonItemDiscountDetailTwo(nonItemDiscountDetail, "3");
        nonItemDiscountDetailList.add(nonItemDiscountDetail);
        entity.setNonItemDiscountDetailList(nonItemDiscountDetailList);

        List<NonItemTaxDetail> nonItemTaxDetailList = new ArrayList<>();
        NonItemTaxDetail nonItemTaxDetail = new NonItemTaxDetail();
        setNonItemTaxDetailTwo(nonItemTaxDetail, "9");
        nonItemTaxDetailList.add(nonItemTaxDetail);
        entity.setNonItemTaxDetailList(nonItemTaxDetailList);

    }

    private void setNonItemInfoTwo(NonItemInfo entity, String key) {
        entity.setDetailSubNumber(1);
        entity.setItemDetailSubNumber(1);
        entity.setKeyCode(key);
        entity.setCodeValue1(key);
        entity.setCodeValue2(key);
        entity.setCodeValue3(key);
        entity.setCodeValue4(key);
        entity.setName1(key);
        entity.setName2(key);

    }

    private void setNonItemDiscountDetailTwo(NonItemDiscountDetail entity, String key) {
        entity.setNonItemDiscountDetailSubNumber(2);
        entity.setNonItemPromotionType(key);
        entity.setNonItemStoreDiscountType(key);
        entity.setNonItemQuantityCode(key);
        entity.setNonItemDiscountQty(1);
        entity.setNonItemDiscountAmtTaxExcluded(getPrice("5"));
        entity.setNonItemDiscountAmtTaxIncluded(getPrice("8"));

    }

    private void setNonItemTaxDetailTwo(NonItemTaxDetail entity, String key) {
        entity.setNonItemTaxDetailSubNumber(1);
        entity.setNonItemTaxType(key);
        entity.setNonItemTaxName(key);
        entity.setNonItemTaxAmountSign(key);
        entity.setNonItemTaxAmt(getPrice("0"));
        entity.setNonItemTaxRate(new BigDecimal(1));

    }

    private void setSalesTransactionTender(SalesTransactionTender entity, String key) {
        entity.setTenderSubNumber(1);
        entity.setTenderGroupId(key);
        entity.setTenderId(key);
        entity.setPaymentSign(key);
        entity.setTaxIncludedPaymentAmount(getPrice("2"));
        TenderInfo tenderInfo = new TenderInfo();
        setTenderInfo(tenderInfo, "4");
        entity.setTenderInfoList(tenderInfo);

    }

    private void setTenderInfo(TenderInfo entity, String key) {
        entity.setDiscountAmount(getPrice("3"));
        entity.setDiscountRate(new BigDecimal(1));
        entity.setDiscountCodeIdCorporateId(key);
        entity.setCouponDiscountAmountSetting(getPrice(""));
        entity.setCouponType(key);
        entity.setCouponDiscountAmountSetting(getPrice("4"));
        entity.setCouponMinUsageAmountThreshold(getPrice("2"));
        entity.setCouponUserId(key);
        entity.setCardNo(key);
        entity.setCreditApprovalCode(key);
        entity.setCreditProcessingSerialNumber(key);
        entity.setCreditPaymentType(key);
        entity.setCreditPaymentCount(1);
        entity.setCreditAffiliatedStoreNumber(key);

    }

    private void setSalesTransactionTaxDetail(SalesTransactionTaxDetail entity, String key) {
        entity.setTaxSubNumber(1);
        entity.setTaxGroup(key);
        entity.setTaxAmountSign(key);
        entity.setTaxAmount(getPrice("2"));

    }

    private void setSalesTransactionTotal(SalesTransactionTotal entity, String key) {
        entity.setTotalAmountSubNumber(1);
        entity.setTotalType("1");
        entity.setTotalAmountTaxExcluded(getPrice(""));
        entity.setTotalAmountTaxIncluded(getPrice("1"));
        entity.setConsumptionTaxRate(new BigDecimal(1));
        entity.setSalesTransactionInformation1(key);
        entity.setSalesTransactionInformation2(key);
        entity.setSalesTransactionInformation3(key);

    }

    private Price getPrice(String key) {
        Price price = new Price();
        price.setCurrencyCode(Currency.getInstance(Locale.JAPAN));
        price.setValue(new BigDecimal(1));
        return price;
    }
}
