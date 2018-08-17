/**
 * @(#)TransactionStatusDynamoRepositoryTest.java
 *
 *                                                Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import com.fastretailing.dcp.storecommon.message.SystemMessageSource;
import com.fastretailing.dcp.storecommon.transaction.ApplicationTest;
import com.fastretailing.dcp.storecommon.transaction.dto.TransactionStatus;

/**
 * TransactionDynamoRepository test class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationTest.class)
public class TransactionStatusDynamoRepositoryTest {

    @MockBean
    private SystemMessageSource systemMessageSource;

    /** DynamoDB repository. */
    @Autowired
    private TransactionStatusDynamoRepository repository;

    /**
     * Save method test.
     */
    @Test
    public void testSave() {

        TransactionStatus info = new TransactionStatus();
        info.setTransactionId(
                String.valueOf(System.currentTimeMillis()) + UUID.randomUUID().toString());
        info.setBusinessStatus("businessstatus");
        info.setExpirationTime(Instant.now().plus(2, ChronoUnit.MINUTES).getEpochSecond());
        TransactionStatus result = repository.save(info);

        assertNotNull(result);
        assertEquals(info.getTransactionId(), result.getTransactionId());
        assertEquals(info.getBusinessStatus(), result.getBusinessStatus());
        assertEquals(info.getExpirationTime(), result.getExpirationTime());

        repository.delete(result);
    }

    /**
     * Save method test.
     */
    @Test
    public void testTTL() {

        TransactionStatus info = new TransactionStatus();
        info.setTransactionId(
                String.valueOf(System.currentTimeMillis()) + UUID.randomUUID().toString());
        info.setBusinessStatus("businessstatus");
        info.setExpirationTime(Instant.now().plus(2, ChronoUnit.MINUTES).getEpochSecond());
        repository.save(info);
    }
}
