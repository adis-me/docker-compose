package com.flinksoftware.demo.customers;

import com.flinksoftware.demo.accounts.models.CountryEntity;
import com.flinksoftware.demo.accounts.models.TenantEntity;
import com.flinksoftware.demo.customers.dto.Customer;
import com.flinksoftware.demo.customers.exceptions.CustomerNotFound;
import com.flinksoftware.demo.customers.mappers.CustomerMapper;
import com.flinksoftware.demo.customers.models.CustomerEntity;
import com.flinksoftware.demo.customers.repositories.CustomerEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Instant;

import static org.springframework.util.StringUtils.hasText;

@RequiredArgsConstructor
@Service
@Slf4j
public class CustomerService {
    private final Clock clock;
    private final CustomerMapper customerMapper;
    private final CustomerEntityRepository customerRepository;

    public Page<Customer> findAll(String tenantId, String text, int page, int pageSize) {
        final Page<CustomerEntity> customers;
        if (hasText(text)) {
            customers = customerRepository.findByTenantIdAndNameContainsIgnoreCase(tenantId, text, PageRequest.of(page, pageSize));
        } else {
            customers = customerRepository.findByTenantId(tenantId, PageRequest.of(page, pageSize));
        }
        return customers.map(customerMapper::toDto);
    }

    public Customer getById(String tenantId, Long customerId) {
        final CustomerEntity entity = customerRepository.findByTenantIdAndId(tenantId, customerId)
                .orElseThrow(() -> new CustomerNotFound(String.format("Cannot find customer with id %s and tenant %s", customerId, tenantId)));

        return customerMapper.toDto(entity);
    }

    public Customer create(String tenantId, Customer customer) {
        final CustomerEntity entity = customerMapper.toEntity(customer).toBuilder()
                .tenant(TenantEntity.builder().id(tenantId).build())
                .country(CountryEntity.builder().id(customer.getCountry().getId()).build())
                .createdAt(Instant.now(clock))
                .build();
        final CustomerEntity customerEntity = customerRepository.save(entity);
        return customerMapper.toDto(customerEntity);
    }

    public Customer update(String tenantId, Customer customer) {
        final CustomerEntity existingEntity = customerRepository.getReferenceById(customer.getId());

        final CustomerEntity updatedEntity = existingEntity.toBuilder()
                .tenant(TenantEntity.builder()
                        .id(tenantId)
                        .build())
                .name(customer.getName())
                .address(customer.getAddress())
                .postalCode(customer.getPostalCode())
                .city(customer.getCity())
                .country(CountryEntity.builder()
                        .id(customer.getCountry().getId())
                        .build())
                .updatedAt(Instant.now(clock))
                .build();

        return customerMapper.toDto(customerRepository.save(updatedEntity));
    }

    public void delete(String tenantId, Long customerId) {
        final Customer customer = getById(tenantId, customerId);
        customerRepository.delete(customerMapper.toEntity(customer));
    }
}
