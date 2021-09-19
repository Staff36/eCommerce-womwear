create table orders
(
    id         bigserial not null
        constraint orders_pk primary key,
    user_id    integer,
    total_sum  double precision,
    address    varchar(255),
    created_at timestamp default now(),
    updated_at timestamp default now()
);

create table ordered_products
(
    id                    bigserial not null
        constraint ordered_products_pk
            primary key,
    product_id            integer,
    quantity              integer   not null,
    order_id              bigint references orders (id),
    ordered_product_price double precision,
    total_price           double precision,
    title                 varchar(255),

    created_at            timestamp default now(),
    updated_at            timestamp default now()
);

create table orders__ordered_products
(
    orders_id           bigint not null,
    ordered_products_id int    not null,
    primary key (orders_id, ordered_products_id),
    foreign key (orders_id) references orders (id),
    foreign key (ordered_products_id) references ordered_products (id)
);

create table carts
(
    id         UUID primary key,
    user_id    integer,
    price      float,
    created_at timestamp default now(),
    updated_at timestamp default now()
);

create table cart_items
(
    id                bigserial primary key,
    cart_id           UUID references carts (id),
    product_id        bigint,
    name              varchar(255),
    quantity          int,
    price_per_product float,
    price             float,
    created_at        timestamp default current_timestamp,
    updated_at        timestamp default current_timestamp
);