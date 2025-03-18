package com.flinksoftware.demo.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Configuration
@ConfigurationProperties(prefix = "flink")
@Getter
@Setter
@Validated
public class AppProperties {

    @NestedConfigurationProperty
    private Jwt jwt;

    @Getter
    @Setter
    public static class Jwt {
        private static final Long TEN_DAYS_IN_SECONDS = 864_000_000L;

        private RSAPrivateKey privateKey;
        private RSAPublicKey publicKey;
        private String issuer;
        private long expiration = TEN_DAYS_IN_SECONDS;
    }
}
