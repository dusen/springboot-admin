/**
 * @(#)SalesAdminIndexController.java
 *
 *                                    Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.index.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fastretailing.dcp.storecommon.screen.authentication.entity.UserDetails;
import com.fastretailing.dcp.storecommon.screen.form.CommonBaseForm;

/**
 * The controller class for sales admin begin page.
 */
@Controller
public class SalesAdminIndexController {

    /** This page template. */
    private static final String CURRENT_PAGE = "sales-admin-index";

    /** Store code. */
    private static final String STORE_CODE = "storeCode";
    /** User id. */
    private static final String USER_ID = "userId";

    /**
     * Initialize page.
     * 
     * @param commonBaseForm Form of common items.
     * @param request HttpRequest.
     * @param brand Brand path variable.
     * @param region Region path variable.
     * @param model The model attribute for this page.
     * @return Initialize screen.
     */
    @RequestMapping(path = "{brand}/{region}/screen/index")
    public String initialize(CommonBaseForm commonBaseForm, HttpServletRequest request,
            @PathVariable("brand") String brand, @PathVariable("region") String region,
            Model model) {

        String storeCode = "123456";
        String userId = "123456";

        setDummyLoginInfo(userId, storeCode, null);

        commonBaseForm.setDefaultLocale("en");
        commonBaseForm.setSpecifyLocale("");
        commonBaseForm.setBrandCode("0001");
        commonBaseForm.setCountryCode("17");

        model.addAttribute("commonBaseForm", commonBaseForm);
        model.addAttribute("contextPath", request.getContextPath());
        model.addAttribute("brand", brand);
        model.addAttribute("region", region);
        return CURRENT_PAGE;
    }

    /**
     * Session variable setting.
     * 
     * @param commonBaseForm Form of common items.
     * @param request Http request.
     * @return Form of common items.
     */
    @RequestMapping(path = "{brand}/{region}/screen/initSession")
    @ResponseBody
    public CommonBaseForm initializeSession(CommonBaseForm commonBaseForm,
            HttpServletRequest request) {

        String storeCode = null;
        String userId = null;
        if (!StringUtils.isEmpty(request.getParameter(STORE_CODE))) {
            storeCode = request.getParameter(STORE_CODE);
        }

        if (!StringUtils.isEmpty(request.getParameter(USER_ID))) {
            userId = request.getParameter(USER_ID);
        }

        setDummyLoginInfo(userId, storeCode, null);

        return commonBaseForm;
    }

    private void setDummyLoginInfo(String userId, String storeCd, List<String> roles) {

        List<String> loginRoles = Arrays.asList("ROLE1");

        if (!CollectionUtils.isEmpty(roles)) {
            loginRoles = roles;
        }

        // User details for unit test.
        List<GrantedAuthority> authorityList =
                loginRoles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        UserDetails userDetails = new UserDetails(storeCd, userId, "testPassword", true, true, true,
                true, authorityList);

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, authorityList);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
