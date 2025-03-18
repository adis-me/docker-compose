package com.flinksoftware.demo.accounts;

import com.flinksoftware.demo.accounts.dto.Account;
import com.flinksoftware.demo.accounts.mappers.AccountMapper;
import com.flinksoftware.demo.accounts.models.AuthorityEntity;
import com.flinksoftware.demo.accounts.models.ClientEntity;
import com.flinksoftware.demo.accounts.models.CountryEntity;
import com.flinksoftware.demo.accounts.models.TenantEntity;
import com.flinksoftware.demo.accounts.models.AccountEntity;
import com.flinksoftware.demo.accounts.repositories.AccountRepository;
import com.flinksoftware.demo.common.UUIDProvider;
import com.flinksoftware.demo.security.dto.SignUp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Instant;
import java.util.Optional;
import java.util.Set;

import static com.flinksoftware.demo.security.config.Constants.ROLE_USER;

@Service
@Slf4j
public class AccountService {
    private static final String DEFAULT_COUNTRY_ID = "NL";
    private final Clock clock;
    private final AccountRepository accountRepository;
    private final UUIDProvider uuidProvider;

    private final AccountMapper accountMapper;

    @Autowired
    public AccountService(Clock clock, AccountRepository accountRepository, UUIDProvider uuidProvider, AccountMapper accountMapper) {
        this.clock = clock;
        this.accountRepository = accountRepository;
        this.uuidProvider = uuidProvider;
        this.accountMapper = accountMapper;
    }

    public Optional<Account> findByEmail(String email) {
        return accountRepository.findByEmail(email)
                .map(accountMapper::toDto);
    }

    /**
     * @implNote {@link SignUp} contains at this stage an encoded password.
     */
    public AccountEntity signUp(SignUp signUp) {
        final String name;
        // in case no name was provided, use username (email)
        if (signUp.getName() == null) {
            name = signUp.getUsername();
        } else {
            name = signUp.getName();
        }

        final Instant now = Instant.now(clock);

        final AccountEntity accountEntity = AccountEntity.builder()
                .createdAt(now)
                .name(name)
                .email(signUp.getUsername())
                .password(signUp.getPassword())
                .authorities(Set.of(AuthorityEntity.builder()
                        .name(ROLE_USER)
                        .build()))
                .client(ClientEntity.builder()
                        .tenant(TenantEntity.builder()
                                .id(uuidProvider.generate().toString())
                                .createdAt(now)
                                .name(name)
                                .build())
                        .name(name)
                        .email(signUp.getUsername())
                        .country(CountryEntity.builder()
                                .id(DEFAULT_COUNTRY_ID)
                                .build())
                        .createdAt(now)
                        .build())
                .createdAt(now)
                .build();


        log.info("Successfully signed up new user [{}]", signUp.getUsername());
        return accountRepository.save(accountEntity);
    }
}
