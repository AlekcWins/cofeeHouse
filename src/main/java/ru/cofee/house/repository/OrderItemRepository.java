package ru.cofee.house.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.cofee.house.model.OrderItem;

import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    Optional<OrderItem> findByItemIdAndOrderId(Long itemId, Long orderId);

}