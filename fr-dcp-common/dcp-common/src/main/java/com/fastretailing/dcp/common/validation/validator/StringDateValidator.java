/**
 * @(#)StringDateValidator.java
 * 
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.validation.validator;

import com.fastretailing.dcp.common.constants.DateTimeFormatterEnum;
import com.fastretailing.dcp.common.validation.annotation.StringDate;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

/**
 * <pre>
 *  check whether the input string matches specified formatter.
 *
 * </pre>
 * @author Fast Retailing
 * @version $Revision$
 */
public class StringDateValidator implements ConstraintValidator<StringDate, String> {

    /**
     * date formatter.
     **/
    private String[] patterns;

    /**
     * date formatter.
     **/
    private DateTimeFormatterEnum[] dateTimeFormatter;

    /**
     * ambiguous model flag (false:rigorous, true:ambiguous).
     **/
    private boolean lenient;

    /**
     * Initializes the validator in preparation for
     * {@link #isValid(Object, ConstraintValidatorContext)} calls.
     * The constraint annotation for a given constraint declaration
     * is passed.
     * <p/>
     * This method is guaranteed to be called before any use of this instance for
     * validation.
     *
     * @param annotation annotation instance for a given constraint declaration
     */
    @Override
    public void initialize(StringDate annotation) {
        patterns = annotation.patterns();
        lenient = annotation.lenient();
        dateTimeFormatter = annotation.dateTimeFormatter();
    }

    /**
     * Implements the validation logic.
     * The state of {@code value} must not be altered.
     * <p/>
     * This method can be accessed concurrently, thread-safety must be ensured
     * by the implementation.
     *
     * @param value object to validate
     * @param context context in which the constraint is evaluated
     *
     * @return {@code false} if {@code value} does not pass the constraint
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isEmpty(value)) {
            return true;
        } else {

            if (dateTimeFormatter.length != 0) {
                for (DateTimeFormatterEnum dateTimeFormatterEnum : dateTimeFormatter) {
                    if (isFormatted(dateTimeFormatterEnum.getFormatter(), value)) {
                        return true;
                    }
                }
            } else {
                for (String pattern: patterns) {
                    if (isFormatted(DateTimeFormatter.ofPattern(pattern), value)) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    /**
     * check whether the value is formatted with inputted dateTimeFormatter.
     * @param formatter DateTimeFormatter
     * @param value value
     * @return check result
     */
    private boolean isFormatted(DateTimeFormatter formatter, String value) {
        if (lenient) {
            formatter = formatter.withResolverStyle(ResolverStyle.LENIENT);
        } else {
            formatter = formatter.withResolverStyle(ResolverStyle.STRICT);
        }

        try {
            // when here is no Exception, return true
            formatter.parse(value);
            return true;
        } catch (Exception e) {
            // when value can not match this pattern, then check next pattern
        }

        return false;
    }
}
