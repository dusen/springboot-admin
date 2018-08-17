/**
 * @(#)AuthenticationEmployeeInformation.java
 *
 *                                            Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.authentication.client.entity.employee;

import com.fastretailing.dcp.storecommon.dto.CommonStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Employee information for authentication.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AuthenticationEmployeeInformation extends CommonStatus {

    /**
     * Employee data.
     */
    private EmployeeData employeeData;

}
