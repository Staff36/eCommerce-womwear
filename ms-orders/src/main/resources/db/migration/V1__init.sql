create table orders (
    id          bigserial not null constraint orders_pk primary key,
    user_id     integer,
    total_sum   double precision,
    created_at  timestamp default now(),
    updated_at  timestamp default now()
);

create table products_shop.ordered_products
(
    id                      bigserial not null
            constraint ordered_products_pk
            primary key,
    product                 integer,
    quantity                integer not null,
    ordered_product_price   double precision,
    total_price             double precision,
    created_at              timestamp default now(),
    updated_at              timestamp default now()
);

create table products_shop.orders__ordered_products (
    orders_id               bigint not null,
    ordered_products_id     int not null,
    primary key (orders_id, ordered_products_id),
    foreign key (orders_id) references orders (id),
    foreign key (ordered_products_id) references ordered_products (id)
);