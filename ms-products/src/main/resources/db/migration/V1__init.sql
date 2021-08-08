create table categories
(
    id         bigserial not null
        constraint categories_pk
            primary key,
    name       varchar(64),
    updated_at timestamp default now(),
    created_at timestamp default now()
);

create table products
(
    id          bigserial not null
                constraint products_pk
                primary key,
    name        varchar(64),
    description varchar(1000),
    cost        double precision,
    category    integer
                constraint products_categories_id_fk
                references categories,
    created_at  timestamp default now(),
    updated_at  timestamp default now()
);
insert into categories (name) values ('category1');
insert into categories (name) values ('category2');
insert into categories (name) values ('category3');
insert into products (name, description, cost, category) values ('test1', 'description1', 12, 1);
insert into products (name, description, cost, category) values ('test2', 'description2', 3, 1);
insert into products (name, description, cost, category) values ('test3', 'description3', 5, 2);

