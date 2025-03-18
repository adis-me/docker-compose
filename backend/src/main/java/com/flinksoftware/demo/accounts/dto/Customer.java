package com.flinksoftware.demo.accounts.dto;

import lombok.Builder;
import lombok.Value;

import java.time.Instant;

@Builder
@Value
public class Customer {
    Long id;

    String name;
    String address;
    String postalCode;
    String city;
    Country country;
    String vatId;
    String chamberOfCommerceId;
    String mobilePhoneNumber;
    String phoneNumber;
    String email;
    String invoiceEmail;

    Instant createdAt;
    Instant updatedAt;
}
