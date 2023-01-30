DELETE theme;
DELETE reservation;
DELETE member;
DELETE schedule;

CREATE TABLE if not exists reservation
(
    id          bigint      not null auto_increment,
    schedule_id bigint      not null,
    name        varchar(20) not null,
    primary key (id)
    );

CREATE TABLE if not exists theme
(
    id    bigint       not null auto_increment,
    name  varchar(20)  not null,
    desc  varchar(255) not null,
    price int          not null,
    primary key (id)
    );

CREATE TABLE if not exists schedule
(
    id       bigint not null auto_increment,
    theme_id bigint not null,
    date     date   not null,
    time     time   not null,
    primary key (id)
    );

CREATE TABLE if not exists member
(
    id       bigint      not null auto_increment,
    username varchar(20) not null,
    password varchar(20) not null,
    name     varchar(20) not null,
    phone    varchar(20) not null,
    role     varchar(20) not null,
    primary key (id)
    );



INSERT INTO member (username, password, name, phone, role)
VALUES('kayla', 'password1', 'kb', '010-9514-4010', 'admin'),
      ('userA', 'qwer12345', 'june', '010-1234-5678', 'user'),
      ('userB', 'abcd12345', 'jj', '010-1234-5678', 'user');

INSERT INTO theme (name, desc, price)
VALUES('타임머신', '과거에서 탈출하기', 10000),
      ('나홀로집에', '집에서 탈출하기', 30000),
      ('비행기', '비행기에서 탈출하기', 50000);

INSERT INTO schedule (theme_id, date, time)
VALUES(1, '2023-01-01', '10:00'),
      (3, '2023-02-01', '10:00'),
      (1, '2023-02-01', '10:00');

INSERT INTO reservation (schedule_id, name)
VALUES(1, 'userA');