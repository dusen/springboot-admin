/**
 * @(#)AwsS3LogInterceptor.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.util.aws;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.fastretailing.dcp.storecommon.util.DateUtility;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Log interceptor for AwsS3Utility class.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Aspect
@Component
@Slf4j
public class AwsS3LogInterceptor {

    /**
     * message id (start).
     */
    private static final String MSG_START = "AwsS3Utility.start{}";

    /**
     * message id (end).
     */
    private static final String MSG_END = "AwsS3Utility.end{}";

    /**
     * the attribute name of method in log.
     */
    private static final String LOG_ATTRIBUTE_METHOD = "method";

    /**
     * the attribute name of service start time in log.
     */
    private static final String LOG_ATTRIBUTE_START = "StartTime";

    /**
     * the attribute name of service end time in log.
     */
    private static final String LOG_ATTRIBUTE_END = "EndTime";

    /**
     * the attribute name of service executing times in log.
     */
    private static final String LOG_ATTRIBUTE_TIMES = "times";

    /**
     * output the method's log of service.
     *
     * @param joinPoint join point
     * @return the result of service's method
     * @throws Throwable Throwable
     */
    @Around("target(com.fastretailing.dcp.storecommon.util.aws.AwsS3Utility)")
    public Object awsS3Log(ProceedingJoinPoint joinPoint) throws Throwable {
        // get the executing class object
        Class<?> target = joinPoint.getTarget().getClass();
        // write the start log
        writeStartLog(joinPoint.getArgs(),
                      target.getName(),
                      joinPoint.getSignature().getName());
        // start instant
        Instant startInstant = DateUtility.getZonedDateTimeUtc().toInstant();
        Object result;
        try {
            // execute service method
            result = joinPoint.proceed();
        } finally {
            // write the end log
            writeEndLog(target.getName(),
                    joinPoint.getSignature().getName(),
                    startInstant);
        }
        return result;
    }

    /**
     * write the start log.
     * @param args the arguments of method
     * @param className the name of class
     * @param methodName the name of method
     */
    private void writeStartLog(Object[] args,
                               String className,
                               String methodName) {
        // the message of parameters
        Map<String, Object> logMap = new LinkedHashMap<>();
        logMap.put(LOG_ATTRIBUTE_METHOD, methodName);
        if (args != null && args.length > 0) {
            logMap.put("param", Stream.of(args).map(arg -> {
                if (arg instanceof String[]) {
                    return String.join(",", (String[])arg);
                }
                return arg;
            }).collect(Collectors.toList()));
        }
        logMap.put(LOG_ATTRIBUTE_START,
                DateUtility.getZonedDateTimeUtc().toInstant());
        log.info(MSG_START, logMap);
    }

    /**
     * write the end log.
     * @param className the name of class
     * @param methodName the name of method
     * @param startInstant the start instant
     */
    private void writeEndLog(String className,
                             String methodName,
                             Instant startInstant) {
        Map<String, Object> logMap = new LinkedHashMap<>();
        logMap.put(LOG_ATTRIBUTE_METHOD, methodName);
        // calculate the execute times
        logMap.put(LOG_ATTRIBUTE_END, DateUtility.getZonedDateTimeUtc().toString());
        Instant endInstant = DateUtility.getZonedDateTimeUtc().toInstant();
        long times = ChronoUnit.MILLIS.between(startInstant, endInstant);
        logMap.put(LOG_ATTRIBUTE_TIMES, times + "ms");
        // end log
        log.info(MSG_END, logMap);
    }
}
