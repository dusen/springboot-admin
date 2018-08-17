package com.fastretailing.dcp.storecommon.generaldatadelete.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestExecutionListeners.MergeMode;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import com.fastretailing.dcp.storecommon.generaldatadelete.dto.GeneralDataDeleteEntity;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.dataset.ReplacementDataSetLoader;

/**
 * JUnit test class of GeneralDataDeleteConfigMapper .
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@TestExecutionListeners(mergeMode = MergeMode.MERGE_WITH_DEFAULTS,
        value = {DependencyInjectionTestExecutionListener.class,
                DirtiesContextTestExecutionListener.class,
                TransactionDbUnitTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DbUnitConfiguration(dataSetLoader = ReplacementDataSetLoader.class)
@Sql(scripts = "/junit_create_table_common.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/create-tables.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/cleanup-tables.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = "/junit_drop_table_common.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public class GeneralDataDeleteConfigMapperTest {

    /** DB access class. */
    @Autowired
    private GeneralDataDeleteConfigMapper generalDataDeleteConfigMapper;


    /** Request information of platform management . */
    public GeneralDataDeleteEntity generalDataDeleteEntity;


    /**
     * Initial request parameter generalDataDeleteEntity.
     * 
     * @throws Exception Exception.
     */
    @Before
    public void setUp() throws Exception {

        /* Initial request information */
        generalDataDeleteEntity = new GeneralDataDeleteEntity();
        generalDataDeleteEntity.setProcessingTargetType("COMMON");

    }

    @Test
    @DatabaseSetup("GeneralDataDeleteConfigMapperestInit.xml")
    public void testSelectByPrimaryKeyWithZeroRecord() throws Exception {

        // Set expected record list to empty
        List<GeneralDataDeleteEntity> expectedGeneralDataDeleteList =
                new ArrayList<GeneralDataDeleteEntity>();

        // Get record list
        List<GeneralDataDeleteEntity> paramList =
                generalDataDeleteConfigMapper.selectByProcessingTargetType("COMMON");
        // Confirm test result
        assertThat(paramList, is(expectedGeneralDataDeleteList));
    }


    @Test
    @DatabaseSetup("GeneralDataDeleteConfigMapperestInit-SELECTONE.xml")
    public void testSelectWithOneRecord() {

        generalDataDeleteEntity.setProcessingTargetType("COMMON");
        generalDataDeleteEntity.setProcessingNo(1L);
        generalDataDeleteEntity.setDeleteTargetTable("del_config");
        generalDataDeleteEntity.setSavedDays(1L);
        generalDataDeleteEntity.setDateItem("create_datetime");
        generalDataDeleteEntity.setSystemBrandCode("01");
        generalDataDeleteEntity.setStoreCode("01");
        generalDataDeleteEntity.setSystemCountryCode("01");
        List<GeneralDataDeleteEntity> generalDataDeleteEntityResult =
                generalDataDeleteConfigMapper.selectByProcessingTargetType("COMMON");

        List<GeneralDataDeleteEntity> mockResult = new ArrayList<>();

        mockResult.add(generalDataDeleteEntity);
        assertThat(generalDataDeleteEntityResult, is(mockResult));
    }

    @Test
    @DatabaseSetup("GeneralDataDeleteConfigMapperestInit-SELECTMANY.xml")
    public void testSelectWithManyRecords() {

        generalDataDeleteEntity.setProcessingTargetType("COMMON");
        generalDataDeleteEntity.setProcessingNo(3L);
        generalDataDeleteEntity.setDeleteTargetTable("del_config");
        generalDataDeleteEntity.setSavedDays(1L);
        generalDataDeleteEntity.setDateItem("create_datetime");
        generalDataDeleteEntity.setSystemBrandCode("01");
        generalDataDeleteEntity.setStoreCode("01");
        generalDataDeleteEntity.setSystemCountryCode("01");
        GeneralDataDeleteEntity generalDataDeleteEntity2 = new GeneralDataDeleteEntity();

        generalDataDeleteEntity2.setProcessingTargetType("COMMON");
        generalDataDeleteEntity2.setProcessingNo(2L);
        generalDataDeleteEntity2.setDeleteTargetTable("del_config");
        generalDataDeleteEntity2.setSavedDays(1L);
        generalDataDeleteEntity2.setDateItem("create_datetime");
        generalDataDeleteEntity2.setSystemBrandCode("01");
        generalDataDeleteEntity2.setStoreCode("01");
        generalDataDeleteEntity2.setSystemCountryCode("01");


        GeneralDataDeleteEntity generalDataDeleteEntity3 = new GeneralDataDeleteEntity();

        generalDataDeleteEntity3.setProcessingTargetType("COMMON");
        generalDataDeleteEntity3.setProcessingNo(1L);
        generalDataDeleteEntity3.setDeleteTargetTable("del_config");
        generalDataDeleteEntity3.setSavedDays(1L);
        generalDataDeleteEntity3.setDateItem("create_datetime");
        generalDataDeleteEntity3.setSystemBrandCode("01");
        generalDataDeleteEntity3.setStoreCode("01");
        generalDataDeleteEntity3.setSystemCountryCode("01");
        List<GeneralDataDeleteEntity> generalDataDeleteEntitys =
                generalDataDeleteConfigMapper.selectByProcessingTargetType("COMMON");

        List<GeneralDataDeleteEntity> mockResult = new ArrayList<GeneralDataDeleteEntity>();

        System.out.println(generalDataDeleteEntitys.size());

        mockResult.add(generalDataDeleteEntity3);
        mockResult.add(generalDataDeleteEntity2);
        mockResult.add(generalDataDeleteEntity);

        assertThat(generalDataDeleteEntitys, is(mockResult));
    }
}
