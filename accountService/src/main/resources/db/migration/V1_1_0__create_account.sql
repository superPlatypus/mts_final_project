create table bank_account
(
    id_bank_account integer generated by default as identity,
    num_bank_account numeric(20,0),
--     amount money,
    amount numeric(15, 2),
    primary key (id_bank_account)
);

insert into bank_account (num_bank_account, amount)
values (12345678901234567890, 15000.00);