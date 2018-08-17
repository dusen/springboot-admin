/**
 * @(#)ScreenMasterMapperTest.java
 *
 *                                 Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.authorization.repository;

import static org.junit.Assert.assertEquals;
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
@MapperScan("com.fastretailing.dcp.storecommon.screen.authorization.repository")
@TestExecutionListeners(mergeMode = MergeMode.MERGE_WITH_DEFAULTS,
        value = {DependencyInjectionTestExecutionListener.class,
                DirtiesContextTestExecutionListener.class,
                TransactionDbUnitTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DbUnitConfiguration(dataSetLoader = ReplacementDataSetLoader.class)
@Sql(scripts = "/junit_create_table_screen.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/junit_drop_table_screen.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public class ScreenMasterMapperTest {

    /** Unit test target mapper. */
    @Autowired
    private ScreenMasterMapper screenMasterMapper;

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.authorization.repository.ScreenMasterMapper#selectRoleAccessibleToPath(java.util.List)}.
     * 
     * Case :: When the normally value(paths is single, roles exist) is an input value.
     */
    @Test
    @DatabaseSetup("ScreenMasterMapperTest_init.xml")
    @ExpectedDatabase(value = "ScreenMasterMapperTest_init.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void testSelectRoleAccessibleToPath_pathsSingle1_rolesExist() {

        // input.
        List<String> pathList = new ArrayList<>();
        pathList.add("/path01");

        // execute test method.
        List<String> actualRoleList = screenMasterMapper.selectRoleAccessibleToPath(pathList);

        // assert output.
        List<String> expectedRoleList = new ArrayList<>();
        expectedRoleList.add("ROLE01");
        expectedRoleList.add("ROLE02");
        assertEquals(expectedRoleList, actualRoleList);

    }

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.authorization.repository.ScreenMasterMapper#selectRoleAccessibleToPath(java.util.List)}.
     * 
     * Case :: When the normally value(paths is single, roles exist) is an input value.
     */
    @Test
    @DatabaseSetup("ScreenMasterMapperTest_init.xml")
    @ExpectedDatabase(value = "ScreenMasterMapperTest_init.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void testSelectRoleAccessibleToPath_pathsSingle2_rolesExist() {

        // input.
        List<String> pathList = new ArrayList<>();
        pathList.add("/path02");

        // execute test method.
        List<String> actualRoleList = screenMasterMapper.selectRoleAccessibleToPath(pathList);

        // assert output.
        List<String> expectedRoleList = new ArrayList<>();
        expectedRoleList.add("ROLE03");
        assertEquals(expectedRoleList, actualRoleList);

    }

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.authorization.repository.ScreenMasterMapper#selectRoleAccessibleToPath(java.util.List)}.
     * 
     * Case :: When the normally value(paths is multiple, roles exist) is an input value.
     */
    @Test
    @DatabaseSetup("ScreenMasterMapperTest_init.xml")
    @ExpectedDatabase(value = "ScreenMasterMapperTest_init.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void testSelectRoleAccessibleToPath_pathsMultiple1_rolesExist() {

        // input.
        List<String> pathList = new ArrayList<>();
        pathList.add("/path01");
        pathList.add("/path01/detail");

        // execute test method.
        List<String> actualRoleList = screenMasterMapper.selectRoleAccessibleToPath(pathList);

        // assert output.
        List<String> expectedRoleList = new ArrayList<>();
        expectedRoleList.add("ROLE01");
        expectedRoleList.add("ROLE02");
        assertEquals(expectedRoleList, actualRoleList);

    }

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.authorization.repository.ScreenMasterMapper#selectRoleAccessibleToPath(java.util.List)}.
     * 
     * Case :: When the normally value(paths is single, roles exist) is an input value.
     */
    @Test
    @DatabaseSetup("ScreenMasterMapperTest_init.xml")
    @ExpectedDatabase(value = "ScreenMasterMapperTest_init.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void testSelectRoleAccessibleToPath_pathsMultiple2_rolesExist() {

        // input.
        List<String> pathList = new ArrayList<>();
        pathList.add("/path01/detail");
        pathList.add("/path02/detail");

        // execute test method.
        List<String> actualRoleList = screenMasterMapper.selectRoleAccessibleToPath(pathList);

        // assert output.
        List<String> expectedRoleList = new ArrayList<>();
        expectedRoleList.add("ROLE01");
        expectedRoleList.add("ROLE02");
        expectedRoleList.add("ROLE03");
        expectedRoleList.add("ROLE04");
        assertEquals(expectedRoleList, actualRoleList);

    }

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.authorization.repository.ScreenMasterMapper#selectRoleAccessibleToPath(java.util.List)}.
     * 
     * Case :: When the normally value(paths not exist) is an input value.
     */
    @Test
    @DatabaseSetup("ScreenMasterMapperTest_init.xml")
    @ExpectedDatabase(value = "ScreenMasterMapperTest_init.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void testSelectRoleAccessibleToPath_pathsNotExist1() {

        // input.
        List<String> pathList = new ArrayList<>();
        pathList.add("/detail");

        // execute test method.
        List<String> actualRoleList = screenMasterMapper.selectRoleAccessibleToPath(pathList);

        // assert output.
        List<String> expectedRoleList = new ArrayList<>();
        assertEquals(expectedRoleList, actualRoleList);

    }

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.authorization.repository.ScreenMasterMapper#selectRoleAccessibleToPath(java.util.List)}.
     * 
     * Case :: When the normally value(paths not exist) is an input value.
     */
    @Test
    @DatabaseSetup("ScreenMasterMapperTest_init.xml")
    @ExpectedDatabase(value = "ScreenMasterMapperTest_init.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void testSelectRoleAccessibleToPath_pathsNotExist2() {

        // input.
        List<String> pathList = new ArrayList<>();
        pathList.add("/path04");

        // execute test method.
        List<String> actualRoleList = screenMasterMapper.selectRoleAccessibleToPath(pathList);

        // assert output.
        List<String> expectedRoleList = new ArrayList<>();
        assertEquals(expectedRoleList, actualRoleList);

    }

    /**
     * Test method for
     * {@link com.fastretailing.dcp.storecommon.screen.authorization.repository.ScreenMasterMapper#selectRoleAccessibleToPath(java.util.List)}.
     * 
     * Case :: When the normally value(paths is single, roles not exist) is an input value.
     */
    @Test
    @DatabaseSetup("ScreenMasterMapperTest_init.xml")
    @ExpectedDatabase(value = "ScreenMasterMapperTest_init.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void testSelectRoleAccessibleToPath_pathsSingle1_rolesNotExist() {

        // input.
        List<String> pathList = new ArrayList<>();
        pathList.add("/path05");

        // execute test method.
        List<String> actualRoleList = screenMasterMapper.selectRoleAccessibleToPath(pathList);

        // assert output.
        List<String> expectedRoleList = new ArrayList<>();
        assertEquals(expectedRoleList, actualRoleList);

    }

}
