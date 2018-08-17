/**
 * @(#)TransactionDynamoRepositoryTest.java
 *
 *                                          Copyright (c) 2018 Fast Retailing Corporation.
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
import com.fastretailing.dcp.storecommon.transaction.dto.ReceivedTransaction;

/**
 * TransactionDynamoRepository test class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationTest.class)
public class TransactionDynamoRepositoryTest {

    @MockBean
    private SystemMessageSource systemMessageSource;

    /** DynamoDB repository. */
    @Autowired
    private TransactionDynamoRepository repository;

    /**
     * Save method test.
     */
    @Test
    public void testSave() {

        ReceivedTransaction info = new ReceivedTransaction();
        info.setRegistrationDate("20180328T120000Z");
        info.setTransactionId(
                String.valueOf(System.currentTimeMillis()) + UUID.randomUUID().toString());
        info.setTransactionData("{\"key\":\"value\"}");
        info.setExpirationTime(Instant.now().plus(2, ChronoUnit.MINUTES).getEpochSecond());
        ReceivedTransaction result = repository.save(info);

        assertNotNull(result);
        assertEquals(info.getRegistrationDate(), result.getRegistrationDate());
        assertEquals(info.getTransactionId(), result.getTransactionId());
        assertEquals(info.getTransactionData(), result.getTransactionData());
        assertEquals(info.getExpirationTime(), result.getExpirationTime());

        repository.delete(result);
    }

    /**
     * Save method test.
     */
    @Test
    public void testExpiration() {

        ReceivedTransaction info = new ReceivedTransaction();
        info.setRegistrationDate("20180328T120000Z");
        info.setTransactionId(
                String.valueOf(System.currentTimeMillis()) + UUID.randomUUID().toString());
        info.setTransactionData("{\"key\":\"value\"}");
        info.setExpirationTime(Instant.now().plus(2, ChronoUnit.MINUTES).getEpochSecond());
        ReceivedTransaction result = repository.save(info);

        assertNotNull(result);
        assertEquals(info.getRegistrationDate(), result.getRegistrationDate());
        assertEquals(info.getTransactionId(), result.getTransactionId());
        assertEquals(info.getTransactionData(), result.getTransactionData());
        assertEquals(info.getExpirationTime(), result.getExpirationTime());
    }
}
