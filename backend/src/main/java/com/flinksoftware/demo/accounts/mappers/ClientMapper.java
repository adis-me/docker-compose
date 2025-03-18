package com.flinksoftware.demo.accounts.mappers;

import com.flinksoftware.demo.accounts.dto.Client;
import com.flinksoftware.demo.accounts.models.ClientEntity;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {

    public Client toDto(ClientEntity entity) {
        return Client.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .vatId(entity.getVatId())
                .chamberOfCommerceId(entity.getChamberOfCommerceId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public ClientEntity toEntity(Client dto) {
        final ClientEntity.ClientEntityBuilder builder = ClientEntity.builder();

        if (dto.getId() != null) {
            builder.id(dto.getId());
        }

        builder.name(dto.getName())
                .email(dto.getEmail())
                .vatId(dto.getVatId())
                .chamberOfCommerceId(dto.getChamberOfCommerceId())
                .invoiceEmail(dto.getInvoiceEmail())
                .phoneNumber(dto.getPhoneNumber());

        if (dto.getCreatedAt() != null) {
            builder.createdAt(dto.getCreatedAt());
        }

        if (dto.getUpdatedAt() != null) {
            builder.updatedAt(dto.getUpdatedAt());
        }

        return builder.build();
    }
}
