/**
 * @(#)DynamoDbClientConfiguration.java
 *
 *                                      Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction.config;

import javax.validation.constraints.Min;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import com.fastretailing.dcp.storecommon.util.aws.AwsBackoffStrategy;
import lombok.Data;

/**
 * Class with DynamoDb timeout and retry setting.
 */
@Component
@ConfigurationProperties(prefix = "transaction-dispatch.dynamodb")
@Validated
@Data
public class DynamoDbClientConfiguration {
    /**
     * Connection timeout value on dynamo db connection.
     */
    private int connectionTimeout;

    /**
     * Request timeout value on dynamo db connection.
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
