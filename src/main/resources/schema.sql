/**
  RESERVATION TABLE
  id            : pk
  schedule_id   : SCHEDULE의 pk (fk)
  member_id     : MEMBER의 pk(fk)
 */
CREATE TABLE reservation
(
    id          bigint      not null auto_increment,
    schedule_id bigint      not null,
    member_id   bigint      not null,
    primary key (id)
);

/**
    id          : THEME 의 pk
    name        : THEME의 이름
    desc        : THEME의 설명
    price       : THEME의 1인당 가격
 */

CREATE TABLE theme
(
    id    bigint       not null auto_increment,
    name  varchar(20)  not null,
    desc  varchar(255) not null,
    price int          not null,
    primary key (id)
);

/**
    id          : SCHEDULE PK
    theme_id    : THEME PK(FK)
    date        : 스케쥴이 실행되는 날
    time        : 스케쥴이 실행되는 시간
 */

CREATE TABLE schedule
(
    id       bigint not null auto_increment,
    theme_id bigint not null,
    date     date   not null,
    time     time   not null,
    primary key (id)
);

/**
    id          : MEMBER PK
    username    : MEMBER의 Login Id
    password    : MEMBER의 Password
    name        : MEMBER의 실명
    phone       : MEMBER의 phone 번호

 */

CREATE TABLE member
(
    id       bigint      not null auto_increment,
    username varchar(20) not null,
    password varchar(20) not null,
    name     varchar(20) not null,
    phone    varchar(20) not null,
    primary key (id)
);