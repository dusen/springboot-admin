/**
 * @(#)CommonUtility.java
 * 
 *                        Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.util;

import java.util.ArrayList;
import java.util.List;
import com.fastretailing.dcp.common.constants.ErrorName;
import com.fastretailing.dcp.common.constants.LogLevel;
import com.fastretailing.dcp.common.exception.BusinessException;
import com.fastretailing.dcp.common.model.Detail;
import com.fastretailing.dcp.common.model.ResultObject;
import com.fastretailing.dcp.storecommon.message.SystemMessageSource;
import com.fastretailing.dcp.storecommon.type.FunctionType;
import com.fastretailing.dcp.storecommon.type.MessageType;
import com.fastretailing.dcp.storecommon.type.PlatformShortName;
import lombok.extern.slf4j.Slf4j;

/**
 * Common is Utility.
 *
 */
@Slf4j
public class CommonUtility {

    /**
     * Under line for message id.
     */
    private static final String UNDERLINE = "_";

    /**
     * Constructor.
     */
    private CommonUtility() {}

    /**
     * Create message id.
     * 
     * @param logLevel The 1st digit is log level.
     * @param platformShortName The 3rd-5th digit is plat form short name.
     * @param functiontype The 7th digits is function type code.
     * @param messageType The 8th digit is message type.
     * @param serialNumber The 9th-14th is serial number.
     * @return Message id.
     */
    public static String createMessageId(LogLevel logLevel, PlatformShortName platformShortName,
            MessageType messageType, FunctionType functiontype, String serialNumber) {
        StringBuilder stringBuilder = new StringBuilder().append(logLevel.toString())
                .append(UNDERLINE)
                .append(platformShortName.getValue())
                .append(UNDERLINE)
                .append(messageType.getValue())
                .append(functiontype.getValue())
                .append(serialNumber);
        return stringBuilder.toString();
    }

    /**
     * Create basic result object.
     * 
     * @param systemMessageSource System message source.
     * @param errorName Error name.
     * @param informationLink Information link.
     * @param links Link list string.
     * @param details detail list.
     * @return Result object.
     */
    public static ResultObject createResultObject(SystemMessageSource systemMessageSource,
            ErrorName errorName, String debugId, String informationLink, List<String> links,
            List<Detail> details) {

        ResultObject resultObject =
                new ResultObject(errorName, debugId, systemMessageSource.getMessage(debugId));
        resultObject.setInformationLink(informationLink);
        resultObject.setDetails(details);
        resultObject.setLinks(links);

        return resultObject;
    }

    /**
     * Create result object.
     * 
     * @param systemMessageSource System message source.
     * @param errorName Error name.
     * @param fieldId Field id.
     * @param value Value.
     * @param issueId Issue id.
     * @param debugId Debug id.
     * @return Result object.
     */
    public static ResultObject createResultObject(SystemMessageSource systemMessageSource,
            ErrorName errorName, String fieldId, String value, String issueId, String debugId) {

        List<Detail> details = new ArrayList<>();
        Detail detail = new Detail(issueId, systemMessageSource.getMessage(issueId));
        detail.setField(fieldId);
        detail.setValue(value);
        details.add(detail);

        ResultObject result =
                new ResultObject(errorName, debugId, systemMessageSource.getMessage(debugId));
        result.setDetails(details);
        return result;
    }

    /**
     * Create result object message .
     * 
     * @param systemMessageSource System message source.
     * @param errorName Error name.
     * @param fieldId Field id.
     * @param value Value.
     * @param issueId Issue id.
     * @param debugId Debug id.
     * @param messages Message list.
     * @return Result object.
     */
    public static ResultObject createResultObject(SystemMessageSource systemMessageSource,
            ErrorName errorName, String fieldId, String value, String issueId, String debugId,
            Object... messages) {

        List<Detail> details = new ArrayList<>();
        Detail detail = new Detail(issueId, systemMessageSource.getMessage(issueId, messages));
        detail.setField(fieldId);
        detail.setValue(value);
        details.add(detail);

        ResultObject result =
                new ResultObject(errorName, debugId, systemMessageSource.getMessage(debugId));
        result.setDetails(details);
        return result;
    }
    
    /**
     * Create business exception.
     * 
     * @param fieldId Validation error field id.
     * @param value Validation error field value.
     * @param issueId Validation error issue id.
     * @param errorName Result object error name value.
     * @param debugId Result object debug id.
     * @param systemMessageSource System message source object.
     * @param messageParameter Message parameter.
     * 
     * @return Business exception.
     */
    public static BusinessException createBusinessException(String fieldId, String value,
            String issueId, ErrorName errorName, String debugId,
            SystemMessageSource systemMessageSource,
            Object... messageParameter) {

        log.error(systemMessageSource.getMessage(issueId, messageParameter));

        ResultObject result = createResultObject(systemMessageSource, errorName, fieldId, value,
                issueId, debugId, messageParameter);

        return new BusinessException(result);
    }
    
    /**
     * Create business exception by system.
     * 
     * @param field Validation error field.
     * @param value Validation error field value.
     * @param issue Validation error issue value.
     * @param name Result object name value.
     * @param debugId Result object debug id.
     * @param systemMessageSource System message source object.
     * 
     * @return Business exception.
     */
    public static BusinessException createBusinessExceptionBySystem(String field, String value,
            String issue, ErrorName name, String debugId, SystemMessageSource systemMessageSource) {

        log.error(issue);
        
        // Set detail information.
        Detail detail = new Detail();
        detail.setField(field);
        detail.setValue(value);
        detail.setIssue(issue);
        List<Detail> details = new ArrayList<>();
        details.add(detail);

        // Set response information.
        ResultObject result = new ResultObject();
        result.setName(name);
        result.setDebugId(debugId);
        result.setMessage(systemMessageSource.getMessage(debugId));
        result.setDetails(details);

        return new BusinessException(result);
    }
}
