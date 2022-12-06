package ru.cofee.house.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.cofee.house.model.Order;
import ru.cofee.house.model.Status;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findOrderByUserName(String userName);

    Optional<Order> findOrderByUserNameAndStatus(String userName, Status status);
}