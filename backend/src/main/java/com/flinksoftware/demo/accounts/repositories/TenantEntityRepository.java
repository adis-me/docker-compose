package com.flinksoftware.demo.accounts.repositories;

import com.flinksoftware.demo.accounts.models.TenantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantEntityRepository extends JpaRepository<TenantEntity, Long> {
}
