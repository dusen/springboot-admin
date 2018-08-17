/**
 * @(#)AuthenticationEmployeeRepository.java
 *
 *                                           Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.authentication.client.repository.employee;

import com.fastretailing.dcp.storecommon.screen.authentication.client.entity.employee.AuthenticationEmployeeInformation;

/**
 * Employee API call interface for authentication.
 */
public interface AuthenticationEmployeeRepository {

    /**
     * Requests employee information API.
     * 
     * @param employeeCode Employee code.
     * @return Employee information.
     */
    public AuthenticationEmployeeInformation requestEmployeeInformation(String employeeCode);

}
