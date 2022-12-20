package ru.cofee.house.controller.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import ru.cofee.house.controller.api.ItemController;
import ru.cofee.house.controller.api.OrderController;
import ru.cofee.house.core.dto.ItemDto;
import ru.cofee.house.model.Order;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ItemController itemRestController;
    private final OrderController orderController;

    @Autowired
    public AdminController(ItemController itemRestController, OrderController orderController) {
        this.itemRestController = itemRestController;
        this.orderController = orderController;
    }

    @GetMapping
    public RedirectView index() {
        return new RedirectView("admin/products");
    }

    @GetMapping("/products")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String products(Model model, Authentication authentication, @RequestParam(defaultValue = "false", required = false) boolean withDeleted) {
        List<ItemDto> items = itemRestController.getItems(authentication, withDeleted);
        model.addAttribute("items", items);
        model.addAttribute("withDeleted", withDeleted);
        return "admin/products";
    }


    @GetMapping("/products/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addProduct(Model model) {
        return "admin/product/add";
    }

    @GetMapping("all_work")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String allWork(Model model) {
        List<Order> items = orderController.getAllForWork();
        model.addAttribute("items", items);
        return "admin/order/all_work";
    }

    @GetMapping("all_complete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String allComplete(Model model) {
        List<Order> items = orderController.allComplete();
        model.addAttribute("items", items);
        return "admin/order/all_complete";
    }


}
