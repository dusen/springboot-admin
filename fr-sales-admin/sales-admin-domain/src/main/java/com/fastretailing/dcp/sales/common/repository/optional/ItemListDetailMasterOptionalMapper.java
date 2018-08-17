/**
 * @(#)ItemListDetailMasterOptionalMapper.java
 *
 *                                             Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.repository.optional;

import java.time.OffsetDateTime;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Item list detail master optional mapper.
 */
@Mapper
public interface ItemListDetailMasterOptionalMapper {

    /**
     * Insert table item list detail master.
     * 
     * @param updateUserId Update and insert user id.
     * @param updateDatetime Update and insert date time.
     * @param updateProgramId Update and insert program id.
     * @param lotNumber Lot number from request.
     * @return Insert result count.
     */
    int insertBySkulistChildWork(@Param("updateUserId") String updateUserId,
            @Param("updateDatetime") OffsetDateTime updateDatetime,
            @Param("updateProgramId") String updateProgramId, @Param("lotNumber") String lotNumber);

    /**
     * Update table item list detail master.
     * 
     * @param updateUserId Update user id.
     * @param updateDatetime Update date time.
     * @param updateProgramId Update program id.
     * @param lotNumber Lot number from request.
     * @return Update result count.
     */
    int updateBySkulistChildWork(@Param("updateUserId") String updateUserId,
            @Param("updateDatetime") OffsetDateTime updateDatetime,
            @Param("updateProgramId") String updateProgramId, @Param("lotNumber") String lotNumber);

    /**
     * Update all column in table item list detail master.Equals to delete all then insert.
     * 
     * @param updateUserId Update user id.
     * @param updateDatetime Update date time.
     * @param updateProgramId Update program id.
     * @param lotNumber Lot number from request.
     * @return Update result count.
     */
    int updateAllBySkulistChildWork(@Param("updateUserId") String updateUserId,
            @Param("updateDatetime") OffsetDateTime updateDatetime,
            @Param("updateProgramId") String updateProgramId, @Param("lotNumber") String lotNumber);

    /**
     * Delete table item list detail master.
     * 
     * @param lotNumber Lot number from request.
     * @return Delete result count.
     */
    int deleteBySkulistChildWork(@Param("lotNumber") String lotNumber);

}
