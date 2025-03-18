insert into tenants(id, name)
values ('78d90a94-7b45-47e2-194f-b776bd6cfce4', 'Flink Software B.V.'),
       ('38d90a94-3bf5-47d1-941f-77b6cfcebd64', 'ADIS.ME'),
       ('526997f4-638f-4dd2-b373-7df946dd6371', 'OP Vloeren'),
       ('c7ede7cd-2727-42ae-b736-2726ce49b0f9', 'Acme INC')
;

insert into countries(id, name)
values ('NL', 'Nederland'),
       ('BE', 'België'),
       ('US', 'United States of America')
;

-- customers
insert into clients (tenant_id, name, email, country_id)
values ('78d90a94-7b45-47e2-194f-b776bd6cfce4', 'Flink Software', 'info@example.com', 'NL'),
       ('38d90a94-3bf5-47d1-941f-77b6cfcebd64', 'adis.me', 'info@example.com', 'NL'),
       ('526997f4-638f-4dd2-b373-7df946dd6371', 'Jane Doe', 'info@example.com', 'NL'),
       ('c7ede7cd-2727-42ae-b736-2726ce49b0f9', 'Mark Smith', 'info@example.com', 'NL')
;

-- users
-- hashed password: secret
insert into users (id, email, password, name, client_id)
values (1, 'admin@flinksoftware.com', '$2a$10$EOFwJrkC8jl5TyAHVPylreWC9XQ.hPMmbMTe5Mt6EzGYEOd2RHFKK', 'admin', 1),
       (2, 'adis@live.nl', '$2a$10$EOFwJrkC8jl5TyAHVPylreWC9XQ.hPMmbMTe5Mt6EzGYEOd2RHFKK', 'Adis Corovic', 2),
       (3, 'jane@example.com', '$2a$10$EOFwJrkC8jl5TyAHVPylreWC9XQ.hPMmbMTe5Mt6EzGYEOd2RHFKK', 'Jane Doe', 3),
       (4, 'mark@example', '$2a$10$EOFwJrkC8jl5TyAHVPylreWC9XQ.hPMmbMTe5Mt6EzGYEOd2RHFKK', 'Mark Smith', 4)
;

-- customers
insert into customers(name, tenant_id, address, city, postal_code, country_id, email)
values ('Pietje Puk', '38d90a94-3bf5-47d1-941f-77b6cfcebd64', 'Belstraat 1', 'Belldam', '5555BC', 'NL', 'pietje@bell.com'),
       ( 'John Doe', '38d90a94-3bf5-47d1-941f-77b6cfcebd64', 'Laan van Katwijk 5', 'Katwijk', '3322SC', 'NL', 'jon.doe@example.com')
;
-- user roles
insert into users_roles(user_id, role_name)
values (1, 'ROLE_OWNER'),
       (2, 'ROLE_OWNER'),
       (3, 'ROLE_OWNER'),
       (4, 'ROLE_OWNER')
;
