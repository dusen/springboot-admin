/**
 * @(#)HmacAuthenticationInterceptorTest.java Copyright (c) 2017 Fast Retailing Corporation.
 */
package com.fastretailing.dcp.common.hmac;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.mock.http.client.MockAsyncClientHttpRequest;
import org.springframework.mock.http.client.MockClientHttpResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.fastretailing.dcp.common.exception.SystemException;
import com.fastretailing.dcp.common.hmac.configuration.HmacAuthenticationProperties;
import com.fastretailing.dcp.common.hmac.configuration.HmacProperties;
import com.fastretailing.dcp.common.hmac.context.HmacAuthenticationContext;
import com.fastretailing.dcp.common.hmac.context.HmacAuthenticationContextHolder;
import com.fastretailing.dcp.common.hmac.interceptor.ClientRestTemplateHmacAuthenticationInterceptor;
import com.fastretailing.dcp.common.util.CommonUtility;

import lombok.extern.slf4j.Slf4j;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({
        @ContextConfiguration("classpath*:com/fastretailing/dcp/common/api/hmac/test-context.xml")
})
@Slf4j
public class ClientRestTemplateHmacAuthenticationInterceptorTest {

    @InjectMocks
    private ClientRestTemplateHmacAuthenticationInterceptor target = new ClientRestTemplateHmacAuthenticationInterceptor();

    @Mock
    private HmacProperties hmacProperties;

    @Mock
    private CommonUtility commonUtility;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        Map<String, HmacAuthenticationProperties> hmacAuthenticationPropertiesMap = new HashMap<>();
        HmacAuthenticationProperties properties = new HmacAuthenticationProperties();
        properties.setClientId("testClientId");
        properties.setClientSecret("ClientSecret");
        hmacAuthenticationPropertiesMap.put("testClientId", properties);

        HmacProperties hmacProperties = new HmacProperties();
        hmacProperties.setHmac(hmacAuthenticationPropertiesMap);

        ReflectionTestUtils.setField(target, "hmacProperties", hmacProperties);
        ReflectionTestUtils.setField(target, "commonUtility", commonUtility);

