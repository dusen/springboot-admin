/**
 * @(#)KafkaConfigurationTest.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.kafka;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * test class of CommonConsumer.
 * 
 * @author Fast Retailing
 * @version $Revision$
 */
@SuppressWarnings("restriction")
public class KafkaConfigurationTest {

    @InjectMocks
    private KafkaConfiguration kafkaConfiguration = new KafkaConfiguration();

    @Mock
    private KafkaProperties kafkaProperties;

    /**
     * Initialize test.<br>
     * @throws Exception any exception
     */
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(kafkaConfiguration, "kafkaProperties", kafkaProperties);
    }

    @Test
    public void test1() throws Exception {
        assertThat(Objects.isNull(kafkaConfiguration.idempotentProducer()), is(false));
    }

    @Test
    public void test2() throws Exception {
        assertThat(Objects.isNull(kafkaConfiguration.kafkaTemplate()), is(false));
    }

    @Test
    public void test3() throws Exception {
        Mockito.when(kafkaProperties.getTransactionIdPrefix()).thenReturn("");
        assertThat(Objects.isNull(kafkaConfiguration.kafkaTransactionManager()), is(false));
    }

    @Test
    public void test4() throws Exception {
        assertThat(Objects.isNull(kafkaConfiguration.transactionalProducer()), is(false));
    }

    @Test
    public void test5() throws Exception {
        Mockito.when(kafkaProperties.getTransactionIdPrefix()).thenReturn("");
        assertThat(Objects.isNull(kafkaConfiguration.transactionKafkaTemplate()), is(false));
    }
}
