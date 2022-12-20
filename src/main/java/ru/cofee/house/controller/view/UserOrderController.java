package ru.cofee.house.controller.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.cofee.house.controller.api.OrderController;
import ru.cofee.house.model.Order;

import java.util.List;

@Controller
public class UserOrderController {
    private final OrderController orderController;

    @Autowired
    public UserOrderController(OrderController orderController) {
        this.orderController = orderController;
    }

    @GetMapping("/user_order")
    public String cartShop(Model model, Authentication authentication) {
        List<Order> activeOrders = orderController.getMyActiveOrders(authentication);
        List<Order> completeOrders = orderController.getMyCompleteOrders(authentication);
        model.addAttribute("activeOrders", activeOrders);
        model.addAttribute("completeOrders", completeOrders);
        return "allUserOrder";
    }
}
