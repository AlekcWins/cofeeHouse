-- todo add create table for user


CREATE table orders
(
    id     SERIAL PRIMARY KEY NOT NULL,
    status VARCHAR(255)       NOT NULL
);

CREATE table items
(
    id       SERIAL PRIMARY KEY NOT NULL,
    capacity float              NOT NULL,
    cost     float              not NULL,
    name     varchar(255)       NOT NULL,
    path_img  varchar(255)       NOT NULL
);

CREATE table order_item
(
    id_order INTEGER NOT NULL,
    id_item  INTEGER NOT NULL
);


ALTER TABLE order_item
    ADD FOREIGN KEY (id_order)
        REFERENCES orders (id);


ALTER TABLE order_item
    ADD FOREIGN KEY (id_item)
        REFERENCES items (id);




