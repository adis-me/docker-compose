package com.flinksoftware.demo.accounts.dto;

import lombok.Builder;
import lombok.Value;

import java.time.Instant;

@Builder
@Value
public class AccountSetting {
    Long id;

    String key;
    String value;
    String description;

    Instant createdAt;
    Instant updatedAt;
}
