package com.fastretailing.dcp.common.mail.loader;

import com.fastretailing.dcp.common.mail.entity.MailTemplateEntity;
import com.fastretailing.dcp.common.mail.service.OmsMailService;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDateTime;

public class DatabaseTemplateLoaderTest {

    @InjectMocks
    private DatabaseTemplateLoader target = new DatabaseTemplateLoader();

    @Mock
    private OmsMailService mockService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testDatabaseTemplateLoaderNullValue() throws IOException {
        Object actual = target.findTemplateSource("xx");
        MatcherAssert.assertThat(actual, CoreMatchers.nullValue());
    }

    @Test
    public void testDatabaseTemplateLoaderNotNullValue() throws IOException {

        ArgumentCaptor<String> brandCodeCaptor      = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> regionCodeCaptor     = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> languageCodeCaptor   = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> mailTypeCaptor       = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> paymentTypeCaptor    = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> deliveryTypeCaptor   = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> receiveTypeCaptor    = ArgumentCaptor.forClass(String.class);

        Mockito.when(mockService.findTemplate(
                Mockito.any(String.class), Mockito.any(String.class), Mockito.any(String.class), Mockito.any(String.class),
                Mockito.any(String.class), Mockito.any(String.class), Mockito.any(String.class))).thenReturn(new MailTemplateEntity());

        Object actual = target.findTemplateSource("#brandCode<mock_brand_code>#,#regionCode<mock_region_code>#,#languageCode<mock_language_code>#,#mailType<mock_mail_type>#,#paymentType<mock_payment_type>#,#deliveryType<mock_delivery_type>#,#receiveType<mock_receive_type>#");
        MatcherAssert.assertThat(actual, CoreMatchers.notNullValue());

        Mockito.verify(mockService).findTemplate(brandCodeCaptor.capture(), regionCodeCaptor.capture(), languageCodeCaptor.capture(), mailTypeCaptor.capture(), paymentTypeCaptor.capture(), deliveryTypeCaptor.capture(), receiveTypeCaptor.capture());
        MatcherAssert.assertThat(brandCodeCaptor.getValue(),    CoreMatchers.is("mock_brand_code"));
        MatcherAssert.assertThat(regionCodeCaptor.getValue(),   CoreMatchers.is("mock_region_code"));
        MatcherAssert.assertThat(languageCodeCaptor.getValue(), CoreMatchers.is("mock_language_code"));
        MatcherAssert.assertThat(mailTypeCaptor.getValue(),     CoreMatchers.is("mock_mail_type"));
        MatcherAssert.assertThat(paymentTypeCaptor.getValue(),  CoreMatchers.is("mock_payment_type"));
        MatcherAssert.assertThat(deliveryTypeCaptor.getValue(), CoreMatchers.is("mock_delivery_type"));
        MatcherAssert.assertThat(receiveTypeCaptor.getValue(),  CoreMatchers.is("mock_receive_type"));
    }

    @Test
    public void testGetLastModifiedInputIsNull() {
        long actual = target.getLastModified(null);
        MatcherAssert.assertThat(actual, CoreMatchers.is(-1L));
    }
    @Test
    public void testGetLastModifiedUpdateAtIsNull() {
        long actual = target.getLastModified(new MailTemplateEntity());
        MatcherAssert.assertThat(actual, CoreMatchers.is(-1L));
    }

    @Test
    public void testGetLastModified() {
        MailTemplateEntity input = new MailTemplateEntity();
        input.setUpdatedDatetime(LocalDateTime.of(2000, 1, 1, 0, 0,0, 0));
        long actual = target.getLastModified(input);
        MatcherAssert.assertThat(actual > -1L, CoreMatchers.is(true));
    }

    @Test
    public void testGetReader() throws IOException {

        MailTemplateEntity input = new MailTemplateEntity();
        input.setTextTemplate("Content");
        Reader actual = target.getReader(input, "UTF-8");
        MatcherAssert.assertThat(new BufferedReader(actual).readLine(), CoreMatchers.is("Content"));

    }

    @Test
    public void testCloseTemplateSource() throws IOException {
        // Do Nothing
        target.closeTemplateSource(null);
    }

}
