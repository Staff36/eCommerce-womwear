create table deliveries
(
    id          bigserial not null constraint deliveries_pk primary key,
    order_id    integer,
    address     integer,
    coast       double precision,
    created_at  timestamp default now(),
    updated_at  timestamp default now()
);

insert into deliveries (order_id, address, coast) VALUES (1,32,17);
insert into deliveries (order_id, address, coast) VALUES (2,13, 23.5);