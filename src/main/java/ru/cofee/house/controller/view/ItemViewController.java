package ru.cofee.house.controller.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.cofee.house.controller.api.ItemController;
import ru.cofee.house.core.dto.ItemDto;

import java.util.List;

@Controller
@RequestMapping("items")
public class ItemViewController {
    private final ItemController controller ;

    @Autowired
    public ItemViewController(ItemController controller) {
        this.controller = controller;
    }

    @GetMapping
    public String getItems(Model model) {
        List<ItemDto> items = controller.getItems();
        model.addAttribute("items",items );
        return "itemsList";
    }
}
