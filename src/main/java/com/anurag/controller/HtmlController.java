package com.anurag.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller

public class HtmlController {
    @GetMapping("/")
    private String index(Model model) {
        String message ="Welcome to Snake And Ladder Game V1.0";
        model.addAttribute("message",message);
        return "index";
    }

}
