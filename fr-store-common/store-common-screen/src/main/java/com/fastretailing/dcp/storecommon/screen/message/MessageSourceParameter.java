/**
 * @(#)MessageSourceParameter.java
 * 
 *                                 Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.message;

import java.util.Locale;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import lombok.Data;

/**
 * Class that manages request scope locale.
 */
@Component
@RequestScope
@Data
public class MessageSourceParameter {

    /**
     * Message locale.
     */
    private Locale messageLocale;

}
