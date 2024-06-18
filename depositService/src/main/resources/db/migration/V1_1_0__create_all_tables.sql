create table deposits_types
(
    id_deposits_types   integer generated by default as identity,
    deposits_types_name varchar(28),
    primary key (id_deposits_types)
);

create table types_percent_payment
(
    id_type_percent_payment     integer generated by default as identity,
    type_percent_payment_period varchar(13),
    primary key (id_type_percent_payment)
);

create table deposits
(
    id_deposit                 integer generated by default as identity,
    deposit_account_id         integer,
    deposits_type_id           integer,
    deposit_refill             boolean,
    deposits_amount            numeric(15, 2),
    start_date                 date,
    end_date                   date,
    deposit_rate               decimal(4, 2),
    type_percent_payment_id    integer,
    percent_payment_account_id integer,
    percent_payment_date       date,
    capitalization             boolean,
    deposit_refund_account_id  integer,
    primary key (id_deposit),
    foreign key (deposits_type_id) references deposits_types (id_deposits_types),
    foreign key (type_percent_payment_id) references types_percent_payment (id_type_percent_payment)
);

create table request_statuses
(
    id_request_status   integer generated by default as identity,
    request_status_name varchar(21),
    primary key (id_request_status)
);

create table requests
(
    id_request   integer generated by default as identity,
    customer_id  integer,
    request_date date,
    deposits_id  integer,
    request_status_id integer,
    change_datetime timestamptz,
    primary key (id_request),
    foreign key (deposits_id) references deposits (id_deposit),
    foreign key (request_status_id) references request_statuses (id_request_status)
);

-- create table current_request_status
-- (
--     request_id        integer,
--     request_status_id integer,
--     change_datetime   timestamptz,
--     primary key (request_id, request_status_id),
--     foreign key (request_id) references requests (id_request),
--     foreign key (request_status_id) references request_statuses (id_request_status)
-- );
