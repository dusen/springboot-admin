/**
 * @(#)StoreGeneralPurposeMasterOptionalMapper.java
 *
 *                                                  Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.repository.optional;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * This class is a repository that getting records from store general purpose mapped.
 */
@Mapper
public interface StoreGeneralPurposeMasterOptionalMapper {
    /**
     * Select time zone by store code.
     * 
     * @param storeCode store Code.
     * @return Code.
     */
    @Select(" SELECT /* SLS0300202-008 */ code FROM m_store_general_purpose "
            + " WHERE  general_purpose_type = 'time_zone' AND store_code = #{storeCode} ")
    List<String> selectTimeZoneByStoreCode(@Param("storeCode") String storeCode);
    
    
    /**
     * Select time zone by store code.
     * 
     * @param storeCode store Code.
     * @return Code.
     */
    @Select(" SELECT /* SLS0300202-009 */ code FROM m_store_general_purpose "
            + " WHERE  general_purpose_type = 'time_zone' AND store_code in (SELECT store_code FROM m_trans_store_code where view_store_code = #{storeCode}) ")
    List<String> selectTimeZoneByViewStoreCode(@Param("storeCode") String storeCode);
}
