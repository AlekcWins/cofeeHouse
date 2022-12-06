package ru.cofee.house.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorsPageController {

    @GetMapping("403")
    public String page403(){
        return "errors/403";
    }
}
