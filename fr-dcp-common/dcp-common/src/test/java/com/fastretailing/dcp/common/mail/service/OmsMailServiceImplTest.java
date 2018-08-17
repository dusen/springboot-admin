package com.fastretailing.dcp.common.mail.service;

import com.fastretailing.dcp.common.mail.entity.MailTemplateEntity;
import com.fastretailing.dcp.common.mail.entity.MailListEntity;
import com.fastretailing.dcp.common.mail.MailSendStatus;
import com.fastretailing.dcp.common.mail.repository.MailRepository;
import com.fastretailing.dcp.common.util.CommonUtility;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.*;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.io.Writer;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class OmsMailServiceImplTest {

    private GreenMail server;

    @InjectMocks
    private OmsMailService target = new OmsMailServiceImpl();

    @Mock
    private FreeMarkerConfigurer mockFreeMarkerConfigurer;

    @Mock
    private Configuration mockConfiguration;

    @Mock
    private Template mockTemplate;

    @Mock
    private CommonUtility mockCommonUtility;

    @InjectMocks
    private CommonUtility mockCommonUtility1;

    @Mock
    private MailRepository mockMailRepository;

    @Captor
    private ArgumentCaptor<String> findMailsFromMailPoolStatusCaptor;

    @Captor
    private ArgumentCaptor<String> findMailsFromMailPoolGroupCaptor;

    @Captor
    private ArgumentCaptor<LocalDateTime> findMailsFromMailPoolScheduledSendingAtCaptor;

    @Captor
    private ArgumentCaptor<Integer> findMailsFromMailPoolMaxLimitCaptor;

    @Captor
    private ArgumentCaptor<MailListEntity> saveMailToMailPoolCaptor;

    @Captor
    private ArgumentCaptor<String> getMailTextCaptor;

    @Captor
    private ArgumentCaptor<MailListEntity> updateMailStatusCaptor;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private int port = 0;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        port = (int) (Math.random() * 10000) + 40000;

        server = new GreenMail(new ServerSetup(port, "127.0.0.1", ServerSetup.PROTOCOL_SMTP));
        Properties prop = new Properties();
        prop.put("sender@oms.test", "123456");
        prop.put("from@test.com", "123456");
        prop.put("to1@test.com", "123456");
        prop.put("to2@test.com", "123456");
        prop.put("cc1@test.com", "123456");
        prop.put("cc2@test.com", "123456");
        prop.put("bcc1@test.com", "123456");
        prop.put("bcc2@test.com", "123456");
        prop.put("reply1@test.com", "123456");
        prop.put("return@test.com", "123456");
        server.setUsers(prop);
        server.start();

        ReflectionTestUtils.setField(target, "mailUser", "sender@oms.test");
        ReflectionTestUtils.setField(target, "mailPassword", "123456");
        ReflectionTestUtils.setField(target, "mailSmtpPort", port);
        ReflectionTestUtils.setField(target, "mailSmtpSslTrust", "*");
}

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void testFindTemplate() {

        MailTemplateEntity mockOut = new MailTemplateEntity();
        mockOut.setMailTemplateId("mock_mail_template_id");
        mockOut.setMailEncode("mock_mail_encode");
        mockOut.setMailHost("mock_mail_host");
        mockOut.setSmtpMailHost("mock_smtp_mail_host");
        mockOut.setSenderMailAddress("mock_sender_mail_address");
        mockOut.setSenderDisplayName("mock_sender_display_name");
        mockOut.setDestinationCc("mock_destination_cc");
        mockOut.setDestinationBcc("mock_destination_bcc");
        mockOut.setDestinationReplyTo("mock_destination_reply_to");
        mockOut.setDestinationReturnPath("mock_destination_return_path");
        mockOut.setContentType("mock_content_type");
        mockOut.setMailTitle("mock_mail_title");
        mockOut.setTextTemplate("mock_text_template");
        mockOut.setSendGroup("mock_send_group");
        mockOut.setSendShiftMinute(5);
        mockOut.setTemplateExplanation("mock_template_explanation");
        mockOut.setRegistrationDatetime(LocalDateTime.now());
        mockOut.setRegistrantBy("mock_registrant_by");
        mockOut.setUpdatedDatetime(LocalDateTime.now());
        mockOut.setLastUpdatedBy("mock_last_updated_by");

        ArgumentCaptor<String> brandCodeCaptor      = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> regionCodeCaptor     = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> languageCodeCaptor   = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> mailTypeCaptor       = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> paymentTypeCaptor    = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> deliveryTypeCaptor   = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> receiveTypeCaptor    = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> schemaNameCaptor     = ArgumentCaptor.forClass(String.class);

        Mockito.when(mockMailRepository.findTemplate(
                Mockito.any(String.class), Mockito.any(String.class), Mockito.any(String.class), Mockito.any(String.class),
                Mockito.any(String.class), Mockito.any(String.class), Mockito.any(String.class),
                Mockito.isNull())
        ).thenReturn(mockOut);

        MailTemplateEntity actual = target.findTemplate("mockBrandCode", "ua", "mockLanguageCode", "mockMailType", "mockPaymentType", "mockDeliveryType", "mockReceiveType");

        Mockito.when(mockMailRepository.findTemplate(
                Mockito.any(String.class), Mockito.any(String.class), Mockito.any(String.class), Mockito.any(String.class),
                Mockito.any(String.class), Mockito.any(String.class), Mockito.any(String.class),
                Mockito.any(String.class)
        )).thenReturn(new MailTemplateEntity());

        Mockito.verify(mockMailRepository).findTemplate(
                brandCodeCaptor.capture(), regionCodeCaptor.capture(), languageCodeCaptor.capture(), mailTypeCaptor.capture(),
                paymentTypeCaptor.capture(), deliveryTypeCaptor.capture(), receiveTypeCaptor.capture(),
                schemaNameCaptor.capture()
        );

        MatcherAssert.assertThat(brandCodeCaptor.getValue(),        CoreMatchers.is("mockBrandCode"));
        MatcherAssert.assertThat(regionCodeCaptor.getValue(),       CoreMatchers.is("ua"));
        MatcherAssert.assertThat(languageCodeCaptor.getValue(),     CoreMatchers.is("mockLanguageCode"));
        MatcherAssert.assertThat(mailTypeCaptor.getValue(),         CoreMatchers.is("mockMailType"));
        MatcherAssert.assertThat(paymentTypeCaptor.getValue(),      CoreMatchers.is("mockPaymentType"));
        MatcherAssert.assertThat(deliveryTypeCaptor.getValue(),     CoreMatchers.is("mockDeliveryType"));
        MatcherAssert.assertThat(receiveTypeCaptor.getValue(),      CoreMatchers.is("mockReceiveType"));

        MatcherAssert.assertThat(actual, CoreMatchers.is(mockOut));
    }

    @Test
    public void testFindMailsFromMailPool() {

        List<MailListEntity> mockOut = new ArrayList<>();
        MailListEntity entity = new MailListEntity();
        entity.setQueueId(BigInteger.valueOf(1));
        mockOut.add(entity);

        Mockito.when(mockMailRepository.findMailsFromMailPool(Mockito.any(String.class), Mockito.any(String.class), Mockito.any(LocalDateTime.class), Mockito.anyInt(), Mockito.isNull())).thenReturn(mockOut);
        LocalDateTime atTime = LocalDateTime.of(2010, 1, 1, 0, 0, 0, 0);
        List<MailListEntity> actual = target.findMailsFromMailPool("mockStatus", "mockGroup", atTime, 10);

        Mockito.verify(mockMailRepository).findMailsFromMailPool(
                findMailsFromMailPoolStatusCaptor.capture(),
                findMailsFromMailPoolGroupCaptor.capture(),
                findMailsFromMailPoolScheduledSendingAtCaptor.capture(),
                findMailsFromMailPoolMaxLimitCaptor.capture(),
                Mockito.isNull()
        );

        MatcherAssert.assertThat(findMailsFromMailPoolStatusCaptor.getValue(), CoreMatchers.is("mockStatus"));
        MatcherAssert.assertThat(findMailsFromMailPoolGroupCaptor.getValue(), CoreMatchers.is("mockGroup"));
        LocalDateTime at = LocalDateTime.of(2010, 1, 1, 0, 0, 0, 0);
        MatcherAssert.assertThat(findMailsFromMailPoolScheduledSendingAtCaptor.getValue(), CoreMatchers.is(at));
        MatcherAssert.assertThat(findMailsFromMailPoolMaxLimitCaptor.getValue(), CoreMatchers.is(10));

        MatcherAssert.assertThat(actual, CoreMatchers.is(mockOut));
    }

    @Test
    public void testSaveMailToMailPool() {

        Mockito.doReturn(1).when(mockMailRepository).saveMailToMailPool(Mockito.any(MailListEntity.class), Mockito.any(String.class));

        MailListEntity entity = new MailListEntity();
        entity.setQueueId(BigInteger.valueOf(1));
        target.saveMailToMailPool(entity);

        Mockito.verify(mockMailRepository).saveMailToMailPool(saveMailToMailPoolCaptor.capture(), Mockito.isNull());
        MatcherAssert.assertThat(saveMailToMailPoolCaptor.getValue(), CoreMatchers.is(entity));

    }

    @Test
    public void testGetMailText() throws Exception {

        Mockito.when(mockFreeMarkerConfigurer.getConfiguration()).thenReturn(mockConfiguration);
        Mockito.when(mockConfiguration.getTemplate(Mockito.any(String.class))).thenReturn(mockTemplate);
        Mockito.doNothing().when(mockTemplate).process(Mockito.any(Object.class), Mockito.any(Writer.class));

        target.getMailText("mockBrandCode", "ua", "mockLanguageCode","mockMailType", "mockPaymentType", "mockDeliveryType", "mockReceiveType", new HashMap<>());

        Mockito.verify(mockConfiguration).getTemplate(getMailTextCaptor.capture());
        String key = "#brandCode<" + "mockBrandCode" + ">#,#regionCode<" + "ua" + ">#,#languageCode<" + "mockLanguageCode" + ">#,#mailType<" + "mockMailType" + ">#,#paymentType<" + "mockPaymentType" + ">#,#deliveryType<" + "mockDeliveryType" + ">#,#receiveType<" + "mockReceiveType" + ">#";
        MatcherAssert.assertThat(getMailTextCaptor.getValue(), CoreMatchers.is(key));

    }

    @Test
    public void testUpdateMailStatusActualSentAtIsNull() {

        Mockito.doReturn(1).when(mockMailRepository).updateMailStatus(Mockito.any(MailListEntity.class), Mockito.any(String.class));
        LocalDateTime now = LocalDateTime.now();

        target.updateMailStatus(BigInteger.valueOf(1), MailSendStatus.SUCCESS.getStatus(), null, now);
        Mockito.verify(mockMailRepository).updateMailStatus(updateMailStatusCaptor.capture(), Mockito.isNull());

        MatcherAssert.assertThat(updateMailStatusCaptor.getValue().getSendStatus(), CoreMatchers.is(MailSendStatus.SUCCESS.getStatus()));
        MatcherAssert.assertThat(updateMailStatusCaptor.getValue().getSendPlanDatetime(), CoreMatchers.nullValue());
        MatcherAssert.assertThat(updateMailStatusCaptor.getValue().getUpdatedDatetime(), CoreMatchers.is(now));
        MatcherAssert.assertThat(updateMailStatusCaptor.getValue().getQueueId(), CoreMatchers.is(BigInteger.valueOf(1)));

    }

    @Test
    public void testUpdateMailStatusStatusIsNotSuccess() {

        Mockito.doReturn(1).when(mockMailRepository).updateMailStatus(Mockito.any(MailListEntity.class), Mockito.any(String.class));
        LocalDateTime now = LocalDateTime.now();

        target.updateMailStatus(BigInteger.valueOf(1), MailSendStatus.FAILURE.getStatus(), now, now);
        Mockito.verify(mockMailRepository).updateMailStatus(updateMailStatusCaptor.capture(), Mockito.isNull());

        MatcherAssert.assertThat(updateMailStatusCaptor.getValue().getSendStatus(), CoreMatchers.is(MailSendStatus.FAILURE.getStatus()));
        MatcherAssert.assertThat(updateMailStatusCaptor.getValue().getSendPlanDatetime(), CoreMatchers.nullValue());
        MatcherAssert.assertThat(updateMailStatusCaptor.getValue().getUpdatedDatetime(), CoreMatchers.is(now));
        MatcherAssert.assertThat(updateMailStatusCaptor.getValue().getQueueId(), CoreMatchers.is(BigInteger.valueOf(1)));

    }

    @Test
    public void testUpdateMailStatus() {

        Mockito.doReturn(1).when(mockMailRepository).updateMailStatus(Mockito.any(MailListEntity.class), Mockito.isNull());
        LocalDateTime now = LocalDateTime.now();

        target.updateMailStatus(BigInteger.valueOf(1), MailSendStatus.SUCCESS.getStatus(), now, now);
        Mockito.verify(mockMailRepository).updateMailStatus(updateMailStatusCaptor.capture(), Mockito.isNull());

        MatcherAssert.assertThat(updateMailStatusCaptor.getValue().getSendStatus(), CoreMatchers.is(MailSendStatus.SUCCESS.getStatus()));
        MatcherAssert.assertThat(updateMailStatusCaptor.getValue().getSendPlanDatetime(), CoreMatchers.is(now));
        MatcherAssert.assertThat(updateMailStatusCaptor.getValue().getUpdatedDatetime(), CoreMatchers.is(now));
        MatcherAssert.assertThat(updateMailStatusCaptor.getValue().getQueueId(), CoreMatchers.is(BigInteger.valueOf(1)));

    }

    @Test
    public void testSendMail() throws Exception {

        LocalDateTime now = LocalDateTime.now();
        Mockito.when(mockCommonUtility.getOperationAt()).thenReturn(now);

        MailListEntity entity = new MailListEntity();
        entity.setQueueId(BigInteger.valueOf(1));
        entity.setMailHost("127.0.0.1");
        entity.setSmtpMailHost("127.0.0.1");
        entity.setSenderMailAddress("from@test.com");
        entity.setSenderDisplayName("mockPersonalName");
        entity.setDestinationTo("to1@test.com,to2@test.com");
        entity.setDestinationCc("cc1@test.com,cc2@test.com");
        entity.setDestinationBcc("bcc1@test.com,bcc2@test.com");
        entity.setDestinationReplyTo("reply1@test.com");
        entity.setMailTitle("mockSubject");
        entity.setDestinationReturnPath("return@test.com");
        entity.setContentType(MimeTypeUtils.TEXT_HTML_VALUE);
        entity.setMailText("mockText");
        entity.setMailEncode("UTF-8");
        entity.setRetryCount(2);

        target.sendMail(entity);

        Thread.sleep(3000);

        MimeMessage[] receivedMessages = server.getReceivedMessages();
        MatcherAssert.assertThat(receivedMessages[0].getHeader("Return-Path")[0], CoreMatchers.containsString("return@test.com"));
        MatcherAssert.assertThat(receivedMessages[0].getHeader("Content-Type")[0], CoreMatchers.containsString(MimeTypeUtils.TEXT_HTML_VALUE));
        MatcherAssert.assertThat(receivedMessages[0].getFrom()[0].toString(), CoreMatchers.is("mockPersonalName <from@test.com>"));
        MatcherAssert.assertThat(receivedMessages[0].getAllRecipients()[0].toString(), CoreMatchers.is("to1@test.com"));
        MatcherAssert.assertThat(receivedMessages[0].getAllRecipients()[1].toString(), CoreMatchers.is("to2@test.com"));
        MatcherAssert.assertThat(receivedMessages[0].getAllRecipients()[2].toString(), CoreMatchers.is("cc1@test.com"));
        MatcherAssert.assertThat(receivedMessages[0].getAllRecipients()[3].toString(), CoreMatchers.is("cc2@test.com"));
        MatcherAssert.assertThat(receivedMessages[0].getReplyTo()[0].toString(), CoreMatchers.is("reply1@test.com"));
        MatcherAssert.assertThat(receivedMessages[0].getSubject(), CoreMatchers.is("mockSubject"));
        MatcherAssert.assertThat(receivedMessages[0].getContent().toString(), CoreMatchers.containsString("mockText"));

    }

    @Test
    public void testSendMailTransportInitialised() throws Exception {

        Properties props = new Properties();
        props.put("mail.host", "127.0.0.1");
        props.put("mail.smtp.host", "127.0.0.1");
        Session globalSession = Session.getInstance(props);
        ReflectionTestUtils.setField(target, "globalTransport", globalSession.getTransport("smtp"));

        LocalDateTime now = LocalDateTime.now();
        Mockito.when(mockCommonUtility.getOperationAt()).thenReturn(now);

        MailListEntity entity = new MailListEntity();
        entity.setQueueId(BigInteger.valueOf(1));
        entity.setMailHost("127.0.0.1");
        entity.setSmtpMailHost("127.0.0.1");
        entity.setSenderMailAddress("from@test.com");
        entity.setSenderDisplayName("mockPersonalName");
        entity.setDestinationTo("to1@test.com,to2@test.com");
        entity.setDestinationCc("cc1@test.com,cc2@test.com");
        entity.setDestinationBcc("bcc1@test.com,bcc2@test.com");
        entity.setDestinationReplyTo("reply1@test.com");
        entity.setMailTitle("mockSubject");
        entity.setDestinationReturnPath("return@test.com");
        entity.setContentType(MimeTypeUtils.TEXT_HTML_VALUE);
        entity.setMailText("mockText");
        entity.setMailEncode("UTF-8");
        entity.setRetryCount(2);

        target.sendMail(entity);

        // Exception not happened is ok.

    }

    /**
     * mailServiceDebugEnabledがブランク以外
     *
     * @throws Exception
     */
    @Test
    public void testSendMailTransportInitialised1() throws Exception {

        Properties props = new Properties();
        props.put("mail.host", "127.0.0.1");
        props.put("mail.smtp.host", "127.0.0.1");
        ReflectionTestUtils.setField(target, "globalTransport", null);
        ReflectionTestUtils.setField(target, "mailServiceDebugEnabled", "enabled");

        LocalDateTime now = LocalDateTime.now();
        Mockito.when(mockCommonUtility.getOperationAt()).thenReturn(now);

        MailListEntity entity = new MailListEntity();
        entity.setQueueId(BigInteger.valueOf(1));
        entity.setMailHost("127.0.0.1");
        entity.setSmtpMailHost("127.0.0.1");
        entity.setSenderMailAddress("from@test.com");
        entity.setSenderDisplayName("mockPersonalName");
        entity.setDestinationTo("to1@test.com,to2@test.com");
        entity.setDestinationCc("cc1@test.com,cc2@test.com");
        entity.setDestinationBcc("bcc1@test.com,bcc2@test.com");
        entity.setDestinationReplyTo("reply1@test.com");
        entity.setMailTitle("mockSubject");
        entity.setDestinationReturnPath("return@test.com");
        entity.setContentType(MimeTypeUtils.TEXT_HTML_VALUE);
        entity.setMailText("mockText");
        entity.setMailEncode("UTF-8");
        entity.setRetryCount(2);

        target.sendMail(entity);

        // Exception not happened is ok.

    }

    /**
     * throw Exception
     *
     * @throws Exception
     */
    @Test
    public void testSendMailThrowException() throws Exception {

        Properties props = new Properties();
        props.put("mail.host", "127.0.0.1");
        props.put("mail.smtp.host", "127.0.0.1");
        Session globalSession = Session.getInstance(props);
        ReflectionTestUtils.setField(target, "globalTransport", globalSession.getTransport("smtp"));

        LocalDateTime now = LocalDateTime.now();
        Mockito.when(mockCommonUtility.getOperationAt()).thenReturn(now);

        MailListEntity entity = new MailListEntity();
        entity.setRetryCount(2);

        boolean existEx = false;

        try {
            target.sendMail(entity);
        } catch (Exception ex) {
            existEx = true;
        }

        assertThat(existEx, is(true));

    }

    @Test
    public void testSendMailSomeAddressIsNotSetting() throws Exception {

        LocalDateTime now = LocalDateTime.now();
        Mockito.when(mockCommonUtility.getOperationAt()).thenReturn(now);

        MailListEntity entity = new MailListEntity();
        entity.setQueueId(BigInteger.valueOf(1));
        entity.setMailHost("127.0.0.1");
        entity.setSmtpMailHost("127.0.0.1");
        entity.setSenderMailAddress("from@test.com");
        entity.setDestinationTo("to1@test.com");
        entity.setMailTitle("mockSubject");
        entity.setDestinationReturnPath("return@test.com");
        entity.setContentType(MimeTypeUtils.TEXT_PLAIN_VALUE);
        entity.setMailText("mockText");
        entity.setMailEncode("UTF-8");
        entity.setRetryCount(2);


        target.sendMail(entity);
        Thread.sleep(3000);

        MimeMessage[] receivedMessages = server.getReceivedMessages();
        MatcherAssert.assertThat(receivedMessages[0].getHeader("Return-Path")[0], CoreMatchers.containsString("return@test.com"));
        MatcherAssert.assertThat(receivedMessages[0].getHeader("Content-Type")[0], CoreMatchers.containsString(MimeTypeUtils.TEXT_PLAIN_VALUE));
        MatcherAssert.assertThat(receivedMessages[0].getFrom()[0].toString(), CoreMatchers.is("from@test.com"));
        MatcherAssert.assertThat(receivedMessages[0].getAllRecipients()[0].toString(), CoreMatchers.is("to1@test.com"));
        MatcherAssert.assertThat(receivedMessages[0].getSubject(), CoreMatchers.is("mockSubject"));
        MatcherAssert.assertThat(receivedMessages[0].getContent().toString(), CoreMatchers.containsString("mockText"));

    }

    @Test
    public void testSendMailMessagingException() throws Exception {

        thrown.expect(MessagingException.class);
        thrown.expectMessage(CoreMatchers.is("Could not connect to SMTP host: 127.0.0.1, port: " + String.valueOf(port)));

        LocalDateTime now = LocalDateTime.now();
        Mockito.when(mockCommonUtility.getOperationAt()).thenReturn(now);

        MailListEntity entity = new MailListEntity();
        entity.setQueueId(BigInteger.valueOf(1));
        entity.setMailHost("127.0.0.1");
        entity.setSmtpMailHost("127.0.0.1");
        entity.setSenderMailAddress("from@test.com");
        entity.setDestinationTo("to1@test.com");
        entity.setMailTitle("mockSubject");
        entity.setDestinationReturnPath("return@test.com");
        entity.setContentType(MimeTypeUtils.TEXT_PLAIN_VALUE);
        entity.setMailText("mockText");
        entity.setMailEncode("UTF-8");
        entity.setRetryCount(2);

        server.stop();

        target.sendMail(entity);

    }


}
