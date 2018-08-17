/**
 * @(#)KafkaRetryTemplateFactory.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

/**
 * Kafka's RetryTemplate's factory bean.<br>
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Component
@Slf4j
public class KafkaRetryTemplateFactory {

    /**
     * Generic Kafka's RetryTemplate's instance.<br>
     * @param retryCount retry count.
     * @param retryInterval retry interval.
     * @return RetryTemplate's instance
     */
    public RetryTemplate buildTemplate(int retryCount, int retryInterval) {

        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy(retryCount + 1);

        FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
        backOffPolicy.setBackOffPeriod(retryInterval);

        RetryTemplate template = new RetryTemplate();
        template.setRetryPolicy(retryPolicy);
        template.setBackOffPolicy(backOffPolicy);

        return template;
    }




}
