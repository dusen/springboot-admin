/**
 * @(#)ScreenRoleDatabaseVoter.java
 *
 *                                  Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.authorization;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import com.fastretailing.dcp.storecommon.screen.authentication.entity.UserDetails;
import com.fastretailing.dcp.storecommon.screen.authorization.repository.ScreenMasterMapper;
import com.fastretailing.dcp.storecommon.screen.config.AuthorizationConfiguration;
import com.fastretailing.dcp.storecommon.screen.util.UriUtility;
import lombok.extern.slf4j.Slf4j;

/**
 * Voter who performs an authorization check based on information registered in the database.
 */
@Component
@MapperScan(basePackages = {"com.fastretailing.dcp.storecommon.screen.authorization.repository"})
@Slf4j
public class ScreenRoleDatabaseVoter implements AccessDecisionVoter<FilterInvocation> {

    /** Configuration for authorization. */
    @Autowired
    private AuthorizationConfiguration authorizationConfiguration;

    /** Mapper for screen masters. */
    @Autowired
    private ScreenMasterMapper screenMasterMapper;

    /**
     * Basically it is all targeted, so always return true.
     * 
     * @param attribute configuration attribute.
     * @return always return true.
     */
    @Override
    public boolean supports(ConfigAttribute attribute) {
        // To be operated on every request.
        return true;
    }

    /**
     * Returns true if the holds objects is given, false otherwise.
     * 
     * @param clazz The class passed to the second argument of the vote method.
     * @return true if the holds objects is given, false otherwise.
     */
    @Override
    public boolean supports(Class<?> clazz) {
        // To be operated on every request.
        return FilterInvocation.class.isAssignableFrom(clazz);
    }

    /**
     * Based on the information registered in the database, votes for authorization.
     * 
     * If the principal type is not {@link UserDetails}, do not participate in the vote.
     * 
     * If the login user has at least one permitted role, the access permission is given, otherwise,
     * the access vote is denied.
     * 
     * @param authentication authentication information.
     * @param filterInvocation holds objects associated with a HTTP filter.
     * @param attributes configuration attributes.
     * @return Voting result.
     */
    @Override
    public int vote(Authentication authentication, FilterInvocation filterInvocation,
            Collection<ConfigAttribute> attributes) {

        if (!authentication.isAuthenticated()) {
            return ACCESS_ABSTAIN;
        }

        if (!UserDetails.class.isInstance(authentication.getPrincipal())) {
            return ACCESS_ABSTAIN;
        }

        // Request path
        String requestUrl = filterInvocation.getRequestUrl();
        log.debug("requestUrl={}", requestUrl);
        log.debug("authorizationConfiguration.getExclusionPathList()={}",
                authorizationConfiguration.getExclusionPathList());

        boolean exclusionPathFlag =
                authorizationConfiguration.getExclusionPathList().stream().allMatch(
                        path -> !requestUrl.contains(path));

        int returnValue = ACCESS_ABSTAIN;

        if (exclusionPathFlag) {

            // Roles allowed in the request path
            List<String> permittedRoleList = selectRoleList(requestUrl);

            log.debug("permittedRoleList={}", permittedRoleList);

            // User role
            List<String> loginUserRoleList = authentication.getAuthorities()
                    .stream()
                    .map(authority -> authority.getAuthority())
                    .collect(Collectors.toList());

            log.debug("loginUserRoleList={}", loginUserRoleList);

            if (CollectionUtils.containsAny(permittedRoleList, loginUserRoleList)) {
                returnValue = ACCESS_GRANTED;
            } else {
                returnValue = ACCESS_DENIED;
            }
        }

        return returnValue;
    }

    /**
     * Search roles that allow access to the request URL path from the database.
     * 
     * @param requestPath request URL path.
     * @return permitted role list.
     */
    private List<String> selectRoleList(String requestPath) {

        List<String> possiblePathList = UriUtility.createPossiblePathList(requestPath);
        log.debug("possiblePathList={}", possiblePathList);

        List<String> allowedRoleList;
        if (possiblePathList.isEmpty()) {
            allowedRoleList = Collections.emptyList();
        } else {
            allowedRoleList = screenMasterMapper.selectRoleAccessibleToPath(possiblePathList);
        }

        return allowedRoleList;

    }

}
