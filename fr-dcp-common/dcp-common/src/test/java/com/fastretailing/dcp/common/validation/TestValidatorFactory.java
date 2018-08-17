/**
 * @(#)TestValidatorFactory.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */


package com.fastretailing.dcp.common.validation;

import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.HibernateValidatorConfiguration;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.resourceloading.AggregateResourceBundleLocator;
import org.hibernate.validator.spi.resourceloading.ResourceBundleLocator;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;

public class TestValidatorFactory {

    private static final String MESSAGE_PROPERTIES_FILE_PATH = "ValidationMessages";

    private TestValidatorFactory() {}

    public static Validator createValidator() {
        HibernateValidatorConfiguration configure = Validation.byProvider(HibernateValidator.class).configure();

        List<String> resources = new ArrayList<String>();
        resources.add(MESSAGE_PROPERTIES_FILE_PATH);

        ResourceBundleLocator aggregateResourceBundleLocator = new AggregateResourceBundleLocator(resources);

        configure.messageInterpolator(new ResourceBundleMessageInterpolator(aggregateResourceBundleLocator));
        ValidatorFactory factory = configure.buildValidatorFactory();
        return factory.getValidator();
    }
}
