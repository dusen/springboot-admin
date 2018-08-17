/**
 * @(#)DynamicRedirectionSavedRequestWrapperTest.java
 *
 *                                                    Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.authentication.saml;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.Cookie;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import com.fastretailing.dcp.common.exception.SystemException;
import com.fastretailing.dcp.storecommon.screen.authentication.entity.UserDetails;
import com.fastretailing.dcp.storecommon.screen.util.StoreRequestPathVariableHolder;

/**
 * DynamicRedirectionSavedRequestWrapper test class.
 */
public class DynamicRedirectionSavedRequestWrapperTest {

    /** Exception assertion. */
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    /**
     * @throws java.lang.Exception Exception.
     */
    @Before
    public void setUp() throws Exception {
        StoreRequestPathVariableHolder.setRequestPathVariableMap(null);
    }

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.authentication.saml.DynamicRedirectionSavedRequestWrapper#getRedirectUrl()}.
     * 
     * Case :: When the redirect destination is the default login URL.
     */
    @Test
    public void testGetRedirectUrlDefaultUrl() {

        // input.
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE01"));
        UserDetails userDetails = new UserDetails("testStoreCode", "testUser", "testPassword", true,
                true, true, true, authorityList);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Map<String, String> pathVariableMap = new HashMap<>();
        pathVariableMap.put(StoreRequestPathVariableHolder.KEY_BRAND, "testBrand");
        pathVariableMap.put(StoreRequestPathVariableHolder.KEY_REGION, "testRegion");
        StoreRequestPathVariableHolder.setRequestPathVariableMap(pathVariableMap);

        SavedRequest savedRequest = new TestSavedRequest(
                "https://host.com:7443/screentest/v1/uq/jp/screen/action?param1=aaa&store_code=111111#fragments");
        Map<String, String> loginUrlMap = new HashMap<>();
        loginUrlMap.put("/path01",
                "https://host.com:8443/screentest/v1/@@Brand@@/@@Region@@/screen/@@StoreCode@@/loginPath01");
        loginUrlMap.put("/path02",
                "https://host.com:8443/screentest/v1/@@Brand@@/@@Region@@/screen/@@StoreCode@@/loginPath02");
        String loginDefaultUrl =
                "https://host.com:8443/screentest/v1/@@Brand@@/@@Region@@/screen/@@StoreCode@@/default";

        DynamicRedirectionSavedRequestWrapper targetWrapper =
                new DynamicRedirectionSavedRequestWrapper(savedRequest, loginUrlMap,
                        loginDefaultUrl);

        // execute test method.
        String actualRedirectUrl = targetWrapper.getRedirectUrl();

        // assert output.
        assertEquals(
                "https://host.com:8443/screentest/v1/testBrand/testRegion/screen/111111/default?param1=aaa&store_code=111111",
                actualRedirectUrl);

    }

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.authentication.saml.DynamicRedirectionSavedRequestWrapper#getRedirectUrl()}.
     * 
     * Case :: When the redirect destination is the mapping URL.
     */
    @Test
    public void testGetRedirectUrlMappingUrl1() {

        // input.
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE01"));
        UserDetails userDetails = new UserDetails("testStoreCode", "testUser", "testPassword", true,
                true, true, true, authorityList);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Map<String, String> pathVariableMap = new HashMap<>();
        pathVariableMap.put(StoreRequestPathVariableHolder.KEY_BRAND, "testBrand");
        pathVariableMap.put(StoreRequestPathVariableHolder.KEY_REGION, "testRegion");
        StoreRequestPathVariableHolder.setRequestPathVariableMap(pathVariableMap);

        SavedRequest savedRequest = new TestSavedRequest(
                "https://host.com:7443/screentest/v1/uq/jp/screen/path01?param1=aaa&store_code=000000#fragments");
        Map<String, String> loginUrlMap = new HashMap<>();
        loginUrlMap.put("/path01",
                "https://host.com:8443/screentest/v1/@@Brand@@/@@Region@@/screen/@@StoreCode@@/loginPath01");
        loginUrlMap.put("/path02",
                "https://host.com:8443/screentest/v1/@@Brand@@/@@Region@@/screen/@@StoreCode@@/loginPath02");
        String loginDefaultUrl =
                "https://host.com:8443/screentest/v1/@@Brand@@/@@Region@@/screen/@@StoreCode@@/default";

        DynamicRedirectionSavedRequestWrapper targetWrapper =
                new DynamicRedirectionSavedRequestWrapper(savedRequest, loginUrlMap,
                        loginDefaultUrl);

        // execute test method.
        String actualRedirectUrl = targetWrapper.getRedirectUrl();

        // assert output.
        assertEquals(
                "https://host.com:8443/screentest/v1/testBrand/testRegion/screen/000000/loginPath01?param1=aaa&store_code=000000",
                actualRedirectUrl);

    }

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.authentication.saml.DynamicRedirectionSavedRequestWrapper#getRedirectUrl()}.
     * 
     * Case :: When the redirect destination is the mapping URL.
     */
    @Test
    public void testGetRedirectUrlMappingUrl2() {

        // input.
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE01"));
        UserDetails userDetails = new UserDetails("testStoreCode", "testUser", "testPassword", true,
                true, true, true, authorityList);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Map<String, String> pathVariableMap = new HashMap<>();
        pathVariableMap.put(StoreRequestPathVariableHolder.KEY_BRAND, "testBrand");
        pathVariableMap.put(StoreRequestPathVariableHolder.KEY_REGION, "testRegion");
        pathVariableMap.put(StoreRequestPathVariableHolder.KEY_STORECODE, "testStoreCode");
        StoreRequestPathVariableHolder.setRequestPathVariableMap(pathVariableMap);

        SavedRequest savedRequest =
                new TestSavedRequest("https://host.com:7443/screentest/v1/uq/jp/screen/path02");
        Map<String, String> loginUrlMap = new HashMap<>();
        loginUrlMap.put("/path01",
                "https://host.com:8443/screentest/v1/@@Brand@@/@@Region@@/screen/@@StoreCode@@/loginPath01");
        loginUrlMap.put("/path02",
                "https://host.com:8443/screentest/v1/@@Brand@@/@@Region@@/screen/@@StoreCode@@/loginPath02");
        String loginDefaultUrl =
                "https://host.com:8443/screentest/v1/@@Brand@@/@@Region@@/screen/@@StoreCode@@/default";

        DynamicRedirectionSavedRequestWrapper targetWrapper =
                new DynamicRedirectionSavedRequestWrapper(savedRequest, loginUrlMap,
                        loginDefaultUrl);

        // execute test method.
        String actualRedirectUrl = targetWrapper.getRedirectUrl();

        // assert output.
        assertEquals(
                "https://host.com:8443/screentest/v1/testBrand/testRegion/screen/testStoreCode/loginPath02",
                actualRedirectUrl);

    }

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.authentication.saml.DynamicRedirectionSavedRequestWrapper#getRedirectUrl()}.
     * 
     * Case :: When the mapping path does not contain the replacement character string.
     */
    @Test
    public void testGetRedirectUrlMappingUrl3() {

        // input.
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE01"));
        UserDetails userDetails = new UserDetails("testStoreCode", "testUser", "testPassword", true,
                true, true, true, authorityList);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Map<String, String> pathVariableMap = new HashMap<>();
        pathVariableMap.put(StoreRequestPathVariableHolder.KEY_BRAND, "testBrand");
        pathVariableMap.put(StoreRequestPathVariableHolder.KEY_REGION, "testRegion");
        StoreRequestPathVariableHolder.setRequestPathVariableMap(pathVariableMap);

        SavedRequest savedRequest = new TestSavedRequest(
                "https://host.com:7443/screentest/v1/uq/jp/screen/path01?param2=bbb#fragments");
        Map<String, String> loginUrlMap = new HashMap<>();
        loginUrlMap.put("/path01", "https://host.com:8443/screentest/v1/screen/loginPath01");
        loginUrlMap.put("/path02",
                "https://host.com:8443/screentest/v1/@@Brand@@/@@Region@@/screen/@@StoreCode@@/loginPath02");
        String loginDefaultUrl =
                "https://host.com:8443/screentest/v1/@@Brand@@/@@Region@@/screen/@@StoreCode@@/default";

        DynamicRedirectionSavedRequestWrapper targetWrapper =
                new DynamicRedirectionSavedRequestWrapper(savedRequest, loginUrlMap,
                        loginDefaultUrl);

        // execute test method.
        String actualRedirectUrl = targetWrapper.getRedirectUrl();

        // assert output.
        assertEquals("https://host.com:8443/screentest/v1/screen/loginPath01?param2=bbb",
                actualRedirectUrl);

    }

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.authentication.saml.DynamicRedirectionSavedRequestWrapper#getRedirectUrl()}.
     * 
     * Case :: In case of mapping to a URL where there is no placeholder.
     */
    @Test
    public void testGetRedirectUrl_noPlaceholder() {

        // input.
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE01"));
        UserDetails userDetails = new UserDetails("testStoreCode", "testUser", "testPassword", true,
                true, true, true, authorityList);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        SavedRequest savedRequest = new TestSavedRequest(
                "https://host.com:7443/screentest/v1/uq/jp/screen/path03?param2=bbb#fragments");
        Map<String, String> loginUrlMap = new HashMap<>();
        loginUrlMap.put("/path01", "https://host.com:8443/screentest/v1/screen/loginPath01");
        loginUrlMap.put("/path02",
                "https://host.com:8443/screentest/v1/@@Brand@@/@@Region@@/screen/@@StoreCode@@/loginPath02");
        loginUrlMap.put("/path03", "https://host.com:8443/screentest/v1/screen/loginPath01");
        String loginDefaultUrl =
                "https://host.com:8443/screentest/v1/@@Brand@@/@@Region@@/screen/@@StoreCode@@/default";

        DynamicRedirectionSavedRequestWrapper targetWrapper =
                new DynamicRedirectionSavedRequestWrapper(savedRequest, loginUrlMap,
                        loginDefaultUrl);

        // execute test method.
        String actualRedirectUrl = targetWrapper.getRedirectUrl();

        // assert output.
        assertEquals("https://host.com:8443/screentest/v1/screen/loginPath01?param2=bbb",
                actualRedirectUrl);

    }

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.authentication.saml.DynamicRedirectionSavedRequestWrapper#getRedirectUrl()}.
     * 
     * Case :: If the replacement value of the brand is null.
     */
    @Test
    public void testGetRedirectUrl_replaceBrandNullError() {

        // input.
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE01"));
        UserDetails userDetails = new UserDetails("testStoreCode", "testUser", "testPassword", true,
                true, true, true, authorityList);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Map<String, String> pathVariableMap = new HashMap<>();
        pathVariableMap.put(StoreRequestPathVariableHolder.KEY_BRAND, null);
        pathVariableMap.put(StoreRequestPathVariableHolder.KEY_REGION, "testRegion");
        pathVariableMap.put(StoreRequestPathVariableHolder.KEY_STORECODE, "333333");
        StoreRequestPathVariableHolder.setRequestPathVariableMap(pathVariableMap);

        SavedRequest savedRequest = new TestSavedRequest(
                "https://host.com:7443/screentest/v1/uq/jp/screen/path01?param1=aaa#fragments");
        Map<String, String> loginUrlMap = new HashMap<>();
        loginUrlMap.put("/path01",
                "https://host.com:8443/screentest/v1/@@Brand@@/@@Region@@/screen/@@StoreCode@@/loginPath01");
        loginUrlMap.put("/path02",
                "https://host.com:8443/screentest/v1/@@Brand@@/@@Region@@/screen/@@StoreCode@@/loginPath02");
        String loginDefaultUrl =
                "https://host.com:8443/screentest/v1/@@Brand@@/@@Region@@/screen/@@StoreCode@@/default";

        DynamicRedirectionSavedRequestWrapper targetWrapper =
                new DynamicRedirectionSavedRequestWrapper(savedRequest, loginUrlMap,
                        loginDefaultUrl);

        // Expect throwing an exception.
        thrown.expect(SystemException.class);
        thrown.expectMessage("The value to be replaced is null or empty character. originalString="
                + "https://host.com:8443/screentest/v1/@@Brand@@/@@Region@@/screen/333333/loginPath01"
                + " / key=@@Brand@@ / value=null");

        // execute test method.
        targetWrapper.getRedirectUrl();

    }

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.authentication.saml.DynamicRedirectionSavedRequestWrapper#getRedirectUrl()}.
     * 
     * Case :: If the replacement value of the brand is empty.
     */
    @Test
    public void testGetRedirectUrl_replaceBrandEmptyError() {

        // input.
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE01"));
        UserDetails userDetails = new UserDetails("testStoreCode", "testUser", "testPassword", true,
                true, true, true, authorityList);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Map<String, String> pathVariableMap = new HashMap<>();
        pathVariableMap.put(StoreRequestPathVariableHolder.KEY_BRAND, "");
        pathVariableMap.put(StoreRequestPathVariableHolder.KEY_REGION, "testRegion");
        pathVariableMap.put(StoreRequestPathVariableHolder.KEY_STORECODE, "333333");
        StoreRequestPathVariableHolder.setRequestPathVariableMap(pathVariableMap);

        SavedRequest savedRequest = new TestSavedRequest(
                "https://host.com:7443/screentest/v1/uq/jp/screen/path01?param1=aaa#fragments");
        Map<String, String> loginUrlMap = new HashMap<>();
        loginUrlMap.put("/path01",
                "https://host.com:8443/screentest/v1/@@Brand@@/@@Region@@/screen/@@StoreCode@@/loginPath01");
        loginUrlMap.put("/path02",
                "https://host.com:8443/screentest/v1/@@Brand@@/@@Region@@/screen/@@StoreCode@@/loginPath02");
        String loginDefaultUrl =
                "https://host.com:8443/screentest/v1/@@Brand@@/@@Region@@/screen/@@StoreCode@@/default";

        DynamicRedirectionSavedRequestWrapper targetWrapper =
                new DynamicRedirectionSavedRequestWrapper(savedRequest, loginUrlMap,
                        loginDefaultUrl);

        // Expect throwing an exception.
        thrown.expect(SystemException.class);
        thrown.expectMessage("The value to be replaced is null or empty character. originalString="
                + "https://host.com:8443/screentest/v1/@@Brand@@/@@Region@@/screen/333333/loginPath01"
                + " / key=@@Brand@@ / value=");

        // execute test method.
        targetWrapper.getRedirectUrl();

    }

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.authentication.saml.DynamicRedirectionSavedRequestWrapper#getRedirectUrl()}.
     * 
     * Case :: If the replacement value of the region is null.
     */
    @Test
    public void testGetRedirectUrl_replaceRegionNullError() {

        // input.
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE01"));
        UserDetails userDetails = new UserDetails("testStoreCode", "testUser", "testPassword", true,
                true, true, true, authorityList);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Map<String, String> pathVariableMap = new HashMap<>();
        pathVariableMap.put(StoreRequestPathVariableHolder.KEY_BRAND, "testBrand");
        pathVariableMap.put(StoreRequestPathVariableHolder.KEY_REGION, null);
        pathVariableMap.put(StoreRequestPathVariableHolder.KEY_STORECODE, "444444");
        StoreRequestPathVariableHolder.setRequestPathVariableMap(pathVariableMap);

        SavedRequest savedRequest = new TestSavedRequest(
                "https://host.com:7443/screentest/v1/uq/jp/screen/path01?param1=aaa#fragments");
        Map<String, String> loginUrlMap = new HashMap<>();
        loginUrlMap.put("/path01",
                "https://host.com:8443/screentest/v1/@@Brand@@/@@Region@@/screen/@@StoreCode@@/loginPath01");
        loginUrlMap.put("/path02",
                "https://host.com:8443/screentest/v1/@@Brand@@/@@Region@@/screen/@@StoreCode@@/loginPath02");
        String loginDefaultUrl =
                "https://host.com:8443/screentest/v1/@@Brand@@/@@Region@@/screen/@@StoreCode@@/default";

        DynamicRedirectionSavedRequestWrapper targetWrapper =
                new DynamicRedirectionSavedRequestWrapper(savedRequest, loginUrlMap,
                        loginDefaultUrl);

        // Expect throwing an exception.
        thrown.expect(SystemException.class);
        thrown.expectMessage("The value to be replaced is null or empty character. originalString="
                + "https://host.com:8443/screentest/v1/testBrand/@@Region@@/screen/444444/loginPath01"
                + " / key=@@Region@@ / value=null");

        // execute test method.
        targetWrapper.getRedirectUrl();

    }

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.authentication.saml.DynamicRedirectionSavedRequestWrapper#getRedirectUrl()}.
     * 
     * Case :: If the replacement value of the region is empty.
     */
    @Test
    public void testGetRedirectUrl_replaceRegionEmptyError() {

        // input.
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE01"));
        UserDetails userDetails = new UserDetails("testStoreCode", "testUser", "testPassword", true,
                true, true, true, authorityList);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Map<String, String> pathVariableMap = new HashMap<>();
        pathVariableMap.put(StoreRequestPathVariableHolder.KEY_BRAND, "testBrand");
        pathVariableMap.put(StoreRequestPathVariableHolder.KEY_REGION, "");
        pathVariableMap.put(StoreRequestPathVariableHolder.KEY_STORECODE, "444444");
        StoreRequestPathVariableHolder.setRequestPathVariableMap(pathVariableMap);

        SavedRequest savedRequest = new TestSavedRequest(
                "https://host.com:7443/screentest/v1/uq/jp/screen/path01?param1=aaa#fragments");
        Map<String, String> loginUrlMap = new HashMap<>();
        loginUrlMap.put("/path01",
                "https://host.com:8443/screentest/v1/@@Brand@@/@@Region@@/screen/@@StoreCode@@/loginPath01");
        loginUrlMap.put("/path02",
                "https://host.com:8443/screentest/v1/@@Brand@@/@@Region@@/screen/@@StoreCode@@/loginPath02");
        String loginDefaultUrl =
                "https://host.com:8443/screentest/v1/@@Brand@@/@@Region@@/screen/@@StoreCode@@/default";

        DynamicRedirectionSavedRequestWrapper targetWrapper =
                new DynamicRedirectionSavedRequestWrapper(savedRequest, loginUrlMap,
                        loginDefaultUrl);

        // Expect throwing an exception.
        thrown.expect(SystemException.class);
        thrown.expectMessage("The value to be replaced is null or empty character. originalString="
                + "https://host.com:8443/screentest/v1/testBrand/@@Region@@/screen/444444/loginPath01"
                + " / key=@@Region@@ / value=");

        // execute test method.
        targetWrapper.getRedirectUrl();

    }

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.authentication.saml.DynamicRedirectionSavedRequestWrapper#getRedirectUrl()}.
     * 
     * Case :: If the replacement value of the store code is null.
     */
    @Test
    public void testGetRedirectUrl_replaceStoreCodeNullError() {

        // input.
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE01"));
        UserDetails userDetails = new UserDetails("testStoreCode", "testUser", "testPassword", true,
                true, true, true, authorityList);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Map<String, String> pathVariableMap = new HashMap<>();
        pathVariableMap.put(StoreRequestPathVariableHolder.KEY_BRAND, "testBrand");
        pathVariableMap.put(StoreRequestPathVariableHolder.KEY_REGION, "testRegion");
        pathVariableMap.put(StoreRequestPathVariableHolder.KEY_STORECODE, null);
        StoreRequestPathVariableHolder.setRequestPathVariableMap(pathVariableMap);

        SavedRequest savedRequest = new TestSavedRequest(
                "https://host.com:7443/screentest/v1/uq/jp/screen/path01?param1=aaa#fragments");
        Map<String, String> loginUrlMap = new HashMap<>();
        loginUrlMap.put("/path01",
                "https://host.com:8443/screentest/v1/@@Brand@@/@@Region@@/screen/@@StoreCode@@/loginPath01");
        loginUrlMap.put("/path02",
                "https://host.com:8443/screentest/v1/@@Brand@@/@@Region@@/screen/@@StoreCode@@/loginPath02");
        String loginDefaultUrl =
                "https://host.com:8443/screentest/v1/@@Brand@@/@@Region@@/screen/@@StoreCode@@/default";

        DynamicRedirectionSavedRequestWrapper targetWrapper =
                new DynamicRedirectionSavedRequestWrapper(savedRequest, loginUrlMap,
                        loginDefaultUrl);

        // Expect throwing an exception.
        thrown.expect(SystemException.class);
        thrown.expectMessage("The value to be replaced is null or empty character. originalString="
                + "https://host.com:8443/screentest/v1/@@Brand@@/@@Region@@/screen/@@StoreCode@@/loginPath01"
                + " / key=@@StoreCode@@ / value=null");

        // execute test method.
        targetWrapper.getRedirectUrl();

    }

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.authentication.saml.DynamicRedirectionSavedRequestWrapper#getRedirectUrl()}.
     * 
     * Case :: If the replacement value of the store code is empty.
     */
    @Test
    public void testGetRedirectUrl_replaceStoreCodeEmptyError() {

        // input.
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE01"));
        UserDetails userDetails = new UserDetails("testStoreCode", "testUser", "testPassword", true,
                true, true, true, authorityList);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Map<String, String> pathVariableMap = new HashMap<>();
        pathVariableMap.put(StoreRequestPathVariableHolder.KEY_BRAND, "testBrand");
        pathVariableMap.put(StoreRequestPathVariableHolder.KEY_REGION, "testRegion");
        pathVariableMap.put(StoreRequestPathVariableHolder.KEY_STORECODE, "");
        StoreRequestPathVariableHolder.setRequestPathVariableMap(pathVariableMap);

        SavedRequest savedRequest = new TestSavedRequest(
                "https://host.com:7443/screentest/v1/uq/jp/screen/path01?param1=aaa#fragments");
        Map<String, String> loginUrlMap = new HashMap<>();
        loginUrlMap.put("/path01",
                "https://host.com:8443/screentest/v1/@@Brand@@/@@Region@@/screen/@@StoreCode@@/loginPath01");
        loginUrlMap.put("/path02",
                "https://host.com:8443/screentest/v1/@@Brand@@/@@Region@@/screen/@@StoreCode@@/loginPath02");
        String loginDefaultUrl =
                "https://host.com:8443/screentest/v1/@@Brand@@/@@Region@@/screen/@@StoreCode@@/default";

        DynamicRedirectionSavedRequestWrapper targetWrapper =
                new DynamicRedirectionSavedRequestWrapper(savedRequest, loginUrlMap,
                        loginDefaultUrl);

        // Expect throwing an exception.
        thrown.expect(SystemException.class);
        thrown.expectMessage("The value to be replaced is null or empty character. originalString="
                + "https://host.com:8443/screentest/v1/@@Brand@@/@@Region@@/screen/@@StoreCode@@/loginPath01"
                + " / key=@@StoreCode@@ / value=");

        // execute test method.
        targetWrapper.getRedirectUrl();

    }

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.authentication.saml.DynamicRedirectionSavedRequestWrapper#getRedirectUrl()}.
     * 
     * Case :: When multiple store codes are transmitted as query parameters.
     */
    @Test
    public void testGetRedirectUrl_multipleStoreCodes() {

        // input.
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE01"));
        UserDetails userDetails = new UserDetails("testStoreCode", "testUser", "testPassword", true,
                true, true, true, authorityList);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Map<String, String> pathVariableMap = new HashMap<>();
        pathVariableMap.put(StoreRequestPathVariableHolder.KEY_BRAND, "testBrand");
        pathVariableMap.put(StoreRequestPathVariableHolder.KEY_REGION, "testRegion");
        StoreRequestPathVariableHolder.setRequestPathVariableMap(pathVariableMap);

        SavedRequest savedRequest = new TestSavedRequest(
                "https://host.com:7443/screentest/v1/uq/jp/screen/action?param1=aaa&store_code=111111&store_code=222222#fragments");
        Map<String, String> loginUrlMap = new HashMap<>();
        loginUrlMap.put("/path01",
                "https://host.com:8443/screentest/v1/@@Brand@@/@@Region@@/screen/@@StoreCode@@/loginPath01");
        loginUrlMap.put("/path02",
                "https://host.com:8443/screentest/v1/@@Brand@@/@@Region@@/screen/@@StoreCode@@/loginPath02");
        String loginDefaultUrl =
                "https://host.com:8443/screentest/v1/@@Brand@@/@@Region@@/screen/@@StoreCode@@/default";

        DynamicRedirectionSavedRequestWrapper targetWrapper =
                new DynamicRedirectionSavedRequestWrapper(savedRequest, loginUrlMap,
                        loginDefaultUrl);

        // Expect throwing an exception.
        thrown.expect(SystemException.class);
        thrown.expectMessage(
                "Multiple store codes were passed from the portal. StoreCodes=111111,222222");

        // execute test method.
        targetWrapper.getRedirectUrl();

    }

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.authentication.saml.DynamicRedirectionSavedRequestWrapper#getCookies()}.
     */
    @Test
    public void testGetCookies() {

        // input.
        SavedRequest savedRequest = new TestSavedRequest("");
        Map<String, String> loginUrlMap = new HashMap<>();
        String loginDefaultUrl = "";

        DynamicRedirectionSavedRequestWrapper targetWrapper =
                new DynamicRedirectionSavedRequestWrapper(savedRequest, loginUrlMap,
                        loginDefaultUrl);

        // execute test method.
        List<Cookie> actualCookies = targetWrapper.getCookies();

        // assert output.
        List<Cookie> expectedCookies = new ArrayList<>();
        expectedCookies.add(new Cookie("cookieName", "cookieValue"));

        assertEquals(expectedCookies.size(), actualCookies.size());
        assertEquals(expectedCookies.get(0).getName(), actualCookies.get(0).getName());
        assertEquals(expectedCookies.get(0).getValue(), actualCookies.get(0).getValue());

    }

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.authentication.saml.DynamicRedirectionSavedRequestWrapper#getMethod()}.
     */
    @Test
    public void testGetMethod() {

        // input.
        SavedRequest savedRequest = new TestSavedRequest("");
        Map<String, String> loginUrlMap = new HashMap<>();
        String loginDefaultUrl = "";

        DynamicRedirectionSavedRequestWrapper targetWrapper =
                new DynamicRedirectionSavedRequestWrapper(savedRequest, loginUrlMap,
                        loginDefaultUrl);

        // execute test method.
        String actualMethod = targetWrapper.getMethod();

        // assert output.
        assertEquals("testMethod", actualMethod);

    }

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.authentication.saml.DynamicRedirectionSavedRequestWrapper#getHeaderValues(java.lang.String)}.
     */
    @Test
    public void testGetHeaderValues() {

        // input.
        SavedRequest savedRequest = new TestSavedRequest("");
        Map<String, String> loginUrlMap = new HashMap<>();
        String loginDefaultUrl = "";

        DynamicRedirectionSavedRequestWrapper targetWrapper =
                new DynamicRedirectionSavedRequestWrapper(savedRequest, loginUrlMap,
                        loginDefaultUrl);

        // execute test method.
        List<String> actualHeaderValues = targetWrapper.getHeaderValues("test");

        // assert output.
        List<String> expectedHeaderValues = new ArrayList<>();
        expectedHeaderValues.add("testHeaderValue1");
        expectedHeaderValues.add("testHeaderValue2");

        assertEquals(expectedHeaderValues, actualHeaderValues);

    }

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.authentication.saml.DynamicRedirectionSavedRequestWrapper#getHeaderNames()}.
     */
    @Test
    public void testGetHeaderNames() {

        // input.
        SavedRequest savedRequest = new TestSavedRequest("");
        Map<String, String> loginUrlMap = new HashMap<>();
        String loginDefaultUrl = "";

        DynamicRedirectionSavedRequestWrapper targetWrapper =
                new DynamicRedirectionSavedRequestWrapper(savedRequest, loginUrlMap,
                        loginDefaultUrl);

        // execute test method.
        Collection<String> actualHeaderNames = targetWrapper.getHeaderNames();

        // assert output.
        Collection<String> expectedHeaderNames = new ArrayList<>();
        expectedHeaderNames.add("testHeader1");
        expectedHeaderNames.add("testHeader2");
        expectedHeaderNames.add("testHeader3");

        assertEquals(expectedHeaderNames, actualHeaderNames);

    }

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.authentication.saml.DynamicRedirectionSavedRequestWrapper#getLocales()}.
     */
    @Test
    public void testGetLocales() {

        // input.
        SavedRequest savedRequest = new TestSavedRequest("");
        Map<String, String> loginUrlMap = new HashMap<>();
        String loginDefaultUrl = "";

        DynamicRedirectionSavedRequestWrapper targetWrapper =
                new DynamicRedirectionSavedRequestWrapper(savedRequest, loginUrlMap,
                        loginDefaultUrl);

        // execute test method.
        List<Locale> actualLocales = targetWrapper.getLocales();

        // assert output.
        List<Locale> expectedLocales = new ArrayList<>();
        expectedLocales.add(Locale.CANADA);
        expectedLocales.add(Locale.JAPAN);

        assertEquals(expectedLocales, actualLocales);

    }

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.authentication.saml.DynamicRedirectionSavedRequestWrapper#getParameterValues(java.lang.String)}.
     */
    @Test
    public void testGetParameterValues() {

        // input.
        SavedRequest savedRequest = new TestSavedRequest(
                "https://host.com:7443/screentest/v1/uq/jp/screen/path01?test=aaa&store_code=000000#fragments");
        Map<String, String> loginUrlMap = new HashMap<>();
        String loginDefaultUrl = "";

        DynamicRedirectionSavedRequestWrapper targetWrapper =
                new DynamicRedirectionSavedRequestWrapper(savedRequest, loginUrlMap,
                        loginDefaultUrl);

        // execute test method.
        String[] actualParameterValues = targetWrapper.getParameterValues("test");

        // assert output.
        String[] expectedParameterValues = savedRequest.getParameterValues("test");

        assertArrayEquals(expectedParameterValues, actualParameterValues);

    }

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.authentication.saml.DynamicRedirectionSavedRequestWrapper#getParameterMap()}.
     */
    @Test
    public void testGetParameterMap() {

        // input.
        SavedRequest savedRequest = new TestSavedRequest(
                "https://host.com:7443/screentest/v1/uq/jp/screen/path01?parameterKey1=parameterValue1&store_code=000000&parameterKey2=parameterValue21&parameterKey2=parameterValue22#fragments");
        Map<String, String> loginUrlMap = new HashMap<>();
        String loginDefaultUrl = "";

        DynamicRedirectionSavedRequestWrapper targetWrapper =
                new DynamicRedirectionSavedRequestWrapper(savedRequest, loginUrlMap,
                        loginDefaultUrl);

        // execute test method.
        Map<String, String[]> actualParameterMap = targetWrapper.getParameterMap();

        // assert output.
        String[] parameterValue1 = {"parameterValue1"};
        String[] parameterValue2 = {"parameterValue21", "parameterValue22"};

        assertArrayEquals(parameterValue1, actualParameterMap.get("parameterKey1"));
        assertArrayEquals(parameterValue2, actualParameterMap.get("parameterKey2"));

    }

