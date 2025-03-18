package com.flinksoftware.demo.security;

import com.flinksoftware.demo.accounts.models.AccountEntity;
import com.flinksoftware.demo.accounts.repositories.AccountRepository;
import com.flinksoftware.demo.security.exception.UserNotActivatedException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class UserModelDetailsService implements UserDetailsService {
    private final AccountRepository accountRepository;

    @Autowired
    public UserModelDetailsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    @SneakyThrows
    public UserDetails loadUserByUsername(String username) {
        final Optional<AccountEntity> optionalUser = accountRepository.findByEmail(username);
        if (optionalUser.isEmpty()) {
            log.warn("Sign in failed, user {} not found", username);
            throw new UsernameNotFoundException(String.format("Username not found: %s", username));
        }

        final AccountEntity accountEntity = optionalUser.get();
        if (!accountEntity.isActive()) {
            log.warn("Sign in failed, user {} is not yet activated", username);
            throw new UserNotActivatedException(String.format("User %s is not yet activated", accountEntity.getUsername()));
        }
        return createSpringPrincipal(accountEntity);
    }

    private UserDetails createSpringPrincipal(AccountEntity accountEntity) {
        return new org.springframework.security.core.userdetails.User(
                accountEntity.getUsername(),
                accountEntity.getPassword(),
                accountEntity.getAuthorities()
        );
    }
}
