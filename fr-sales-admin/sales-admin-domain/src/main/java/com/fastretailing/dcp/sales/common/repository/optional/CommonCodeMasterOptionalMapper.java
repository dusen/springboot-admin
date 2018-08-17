/**
 * @(#)CommonCodeMasterOptionalMapper.java
 *
 *                                         Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.repository.optional;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.fastretailing.dcp.sales.common.entity.optional.CommonCodeMasterOptional;

@Mapper
public interface CommonCodeMasterOptionalMapper {

    /**
     * Select common code master.
     * 
     * @param typeId Type id.
     * @return Common code master list.
     */
    @Select("SELECT /* CommonCodeMaster-001 */ " + "type_value , name_1, name_2 "
            + "FROM m_common_code_master WHERE type_id = #{type_id} order by display_order ")
    List<CommonCodeMasterOptional> selectByTypeId(@Param("typeId") String typeId);
}