    /**
     * SavedRequest for unit test.
     */
    private class TestSavedRequest implements SavedRequest {

        /** Serial version UID. */
        private static final long serialVersionUID = -5029717293244274837L;

        /** Redirect URL. */
        private String redirectUrl;

        /** Query parameters. */
        private MultiValueMap<String, String> queryParameters;

        /**
         * @throws URISyntaxException Constructor.
         * 
         * @param redirectUrl redirect URL.
         */
        public TestSavedRequest(String redirectUrl) {
            this.redirectUrl = redirectUrl;
            try {
                URI uri = new URI(redirectUrl);
                this.queryParameters = UriComponentsBuilder.fromUri(uri).build().getQueryParams();
            } catch (URISyntaxException e) {
                this.queryParameters =
                        UriComponentsBuilder.fromHttpUrl(redirectUrl).build().getQueryParams();
            }
        }

        /**
         * Returns redirect URL.
         * 
         * @return redirect URL.
         */
        @Override
        public String getRedirectUrl() {
            return this.redirectUrl;
        }

        /**
         * Return null for not using it.
         * 
         * @return Fixed value for unit test.
         */
        @Override
        public List<Cookie> getCookies() {
            List<Cookie> actualCookies = new ArrayList<>();
            actualCookies.add(new Cookie("cookieName", "cookieValue"));
            return actualCookies;
        }

