/**
 * @(#)ExcelDetailData.java
 * 
 *                          Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.report.dto.excel;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This DTO class to handle Excel details data.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExcelDetailData implements Serializable {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = -2904459986054719450L;

    /**
     * Key Name.
     */
    @JsonProperty("key_name")
    @NotNull
    private String keyName;

    /**
     * Cell Value.
     */
    @JsonProperty("cell_value")
    @NotNull
    private String cellValue;

}
