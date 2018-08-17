/**
 * @(#)UserDetails.java
 *
 *                      Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.authentication.entity;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Authenticated user details as user principal.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserDetails extends User {

    /** Serial version UID. */
    private static final long serialVersionUID = 1838004032654976132L;

    /** Store code set only at the store. */
    private String storeCode;

    /**
     * Constructs an user details.
     * 
     * @param storeCode The store code.
     * @param username The user name.
     * @param password The password. (A dummy password is set.)
     * @param enabled Set to <code>true</code> if the user is enabled
     * @param accountNonExpired Set to <code>true</code> if the account has not expired.
     * @param credentialsNonExpired Set to <code>true</code> if the credentials have not expired.
     * @param accountNonLocked Set to <code>true</code> if the account is not locked.
     * @param authorities The authorities that should be granted to the caller if they presented the
     *        correct username and password and the user is enabled. Not null.
     */
    public UserDetails(String storeCode, String username, String password, boolean enabled,
            boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked,
            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired,
                accountNonLocked, authorities);
        this.storeCode = storeCode;
    }

}
