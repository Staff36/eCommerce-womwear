create table users (
     id          bigserial not null constraint users_pk primary key,
     login       varchar(32) not null unique,
     password    varchar(64) not null,
     email    varchar(128) not null,
     birth_date date,
     phone_number   varchar(128),
     updated_at timestamp default now(),
     created_at timestamp default now()
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

create table products_shop.countries(
    id          bigserial not null constraint countries_pk primary key,
    name        varchar(64)

);
create table products_shop.regions(
    id          bigserial not null constraint regions_pk primary key,
    name        varchar(64),
    country     integer
                constraint regions_countries_id_fk
                references countries
);

create table products_shop.cities(
    id          bigserial not null constraint cities_pk primary key,
    name        varchar(64),
    region      integer
                 constraint cites_regions_id_fk
                     references regions
);

create table products_shop.addresses(
    id          bigserial not null constraint addresses_pk primary key,
    city        integer
                constraint addresses_cities_id_fk
                    references cities,
    created_at  timestamp default now(),
    updated_at  timestamp default now()
);

create table products_shop.users_addresses (
    users_id    bigint not null,
    addresses_id    int not null,
    primary key (users_id, addresses_id),
    foreign key (users_id) references users (id),
    foreign key (addresses_id) references addresses (id)
);
