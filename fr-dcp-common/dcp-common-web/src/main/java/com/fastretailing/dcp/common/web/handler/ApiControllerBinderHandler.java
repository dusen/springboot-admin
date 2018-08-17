/**
 * @(#)ApiControllerBinderHandler.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.web.handler;

import com.fastretailing.dcp.common.timezone.DateTimePropertyEditor;
import com.fastretailing.dcp.common.timezone.TimePropertyEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * initialize the binder of controller.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@RestControllerAdvice
public class ApiControllerBinderHandler {

    /**
     * add the binder for LocalDateTime and LocalTime.
     * @param binder binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // add the  LocalDateTime and LocalTime to property editor.
        binder.registerCustomEditor(LocalDateTime.class, new DateTimePropertyEditor());
        binder.registerCustomEditor(LocalTime.class, new TimePropertyEditor());
    }
}
