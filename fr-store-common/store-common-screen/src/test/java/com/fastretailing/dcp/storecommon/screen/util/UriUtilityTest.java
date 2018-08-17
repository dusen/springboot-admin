/**
 * @(#)UriUtilityTest.java
 *
 *                         Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.fastretailing.dcp.common.exception.SystemException;

/**
 * UriUtility test class.
 */
public class UriUtilityTest {

    /** Exception assertion. */
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.util.UriUtility#getUriInformation(java.lang.String)}.
     * 
     * Case :: When the complete URL is an input value.
     */
    @Test
    public void testGetUriInformation_fullUrl() {

        // input.
        String inputUri =
                "https://host.com:7443/screentest/v1/uq/jp/screen/000000/action?param1=aaa&param2=bbb#fragments";

        // execute test method.
        UriInformation actualUriInformation = UriUtility.getUriInformation(inputUri);

        // assert output.
        UriInformation expectedUriInformation = new UriInformation();
        expectedUriInformation
                .setBasePath("https://host.com:7443/screentest/v1/uq/jp/screen/000000/action");
        expectedUriInformation.setQuery("param1=aaa&param2=bbb");
        expectedUriInformation.setFragment("fragments");

        assertEquals(expectedUriInformation, actualUriInformation);

    }

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.util.UriUtility#getUriInformation(java.lang.String)}.
     * 
     * Case :: When the empty URL is an input value.
     */
    @Test
    public void testGetUriInformation_empty() {

        // input.
        String inputUri = "";

        // execute test method.
        UriInformation actualUriInformation = UriUtility.getUriInformation(inputUri);

        // assert output.
        UriInformation expectedUriInformation = new UriInformation();
        expectedUriInformation.setBasePath("");
        expectedUriInformation.setQuery(null);
        expectedUriInformation.setFragment(null);

        assertEquals(expectedUriInformation, actualUriInformation);

    }

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.util.UriUtility#getUriInformation(java.lang.String)}.
     * 
     * Case :: When the null value is an input value.
     */
    @Test
    public void testGetUriInformation_null() {

        // input.
        String inputUri = null;

        // execute test method.
        UriInformation actualUriInformation = UriUtility.getUriInformation(inputUri);

        // assert output.
        UriInformation expectedUriInformation = new UriInformation();
        expectedUriInformation.setBasePath(null);
        expectedUriInformation.setQuery(null);
        expectedUriInformation.setFragment(null);

        assertEquals(expectedUriInformation, actualUriInformation);

    }

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.util.UriUtility#getBasePath(java.lang.String)}.
     * 
     * Case :: When the complete URL is an input value.
     */
    @Test
    public void testGetBasePath_fullUrl() {

        // input.
        String inputUri =
                "https://host.com:7443/screentest/v1/uq/jp/screen/000000/action?param1=aaa&param2=bbb#fragments";

        // execute test method.
        String actualBasePath = UriUtility.getBasePath(inputUri);

        // assert output.
        assertEquals("https://host.com:7443/screentest/v1/uq/jp/screen/000000/action",
                actualBasePath);

    }

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.util.UriUtility#getBasePath(java.lang.String)}.
     * 
     * Case :: When the empty URL is an input value.
     */
    @Test
    public void testGetBasePath_empty() {

        // input.
        String inputUri = "";

        // execute test method.
        String actualBasePath = UriUtility.getBasePath(inputUri);

        // assert output.
        assertEquals("", actualBasePath);

    }

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.util.UriUtility#getBasePath(java.lang.String)}.
     * 
     * Case :: When the null value is an input value.
     */
    @Test
    public void testGetBasePath_null() {

        // input.
        String inputUri = null;

        // execute test method.
        String actualBasePath = UriUtility.getBasePath(inputUri);

        // assert output.
        assertNull(actualBasePath);

    }

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.util.UriUtility#setRequestPathVariable(java.lang.String)}.
     * 
     * Case :: When the complete URL is an input value.
     */
    @Test
    public void testSetRequestPathVariable_fullUrl() {

        // initialize.
        StoreRequestPathVariableHolder.setBrandAndRegionMap(null);
        assertNull(StoreRequestPathVariableHolder.getBrand());
        assertNull(StoreRequestPathVariableHolder.getRegion());

        // input.
        String serverContextPath = "/screentest/v1";

        SavedRequest savedRequset = new TestSavedRequest(
                "https://host.com:7443/screentest/v1/uq/jp/screen/000000/action?param1=aaa&param2=bbb#fragments");
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("SPRING_SECURITY_SAVED_REQUEST", savedRequset);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(session);
        RequestContextHolder.setRequestAttributes(new TestServletRequestAttributes(request));

        // execute test method.
        UriUtility.setRequestPathVariable(serverContextPath);

        // assert output.
        assertEquals("uq", StoreRequestPathVariableHolder.getBrand());
        assertEquals("jp", StoreRequestPathVariableHolder.getRegion());
        assertEquals("000000", StoreRequestPathVariableHolder.getStoreCode());

    }

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.util.UriUtility#setRequestPathVariable(java.lang.String)}.
     * 
     * Case :: When the short URL is an input value.
     */
    @Test
    public void testSetRequestPathVariable_shortUrl() {

        // initialize.
        StoreRequestPathVariableHolder.setBrandAndRegionMap(null);
        assertNull(StoreRequestPathVariableHolder.getBrand());
        assertNull(StoreRequestPathVariableHolder.getRegion());

        // input.
        String serverContextPath = "/screentest/v1";

        SavedRequest savedRequset =
                new TestSavedRequest("https://host.com:7443/screentest/v1/uq/jp/screen/777777");
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("SPRING_SECURITY_SAVED_REQUEST", savedRequset);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(session);
        RequestContextHolder.setRequestAttributes(new TestServletRequestAttributes(request));

        // execute test method.
        UriUtility.setRequestPathVariable(serverContextPath);

        // assert output.
        assertEquals("uq", StoreRequestPathVariableHolder.getBrand());
        assertEquals("jp", StoreRequestPathVariableHolder.getRegion());
        assertEquals("777777", StoreRequestPathVariableHolder.getStoreCode());

    }

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.util.UriUtility#setRequestPathVariable(java.lang.String)}.
     * 
     * Case :: When the too short URL is an input value.
     */
    @Test
    public void testSetRequestPathVariable_tooShortUrl() {

        // initialize.
        StoreRequestPathVariableHolder.setBrandAndRegionMap(null);
        assertNull(StoreRequestPathVariableHolder.getBrand());
        assertNull(StoreRequestPathVariableHolder.getRegion());

        // input.
        String serverContextPath = "/screentest/v1";

        SavedRequest savedRequset = new TestSavedRequest("https://host.com:7443/screentest/v1/uq");
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("SPRING_SECURITY_SAVED_REQUEST", savedRequset);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(session);
        RequestContextHolder.setRequestAttributes(new TestServletRequestAttributes(request));

        // Expect throwing an exception.
        thrown.expect(SystemException.class);
        thrown.expectMessage(
                "The layer of the URL does not conform to API regulations. controllerPath=/uq");

        // execute test method.
        UriUtility.setRequestPathVariable(serverContextPath);

    }

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.util.UriUtility#setRequestPathVariable(java.lang.String)}.
     * 
     * Case :: When the empty URL is an input value.
     */
    @Test
    public void testSetRequestPathVariable_emptyUrl() {

        // initialize.
        StoreRequestPathVariableHolder.setBrandAndRegionMap(null);
        assertNull(StoreRequestPathVariableHolder.getBrand());
        assertNull(StoreRequestPathVariableHolder.getRegion());

        // input.
        String serverContextPath = "/screentest/v1";

        SavedRequest savedRequset = new TestSavedRequest("");
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("SPRING_SECURITY_SAVED_REQUEST", savedRequset);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(session);
        RequestContextHolder.setRequestAttributes(new TestServletRequestAttributes(request));

        // Expect throwing an exception.
        thrown.expect(SystemException.class);
        thrown.expectMessage("The URI does not conform to API regulations. URI=");

        // execute test method.
        UriUtility.setRequestPathVariable(serverContextPath);

    }

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.util.UriUtility#setRequestPathVariable(java.lang.String)}.
     * 
     * Case :: When the illegal store code URL is an input value.
     */
    @Test
    public void testSetRequestPathVariable_illegalStoreCodeUrl() {

        // initialize.
        StoreRequestPathVariableHolder.setBrandAndRegionMap(null);
        assertNull(StoreRequestPathVariableHolder.getBrand());
        assertNull(StoreRequestPathVariableHolder.getRegion());

        // input.
        String serverContextPath = "/screentest/v1";

        SavedRequest savedRequset =
                new TestSavedRequest("https://host.com:7443/screentest/v1/uq/jp/screen/77777a");
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("SPRING_SECURITY_SAVED_REQUEST", savedRequset);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(session);
        RequestContextHolder.setRequestAttributes(new TestServletRequestAttributes(request));

        // execute test method.
        UriUtility.setRequestPathVariable(serverContextPath);

        // assert output.
        assertEquals("uq", StoreRequestPathVariableHolder.getBrand());
        assertEquals("jp", StoreRequestPathVariableHolder.getRegion());
        assertNull(StoreRequestPathVariableHolder.getStoreCode());

    }

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.util.UriUtility#setRequestPathVariable(java.lang.String)}.
     * 
     * Case :: When the null value is an input value.
     */
    @Test
    public void testSetRequestPathVariable_null() {

        // initialize.
        StoreRequestPathVariableHolder.setBrandAndRegionMap(null);
        assertNull(StoreRequestPathVariableHolder.getBrand());
        assertNull(StoreRequestPathVariableHolder.getRegion());

        // input.
        String serverContextPath = "/screentest/v1";

        SavedRequest savedRequset = new TestSavedRequest(null);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("SPRING_SECURITY_SAVED_REQUEST", savedRequset);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(session);
        RequestContextHolder.setRequestAttributes(new TestServletRequestAttributes(request));

        // Expect throwing an exception.
        thrown.expect(SystemException.class);
        thrown.expectMessage("The URI is null. URI=null");

        // execute test method.
        UriUtility.setRequestPathVariable(serverContextPath);

    }

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.util.UriUtility#createPossiblePathList(java.lang.String)}.
     * 
     * Case :: When the null value is an input value.
     */
    @Test
    public void testCreatePossiblePathList_null() {

        // input.
        String requestPath = null;

        // execute test method.
        List<String> actualPathList = UriUtility.createPossiblePathList(requestPath);

        // assert output.
        List<String> expectedPathList = new ArrayList<>();
        assertEquals(expectedPathList, actualPathList);

    }

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.util.UriUtility#createPossiblePathList(java.lang.String)}.
     * 
     * Case :: When the empty value is an input value.
     */
    @Test
    public void testCreatePossiblePathList_empty() {

        // input.
        String requestPath = "";

        // execute test method.
        List<String> actualPathList = UriUtility.createPossiblePathList(requestPath);

        // assert output.
        List<String> expectedPathList = new ArrayList<>();
        assertEquals(expectedPathList, actualPathList);

    }

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.util.UriUtility#createPossiblePathList(java.lang.String)}.
     * 
     * Case :: When the normally value with store code layer is an input value.
     */
    @Test
    public void testCreatePossiblePathList_normally() {

        // input.
        String requestPath =
                "https://host.com:7443/screentest/v1/uq/jp/screen/000000/action?param1=aaa&param2=bbb#fragments";

        // execute test method.
        List<String> actualPathList = UriUtility.createPossiblePathList(requestPath);

        // assert output.
        List<String> expectedPathList = new ArrayList<>();
        expectedPathList.add("/000000/action");
        expectedPathList.add("/action");
        assertEquals(expectedPathList, actualPathList);

    }

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.util.UriUtility#createPossiblePathList(java.lang.String)}.
     * 
     * Case :: When the normally value without store code layer is an input value.
     */
    @Test
    public void testCreatePossiblePathList_normallyWithoutStoreCodeLayer() {

        // input.
        String requestPath =
                "https://host.com:7443/screentest/v1/uq/jp/screen/action?param1=aaa&param2=bbb#fragments";

        // execute test method.
        List<String> actualPathList = UriUtility.createPossiblePathList(requestPath);

        // assert output.
        List<String> expectedPathList = new ArrayList<>();
        expectedPathList.add("/action");
        assertEquals(expectedPathList, actualPathList);

    }

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.util.UriUtility#createPossiblePathList(java.lang.String)}.
     * 
     * Case :: When the normally value without store code layer is an input value.
     */
    @Test
    public void testCreatePossiblePathList_normallyWithoutStoreCodeLayer2() {

        // input.
        String requestPath =
                "https://host.com:7443/screentest/v1/uq/jp/screen/action/detail?param1=aaa&param2=bbb#fragments";

        // execute test method.
        List<String> actualPathList = UriUtility.createPossiblePathList(requestPath);

        // assert output.
        List<String> expectedPathList = new ArrayList<>();
        expectedPathList.add("/action/detail");
        expectedPathList.add("/detail");
        assertEquals(expectedPathList, actualPathList);

    }

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.util.UriUtility#createPossiblePathList(java.lang.String)}.
     * 
     * Case :: When the normally value without screen common layer is an input value.
     */
    @Test
    public void testCreatePossiblePathList_normallyWithoutScreenCommonLayer() {

        // input.
        String requestPath =
                "https://host.com:7443/screentest/v1/uq/jp/000000/action?param1=aaa&param2=bbb#fragments";

        // execute test method.
        List<String> actualPathList = UriUtility.createPossiblePathList(requestPath);

        // assert output.
        List<String> expectedPathList = new ArrayList<>();
        assertEquals(expectedPathList, actualPathList);

    }

