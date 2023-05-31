package ru.cofee.house.controller.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.cofee.house.controller.api.OrderController;
import ru.cofee.house.model.Order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class Comments {

    @GetMapping("comments")
    public String comments(Model model) {
            List<HashMap<String,String>> list = new ArrayList<>();
            list.add(new HashMap<>(){{
                put("id","felitsiya");
                put("oldCost","Отличный кофе");;
            }});
        model.addAttribute("items", list);
        return "admin/order/comments";
    }
}