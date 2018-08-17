/**
 * @(#)MD5Utility.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.util.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Compute the MD5 hash code.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Slf4j
public class MD5Utility {

    /**
     * Compute the MD5 hash code of the specified string.
     * 
     * @param input The string to be hash computed
     * @return Encoded MD5
     */
    public static String md5(String input) {
        String result = "";
        if (StringUtils.isEmpty(input)) {
            return result;
        } else {
            MessageDigest md5 = null;
            try {
                md5 = MessageDigest.getInstance("MD5");
                byte[] bytes = md5.digest(input.getBytes());
                result = MD5Encoder.encode(bytes);
            } catch (NoSuchAlgorithmException e) {
                // MD5 is specified, so it cannot happen
                log.error("Please check The name of the algorithm", e);
            }
        }
        return result;
    }
}
