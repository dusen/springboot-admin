/**
 * @(#)AwsBackoffStrategy.java
 *
 *                             Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.util.aws;

import com.amazonaws.retry.PredefinedRetryPolicies;
import com.amazonaws.retry.RetryPolicy.BackoffStrategy;
import lombok.Getter;

/**
 * Back-off strategy setting of AWS-SDK.
 */
public enum AwsBackoffStrategy {

    /**
     * Default back-off strategy of AWS-SDK. There are dynamo db and other default settings.
     * 
     * @see PredefinedRetryPolicies.DEFAULT_BACKOFF_STRATEGY
     * @see PredefinedRetryPolicies.DYNAMODB_DEFAULT_BACKOFF_STRATEGY
     */
    DEFAULT(PredefinedRetryPolicies.DEFAULT_BACKOFF_STRATEGY,
            PredefinedRetryPolicies.DYNAMODB_DEFAULT_BACKOFF_STRATEGY),

    /**
     * Setting not to use back-off strategy.
     * 
     * @see BackoffStrategy.NO_DELAY
     */
    NO_DELAY(BackoffStrategy.NO_DELAY, BackoffStrategy.NO_DELAY);

    /**
     * Back-off strategy of SQS.
     */
    @Getter
    private BackoffStrategy sqsBackoffStrategy;

    /**
     * Back-off strategy of dynamo db.
     */
    @Getter
    private BackoffStrategy dynamoDbBackoffStrategy;

    /**
     * Set argument back-off strategy to field value.
     * 
     * @param sqsBackoffStrategy Back-off strategy of SQS.
     * @param dynamoDbBackoffStrategy Back-off strategy of dynamo db.
     */
    private AwsBackoffStrategy(BackoffStrategy sqsBackoffStrategy,
            BackoffStrategy dynamoDbBackoffStrategy) {
        this.sqsBackoffStrategy = sqsBackoffStrategy;
        this.dynamoDbBackoffStrategy = dynamoDbBackoffStrategy;
    }

}
