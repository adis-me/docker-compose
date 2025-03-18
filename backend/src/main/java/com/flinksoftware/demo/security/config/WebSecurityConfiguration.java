package com.flinksoftware.demo.security.config;


import com.flinksoftware.demo.config.AppProperties;
import com.flinksoftware.demo.security.UserModelDetailsService;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

import static org.springframework.http.HttpMethod.OPTIONS;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfiguration {

    private final AppProperties appProperties;

    @Autowired
    public WebSecurityConfiguration(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @SneakyThrows
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http
                /*
                 * When should you use CSRF protection? Our recommendation is to use CSRF protection for any request
                 * that could be processed by a browser by normal users. If you are only creating a service that is
                 * used by non-browser clients, you will likely want to disable CSRF protection
                 * @see https://docs.spring.io/spring-security/site/docs/current/reference/html5/
                 */
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                // allow sign-up and sign-in
                                antMatcher("/api/sign-up"),
                                antMatcher("/api/sign-in"),
                                // allow Swagger resources
                                antMatcher("/api/sign-in"),
                                antMatcher("/v3/api-docs/**"),
                                antMatcher("/swagger-ui.html"),
                                antMatcher("/swagger-ui/**"),
                                // allow anonymous resources
                                antMatcher("/*.html"),
                                antMatcher("/favicon.ico"),
                                antMatcher("/**/*.html"),
                                antMatcher("/**/*.css"),
                                antMatcher("/**/*.js"),
                                // allow OPTIONS http method for spring docs
                                antMatcher(OPTIONS, "/**")
                        ).permitAll()
                        .anyRequest().authenticated())
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                        .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
                )
                // no session management should be created or used by spring security
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS));

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationProvider(UserModelDetailsService userDetailsService) {
        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(authenticationProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder
                .withPublicKey(appProperties.getJwt().getPublicKey())
                .build();
    }

    @Bean
    public JwtEncoder jwtEncoder() {
        final RSAKey rsaKey = new RSAKey.Builder(appProperties.getJwt().getPublicKey()).privateKey(appProperties.getJwt().getPrivateKey()).build();
        final JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(rsaKey));
        return new NimbusJwtEncoder(jwkSource);
    }

    @EventListener
    public void handleContextRefresh(ContextRefreshedEvent event) {
        event.getApplicationContext().getBean("requestMappingHandlerMapping", RequestMappingHandlerMapping.class)
                .getHandlerMethods()
                .forEach((requestMappingInfo, handlerMethod) -> {
                    final Method method = handlerMethod.getMethod();
                    final Secured securedAnnotation = AnnotationUtils.findAnnotation(method, Secured.class);
                    final OpenEndpoint openEndpointAnnotation = AnnotationUtils.findAnnotation(method, OpenEndpoint.class);
                    if (securedAnnotation != null && openEndpointAnnotation != null) {
                        throw new AssertionError("Method " + method + " can not be secured and open at the same time");
                    } else if (securedAnnotation == null && openEndpointAnnotation == null && method.getDeclaringClass().getPackageName().startsWith("com.alliander.so.dsa")) {
                        throw new AssertionError("Method " + method + " has no security annotation");
                    }
                });
    }
}
