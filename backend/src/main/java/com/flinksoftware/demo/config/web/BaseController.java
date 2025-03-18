package com.flinksoftware.demo.config.web;

import lombok.SneakyThrows;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

@SuppressWarnings({"PMD.AbstractClassWithoutAbstractMethod", "squid:S00112"})
public abstract class BaseController {

    @SneakyThrows
    protected static String getTenantId() {
        final JwtAuthenticationToken authentication = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return authentication.getToken().getClaimAsString("tenant");
    }
}
