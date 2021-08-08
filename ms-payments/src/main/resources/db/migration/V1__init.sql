create table products_shop.payments
(
    id          bigserial not null constraint payments_pk primary key,
    accepted    boolean not null default false,
    type        varchar(64) not null,
    order_id    integer,
    created_at  timestamp default now(),
    updated_at  timestamp default now()
);