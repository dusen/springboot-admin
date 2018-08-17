/**
 * @(#)HmacAuthenticationJoinPointTest.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */
package com.fastretailing.dcp.common.mail;

import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.util.Objects;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({
        @ContextConfiguration(locations = {
                "classpath*:com/fastretailing/dcp/common/validation/test-context.xml"
        })
})
@Slf4j
public class OmsFreemarkConfigurationTest {

    @InjectMocks
    private OmsFreemarkConfiguration omsFreemarkConfiguration1;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * test keyGenerator
     */
    @Test
    public void testInitConfiguration() throws Exception {
        FreeMarkerConfigurer freeMarkerConfigurer = omsFreemarkConfiguration1.initConfiguration();

        assertThat(Objects.isNull(freeMarkerConfigurer), is(false));
    }

}
