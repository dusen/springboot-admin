/**
 * @(#)CommonItemController.java
 *
 *                               Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.controller;

import java.util.Locale;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.LocaleResolver;
import com.fastretailing.dcp.storecommon.screen.config.UrlPathConfig;
import com.fastretailing.dcp.storecommon.screen.form.CommonBaseForm;
import com.fastretailing.dcp.storecommon.screen.menu.MenuComposer;
import com.fastretailing.dcp.storecommon.screen.message.LocaleMessageSource;

/**
 * Set common item and locale information from request.
 *
 */
@ControllerAdvice
public final class CommonItemController {

    /** Request parameter name of default language. */
    private static final String DEFAULT_LANGUAGE_REQUEST_PARAMETER = "lang";

    /**
     * Bean for retrieving locale from request.
     */
    @Autowired
    private LocaleResolver localeResolver;

    /**
     * Bean for getting message from properties.
     */
    @Autowired
    private LocaleMessageSource messageSource;

    /**
     * Bean for getting menu list.
     */
    @Autowired
    private MenuComposer menuComposer;

    /**
     * Bean for getting UrlPathConfig from configuration.
     */
    @Autowired
    private UrlPathConfig urlPathConfig;

    /**
     * Process common item.
     *
     * @param commonBaseForm The data object of common item.
     * @param request Request.
     * @param response Response.
     */
    @ModelAttribute
    public CommonBaseForm processCommonItem(CommonBaseForm commonBaseForm,
            HttpServletRequest request, HttpServletResponse response) {

        if (StringUtils.isEmpty(commonBaseForm.getDefaultLocale())) {
            String defaultLocale =
                    Optional.ofNullable(request.getParameter(DEFAULT_LANGUAGE_REQUEST_PARAMETER))
                            .orElse(Locale.getDefault().toString());
            commonBaseForm.setDefaultLocale(defaultLocale);
        }

        // Switch language by locale in common item.
        String locale = commonBaseForm.getSpecifyLocale();
        if (StringUtils.isEmpty(locale)) {
            locale = commonBaseForm.getDefaultLocale();
        }

        String menuName = commonBaseForm.getMenuName();
        if (StringUtils.isEmpty(menuName) && request.getParameterMap()
                .containsKey(MenuComposer.MENU_NAME_REQUEST_PARAMETER)) {
            menuName = request.getParameter(MenuComposer.MENU_NAME_REQUEST_PARAMETER);
            commonBaseForm.setMenuName(menuName);
        }

        commonBaseForm.setMenuList(menuComposer.getMenuList(menuName));

        if (!localeResolver.resolveLocale(request).equals(StringUtils.parseLocaleString(locale))) {
            localeResolver.setLocale(request, response, StringUtils.parseLocaleString(locale));
        }

        if (!messageSource.getLocale().equals(StringUtils.parseLocaleString(locale))) {
            messageSource.setLocale(StringUtils.parseLocaleString(locale));
        }

        if (urlPathConfig != null) {
            String[] paths =
                    request.getRequestURI().substring(request.getContextPath().length() + 1).split(
                            "/");
            if (paths.length >= 2) {
                // set {brand} path
                urlPathConfig.setBrandCode(paths[0]);
                // set {region} path
                urlPathConfig.setRegionCode(paths[1]);
            }
            commonBaseForm.setUrlBasePath(urlPathConfig.getContextPath());
        }

        commonBaseForm.setBrandCode(urlPathConfig.getBrandCode());
        commonBaseForm.setCountryCode(urlPathConfig.getRegionCode());

        return commonBaseForm;
    }
}
