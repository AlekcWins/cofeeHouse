package ru.cofee.house.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class IndexController {
    @GetMapping
    public RedirectView index(){
        return new RedirectView("items");
    }
}
