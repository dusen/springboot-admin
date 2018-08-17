/**
 * @(#)TransactionDataValidatorTest.java
 *
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import com.fastretailing.dcp.storecommon.ValidationException;
import com.fastretailing.dcp.storecommon.message.SystemMessageSource;
import com.fastretailing.dcp.storecommon.transaction.dto.OrderDetailInfo;
import com.fastretailing.dcp.storecommon.transaction.dto.OrderInfo;

/**
 * TransactionDataValidator test class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionDataValidatorTest {

    /** Mock. */
    @MockBean
    private SystemMessageSource systemMessageSource;

    /** Test class. */
    @Autowired
    private TransactionDataValidator validator;

    /**
     * Test validation error.
     */
    @Test(expected = ValidationException.class)
    public void testValidateInValidThrowException() {
        OrderInfo o = new OrderInfo();
        validator.validate(o);
    }

    /**
     * Test validation success.
     */
    @Test
    public void testValid() {
        OrderInfo header = new OrderInfo();
        header.setBusinessDate("1");
        header.setPosNo("1");
        header.setReceiptNo("1");
        header.setSalesTime("1");
        header.setTransactionType("1");

        List<OrderDetailInfo> list = new ArrayList<>();
        OrderDetailInfo detail = new OrderDetailInfo();
        detail.setItemCode("1");
        detail.setItemSales(1);
        detail.setSalesId("1");
        detail.setSalesNum(2);
        detail.setUnitPrice(3);
        list.add(detail);
        header.setDetail(list);
        
        validator.validate(header);
    }
}
