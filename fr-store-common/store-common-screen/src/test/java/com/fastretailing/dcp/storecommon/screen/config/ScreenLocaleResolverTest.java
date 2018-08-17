/**
 * @(#)ScreenLocaleResolverTest.java
 *
 *                                   Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.config;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import java.util.Locale;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Unit test of ScreenLocaleResolver class.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ScreenLocaleResolverTest {

    /** Test target object of ScreenLocaleResolver. */
    @InjectMocks
    private ScreenLocaleResolver screenLocaleResolver = new ScreenLocaleResolver();

    /** Mock of httpServlet request. */
    private MockHttpServletRequest request;

    /** Mock of httpServlet response. */
    private MockHttpServletResponse response;

    /**
     * The preHandle method.
     * 
     * @throws Exception Exception that occurred.
     */
    @Before
    public void setUp() throws Exception {
        // Initialize mock of HttpServlet request.
        request = new MockHttpServletRequest();

        // Initialize mock of HttpServlet response.
        response = new MockHttpServletResponse();
    }

    /**
     * <UL>
     * <LI>Target method：resolveLocale.
     * <LI>Condition：Locale was set.
     * <LI>Verification result confirmation：The acquired data matches the expected value.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    public void testResolveLocale01() throws Exception {
        // Initialize private field of ScreenLocaleResolver object.
        ReflectionTestUtils.setField(screenLocaleResolver, "defaultLanguage", "en");

        ThreadLocal<Locale> localThreadLocal = new ThreadLocal<Locale>();
        localThreadLocal.set(Locale.JAPANESE);
        ReflectionTestUtils.setField(screenLocaleResolver, "localThreadLocal", localThreadLocal);

        // Set expectation value.
        Locale expLocale = Locale.JAPANESE;

        // Method execution
        Locale actLocale = screenLocaleResolver.resolveLocale(request);

        // Confirm result
        assertThat(actLocale, is(expLocale));
    }

    /**
     * <UL>
     * <LI>Target method：resolveLocale.
     * <LI>Condition：Locale was not set.
     * <LI>Verification result confirmation：The acquired data matches the expected value.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    public void testResolveLocale02() throws Exception {
        // Initialize private field of ScreenLocaleResolver object.
        ReflectionTestUtils.setField(screenLocaleResolver, "defaultLanguage", "en");

        // Set expectation value.
        Locale expLocale = Locale.ENGLISH;

        // Method execution
        Locale actLocale = screenLocaleResolver.resolveLocale(request);

        // Confirm result
        assertThat(actLocale, is(expLocale));
    }

    /**
     * <UL>
     * <LI>Target method：resolveLocale.
     * <LI>Condition：Locale was set.
     * <LI>Verification result confirmation：The acquired data matches the expected value.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    public void testSetLocale01() throws Exception {
        // Initialize private field of ScreenLocaleResolver object.
        ReflectionTestUtils.setField(screenLocaleResolver, "defaultLanguage", "en");

        ThreadLocal<Locale> localThreadLocal = new ThreadLocal<Locale>();
        localThreadLocal.set(Locale.JAPANESE);
        ReflectionTestUtils.setField(screenLocaleResolver, "localThreadLocal", localThreadLocal);

        // Set expectation value.
        Locale expLocale = Locale.CHINESE;

        // Method execution
        screenLocaleResolver.setLocale(request, response, Locale.CHINESE);

        // Confirm result
        @SuppressWarnings("unchecked")
        ThreadLocal<Locale> actLocalThreadLocal = (ThreadLocal<Locale>) ReflectionTestUtils
                .getField(screenLocaleResolver, "localThreadLocal");
        assertThat(actLocalThreadLocal.get(), is(expLocale));
    }

    /**
     * <UL>
     * <LI>Target method：resolveLocale.
     * <LI>Condition：Locale was not set.
     * <LI>Verification result confirmation：The acquired data matches the expected value.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    public void testSetLocale02() throws Exception {
        // Initialize private field of ScreenLocaleResolver object.
        ReflectionTestUtils.setField(screenLocaleResolver, "defaultLanguage", "en");

        // Set expectation value.
        Locale expLocale = Locale.JAPANESE;

        // Method execution
        screenLocaleResolver.setLocale(request, response, Locale.JAPANESE);

        // Confirm result
        @SuppressWarnings("unchecked")
        ThreadLocal<Locale> actLocalThreadLocal = (ThreadLocal<Locale>) ReflectionTestUtils
                .getField(screenLocaleResolver, "localThreadLocal");
        assertThat(actLocalThreadLocal.get(), is(expLocale));
    }
}
