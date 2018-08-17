/**
 * @(#)RestTemplateErrorHandlerTest.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.client.handler;

import java.io.IOException;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;

/**
 * RestTemplateErrorHandlerTest.<br>
 *
 * @author Fast Retailing
 * @version $Revision$
 */
public class RestTemplateErrorHandlerTest {

    private RestTemplateErrorHandler handler = new RestTemplateErrorHandler();

    @Mock
    ClientHttpResponse response;

    /**
     * Client error.
     * @throws IOException IOException
     */
    @Test
    public void hasError01() throws IOException {

        MockitoAnnotations.initMocks(this);
        Mockito.when(response.getStatusCode()).thenReturn(HttpStatus.NOT_FOUND);
        boolean actual = handler.hasError(response);

        // assert
        MatcherAssert.assertThat(actual, CoreMatchers.is(Boolean.FALSE));
    }

    /**
     * Server error.
     * @throws IOException IOException
     */
    @Test
    public void hasError02() throws IOException {

        MockitoAnnotations.initMocks(this);
        Mockito.when(response.getStatusCode()).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR);
        boolean actual = handler.hasError(response);

        // assert
        MatcherAssert.assertThat(actual, CoreMatchers.is(Boolean.FALSE));
    }

    /**
     * HttpStatus.OK.
     * @throws IOException IOException
     */
    @Test
    public void hasError03() throws IOException {

        MockitoAnnotations.initMocks(this);
        Mockito.when(response.getStatusCode()).thenReturn(HttpStatus.OK);
        boolean actual = handler.hasError(response);

        // assert
        MatcherAssert.assertThat(actual, CoreMatchers.is(Boolean.FALSE));
    }

    /**
     * IOException.
     * @throws IOException IOException
     */
    @Test
    public void hasError04() throws IOException {

        MockitoAnnotations.initMocks(this);
        Mockito.when(response.getStatusCode()).thenThrow(new IOException());
        try {
            boolean actual = handler.hasError(response);

            // assert
            MatcherAssert.assertThat(actual, CoreMatchers.is(Boolean.FALSE));
        } catch (Exception e) {
            // assert
            Assert.fail();
        }
    }
}
