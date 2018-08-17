/**
 * @(#)DynamoDbLogInterceptor.java
 *
 *                                 Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction.log;

import java.lang.reflect.InvocationTargetException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fastretailing.dcp.common.exception.SystemException;
import com.fastretailing.dcp.storecommon.message.SystemMessageSource;

/**
 * Logging intercepter for DynamoDB.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Aspect
@Component
public class DynamoDbLogInterceptor {

    /**
     * system message source.
     */
    @Autowired
    private SystemMessageSource systemMessageSource;

    /**
     * message id (state).
     */
    private static final String MSG_DYNAMODB_STATE = "e.common.log.dynamodb.state";

    /**
     * Intercept DynamoDB call processing.
     * 
     * @param joinPoint Join point of AOP processing.
     * @return Processing result object.
     * @throws Throwable Thrown exception.
     */
    @Around("execution(public * org.socialsignin.spring.data.dynamodb.core.DynamoDBTemplate.*(..))")
    public Object intercept(ProceedingJoinPoint joinPoint) throws Throwable {
        // start time stamp
        try {
            return joinPoint.proceed();
        } catch (InvocationTargetException e) {
            // write the error log
            throw new SystemException(
                    systemMessageSource.getMessage(MSG_DYNAMODB_STATE, joinPoint.getArgs()), e);
        }
    }
}
