/**
 * @(#)ScreenLocaleResolver.java
 *
 *                               Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.config;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

/**
 * {@link org.springframework.web.servlet.LocaleResolver} implementation that supply the locale
 * resolution for screen application.
 *
 */
public class ScreenLocaleResolver implements LocaleResolver {

    /** System default language. */
    @Value("${system.defaultlanguage}")
    private String defaultLanguage;

    /**
     * Thread local.
     */
    private ThreadLocal<Locale> localThreadLocal = new ThreadLocal<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public Locale resolveLocale(HttpServletRequest request) {

        Locale locale = localThreadLocal.get();
        if (locale == null) {
            locale = StringUtils.parseLocaleString(defaultLanguage);
        }
        return locale;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
        localThreadLocal.set(locale);

    }

}
