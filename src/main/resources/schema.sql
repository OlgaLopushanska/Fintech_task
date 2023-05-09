CREATE TABLE currencies(
 id bigserial,
 base_currency_name varchar(10) not null,
 buy double precision,
 sell double precision,
 currency_name varchar(10) not null,
 date date,
 source varchar(20) not null,
 PRIMARY KEY (id)
);