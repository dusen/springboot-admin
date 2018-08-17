/**
 * @(#)SalesFiguresRequest.java
 *
 *                              Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.resttemplate.client.entity.storeinventory;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Sales figures request entity.
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen",
        date = "2018-03-28T17:43:56.474+09:00")

@Data
public class SalesFiguresRequest {

    /**
     * Store Code.
     */
    @JsonProperty("store_code")
    private String storeCode;

    /**
     * Item list hierarchy code.
     */
    @JsonProperty("item_list_hierarchy_code")
    @NotEmpty
    @Size(max = 2)
    private String itemListHierarchyCode;

    /**
     * Aggregation code list.
     */
    @JsonProperty("aggregation_code_list")
    @Valid
    @NotEmpty
    private List<String> aggregationCodeList;

}
