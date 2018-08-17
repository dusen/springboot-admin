/**
 * @(#)DataSourceSwitchInterceptor.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.api.datasource.interceptor;

import com.fastretailing.dcp.common.api.datasource.DataSourceConfiguration;
import com.fastretailing.dcp.common.api.datasource.annotation.InnerTransactional;
import com.fastretailing.dcp.common.api.datasource.annotation.ReadOnlyTransactional;
import com.fastretailing.dcp.common.api.datasource.annotation.WriteTransactional;
import com.fastretailing.dcp.common.api.datasource.annotation.WriteTransactionalAsNew;
import com.fastretailing.dcp.common.api.datasource.context.DataSourceContext;
import com.fastretailing.dcp.common.api.datasource.context.DataSourceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.batch.core.step.tasklet.SystemCommandException;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * The datasource's aspect class.<br>
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Slf4j
@Order(1)
@Aspect
@Component
public class DataSourceSwitchInterceptor {

    /**
     * Around advice for the Master datasource.<br>
     * @param proceedingJoinPoint join point
     * @param writeTransactional the annotation for the Master datasource
     * @return join point's return
     * @throws Throwable Throwable
     */
    @Around("@annotation(writeTransactional)")
    public Object invoke(
            ProceedingJoinPoint proceedingJoinPoint,
            WriteTransactional writeTransactional
    ) throws Throwable {
        try {
            if (log.isDebugEnabled()) {
                log.debug("datasource - " + writeTransactional.name());
            }
            this.setDataSourceContext(
                    writeTransactional.name(),
                    DataSourceConfiguration.DATASOURCE_TYPE_WRITE
            );
            return proceedingJoinPoint.proceed();
        } finally {
            clearDataSourceContext();
        }
    }

    /**
     * Around advice for the Read-Only datasource.<br>
     * @param proceedingJoinPoint join point
     * @param readOnlyTransactional the annotation for the Read-Only datasource
     * @return join point's return
     * @throws Throwable Throwable
     */
    @Around("@annotation(readOnlyTransactional)")
    public Object invoke(
            ProceedingJoinPoint proceedingJoinPoint,
            ReadOnlyTransactional readOnlyTransactional
    ) throws Throwable {
        try {
            if (log.isDebugEnabled()) {
                log.debug("datasource - " + readOnlyTransactional.name());
            }
            this.setDataSourceContext(
                    readOnlyTransactional.name(),
                    DataSourceConfiguration.DATASOURCE_TYPE_READONLY
            );
            return proceedingJoinPoint.proceed();
        } finally {
            clearDataSourceContext();
        }
    }

    /**
     * Around advice for the switch datasource.<br>
     * @param proceedingJoinPoint join point
     * @param writeTransactionalAsNew the annotation WriteTransactionalAsNew
     * @return join point's return
     * @throws Throwable Throwable
     */
    @Around("@annotation(writeTransactionalAsNew)")
    public Object invoke(
            ProceedingJoinPoint proceedingJoinPoint,
            WriteTransactionalAsNew writeTransactionalAsNew
    ) throws Throwable {
        try {
            if (log.isDebugEnabled()) {
                log.debug("switch to datasource - " + writeTransactionalAsNew.name());
            }
            this.setDataSourceContext(
                    writeTransactionalAsNew.name(),
                    DataSourceConfiguration.DATASOURCE_TYPE_WRITE
            );
            return proceedingJoinPoint.proceed();
        } finally {
            clearDataSourceContext();
        }
    }

    /**
     * Around advice for check sub-service's datasource was set.<br>
     * @param proceedingJoinPoint join point
     * @param innerTransactional the annotation for the InnerTransactional
     * @return join point's return
     * @throws Throwable Throwable
     */
    @Around("@annotation(innerTransactional)")
    public Object invoke(
            ProceedingJoinPoint proceedingJoinPoint,
            InnerTransactional innerTransactional) throws Throwable {
        if (Objects.isNull(DataSourceContextHolder.getDataSourceContext())) {
            throw new SystemCommandException("There is no outer transaction.");
        }
        return proceedingJoinPoint.proceed();
    }

    /**
     * Set the datasource's context into the datasource's holder.<br>
     * @param dataSourceName datasource's name
     * @param dataSourceType datasource's type
     */
    private void setDataSourceContext(String dataSourceName, String dataSourceType) {
        DataSourceContextHolder.setDataSourceContext(
                new DataSourceContext(dataSourceName, dataSourceType)
        );
    }

    /**
     * Clean the datasource's context.<br>
     */
    private void clearDataSourceContext() {
        DataSourceContextHolder.cleanDataSourceContext();
    }
}
