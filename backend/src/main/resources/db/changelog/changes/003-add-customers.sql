drop table if exists customers;

create table customers
(
    id                     bigint auto_increment primary key,
    name                   varchar(255) null,
    tenant_id              varchar(36)  not null,
    address                varchar(255) null,
    city                   varchar(255) null,
    postal_code            varchar(50)  null,
    country_id             varchar(2)   null,
    email                  varchar(255) null,
    created_at             timestamp    not null default current_timestamp,
    updated_at             timestamp    not null default current_timestamp,
    key customers_tenant_id_idx (tenant_id),
    constraint customers_tenants_id_fk foreign key (tenant_id) references tenants (id)
) engine = innodb
  default charset = utf8mb4;
