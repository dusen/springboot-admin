/**
 * @(#)NonItemMasterWorkOptionalMapper.java
 *
 *                                          Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.repository.optional;

import org.apache.ibatis.annotations.Mapper;
import com.fastretailing.dcp.sales.common.entity.optional.NonItemMasterOptionalInsertCondition;

@Mapper
public interface NonItemMasterWorkOptionalMapper {

    /**
     * Insert into non item master table.
     * 
     * @param example Non item master optional insert condition.
     * @return Insert result count.
     */
    int insertByLotNumber(NonItemMasterOptionalInsertCondition example);
}
