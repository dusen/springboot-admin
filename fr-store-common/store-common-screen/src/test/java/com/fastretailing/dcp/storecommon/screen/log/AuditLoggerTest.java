/**
 * @(#)AuditLoggerTest.java
 *
 *                          Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.log;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.fastretailing.dcp.storecommon.screen.authentication.entity.UserDetails;

/**
 * AuditLogger test class.
 */
public class AuditLoggerTest {

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.log.AuditLogger#outputAuditLog(com.fastretailing.dcp.storecommon.screen.authentication.entity.UserDetails, java.lang.Class, java.lang.String, javax.servlet.http.HttpServletRequest, java.util.Map)}.
     * 
     * Case :: When the normally value is an input value.
     */
    @Test
    public void testOutputAuditLog_normally() {

        // input.
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE1"));
        UserDetails userDetails = new UserDetails("000000", "testUser", "testPassword", true, true,
                true, true, authorityList);

        Class<?> outputClass = this.getClass();

        String executedHandler = "executed handler.";

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI(
                "https://host.com:7443/screentest/v1/uq/jp/screen/000000/action?param1=aaa&param2=bbb#fragments");

        Map<String, String> informationMap = new LinkedHashMap<>();
        informationMap.put("additionKey01", "additionValue01");
        informationMap.put("additionKey02", "additionValue02");

        // execute test method.
        AuditLogger.outputAuditLog(userDetails, outputClass, executedHandler, request,
                informationMap);

    }

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.log.AuditLogger#outputAuditLog(com.fastretailing.dcp.storecommon.screen.authentication.entity.UserDetails, java.lang.Class, java.lang.String, javax.servlet.http.HttpServletRequest, java.util.Map)}.
     * 
     * Case :: When the user details is null.
     */
    @Test
    public void testOutputAuditLog_nullUserDetails() {

        // input.
        UserDetails userDetails = null;

        Class<?> outputClass = this.getClass();

        String executedHandler = "executed handler.";

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI(
                "https://host.com:7443/screentest/v1/uq/jp/screen/000000/action?param1=aaa&param2=bbb#fragments");

        Map<String, String> informationMap = new LinkedHashMap<>();
        informationMap.put("additionKey01", "additionValue01");
        informationMap.put("additionKey02", "additionValue02");

        // execute test method.
        AuditLogger.outputAuditLog(userDetails, outputClass, executedHandler, request,
                informationMap);

    }

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.log.AuditLogger#outputAuditLog(com.fastretailing.dcp.storecommon.screen.authentication.entity.UserDetails, java.lang.Class, java.lang.String, javax.servlet.http.HttpServletRequest, java.util.Map)}.
     * 
     * Case :: When the output class is null.
     */
    @Test
    public void testOutputAuditLog_nullOutputClass() {

        // input.
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE1"));
        UserDetails userDetails = new UserDetails("000000", "testUser", "testPassword", true, true,
                true, true, authorityList);

        Class<?> outputClass = null;

        String executedHandler = "executed handler.";

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI(
                "https://host.com:7443/screentest/v1/uq/jp/screen/000000/action?param1=aaa&param2=bbb#fragments");

        Map<String, String> informationMap = new LinkedHashMap<>();
        informationMap.put("additionKey01", "additionValue01");
        informationMap.put("additionKey02", "additionValue02");

        // execute test method.
        AuditLogger.outputAuditLog(userDetails, outputClass, executedHandler, request,
                informationMap);

    }

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.log.AuditLogger#outputAuditLog(com.fastretailing.dcp.storecommon.screen.authentication.entity.UserDetails, java.lang.Class, java.lang.String, javax.servlet.http.HttpServletRequest, java.util.Map)}.
     * 
     * Case :: When the handler is null.
     */
    @Test
    public void testOutputAuditLog_nullHandler() {

        // input.
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE1"));
        UserDetails userDetails = new UserDetails("000000", "testUser", "testPassword", true, true,
                true, true, authorityList);

        Class<?> outputClass = this.getClass();

        String executedHandler = null;

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI(
                "https://host.com:7443/screentest/v1/uq/jp/screen/000000/action?param1=aaa&param2=bbb#fragments");

        Map<String, String> informationMap = new LinkedHashMap<>();
        informationMap.put("additionKey01", "additionValue01");
        informationMap.put("additionKey02", "additionValue02");

        // execute test method.
        AuditLogger.outputAuditLog(userDetails, outputClass, executedHandler, request,
                informationMap);

    }

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.log.AuditLogger#outputAuditLog(com.fastretailing.dcp.storecommon.screen.authentication.entity.UserDetails, java.lang.Class, java.lang.String, javax.servlet.http.HttpServletRequest, java.util.Map)}.
     * 
     * Case :: When the request is null.
     */
    @Test
    public void testOutputAuditLog_nullRequset() {

        // input.
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE1"));
        UserDetails userDetails = new UserDetails("000000", "testUser", "testPassword", true, true,
                true, true, authorityList);

        Class<?> outputClass = this.getClass();

        String executedHandler = "executed handler.";

        MockHttpServletRequest request = null;

        Map<String, String> informationMap = new LinkedHashMap<>();
        informationMap.put("additionKey01", "additionValue01");
        informationMap.put("additionKey02", "additionValue02");

        // execute test method.
        AuditLogger.outputAuditLog(userDetails, outputClass, executedHandler, request,
                informationMap);

    }

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.log.AuditLogger#outputAuditLog(com.fastretailing.dcp.storecommon.screen.authentication.entity.UserDetails, java.lang.Class, java.lang.String, javax.servlet.http.HttpServletRequest, java.util.Map)}.
     * 
     * Case :: When the information map is null.
     */
    @Test
    public void testOutputAuditLog_nullInformationMap() {

        // input.
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE1"));
        UserDetails userDetails = new UserDetails("000000", "testUser", "testPassword", true, true,
                true, true, authorityList);

        Class<?> outputClass = this.getClass();

        String executedHandler = "executed handler.";

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI(
                "https://host.com:7443/screentest/v1/uq/jp/screen/000000/action?param1=aaa&param2=bbb#fragments");

        Map<String, String> informationMap = null;

        // execute test method.
        AuditLogger.outputAuditLog(userDetails, outputClass, executedHandler, request,
                informationMap);

    }

}
