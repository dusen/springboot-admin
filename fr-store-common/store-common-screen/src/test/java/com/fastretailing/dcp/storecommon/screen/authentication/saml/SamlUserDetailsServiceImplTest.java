/**
 * @(#)SamlUserDetailsServiceImplTest.java
 *
 *                                         Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.authentication.saml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.NameID;
import org.opensaml.saml2.core.impl.AssertionBuilder;
import org.opensaml.saml2.core.impl.NameIDBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.saml.SAMLCredential;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.fastretailing.dcp.common.exception.SystemException;
import com.fastretailing.dcp.storecommon.ResultCode;
import com.fastretailing.dcp.storecommon.screen.authentication.client.constant.ItemKey;
import com.fastretailing.dcp.storecommon.screen.authentication.client.entity.employee.AuthenticationEmployeeInformation;
import com.fastretailing.dcp.storecommon.screen.authentication.client.entity.employee.EmployeeData;
import com.fastretailing.dcp.storecommon.screen.authentication.entity.UserDetails;
import com.fastretailing.dcp.storecommon.screen.config.DevelopmentConfiguration;

/**
 * SamlUserDetailsServiceImpl test class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@SpringBootApplication(scanBasePackages = {
        "com.fastretailing.dcp.storecommon.screen.authentication",
        "com.fastretailing.dcp.storecommon.screen.config", "com.fastretailing.dcp.common.api.uri"})
public class SamlUserDetailsServiceImplTest {

    /** Unit test target repository. */
    @Autowired
    private SamlUserDetailsServiceImpl targetService;

    /** Rest template mock. */
    @MockBean
    private RestTemplate restTemplate;

    /** Configuration for development. */
    @MockBean
    private DevelopmentConfiguration developmentConfiguration;

    /** Exception assertion. */
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    /**
     * @throws java.lang.Exception Exception.
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {}

    /**
     * @throws java.lang.Exception Exception.
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {}

    /**
     * @throws java.lang.Exception Exception.
     */
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        when(developmentConfiguration.getRoles()).thenReturn(null);
    }

    /**
     * @throws java.lang.Exception Exception.
     */
    @After
    public void tearDown() throws Exception {}

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.authentication.saml.SamlUserDetailsServiceImpl#loadUserBySAML(org.springframework.security.saml.SAMLCredential)}.
     * 
     * Case :: Normal case.
     */
    @Test
    public void testLoadUserBySamlNormal() {

        // Mock request, mock session.
        SavedRequest savedRequset = new TestSavedRequest(
                "https://host.com:7443/ScreenProtoAP/v1/uq/jp/screen/000000/action?param1=aaa&param2=bbb#fragments");
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("SPRING_SECURITY_SAVED_REQUEST", savedRequset);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(session);
        RequestContextHolder.setRequestAttributes(new TestServletRequestAttributes(request));

        // Mock restTemplate.
        String employeeCode = "testS888888";
        String url =
                "http://localhost:8080/employee/v1/{brand}/{region}/employee-information/{employee_code}";
        String brand = "uq";
        String region = "jp";

        Map<String, String> pathParameterMap = new HashMap<>();
        pathParameterMap.put(ItemKey.BRAND, brand);
        pathParameterMap.put(ItemKey.REGION, region);
        pathParameterMap.put(ItemKey.EMPLOYEE_CODE, employeeCode);

        ResponseEntity<AuthenticationEmployeeInformation> responseEntity =
                new ResponseEntity<>(createBasicInformation(), HttpStatus.OK);

        when(this.restTemplate.getForEntity(url, AuthenticationEmployeeInformation.class,
                pathParameterMap)).thenReturn(responseEntity);

        // Input argument.
        NameIDBuilder nameIdBuilder = new NameIDBuilder();
        NameID nameId = nameIdBuilder.buildObject();
        nameId.setValue(employeeCode);
        AssertionBuilder assertionBuilder = new AssertionBuilder();
        Assertion authenticationAssertion = assertionBuilder.buildObject();
        SAMLCredential credential = new SAMLCredential(nameId, authenticationAssertion, null, null);

        // Execute test method.
        Object actualObject = targetService.loadUserBySAML(credential);

        // Assert output.
        assertTrue(UserDetails.class.isInstance(actualObject));
        UserDetails actualUserDetails = UserDetails.class.cast(actualObject);

        Collection<GrantedAuthority> expectedAuthorityList = new ArrayList<>();
        expectedAuthorityList.add(new SimpleGrantedAuthority("ROLE01"));
        expectedAuthorityList.add(new SimpleGrantedAuthority("ROLE02"));

        assertEquals("testS888888", actualUserDetails.getUsername());
        assertNull(actualUserDetails.getStoreCode());
        assertEquals("<dummy!2018>", actualUserDetails.getPassword());
        assertEquals(expectedAuthorityList.size(), actualUserDetails.getAuthorities().size());
        assertTrue(actualUserDetails.getAuthorities().containsAll(expectedAuthorityList));
        assertTrue(actualUserDetails.isEnabled());
        assertTrue(actualUserDetails.isCredentialsNonExpired());
        assertTrue(actualUserDetails.isAccountNonExpired());
        assertTrue(actualUserDetails.isAccountNonLocked());

    }

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.authentication.saml.SamlUserDetailsServiceImpl#loadUserBySAML(org.springframework.security.saml.SAMLCredential)}.
     * 
     * Case :: When the employee role list that is the return value of the Employee API is null.
     */
    @Test
    public void testLoadUserBySamlEmployeeRoleListNull() {

        // Mock request, mock session.
        SavedRequest savedRequset = new TestSavedRequest(
                "https://host.com:7443/ScreenProtoAP/v1/uq/jp/screen/000000/action?param1=aaa&param2=bbb#fragments");
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("SPRING_SECURITY_SAVED_REQUEST", savedRequset);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(session);
        RequestContextHolder.setRequestAttributes(new TestServletRequestAttributes(request));

        // Mock restTemplate.
        String employeeCode = "testS888888";
        String url =
                "http://localhost:8080/employee/v1/{brand}/{region}/employee-information/{employee_code}";
        String brand = "uq";
        String region = "jp";

        Map<String, String> pathParameterMap = new HashMap<>();
        pathParameterMap.put(ItemKey.BRAND, brand);
        pathParameterMap.put(ItemKey.REGION, region);
        pathParameterMap.put(ItemKey.EMPLOYEE_CODE, employeeCode);

        AuthenticationEmployeeInformation returnInformation = createBasicInformation();
        returnInformation.getEmployeeData().setEmployeeRoleList(null);

        ResponseEntity<AuthenticationEmployeeInformation> responseEntity =
                new ResponseEntity<>(returnInformation, HttpStatus.OK);

        when(this.restTemplate.getForEntity(url, AuthenticationEmployeeInformation.class,
                pathParameterMap)).thenReturn(responseEntity);

        // Input argument.
        NameIDBuilder nameIDBuilder = new NameIDBuilder();
        NameID nameID = nameIDBuilder.buildObject();
        nameID.setValue(employeeCode);
        AssertionBuilder assertionBuilder = new AssertionBuilder();
        Assertion authenticationAssertion = assertionBuilder.buildObject();
        SAMLCredential credential = new SAMLCredential(nameID, authenticationAssertion, null, null);

        // Execute test method.
        Object actualObject = targetService.loadUserBySAML(credential);

        // Assert output.
        assertTrue(UserDetails.class.isInstance(actualObject));
        UserDetails actualUserDetails = UserDetails.class.cast(actualObject);

        assertEquals("testS888888", actualUserDetails.getUsername());
        assertNull(actualUserDetails.getStoreCode());
        assertEquals("<dummy!2018>", actualUserDetails.getPassword());
        assertTrue(actualUserDetails.getAuthorities().isEmpty());
        assertTrue(actualUserDetails.isEnabled());
        assertTrue(actualUserDetails.isCredentialsNonExpired());
        assertTrue(actualUserDetails.isAccountNonExpired());
        assertTrue(actualUserDetails.isAccountNonLocked());

    }

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.authentication.saml.SamlUserDetailsServiceImpl#loadUserBySAML(org.springframework.security.saml.SAMLCredential)}.
     * 
     * Case :: When the argument is null.
     */
    @Test
    public void testLoadUserBySamlArgumentNull() {

        // Mock request, mock session.
        SavedRequest savedRequset = new TestSavedRequest(
                "https://host.com:7443/ScreenProtoAP/v1/uq/jp/screen/000000/action?param1=aaa&param2=bbb#fragments");
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("SPRING_SECURITY_SAVED_REQUEST", savedRequset);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(session);
        RequestContextHolder.setRequestAttributes(new TestServletRequestAttributes(request));

        // Mock restTemplate.
        String employeeCode = "testS888888";
        String url =
                "http://localhost:8080/employee/v1/{brand}/{region}/employee-information/{employee_code}";
        String brand = "uq";
        String region = "jp";

        Map<String, String> pathParameterMap = new HashMap<>();
        pathParameterMap.put(ItemKey.BRAND, brand);
        pathParameterMap.put(ItemKey.REGION, region);
        pathParameterMap.put(ItemKey.EMPLOYEE_CODE, employeeCode);

        ResponseEntity<AuthenticationEmployeeInformation> responseEntity =
                new ResponseEntity<>(createBasicInformation(), HttpStatus.OK);

        when(this.restTemplate.getForEntity(url, AuthenticationEmployeeInformation.class,
                pathParameterMap)).thenReturn(responseEntity);

        // Input argument.
        SAMLCredential credential = null;

        // Expect throwing an exception.
        thrown.expect(SystemException.class);
        thrown.expectMessage("The credential is null.");

        // Execute test method.
        targetService.loadUserBySAML(credential);

    }

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.authentication.saml.SamlUserDetailsServiceImpl#loadUserBySAML(org.springframework.security.saml.SAMLCredential)}.
     * 
     * Case :: Dummy roles case.
     */
    @Test
    public void testLoadUserBySamlDummyRoles() {

        // Mock request, mock session.
        SavedRequest savedRequset = new TestSavedRequest(
                "https://host.com:7443/ScreenProtoAP/v1/uq/jp/screen/000000/action?param1=aaa&param2=bbb#fragments");
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("SPRING_SECURITY_SAVED_REQUEST", savedRequset);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(session);
        RequestContextHolder.setRequestAttributes(new TestServletRequestAttributes(request));

        // Mock restTemplate.
        String employeeCode = "testS888888";

        when(developmentConfiguration.getRoles()).thenReturn(Arrays.asList("ROLE01", "ROLE02"));

        // Input argument.
        NameIDBuilder nameIdBuilder = new NameIDBuilder();
        NameID nameId = nameIdBuilder.buildObject();
        nameId.setValue(employeeCode);
        AssertionBuilder assertionBuilder = new AssertionBuilder();
        Assertion authenticationAssertion = assertionBuilder.buildObject();
        SAMLCredential credential = new SAMLCredential(nameId, authenticationAssertion, null, null);

        // Execute test method.
        Object actualObject = targetService.loadUserBySAML(credential);

        // Assert output.
        assertTrue(UserDetails.class.isInstance(actualObject));
        UserDetails actualUserDetails = UserDetails.class.cast(actualObject);

        Collection<GrantedAuthority> expectedAuthorityList = new ArrayList<>();
        expectedAuthorityList.add(new SimpleGrantedAuthority("ROLE01"));
        expectedAuthorityList.add(new SimpleGrantedAuthority("ROLE02"));

        assertEquals("testS888888", actualUserDetails.getUsername());
        assertNull(actualUserDetails.getStoreCode());
        assertEquals("<dummy!2018>", actualUserDetails.getPassword());
        assertEquals(expectedAuthorityList.size(), actualUserDetails.getAuthorities().size());
        assertTrue(actualUserDetails.getAuthorities().containsAll(expectedAuthorityList));
        assertTrue(actualUserDetails.isEnabled());
        assertTrue(actualUserDetails.isCredentialsNonExpired());
        assertTrue(actualUserDetails.isAccountNonExpired());
        assertTrue(actualUserDetails.isAccountNonLocked());

    }

    /**
     * Servlet request attributes for unit test.
     */
    private class TestServletRequestAttributes extends ServletRequestAttributes {
        /**
         * Constructor.
         * 
         * @param request HTTP request.
         */
        public TestServletRequestAttributes(HttpServletRequest request) {
            super(request);
        }
    }

    /**
     * Saved request for unit test.
     */
    private class TestSavedRequest implements SavedRequest {

        /** Serial version UID. */
        private static final long serialVersionUID = -5029717293244274837L;

        /** Redirect URL. */
        private String redirectUrl;

        /**
         * Constructor.
         * 
         * @param redirectUrl redirect URL.
         */
        public TestSavedRequest(String redirectUrl) {
            this.redirectUrl = redirectUrl;
        }

        /**
         * Returns redirect URL.
         * 
         * @return redirect URL.
         */
        @Override
        public String getRedirectUrl() {
            return this.redirectUrl;
        }

        /**
         * Return null for not using it.
         * 
         * @return null.
         */
        @Override
        public List<Cookie> getCookies() {
            return null;
        }

        /**
         * Return null for not using it.
         * 
         * @return null.
         */
        @Override
        public String getMethod() {
            return null;
        }

        /**
         * Return null for not using it.
         * 
         * @return null.
         */
        @Override
        public List<String> getHeaderValues(String name) {
            return null;
        }

        /**
         * Return null for not using it.
         * 
         * @return null.
         */
        @Override
        public Collection<String> getHeaderNames() {
            return null;
        }

        /**
         * Return null for not using it.
         * 
         * @return null.
         */
        @Override
        public List<Locale> getLocales() {
            return null;
        }

        /**
         * Return null for not using it.
         * 
         * @return null.
         */
        @Override
        public String[] getParameterValues(String name) {
            return null;
        }

        /**
         * Return null for not using it.
         * 
         * @return null.
         */
        @Override
        public Map<String, String[]> getParameterMap() {
            return null;
        }

    }

    /**
     * Creates the basic instance for AuthenticationEmployeeInformation.
     * 
     * @return the basic instance for AuthenticationEmployeeInformation.
     */
    private AuthenticationEmployeeInformation createBasicInformation() {

        AuthenticationEmployeeInformation information = new AuthenticationEmployeeInformation();

        information.setResultCode(ResultCode.NORMAL);

        List<String> employeeRoleList = new ArrayList<>();
        employeeRoleList.add("ROLE01");
        employeeRoleList.add("ROLE02");

        EmployeeData employeeData = new EmployeeData();
        employeeData.setEmployeeRoleList(employeeRoleList);

        information.setEmployeeData(employeeData);

        return information;

    }

}
