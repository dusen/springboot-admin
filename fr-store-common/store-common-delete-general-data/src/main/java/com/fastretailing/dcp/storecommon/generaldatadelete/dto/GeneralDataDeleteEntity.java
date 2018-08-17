/**
 * @(#)GeneralDataDeleteEntity.java
 *
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.generaldatadelete.dto;

import java.time.OffsetDateTime;
import lombok.Data;


/**
 * General data delete entity.
 */
@Data
public final class GeneralDataDeleteEntity {

    /** Processing target type. */
    private String processingTargetType;

    /** Processing number. */
    private Long processingNo;

    /** Delete target table. */
    private String deleteTargetTable;

    /** Saved days. */
    private Long savedDays;

    /** Date item. */
    private String dateItem;

    /** System brand code. */
    private String systemBrandCode;

    /** System country code. */
    private String systemCountryCode;

    /** Store code. */
    private String storeCode;

    /** Create user id. */
    private String createUserId;

    /** Create date time. */
    private OffsetDateTime createDatetime;

    /** Create program id. */
    private String createProgramId;

    /** Update user id. */
    private String updateUserId;

    /** Update date time. */
    private OffsetDateTime updateDatetime;

    /** Update program id. */
    private String updateProgramId;


}
