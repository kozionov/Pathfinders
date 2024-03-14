--liquibase formatted sql

--changeset kozionov:2023-11-02-001-tables
create table authors (
    id bigserial,
    full_name varchar(255),
    primary key (id)
);

create table genres (
    id bigserial,
    name varchar(255),
    primary key (id)
);

create table books (
    id bigserial,
    title varchar(255),
    author_id bigint references authors (id) on delete cascade,
    primary key (id)
);

create table books_genres (
    book_id bigint references books(id) on delete cascade,
    genre_id bigint references genres(id) on delete cascade,
    primary key (book_id, genre_id)
);

create table comments (
    id bigserial,
    text varchar(255),
    book_id bigint references books (id) on delete cascade,
    primary key (id)
);

create table users (id bigint generated by default as identity, email varchar(255), login varchar(255) not null, mobile_number varchar(255), name varchar(255), password varchar(255) not null, surname varchar(255), role_id bigint, primary key (id));

create table role (id bigint generated by default as identity, role_name varchar(255), primary key (id));