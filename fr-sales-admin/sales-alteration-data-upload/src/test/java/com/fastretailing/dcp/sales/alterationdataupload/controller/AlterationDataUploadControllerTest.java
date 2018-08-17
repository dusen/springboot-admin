package com.fastretailing.dcp.sales.alterationdataupload.controller;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.fastretailing.dcp.sales.alterationdataupload.form.AlterationDataUploadForm;
import com.fastretailing.dcp.sales.alterationdataupload.service.AlterationDataUploadService;
import com.fastretailing.dcp.storecommon.screen.authentication.entity.UserDetails;
import com.fastretailing.dcp.storecommon.screen.config.DevelopmentConfiguration;
import com.fastretailing.dcp.storecommon.screen.form.CommonBaseForm;
import io.florianlopes.spring.test.web.servlet.request.MockMvcRequestBuilderUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AlterationDataUploadControllerTest {

    /** SpringMVC request mock. */
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    /** Create alteration data upload service Mock. */
    @MockBean
    private AlterationDataUploadService alterationDataUploadService;

    /** Request url. */
    private static final String REQUEST_URL = "/uq/jp/screen/alteration-dataup";

    /** Request upload url. */
    private static final String REQUEST_UPLOAD_URL = "/uq/jp/screen/alteration-dataup/upload";

    /** Configuration for development. */
    @Autowired
    private DevelopmentConfiguration developmentConfiguration;

    /** Set up. */
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        // User details for unit test.
        List<GrantedAuthority> authorityList = developmentConfiguration.getRoles()
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        UserDetails userDetails = new UserDetails(developmentConfiguration.getStoreCode(),
                developmentConfiguration.getUserId(), "testPassword", true, true, true, true,
                authorityList);

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, authorityList);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    public void testDisplayInitPage() throws Exception {

        CommonBaseForm commonBaseForm = new CommonBaseForm();
        ResultActions res = mockMvc.perform(get(REQUEST_URL, commonBaseForm));
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(view().name("alteration-data-upload")).andExpect(model().hasNoErrors());

    }

    @Test
    public void testDisplayListPage() throws Exception {
        when(alterationDataUploadService.handleTransactionZip(anyObject(), anyString()))
                .thenReturn(true);
        AlterationDataUploadForm alterationDataUploadForm = new AlterationDataUploadForm();
        alterationDataUploadForm.setAlterationDataType("0");
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilderUtils.postForm(REQUEST_UPLOAD_URL, alterationDataUploadForm);
        ResultActions res = mockMvc.perform(mockHttpServletRequestBuilder);
        res.andExpect(MockMvcResultMatchers.status().isOk());

    }
}
