/**
 * @(#)DynamoDbConfigration.java
 *
 *                               Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction.config;

import org.apache.commons.lang3.StringUtils;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.retry.PredefinedRetryPolicies;
import com.amazonaws.retry.RetryPolicy;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsync;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverterFactory;
import com.amazonaws.services.dynamodbv2.model.DescribeTimeToLiveRequest;
import com.amazonaws.services.dynamodbv2.model.TimeToLiveSpecification;
import com.amazonaws.services.dynamodbv2.model.TimeToLiveStatus;
import com.amazonaws.services.dynamodbv2.model.UpdateTimeToLiveRequest;

/**
 * This class is configure to connect AmazonDynamoDB.
 */
@Configuration
@EnableDynamoDBRepositories(dynamoDBMapperConfigRef = "dynamoDBMapperConfig",
        basePackages = "com.fastretailing.dcp.storecommon.transaction.repository")
public class DynamoDbConfigration {

    /** AWS region for Dynamo DB. */
    @Value("${transaction-dispatch.dynamodb.region}")
    private String region;

    /** Transaction table name. */
    @Value("${transaction-dispatch.dynamodb.transaction-table:#{null}}")
    private String transactionTable;

    /** Transaction status table name. */
    @Value("${transaction-dispatch.dynamodb.transaction-status-table:#{null}}")
    private String transactionStatusTable;

    /** DynamoDB time to live attribute name. */
    private String TIME_TO_LIVE_ATTRIBUTE = "ExpirationTime";

    /**
     * The environment in which the current application is running.
     */
    @Autowired
    private Environment env;

    /**
     * Setting of AWS-SDK when using dynamo db.
     */
    @Autowired
    private DynamoDbClientConfiguration dynamoDbClientConfiguration;

    /**
     * Create AmazonDynamoDB connection configuration. Apply the dynamo db timeout and retry setting
     * of the configuration file.
     * 
     * @return AmazonDynamoDBAsync.
     */
    @Bean
    public AmazonDynamoDBAsync amazonDynamoDB() {
        AmazonDynamoDBAsync amazonDynamoDbAsync = AmazonDynamoDBAsyncClientBuilder.standard()
                .withRegion(region)
                .withClientConfiguration(new ClientConfiguration()
                        .withConnectionTimeout(dynamoDbClientConfiguration.getConnectionTimeout())
                        .withRequestTimeout(dynamoDbClientConfiguration.getRequestTimeout())
                        .withMaxErrorRetry(dynamoDbClientConfiguration.getMaxErrorRetry())
                        .withRetryPolicy(
                                new RetryPolicy(PredefinedRetryPolicies.DEFAULT_RETRY_CONDITION,
                                        dynamoDbClientConfiguration.getBackoffStrategy()
                                                .getDynamoDbBackoffStrategy(),
                                        dynamoDbClientConfiguration.getMaxErrorRetry(), true)))
                .build();

        if (StringUtils.isNotEmpty(transactionTable)) {
            enableTimeToLive(amazonDynamoDbAsync, transactionTable);
        }

        if (StringUtils.isNotEmpty(transactionStatusTable)) {
            enableTimeToLive(amazonDynamoDbAsync, transactionStatusTable);
        }

        return amazonDynamoDbAsync;
    }

    /**
     * Create DynamoDBMapper configuration. Get table name from properties file.
     * 
     * @return DynamoDBMapperConfig.
     */
    @Bean
    public DynamoDBMapperConfig dynamoDBMapperConfig() {
        // Create basic DynamoDBMapperConfig builder
        DynamoDBMapperConfig.Builder builder = new DynamoDBMapperConfig.Builder();
        // Get table name from properties file.
        builder.setTableNameResolver((clazz, config) -> {
            DynamoDBTable annotationDynamoDBTable = clazz.getAnnotation(DynamoDBTable.class);
            String annotedTableName = annotationDynamoDBTable.tableName();
            String tableName = env.getProperty(annotedTableName);
            if (StringUtils.isEmpty(tableName)) {
                tableName = annotedTableName;
            }
            return tableName;
        });
        builder.setTypeConverterFactory(DynamoDBTypeConverterFactory.standard());
        return builder.build();
    }

    /**
     * Enable dyanmoDB's time to live, if not being enabled.
     * 
     * @param dynamoDB Amazon DynamoDB client.
     * @param tableName Table name.
     */
    private void enableTimeToLive(AmazonDynamoDB dynamoDB, String tableName) {

        DescribeTimeToLiveRequest describeTimeToLiveRequest =
                new DescribeTimeToLiveRequest().withTableName(tableName);
        String timeToLiveStatus = dynamoDB.describeTimeToLive(describeTimeToLiveRequest)
                .getTimeToLiveDescription()
                .getTimeToLiveStatus();
        if (TimeToLiveStatus.DISABLED.toString().equals(timeToLiveStatus)) {
            TimeToLiveSpecification timeToLiveSpecification =
                    new TimeToLiveSpecification().withAttributeName(TIME_TO_LIVE_ATTRIBUTE)
                            .withEnabled(true);
            UpdateTimeToLiveRequest updateTimeToLiveRequest =
                    new UpdateTimeToLiveRequest().withTableName(tableName)
                            .withTimeToLiveSpecification(timeToLiveSpecification);
            dynamoDB.updateTimeToLive(updateTimeToLiveRequest);
        }
    }
}
