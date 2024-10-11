package com.deloitte.controller;

import com.deloitte.security.GrantedAuthoritiesExtractor;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

@GetMapping("/user")
public Collection<GrantedAuthority> getUserRoles(Principal principal) {
    if (principal instanceof KeycloakAuthenticationToken) {
        return GrantedAuthoritiesExtractor.extractAuthorities((KeycloakAuthenticationToken) principal);
    }
    return List.of();
}