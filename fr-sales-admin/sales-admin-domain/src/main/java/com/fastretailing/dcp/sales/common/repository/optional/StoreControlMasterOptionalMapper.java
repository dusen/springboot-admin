/**
 * @(#)StoreControlMasterOptionalMapper.java
 *
 *                                           Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.repository.optional;

import org.apache.ibatis.annotations.Mapper;
import com.fastretailing.dcp.sales.common.entity.optional.StoreControlMasterOptional;

/**
 * Store control master optional mapper.
 */
@Mapper
public interface StoreControlMasterOptionalMapper {

    /**
     * Select store control master optional data.
     * 
     * @param storeCode Store code.
     * @return Store control master optional.
     */
    StoreControlMasterOptional selectByPrimaryKey(String storeCode);

    /**
     * Update store control master optional data.
     * 
     * @param storeControlMasterOptional Store control master optional.
     * @return Update result count.
     */
    int updateByPrimaryKeySelective(StoreControlMasterOptional storeControlMasterOptional);

}
