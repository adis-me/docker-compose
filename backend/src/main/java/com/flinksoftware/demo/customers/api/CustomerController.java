package com.flinksoftware.demo.customers.api;

import com.flinksoftware.demo.customers.CustomerService;
import com.flinksoftware.demo.customers.dto.Customer;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
@Slf4j
@Tag(name = "Customers", description = "Customer operations")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<Page<Customer>> all(
            Authentication authentication,
            @RequestParam(name = "text", required = false) String text,
            @RequestParam(name = "size", defaultValue = "25", required = false) Integer pageSize,
            @RequestParam(name = "page", defaultValue = "0", required = false) Integer page
    ) {
        final Page<Customer> customers = customerService.findAll(getTenantId(authentication), text, page, pageSize);

        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<Customer> details(Authentication authentication, @PathVariable Long id) {
        final Customer customer = customerService.getById(getTenantId(authentication), id);
        return ResponseEntity.ok(customer);
    }

    @PostMapping("create")
    public ResponseEntity<Customer> create(Authentication authentication, @RequestBody Customer newCustomer) {
        final Customer customer = customerService.create(getTenantId(authentication), newCustomer);
        return ResponseEntity.ok(customer);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<Customer> update(Authentication authentication, @PathVariable Long id, @RequestBody Customer updatedCustomer) {
        final Customer customer = customerService.update(getTenantId(authentication), updatedCustomer);
        return ResponseEntity.ok(customer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(Authentication authentication, @PathVariable Long id) {
        customerService.delete(getTenantId(authentication), id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private static String getTenantId(Authentication authentication) {
        final JwtAuthenticationToken jwtAuth = (JwtAuthenticationToken) authentication;
        return jwtAuth.getToken().getClaimAsString("tenant");
    }
}
