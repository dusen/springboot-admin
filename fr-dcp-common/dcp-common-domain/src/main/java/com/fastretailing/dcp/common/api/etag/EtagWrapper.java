/**
 * @(#)EtagWrapper.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.api.etag;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * In controller, <br>
 * you can use this class to include your own return object if you want to return Etag too.<br>
 * Just set Etag to etag and set your own return object to entity.
 * 
 * @author Fast Retailing
 * @version $Revision$
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EtagWrapper<T> {

    /**
     * etag.<br>
     */
    private Etag etag;

    /**
     * Controller's return object.<br>
     */
    private T entity;
}
