package com.flinksoftware.demo.customers.dto;

import com.flinksoftware.demo.accounts.dto.Country;
import lombok.Builder;
import lombok.Value;

import java.time.Instant;

@Builder(toBuilder = true)
@Value
public class Customer {
    private Long id;

    private String name;
    private String address;
    private String postalCode;
    private String city;
    private Country country;

    private String email;

    private Instant createdAt;
    private Instant updatedAt;
}
