/**
 * @(#)EmployeeData.java
 *
 *                       Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.authentication.client.entity.employee;

import java.util.List;
import lombok.Data;

/**
 * Employee data.
 */
@Data
public class EmployeeData {

    /**
     * Employee code.
     */
    private String employeeCode;

    /**
     * Employee Role list.
     */
    private List<String> employeeRoleList;

    /**
     * First name.
     */
    private String firstName;

    /**
     * Middle name.
     */
    private String middleName;

    /**
     * Last name.
     */
    private String lastName;

    /**
     * Local first name secondary.
     */
    private String localFirstNameSecondary;

    /**
     * Local middle name secondary.
     */
    private String localMiddleNameSecondary;

    /**
     * Local last name secondary.
     */
    private String localLastNameSecondary;

    /**
     * Local first name by Chinese.
     */
    private String localFirstNameChineseCharacters;

    /**
     * Local middle name by Chinese.
     */
    private String localMiddleNameChineseCharacters;

    /**
     * Local last name by Chinese.
     */
    private String localLastNameChineseCharacters;

}
