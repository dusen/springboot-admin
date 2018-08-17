/**
 * @(#)AuthenticationEmployeeRepositoryImplTest.java
 *
 *                                                   Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.authentication.client.repository.employee.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import com.fastretailing.dcp.common.api.threadlocal.RequestPathVariableHolder;
import com.fastretailing.dcp.common.exception.SystemException;
import com.fastretailing.dcp.storecommon.ResultCode;
import com.fastretailing.dcp.storecommon.screen.authentication.client.constant.ItemKey;
import com.fastretailing.dcp.storecommon.screen.authentication.client.entity.employee.AuthenticationEmployeeInformation;
import com.fastretailing.dcp.storecommon.screen.authentication.client.entity.employee.EmployeeData;
import com.fastretailing.dcp.storecommon.screen.authentication.client.repository.employee.AuthenticationEmployeeRepository;

/**
 * AuthenticationEmployeeRepositoryImpl test class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@SpringBootApplication(scanBasePackages = {
        "com.fastretailing.dcp.storecommon.screen.authentication.client.repository",
        "com.fastretailing.dcp.common.api.uri"})
public class AuthenticationEmployeeRepositoryImplTest {

    /** Unit test target repository. */
    @Autowired
    private AuthenticationEmployeeRepository targetRepository;

    /** Rest template mock. */
    @MockBean
    private RestTemplate restTemplate;

    /** Exception assertion. */
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    /**
     * @throws java.lang.Exception Exception.
     */
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.authentication.client.repository.employee.impl.AuthenticationEmployeeRepositoryImpl#requestEmployeeInformation(java.lang.String)}.
     * 
     * Case :: When the response is OK.
     */
    @Test
    public void testRequestEmployeeInformation_okResponse() {

        // Input.
        String employeeCode = "testUser01";
        String url =
                "http://localhost:8080/employee/v1/{brand}/{region}/employee-information/{employee_code}";
        String brand = "testBrand";
        String region = "testRegion";

        Map<String, String> pathVariableMap = new HashMap<>();
        pathVariableMap.put(RequestPathVariableHolder.KEY_BRAND, brand);
        pathVariableMap.put(RequestPathVariableHolder.KEY_REGION, region);
        RequestPathVariableHolder.setRequestPathVariableMap(pathVariableMap);

        Map<String, String> pathParameterMap = new HashMap<>();
        pathParameterMap.put(ItemKey.BRAND, brand);
        pathParameterMap.put(ItemKey.REGION, region);
        pathParameterMap.put(ItemKey.EMPLOYEE_CODE, employeeCode);

        ResponseEntity<AuthenticationEmployeeInformation> responseEntity =
                new ResponseEntity<>(createBasicInformation(), HttpStatus.OK);

        // Initialize mock object.
        when(this.restTemplate.getForEntity(url, AuthenticationEmployeeInformation.class,
                pathParameterMap)).thenReturn(responseEntity);

        // Execute test method.
        AuthenticationEmployeeInformation actualInformation =
                targetRepository.requestEmployeeInformation(employeeCode);

        // Assert output.
        AuthenticationEmployeeInformation expectedInformation = createBasicInformation();
        assertEquals(expectedInformation, actualInformation);

    }

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.authentication.client.repository.employee.impl.AuthenticationEmployeeRepositoryImpl#requestEmployeeInformation(java.lang.String)}.
     * 
     * Case :: When the response is NG.
     */
    @Test
    public void testRequestEmployeeInformation_ngResponse() {

        // input.
        String employeeCode = "testUser01";
        String url =
                "http://localhost:8080/employee/v1/{brand}/{region}/employee-information/{employee_code}";
        String brand = "testBrand";
        String region = "testRegion";

        Map<String, String> pathVariableMap = new HashMap<>();
        pathVariableMap.put(RequestPathVariableHolder.KEY_BRAND, brand);
        pathVariableMap.put(RequestPathVariableHolder.KEY_REGION, region);
        RequestPathVariableHolder.setRequestPathVariableMap(pathVariableMap);

        Map<String, String> pathParameterMap = new HashMap<>();
        pathParameterMap.put(ItemKey.BRAND, brand);
        pathParameterMap.put(ItemKey.REGION, region);
        pathParameterMap.put(ItemKey.EMPLOYEE_CODE, employeeCode);

        ResponseEntity<AuthenticationEmployeeInformation> responseEntity =
                new ResponseEntity<>(createBasicInformation(), HttpStatus.BAD_REQUEST);

        // initialize mock object.
        when(this.restTemplate.getForEntity(url, AuthenticationEmployeeInformation.class,
                pathParameterMap)).thenReturn(responseEntity);

        // Expect throwing an exception.
        thrown.expect(SystemException.class);
        thrown.expectMessage(
                "Employee API(employee-information) call status code is not 20x. EmployeeCode="
                        + employeeCode + "/StatusCode=" + HttpStatus.BAD_REQUEST);

        // execute test method.
        targetRepository.requestEmployeeInformation(employeeCode);

    }

    /**
     * creates the basic instance for AuthenticationEmployeeInformation.
     * 
     * @return the basic instance for AuthenticationEmployeeInformation.
     */
    private AuthenticationEmployeeInformation createBasicInformation() {

        AuthenticationEmployeeInformation information = new AuthenticationEmployeeInformation();

        information.setResultCode(ResultCode.NORMAL);

        List<String> employeeRoleList = new ArrayList<>();
        employeeRoleList.add("ROLE01");

        EmployeeData employeeData = new EmployeeData();
        employeeData.setEmployeeRoleList(employeeRoleList);

        information.setEmployeeData(employeeData);

        return information;

    }

}
