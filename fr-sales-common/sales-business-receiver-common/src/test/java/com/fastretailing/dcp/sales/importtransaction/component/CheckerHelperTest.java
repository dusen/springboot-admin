package com.fastretailing.dcp.sales.importtransaction.component;

import java.lang.reflect.Field;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionErrorDetail;
import com.fastretailing.dcp.sales.importtransaction.exception.TransactionConsistencyErrorException;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CheckerHelperTest {

    @Test
    public void testValidationLevel1Required() throws Exception {
        SalesTransactionErrorDetail salesTransactionErrorDetail = new SalesTransactionErrorDetail();
        salesTransactionErrorDetail.setStoreCode("111111");
        salesTransactionErrorDetail.setSalesTransactionErrorId("ErrorId");

        CheckerHelper checkerHelper = new CheckerHelper();
        Class<CheckerHelper> helper = CheckerHelper.class;
        Field field = helper.getDeclaredField("transactionErrorThrowExceptionFlag");
        field.setAccessible(true);
        field.set(checkerHelper, true);

        try {
            checkerHelper.insertSalesTransactionError(salesTransactionErrorDetail);
        } catch (TransactionConsistencyErrorException e) {
            Assert.assertEquals(e.getEntity().getStoreCode(), "111111");
            Assert.assertEquals(e.getEntity().getSalesTransactionErrorId(), "ErrorId");
        }
    }
}
