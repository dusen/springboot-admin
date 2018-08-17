/**
 * @(#)ResponseHeaderInterceptor.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.web.interceptor;

import com.fastretailing.dcp.common.api.log.MdcKeyEnum;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.http.HttpServletResponse;

/**
 * ResponseHeaderInterceptor's testIfHasBodySetLanguageIntoHeader class.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({
    @ContextConfiguration(locations = {
        "classpath*:com/fastretailing/dcp/common/web/test-context.xml"
    })
})
public class ResponseHeaderInterceptorTest {

    @InjectMocks
    private ResponseHeaderInterceptor target = new ResponseHeaderInterceptor();

    @Spy
    private ContentCachingResponseWrapper mockResponse = new ContentCachingResponseWrapper(new MockHttpServletResponse());

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testIfHasBodySetLanguageIntoHeader() throws Exception {

        Mockito.when(mockResponse.getContentSize()).thenReturn(100);
        MDC.put(MdcKeyEnum.AMAZON_TRACE_ID.getKey(), "ab-cd-ef-gh");

        target.postHandle(null, mockResponse, null, null);

        MatcherAssert.assertThat(mockResponse.getHeader(HttpHeaders.CONTENT_LANGUAGE), CoreMatchers.is("en-US"));
        MatcherAssert.assertThat(mockResponse.getHeader(MdcKeyEnum.AMAZON_TRACE_ID.getKey()), CoreMatchers.is("Root=ab-cd-ef-gh"));

    }

    @Test
    public void testIfHasNotBodyNotSetLanguageIntoHeader() throws Exception {

        Mockito.when(mockResponse.getContentSize()).thenReturn(0);

        target.postHandle(null, mockResponse, null, null);

        MatcherAssert.assertThat(mockResponse.getHeader(HttpHeaders.CONTENT_LANGUAGE), CoreMatchers.nullValue());

    }

    @Test
    public void testIfHasNotSetLanguageIntoHeader() throws Exception {

        target.postHandle(null, (HttpServletResponse) mockResponse.getResponse(), null, null);

        MatcherAssert.assertThat(mockResponse.getHeader(HttpHeaders.CONTENT_LANGUAGE), CoreMatchers.nullValue());

    }

}
