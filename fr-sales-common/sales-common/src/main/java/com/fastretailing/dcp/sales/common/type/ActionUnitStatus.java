/**
 * ActionUnitStatus.java
 *
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.type;

import lombok.Getter;

/**
 * This class is the enumerable class of the action unit status.
 */
public enum ActionUnitStatus {

    /** total. */
    TOTAL(0),
    /** individual. */
    INDIVIDUAL(1);

    /**
     * String representation of the action unit.
     */
    @Getter
    private Integer value;

    /**
     * Sets the string representation of the action unit.
     * 
     * @param value Action unit.
     */
    private ActionUnitStatus(Integer value) {
        this.value = value;
    }

    public static boolean compare(ActionUnitStatus actionUnitStatus, Integer integerValue) {
        return actionUnitStatus.getValue().equals(integerValue);
    }

}
