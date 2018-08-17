/**
 * @(#)RestTemplateErrorHandler.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */
package com.fastretailing.dcp.common.api.client.interceptor;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.mock.http.client.MockClientHttpRequest;
import org.springframework.mock.http.client.MockClientHttpResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;

import java.io.IOException;
import java.net.URI;

/**
 * The unit test for RestTemplate's RestTemplateLoggerInterceptor.<br>
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@ContextHierarchy({
    @ContextConfiguration("classpath*:com/fastretailing/dcp/common/api/client/test-context.xml")
})
public class RestTemplateLoggerInterceptorTest {

    @InjectMocks
    private ApiRestTemplateInterceptor target = new ApiRestTemplateInterceptor();


    @Mock
    private Appender mockAppender;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        Mockito.when(mockAppender.getName()).thenReturn("MockAppender");
        Mockito.when(mockAppender.isStarted()).thenReturn(true);

        Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        logger.setLevel(Level.DEBUG);
        logger.addAppender(mockAppender);

    }

    @Test
    public void testIntercept() throws IOException {

        MockClientHttpRequest mockRequest = new MockClientHttpRequest(HttpMethod.PUT, URI.create("http://basket.oms.fastretailing.com/mock"));

        ClientHttpRequestExecution mockExecution = new ClientHttpRequestExecution() {
            @Override
            public ClientHttpResponse execute(HttpRequest httpRequest, byte[] bytes) throws IOException {
                MockClientHttpResponse mockResponse = new MockClientHttpResponse(new byte[]{}, HttpStatus.OK);
                return mockResponse;
            }
        };

        ClientHttpResponse actual = target.intercept(mockRequest, new byte[]{}, mockExecution);
        MatcherAssert.assertThat(actual.getStatusCode(), CoreMatchers.is(HttpStatus.OK));

        Mockito.verify(mockAppender, Mockito.times(16)).doAppend(Mockito.argThat(new ArgumentMatcher<ILoggingEvent>() {
            @Override
            public boolean matches(ILoggingEvent iLoggingEvent) {
                MatcherAssert.assertThat(iLoggingEvent.getLevel(), CoreMatchers.is(Level.INFO));
                return true;
            }
        }));

    }
}
