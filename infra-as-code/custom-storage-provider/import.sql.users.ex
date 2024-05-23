create table if not exists users(
    username varchar(64) not null primary key,
    password varchar(64),
    email varchar(128),
    firstName varchar(128),
    lastName varchar(128),
);