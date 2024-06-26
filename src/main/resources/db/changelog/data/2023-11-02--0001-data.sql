--liquibase formatted sql

--changeset kozionov:2023-11-02-001-data
insert into role(role_name)
values ('ADMIN'),
       ('DIRECTOR'),
       ('USER');

insert into scores(name, val)
values ('Следопытский галстук', 5), ('ПЖ', 5), ('ДЗ', 5), ('Активность', 4), ('Пунктуальность', 5),
('Поведение', 4), ('Библия', 4), ('Ручка/тетрадь', 4), ('Галстук на Богослужении', 5), ('Специализация', 10);

insert into users(login, password, role_id, name)
values ('admin', '$2a$10$SdGnZ4OyVPTWf.EGjljD1.lzS2Nt9gUj2CSSR90Isoqdf2gBM7b/C', 1, 'admin'),
       ('user', '$2a$10$KIH3lk/U7xlD5cpugEG.COJpja47vyo0bowimelzS5poHhCu4WYfa', 3, 'user'),
       ('dir', '$2a$10$k9E9KzDf8hciEIAuSJF3ZO883TTqegesnORcC8sDU83JTdpRpGYp6', 2, 'dir');