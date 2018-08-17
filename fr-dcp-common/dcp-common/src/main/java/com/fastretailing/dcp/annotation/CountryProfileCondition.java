/**
 * @(#)CountryProfileCondition.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.annotation;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;

/**
 * Switch business logic of country.This class is for determine country's profile.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
class CountryProfileCondition implements Condition {

    /**
     * determine which profile's bean will be load.
     * @param context Environment's context
     * @param metadata annotation's metadata
     * @return matched?
     */
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        // get environment
        Environment environment = context.getEnvironment();

        if (environment != null) {
            MultiValueMap<String, Object> attrs =
                    metadata.getAllAnnotationAttributes(Country.class.getName());
            if (attrs != null) {
                for (Object value : attrs.get("value")) {
                    boolean matched = Arrays.stream(((Countries[]) value))
                            .map(Countries::getCountry)
                            .anyMatch(e -> e.equalsIgnoreCase(environment.getProperty("country")));

                    if (matched) {
                        return true;
                    }
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

}