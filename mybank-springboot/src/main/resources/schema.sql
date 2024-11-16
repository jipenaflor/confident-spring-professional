create table if not exists "transactions"
(
    id              uuid                default random_uuid() primary key,
    amount          decimal(15, 4)      not null,
    `timestamp`     timestamp           not null,
    reference       varchar(255)        not null,
    bank_slogan     varchar(255),
    receiving_user  varchar(255)        not null
);