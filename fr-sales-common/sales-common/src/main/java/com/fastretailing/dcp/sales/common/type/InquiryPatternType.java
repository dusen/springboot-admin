/**
 * @(#)InquiryPatternType.java
 * 
 *                             Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.type;


/**
 * This class is the enumerable class of the inquiry pattern type.
 *
 */
public enum InquiryPatternType {


    /** Value of single. */
    LATEST("1"),

    /** Value of set sale. */
    INITIAL("2"),

    /** Value of payment. */
    BOTH("3");

    /**
     * String representation of the inquiry pattern type.
     */
    private final String inquiryPatternType;

    /**
     * Set the string representation of the the inquiry pattern type.
     * 
     * @param inquiryPatternType Inquiry pattern type.
     */
    private InquiryPatternType(String inquiryPatternType) {
        this.inquiryPatternType = inquiryPatternType;
    }

    /**
     * Enum to string.
     */
    @Override
    public String toString() {
        return inquiryPatternType;
    }

    /**
     * Compares this object to the specified object.
     * 
     * @param inquiryPatternType Inquiry pattern type.
     * @return True if the given object represents a String equivalent to this string, false
     *         otherwise.
     */
    public static boolean compareInquiryPatternType(InquiryPatternType sourceInquiryPatternType,
            final Integer inquiryPatternType) {
        if (inquiryPatternType == null) {
            return false;
        }
        return sourceInquiryPatternType.toString().equals(String.valueOf(inquiryPatternType));
    }
}
