/**
 * @(#)MenuConfig.java
 *
 *                     Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.config;

import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import com.fastretailing.dcp.storecommon.screen.form.MenuElement;
import lombok.Data;

/**
 * Class for getting menu list from configuration.
 *
 */
@Component
@ConfigurationProperties(prefix = "menuConfig")
@Data
public class MenuConfig {

    /**
     * Menu list.
     */
    private List<MenuElement> menuList = new ArrayList<>();
}
