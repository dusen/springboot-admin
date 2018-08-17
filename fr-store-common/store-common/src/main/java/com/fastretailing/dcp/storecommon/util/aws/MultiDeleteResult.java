/**
 * @(#)MultiDeleteResult.java
 *
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.util.aws;

import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * AWS S3 file information result class.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Data
@AllArgsConstructor
public class MultiDeleteResult {

    /** [key, messeage] set of error objects. */
    private Map<String, String> deleteErrors;
    /** deleted keys. */
    private List<String> deletedKeys;
}
