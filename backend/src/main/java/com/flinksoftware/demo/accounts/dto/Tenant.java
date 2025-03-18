package com.flinksoftware.demo.accounts.dto;

import com.flinksoftware.demo.accounts.models.ClientEntity;
import lombok.Builder;
import lombok.Value;

import java.time.Instant;

@Builder
@Value
public class Tenant {
    String id;

    String name;
    ClientEntity client;

    Instant createdAt;
    Instant updatedAt;
}
