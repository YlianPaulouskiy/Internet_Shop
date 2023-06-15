SET  schema 'public';

DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS addresses CASCADE;
DROP TABLE IF EXISTS orders CASCADE;
DROP TABLE IF EXISTS products CASCADE;
DROP TABLE IF EXISTS order_product CASCADE;

CREATE TABLE users
(
    id        serial PRIMARY KEY,
    name      varchar(128) NOT NULL,
    last_name varchar(128) NOT NULL,
    email     varchar(256) UNIQUE NOT NULL ,
    password varchar(128) NOT NULL,
    phone     varchar(20)
);


CREATE TABLE products
(
    id          serial PRIMARY KEY,
    name        varchar(128)  NOT NULL UNIQUE ,
    description text          NOT NULL,
    price       decimal(8, 2) NOT NULL,
    user_id int REFERENCES users(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE addresses
(
    id       serial PRIMARY KEY,
    city     varchar(128) NOT NULL,
    street   varchar(128) NOT NULL,
    house    varchar(10)  NOT NULL,
    flat     varchar(10),
    user_id  int UNIQUE NOT NULL REFERENCES users(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE orders
(
    id                serial PRIMARY KEY,
    registration_time timestamp   NOT NULL,
    user_id           int REFERENCES users(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE order_product
(
    order_id   int REFERENCES orders(id) ON UPDATE CASCADE ON DELETE CASCADE,
    product_id int REFERENCES products(id) ON UPDATE CASCADE ON DELETE CASCADE,
    PRIMARY KEY (order_id, product_id)
);