package com.flinksoftware.demo.customers.repositories;

import com.flinksoftware.demo.customers.models.CustomerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerEntityRepository extends JpaRepository<CustomerEntity, Long> {

    Page<CustomerEntity> findByTenantIdAndNameContainsIgnoreCase(String tenantId, String name, Pageable pageable);

    Page<CustomerEntity> findByTenantId(String tenantId, PageRequest pageable);

    Optional<CustomerEntity> findByTenantIdAndId(String tenantId, Long customerId);

    long countByTenantId(String tenantId);
}
