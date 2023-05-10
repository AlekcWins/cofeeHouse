create table comments
(
    id           SERIAL PRIMARY KEY NOT NULL,
    comment      varchar(1000)      NOT NULL,
    user_id      INT                NOT NULL,
    id_item      INT                NOT NULL,
    created_date DATE               NOT NULL default CURRENT_DATE,
    CONSTRAINT fk_items_comments
        FOREIGN KEY (id_item)
            REFERENCES items (id),
    CONSTRAINT fk_user_comments
        FOREIGN KEY (user_id)
            REFERENCES users (id)
);
