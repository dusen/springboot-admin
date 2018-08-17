package com.fastretailing.dcp.storecommon.screen.message;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import java.util.Locale;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.fastretailing.dcp.storecommon.screen.ApplicationTest;
import com.fastretailing.dcp.storecommon.screen.message.LocaleMessageSource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationTest.class)
public class LocaleMessageSourceTest {

    @Autowired
    LocaleMessageSource localeMessageSource;

    /**
     * gets a message with, <BR>
     * <li>locale -> no setting.
     * <li>parameter -> no setting.
     * <p/>
     * expected:<br>
     * <li>the message from messages.properties.
     */
    @Test
    public void getMessageNoParam() {
        localeMessageSource.setLocale(null);
        assertThat(localeMessageSource.getMessage("msg.noparam"),
                equalTo("this is a message with no parameter."));
    }

    /**
     * gets a message with, <BR>
     * <li>locale -> no setting.
     * <li>parameter -> three parameters.
     * <p/>
     * expected:<br>
     * <li>the message with parameters from messages.properties.
     */
    @Test
    public void getMessageWithParam() {
        localeMessageSource.setLocale(null);
        assertThat(localeMessageSource.getMessage("msg.withparam", new String[] {"1", "2", "3"}),
                equalTo("this is a message with 1, 2, 3, three parameters."));
    }

    /**
     * gets a message with, <BR>
     * <li>locale -> Chinese.
     * <li>parameter -> no setting.
     * <p/>
     * expected:<br>
     * <li>the message from messages_zh.properties.
     */
    @Test
    public void getMessageNoParamZhLocale() {
        localeMessageSource.setLocale(Locale.SIMPLIFIED_CHINESE);
        assertThat(localeMessageSource.getMessage("msg.noparam"), equalTo("これはパラメーターなしのメッセージです。"));
    }

    /**
     * gets a message with, <BR>
     * <li>locale -> Chinese.
     * <li>parameter -> three parameters.
     * <p/>
     * expected:<br>
     * <li>the message with parameters from messages_zh.properties.
     */
    @Test
    public void getMessageWithParamZhLocale() {
        localeMessageSource.setLocale(Locale.SIMPLIFIED_CHINESE);
        assertThat(localeMessageSource.getMessage("msg.withparam", new String[] {"１", "２", "３"}),
                equalTo("これはパラメーターが三つあるメッセージです。（１、２、３）"));
    }

    /**
     * gets a message with, <BR>
     * <li>locale -> Japanese (messages_ja.properties dose not exist).
     * <li>parameter -> no setting.
     * <p/>
     * expected:<br>
     * <li>the message from messages.properties.
     */
    @Test
    public void getMessageNoParamUndefinedLocale() {
        localeMessageSource.setLocale(Locale.JAPANESE);
        assertThat(localeMessageSource.getMessage("msg.noparam"),
                equalTo("this is a message with no parameter."));
    }

    /**
     * gets a message with, <BR>
     * <li>locale -> Japanese (messages_ja.properties dose not exist).
     * <li>parameter -> three parameters.
     * <p/>
     * expected:<br>
     * <li>the message with parameters from messages.properties.
     */
    @Test
    public void getMessageWithParamUndefinedLocale() {
        localeMessageSource.setLocale(Locale.JAPANESE);
        assertThat(localeMessageSource.getMessage("msg.withparam", new String[] {"1", "2", "3"}),
                equalTo("this is a message with 1, 2, 3, three parameters."));
    }
}
