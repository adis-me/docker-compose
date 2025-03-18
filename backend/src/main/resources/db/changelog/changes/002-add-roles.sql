delete from users_roles;
delete from roles;

insert into roles(name, description)
values ('ROLE_ADMIN', 'Demo Administrator'),
       ('ROLE_OWNER', 'Tenant owner'),
       ('ROLE_USER', 'Tenant user')
;