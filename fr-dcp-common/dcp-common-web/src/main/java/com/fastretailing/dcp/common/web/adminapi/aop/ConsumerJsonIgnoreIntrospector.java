/**
 * @(#)ConsumerJsonIgnoreIntrospector.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.web.adminapi.aop;

import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fastretailing.dcp.annotation.ConsumerJsonIgnore;
import com.fastretailing.dcp.common.api.jvm.OmsJvmParameters;

/**
 * The annotation ConsumerJsonIgnore check introspector.<br>
 *
 * @author Fast Retailing
 * @version $Revision$
 */
public class ConsumerJsonIgnoreIntrospector extends JacksonAnnotationIntrospector {

    /**
     * Check the ignore marker.
     * @param annotatedMember annotatedMember Introspector
     * @return check result
     */
    @Override
    public boolean hasIgnoreMarker(AnnotatedMember annotatedMember) {
        boolean isIgnore = super.hasIgnoreMarker(annotatedMember);
        if (isIgnore) {
            return  true;
        }
        boolean isAdminApi = OmsJvmParameters.isAdminApi();
        if (isAdminApi) {
            return false;
        }
        ConsumerJsonIgnore consumerJsonIgnore =
                _findAnnotation(annotatedMember, ConsumerJsonIgnore.class);
        return (consumerJsonIgnore != null);
    }

}
