/**
 * @(#)EtagResponseAdviceTest.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.web.etag;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.fastretailing.dcp.common.api.etag.Etag;
import com.fastretailing.dcp.common.api.etag.EtagWrapper;

/**
 * test class of EtagResponseAdviceTest.
 * 
 * @author Fast Retailing
 * @version $Revision$
 */
public class EtagResponseAdviceTest {

    @Test
    public void supports01() {
        EtagResponseAdvice target = new EtagResponseAdvice();

        Method method = null;
        try {
            method = TestControlWhitWrapReturnTypeWithEtagArgument.class.getMethod("testMethod", ResponseEntity.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        MethodParameter parameter = new MethodParameter(method, 0);
        boolean result = target.supports(parameter, StringHttpMessageConverter.class);

        Assert.assertTrue(result);
    }
    @Test
    public void supports02() {
        EtagResponseAdvice target = new EtagResponseAdvice();

        Method method = null;
        try {
            method = TestControlWhitWrapReturnTypeWithEtagArgument.class.getMethod("testMethod2", EtagWrapper.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        MethodParameter parameter = new MethodParameter(method, 0);
        boolean result = target.supports(parameter, StringHttpMessageConverter.class);

        Assert.assertTrue(result);
    }
    
    @Test
    public void supports03() {
        EtagResponseAdvice target = new EtagResponseAdvice();

        Method method = null;
        try {
            method = TestControlWhitWrapReturnTypeWithEtagArgument.class.getMethod("testMetho3", String.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        MethodParameter parameter = new MethodParameter(method, 0);
        boolean result = target.supports(parameter, StringHttpMessageConverter.class);

        Assert.assertFalse(result);
    }

    @Test
    public void supports04() {
        EtagResponseAdvice target = new EtagResponseAdvice();

        Method method = null;
        try {
            method = TestControlWhitWrapReturnTypeWithEtagArgument.class.getMethod("testMethod04", ResponseEntity.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        MethodParameter parameter = new MethodParameter(method, 0);
        boolean result = target.supports(parameter, StringHttpMessageConverter.class);

        Assert.assertFalse(result);
    }

    @Test
    public void beforeBodyWrite01() {
        // Test has etag
        EtagResponseAdvice target = new EtagResponseAdvice();

        // Controller's return object
        Map<String, String> etagMap = new HashMap<>();
        etagMap.put("basket", "2007-12-03T10:15:30.123");
        Etag etag = new Etag(etagMap);
        Object body = new EtagWrapper(etag, "returnDate");
        // returnType
        Method method = null;
        try {
            method = TestControlWhitWrapReturnTypeWithEtagArgument.class.getMethod("testMethod", ResponseEntity.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        MethodParameter returnType = new MethodParameter(method, 0);
        // request
        MockHttpServletRequest servletRequest = new MockHttpServletRequest();
        ServerHttpRequest request = new ServletServerHttpRequest(servletRequest);
        // response
        MockHttpServletResponse servletResponse = new MockHttpServletResponse();
        ServerHttpResponse response = new ServletServerHttpResponse(servletResponse);

        Object result = target.beforeBodyWrite(body, returnType, MediaType.APPLICATION_JSON, StringHttpMessageConverter.class, request, response);

        Assert.assertEquals("basket=2007-12-03T10:15:30.123",response.getHeaders().get("Etag").get(0));
        Assert.assertEquals("returnDate",result);
    }

    @Test
    public void beforeBodyWrite02() {
        // Test there is no etag
        EtagResponseAdvice target = new EtagResponseAdvice();

        // Controller's return object
        Object body = new EtagWrapper(null,"returnDate");
        // returnType
        Method method = null;
        try {
            method = TestControlWhitWrapReturnTypeWithEtagArgument.class.getMethod("testMethod", ResponseEntity.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        MethodParameter returnType = new MethodParameter(method, 0);
        // request
        MockHttpServletRequest servletRequest = new MockHttpServletRequest();
        ServerHttpRequest request = new ServletServerHttpRequest(servletRequest);
        // response
        MockHttpServletResponse servletResponse = new MockHttpServletResponse();
        ServerHttpResponse response = new ServletServerHttpResponse(servletResponse);

        Object result = target.beforeBodyWrite(body, returnType, MediaType.APPLICATION_JSON, StringHttpMessageConverter.class, request, response);

        Assert.assertEquals("",response.getHeaders().get("Etag").get(0));
        Assert.assertEquals("returnDate",result);
    }

    class TestControlWhitWrapReturnTypeWithEtagArgument {
        public void testMethod(ResponseEntity<EtagWrapper> arg) {
            // do nothing
        }
        
        public void testMethod2(EtagWrapper arg) {
            // do nothing
        }
        
        public void testMetho3(String arg) {
            // do nothing
        }

        public void testMethod04(ResponseEntity arg) {
            // do nothing
        }
    }
}
