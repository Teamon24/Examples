CREATE SEQUENCE examples.carts_ids_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;

CREATE SEQUENCE examples.items_ids_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;

CREATE TABLE examples.carts (
    id int NOT NULL,
    PRIMARY KEY (id));

CREATE TABLE examples.items (
    id int NOT NULL,
    cart_id int NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT carts_fk_1hsl3h FOREIGN KEY (cart_id) REFERENCES examples.carts (id));