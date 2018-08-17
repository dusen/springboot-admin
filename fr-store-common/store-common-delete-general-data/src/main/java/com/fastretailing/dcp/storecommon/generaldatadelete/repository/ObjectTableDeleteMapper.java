/**
 * @(#)ObjectTableDeleteMapper.java
 *
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.generaldatadelete.repository;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.fastretailing.dcp.storecommon.generaldatadelete.dto.GeneralDataDeleteEntity;


/**
 * Repository class for delete getting Item table information.
 */
@Mapper
public interface ObjectTableDeleteMapper {

    /**
     * Delete treated data from the getting item table table.
     * 
     * @param generalDataDelete General data delete.
     * @param limitedDate.
     * @return The count of this operation.
     */

    int delete(@Param("generalDataDelete") GeneralDataDeleteEntity generalDataDelete,
               @Param("limitedDate") String limitedDate);

}
