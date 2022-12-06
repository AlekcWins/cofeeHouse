ALTER TABLE order_item
    ADD count int;


ALTER TABLE order_item
    ADD UNIQUE (id_order, id_item);