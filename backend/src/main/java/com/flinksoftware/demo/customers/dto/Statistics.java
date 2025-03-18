package com.flinksoftware.demo.customers.dto;

import lombok.Builder;
import lombok.Value;

import java.time.Instant;
import java.util.List;

@Builder
@Value
public class Statistics {

    private long numberOfOffers;
    private long numberOfCustomers;
    private List<Offer> offers;
    private List<Customer> customers;

    @Value
    public static class Offer {
        private final Long id;
        private final Long customerId;
        private final String status;
        private final Instant createdAt;
    }

    @Value
    public static class Customer {
        private final Long id;
        private final Instant createdAt;
    }
}
