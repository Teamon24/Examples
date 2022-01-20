CREATE TABLE users (
    id integer NOT NULL
);

CREATE TABLE product_pages (
    id         integer,
    product_id integer,
    comment_id integer
);

CREATE TABLE products (
    id         integer,
    company_id integer NOT NULL
);

CREATE TABLE product_costs (
    id   integer,
    cost float NOT NUll,
    data date  NOT NULL
);

CREATE TABLE sales (
    id         integer,
    user_id    integer,
    product_id integer NOT NULL
);

CREATE TABLE "order" (
    id      integer,
    sale_id integer NOT NULL
);

CREATE TABLE companies (
    id      integer,
    city_id integer NOT NULL
);

CREATE TABLE cities (
    id       integer,
    state_id integer NOT NULL
);

CREATE TABLE states (
    id integer NOT NULL
)

