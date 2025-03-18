package com.flinksoftware.demo.accounts;

import com.flinksoftware.demo.accounts.dto.Client;
import com.flinksoftware.demo.accounts.mappers.ClientMapper;
import com.flinksoftware.demo.accounts.models.ClientEntity;
import com.flinksoftware.demo.accounts.models.TenantEntity;
import com.flinksoftware.demo.accounts.repositories.ClientEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Instant;

@Service
public class ClientService {
    private final Clock clock;
    private final ClientMapper clientMapper;
    private final ClientEntityRepository clientRepository;

    @Autowired
    public ClientService(Clock clock, ClientMapper clientMapper, ClientEntityRepository clientRepository) {
        this.clock = clock;
        this.clientMapper = clientMapper;
        this.clientRepository = clientRepository;
    }

    public Page<Client> filterByName(String tenantId, String text, Integer page, Integer pageSize) {
        final Page<ClientEntity> clients = clientRepository.findByTenantIdAndNameContainsIgnoreCase(tenantId, text, PageRequest.of(page, pageSize));
        return clients.map(clientMapper::toDto);
    }

    public Client getById(String tenantId, Long id) {
        final ClientEntity clientEntity = clientRepository.getByTenantIdAndId(tenantId, id);
        return clientMapper.toDto(clientEntity);
    }

    public Client create(String tenantId, Client client) {
        final ClientEntity entity = clientMapper.toEntity(client).toBuilder()
                .tenant(TenantEntity.builder().id(tenantId).build())
                .createdAt(Instant.now(clock))
                .build();
        final ClientEntity clientEntity = clientRepository.save(entity);
        return clientMapper.toDto(clientEntity);
    }

    public Client update(String tenantId, Client updatedClient) {
        final ClientEntity entity = clientMapper.toEntity(updatedClient).toBuilder()
                .tenant(TenantEntity.builder()
                        .id(tenantId)
                        .build())
                .updatedAt(Instant.now(clock))
                .build();
        return clientMapper.toDto(clientRepository.save(entity));
    }

    public void delete(String tenantId, Long id) {
        final Client client = getById(tenantId, id);
        clientRepository.delete(clientMapper.toEntity(client));
    }
}