    /**
     * ServletRequestAttributes for unit test.
     */
    private class TestServletRequestAttributes extends ServletRequestAttributes {
        /**
         * Constructor.
         * 
         * @param request HTTP request.
         */
        public TestServletRequestAttributes(HttpServletRequest request) {
            super(request);
        }
    }

    /**
     * SavedRequest for unit test.
     */
    private class TestSavedRequest implements SavedRequest {

        /** Serial version UID. */
        private static final long serialVersionUID = -5029717293244274837L;

        /** Redirect URL. */
        private String redirectUrl;

        /**
         * Constructor.
         * 
         * @param redirectUrl redirect URL.
         */
        public TestSavedRequest(String redirectUrl) {
            this.redirectUrl = redirectUrl;
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
         * @return null.
         */
        @Override
        public List<Cookie> getCookies() {
            return null;
        }

        /**
         * Return null for not using it.
         * 
         * @return null.
         */
        @Override
        public String getMethod() {
            return null;
        }

        /**
         * Return null for not using it.
         * 
         * @return null.
         */
        @Override
        public List<String> getHeaderValues(String name) {
            return null;
        }

        /**
         * Return null for not using it.
         * 
         * @return null.
         */
        @Override
        public Collection<String> getHeaderNames() {
            return null;
        }

        /**
         * Return null for not using it.
         * 
         * @return null.
         */
        @Override
        public List<Locale> getLocales() {
            return null;
        }

        /**
         * Return null for not using it.
         * 
         * @return null.
         */
        @Override
        public String[] getParameterValues(String name) {
            return null;
        }

        /**
         * Return null for not using it.
         * 
         * @return null.
         */
        @Override
        public Map<String, String[]> getParameterMap() {
            return null;
        }

    }

}
