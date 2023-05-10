create table regular_customers
(
    id           SERIAL PRIMARY KEY NOT NULL,
    user_id      INT                NOT NULL,
    count_orders INTEGER            NOT NULL,
    CONSTRAINT fk_user_regular_customers
        FOREIGN KEY (user_id)
            REFERENCES users (id)
);
