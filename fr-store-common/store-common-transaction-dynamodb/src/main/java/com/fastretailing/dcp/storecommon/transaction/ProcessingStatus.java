/**
 * @(#)ProcessingStatus.java
 *
 *                           Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Status for status table.
 */
public enum ProcessingStatus {

    /** Status for processing. */
    PROCESSING("processing"),

    /** Status unnecessary for recovery. */
    DO_NOT_RECOVER("doNotRecover"),

    /** Status for completion. */
    COMPLETION("completion"),

    /** Status for error. */
    ERROR("error");

    /** String representation list of completion statuses. */
    private static List<String> completionStatusStringList;

    /**
     * Static initializer.
     */
    static {
        completionStatusStringList = new ArrayList<>();
        completionStatusStringList.add(COMPLETION.toString());
        completionStatusStringList.add(ERROR.toString());
    }

    /** Transaction process status. */
    private final String processingStatus;

    /**
     * Constructor that sets a character string defined by Enum to a field.
     * 
     * @param processingStatus Transaction processing status.
     */
    private ProcessingStatus(String processingStatus) {
        this.processingStatus = processingStatus;
    }

    /**
     * Returns the character string set in the field.
     * 
     * @return Transaction processing status set in the field.
     */
    @Override
    public String toString() {
        return this.processingStatus;
    }

    /**
     * Confirms that completion status is included in the target status list.
     * 
     * @param targetStatusList Target status list.
     * @return Returns true if completion status is included, false otherwise. It returns false if
     *         the target status list is null.
     */
    public static boolean containsCompletionStatus(List<String> targetStatusList) {
        if (targetStatusList == null) {
            return false;
        } else {
            return completionStatusStringList.stream()
                    .anyMatch(completionStatus -> targetStatusList.contains(completionStatus));
        }
    }

    /**
     * Confirms if the target status is this status.
     * 
     * @param targetStatus Target status.
     * @return Returns true on this status, false otherwise.
     */
    public boolean equals(String targetStatus) {
        return this.processingStatus.equals(targetStatus);
    }
}
