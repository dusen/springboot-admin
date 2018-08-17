/**
 * @(#)MenuScreenMasterMapper.java
 *
 *                                 Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.menu.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.fastretailing.dcp.storecommon.screen.menu.entity.MenuScreenEntity;

/**
 * Class to access master tables related to screen.
 */
@Mapper
public interface MenuScreenMasterMapper {

    /**
     * Select accessible screen list.
     *
     * @param menuName Target menu name.
     * @param roles Role list.
     * @return Accessible screen list.
     */
    List<MenuScreenEntity> selectScreenListByRole(@Param("menuName") String menuName,
            @Param("roles") List<String> roles);
}
