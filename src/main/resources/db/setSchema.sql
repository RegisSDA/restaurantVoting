create schema if not exists PUBLIC;

create table if not exists RESTAURANTS
(
    NAME VARCHAR(255) not null
        primary key
);

create table if not exists REST_MENU
(
    RESTAURANT_NAME VARCHAR(255) not null,
    NAME            VARCHAR(255),
    PRICE           INTEGER      not null,
    foreign key (RESTAURANT_NAME) references RESTAURANTS
);

create table if not exists USERS
(
    LOGIN    VARCHAR(255) not null
        primary key,
    PASSWORD VARCHAR(255)
);

create table if not exists USER_ROLES
(
    USER_ID VARCHAR(255) not null,
    ROLE    VARCHAR(255),
    foreign key (USER_ID) references USERS
);

create table if not exists VOTES
(
    ID              INTEGER auto_increment
        primary key,
    VOTEDATE        DATE,
    RESTAURANT_NAME VARCHAR(255),
    USER_LOGIN      VARCHAR(255),
    foreign key (RESTAURANT_NAME) references RESTAURANTS,
    foreign key (USER_LOGIN) references USERS
);


