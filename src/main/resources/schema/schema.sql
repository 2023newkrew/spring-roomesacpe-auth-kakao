DROP TABLE IF EXISTS RESERVATION;

DROP TABLE IF EXISTS THEME;

DROP TABLE IF EXISTS MEMBER;


CREATE TABLE MEMBER
(
    id          bigint not null auto_increment,
    username    varchar(20) not null,
    password    varchar(255) not null,
    name        varchar(20) not null,
    phone       varchar(20) not null,
    primary key (id)
);

CREATE TABLE THEME
(
    id          bigint not null auto_increment,
    name        varchar(20),
    desc        varchar(255),
    price       int,
    primary key (id),
    unique (name)
);

CREATE TABLE RESERVATION
(
    id          bigint not null auto_increment,
    date        date,
    time        time,
    name        varchar(20),
    theme_name  varchar(20),
    theme_desc  varchar(255),
    theme_price int,
    member_id   bigint not null,
    primary key (id),
    foreign key (theme_name) references THEME(name) on delete cascade,
    foreign key (member_id) references MEMBER(id) on delete cascade,
    unique (date, time, theme_name)
);