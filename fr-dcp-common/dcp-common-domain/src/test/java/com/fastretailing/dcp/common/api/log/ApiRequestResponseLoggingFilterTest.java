package com.fastretailing.dcp.common.api.log;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import com.fastretailing.dcp.common.util.CommonUtility;
import com.fastretailing.dcp.common.util.JsonUtility;
import lombok.Getter;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({@ContextConfiguration(locations = {"classpath*:com/fastretailing/dcp/common/api/log/test-context.xml"})})
public class ApiRequestResponseLoggingFilterTest {

    private MockHttpServletResponse response;
    private MockHttpServletRequest request;
    private MockFilterChain filterChain;
    private MockHttpSession session;

    @Autowired
    private MessageSource messageSource;

    @Mock
    private MessageSource messageSourceMock;

    @Mock
    private Appender<ILoggingEvent> mockAppender;

    @InjectMocks
    private ApiRequestResponseLoggingFilter apiRequestResponseLoggingFilter = new ApiRequestResponseLoggingFilter();

    @Mock
    private CommonUtility commonUtility;

    private final String serviceName = "basket";

    private String hostname = "";

    @Autowired
    private ConfigurableApplicationContext context;

    @Before
    public void setUp() throws Exception {
        hostname =InetAddress.getLocalHost().getHostName();
        MockitoAnnotations.initMocks(this);
        request = new MockHttpServletRequest();
        request.setMethod("testMethod");
        request.setRequestURI("testRequestURI");
        request.setQueryString("queryString=testQueryString");
        request.setRemoteUser("testRemoteUser");
        request.setRemoteAddr("testUrl");
        request.addHeader("X-Time-Zone", "Asia/Shanghai");
        request.addHeader("host", "localhost:8080");
        request.addHeader("Postman-Token", "token1");
        response = new MockHttpServletResponse();
        response.addHeader("head1Name", "head1Value");
        response.addHeader("head2Name", "head2Value");
        response.addHeader(MdcKeyEnum.AMAZON_TRACE_ID.getKey(), "Self=testSelf2;Root=testRoot2");
        filterChain = new MockFilterChain();
        session = new MockHttpSession();

        ch.qos.logback.classic.Logger root =
                (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
        Mockito.when(mockAppender.getName()).thenReturn("consoleLog");
        root.addAppender(mockAppender);

        Map<String, Object> maskingUris = new HashMap<>();
        maskingUris.put("basket_basketNo_no1", "/v1/uq/ca/test1/(.*)");
        maskingUris.put("basket_basketNo_no2", "/v1/uq/ca/test2/(.*)");
        Map<String, Object> maskingItems = new HashMap<>();
        maskingItems.put("basket_basketNo_no1", "(business_date|cd_id)");
        maskingItems.put("basket_basketNo_no2", "(non_item_detail_list_by_item|currency_code)");
        maskingItems.put("basket_basketNo_no3", "(non_item_detail_list_by_item|currency_code)");
        maskingItems.put("basket_basketNo_no4", "(item_delete_flag)");
        ReflectionTestUtils.setField(apiRequestResponseLoggingFilter, "maskingUris", maskingUris);
        ReflectionTestUtils.setField(apiRequestResponseLoggingFilter, "maskingItems", maskingItems);


        ReflectionTestUtils.setField(apiRequestResponseLoggingFilter, "messageSource", messageSource);
        ReflectionTestUtils.setField(apiRequestResponseLoggingFilter,
                                     "apiServiceName", serviceName);
        Mockito.when(commonUtility.getOperationAt()).thenReturn(LocalDateTime.of(2017, 1, 2, 3, 4, 5, 678000000));
        ReflectionTestUtils.setField(apiRequestResponseLoggingFilter,
                "commonUtility", commonUtility);

        ReflectionTestUtils.setField(apiRequestResponseLoggingFilter,
                "versionAws", "2");
    }

    @Test
    public void maskLogCase8() throws Exception {

        request.setRequestURI("/v1/uq/ca/test1/case8");
        // business data
        Assert.assertEquals("(business_date|cd_id)", ReflectionTestUtils.invokeMethod(apiRequestResponseLoggingFilter, "getMaskItemsKey", request));

    }
    @Test
    public void maskLogCase9() throws Exception {

        request.setRequestURI("/v1/uq/ca/aaaa/case8");
        // business data
        Assert.assertNull(ReflectionTestUtils.invokeMethod(apiRequestResponseLoggingFilter, "getMaskItemsKey", request));

    }

    @Test
    public void maskLogCase10() throws Exception {
        apiRequestResponseLoggingFilter.setMaskingItems(null);
        // business data
        Assert.assertNull(ReflectionTestUtils.invokeMethod(apiRequestResponseLoggingFilter, "getMaskItemsKey", request));
    }

    @Test
    public void maskLogCase11() throws Exception {
        apiRequestResponseLoggingFilter.setMaskingItems(new HashMap<>());
        // business data
        Assert.assertNull(ReflectionTestUtils.invokeMethod(apiRequestResponseLoggingFilter, "getMaskItemsKey", request));
    }

    @Test
    public void maskLogCase12() throws Exception {
        apiRequestResponseLoggingFilter.setMaskingUris(new HashMap<>());
        // business data
        Assert.assertNull(ReflectionTestUtils.invokeMethod(apiRequestResponseLoggingFilter, "getMaskItemsKey", request));
    }

    @Test
    public void maskLogCase13() throws Exception {
        apiRequestResponseLoggingFilter.setMaskingUris(null);
        // business data
        Assert.assertNull(ReflectionTestUtils.invokeMethod(apiRequestResponseLoggingFilter, "getMaskItemsKey", request));
    }

    @Test
    public void doFilterInternalCase1() throws ServletException, IOException {
        // QueryString
        apiRequestResponseLoggingFilter.setIncludeQueryString(true);
        // client info
        request.setSession(session);
        apiRequestResponseLoggingFilter.setIncludeClientInfo(true);
        // header
        apiRequestResponseLoggingFilter.setIncludeHeaders(true);
        // Payload
        apiRequestResponseLoggingFilter.setIncludePayload(true);
        request.addHeader(MdcKeyEnum.AMAZON_TRACE_ID.getKey(), "Self=testSelf1;Root=testRoot1");
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        responseWrapper.getOutputStream().write("OK".getBytes());

        apiRequestResponseLoggingFilter.doFilterInternal(request, responseWrapper, filterChain);

        // check the request log
        RequestInfo requestInfo = new RequestInfo();
        requestInfo.setTraceId("testRoot1");
        requestInfo.setMethod("testMethod");
        HttpHeaders headers = new HttpHeaders();
        headers.put("X-Time-Zone", Arrays.asList("Asia/Shanghai"));
        requestInfo.setHeaders(JsonUtility.toJson(headers));
        requestInfo.setQuery("queryString=testQueryString");

        requestInfo.setUrl("http://localhost:8080testRequestURI");
        requestInfo.setOriginalUrl("testRequestURI");
        requestInfo.setHttpVersion("HTTP/1.1");
        requestInfo.setBody("null");

        Map<String, Object> requestLogMap = new LinkedHashMap<>();
        requestLogMap.put("requestId", hostname + "/testSelf1");
        requestLogMap.put("req", requestInfo);
        // check the before request log
        checkRequestLog("request start", requestLogMap);

        // check the after request log
        checkRequestLog("request completed", requestLogMap);

        // check the response log
        Map<String, Object> responseLogMap = new LinkedHashMap<>();
        ResponseLogInfo responseLogInfo = new ResponseLogInfo();
        responseLogInfo.setStatusCode(200);
        responseLogInfo.setBody("OK");
        headers = new HttpHeaders();
        headers.add("head1Name", "head1Value");
        headers.add("head2Name", "head2Value");
        headers.add(MdcKeyEnum.AMAZON_TRACE_ID.getKey(), "Self=testSelf2;Root=testRoot2");
        responseLogInfo.setHeaders(JsonUtility.toJson(headers));
        responseLogMap.put("res", responseLogInfo);
        checkRequestLog("", responseLogMap);

    }

    @Test
    public void doFilterInternalCase2() throws ServletException, IOException {
        // QueryString
        apiRequestResponseLoggingFilter.setIncludeQueryString(false);
        // client info
        request.setSession(session);
        apiRequestResponseLoggingFilter.setIncludeClientInfo(false);
        // header
        apiRequestResponseLoggingFilter.setIncludeHeaders(false);
        // Payload
        apiRequestResponseLoggingFilter.setIncludePayload(false);
        request.addHeader(MdcKeyEnum.AMAZON_TRACE_ID.getKey(), "Self=testSelf1;Root=testRoot1");
        apiRequestResponseLoggingFilter.doFilterInternal(request, response, filterChain);
        // check the request log
        RequestInfo requestInfo = new RequestInfo();
        requestInfo.setTraceId("testRoot1");
        requestInfo.setMethod("testMethod");

        requestInfo.setUrl("http://localhost:8080testRequestURI");
        requestInfo.setOriginalUrl("testRequestURI");
        requestInfo.setHttpVersion("HTTP/1.1");
        requestInfo.setBody("null");

        Map<String, Object> requestLogMap = new LinkedHashMap<>();
        requestLogMap.put("requestId", hostname + "/testSelf1");
        requestLogMap.put("req", requestInfo);
        // check the before request log
        checkRequestLog("request start", requestLogMap);

        // check the after request log
        checkRequestLog("request completed", requestLogMap);

        // check the response log
        Map<String, Object> responseLogMap = new LinkedHashMap<>();
        ResponseLogInfo responseLogInfo = new ResponseLogInfo();
        responseLogInfo.setStatusCode(200);
        HttpHeaders headers = new HttpHeaders();
        headers.add("head1Name", "head1Value");
        headers.add("head2Name", "head2Value");
        headers.add(MdcKeyEnum.AMAZON_TRACE_ID.getKey(), "Self=testSelf2;Root=testRoot2");
        responseLogInfo.setHeaders(JsonUtility.toJson(headers));
        responseLogMap.put("res", responseLogInfo);
        checkRequestLog("", responseLogMap);
    }

    @Test
    public void doFilterInternalCase3() throws ServletException, IOException {
        // QueryString
        apiRequestResponseLoggingFilter.setIncludeQueryString(true);
        // client info
        request.setSession(session);
        apiRequestResponseLoggingFilter.setIncludeClientInfo(true);
        // header
        apiRequestResponseLoggingFilter.setIncludeHeaders(true);
        // Payload
        apiRequestResponseLoggingFilter.setIncludePayload(true);
        request.addHeader(MdcKeyEnum.AMAZON_TRACE_ID.getKey(), "Self=testSelf1;Root=testRoot1");

        String queryString = StringUtils.join(
                "queryString=testQueryString",
                "&form.id=formId",
                "&form.name=formName");
        request.setQueryString(queryString);

        //
        apiRequestResponseLoggingFilter.doFilterInternal(request, response, filterChain);

        // check the request log
        RequestInfo requestInfo = new RequestInfo();
        requestInfo.setTraceId("testRoot1");
        requestInfo.setMethod("testMethod");
        HttpHeaders headers = new HttpHeaders();
        headers.put("X-Time-Zone",
                    Arrays.asList(new String[]{"Asia/Shanghai"}));
        requestInfo.setHeaders(JsonUtility.toJson(headers));
        requestInfo.setQuery(queryString);

        requestInfo.setUrl("http://localhost:8080testRequestURI");
        requestInfo.setOriginalUrl("testRequestURI");
        requestInfo.setHttpVersion("HTTP/1.1");
        requestInfo.setBody("null");

        Map<String, Object> requestLogMap = new LinkedHashMap<>();
        requestLogMap.put("requestId", hostname + "/testSelf1");
        requestLogMap.put("req", requestInfo);
        // check the before request log
        checkRequestLog("request start", requestLogMap);

    }

    @Test
    public void doFilterInternalCase4() throws ServletException, IOException {
        // QueryString
        apiRequestResponseLoggingFilter.setIncludeQueryString(true);
        // client info
        request.setSession(session);
        apiRequestResponseLoggingFilter.setIncludeClientInfo(true);
        // header
        apiRequestResponseLoggingFilter.setIncludeHeaders(true);
        // Payload
        apiRequestResponseLoggingFilter.setIncludePayload(true);

        request.addHeader(MdcKeyEnum.AMAZON_TRACE_ID.getKey(), "Root=testRoot1");
        //
        apiRequestResponseLoggingFilter.doFilterInternal(request, response, filterChain);

        // check the request log
        RequestInfo requestInfo = new RequestInfo();
        requestInfo.setTraceId("testRoot1");
        requestInfo.setMethod("testMethod");
        HttpHeaders headers = new HttpHeaders();
        headers.put("X-Time-Zone", Arrays.asList("Asia/Shanghai"));
        requestInfo.setHeaders(JsonUtility.toJson(headers));
        requestInfo.setQuery("queryString=testQueryString");

        requestInfo.setUrl("http://localhost:8080testRequestURI");
        requestInfo.setOriginalUrl("testRequestURI");
        requestInfo.setHttpVersion("HTTP/1.1");
        requestInfo.setBody("null");

        Map<String, Object> requestLogMap = new LinkedHashMap<>();
        requestLogMap.put("requestId", hostname + "/testRoot1");
        requestLogMap.put("req", requestInfo);
        // check the before request log
        checkRequestLog("request start", requestLogMap);
    }

    @Test
    public void doFilterInternalCase5() throws ServletException, IOException {
        // QueryString
        apiRequestResponseLoggingFilter.setIncludeQueryString(true);
        // client info
        request.setSession(session);
        apiRequestResponseLoggingFilter.setIncludeClientInfo(true);
        // header
        apiRequestResponseLoggingFilter.setIncludeHeaders(true);
        // Payload
        apiRequestResponseLoggingFilter.setIncludePayload(true);

        apiRequestResponseLoggingFilter.doFilterInternal(request, response, filterChain);
        String trace_id = "2-" + LocalDateTime.of(2017, 1, 2, 3, 4, 5, 678).toEpochSecond(ZoneOffset.UTC);
        assertThat(MDC.get(MdcKeyEnum.AMAZON_TRACE_ID.getKey()).contains(trace_id), is(true));
    }

    @Test
    public void doFilterInternalCase6() throws ServletException, IOException {
        // QueryString
        apiRequestResponseLoggingFilter.setIncludeQueryString(true);
        // client info
        request.setSession(session);
        apiRequestResponseLoggingFilter.setIncludeClientInfo(true);
        // header
        apiRequestResponseLoggingFilter.setIncludeHeaders(true);
        // Payload
        apiRequestResponseLoggingFilter.setIncludePayload(true);

        request.addHeader(MdcKeyEnum.AMAZON_TRACE_ID.getKey(), "Root=");

        apiRequestResponseLoggingFilter.doFilterInternal(request, response, filterChain);
        String trace_id = "2-" + LocalDateTime.of(2017, 1, 2, 3, 4, 5, 678).toEpochSecond(ZoneOffset.UTC);
        assertThat(MDC.get(MdcKeyEnum.AMAZON_TRACE_ID.getKey()).contains(trace_id), is(true));
    }

    @Test
    public void doFilterInternalCase7() throws ServletException, IOException {
        testNotSupportContentType("image");
    }

    @Test
    public void doFilterInternalCase8() throws ServletException, IOException {
        testNotSupportContentType("application/javascript");
    }

    @Test
    public void doFilterInternalCase9() throws ServletException, IOException {
        testNotSupportContentType("text/html");
    }

    @Test
    public void doFilterInternalCase10() throws ServletException, IOException {
        testNotSupportContentType("text/css");
    }

    @Test
    public void doFilterInternalCase10_1() throws ServletException, IOException {

        ReflectionTestUtils.setField(apiRequestResponseLoggingFilter, "messageSource", messageSourceMock);

        Mockito.when(messageSourceMock.getMessage(Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any())).thenThrow(new NoSuchMessageException(""));
        testNotSupportContentType1("text/css");
    }

    /**
     * check response's header&body
     *
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void doFilterInternalCase_TestMask01() throws ServletException, IOException {

        // mock
        ReflectionTestUtils.setField(apiRequestResponseLoggingFilter, "messageSource", messageSourceMock);
        ReflectionTestUtils.setField(apiRequestResponseLoggingFilter, "includeHeaders", true);
        ReflectionTestUtils.setField(apiRequestResponseLoggingFilter, "includePayload", true);
        Mockito.when(messageSourceMock.getMessage(Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any())).thenThrow(new NoSuchMessageException(""));

        // url & mask target setting
        Map<String, Object> maskingUris = new HashMap<>();
        maskingUris.put("basket_basketNo_no1", "/v1/uq/ca/test11/(.*)");
        Map<String, Object> maskingItems = new HashMap<>();
        maskingItems.put("basket_basketNo_no1", "(test_header_key1|test_header_key3|test_body_key2)");
        ReflectionTestUtils.setField(apiRequestResponseLoggingFilter, "maskingUris", maskingUris);
        ReflectionTestUtils.setField(apiRequestResponseLoggingFilter, "maskingItems", maskingItems);

        // request setting
        request = new MockHttpServletRequest();
        request.setRequestURI("/v1/uq/ca/test11/doFilterInternalCase_TestMask01");
        // response setting
        response = new MockHttpServletResponse();
        // response header log setting
        response.addHeader("test_header_key1", "test_header_val1");
        response.addHeader("test_header_key2", "test_header_val2");
        response.addHeader("test_header_key3", "test_header_val3");

        // response body log setting
        Map<String, Object> body = new HashMap<>();
        body.put("test_body_key1", "test_body_val1");
        body.put("test_body_key2", "test_body_val2");
        body.put("test_body_key3", "test_body_val3");
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        responseWrapper.getOutputStream().write(JsonUtility.toJson(body).getBytes());

        apiRequestResponseLoggingFilter.doFilterInternal(request, responseWrapper, filterChain);

        // check response header log setting
        HttpHeaders headersForChk = new HttpHeaders();
        headersForChk.add("test_header_key1", "*");
        headersForChk.add("test_header_key2", "test_header_val2");
        headersForChk.add("test_header_key3", "*");

        // check response body log setting
        Map<String, Object> bodyForChk = new HashMap<>();
        bodyForChk.put("test_body_key1", "test_body_val1");
        bodyForChk.put("test_body_key2", "*");
        bodyForChk.put("test_body_key3", "test_body_val3");

        ResponseLogInfo responseLogInfo = new ResponseLogInfo();
        responseLogInfo.setStatusCode(200);
        responseLogInfo.setHeaders(JsonUtility.toJson(headersForChk));
        responseLogInfo.setBody(JsonUtility.toJson(bodyForChk));

        // check the response log
        Map<String, Object> responseLogMap = new LinkedHashMap<>();
        responseLogMap.put("res", responseLogInfo);
        // check the response log
        checkRequestLog("", responseLogMap);
    }

    /**
     * check response's header&body
     *
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void doFilterInternalCase_TestMask02() throws ServletException, IOException {

        // mock
        ReflectionTestUtils.setField(apiRequestResponseLoggingFilter, "messageSource", messageSourceMock);
        ReflectionTestUtils.setField(apiRequestResponseLoggingFilter, "includeHeaders", true);
        ReflectionTestUtils.setField(apiRequestResponseLoggingFilter, "includePayload", true);
        Mockito.when(messageSourceMock.getMessage(Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any())).thenThrow(new NoSuchMessageException(""));

        // url & mask target setting
        Map<String, Object> maskingUris = new HashMap<>();
        maskingUris.put("basket_basketNo_no1", "/v1/uq/ca/test11/(.*)");
        Map<String, Object> maskingItems = new HashMap<>();
        maskingItems.put("basket_basketNo_no1", "(test_header_key1|test_header_key3|test_body_key2)");
        ReflectionTestUtils.setField(apiRequestResponseLoggingFilter, "maskingUris", maskingUris);
        ReflectionTestUtils.setField(apiRequestResponseLoggingFilter, "maskingItems", maskingItems);

        // request setting
        request = new MockHttpServletRequest();
        request.setRequestURI("/v1/uq/ca/test11/doFilterInternalCase_TestMask01");
        // response setting
        response = new MockHttpServletResponse();
        // response header log setting
        response.addHeader("test_header_key1", "test_header_val1");
        response.addHeader("test_header_key1", "test_header_val11");
        response.addHeader("test_header_key1", "test_header_val12");
        response.addHeader("test_header_key1", "test_header_val13");
        response.addHeader("test_header_key2", "test_header_val2");
        response.addHeader("test_header_key2", "test_header_val21");
        response.addHeader("test_header_key2", "test_header_val22");
        response.addHeader("test_header_key2", "test_header_val23");
        response.addHeader("test_header_key3", "test_header_val3");
        response.addHeader("test_header_key3", "test_header_val31");
        response.addHeader("test_header_key3", "test_header_val32");
        response.addHeader("test_header_key3", "test_header_val33");

        // response body log setting
        Map<String, Object> body = new HashMap<>();
        Map<String, Object> bodyVal1 = new HashMap<>();
        bodyVal1.put("test_body_key1_mapKey1", "test_body_key1_mapVal1");
        Map<String, Object> bodyKey1MapVal2 = new HashMap<>();
        bodyKey1MapVal2.put("test_body_key1_mapKey2_subMapKey1", "test_body_key1_mapKey2_subMapVal1");
        bodyKey1MapVal2.put("test_body_key1_mapKey2_subMapKey2", Arrays.asList(1, 2, 3));
        Map<String, Object> list0Map = new HashMap<>();
        list0Map.put("list0MapKey0", "list0MapKey0");
        list0Map.put("list0MapKey1", "list0MapKey2");
        Map<String, Object> list1Map = new HashMap<>();
        list1Map.put("list1MapKey0", "list1MapKey0");
        list1Map.put("list1MapKey1", "list1MapKey2");
        bodyKey1MapVal2.put("test_body_key1_mapKey2_subMapKey3", Arrays.asList(list0Map, list1Map));
        bodyVal1.put("test_body_key1_mapKey2", bodyKey1MapVal2);
        bodyVal1.put("test_body_key1_mapKey3", "test_body_key1_mapVal3");
        body.put("test_body_key1", bodyVal1);
        body.put("test_body_key2", "test_body_val2");
        body.put("test_body_key3", "test_body_val3");
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        responseWrapper.getOutputStream().write(JsonUtility.toJson(body).getBytes());

        apiRequestResponseLoggingFilter.doFilterInternal(request, responseWrapper, filterChain);

        // check response header log setting
        HttpHeaders headersForChk = new HttpHeaders();
        headersForChk.add("test_header_key1", "*");
        headersForChk.add("test_header_key2", "test_header_val2");
        headersForChk.add("test_header_key2", "test_header_val21");
        headersForChk.add("test_header_key2", "test_header_val22");
        headersForChk.add("test_header_key2", "test_header_val23");
        headersForChk.add("test_header_key3", "*");

        // check response body log setting
        Map<String, Object> bodyForChk = new HashMap<>();
        Map<String, Object> bodyVal1ForChk = new HashMap<>();
        bodyVal1ForChk.put("test_body_key1_mapKey1", "test_body_key1_mapVal1");
        Map<String, Object> bodyKey1MapVal2ForChk = new HashMap<>();
        bodyKey1MapVal2ForChk.put("test_body_key1_mapKey2_subMapKey1", "test_body_key1_mapKey2_subMapVal1");
        bodyKey1MapVal2ForChk.put("test_body_key1_mapKey2_subMapKey2", Arrays.asList(1, 2, 3));
        Map<String, Object> list0MapForChk = new HashMap<>();
        list0MapForChk.put("list0MapKey0", "list0MapKey0");
        list0MapForChk.put("list0MapKey1", "list0MapKey2");
        Map<String, Object> list1MapForChk = new HashMap<>();
        list1MapForChk.put("list1MapKey0", "list1MapKey0");
        list1MapForChk.put("list1MapKey1", "list1MapKey2");
        bodyKey1MapVal2ForChk.put("test_body_key1_mapKey2_subMapKey3", Arrays.asList(list0MapForChk, list1MapForChk));
        bodyVal1ForChk.put("test_body_key1_mapKey2", bodyKey1MapVal2ForChk);
        bodyVal1ForChk.put("test_body_key1_mapKey3", "test_body_key1_mapVal3");
        bodyForChk.put("test_body_key1", bodyVal1ForChk);
        bodyForChk.put("test_body_key2", "*");
        bodyForChk.put("test_body_key3", "test_body_val3");

        ResponseLogInfo responseLogInfo = new ResponseLogInfo();
        responseLogInfo.setStatusCode(200);
        responseLogInfo.setHeaders(JsonUtility.toJson(headersForChk));
        responseLogInfo.setBody(JsonUtility.toJson(bodyForChk));

        // check the response log
        Map<String, Object> responseLogMap = new LinkedHashMap<>();
        responseLogMap.put("res", responseLogInfo);
        // check the response log
        checkRequestLog("", responseLogMap);
    }

    /**
     * check response's header&body
     *
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void doFilterInternalCase_TestMask03() throws ServletException, IOException {

        // mock
        ReflectionTestUtils.setField(apiRequestResponseLoggingFilter, "messageSource", messageSourceMock);
        ReflectionTestUtils.setField(apiRequestResponseLoggingFilter, "includeHeaders", true);
        ReflectionTestUtils.setField(apiRequestResponseLoggingFilter, "includePayload", true);
        Mockito.when(messageSourceMock.getMessage(Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any())).thenThrow(new NoSuchMessageException(""));

        // url & mask target setting
        Map<String, Object> maskingUris = new HashMap<>();
        maskingUris.put("basket_basketNo_no1", "/v1/uq/ca/test11/(.*)");
        Map<String, Object> maskingItems = new HashMap<>();
        maskingItems.put("basket_basketNo_no1", "(test_header_key1|test_header_key3|list1MapKey1)");
        ReflectionTestUtils.setField(apiRequestResponseLoggingFilter, "maskingUris", maskingUris);
        ReflectionTestUtils.setField(apiRequestResponseLoggingFilter, "maskingItems", maskingItems);

        // request setting
        request = new MockHttpServletRequest();
        request.setRequestURI("/v1/uq/ca/test11/doFilterInternalCase_TestMask01");
        // response setting
        response = new MockHttpServletResponse();
        // response header log setting
        response.addHeader("test_header_key1", "test_header_val1");
        response.addHeader("test_header_key1", "test_header_val11");
        response.addHeader("test_header_key1", "test_header_val12");
        response.addHeader("test_header_key1", "test_header_val13");
        response.addHeader("test_header_key2", "test_header_val2");
        response.addHeader("test_header_key2", "test_header_val21");
        response.addHeader("test_header_key2", "test_header_val22");
        response.addHeader("test_header_key2", "test_header_val23");
        response.addHeader("test_header_key3", "test_header_val3");
        response.addHeader("test_header_key3", "test_header_val31");
        response.addHeader("test_header_key3", "test_header_val32");
        response.addHeader("test_header_key3", "test_header_val33");

        // response body log setting
        Map<String, Object> body = new HashMap<>();
        Map<String, Object> bodyVal1 = new HashMap<>();
        bodyVal1.put("test_body_key1_mapKey1", "test_body_key1_mapVal1");
        Map<String, Object> bodyKey1MapVal2 = new HashMap<>();
        bodyKey1MapVal2.put("test_body_key1_mapKey2_subMapKey1", "test_body_key1_mapKey2_subMapVal1");
        bodyKey1MapVal2.put("test_body_key1_mapKey2_subMapKey2", Arrays.asList(1, 2, 3));
        Map<String, Object> list0Map = new HashMap<>();
        list0Map.put("list0MapKey0", "list0MapKey0");
        list0Map.put("list0MapKey1", "list0MapKey2");
        Map<String, Object> list1Map = new HashMap<>();
        list1Map.put("list1MapKey0", "list1MapKey0");
        list1Map.put("list1MapKey1", "list1MapKey2");
        bodyKey1MapVal2.put("test_body_key1_mapKey2_subMapKey3", Arrays.asList(list0Map, list1Map));
        bodyVal1.put("test_body_key1_mapKey2", bodyKey1MapVal2);
        bodyVal1.put("test_body_key1_mapKey3", "test_body_key1_mapVal3");
        body.put("test_body_key1", bodyVal1);
        body.put("test_body_key2", "test_body_val2");
        body.put("test_body_key3", "test_body_val3");
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        responseWrapper.getOutputStream().write(JsonUtility.toJson(body).getBytes());
        apiRequestResponseLoggingFilter.doFilterInternal(request, responseWrapper, filterChain);

        // check response header log setting
        HttpHeaders headersForChk = new HttpHeaders();
        headersForChk.add("test_header_key1", "*");
        headersForChk.add("test_header_key2", "test_header_val2");
        headersForChk.add("test_header_key2", "test_header_val21");
        headersForChk.add("test_header_key2", "test_header_val22");
        headersForChk.add("test_header_key2", "test_header_val23");
        headersForChk.add("test_header_key3", "*");

        // check response body log setting
        Map<String, Object> bodyForChk = new HashMap<>();
        Map<String, Object> bodyVal1ForChk = new HashMap<>();
        bodyVal1ForChk.put("test_body_key1_mapKey1", "test_body_key1_mapVal1");
        Map<String, Object> bodyKey1MapVal2ForChk = new HashMap<>();
        bodyKey1MapVal2ForChk.put("test_body_key1_mapKey2_subMapKey1", "test_body_key1_mapKey2_subMapVal1");
        bodyKey1MapVal2ForChk.put("test_body_key1_mapKey2_subMapKey2", Arrays.asList(1, 2, 3));
        Map<String, Object> list0MapForChk = new HashMap<>();
        list0MapForChk.put("list0MapKey0", "list0MapKey0");
        list0MapForChk.put("list0MapKey1", "list0MapKey2");
        Map<String, Object> list1MapForChk = new HashMap<>();
        list1MapForChk.put("list1MapKey0", "list1MapKey0");
        list1MapForChk.put("list1MapKey1", "*");
        bodyKey1MapVal2ForChk.put("test_body_key1_mapKey2_subMapKey3", Arrays.asList(list0MapForChk, list1MapForChk));
        bodyVal1ForChk.put("test_body_key1_mapKey2", bodyKey1MapVal2ForChk);
        bodyVal1ForChk.put("test_body_key1_mapKey3", "test_body_key1_mapVal3");
        bodyForChk.put("test_body_key1", bodyVal1ForChk);
        bodyForChk.put("test_body_key2", "test_body_val2");
        bodyForChk.put("test_body_key3", "test_body_val3");

        ResponseLogInfo responseLogInfo = new ResponseLogInfo();
        responseLogInfo.setStatusCode(200);
        responseLogInfo.setHeaders(JsonUtility.toJson(headersForChk));
        responseLogInfo.setBody(JsonUtility.toJson(bodyForChk));

        // check the response log
        Map<String, Object> responseLogMap = new LinkedHashMap<>();
        responseLogMap.put("res", responseLogInfo);
        // check the response log
        checkRequestLog("", responseLogMap);
    }

    /**
     * check response's header&body
     *
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void doFilterInternalCase_TestMask04() throws ServletException, IOException {

        // mock
        ReflectionTestUtils.setField(apiRequestResponseLoggingFilter, "messageSource", messageSourceMock);
        ReflectionTestUtils.setField(apiRequestResponseLoggingFilter, "includeHeaders", true);
        ReflectionTestUtils.setField(apiRequestResponseLoggingFilter, "includePayload", true);
        Mockito.when(messageSourceMock.getMessage(Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any())).thenThrow(new NoSuchMessageException(""));

        // url & mask target setting
        Map<String, Object> maskingUris = new HashMap<>();
        maskingUris.put("basket_basketNo_no1", "/v1/uq/ca/test11/(.*)");
        Map<String, Object> maskingItems = new HashMap<>();
        maskingItems.put("basket_basketNo_no1", "(test_header_key1|test_header_key3|test_body_key1_mapKey2_subMapKey2)");
        ReflectionTestUtils.setField(apiRequestResponseLoggingFilter, "maskingUris", maskingUris);
        ReflectionTestUtils.setField(apiRequestResponseLoggingFilter, "maskingItems", maskingItems);

        // request setting
        request = new MockHttpServletRequest();
        request.setRequestURI("/v1/uq/ca/test11/doFilterInternalCase_TestMask01");
        // response setting
        response = new MockHttpServletResponse();
        // response header log setting
        response.addHeader("test_header_key1", "test_header_val1");
        response.addHeader("test_header_key1", "test_header_val11");
        response.addHeader("test_header_key1", "test_header_val12");
        response.addHeader("test_header_key1", "test_header_val13");
        response.addHeader("test_header_key2", "test_header_val2");
        response.addHeader("test_header_key2", "test_header_val21");
        response.addHeader("test_header_key2", "test_header_val22");
        response.addHeader("test_header_key2", "test_header_val23");
        response.addHeader("test_header_key3", "test_header_val3");
        response.addHeader("test_header_key3", "test_header_val31");
        response.addHeader("test_header_key3", "test_header_val32");
        response.addHeader("test_header_key3", "test_header_val33");

        // response body log setting
        Map<String, Object> body = new HashMap<>();
        Map<String, Object> bodyVal1 = new HashMap<>();
        bodyVal1.put("test_body_key1_mapKey1", "test_body_key1_mapVal1");
        Map<String, Object> bodyKey1MapVal2 = new HashMap<>();
        bodyKey1MapVal2.put("test_body_key1_mapKey2_subMapKey1", "test_body_key1_mapKey2_subMapVal1");
        bodyKey1MapVal2.put("test_body_key1_mapKey2_subMapKey2", Arrays.asList(1, 2, 3));
        Map<String, Object> list0Map = new HashMap<>();
        list0Map.put("list0MapKey0", "list0MapKey0");
        list0Map.put("list0MapKey1", "list0MapKey2");
        Map<String, Object> list1Map = new HashMap<>();
        list1Map.put("list1MapKey0", "list1MapKey0");
        list1Map.put("list1MapKey1", "list1MapKey2");
        bodyKey1MapVal2.put("test_body_key1_mapKey2_subMapKey3", Arrays.asList(list0Map, list1Map));
        bodyVal1.put("test_body_key1_mapKey2", bodyKey1MapVal2);
        bodyVal1.put("test_body_key1_mapKey3", "test_body_key1_mapVal3");
        body.put("test_body_key1", bodyVal1);
        body.put("test_body_key2", "test_body_val2");
        body.put("test_body_key3", "test_body_val3");
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        responseWrapper.getOutputStream().write(JsonUtility.toJson(body).getBytes());
        apiRequestResponseLoggingFilter.doFilterInternal(request, responseWrapper, filterChain);

        // check response header log setting
        HttpHeaders headersForChk = new HttpHeaders();
        headersForChk.add("test_header_key1", "*");
        headersForChk.add("test_header_key2", "test_header_val2");
        headersForChk.add("test_header_key2", "test_header_val21");
        headersForChk.add("test_header_key2", "test_header_val22");
        headersForChk.add("test_header_key2", "test_header_val23");
        headersForChk.add("test_header_key3", "*");

        // check response body log setting
        Map<String, Object> bodyForChk = new HashMap<>();
        Map<String, Object> bodyVal1ForChk = new HashMap<>();
        bodyVal1ForChk.put("test_body_key1_mapKey1", "test_body_key1_mapVal1");
        Map<String, Object> bodyKey1MapVal2ForChk = new HashMap<>();
        bodyKey1MapVal2ForChk.put("test_body_key1_mapKey2_subMapKey1", "test_body_key1_mapKey2_subMapVal1");
        bodyKey1MapVal2ForChk.put("test_body_key1_mapKey2_subMapKey2", "*");
        Map<String, Object> list0MapForChk = new HashMap<>();
        list0MapForChk.put("list0MapKey0", "list0MapKey0");
        list0MapForChk.put("list0MapKey1", "list0MapKey2");
        Map<String, Object> list1MapForChk = new HashMap<>();
        list1MapForChk.put("list1MapKey0", "list1MapKey0");
        list1MapForChk.put("list1MapKey1", "list1MapKey2");
        bodyKey1MapVal2ForChk.put("test_body_key1_mapKey2_subMapKey3", Arrays.asList(list0MapForChk, list1MapForChk));
        bodyVal1ForChk.put("test_body_key1_mapKey2", bodyKey1MapVal2ForChk);
        bodyVal1ForChk.put("test_body_key1_mapKey3", "test_body_key1_mapVal3");
        bodyForChk.put("test_body_key1", bodyVal1ForChk);
        bodyForChk.put("test_body_key2", "test_body_val2");
        bodyForChk.put("test_body_key3", "test_body_val3");

        ResponseLogInfo responseLogInfo = new ResponseLogInfo();
        responseLogInfo.setStatusCode(200);
        responseLogInfo.setHeaders(JsonUtility.toJson(headersForChk));
        responseLogInfo.setBody(JsonUtility.toJson(bodyForChk));

        // check the response log
        Map<String, Object> responseLogMap = new LinkedHashMap<>();
        responseLogMap.put("res", responseLogInfo);
        // check the response log
        checkRequestLog("", responseLogMap);
    }

    /**
     * check response's header&body
     *
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void doFilterInternalCase_TestMask05() throws ServletException, IOException {

        // mock
        ReflectionTestUtils.setField(apiRequestResponseLoggingFilter, "messageSource", messageSourceMock);
        ReflectionTestUtils.setField(apiRequestResponseLoggingFilter, "includeHeaders", true);
        ReflectionTestUtils.setField(apiRequestResponseLoggingFilter, "includePayload", true);
        Mockito.when(messageSourceMock.getMessage(Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any())).thenThrow(new NoSuchMessageException(""));

        // url & mask target setting
        Map<String, Object> maskingUris = new HashMap<>();
        maskingUris.put("basket_basketNo_no1", "/v1/uq/ca/test11/(.*)");
        Map<String, Object> maskingItems = new HashMap<>();
        maskingItems.put("basket_basketNo_no1", "(test_header_key1|test_header_key3|test_body_key1_mapKey2_subMapKey3)");
        ReflectionTestUtils.setField(apiRequestResponseLoggingFilter, "maskingUris", maskingUris);
        ReflectionTestUtils.setField(apiRequestResponseLoggingFilter, "maskingItems", maskingItems);

        // request setting
        request = new MockHttpServletRequest();
        request.setRequestURI("/v1/uq/ca/test11/doFilterInternalCase_TestMask01");
        // response setting
        response = new MockHttpServletResponse();
        // response header log setting
        response.addHeader("test_header_key1", "test_header_val1");
        response.addHeader("test_header_key1", "test_header_val11");
        response.addHeader("test_header_key1", "test_header_val12");
        response.addHeader("test_header_key1", "test_header_val13");
        response.addHeader("test_header_key2", "test_header_val2");
        response.addHeader("test_header_key2", "test_header_val21");
        response.addHeader("test_header_key2", "test_header_val22");
        response.addHeader("test_header_key2", "test_header_val23");
        response.addHeader("test_header_key3", "test_header_val3");
        response.addHeader("test_header_key3", "test_header_val31");
        response.addHeader("test_header_key3", "test_header_val32");
        response.addHeader("test_header_key3", "test_header_val33");

        // response body log setting
        Map<String, Object> body = new HashMap<>();
        Map<String, Object> bodyVal1 = new HashMap<>();
        bodyVal1.put("test_body_key1_mapKey1", "test_body_key1_mapVal1");
        Map<String, Object> bodyKey1MapVal2 = new HashMap<>();
        bodyKey1MapVal2.put("test_body_key1_mapKey2_subMapKey1", "test_body_key1_mapKey2_subMapVal1");
        bodyKey1MapVal2.put("test_body_key1_mapKey2_subMapKey2", Arrays.asList(1, 2, 3));
        Map<String, Object> list0Map = new HashMap<>();
        list0Map.put("list0MapKey0", "list0MapKey0");
        list0Map.put("list0MapKey1", "list0MapKey2");
        Map<String, Object> list1Map = new HashMap<>();
        list1Map.put("list1MapKey0", "list1MapKey0");
        list1Map.put("list1MapKey1", "list1MapKey2");
        bodyKey1MapVal2.put("test_body_key1_mapKey2_subMapKey3", Arrays.asList(list0Map, list1Map));
        bodyVal1.put("test_body_key1_mapKey2", bodyKey1MapVal2);
        bodyVal1.put("test_body_key1_mapKey3", "test_body_key1_mapVal3");
        body.put("test_body_key1", bodyVal1);
        body.put("test_body_key2", "test_body_val2");
        body.put("test_body_key3", "test_body_val3");
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        responseWrapper.getOutputStream().write(JsonUtility.toJson(body).getBytes());
        apiRequestResponseLoggingFilter.doFilterInternal(request, responseWrapper, filterChain);

        // check response header log setting
        HttpHeaders headersForChk = new HttpHeaders();
        headersForChk.add("test_header_key1", "*");
        headersForChk.add("test_header_key2", "test_header_val2");
        headersForChk.add("test_header_key2", "test_header_val21");
        headersForChk.add("test_header_key2", "test_header_val22");
        headersForChk.add("test_header_key2", "test_header_val23");
        headersForChk.add("test_header_key3", "*");

        // check response body log setting
        Map<String, Object> bodyForChk = new HashMap<>();
        Map<String, Object> bodyVal1ForChk = new HashMap<>();
        bodyVal1ForChk.put("test_body_key1_mapKey1", "test_body_key1_mapVal1");
        Map<String, Object> bodyKey1MapVal2ForChk = new HashMap<>();
        bodyKey1MapVal2ForChk.put("test_body_key1_mapKey2_subMapKey1", "test_body_key1_mapKey2_subMapVal1");
        bodyKey1MapVal2ForChk.put("test_body_key1_mapKey2_subMapKey2", Arrays.asList(1, 2, 3));
        bodyKey1MapVal2ForChk.put("test_body_key1_mapKey2_subMapKey3", "*");
        bodyVal1ForChk.put("test_body_key1_mapKey2", bodyKey1MapVal2ForChk);
        bodyVal1ForChk.put("test_body_key1_mapKey3", "test_body_key1_mapVal3");
        bodyForChk.put("test_body_key1", bodyVal1ForChk);
        bodyForChk.put("test_body_key2", "test_body_val2");
        bodyForChk.put("test_body_key3", "test_body_val3");

        ResponseLogInfo responseLogInfo = new ResponseLogInfo();
        responseLogInfo.setStatusCode(200);
        responseLogInfo.setHeaders(JsonUtility.toJson(headersForChk));
        responseLogInfo.setBody(JsonUtility.toJson(bodyForChk));

        // check the response log
        Map<String, Object> responseLogMap = new LinkedHashMap<>();
        responseLogMap.put("res", responseLogInfo);
        // check the response log
        checkRequestLog("", responseLogMap);
    }

    /**
     * check request's header&body
     *
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void createMessage_TestMask01() throws ServletException, IOException {

        // mock
        ReflectionTestUtils.setField(apiRequestResponseLoggingFilter, "messageSource", messageSourceMock);
        ReflectionTestUtils.setField(apiRequestResponseLoggingFilter, "includeHeaders", true);
        ReflectionTestUtils.setField(apiRequestResponseLoggingFilter, "includePayload", true);
        Mockito.when(messageSourceMock.getMessage(Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any())).thenThrow(new NoSuchMessageException(""));

        // url & mask target setting
        Map<String, Object> maskingUris = new HashMap<>();
        maskingUris.put("basket_basketNo_no1", "/v1/uq/ca/test11/(.*)");
        Map<String, Object> maskingItems = new HashMap<>();
        maskingItems.put("basket_basketNo_no1", "(test_header_key2|test_body_key3)");
        ReflectionTestUtils.setField(apiRequestResponseLoggingFilter, "maskingUris", maskingUris);
        ReflectionTestUtils.setField(apiRequestResponseLoggingFilter, "maskingItems", maskingItems);

        // request setting
        request = new MockHttpServletRequest();
        request.setRequestURI("/v1/uq/ca/test11/doFilterInternalCase_TestMask01");

        // request header log setting
        request.addHeader("test_header_key1", "test_header_val1");
        request.addHeader("test_header_key2", "test_header_val2");
        request.addHeader("test_header_key3", "test_header_val3");

        // request body log setting
        Map<String, Object> body = new HashMap<>();
        body.put("test_body_key1", "test_body_val1");
        body.put("test_body_key2", "test_body_val2");
        body.put("test_body_key3", "test_body_val3");
        TestBodyReaderHttpServletRequestWrapper testBodyReaderHttpServletRequestWrapper = new TestBodyReaderHttpServletRequestWrapper(request, JsonUtility.toJson(body));

        Assert.assertEquals("{\"req\":{\"method\":\"\",\"headers\":\"{\\\"test_header_key1\\\":[\\\"test_header_val1\\\"],\\\"test_header_key3\\\":[\\\"test_header_val3\\\"],\\\"test_header_key2\\\":[\\\"*\\\"]}\",\"body\":\"{\\\"test_body_key2\\\":\\\"test_body_val2\\\",\\\"test_body_key1\\\":\\\"test_body_val1\\\",\\\"test_body_key3\\\":\\\"*\\\"}\",\"url\":\"http://localhost/v1/uq/ca/test11/doFilterInternalCase_TestMask01\",\"originalUrl\":\"/v1/uq/ca/test11/doFilterInternalCase_TestMask01\",\"httpVersion\":\"HTTP/1.1\"}}", ReflectionTestUtils.invokeMethod(apiRequestResponseLoggingFilter, "createMessage", testBodyReaderHttpServletRequestWrapper, "[", "]"));

    }

    /**
     * check request's header&body
     *
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void createMessage_TestMask02() throws ServletException, IOException {

        // mock
        ReflectionTestUtils.setField(apiRequestResponseLoggingFilter, "messageSource", messageSourceMock);
        ReflectionTestUtils.setField(apiRequestResponseLoggingFilter, "includeHeaders", true);
        ReflectionTestUtils.setField(apiRequestResponseLoggingFilter, "includePayload", true);
        Mockito.when(messageSourceMock.getMessage(Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any())).thenThrow(new NoSuchMessageException(""));

        // url & mask target setting
        Map<String, Object> maskingUris = new HashMap<>();
        maskingUris.put("basket_basketNo_no1", "/v1/uq/ca/test11/(.*)");
        Map<String, Object> maskingItems = new HashMap<>();
        maskingItems.put("basket_basketNo_no1", "(test_header_key2|test_body_key2)");
        ReflectionTestUtils.setField(apiRequestResponseLoggingFilter, "maskingUris", maskingUris);
        ReflectionTestUtils.setField(apiRequestResponseLoggingFilter, "maskingItems", maskingItems);

        // request setting
        request = new MockHttpServletRequest();
        request.setRequestURI("/v1/uq/ca/test11/doFilterInternalCase_TestMask01");

        // request header log setting
        request.addHeader("test_header_key1", "test_header_val1");
        request.addHeader("test_header_key1", "test_header_val11");
        request.addHeader("test_header_key1", "test_header_val12");
        request.addHeader("test_header_key1", "test_header_val13");
        request.addHeader("test_header_key2", "test_header_val2");
        request.addHeader("test_header_key2", "test_header_val21");
        request.addHeader("test_header_key2", "test_header_val22");
        request.addHeader("test_header_key2", "test_header_val23");
        request.addHeader("test_header_key3", "test_header_val3");
        request.addHeader("test_header_key3", "test_header_val31");
        request.addHeader("test_header_key3", "test_header_val32");
        request.addHeader("test_header_key3", "test_header_val33");

        // request body log setting
        Map<String, Object> body = new HashMap<>();
        Map<String, Object> bodyVal1 = new HashMap<>();
        bodyVal1.put("test_body_key1_mapKey1", "test_body_key1_mapVal1");
        Map<String, Object> bodyKey1MapVal2 = new HashMap<>();
        bodyKey1MapVal2.put("test_body_key1_mapKey2_subMapKey1", "test_body_key1_mapKey2_subMapVal1");
        bodyKey1MapVal2.put("test_body_key1_mapKey2_subMapKey2", Arrays.asList(1, 2, 3));
        Map<String, Object> list0Map = new HashMap<>();
        list0Map.put("list0MapKey0", "list0MapKey0");
        list0Map.put("list0MapKey1", "list0MapKey2");
        Map<String, Object> list1Map = new HashMap<>();
        list1Map.put("list1MapKey0", "list1MapKey0");
        list1Map.put("list1MapKey1", "list1MapKey2");
        bodyKey1MapVal2.put("test_body_key1_mapKey2_subMapKey3", Arrays.asList(list0Map, list1Map));
        bodyVal1.put("test_body_key1_mapKey2", bodyKey1MapVal2);
        bodyVal1.put("test_body_key1_mapKey3", "test_body_key1_mapVal3");
        body.put("test_body_key1", bodyVal1);
        body.put("test_body_key2", "test_body_val2");
        body.put("test_body_key3", "test_body_val3");
        TestBodyReaderHttpServletRequestWrapper testBodyReaderHttpServletRequestWrapper = new TestBodyReaderHttpServletRequestWrapper(request, JsonUtility.toJson(body));

        Assert.assertEquals("{\"req\":{\"method\":\"\",\"headers\":\"{\\\"test_header_key1\\\":[\\\"test_header_val1\\\",\\\"test_header_val11\\\",\\\"test_header_val12\\\",\\\"test_header_val13\\\"],\\\"test_header_key3\\\":[\\\"test_header_val3\\\",\\\"test_header_val31\\\",\\\"test_header_val32\\\",\\\"test_header_val33\\\"],\\\"test_header_key2\\\":[\\\"*\\\"]}\",\"body\":\"{\\\"test_body_key2\\\":\\\"*\\\",\\\"test_body_key1\\\":{\\\"test_body_key1_mapKey1\\\":\\\"test_body_key1_mapVal1\\\",\\\"test_body_key1_mapKey2\\\":{\\\"test_body_key1_mapKey2_subMapKey2\\\":[1,2,3],\\\"test_body_key1_mapKey2_subMapKey3\\\":[{\\\"list0MapKey0\\\":\\\"list0MapKey0\\\",\\\"list0MapKey1\\\":\\\"list0MapKey2\\\"},{\\\"list1MapKey0\\\":\\\"list1MapKey0\\\",\\\"list1MapKey1\\\":\\\"list1MapKey2\\\"}],\\\"test_body_key1_mapKey2_subMapKey1\\\":\\\"test_body_key1_mapKey2_subMapVal1\\\"},\\\"test_body_key1_mapKey3\\\":\\\"test_body_key1_mapVal3\\\"},\\\"test_body_key3\\\":\\\"test_body_val3\\\"}\",\"url\":\"http://localhost/v1/uq/ca/test11/doFilterInternalCase_TestMask01\",\"originalUrl\":\"/v1/uq/ca/test11/doFilterInternalCase_TestMask01\",\"httpVersion\":\"HTTP/1.1\"}}", ReflectionTestUtils.invokeMethod(apiRequestResponseLoggingFilter, "createMessage", testBodyReaderHttpServletRequestWrapper, "[", "]"));

    }

    /**
     * check request's header&body
     *
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void createMessage_TestMask03() throws ServletException, IOException {

        // mock
        ReflectionTestUtils.setField(apiRequestResponseLoggingFilter, "messageSource", messageSourceMock);
        ReflectionTestUtils.setField(apiRequestResponseLoggingFilter, "includeHeaders", true);
        ReflectionTestUtils.setField(apiRequestResponseLoggingFilter, "includePayload", true);
        Mockito.when(messageSourceMock.getMessage(Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any())).thenThrow(new NoSuchMessageException(""));

        // url & mask target setting
        Map<String, Object> maskingUris = new HashMap<>();
        maskingUris.put("basket_basketNo_no1", "/v1/uq/ca/test11/(.*)");
        Map<String, Object> maskingItems = new HashMap<>();
        maskingItems.put("basket_basketNo_no1", "(test_header_key2|list1MapKey1)");
        ReflectionTestUtils.setField(apiRequestResponseLoggingFilter, "maskingUris", maskingUris);
        ReflectionTestUtils.setField(apiRequestResponseLoggingFilter, "maskingItems", maskingItems);

        // request setting
        request = new MockHttpServletRequest();
        request.setRequestURI("/v1/uq/ca/test11/doFilterInternalCase_TestMask01");

        // request header log setting
        request.addHeader("test_header_key1", "test_header_val1");
        request.addHeader("test_header_key1", "test_header_val11");
        request.addHeader("test_header_key1", "test_header_val12");
        request.addHeader("test_header_key1", "test_header_val13");
        request.addHeader("test_header_key2", "test_header_val2");
        request.addHeader("test_header_key2", "test_header_val21");
        request.addHeader("test_header_key2", "test_header_val22");
        request.addHeader("test_header_key2", "test_header_val23");
        request.addHeader("test_header_key3", "test_header_val3");
        request.addHeader("test_header_key3", "test_header_val31");
        request.addHeader("test_header_key3", "test_header_val32");
        request.addHeader("test_header_key3", "test_header_val33");

        // request body log setting
        Map<String, Object> body = new HashMap<>();
        Map<String, Object> bodyVal1 = new HashMap<>();
        bodyVal1.put("test_body_key1_mapKey1", "test_body_key1_mapVal1");
        Map<String, Object> bodyKey1MapVal2 = new HashMap<>();
        bodyKey1MapVal2.put("test_body_key1_mapKey2_subMapKey1", "test_body_key1_mapKey2_subMapVal1");
        bodyKey1MapVal2.put("test_body_key1_mapKey2_subMapKey2", Arrays.asList(1, 2, 3));
        Map<String, Object> list0Map = new HashMap<>();
        list0Map.put("list0MapKey0", "list0MapKey0");
        list0Map.put("list0MapKey1", "list0MapKey2");
        Map<String, Object> list1Map = new HashMap<>();
        list1Map.put("list1MapKey0", "list1MapKey0");
        list1Map.put("list1MapKey1", "list1MapKey2");
        bodyKey1MapVal2.put("test_body_key1_mapKey2_subMapKey3", Arrays.asList(list0Map, list1Map));
        bodyVal1.put("test_body_key1_mapKey2", bodyKey1MapVal2);
        bodyVal1.put("test_body_key1_mapKey3", "test_body_key1_mapVal3");
        body.put("test_body_key1", bodyVal1);
        body.put("test_body_key2", "test_body_val2");
        body.put("test_body_key3", "test_body_val3");
        TestBodyReaderHttpServletRequestWrapper testBodyReaderHttpServletRequestWrapper = new TestBodyReaderHttpServletRequestWrapper(request, JsonUtility.toJson(body));

        Assert.assertEquals("{\"req\":{\"method\":\"\",\"headers\":\"{\\\"test_header_key1\\\":[\\\"test_header_val1\\\",\\\"test_header_val11\\\",\\\"test_header_val12\\\",\\\"test_header_val13\\\"],\\\"test_header_key3\\\":[\\\"test_header_val3\\\",\\\"test_header_val31\\\",\\\"test_header_val32\\\",\\\"test_header_val33\\\"],\\\"test_header_key2\\\":[\\\"*\\\"]}\",\"body\":\"{\\\"test_body_key2\\\":\\\"test_body_val2\\\",\\\"test_body_key1\\\":{\\\"test_body_key1_mapKey1\\\":\\\"test_body_key1_mapVal1\\\",\\\"test_body_key1_mapKey2\\\":{\\\"test_body_key1_mapKey2_subMapKey2\\\":[1,2,3],\\\"test_body_key1_mapKey2_subMapKey3\\\":[{\\\"list0MapKey0\\\":\\\"list0MapKey0\\\",\\\"list0MapKey1\\\":\\\"list0MapKey2\\\"},{\\\"list1MapKey0\\\":\\\"list1MapKey0\\\",\\\"list1MapKey1\\\":\\\"*\\\"}],\\\"test_body_key1_mapKey2_subMapKey1\\\":\\\"test_body_key1_mapKey2_subMapVal1\\\"},\\\"test_body_key1_mapKey3\\\":\\\"test_body_key1_mapVal3\\\"},\\\"test_body_key3\\\":\\\"test_body_val3\\\"}\",\"url\":\"http://localhost/v1/uq/ca/test11/doFilterInternalCase_TestMask01\",\"originalUrl\":\"/v1/uq/ca/test11/doFilterInternalCase_TestMask01\",\"httpVersion\":\"HTTP/1.1\"}}", ReflectionTestUtils.invokeMethod(apiRequestResponseLoggingFilter, "createMessage", testBodyReaderHttpServletRequestWrapper, "[", "]"));

    }

    /**
     * check request's header&body
     *
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void createMessage_TestMask04() throws ServletException, IOException {

        // mock
        ReflectionTestUtils.setField(apiRequestResponseLoggingFilter, "messageSource", messageSourceMock);
        ReflectionTestUtils.setField(apiRequestResponseLoggingFilter, "includeHeaders", true);
        ReflectionTestUtils.setField(apiRequestResponseLoggingFilter, "includePayload", true);
        Mockito.when(messageSourceMock.getMessage(Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any())).thenThrow(new NoSuchMessageException(""));

        // url & mask target setting
        Map<String, Object> maskingUris = new HashMap<>();
        maskingUris.put("basket_basketNo_no1", "/v1/uq/ca/test11/(.*)");
        Map<String, Object> maskingItems = new HashMap<>();
        maskingItems.put("basket_basketNo_no1", "(test_header_key2|test_body_key1_mapKey2_subMapKey2)");
        ReflectionTestUtils.setField(apiRequestResponseLoggingFilter, "maskingUris", maskingUris);
        ReflectionTestUtils.setField(apiRequestResponseLoggingFilter, "maskingItems", maskingItems);

        // request setting
        request = new MockHttpServletRequest();
        request.setRequestURI("/v1/uq/ca/test11/doFilterInternalCase_TestMask01");

        // request header log setting
        request.addHeader("test_header_key1", "test_header_val1");
        request.addHeader("test_header_key1", "test_header_val11");
        request.addHeader("test_header_key1", "test_header_val12");
        request.addHeader("test_header_key1", "test_header_val13");
        request.addHeader("test_header_key2", "test_header_val2");
        request.addHeader("test_header_key2", "test_header_val21");
        request.addHeader("test_header_key2", "test_header_val22");
        request.addHeader("test_header_key2", "test_header_val23");
        request.addHeader("test_header_key3", "test_header_val3");
        request.addHeader("test_header_key3", "test_header_val31");
        request.addHeader("test_header_key3", "test_header_val32");
        request.addHeader("test_header_key3", "test_header_val33");

        // request body log setting
        Map<String, Object> body = new HashMap<>();
        Map<String, Object> bodyVal1 = new HashMap<>();
        bodyVal1.put("test_body_key1_mapKey1", "test_body_key1_mapVal1");
        Map<String, Object> bodyKey1MapVal2 = new HashMap<>();
        bodyKey1MapVal2.put("test_body_key1_mapKey2_subMapKey1", "test_body_key1_mapKey2_subMapVal1");
        bodyKey1MapVal2.put("test_body_key1_mapKey2_subMapKey2", Arrays.asList(1, 2, 3));
        Map<String, Object> list0Map = new HashMap<>();
        list0Map.put("list0MapKey0", "list0MapKey0");
        list0Map.put("list0MapKey1", "list0MapKey2");
        Map<String, Object> list1Map = new HashMap<>();
        list1Map.put("list1MapKey0", "list1MapKey0");
        list1Map.put("list1MapKey1", "list1MapKey2");
        bodyKey1MapVal2.put("test_body_key1_mapKey2_subMapKey3", Arrays.asList(list0Map, list1Map));
        bodyVal1.put("test_body_key1_mapKey2", bodyKey1MapVal2);
        bodyVal1.put("test_body_key1_mapKey3", "test_body_key1_mapVal3");
        body.put("test_body_key1", bodyVal1);
        body.put("test_body_key2", "test_body_val2");
        body.put("test_body_key3", "test_body_val3");
        TestBodyReaderHttpServletRequestWrapper testBodyReaderHttpServletRequestWrapper = new TestBodyReaderHttpServletRequestWrapper(request, JsonUtility.toJson(body));

        Assert.assertEquals("{\"req\":{\"method\":\"\",\"headers\":\"{\\\"test_header_key1\\\":[\\\"test_header_val1\\\",\\\"test_header_val11\\\",\\\"test_header_val12\\\",\\\"test_header_val13\\\"],\\\"test_header_key3\\\":[\\\"test_header_val3\\\",\\\"test_header_val31\\\",\\\"test_header_val32\\\",\\\"test_header_val33\\\"],\\\"test_header_key2\\\":[\\\"*\\\"]}\",\"body\":\"{\\\"test_body_key2\\\":\\\"test_body_val2\\\",\\\"test_body_key1\\\":{\\\"test_body_key1_mapKey1\\\":\\\"test_body_key1_mapVal1\\\",\\\"test_body_key1_mapKey2\\\":{\\\"test_body_key1_mapKey2_subMapKey2\\\":\\\"*\\\",\\\"test_body_key1_mapKey2_subMapKey3\\\":[{\\\"list0MapKey0\\\":\\\"list0MapKey0\\\",\\\"list0MapKey1\\\":\\\"list0MapKey2\\\"},{\\\"list1MapKey0\\\":\\\"list1MapKey0\\\",\\\"list1MapKey1\\\":\\\"list1MapKey2\\\"}],\\\"test_body_key1_mapKey2_subMapKey1\\\":\\\"test_body_key1_mapKey2_subMapVal1\\\"},\\\"test_body_key1_mapKey3\\\":\\\"test_body_key1_mapVal3\\\"},\\\"test_body_key3\\\":\\\"test_body_val3\\\"}\",\"url\":\"http://localhost/v1/uq/ca/test11/doFilterInternalCase_TestMask01\",\"originalUrl\":\"/v1/uq/ca/test11/doFilterInternalCase_TestMask01\",\"httpVersion\":\"HTTP/1.1\"}}", ReflectionTestUtils.invokeMethod(apiRequestResponseLoggingFilter, "createMessage", testBodyReaderHttpServletRequestWrapper, "[", "]"));

    }

    /**
     * check request's header&body
     *
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void createMessage_TestMask05() throws ServletException, IOException {

        // mock
        ReflectionTestUtils.setField(apiRequestResponseLoggingFilter, "messageSource", messageSourceMock);
        ReflectionTestUtils.setField(apiRequestResponseLoggingFilter, "includeHeaders", true);
        ReflectionTestUtils.setField(apiRequestResponseLoggingFilter, "includePayload", true);
        Mockito.when(messageSourceMock.getMessage(Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any())).thenThrow(new NoSuchMessageException(""));

        // url & mask target setting
        Map<String, Object> maskingUris = new HashMap<>();
        maskingUris.put("basket_basketNo_no1", "/v1/uq/ca/test11/(.*)");
        Map<String, Object> maskingItems = new HashMap<>();
        maskingItems.put("basket_basketNo_no1", "(test_header_key2|test_body_key1_mapKey2_subMapKey3)");
        ReflectionTestUtils.setField(apiRequestResponseLoggingFilter, "maskingUris", maskingUris);
        ReflectionTestUtils.setField(apiRequestResponseLoggingFilter, "maskingItems", maskingItems);

        // request setting
        request = new MockHttpServletRequest();
        request.setRequestURI("/v1/uq/ca/test11/doFilterInternalCase_TestMask01");

        // request header log setting
        request.addHeader("test_header_key1", "test_header_val1");
        request.addHeader("test_header_key1", "test_header_val11");
        request.addHeader("test_header_key1", "test_header_val12");
        request.addHeader("test_header_key1", "test_header_val13");
        request.addHeader("test_header_key2", "test_header_val2");
        request.addHeader("test_header_key2", "test_header_val21");
        request.addHeader("test_header_key2", "test_header_val22");
        request.addHeader("test_header_key2", "test_header_val23");
        request.addHeader("test_header_key3", "test_header_val3");
        request.addHeader("test_header_key3", "test_header_val31");
        request.addHeader("test_header_key3", "test_header_val32");
        request.addHeader("test_header_key3", "test_header_val33");

        // request body log setting
        Map<String, Object> body = new HashMap<>();
        Map<String, Object> bodyVal1 = new HashMap<>();
        bodyVal1.put("test_body_key1_mapKey1", "test_body_key1_mapVal1");
        Map<String, Object> bodyKey1MapVal2 = new HashMap<>();
        bodyKey1MapVal2.put("test_body_key1_mapKey2_subMapKey1", "test_body_key1_mapKey2_subMapVal1");
        bodyKey1MapVal2.put("test_body_key1_mapKey2_subMapKey2", Arrays.asList(1, 2, 3));
        Map<String, Object> list0Map = new HashMap<>();
        list0Map.put("list0MapKey0", "list0MapKey0");
        list0Map.put("list0MapKey1", "list0MapKey2");
        Map<String, Object> list1Map = new HashMap<>();
        list1Map.put("list1MapKey0", "list1MapKey0");
        list1Map.put("list1MapKey1", "list1MapKey2");
        bodyKey1MapVal2.put("test_body_key1_mapKey2_subMapKey3", Arrays.asList(list0Map, list1Map));
        bodyVal1.put("test_body_key1_mapKey2", bodyKey1MapVal2);
        bodyVal1.put("test_body_key1_mapKey3", "test_body_key1_mapVal3");
        body.put("test_body_key1", bodyVal1);
        body.put("test_body_key2", "test_body_val2");
        body.put("test_body_key3", "test_body_val3");
        TestBodyReaderHttpServletRequestWrapper testBodyReaderHttpServletRequestWrapper = new TestBodyReaderHttpServletRequestWrapper(request, JsonUtility.toJson(body));

        Assert.assertEquals("{\"req\":{\"method\":\"\",\"headers\":\"{\\\"test_header_key1\\\":[\\\"test_header_val1\\\",\\\"test_header_val11\\\",\\\"test_header_val12\\\",\\\"test_header_val13\\\"],\\\"test_header_key3\\\":[\\\"test_header_val3\\\",\\\"test_header_val31\\\",\\\"test_header_val32\\\",\\\"test_header_val33\\\"],\\\"test_header_key2\\\":[\\\"*\\\"]}\",\"body\":\"{\\\"test_body_key2\\\":\\\"test_body_val2\\\",\\\"test_body_key1\\\":{\\\"test_body_key1_mapKey1\\\":\\\"test_body_key1_mapVal1\\\",\\\"test_body_key1_mapKey2\\\":{\\\"test_body_key1_mapKey2_subMapKey2\\\":[1,2,3],\\\"test_body_key1_mapKey2_subMapKey3\\\":\\\"*\\\",\\\"test_body_key1_mapKey2_subMapKey1\\\":\\\"test_body_key1_mapKey2_subMapVal1\\\"},\\\"test_body_key1_mapKey3\\\":\\\"test_body_key1_mapVal3\\\"},\\\"test_body_key3\\\":\\\"test_body_val3\\\"}\",\"url\":\"http://localhost/v1/uq/ca/test11/doFilterInternalCase_TestMask01\",\"originalUrl\":\"/v1/uq/ca/test11/doFilterInternalCase_TestMask01\",\"httpVersion\":\"HTTP/1.1\"}}", ReflectionTestUtils.invokeMethod(apiRequestResponseLoggingFilter, "createMessage", testBodyReaderHttpServletRequestWrapper, "[", "]"));

    }

    /**
     * check request's body
     *
     * body type:int
     *
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void createMessage_TestMask06() throws ServletException, IOException {

        // mock
        ReflectionTestUtils.setField(apiRequestResponseLoggingFilter, "messageSource", messageSourceMock);
        ReflectionTestUtils.setField(apiRequestResponseLoggingFilter, "includeHeaders", true);
        ReflectionTestUtils.setField(apiRequestResponseLoggingFilter, "includePayload", true);
        Mockito.when(messageSourceMock.getMessage(Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any())).thenThrow(new NoSuchMessageException(""));

        // url & mask target setting
        Map<String, Object> maskingUris = new HashMap<>();
        maskingUris.put("basket_basketNo_no1", "/v1/uq/ca/test11/(.*)");
        Map<String, Object> maskingItems = new HashMap<>();
        maskingItems.put("basket_basketNo_no1", "(test_header_key2|test_body_key1_mapKey2_subMapKey3)");
        ReflectionTestUtils.setField(apiRequestResponseLoggingFilter, "maskingUris", maskingUris);
        ReflectionTestUtils.setField(apiRequestResponseLoggingFilter, "maskingItems", maskingItems);

        // request setting
        request = new MockHttpServletRequest();
        request.setRequestURI("/v1/uq/ca/test11/doFilterInternalCase_TestMask01");

        // request header log setting
        request.addHeader("test_header_key1", "test_header_val1");
        request.addHeader("test_header_key1", "test_header_val11");
        request.addHeader("test_header_key1", "test_header_val12");
        request.addHeader("test_header_key1", "test_header_val13");
        request.addHeader("test_header_key2", "test_header_val2");
        request.addHeader("test_header_key2", "test_header_val21");
        request.addHeader("test_header_key2", "test_header_val22");
        request.addHeader("test_header_key2", "test_header_val23");
        request.addHeader("test_header_key3", "test_header_val3");
        request.addHeader("test_header_key3", "test_header_val31");
        request.addHeader("test_header_key3", "test_header_val32");
        request.addHeader("test_header_key3", "test_header_val33");

        // request body log setting
        TestBodyReaderHttpServletRequestWrapper testBodyReaderHttpServletRequestWrapper = new TestBodyReaderHttpServletRequestWrapper(request, JsonUtility.toJson(12345));

        Assert.assertEquals("{\"req\":{\"method\":\"\",\"headers\":\"{\\\"test_header_key1\\\":[\\\"test_header_val1\\\",\\\"test_header_val11\\\",\\\"test_header_val12\\\",\\\"test_header_val13\\\"],\\\"test_header_key3\\\":[\\\"test_header_val3\\\",\\\"test_header_val31\\\",\\\"test_header_val32\\\",\\\"test_header_val33\\\"],\\\"test_header_key2\\\":[\\\"*\\\"]}\",\"body\":\"12345\",\"url\":\"http://localhost/v1/uq/ca/test11/doFilterInternalCase_TestMask01\",\"originalUrl\":\"/v1/uq/ca/test11/doFilterInternalCase_TestMask01\",\"httpVersion\":\"HTTP/1.1\"}}", ReflectionTestUtils.invokeMethod(apiRequestResponseLoggingFilter, "createMessage", testBodyReaderHttpServletRequestWrapper, "[", "]"));

    }

    /**
     * check request's body
     *
     * body type:List
     *
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void createMessage_TestMask07() throws ServletException, IOException {

        // mock
        ReflectionTestUtils.setField(apiRequestResponseLoggingFilter, "messageSource", messageSourceMock);
        ReflectionTestUtils.setField(apiRequestResponseLoggingFilter, "includeHeaders", true);
        ReflectionTestUtils.setField(apiRequestResponseLoggingFilter, "includePayload", true);
        Mockito.when(messageSourceMock.getMessage(Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any())).thenThrow(new NoSuchMessageException(""));

        // url & mask target setting
        Map<String, Object> maskingUris = new HashMap<>();
        maskingUris.put("basket_basketNo_no1", "/v1/uq/ca/test11/(.*)");
        Map<String, Object> maskingItems = new HashMap<>();
        maskingItems.put("basket_basketNo_no1", "(test_header_key2|test_body_key1_mapKey2_subMapKey3)");
        ReflectionTestUtils.setField(apiRequestResponseLoggingFilter, "maskingUris", maskingUris);
        ReflectionTestUtils.setField(apiRequestResponseLoggingFilter, "maskingItems", maskingItems);

        // request setting
        request = new MockHttpServletRequest();
        request.setRequestURI("/v1/uq/ca/test11/doFilterInternalCase_TestMask01");

        // request header log setting
        request.addHeader("test_header_key1", "test_header_val1");
        request.addHeader("test_header_key1", "test_header_val11");
        request.addHeader("test_header_key1", "test_header_val12");
        request.addHeader("test_header_key1", "test_header_val13");
        request.addHeader("test_header_key2", "test_header_val2");
        request.addHeader("test_header_key2", "test_header_val21");
        request.addHeader("test_header_key2", "test_header_val22");
        request.addHeader("test_header_key2", "test_header_val23");
        request.addHeader("test_header_key3", "test_header_val3");
        request.addHeader("test_header_key3", "test_header_val31");
        request.addHeader("test_header_key3", "test_header_val32");
        request.addHeader("test_header_key3", "test_header_val33");

        // request body log setting
        TestBodyReaderHttpServletRequestWrapper testBodyReaderHttpServletRequestWrapper = new TestBodyReaderHttpServletRequestWrapper(request, JsonUtility.toJson(Arrays.asList(Arrays.asList("123", "456", "789"), "456", "789")));

        Assert.assertEquals("{\"req\":{\"method\":\"\",\"headers\":\"{\\\"test_header_key1\\\":[\\\"test_header_val1\\\",\\\"test_header_val11\\\",\\\"test_header_val12\\\",\\\"test_header_val13\\\"],\\\"test_header_key3\\\":[\\\"test_header_val3\\\",\\\"test_header_val31\\\",\\\"test_header_val32\\\",\\\"test_header_val33\\\"],\\\"test_header_key2\\\":[\\\"*\\\"]}\",\"body\":\"[[\\\"123\\\",\\\"456\\\",\\\"789\\\"],\\\"456\\\",\\\"789\\\"]\",\"url\":\"http://localhost/v1/uq/ca/test11/doFilterInternalCase_TestMask01\",\"originalUrl\":\"/v1/uq/ca/test11/doFilterInternalCase_TestMask01\",\"httpVersion\":\"HTTP/1.1\"}}", ReflectionTestUtils.invokeMethod(apiRequestResponseLoggingFilter, "createMessage", testBodyReaderHttpServletRequestWrapper, "[", "]"));

    }


    @Test
    public void doFilterInternalCase11() throws ServletException, IOException {
        // QueryString
        apiRequestResponseLoggingFilter.setIncludeQueryString(true);
        // client info
        request.setSession(session);
        apiRequestResponseLoggingFilter.setIncludeClientInfo(true);
        // header
        apiRequestResponseLoggingFilter.setIncludeHeaders(true);
        // Payload
        apiRequestResponseLoggingFilter.setIncludePayload(true);
        request.addHeader(MdcKeyEnum.AMAZON_TRACE_ID.getKey(), "Self=testSelf1;Root=testRoot1");
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        responseWrapper.setCharacterEncoding("noexist");
        responseWrapper.getOutputStream().write("OK".getBytes());
        //
        apiRequestResponseLoggingFilter.doFilterInternal(request, responseWrapper, filterChain);

        // check the response log
        Map<String, Object> responseLogMap = new LinkedHashMap<>();
        ResponseLogInfo responseLogInfo = new ResponseLogInfo();
        responseLogInfo.setStatusCode(200);
        responseLogInfo.setBody("\"[unknown]\"");

        HttpHeaders headers = new HttpHeaders();
        headers.add("head1Name", "head1Value");
        headers.add("head2Name", "head2Value");
        headers.add(MdcKeyEnum.AMAZON_TRACE_ID.getKey(), "Self=testSelf2;Root=testRoot2");
        responseLogInfo.setHeaders(JsonUtility.toJson(headers));

        responseLogMap.put("res", responseLogInfo);
        // check the response log
        checkRequestLog("", responseLogMap);
    }

    private void testNotSupportContentType(String ContentType) throws ServletException, IOException {
        response.setContentType(ContentType);
        // QueryString
        apiRequestResponseLoggingFilter.setIncludeQueryString(true);
        // client info
        request.setSession(session);
        apiRequestResponseLoggingFilter.setIncludeClientInfo(true);
        // header
        apiRequestResponseLoggingFilter.setIncludeHeaders(true);
        // Payload
        apiRequestResponseLoggingFilter.setIncludePayload(true);

        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        responseWrapper.getOutputStream().write("OK".getBytes());

        apiRequestResponseLoggingFilter.doFilterInternal(request, response, filterChain);

        // check the response log
        Map<String, Object> responseLogMap = new LinkedHashMap<>();
        ResponseLogInfo responseLogInfo = new ResponseLogInfo();
        responseLogInfo.setStatusCode(200);
        HttpHeaders headers = new HttpHeaders();
        headers.add("head1Name", "head1Value");
        headers.add("head2Name", "head2Value");
        headers.add(MdcKeyEnum.AMAZON_TRACE_ID.getKey(), "Self=testSelf2;Root=testRoot2");
        headers.add("Content-Type", ContentType);
        responseLogInfo.setHeaders(JsonUtility.toJson(headers));
        responseLogMap.put("res", responseLogInfo);
        // check the response log
        checkRequestLog("", responseLogMap);
    }

    private void testNotSupportContentType1(String ContentType) throws ServletException, IOException {
        response.setContentType(ContentType);
        // QueryString
        apiRequestResponseLoggingFilter.setIncludeQueryString(true);
        // client info
        request.setSession(session);
        apiRequestResponseLoggingFilter.setIncludeClientInfo(true);
        // header
        apiRequestResponseLoggingFilter.setIncludeHeaders(true);
        // Payload
        apiRequestResponseLoggingFilter.setIncludePayload(true);

        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        responseWrapper.getOutputStream().write("OK".getBytes());

        apiRequestResponseLoggingFilter.doFilterInternal(request, response, filterChain);

        // check the response log
        Map<String, Object> responseLogMap = new LinkedHashMap<>();
        ResponseLogInfo responseLogInfo = new ResponseLogInfo();
        responseLogInfo.setStatusCode(200);
        HttpHeaders headers = new HttpHeaders();
        headers.add("head1Name", "head1Value");
        headers.add("head2Name", "head2Value");
        headers.add(MdcKeyEnum.AMAZON_TRACE_ID.getKey(), "Self=testSelf2;Root=testRoot2");
        headers.add("Content-Type", ContentType);
        responseLogInfo.setHeaders(JsonUtility.toJson(headers));
        responseLogMap.put("res", responseLogInfo);
        // check the response log
        checkRequestLog("", responseLogMap);
    }

    private void checkRequestLog(String message, Map<String, Object> logMap, String... logStr){
        // check the log
        Mockito.verify(mockAppender).doAppend(Mockito.argThat(new ArgumentMatcher<ILoggingEvent>() {
            @Override
            public boolean matches(ILoggingEvent iLoggingEvent) {
                boolean hasSameMap= false;
                if (iLoggingEvent.getArgumentArray() != null && iLoggingEvent.getArgumentArray().length > 0) {
                    for (String tmpMapKey : logMap.keySet()) {
                        if (iLoggingEvent.getArgumentArray()[0] instanceof Map) {
                            if (logStr.length > 0) {
                                hasSameMap = logStr[0].equals(String.valueOf(((Map) iLoggingEvent.getArgumentArray()[0]).get(tmpMapKey)));
                            } else {
                                hasSameMap = logMap.get(tmpMapKey).equals(((Map) iLoggingEvent.getArgumentArray()[0]).get(tmpMapKey));
                            }
                        }
                    }
                }
                boolean hasSameMessage = false;
                if (StringUtils.isEmpty(message)){
                    hasSameMessage = true;
                } else {
                    hasSameMessage = iLoggingEvent.getFormattedMessage().contains(
                            message);
                }
                return hasSameMessage && hasSameMap ;
            }
        }));
    }
}


class TestBodyReaderHttpServletRequestWrapper extends BodyReaderHttpServletRequestWrapper {

    /**
     * The request's InputStream is saved in requestBody.
     */
    @Getter
    private final byte[] requestBody;

    /**
     * Byte array requestBody conversion saved in bodyMap.
     */
    @Getter
    private Object bodyObject;

    public TestBodyReaderHttpServletRequestWrapper(HttpServletRequest request, String jsonBody) throws IOException {
        super(request);
        this.requestBody = jsonBody.getBytes();
        if (ArrayUtils.isNotEmpty(requestBody)) {
            this.bodyObject = JsonUtility.toObject(new String(requestBody, Charset.forName("UTF-8")), Object.class);
        }
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    /**
     * Use the current class object can read InputStream repeatedly
     *
     * @return InputStream
     * @throws IOException
     */
    @Override
    public ServletInputStream getInputStream() throws IOException {

        final ByteArrayInputStream stream = new ByteArrayInputStream(requestBody);
        return new ServletInputStream() {

            @Override
            public int read() throws IOException {
                return stream.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }
        };
    }
}