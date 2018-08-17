/**
 * @(#)ComponentHelper.java
 *
 *                          Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fastretailing.dcp.sales.common.entity.optional.CommonCodeMasterOptional;
import com.fastretailing.dcp.sales.common.form.SelectItem;
import com.fastretailing.dcp.sales.common.repository.optional.CommonCodeMasterOptionalMapper;

/**
 * Sales transaction error list service helper.
 */
@Component
public class ComponentHelper {

    /** Type id is system country code. */
    public static final String TYPE_ID_SYSTEM_COUNTRY_CODE = "system_country_code_screen";

    /** Type id is system brand code. */
    public static final String TYPE_ID_SYSTEM_BRAND_CODE = "system_brand_code_screen";

    /** Type id is transaction type . */
    public static final String TYPE_ID_TRANSACTION_TYPE = "transaction_type";

    /** Type id is error type. */
    public static final String TYPE_ID_ERROR_TYPE = "error_type";

    /** Common code master mapper. */
    @Autowired
    private CommonCodeMasterOptionalMapper commonCodeMasterMapper;

    /**
     * Select common code master by type id.
     * 
     * @param typeId Type id.
     * @return Common code master.
     */
    public List<CommonCodeMasterOptional> selectCommonCodeMasterByTypeId(String typeId) {
        return commonCodeMasterMapper.selectByTypeId(typeId);
    }

    /**
     * Get select item by common code master list.
     * 
     * @param typeId Type id.
     * @return Select item list.
     */
    public List<SelectItem> getSelectItemListByCommonCodeMaster(String typeId) {
        List<CommonCodeMasterOptional> commonCodeList = selectCommonCodeMasterByTypeId(typeId);
        if (CollectionUtils.isEmpty(commonCodeList)) {
            return null;
        }
        return commonCodeList.stream().map(commonCodeMaster -> {
            SelectItem selectItem = new SelectItem();
            selectItem.setValue(commonCodeMaster.getTypeValue());
            selectItem.setName(commonCodeMaster.getName1());
            return selectItem;
        }).collect(Collectors.toList());
    }

    /**
     * Get select item by common code master list.
     * 
     * @param typeId Type id.
     * @return Select item list.
     */
    public Map<String, String> getSelectMapByCommonCodeMaster(String typeId) {
        List<CommonCodeMasterOptional> commonCodeList = selectCommonCodeMasterByTypeId(typeId);
        if (CollectionUtils.isEmpty(commonCodeList)) {
            return null;
        }
        return commonCodeList.stream()
                .collect(Collectors.toMap(CommonCodeMasterOptional::getTypeValue,
                        CommonCodeMasterOptional::getName1, (master1, master2) -> master1,
                        LinkedHashMap::new));
    }
}
