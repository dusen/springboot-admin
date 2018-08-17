/**
 * @(#)PayoffDataOptionalMapper.java
 *
 *                                   Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.repository.optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.fastretailing.dcp.sales.common.entity.optional.PayoffDataOptional;
import com.fastretailing.dcp.sales.common.entity.optional.PayoffDataOptionalCondition;

@Mapper
public interface PayoffDataOptionalMapper {

    /**
     * Insert pay off data.
     * 
     * @param record Pay off data optional.
     * @return Count.
     */
    int insert(PayoffDataOptional record);

    /**
     * Update by primary key selective.
     * 
     * @param record Pay off data optional.
     * @return Count.
     */
    int updateByPrimaryKeySelective(PayoffDataOptional record);
    
    /**
     * Update by condition.
     * This method corresponds to the database table t_pay_off_data
     *
     *@param record Pay off data optional.
     *@return Count.
     */
    int updateByConditionSelective(@Param("record") PayoffDataOptional record, @Param("example") PayoffDataOptionalCondition example);
}
