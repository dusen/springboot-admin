/**
 * @(#)MenuElement.java
 *
 *                      Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.form;

import org.springframework.stereotype.Component;
import lombok.Data;

/**
 * The menu item of for hamburger menu.
 *
 */
@Component
@Data
public class MenuElement {

    /** Menu id. */
    private String menuId;
    /** Transition destination url. */
    private String url;
}
