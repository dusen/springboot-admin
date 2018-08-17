/**
 * @(#)UriInformation.java
 *
 *                         Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.util;

import lombok.Data;

/**
 * URI information.
 */
@Data
public class UriInformation {

    /**
     * The base path of the URI excluding queries and fragments.
     */
    private String basePath;

    /**
     * Query part of URI.
     */
    private String query;

    /**
     * Fragment part of URI.
     */
    private String fragment;

}
