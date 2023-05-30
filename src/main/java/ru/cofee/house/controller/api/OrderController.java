package ru.cofee.house.controller.api;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.cofee.house.model.Order;
import ru.cofee.house.model.OrderItem;
import ru.cofee.house.service.OrderService;

import java.util.ArrayList;
import java.util.HashMap;
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

    //todo param
    @PostMapping("/issue")
    public ResponseEntity<Order> issue(long orderId) {
        try {
            return ResponseEntity.ok(service.issueOrder(orderId));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //todo dto
    //    todo path param
    @PostMapping
    public ResponseEntity<Order> addOrderItem(Authentication authentication, long itemId) {
        try {
            return ResponseEntity.ok(service.addOrderItem(authentication.getName(), itemId));
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping
    //todo dto
    //    todo path param
    public ResponseEntity<Order> updateOrderItem(Authentication authentication, long itemId, int count) {
        try {
            return ResponseEntity.ok(service.updateOrderItem(authentication.getName(), itemId, count));
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    //todo dto
    //    todo path param
    @DeleteMapping
    public ResponseEntity<Order> deleteOrderItem(Authentication authentication, long itemId) {
        try {
            return ResponseEntity.ok(service.deleteOrderItem(authentication.getName(), itemId));
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/count")
    @PreAuthorize("hasRole('ROLE_USER')")
    public int getMyOrderCount(Authentication authentication) {
        return service.findMyDraftOrder(authentication.getName()).getItems()
                .stream()
                .mapToInt(OrderItem::getCount)
                .reduce(0, Integer::sum);
    }


    @GetMapping("/all_work")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Order> getAllForWork() {
        return service.getAllForWork();
    }

    @GetMapping("/statistic")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<HashMap<String,String>> statistic() {
        List<HashMap<String,String>> list = new ArrayList<>();
        list.add(new HashMap<>(){{
            put("id","1");
            put("oldCost","400");
            put("newCost","450");
            put("idItem","3");
            put("date","16/05/2023");
        }});
        return list;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Order> allComplete() {
        return service.allComplete();
    }

    public List<Order> getMyActiveOrders(Authentication authentication) {
        return service.getMyActiveOrders(authentication.getName());
    }

    public List<Order> getMyCompleteOrders(Authentication authentication) {
        return service.getMyCompleteOrders(authentication.getName());
    }
}
