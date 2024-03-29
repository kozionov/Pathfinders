--liquibase formatted sql

--changeset kozionov:2023-11-02-001-tables
create table users (id bigint generated by default as identity, email varchar(255), login varchar(255) not null, mobile_number varchar(255), name varchar(255), password varchar(255) not null, surname varchar(255), role_id bigint, primary key (id));

create table role (id bigint generated by default as identity, role_name varchar(255), primary key (id));

create table scores (id bigint generated by default as identity, name varchar(255), val integer, primary key (id));

create table club_log_link (club_id bigint not null, log_id bigint not null);
create table club_score_link (club_id bigint not null, score_id bigint not null);
create table clubs (id bigint generated by default as identity, city varchar(255), name varchar(255), director_id bigint, primary key (id));
create table log_user_link (log_id bigint not null, user_id bigint not null);
create table logs (id bigint not null, date_from date, date_to date, primary key (id));
create table record_score_link (record_id bigint not null, score_id bigint not null);
create table records (id bigint generated by default as identity, class_date date, score_sum integer, log_id bigint not null, user_id bigint not null, primary key (id));
alter table if exists users add column club_id bigint;

create sequence logs_seq start with 1 increment by 50;
create sequence scores_seq start with 1 increment by 50;

alter table if exists club_log_link add constraint FK8ky3gxajttayrkomi1q9imeu foreign key (log_id) references logs;
alter table if exists club_log_link add constraint FK5rm0v7og7lav8dghqb54ossk6 foreign key (club_id) references clubs;
alter table if exists club_score_link add constraint FKep4djtpnti91x8sapyn85ynxl foreign key (score_id) references scores;
alter table if exists club_score_link add constraint FKm35wgor8dx1k1j2ihrvoqft2f foreign key (club_id) references clubs;
alter table if exists clubs add constraint FKfr8j56g6e73308hivluf75ftx foreign key (director_id) references users;
alter table if exists log_user_link add constraint FKpe3953jnvtc6gaclrt5fhhhf8 foreign key (user_id) references users;
alter table if exists log_user_link add constraint FK489rl3el8m0rh5ca5s49ds4m3 foreign key (log_id) references logs;
alter table if exists records add constraint FK9pwtev2vbnxoj0wxvkiu749f5 foreign key (log_id) references logs;
alter table if exists records add constraint FK6p95uajgka0j0dc9vlbjw1sf1 foreign key (user_id) references users;
alter table if exists users add constraint FKko09rpuwtvpj0axpis8at14hm foreign key (club_id) references clubs;
