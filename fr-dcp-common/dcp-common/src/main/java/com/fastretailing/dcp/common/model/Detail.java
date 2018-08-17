/**
 * @(#)Detail.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * error detail info for result object.
 * @author Fast Retailing
 * @version $Revision$
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Detail implements Serializable {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 7303854030023633991L;

    /**
     * validation error field.
     */
    private String field;

    /**
     * validation error field value.
     */
    private String value;

    /**
     * error field issue.
     */
    private String issue;

    /**
     * error issueCode.
     */
    private String issueCode;

    /**
     * constructor for detail with param : issueCode and issue.
     * @param issueCode issueCode
     * @param issue issue
     */
    public Detail(String issueCode, String issue) {
        this.issue = issue;
        this.issueCode = issueCode;
    }
}
