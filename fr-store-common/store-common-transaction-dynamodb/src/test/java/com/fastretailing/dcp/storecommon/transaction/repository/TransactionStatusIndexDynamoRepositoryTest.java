/**
 * @(#)TransactionStatusIndexDynamoRepositoryTest.java
 *
 *                                                     Copyright (c) 2018 Fast Retailing
 *                                                     Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import com.fastretailing.dcp.storecommon.message.SystemMessageSource;
import com.fastretailing.dcp.storecommon.transaction.ApplicationTest;
import com.fastretailing.dcp.storecommon.transaction.dto.TransactionStatus;
import com.fastretailing.dcp.storecommon.transaction.dto.TransactionStatusIndex;
import lombok.extern.slf4j.Slf4j;

/**
 * TransactionDynamoRepository test class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationTest.class)
@Slf4j
public class TransactionStatusIndexDynamoRepositoryTest {

    @MockBean
    private SystemMessageSource systemMessageSource;

    /** DynamoDB repository. */
    @Autowired
    private TransactionStatusIndexDynamoRepository statusIndexRepository;

    /** DynamoDB repository. */
    @Autowired
    private TransactionStatusDynamoRepository satusRepository;

    /**
     * Find by date method test.
     */
    @Test
    public void testFindByDate() {

        // Find.
        List<TransactionStatusIndex> resultList =
                statusIndexRepository.findByRegistrationDate("201712250713");

        // Assert.
        assertNotNull(resultList);

        log.info("resultList.size={}", resultList.size());
        for (TransactionStatusIndex index : resultList) {
            log.info("index={}", index);
        }

        Set<TransactionStatusIndex> uniqueResultSet = new LinkedHashSet<>(resultList);

        log.info("uniqueResultList.size={}", uniqueResultSet.size());
        for (TransactionStatusIndex index : uniqueResultSet) {
            log.info("unique index={}", index);
        }
    }

    /**
     * Find between dates method test.
     */
    @Test
    public void findByRegistrationDateBetween() {

        List<TransactionStatus> savedResult = IntStream.rangeClosed(1, 10).boxed().map(i -> {
            TransactionStatus info = new TransactionStatus();
            info.setTransactionId("findByRegistrationDateBetween_id" + i);
            info.setBusinessStatus("findByRegistrationDateBetween_status" + i);
            info.setRegistrationDate(String.valueOf(201808010000l + i));
            info.setExpirationTime(Instant.now().plus(2, ChronoUnit.MINUTES).getEpochSecond());
            return satusRepository.save(info);
        }).collect(Collectors.toList());

        try {
            List<TransactionStatusIndex> statusList = statusIndexRepository
                    .findByRegistrationDateBetween("201808010005", "201808010010");

            assertThat(statusList.size(), is(6));
        } finally {
            savedResult.forEach(result -> satusRepository.delete(result));
        }
    }

}
