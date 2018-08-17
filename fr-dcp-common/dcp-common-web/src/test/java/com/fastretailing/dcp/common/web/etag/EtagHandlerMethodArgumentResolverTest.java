/**
 * @(#)EtagHandlerMethodArgumentResolverTest.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.web.etag;

import com.fastretailing.dcp.common.api.etag.Etag;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.MethodParameter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.bind.support.DefaultDataBinderFactory;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.reflect.Method;

/**
 * test class of EtagHandlerMethodArgumentResolver.
 * 
 * @author Fast Retailing
 * @version $Revision$
 */
public class EtagHandlerMethodArgumentResolverTest {

    @Test
    public void supportsParameter() {
        EtagHandlerMethodArgumentResolver target = new EtagHandlerMethodArgumentResolver();
        Method method = null;
        try {
            method = TestControlWhitEtagArgument.class.getMethod("testMethod", Etag.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        MethodParameter parameter = new MethodParameter(method, 0);
        boolean result = target.supportsParameter(parameter);

        Assert.assertTrue(result);

    }

    @Test
    public void resolveArgument01() {

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("If-Match", "basket=2007-12-03T10:15:30.123");
        NativeWebRequest webRequest = new ServletWebRequest(request);
        Method method = null;
        try {
            method = TestControlWhitEtagArgument.class.getMethod("testMethod", Etag.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        MethodParameter parameter = new MethodParameter(method, 0);
        ModelAndViewContainer mavContainer = new ModelAndViewContainer();
        WebDataBinderFactory binderFactory = new DefaultDataBinderFactory(new ConfigurableWebBindingInitializer());

        EtagHandlerMethodArgumentResolver target = new EtagHandlerMethodArgumentResolver();
        Etag result = null;
        try {
            result = (Etag) target.resolveArgument(parameter, mavContainer, webRequest, binderFactory);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertEquals("basket=2007-12-03T10:15:30.123", result.toString());

    }

    @Test
    public void resolveArgument02() {
        // No header names "If-Match"
        MockHttpServletRequest request = new MockHttpServletRequest();
        NativeWebRequest webRequest = new ServletWebRequest(request);
        Method method = null;
        try {
            method = TestControlWhitEtagArgument.class.getMethod("testMethod", Etag.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        MethodParameter parameter = new MethodParameter(method, 0);
        ModelAndViewContainer mavContainer = new ModelAndViewContainer();
        WebDataBinderFactory binderFactory = new DefaultDataBinderFactory(new ConfigurableWebBindingInitializer());

        EtagHandlerMethodArgumentResolver target = new EtagHandlerMethodArgumentResolver();
        Etag result = null;
        try {
            result = (Etag) target.resolveArgument(parameter, mavContainer, webRequest, binderFactory);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertSame(null, result);

    }

    class TestControlWhitEtagArgument {

        public void testMethod(Etag arg) {
            // do nothing
        }
    }
}
