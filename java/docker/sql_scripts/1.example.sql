
CREATE SCHEMA IF NOT EXISTS examples;
CREATE SEQUENCE examples.hibernate_sequence START 1;

CREATE TABLE examples.user
(
    id        varchar(50) NOT NULL PRIMARY KEY,
    full_name varchar(50),
    password  varchar(30) NOT NULL,
    email     varchar(50)
);