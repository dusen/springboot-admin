/**
 * @(#)ExcelDetailDefine.java
 * 
 *                            Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.report.dto.excel;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This DTO class to handle Excel detail define.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExcelDetailDefine implements Serializable {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 3838624790469794431L;

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
     * Key Name.
     */
    @JsonProperty("key_name")
    private String keyName;

}
