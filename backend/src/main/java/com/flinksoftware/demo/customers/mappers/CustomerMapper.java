package com.flinksoftware.demo.customers.mappers;

import com.flinksoftware.demo.accounts.dto.Country;
import com.flinksoftware.demo.accounts.models.CountryEntity;
import com.flinksoftware.demo.customers.dto.Customer;
import com.flinksoftware.demo.customers.models.CustomerEntity;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public CustomerEntity toEntity(Customer customer) {
        final CustomerEntity.CustomerEntityBuilder builder = CustomerEntity.builder()
                .id(customer.getId())
                .name(customer.getName())
                .address(customer.getAddress())
                .postalCode(customer.getPostalCode())
                .city(customer.getCity());
        if (customer.getCountry() != null) {
            builder.country(CountryEntity.builder()
                    .id(customer.getCountry().getId())
                    .build());
        }

        return builder.email(customer.getEmail())
                .createdAt(customer.getCreatedAt())
                .updatedAt(customer.getUpdatedAt())
                .build();
    }

    public Customer toDto(CustomerEntity entity) {
        final Customer.CustomerBuilder builder = Customer.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .address(entity.getAddress())
                .postalCode(entity.getPostalCode())
                .city(entity.getCity());
        if (entity.getCountry() != null) {
            builder.country(Country.builder()
                    .id(entity.getCountry().getId())
                    .build());
        }
        return builder.createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

}
