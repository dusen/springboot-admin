/**
 * @(#)NonItemInformation.java
 * 
 *                             Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Non item information.
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen",
        date = "2018-05-16T10:34:00.975+09:00")

@Data
public class NonItemInformation {
    @JsonProperty("detail_sub_number")
    private Integer detailSubNumber = null;

    @JsonProperty("item_detail_sub_number")
    private Integer itemDetailSubNumber = null;

    @JsonProperty("key_code")
    private String keyCode = null;

    @JsonProperty("code_value_1")
    private String codeValue1 = null;

    @JsonProperty("code_value_2")
    private String codeValue2 = null;

    @JsonProperty("code_value_3")
    private String codeValue3 = null;

    @JsonProperty("code_value_4")
    private String codeValue4 = null;

    @JsonProperty("name_1")
    private String name1 = null;

    @JsonProperty("name_2")
    private String name2 = null;

    @JsonProperty("name_3")
    private String name3 = null;

    @JsonProperty("name_4")
    private String name4 = null;

}

