insert into deposits_types (deposits_types_name)
values ('С пополнением и снятием'),
       ('С пополнением, но без снятия'),
       ('Без пополнения и без снятия');

insert into types_percent_payment (type_percent_payment_period)
values ('Ежемесячно'),
       ('В конце срока');

insert into request_statuses (request_status_name)
values ('Подтверждение заявки'),
       ('Заявка подтверждена'),
       ('Заявка одобрена'),
       ('Заявка отклонена');
