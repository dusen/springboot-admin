/**
 * @(#)PayOffDataForm.java
 *
 *                         Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.alterationdataupload.form;


import java.util.ArrayList;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fastretailing.dcp.common.validation.annotation.Alphanumeric;
import com.fastretailing.dcp.common.validation.annotation.StringDate;
import lombok.Data;

/**
 * Form data class to payOff data.
 *
 */
@Data
public final class PayOffDataForm {

    /** Store code. */
    @JsonProperty("store_code")
    @NotBlank
    @Size(max = 10)
    @Alphanumeric
    private String storeCode;

    /** Cash register number. */
    @JsonProperty("cash_register_number")
    @Max(3)
    private int cashRegisterNo;

    /** Payoff date. */
    @JsonProperty("payoff_date")
    @Size(max = 10)
    @StringDate(patterns = "yyyy-MM-dd")
    private String payOffDate;

    /** Data correction user code. */
    @JsonProperty("data_correction_user_code")
    @NotBlank
    @Size(max = 30)
    @Alphanumeric
    private String dataCorrectionUserCode;

    /** Pay off type list. */
    @JsonProperty("payoff_type_list")
    private ArrayList<PayOffType> payOffTypeList;
}
