/**
 * @(#)TransactionDataValidatorImpl.java
 *
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import com.fastretailing.dcp.storecommon.ValidationException;
import lombok.extern.slf4j.Slf4j;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.springframework.validation.annotation.Validated;

/**
 * Class for validation.
 */
@Slf4j
@Component
public class TransactionDataValidatorImpl implements TransactionDataValidator {

    @Autowired
    private Validator validator = null;

    /**
     * {@inheritDoc}
     */
    @Override
    public <V> void validate(@Validated V validatedData) {

        log.info("check validation  target class is" + validatedData);

        Set<ConstraintViolation<V>> result = validator.validate(validatedData);
        log.info("validatation message = " + result.toString());

        if (result != null && !result.isEmpty()) {
            throw new ValidationException(result.toString());
        }
    }

}
