drop table if exists accounts;
drop table if exists clients;
drop table if exists countries;
drop table if exists roles;
drop table if exists tenants;

create table tenants
(
    id         varchar(36) primary key,
    name       varchar(128) not null,
    client_id  bigint       null,
    created_at timestamp default current_timestamp,
    updated_at timestamp    null
) engine = innodb
  default charset = utf8mb4;

create table roles
(
    name        varchar(50) primary key,
    description varchar(100) default null
) engine = innodb
  default charset = utf8mb4;

create table countries
(
    id   varchar(2) primary key,
    name varchar(255) not null
) engine = innodb
  default charset = utf8mb4;

create table clients
(
    id                     bigint auto_increment primary key,
    tenant_id              varchar(36) unique not null,
    name                   varchar(255)       not null,
    address                varchar(255)       null,
    postal_code            varchar(10)        null,
    city                   varchar(255)       null,
    country_id             varchar(255)       null,
    email                  varchar(255)       not null,
    invoice_email          varchar(255)       null,
    phone_number           varchar(100)       null,
    vat_id                 varchar(128)       null,
    chamber_of_commerce_id varchar(128)       null,
    created_at             timestamp default current_timestamp,
    updated_at             timestamp          null,
    constraint clients_tenants_id_fk foreign key (tenant_id) references tenants (id),
    constraint clients_countries_id_fk foreign key (country_id) references countries (id)
) engine = innodb
  default charset = utf8mb4;

create table users
(
    id           bigint auto_increment primary key,
    email        varchar(100) not null,
    password     varchar(255) not null,
    name         varchar(255) not null,
    client_id    bigint       not null,
    active       tinyint(1)            default 1,
    last_seen_at timestamp    null,
    updated_at   timestamp    not null default current_timestamp,
    created_at   timestamp    not null default current_timestamp,
    key users_email_idx (email),
    constraint users_clients_id_fk foreign key (client_id) references clients (id)
) engine = innodb
  default charset = utf8mb4;

create table users_roles
(
    user_id   bigint      not null,
    role_name varchar(50) not null,
    constraint users_roles_user_id_fk foreign key (user_id) references users (id),
    constraint users_roles_role_name_fk foreign key (role_name) references roles (name)
) engine = innodb
  default charset = utf8mb4;