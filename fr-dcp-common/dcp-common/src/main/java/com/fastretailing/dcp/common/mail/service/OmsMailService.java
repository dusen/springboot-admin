/**
 * @(#)OmsMailService.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.mail.service;

import com.fastretailing.dcp.common.mail.entity.MailTemplateEntity;
import com.fastretailing.dcp.common.mail.entity.MailListEntity;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Mail service interface.<br>
 *
 * @author Fast Retailing
 * @version $Revision$
 */
public interface OmsMailService {

    /**
     * Find mail template by template's id.<br>
     * @param brandCode brandCode
     * @param regionCode regionCode
     * @param languageCode languageCode
     * @param mailType mailType
     * @param paymentType paymentType
     * @param deliveryType deliveryType
     * @param receiveType receiveType
     * @return Template's Entity
     */
    MailTemplateEntity findTemplate(
            String brandCode, String regionCode, String languageCode, String mailType,
            String paymentType, String deliveryType, String receiveType);

    /**
     * Find wait to send mail's records.<br>
     * @param mailStatus mail's status
     * @param group mail's group
     * @param scheduledSendingAt mail's scheduled sent datetime
     * @param  maxLimit record's max limit
     * @return wait to send mail's records.
     */
    List<MailListEntity> findMailsFromMailPool(
            String mailStatus, String group, LocalDateTime scheduledSendingAt, int maxLimit);

    /**
     * Create the mail that wait for send.<br>
     * @param entity mail's information
     */
    int saveMailToMailPool(MailListEntity entity);

    /**
     * Get the mail's body that replace template's variates with Replace-Model Object.<br>
     * @param brandCode brandCode
     * @param regionCode regionCode
     * @param languageCode languageCode
     * @param mailType mailType
     * @param paymentType paymentType
     * @param deliveryType deliveryType
     * @param receiveType receiveType
     * @param replaceModel replaceModel
     * @return mail's body
     * @throws Exception Exception
     */
    String getMailText(
            String brandCode, String regionCode, String languageCode, String mailType,
            String paymentType, String deliveryType, String receiveType,
                       Object replaceModel) throws Exception;

    /**
     * Update the mail's information.<br>
     * @param queueId mail's id
     * @param status mail's status
     * @param actualSentAt mail's actual sent datetime
     * @param operateDateTime update datetime
     */
    int updateMailStatus(BigInteger queueId, String status,
                         LocalDateTime actualSentAt, LocalDateTime operateDateTime);

    /**
     * Send the mail.<br>
     * @param mail mail information
     * @throws Exception Exception
     */
    void sendMail(MailListEntity mail) throws Exception;



}
