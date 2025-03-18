package com.flinksoftware.demo.accounts.repositories;

import com.flinksoftware.demo.accounts.models.ClientEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientEntityRepository extends JpaRepository<ClientEntity, Long> {
    Page<ClientEntity> findByTenantIdAndNameContainsIgnoreCase(String tenantId, String text, PageRequest pageRequest);

    ClientEntity getByTenantIdAndId(String tenantId, Long clientId);
}
