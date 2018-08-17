package com.fastretailing.dcp.sales.importtransaction.converter;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import java.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import com.fastretailing.dcp.sales.importtransaction.dto.TableCommonItem;
import com.fastretailing.dcp.sales.importtransaction.dto.TransactionImportData;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesOrderInformation;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionDetail;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionDiscount;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionErrorDetail;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionHeader;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionTax;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionTenderTable;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionTotalAmount;

/**
 * ErrorDetailConverterTest JUnit Test class.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ErrorDetailConverterTest {

    @Autowired
    private ErrorDetailConverter errorDetailConverter;

    @MockBean
    private CommonDataProcessor commonDataProcessor;

    /**
     * Parameter information setting.
     * 
     * @throws Exception occurred exception
     */
    @Before
    public void setUp() throws Exception {}

    /**
     * ConvertTSalesTransactionErrorDetailEntityForUpdateType.
     */
    @Test
    public void testConvertTSalesTransactionErrorDetailEntityForUpdateType() {
        TransactionImportData transactionImportData =
                ErrorDetailConverterTestHelper.makeImportData();
        String salesTransactionErrorId = "salesTransactionErrorId01";
        String userId = "userId01";
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 21);
        tableCommonItem.setCreateUserId("user01");
        tableCommonItem.setCreateProgramId("SLS0300101");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user01");
        tableCommonItem.setUpdateProgramId("SLS0300101");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        SalesTransactionErrorDetail expect =
                ErrorDetailConverterTestHelper.makeTSalesTransactionErrorDetail1();

        SalesTransactionErrorDetail result =
                errorDetailConverter.convertTSalesTransactionErrorDetailEntityForUpdateType(
                        transactionImportData, salesTransactionErrorId, userId);

        System.out.println(result);
        System.out.println(expect);
        assertEquals(expect, result);
    }

    /**
     * ConvertTSalesTransactionErrorDetailEntityForUniqueConstraintsOrder.
     */
    @Test
    public void testConvertTSalesTransactionErrorDetailEntityForUniqueConstraintsOrder() {
        SalesOrderInformation salesOrderInformationEntity =
                ErrorDetailConverterTestHelper.makeTSalesOrderInformation();
        String userId = "userId02";
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 23);
        tableCommonItem.setCreateUserId("user02");
        tableCommonItem.setCreateProgramId("SLS0300102");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user02");
        tableCommonItem.setUpdateProgramId("SLS0300102");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        SalesTransactionErrorDetail expect =
                ErrorDetailConverterTestHelper.makeTSalesTransactionErrorDetail2();

        SalesTransactionErrorDetail result =
                errorDetailConverter.convertTSalesTransactionErrorDetailEntityForUniqueConstraints(
                        salesOrderInformationEntity, userId);

        System.out.println(result);
        System.out.println(expect);
        assertEquals(expect, result);
    }

    /**
     * onvertTSalesTransactionErrorDetailEntityForUniqueConstraintsHeader.
     */
    @Test
    public void testConvertTSalesTransactionErrorDetailEntityForUniqueConstraintsHeader() {
        SalesTransactionHeader salesTransactionHeaderEntity =
                ErrorDetailConverterTestHelper.makeTSalesTransactionHeader();
        String userId = "userId03";
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 23);
        tableCommonItem.setCreateUserId("user03");
        tableCommonItem.setCreateProgramId("SLS0300103");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user03");
        tableCommonItem.setUpdateProgramId("SLS0300103");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        SalesTransactionErrorDetail expect =
                ErrorDetailConverterTestHelper.makeTSalesTransactionErrorDetail3();

        SalesTransactionErrorDetail result =
                errorDetailConverter.convertTSalesTransactionErrorDetailEntityForUniqueConstraints(
                        salesTransactionHeaderEntity, userId);

        System.out.println(result);
        System.out.println(expect);
        assertEquals(expect, result);
    }

    /**
     * ConvertTSalesTransactionErrorDetailEntityForUniqueConstraintsDetail.
     */
    @Test
    public void testConvertTSalesTransactionErrorDetailEntityForUniqueConstraintsDetail() {
        SalesTransactionDetail salesTransactionDetailEntity =
                ErrorDetailConverterTestHelper.makeTSalesTransactionDetail();

        String userId = "userId04";
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 24);
        tableCommonItem.setCreateUserId("user04");
        tableCommonItem.setCreateProgramId("SLS0300104");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user04");
        tableCommonItem.setUpdateProgramId("SLS0300104");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        SalesTransactionErrorDetail expect =
                ErrorDetailConverterTestHelper.makeTSalesTransactionErrorDetail4();

        SalesTransactionErrorDetail result =
                errorDetailConverter.convertTSalesTransactionErrorDetailEntityForUniqueConstraints(
                        salesTransactionDetailEntity, userId);

        System.out.println(result);
        System.out.println(expect);
        assertEquals(expect, result);
    }

    /**
     * TSalesTransactionErrorDetailEntityForUniqueConstraintsTender.
     */
    @Test
    public void testConvertTSalesTransactionErrorDetailEntityForUniqueConstraintsTender() {
        SalesTransactionTenderTable salesTransactionTenderEntity =
                ErrorDetailConverterTestHelper.makeTSalesTransactionTender();

        String userId = "userId05";
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 25);
        tableCommonItem.setCreateUserId("user05");
        tableCommonItem.setCreateProgramId("SLS0300105");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user05");
        tableCommonItem.setUpdateProgramId("SLS0300105");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        SalesTransactionErrorDetail expect =
                ErrorDetailConverterTestHelper.makeTSalesTransactionErrorDetail5();

        SalesTransactionErrorDetail result =
                errorDetailConverter.convertTSalesTransactionErrorDetailEntityForUniqueConstraints(
                        salesTransactionTenderEntity, userId);

        System.out.println(result);
        System.out.println(expect);
        assertEquals(expect, result);
    }

    /**
     * ConvertTSalesTransactionErrorDetailEntityForUniqueConstraintsTax.
     */
    @Test
    public void testConvertTSalesTransactionErrorDetailEntityForUniqueConstraintsTax() {
        SalesTransactionTax salesTransactionTaxEntity =
                ErrorDetailConverterTestHelper.makeTSalesTransactionTax();

        String userId = "userId06";
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 26);
        tableCommonItem.setCreateUserId("user06");
        tableCommonItem.setCreateProgramId("SLS0300106");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user06");
        tableCommonItem.setUpdateProgramId("SLS0300106");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        SalesTransactionErrorDetail expect =
                ErrorDetailConverterTestHelper.makeTSalesTransactionErrorDetail6();

        SalesTransactionErrorDetail result =
                errorDetailConverter.convertTSalesTransactionErrorDetailEntityForUniqueConstraints(
                        salesTransactionTaxEntity, userId);

        System.out.println(result);
        System.out.println(expect);
        assertEquals(expect, result);
    }

    /**
     * ConvertTSalesTransactionErrorDetailEntityForUniqueConstraintsTotal.
     */
    @Test
    public void testConvertTSalesTransactionErrorDetailEntityForUniqueConstraintsTotal() {
        SalesTransactionTotalAmount salesTransactionTotalAmountEntity =
                ErrorDetailConverterTestHelper.makeTSalesTransactionTotalAmount();

        String userId = "userId07";
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 27);
        tableCommonItem.setCreateUserId("user07");
        tableCommonItem.setCreateProgramId("SLS0300107");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user07");
        tableCommonItem.setUpdateProgramId("SLS0300107");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        SalesTransactionErrorDetail expect =
                ErrorDetailConverterTestHelper.makeTSalesTransactionErrorDetail7();

        SalesTransactionErrorDetail result =
                errorDetailConverter.convertTSalesTransactionErrorDetailEntityForUniqueConstraints(
                        salesTransactionTotalAmountEntity, userId);

        System.out.println(result);
        System.out.println(expect);
        assertEquals(expect, result);
    }

    /**
     * ConvertTSalesTransactionErrorDetailEntityForUniqueConstraintsDiscount.
     */
    @Test
    public void testConvertTSalesTransactionErrorDetailEntityForUniqueConstraintsDiscount() {
        SalesTransactionDiscount salesTransactionDiscountEntity =
                ErrorDetailConverterTestHelper.makeTSalesTransactionDiscount();

        String userId = "userId08";
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 28);
        tableCommonItem.setCreateUserId("user08");
        tableCommonItem.setCreateProgramId("SLS0300108");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user08");
        tableCommonItem.setUpdateProgramId("SLS0300108");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        SalesTransactionErrorDetail expect =
                ErrorDetailConverterTestHelper.makeTSalesTransactionErrorDetail8();

        SalesTransactionErrorDetail result =
                errorDetailConverter.convertTSalesTransactionErrorDetailEntityForUniqueConstraints(
                        salesTransactionDiscountEntity, userId);

        System.out.println(result);
        System.out.println(expect);
        assertEquals(expect, result);
    }

    /**
     * ConvertTSalesTransactionErrorDetailEntityForUniqueConstraintsLevel4Detail.
     */
    @Test
    public void testConvertTSalesTransactionErrorDetailEntityForUniqueConstraintsLevel4Detail() {
        SalesTransactionDetail salesTransactionDetailEntity =
                ErrorDetailConverterTestHelper.makeTSalesTransactionDetail();
        String userId = "userId09";
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 29);
        tableCommonItem.setCreateUserId("user09");
        tableCommonItem.setCreateProgramId("SLS0300109");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user09");
        tableCommonItem.setUpdateProgramId("SLS0300109");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        SalesTransactionErrorDetail expect =
                ErrorDetailConverterTestHelper.makeTSalesTransactionErrorDetail9();

        SalesTransactionErrorDetail result = errorDetailConverter
                .convertTSalesTransactionErrorDetailEntityForUniqueConstraintsLevel4(
                        salesTransactionDetailEntity, userId);

        System.out.println(result);
        System.out.println(expect);
        assertEquals(expect, result);
    }

    /**
     * TSalesTransactionErrorDetailEntityForUniqueConstraintsLevel4Tax.
     */
    @Test
    public void testConvertTSalesTransactionErrorDetailEntityForUniqueConstraintsLevel4Tax() {
        SalesTransactionTax salesTransactionTaxEntity =
                ErrorDetailConverterTestHelper.makeTSalesTransactionTax();

        String userId = "userId10";
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 30);
        tableCommonItem.setCreateUserId("user10");
        tableCommonItem.setCreateProgramId("SLS0300110");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user10");
        tableCommonItem.setUpdateProgramId("SLS0300110");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        SalesTransactionErrorDetail expect =
                ErrorDetailConverterTestHelper.makeTSalesTransactionErrorDetail10();

        SalesTransactionErrorDetail result = errorDetailConverter
                .convertTSalesTransactionErrorDetailEntityForUniqueConstraintsLevel4(
                        salesTransactionTaxEntity, userId);

        System.out.println(result);
        System.out.println(expect);
        assertEquals(expect, result);
    }

    /**
     * ConvertTSalesTransactionErrorDetailEntityForUniqueConstraintsLevel5Tax.
     */
    @Test
    public void testConvertTSalesTransactionErrorDetailEntityForUniqueConstraintsLevel5Tax() {
        SalesTransactionTax salesTransactionTaxEntity =
                ErrorDetailConverterTestHelper.makeTSalesTransactionTax();

        String userId = "userId11";
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 31);
        tableCommonItem.setCreateUserId("user11");
        tableCommonItem.setCreateProgramId("SLS0300111");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user11");
        tableCommonItem.setUpdateProgramId("SLS0300111");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        SalesTransactionErrorDetail expect =
                ErrorDetailConverterTestHelper.makeTSalesTransactionErrorDetail11();

        SalesTransactionErrorDetail result = errorDetailConverter
                .convertTSalesTransactionErrorDetailEntityForUniqueConstraintsLevel5(
                        salesTransactionTaxEntity, userId);

        System.out.println(result);
        System.out.println(expect);
        assertEquals(expect, result);
    }

    /**
     * ConvertTSalesTransactionErrorDetailEntityForUniqueConstraintsLevel5Discount.
     */
    @Test
    public void testConvertTSalesTransactionErrorDetailEntityForUniqueConstraintsLevel5Discount() {
        SalesTransactionDiscount salesTransactionDiscountEntity =
                ErrorDetailConverterTestHelper.makeTSalesTransactionDiscount();

        String userId = "userId12";
        TableCommonItem tableCommonItem = new TableCommonItem();
        LocalDateTime nowDateTime = LocalDateTime.of(2018, 02, 24, 11, 54, 32);
        tableCommonItem.setCreateUserId("user12");
        tableCommonItem.setCreateProgramId("SLS0300112");
        tableCommonItem.setCreateDatetime(nowDateTime);
        tableCommonItem.setUpdateUserId("user12");
        tableCommonItem.setUpdateProgramId("SLS0300112");
        tableCommonItem.setUpdateDatetime(nowDateTime);
        when(commonDataProcessor.getTableCommonItemForInsert(userId)).thenReturn(tableCommonItem);
        SalesTransactionErrorDetail expect =
                ErrorDetailConverterTestHelper.makeTSalesTransactionErrorDetail12();

        SalesTransactionErrorDetail result = errorDetailConverter
                .convertTSalesTransactionErrorDetailEntityForUniqueConstraintsLevel5(
                        salesTransactionDiscountEntity, userId);

        System.out.println(result);
        System.out.println(expect);
        assertEquals(expect, result);
    }

}
