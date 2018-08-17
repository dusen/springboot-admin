/**
 * @(#)CustomizationBufferingClientHttpResponseWrapperTest.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.client.customization;

import java.io.IOException;
import java.util.Objects;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.mock.http.client.MockClientHttpResponse;

/**
 * The unit test for RestTemplate's CustomizationBufferingClientHttpResponseWrapper.<br>
 *
 * @author Fast Retailing
 * @version $Revision$
 */
public class CustomizationBufferingClientHttpResponseWrapperTest {

    @Test
    public void test() throws IOException {
        ClientHttpResponse resp = new MockClientHttpResponse("mockBody".getBytes(), HttpStatus.OK);
        CustomizationBufferingClientHttpResponseWrapper target =
                new CustomizationBufferingClientHttpResponseWrapper(resp);
        target.close();

        MatcherAssert.assertThat(target.getRawStatusCode(), CoreMatchers.is(HttpStatus.OK.value()));
        MatcherAssert.assertThat(target.getStatusCode(), CoreMatchers.is(HttpStatus.OK));
        MatcherAssert.assertThat(Objects.isNull(target.getBody()), CoreMatchers.is(false));
        MatcherAssert.assertThat(Objects.isNull(target.getHeaders()), CoreMatchers.is(false));
        MatcherAssert.assertThat(target.getStatusText(), CoreMatchers.is("OK"));
    }

}
