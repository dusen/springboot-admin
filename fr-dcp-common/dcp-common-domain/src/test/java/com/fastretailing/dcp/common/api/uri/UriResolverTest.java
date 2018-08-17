/**
 * @(#)UriResolverTest.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.api.uri;

import com.fastretailing.dcp.common.api.jvm.OmsJvmParameters;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.MapPropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({
        @ContextConfiguration("classpath*:com/fastretailing/dcp/common/api/uri/test-context.xml")
})
@Slf4j
public class UriResolverTest {

    private static UriResolver uriResolver;

    @Autowired
    private ConfigurableApplicationContext context;

    @Before
    public void setUp() throws Exception {
        uriResolver = new UriResolver();

        Map<String, Object> uriMap = new HashMap<>();
        uriMap.put("client.apiConfig.basket.uri.apis.user.relative.get.admin", "http://admin.basket.oms.fastretailing.com:8888/test/getInfo");
        uriMap.put("client.apiConfig.basket.uri.apis.user.relative.get.consumer", "http://consumer.basket.oms.fastretailing.com:8888/test/getInfo");

        MapPropertySource propertySource = new MapPropertySource("uri", uriMap);

        context.getEnvironment().getPropertySources().addLast(propertySource);

        ReflectionTestUtils.setField(uriResolver, "applicationContext", context);
    }

    @Test
    public void adminTest01()  {
        try {

            OmsJvmParameters omsJvmParameters = new OmsJvmParameters();

            ReflectionTestUtils.setField(omsJvmParameters, "isAdminApi", true);

            String adminUri = uriResolver.getUriAccordingToServerType("client.apiConfig.basket.uri.apis.user.relative.get");

            assertEquals("http://admin.basket.oms.fastretailing.com:8888/test/getInfo", adminUri);

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public  void frontTest01()  {
        try {


            OmsJvmParameters omsJvmParameters = new OmsJvmParameters();

            ReflectionTestUtils.setField(omsJvmParameters, "isAdminApi", false);

            String adminUri = uriResolver.getUriAccordingToServerType("client.apiConfig.basket.uri.apis.user.relative.get");

            assertEquals("http://consumer.basket.oms.fastretailing.com:8888/test/getInfo", adminUri);

        } catch (Exception e) {
            fail();
        }
    }

}
