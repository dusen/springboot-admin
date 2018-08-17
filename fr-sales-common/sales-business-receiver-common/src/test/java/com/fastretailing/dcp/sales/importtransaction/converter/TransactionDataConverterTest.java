package com.fastretailing.dcp.sales.importtransaction.converter;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import com.fastretailing.dcp.sales.importtransaction.dto.TransactionImportData;

/**
 * TransactionDataConverter JUnit Test class.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TransactionDataConverterTest {

    @Autowired
    private TransactionDataConverter transactionDataConverter;

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
     * ConvertTransactionImportData.
     */
    @Test
    public void testConvertTransactionImportData() {
        TransactionImportData importData = TransactionDataConverterTestHelper.makeImportData();

        TransactionImportData expect =
                TransactionDataConverterTestHelper.makeTransactionImportData();

        transactionDataConverter.convertTransactionImportData(importData);

        System.out.println(importData);
        System.out.println(expect);
        assertEquals(expect, importData);
    }

    @Test
    public void testDiscountPromotionTypeAndDiscountTypeNull() {
        TransactionImportData importData = TransactionDataConverterTestHelper.makeImportData();
        importData.getTransactionList().forEach(transaction -> {
            transaction.getTransactionItemDetailList().forEach(itemDetail -> {
                itemDetail.getItemDiscountList().forEach(itemDiscount -> {
                    itemDiscount.setItemPromotionNumber(null);
                    itemDiscount.setItemStoreDiscountType(null);
                });
                itemDetail.getNonItemDetailListByItem().forEach(nonItemDetail -> {
                    nonItemDetail.getNonItemDiscountDetailList().forEach(nonItemDiscountDetail -> {
                        nonItemDiscountDetail.setNonItemPromotionNumber(null);
                        nonItemDiscountDetail.setNonItemStoreDiscountType(null);
                    });
                });
            });
            transaction.getNonItemDetailList().forEach(nonItemDetail -> {
                nonItemDetail.getNonItemDiscountDetailList().forEach(nonItemDiscount -> {
                    nonItemDiscount.setNonItemPromotionNumber(null);
                    nonItemDiscount.setNonItemStoreDiscountType(null);
                });
            });
        });

        TransactionImportData expect =
                TransactionDataConverterTestHelper.makeTransactionImportData();
        expect.getTransactionList().forEach(transaction -> {
            transaction.getTransactionItemDetailList().forEach(itemDetail -> {
                itemDetail.getItemDiscountList().forEach(itemDiscount -> {
                    itemDiscount.setItemPromotionNumber("0");
                    itemDiscount.setItemStoreDiscountType("0");
                });
                itemDetail.getNonItemDetailListByItem().forEach(nonItemDetail -> {
                    nonItemDetail.getNonItemDiscountDetailList().forEach(nonItemDiscountDetail -> {
                        nonItemDiscountDetail.setNonItemPromotionNumber("0");
                        nonItemDiscountDetail.setNonItemStoreDiscountType("0");
                    });
                });
            });
            transaction.getNonItemDetailList().forEach(nonItemDetail -> {
                nonItemDetail.getNonItemDiscountDetailList().forEach(nonItemDiscount -> {
                    nonItemDiscount.setNonItemPromotionNumber("0");
                    nonItemDiscount.setNonItemStoreDiscountType("0");
                });
            });
        });

        transactionDataConverter.convertTransactionImportData(importData);

        System.out.println(importData);
        System.out.println(expect);
        assertEquals(expect, importData);
    }

    /**
     * ChangeToDbFormatter.
     */
    @Test
    public void testChangeToDbFormatter() {
        String orderDate = "2018-12-18";
        String expect = "20181218";

        String result = transactionDataConverter.changeToDbFormatter(orderDate);

        assertEquals(expect, result);
    }

    @Test
    public void testFormatterEmpty() {
        String result = transactionDataConverter.changeFormatter("");

        assertEquals(null, result);
    }

    /**
     * ChangeFormatter.
     */
    @Test
    public void testChangeFormatter() {
        String dbDate = "20191130";
        String expect = "2019-11-30";

        String result = transactionDataConverter.changeFormatter(dbDate);

        assertEquals(expect, result);
    }

}
