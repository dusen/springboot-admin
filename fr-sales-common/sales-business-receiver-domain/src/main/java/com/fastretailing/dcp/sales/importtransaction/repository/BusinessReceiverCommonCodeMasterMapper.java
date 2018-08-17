/**
 * @(#)BusinessReceiverCommonCodeMasterMapper.java
 *
 *                          Copyright (c) 2018 Fast Retailing Corporation.
 */
package com.fastretailing.dcp.sales.importtransaction.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Type name acquisition mapper. 
 *
 */
@Mapper
public interface BusinessReceiverCommonCodeMasterMapper {
    /**
     * Select type name from common code master.
     *
     * @param typeId Type id.
     * @param typeValue Type value.
     * @return Type name.
     */
    @Select("SELECT name_1 FROM m_common_code_master "
            + "WHERE type_value=#{typeValue} AND type_id=#{typeId}")
    String selectTypeName(@Param("typeId")String typeId, @Param("typeValue") String typeValue);
}
