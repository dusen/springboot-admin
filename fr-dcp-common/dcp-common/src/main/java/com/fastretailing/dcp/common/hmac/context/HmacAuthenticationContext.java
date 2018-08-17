/**
 * @(#)HmacAuthenticationContext.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.hmac.context;

import lombok.Data;

/**
 * HmacAuthentication's context bean.<br>
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Data
public class HmacAuthenticationContext {

    /**
     * the platform name of hmac authentication.
     */
    private String platformName;
}
