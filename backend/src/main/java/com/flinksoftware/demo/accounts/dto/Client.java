package com.flinksoftware.demo.accounts.dto;

import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Value;

import java.time.Instant;

@Builder
@Value
public class Client {
    Long id;
    Tenant tenant;
    String name;
    @Email
    String email;
    String address;
    String postalCode;
    String city;
    Country country;

    String vatId;
    String chamberOfCommerceId;
    String invoiceEmail;
    String phoneNumber;

    Instant createdAt;
    Instant updatedAt;
}
