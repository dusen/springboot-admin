package com.fastretailing.dcp.sales.importtransaction.component;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;
import com.fastretailing.dcp.sales.common.type.ErrorType;
import com.fastretailing.dcp.sales.importtransaction.converter.CommonDataProcessor;
import com.fastretailing.dcp.sales.importtransaction.converter.CoreTableDataConverter;
import com.fastretailing.dcp.sales.importtransaction.converter.ErrorDetailConverter;
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
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesOrderInformation;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesOrderInformation;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionDetail;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionDetailInfo;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionDiscount;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionErrorDetail;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionHeader;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionTax;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionTenderInfo;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionTenderTable;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionTotalAmount;
import com.fastretailing.dcp.sales.importtransaction.exception.ErrorEvacuationException;
import com.fastretailing.dcp.sales.importtransaction.exception.UniqueConstraintsException;
import com.fastretailing.dcp.sales.importtransaction.repository.SalesErrorSalesOrderInformationMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.SalesOrderInformationMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.SalesTransactionDetailInfoMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.SalesTransactionDetailMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.SalesTransactionDiscountMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.SalesTransactionErrorDetailMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.SalesTransactionHeaderMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.SalesTransactionTaxMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.SalesTransactionTenderInfoMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.SalesTransactionTenderTableMapper;
import com.fastretailing.dcp.sales.importtransaction.repository.SalesTransactionTotalAmountMapper;
import com.fastretailing.dcp.storecommon.dto.Price;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InsertSalesCoreServiceTest {

    @SpyBean
    private InsertSalesCoreService insertSalesCoreTableService;

    @MockBean
    private ErrorDataCorrectionService errorDataCorrectionService;

    /** Model tools parts. */
    @MockBean
    private ModelMapper modelMapper;

    /** Common data processor parts. */
    @MockBean
    private CommonDataProcessor commonDataProcessor;

    /**
     * The converter for sales core table.
     */
    @MockBean
    private CoreTableDataConverter coreTableDataConverter;

    /**
     * The service for insert data to error evacuation table.
     */
    @SpyBean
    private InsertErrorEvacuationService insertErrorEvacuationTableService;

    /**
     * Component for operating DB operations on the order information table.
     */
    @MockBean
    private SalesOrderInformationMapper salesOrderInformationEntityMapper;

    /**
     * Component for operating DB operations on the sales transaction header table.
     */
    @MockBean
    private SalesTransactionHeaderMapper salesTransactionHeaderEntityMapper;

    /**
     * Component for operating DB operations on the sales transaction detail table.
     */
    @MockBean
    private SalesTransactionDetailMapper salesTransactionDetailEntityMapper;

    /**
     * Component for operating DB operations on the sales transaction detail info table.
     */
    @MockBean
    private SalesTransactionDetailInfoMapper salesTransactionDetailInfoEntityMapper;

    /**
     * Component for operating DB operations on the sales transaction discount table.
     */
    @MockBean
    private SalesTransactionDiscountMapper salesTransactionDiscountEntityMapper;

    /**
     * Component for operating DB operations on the sales transaction tax table.
     */
    @MockBean
    private SalesTransactionTaxMapper salesTransactionTaxEntityMapper;

    /**
     * Component for operating DB operations on the sales transaction tender table.
     */
    @MockBean
    private SalesTransactionTenderTableMapper salesTransactionTenderEntityMapper;

    /**
     * Component for operating DB operations on the sales transaction tender info table.
     */
    @MockBean
    private SalesTransactionTenderInfoMapper salesTransactionTenderInfoEntityMapper;

    /**
     * Component for operating DB operations on the sales transaction total amount table.
     */
    @MockBean
    private SalesTransactionTotalAmountMapper salesTransactionTotalAmountEntityMapper;

    /**
     * Component for operating DB operations on the sales error order information table.
     */
    @MockBean
    private SalesErrorSalesOrderInformationMapper salesErrorSalesOrderInformationEntityMapper;

    /**
     * Component for operating DB operations on the sales transaction error detail table.
     */
    @MockBean
    private SalesTransactionErrorDetailMapper salesTransactionErrorDetailEntityMapper;

    @MockBean
    private ErrorDetailConverter errorDetailConverter;

    /**
     * Set up for insert service test.
     * 
     * @throws Exception exception.
     */
    @Before
    public void setUp() throws Exception {

        List<SalesTransactionErrorDetail> salesTransactionErrorDetailEntityList = new ArrayList<>();
        SalesTransactionErrorDetail errorDetail = new SalesTransactionErrorDetail();
        salesTransactionErrorDetailEntityList.add(errorDetail);

        when(salesTransactionErrorDetailEntityMapper.selectByCondition(Mockito.any()))
                .thenReturn(salesTransactionErrorDetailEntityList);

        when(salesOrderInformationEntityMapper.updateByPrimaryKeySelective(Mockito.any()))
                .thenReturn(1);

        doNothing().when(insertErrorEvacuationTableService)
                .insertErrorEvacuationTable(Mockito.any(), Mockito.any(), Mockito.any());

        when(salesOrderInformationEntityMapper.insertSelective(Mockito.any())).thenReturn(1);

        when(coreTableDataConverter.convertTSalesTransactionHeaderEntityForInsert(Mockito.any(),
                Mockito.any(), Mockito.anyInt())).thenReturn(new SalesTransactionHeader());

        when(salesTransactionHeaderEntityMapper.insertSelective(Mockito.any())).thenReturn(1);

        when(coreTableDataConverter.convertTSalesTransactionDetailEntityForInsert(Mockito.any(),
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt()))
                        .thenReturn(new SalesTransactionDetail());

        when(salesTransactionDetailEntityMapper.insertSelective(Mockito.any())).thenReturn(1);

        when(coreTableDataConverter.convertTSalesTransactionDetailEntityForInsertOutside(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.any(),
                Mockito.anyInt(), Mockito.anyInt())).thenReturn(new SalesTransactionDetail());

        when(salesTransactionDetailEntityMapper.insertSelective(Mockito.any())).thenReturn(1);

        when(coreTableDataConverter.convertTSalesTransactionDetailInfoEntityForInsert(Mockito.any(),
                Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(), Mockito.anyInt()))
                        .thenReturn(new SalesTransactionDetailInfo());

        when(salesTransactionDetailInfoEntityMapper.insertSelective(Mockito.any())).thenReturn(1);

        when(coreTableDataConverter.convertTSalesTransactionDiscountEntityForInsertNon(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(),
                Mockito.anyInt())).thenReturn(new SalesTransactionDiscount());

        when(salesTransactionDiscountEntityMapper.insertSelective(Mockito.any())).thenReturn(1);

        when(coreTableDataConverter.convertTSalesTransactionTaxEntityForInsertNon(Mockito.any(),
                Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(), Mockito.anyInt()))
                        .thenReturn(new SalesTransactionTax());
        when(salesTransactionTaxEntityMapper.insertSelective(Mockito.any())).thenReturn(1);

        when(coreTableDataConverter.convertTSalesTransactionDiscountEntityForInsert(Mockito.any(),
                Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(), Mockito.anyInt()))
                        .thenReturn(new SalesTransactionDiscount());

        when(salesTransactionDiscountEntityMapper.insertSelective(Mockito.any())).thenReturn(1);

        when(coreTableDataConverter.convertTSalesTransactionTaxEntityForInsert(Mockito.any(),
                Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(), Mockito.anyInt()))
                        .thenReturn(new SalesTransactionTax());

        when(salesTransactionTaxEntityMapper.insertSelective(Mockito.any())).thenReturn(1);

        when(coreTableDataConverter.convertTSalesTransactionDetailEntityForInsertOutside(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.any(),
                Mockito.anyInt(), Mockito.anyInt())).thenReturn(new SalesTransactionDetail());

        when(salesTransactionDetailEntityMapper.insertSelective(Mockito.any())).thenReturn(1);

        when(coreTableDataConverter.convertTSalesTransactionDetailInfoEntityForInsert(Mockito.any(),
                Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(), Mockito.anyInt()))
                        .thenReturn(new SalesTransactionDetailInfo());

        when(salesTransactionDetailInfoEntityMapper.insertSelective(Mockito.any())).thenReturn(1);

        when(coreTableDataConverter.convertTSalesTransactionDiscountEntityForInsertNon(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(),
                Mockito.anyInt())).thenReturn(new SalesTransactionDiscount());

        when(salesTransactionDiscountEntityMapper.insertSelective(Mockito.any())).thenReturn(1);

        when(coreTableDataConverter.convertTSalesTransactionTaxEntityForInsertNon(Mockito.any(),
                Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(), Mockito.anyInt()))
                        .thenReturn(new SalesTransactionTax());
        when(salesTransactionTaxEntityMapper.insertSelective(Mockito.any())).thenReturn(1);

        when(coreTableDataConverter.convertTSalesTransactionTenderEntityForInsert(Mockito.any(),
                Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt()))
                        .thenReturn(new SalesTransactionTenderTable());

        when(salesTransactionTenderEntityMapper.insertSelective(Mockito.any())).thenReturn(1);

        when(coreTableDataConverter.convertTSalesTransactionTenderInfoEntityForInsert(Mockito.any(),
                Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.any(), Mockito.any(),
                anyInt())).thenReturn(new SalesTransactionTenderInfo());

        when(salesTransactionTenderInfoEntityMapper.insertSelective(Mockito.any())).thenReturn(1);

        when(coreTableDataConverter.convertTSalesTransactionTaxEntityForInsertTransaction(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(),
                Mockito.anyInt())).thenReturn(new SalesTransactionTax());

        when(salesTransactionTaxEntityMapper.insertSelective(Mockito.any())).thenReturn(1);

        when(coreTableDataConverter.convertTSalesTransactionTotalAmountEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt()))
                        .thenReturn(new SalesTransactionTotalAmount());

        when(salesTransactionTotalAmountEntityMapper.insertSelective(Mockito.any())).thenReturn(1);

    }

    @Test
    public void insertSalesCoreTableTestOne() throws ErrorEvacuationException  {

        TransactionImportData transactionImportData = new TransactionImportData();
        setTransactionImportData(transactionImportData);
        transactionImportData.setSalesTransactionErrorId("transactionid00000000000000000001");
        transactionImportData.setUpdateType("CORRECTION");
        String salesTransactionErrorId = "salesTransactionErrorId001";
        String userId = "userId01";

        insertSalesCoreTableService.insertSalesCoreTable(transactionImportData,
                salesTransactionErrorId, userId);

        verify(salesOrderInformationEntityMapper, times(1)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(1)).convertTSalesTransactionHeaderEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.anyInt());

        verify(salesTransactionHeaderEntityMapper, times(1)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(1)).convertTSalesTransactionDetailEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.anyInt(),
                Mockito.anyInt());

        verify(salesTransactionDetailEntityMapper, times(3)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(2))
                .convertTSalesTransactionDetailEntityForInsertOutside(Mockito.any(), Mockito.any(),
                        Mockito.anyInt(), Mockito.any(), Mockito.any(), Mockito.anyInt(),
                        Mockito.anyInt());

        verify(salesTransactionDetailEntityMapper, times(3)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(2)).convertTSalesTransactionDetailInfoEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(),
                Mockito.anyInt());

        verify(salesTransactionDetailInfoEntityMapper, times(2)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(2)).convertTSalesTransactionDiscountEntityForInsertNon(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(),
                Mockito.anyInt());

        verify(salesTransactionDiscountEntityMapper, times(3)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(2)).convertTSalesTransactionTaxEntityForInsertNon(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(),
                Mockito.anyInt());

        verify(salesTransactionTaxEntityMapper, times(4)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(1)).convertTSalesTransactionDiscountEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(),
                Mockito.anyInt());

        verify(salesTransactionDiscountEntityMapper, times(3)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(1)).convertTSalesTransactionTaxEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(),
                Mockito.anyInt());

        verify(salesTransactionTaxEntityMapper, times(4)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(2))
                .convertTSalesTransactionDetailEntityForInsertOutside(Mockito.any(), Mockito.any(),
                        Mockito.anyInt(), Mockito.any(), Mockito.any(), Mockito.anyInt(),
                        Mockito.anyInt());

        verify(salesTransactionDetailEntityMapper, times(3)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(2)).convertTSalesTransactionDetailInfoEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(),
                Mockito.anyInt());

        verify(salesTransactionDetailInfoEntityMapper, times(2)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(2)).convertTSalesTransactionDiscountEntityForInsertNon(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(),
                Mockito.anyInt());

        verify(salesTransactionDiscountEntityMapper, times(3)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(2)).convertTSalesTransactionTaxEntityForInsertNon(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(),
                Mockito.anyInt());

        verify(salesTransactionTaxEntityMapper, times(4)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(1)).convertTSalesTransactionTenderEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt());

        verify(salesTransactionTenderEntityMapper, times(1)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(1)).convertTSalesTransactionTenderInfoEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.any(),
                Mockito.any(), anyInt());

        verify(salesTransactionTenderInfoEntityMapper, times(1)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(1))
                .convertTSalesTransactionTaxEntityForInsertTransaction(Mockito.any(), Mockito.any(),
                        Mockito.anyInt(), Mockito.any(), Mockito.anyInt(), Mockito.anyInt());

        verify(salesTransactionDiscountEntityMapper, times(3)).insertSelective(Mockito.any());

        verify(salesTransactionTaxEntityMapper, times(4)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(1)).convertTSalesTransactionTotalAmountEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt());

        verify(salesTransactionTotalAmountEntityMapper, times(1)).insertSelective(Mockito.any());

    }

    @Test
    public void insertSalesCoreTableTestNullList() throws ErrorEvacuationException {

        TransactionImportData transactionImportData = new TransactionImportData();
        setTransactionImportData(transactionImportData);
        transactionImportData.setSalesTransactionErrorId("transactionid00000000000000000001");
        transactionImportData.setUpdateType("CORRECTION");
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getTransactionItemDetailList().forEach(itemDetail -> {
                itemDetail.setNonItemDetailListByItem(null);
                itemDetail.setItemDiscountList(null);
                itemDetail.setItemTaxDetailList(null);
            });
            transaction.getNonItemDetailList().forEach(nonItemDetail -> {
                nonItemDetail.setNonItemDiscountDetailList(null);
                nonItemDetail.setNonItemTaxDetailList(null);
                nonItemDetail.setNonItemInfo(null);
            });
            transaction.getSalesTransactionTenderList().forEach(tender -> {
                tender.setTenderInfoList(null);
            });

        });
        String salesTransactionErrorId = "salesTransactionErrorId001";
        String userId = "userId01";

        insertSalesCoreTableService.insertSalesCoreTable(transactionImportData,
                salesTransactionErrorId, userId);

        verify(salesOrderInformationEntityMapper, times(1)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(1)).convertTSalesTransactionHeaderEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.anyInt());

        verify(salesTransactionHeaderEntityMapper, times(1)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(1)).convertTSalesTransactionDetailEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.anyInt(),
                Mockito.anyInt());

        verify(salesTransactionDetailEntityMapper, times(2)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(1))
                .convertTSalesTransactionDetailEntityForInsertOutside(Mockito.any(), Mockito.any(),
                        Mockito.anyInt(), Mockito.any(), Mockito.any(), Mockito.anyInt(),
                        Mockito.anyInt());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionDetailInfoEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(),
                Mockito.anyInt());

        verify(salesTransactionDetailInfoEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionDiscountEntityForInsertNon(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(),
                Mockito.anyInt());

        verify(salesTransactionDiscountEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionTaxEntityForInsertNon(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(),
                Mockito.anyInt());

        verify(salesTransactionTaxEntityMapper, times(1)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionDiscountEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(),
                Mockito.anyInt());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionTaxEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(),
                Mockito.anyInt());

        verify(coreTableDataConverter, times(1))
                .convertTSalesTransactionDetailEntityForInsertOutside(Mockito.any(), Mockito.any(),
                        Mockito.anyInt(), Mockito.any(), Mockito.any(), Mockito.anyInt(),
                        Mockito.anyInt());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionDetailInfoEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(),
                Mockito.anyInt());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionDiscountEntityForInsertNon(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(),
                Mockito.anyInt());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionTaxEntityForInsertNon(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(),
                Mockito.anyInt());

        verify(coreTableDataConverter, times(1)).convertTSalesTransactionTenderEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt());

        verify(salesTransactionTenderEntityMapper, times(1)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionTenderInfoEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.any(),
                Mockito.any(), anyInt());

        verify(salesTransactionTenderInfoEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(1))
                .convertTSalesTransactionTaxEntityForInsertTransaction(Mockito.any(), Mockito.any(),
                        Mockito.anyInt(), Mockito.any(), Mockito.anyInt(), Mockito.anyInt());

        verify(coreTableDataConverter, times(1)).convertTSalesTransactionTotalAmountEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt());

        verify(salesTransactionTotalAmountEntityMapper, times(1)).insertSelective(Mockito.any());

    }

    @Test
    public void insertSalesCoreTableTestEmptyListInTransaction() throws ErrorEvacuationException  {

        TransactionImportData transactionImportData = new TransactionImportData();
        setTransactionImportData(transactionImportData);
        transactionImportData.setSalesTransactionErrorId("transactionid00000000000000000001");
        transactionImportData.setUpdateType("CORRECTION");
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.setTransactionItemDetailList(null);
            transaction.setNonItemDetailList(null);
            transaction.setSalesTransactionTaxDetailList(null);
            transaction.setSalesTransactionTenderList(null);
            transaction.setSalesTransactionTotalList(null);
        });
        String salesTransactionErrorId = "salesTransactionErrorId001";
        String userId = "userId01";

        insertSalesCoreTableService.insertSalesCoreTable(transactionImportData,
                salesTransactionErrorId, userId);

        verify(salesOrderInformationEntityMapper, times(1)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(1)).convertTSalesTransactionHeaderEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.anyInt());

        verify(salesTransactionHeaderEntityMapper, times(1)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionDetailEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.anyInt(),
                Mockito.anyInt());

        verify(salesTransactionDetailEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0))
                .convertTSalesTransactionDetailEntityForInsertOutside(Mockito.any(), Mockito.any(),
                        Mockito.anyInt(), Mockito.any(), Mockito.any(), Mockito.anyInt(),
                        Mockito.anyInt());

        verify(salesTransactionDetailEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionDetailInfoEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(),
                Mockito.anyInt());

        verify(salesTransactionDetailInfoEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionDiscountEntityForInsertNon(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(),
                Mockito.anyInt());

        verify(salesTransactionDiscountEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionTaxEntityForInsertNon(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(),
                Mockito.anyInt());

        verify(salesTransactionTaxEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionDiscountEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(),
                Mockito.anyInt());

        verify(salesTransactionDiscountEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionTaxEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(),
                Mockito.anyInt());

        verify(salesTransactionTaxEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0))
                .convertTSalesTransactionDetailEntityForInsertOutside(Mockito.any(), Mockito.any(),
                        Mockito.anyInt(), Mockito.any(), Mockito.any(), Mockito.anyInt(),
                        Mockito.anyInt());

        verify(salesTransactionDetailEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionDetailInfoEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(),
                Mockito.anyInt());

        verify(salesTransactionDetailInfoEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionDiscountEntityForInsertNon(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(),
                Mockito.anyInt());

        verify(salesTransactionDiscountEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionTaxEntityForInsertNon(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(),
                Mockito.anyInt());

        verify(salesTransactionTaxEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionTenderEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt());

        verify(salesTransactionTenderEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionTenderInfoEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.any(),
                Mockito.any(), anyInt());

        verify(salesTransactionTenderInfoEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0))
                .convertTSalesTransactionTaxEntityForInsertTransaction(Mockito.any(), Mockito.any(),
                        Mockito.anyInt(), Mockito.any(), Mockito.anyInt(), Mockito.anyInt());

        verify(salesTransactionDiscountEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(salesTransactionTaxEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionTotalAmountEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt());

        verify(salesTransactionTotalAmountEntityMapper, times(0)).insertSelective(Mockito.any());

    }

    @Test
    public void insertSalesCoreTableTestEmptyListInNonItemDetail() throws ErrorEvacuationException {

        TransactionImportData transactionImportData = new TransactionImportData();
        setTransactionImportData(transactionImportData);
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getTransactionItemDetailList().forEach(itemDetail -> {
                itemDetail.getNonItemDetailListByItem().forEach(non -> {
                    non.setNonItemDiscountDetailList(new ArrayList<>());
                    non.setNonItemTaxDetailList(new ArrayList<>());
                    non.setNonItemInfo(null);
                });
            });
        });
        transactionImportData.setSalesTransactionErrorId("transactionid00000000000000000001");
        transactionImportData.setUpdateType("CORRECTION");
        String salesTransactionErrorId = "salesTransactionErrorId001";
        String userId = "userId01";

        insertSalesCoreTableService.insertSalesCoreTable(transactionImportData,
                salesTransactionErrorId, userId);

        verify(salesOrderInformationEntityMapper, times(1)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(1)).convertTSalesTransactionHeaderEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.anyInt());

        verify(salesTransactionHeaderEntityMapper, times(1)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(1)).convertTSalesTransactionDetailEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.anyInt(),
                Mockito.anyInt());

        verify(salesTransactionDetailEntityMapper, times(3)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(2))
                .convertTSalesTransactionDetailEntityForInsertOutside(Mockito.any(), Mockito.any(),
                        Mockito.anyInt(), Mockito.any(), Mockito.any(), Mockito.anyInt(),
                        Mockito.anyInt());

        verify(coreTableDataConverter, times(1)).convertTSalesTransactionDetailInfoEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(),
                Mockito.anyInt());

        verify(salesTransactionDetailInfoEntityMapper, times(1)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(1)).convertTSalesTransactionDiscountEntityForInsertNon(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(),
                Mockito.anyInt());

        verify(salesTransactionDiscountEntityMapper, times(2)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(1)).convertTSalesTransactionTaxEntityForInsertNon(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(),
                Mockito.anyInt());

        verify(salesTransactionTaxEntityMapper, times(3)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(1)).convertTSalesTransactionDiscountEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(),
                Mockito.anyInt());

        verify(coreTableDataConverter, times(1)).convertTSalesTransactionTaxEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(),
                Mockito.anyInt());

        verify(coreTableDataConverter, times(2))
                .convertTSalesTransactionDetailEntityForInsertOutside(Mockito.any(), Mockito.any(),
                        Mockito.anyInt(), Mockito.any(), Mockito.any(), Mockito.anyInt(),
                        Mockito.anyInt());

        verify(coreTableDataConverter, times(1)).convertTSalesTransactionDetailInfoEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(),
                Mockito.anyInt());

        verify(coreTableDataConverter, times(1)).convertTSalesTransactionDiscountEntityForInsertNon(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(),
                Mockito.anyInt());

        verify(coreTableDataConverter, times(1)).convertTSalesTransactionTaxEntityForInsertNon(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(),
                Mockito.anyInt());

        verify(coreTableDataConverter, times(1)).convertTSalesTransactionTenderEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt());

        verify(salesTransactionTenderEntityMapper, times(1)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(1)).convertTSalesTransactionTenderInfoEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.any(),
                Mockito.any(), anyInt());

        verify(salesTransactionTenderInfoEntityMapper, times(1)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(1)).convertTSalesTransactionTotalAmountEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt());

        verify(salesTransactionTotalAmountEntityMapper, times(1)).insertSelective(Mockito.any());

    }

    @Test
    public void insertSalesCoreTableTestInsertType() throws ErrorEvacuationException {

        TransactionImportData transactionImportData = new TransactionImportData();
        setTransactionImportData(transactionImportData);
        transactionImportData.setSalesTransactionErrorId("transactionid00000000000000000001");
        transactionImportData.setUpdateType("INSERT");
        transactionImportData.setTransactionList(null);
        String salesTransactionErrorId = "salesTransactionErrorId001";
        String userId = "userId01";

        insertSalesCoreTableService.insertSalesCoreTable(transactionImportData,
                salesTransactionErrorId, userId);

        verify(salesOrderInformationEntityMapper, times(1)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionHeaderEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.anyInt());

        verify(salesTransactionHeaderEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionDetailEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.anyInt(),
                Mockito.anyInt());

        verify(salesTransactionDetailEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0))
                .convertTSalesTransactionDetailEntityForInsertOutside(Mockito.any(), Mockito.any(),
                        Mockito.anyInt(), Mockito.any(), Mockito.any(), Mockito.anyInt(),
                        Mockito.anyInt());

        verify(salesTransactionDetailEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionDetailInfoEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(),
                Mockito.anyInt());

        verify(salesTransactionDetailInfoEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionDiscountEntityForInsertNon(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(),
                Mockito.anyInt());

        verify(salesTransactionDiscountEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionTaxEntityForInsertNon(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(),
                Mockito.anyInt());

        verify(salesTransactionTaxEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionDiscountEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(),
                Mockito.anyInt());

        verify(salesTransactionDiscountEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionTaxEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(),
                Mockito.anyInt());

        verify(salesTransactionTaxEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0))
                .convertTSalesTransactionDetailEntityForInsertOutside(Mockito.any(), Mockito.any(),
                        Mockito.anyInt(), Mockito.any(), Mockito.any(), Mockito.anyInt(),
                        Mockito.anyInt());

        verify(salesTransactionDetailEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionDetailInfoEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(),
                Mockito.anyInt());

        verify(salesTransactionDetailInfoEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionDiscountEntityForInsertNon(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(),
                Mockito.anyInt());

        verify(salesTransactionDiscountEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionTaxEntityForInsertNon(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(),
                Mockito.anyInt());

        verify(salesTransactionTaxEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionTenderEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt());

        verify(salesTransactionTenderEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionTenderInfoEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.any(),
                Mockito.any(), anyInt());

        verify(salesTransactionTenderInfoEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0))
                .convertTSalesTransactionTaxEntityForInsertTransaction(Mockito.any(), Mockito.any(),
                        Mockito.anyInt(), Mockito.any(), Mockito.anyInt(), Mockito.anyInt());

        verify(salesTransactionDiscountEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(salesTransactionTaxEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionTotalAmountEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt());

        verify(salesTransactionTotalAmountEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(errorDataCorrectionService, times(1)).correctErrorData(Mockito.any(), Mockito.any(),
                Mockito.any());
    }

    @Test
    public void insertSalesCoreTableTestTwo()  {

        TransactionImportData transactionImportData = new TransactionImportData();
        setTransactionImportData(transactionImportData);
        transactionImportData.setSalesTransactionErrorId("");
        transactionImportData.setUpdateType("CORRECTION");
        String salesTransactionErrorId = "";
        String userId = "userId01";

        SalesErrorSalesOrderInformation tSalesErrorSalesOrderInformationEntity =
                new SalesErrorSalesOrderInformation();
        tSalesErrorSalesOrderInformationEntity.setTransactionId("001");

        List<SalesErrorSalesOrderInformation> tSalesErrorSalesOrderInformationEntityList =
                new ArrayList<>();
        tSalesErrorSalesOrderInformationEntityList.add(tSalesErrorSalesOrderInformationEntity);
        when(salesErrorSalesOrderInformationEntityMapper.selectByCondition(Mockito.any()))
                .thenReturn(tSalesErrorSalesOrderInformationEntityList);

        doNothing().when(insertErrorEvacuationTableService)
                .insertErrorEvacuationTable(Mockito.any(), Mockito.any(), Mockito.any());

        when(salesOrderInformationEntityMapper.insertSelective(Mockito.any())).thenReturn(1);

        

        try {
            doNothing().when(errorDataCorrectionService).correctErrorData(Mockito.any(), Mockito.any(),
                    Mockito.any());
            
            insertSalesCoreTableService.insertSalesCoreTable(transactionImportData,
                    salesTransactionErrorId, userId);
        } catch (ErrorEvacuationException e) {
            e.printStackTrace();
        }

        verify(insertErrorEvacuationTableService, times(1))
                .insertErrorEvacuationTable(Mockito.any(), Mockito.any(), Mockito.any());

        verify(salesOrderInformationEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionHeaderEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.anyInt());

        verify(salesTransactionHeaderEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionDetailEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.anyInt(),
                Mockito.anyInt());

        verify(salesTransactionDetailEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0))
                .convertTSalesTransactionDetailEntityForInsertOutside(Mockito.any(), Mockito.any(),
                        Mockito.anyInt(), Mockito.any(), Mockito.any(), Mockito.anyInt(),
                        Mockito.anyInt());

        verify(salesTransactionDetailEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionDetailInfoEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(),
                Mockito.anyInt());

        verify(salesTransactionDetailInfoEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionDiscountEntityForInsertNon(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(),
                Mockito.anyInt());

        verify(salesTransactionDiscountEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionTaxEntityForInsertNon(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(),
                Mockito.anyInt());

        verify(salesTransactionTaxEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionDiscountEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(),
                Mockito.anyInt());

        verify(salesTransactionDiscountEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionTaxEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(),
                Mockito.anyInt());

        verify(salesTransactionTaxEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0))
                .convertTSalesTransactionDetailEntityForInsertOutside(Mockito.any(), Mockito.any(),
                        Mockito.anyInt(), Mockito.any(), Mockito.any(), Mockito.anyInt(),
                        Mockito.anyInt());

        verify(salesTransactionDetailEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionDetailInfoEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(),
                Mockito.anyInt());

        verify(salesTransactionDetailInfoEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionDiscountEntityForInsertNon(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(),
                Mockito.anyInt());

        verify(salesTransactionDiscountEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionTaxEntityForInsertNon(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(),
                Mockito.anyInt());

        verify(salesTransactionTaxEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionTenderEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt());

        verify(salesTransactionTenderEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionTenderInfoEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.any(),
                Mockito.any(), anyInt());

        verify(salesTransactionTenderInfoEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0))
                .convertTSalesTransactionTaxEntityForInsertTransaction(Mockito.any(), Mockito.any(),
                        Mockito.anyInt(), Mockito.any(), Mockito.anyInt(), Mockito.anyInt());

        verify(salesTransactionDiscountEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(salesTransactionTaxEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionTotalAmountEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt());

   

    }

    @Test
    public void insertSalesCoreTableTestNullTransacionList() throws ErrorEvacuationException {

        TransactionImportData transactionImportData = new TransactionImportData();
        setTransactionImportData(transactionImportData);
        transactionImportData.setSalesTransactionErrorId(null);
        transactionImportData.setUpdateType("CORRECTION");
        transactionImportData.setTransactionList(null);
        String salesTransactionErrorId = "";
        String userId = "userId01";

        transactionImportData.setIntegratedOrderId("");
        transactionImportData.setStoreCode("");

        transactionImportData.setChannelCode("");
        transactionImportData.setCustomerId("");
        transactionImportData.setOrderConfirmationBusinessDate("");

        List<SalesTransactionErrorDetail> tSalesTransactionErrorDetailEntityList =
                new ArrayList<>();

        when(salesTransactionErrorDetailEntityMapper.selectByCondition(Mockito.any()))
                .thenReturn(tSalesTransactionErrorDetailEntityList);

        doNothing().when(insertErrorEvacuationTableService)
                .insertErrorEvacuationTable(Mockito.any(), Mockito.any(), Mockito.any());

        when(salesOrderInformationEntityMapper.insertSelective(Mockito.any())).thenReturn(1);

        doNothing().when(errorDataCorrectionService).correctErrorData(Mockito.any(), Mockito.any(),
                Mockito.any());

        insertSalesCoreTableService.insertSalesCoreTable(transactionImportData,
                salesTransactionErrorId, userId);

        verify(insertErrorEvacuationTableService, times(0))
                .insertErrorEvacuationTable(Mockito.any(), Mockito.any(), Mockito.any());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionHeaderEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.anyInt());

        verify(salesTransactionHeaderEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionDetailEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.anyInt(),
                Mockito.anyInt());

        verify(salesTransactionDetailEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0))
                .convertTSalesTransactionDetailEntityForInsertOutside(Mockito.any(), Mockito.any(),
                        Mockito.anyInt(), Mockito.any(), Mockito.any(), Mockito.anyInt(),
                        Mockito.anyInt());

        verify(salesTransactionDetailEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionDetailInfoEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(),
                Mockito.anyInt());

        verify(salesTransactionDetailInfoEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionDiscountEntityForInsertNon(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(),
                Mockito.anyInt());

        verify(salesTransactionDiscountEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionTaxEntityForInsertNon(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(),
                Mockito.anyInt());

        verify(salesTransactionTaxEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionDiscountEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(),
                Mockito.anyInt());

        verify(salesTransactionDiscountEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionTaxEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(),
                Mockito.anyInt());

        verify(salesTransactionTaxEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0))
                .convertTSalesTransactionDetailEntityForInsertOutside(Mockito.any(), Mockito.any(),
                        Mockito.anyInt(), Mockito.any(), Mockito.any(), Mockito.anyInt(),
                        Mockito.anyInt());

        verify(salesTransactionDetailEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionDetailInfoEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(),
                Mockito.anyInt());

        verify(salesTransactionDetailInfoEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionDiscountEntityForInsertNon(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(),
                Mockito.anyInt());

        verify(salesTransactionDiscountEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionTaxEntityForInsertNon(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(),
                Mockito.anyInt());

        verify(salesTransactionTaxEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionTenderEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt());

        verify(salesTransactionTenderEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionTenderInfoEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.any(),
                Mockito.any(), anyInt());

        verify(salesTransactionTenderInfoEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0))
                .convertTSalesTransactionTaxEntityForInsertTransaction(Mockito.any(), Mockito.any(),
                        Mockito.anyInt(), Mockito.any(), Mockito.anyInt(), Mockito.anyInt());

        verify(salesTransactionDiscountEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(salesTransactionTaxEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionTotalAmountEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt());

        verify(salesTransactionTotalAmountEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(errorDataCorrectionService, times(0)).correctErrorData(Mockito.any(), Mockito.any(),
                Mockito.any());

    }

    @Test
    public void insertSalesCoreTableTestFour() throws ErrorEvacuationException {

        TransactionImportData transactionImportData = new TransactionImportData();
        setTransactionImportData(transactionImportData);
        transactionImportData.setSalesTransactionErrorId(null);
        transactionImportData.setUpdateType("CORRECTION");
        String salesTransactionErrorId = "";
        String userId = "userId01";

        transactionImportData.setIntegratedOrderId("");
        transactionImportData.setStoreCode("");

        transactionImportData.setChannelCode("");
        transactionImportData.setCustomerId("");
        transactionImportData.setOrderConfirmationBusinessDate("");

        List<Transaction> transactionList = new ArrayList<>();

        transactionImportData.setTransactionList(transactionList);

        List<SalesTransactionErrorDetail> tSalesTransactionErrorDetailEntityList =

                new ArrayList<>();

        when(salesTransactionErrorDetailEntityMapper.selectByCondition(Mockito.any()))
                .thenReturn(tSalesTransactionErrorDetailEntityList);

        doNothing().when(insertErrorEvacuationTableService)
                .insertErrorEvacuationTable(Mockito.any(), Mockito.any(), Mockito.any());

        when(salesOrderInformationEntityMapper.insertSelective(Mockito.any())).thenReturn(1);

        doNothing().when(errorDataCorrectionService).correctErrorData(Mockito.any(), Mockito.any(),
                Mockito.any());

        insertSalesCoreTableService.insertSalesCoreTable(transactionImportData,
                salesTransactionErrorId, userId);

        verify(insertErrorEvacuationTableService, times(0))
                .insertErrorEvacuationTable(Mockito.any(), Mockito.any(), Mockito.any());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionHeaderEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.anyInt());

        verify(salesTransactionHeaderEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionDetailEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.anyInt(),
                Mockito.anyInt());

        verify(salesTransactionDetailEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0))
                .convertTSalesTransactionDetailEntityForInsertOutside(Mockito.any(), Mockito.any(),
                        Mockito.anyInt(), Mockito.any(), Mockito.any(), Mockito.anyInt(),
                        Mockito.anyInt());

        verify(salesTransactionDetailEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionDetailInfoEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(),
                Mockito.anyInt());

        verify(salesTransactionDetailInfoEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionDiscountEntityForInsertNon(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(),
                Mockito.anyInt());

        verify(salesTransactionDiscountEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionTaxEntityForInsertNon(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(),
                Mockito.anyInt());

        verify(salesTransactionTaxEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionDiscountEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(),
                Mockito.anyInt());

        verify(salesTransactionDiscountEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionTaxEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(),
                Mockito.anyInt());

        verify(salesTransactionTaxEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0))
                .convertTSalesTransactionDetailEntityForInsertOutside(Mockito.any(), Mockito.any(),
                        Mockito.anyInt(), Mockito.any(), Mockito.any(), Mockito.anyInt(),
                        Mockito.anyInt());

        verify(salesTransactionDetailEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionDetailInfoEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(),
                Mockito.anyInt());

        verify(salesTransactionDetailInfoEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionDiscountEntityForInsertNon(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(),
                Mockito.anyInt());

        verify(salesTransactionDiscountEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionTaxEntityForInsertNon(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(),
                Mockito.anyInt());

        verify(salesTransactionTaxEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionTenderEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt());

        verify(salesTransactionTenderEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionTenderInfoEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.any(),
                Mockito.any(), anyInt());

        verify(salesTransactionTenderInfoEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0))
                .convertTSalesTransactionTaxEntityForInsertTransaction(Mockito.any(), Mockito.any(),
                        Mockito.anyInt(), Mockito.any(), Mockito.anyInt(), Mockito.anyInt());

        verify(salesTransactionDiscountEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(salesTransactionTaxEntityMapper, times(0)).insertSelective(Mockito.any());

        verify(coreTableDataConverter, times(0)).convertTSalesTransactionTotalAmountEntityForInsert(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt());

        verify(errorDataCorrectionService, times(0)).correctErrorData(Mockito.any(), Mockito.any(),
                Mockito.any());

    }

    @Test
    public void insertSalesCoreTableTestOne1() throws ErrorEvacuationException {

        TransactionImportData transactionImportData = new TransactionImportData();
        setTransactionImportData(transactionImportData);
        transactionImportData.setSalesTransactionErrorId("transactionid00000000000000000001");
        transactionImportData.setUpdateType("CORRECTION");
        String salesTransactionErrorId = "salesTransactionErrorId001";
        String userId = "userId01";

        when(salesOrderInformationEntityMapper.insertSelective(Mockito.any())).thenReturn(0);
        SalesOrderInformation orderInformationEntity = new SalesOrderInformation();
        orderInformationEntity.setIntegratedOrderId("1");
        when(coreTableDataConverter.convertTSalesOrderInformationEntityForInsert(Mockito.any(),
                Mockito.any())).thenReturn(orderInformationEntity);

        insertSalesCoreTableService.insertSalesCoreTable(transactionImportData,
                salesTransactionErrorId, userId);

        verify(salesOrderInformationEntityMapper, times(0))
                .updateByPrimaryKeySelective(Mockito.any());
    }

    @Test
    public void insertSalesCoreTableTestOne2() throws ErrorEvacuationException {

        TransactionImportData transactionImportData = new TransactionImportData();
        setTransactionImportData(transactionImportData);
        transactionImportData.setSalesTransactionErrorId("transactionid00000000000000000001");
        transactionImportData.setUpdateType("CORRECTION");
        String salesTransactionErrorId = "salesTransactionErrorId001";
        String userId = "userId01";

        when(salesTransactionHeaderEntityMapper.insertSelective(Mockito.any())).thenReturn(0);

        SalesTransactionErrorDetail salesTransactionErrorDetailEntity =
                new SalesTransactionErrorDetail();
        salesTransactionErrorDetailEntity
                .setErrorType(ErrorType.UNIQUE_CONSTRAINTS_ERROR.getErrorType());

        when(errorDetailConverter.convertTSalesTransactionErrorDetailEntityForUniqueConstraints(
                Mockito.any(SalesTransactionHeader.class), Mockito.anyString()))
                        .thenReturn(salesTransactionErrorDetailEntity);

        try {

            insertSalesCoreTableService.insertSalesCoreTable(transactionImportData,
                    salesTransactionErrorId, userId);

        } catch (UniqueConstraintsException e) {

            assertEquals(salesTransactionErrorDetailEntity.getErrorType(),
                    e.getEntity().getErrorType());
        }

    }

    @Test
    public void insertSalesCoreTableTestOne3() throws ErrorEvacuationException {

        TransactionImportData transactionImportData = new TransactionImportData();
        setTransactionImportData(transactionImportData);
        transactionImportData.setSalesTransactionErrorId("transactionid00000000000000000001");
        transactionImportData.setUpdateType("CORRECTION");
        String salesTransactionErrorId = "salesTransactionErrorId001";
        String userId = "userId01";

        when(salesTransactionDetailEntityMapper.insertSelective(Mockito.any())).thenReturn(0);

        SalesTransactionErrorDetail salesTransactionErrorDetailEntity =
                new SalesTransactionErrorDetail();
        salesTransactionErrorDetailEntity
                .setErrorType(ErrorType.UNIQUE_CONSTRAINTS_ERROR.getErrorType());

        when(errorDetailConverter.convertTSalesTransactionErrorDetailEntityForUniqueConstraints(
                Mockito.any(SalesTransactionDetail.class), Mockito.anyString()))
                        .thenReturn(salesTransactionErrorDetailEntity);

        try {

            insertSalesCoreTableService.insertSalesCoreTable(transactionImportData,
                    salesTransactionErrorId, userId);

        } catch (UniqueConstraintsException e) {

            assertEquals(salesTransactionErrorDetailEntity.getErrorType(),
                    e.getEntity().getErrorType());
        }

    }

    @Test
    public void insertSalesCoreTableTestOne4() throws ErrorEvacuationException {

        TransactionImportData transactionImportData = new TransactionImportData();
        setTransactionImportData(transactionImportData);
        transactionImportData.setSalesTransactionErrorId("transactionid00000000000000000001");
        transactionImportData.setUpdateType("CORRECTION");
        String salesTransactionErrorId = "salesTransactionErrorId001";
        String userId = "userId01";

        SalesTransactionDetail tt1 = new SalesTransactionDetail();
        tt1.setBclassPriceCurrencyCode("1");
        when(coreTableDataConverter.convertTSalesTransactionDetailEntityForInsert(Mockito.any(),
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt()))
                        .thenReturn(tt1);

        SalesTransactionDetail tt2 = new SalesTransactionDetail();
        tt2.setBclassPriceCurrencyCode("2");
        when(coreTableDataConverter.convertTSalesTransactionDetailEntityForInsertOutside(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.any(),
                Mockito.anyInt(), Mockito.anyInt())).thenReturn(tt2);

        Mockito.doAnswer(new Answer<Integer>() {
            @Override
            public Integer answer(final InvocationOnMock invocation) throws Throwable {

                Object[] args = invocation.getArguments();

                SalesTransactionDetail returnedValue = (SalesTransactionDetail) args[0];

                int value = 0;
                if ("1".equals(returnedValue.getBclassPriceCurrencyCode())) {
                    value = 1;
                }
                return value;
            }
        }).when(salesTransactionDetailEntityMapper).insertSelective(Mockito.any());

        SalesTransactionErrorDetail salesTransactionErrorDetailEntity =
                new SalesTransactionErrorDetail();

        salesTransactionErrorDetailEntity
                .setErrorType(ErrorType.UNIQUE_CONSTRAINTS_ERROR.getErrorType());

        when(errorDetailConverter
                .convertTSalesTransactionErrorDetailEntityForUniqueConstraintsLevel4(
                        Mockito.any(SalesTransactionDetail.class), Mockito.anyString()))
                                .thenReturn(salesTransactionErrorDetailEntity);

        try {

            insertSalesCoreTableService.insertSalesCoreTable(transactionImportData,
                    salesTransactionErrorId, userId);

        } catch (UniqueConstraintsException e) {

            assertEquals(salesTransactionErrorDetailEntity.getErrorType(),
                    e.getEntity().getErrorType());
        }

    }

    @Test
    public void insertSalesCoreTableTestOne5() throws ErrorEvacuationException {

        TransactionImportData transactionImportData = new TransactionImportData();
        setTransactionImportData(transactionImportData);
        transactionImportData.setSalesTransactionErrorId("transactionid00000000000000000001");
        transactionImportData.setUpdateType("CORRECTION");
        String salesTransactionErrorId = "salesTransactionErrorId001";
        String userId = "userId01";

        when(salesTransactionDiscountEntityMapper.insertSelective(Mockito.any())).thenReturn(0);

        SalesTransactionErrorDetail salesTransactionErrorDetailEntity =
                new SalesTransactionErrorDetail();
        salesTransactionErrorDetailEntity
                .setErrorType(ErrorType.UNIQUE_CONSTRAINTS_ERROR.getErrorType());

        when(errorDetailConverter
                .convertTSalesTransactionErrorDetailEntityForUniqueConstraintsLevel5(
                        Mockito.any(SalesTransactionDiscount.class), Mockito.anyString()))
                                .thenReturn(salesTransactionErrorDetailEntity);

        try {

            insertSalesCoreTableService.insertSalesCoreTable(transactionImportData,
                    salesTransactionErrorId, userId);

        } catch (UniqueConstraintsException e) {

            assertEquals(salesTransactionErrorDetailEntity.getErrorType(),
                    e.getEntity().getErrorType());
        }

    }

    @Test
    public void testSalesCoreTableTransactionDetailUniqueConstraints() throws ErrorEvacuationException {

        TransactionImportData transactionImportData = new TransactionImportData();
        setTransactionImportData(transactionImportData);
        transactionImportData.setSalesTransactionErrorId("transactionid00000000000000000001");
        transactionImportData.setUpdateType("CORRECTION");
        String salesTransactionErrorId = "salesTransactionErrorId001";
        String userId = "userId01";

        when(salesTransactionDetailEntityMapper.insertSelective(Mockito.any())).thenReturn(0);

        SalesTransactionErrorDetail salesTransactionErrorDetailEntity =
                new SalesTransactionErrorDetail();
        salesTransactionErrorDetailEntity
                .setErrorType(ErrorType.UNIQUE_CONSTRAINTS_ERROR.getErrorType());

        when(errorDetailConverter.convertTSalesTransactionErrorDetailEntityForUniqueConstraints(
                Mockito.any(SalesTransactionDetail.class), Mockito.anyString()))
                        .thenReturn(salesTransactionErrorDetailEntity);
        try {
            insertSalesCoreTableService.insertSalesCoreTable(transactionImportData,
                    salesTransactionErrorId, userId);
        } catch (UniqueConstraintsException e) {
            assertEquals(salesTransactionErrorDetailEntity.getErrorType(),
                    e.getEntity().getErrorType());
        }

    }

    @Test
    public void testSalesCoreTableNonItemDetailUniqueConstraints() throws ErrorEvacuationException {

        TransactionImportData transactionImportData = new TransactionImportData();
        setTransactionImportData(transactionImportData);
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.setTransactionItemDetailList(new ArrayList<>());
        });
        transactionImportData.setSalesTransactionErrorId("transactionid00000000000000000001");
        transactionImportData.setUpdateType("CORRECTION");
        String salesTransactionErrorId = "salesTransactionErrorId001";
        String userId = "userId01";

        when(salesTransactionDetailEntityMapper.insertSelective(Mockito.any())).thenReturn(0);

        SalesTransactionErrorDetail salesTransactionErrorDetailEntity =
                new SalesTransactionErrorDetail();
        salesTransactionErrorDetailEntity
                .setErrorType(ErrorType.UNIQUE_CONSTRAINTS_ERROR.getErrorType());

        when(errorDetailConverter.convertTSalesTransactionErrorDetailEntityForUniqueConstraints(
                Mockito.any(SalesTransactionDetail.class), Mockito.anyString()))
                        .thenReturn(salesTransactionErrorDetailEntity);
        try {
            insertSalesCoreTableService.insertSalesCoreTable(transactionImportData,
                    salesTransactionErrorId, userId);
        } catch (UniqueConstraintsException e) {
            assertEquals(salesTransactionErrorDetailEntity.getErrorType(),
                    e.getEntity().getErrorType());
        }

    }

    @Test
    public void testSalesCoreTableTransactionTaxUniqueConstraints() throws ErrorEvacuationException  {

        TransactionImportData transactionImportData = new TransactionImportData();
        setTransactionImportData(transactionImportData);
        transactionImportData.setSalesTransactionErrorId("transactionid00000000000000000001");
        transactionImportData.setUpdateType("CORRECTION");
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.setTransactionItemDetailList(null);
            transaction.getNonItemDetailList().forEach(nonItemDetail -> {
                nonItemDetail.setNonItemDiscountDetailList(null);
                nonItemDetail.setNonItemTaxDetailList(null);
                nonItemDetail.setNonItemInfo(null);

            });
        });
        String salesTransactionErrorId = "salesTransactionErrorId001";
        String userId = "userId01";

        when(salesTransactionTaxEntityMapper.insertSelective(Mockito.any())).thenReturn(0);

        SalesTransactionErrorDetail salesTransactionErrorDetailEntity =
                new SalesTransactionErrorDetail();
        salesTransactionErrorDetailEntity
                .setErrorType(ErrorType.UNIQUE_CONSTRAINTS_ERROR.getErrorType());

        when(errorDetailConverter.convertTSalesTransactionErrorDetailEntityForUniqueConstraints(
                Mockito.any(SalesTransactionTax.class), Mockito.anyString()))
                        .thenReturn(salesTransactionErrorDetailEntity);
        try {
            insertSalesCoreTableService.insertSalesCoreTable(transactionImportData,
                    salesTransactionErrorId, userId);
        } catch (UniqueConstraintsException e) {
            assertEquals(salesTransactionErrorDetailEntity.getErrorType(),
                    e.getEntity().getErrorType());
        }

    }

    @Test
    public void testSalesCoreTableNonDiscountUniqueConstraints() throws ErrorEvacuationException  {

        TransactionImportData transactionImportData = new TransactionImportData();
        setTransactionImportData(transactionImportData);
        transactionImportData.setSalesTransactionErrorId("transactionid00000000000000000001");
        transactionImportData.setUpdateType("CORRECTION");
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.setTransactionItemDetailList(null);
        });
        String salesTransactionErrorId = "salesTransactionErrorId001";
        String userId = "userId01";

        when(salesTransactionDiscountEntityMapper.insertSelective(Mockito.any())).thenReturn(0);

        SalesTransactionErrorDetail salesTransactionErrorDetailEntity =
                new SalesTransactionErrorDetail();
        salesTransactionErrorDetailEntity
                .setErrorType(ErrorType.UNIQUE_CONSTRAINTS_ERROR.getErrorType());

        when(errorDetailConverter.convertTSalesTransactionErrorDetailEntityForUniqueConstraints(
                Mockito.any(SalesTransactionDiscount.class), Mockito.anyString()))
                        .thenReturn(salesTransactionErrorDetailEntity);
        try {
            insertSalesCoreTableService.insertSalesCoreTable(transactionImportData,
                    salesTransactionErrorId, userId);
        } catch (UniqueConstraintsException e) {
            assertEquals(salesTransactionErrorDetailEntity.getErrorType(),
                    e.getEntity().getErrorType());
        }

    }

    @Test
    public void testSalesCoreTableNonItemTaxUniqueConstraints() throws ErrorEvacuationException {

        TransactionImportData transactionImportData = new TransactionImportData();
        setTransactionImportData(transactionImportData);
        transactionImportData.setSalesTransactionErrorId("transactionid00000000000000000001");
        transactionImportData.setUpdateType("CORRECTION");
        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.setTransactionItemDetailList(null);
            transaction.setSalesTransactionTaxDetailList(null);
        });
        String salesTransactionErrorId = "salesTransactionErrorId001";
        String userId = "userId01";

        when(salesTransactionTaxEntityMapper.insertSelective(Mockito.any())).thenReturn(0);

        SalesTransactionErrorDetail salesTransactionErrorDetailEntity =
                new SalesTransactionErrorDetail();
        salesTransactionErrorDetailEntity
                .setErrorType(ErrorType.UNIQUE_CONSTRAINTS_ERROR.getErrorType());

        when(errorDetailConverter
                .convertTSalesTransactionErrorDetailEntityForUniqueConstraintsLevel4(
                        Mockito.any(SalesTransactionTax.class), Mockito.anyString()))
                                .thenReturn(salesTransactionErrorDetailEntity);
        try {
            insertSalesCoreTableService.insertSalesCoreTable(transactionImportData,
                    salesTransactionErrorId, userId);
        } catch (UniqueConstraintsException e) {
            assertEquals(salesTransactionErrorDetailEntity.getErrorType(),
                    e.getEntity().getErrorType());
        }

    }

    @Test
    public void insertSalesCoreTableTestOne6() throws ErrorEvacuationException {

        TransactionImportData transactionImportData = new TransactionImportData();
        setTransactionImportData(transactionImportData);
        transactionImportData.setSalesTransactionErrorId("transactionid00000000000000000001");
        transactionImportData.setUpdateType("CORRECTION");
        String salesTransactionErrorId = "salesTransactionErrorId001";
        String userId = "userId01";

        when(salesTransactionTaxEntityMapper.insertSelective(Mockito.any())).thenReturn(0);

        SalesTransactionErrorDetail salesTransactionErrorDetailEntity =
                new SalesTransactionErrorDetail();
        salesTransactionErrorDetailEntity
                .setErrorType(ErrorType.UNIQUE_CONSTRAINTS_ERROR.getErrorType());

        when(errorDetailConverter
                .convertTSalesTransactionErrorDetailEntityForUniqueConstraintsLevel5(
                        Mockito.any(SalesTransactionTax.class), Mockito.anyString()))
                                .thenReturn(salesTransactionErrorDetailEntity);

        try {

            insertSalesCoreTableService.insertSalesCoreTable(transactionImportData,
                    salesTransactionErrorId, userId);

        } catch (UniqueConstraintsException e) {

            assertEquals(salesTransactionErrorDetailEntity.getErrorType(),
                    e.getEntity().getErrorType());
        }

    }

    @Test
    public void insertSalesCoreTableTestOne7() throws ErrorEvacuationException {

        TransactionImportData transactionImportData = new TransactionImportData();
        setTransactionImportData(transactionImportData);
        transactionImportData.setSalesTransactionErrorId("transactionid00000000000000000001");
        transactionImportData.setUpdateType("CORRECTION");
        String salesTransactionErrorId = "salesTransactionErrorId001";
        String userId = "userId01";

        SalesTransactionDiscount tt1 = new SalesTransactionDiscount();

        tt1.setCreateProgramId("1");

        when(coreTableDataConverter.convertTSalesTransactionDiscountEntityForInsertNon(
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(),
                Mockito.anyInt())).thenReturn(tt1);

        SalesTransactionDiscount tt2 = new SalesTransactionDiscount();
        tt2.setCreateProgramId("2");
        when(coreTableDataConverter.convertTSalesTransactionDiscountEntityForInsert(Mockito.any(),
                Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(), Mockito.anyInt()))
                        .thenReturn(tt2);

        Mockito.doAnswer(new Answer<Integer>() {
            @Override
            public Integer answer(final InvocationOnMock invocation) throws Throwable {

                Object[] args = invocation.getArguments();

                SalesTransactionDiscount returnedValue = (SalesTransactionDiscount) args[0];

                int value = 0;
                if ("1".equals(returnedValue.getCreateProgramId())) {
                    value = 1;
                }
                return value;
            }
        }).when(salesTransactionDiscountEntityMapper).insertSelective(Mockito.any());

        SalesTransactionErrorDetail salesTransactionErrorDetailEntity =
                new SalesTransactionErrorDetail();

        salesTransactionErrorDetailEntity
                .setErrorType(ErrorType.UNIQUE_CONSTRAINTS_ERROR.getErrorType());

        when(errorDetailConverter.convertTSalesTransactionErrorDetailEntityForUniqueConstraints(
                Mockito.any(SalesTransactionDiscount.class), Mockito.anyString()))
                        .thenReturn(salesTransactionErrorDetailEntity);

        try {

            insertSalesCoreTableService.insertSalesCoreTable(transactionImportData,
                    salesTransactionErrorId, userId);

        } catch (UniqueConstraintsException e) {

            assertEquals(salesTransactionErrorDetailEntity.getErrorType(),
                    e.getEntity().getErrorType());
        }

    }

    @Test
    public void insertSalesCoreTableTestOne8() throws ErrorEvacuationException {

        TransactionImportData transactionImportData = new TransactionImportData();
        setTransactionImportData(transactionImportData);
        transactionImportData.setSalesTransactionErrorId("transactionid00000000000000000001");
        transactionImportData.setUpdateType("CORRECTION");
        String salesTransactionErrorId = "salesTransactionErrorId001";
        String userId = "userId01";

        SalesTransactionDetail detail = new SalesTransactionDetail();
        detail.setDetailSubNumber(1);
        when(coreTableDataConverter.convertTSalesTransactionDetailEntityForInsert(Mockito.any(),
                Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt()))
                        .thenReturn(detail);

        SalesTransactionTax tt1 = new SalesTransactionTax();

        tt1.setCreateProgramId("1");

        when(coreTableDataConverter.convertTSalesTransactionTaxEntityForInsertNon(Mockito.any(),
                Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(), Mockito.anyInt()))
                        .thenReturn(tt1);

        SalesTransactionTax tt2 = new SalesTransactionTax();
        tt2.setCreateProgramId("2");
        when(coreTableDataConverter.convertTSalesTransactionTaxEntityForInsert(Mockito.any(),
                Mockito.any(), Mockito.anyInt(), Mockito.any(), Mockito.anyInt(), Mockito.anyInt()))
                        .thenReturn(tt2);

        Mockito.doAnswer(new Answer<Integer>() {
            @Override
            public Integer answer(final InvocationOnMock invocation) throws Throwable {

                Object[] args = invocation.getArguments();

                SalesTransactionTax returnedValue = (SalesTransactionTax) args[0];

                int value = 0;
                if ("1".equals(returnedValue.getCreateProgramId())) {
                    value = 1;
                }
                return value;
            }
        }).when(salesTransactionTaxEntityMapper).insertSelective(Mockito.any());

        SalesTransactionErrorDetail salesTransactionErrorDetailEntity =
                new SalesTransactionErrorDetail();
        salesTransactionErrorDetailEntity
                .setErrorType(ErrorType.UNIQUE_CONSTRAINTS_ERROR.getErrorType());

        when(errorDetailConverter
                .convertTSalesTransactionErrorDetailEntityForUniqueConstraintsLevel4(
                        Mockito.any(SalesTransactionTax.class), Mockito.anyString()))
                                .thenReturn(salesTransactionErrorDetailEntity);

        try {

            insertSalesCoreTableService.insertSalesCoreTable(transactionImportData,
                    salesTransactionErrorId, userId);

        } catch (UniqueConstraintsException e) {

            assertEquals(salesTransactionErrorDetailEntity.getErrorType(),
                    e.getEntity().getErrorType());
        }

    }

    @Test
    public void insertSalesCoreTableTestOne10() throws ErrorEvacuationException {

        TransactionImportData transactionImportData = new TransactionImportData();
        setTransactionImportData(transactionImportData);
        transactionImportData.setSalesTransactionErrorId("transactionid00000000000000000001");
        transactionImportData.setUpdateType("CORRECTION");
        String salesTransactionErrorId = "salesTransactionErrorId001";
        String userId = "userId01";

        when(salesTransactionTotalAmountEntityMapper.insertSelective(Mockito.any())).thenReturn(0);

        SalesTransactionErrorDetail salesTransactionErrorDetailEntity =
                new SalesTransactionErrorDetail();
        salesTransactionErrorDetailEntity
                .setErrorType(ErrorType.UNIQUE_CONSTRAINTS_ERROR.getErrorType());

        when(errorDetailConverter.convertTSalesTransactionErrorDetailEntityForUniqueConstraints(
                Mockito.any(SalesTransactionTotalAmount.class), Mockito.anyString()))
                        .thenReturn(salesTransactionErrorDetailEntity);

        try {

            insertSalesCoreTableService.insertSalesCoreTable(transactionImportData,
                    salesTransactionErrorId, userId);

        } catch (UniqueConstraintsException e) {

            assertEquals(salesTransactionErrorDetailEntity.getErrorType(),
                    e.getEntity().getErrorType());
        }

    }

    @Test
    public void insertSalesCoreTableTestOne13() throws ErrorEvacuationException {

        TransactionImportData transactionImportData = new TransactionImportData();
        setTransactionImportData(transactionImportData);
        transactionImportData.setSalesTransactionErrorId("transactionid00000000000000000001");
        transactionImportData.setUpdateType("CORRECTION");
        String salesTransactionErrorId = "salesTransactionErrorId001";
        String userId = "userId01";

        when(salesTransactionTenderEntityMapper.insertSelective(Mockito.any())).thenReturn(0);

        SalesTransactionErrorDetail salesTransactionErrorDetailEntity =
                new SalesTransactionErrorDetail();
        salesTransactionErrorDetailEntity
                .setErrorType(ErrorType.UNIQUE_CONSTRAINTS_ERROR.getErrorType());

        when(errorDetailConverter.convertTSalesTransactionErrorDetailEntityForUniqueConstraints(
                Mockito.any(SalesTransactionTenderTable.class), Mockito.anyString()))
                        .thenReturn(salesTransactionErrorDetailEntity);

        try {

            insertSalesCoreTableService.insertSalesCoreTable(transactionImportData,
                    salesTransactionErrorId, userId);

        } catch (UniqueConstraintsException e) {

            assertEquals(salesTransactionErrorDetailEntity.getErrorType(),
                    e.getEntity().getErrorType());
        }

    }

    @Test
    public void insertSalesCoreTableTestThree14() {

        TransactionImportData transactionImportData = new TransactionImportData();
        setTransactionImportData(transactionImportData);
        transactionImportData.setSalesTransactionErrorId(null);
        transactionImportData.setUpdateType("CORRECTION");
        String salesTransactionErrorId = "";
        String userId = "userId01";

        transactionImportData.setIntegratedOrderId("");
        transactionImportData.setStoreCode("");
        transactionImportData.setChannelCode("");
        transactionImportData.setCustomerId("");
        transactionImportData.setOrderConfirmationBusinessDate("");

        List<SalesErrorSalesOrderInformation> tSalesErrorSalesOrderInformationEntityList =
                new ArrayList<>();
        when(salesErrorSalesOrderInformationEntityMapper.selectByCondition(Mockito.any()))
                .thenReturn(tSalesErrorSalesOrderInformationEntityList);

        doNothing().when(insertErrorEvacuationTableService)
                .insertErrorEvacuationTable(Mockito.any(), Mockito.any(), Mockito.any());
        when(salesOrderInformationEntityMapper.insertSelective(Mockito.any())).thenReturn(0);

       

        SalesTransactionErrorDetail salesTransactionErrorDetailEntity =
                new SalesTransactionErrorDetail();
        salesTransactionErrorDetailEntity
                .setErrorType(ErrorType.UNIQUE_CONSTRAINTS_ERROR.getErrorType());

        when(errorDetailConverter.convertTSalesTransactionErrorDetailEntityForUniqueConstraints(
                Mockito.any(SalesOrderInformation.class), Mockito.anyString()))
                        .thenReturn(salesTransactionErrorDetailEntity);

        try {
            doNothing().when(errorDataCorrectionService).correctErrorData(Mockito.any(), Mockito.any(),
                    Mockito.any());

            insertSalesCoreTableService.insertSalesCoreTable(transactionImportData,
                    salesTransactionErrorId, userId);

        } catch (UniqueConstraintsException e) {

            assertEquals(salesTransactionErrorDetailEntity.getErrorType(),
                    e.getEntity().getErrorType());
        } catch ( ErrorEvacuationException e) {
            e.getStackTrace();
        }
       

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

        transaction.setOrderStatusUpdateDate("1");

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

        TenderInfo tenderInfo = new TenderInfo();
        setTenderInfo(tenderInfo);
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
