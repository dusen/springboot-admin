/**
 * @(#)Etag.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.api.etag;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.MissingServletRequestParameterException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *     Etag entity.Will be used for store etag value.<br>
 *     In controller's parameters, it will be set the value of request header If-Match.<br>
 *     In controller's return object, the up-to-date value of the resource needs be set.
 * </pre>
 * 
 * @author Fast Retailing
 * @version $Revision$
 */
@Getter
@Setter
public class Etag {

    /**
     * The length of the etag array.
     */
    private static final int LENGTH = 2;
    
    /**
     * All platform's etag will be saved in this map.
     */
    private Map<String, String> etagMap;

    /**
     * constructor without parameter.
     */
    public Etag() {
    }

    /**
     * constructor with etag string from request header.<br>
     * You can see the sample format below.<br>
     * Sample:  basket=56bde027c12f5707e7d00ef68e43710c|inventory=56bde027c12f5707e7d00ef68e43710c
     * @param etags etag string
     */
    public Etag(String etags) throws Exception {
        this.etagMap = splitEtags(etags);
    }

    /**
     * constructor with etag hash map.<br>
     * You can see the sample format below.<br>
     * Sample: <br>
     * {<"basket":56bde027c12f5707e7d00ef68e43710c>,<"inventory":56bde027c12f5707e7d00ef68e43710c>}
     *
     * @param etagMap etag's hash map
     */
    public Etag(Map<String, String> etagMap) {
        this.etagMap = etagMap;
    }

    /**
     * reduce all etag to hash map.
     * @param acc processor for reducer
     * @param e etag item
     * @return result of reducer
     */
    private static Map<String, String> apply(Map<String, String> acc, String e) {
        String[] splitEtag = e.split("=");
        if (splitEtag.length == LENGTH) {
            acc.put(splitEtag[0], splitEtag[1]);
        }
        return acc;
    }

    /**
     * Split Etags to hash map.
     * @param etags all etags
     * @return split etags in hash map.
     * @throws Exception Exception
     */
    private Map<String, String> splitEtags(String etags) throws Exception {

        Map<String, String> result;

        try {
            result = Arrays.stream(etags.split("\\|")).reduce(new HashMap<>(),
                    Etag::apply,
                    (a, b) -> {
                        a.putAll(b);
                        return a;
                    });
        } catch (Exception e) {
            result = null;
        }

        // If can not create instance via constructor's parameter,
        // will throw exception to stop request processor.
        if (result == null || (etags.split("\\|").length != result.size())) {
            throw new MissingServletRequestParameterException("If-Match", "Etag");
        }

        return result;
    }

    /**
     * Convert etag instances to string.<br>
     * You can see the sample format below.<br>
     * Sample:basket=56bde027c12f5707e7d00ef68e43710c|inventory=56bde027c12f5707e7d00ef68e43710c
     * @return result string
     */
    public String toString() {
        if (etagMap == null) {
            return "";
        } else {
            return etagMap.entrySet().stream()
                    .map(e -> e.getKey() + "=" + e.getValue())
                    .reduce((acc, item) -> acc + "|" + item)
                    .orElse("");
        }
    }

}
