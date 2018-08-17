/**
 * @(#)RestTemplateInterceptorTest.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.client.interceptor;

import java.io.IOException;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;

import com.fastretailing.dcp.common.client.customization.CustomizationBufferingClientHttpResponseWrapper;

/**
 * RestTemplateInterceptorTest.<br>
 *
 * @author Fast Retailing
 * @version $Revision$
 */
public class RestTemplateInterceptorTest {

    private RestTemplateInterceptor interceptor = new RestTemplateInterceptor();

    @Mock
    HttpRequest request;

    @Mock
    ClientHttpRequestExecution execution;

    @Mock
    ClientHttpResponse response;

    @Test
    public void intercept01() throws IOException {

        MockitoAnnotations.initMocks(this);
        HttpHeaders headers = new HttpHeaders();
        headers.set("a", "1");
        headers.set("a", "2");
        headers.set("a", "3");
        headers.set("b", "111");
        headers.set("c", "222");
        headers.set("d", "333");
        Mockito.when(request.getHeaders()).thenReturn(headers);
        Mockito.when(execution.execute(Mockito.any(), Mockito.any()))
                .thenReturn(response);
        Mockito.when(response.getStatusCode())
                .thenReturn(HttpStatus.ACCEPTED);

        CustomizationBufferingClientHttpResponseWrapper expected =
                new CustomizationBufferingClientHttpResponseWrapper(response);
        String body = "abc";

        CustomizationBufferingClientHttpResponseWrapper actual =
                (CustomizationBufferingClientHttpResponseWrapper) interceptor
                        .intercept(request, body.getBytes(), execution);

        // assert
        MatcherAssert.assertThat(actual.getHeaders(), CoreMatchers.is(expected.getHeaders()));
        byte[] actualByte = new byte[1024];
        actual.getBody().read(actualByte);
        byte[] expectedByte = new byte[1024];
        expected.getBody().read(expectedByte);
        MatcherAssert.assertThat(new String(actualByte), CoreMatchers.is(new String(expectedByte)));
        MatcherAssert.assertThat(actual.getStatusCode(), CoreMatchers.is(expected.getStatusCode()));
        MatcherAssert.assertThat(actual.getRawStatusCode(), CoreMatchers.is(expected.getRawStatusCode()));
        MatcherAssert.assertThat(actual.getStatusText(), CoreMatchers.is(expected.getStatusText()));
    }
}
