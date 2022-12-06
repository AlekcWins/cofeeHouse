package ru.cofee.house.controller.api;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.cofee.house.model.Order;
import ru.cofee.house.model.OrderItem;
import ru.cofee.house.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService service;

    @Autowired
    public OrderController(OrderService service) {
        this.service = service;
    }

    //todo dto
    @GetMapping
    public Order getMyOrder(Authentication authentication) {
        return service.findMyDraftOrder(authentication.getName());
    }

    @PostMapping("/confirm")
    public Order confirm(Authentication authentication) {
        Order order = service.confirmMyOrder(authentication.getName());
        return order;
    }

    //todo dto
    @PostMapping
    public ResponseEntity<Order> addOrderItem(Authentication authentication, long itemId) {
        //todo exception need??
        try {
            return ResponseEntity.ok(service.addOrderItem(authentication.getName(), itemId));
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/count")
    public int getMyOrderCount(Authentication authentication) {
        return service.findMyDraftOrder(authentication.getName()).getItems()
                .stream()
                .mapToInt(OrderItem::getCount)
                .reduce(0, Integer::sum);
    }


    @GetMapping("/all_work")
    public List<Order> getAllForWork() {
        return service.getAllForWork();
    }


    public List<Order> allComplete() {
        return service.allComplete();
    }
}
