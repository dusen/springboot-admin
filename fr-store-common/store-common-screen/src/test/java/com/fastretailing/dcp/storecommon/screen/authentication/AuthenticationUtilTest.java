/**
 * @(#)AuthenticationUtilTest.java
 *
 *                                 Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.authentication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.ArrayList;
import java.util.List;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import com.fastretailing.dcp.storecommon.screen.authentication.entity.UserDetails;
import com.fastretailing.dcp.storecommon.screen.exception.NoAuthenticationException;

/**
 * AuthenticationUtil test class.
 */
public class AuthenticationUtilTest {

    /** Exception assertion. */
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.authentication.AuthenticationUtil#getUserDetails()}.
     * 
     * Case :: When the normally value is an input value.
     */
    @Test
    public void testGetUserDetails_normally() {

        // input.
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE1"));
        UserDetails userDetails = new UserDetails("000000", "testUser", "testPassword", true, true,
                true, true, authorityList);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // execute test method.
        UserDetails actualReturn = AuthenticationUtil.getUserDetails();

        // assert output.
        assertNotNull(actualReturn);
        assertEquals(UserDetails.class, actualReturn.getClass());
        assertEquals("000000", actualReturn.getStoreCode());
        assertEquals("testUser", actualReturn.getUsername());
        assertEquals("testPassword", actualReturn.getPassword());
        assertEquals(authorityList.size(), actualReturn.getAuthorities().size());

    }

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.authentication.AuthenticationUtil#getUserDetails()}.
     * 
     * Case :: When the principal is not UserDetails class.
     */
    @Test
    public void testGetUserDetails_notUserDetails() {

        // input.
        Authentication authentication =
                new UsernamePasswordAuthenticationToken("not UserDetails", null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Expect throwing an exception.
        thrown.expect(NoAuthenticationException.class);
        thrown.expectMessage(
                "store-common-screen framework authentication is not being done by SAML authentication.");

        // execute test method.
        AuthenticationUtil.getUserDetails();

    }

}
