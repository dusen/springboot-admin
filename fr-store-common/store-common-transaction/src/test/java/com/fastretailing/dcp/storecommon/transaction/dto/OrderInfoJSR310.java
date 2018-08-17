/**
 * @(#)OrderInfo.java
 *
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction.dto;


import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import lombok.Data;

/**
 * This class is DTO of order history. The following values are set as the initial value.
 * <UL>
 * <LI>offsetDateTime: null.
 * <LI>zonedDateTime: null.
 * <LI>localDateTime: null.
 * </UL>
 */
@Data
public class OrderInfoJSR310 {

    private OffsetDateTime offsetDateTime;

    private ZonedDateTime zonedDateTime;

    private LocalDateTime localDateTime;
}
