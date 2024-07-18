package com.vhs.rental.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class LogInController {
    @GetMapping("showMyLoginPage")
    public String showMyLoginPage(){
        return "login";
    }
    @GetMapping("/access-denied")
    public String accessDenied(){
        return "denied";
    }
}
