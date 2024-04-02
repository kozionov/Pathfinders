--liquibase formatted sql

--changeset kozionov:2023-11-02-001-tables
create table pearls
(
    id   bigint generated by default as identity,
    link varchar(255),
    text  varchar(1000),
    primary key (id)
);


