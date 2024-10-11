package com.deloitte.security;

import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.stream.Collectors;

public class GrantedAuthoritiesExtractor {

    public static Collection<GrantedAuthority> extractAuthorities(KeycloakAuthenticationToken authentication) {
        return authentication.getAccount()
                .getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role)) // Prefix roles with "ROLE_"
                .collect(Collectors.toList());
    }
}