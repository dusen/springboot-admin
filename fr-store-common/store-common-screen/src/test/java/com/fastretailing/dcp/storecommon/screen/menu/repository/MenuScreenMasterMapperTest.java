/**
 * @(#)ScreenMasterMapperTest.java
 *
 *                                 Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.menu.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestExecutionListeners.MergeMode;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import com.fastretailing.dcp.storecommon.screen.menu.entity.MenuScreenEntity;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.github.springtestdbunit.dataset.ReplacementDataSetLoader;

/**
 * ScreenMasterMapper test class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@SpringBootApplication
@MapperScan("com.fastretailing.dcp.storecommon.screen.menu.repository")
@TestExecutionListeners(mergeMode = MergeMode.MERGE_WITH_DEFAULTS,
        value = {DependencyInjectionTestExecutionListener.class,
                DirtiesContextTestExecutionListener.class,
                TransactionDbUnitTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DbUnitConfiguration(dataSetLoader = ReplacementDataSetLoader.class)
@Sql(scripts = "/junit_create_table_screen.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/junit_drop_table_screen.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public class MenuScreenMasterMapperTest {

    /** Unit test target mapper. */
    @Autowired
    private MenuScreenMasterMapper screenMasterMapper;

    /**
     * Single role, result exists.
     */
    @Test
    @DatabaseSetup("MenuScreenMasterMapperTest_init.xml")
    @ExpectedDatabase(value = "MenuScreenMasterMapperTest_init.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void testSelectScreenListByRoleSingleExist() {

        // input.
        List<String> roles = new ArrayList<>();
        roles.add("ROLE01");

        // execute test method.
        List<MenuScreenEntity> menuScreenList =
                screenMasterMapper.selectScreenListByRole("TEST_MENU01", roles);

        assertThat(menuScreenList.size(), is(2));
        assertThat(menuScreenList.get(0).getScreenName(), is("TST0100101003"));
        assertThat(menuScreenList.get(0).getPath(), is("/path03"));
        assertThat(menuScreenList.get(1).getScreenName(), is("TST0100101001"));
        assertThat(menuScreenList.get(1).getPath(), is("/path01"));
    }

    /**
     * Multiple roles, result exists.
     */
    @Test
    @DatabaseSetup("MenuScreenMasterMapperTest_init.xml")
    @ExpectedDatabase(value = "MenuScreenMasterMapperTest_init.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void testSelectScreenListByRoleMultiExist() {

        // input.
        List<String> roles = new ArrayList<>();
        roles.add("ROLE01");
        roles.add("ROLE03");

        // execute test method.
        List<MenuScreenEntity> menuScreenList =
                screenMasterMapper.selectScreenListByRole("TEST_MENU01", roles);

        assertThat(menuScreenList.size(), is(3));
        assertThat(menuScreenList.get(0).getScreenName(), is("TST0100101002"));
        assertThat(menuScreenList.get(0).getPath(), is("/path02"));
        assertThat(menuScreenList.get(1).getScreenName(), is("TST0100101003"));
        assertThat(menuScreenList.get(1).getPath(), is("/path03"));
        assertThat(menuScreenList.get(2).getScreenName(), is("TST0100101001"));
        assertThat(menuScreenList.get(2).getPath(), is("/path01"));
    }

    /**
     * No such menu.
     */
    @Test
    @DatabaseSetup("MenuScreenMasterMapperTest_init.xml")
    @ExpectedDatabase(value = "MenuScreenMasterMapperTest_init.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void testSelectScreenListByRoleMenuNotExists() {

        // input.
        List<String> roles = new ArrayList<>();
        roles.add("ROLE01");
        roles.add("ROLE03");

        // execute test method.
        List<MenuScreenEntity> menuScreenList =
                screenMasterMapper.selectScreenListByRole("TEST_MENU03", roles);

        assertThat(menuScreenList.size(), is(0));
    }

    /**
     * No such menu.
     */
    @Test
    @DatabaseSetup("MenuScreenMasterMapperTest_init.xml")
    @ExpectedDatabase(value = "MenuScreenMasterMapperTest_init.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void testSelectScreenListByRoleRoleNotExists() {

        // input.
        List<String> roles = new ArrayList<>();
        roles.add("ROLE05");

        // execute test method.
        List<MenuScreenEntity> menuScreenList =
                screenMasterMapper.selectScreenListByRole("TEST_MENU01", roles);

        assertThat(menuScreenList.size(), is(0));
    }

}
