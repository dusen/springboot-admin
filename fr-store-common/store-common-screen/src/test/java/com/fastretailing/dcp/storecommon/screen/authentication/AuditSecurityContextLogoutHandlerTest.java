/**
 * @(#)AuditSecurityContextLogoutHandlerTest.java
 *
 *                                                Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.authentication;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import com.fastretailing.dcp.storecommon.screen.authentication.entity.UserDetails;

/**
 * AuditSecurityContextLogoutHandler test class.
 */
public class AuditSecurityContextLogoutHandlerTest {

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.authentication.AuditSecurityContextLogoutHandler#logout(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.Authentication)}.
     * 
     * Case :: When the normally value is an input value.
     */
    @Test
    public void testLogoutHttpServletRequestHttpServletResponseAuthentication_normally() {

        // Input.
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
        AuditSecurityContextLogoutHandler target = new AuditSecurityContextLogoutHandler();
        target.setInvalidateHttpSession(false);
        target.setClearAuthentication(false);
        target.logout(request, response, authentication);

    }

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.authentication.AuditSecurityContextLogoutHandler#logout(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.Authentication)}.
     * 
     * Case :: When the request is null. Since this case is error handled by
     * {@link org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler#logout(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, Authentication)},
     * no test is done.
     */
    public void testLogoutHttpServletRequestHttpServletResponseAuthentication_nullRequest() {

        // input.
        MockHttpServletRequest request = null;

        MockHttpServletResponse response = new MockHttpServletResponse();

        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE1"));
        UserDetails userDetails = new UserDetails("000000", "testUser", "testPassword", true, true,
                true, true, authorityList);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // execute test method.
        AuditSecurityContextLogoutHandler target = new AuditSecurityContextLogoutHandler();
        target.setInvalidateHttpSession(false);
        target.setClearAuthentication(false);
        target.logout(request, response, authentication);

    }

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.authentication.AuditSecurityContextLogoutHandler#logout(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.Authentication)}.
     * 
     * Case :: When the response is null.
     */
    @Test
    public void testLogoutHttpServletRequestHttpServletResponseAuthentication_nullResponse() {

        // input.
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI(
                "https://host.com:7443/screentest/v1/uq/jp/screen/000000/action?param1=aaa&param2=bbb#fragments");

        MockHttpServletResponse response = null;

        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE1"));
        UserDetails userDetails = new UserDetails("000000", "testUser", "testPassword", true, true,
                true, true, authorityList);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // execute test method.
        AuditSecurityContextLogoutHandler target = new AuditSecurityContextLogoutHandler();
        target.setInvalidateHttpSession(false);
        target.setClearAuthentication(false);
        target.logout(request, response, authentication);

    }

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.authentication.AuditSecurityContextLogoutHandler#logout(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.Authentication)}.
     * 
     * Case :: When the authentication is null.
     */
    @Test
    public void testLogoutHttpServletRequestHttpServletResponseAuthentication_nullAuthentication() {

        // input.
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
        AuditSecurityContextLogoutHandler target = new AuditSecurityContextLogoutHandler();
        target.setInvalidateHttpSession(false);
        target.setClearAuthentication(false);
        target.logout(request, response, null);

    }

}
