package com.fastretailing.dcp.storecommon.message;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fastretailing.dcp.storecommon.ApplicationTest;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationTest.class)
public class SystemMessageSourceTest {

    @Autowired
    SystemMessageSource systemMessageSource;

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
        assertThat(systemMessageSource.getMessage("msg.noparam"),
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
        assertThat(systemMessageSource.getMessage("msg.withparam", new String[] { "1", "2", "3" }),
                equalTo("this is a message with 1, 2, 3, three parameters."));
    }
}
