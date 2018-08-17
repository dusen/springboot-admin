/**
 * @(#)BusinessProcessorPartitionServiceTest.java
 *
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction.businessprocessor.service;

import static org.junit.Assert.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import com.fastretailing.dcp.storecommon.transaction.businessprocessor.config.BusinessProcessorConfiguration;
import com.fastretailing.dcp.storecommon.transaction.businessprocessor.repository.BusinessProcessorStatusMapper;

/**
 * BusinessProcessorPartitionCalculationService test class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@SpringBootApplication(scanBasePackages = {
        "com.fastretailing.dcp.storecommon.transaction.businessprocessor.service"})
public class BusinessProcessorPartitionServiceTest {

    /** Mock. */
    @MockBean
    private BusinessProcessorStatusMapper mapper;

    /** Mock. */
    @MockBean
    private BusinessProcessorConfiguration config;

    /** Partition count. */
    @Value("${transaction-dispatch.business-processor.partitioning.total-count}")
    private int partitioningTotalCount;

    /** Test class. */
    @Autowired
    private BusinessProcessorPartitionService service;

    /**
     * 
     */
    @Test
    public void testValid() {

        // // 0
        // int i = service.calculationPartitionNo("0");
        // System.out.println("0:i = " + i);
        // assertTrue(0 <= i && i <= partitioningTotalCount - 1);
        //
        // // 1
        // i = service.calculationPartitionNo("1");
        // System.out.println("1:i = " + i);
        // assertTrue(0 <= i && i <= partitioningTotalCount - 1);
        //
        // // 2
        // i = service.calculationPartitionNo("2");
        // System.out.println("2:i = " + i);
        // assertTrue(0 <= i && i <= partitioningTotalCount - 1);
        //
        // // 3
        // i = service.calculationPartitionNo("3");
        // System.out.println("3:i = " + i);
        // assertTrue(0 <= i && i <= partitioningTotalCount - 1);

        // System.out.println(String.valueOf(System.currentTimeMillis()).length());

//        int ret =
//                service.calculationPartitionNo("15123797261323d2372ca-9a86-49ce-9bb4-94847c77cbbd");
//        System.out.println("ret = " + ret);
//        assertTrue(0 <= ret && ret <= partitioningTotalCount - 1);

        Map<Long, Integer> map = new HashMap<Long, Integer>();

        for (int i = 0; i < 1000000; i++) {
            long x = service.calculationPartitionNo("0123456789123" + UUID.randomUUID().toString());

            if (!map.containsKey(x)) {
                map.put(x, 1);
            } else {
                int value = map.get(x);
                value++;
                map.put(x, value);
            }

            assertTrue(0 <= x && x <= partitioningTotalCount - 1);
        }

        map.forEach((key, value) -> {
            System.out.println(String.format("key = %d, value = %d", key, value));
        });
    }
}
