/**
 * @(#)ErrorName.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.constants;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;

/**
 * enum for error name.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
public interface ErrorName {


    /**
     * basis error's enum error name.
     */
    enum  Basis implements ErrorName {

         /**
         * validation error.
         */
         VALIDATION_ERROR,

         /**
         * error occurred in request.
         */
         UNAVAILABLE_REQUEST,

         /**
         * system error.
         */
         SYSTEM_ERROR,

         /**
         * async request timeout error.
         */
         ASYNC_TIMEOUT,

         /**
         * resource access occurred error.
         */
         SERVICE_COMMUNICATION_ERROR

    }

    /**
     * business error's enum error name.
     */
    enum Business implements ErrorName {

        /**
         * Required authorized parameters do not exist error.
         */
        NO_AUTHENTICATION,

        /**
         * The authentication can not execute the API error.
         */
        FORBIDDEN_AUTHENTICATION,

        /**
         * If-Match in HTTP Header is invalid. Please check the latest information with Etag.
         */
        ETAG_ERROR,

        /**
         * This API can not execute. Please check details and modify it.
         */
        BUSINESS_CHECK_ERROR,

        /**
         * Resource busy. Please redo this api after a brief interval.
         */
        ACCESS_LOCK_RESOURCE,

        /**
         * hmac validation failed error.
         */
        HMAC_VALIDATION_FAILED
    }

    /**
     * deserializer string error name to interface.
     * @param errorName errorName
     * @return errorName
     */
    @JsonCreator
    static ErrorName deserializer(String errorName) {

        return Arrays.stream(Basis.values()).anyMatch(basis -> basis.name().equals(errorName))
                ? ErrorName.Basis.valueOf(errorName) : ErrorName.Business.valueOf(errorName);

    }
}
