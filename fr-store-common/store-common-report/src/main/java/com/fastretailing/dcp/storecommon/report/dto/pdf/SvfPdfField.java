/**
 * @(#)SvfPdfField.java
 * 
 *                      Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.report.dto.pdf;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This DTO class to handle SVF PDF field.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SvfPdfField implements Serializable {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = -8345241839536371502L;

    /**
     * Field name.
     */
    @JsonProperty("field_name")
    @NotNull
    private String fieldName;

    /**
     * Field value.
     */
    @JsonProperty("field_value")
    @NotNull
    private String fieldValue;

}
