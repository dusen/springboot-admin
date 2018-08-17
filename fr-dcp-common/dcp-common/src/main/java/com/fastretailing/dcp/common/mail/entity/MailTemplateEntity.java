/**
 * @(#)MailTemplateEntity.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.mail.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * t_mail_template table's Entity class.<br>
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Data
public class MailTemplateEntity {

    /**
     * mail_template_id.
     */
    private String mailTemplateId;

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
     * content_type.
     */
    private String contentType;

    /**
     * mail_title.
     */
    private String mailTitle;

    /**
     * text_template.
     */
    private String textTemplate;

    /**
     * send_group.
     */
    private String sendGroup;

    /**
     * send_shift_minute.
     */
    private int sendShiftMinute;

    /**
     * template_explanation.
     */
    private String templateExplanation;

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
