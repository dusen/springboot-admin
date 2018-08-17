/**
 * @(#)HmacAuthenticationProperties.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.hmac.configuration;

import lombok.Data;

/**
 * the properties info bean of hmac authentication.
 * @author Fast Retailing
 * @version $Revision$
 */
@Data
public class HmacAuthenticationProperties {

    /**
     * client id for hmac authentication.
     */
    private String clientId;

    /**
     * client secret for hmac authentication.
     */
    private String clientSecret;
}
