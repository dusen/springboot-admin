/**
 * @(#)MailRepository.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.mail.repository;

import com.fastretailing.dcp.common.mail.entity.MailTemplateEntity;
import com.fastretailing.dcp.common.mail.entity.MailListEntity;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Mail Template's Repository interface.<br>
 *
 * @author Fast Retailing
 * @version $Revision$
 */
public interface MailRepository {

    /**
     * Find mail template.<br>
     * @param brandCode brandCode
     * @param regionCode regionCode
     * @param languageCode languageCode
     * @param mailType mailType
     * @param paymentType paymentType
     * @param deliveryType deliveryType
     * @param receiveType receiveType
     * @param schemaName business database's schema name
     * @return template's entity
     */
    MailTemplateEntity findTemplate(
            @Param("brandCode") String brandCode,
            @Param("regionCode") String regionCode,
            @Param("languageCode") String languageCode,
            @Param("mailType") String mailType,
            @Param("paymentType") String paymentType,
            @Param("deliveryType") String deliveryType,
            @Param("receiveType") String receiveType,
            @Param("schemaName") String schemaName
    );

    /**
     * Find wait to send mail's records.<br>
     * @param sendStatus mail's status
     * @param sendGroup mail's group
     * @param sendPlanDatetime mail's send at
     * @param maxLimit record's max limit
     * @param schemaName business database's schema name
     * @return wait to send mail's records.
     */
    List<MailListEntity> findMailsFromMailPool(
            @Param("sendStatus") String sendStatus,
            @Param("sendGroup") String sendGroup,
            @Param("sendPlanDatetime") LocalDateTime sendPlanDatetime,
            @Param("maxLimit") int maxLimit,
            @Param("schemaName") String schemaName
    );

    /**
     * Create the mail that wait for send.<br>
     * @param input mail's information
     * @param schemaName business database's schema name
     * @return inserted count
     */
    int saveMailToMailPool(@Param("input") MailListEntity input,
                           @Param("schemaName") String schemaName);

    /**
     * Update the mail's information.<br>
     * @param input t_mail_text table's Entity
     * @param schemaName business database's schema name
     * @return updated count
     */
    int updateMailStatus(@Param("input") MailListEntity input,
                         @Param("schemaName") String schemaName);

}
