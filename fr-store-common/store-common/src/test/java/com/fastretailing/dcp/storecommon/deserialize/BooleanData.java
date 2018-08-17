/**
 * @(#)BooleanData.java
 *
 *                      Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.deserialize;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

/**
 * DTO class with Boolean data for testing.
 */
@Data
@JsonIgnoreProperties
public class BooleanData {

    /**
     * A variable that stores the Boolean conversion value of the string. "1" is set to true, "0" is
     * set to false.
     */
    @JsonDeserialize(using = OneZeroToBooleanDeserializer.class)
    private Boolean oneZero;

    /**
     * A variable that stores the Boolean conversion value of the string. "T" is set to true, "F" is
     * set to false.
     */
    @JsonDeserialize(using = TrueFalseToBooleanDeserializer.class)
    private Boolean trueFalse;

    /**
     * A variable that stores the Boolean conversion value of the string. "Y" is set to true, "N" is
     * set to false.
     */
    @JsonDeserialize(using = YesNoToBooleanDeserializer.class)
    private Boolean yesNo;
}
