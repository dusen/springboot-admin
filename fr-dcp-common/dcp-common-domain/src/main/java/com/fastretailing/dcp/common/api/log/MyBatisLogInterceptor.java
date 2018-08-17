/**
 * @(#)MyBatisLogInterceptor.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.api.log;

import com.fastretailing.dcp.common.util.CommonUtility;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

/**
 * MyBatisLogInterceptor.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Intercepts({@Signature(type = StatementHandler.class,
                        method = "query",
                        args = {Statement.class, ResultHandler.class}),
        @Signature(type = StatementHandler.class,
                   method = "update",
                   args = {Statement.class})})
@Component
@Slf4j
public class MyBatisLogInterceptor implements Interceptor {

    /**
     * the attribute name of sql in log.
     */
    private static final String LOG_ATTRIBUTE_SQL = "sql";

    /**
     * the attribute name of sql parameter in log.
     */
    private static final String LOG_ATTRIBUTE_PARAMETER = "parameter";

    /**
     * the attribute name of sql state in log.
     */
    private static final String LOG_ATTRIBUTE_STATE = "state";

    /**
     * the attribute name of executing times in log.
     */
    private static final String LOG_ATTRIBUTE_TIMES = "times";

    /**
     * CommonUtility.
     */
    @Autowired
    private CommonUtility commonUtility;

    /**
     * mybatis StatementHandler Interceptor.<br>
     * When error occurred in sql executing, This handler will log sql and parameters.
     * 
     * @param invocation Invocation
     * @return sql execute result
     * @throws Throwable Throwable
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        if (!(invocation.getTarget() instanceof StatementHandler)) {
            return invocation.proceed();
        } else {
            StatementHandler handler = (StatementHandler) invocation.getTarget();
            // start instant
            Instant startInstant = commonUtility.getOperationAt().toInstant(ZoneOffset.UTC);
            try {
                return invocation.proceed();
            } catch (InvocationTargetException e) {
                // write the error log
                writeErrorLog(handler, startInstant, e);
                throw e;
            }
        }
    }

    /**
     * write the sql log.
     * @param handler handler
     * @param startInstant start instant
     * @param e sql exception
     */
    private void writeErrorLog(StatementHandler handler,
                               Instant startInstant,
                               InvocationTargetException e) {
        if (e.getTargetException() instanceof SQLException) {
            Map<String, Object> logMap = new LinkedHashMap<>();
            // sql
            logMap.put(LOG_ATTRIBUTE_SQL, handler.getBoundSql().getSql());
            // parameter
            if (handler.getBoundSql().getParameterObject() != null) {
                logMap.put(LOG_ATTRIBUTE_PARAMETER,
                        handler.getBoundSql().getParameterObject().toString());
            }
            // state
            logMap.put(LOG_ATTRIBUTE_STATE, ((SQLException) e.getTargetException()).getSQLState());

            // calculate the execute times
            // times
            Instant endInstant = commonUtility.getOperationAt().toInstant(ZoneOffset.UTC);
            logMap.put(LOG_ATTRIBUTE_TIMES,
                    ChronoUnit.MILLIS.between(startInstant, endInstant) + "ms");
            log.info("", logMap);
        }
    }

    /**
     * plugin setting.
     * 
     * @param target wrap target
     * @return wrap result
     */
    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    /**
     * properties setting.
     * 
     * @param properties Properties
     */
    @Override
    public void setProperties(Properties properties) {
    }
}
