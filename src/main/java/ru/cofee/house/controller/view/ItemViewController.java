package ru.cofee.house.controller.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.cofee.house.controller.api.ItemController;
import ru.cofee.house.controller.api.OrderController;
import ru.cofee.house.core.dto.ItemDto;
import ru.cofee.house.model.Order;

import java.util.List;

@Controller
@RequestMapping("items")
public class ItemViewController {
    private final ItemController controller;
    private final OrderController orderController;

    @Autowired
    public ItemViewController(ItemController controller, OrderController orderController) {
        this.controller = controller;
        this.orderController = orderController;
    }

    @GetMapping
    public String getItems(Model model) {
        List<ItemDto> items = controller.getItems();
        model.addAttribute("items", items);
        return "itemsList";
    }

    @GetMapping("/cart")
    public String cartShop(Model model, Authentication authentication) {
        Order myOrder = orderController.getMyOrder(authentication);
        model.addAttribute("order", myOrder);
        return "cartShop";
    }
}
