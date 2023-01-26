package com.hhgg.hhggbe.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/users")
public class UserController {

    @GetMapping("/login-page")
    public ModelAndView loginPage() {
        return new ModelAndView("login");
    }

}