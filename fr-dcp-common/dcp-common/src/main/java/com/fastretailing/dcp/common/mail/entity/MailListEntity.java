/**
 * @(#)MailTextEntity.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.mail.entity;

import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDateTime;

/**
 * t_mail_text table's Entity class.<br>
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Data
public class MailListEntity {

    /**
     * queue_id.
     */
    private BigInteger queueId;

    /**
     * mail_encode.
     */
    private String mailEncode;

    /**
     * mail_host.
     */
    private String mailHost;

    /**
     * smtp_mail_host.
     */
    private String smtpMailHost;

    /**
     * sender_mail_address.
     */
    private String senderMailAddress;

    /**
     * sender_display_name.
     */
    private String senderDisplayName;

    /**
     * destination_to.
     */
    private String destinationTo;

    /**
     * destination_cc.
     */
    private String destinationCc;

    /**
     * destination_bcc.
     */
    private String destinationBcc;

    /**
     * destination_reply_to.
     */
    private String destinationReplyTo;

    /**
     * destination_return_path.
     */
    private String destinationReturnPath;

    /**
     * mail_title.
     */
    private String mailTitle;

    /**
     * mail_text.
     */
    private String mailText;

    /**
     * content_type.
     */
    private String contentType;

    /**
     * send_status.
     */
    private String sendStatus;

    /**
     * send_group.
     */
    private String sendGroup;

    /**
     * retry_count.
     */
    private Integer retryCount;

    /**
     * send_plan_datetime.
     */
    private LocalDateTime sendPlanDatetime;

    /**
     * order_no.
     */
    private String orderNo;

    /**
     * customer_no.
     */
    private String customerNo;

    /**
     * registration_datetime.
     */
    private LocalDateTime registrationDatetime;

    /**
     * registrant_by.
     */
    private String registrantBy;

    /**
     * updated_datetime.
     */
    private LocalDateTime updatedDatetime;

    /**
     * last_updated_by.
     */
    private String lastUpdatedBy;

}
