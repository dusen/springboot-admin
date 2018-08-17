/**
 * @(#)ServiceLogInterceptor.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.api.log;

import com.fastretailing.dcp.common.util.CommonUtility;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

/**
 * ServiceLogInterceptor.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Aspect
@Component
@Slf4j
public class ServiceLogInterceptor {

    /**
     * message.
     */
    @Autowired
    private MessageSource messageSource;

    /**
     * CommonUtility.
     */
    @Autowired
    private CommonUtility commonUtility;

    /**
     * message id (start).
     */
    private static final String MSG_START = "i.common.log.service.start";

    /**
     * message id (end).
     */
    private static final String MSG_END = "i.common.log.service.end";

    /**
     * the attribute name of class in log.
     */
    private static final String LOG_ATTRIBUTE_CLASS = "class";

    /**
     * the attribute name of method in log.
     */
    private static final String LOG_ATTRIBUTE_METHOD = "method";

    /**
     * the attribute name of service start time in log.
     */
    private static final String LOG_ATTRIBUTE_START = "serviceStartTime";

    /**
     * the attribute name of service end time in log.
     */
    private static final String LOG_ATTRIBUTE_END = "serviceEndTime";

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
    @Around("@within(org.springframework.stereotype.Service)")
    public Object serviceLog(ProceedingJoinPoint joinPoint) throws Throwable {
        // get the executing class object
        Class<?> target = joinPoint.getTarget().getClass();
        // write the start log
        writeStartLog(joinPoint.getArgs(),
                      target.getName(),
                      joinPoint.getSignature().getName());
        // start instant
        Instant startInstant = commonUtility.getOperationAt().toInstant(ZoneOffset.UTC);
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
        logMap.put(LOG_ATTRIBUTE_CLASS, className);
        logMap.put(LOG_ATTRIBUTE_METHOD, methodName);
        // add all parameters to log map.
        if (args != null && args.length > 0) {
            int index = 1;
            for(Object arg : args){
                logMap.put("parameter-" + index, arg);
                index++;
            }
        }
        logMap.put(LOG_ATTRIBUTE_START,
                commonUtility.getOperationAt().atOffset(ZoneOffset.UTC).toString());
        log.info(messageSource.getMessage(MSG_START, new Object[]{}, Locale.getDefault()), logMap);
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
        logMap.put(LOG_ATTRIBUTE_CLASS, className);
        logMap.put(LOG_ATTRIBUTE_METHOD, methodName);
        // calculate the execute times
        LocalDateTime now = commonUtility.getOperationAt();
        logMap.put(LOG_ATTRIBUTE_END, now.atOffset(ZoneOffset.UTC).toString());
        Instant endInstant = now.toInstant(ZoneOffset.UTC);
        long times = ChronoUnit.MILLIS.between(startInstant, endInstant);
        logMap.put(LOG_ATTRIBUTE_TIMES, times + "ms");
        // end log
        log.info(messageSource.getMessage(MSG_END, new Object[]{}, Locale.getDefault()), logMap);
    }
}
