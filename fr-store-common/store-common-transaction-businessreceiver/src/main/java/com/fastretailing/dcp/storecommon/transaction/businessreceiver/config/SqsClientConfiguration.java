/**
 * @(#)SqsClientConfiguration.java
 *
 *                                 Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction.businessreceiver.config;

import javax.validation.constraints.Min;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import com.fastretailing.dcp.storecommon.util.aws.AwsBackoffStrategy;
import lombok.Data;

/**
 * Class with sqs timeout and retry setting.
 */
@Component
@ConfigurationProperties(prefix = "transaction-dispatch.sqs")
@Data
@Validated
public class SqsClientConfiguration {
    /**
     * Connection timeout value on sqs connection.
     */
    private int connectionTimeout;

    /**
     * Request timeout value on sqs connection.
     */
    private int requestTimeout;

    /**
     * Back-off strategy setting of AWS-SDK.
     */
    private AwsBackoffStrategy backoffStrategy;

    /**
     * The maximum number of retry attempts.
     */
    @Min(0)
    private int maxErrorRetry;
}
