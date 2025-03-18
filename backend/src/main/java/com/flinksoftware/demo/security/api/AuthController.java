package com.flinksoftware.demo.security.api;

import com.flinksoftware.demo.security.UserAuthenticator;
import com.flinksoftware.demo.security.config.OpenEndpoint;
import com.flinksoftware.demo.security.dto.AuthUser;
import com.flinksoftware.demo.security.dto.SignIn;
import com.flinksoftware.demo.security.dto.SignUp;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import jakarta.security.auth.message.AuthException;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Slf4j
@Tag(name = "Authentication")
public class AuthController {
    private final UserAuthenticator userAuthenticator;

    @Autowired
    public AuthController(UserAuthenticator userAuthenticator) {
        this.userAuthenticator = userAuthenticator;
    }

    @OpenEndpoint
    @PostMapping(value = "/sign-in", produces = MediaType.APPLICATION_JSON_VALUE)
    @SneakyThrows
    public ResponseEntity<Map<String, String>> signIn(@RequestBody @Valid SignIn signIn) {
        return ResponseEntity.ok(userAuthenticator.signIn(signIn.getUsername(), signIn.getPassword())
                .map(token -> Map.of(
                        "token", token
                ))
                .orElseThrow(() -> new AuthException("Authentication failed")));
    }

    @OpenEndpoint
    @PostMapping(value = "/sign-up", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthUser> signUp(@RequestBody @Valid SignUp signUp) {

        return ResponseEntity.ok(userAuthenticator
                .signUp(signUp)
                .map(signUpUser -> AuthUser.builder()
                        .email(signUpUser.getUsername())
                        .name(signUpUser.getName())
                        .build())
                .orElseThrow());
    }
}
