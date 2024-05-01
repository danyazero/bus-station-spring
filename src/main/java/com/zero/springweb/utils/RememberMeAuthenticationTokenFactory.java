package com.zero.springweb.utils;

import org.springframework.security.authentication.RememberMeAuthenticationToken;

public class RememberMeAuthenticationTokenFactory extends RememberMeAuthenticationToken {
    public RememberMeAuthenticationTokenFactory(UserPrincipal principal) {
        super(principal.getUsername(), principal, principal.getAuthorities());
    }
}
