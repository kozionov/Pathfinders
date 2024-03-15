--liquibase formatted sql

--changeset kozionov:2023-11-02-001-data
insert into authors(full_name)
values ('Author_1'), ('Author_2'), ('Author_3');

insert into genres(name)
values ('Genre_1'), ('Genre_2'), ('Genre_3'),
       ('Genre_4'), ('Genre_5'), ('Genre_6');

insert into books(title, author_id)
values ('BookTitle_1', 1), ('BookTitle_2', 2), ('BookTitle_3', 3);

insert into books_genres(book_id, genre_id)
values (1, 1),   (1, 2),
       (2, 3),   (2, 4),
       (3, 5),   (3, 6);

insert into comments(text, book_id)
values ('Comment_1', 1), ('Comment_2', 1), ('Comment_2', 2), ('Comment_3', 3);

insert into role(role_name)
values ('ADMIN'), ('DIRECTOR'), ('USER');

insert into users(login, password, role_id, name)
values ('admin', '$2a$10$SdGnZ4OyVPTWf.EGjljD1.lzS2Nt9gUj2CSSR90Isoqdf2gBM7b/C', 1, 'admin'), ('user', '$2a$10$KIH3lk/U7xlD5cpugEG.COJpja47vyo0bowimelzS5poHhCu4WYfa', 3, 'user');