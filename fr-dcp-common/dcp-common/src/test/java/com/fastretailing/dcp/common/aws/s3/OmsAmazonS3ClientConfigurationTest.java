/**
 * @(#)CombinationRequireTest.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.aws.s3;

import com.amazonaws.services.s3.AmazonS3;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({
        @ContextConfiguration("classpath*:com/fastretailing/dcp/common/aws/s3/test-context.xml")
})
@Slf4j
public class OmsAmazonS3ClientConfigurationTest {


    OmsAmazonS3ClientConfiguration clientConfiguration = new OmsAmazonS3ClientConfiguration();

    @Autowired
    private ConfigurableApplicationContext context;

    @PostConstruct
    public void setUp() throws Exception {
        Map<String, Object> uriMap = new HashMap<>();
        uriMap.put("cloud.aws.region.static", "anything");

        MapPropertySource propertySource = new MapPropertySource("uri", uriMap);

        context.getEnvironment().getPropertySources().addLast(propertySource);

        ReflectionTestUtils.setField(clientConfiguration, "accessKey", "accessKey");
        ReflectionTestUtils.setField(clientConfiguration, "secretKey", "secretKey");
        ReflectionTestUtils.setField(clientConfiguration, "serviceEndpoint", "serviceEndpoint");
        ReflectionTestUtils.setField(clientConfiguration, "signingRegion", "signingRegion");

    }


    /**
     * check whether the target field size is 0
     */
    @Test
    public void initAmazonS3Test() {

        log.debug("clientConfiguration.isValid01");
        AmazonS3 amazonS3 = clientConfiguration.initAmazonS3();

        Assert.assertNotNull(amazonS3);
    }

    /**
     * check whether the target field size is 0
     */
    @Test
    public void initAmazonS3WithoutCredentialsTest() {

        ReflectionTestUtils.setField(clientConfiguration, "accessKey", null);
        ReflectionTestUtils.setField(clientConfiguration, "secretKey", null);

        log.debug("clientConfiguration.isValid01");
        AmazonS3 amazonS3 = clientConfiguration.initAmazonS3();

        Assert.assertNotNull(amazonS3);
    }
}
