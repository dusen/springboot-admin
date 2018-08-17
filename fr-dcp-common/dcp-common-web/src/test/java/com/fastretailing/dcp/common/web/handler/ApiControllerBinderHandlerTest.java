package com.fastretailing.dcp.common.web.handler;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.bind.WebDataBinder;

import java.beans.PropertyEditor;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ApiControllerBinderHandlerTest {
    @Mock
    private WebDataBinder webDataBinder;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void initBinderCase1() throws Exception {
        ApiControllerBinderHandler binderHandler = new ApiControllerBinderHandler();
        binderHandler.initBinder(webDataBinder);
        ArgumentCaptor<Class> captor1 = ArgumentCaptor.forClass(Class.class);
        ArgumentCaptor<PropertyEditor> captor2 = ArgumentCaptor.forClass(PropertyEditor.class);
        verify(webDataBinder, times(2)).registerCustomEditor(captor1.capture(), captor2.capture());
        List<Class> captor1List = captor1.getAllValues();
        List<PropertyEditor> captor2List = captor2.getAllValues();
        assertThat(captor1List.get(0).getSimpleName(), is("LocalDateTime"));
        assertThat(captor1List.get(1).getSimpleName(), is("LocalTime"));
        assertThat(captor2List.get(0).getClass().getSimpleName(), is("DateTimePropertyEditor"));
        assertThat(captor2List.get(1).getClass().getSimpleName(), is("TimePropertyEditor"));

    }
}

