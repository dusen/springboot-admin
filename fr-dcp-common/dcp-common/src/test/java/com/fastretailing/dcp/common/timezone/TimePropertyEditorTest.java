package com.fastretailing.dcp.common.timezone;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class TimePropertyEditorTest {

    @Test
    public void getAsTextCase1() {
        TimePropertyEditor editor = new TimePropertyEditor();
        editor.setAsText("10:11:12.123+01:00");
        assertThat(editor.getAsText(), is("09:11:12.123Z"));
    }

    @Test
    public void getAsTextCase2() {
        TimePropertyEditor editor = new TimePropertyEditor();
        assertThat(editor.getAsText(), is(nullValue()));
    }

    @Test
    public void getAsTextCase3() {
        TimePropertyEditor editor = new TimePropertyEditor();
        try {
            editor.setAsText("1112:13:14.156-01:00");
        } catch (Exception e) {
            assertThat(e.getClass().getSimpleName(), is("IllegalArgumentException"));
        }
    }
}
