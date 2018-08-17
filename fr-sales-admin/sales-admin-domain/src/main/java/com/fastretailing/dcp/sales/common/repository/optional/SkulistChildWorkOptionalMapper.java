/**
 * @(#)SkulistChildWorkOptionalMapper.java
 *
 *                                         Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.repository.optional;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Skulist child work optional mapper.
 */
@Mapper
public interface SkulistChildWorkOptionalMapper {

    /**
     * Get different eai update type.
     * 
     * @param lotNumber Lot number from request.
     * @return List of eai update type.
     */
    List<String> getEaiUpdateType(@Param("lotNumber") String lotNumber);

}
