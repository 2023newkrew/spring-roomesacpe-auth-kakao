DROP TABLE IF EXISTS member;
DROP TABLE IF EXISTS schedule;
DROP TABLE IF EXISTS theme;
DROP TABLE IF EXISTS reservation;

CREATE TABLE reservation
(
    id          bigint      not null auto_increment,
    schedule_id bigint      not null,
    name        varchar(20) not null,
    member_id   bigint      not null,
    primary key (id)
);

CREATE TABLE theme
(
    id    bigint       not null auto_increment,
    name  varchar(20)  not null,
    desc  varchar(255) not null,
    price int          not null,
    primary key (id)
);

CREATE TABLE schedule
(
    id       bigint not null auto_increment,
    theme_id bigint not null,
    date     date   not null,
    time     time   not null,
    primary key (id)
);

CREATE TABLE member
(
    id       bigint      not null auto_increment,
    username varchar(20) not null,
    password varchar(20) not null,
    name     varchar(20) not null,
    phone    varchar(20) not null,
    role     varchar(20) not null,
    primary key (id)
);

INSERT INTO member VALUES (9999, 'admin', 'admin', 'admin', '010-1234-5678', 'admin');
INSERT INTO member VALUES (9998, 'user', 'user', 'user', '010-1234-5678', 'user');
INSERT INTO member VALUES (9997, 'anotherUser', 'anotherUser', 'anotherUser', '010-1234-5678', 'user');

INSERT INTO theme VALUES (9999, '테마이름', '테마설명', 22000);

INSERT INTO schedule VALUES (9999, 9999, '2022-08-11', '13:00');

