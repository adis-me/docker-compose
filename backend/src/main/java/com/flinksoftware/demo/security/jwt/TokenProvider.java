package com.flinksoftware.demo.security.jwt;

import com.flinksoftware.demo.accounts.dto.Account;
import com.flinksoftware.demo.accounts.models.AuthorityEntity;
import com.flinksoftware.demo.config.AppProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Instant;
import java.util.List;
import java.util.Set;

@Component
@Slf4j
public class TokenProvider {
    private final AppProperties.Jwt jwtProperties;
    private final Clock clock;
    private final JwtEncoder jwtEncoder;

    @Autowired
    public TokenProvider(AppProperties appProperties, Clock clock, JwtEncoder jwtEncoder) {
        this.jwtProperties = appProperties.getJwt();
        this.clock = clock;
        this.jwtEncoder = jwtEncoder;
    }

    public String createToken(Account account) {
        final Set<AuthorityEntity> userAccountRoles = account.getRoles();
        if (userAccountRoles == null || userAccountRoles.isEmpty()) {
            throw new IllegalArgumentException(String.format("User %s has no roles, cannot create token", account.getEmail()));
        }
        final List<String> roles = account.getRoles().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        final JwtClaimsSet claims = JwtClaimsSet.builder()
                .claim("tenant", account.getClient().getTenant().getId())
                .claim("customer", account.getClient().getName())
                .claim("name", account.getName())
                .claim("email", account.getEmail())
                .claim("roles", roles)
                .issuer(jwtProperties.getIssuer())
                .issuedAt(Instant.now(clock))
                .subject(account.getEmail())
                .expiresAt(Instant.now(clock).plusSeconds(jwtProperties.getExpiration()))
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
