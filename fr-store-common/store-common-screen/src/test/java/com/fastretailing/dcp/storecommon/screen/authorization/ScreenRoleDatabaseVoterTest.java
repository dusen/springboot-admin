/**
 * @(#)ScreenRoleDatabaseVoterTest.java
 *
 *                                      Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.authorization;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestExecutionListeners.MergeMode;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import com.fastretailing.dcp.storecommon.screen.authentication.entity.UserDetails;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.dataset.ReplacementDataSetLoader;

/**
 * ScreenRoleDatabaseVoter test class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@SpringBootApplication(scanBasePackages = {"com.fastretailing.dcp.storecommon.screen.config"})
@TestExecutionListeners(mergeMode = MergeMode.MERGE_WITH_DEFAULTS,
        value = {DependencyInjectionTestExecutionListener.class,
                DirtiesContextTestExecutionListener.class,
                TransactionDbUnitTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DbUnitConfiguration(dataSetLoader = ReplacementDataSetLoader.class)
@Sql(scripts = "/junit_create_table_screen.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/junit_drop_table_screen.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public class ScreenRoleDatabaseVoterTest {

    /** Unit test target voter. */
    @Autowired
    private ScreenRoleDatabaseVoter screenRoleDatabaseVoter;

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
    public void setUp() throws Exception {}

    /**
     * @throws java.lang.Exception Exception.
     */
    @After
    public void tearDown() throws Exception {}

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.authorization.ScreenRoleDatabaseVoter#supports(org.springframework.security.access.ConfigAttribute)}.
     */
    @Test
    public void testSupportsConfigAttribute() {

        // input.
        ConfigAttribute attribute = null;

        // execute test method.
        boolean actualReturn = screenRoleDatabaseVoter.supports(attribute);

        // assert output.
        assertTrue(actualReturn);

    }

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.authorization.ScreenRoleDatabaseVoter#supports(java.lang.Class)}.
     * 
     * Case :: When the class is FilterInvocation.
     */
    @Test
    public void testSupportsClassOfQ_FilterInvocation() {

        // execute test method.
        boolean actualReturn = screenRoleDatabaseVoter.supports(FilterInvocation.class);

        // assert output.
        assertTrue(actualReturn);

    }

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.authorization.ScreenRoleDatabaseVoter#supports(java.lang.Class)}.
     * 
     * Case :: When the class is not FilterInvocation.
     */
    @Test
    public void testSupportsClassOfQ_notFilterInvocation() {

        // execute test method.
        boolean actualReturn = screenRoleDatabaseVoter.supports(Object.class);

        // assert output.
        assertFalse(actualReturn);

    }

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.authorization.ScreenRoleDatabaseVoter#vote(org.springframework.security.core.Authentication, org.springframework.security.web.FilterInvocation, java.util.Collection)}.
     * 
     * Case :: When the request path is normal and the authenticated user has a role.
     */
    @Test
    @DatabaseSetup("ScreenRoleDatabaseVoterTest_init.xml")
    public void testVote_requestPathNomal_userHasRole() {

        // input.
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE01"));
        UserDetails userDetails = new UserDetails("000000", "testUser", "testPassword", true, true,
                true, true, authorityList);

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, authorityList);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        FilterInvocation filterInvocation = new FilterInvocation("/ScreenProtoAP/v1",
                "/uq/jp/screen/000000/path01?param1=aaa&param2=bbb#fragments", "GET");
        Collection<ConfigAttribute> attributes = new ArrayList<>();

        // execute test method.
        int actualReturn =
                screenRoleDatabaseVoter.vote(authentication, filterInvocation, attributes);

        // assert output.
        assertEquals(AccessDecisionVoter.ACCESS_GRANTED, actualReturn);

    }

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.authorization.ScreenRoleDatabaseVoter#vote(org.springframework.security.core.Authentication, org.springframework.security.web.FilterInvocation, java.util.Collection)}.
     * 
     * Case :: When the request path is normal and the user is unauthenticated.
     */
    @Test
    @DatabaseSetup("ScreenRoleDatabaseVoterTest_init.xml")
    public void testVote_requestPathNomal_unauthenticatedUser() {

        // input.
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE01"));
        UserDetails userDetails = new UserDetails("000000", "testUser", "testPassword", true, true,
                true, true, authorityList);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
        authentication.setAuthenticated(false);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        FilterInvocation filterInvocation = new FilterInvocation("/ScreenProtoAP/v1",
                "/uq/jp/screen/000000/path01?param1=aaa&param2=bbb#fragments", "GET");
        Collection<ConfigAttribute> attributes = new ArrayList<>();

        // execute test method.
        int actualReturn =
                screenRoleDatabaseVoter.vote(authentication, filterInvocation, attributes);

        // assert output.
        assertEquals(AccessDecisionVoter.ACCESS_ABSTAIN, actualReturn);

    }

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.authorization.ScreenRoleDatabaseVoter#vote(org.springframework.security.core.Authentication, org.springframework.security.web.FilterInvocation, java.util.Collection)}.
     * 
     * Case :: When the request path is normal and authentication is performed with a part other
     * than store-common-screen.
     */
    @Test
    @DatabaseSetup("ScreenRoleDatabaseVoterTest_init.xml")
    public void testVote_requestPathNomal_otherAuthenticatedUser() {

        // input.
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE01"));

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "Other authenticated.", null, authorityList);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        FilterInvocation filterInvocation = new FilterInvocation("/ScreenProtoAP/v1",
                "/uq/jp/screen/000000/path01?param1=aaa&param2=bbb#fragments", "GET");
        Collection<ConfigAttribute> attributes = new ArrayList<>();

        // execute test method.
        int actualReturn =
                screenRoleDatabaseVoter.vote(authentication, filterInvocation, attributes);

        // assert output.
        assertEquals(AccessDecisionVoter.ACCESS_ABSTAIN, actualReturn);

    }

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.authorization.ScreenRoleDatabaseVoter#vote(org.springframework.security.core.Authentication, org.springframework.security.web.FilterInvocation, java.util.Collection)}.
     * 
     * Case :: When the request path is illegal screen path value.
     */
    @Test
    @DatabaseSetup("ScreenRoleDatabaseVoterTest_init.xml")
    public void testVote_requestPathIllegal() {

        // input.
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE01"));
        UserDetails userDetails = new UserDetails("000000", "testUser", "testPassword", true, true,
                true, true, authorityList);

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, authorityList);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        FilterInvocation filterInvocation = new FilterInvocation("/ScreenProtoAP/v1",
                "/uq/jp/000000/path01?param1=aaa&param2=bbb#fragments", "GET");
        Collection<ConfigAttribute> attributes = new ArrayList<>();

        // execute test method.
        int actualReturn =
                screenRoleDatabaseVoter.vote(authentication, filterInvocation, attributes);

        // assert output.
        assertEquals(AccessDecisionVoter.ACCESS_DENIED, actualReturn);

    }

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.authorization.ScreenRoleDatabaseVoter#vote(org.springframework.security.core.Authentication, org.springframework.security.web.FilterInvocation, java.util.Collection)}.
     * 
     * Case :: When the request path is exclusion screen path value.
     */
    @Test
    @DatabaseSetup("ScreenRoleDatabaseVoterTest_init.xml")
    public void testVote_requestPathExclusion() {

        // input.
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE01"));
        UserDetails userDetails = new UserDetails("000000", "testUser", "testPassword", true, true,
                true, true, authorityList);

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, authorityList);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        FilterInvocation filterInvocation = new FilterInvocation("/ScreenProtoAP/v1",
                "/uq/jp/screen/000000/static/path01?param1=aaa&param2=bbb#fragments", "GET");
        Collection<ConfigAttribute> attributes = new ArrayList<>();

        // execute test method.
        int actualReturn =
                screenRoleDatabaseVoter.vote(authentication, filterInvocation, attributes);

        // assert output.
        assertEquals(AccessDecisionVoter.ACCESS_ABSTAIN, actualReturn);

    }

}
