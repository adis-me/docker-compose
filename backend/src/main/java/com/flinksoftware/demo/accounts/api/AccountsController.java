package com.flinksoftware.demo.accounts.api;

import com.flinksoftware.demo.accounts.AccountService;
import com.flinksoftware.demo.accounts.dto.Account;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@Tag(name = "Accounts", description = "Account operations")
public class AccountsController {
    private final AccountService accountService;

    public AccountsController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/profile")
    public ResponseEntity<Account> profile(Authentication authentication) {
        final JwtAuthenticationToken jwtAuth = (JwtAuthenticationToken) authentication;
        final String email = jwtAuth.getToken().getClaimAsString("email");

        final Optional<Account> userByMail = accountService.findByEmail(email);

        return ResponseEntity.of(userByMail);
    }
}
