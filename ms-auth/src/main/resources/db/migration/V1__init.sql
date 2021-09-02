create table users (
     id             bigserial not null constraint users_pk primary key,
     email          varchar(32) not null unique,
     password       varchar(64) not null,
     birth_date     date,
     phone_number   varchar(32),
     updated_at     timestamp default now(),
     created_at     timestamp default now()
);

create table roles (
     id      bigserial not null constraint roles_pk primary key,
     name    varchar(32) not null
);

create table users_roles (
    users_id    bigint not null,
    roles_id    int not null,
    primary key (users_id, roles_id),
    foreign key (users_id) references users (id),
    foreign key (roles_id) references roles (id)
);

insert into roles (name) values  ('ROLE_USER'),
                                 ('ROLE_ADMIN'),
                                 ('ROLE_MODERATOR');


