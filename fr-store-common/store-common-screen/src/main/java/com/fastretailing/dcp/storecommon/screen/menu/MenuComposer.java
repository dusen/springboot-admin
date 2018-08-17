/**
 * @(#)MenuComposer.java
 *
 *                       Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fastretailing.dcp.storecommon.screen.authentication.AuthenticationUtil;
import com.fastretailing.dcp.storecommon.screen.authentication.entity.UserDetails;
import com.fastretailing.dcp.storecommon.screen.form.MenuElement;
import com.fastretailing.dcp.storecommon.screen.menu.entity.MenuScreenEntity;
import com.fastretailing.dcp.storecommon.screen.menu.repository.MenuScreenMasterMapper;

/**
 * Menu utility class.
 */
@Component
@MapperScan({"com.fastretailing.dcp.storecommon.screen.menu.repository"})
public class MenuComposer {

    /** Request parameter for getting menu name. */
    public static final String MENU_NAME_REQUEST_PARAMETER = "menuName";

    /** Property key suffix of screen title. */
    private static final String SCREEN_TITLE_PROPERTY_KEY = "title";

    /** Property delimiter. */
    private static final String PROPERTY_KEY_DELIMITER = ".";

    /** Mapper class for menu screen master. */
    @Autowired
    private MenuScreenMasterMapper menuScreenMasterMapper;

    /**
     * Get accessible menu list.
     * 
     * @param menuNmae Parent menu name.
     * @return Menu list.
     */
    public List<MenuElement> getMenuList(String menuName) {

        List<MenuElement> menuList = new ArrayList<>();

        if (StringUtils.isEmpty(menuName)) {
            return menuList;
        }

        UserDetails userDetails = AuthenticationUtil.getUserDetails();

        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .collect(Collectors.toList());

        List<MenuScreenEntity> menuEntityList =
                menuScreenMasterMapper.selectScreenListByRole(menuName, roles);

        if (CollectionUtils.isNotEmpty(menuEntityList)) {
            menuList = menuEntityList.stream().map(menu -> {
                MenuElement menuElement = new MenuElement();
                String screenLocaleTitle = String.join(PROPERTY_KEY_DELIMITER, menu.getScreenName(),
                        SCREEN_TITLE_PROPERTY_KEY);
                menuElement.setMenuId(screenLocaleTitle);
                menuElement.setUrl(menu.getPath());
                return menuElement;
            }).collect(Collectors.toList());
        }

        return menuList;
    }
}
