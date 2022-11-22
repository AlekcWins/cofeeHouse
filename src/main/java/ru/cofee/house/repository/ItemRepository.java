package ru.cofee.house.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.cofee.house.model.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
}