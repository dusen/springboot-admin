/**
 * @(#)SqsMessageReceiverConfiguration.java
 *
 *                                          Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction.businessreceiver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.config.SimpleMessageListenerContainerFactory;
import org.springframework.cloud.aws.messaging.config.annotation.EnableSqs;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.retry.PredefinedRetryPolicies;
import com.amazonaws.retry.RetryPolicy;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;

/**
 * This class is a business reception class that accepts sales transaction data and registers it in
 * the sales tables.
 */
@Configuration
@EnableSqs
public class SqsMessageReceiverConfiguration {

    /** Project configuration. */
    @Autowired
    private BusinessReceiverConfiguration businessReceiverConfiguration;

    /**
     * Setting of AWS-SDK when using SQS.
     */
    @Autowired
    private SqsClientConfiguration sqsClientConfiguration;

    /**
     * Configuration of connection to SQS. Apply the SQS timeout and retry setting of the
     * configuration file.
     * 
     * @return amazonSqs Parts for connect to SQS.
     */
    @Bean
    public AmazonSQSAsync amazonSqs() {
        AmazonSQSAsync amazonSqs = AmazonSQSAsyncClientBuilder.standard()
                .withRegion(businessReceiverConfiguration.getAwsRegion())
                .withClientConfiguration(new ClientConfiguration()
                        .withConnectionTimeout(sqsClientConfiguration.getConnectionTimeout())
                        .withRequestTimeout(sqsClientConfiguration.getRequestTimeout())
                        .withMaxErrorRetry(sqsClientConfiguration.getMaxErrorRetry())
                        .withRetryPolicy(
                                new RetryPolicy(PredefinedRetryPolicies.DEFAULT_RETRY_CONDITION,
                                        sqsClientConfiguration.getBackoffStrategy()
                                                .getSqsBackoffStrategy(),
                                        sqsClientConfiguration.getMaxErrorRetry(), true)))
                .build();
        return amazonSqs;
    }

    /**
     * Configuration of SQS message listener.
     * 
     * @return Listener factory.
     */
    @Bean
    public SimpleMessageListenerContainerFactory simpleMessageListenerContainerFactory() {
        SimpleMessageListenerContainerFactory factory = new SimpleMessageListenerContainerFactory();
        factory.setAmazonSqs(amazonSqs());
        factory.setMaxNumberOfMessages(businessReceiverConfiguration.getMaxNumberOfMessage());
        factory.setWaitTimeOut(businessReceiverConfiguration.getWaitTimeOutSeconds());
        return factory;
    }
}
