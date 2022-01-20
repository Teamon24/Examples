CREATE SCHEMA IF NOT EXISTS examples;

CREATE TABLE examples.user
(
    id        varchar(50) NOT NULL PRIMARY KEY,
    full_name varchar(50),
    password  varchar(30) NOT NULL,
    email     varchar(50)
);