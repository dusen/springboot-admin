
/**
 * @(#)AlterationPayOffImportData.java
 *
 *                                     Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.importtransaction.dto;

import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Alteration pay off import multi data.
 * 
 */
@Data
public class AlterationPayOffImportMultiData {

    /** Payoff data form list. */
    @JsonProperty("payoff_data_form_list")
    private ArrayList<AlterationPayOffImportData> payoffDataFormList;

}
