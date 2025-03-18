package com.flinksoftware.demo.accounts.api;

import com.flinksoftware.demo.accounts.ClientService;
import com.flinksoftware.demo.accounts.dto.Client;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/api/clients")
@Tag(name = "Clients", description = "Client operations")
public class ClientsController {

    private final ClientService clientService;

    public ClientsController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/all")
    public ResponseEntity<Page<Client>> all(
            Authentication authentication,
            @RequestParam(name = "text", required = false) String text,
            @RequestParam(name = "size", defaultValue = "25", required = false) Integer pageSize,
            @RequestParam(name = "page", defaultValue = "0", required = false) Integer page
    ) {
        final Page<Client> clients = clientService.filterByName(getTenantId(authentication), text, page, pageSize);

        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<Client> details(Authentication authentication, @PathVariable Long id) {
        final Client client = clientService.getById(getTenantId(authentication), id);
        return ResponseEntity.ok(client);
    }

    @PostMapping("create")
    public ResponseEntity<Client> create(Authentication authentication, @RequestBody Client newClient) {
        final Client client = clientService.create(getTenantId(authentication), newClient);
        return ResponseEntity.ok(client);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<Client> update(Authentication authentication, @PathVariable Long id, @RequestBody Client updatedClient) {
        final Client client = clientService.update(getTenantId(authentication), updatedClient);
        return ResponseEntity.ok(client);
    }

    @DeleteMapping("/{id}/archive")
    public ResponseEntity<Void> archive(Authentication authentication, @PathVariable Long id) {
        clientService.delete(getTenantId(authentication), id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private static String getTenantId(Authentication authentication) {
        final JwtAuthenticationToken jwtAuth = (JwtAuthenticationToken) authentication;
        return jwtAuth.getToken().getClaimAsString("tenant");
    }
}
