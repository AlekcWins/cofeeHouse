create table likes_dislikes
(
    id       SERIAL PRIMARY KEY NOT NULL,
    likes    INTEGER            NOT NULL,
    dislikes INTEGER            NOT NULL,
    id_item  INTEGER            NOT NULL,
    UNIQUE (id_item),
    CONSTRAINT fk_items_likes
        FOREIGN KEY (id_item)
            REFERENCES items (id)
);

