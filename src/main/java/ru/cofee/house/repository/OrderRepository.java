package ru.cofee.house.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.cofee.house.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}