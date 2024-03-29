package ru.cofee.house.controller.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.audit.AuditEvent;
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
import ru.cofee.house.model.AuditItem;
import ru.cofee.house.model.Item;
import ru.cofee.house.model.Order;
import ru.cofee.house.repository.AuditLogRepository;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ItemController itemRestController;
    private final OrderController orderController;
    private final AuditLogRepository repository;

    @Autowired
    public AdminController(ItemController itemRestController,
                           OrderController orderController,
                           AuditLogRepository repository) {
        this.itemRestController = itemRestController;
        this.orderController = orderController;
        this.repository = repository;
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

    @GetMapping("/products/edit")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editProduct(Model model, @RequestParam() long id) {
        Item item = itemRestController.getItem(id);
        model.addAttribute("item", item);
        return "admin/product/edit";
    }

    @GetMapping("all_work")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String allWork(Model model) {
        List<Order> items = orderController.getAllForWork();
        model.addAttribute("items", items);
        return "admin/order/all_work";
    }

    @GetMapping("statistic")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String statistic(Model model) {
        List<HashMap<String, String>> items = orderController.statistic();
        model.addAttribute("items", items);
        return "admin/order/statistic";
    }

    @GetMapping("all_complete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String allComplete(Model model) {
        List<Order> items = orderController.allComplete();
        model.addAttribute("items", items);
        return "admin/order/all_complete";
    }

    @GetMapping("audit_log")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String auditLog(Model model) {
        List<AuditItem> auditEvents = repository.findAll()
                .stream()
                .sorted(Comparator.comparing(AuditItem::getTimestamp).reversed())
                .collect(Collectors.toList());
        model.addAttribute("items", auditEvents);
        return "admin/audit_log";
    }

}
