create sequence if not exists seq_booking start 1 increment 50;
create table if not exists booking
(
    id              int8 not null,
    checkin_date    timestamp,
    checking_status varchar(255),
    checkout_date   timestamp,
    owner_name      varchar(255),
    owner_phone     varchar(255),
    payment_status  varchar(255),
    pet_name        varchar(255),
    price           numeric(19, 2),
    primary key (id)
)
