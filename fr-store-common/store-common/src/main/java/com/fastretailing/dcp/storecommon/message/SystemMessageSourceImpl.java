/**
 * @(#)SystemMessageSourceImpl.java
 *
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.message;

import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

/**
 * This class is the class for getting messages from external resources with system locale.
 */
@Component
public class SystemMessageSourceImpl implements SystemMessageSource {

    /**
     * Message source.
     */
    @Autowired
    private MessageSource source;

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getMessage(String code) {
        return this.getMessage(code, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getMessage(String code, Object[] args) {
        return source.getMessage(code, args, Locale.getDefault());
    }
}
