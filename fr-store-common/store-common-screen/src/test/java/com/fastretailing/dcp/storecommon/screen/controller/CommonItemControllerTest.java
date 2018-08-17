/**
 * @(#)CommonItemControllerTest.java
 *
 *                                   Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.LocaleResolver;
import com.fastretailing.dcp.storecommon.screen.authentication.entity.UserDetails;
import com.fastretailing.dcp.storecommon.screen.config.UrlPathConfig;
import com.fastretailing.dcp.storecommon.screen.form.CommonBaseForm;
import com.fastretailing.dcp.storecommon.screen.form.MenuElement;
import com.fastretailing.dcp.storecommon.screen.menu.MenuComposer;
import com.fastretailing.dcp.storecommon.screen.message.LocaleMessageSource;

/**
 * Unit test of CommonItemController class.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class CommonItemControllerTest {

    /** Create LocaleResolver Mock. */
    @Mock
    private LocaleResolver mockLocaleResolver;

    /** Create LocaleMessageSource Mock. */
    @Mock
    private LocaleMessageSource mockMessageSource;

    /** Create MenuComposer Mock. */
    @Mock
    private MenuComposer menuComposer;

    /**
     * Bean for getting UrlPathConfig from configuration.
     */
    @Mock
    private UrlPathConfig urlPathConfig;

    /** Test target object of CommonItemController. */
    @InjectMocks
    private CommonItemController commonItemController;

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
     * <LI>Target method：processCommonItem.
     * <LI>Condition：Common item was not set.
     * <LI>Verification result confirmation：The acquired data matches the expected value.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    public void testProcessCommonItem01() throws Exception {

        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE1"));
        UserDetails userDetails = new UserDetails("storeCode", "userId", "testPassword", true, true,
                true, true, authorityList);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Set expectation value
        CommonBaseForm expCommonBaseForm = new CommonBaseForm();
        expCommonBaseForm.setDefaultLocale(Locale.ENGLISH.toString());
        expCommonBaseForm.setMenuName("MENU01");
        expCommonBaseForm.setUrlBasePath("/storeinventory/v1/uq/japan/screen");
        expCommonBaseForm.setBrandCode("uq");
        expCommonBaseForm.setCountryCode("japan");

        MenuElement menuElement1 = new MenuElement();
        menuElement1.setMenuId("screen1");
        menuElement1.setUrl("/screen1");
        MenuElement menuElement2 = new MenuElement();
        menuElement2.setMenuId("screen2");
        menuElement2.setUrl("/screen2");
        List<MenuElement> menuList = Arrays.asList(menuElement1, menuElement2);
        expCommonBaseForm.setMenuList(menuList);

        when(menuComposer.getMenuList("MENU01")).thenReturn(menuList);

        request.setParameter("lang", Locale.ENGLISH.toString());
        // Mock for LocaleResolver handle.
        when(mockLocaleResolver.resolveLocale(request)).thenReturn(Locale.JAPANESE);

        // Mock for MessageSource handle.
        when(mockMessageSource.getLocale()).thenReturn(Locale.JAPANESE);

        // Prepare common item information for test.
        CommonBaseForm paramCommonBaseForm = new CommonBaseForm();
        paramCommonBaseForm.setMenuName("MENU01");

        request.setRequestURI("/uq/japan/screen/screen1");
        when(urlPathConfig.getContextPath()).thenReturn("/storeinventory/v1/uq/japan/screen");
        when(urlPathConfig.getRegionCode()).thenReturn("japan");
        when(urlPathConfig.getBrandCode()).thenReturn("uq");

        // Method execution
        CommonBaseForm actCommonBaseForm =
                commonItemController.processCommonItem(paramCommonBaseForm, request, response);
        // Confirm result
        assertThat(actCommonBaseForm, is(expCommonBaseForm));
        assertThat(actCommonBaseForm.getLoginUserId(), is("userId"));
        assertThat(actCommonBaseForm.getLoginStoreCode(), is("storeCode"));

        // verify if the LocaleResolver.resolveLocale method is called only once successfully.
        verify(mockLocaleResolver, times(1)).resolveLocale(request);
        // verify if the LocaleResolver.setLocale method is called only once successfully.
        verify(mockLocaleResolver, times(1)).setLocale(request, response, Locale.ENGLISH);
        // verify if the MessageSource.getLocale method is called only once successfully.
        verify(mockMessageSource, times(1)).getLocale();
        // verify if the MessageSource.setLocale method is called only once successfully.
        verify(mockMessageSource, times(1)).setLocale(Locale.ENGLISH);
        reset(mockLocaleResolver);
        reset(mockMessageSource);
    }

    /**
     * <UL>
     * <LI>Target method：processCommonItem.
     * <LI>Condition：Common item is set, and value is same of last time.
     * <LI>Verification result confirmation：The acquired data matches the expected value.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    public void testProcessCommonItem02() throws Exception {

        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE1"));
        UserDetails userDetails = new UserDetails("storeCode", "userId", "testPassword", true, true,
                true, true, authorityList);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Set expectation value
        CommonBaseForm expCommonBaseForm = new CommonBaseForm();
        expCommonBaseForm.setMenuName("MENU01");
        expCommonBaseForm.setDefaultLocale(Locale.ENGLISH.toString());
        expCommonBaseForm.setUrlBasePath("/storeinventory/v1/uq/japan/screen");
        expCommonBaseForm.setBrandCode("uq");
        expCommonBaseForm.setCountryCode("japan");

        MenuElement menuElement1 = new MenuElement();
        menuElement1.setMenuId("screen1");
        menuElement1.setUrl("/screen1");
        MenuElement menuElement2 = new MenuElement();
        menuElement2.setMenuId("screen2");
        menuElement2.setUrl("/screen2");
        List<MenuElement> menuList = Arrays.asList(menuElement1, menuElement2);
        expCommonBaseForm.setMenuList(menuList);

        request.setParameter(MenuComposer.MENU_NAME_REQUEST_PARAMETER, "MENU01");
        when(menuComposer.getMenuList("MENU01")).thenReturn(menuList);

        request.setParameter("lang", Locale.ENGLISH.toString());
        // Mock for LocaleResolver handle.
        when(mockLocaleResolver.resolveLocale(request)).thenReturn(Locale.JAPANESE);

        // Mock for MessageSource handle.
        when(mockMessageSource.getLocale()).thenReturn(Locale.JAPANESE);

        // Prepare common item information for test.
        CommonBaseForm paramCommonBaseForm = new CommonBaseForm();
        request.setRequestURI("/uq/japan/screen/screen1");
        when(urlPathConfig.getContextPath()).thenReturn("/storeinventory/v1/uq/japan/screen");
        when(urlPathConfig.getRegionCode()).thenReturn("japan");
        when(urlPathConfig.getBrandCode()).thenReturn("uq");

        // Method execution
        CommonBaseForm actCommonBaseForm =
                commonItemController.processCommonItem(paramCommonBaseForm, request, response);
        // Confirm result
        assertThat(actCommonBaseForm, is(expCommonBaseForm));
        assertThat(actCommonBaseForm.getLoginUserId(), is("userId"));
        assertThat(actCommonBaseForm.getLoginStoreCode(), is("storeCode"));

        // verify if the LocaleResolver.resolveLocale method is called only once successfully.
        verify(mockLocaleResolver, times(1)).resolveLocale(request);
        // verify if the LocaleResolver.setLocale method is called only once successfully.
        verify(mockLocaleResolver, times(1)).setLocale(request, response, Locale.ENGLISH);
        // verify if the MessageSource.getLocale method is called only once successfully.
        verify(mockMessageSource, times(1)).getLocale();
        // verify if the MessageSource.setLocale method is called only once successfully.
        verify(mockMessageSource, times(1)).setLocale(Locale.ENGLISH);
        reset(mockLocaleResolver);
        reset(mockMessageSource);
    }
}
