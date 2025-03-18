package com.flinksoftware.demo.accounts.repositories;

import com.flinksoftware.demo.accounts.models.AccountSettingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountSettingRepository extends JpaRepository<AccountSettingEntity, Long> {
}
