/**
 * @(#)MasterUpdateComponentImplTest.java
 *
 *                                        Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.masterupdate.component;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;
import com.fastretailing.dcp.common.exception.BusinessException;
import com.fastretailing.dcp.storecommon.masterupdate.dto.GeneralMasterRequest;
import com.fastretailing.dcp.storecommon.masterupdate.dto.PropertiesItemBean;
import com.fastretailing.dcp.storecommon.masterupdate.repository.MasterUpdateMapper;
import com.fastretailing.dcp.storecommon.util.DateUtility;

/**
 * Unit test class of master update component.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MasterUpdateComponentImplTest {

    /**
     * Exception throw rule.
     */
    @Rule
    public ExpectedException testException = ExpectedException.none();

    /** Service bean to application context. */
    @SpyBean
    private MasterUpdateComponentImpl masterUpdateComponentImpl;

    /** Db access parts mock. */
    @MockBean
    private MasterUpdateMapper masterUpdateMapper;

    /** Properties items bean. */
    @MockBean
    private PropertiesItemBean propertiesItemBean;

    /** Request Information object. */
    private GeneralMasterRequest information;

    /** Column list in table. */
    private Set<String> nameSet;

    /** Properties map. */
    private Map<String, Map<String, String>> propertiesMap;

    /** The map that included update information. */
    private Map<String, String> dataColumnMap;

    /**
     * Test processing. Perform initial setting of transaction information.
     * <P>
     * request JSON data initialize.
     * </P>
     * 
     * @throws Exception Exception.
     */
    @Before
    public void setUp() throws Exception {
        propertiesMap = new HashMap<>();
        dataColumnMap = new HashMap<>();

        // Set trance information
        information = new GeneralMasterRequest();
        information.setOutboundId("OBPF0019E01");
        information.setLotNumber("A001");
        String updateItems = "lot_num,country_code,g_business,non_item_code,"
                + "apply_start_date,create_user_id,create_datetime,create_program_id,"
                + "update_user_id,update_datetime,update_program_id";
        List<String> list = Arrays.asList(updateItems.split(","));
        nameSet = new HashSet<>(list);

    }

    /**
     * Target method: MasterUpdateComponent#masterUpdateComponentImpl.
     * <P>
     * Conditions: Bulk count not defined in property file.
     * </P>
     * 
     * @throws Exception Exception.
     */
    @Test
    public void testBulkCountNotExist() throws Exception {
        when(propertiesItemBean.getBulkCount()).thenReturn(null);

        // Verification start.
        try {
            masterUpdateComponentImpl.updateMasterData(information);
            fail();
        } catch (BusinessException be) {
            // Confirm return value.
            assertThat(be.getResultObject().getMessage(),
                    is("Bulk-count item does not exist in the property file."));
        }
    }

    /**
     * Target method: MasterUpdateComponent#masterUpdateComponentImpl.
     * <P>
     * Conditions: Bulk count is zero in property file.
     * </P>
     * 
     * @throws Exception Exception.
     */
    @Test
    public void testBulkCountIsZero() throws Exception {
        when(propertiesItemBean.getBulkCount()).thenReturn(0);

        // Verification start.
        try {
            masterUpdateComponentImpl.updateMasterData(information);
            fail();
        } catch (BusinessException be) {
            // Confirm return value.
            assertThat(be.getResultObject().getMessage(),
                    is("Bulk-count can not be zero or negative number."));
        }
    }

    /**
     * Target method: MasterUpdateComponent#masterUpdateComponentImpl.
     * <P>
     * Conditions: Null parameter of lot number.
     * </P>
     * 
     * @throws Exception Exception.
     */
    @Test
    public void testGetPropertiesInfoNotExist() throws Exception {

        information.setOutboundId("AAAAF0019E01");
        when(propertiesItemBean.getBulkCount()).thenReturn(10);

        // Verification start.
        try {
            masterUpdateComponentImpl.updateMasterData(information);
            fail();
        } catch (BusinessException be) {
            // Confirm return value.
            assertThat(be.getResultObject().getMessage(),
                    is("An item matching outBoundId does not exist in the property file."));
        }
    }

    /**
     * Target method: MasterUpdateComponent#masterUpdateComponentImpl.
     * <P>
     * Conditions: Out bound id is 'OBPF0019A01'.
     * </P>
     * 
     * @throws Exception Exception.
     */
    @Test
    public void testGetPropertiesInfoIfWorktTblNameNotExist() throws Exception {
        information.setOutboundId("OBPF0019A01");
        propertiesMap.put("OBPF0019A01", dataColumnMap);
        when(propertiesItemBean.getProperties()).thenReturn(propertiesMap);
        when(propertiesItemBean.getBulkCount()).thenReturn(10);

        // Verification start.
        try {
            masterUpdateComponentImpl.updateMasterData(information);
        } catch (BusinessException be) {
            // Confirm return value.
            assertThat(be.getResultObject().getMessage(),
                    is("Correspond [if-work-table-name] does not exist in properties file."));
        }
    }

    /**
     * Target method: MasterUpdateComponent#masterUpdateComponentImpl.
     * <P>
     * Conditions: Out bound id is 'OBPF0019A02'.
     * </P>
     * 
     * @throws Exception Exception.
     */
    @Test
    public void testGetPropertiesInfoInputTblNameNotExist() throws Exception {
        information.setOutboundId("OBPF0019A02");
        dataColumnMap.put("if-work-table-name", "w_type");
        propertiesMap.put("OBPF0019A02", dataColumnMap);
        when(propertiesItemBean.getProperties()).thenReturn(propertiesMap);
        when(propertiesItemBean.getBulkCount()).thenReturn(10);
        when(masterUpdateMapper.countTableName(any(String.class))).thenReturn(1);
        // Service call.
        try {
            masterUpdateComponentImpl.updateMasterData(information);
            fail();
        } catch (BusinessException be) {
            // Confirm return value.
            assertThat(be.getResultObject().getMessage(),
                    is("Correspond [input-table-name] does not exist in properties file."));
        }
    }

    /**
     * Target method: MasterUpdateComponent#masterUpdateComponentImpl.
     * <P>
     * Conditions: Out bound id is 'OBPF0019A03'.
     * </P>
     * 
     * @throws Exception Exception.
     */
    @Test
    public void testGetPropertiesInfoUpdateTypeNotExist() throws Exception {
        information.setOutboundId("OBPF0019A03");
        dataColumnMap.put("if-work-table-name", "m_type_if");
        dataColumnMap.put("input-table-name", "m_type_if");
        propertiesMap.put("OBPF0019A03", dataColumnMap);
        when(propertiesItemBean.getProperties()).thenReturn(propertiesMap);
        when(propertiesItemBean.getBulkCount()).thenReturn(10);
        when(masterUpdateMapper.countTableName(any(String.class))).thenReturn(1).thenReturn(1);
        try {
            // Service call.
            masterUpdateComponentImpl.updateMasterData(information);
            fail();
        } catch (BusinessException be) {
            // Confirm return value.
            assertThat(be.getResultObject().getMessage(),
                    is("Correspond [update-type] does not exist in properties file."));
        }
    }

    /**
     * Target method: MasterUpdateComponent#masterUpdateComponentImpl.
     * <P>
     * Conditions: Out bound id is 'OBPF0019A04'.
     * </P>
     * 
     * @throws Exception Exception.
     */
    @Test
    public void testGetPropertiesInfoUpdateTargetTblNameNotExist() throws Exception {
        information.setOutboundId("OBPF0019A04");
        dataColumnMap.put("if-work-table-name", "w_type");
        dataColumnMap.put("input-table-name", "w_type");
        dataColumnMap.put("update-type", "1");
        propertiesMap.put("OBPF0019A04", dataColumnMap);
        when(propertiesItemBean.getProperties()).thenReturn(propertiesMap);
        when(propertiesItemBean.getBulkCount()).thenReturn(10);
        when(masterUpdateMapper.countTableName(any(String.class))).thenReturn(1).thenReturn(1);
        try {
            masterUpdateComponentImpl.updateMasterData(information);
        } catch (BusinessException be) {
            // Confirm return value.
            assertThat(be.getResultObject().getMessage(),
                    is("Correspond [update-target-table-name] does not exist in properties file."));
        }
    }

    /**
     * Target method: MasterUpdateComponent#masterUpdateComponentImpl.
     * <P>
     * Conditions: Out bound id is 'OBPF0019A05'.
     * </P>
     * 
     * @throws Exception Exception.
     */
    @Test
    public void testGetPropertiesInfoUpdateKeyItemNotExist() throws Exception {
        information.setOutboundId("OBPF0019A05");

        dataColumnMap.put("if-work-table-name", "w_type");
        dataColumnMap.put("input-table-name", "w_type");
        dataColumnMap.put("update-target-table-name", "m_type");
        dataColumnMap.put("update-type", "1");
        propertiesMap.put("OBPF0019A05", dataColumnMap);
        when(propertiesItemBean.getProperties()).thenReturn(propertiesMap);
        when(propertiesItemBean.getBulkCount()).thenReturn(10);
        when(masterUpdateMapper.countTableName(any(String.class))).thenReturn(1)
                .thenReturn(1)
                .thenReturn(1);
        try {
            masterUpdateComponentImpl.updateMasterData(information);
            fail();
        } catch (BusinessException be) {
            // Confirm return value.
            assertThat(be.getResultObject().getMessage(),
                    is("Correspond [update-key-item] does not exist in properties file."));
        }
    }

    /**
     * Target method: MasterUpdateComponent#masterUpdateComponentImpl.
     * <P>
     * Conditions: Out bound id is 'IFTBLNAMENOTEXIT'.
     * </P>
     * 
     * @throws Exception Exception.
     */
    @Test
    public void testIfWorkTableNameNotExist() throws Exception {
        information.setOutboundId("IFTBLNAMENOTEXIT");
        dataColumnMap.put("if-work-table-name", "XXXXX_XX_XXX");
        dataColumnMap.put("input-table-name", "m_non_item_if_test");
        dataColumnMap.put("update-target-table-name", "master_test_01");
        dataColumnMap.put("update-type", "1");
        dataColumnMap.put("update-key-item",
                "lot_num,country_code,g_business,non_item_code,apply_start_date");

        propertiesMap.put("IFTBLNAMENOTEXIT", dataColumnMap);
        when(propertiesItemBean.getProperties()).thenReturn(propertiesMap);
        when(propertiesItemBean.getBulkCount()).thenReturn(10);

        try {
            masterUpdateComponentImpl.updateMasterData(information);
            fail();
        } catch (BusinessException be) {
            // Confirm return value.
            assertThat(be.getResultObject().getMessage(),
                    is("The table named [XXXXX_XX_XXX] does not exist in data base."));
        }
    }

    /**
     * Target method: MasterUpdateComponent#masterUpdateComponentImpl.
     * <P>
     * Conditions: Out bound id is 'INPUTTBLNAMENOTEXIT'.
     * </P>
     * 
     * @throws Exception Exception.
     */
    @Test
    public void testInputTableNameNotExist() throws Exception {
        information.setOutboundId("INPUTTBLNAMENOTEXIT");
        dataColumnMap.put("if-work-table-name", "m_non_item_if_test");
        dataColumnMap.put("input-table-name", "XXX_XXX_X01");
        dataColumnMap.put("update-target-table-name", "master_test_01");
        dataColumnMap.put("update-type", "1");
        dataColumnMap.put("update-key-item",
                "lot_num,country_code,g_business,non_item_code,apply_start_date");

        propertiesMap.put("INPUTTBLNAMENOTEXIT", dataColumnMap);
        when(propertiesItemBean.getProperties()).thenReturn(propertiesMap);
        when(propertiesItemBean.getBulkCount()).thenReturn(10);
        when(masterUpdateMapper.countTableName(any(String.class))).thenReturn(1).thenReturn(0);

        try {
            masterUpdateComponentImpl.updateMasterData(information);
            fail();
        } catch (BusinessException be) {
            // Confirm return value.
            assertThat(be.getResultObject().getMessage(),
                    is("The table named [XXX_XXX_X01] does not exist in data base."));
        }
    }

    /**
     * Target method: MasterUpdateComponent#masterUpdateComponentImpl.
     * <P>
     * Conditions: Out bound id is 'UPDTTAGTBLNAMENOTEXIT'.
     * </P>
     * 
     * @throws Exception Exception.
     */
    @Test
    public void testUpdateTargetTableNameNotExist() throws Exception {
        information.setOutboundId("UPDTTAGTBLNAMENOTEXIT");
        dataColumnMap.put("if-work-table-name", "m_non_item_if_test");
        dataColumnMap.put("input-table-name", "m_non_item_if_test");
        dataColumnMap.put("update-target-table-name", "XXX_TEST_UT_01");
        dataColumnMap.put("update-type", "1");
        dataColumnMap.put("update-key-item",
                "lot_num,country_code,g_business,non_item_code,apply_start_date");

        propertiesMap.put("UPDTTAGTBLNAMENOTEXIT", dataColumnMap);
        when(propertiesItemBean.getProperties()).thenReturn(propertiesMap);
        when(propertiesItemBean.getBulkCount()).thenReturn(10);
        // Verification start.
        when(masterUpdateMapper.countTableName(any(String.class))).thenReturn(1)
                .thenReturn(1)
                .thenReturn(0);

        try {
            masterUpdateComponentImpl.updateMasterData(information);
            fail();
        } catch (BusinessException be) {
            // Confirm return value.
            assertThat(be.getResultObject().getMessage(),
                    is("The table named [XXX_TEST_UT_01] does not exist in data base."));
        }
    }

    /**
     * Target method: MasterUpdateComponent#masterUpdateComponentImpl.
     * <P>
     * Conditions: Out bound id is 'UPDATETYPEWRONG'.
     * </P>
     * 
     * @throws Exception Exception.
     */
    @Test
    public void testUpdateTypeWrong() throws Exception {
        // Set UPDATETYPEWRONG to out bound id.
        information.setOutboundId("UPDATETYPEWRONG");
        dataColumnMap.put("if-work-table-name", "m_non_item_if_test");
        dataColumnMap.put("input-table-name", "m_non_item_if_test");
        dataColumnMap.put("update-target-table-name", "master_test_01");
        dataColumnMap.put("update-type", "10");
        dataColumnMap.put("update-key-item",
                "lot_num,country_code,g_business,non_item_code,apply_start_date");

        propertiesMap.put("UPDATETYPEWRONG", dataColumnMap);
        when(propertiesItemBean.getProperties()).thenReturn(propertiesMap);
        when(propertiesItemBean.getBulkCount()).thenReturn(10);
        when(masterUpdateMapper.getColumnName(any(String.class))).thenReturn(nameSet);
        when(masterUpdateMapper.getColumnName(any(String.class))).thenReturn(nameSet)
                .thenReturn(nameSet);
        when(masterUpdateMapper.countTableName(any(String.class))).thenReturn(1)
                .thenReturn(1)
                .thenReturn(1);
        try {
            masterUpdateComponentImpl.updateMasterData(information);
        } catch (BusinessException be) {
            // Confirm return value.
            assertThat(be.getResultObject().getMessage(),
                    is("The update type is wrong in property file, it must be 0 or 1."));
        }
    }

    /**
     * Target method: MasterUpdateComponent#masterUpdateComponentImpl.
     * <P>
     * Conditions: Out bound id is 'KEYITEMNOTEXIST'.
     * </P>
     * 
     * @throws Exception Exception.
     */
    @Test
    public void testUpdateKeyNotExist() throws Exception {
        information.setOutboundId("KEYITEMNOTEXIST");
        dataColumnMap.put("if-work-table-name", "m_non_item_if_test");
        dataColumnMap.put("input-table-name", "m_non_item_if_test");
        dataColumnMap.put("update-target-table-name", "master_test_01");
        dataColumnMap.put("update-type", "1");
        dataColumnMap.put("update-key-item",
                "xxxaaxxx_code,g_business,non_item_code,apply_start_date");

        propertiesMap.put("KEYITEMNOTEXIST", dataColumnMap);
        String updateItems = "xxxaaxxx_code,country_code,g_business,non_item_code,"
                + "apply_start_date,create_user_id,create_datetime,create_program_id,"
                + "update_user_id,update_datetime,update_program_id";
        Set<String> inputNameSet = new HashSet<>(Arrays.asList(updateItems.split(",")));
        when(propertiesItemBean.getProperties()).thenReturn(propertiesMap);
        when(propertiesItemBean.getBulkCount()).thenReturn(10);
        when(masterUpdateMapper.countTableName(any(String.class))).thenReturn(1)
                .thenReturn(1)
                .thenReturn(1);
        when(masterUpdateMapper.countByLotNumber(any(String.class), any(String.class),
                any(String.class))).thenReturn(1);
        when(masterUpdateMapper.getColumnName(any(String.class))).thenReturn(inputNameSet);
        when(masterUpdateMapper.getColumnName(any(String.class))).thenReturn(inputNameSet)
                .thenReturn(nameSet);
        try {
            masterUpdateComponentImpl.updateMasterData(information);
            fail();
        } catch (BusinessException be) {
            // Confirm return value.
            assertThat(be.getResultObject().getMessage(), is(
                    "There update key item named [xxxaaxxx_code] does not exist in [master_test_01] table."));
        }
        when(masterUpdateMapper.getColumnName(any(String.class))).thenReturn(nameSet);
        try {
            masterUpdateComponentImpl.updateMasterData(information);
            fail();
        } catch (BusinessException be) {
            // Confirm return value.
            assertThat(be.getResultObject().getMessage(), is(
                    "There update key item named [xxxaaxxx_code] does not exist in [m_non_item_if_test] table."));
        }
    }

    /**
     * Target method: MasterUpdateComponent#masterUpdateComponentImpl.
     * <P>
     * Conditions: Out bound id is 'OBPF0019E02'. Lot number is 'A001'.
     * </P>
     * 
     * @throws Exception Exception.
     */
    @Test
    public void testDeleteAll() throws Exception {
        information.setOutboundId("OBPF0019E02");
        information.setLotNumber("A001");
        dataColumnMap.put("if-work-table-name", "m_non_item_if_test");
        dataColumnMap.put("input-table-name", "m_non_item_if_test");
        dataColumnMap.put("update-target-table-name", "master_test_01");
        dataColumnMap.put("update-type", "0");
        dataColumnMap.put("update-key-item",
                "lot_num,country_code,g_business,non_item_code,apply_start_date");

        propertiesMap.put("OBPF0019E02", dataColumnMap);
        when(propertiesItemBean.getProperties()).thenReturn(propertiesMap);
        when(propertiesItemBean.getBulkCount()).thenReturn(10);
        when(masterUpdateMapper.getColumnName(any(String.class))).thenReturn(nameSet);
        when(masterUpdateMapper.getColumnName(any(String.class))).thenReturn(nameSet)
                .thenReturn(nameSet);
        when(masterUpdateMapper.countTableName(any(String.class))).thenReturn(1);
        when(masterUpdateMapper.countEaiUpdateTypeOthers(any(String.class), any(String.class)))
                .thenReturn(0);
        when(masterUpdateMapper.countByLotNumber(any(String.class), any(String.class),
                any(String.class))).thenReturn(1);
        when(masterUpdateMapper.delete(any(String.class))).thenReturn(1);
        masterUpdateComponentImpl.updateMasterData(information);

        verify(masterUpdateMapper).delete(any(String.class));

    }

    /**
     * Target method: MasterUpdateComponent#masterUpdateComponentImpl.
     * <P>
     * Conditions: Out bound id is 'OBPF412E01'.
     * </P>
     * 
     * @throws Exception Exception.
     */
    @Test
    public void testDeleteDifference() throws Exception {
        //
        information.setOutboundId("OBPF412E01");
        information.setLotNumber("A001");
        dataColumnMap.put("if-work-table-name", "m_non_item_if_test");
        dataColumnMap.put("input-table-name", "m_non_item_if_test");
        dataColumnMap.put("update-target-table-name", "master_test_02");
        dataColumnMap.put("update-type", "1");
        dataColumnMap.put("update-key-item",
                "lot_num,country_code,g_business,non_item_code,apply_start_date");

        propertiesMap.put("OBPF412E01", dataColumnMap);
        when(propertiesItemBean.getProperties()).thenReturn(propertiesMap);
        when(propertiesItemBean.getBulkCount()).thenReturn(10);
        when(masterUpdateMapper.getColumnName(any(String.class))).thenReturn(nameSet);
        when(masterUpdateMapper.getColumnName(any(String.class))).thenReturn(nameSet)
                .thenReturn(nameSet);
        when(masterUpdateMapper.countTableName(any(String.class))).thenReturn(1);
        when(masterUpdateMapper.countEaiUpdateTypeOthers(any(String.class), any(String.class)))
                .thenReturn(0);
        when(masterUpdateMapper.countByLotNumber(any(String.class), any(String.class),
                any(String.class))).thenReturn(1);
        when(masterUpdateMapper.deleteByCondition(any(String.class), any(String.class), any(),
                any(String.class))).thenReturn(1);

        // Service call.
        masterUpdateComponentImpl.updateMasterData(information);
        // Confirm return value.
        verify(masterUpdateMapper).deleteByCondition(any(String.class), any(String.class), any(),
                any(String.class));

    }

    /**
     * Target method: MasterUpdateComponent#masterUpdateComponentImpl.
     * <P>
     * Conditions: Out bound id is 'TESTEAIXA01'. Lot number is 'A001'.
     * </P>
     * 
     * @throws Exception Exception.
     */
    @Test
    public void testMasterUpdateAllOthers() throws Exception {

        information.setOutboundId("TESTEAIXA01");
        information.setLotNumber("A001");
        dataColumnMap.put("if-work-table-name", "m_non_item_if_eaitest_x_all");
        dataColumnMap.put("input-table-name", "m_non_item_if_eaitest_x_all");
        dataColumnMap.put("update-target-table-name", "master_eaitest_x_all");
        dataColumnMap.put("update-type", "1");
        dataColumnMap.put("update-key-item", "lot_num");

        propertiesMap.put("TESTEAIXA01", dataColumnMap);
        when(propertiesItemBean.getProperties()).thenReturn(propertiesMap);
        when(propertiesItemBean.getBulkCount()).thenReturn(10);
        when(masterUpdateMapper.countTableName(any(String.class))).thenReturn(1);
        when(masterUpdateMapper.countByLotNumber(any(String.class), any(String.class),
                any(String.class))).thenReturn(1);
        when(masterUpdateMapper.getColumnName(any(String.class))).thenReturn(nameSet);
        when(masterUpdateMapper.getColumnName(any(String.class))).thenReturn(nameSet)
                .thenReturn(nameSet);
        when(masterUpdateMapper.countEaiUpdateTypeOthers(any(String.class), any(String.class)))
                .thenReturn(1);
        try {
            // Service call.
            masterUpdateComponentImpl.updateMasterData(information);
            fail();
        } catch (BusinessException be) {
            // Confirm return value.
            assertThat(be.getResultObject().getMessage(),
                    is("EAI update type of input table is not correct."));
        }

        verify(masterUpdateMapper, never()).upsert(any());
    }

    /**
     * Target method: MasterUpdateComponent#masterUpdateComponentImpl.
     * <P>
     * Conditions: Out bound id is 'OBPF0019E04'. Lot number is 'A001'.
     * </P>
     * 
     * @throws Exception Exception.
     */
    @Test
    public void testGetColumnNameTable() throws Exception {

        information.setOutboundId("OBPF0019E04");
        information.setLotNumber("A001");
        dataColumnMap.put("if-work-table-name", "m_non_item_if_test");
        dataColumnMap.put("input-table-name", "m_non_item_if_test");
        dataColumnMap.put("update-target-table-name", "master_test_02");
        dataColumnMap.put("update-type", "0");
        dataColumnMap.put("update-key-item",
                "lot_num,country_code,g_business,non_item_code,apply_start_date");

        propertiesMap.put("OBPF0019E04", dataColumnMap);
        when(propertiesItemBean.getProperties()).thenReturn(propertiesMap);
        when(propertiesItemBean.getBulkCount()).thenReturn(10);
        when(masterUpdateMapper.countByLotNumber(any(String.class), any(String.class),
                any(String.class))).thenReturn(1);
        when(masterUpdateMapper.getColumnName(any(String.class))).thenReturn(nameSet);
        when(masterUpdateMapper.getColumnName(any(String.class))).thenReturn(nameSet)
                .thenReturn(nameSet);
        when(masterUpdateMapper.countTableName(any(String.class))).thenReturn(1);

        when(masterUpdateMapper.countEaiUpdateTypeOthers(any(String.class), any(String.class)))
                .thenReturn(0);

        // Service call.
        masterUpdateComponentImpl.updateMasterData(information);

        verify(masterUpdateMapper, times(2)).getColumnName(any(String.class));

    }

    /**
     * Target method: MasterUpdateComponent#masterUpdateComponentImpl.
     * <P>
     * Conditions: Out bound id is 'OBPF0019V01'. Lot number is 'A001'.
     * </P>
     * 
     * @throws Exception Exception.
     */
    @Test
    public void testGetColumnNameView() throws Exception {

        information.setOutboundId("OBPF0019V01");
        information.setLotNumber("A001");
        dataColumnMap.put("if-work-table-name", "m_non_item_if_test");
        dataColumnMap.put("input-table-name", "m_non_item_if_test_view_01");
        dataColumnMap.put("update-target-table-name", "master_test_02");
        dataColumnMap.put("update-type", "0");
        dataColumnMap.put("update-key-item",
                "lot_num,country_code,g_business,non_item_code,apply_start_date");

        propertiesMap.put("OBPF0019V01", dataColumnMap);
        when(propertiesItemBean.getProperties()).thenReturn(propertiesMap);
        when(propertiesItemBean.getBulkCount()).thenReturn(10);
        when(masterUpdateMapper.countTableName(any(String.class))).thenReturn(1);
        when(masterUpdateMapper.getColumnName(any(String.class))).thenReturn(nameSet);
        when(masterUpdateMapper.getColumnName(any(String.class))).thenReturn(nameSet)
                .thenReturn(nameSet);
        when(masterUpdateMapper.countByLotNumber(any(String.class), any(String.class),
                any(String.class))).thenReturn(1);
        when(masterUpdateMapper.countEaiUpdateTypeOthers(any(String.class), any(String.class)))
                .thenReturn(0);

        // Service call.
        masterUpdateComponentImpl.updateMasterData(information);

        verify(masterUpdateMapper, times(2)).getColumnName(any(String.class));
    }

    /**
     * Target method: MasterUpdateComponent#masterUpdateComponentImpl.
     * <P>
     * Conditions: Out bound id is 'OBPF0019V01'. Lot number is 'A001'.
     * </P>
     * 
     * @throws Exception Exception.
     */
    @Test
    public void testGetInputDataNotExist() throws Exception {

        information.setOutboundId("OBPF0019V01");
        information.setLotNumber("A001");
        dataColumnMap.put("if-work-table-name", "m_non_item_if_test");
        dataColumnMap.put("input-table-name", "m_non_item_if_test_view_01");
        dataColumnMap.put("update-target-table-name", "master_test_02");
        dataColumnMap.put("update-type", "0");
        dataColumnMap.put("update-key-item",
                "lot_num,country_code,g_business,non_item_code,apply_start_date");

        propertiesMap.put("OBPF0019V01", dataColumnMap);
        when(propertiesItemBean.getProperties()).thenReturn(propertiesMap);
        when(propertiesItemBean.getBulkCount()).thenReturn(10);
        when(masterUpdateMapper.getColumnName(any(String.class))).thenReturn(nameSet);
        when(masterUpdateMapper.getColumnName(any(String.class))).thenReturn(nameSet)
                .thenReturn(nameSet);
        when(masterUpdateMapper.countTableName(any(String.class))).thenReturn(1);
        when(masterUpdateMapper.countByLotNumber(any(String.class), any(String.class),
                any(String.class))).thenReturn(0);
        when(masterUpdateMapper.countEaiUpdateTypeOthers(any(String.class), any(String.class)))
                .thenReturn(0);

        // Service call.
        masterUpdateComponentImpl.updateMasterData(information);

        verify(masterUpdateMapper, never()).deleteByCondition(any(String.class), any(String.class),
                any(), any(String.class));
        verify(masterUpdateMapper, never()).delete(any(String.class));
        verify(masterUpdateMapper, never()).upsert(any());
    }

    /**
     * Target method: MasterUpdateComponent#masterUpdateComponentImpl.
     * <P>
     * Conditions: Out bound id is 'OBPF0019V01'. Lot number is 'A001'.
     * </P>
     * 
     * @throws Exception Exception.
     */
    @Test
    public void testGetInputDataExist() throws Exception {

        information.setOutboundId("TEST0019E01");
        information.setLotNumber("A001");
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < 20; i++) {
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("type", "varchar");
            dataMap.put("value", "A" + i);
            list.add(dataMap);
        }

        dataColumnMap.put("if-work-table-name", "m_non_item_if_test");
        dataColumnMap.put("input-table-name", "m_non_item_if_test_01");
        dataColumnMap.put("update-target-table-name", "master_test_01");
        dataColumnMap.put("update-type", "1");
        dataColumnMap.put("update-key-item",
                "lot_num,country_code,g_business,non_item_code,apply_start_date");

        propertiesMap.put("TEST0019E01", dataColumnMap);
        when(propertiesItemBean.getProperties()).thenReturn(propertiesMap);
        when(propertiesItemBean.getBulkCount()).thenReturn(10);

        String updateItems = "lot_num,country_code,g_business,non_item_code,apply_start_date";
        List<String> updateItemsList = Arrays.asList(updateItems.split(","));
        Set<String> nameSet = new HashSet<>(updateItemsList);
        when(masterUpdateMapper.getColumnName(any(String.class))).thenReturn(nameSet);
        when(masterUpdateMapper.getColumnName(any(String.class))).thenReturn(nameSet)
                .thenReturn(nameSet);
        when(masterUpdateMapper.countTableName(any(String.class))).thenReturn(1);
        when(masterUpdateMapper.countByLotNumber(any(String.class), any(String.class),
                any(String.class))).thenReturn(3);
        when(masterUpdateMapper.countEaiUpdateTypeOthers(any(String.class), any(String.class)))
                .thenReturn(0);
        when(masterUpdateMapper.deleteByCondition(any(String.class), any(String.class), any(),
                any(String.class))).thenReturn(list.size());
        when(masterUpdateMapper.getInputData(any(String.class), any(String.class),
                any(String.class), any(String.class), any(Integer.class), any(Integer.class),
                any(String.class), any(String.class))).thenReturn(list);
        when(masterUpdateMapper.upsert(any())).thenReturn(list.size());
        // Service call.
        masterUpdateComponentImpl.updateMasterData(information);

        verify(masterUpdateMapper).deleteByCondition(any(String.class), any(String.class), any(),
                any(String.class));
        verify(masterUpdateMapper, never()).delete(any(String.class));
        verify(masterUpdateMapper).upsert(any());
    }

    /**
     * Target method: MasterUpdateComponent#masterUpdateComponentImpl.
     * <P>
     * Conditions: Out bound id is 'OBPF0019E01'. Lot number is 'A003'.
     * </P>
     * 
     * @throws Exception Exception.
     */
    @Test
    public void testUpsertFailed() throws Exception {
        information.setOutboundId("TEST0019E01");
        information.setLotNumber("A001");

        dataColumnMap.put("if-work-table-name", "m_non_item_if_test");
        dataColumnMap.put("input-table-name", "m_non_item_if_test_01");
        dataColumnMap.put("update-target-table-name", "master_test_01");
        dataColumnMap.put("update-type", "1");
        dataColumnMap.put("update-key-item",
                "lot_num,country_code,g_business,non_item_code,apply_start_date");

        propertiesMap.put("TEST0019E01", dataColumnMap);
        when(propertiesItemBean.getProperties()).thenReturn(propertiesMap);
        when(propertiesItemBean.getBulkCount()).thenReturn(10);
        when(masterUpdateMapper.getColumnName(any(String.class))).thenReturn(nameSet);
        when(masterUpdateMapper.getColumnName(any(String.class))).thenReturn(nameSet)
                .thenReturn(nameSet);
        when(masterUpdateMapper.countTableName(any(String.class))).thenReturn(1);
        when(masterUpdateMapper.countByLotNumber(any(String.class), any(String.class),
                any(String.class))).thenReturn(3);
        when(masterUpdateMapper.countEaiUpdateTypeOthers(any(String.class), any(String.class)))
                .thenReturn(0);
        when(masterUpdateMapper.deleteByCondition(any(String.class), any(String.class), any(),
                any(String.class))).thenReturn(0);
        when(masterUpdateMapper.upsert(any())).thenReturn(10);
        // Service call.
        try {
            masterUpdateComponentImpl.updateMasterData(information);
            fail();
        } catch (BusinessException be) {
            assertThat(be.getResultObject().getMessage(), is("Bulk update or insert is failed."));
            verify(masterUpdateMapper, never()).delete(any(String.class));
            verify(masterUpdateMapper).upsert(any());
        }

    }

    /**
     * Target method: MasterUpdateComponent#masterUpdateComponentImpl.
     * <P>
     * Conditions: There is a conversion target in property file.
     * </P>
     * 
     * @throws Exception Exception.
     */
    @Test
    public void testTenderUpsertSuccess() throws Exception {
        information.setOutboundId("TENDER0019E01");
        information.setLotNumber("A001");
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < 20; i++) {
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("type", "varchar");
            dataMap.put("value", DateUtility.getZonedDateTimeUtc().format(DateTimeFormatter
                    .ofPattern(DateUtility.DateTimeFormat.UUUUHMMHDDHHQMIQSS_SSS.getFormat())));
            dataMap.put("timezoneid", ZoneId.systemDefault());
            list.add(dataMap);
        }
        dataColumnMap.put("if-work-table-name", "w_tender");
        dataColumnMap.put("input-table-name", "v_tender");
        dataColumnMap.put("update-target-table-name", "m_tender");
        dataColumnMap.put("update-type", "1");
        dataColumnMap.put("update-key-item", "store_code,ims_tender_id");
        dataColumnMap.put(PropertiesFileItemName.LOCAL_TO_UTC_CONVERSION_ITEM_NAME, "valid_date");
        dataColumnMap.put(PropertiesFileItemName.LOCAL_TO_UTC_CONVERSION_FORMAT,
                DateUtility.DateTimeFormat.UUUUHMMHDDHHQMIQSS_SSS.getFormat());
        dataColumnMap.put(PropertiesFileItemName.TIME_ZONE_ID_ITEM_NAME, "code");

        propertiesMap.put("TENDER0019E01", dataColumnMap);
        when(propertiesItemBean.getProperties()).thenReturn(propertiesMap);

        when(propertiesItemBean.getBulkCount()).thenReturn(10);
        String updateItems = "store_code,tender_name,receipt_tender_name,valid_date,"
                + "currency_code,kid,change_flag,current_deposit_flag,"
                + "credit_company_code,system_country_code,system_business_code,"
                + "system_brand_code,ims_tender_id,ims_tender_group,create_user_id,create_datetime,"
                + "create_program_id,update_user_id,update_datetime,update_program_id";
        List<String> list2 = Arrays.asList(updateItems.split(","));
        Set<String> nameSet2 = new HashSet<>(list2);

        when(masterUpdateMapper.getColumnName(any())).thenReturn(nameSet2);
        when(masterUpdateMapper.getColumnName(any())).thenReturn(nameSet2);
        when(masterUpdateMapper.countTableName(any(String.class))).thenReturn(1);
        when(masterUpdateMapper.countByLotNumber(any(String.class), any(String.class),
                any(String.class))).thenReturn(3);
        when(masterUpdateMapper.countEaiUpdateTypeOthers(any(String.class), any(String.class)))
                .thenReturn(0);
        when(masterUpdateMapper.deleteByCondition(any(String.class), any(String.class), any(),
                any(String.class))).thenReturn(0);
        when(masterUpdateMapper.getInputData(any(String.class), any(String.class),
                any(String.class), any(String.class), any(Integer.class), any(Integer.class),
                any(String.class), any(String.class))).thenReturn(list);
        when(masterUpdateMapper.upsert(any())).thenReturn(list.size());
        // Service call.
        masterUpdateComponentImpl.updateMasterData(information);
        verify(masterUpdateMapper, never()).delete(any(String.class));
        verify(masterUpdateMapper).upsert(any());
    }

    /**
     * Target method: MasterUpdateComponent#masterUpdateComponentImpl.
     * <P>
     * Conditions: Out bound id is 'TEST0019E01'. Lot number is 'A003'.
     * </P>
     * 
     * @throws Exception Exception.
     */
    @Test
    public void testTenderSpaceFormat() throws Exception {
        information.setOutboundId("TENDER0019E01");
        information.setLotNumber("A001");
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < 20; i++) {
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("type", "varchar");
            dataMap.put("value", DateUtility.getZonedDateTimeUtc().format(DateTimeFormatter
                    .ofPattern(DateUtility.DateTimeFormat.UUUUHMMHDDHHQMIQSS_SSS.getFormat())));
            dataMap.put("timezoneid", ZoneId.systemDefault());
            list.add(dataMap);
        }
        dataColumnMap.put("if-work-table-name", "w_tender");
        dataColumnMap.put("input-table-name", "v_tender");
        dataColumnMap.put("update-target-table-name", "m_tender");
        dataColumnMap.put("update-type", "1");
        dataColumnMap.put("update-key-item", "store_code,ims_tender_id");
        dataColumnMap.put(PropertiesFileItemName.LOCAL_TO_UTC_CONVERSION_ITEM_NAME, "valid_date");
        dataColumnMap.put(PropertiesFileItemName.LOCAL_TO_UTC_CONVERSION_FORMAT, " ");
        dataColumnMap.put(PropertiesFileItemName.TIME_ZONE_ID_ITEM_NAME, "code");

        propertiesMap.put("TENDER0019E01", dataColumnMap);
        when(propertiesItemBean.getProperties()).thenReturn(propertiesMap);

        when(propertiesItemBean.getBulkCount()).thenReturn(10);
        String updateItems = "store_code,tender_name,receipt_tender_name,valid_date,"
                + "currency_code,kid,change_flag,current_deposit_flag,"
                + "credit_company_code,system_country_code,system_business_code,"
                + "system_brand_code,ims_tender_id,ims_tender_group,create_user_id,create_datetime,"
                + "create_program_id,update_user_id,update_datetime,update_program_id";
        List<String> list2 = Arrays.asList(updateItems.split(","));
        Set<String> nameSet2 = new HashSet<>(list2);

        when(masterUpdateMapper.getColumnName(any())).thenReturn(nameSet2);
        when(masterUpdateMapper.getColumnName(any())).thenReturn(nameSet2);
        when(masterUpdateMapper.countTableName(any(String.class))).thenReturn(1);
        when(masterUpdateMapper.countByLotNumber(any(String.class), any(String.class),
                any(String.class))).thenReturn(3);
        when(masterUpdateMapper.countEaiUpdateTypeOthers(any(String.class), any(String.class)))
                .thenReturn(0);
        when(masterUpdateMapper.deleteByCondition(any(String.class), any(String.class), any(),
                any(String.class))).thenReturn(0);
        when(masterUpdateMapper.getInputData(any(String.class), any(String.class),
                any(String.class), any(String.class), any(Integer.class), any(Integer.class),
                any(String.class), any(String.class))).thenReturn(list);
        when(masterUpdateMapper.upsert(any())).thenReturn(list.size());
        // Service call.
        masterUpdateComponentImpl.updateMasterData(information);
        verify(masterUpdateMapper, never()).delete(any(String.class));
        verify(masterUpdateMapper).upsert(any());
    }

    /**
     * Target method: MasterUpdateComponent#masterUpdateComponentImpl.
     * <P>
     * Conditions: Out bound id is 'INPUTTBLE01'. Lot number is 'A001'.
     * </P>
     * 
     * @throws Exception Exception.
     */
    @Test
    public void testDeleteInputDataTable() throws Exception {

        information.setOutboundId("INPUTTBLE01");
        information.setLotNumber("A001");

        dataColumnMap.put("if-work-table-name", "m_non_item_if_test_04");
        dataColumnMap.put("input-table-name", "m_non_item_if_test_04");
        dataColumnMap.put("update-target-table-name", "master_test_05");
        dataColumnMap.put("update-type", "1");
        dataColumnMap.put("update-key-item",
                "lot_num,country_code,g_business,non_item_code,apply_start_date");

        propertiesMap.put("INPUTTBLE01", dataColumnMap);
        when(propertiesItemBean.getProperties()).thenReturn(propertiesMap);
        when(propertiesItemBean.getBulkCount()).thenReturn(10);
        when(masterUpdateMapper.getColumnName(any(String.class))).thenReturn(nameSet);
        when(masterUpdateMapper.getColumnName(any(String.class))).thenReturn(nameSet)
                .thenReturn(nameSet);
        when(masterUpdateMapper.countTableName(any(String.class))).thenReturn(1);

        when(masterUpdateMapper.countByLotNumber(any(String.class), any(String.class),
                any(String.class))).thenReturn(1);
        when(masterUpdateMapper.countEaiUpdateTypeOthers(any(String.class), any(String.class)))
                .thenReturn(0);

        // Service call.
        masterUpdateComponentImpl.updateMasterData(information);

        verify(masterUpdateMapper).deleteByLotNumber(any(String.class), any(String.class));
    }

    /**
     * Target method: MasterUpdateComponent#masterUpdateComponentImpl.
     * <P>
     * Conditions: Out bound id is 'INPUTVIEW01'. Lot number is 'A001'.
     * </P>
     * 
     * @throws Exception Exception.
     */
    @Test
    public void testDeleteInputDataView() throws Exception {

        information.setOutboundId("INPUTVIEW01");
        information.setLotNumber("A001");
        dataColumnMap.put("if-work-table-name", "m_non_item_if_test_view_05");
        dataColumnMap.put("input-table-name", "m_non_item_if_test_view_05");
        dataColumnMap.put("update-target-table-name", "master_test_05");
        dataColumnMap.put("update-type", "1");
        dataColumnMap.put("update-key-item",
                "lot_num,country_code,g_business,non_item_code,apply_start_date");

        propertiesMap.put("INPUTVIEW01", dataColumnMap);
        when(propertiesItemBean.getProperties()).thenReturn(propertiesMap);
        when(propertiesItemBean.getBulkCount()).thenReturn(10);
        when(masterUpdateMapper.getColumnName(any(String.class))).thenReturn(nameSet);
        when(masterUpdateMapper.getColumnName(any(String.class))).thenReturn(nameSet)
                .thenReturn(nameSet);
        when(masterUpdateMapper.countTableName(any(String.class))).thenReturn(1);

        when(masterUpdateMapper.countByLotNumber(any(String.class), any(String.class),
                any(String.class))).thenReturn(1);
        when(masterUpdateMapper.countEaiUpdateTypeOthers(any(String.class), any(String.class)))
                .thenReturn(0);

        /* service call. */
        masterUpdateComponentImpl.updateMasterData(information);

        verify(masterUpdateMapper).deleteByLotNumber(any(String.class), any(String.class));
    }

}
