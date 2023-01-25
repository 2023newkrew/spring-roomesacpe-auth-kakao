DROP TABLE IF EXISTS RESERVATION;

DROP TABLE IF EXISTS THEME;

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
    primary key (id),
    foreign key (theme_name) references THEME(name) on delete cascade,
    unique (date, time, theme_name)
);