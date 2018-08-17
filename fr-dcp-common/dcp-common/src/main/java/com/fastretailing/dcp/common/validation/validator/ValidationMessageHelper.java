/**
 * @(#)ValidationMessageHelper.java
 * 
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.validation.validator;

import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Arrays;
import java.util.Locale;

/**
 * <pre>
 * process message class for validation
 * it's needed that the bean of MessageSource
 * has read the properties file named ValidationMessage.properties
 *
 * </pre>
 * @author Fast Retailing
 * @version $Revision$
 */
public class ValidationMessageHelper {

    /**
     * <pre>
     * put label name input validation message process
     * if messageTemplate does not matches /^\{[i,e,w]\..+\.\d{4}\}$/,
     * return messageTemplate directly ,as not the key of message
     * and,message {@link NoSuchMessageException} occurs
     * sample as follow
     * 
     * ・validation message definition
     * w.ex.fw.5073={0}is different with {1}
     * 
     * ・label definition
     * label.CMN01.first=mail address
     * label.CMN01.second=mail address(confirm)
     * 
     * ・Annotation
     * {@literal @}FieldEquals(firstField = "first",
     * secondField = "second", messageParam= {"label.CMN01.first", "label.CMN01.second"})
     * 
     * ・output message
     * mail address is different with mail address(confirm)
     * </pre>
     * @param messageTemplate
     * specified value of annotation messageTemplate param. excepted is a key of property
     * @param messageParam
     * specified value of annotation messageParam param ,label name is property array
     * @param messageSource read validation message and label name{@link MessageSource}
     * @return annotation message with label name
     */
    public static String createValidationMessage(String messageTemplate,
            String[] messageParam, MessageSource messageSource) {
        if (!messageTemplate.matches("^\\{[iew]\\..+\\..+}$")) {
            return messageTemplate;
        } else {
            Locale locale = LocaleContextHolder.getLocale();
            String template = messageTemplate.substring(1, (messageTemplate.length() - 1));

            String[] paramArray = null;
            if (messageParam != null) {
                paramArray = Arrays.stream(messageParam)
                        .map(e -> messageSource.getMessage(e, null, locale))
                        .toArray(String[]::new);
            }

            return messageSource.getMessage(template, paramArray, locale);
        }
    }
}
