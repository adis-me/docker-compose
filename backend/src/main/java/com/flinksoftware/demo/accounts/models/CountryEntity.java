package com.flinksoftware.demo.accounts.models;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Builder
@Entity
@Getter
@NoArgsConstructor
@Setter
@Table(name = "countries")
public class CountryEntity {
    @Id
    private String id;
    private String name;
}
