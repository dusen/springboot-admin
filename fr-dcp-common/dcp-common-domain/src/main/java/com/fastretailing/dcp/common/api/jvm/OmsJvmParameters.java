/**
 * @(#)OmsJvmParameters.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.api.jvm;

import lombok.Getter;

/**
 * OmsJvmParameters.
 * the class to save the jvm options
 *
 * @author Fast Retailing
 * @version $Revision$
 */
public class OmsJvmParameters {

    /**
     * AdminApi.
     */
    @Getter
    static boolean isAdminApi = false;

    /**
     * Country.
     */
    @Getter
    static String country;
}
