DROP TABLE IF EXISTS reservation;
DROP TABLE IF EXISTS theme;
DROP TABLE IF EXISTS schedule;
DROP TABLE IF EXISTS member;

CREATE TABLE reservation
(
    id          bigint      not null auto_increment,
    schedule_id bigint      not null,
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
    admin    bool        not null,
    primary key (id)
);

INSERT INTO member(username, password, name, phone, admin) VALUES ('admin', 'P@ssw0rd', 'admin', '010-1234-5678', true);
INSERT INTO member(username, password, name, phone, admin) VALUES ('username', 'password', 'name', '010-0000-0000', false);
INSERT INTO theme(name, desc, price) VALUES ('테스트 테마', '테스트 테마 설명', 1000);
INSERT INTO schedule(theme_id, date, time) VALUES (1, '2022-08-11', '13:00');
INSERT INTO schedule(theme_id, date, time) VALUES (1, '2022-08-11', '14:00');
INSERT INTO reservation(schedule_id, member_id) VALUES (1, 2);
INSERT INTO reservation(schedule_id, member_id) VALUES (1, 1);
