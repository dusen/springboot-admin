/**
 * @(#)HmacProperties.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.hmac.configuration;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

/**
 * the properties info bean of hmac authentication.
 * @author Fast Retailing
 * @version $Revision$
 */
@Data
public class HmacProperties {

    /**
     * the hmac authentication info map.
     */
    private Map<String, HmacAuthenticationProperties> hmac = new HashMap<>();
}
