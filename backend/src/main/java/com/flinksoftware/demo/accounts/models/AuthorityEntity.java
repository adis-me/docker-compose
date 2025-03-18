package com.flinksoftware.demo.accounts.models;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serial;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Builder
@Entity
@Getter
@NoArgsConstructor
@Setter
@Table(name = "roles")
public class AuthorityEntity implements GrantedAuthority {
    @Serial
    private static final long serialVersionUID = 808186725167003005L;

    @Id
    @Column(name = "name")
    private String name;
    private String description;

    @Override
    public String getAuthority() {
        return name;
    }
}
