package com.fastretailing.dcp.common.timezone;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
public class DateTimePropertyEditorTest {

    @Test
    public void getAsTextCase1() {
        DateTimePropertyEditor dateTimePropertyEditor = new DateTimePropertyEditor();
        dateTimePropertyEditor.setAsText("2017-10-11T12:13:14.156-01:00");
        assertThat(dateTimePropertyEditor.getAsText(), is("2017-10-11T13:13:14.156Z"));
    }

    @Test
    public void getAsTextCase2() {
        DateTimePropertyEditor dateTimePropertyEditor = new DateTimePropertyEditor();
        assertThat(dateTimePropertyEditor.getAsText(), is(nullValue()));
    }

    @Test
    public void getAsTextCase3() {
        DateTimePropertyEditor dateTimePropertyEditor = new DateTimePropertyEditor();
        try {
            dateTimePropertyEditor.setAsText("2017-10-11T1112:13:14.156-01:00");
        } catch (Exception e) {
            assertThat(e.getClass().getSimpleName(), is("IllegalArgumentException"));
        }
    }

    @Test
    public void getAsTextCase4() {
        DateTimePropertyEditor dateTimePropertyEditor = new DateTimePropertyEditor();
        dateTimePropertyEditor.setAsText("2017-10-11T12:13:14.156Z");
        assertThat(dateTimePropertyEditor.getAsText(), is("2017-10-11T12:13:14.156Z"));
    }

    @Test
    public void getAsTextCase5() {
        DateTimePropertyEditor dateTimePropertyEditor = new DateTimePropertyEditor();
        dateTimePropertyEditor.setAsText("2017-10-11T12:13:14Z");
        assertThat(dateTimePropertyEditor.getAsText(), is("2017-10-11T12:13:14Z"));
    }

    @Test
    public void getAsTextCase6() {
        DateTimePropertyEditor dateTimePropertyEditor = new DateTimePropertyEditor();
        dateTimePropertyEditor.setAsText("2017-10-11T12:13:14-01:00");
        assertThat(dateTimePropertyEditor.getAsText(), is("2017-10-11T13:13:14Z"));
    }
}
