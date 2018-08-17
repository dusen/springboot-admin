/**
 * @(#)LogMessageUtility.java
 *
 *                            Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.util;

import java.util.List;
import java.util.StringJoiner;
import org.apache.commons.lang3.StringUtils;
import com.fastretailing.dcp.common.model.Detail;

/**
 * Log message utility class.
 */
public class LogMessageUtility {

    /** Line separator. */
    private static final String LINE_SEPARATOR = "\n";

    /**
     * Create log message.
     * 
     * @param details List of detail.
     * @return Log message.
     */
    public static String createLogMessage(List<Detail> details) {
        StringJoiner logMessage = new StringJoiner(LINE_SEPARATOR);
        details.forEach(detail -> {
            String fieldValue = StringUtils.isNotEmpty(detail.getField())
                    ? "(" + detail.getField() + "=" + detail.getValue() + ")"
                    : "";
            logMessage.add(detail.getIssue() + fieldValue);
        });
        return logMessage.toString();
    }
}
