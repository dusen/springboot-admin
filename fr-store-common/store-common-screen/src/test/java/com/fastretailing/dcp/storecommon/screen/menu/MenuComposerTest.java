/**
 * @(#)ScreenRoleDatabaseVoterTest.java
 *
 *                                      Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.menu;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import java.util.ArrayList;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestExecutionListeners.MergeMode;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import com.fastretailing.dcp.storecommon.screen.authentication.entity.UserDetails;
import com.fastretailing.dcp.storecommon.screen.form.MenuElement;
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
public class MenuComposerTest {

    /** Unit test target voter. */
    @Autowired
    private MenuComposer menuComposer;

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
     * Test method for getMenuList.
     */
    @Test
    @DatabaseSetup("MenuComposerTest_init.xml")
    public void testGetMenuListDataExists() {

        // Input.
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE01"));
        UserDetails userDetails = new UserDetails("000000", "testUser", "testPassword", true, true,
                true, true, authorityList);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        List<MenuElement> menuList = menuComposer.getMenuList("TEST_MENU01");

        assertThat(menuList.size(), is(2));
        assertThat(menuList.get(0).getMenuId(), is("TST0100101003.title"));
        assertThat(menuList.get(0).getUrl(), is("/path03"));
    }

    /**
     * Test method for getMenuList.
     */
    @Test
    @DatabaseSetup("MenuComposerTest_init.xml")
    public void testGetMenuListMenuNull() {

        // input.
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE01"));
        UserDetails userDetails = new UserDetails("000000", "testUser", "testPassword", true, true,
                true, true, authorityList);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        List<MenuElement> menuList = menuComposer.getMenuList(null);

        assertThat(menuList.size(), is(0));
    }

    /**
     * Test method for getMenuList.
     */
    @Test
    @DatabaseSetup("MenuComposerTest_init.xml")
    public void testGetMenuListNoData() {

        // input.
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE01"));
        UserDetails userDetails = new UserDetails("000000", "testUser", "testPassword", true, true,
                true, true, authorityList);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        List<MenuElement> menuList = menuComposer.getMenuList("TEST_MENU03");

        assertThat(menuList.size(), is(0));
    }

}