        /**
         * Return null for not using it.
         * 
         * @return Fixed value for unit test.
         */
        @Override
        public String getMethod() {
            return "testMethod";
        }

        /**
         * Return null for not using it.
         * 
         * @param name name.
         * @return Fixed value for unit test.
         */
        @Override
        public List<String> getHeaderValues(String name) {
            List<String> actualHeaderValues = new ArrayList<>();
            actualHeaderValues.add("testHeaderValue1");
            actualHeaderValues.add("testHeaderValue2");
            return actualHeaderValues;
        }

        /**
         * Return null for not using it.
         * 
         * @return Fixed value for unit test.
         */
        @Override
        public Collection<String> getHeaderNames() {
            Collection<String> actualHeaderNames = new ArrayList<>();
            actualHeaderNames.add("testHeader1");
            actualHeaderNames.add("testHeader2");
            actualHeaderNames.add("testHeader3");
            return actualHeaderNames;
        }

        /**
         * Return null for not using it.
         * 
         * @return Fixed value for unit test.
         */
        @Override
        public List<Locale> getLocales() {
            List<Locale> actualLocales = new ArrayList<>();
            actualLocales.add(Locale.CANADA);
            actualLocales.add(Locale.JAPAN);
            return actualLocales;
        }

        /**
         * Return null for not using it.
         * 
         * @param name name.
         * @return Fixed value for unit test.
         */
        @Override
        public String[] getParameterValues(String name) {
            List<String> actualParameterValues = queryParameters.get(name);
            if (actualParameterValues == null) {
                return null;
            } else {
                return actualParameterValues.toArray(new String[actualParameterValues.size()]);
            }
        }

        /**
         * Return null for not using it.
         * 
         * @return Fixed value for unit test.
         */
        @Override
        public Map<String, String[]> getParameterMap() {
            Map<String, String[]> actualParameterMap = new HashMap<>();
            queryParameters.keySet().stream().forEach(
                    key -> actualParameterMap.put(key, getParameterValues(key)));
            return actualParameterMap;
        }

    }

}
