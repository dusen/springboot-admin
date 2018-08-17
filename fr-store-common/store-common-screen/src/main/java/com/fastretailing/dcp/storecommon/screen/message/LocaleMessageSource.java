/**
 * @(#)LocaleMessageSource.java
 *
 *                              Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.message;

import java.util.Locale;

/**
 * This class is the interface for getting messages from external resources.
 */
public interface LocaleMessageSource {

    /**
     * Try to resolve the message. The message is taken from the resource file. The locale is
     * determined by this part.
     * 
     * @param code Code for getting messages from the resource file.
     * @return Resolved message.
     */
    String getMessage(String code);

    /**
     * Try to resolve the message. The message is taken from the resource file. The locale is
     * determined by this part.
     * 
     * @param code Code for getting messages from the resource file.
     * @param args The array of variables to set in the message variable part.
     * @return Resolved message.
     */
    String getMessage(String code, Object[] args);

    /**
     * set resource's locale information.
     * 
     * @param locale the locale information.
     */
    void setLocale(Locale locale);

    /**
     * get resource's locale information.
     * 
     * @return the locale information.
     */
    Locale getLocale();

}
