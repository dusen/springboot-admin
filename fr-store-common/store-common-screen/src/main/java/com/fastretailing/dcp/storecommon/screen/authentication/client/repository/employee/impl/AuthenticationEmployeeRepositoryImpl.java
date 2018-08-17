/**
 * @(#)AuthenticationEmployeeRepositoryImpl.java
 *
 *                                               Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.authentication.client.repository.employee.impl;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import com.fastretailing.dcp.common.api.threadlocal.RequestPathVariableHolder;
import com.fastretailing.dcp.common.api.uri.UriResolver;
import com.fastretailing.dcp.common.exception.SystemException;
import com.fastretailing.dcp.common.hmac.annotation.HmacAuthentication;
import com.fastretailing.dcp.storecommon.screen.authentication.client.constant.ItemKey;
import com.fastretailing.dcp.storecommon.screen.authentication.client.entity.employee.AuthenticationEmployeeInformation;
import com.fastretailing.dcp.storecommon.screen.authentication.client.repository.employee.AuthenticationEmployeeRepository;

/**
 * Employee API call class for authentication.
 */
@Repository
public class AuthenticationEmployeeRepositoryImpl implements AuthenticationEmployeeRepository {

    /** Rest template. */
    @Autowired
    private RestTemplate restTemplate;

    /** URI resolver. */
    @Autowired
    private UriResolver uriResolver;

    /** Carry over stock list URI. */
    private static final String EMPLOYEE_INFORMATION_URI =
            "client.apiConfig.employee.uri.apis.employeeInformation.get";

    /**
     * {@inheritDoc}
     */
    @HmacAuthentication(value = "employee")
    public AuthenticationEmployeeInformation requestEmployeeInformation(String employeeCode) {

        Map<String, String> pathParameterMap = new HashMap<>();

        pathParameterMap.put(ItemKey.BRAND, RequestPathVariableHolder.getBrand());
        pathParameterMap.put(ItemKey.REGION, RequestPathVariableHolder.getRegion());
        pathParameterMap.put(ItemKey.EMPLOYEE_CODE, employeeCode);

        ResponseEntity<AuthenticationEmployeeInformation> responseEntity = restTemplate
                .getForEntity(uriResolver.getUriAccordingToServerType(EMPLOYEE_INFORMATION_URI),
                        AuthenticationEmployeeInformation.class, pathParameterMap);

        HttpStatus statusCode = responseEntity.getStatusCode();
        if (statusCode.is2xxSuccessful()) {
            return responseEntity.getBody();
        } else {
            throw new SystemException(
                    "Employee API(employee-information) call status code is not 20x. EmployeeCode="
                            + employeeCode + "/StatusCode=" + statusCode);
        }

    }
}
