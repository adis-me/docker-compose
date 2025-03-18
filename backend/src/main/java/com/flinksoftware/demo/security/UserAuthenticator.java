package com.flinksoftware.demo.security;

import com.flinksoftware.demo.accounts.AccountService;
import com.flinksoftware.demo.accounts.dto.Account;
import com.flinksoftware.demo.accounts.models.AccountEntity;
import com.flinksoftware.demo.security.dto.SignUp;
import com.flinksoftware.demo.security.jwt.TokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserAuthenticator {
    private final AccountService accountService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @Autowired
    public UserAuthenticator(AccountService accountService,
                             AuthenticationManager authenticationManager,
                             PasswordEncoder passwordEncoder,
                             TokenProvider tokenProvider) {
        this.accountService = accountService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    /**
     * @implNote can return empty token if sign in failed.
     */
    public Optional<String> signIn(String username, String password) {
        log.debug("[{}] attempts to sign in", username);
        String token = null;
        final Optional<Account> optionalUser = accountService.findByEmail(username);

        if (optionalUser.isPresent()) {
            token = tokenProvider.createToken(optionalUser.get());
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
        }

        return Optional.ofNullable(token);
    }

    /**
     * Create a new user in the database.
     */
    public Optional<AccountEntity> signUp(SignUp signUp) {
        log.info("New user [{}] attempting to sign in", signUp.getUsername());
        final Optional<Account> existingUser = accountService.findByEmail(signUp.getUsername());
        if (existingUser.isPresent()) {
            log.warn("Existing user [{}] tries to sign up again?", signUp.getUsername());
            return Optional.empty();
        }

        final SignUp encodedSignUp = signUp.toBuilder()
                .password(passwordEncoder.encode(signUp.getPassword()))
                .build();
        return Optional.of(accountService.signUp(encodedSignUp));
    }

    public Optional<String> getUserNameFromAuthenticationContext(Authentication authentication) {
        return Optional.ofNullable(((org.springframework.security.core.userdetails.User) authentication.getPrincipal()).getUsername());
    }

    public Optional<Account> getUserFromAuthenticationContext(Authentication authentication) {
        return getUserNameFromAuthenticationContext(authentication)
                .flatMap(accountService::findByEmail);
    }
}
