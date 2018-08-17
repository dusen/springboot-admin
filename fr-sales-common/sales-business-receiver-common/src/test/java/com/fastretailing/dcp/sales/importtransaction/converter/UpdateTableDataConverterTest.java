package com.fastretailing.dcp.sales.importtransaction.converter;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import java.time.LocalDateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import com.fastretailing.dcp.sales.importtransaction.dto.TableCommonItem;
import com.fastretailing.dcp.sales.importtransaction.dto.TransactionImportData;
import com.fastretailing.dcp.sales.importtransaction.dto.TransactionItemDetail;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionDetail;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionHeader;

/**
 * ErrorEvacuationTableDataConverter JUnit Test class.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UpdateTableDataConverterTest {

    @Autowired
    private CoreTableDataConverter coreTableDataConverter;

    @MockBean
    private CommonDataProcessor commonDataProcessor;

    /**
     * ConvertTSalesTransactionHeaderEntityForUpdate.
     */
    @Test
    public void testConvertTSalesTransactionHeaderEntityForUpdate() {
        TransactionImportData transactionImportData =
                UpdateTableDataConverterTestHelper.makeImportData();
        String userId = "user01";
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 29);
        tableCommonItem.setUpdateUserId("user01");
        tableCommonItem.setUpdateProgramId("SLS0300101");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForUpdate(userId)).thenReturn(tableCommonItem);
        SalesTransactionHeader expect =
                UpdateTableDataConverterTestHelper.makeTSalesTransactionHeaderforUpdate();

        transactionImportData.getTransactionList().forEach(transaction -> {
            SalesTransactionHeader result = coreTableDataConverter
                    .convertTSalesTransactionHeaderEntityForUpdate(transaction, userId);

            System.out.println(result);
            System.out.println(expect);
            assertEquals(expect, result);
        });
    }

    /**
     * ConvertTSalesTransactionDetailEntityForUpdate.
     */
    @Test
    public void testConvertTSalesTransactionDetailEntityForUpdate() {
        TransactionImportData transactionImportData =
                UpdateTableDataConverterTestHelper.makeImportData();
        String userId = "user01";
        String salesTransactionId = "salesTransactionId";
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 29);
        tableCommonItem.setUpdateUserId("user01");
        tableCommonItem.setUpdateProgramId("SLS0300101");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForUpdate(userId)).thenReturn(tableCommonItem);
        SalesTransactionDetail expect =
                UpdateTableDataConverterTestHelper.makeTSalesTransactionDetailforUpdate();

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getTransactionItemDetailList().forEach(transactionItemDetail -> {
                SalesTransactionDetail result =
                        coreTableDataConverter.convertTSalesTransactionDetailEntityForUpdate(
                                transactionItemDetail, salesTransactionId, userId);

                System.out.println(result);
                System.out.println(expect);
                assertEquals(expect, result);
            });
        });

        TransactionItemDetail transactionItemDetail = transactionImportData.getTransactionList()
                .get(0)
                .getTransactionItemDetailList()
                .get(0);
        transactionItemDetail.setBundleSalesFlag(null);
        SalesTransactionDetail result =
                coreTableDataConverter.convertTSalesTransactionDetailEntityForUpdate(
                        transactionItemDetail, salesTransactionId, userId);

        expect.setStoreBundleSaleFlag(false);
        assertEquals(expect, result);
    }

    /**
     * ConvertTSalesTransactionDetailOutsideEntityForUpdate.
     */
    @Test
    public void testConvertTSalesTransactionDetailOutsideEntityForUpdate() {
        TransactionImportData transactionImportData =
                UpdateTableDataConverterTestHelper.makeImportData();
        String userId = "user01";
        String salesTransactionId = "salesTransactionId";
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 29);
        tableCommonItem.setUpdateUserId("user01");
        tableCommonItem.setUpdateProgramId("SLS0300101");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForUpdate(userId)).thenReturn(tableCommonItem);
        SalesTransactionDetail expect =
                UpdateTableDataConverterTestHelper.makeTSalesTransactionDetailNonforUpdate();

        transactionImportData.getTransactionList().forEach(transaction -> {
            transaction.getNonItemDetailList().forEach(nonItemDetail -> {
                SalesTransactionDetail result =
                        coreTableDataConverter.convertTSalesTransactionDetailOutsideEntityForUpdate(
                                nonItemDetail, salesTransactionId, userId);

                System.out.println(result);
                System.out.println(expect);
                assertEquals(expect, result);
            });
        });
    }
}
