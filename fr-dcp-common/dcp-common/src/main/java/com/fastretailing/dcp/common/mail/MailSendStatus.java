/**
 * @(#)MailSendStatus.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.mail;

/**
 * t_mail_text table's mail_status column's  enumeration class.<br>
 *
 * @author Fast Retailing
 * @version $Revision$
 */
public enum MailSendStatus {

    /**
     * Unsent.<br>
     */
    UNSENT("0"),

    /**
     * Sent Success.<br>
     */
    SUCCESS("1"),

    /**
     * Sent Failure.<br>
     */
    FAILURE("9");

    /**
     * Enumeration's constructor.<br>
     * @param status sent status
     */
    MailSendStatus(String status) {
        this.status = status;
    }

    /**
     * Sent status.<br>
     */
    private String status;

    /**
     * Get the sent status.<br>
     * @return sent status
     */
    public String getStatus() {
        return status;
    }

}
