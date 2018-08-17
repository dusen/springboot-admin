/**
 * @(#)SystemMessageSource.java
 *
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.message;

/**
 * This interface is the interface for getting messages from external resources with system locale.
 */
public interface SystemMessageSource {

    /**
     * Try to resolve the message. Messages are taken from resource files. The locale uses the
     * system locale.
     * 
     * @param code Code for getting messages from the resource file.
     * @return Resolved message.
     */
    String getMessage(String code);

    /**
     * Try to resolve the message. Messages are taken from resource files. The locale uses the
     * system locale.
     * 
     * @param code Code for getting messages from the resource file.
     * @param args The array of variables to set in the message variable part.
     * @return Resolved message.
     */
    String getMessage(String code, Object[] args);

}
