/**
 * @(#)Countries.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.annotation;

/**
 * define the enum for country's name.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
public enum Countries {

    /**
     * canada.
     */
    CANADA("canada"),

    /**
     * japan.
     */
    JAPAN("japan");

    /**
     * The field for hold country's name.
     */
    String country;

    /**
     * Constructor.
     * @param country country's name
     */
    Countries(String country) {
        this.country = country;
    }

    /**
     * Get the value of country.
     * @return country's name
     */
    public String getCountry() {
        return country;
    }
}
