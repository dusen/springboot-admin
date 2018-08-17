/**
 * @(#)DynamoDbConfigrationZeroTest.java
 *
 *                                       Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction.config;

import static org.junit.Assert.assertEquals;
import java.lang.reflect.Field;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ReflectionUtils;
import com.amazonaws.AmazonWebServiceClient;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.retry.PredefinedRetryPolicies;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsync;
import com.fastretailing.dcp.storecommon.message.SystemMessageSource;
import com.fastretailing.dcp.storecommon.transaction.ApplicationTest;

/**
 * Test class when timeout value of dynamo db is zero.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationTest.class)
@ActiveProfiles("zero")
public class DynamoDbConfigrationZeroTest {

    /**
     * Mock of message acquisition parts.
     */
    @MockBean
    private SystemMessageSource systemMessageSource;

    /**
     * Object with dynamo db client configuration information.
     */
    @Autowired
    private AmazonDynamoDBAsync amazonDynamoDBAsync;

    /**
     * Check if the AWS-SDK value set in the setting file matches the actual AWS-SDK Client setting
     * value.
     * 
     * <ul>
     * <li>connection-timeout: 0
     * <li>request-timeout: 0
     * <li>backoff-strategy: DYNAMODB_DEFAULT_BACKOFF_STRATEGY("DEFAULT" in the setting file.)
     * <li>max-error-retry: 1
     * </ul>
     * 
     * @throws SecurityException Thrown by the security manager to indicate a security violation.
     * @throws NoSuchFieldException Signals that the class doesn't have a field of a specified name.
     */
    @Test
    public void testTimeoutValueZero() throws NoSuchFieldException, SecurityException {
        // Get the actual AWS-SDK client settings.
        Field field = AmazonWebServiceClient.class.getDeclaredField("clientConfiguration");
        field.setAccessible(true);
        ClientConfiguration clientConfiguration = ClientConfiguration.class
                .cast(ReflectionUtils.getField(field, amazonDynamoDBAsync));

        // Check that the setting of application-minus.yml matches the setting value of AWS-SDK
        // client.
        assertEquals(0, clientConfiguration.getConnectionTimeout());
        assertEquals(0, clientConfiguration.getRequestTimeout());
        assertEquals(PredefinedRetryPolicies.DYNAMODB_DEFAULT_BACKOFF_STRATEGY,
                clientConfiguration.getRetryPolicy().getBackoffStrategy());
        assertEquals(1, clientConfiguration.getMaxErrorRetry());
    }
}
