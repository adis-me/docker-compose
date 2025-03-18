package com.flinksoftware.demo.accounts.dto;

import com.flinksoftware.demo.accounts.models.AuthorityEntity;
import lombok.Builder;
import lombok.Value;

import java.time.Instant;
import java.util.Set;

@Builder
@Value
public class Account {
    Long id;

    String email;
    String name;
    boolean active;
    Client client;
    Set<AuthorityEntity> roles;

    Instant createdAt;
    Instant updatedAt;
}
