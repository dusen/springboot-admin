/**
 * @(#)ImportNonItemInfo.java
 *
 *                            Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salestransactioncorrectionhistorydetail.form.transactionomport;

import javax.validation.constraints.Max;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Import non item info.
 * 
 */
@Data
public class ImportNonItemInfo {
    /** Detail sub number. */
    @JsonProperty("detail_sub_number")
    @Max(9999)
    private Integer detailSubNumber;

    /** Item detail sub number. */
    @JsonProperty("item_detail_sub_number")
    @Max(9999)
    private Integer itemDetailSubNumber;

    /** Key code. */
    @JsonProperty("key_code")
    @Size(max = 20)
    @Pattern(regexp = "[a-zA-Z0-9-_]*")
    private String keyCode;

    /** Code 1. */
    @JsonProperty("code_value_1")
    @Size(max = 25)
    @Pattern(regexp = "[a-zA-Z0-9-_]*")
    private String codeValue1;

    /** Code 2. */
    @JsonProperty("code_value_2")
    @Size(max = 25)
    @Pattern(regexp = "[a-zA-Z0-9-_]*")
    private String codeValue2;

    /** Code 3. */
    @JsonProperty("code_value_3")
    @Size(max = 25)
    @Pattern(regexp = "[a-zA-Z0-9-_]*")
    private String codeValue3;

    /** Code 4. */
    @JsonProperty("code_value_4")
    @Size(max = 25)
    @Pattern(regexp = "[a-zA-Z0-9-_]*")
    private String codeValue4;

    /** Name 1. */
    @JsonProperty("name_1")
    @Size(max = 250)
    private String name1;

    /** Name 2. */
    @JsonProperty("name_2")
    @Size(max = 250)
    private String name2;

    /** Name ３. */
    @JsonProperty("name_3")
    @Size(max = 250)
    private String name3;

    /** Name 4. */
    @JsonProperty("name_4")
    @Size(max = 250)
    private String name4;

}
