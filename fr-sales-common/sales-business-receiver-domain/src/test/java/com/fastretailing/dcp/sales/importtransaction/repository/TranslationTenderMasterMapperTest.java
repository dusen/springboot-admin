package com.fastretailing.dcp.sales.importtransaction.repository;

import static org.junit.Assert.assertEquals;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestExecutionListeners.MergeMode;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import com.fastretailing.dcp.sales.importtransaction.entity.TranslationTenderMaster;
import com.fastretailing.dcp.sales.importtransaction.entity.TranslationTenderMasterCondition;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.dataset.ReplacementDataSetLoader;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestExecutionListeners(mergeMode = MergeMode.MERGE_WITH_DEFAULTS,
        value = {DependencyInjectionTestExecutionListener.class,
                DirtiesContextTestExecutionListener.class,
                TransactionDbUnitTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DbUnitConfiguration(dataSetLoader = ReplacementDataSetLoader.class)
@Sql(scripts = "/junit_create_table_sales_6.sql",
        executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/junit_drop_table_sales_6.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
@MapperScan(value = "com.fastretailing.dcp.sales.importtransaction.repository")
public class TranslationTenderMasterMapperTest {

    @Autowired
    private TranslationTenderMasterMapper translationTenderMasterMapper;

    @Test
    @DatabaseSetup("TranslationTenderMasterMapperTest.xml")
    public void testSelectByConditionExisted() {
        TranslationTenderMasterCondition condition = new TranslationTenderMasterCondition();
        condition.createCriteria()
                .andStoreCodeEqualTo("99")
                .andImsTenderGroupEqualTo("95")
                .andImsTenderIdEqualTo(96);

        List<TranslationTenderMaster> result =
                translationTenderMasterMapper.selectByCondition(condition);
        assertEquals(result.size(), 1);
        assertEquals(result.get(0).getStoreCode(), "99");
        assertEquals(result.get(0).getImsTenderGroup(), "95");
        assertEquals(result.get(0).getImsTenderId(), Integer.valueOf(96));
    }

    @Test
    @DatabaseSetup("TranslationTenderMasterMapperTest.xml")
    public void testSelectByConditionNotExisted() {
        TranslationTenderMasterCondition condition = new TranslationTenderMasterCondition();
        condition.createCriteria()
                .andStoreCodeEqualTo("1")
                .andImsTenderGroupEqualTo("1")
                .andImsTenderIdEqualTo(1);

        List<TranslationTenderMaster> result =
                translationTenderMasterMapper.selectByCondition(condition);
        assertEquals(result.size(), 0);
    }

    @Test
    @DatabaseSetup("TranslationTenderMasterMapperTest.xml")
    public void testCountByConditionExisted() {
        TranslationTenderMasterCondition condition = new TranslationTenderMasterCondition();
        condition.createCriteria()
                .andStoreCodeEqualTo("2")
                .andTenderIdEqualTo("2")
                .andValidDateLessThanOrEqualTo(LocalDateTime.of(2021, 03, 15, 00, 00, 00));
        Long result = translationTenderMasterMapper.countByCondition(condition);
        assertEquals(result, Long.valueOf(1L));
    }

    @Test
    @DatabaseSetup("TranslationTenderMasterMapperTest.xml")
    public void testCountByConditionNotExisted() {
        TranslationTenderMasterCondition condition = new TranslationTenderMasterCondition();
        condition.createCriteria()
                .andStoreCodeEqualTo("3")
                .andTenderIdEqualTo("3")
                .andValidDateLessThanOrEqualTo(LocalDateTime.of(1999, 03, 15, 00, 00, 00));
        Long result = translationTenderMasterMapper.countByCondition(condition);
        assertEquals(result, Long.valueOf(0L));
    }
}
