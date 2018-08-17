/**
 * @(#)OmsAmazonCloudWatchClientConfigurationTest.java
 *
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.aws.cloudwatch;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.MapPropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.amazonaws.services.cloudwatch.AmazonCloudWatch;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({
        @ContextConfiguration("classpath*:com/fastretailing/dcp/common/aws/cloudwatch/test-context.xml")
})
@Slf4j
public class OmsAmazonCloudWatchClientConfigurationTest {

    OmsAmazonCloudWatchClientConfiguration clientConfiguration = new OmsAmazonCloudWatchClientConfiguration();

    @Autowired
    private ConfigurableApplicationContext context;

    @PostConstruct
    public void setUp() throws Exception {
        Map<String, Object> uriMap = new HashMap<>();
        uriMap.put("cloud.aws.cloudwatch.static", "anything");

        MapPropertySource propertySource = new MapPropertySource("uri", uriMap);

        context.getEnvironment().getPropertySources().addLast(propertySource);

        ReflectionTestUtils.setField(clientConfiguration, "accessKey", "accessKey");
        ReflectionTestUtils.setField(clientConfiguration, "secretKey", "secretKey");
        ReflectionTestUtils.setField(clientConfiguration, "serviceEndpoint", "serviceEndpoint");
        ReflectionTestUtils.setField(clientConfiguration, "signingRegion", "signingRegion");

    }

    /**
     * check whether the Configuration is useful.
     */
    @Test
    public void initAmazonCloudWatchTest() {

        log.debug("clientConfiguration.isValid01");
        AmazonCloudWatch cloudWatch = clientConfiguration.initAmazonCloudWatch();

        Assert.assertNotNull(cloudWatch);
    }

    /**
     * check whether the accessKey is null.
     */
    @Test
    public void initAmazonCloudWatchWithoutCredentialsTest() {

        ReflectionTestUtils.setField(clientConfiguration, "accessKey", null);
        ReflectionTestUtils.setField(clientConfiguration, "secretKey", null);

        log.debug("clientConfiguration.isValid01");
        AmazonCloudWatch cloudWatch = clientConfiguration.initAmazonCloudWatch();

        Assert.assertNotNull(cloudWatch);
    }
}
