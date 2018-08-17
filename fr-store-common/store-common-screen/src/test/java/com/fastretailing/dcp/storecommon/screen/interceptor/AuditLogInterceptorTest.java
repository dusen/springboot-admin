/**
 * @(#)AuditLogInterceptorTest.java
 *
 *                                  Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.interceptor;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import com.fastretailing.dcp.common.exception.SystemException;
import com.fastretailing.dcp.storecommon.screen.authentication.entity.UserDetails;
import com.fastretailing.dcp.storecommon.screen.config.AuditConfiguration;

/**
 * AuditLogInterceptor test class.
 */
public class AuditLogInterceptorTest {

    @Mock
    private Object mockHandler;

    /**
     * @throws java.lang.Exception Exception.
     */
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * @throws java.lang.Exception Exception.
     */
    @After
    public void tearDown() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.interceptor.AuditLogInterceptor#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)}.
     * 
     * Case :: When the normally value is an input value.
     */
    @Test
    public void testPreHandleHttpServletRequestHttpServletResponseObject_normally() {

        // input.
        List<String> exclusionPathList = new ArrayList<>();
        exclusionPathList.add("/version");
        AuditConfiguration configuration = new AuditConfiguration();
        configuration.setExclusionPathList(exclusionPathList);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI(
                "https://host.com:7443/screentest/v1/uq/jp/screen/000000/action?param1=aaa&param2=bbb#fragments");

        MockHttpServletResponse response = new MockHttpServletResponse();

        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE1"));
        UserDetails userDetails = new UserDetails("000000", "testUser", "testPassword", true, true,
                true, true, authorityList);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // initialize mock object.
        when(mockHandler.toString()).thenReturn("handler.toString()");

        // execute test method.
        AuditLogInterceptor target = new AuditLogInterceptor(configuration);
        boolean actualReturn = target.preHandle(request, response, mockHandler);

        // assert output.
        assertTrue(actualReturn);

    }

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.interceptor.AuditLogInterceptor#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)}.
     * 
     * Case :: When the request URL is exclusion path.
     */
    @Test
    public void testPreHandleHttpServletRequestHttpServletResponseObject_exclusionPath() {

        // input.
        List<String> exclusionPathList = new ArrayList<>();
        exclusionPathList.add("/version");
        AuditConfiguration configuration = new AuditConfiguration();
        configuration.setExclusionPathList(exclusionPathList);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("https://host.com:7443/screentest/v1/version");

        MockHttpServletResponse response = new MockHttpServletResponse();

        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE1"));
        UserDetails userDetails = new UserDetails("000000", "testUser", "testPassword", true, true,
                true, true, authorityList);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // initialize mock object.
        when(mockHandler.toString()).thenReturn("handler.toString()");

        // execute test method.
        AuditLogInterceptor target = new AuditLogInterceptor(configuration);
        boolean actualReturn = target.preHandle(request, response, mockHandler);

        // assert output.
        assertTrue(actualReturn);

    }

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.interceptor.AuditLogInterceptor#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)}.
     * 
     * Case :: When an exception occurs.
     */
    @Test
    public void testPreHandleHttpServletRequestHttpServletResponseObject_throwException() {

        // input.
        List<String> exclusionPathList = new ArrayList<>();
        exclusionPathList.add("/version");
        AuditConfiguration configuration = new AuditConfiguration();
        configuration.setExclusionPathList(exclusionPathList);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI(
                "https://host.com:7443/screentest/v1/uq/jp/screen/000000/action?param1=aaa&param2=bbb#fragments");

        MockHttpServletResponse response = new MockHttpServletResponse();

        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE1"));
        UserDetails userDetails = new UserDetails("000000", "testUser", "testPassword", true, true,
                true, true, authorityList);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // initialize mock object.
        Throwable throwedException = new SystemException("test exception");
        when(mockHandler.toString()).thenThrow(throwedException);

        // execute test method.
        AuditLogInterceptor target = new AuditLogInterceptor(configuration);
        boolean actualReturn = target.preHandle(request, response, mockHandler);

        // assert output.
        assertTrue(actualReturn);

    }

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.interceptor.AuditLogInterceptor#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)}.
     * 
     * Case :: When request is null.
     */
    @Test
    public void testPreHandleHttpServletRequestHttpServletResponseObject_nullRequest() {

        // input.
        List<String> exclusionPathList = new ArrayList<>();
        exclusionPathList.add("/version");
        AuditConfiguration configuration = new AuditConfiguration();
        configuration.setExclusionPathList(exclusionPathList);

        MockHttpServletRequest request = null;

        MockHttpServletResponse response = new MockHttpServletResponse();

        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE1"));
        UserDetails userDetails = new UserDetails("000000", "testUser", "testPassword", true, true,
                true, true, authorityList);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // initialize mock object.
        when(mockHandler.toString()).thenReturn("handler.toString()");

        // execute test method.
        AuditLogInterceptor target = new AuditLogInterceptor(configuration);
        boolean actualReturn = target.preHandle(request, response, mockHandler);

        // assert output.
        assertTrue(actualReturn);

    }

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.interceptor.AuditLogInterceptor#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)}.
     * 
     * Case :: When handler is null.
     */
    @Test
    public void testPreHandleHttpServletRequestHttpServletResponseObject_nullHandler() {

        // input.
        List<String> exclusionPathList = new ArrayList<>();
        exclusionPathList.add("/version");
        AuditConfiguration configuration = new AuditConfiguration();
        configuration.setExclusionPathList(exclusionPathList);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI(
                "https://host.com:7443/screentest/v1/uq/jp/screen/000000/action?param1=aaa&param2=bbb#fragments");

        MockHttpServletResponse response = new MockHttpServletResponse();

        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE1"));
        UserDetails userDetails = new UserDetails("000000", "testUser", "testPassword", true, true,
                true, true, authorityList);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // execute test method.
        AuditLogInterceptor target = new AuditLogInterceptor(configuration);
        boolean actualReturn = target.preHandle(request, response, null);

        // assert output.
        assertTrue(actualReturn);

    }

}
