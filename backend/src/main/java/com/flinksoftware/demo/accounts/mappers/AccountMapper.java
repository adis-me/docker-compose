package com.flinksoftware.demo.accounts.mappers;

import com.flinksoftware.demo.accounts.dto.Client;
import com.flinksoftware.demo.accounts.dto.Country;
import com.flinksoftware.demo.accounts.dto.Tenant;
import com.flinksoftware.demo.accounts.dto.Account;
import com.flinksoftware.demo.accounts.models.AccountEntity;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    public Account toDto(AccountEntity entity) {
        return Account.builder()
                .active(entity.isActive())
                .email(entity.getEmail())
                .name(entity.getName())
                .client(Client.builder()
                        .id(entity.getClient().getId())
                        .tenant(Tenant.builder()
                                .id(entity.getClient().getTenant().getId())
                                .name(entity.getClient().getTenant().getName())
                                .createdAt(entity.getClient().getTenant().getCreatedAt())
                                .updatedAt(entity.getClient().getTenant().getUpdatedAt())
                                .build())
                        .name(entity.getClient().getName())
                        .address(entity.getClient().getAddress())
                        .city(entity.getClient().getCity())
                        .country(Country.builder()
                                .id(entity.getClient().getCountry().getId())
                                .name(entity.getClient().getCountry().getName())
                                .build())
                        .chamberOfCommerceId(entity.getClient().getChamberOfCommerceId())
                        .vatId(entity.getClient().getVatId())
                        .email(entity.getClient().getEmail())
                        .invoiceEmail(entity.getClient().getInvoiceEmail())
                        .phoneNumber(entity.getClient().getPhoneNumber())
                        .build())
                .roles(entity.getAuthorities())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
