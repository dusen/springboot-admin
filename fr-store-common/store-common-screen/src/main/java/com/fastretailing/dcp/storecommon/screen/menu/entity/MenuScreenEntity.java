/**
 * @(#)MenuScreenEntity.java
 *
 *                           Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.menu.entity;

import lombok.Data;

/**
 * The menu item of for hamburger menu.
 *
 */
@Data
public class MenuScreenEntity {

    /** Menu name. */
    private String menuName;
    /** Screen name. */
    private String screenName;
    /** Screen path. */
    private String path;
}