        Mockito.when(commonUtility.getOperationAt()).thenReturn(LocalDateTime.parse("2017-01-01T01:01:01.000"));


    }

    @Test
    public void hmacAuthenticationInterceptor01() throws IOException {

        MockAsyncClientHttpRequest mockRequest = new MockAsyncClientHttpRequest(HttpMethod.PUT, URI.create("http://basket.oms.fastretailing.com/mock"));

        mockRequest.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        ClientHttpRequestExecution mockExecution = new ClientHttpRequestExecution() {
            @Override
            public ClientHttpResponse execute(HttpRequest httpRequest, byte[] bytes) throws IOException {
                MockClientHttpResponse mockResponse = new MockClientHttpResponse(new byte[]{}, HttpStatus.OK);
                assertEquals(6, httpRequest.getHeaders().size());
                assertEquals("[application/json]", httpRequest.getHeaders().get("Content-Type").toString());
                assertNotNull(httpRequest.getHeaders().get("Authorization"));
                assertEquals("[testClientId]", httpRequest.getHeaders().get("X-FR-clientid").toString());
                assertNotNull(httpRequest.getHeaders().get("Date"));
                assertEquals("[1B2M2Y8AsgTpgAmY7PhCfg==]", httpRequest.getHeaders().get("Content-MD5").toString());

                return mockResponse;
            }
        };

        HmacAuthenticationContext context = new HmacAuthenticationContext();
        context.setPlatformName("testClientId");
        HmacAuthenticationContextHolder.setHmacAuthenticationContext(context);

        ClientHttpResponse response = target.intercept(mockRequest, new byte[]{}, mockExecution);

    }

    @Test
    public void hmacAuthenticationInterceptor02() throws IOException {

        HmacProperties hmacProperties = new HmacProperties();

        ReflectionTestUtils.setField(target, "hmacProperties", hmacProperties);
        MockAsyncClientHttpRequest mockRequest = new MockAsyncClientHttpRequest(HttpMethod.PUT, URI.create("http://basket.oms.fastretailing.com/mock"));

        mockRequest.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        thrown.expect(SystemException.class);

        ClientHttpRequestExecution mockExecution = new ClientHttpRequestExecution() {
            @Override
            public ClientHttpResponse execute(HttpRequest httpRequest, byte[] bytes) throws IOException {
                MockClientHttpResponse mockResponse = new MockClientHttpResponse(new byte[]{}, HttpStatus.OK);
                assertEquals(1, httpRequest.getHeaders().size());

                return mockResponse;
            }
        };

        HmacAuthenticationContext context = new HmacAuthenticationContext();
        context.setPlatformName("testClientId");
        HmacAuthenticationContextHolder.setHmacAuthenticationContext(context);

        ClientHttpResponse response = target.intercept(mockRequest, new byte[]{}, mockExecution);

    }

    @Test
    public void hmacAuthenticationInterceptor03() throws IOException {

        MockAsyncClientHttpRequest mockRequest = new MockAsyncClientHttpRequest(HttpMethod.POST, URI.create("http://basket.oms.fastretailing.com/mock"));

        // no content type
        // mockRequest.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        // body
        String name = "test";
        mockRequest.getBody().write(name.getBytes());
        mockRequest.getBody().flush();

        ClientHttpRequestExecution mockExecution = new ClientHttpRequestExecution() {
            @Override
            public ClientHttpResponse execute(HttpRequest httpRequest, byte[] bytes) throws IOException {
                MockClientHttpResponse mockResponse = new MockClientHttpResponse(new byte[]{}, HttpStatus.OK);

                assertEquals(6, httpRequest.getHeaders().size());
                assertNotNull(httpRequest.getHeaders().get("Authorization"));
                assertEquals("[testClientId]", httpRequest.getHeaders().get("X-FR-clientid").toString());
                assertNotNull(httpRequest.getHeaders().get("Date"));
                assertEquals("[CY9rzUYh03PK3k6DJie09g==]", httpRequest.getHeaders().get("Content-MD5").toString());
                return mockResponse;
            }
        };

        HmacAuthenticationContext context = new HmacAuthenticationContext();
        context.setPlatformName("testClientId");
        HmacAuthenticationContextHolder.setHmacAuthenticationContext(context);

        ClientHttpResponse response = target.intercept(mockRequest, "test".getBytes(), mockExecution);

    }

    @Test
    public void hmacAuthenticationInterceptor05() throws IOException {

        MockAsyncClientHttpRequest mockRequest = new MockAsyncClientHttpRequest(HttpMethod.POST, URI.create("http://basket.oms.fastretailing.com/mock"));

        String name = "test";
        mockRequest.getBody().write(name.getBytes());
        mockRequest.getBody().flush();
        List<String> algoList = new ArrayList<>();
        algoList.add("103");
        mockRequest.getHeaders().put("X-FR-phumac-algo", algoList);
        List<String> dateList = new ArrayList<>();
        dateList.add("2017");
        dateList.add("2018");
        mockRequest.getHeaders().put("X-FR-date", dateList);

        ClientHttpRequestExecution mockExecution = new ClientHttpRequestExecution() {
            @Override
            public ClientHttpResponse execute(HttpRequest httpRequest, byte[] bytes) throws IOException {
                MockClientHttpResponse mockResponse = new MockClientHttpResponse(new byte[]{}, HttpStatus.OK);

                assertEquals(8, httpRequest.getHeaders().size());
                assertNotNull(httpRequest.getHeaders().get("Authorization"));
                assertEquals("[testClientId]", httpRequest.getHeaders().get("X-FR-clientid").toString());
                assertEquals(2, httpRequest.getHeaders().get("X-FR-date").size());
                assertNotNull(httpRequest.getHeaders().get("Date"));
                assertEquals("[CY9rzUYh03PK3k6DJie09g==]", httpRequest.getHeaders().get("Content-MD5").toString());
                return mockResponse;
            }
        };

        HmacAuthenticationContext context = new HmacAuthenticationContext();
        context.setPlatformName("testClientId");
        HmacAuthenticationContextHolder.setHmacAuthenticationContext(context);

        ClientHttpResponse response = target.intercept(mockRequest, "test".getBytes(), mockExecution);

    }

    @Test
    public void hmacAuthenticationInterceptor04() throws IOException {

        MockAsyncClientHttpRequest mockRequest = new MockAsyncClientHttpRequest(HttpMethod.POST, URI.create("http://basket.oms.fastretailing.com/mock"));

        String name = "test";
        mockRequest.getBody().write(name.getBytes());
        mockRequest.getBody().flush();
        List<String> algoList = new ArrayList<>();
        algoList.add("101");
        mockRequest.getHeaders().put("X-FR-phumac-algo", algoList);
        List<String> dateList = new ArrayList<>();
        dateList.add("2017");
        dateList.add("2018");
        mockRequest.getHeaders().put("X-FR-date", dateList);

        ClientHttpRequestExecution mockExecution = new ClientHttpRequestExecution() {
            @Override
            public ClientHttpResponse execute(HttpRequest httpRequest, byte[] bytes) throws IOException {
                MockClientHttpResponse mockResponse = new MockClientHttpResponse(new byte[]{}, HttpStatus.OK);

                assertEquals(8, httpRequest.getHeaders().size());
                assertNotNull(httpRequest.getHeaders().get("Authorization"));
                assertEquals("[testClientId]", httpRequest.getHeaders().get("X-FR-clientid").toString());
                assertEquals(2, httpRequest.getHeaders().get("X-FR-date").size());
                assertNotNull(httpRequest.getHeaders().get("Date"));
                assertEquals("[CY9rzUYh03PK3k6DJie09g==]", httpRequest.getHeaders().get("Content-MD5").toString());
                return mockResponse;
            }
        };

        HmacAuthenticationContext context = new HmacAuthenticationContext();
        context.setPlatformName("testClientId");
        HmacAuthenticationContextHolder.setHmacAuthenticationContext(context);

        ClientHttpResponse response = target.intercept(mockRequest, "test".getBytes(), mockExecution);

    }

    @Test
    public void hmacAuthenticationInterceptor06() throws IOException {

        MockAsyncClientHttpRequest mockRequest = new MockAsyncClientHttpRequest(HttpMethod.PUT, URI.create("http://basket.oms.fastretailing.com/mock"));

        mockRequest.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        HmacAuthenticationContextHolder.cleanHmacAuthenticationContext();
        ClientHttpRequestExecution mockExecution = new ClientHttpRequestExecution() {
            @Override
            public ClientHttpResponse execute(HttpRequest httpRequest, byte[] bytes) throws IOException {
                MockClientHttpResponse mockResponse = new MockClientHttpResponse(new byte[]{}, HttpStatus.OK);
                assertEquals(false, httpRequest.getHeaders().containsKey("Authorization"));
                return mockResponse;
            }
        };

        ClientHttpResponse response = target.intercept(mockRequest, new byte[]{}, mockExecution);

    }

    @Test
    public void hmacAuthenticationInterceptor07() throws IOException {

        MockAsyncClientHttpRequest mockRequest = new MockAsyncClientHttpRequest(HttpMethod.POST, URI.create("http://basket.oms.fastretailing.com/mock"));

        String name = "test";
        mockRequest.getBody().write(name.getBytes());
        mockRequest.getBody().flush();
        List<String> algoList = new ArrayList<>();
        algoList.add("101");
        mockRequest.getHeaders().put("X-FR-phumac-algo", algoList);
        List<String> dateList = new ArrayList<>();
        dateList.add("2016-01-29T02：09：12+01:00");
        mockRequest.getHeaders().put("X-FR-date", dateList);

        ClientHttpRequestExecution mockExecution = new ClientHttpRequestExecution() {
            @Override
            public ClientHttpResponse execute(HttpRequest httpRequest, byte[] bytes) throws IOException {
                MockClientHttpResponse mockResponse = new MockClientHttpResponse(new byte[]{}, HttpStatus.OK);

                assertEquals(8, httpRequest.getHeaders().size());
                assertNotNull(httpRequest.getHeaders().get("Authorization"));
                assertEquals("[testClientId]", httpRequest.getHeaders().get("X-FR-clientid").toString());
                assertEquals(1, httpRequest.getHeaders().get("X-FR-date").size());
                assertNotNull(httpRequest.getHeaders().get("Date"));
                assertEquals("[CY9rzUYh03PK3k6DJie09g==]", httpRequest.getHeaders().get("Content-MD5").toString());
                return mockResponse;
            }
        };

        HmacAuthenticationContext context = new HmacAuthenticationContext();
        context.setPlatformName("testClientId");
        HmacAuthenticationContextHolder.setHmacAuthenticationContext(context);

        ClientHttpResponse response = target.intercept(mockRequest, "test".getBytes(), mockExecution);

    }

    @Test
    public void hmacAuthenticationInterceptor08() throws IOException {

        MockAsyncClientHttpRequest mockRequest = new MockAsyncClientHttpRequest(HttpMethod.POST, URI.create("http://basket.oms.fastretailing.com/mock"));

        String name = "test";
        mockRequest.getBody().write(name.getBytes());
        mockRequest.getBody().flush();
        List<String> algoList = new ArrayList<>();
        algoList.add("101");
        mockRequest.getHeaders().put("X-FR-phumac-algo", algoList);

        ClientHttpRequestExecution mockExecution = new ClientHttpRequestExecution() {
            @Override
            public ClientHttpResponse execute(HttpRequest httpRequest, byte[] bytes) throws IOException {
                MockClientHttpResponse mockResponse = new MockClientHttpResponse(new byte[]{}, HttpStatus.OK);

                assertEquals(7, httpRequest.getHeaders().size());
                assertNotNull(httpRequest.getHeaders().get("Authorization"));
                assertEquals("[testClientId]", httpRequest.getHeaders().get("X-FR-clientid").toString());
                assertNotNull(httpRequest.getHeaders().get("Date"));
                assertEquals("[CY9rzUYh03PK3k6DJie09g==]", httpRequest.getHeaders().get("Content-MD5").toString());
                return mockResponse;
            }
        };

        HmacAuthenticationContext context = new HmacAuthenticationContext();
        context.setPlatformName("testClientId");
        HmacAuthenticationContextHolder.setHmacAuthenticationContext(context);

        ClientHttpResponse response = target.intercept(mockRequest, "test".getBytes(), mockExecution);

    }

    @Test
    public void test1() throws IOException {
        assertThat(Objects.isNull(new HmacAuthenticationContextHolder()), is(false));
    }
}
