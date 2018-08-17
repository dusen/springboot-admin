/**
 * @(#)VersionInfo.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.web.controller;

import lombok.Data;

import java.util.Map;

/**
 * VersionInfo.<br>
 *     the version information of oms package <br>
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Data
public class VersionInfo {

    /**
     * version info map.
     */
    private Map<String, String> version;
}
