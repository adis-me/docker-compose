package com.flinksoftware.demo.customers;

import com.flinksoftware.demo.customers.dto.Statistics;
import com.flinksoftware.demo.customers.repositories.CustomerEntityRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class StatisticService {

    private final CustomerEntityRepository customerRepository;

    private final EntityManager entityManager;

    public StatisticService(CustomerEntityRepository customerRepository, EntityManager entityManager) {
        this.customerRepository = customerRepository;
        this.entityManager = entityManager;
    }

    @SuppressWarnings("checkstyle:magicnumber")
    public Statistics getStatistic(String tenantId) {
        final long customerCount = customerRepository.countByTenantId(tenantId);

        final Iterator customerIterator = entityManager
                .createNativeQuery("SELECT c.id, c.created_at "
                        + "FROM customers c "
                        + "WHERE c.tenant_id = :tenantId")
                .setParameter("tenantId", tenantId)
                .getResultList()
                .iterator();

        final List<Statistics.Customer> customers = new ArrayList<>();
        while (customerIterator.hasNext()) {
            final Object[] row = (Object[]) customerIterator.next();
            customers.add(new Statistics.Customer((Long) row[0], ((Timestamp) row[1]).toInstant()));
        }

        return Statistics.builder()
                .numberOfCustomers(customerCount)
                .customers(customers)
                .build();
    }
}
