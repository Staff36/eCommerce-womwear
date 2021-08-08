create table products_stock
(
    id          bigserial not null constraint products_stock_pk primary key,
    product     integer unique not null,
    quantity    integer not null,
    updated_at  timestamp default now()
);

insert into products_stock (product, quantity) VALUES (1,50);
insert into products_stock (product, quantity) VALUES (2,10);
insert into products_stock (product, quantity) VALUES (3,0);