create table "user" (
    id bigint NOT NULL,
    email character varying(255),
    firstname character varying(255),
    name character varying(255),
    password character varying(255)
);

create table "group" (
    id bigint NOT NULL,
    description character varying(255),
    name character varying(255)
);

create table groups_rights (
    group_id bigint NOT NULL,
    right_id bigint NOT NULL
);

create table "right" (
    id bigint NOT NULL,
    description character varying(255),
    key character varying(255),
    name character varying(255)
);

create table users_groups (
    user_id bigint NOT NULL,
    group_id bigint NOT NULL
);

alter table only "group"
    add constraint group_pkey primary key (id);

alter table only groups_rights
    add constraint groups_rights_pkey primary key (group_id, right_id);
    
alter table only "right"
    add constraint right_pkey primary key (id);

alter table only "user"
    add constraint user_pkey primary key (id);
    
alter table only users_groups
    add constraint users_groups_pkey primary key (user_id, group_id);

insert into "user" (id, email, firstname, name, password) values (1, 'admin', 'Default', 'Admin', '$2a$10$DFG5520WrDpPRXAzc3iQg.jzOJK3agE0ZpMvzQUsKMn13imck/HQa');
insert into "group" (id, name, description) values (1, 'ROLE_ADMIN', 'role for groups');
insert into "group" (id, name, description) values (2, 'ROLE_USER', 'role for users');
insert into "users_groups" (user_id, group_id) values (1, 1);