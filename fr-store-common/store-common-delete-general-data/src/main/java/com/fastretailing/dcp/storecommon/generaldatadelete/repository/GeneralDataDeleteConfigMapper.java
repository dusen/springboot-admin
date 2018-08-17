/**
 * @(#)GeneralDataDeleteConfigMapper.java
 *
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.generaldatadelete.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.fastretailing.dcp.storecommon.generaldatadelete.dto.GeneralDataDeleteEntity;

/**
 * Repository class for getting general delete data config information.
 */

@Mapper
public interface GeneralDataDeleteConfigMapper {
    /**
     * From the [m_general_delete_setting] table  retrieve all relevant items.
     * 
     * @param processingTargetType Processing target type.
     * @return General data delete entity.
     */
    List<GeneralDataDeleteEntity> selectByProcessingTargetType(
            @Param("processingTargetType") String processingTargetType);
    
}
