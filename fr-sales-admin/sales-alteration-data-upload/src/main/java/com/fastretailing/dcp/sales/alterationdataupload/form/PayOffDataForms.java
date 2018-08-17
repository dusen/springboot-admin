/**
 * @(#)PayOffDataForms.java
 *
 *                          Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.alterationdataupload.form;


import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Form data class to pay off data.
 *
 */
@Data
public final class PayOffDataForms {

    /** Pay off data form list. */
    @JsonProperty("payoff_data_form_list")
    private ArrayList<PayOffDataForm> payoffDataFormList;

}
