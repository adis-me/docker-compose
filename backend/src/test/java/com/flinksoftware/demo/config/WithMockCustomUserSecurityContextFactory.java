package com.flinksoftware.demo.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.Collection;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {
    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        final Jwt jwt = Jwt.withTokenValue("token")
                .header("alg", "RS256")
                .claim("sub", "adis@live.nl")
                .claim("iss", "flink")
                .claim("name", "Adis Corovic")
                .claim("tenant", "38d90a94-3bf5-47d1-941f-77b6cfcebd64")
                .claim("email", "adis@live.nl")
                .claim("customer", "adis.me")
                .build();
        Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_OWNER");
        JwtAuthenticationToken token = new JwtAuthenticationToken(jwt, authorities);
        context.setAuthentication(token);
        return context;
    }
}