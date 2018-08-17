/**
 * @(#)ExcelCellFormatting.java
 * 
 *                              Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.report.dto.excel;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * This DTO class to handle Excel cell formatting.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Data
public class ExcelCellFormatting implements Serializable {

    /**
     * Serial version uid.
     */
    private static final long serialVersionUID = -5632363528285457486L;

    /**
     * Search Target Column.
     */
    @JsonProperty("search_target_column")
    private int searchTargetColumn;

    /**
     * Search Condition.
     */
    @JsonProperty("search_condition_word")
    private String searchConditionWord;

    /**
     * Compare Method.
     */
    @JsonProperty("compare_method")
    private String compareMethod;

    /**
     * Rows Height.
     */
    @JsonProperty("rows_height")
    private float rowsHeight;

    /**
     * Target Modify Cells Column.
     */
    @JsonProperty("modify_target_column")
    private int modifyTargetColumn;

    /**
     * Font color.
     */
    @JsonProperty("font_color")
    private short fontColor;

    /**
     * Font size.
     */
    @JsonProperty("font_size")
    private short fontSize;

    /**
     * Background color.
     */
    @JsonProperty("background_color")
    private short backgroundColor;

    /**
     * Hatching.
     */
    @JsonProperty("hatching")
    private short hatching;

    /**
     * Hatching color. @JsonProperty("hatching_color")
     */
    private short hatchingColor;

}
