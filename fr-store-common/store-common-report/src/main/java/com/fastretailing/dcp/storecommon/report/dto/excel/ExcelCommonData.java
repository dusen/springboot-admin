/**
 * @(#)ExcelCommonData.java
 * 
 *                          Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.report.dto.excel;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This DTO class to handle Excel common data.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExcelCommonData implements Serializable {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 8226679587616098127L;

    /**
     * Row Position.
     */
    @JsonProperty("row_position")
    private int rowPosition;

    /**
     * Column Position.
     */
    @JsonProperty("column_position")
    private int columnPosition;

    /**
     * Cell Value.
     */
    @JsonProperty("cell_value")
    private String cellValue;

}
