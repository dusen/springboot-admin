/**
 * @(#)ScreenMasterMapper.java
 *
 *                             Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.authorization.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Class to access master tables related to screen.
 */
@Mapper
public interface ScreenMasterMapper {

    /**
     * Select role accessible to path.
     *
     * @param pathList Target path list.
     * @return Accessible role list.
     */
    List<String> selectRoleAccessibleToPath(@Param("pathList") List<String> pathList);

}
