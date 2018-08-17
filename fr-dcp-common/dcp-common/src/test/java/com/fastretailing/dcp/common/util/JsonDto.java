/**
 * @(#)JsonDto.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.util;

import java.io.Serializable;

import lombok.Data;

/**
 * JsonDto for test.
 * 
 * @author Fast Retailing
 * @version $Revision$
 */
@Data
class JsonDto implements Serializable {

    private static final long serialVersionUID = 543200551447440440L;
    private String testId;
    private String testName;
}
