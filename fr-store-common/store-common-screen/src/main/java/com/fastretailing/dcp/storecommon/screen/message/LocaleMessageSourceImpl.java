/**
 * @(#)LocaleMessageSourceImpl.java
 *
 *                                  Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.message;

import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * This class gets messages from external resources.
 */
@Component
public class LocaleMessageSourceImpl implements LocaleMessageSource, ApplicationContextAware {

    /**
     * Application context.
     */
    private ApplicationContext applicationContext;

    /**
     * Message source.
     */
    @Autowired
    private org.springframework.context.MessageSource source;

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getMessage(String code) {
        return source.getMessage(code, null, getLocale());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getMessage(String code, Object[] args) {
        return source.getMessage(code, args, getLocale());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLocale(Locale locale) {
        getMessageSourceParameter().setMessageLocale(locale);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Locale getLocale() {
        Locale locale = getMessageSourceParameter().getMessageLocale();
        if (locale == null) {
            locale = Locale.getDefault();
        }
        return locale;
    }

    /**
     * Set the application context of the argument to the field variable.
     * 
     * @param applicationContext Application context to set in the field.
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * Gets the bean that manages resource's locale.
     * 
     * @return Bean to manage resource's locale.
     */
    private MessageSourceParameter getMessageSourceParameter() {
        return applicationContext.getBean(MessageSourceParameter.class);
    }
}
