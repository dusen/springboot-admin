package com.fastretailing.dcp.sales.importtransaction.component;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
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
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;
import com.fastretailing.dcp.sales.common.type.ErrorType;
import com.fastretailing.dcp.sales.common.type.UpdateType;
import com.fastretailing.dcp.sales.importtransaction.converter.CommonDataProcessor;
import com.fastretailing.dcp.sales.importtransaction.converter.HistoryTableDataConverter;
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
import com.fastretailing.dcp.sales.importtransaction.entity.AlterationHistoryOrderInformation;
import com.fastretailing.dcp.sales.importtransaction.entity.AlterationHistorySalesTransactionDetail;
import com.fastretailing.dcp.sales.importtransaction.entity.AlterationHistorySalesTransactionDetailInfo;
import com.fastretailing.dcp.sales.importtransaction.entity.AlterationHistorySalesTransactionDiscount;
import com.fastretailing.dcp.sales.importtransaction.entity.AlterationHistorySalesTransactionHeader;
import com.fastretailing.dcp.sales.importtransaction.entity.AlterationHistorySalesTransactionTax;
import com.fastretailing.dcp.sales.importtransaction.entity.AlterationHistorySalesTransactionTender;
import com.fastretailing.dcp.sales.importtransaction.entity.AlterationHistorySalesTransactionTenderInfo;
import com.fastretailing.dcp.sales.importtransaction.entity.AlterationHistorySalesTransactionTotalAmount;
import com.fastretailing.dcp.sales.importtransaction.entity.ErrorEvacuationSalesOrderInformationCondition;
import com.fastretailing.dcp.sales.importtransaction.entity.ErrorEvacuationSalesTransactionDetailCondition;
import com.fastretailing.dcp.sales.importtransaction.entity.ErrorEvacuationSalesTransactionDetailInfoCondition;
import com.fastretailing.dcp.sales.importtransaction.entity.ErrorEvacuationSalesTransactionDiscountCondition;
import com.fastretailing.dcp.sales.importtransaction.entity.ErrorEvacuationSalesTransactionHeaderCondition;
import com.fastretailing.dcp.sales.importtransaction.entity.ErrorEvacuationSalesTransactionTaxCondition;
import com.fastretailing.dcp.sales.importtransaction.entity.ErrorEvacuationSalesTransactionTenderCondition;
import com.fastretailing.dcp.sales.importtransaction.entity.ErrorEvacuationSalesTransactionTenderInfoCondition;
import com.fastretailing.dcp.sales.importtransaction.entity.ErrorEvacuationSalesTransactionTotalAmountCondition;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesOrderInformation;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesOrderInformationCondition;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesOrderInformationKey;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionDetail;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionDetailCondition;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionDetailInfo;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionDetailInfoCondition;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionDiscount;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionDiscountCondition;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionHeader;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionHeaderCondition;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionTax;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionTaxCondition;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionTender;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionTenderCondition;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionTenderInfo;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionTenderInfoCondition;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionTotalAmount;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionTotalAmountCondition;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionErrorDetail;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionErrorDetailCondition;
import com.fastretailing.dcp.sales.importtransaction.exception.ErrorEvacuationException;
import com.fastretailing.dcp.sales.importtransaction.exception.InvalidDataException;
import com.fastretailing.dcp.sales.importtransaction.repository.AlterationHistoryOrderInformationMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.AlterationHistorySalesTransactionDetailInfoMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.AlterationHistorySalesTransactionDetailMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.AlterationHistorySalesTransactionDiscountMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.AlterationHistorySalesTransactionHeaderMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.AlterationHistorySalesTransactionTaxMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.AlterationHistorySalesTransactionTenderInfoMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.AlterationHistorySalesTransactionTenderMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.AlterationHistorySalesTransactionTotalAmountMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.ErrorEvacuationSalesOrderInformationMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.ErrorEvacuationSalesTransactionDetailInfoMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.ErrorEvacuationSalesTransactionDetailMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.ErrorEvacuationSalesTransactionDiscountMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.ErrorEvacuationSalesTransactionHeaderMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.ErrorEvacuationSalesTransactionTaxMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.ErrorEvacuationSalesTransactionTenderInfoMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.ErrorEvacuationSalesTransactionTenderMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.ErrorEvacuationSalesTransactionTotalAmountMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.SalesErrorSalesOrderInformationMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.SalesErrorSalesTransactionDetailInfoMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.SalesErrorSalesTransactionDetailMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.SalesErrorSalesTransactionDiscountMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.SalesErrorSalesTransactionHeaderMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.SalesErrorSalesTransactionTaxMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.SalesErrorSalesTransactionTenderInfoMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.SalesErrorSalesTransactionTenderMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.SalesErrorSalesTransactionTotalAmountMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.SalesTransactionErrorDetailMapper;
import com.fastretailing.dcp.storecommon.dto.Price;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ErrorDataCorrectionServiceTest {

    @SpyBean
    private ErrorDataCorrectionService errorDataCorrectionService;

    @SpyBean
    private ErrorDataCorrectionServiceImpl errorDataCorrectionServiceImpl;

    @MockBean
    private UpdateSalesCoreService updateSalesCoreService;

    @MockBean
    InsertSalesCoreService insertSalesCoreTableService;

    @MockBean
    HistoryTableDataConverter historyTableDataConverter;

    @MockBean
    SalesErrorSalesOrderInformationMapper salesErrorSalesOrderInformationEntityMapper;

    @MockBean
    SalesErrorSalesTransactionHeaderMapper salesErrorSalesTransactionHeaderEntityMapper;

    @MockBean
    ErrorEvacuationSalesTransactionHeaderMapper errorEvacuationSalesTransactionHeaderEntityMapper;

    @MockBean
    SalesErrorSalesTransactionDetailMapper salesErrorSalesTransactionDetailEntityMapper;

    @MockBean
    ErrorEvacuationSalesTransactionDetailMapper errorEvacuationSalesTransactionDetailEntityMapper;

    @MockBean
    ErrorEvacuationSalesTransactionDetailInfoMapper errorEvacuationSalesTransactionDetailInfoEntityMapper;

    @MockBean
    SalesErrorSalesTransactionDetailInfoMapper salesErrorSalesTransactionDetailInfoEntityMapper;

    @MockBean
    SalesErrorSalesTransactionDiscountMapper salesErrorSalesTransactionDiscountEntityMapper;

    @MockBean
    ErrorEvacuationSalesTransactionDiscountMapper errorEvacuationSalesTransactionDiscountEntityMapper;

    @MockBean
    SalesErrorSalesTransactionTaxMapper salesErrorSalesTransactionTaxEntityMapper;

    @MockBean
    ErrorEvacuationSalesTransactionTaxMapper errorEvacuationSalesTransactionTaxEntityMapper;

    @MockBean
    SalesErrorSalesTransactionTenderMapper salesErrorSalesTransactionTenderEntityMapper;

    @MockBean
    ErrorEvacuationSalesTransactionTenderMapper errorEvacuationSalesTransactionTenderEntityMapper;

    @MockBean
    SalesErrorSalesTransactionTenderInfoMapper salesErrorSalesTransactionTenderInfoEntityMapper;

    @MockBean
    ErrorEvacuationSalesTransactionTenderInfoMapper errorEvacuationSalesTransactionTenderInfoEntityMapper;

    @MockBean
    SalesErrorSalesTransactionTotalAmountMapper salesErrorSalesTransactionTotalAmountEntityMapper;

    @MockBean
    ErrorEvacuationSalesTransactionTotalAmountMapper errorEvacuationSalesTransactionTotalAmountEntityMapper;

    @MockBean
    ErrorEvacuationSalesOrderInformationMapper errorEvacuationSalesOrderInformationEntityMapper;

    @MockBean
    AlterationHistoryOrderInformationMapper alterationHistoryOrderInformationEntityMapper;

    @MockBean
    AlterationHistorySalesTransactionHeaderMapper alterationHistorySalesTransactionHeaderEntityMapper;

    @MockBean
    AlterationHistorySalesTransactionDetailMapper alterationHistorySalesTransactionDetailEntityMapper;

    @MockBean
    AlterationHistorySalesTransactionDetailInfoMapper alterationHistorySalesTransactionDetailInfoEntityMapper;

    @MockBean
    AlterationHistorySalesTransactionDiscountMapper alterationHistorySalesTransactionDiscountEntityMapper;

    @MockBean
    AlterationHistorySalesTransactionTaxMapper alterationHistorySalesTransactionTaxEntityMapper;

    @MockBean
    AlterationHistorySalesTransactionTenderMapper alterationHistorySalesTransactionTenderEntityMapper;

    @MockBean
    AlterationHistorySalesTransactionTenderInfoMapper alterationHistorySalesTransactionTenderInfoEntityMapper;

    @MockBean
    AlterationHistorySalesTransactionTotalAmountMapper alterationHistorySalesTransactionTotalAmountEntityMapper;

    @MockBean
    SalesTransactionErrorDetailMapper salesTransactionErrorDetailEntityMapper;

    @MockBean
    CommonDataProcessor commonDataProcessor;

    @MockBean
    ModelMapper modelMapper;
    private TransactionImportData transactionImportData;

    @Before
    public void setUp() {
        transactionImportData = new TransactionImportData();
        setTransactionImportData(transactionImportData);
        transactionImportData.setSalesTransactionErrorId("transactionid00000000000000000001");
        transactionImportData.setUpdateType("CORRECTION");
        when(alterationHistorySalesTransactionTotalAmountEntityMapper
                .insertSelective(Mockito.any(AlterationHistorySalesTransactionTotalAmount.class)))
                        .thenReturn(1);
        when(alterationHistoryOrderInformationEntityMapper
                .insertSelective(Mockito.any(AlterationHistoryOrderInformation.class)))
                        .thenReturn(1);
        when(alterationHistorySalesTransactionDetailEntityMapper
                .insertSelective(Mockito.any(AlterationHistorySalesTransactionDetail.class)))
                        .thenReturn(1);
        when(alterationHistorySalesTransactionDetailInfoEntityMapper
                .insertSelective(Mockito.any(AlterationHistorySalesTransactionDetailInfo.class)))
                        .thenReturn(1);
        when(alterationHistorySalesTransactionDiscountEntityMapper
                .insertSelective(Mockito.any(AlterationHistorySalesTransactionDiscount.class)))
                        .thenReturn(1);
        when(alterationHistorySalesTransactionHeaderEntityMapper
                .insertSelective(Mockito.any(AlterationHistorySalesTransactionHeader.class)))
                        .thenReturn(1);
        when(alterationHistorySalesTransactionTaxEntityMapper
                .insertSelective(Mockito.any(AlterationHistorySalesTransactionTax.class)))
                        .thenReturn(1);
        when(alterationHistorySalesTransactionTenderEntityMapper
                .insertSelective(Mockito.any(AlterationHistorySalesTransactionTender.class)))
                        .thenReturn(1);
        when(alterationHistorySalesTransactionTenderInfoEntityMapper
                .insertSelective(Mockito.any(AlterationHistorySalesTransactionTenderInfo.class)))
                        .thenReturn(1);
        List<SalesErrorSalesOrderInformation> salesErrorSalesOrderInformationEntity =
                new ArrayList<>();
        SalesErrorSalesOrderInformation a = new SalesErrorSalesOrderInformation();
        a.setTransactionId("56541321");
        salesErrorSalesOrderInformationEntity.add(a);
        when(salesErrorSalesOrderInformationEntityMapper
                .selectByCondition(Mockito.any(SalesErrorSalesOrderInformationCondition.class)))
                        .thenReturn(salesErrorSalesOrderInformationEntity);
        List<SalesErrorSalesTransactionDetail> salesErrorSalesTransactionDetailEntity =
                new ArrayList<>();
        SalesErrorSalesTransactionDetail a1 = new SalesErrorSalesTransactionDetail();
        a1.setTransactionId("56541321");
        salesErrorSalesTransactionDetailEntity.add(a1);
        when(salesErrorSalesTransactionDetailEntityMapper
                .selectByCondition(Mockito.any(SalesErrorSalesTransactionDetailCondition.class)))
                        .thenReturn(salesErrorSalesTransactionDetailEntity);
        List<SalesErrorSalesTransactionDetailInfo> TSalesErrorSalesTransactionDetailInfoEntity =
                new ArrayList<>();
        SalesErrorSalesTransactionDetailInfo a2 = new SalesErrorSalesTransactionDetailInfo();
        a2.setTransactionId("56541321");
        TSalesErrorSalesTransactionDetailInfoEntity.add(a2);
        when(salesErrorSalesTransactionDetailInfoEntityMapper.selectByCondition(
                Mockito.any(SalesErrorSalesTransactionDetailInfoCondition.class)))
                        .thenReturn(TSalesErrorSalesTransactionDetailInfoEntity);
        List<SalesErrorSalesTransactionDiscount> TSalesErrorSalesTransactionDiscountEntity =
                new ArrayList<>();
        SalesErrorSalesTransactionDiscount a3 = new SalesErrorSalesTransactionDiscount();
        a3.setTransactionId("56541321");
        TSalesErrorSalesTransactionDiscountEntity.add(a3);
        when(salesErrorSalesTransactionDiscountEntityMapper
                .selectByCondition(Mockito.any(SalesErrorSalesTransactionDiscountCondition.class)))
                        .thenReturn(TSalesErrorSalesTransactionDiscountEntity);
        List<SalesErrorSalesTransactionHeader> TSalesErrorSalesTransactionHeaderEntity =
                new ArrayList<>();
        SalesErrorSalesTransactionHeader a4 = new SalesErrorSalesTransactionHeader();
        a4.setTransactionId("56541321");
        TSalesErrorSalesTransactionHeaderEntity.add(a4);
        when(salesErrorSalesTransactionHeaderEntityMapper
                .selectByCondition(Mockito.any(SalesErrorSalesTransactionHeaderCondition.class)))
                        .thenReturn(TSalesErrorSalesTransactionHeaderEntity);
        List<SalesErrorSalesTransactionTax> TSalesErrorSalesTransactionTaxEntity =
                new ArrayList<>();
        SalesErrorSalesTransactionTax a5 = new SalesErrorSalesTransactionTax();
        a5.setTransactionId("56541321");
        TSalesErrorSalesTransactionTaxEntity.add(a5);
        when(salesErrorSalesTransactionTaxEntityMapper
                .selectByCondition(Mockito.any(SalesErrorSalesTransactionTaxCondition.class)))
                        .thenReturn(TSalesErrorSalesTransactionTaxEntity);
        List<SalesErrorSalesTransactionTender> TSalesErrorSalesTransactionTenderEntity =
                new ArrayList<>();
        SalesErrorSalesTransactionTender a6 = new SalesErrorSalesTransactionTender();
        a6.setTransactionId("56541321");
        TSalesErrorSalesTransactionTenderEntity.add(a6);
        when(salesErrorSalesTransactionTenderEntityMapper
                .selectByCondition(Mockito.any(SalesErrorSalesTransactionTenderCondition.class)))
                        .thenReturn(TSalesErrorSalesTransactionTenderEntity);
        List<SalesErrorSalesTransactionTenderInfo> TSalesErrorSalesTransactionTenderInfoEntity =
                new ArrayList<>();
        SalesErrorSalesTransactionTenderInfo a7 = new SalesErrorSalesTransactionTenderInfo();
        a7.setTransactionId("56541321");
        TSalesErrorSalesTransactionTenderInfoEntity.add(a7);
        when(salesErrorSalesTransactionTenderInfoEntityMapper.selectByCondition(
                Mockito.any(SalesErrorSalesTransactionTenderInfoCondition.class)))
                        .thenReturn(TSalesErrorSalesTransactionTenderInfoEntity);
        List<SalesErrorSalesTransactionTotalAmount> TSalesErrorSalesTransactionTotalAmountEntity =
                new ArrayList<>();
        SalesErrorSalesTransactionTotalAmount a8 = new SalesErrorSalesTransactionTotalAmount();
        a8.setTransactionId("56541321");
        TSalesErrorSalesTransactionTotalAmountEntity.add(a8);
        when(salesErrorSalesTransactionTotalAmountEntityMapper.selectByCondition(
                Mockito.any(SalesErrorSalesTransactionTotalAmountCondition.class)))
                        .thenReturn(TSalesErrorSalesTransactionTotalAmountEntity);

        when(salesErrorSalesOrderInformationEntityMapper
                .deleteByCondition(Mockito.any(SalesErrorSalesOrderInformationCondition.class)))
                        .thenReturn(1);
        when(salesErrorSalesTransactionDetailEntityMapper
                .deleteByCondition(Mockito.any(SalesErrorSalesTransactionDetailCondition.class)))
                        .thenReturn(1);
        when(salesErrorSalesTransactionDetailInfoEntityMapper.deleteByCondition(
                Mockito.any(SalesErrorSalesTransactionDetailInfoCondition.class))).thenReturn(1);
        when(salesErrorSalesTransactionDiscountEntityMapper
                .deleteByCondition(Mockito.any(SalesErrorSalesTransactionDiscountCondition.class)))
                        .thenReturn(1);
        when(salesErrorSalesTransactionHeaderEntityMapper
                .deleteByCondition(Mockito.any(SalesErrorSalesTransactionHeaderCondition.class)))
                        .thenReturn(1);
        when(salesErrorSalesTransactionTaxEntityMapper
                .deleteByCondition(Mockito.any(SalesErrorSalesTransactionTaxCondition.class)))
                        .thenReturn(1);
        when(salesErrorSalesTransactionTenderEntityMapper
                .deleteByCondition(Mockito.any(SalesErrorSalesTransactionTenderCondition.class)))
                        .thenReturn(1);
        when(salesErrorSalesTransactionTenderInfoEntityMapper.deleteByCondition(
                Mockito.any(SalesErrorSalesTransactionTenderInfoCondition.class))).thenReturn(1);
        when(salesErrorSalesTransactionTotalAmountEntityMapper.deleteByCondition(
                Mockito.any(SalesErrorSalesTransactionTotalAmountCondition.class))).thenReturn(1);

        when(errorEvacuationSalesOrderInformationEntityMapper.deleteByCondition(
                Mockito.any(ErrorEvacuationSalesOrderInformationCondition.class))).thenReturn(1);
        when(errorEvacuationSalesTransactionDetailEntityMapper.deleteByCondition(
                Mockito.any(ErrorEvacuationSalesTransactionDetailCondition.class))).thenReturn(1);
        when(errorEvacuationSalesTransactionDetailInfoEntityMapper.deleteByCondition(
                Mockito.any(ErrorEvacuationSalesTransactionDetailInfoCondition.class)))
                        .thenReturn(1);
        when(errorEvacuationSalesTransactionDiscountEntityMapper.deleteByCondition(
                Mockito.any(ErrorEvacuationSalesTransactionDiscountCondition.class))).thenReturn(1);
        when(errorEvacuationSalesTransactionHeaderEntityMapper.deleteByCondition(
                Mockito.any(ErrorEvacuationSalesTransactionHeaderCondition.class))).thenReturn(1);
        when(errorEvacuationSalesTransactionTaxEntityMapper
                .deleteByCondition(Mockito.any(ErrorEvacuationSalesTransactionTaxCondition.class)))
                        .thenReturn(1);
        when(errorEvacuationSalesTransactionTenderEntityMapper.deleteByCondition(
                Mockito.any(ErrorEvacuationSalesTransactionTenderCondition.class))).thenReturn(1);
        when(errorEvacuationSalesTransactionTenderInfoEntityMapper.deleteByCondition(
                Mockito.any(ErrorEvacuationSalesTransactionTenderInfoCondition.class)))
                        .thenReturn(1);
        when(errorEvacuationSalesTransactionTotalAmountEntityMapper.deleteByCondition(
                Mockito.any(ErrorEvacuationSalesTransactionTotalAmountCondition.class)))
                        .thenReturn(1);
        List<SalesTransactionErrorDetail> detailList = new ArrayList<>();
        SalesTransactionErrorDetail detail = new SalesTransactionErrorDetail();
        detailList.add(detail);
        when(salesTransactionErrorDetailEntityMapper
                .selectByCondition(Mockito.any(SalesTransactionErrorDetailCondition.class)))
                        .thenReturn(detailList);

    }

    @Test
    public void ErrorDataCorrectionServiceOne() throws ErrorEvacuationException{

        String salesTransactionErrorId = "salesTransactionErrorId001";
        String userId = "userId01";

        SalesErrorSalesOrderInformationKey salesErrorOrderInformationEntityKey =
                new SalesErrorSalesOrderInformationKey();
        salesErrorOrderInformationEntityKey
                .setTransactionId(transactionImportData.getSalesTransactionErrorId());

        SalesErrorSalesOrderInformation salesErrorOrderInfo = new SalesErrorSalesOrderInformation();
        salesErrorOrderInfo.setChannelCode("setChannelCode");
        salesErrorOrderInfo.setOrderBarcodeNumber("12");

        errorDataCorrectionService.correctErrorData(transactionImportData, salesTransactionErrorId,
                userId);

        ArgumentCaptor<SalesErrorSalesOrderInformationCondition> argument =
                ArgumentCaptor.forClass(SalesErrorSalesOrderInformationCondition.class);

        verify(salesErrorSalesOrderInformationEntityMapper, times(1))
                .selectByCondition(argument.capture());

        verify(alterationHistoryOrderInformationEntityMapper, times(2))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionHeaderEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionHeaderEntityMapper, times(2))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionDetailEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionDetailEntityMapper, times(4))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionDetailInfoEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionDetailInfoEntityMapper, times(3))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionDiscountEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionDiscountEntityMapper, times(4))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionTaxEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionTaxEntityMapper, times(5))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionTenderEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionTenderEntityMapper, times(2))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionTenderInfoEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionTenderInfoEntityMapper, times(2))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionTotalAmountEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionTotalAmountEntityMapper, times(2))
                .insertSelective(Mockito.any());

        verify(salesErrorSalesOrderInformationEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionHeaderEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionDetailEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionDetailInfoEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionTenderEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionTenderInfoEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionTaxEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionDiscountEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionTotalAmountEntityMapper, times(1))
                .deleteByCondition(Mockito.any());

        verify(errorEvacuationSalesOrderInformationEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionHeaderEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionDetailEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionDetailInfoEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionTenderEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionTenderInfoEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionTaxEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionDiscountEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionTotalAmountEntityMapper, times(1))
                .deleteByCondition(Mockito.any());

        assertThat(argument.getValue()
                .getOredCriteria()
                .stream()
                .findFirst()
                .orElse(null)
                .getAllCriteria()
                .stream()
                .findFirst()
                .orElse(null)
                .getValue(), equalTo("transactionid00000000000000000001"));

    }

    @Test
    public void ErrorDataCorrectionServiceTransactionEmpty() throws ErrorEvacuationException{
        transactionImportData.setTransactionList(new ArrayList<>());
        transactionImportData.setUpdateType(UpdateType.INSERT.getUpdateType());
        transactionImportData.setDataCorrectionEditingFlag(null);
        String salesTransactionErrorId = "salesTransactionErrorId001";
        String userId = "userId01";

        SalesErrorSalesOrderInformationKey salesErrorOrderInformationEntityKey =
                new SalesErrorSalesOrderInformationKey();
        salesErrorOrderInformationEntityKey
                .setTransactionId(transactionImportData.getSalesTransactionErrorId());

        SalesErrorSalesOrderInformation salesErrorOrderInfo = new SalesErrorSalesOrderInformation();
        salesErrorOrderInfo.setChannelCode("setChannelCode");
        salesErrorOrderInfo.setOrderBarcodeNumber("12");

        errorDataCorrectionService.correctErrorData(transactionImportData, salesTransactionErrorId,
                userId);

        ArgumentCaptor<SalesErrorSalesOrderInformationCondition> argument =
                ArgumentCaptor.forClass(SalesErrorSalesOrderInformationCondition.class);

        verify(salesErrorSalesOrderInformationEntityMapper, times(1))
                .selectByCondition(argument.capture());

        verify(alterationHistoryOrderInformationEntityMapper, times(2))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionHeaderEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionHeaderEntityMapper, times(1))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionDetailEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionDetailEntityMapper, times(1))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionDetailInfoEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionDetailInfoEntityMapper, times(1))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionDiscountEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionDiscountEntityMapper, times(1))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionTaxEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionTaxEntityMapper, times(1))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionTenderEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionTenderEntityMapper, times(1))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionTenderInfoEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionTenderInfoEntityMapper, times(1))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionTotalAmountEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionTotalAmountEntityMapper, times(1))
                .insertSelective(Mockito.any());

        verify(salesErrorSalesOrderInformationEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionHeaderEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionDetailEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionDetailInfoEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionTenderEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionTenderInfoEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionTaxEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionDiscountEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionTotalAmountEntityMapper, times(1))
                .deleteByCondition(Mockito.any());

        verify(errorEvacuationSalesOrderInformationEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionHeaderEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionDetailEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionDetailInfoEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionTenderEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionTenderInfoEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionTaxEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionDiscountEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionTotalAmountEntityMapper, times(1))
                .deleteByCondition(Mockito.any());

        assertThat(argument.getValue()
                .getOredCriteria()
                .stream()
                .findFirst()
                .orElse(null)
                .getAllCriteria()
                .stream()
                .findFirst()
                .orElse(null)
                .getValue(), equalTo("transactionid00000000000000000001"));

    }

    @Test
    public void ErrorDataCorrectionServiceEmptyInTransaction() throws ErrorEvacuationException{
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.setTransactionItemDetailList(new ArrayList<>());
            transaction.setSalesTransactionTenderList(new ArrayList<>());
        });

        String salesTransactionErrorId = "salesTransactionErrorId001";
        String userId = "userId01";

        SalesErrorSalesOrderInformationKey salesErrorOrderInformationEntityKey =
                new SalesErrorSalesOrderInformationKey();
        salesErrorOrderInformationEntityKey
                .setTransactionId(transactionImportData.getSalesTransactionErrorId());

        SalesErrorSalesOrderInformation salesErrorOrderInfo = new SalesErrorSalesOrderInformation();
        salesErrorOrderInfo.setChannelCode("setChannelCode");
        salesErrorOrderInfo.setOrderBarcodeNumber("12");

        errorDataCorrectionService.correctErrorData(transactionImportData, salesTransactionErrorId,
                userId);

        ArgumentCaptor<SalesErrorSalesOrderInformationCondition> argument =
                ArgumentCaptor.forClass(SalesErrorSalesOrderInformationCondition.class);

        verify(salesErrorSalesOrderInformationEntityMapper, times(1))
                .selectByCondition(argument.capture());

        verify(alterationHistoryOrderInformationEntityMapper, times(2))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionHeaderEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionHeaderEntityMapper, times(2))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionDetailEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionDetailEntityMapper, times(2))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionDetailInfoEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionDetailInfoEntityMapper, times(2))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionDiscountEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionDiscountEntityMapper, times(2))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionTaxEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionTaxEntityMapper, times(3))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionTenderEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionTenderEntityMapper, times(1))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionTenderInfoEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionTenderInfoEntityMapper, times(1))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionTotalAmountEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionTotalAmountEntityMapper, times(2))
                .insertSelective(Mockito.any());

        verify(salesErrorSalesOrderInformationEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionHeaderEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionDetailEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionDetailInfoEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionTenderEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionTenderInfoEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionTaxEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionDiscountEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionTotalAmountEntityMapper, times(1))
                .deleteByCondition(Mockito.any());

        verify(errorEvacuationSalesOrderInformationEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionHeaderEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionDetailEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionDetailInfoEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionTenderEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionTenderInfoEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionTaxEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionDiscountEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionTotalAmountEntityMapper, times(1))
                .deleteByCondition(Mockito.any());

        assertThat(argument.getValue()
                .getOredCriteria()
                .stream()
                .findFirst()
                .orElse(null)
                .getAllCriteria()
                .stream()
                .findFirst()
                .orElse(null)
                .getValue(), equalTo("transactionid00000000000000000001"));

    }

    @Test
    public void ErrorDataCorrectionServiceNonItemEmpty() throws ErrorEvacuationException{
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.setNonItemDetailList(null);
            transaction.getTransactionItemDetailList().forEach(itemDetail -> {
                itemDetail.setNonItemDetailListByItem(null);
            });
        });

        String salesTransactionErrorId = "salesTransactionErrorId001";
        String userId = "userId01";

        SalesErrorSalesOrderInformationKey salesErrorOrderInformationEntityKey =
                new SalesErrorSalesOrderInformationKey();
        salesErrorOrderInformationEntityKey
                .setTransactionId(transactionImportData.getSalesTransactionErrorId());

        SalesErrorSalesOrderInformation salesErrorOrderInfo = new SalesErrorSalesOrderInformation();
        salesErrorOrderInfo.setChannelCode("setChannelCode");
        salesErrorOrderInfo.setOrderBarcodeNumber("12");

        errorDataCorrectionService.correctErrorData(transactionImportData, salesTransactionErrorId,
                userId);

        ArgumentCaptor<SalesErrorSalesOrderInformationCondition> argument =
                ArgumentCaptor.forClass(SalesErrorSalesOrderInformationCondition.class);

        verify(salesErrorSalesOrderInformationEntityMapper, times(1))
                .selectByCondition(argument.capture());

        verify(alterationHistoryOrderInformationEntityMapper, times(2))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionHeaderEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionHeaderEntityMapper, times(2))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionDetailEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionDetailEntityMapper, times(2))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionDetailInfoEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionDetailInfoEntityMapper, times(1))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionDiscountEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionDiscountEntityMapper, times(2))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionTaxEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionTaxEntityMapper, times(3))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionTenderEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionTenderEntityMapper, times(2))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionTenderInfoEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionTenderInfoEntityMapper, times(2))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionTotalAmountEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionTotalAmountEntityMapper, times(2))
                .insertSelective(Mockito.any());

        verify(salesErrorSalesOrderInformationEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionHeaderEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionDetailEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionDetailInfoEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionTenderEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionTenderInfoEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionTaxEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionDiscountEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionTotalAmountEntityMapper, times(1))
                .deleteByCondition(Mockito.any());

        verify(errorEvacuationSalesOrderInformationEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionHeaderEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionDetailEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionDetailInfoEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionTenderEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionTenderInfoEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionTaxEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionDiscountEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionTotalAmountEntityMapper, times(1))
                .deleteByCondition(Mockito.any());

        assertThat(argument.getValue()
                .getOredCriteria()
                .stream()
                .findFirst()
                .orElse(null)
                .getAllCriteria()
                .stream()
                .findFirst()
                .orElse(null)
                .getValue(), equalTo("transactionid00000000000000000001"));

    }

    @Test
    public void ErrorDataCorrectionServiceTenderInfoEmpty() throws ErrorEvacuationException {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getSalesTransactionTenderList().forEach(tender -> {
                tender.setTenderInfoList(null);
            });
            transaction.setSalesTransactionTotalList(null);
        });

        String salesTransactionErrorId = "salesTransactionErrorId001";
        String userId = "userId01";

        SalesErrorSalesOrderInformationKey salesErrorOrderInformationEntityKey =
                new SalesErrorSalesOrderInformationKey();
        salesErrorOrderInformationEntityKey
                .setTransactionId(transactionImportData.getSalesTransactionErrorId());

        SalesErrorSalesOrderInformation salesErrorOrderInfo = new SalesErrorSalesOrderInformation();
        salesErrorOrderInfo.setChannelCode("setChannelCode");
        salesErrorOrderInfo.setOrderBarcodeNumber("12");

        errorDataCorrectionService.correctErrorData(transactionImportData, salesTransactionErrorId,
                userId);

        ArgumentCaptor<SalesErrorSalesOrderInformationCondition> argument =
                ArgumentCaptor.forClass(SalesErrorSalesOrderInformationCondition.class);

        verify(salesErrorSalesOrderInformationEntityMapper, times(1))
                .selectByCondition(argument.capture());

        verify(alterationHistoryOrderInformationEntityMapper, times(2))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionHeaderEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionHeaderEntityMapper, times(2))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionDetailEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionDetailEntityMapper, times(4))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionDetailInfoEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionDetailInfoEntityMapper, times(3))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionDiscountEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionDiscountEntityMapper, times(4))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionTaxEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionTaxEntityMapper, times(5))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionTenderEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionTenderEntityMapper, times(2))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionTenderInfoEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionTenderInfoEntityMapper, times(1))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionTotalAmountEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionTotalAmountEntityMapper, times(1))
                .insertSelective(Mockito.any());

        verify(salesErrorSalesOrderInformationEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionHeaderEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionDetailEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionDetailInfoEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionTenderEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionTenderInfoEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionTaxEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionDiscountEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionTotalAmountEntityMapper, times(1))
                .deleteByCondition(Mockito.any());

        verify(errorEvacuationSalesOrderInformationEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionHeaderEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionDetailEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionDetailInfoEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionTenderEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionTenderInfoEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionTaxEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionDiscountEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionTotalAmountEntityMapper, times(1))
                .deleteByCondition(Mockito.any());

        assertThat(argument.getValue()
                .getOredCriteria()
                .stream()
                .findFirst()
                .orElse(null)
                .getAllCriteria()
                .stream()
                .findFirst()
                .orElse(null)
                .getValue(), equalTo("transactionid00000000000000000001"));

    }

    @Test
    public void ErrorDataCorrectionServiceTaxEmpty() throws ErrorEvacuationException {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.setSalesTransactionTaxDetailList(null);
            transaction.getNonItemDetailList().forEach(non -> {
                non.setNonItemTaxDetailList(null);
            });
            transaction.getTransactionItemDetailList().forEach(itemDetail -> {
                itemDetail.setItemTaxDetailList(null);
                itemDetail.getNonItemDetailListByItem().forEach(non -> {
                    non.setNonItemTaxDetailList(null);
                });
            });
        });

        String salesTransactionErrorId = "salesTransactionErrorId001";
        String userId = "userId01";

        SalesErrorSalesOrderInformationKey salesErrorOrderInformationEntityKey =
                new SalesErrorSalesOrderInformationKey();
        salesErrorOrderInformationEntityKey
                .setTransactionId(transactionImportData.getSalesTransactionErrorId());

        SalesErrorSalesOrderInformation salesErrorOrderInfo = new SalesErrorSalesOrderInformation();
        salesErrorOrderInfo.setChannelCode("setChannelCode");
        salesErrorOrderInfo.setOrderBarcodeNumber("12");

        errorDataCorrectionService.correctErrorData(transactionImportData, salesTransactionErrorId,
                userId);

        ArgumentCaptor<SalesErrorSalesOrderInformationCondition> argument =
                ArgumentCaptor.forClass(SalesErrorSalesOrderInformationCondition.class);

        verify(salesErrorSalesOrderInformationEntityMapper, times(1))
                .selectByCondition(argument.capture());

        verify(alterationHistoryOrderInformationEntityMapper, times(2))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionHeaderEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionHeaderEntityMapper, times(2))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionDetailEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionDetailEntityMapper, times(4))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionDetailInfoEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionDetailInfoEntityMapper, times(3))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionDiscountEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionDiscountEntityMapper, times(4))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionTaxEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionTaxEntityMapper, times(1))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionTenderEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionTenderEntityMapper, times(2))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionTenderInfoEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionTenderInfoEntityMapper, times(2))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionTotalAmountEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionTotalAmountEntityMapper, times(2))
                .insertSelective(Mockito.any());

        verify(salesErrorSalesOrderInformationEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionHeaderEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionDetailEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionDetailInfoEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionTenderEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionTenderInfoEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionTaxEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionDiscountEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionTotalAmountEntityMapper, times(1))
                .deleteByCondition(Mockito.any());

        verify(errorEvacuationSalesOrderInformationEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionHeaderEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionDetailEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionDetailInfoEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionTenderEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionTenderInfoEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionTaxEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionDiscountEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionTotalAmountEntityMapper, times(1))
                .deleteByCondition(Mockito.any());

        assertThat(argument.getValue()
                .getOredCriteria()
                .stream()
                .findFirst()
                .orElse(null)
                .getAllCriteria()
                .stream()
                .findFirst()
                .orElse(null)
                .getValue(), equalTo("transactionid00000000000000000001"));

    }

    @Test
    public void ErrorDataCorrectionServiceDiscountEmpty() throws ErrorEvacuationException {
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getNonItemDetailList().forEach(non -> {
                non.setNonItemDiscountDetailList(null);
            });
            transaction.getTransactionItemDetailList().forEach(itemDetail -> {
                itemDetail.setItemDiscountList(null);
                itemDetail.getNonItemDetailListByItem().forEach(non -> {
                    non.setNonItemDiscountDetailList(null);
                });
            });
        });

        String salesTransactionErrorId = "salesTransactionErrorId001";
        String userId = "userId01";

        SalesErrorSalesOrderInformationKey salesErrorOrderInformationEntityKey =
                new SalesErrorSalesOrderInformationKey();
        salesErrorOrderInformationEntityKey
                .setTransactionId(transactionImportData.getSalesTransactionErrorId());

        SalesErrorSalesOrderInformation salesErrorOrderInfo = new SalesErrorSalesOrderInformation();
        salesErrorOrderInfo.setChannelCode("setChannelCode");
        salesErrorOrderInfo.setOrderBarcodeNumber("12");

        errorDataCorrectionService.correctErrorData(transactionImportData, salesTransactionErrorId,
                userId);

        ArgumentCaptor<SalesErrorSalesOrderInformationCondition> argument =
                ArgumentCaptor.forClass(SalesErrorSalesOrderInformationCondition.class);

        verify(salesErrorSalesOrderInformationEntityMapper, times(1))
                .selectByCondition(argument.capture());

        verify(alterationHistoryOrderInformationEntityMapper, times(2))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionHeaderEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionHeaderEntityMapper, times(2))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionDetailEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionDetailEntityMapper, times(4))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionDetailInfoEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionDetailInfoEntityMapper, times(3))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionDiscountEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionDiscountEntityMapper, times(1))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionTaxEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionTaxEntityMapper, times(5))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionTenderEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionTenderEntityMapper, times(2))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionTenderInfoEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionTenderInfoEntityMapper, times(2))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionTotalAmountEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionTotalAmountEntityMapper, times(2))
                .insertSelective(Mockito.any());

        verify(salesErrorSalesOrderInformationEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionHeaderEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionDetailEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionDetailInfoEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionTenderEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionTenderInfoEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionTaxEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionDiscountEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionTotalAmountEntityMapper, times(1))
                .deleteByCondition(Mockito.any());

        verify(errorEvacuationSalesOrderInformationEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionHeaderEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionDetailEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionDetailInfoEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionTenderEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionTenderInfoEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionTaxEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionDiscountEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionTotalAmountEntityMapper, times(1))
                .deleteByCondition(Mockito.any());

        assertThat(argument.getValue()
                .getOredCriteria()
                .stream()
                .findFirst()
                .orElse(null)
                .getAllCriteria()
                .stream()
                .findFirst()
                .orElse(null)
                .getValue(), equalTo("transactionid00000000000000000001"));

    }

    @Test
    public void ErrorDataCorrectionServicetTwo() throws ErrorEvacuationException {
        String salesTransactionErrorId = "salesTransactionErrorId001";
        String userId = "userId01";

        SalesErrorSalesOrderInformationKey salesErrorOrderInformationEntityKey =
                new SalesErrorSalesOrderInformationKey();
        salesErrorOrderInformationEntityKey
                .setTransactionId(transactionImportData.getSalesTransactionErrorId());
        List<SalesTransactionErrorDetail> detailList = new ArrayList<>();
        when(salesTransactionErrorDetailEntityMapper
                .selectByCondition(Mockito.any(SalesTransactionErrorDetailCondition.class)))
                        .thenReturn(detailList);
        SalesTransactionErrorDetail salesTransactionErrorDetailEntity =
                new SalesTransactionErrorDetail();

        TableCommonItem tableCommonItem = commonDataProcessor.getTableCommonItemForInsert(userId);
        modelMapper.map(tableCommonItem, salesTransactionErrorDetailEntity);
        modelMapper.map(transactionImportData, salesTransactionErrorDetailEntity);
        salesTransactionErrorDetailEntity.setSalesTransactionErrorId(salesTransactionErrorId);
        salesTransactionErrorDetailEntity.setErrorType(ErrorType.BUSINESS_ERROR.getErrorType());
        salesTransactionErrorDetailEntity.setErrorItemId1("salesTransactionErrorId");
        salesTransactionErrorDetailEntity
                .setErrorItemValue1(transactionImportData.getSalesTransactionErrorId());
        InvalidDataException expectedException =
                new InvalidDataException(salesTransactionErrorDetailEntity);

        try {
            errorDataCorrectionService.correctErrorData(transactionImportData,
                    salesTransactionErrorId, userId);
        } catch (InvalidDataException exception) {
            assertEquals(expectedException.toString(), exception.toString());
        }

        verify(alterationHistoryOrderInformationEntityMapper, times(0))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionHeaderEntityMapper, times(0))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionHeaderEntityMapper, times(0))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionDetailEntityMapper, times(0))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionDetailEntityMapper, times(0))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionDetailInfoEntityMapper, times(0))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionDetailInfoEntityMapper, times(0))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionDiscountEntityMapper, times(0))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionDiscountEntityMapper, times(0))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionTaxEntityMapper, times(0))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionTaxEntityMapper, times(0))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionTenderEntityMapper, times(0))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionTenderEntityMapper, times(0))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionTenderInfoEntityMapper, times(0))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionTenderInfoEntityMapper, times(0))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionTotalAmountEntityMapper, times(0))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionTotalAmountEntityMapper, times(0))
                .insertSelective(Mockito.any());

    }

    @Test
    public void ErrorDataCorrectionServiceThree() throws ErrorEvacuationException {
        String salesTransactionErrorId = "salesTransactionErrorId001";
        String userId = "userId01";

        SalesErrorSalesOrderInformationKey salesErrorOrderInformationEntityKey =
                new SalesErrorSalesOrderInformationKey();
        salesErrorOrderInformationEntityKey
                .setTransactionId(transactionImportData.getSalesTransactionErrorId());

        SalesErrorSalesOrderInformation salesErrorOrderInfo = new SalesErrorSalesOrderInformation();
        salesErrorOrderInfo.setChannelCode("setChannelCode");
        salesErrorOrderInfo.setOrderBarcodeNumber("12");
        salesErrorOrderInfo.setUpdateType("INSERT");

        errorDataCorrectionService.correctErrorData(transactionImportData, salesTransactionErrorId,
                userId);

        ArgumentCaptor<SalesErrorSalesOrderInformationCondition> argument =
                ArgumentCaptor.forClass(SalesErrorSalesOrderInformationCondition.class);

        verify(salesErrorSalesOrderInformationEntityMapper, times(1))
                .selectByCondition(argument.capture());

        verify(alterationHistoryOrderInformationEntityMapper, times(2))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionHeaderEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionHeaderEntityMapper, times(2))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionDetailEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionDetailEntityMapper, times(4))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionDetailInfoEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionDetailInfoEntityMapper, times(3))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionDiscountEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionDiscountEntityMapper, times(4))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionTaxEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionTaxEntityMapper, times(5))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionTenderEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionTenderEntityMapper, times(2))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionTenderInfoEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionTenderInfoEntityMapper, times(2))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionTotalAmountEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionTotalAmountEntityMapper, times(2))
                .insertSelective(Mockito.any());

        verify(salesErrorSalesOrderInformationEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionHeaderEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionDetailEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionDetailInfoEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionTenderEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionTenderInfoEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionTaxEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionDiscountEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionTotalAmountEntityMapper, times(1))
                .deleteByCondition(Mockito.any());

        verify(errorEvacuationSalesOrderInformationEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionHeaderEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionDetailEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionDetailInfoEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionTenderEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionTenderInfoEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionTaxEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionDiscountEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionTotalAmountEntityMapper, times(1))
                .deleteByCondition(Mockito.any());

        assertThat(argument.getValue()
                .getOredCriteria()
                .stream()
                .findFirst()
                .orElse(null)
                .getAllCriteria()
                .stream()
                .findFirst()
                .orElse(null)
                .getValue(), equalTo("transactionid00000000000000000001"));

    }

    @Test
    public void ErrorDataCorrectionServiceFour() throws ErrorEvacuationException {
        String salesTransactionErrorId = "salesTransactionErrorId001";
        String userId = "userId01";

        SalesErrorSalesOrderInformationKey salesErrorOrderInformationEntityKey =
                new SalesErrorSalesOrderInformationKey();
        salesErrorOrderInformationEntityKey
                .setTransactionId(transactionImportData.getSalesTransactionErrorId());

        SalesErrorSalesOrderInformation salesErrorOrderInfo = new SalesErrorSalesOrderInformation();
        salesErrorOrderInfo.setChannelCode("setChannelCode");
        salesErrorOrderInfo.setOrderBarcodeNumber("12");
        salesErrorOrderInfo.setUpdateType("UPDATE");

        errorDataCorrectionService.correctErrorData(transactionImportData, salesTransactionErrorId,
                userId);

        ArgumentCaptor<SalesErrorSalesOrderInformationCondition> argument =
                ArgumentCaptor.forClass(SalesErrorSalesOrderInformationCondition.class);

        verify(salesErrorSalesOrderInformationEntityMapper, times(1))
                .selectByCondition(argument.capture());

        verify(alterationHistoryOrderInformationEntityMapper, times(2))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionHeaderEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionHeaderEntityMapper, times(2))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionDetailEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionDetailEntityMapper, times(4))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionDetailInfoEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionDetailInfoEntityMapper, times(3))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionDiscountEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionDiscountEntityMapper, times(4))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionTaxEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionTaxEntityMapper, times(5))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionTenderEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionTenderEntityMapper, times(2))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionTenderInfoEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionTenderInfoEntityMapper, times(2))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionTotalAmountEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionTotalAmountEntityMapper, times(2))
                .insertSelective(Mockito.any());

        verify(salesErrorSalesOrderInformationEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionHeaderEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionDetailEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionDetailInfoEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionTenderEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionTenderInfoEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionTaxEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionDiscountEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionTotalAmountEntityMapper, times(1))
                .deleteByCondition(Mockito.any());

        verify(errorEvacuationSalesOrderInformationEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionHeaderEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionDetailEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionDetailInfoEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionTenderEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionTenderInfoEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionTaxEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionDiscountEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionTotalAmountEntityMapper, times(1))
                .deleteByCondition(Mockito.any());

        assertThat(argument.getValue()
                .getOredCriteria()
                .stream()
                .findFirst()
                .orElse(null)
                .getAllCriteria()
                .stream()
                .findFirst()
                .orElse(null)
                .getValue(), equalTo("transactionid00000000000000000001"));
    }

    @Test
    public void ErrorDataCorrectionServiceFive() throws ErrorEvacuationException {
        transactionImportData.setUpdateType("INSERT");
        String salesTransactionErrorId = "salesTransactionErrorId001";
        String userId = "userId01";

        SalesErrorSalesOrderInformationKey salesErrorOrderInformationEntityKey =
                new SalesErrorSalesOrderInformationKey();
        salesErrorOrderInformationEntityKey
                .setTransactionId(transactionImportData.getSalesTransactionErrorId());

        SalesErrorSalesOrderInformation salesErrorOrderInfo = new SalesErrorSalesOrderInformation();
        salesErrorOrderInfo.setChannelCode("setChannelCode");
        salesErrorOrderInfo.setOrderBarcodeNumber("12");

        errorDataCorrectionService.correctErrorData(transactionImportData, salesTransactionErrorId,
                userId);

        ArgumentCaptor<SalesErrorSalesOrderInformationCondition> argument =
                ArgumentCaptor.forClass(SalesErrorSalesOrderInformationCondition.class);

        verify(salesErrorSalesOrderInformationEntityMapper, times(1))
                .selectByCondition(argument.capture());

        verify(alterationHistoryOrderInformationEntityMapper, times(2))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionHeaderEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionHeaderEntityMapper, times(2))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionDetailEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionDetailEntityMapper, times(4))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionDetailInfoEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionDetailInfoEntityMapper, times(3))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionDiscountEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionDiscountEntityMapper, times(4))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionTaxEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionTaxEntityMapper, times(5))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionTenderEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionTenderEntityMapper, times(2))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionTenderInfoEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionTenderInfoEntityMapper, times(2))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionTotalAmountEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionTotalAmountEntityMapper, times(2))
                .insertSelective(Mockito.any());

        assertThat(argument.getValue()
                .getOredCriteria()
                .stream()
                .findFirst()
                .orElse(null)
                .getAllCriteria()
                .stream()
                .findFirst()
                .orElse(null)
                .getValue(), equalTo("transactionid00000000000000000001"));

    }

    @Test
    public void ErrorDataCorrectionServiceSix() throws ErrorEvacuationException {
        transactionImportData.setUpdateType("INSERT");
        transactionImportData.setDataCorrectionEditingFlag(false);
        String salesTransactionErrorId = "salesTransactionErrorId001";
        String userId = "userId01";

        SalesErrorSalesOrderInformationKey salesErrorOrderInformationEntityKey =
                new SalesErrorSalesOrderInformationKey();
        salesErrorOrderInformationEntityKey
                .setTransactionId(transactionImportData.getSalesTransactionErrorId());

        SalesErrorSalesOrderInformation salesErrorOrderInfo = new SalesErrorSalesOrderInformation();
        salesErrorOrderInfo.setChannelCode("setChannelCode");
        salesErrorOrderInfo.setOrderBarcodeNumber("12");

        errorDataCorrectionService.correctErrorData(transactionImportData, salesTransactionErrorId,
                userId);

        ArgumentCaptor<SalesErrorSalesOrderInformationCondition> argument =
                ArgumentCaptor.forClass(SalesErrorSalesOrderInformationCondition.class);

        verify(salesErrorSalesOrderInformationEntityMapper, times(1))
                .selectByCondition(argument.capture());

        verify(alterationHistoryOrderInformationEntityMapper, times(2))
                .insertSelective(Mockito.any());

        verify(alterationHistoryOrderInformationEntityMapper, times(2))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionHeaderEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionHeaderEntityMapper, times(2))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionDetailEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionDetailEntityMapper, times(4))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionDetailInfoEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionDetailInfoEntityMapper, times(3))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionDiscountEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionDiscountEntityMapper, times(4))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionTaxEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionTaxEntityMapper, times(5))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionTenderEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionTenderEntityMapper, times(2))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionTenderInfoEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionTenderInfoEntityMapper, times(2))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionTotalAmountEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionTotalAmountEntityMapper, times(2))
                .insertSelective(Mockito.any());

        verify(salesErrorSalesOrderInformationEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionHeaderEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionDetailEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionDetailInfoEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionTenderEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionTenderInfoEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionTaxEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionDiscountEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionTotalAmountEntityMapper, times(1))
                .deleteByCondition(Mockito.any());

        verify(errorEvacuationSalesOrderInformationEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionHeaderEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionDetailEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionDetailInfoEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionTenderEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionTenderInfoEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionTaxEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionDiscountEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionTotalAmountEntityMapper, times(1))
                .deleteByCondition(Mockito.any());

        assertThat(argument.getValue()
                .getOredCriteria()
                .stream()
                .findFirst()
                .orElse(null)
                .getAllCriteria()
                .stream()
                .findFirst()
                .orElse(null)
                .getValue(), equalTo("transactionid00000000000000000001"));

    }

    @Test
    public void ErrorDataCorrectionServiceSeven() throws ErrorEvacuationException {
        transactionImportData.getTransactionList()
                .get(0)
                .getNonItemDetailList()
                .get(0)
                .setNonItemInfo(null);
        String salesTransactionErrorId = "salesTransactionErrorId001";
        String userId = "userId01";

        SalesErrorSalesOrderInformationKey salesErrorOrderInformationEntityKey =
                new SalesErrorSalesOrderInformationKey();
        salesErrorOrderInformationEntityKey
                .setTransactionId(transactionImportData.getSalesTransactionErrorId());

        SalesErrorSalesOrderInformation salesErrorOrderInfo = new SalesErrorSalesOrderInformation();
        salesErrorOrderInfo.setChannelCode("setChannelCode");
        salesErrorOrderInfo.setOrderBarcodeNumber("12");

        errorDataCorrectionService.correctErrorData(transactionImportData, salesTransactionErrorId,
                userId);

        ArgumentCaptor<SalesErrorSalesOrderInformationCondition> argument =
                ArgumentCaptor.forClass(SalesErrorSalesOrderInformationCondition.class);

        verify(salesErrorSalesOrderInformationEntityMapper, times(1))
                .selectByCondition(argument.capture());

        verify(alterationHistoryOrderInformationEntityMapper, times(2))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionHeaderEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionHeaderEntityMapper, times(2))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionDetailEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionDetailEntityMapper, times(4))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionDetailInfoEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionDetailInfoEntityMapper, times(2))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionDiscountEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionDiscountEntityMapper, times(4))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionTaxEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionTaxEntityMapper, times(5))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionTenderEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionTenderEntityMapper, times(2))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionTenderInfoEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionTenderInfoEntityMapper, times(2))
                .insertSelective(Mockito.any());
        verify(salesErrorSalesTransactionTotalAmountEntityMapper, times(1))
                .selectByCondition(Mockito.any());
        verify(alterationHistorySalesTransactionTotalAmountEntityMapper, times(2))
                .insertSelective(Mockito.any());

        verify(salesErrorSalesOrderInformationEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionHeaderEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionDetailEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionDetailInfoEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionTenderEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionTenderInfoEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionTaxEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionDiscountEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(salesErrorSalesTransactionTotalAmountEntityMapper, times(1))
                .deleteByCondition(Mockito.any());

        verify(errorEvacuationSalesOrderInformationEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionHeaderEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionDetailEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionDetailInfoEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionTenderEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionTenderInfoEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionTaxEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionDiscountEntityMapper, times(1))
                .deleteByCondition(Mockito.any());
        verify(errorEvacuationSalesTransactionTotalAmountEntityMapper, times(1))
                .deleteByCondition(Mockito.any());

        assertThat(argument.getValue()
                .getOredCriteria()
                .stream()
                .findFirst()
                .orElse(null)
                .getAllCriteria()
                .stream()
                .findFirst()
                .orElse(null)
                .getValue(), equalTo("transactionid00000000000000000001"));

    }

    private void setTransactionImportData(TransactionImportData importData) {
        importData.setUpdateType("1");
        importData.setErrorCheckType(1);
        importData.setDataAlterationSalesLinkageType(1);
        importData.setDataAlterationBackboneLinkageType(1);
        importData.setSalesTransactionErrorId("");
        importData.setIntegratedOrderId("1");
        importData.setOrderBarcodeNumber("1");
        importData.setChannelCode("1");
        importData.setStoreCode("1");
        importData.setSystemBrandCode("1");
        importData.setSystemBusinessCode("1");
        importData.setSystemCountryCode("1");
        importData.setCustomerId("1");
        importData.setOrderConfirmationBusinessDate("1");
        importData.setOrderConfirmationDateTime(
                OffsetDateTime.of(2017, 12, 31, 8, 0, 0, 0, ZoneOffset.of("Z")));
        importData.setDataCorrectionEditingFlag(true);
        importData.setDataCorrectionUserId("1");

        List<Transaction> transactionList = new ArrayList<>();
        Transaction transaction = new Transaction();
        setTransaction(transaction);
        transactionList.add(transaction);
        importData.setTransactionList(transactionList);
        importData.setUpdateType("");
    }

    private void setTransaction(Transaction transaction) {

        transaction.setTransactionSerialNumber(1);
        transaction.setIntegratedOrderId("1");
        transaction.setOrderSubNumber(1);
        transaction.setSalesTransactionId("1");
        transaction.setTokenCode("1");
        transaction.setTransactionType("1");
        transaction.setSalesLinkageType(1);
        transaction.setReturnType(1);
        transaction.setSystemBrandCode("1");
        transaction.setSystemBusinessCode("1");
        transaction.setSystemCountryCode("1");
        transaction.setStoreCode("1");
        transaction.setChannelCode("1");
        transaction.setDataCreationBusinessDate("1");
        transaction.setDataCreationDateTime(
                OffsetDateTime.of(2017, 12, 31, 8, 0, 0, 0, ZoneOffset.of("Z")));
        transaction.setOrderStatusUpdateDate("1");
        transaction.setOrderStatusLastUpdateDateTime(
                OffsetDateTime.of(2017, 12, 31, 8, 0, 0, 0, ZoneOffset.of("Z")));
        transaction.setCashRegisterNo(1);
        transaction.setReceiptNo("1");
        transaction.setOrderNumberForStorePayment("1");
        transaction.setAdvanceReceivedStoreCode("1");
        transaction.setAdvanceReceivedStoreSystemBrandCode("1");
        transaction.setAdvanceReceivedStoreSystemBusinessCode("1");
        transaction.setAdvanceReceivedStoreSystemCountryCode("1");
        transaction.setOperatorCode("1");
        transaction.setOriginalTransactionId("1");
        transaction.setOriginalReceiptNo("1");
        transaction.setOriginalCashRegisterNo(1);
        transaction.setDeposit(getPrice());
        transaction.setChange(getPrice());
        transaction.setReceiptNoForCreditCardCancellation("1");
        transaction.setReceiptNoForCreditCard("1");
        transaction.setPaymentStoreCode("1");
        transaction.setPaymentStoreSystemBrandCode("1");
        transaction.setPaymentStoreSystemBusinessCode("1");
        transaction.setPaymentStoreSystemCountryCode("1");
        transaction.setReceiptIssuedFlag(true);
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
        transaction.setBookingStoreSystemBusinessCode("1");
        transaction.setBookingStoreSystemCountryCode("1");
        transaction.setShipmentStoreCode("1");
        transaction.setShipmentStoreSystemBrandCode("1");
        transaction.setShipmentStoreSystemBusinessCode("1");
        transaction.setShipmentStoreSystemCountryCode("1");
        transaction.setReceiptStoreCode("1");
        transaction.setReceiptStoreSystemBrandCode("1");
        transaction.setReceiptStoreSystemBusinessCode("1");
        transaction.setReceiptStoreSystemCountryCode("1");
        transaction.setCustomerId("1");
        transaction.setCorporateId("1");
        transaction.setSalesTransactionDiscountFlag(true);
        transaction.setSalesTransactionDiscountAmountRate(getPrice());
        transaction.setConsistencySalesFlag(true);
        transaction.setEmployeeSaleFlag(true);
        transaction.setEReceiptTargetFlag(true);

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

    void setTransactionItemDetail(TransactionItemDetail entity) {

        entity.setSystemBrandCode("1");
        entity.setDetailSubNumber(1);
        entity.setDetailListSalesTransactionType("1");
        entity.setL2ItemCode("1");
        entity.setViewL2ItemCode("1");
        entity.setL2ProductName("1");
        entity.setL3ItemCode("1");
        entity.setL3PosProductName("1");
        entity.setEpcCode("1");
        entity.setGDepartmentCode("1");
        entity.setDeptCode(1);
        entity.setQuantityCode("1");
        entity.setItemQty(1);
        entity.setItemCost(getPrice());
        entity.setInitialSellingPrice(getPrice());
        entity.setBItemSellingPrice(getPrice());
        entity.setItemNewPrice(getPrice());
        entity.setItemUnitPriceTaxExcluded(getPrice());
        entity.setItemUnitPriceTaxIncluded(getPrice());
        entity.setItemSalesAmtTaxExcluded(getPrice());
        entity.setItemSalesAmtTaxIncluded(getPrice());
        entity.setOrderStatusUpdateDate("1");
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
        entity.setCustomerId("1");
        entity.setBundlePurchaseQty(1);
        entity.setBundlePurchasePrice(getPrice());
        entity.setBundlePurchaseIndex(1);
        entity.setLimitedAmountPromotionCount(1);
        entity.setCalculationUnavailableType("1");
        entity.setItemMountDiscountType("1");
        entity.setItemDiscountAmount(getPrice());
        entity.setBundleSalesFlag(true);
        entity.setBundleSalesPrice(getPrice());
        entity.setBundleSalesDetailIndex(1);
        entity.setItemDetailNumber(1);
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
        entity.setNonMdDetailListSalesTransactionType("1");
        entity.setNonItemDetailSalesLinkageType(1);
        entity.setNonMdType("1");
        entity.setNonItemCode("1");
        entity.setServiceCode("1");
        entity.setPosNonItemName("1");
        entity.setQuantityCode("1");
        entity.setNonItemQty(1);
        entity.setNonItemUnitPriceTaxExcluded(getPrice());
        entity.setNonItemUnitPriceTaxIncluded(getPrice());
        entity.setNonItemSalesAmtTaxExcluded(getPrice());
        entity.setNonItemSalesAmtTaxIncluded(getPrice());
        entity.setNonItemNewPrice(getPrice());
        entity.setNonCalculationNonItemType("1");
        entity.setOrderStatusUpdateDate("1");
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
        entity.setDetailSubNumber(1);
        entity.setItemDetailSubNumber(1);
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
        entity.setDetailSubNumber(1);
        entity.setItemDetailSubNumber(1);
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

        entity.setNonItemPromotionType("1");
        entity.setNonItemStoreDiscountType("1");
        entity.setNonItemQuantityCode("1");
        entity.setNonItemDiscountQty(1);
        entity.setNonItemDiscountAmtTaxExcluded(getPrice());
        entity.setNonItemDiscountAmtTaxIncluded(getPrice());

    }

    private void setNonItemTaxDetailOne(NonItemTaxDetail entity) {
        entity.setNonItemTaxDetailSubNumber(1);
        entity.setNonItemTaxDiscountSubNumber(1);
        entity.setNonItemTaxType("1");
        entity.setNonItemTaxName("1");
        entity.setNonItemTaxAmountSign("1");
        entity.setNonItemTaxAmt(getPrice());
        entity.setNonItemTaxRate(new BigDecimal(1));

    }

    private void setItemDiscount(ItemDiscount entity) {

        entity.setItemDiscountDetailSubNumber(1);
        entity.setItemDiscountSubNumber(1);
        entity.setItemPromotionType("1");
        entity.setItemPromotionNumber("1");
        entity.setItemStoreDiscountType("1");
        entity.setItemQuantityCode("1");
        entity.setItemDiscountQty(1);
        entity.setItemDiscountAmtTaxExcluded(getPrice());
        entity.setItemDiscountAmtTaxIncluded(getPrice());

    }

    private void setItemTaxDetail(ItemTaxDetail entity) {
        entity.setItemTaxDetailSubNumber(1);
        entity.setItemTaxSubNumber(1);
        entity.setItemTaxType("1");
        entity.setItemTaxName("1");
        entity.setItemTaxAmountSign("1");
        entity.setItemTaxAmt(getPrice());
        entity.setItemTaxRate(new BigDecimal(1));

    }

    private void setNonItemDetailTwo(NonItemDetail entity) {

        entity.setNonItemDetailNumber(1);
        entity.setNonMdDetailListSalesTransactionType("1");
        entity.setNonItemDetailSalesLinkageType(1);
        entity.setNonMdType("1");
        entity.setNonItemCode("1");
        entity.setServiceCode("1");
        entity.setPosNonItemName("1");
        entity.setQuantityCode("1");
        entity.setNonItemQty(1);
        entity.setNonItemUnitPriceTaxExcluded(getPrice());
        entity.setNonItemUnitPriceTaxIncluded(getPrice());
        entity.setNonItemSalesAmtTaxExcluded(getPrice());
        entity.setNonItemSalesAmtTaxIncluded(getPrice());
        entity.setNonItemNewPrice(getPrice());
        entity.setNonCalculationNonItemType("1");
        entity.setOrderStatusUpdateDate("1");
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
        entity.setDetailSubNumber(1);
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

        entity.setDetailSubNumber(1);
        entity.setItemDetailSubNumber(1);
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

    private void setNonItemDiscountDetailTwo(NonItemDiscountDetail entity) {
        entity.setNonItemPromotionType("1");
        entity.setNonItemStoreDiscountType("1");
        entity.setNonItemQuantityCode("1");
        entity.setNonItemDiscountQty(1);
        entity.setNonItemDiscountAmtTaxExcluded(getPrice());
        entity.setNonItemDiscountAmtTaxIncluded(getPrice());

    }

    private void setNonItemTaxDetailTwo(NonItemTaxDetail entity) {
        entity.setNonItemTaxDetailSubNumber(1);
        entity.setNonItemTaxDiscountSubNumber(1);
        entity.setNonItemTaxType("1");
        entity.setNonItemTaxName("1");
        entity.setNonItemTaxAmountSign("1");
        entity.setNonItemTaxAmt(getPrice());
        entity.setNonItemTaxRate(new BigDecimal(1));

    }

    private void setSalesTransactionTender(SalesTransactionTender entity) {

        entity.setTenderSubNumber(1);
        entity.setTenderGroupId("1");
        entity.setTenderId("1");
        entity.setPaymentSign("1");
        entity.setTaxIncludedPaymentAmount(getPrice());

        List<TenderInfo> tenderInfoList = new ArrayList<>();
        TenderInfo tenderInfo = new TenderInfo();
        setTenderInfo(tenderInfo);
        tenderInfoList.add(tenderInfo);
        entity.setTenderInfoList(tenderInfo);

    }

    private void setTenderInfo(TenderInfo entity) {

        entity.setDiscountAmount(getPrice());
        entity.setDiscountRate(new BigDecimal(1));
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
        entity.setTaxAmountSign("1");
        entity.setTaxAmount(getPrice());

    }

    private void setSalesTransactionTotal(SalesTransactionTotal entity) {

        entity.setTotalAmountSubNumber(1);
        entity.setTotalType("1");
        entity.setTotalAmountTaxExcluded(getPrice());
        entity.setTotalAmountTaxIncluded(getPrice());
        entity.setConsumptionTaxRate(new BigDecimal(1));
        entity.setSalesTransactionInformation1("1");
        entity.setSalesTransactionInformation2("1");
        entity.setSalesTransactionInformation3("1");

    }

    private Price getPrice() {
        Price price = new Price();
        price.setCurrencyCode(Currency.getInstance(Locale.JAPAN));
        price.setValue(new BigDecimal(1));
        return price;
    }

}
