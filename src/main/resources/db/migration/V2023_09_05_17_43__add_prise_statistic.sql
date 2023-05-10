create table price_statistics
(
    id           SERIAL PRIMARY KEY NOT NULL,
    old_cost     FLOAT              NOT NULL,
    new_cost     FLOAT              NOT NULL,
    id_item      INTEGER            NOT NULL,
    created_date DATE               NOT NULL default CURRENT_DATE,
    UNIQUE (id_item),
    CONSTRAINT fk_items_price_statistics
        FOREIGN KEY (id_item)
            REFERENCES items (id)
);

