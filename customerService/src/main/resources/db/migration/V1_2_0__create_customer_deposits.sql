create table customer_deposits
(
    customer_id integer,
    deposit_id  integer,
    primary key (customer_id, deposit_id),
    foreign key (customer_id) references customer (id_customer)
);
