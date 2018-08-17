package com.fastretailing.dcp.sales.common;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import com.fastretailing.dcp.sales.common.entity.optional.CommonCodeMasterOptional;
import com.fastretailing.dcp.sales.common.form.SelectItem;
import com.fastretailing.dcp.sales.common.repository.optional.CommonCodeMasterOptionalMapper;

/**
 * RestTemplate utility class test.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SalesAdminCommonApplication.class)
public class ComponentHelperTest {

    /**
     * Component helper.
     */
    @Autowired
    private ComponentHelper componentHelper;

    /**
     * Common code master mapper.
     **/
    @MockBean
    private CommonCodeMasterOptionalMapper commonCodeMasterMapper;

    /**
     * Select common code master by type id test.
     * 
     */
    @Test
    public void testSelectCommonCodeMasterByTypeId() {
        CommonCodeMasterOptional entity = new CommonCodeMasterOptional();
        entity.setName1("name1");
        entity.setName2("name2");
        entity.setTypeValue("typeValue");
        List<CommonCodeMasterOptional> resultList = new ArrayList<>();
        resultList.add(entity);

        when(commonCodeMasterMapper.selectByTypeId(anyString())).thenReturn(resultList);
        List<CommonCodeMasterOptional> expectedList =
                componentHelper.selectCommonCodeMasterByTypeId("1");

        assertThat(expectedList.get(0).getName1(), is("name1"));
        assertThat(expectedList.get(0).getName2(), is("name2"));
        assertThat(expectedList.get(0).getTypeValue(), is("typeValue"));
    }

    /**
     * Get select item list by common code master test.
     * 
     */
    @Test
    public void testGetSelectItemListByCommonCodeMaster() {
        CommonCodeMasterOptional entity = new CommonCodeMasterOptional();
        entity.setName1("name1");
        entity.setName2("name2");
        entity.setTypeValue("typeValue");

        List<CommonCodeMasterOptional> mapperResultList = new ArrayList<>();
        mapperResultList.add(entity);

        when(commonCodeMasterMapper.selectByTypeId(anyString())).thenReturn(mapperResultList);

        List<SelectItem> expectedList =
                componentHelper.getSelectItemListByCommonCodeMaster(anyString());

        assertThat(expectedList.get(0).getName(), is("name1"));
        assertThat(expectedList.get(0).getValue(), is("typeValue"));
    }

    /**
     * Get select item map by common code master test.
     * 
     */
    @Test
    public void testGetSelectMapByCommonCodeMaster() {
        CommonCodeMasterOptional entity = new CommonCodeMasterOptional();
        entity.setName1("name1");
        entity.setName2("name2");
        entity.setTypeValue("typeValue");
        List<CommonCodeMasterOptional> mapperResultList = new ArrayList<>();
        mapperResultList.add(entity);

        when(commonCodeMasterMapper.selectByTypeId(anyString())).thenReturn(mapperResultList);

        Map<String, String> expectedMap =
                componentHelper.getSelectMapByCommonCodeMaster(anyString());

        for (Map.Entry<String, String> entry : expectedMap.entrySet()) {
            assertThat(entry.getKey(), is("typeValue"));
            assertThat(entry.getValue(), is("name1"));
        }
    }

    /**
     * Get select item list by common code master is null test.
     * 
     */
    @Test
    public void testGetSelectItemListByCommonCodeMasterIsNull() {
        when(commonCodeMasterMapper.selectByTypeId(anyString())).thenReturn(null);

        List<SelectItem> expectedList =
                componentHelper.getSelectItemListByCommonCodeMaster(anyString());

        assertNull(expectedList);
    }

    /**
     * Get select item map by common code master is null test.
     * 
     */
    @Test
    public void testGetSelectMapByCommonCodeMasterIsNull() {
        when(commonCodeMasterMapper.selectByTypeId(anyString())).thenReturn(null);

        Map<String, String> expectedMap =
                componentHelper.getSelectMapByCommonCodeMaster(anyString());

        assertNull(expectedMap);
    }

}
