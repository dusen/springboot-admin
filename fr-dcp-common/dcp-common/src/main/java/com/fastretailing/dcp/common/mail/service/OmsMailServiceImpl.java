/**
 * @(#)OmsMailServiceImpl.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.mail.service;

import com.fastretailing.dcp.common.mail.MailSendStatus;
import com.fastretailing.dcp.common.mail.entity.MailListEntity;
import com.fastretailing.dcp.common.mail.entity.MailTemplateEntity;
import com.fastretailing.dcp.common.mail.repository.MailRepository;
import com.fastretailing.dcp.common.mail.util.MailUtility;
import com.fastretailing.dcp.common.util.CommonUtility;
import com.sun.mail.smtp.SMTPMessage;
import com.sun.mail.util.MailSSLSocketFactory;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Template;
/**
 * Mail service interface.<br>
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Slf4j
@Service
public class OmsMailServiceImpl implements OmsMailService {

    /**
     * FreeMarker's configuration instance.<br>
     */
    @Autowired
    private FreeMarkerConfigurer configurer;

    /**
     * CommonUtility instance.<br>
     */
    @Autowired
    private CommonUtility utility;

    /**
     * Mail information repository.<br>
     */
    @Autowired
    private MailRepository repository;

    /**
     * Mail's user.<br>
     */
    @Value("${oms.mail.user:#{null}}")
    private String mailUser;

    /**
     * Mail's password.<br>
     */
    @Value("${oms.mail.password:#{null}}")
    private String mailPassword;

    /**
     * Mail's host.<br>
     */
    @Value("${mail.smtp.host:#{null}}")
    private String mailSmtpHost;

    /**
     * Mail's port.<br>
     */
    @Value("${mail.smtp.port:25}")
    private Integer mailSmtpPort;

    /**
     * Mail's debug mode flag.<br>
     */
    @Value("${oms.mail.debug:#{null}}")
    private String mailServiceDebugEnabled;

    /**
     * Mail smtp authentication flag.<br>
     */
    @Value("${mail.smtp.auth:#{null}}")
    private String mailSmtpAuth;

    /**
     * Mail smtp tls's enable flag.<br>
     */
    @Value("${mail.smtp.starttls.enable:#{null}}")
    private String mailSmtpStarttlsEnable;

    /**
     * Mail smtp tls's required flag.<br>
     */
    @Value("${mail.smtp.starttls.required:#{null}}")
    private String mailSmtpStarttlsRequired;

    /**
     * trusted hosts of ssl connection.Default setting is trust all hosts.<br>
     */
    @Value("${mail.smtp.ssl.trust:*}")
    private String mailSmtpSslTrust;

    /**
     * SSL verify flag. Default value is true.<br>
     * If you want disable ssl verify in test environment,<br>
     * Please set it to false.
     */
    @Value("${mail.smtp.use.ssl.verify:true}")
    private boolean useSslVerify;

    /**
     * Global mail environment's session.<br>
     */
    private Session globalSession;

    /**
     * Global mail environment's transport.<br>
     */
    private Transport globalTransport;

    /**
     * Business database's schema name.<br>
     */
    @Value("${business.database.schema.name.with.dot:#{null}}")
    private String schemaName;

    /**
     * Find mail template.<br>
     * @param brandCode brandCode
     * @param regionCode regionCode (not required)
     * @param languageCode languageCode
     * @param mailType mailType
     * @param paymentType paymentType
     * @param deliveryType deliveryType
     * @param receiveType receiveType
     * @return Template's Entity
     */
    public MailTemplateEntity findTemplate(
            String brandCode, String regionCode, String languageCode, String mailType,
            String paymentType, String deliveryType, String receiveType) {

        return repository.findTemplate(
                brandCode, regionCode, languageCode, mailType, paymentType, deliveryType, receiveType, schemaName
        );
    }

    /**
     * Find wait to send mail's records.<br>
     * @param sendStatus mail's sendStatus
     * @param group mail's group
     * @param sendPlanDatetime mail's scheduled sent datetime
     * @param  maxLimit record's max limit
     * @return wait to send mail's records.
     */
    @Override
    public List<MailListEntity> findMailsFromMailPool(
            String sendStatus, String group, LocalDateTime sendPlanDatetime, int maxLimit) {
        return repository.findMailsFromMailPool(sendStatus, group, sendPlanDatetime, maxLimit, schemaName);
    }

    /**
     * Create the mail that wait for send.<br>
     * @param entity mail's information
     * @return inserted count
     */
    @Override
    public int saveMailToMailPool(MailListEntity entity) {
        return repository.saveMailToMailPool(entity, schemaName);
    }

    /**
     * Get the mail's body that replace template's variates with Replace-Model Object.<br>
     * @param brandCode brandCode
     * @param regionCode regionCode
     * @param languageCode languageCode
     * @param mailType mailType
     * @param paymentType paymentType
     * @param deliveryType deliveryType
     * @param receiveType receiveType
     * @param replaceModel Replace-Model Object (Map or Entity)
     * @return mail's body
     * @throws Exception Exception
     */
    @Override
    public String getMailText(
            String brandCode, String regionCode, String languageCode, String mailType,
            String paymentType, String deliveryType, String receiveType, Object replaceModel) throws Exception {
        Template template = configurer.getConfiguration().getTemplate(
                MailUtility.buildTemplateKey(brandCode, regionCode, languageCode, mailType, paymentType, deliveryType, receiveType)
        );
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, replaceModel);
    }

    /**
     * Update the mail's information.<br>
     * @param queueId mail's id
     * @param sendStatus mail's sendStatus
     * @param actualSentAt mail's actual sent datetime
     * @return updated count
     * @param operateDateTime update datetime
     */
    @Override
    public int updateMailStatus(
            BigInteger queueId, String sendStatus,
            LocalDateTime actualSentAt, LocalDateTime operateDateTime) {

        MailListEntity sqlInput = new MailListEntity();
        sqlInput.setQueueId(queueId);
        sqlInput.setSendStatus(sendStatus);
        if (actualSentAt != null && MailSendStatus.SUCCESS.getStatus().equals(sendStatus)) {
            sqlInput.setSendPlanDatetime(actualSentAt);
        }
        sqlInput.setUpdatedDatetime(operateDateTime);
        return repository.updateMailStatus(sqlInput, schemaName);
    }

    /**
     * Send the mail.<br>
     * @param mail mail information
     * @throws Exception Exception
     */
    @Override
    public void sendMail(MailListEntity mail) throws Exception {

        // total = send + retry
        int total = 1 + mail.getRetryCount();
        for (int current = 0; current < total; current++) {
            try {
                this.send(mail);
                break;
            } catch (MessagingException e) {
                if (log.isDebugEnabled()) {
                    log.debug(
                            "Execute send mail failure. MailID: {}, Current/Total: {}/{}",
                            mail.getQueueId(), current + 1, total
                    );
                    log.debug("{}", e.getMessage());
                }
                if (current == total - 1) {
                    throw e;
                }
            } catch (Exception e) {
                if (log.isDebugEnabled()) {
                    log.debug(
                            "Unrecoverable Errors! MailID: {}, {}.",
                            mail.getQueueId(), mail
                    );
                    log.debug("{}", e.getMessage());
                }
                throw e;
            }
        }

    }

    /**
     * Send mail.<br>
     * @param mail mail information
     * @throws MessagingException MessagingException
     * @throws UnsupportedEncodingException Unrecoverable Errors
     */
    private void send(MailListEntity mail) throws MessagingException, UnsupportedEncodingException, GeneralSecurityException {
        this.initGlobalsMailEnvironment(mail);
        MimeMessage message = this.createMimeMessage(mail, globalSession);
        globalTransport.sendMessage(message, message.getAllRecipients());
    }

    /**
     * Initialize mail's environment.
     * @param mail mail information
     * @throws MessagingException MessagingException
     * @throws GeneralSecurityException GeneralSecurityException
     */
    private void initGlobalsMailEnvironment(MailListEntity mail) throws MessagingException, GeneralSecurityException {
        if (globalTransport == null) {
            if (log.isDebugEnabled()) {
                log.debug("[Mail Environment] Transport will be initialize.");
            }

            Properties props = new Properties();

            // If useSslVerify is false(disable ssl verify), trust all host.
            if (!useSslVerify) {
                MailSSLSocketFactory sf = new MailSSLSocketFactory();
                sf.setTrustAllHosts(true);
                props.put("mail.smtp.ssl.trust", "*");
                props.put("mail.smtp.ssl.socketFactory", sf);
            }

            props.put("mail.host", mail.getMailHost());

            if (StringUtils.isBlank(mailSmtpHost)) {
                mailSmtpHost = mail.getSmtpMailHost();
            }
            if (StringUtils.isNotBlank(mailServiceDebugEnabled)) {
                props.put("mail.debug", mailServiceDebugEnabled);
            }
            if (StringUtils.isNotBlank(mailSmtpAuth)) {
                props.put("mail.smtp.auth", mailSmtpAuth);
            }
            if (StringUtils.isNotBlank(mailSmtpStarttlsEnable)) {
                props.put("mail.smtp.starttls.enable", mailSmtpStarttlsEnable);
            }
            if (StringUtils.isNotBlank(mailSmtpStarttlsRequired)) {
                props.put("mail.smtp.starttls.required", mailSmtpStarttlsRequired);
            }
            globalSession = Session.getInstance(props);
            globalTransport = globalSession.getTransport("smtp");
            globalTransport.connect(mailSmtpHost, mailSmtpPort, mailUser, mailPassword);
        } else {
            if (!globalTransport.isConnected()) {
                if (log.isDebugEnabled()) {
                    log.debug("[Mail Environment] Transport will be reconnect.");
                }
                globalTransport.connect(mailSmtpHost, mailSmtpPort, mailUser, mailPassword);
            }
        }
    }

    /**
     * Create and fill MimeMessage with database's mail's information.<br>
     * @param mail database's mail's information.
     * @param session mail's Session
     * @return MimeMessage instance
     * @throws MessagingException MessagingException
     * @throws UnsupportedEncodingException Unrecoverable Errors
     */
    private MimeMessage createMimeMessage(
            MailListEntity mail, Session session
    ) throws MessagingException, UnsupportedEncodingException {

        SMTPMessage message = new SMTPMessage(session);
        MimeMessageHelper helper = new MimeMessageHelper(message,false, mail.getMailEncode());

        // Return-Path
        message.setEnvelopeFrom(mail.getDestinationReturnPath());
        // From
        helper.setFrom(mail.getSenderMailAddress(), mail.getSenderDisplayName());
        // TO
        helper.setTo(InternetAddress.parse(mail.getDestinationTo()));
        // CC
        if (StringUtils.isNotBlank(mail.getDestinationCc())) {
            helper.setCc(InternetAddress.parse(mail.getDestinationCc()));
        }
        // BCC
        if (StringUtils.isNotBlank(mail.getDestinationBcc())) {
            helper.setBcc(InternetAddress.parse(mail.getDestinationBcc()));
        }
        // Reply To
        if (StringUtils.isNotBlank(mail.getDestinationReplyTo())) {
            helper.setReplyTo(new InternetAddress(mail.getDestinationReplyTo()));
        }
        // Subject
        helper.setSubject(mail.getMailTitle());

        if (MimeTypeUtils.TEXT_HTML_VALUE.equals(mail.getContentType())) {
            // text/html
            helper.setText(mail.getMailText(), true);
        } else {
            // text/plain
            helper.setText(mail.getMailText());
        }

        helper.setSentDate(
                Date.from(
                        utility.getOperationAt().atZone(ZoneId.systemDefault()).toInstant()
                )
        );

        return message;
    }

}
