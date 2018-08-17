/**
 * @(#)HeaderAuthenticationInterceptor.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.web.interceptor;


import com.fastretailing.dcp.common.api.jvm.OmsJvmParameters;
import com.fastretailing.dcp.common.constants.ErrorName;
import com.fastretailing.dcp.common.constants.LogLevel;
import com.fastretailing.dcp.common.exception.NoAuthenticationException;
import com.fastretailing.dcp.common.model.Detail;
import com.fastretailing.dcp.common.model.IssueCode;
import com.fastretailing.dcp.common.model.ResultObject;
import com.fastretailing.dcp.common.threadlocal.UserIdAndMemberIdHolder;
import com.fastretailing.dcp.common.util.ResultObjectUtility;
import com.fastretailing.dcp.common.web.authentication.annotation.HeaderAuthentication;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Locale;

/**
 * HeaderAuthenticationInterceptor.
 *
 * Non-empty check for admin-userid or front-memberid when the {@link HeaderAuthentication} is used.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Order(1)
@Aspect
@Component
public class HeaderAuthenticationInterceptor {

    /** error message. */
    @Autowired
    private MessageSource messageSource;

    /**
     * Non-empty check for admin-userid or front-memberid
     *
     * @param headerAuthentication HeaderAuthentication
     * @throws Throwable Throwable
     */
    @Before("@annotation(headerAuthentication)")
    public void invoke(HeaderAuthentication headerAuthentication)
            throws Throwable {

        boolean hasError = false;
        String issueCodeKey = StringUtils.EMPTY;

        if (OmsJvmParameters.isAdminApi()) {
            if (headerAuthentication.userIdCheckFlag()
                    && StringUtils.isEmpty(UserIdAndMemberIdHolder.getUserId())) {
                issueCodeKey = "AdminUserIdNotFound";
                hasError = true;
            }
        } else {
            if (headerAuthentication.memberIdCheckFlag()
                    && StringUtils.isEmpty(UserIdAndMemberIdHolder.getMemberId())) {
                issueCodeKey = "FrontMemberIdNotFound";
                hasError = true;
            }
        }

        if (hasError) {
            ResultObject resultObject = ResultObjectUtility.ResultObjectBuilder.getBuilder()
                    .withDetails(Arrays.asList(
                            new Detail(
                                    IssueCode.CODE_MAP.get(issueCodeKey),
                                    messageSource.getMessage(IssueCode.CODE_MAP.get(issueCodeKey),
                                            null, Locale.getDefault()))))
                    .withName(ErrorName.Business.NO_AUTHENTICATION)
                    .withDebugId(LogLevel.INFO, "40000002")
                    .withMessage("e.common.exception.no-authentication")
                    .build();

            throw new NoAuthenticationException(resultObject);
        }

    }
}
