/**
 * @(#)ScreenCommonUtility.java
 * 
 *                              Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.util;

import com.fastretailing.dcp.common.constants.ErrorName;
import com.fastretailing.dcp.common.model.ResultObject;
import com.fastretailing.dcp.storecommon.screen.message.LocaleMessageSource;

/**
 * Screen common is Utility.
 *
 */
public class ScreenCommonUtility {

    /**
     * Create result object.
     * 
     * @param localeMessageSource Locale message source.
     * @param errorName Error name.
     * @param debugId Debug id.
     * @param messageId MessageId id.
     * @return Result object.
     */
    public static ResultObject createResultObject(LocaleMessageSource localeMessageSource,
            ErrorName errorName, String debugId, String messageId) {
        return new ResultObject(errorName, debugId, localeMessageSource.getMessage(debugId));
    }

    /**
     * Create result object message .
     * 
     * @param localeMessageSource Locale message source.
     * @param errorName Error name.
     * @param debugId Debug id.
     * @param messageId MessageId id.
     * @param arguments Argument array.
     * @return Result object.
     */
    public static ResultObject createResultObject(LocaleMessageSource localeMessageSource,
            ErrorName errorName, String debugId, String messageId, Object... arguments) {
        return new ResultObject(errorName, debugId,
                localeMessageSource.getMessage(messageId, arguments));
    }
}
