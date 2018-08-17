/**
 * @(#)ResultObjectUtility.java
 *
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.util;

import com.fastretailing.dcp.common.constants.ErrorName;
import com.fastretailing.dcp.common.constants.LogLevel;
import com.fastretailing.dcp.common.model.Detail;
import com.fastretailing.dcp.common.model.IssueCode;
import com.fastretailing.dcp.common.model.ResultObject;
import com.google.common.base.CaseFormat;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import javax.annotation.PostConstruct;
import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * ResultObject's utility.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Component
public class ResultObjectUtility {

    /**
     * Autowired CommonUtility.
     */
    @Autowired
    private CommonUtility autowiredCommonUtility;

    /**
     * CommonUtility.
     */
    private static CommonUtility commonUtility;

    /**
     * Autowired MessageSource.
     */
    @Autowired
    private MessageSource autowiredMessageSource;

    /**
     * MessageSource.
     */
    private static MessageSource messageSource;

    /**
     * Initialize utility's static field.
     */
    @PostConstruct
    public void initialize() {
        commonUtility = autowiredCommonUtility;
        messageSource = autowiredMessageSource;
    }

    /**
     * ResultObject's Builder.
     */
    public static class ResultObjectBuilder {

        /**
         * Target ResultObject instance.
         */
        private ResultObject result = new ResultObject();

        /**
         * Private constructor.
         */
        private ResultObjectBuilder() {
        }

        /**
         * ResultObject Builder's instance.
         */
        public static ResultObjectBuilder getBuilder() {
            return new ResultObjectBuilder();
        }

        /**
         * Set the ResultObject's name.
         * @param name Error Name
         * @return ResultObject Builder
         */
        public ResultObjectBuilder withName(final ErrorName name) {
            this.result.setName(name);
            return this;
        }

        /**
         * Set the ResultObject's debug id.
         * @param level log's level
         * @param debugId debug id
         * @return ResultObject Builder
         */
        public ResultObjectBuilder withDebugId(final LogLevel level, final String debugId) {
            this.result.setDebugId(commonUtility.getDebugId(level.toString(), debugId));
            return this;
        }

        /**
         * Set the ResultObject's message.
         * @param messageText message's content text
         * @return ResultObject Builder
         */
        public ResultObjectBuilder withMessageText(final String messageText) {
            this.result.setMessage(messageText);
            return this;
        }


        /**
         * Set the ResultObject's message.
         * @param messageCode message's code
         * @param args an array of arguments that will be filled in for params within the message
         * @return ResultObject Builder
         */
        public ResultObjectBuilder withMessage(final String messageCode, final Object... args) {
            this.result.setMessage(
                    messageSource.getMessage(messageCode, args, Locale.getDefault())
            );
            return this;
        }

        /**
         * Set the ResultObject's informationLink.
         * @param informationLink information link
         * @return ResultObject Builder
         */
        public ResultObjectBuilder withInformationLink(final String informationLink) {
            this.result.setInformationLink(informationLink);
            return this;
        }

        /**
         * Set the ResultObject's links.
         * @param links link's List instance
         * @return ResultObject Builder
         */
        public ResultObjectBuilder withLinks(final List<String> links) {
            if (CollectionUtils.isNotEmpty(links)) {
                this.result.setLinks(links);
            }
            return this;
        }

        /**
         * Set the ResultObject's links.
         * @param links links
         * @return ResultObject Builder
         */
        public ResultObjectBuilder withLinks(final String... links) {
            if (ArrayUtils.isNotEmpty(links)) {
                this.result.setLinks(Arrays.asList(links));
            }
            return this;
        }

        /**
         * Set the ResultObject's details.
         * @param details Detail's List instance
         * @return ResultObject Builder
         */
        public ResultObjectBuilder withDetails(final List<Detail> details) {
            if (CollectionUtils.isNotEmpty(details)) {
                this.result.setDetails(details);
            }
            return this;
        }

        /**
         * Set the ResultObject's details.
         * @param details Detail's instances
         * @return ResultObject Builder
         */
        public ResultObjectBuilder withDetails(final Detail... details) {
            if (ArrayUtils.isNotEmpty(details)) {
                this.result.setDetails(Arrays.asList(details));
            }
            return this;
        }

        /**
         * Build target ResultObject with set parameters.
         * @return Generic the target ResultObject
         */
        public ResultObject build() {
            return this.result;
        }
    }

    /**
     * Detail's Builder.
     */
    public static class DetailBuilder {

        /**
         * RegEx for camel to underline.
         */
        private static final Pattern CAMEL_TO_UNDERLINE_REGEX = Pattern.compile(
                "(\\p{Alpha}+)(\\p{Digit}+)$"
        );

        /**
         * RegEx for 'magnitude-relation-can-equal'.
         */
        private static final Pattern ISSUE_CODE_ANALYSE_REGEX = Pattern.compile(
                messageSource.getMessage(
                        "w.common.validation.magnitude-relation-can-equal",
                        new String[] {".*", ".*"},
                        Locale.getDefault()
                )
        );

        /**
         * Target Detail instance.
         */
        private Detail detail = new Detail();

        /**
         * Private constructor.
         */
        private DetailBuilder() {
        }

        /**
         * ResultObject's Detail Builder's instance.
         */
        public static DetailBuilder getBuilder() {
            return new DetailBuilder();
        }

        /**
         * Set the Detail's  field.
         * @param field field
         * @return Detail's builder
         */
        public DetailBuilder withField(final String field) {
            if (StringUtils.isNotBlank(field)) {
                this.detail.setField(toUnderline(field));
            }
            return this;
        }

        /**
         * Set the Detail's value.
         * @param value value
         * @return Detail's builder
         */
        public DetailBuilder withValue(final String value) {
            this.detail.setValue(value);
            return this;
        }

        /**
         * Set the Detail's issue code and issue.
         * @param issueCode issue enum's issue code
         * @return Detail's builder
         */
        public DetailBuilder withIssue(final String issueCode) {
            this.detail.setIssueCode(issueCode);
            this.detail.setIssue(
                messageSource.getMessage(issueCode, null, Locale.getDefault())
            );
            return this;
        }

        /**
         * Set the Detail's issue code and issue.
         * @param issueCode issue enum's issue code
         * @param args an array of arguments that will be filled in for params within the message
         * @return Detail's builder
         */
        public DetailBuilder withIssue(final String issueCode, final Object[] args) {
            this.detail.setIssueCode(issueCode);
            this.detail.setIssue(
                    messageSource.getMessage(issueCode, args, Locale.getDefault())
            );
            return this;
        }

        /**
         * Set the Detail's issue with given content.
         * @param issueCode issue enum's issue code
         * @param issueText issue's content text
         * @return Detail's builder
         */
        public DetailBuilder withIssue(final String issueCode, final String issueText) {
            this.detail.setIssueCode(issueCode);
            this.detail.setIssue(issueText);
            return this;
        }

        /**
         * Build target Detail with set parameters.
         * @return Detail instance
         */
        public Detail build() {
            return this.detail;
        }

        /**
         * Build target Detail with given FieldError List.
         * @return Detail List instance
         */
        public static List<Detail> build(final List<FieldError> errors) {
            List<Detail> details = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(errors)) {
                return errors.stream().map(DetailBuilder::build).collect(Collectors.toList());
            }
            return details;
        }

        /**
         * Build target Detail with given FieldError.
         * @return Detail instance
         */
        public static Detail build(final FieldError error) {
            Detail detail = new Detail();
            detail.setField(toUnderline(error.getField()));
            if (Objects.nonNull(error.getRejectedValue())) {
                detail.setValue(error.getRejectedValue().toString());
            }
            detail.setIssueCode(analyseIssueCode(error));
            detail.setIssue(error.getDefaultMessage());
            return detail;
        }

        /**
         * Set the Detail's filed,value,issue code,issue with given ConstraintViolation Set.
         * @param errors FieldError instance
         * @return Detail List's instance
         */
        public static List<Detail> build(final Set<ConstraintViolation> errors) {
            List<Detail> details = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(errors)) {
                return errors.stream().map(DetailBuilder::build).collect(Collectors.toList());
            }
            return details;
        }

        /**
         * Set the Detail's filed,value,issue code,issue with given ConstraintViolation.
         * @param error FieldError instance
         * @return Detail's instance
         */
        public static Detail build(final ConstraintViolation error) {
            Detail detail = new Detail();
            detail.setField(toUnderline(error.getPropertyPath().toString()));
            if (Objects.nonNull(error.getInvalidValue())) {
                detail.setValue(error.getInvalidValue().toString());
            }
            detail.setIssueCode(analyseIssueCode(error));
            detail.setIssue(error.getMessage());
            return detail;
        }

        /**
         * Convert field name form camel to underline.
         *
         * @param camel camel field
         * @return under line field
         */
        private static String toUnderline(final String camel) {
            return CAMEL_TO_UNDERLINE_REGEX
                    .matcher(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, camel))
                    .replaceAll("$1_$2");
        }

        /**
         * Analyse issue code from filedError.
         *
         * @param error field error
         * @return issueCode
         */
        private static String analyseIssueCode(final FieldError error) {
            return analyseIssueCode(error.getCode(), error.getDefaultMessage());
        }

        /**
         * Analyse issue code from ConstraintViolation.
         *
         * @param error field error
         * @return issueCode
         */
        private static String analyseIssueCode(final ConstraintViolation error) {

            String[] parts = StringUtils.splitByWholeSeparatorPreserveAllTokens(
                    error.getConstraintDescriptor().getAnnotation().annotationType().getName(),
                    "."
            );

            return analyseIssueCode(parts[parts.length - 1], error.getMessage());

        }

        /**
         * Analyse issue code.
         * @param errorCode error code
         * @param message message
         * @return issue code
         */
        private static String analyseIssueCode(final String errorCode, final String message) {

            if (!StringUtils.equals("MagnitudeRelation", errorCode)) {
                return IssueCode.CODE_MAP.get(errorCode);
            }

            return ISSUE_CODE_ANALYSE_REGEX.matcher(message).matches()
                ? IssueCode.CODE_MAP.get("MagnitudeRelationEqual") :
                  IssueCode.CODE_MAP.get("MagnitudeRelationNotEqual");
        }
    }


}
