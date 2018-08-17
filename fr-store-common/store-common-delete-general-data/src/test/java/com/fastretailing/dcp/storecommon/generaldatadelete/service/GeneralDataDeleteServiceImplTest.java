package com.fastretailing.dcp.storecommon.generaldatadelete.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;
import com.fastretailing.dcp.storecommon.ResultCode;
import com.fastretailing.dcp.storecommon.dto.CommonStatus;
import com.fastretailing.dcp.storecommon.generaldatadelete.dto.GeneralDataDeleteEntity;
import com.fastretailing.dcp.storecommon.generaldatadelete.repository.GeneralDataDeleteConfigMapper;
import com.fastretailing.dcp.storecommon.generaldatadelete.repository.ObjectTableDeleteMapper;
import com.fastretailing.dcp.storecommon.generaldatadelete.service.GeneralDataDeleteServiceImpl;

/**
 * Unit test of GeneralDataDeleteService class.
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class GeneralDataDeleteServiceImplTest {

    /** Service class with master maintenance function of item. */
    @SpyBean
    private GeneralDataDeleteServiceImpl generalDataDeleteService;

    /** Create a mock of access DB for item table. */
    @MockBean
    private GeneralDataDeleteConfigMapper generalDataDeleteConfigMapper;

    /** Create a mock of access DB for item table. */
    @MockBean
    private ObjectTableDeleteMapper objectTableDeleteMapper;

    private GeneralDataDeleteEntity generalDataDeleteEntity;

    /**
     * The preHandle method. Perform initial setting of item information.
     * 
     * @throws Exception (It does not occur as expected value)
     */
    @Before
    public void setUp() throws Exception {
        generalDataDeleteEntity = new GeneralDataDeleteEntity();
        generalDataDeleteEntity.setProcessingNo(1L);
        generalDataDeleteEntity.setProcessingTargetType("01");
        generalDataDeleteEntity.setDateItem("01");
        generalDataDeleteEntity.setSavedDays(Long.parseLong("1"));
        generalDataDeleteEntity.setDeleteTargetTable("01");
    }

    @Test
    public void testParamterNotExist() {

        when(generalDataDeleteConfigMapper.selectByProcessingTargetType("COMMON"))
                .thenReturn(null);
        List<GeneralDataDeleteEntity> dataDeleteEntities = new ArrayList<>();
        when(generalDataDeleteConfigMapper.selectByProcessingTargetType(any()))
                .thenReturn(dataDeleteEntities);
        CommonStatus result = generalDataDeleteService.delete(null);

        CommonStatus commonStatus = new CommonStatus();
        assertThat(result, is(commonStatus));
    }

    @Test
    public void testParamterExist() {

        String processingTargetType = "COMMON";
        List<GeneralDataDeleteEntity> dataDeleteEntities = new ArrayList<>();
        when(generalDataDeleteConfigMapper.selectByProcessingTargetType(processingTargetType))
                .thenReturn(dataDeleteEntities);
        CommonStatus result = generalDataDeleteService.delete(processingTargetType);
        verify(generalDataDeleteConfigMapper, times(1))
                .selectByProcessingTargetType(processingTargetType);

        CommonStatus commonStatus = new CommonStatus();
        commonStatus.setResultCode(ResultCode.NORMAL);
        assertThat(result, is(commonStatus));
    }



    @Test
    public void testGetGeneralNotData() {

        List<GeneralDataDeleteEntity> generalDataDeleteEntity =
                new ArrayList<GeneralDataDeleteEntity>();
        when(generalDataDeleteConfigMapper.selectByProcessingTargetType(Mockito.anyString()))
                .thenReturn(generalDataDeleteEntity);
        generalDataDeleteEntity = null;
        CommonStatus commontStatus = new CommonStatus();
        CommonStatus result = generalDataDeleteService.delete(Mockito.anyString());
        assertThat(result, is(commontStatus));
    }

    @Test
    public void testGetGeneralNotDataNull() {

        List<GeneralDataDeleteEntity> dataDeleteEntities = new ArrayList<>();
        when(generalDataDeleteConfigMapper.selectByProcessingTargetType(any()))
                .thenReturn(dataDeleteEntities);
        CommonStatus commontStatus = new CommonStatus();
        CommonStatus result = generalDataDeleteService.delete(Mockito.anyString());
        assertThat(result, is(commontStatus));
    }

    
    @Test
    public void testGetGeneralDataConfigSuccess() {
        generalDataDeleteEntity.setProcessingNo(Long.parseLong("1"));
        generalDataDeleteEntity.setProcessingTargetType("01");
        generalDataDeleteEntity.setDateItem("01");
        generalDataDeleteEntity.setSavedDays(Long.parseLong("1"));
        generalDataDeleteEntity.setDeleteTargetTable("01");

        List<GeneralDataDeleteEntity> generalDataDeleteEntitys =
                new ArrayList<>();
        generalDataDeleteEntitys.add(generalDataDeleteEntity);
        when(generalDataDeleteConfigMapper.selectByProcessingTargetType(Mockito.anyString()))
                .thenReturn(generalDataDeleteEntitys);
        generalDataDeleteService.delete(Mockito.anyString());

        verify(generalDataDeleteConfigMapper, times(1)).selectByProcessingTargetType(Mockito.any());
        verify(objectTableDeleteMapper,times(1)).delete(Mockito.any(), Mockito.any());
        reset(generalDataDeleteConfigMapper);
    }

    @Test
    public void testGetGeneralDataConfigSuccessTwo() {
        generalDataDeleteEntity.setProcessingNo(Long.parseLong("1"));
        generalDataDeleteEntity.setProcessingTargetType("02");
        generalDataDeleteEntity.setDateItem("01");
        generalDataDeleteEntity.setSavedDays(Long.parseLong("1"));
        generalDataDeleteEntity.setDeleteTargetTable("01");
        
        GeneralDataDeleteEntity generalDataDeleteEntityTwo = new GeneralDataDeleteEntity();
        generalDataDeleteEntityTwo.setProcessingNo(Long.parseLong("2"));
        generalDataDeleteEntityTwo.setProcessingTargetType("02");
        generalDataDeleteEntityTwo.setDateItem("01");
        generalDataDeleteEntityTwo.setSavedDays(Long.parseLong("1"));
        generalDataDeleteEntityTwo.setDeleteTargetTable("01");


        List<GeneralDataDeleteEntity> generalDataDeleteEntitys = new ArrayList<>();
        generalDataDeleteEntitys.add(generalDataDeleteEntity);
        generalDataDeleteEntitys.add(generalDataDeleteEntityTwo);

        when(generalDataDeleteConfigMapper.selectByProcessingTargetType(Mockito.anyString()))
                .thenReturn(generalDataDeleteEntitys);
        generalDataDeleteService.delete(Mockito.anyString());

        verify(objectTableDeleteMapper, times(2)).delete(Mockito.any(),Mockito.any());
        reset(generalDataDeleteConfigMapper);
    }



    @Test
    public void testGetGeneralDataConfigSuccessThree() {
        generalDataDeleteEntity.setProcessingNo(Long.parseLong("1"));
        generalDataDeleteEntity.setProcessingTargetType("02");
        generalDataDeleteEntity.setDateItem("01");
        generalDataDeleteEntity.setSavedDays(Long.parseLong("1"));
        generalDataDeleteEntity.setDeleteTargetTable("01");
        
        GeneralDataDeleteEntity generalDataDeleteEntityTwo = new GeneralDataDeleteEntity();
        generalDataDeleteEntityTwo.setProcessingNo(Long.parseLong("2"));
        generalDataDeleteEntityTwo.setProcessingTargetType("02");
        generalDataDeleteEntityTwo.setDateItem("01");
        generalDataDeleteEntityTwo.setSavedDays(Long.parseLong("1"));
        generalDataDeleteEntityTwo.setDeleteTargetTable("01");
        
        GeneralDataDeleteEntity generalDataDeleteEntityThree = new GeneralDataDeleteEntity();
        generalDataDeleteEntityThree.setProcessingNo(Long.parseLong("3"));
        generalDataDeleteEntityThree.setProcessingTargetType("02");
        generalDataDeleteEntityThree.setDateItem("01");
        generalDataDeleteEntityThree.setSavedDays(Long.parseLong("1"));
        generalDataDeleteEntityThree.setDeleteTargetTable("01");
        List<GeneralDataDeleteEntity> generalDataDeleteEntitys = new ArrayList<>();
        generalDataDeleteEntitys.add(generalDataDeleteEntity);
        generalDataDeleteEntitys.add(generalDataDeleteEntityTwo);
        generalDataDeleteEntitys.add(generalDataDeleteEntityThree);
        when(generalDataDeleteConfigMapper.selectByProcessingTargetType(Mockito.anyString()))
                .thenReturn(generalDataDeleteEntitys);
        generalDataDeleteService.delete(Mockito.anyString());

        verify(generalDataDeleteConfigMapper, times(1)).selectByProcessingTargetType(Mockito.any());
        reset(generalDataDeleteConfigMapper);
    }
}
