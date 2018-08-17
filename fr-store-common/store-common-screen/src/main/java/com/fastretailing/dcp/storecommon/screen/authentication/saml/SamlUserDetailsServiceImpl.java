/**
 * @(#)SamlUserDetailsServiceImpl.java
 *
 *                                     Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.authentication.saml;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.collections.CollectionUtils;
import org.jboss.logging.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.saml.SAMLCredential;
import org.springframework.security.saml.userdetails.SAMLUserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.fastretailing.dcp.common.exception.SystemException;
import com.fastretailing.dcp.storecommon.screen.authentication.client.entity.employee.AuthenticationEmployeeInformation;
import com.fastretailing.dcp.storecommon.screen.authentication.client.repository.employee.AuthenticationEmployeeRepository;
import com.fastretailing.dcp.storecommon.screen.authentication.entity.UserDetails;
import com.fastretailing.dcp.storecommon.screen.config.DevelopmentConfiguration;
import com.fastretailing.dcp.storecommon.screen.log.AuditLogger;
import com.fastretailing.dcp.storecommon.screen.log.MdcKey;
import com.fastretailing.dcp.storecommon.screen.util.UriUtility;
import lombok.extern.slf4j.Slf4j;

/**
 * User details creation service for SAML authentication.
 */
@Service
@Slf4j
public class SamlUserDetailsServiceImpl implements SAMLUserDetailsService {

    /** Dummy password. */
    private static final String DUMMY_PASSWORD = "<dummy!2018>";

    /** Employee API call interface. */
    @Autowired
    private AuthenticationEmployeeRepository employeeInformationRepository;

    /** Environment. */
    @Autowired
    private Environment environment;

    /** Configuration for development. */
    @Autowired
    private DevelopmentConfiguration developmentConfiguration;

    /**
     * Returns authenticated user detail information.
     * 
     * The role is acquired from the employee information API on Employee Platform. The store code
     * is created from user ID.
     * 
     * @param credential SAML credential.
     * @return User detail information.
     * @throws UsernameNotFoundException This exception does not occur.
     *         {@link SAMLUserDetailsService#loadUserBySAML(SAMLCredential)}
     */
    @Override
    public Object loadUserBySAML(SAMLCredential credential) throws UsernameNotFoundException {
        try {
            return createUserDetails(credential);
        } catch (Exception e) {
            throw new UsernameNotFoundException(
                    "An exception occurred while creating user principal.", e);
        }
    }

    /**
     * Returns authenticated user detail information.
     * 
     * The role is acquired from the employee information API on Employee Platform. The store code
     * is created from user ID.
     * 
     * @param credential SAML credential.
     * @return User detail information.
     */
    private Object createUserDetails(SAMLCredential credential) {

        if (credential == null) {
            throw new SystemException("The credential is null.");
        }

        // The method is supposed to identify local account of user referenced by
        // data in the SAML assertion and return UserDetails object describing the user.

        String userId = credential.getNameID().getValue();
        log.debug("userId(NameID)={}", userId);

        // Prepare brand and region.
        String serverContextPath = environment.getProperty("server.context-path");
        UriUtility.setRequestPathVariable(serverContextPath);

        // Assign role.
        List<GrantedAuthority> authorities = getGrantedAuthorityList(userId);

        // In a real scenario, this implementation has to locate user in a arbitrary
        // dataStore based on information present in the SAMLCredential and
        // returns such a date in a form of application specific UserDetails object.
        UserDetails userDetails =
                new UserDetails(null, userId, DUMMY_PASSWORD, true, true, true, true, authorities);
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                        .getRequest();
        Map<String, String> informationMap = new LinkedHashMap<>();
        AuditLogger.outputAuditLog(userDetails, SamlUserDetailsServiceImpl.class,
                AuditLogger.LOGIN_HANDLER_STRING, request, informationMap);
        MDC.put(MdcKey.USER_ID.getKey(), userId);

        return userDetails;
    }

    /**
     * Returns granted authority list given to the authenticated user.
     * 
     * @param userId the user ID.
     * @return the granted authority list.
     */
    private List<GrantedAuthority> getGrantedAuthorityList(String userId) {

        // Uses dummy roles, if there are some.
        if (CollectionUtils.isNotEmpty(developmentConfiguration.getRoles())) {
            List<GrantedAuthority> authorities = developmentConfiguration.getRoles()
                    .stream()
                    .map(role -> new SimpleGrantedAuthority(role))
                    .collect(Collectors.toList());
            return authorities;
        }

        // Call employee-information API on Employee Platform.
        AuthenticationEmployeeInformation response =
                employeeInformationRepository.requestEmployeeInformation(userId);
        List<String> employeeRoleList = response.getEmployeeData().getEmployeeRoleList();

        log.debug("employeeRoleList={}", employeeRoleList);

        List<GrantedAuthority> authorities = new ArrayList<>();
        if (employeeRoleList != null) {
            employeeRoleList.forEach(role -> {
                GrantedAuthority authority = new SimpleGrantedAuthority(role);
                authorities.add(authority);
            });
        }

        return authorities;

    }

}
