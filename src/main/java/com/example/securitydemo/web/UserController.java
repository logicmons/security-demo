package com.example.securitydemo.web;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {
    @GetMapping("/login")
    public String  login(Model model){
        System.out.println("logingggg");
        return "login";
    }

    @RequestMapping("/")
    public String index() {
        return "index";
    }

//    @GetMapping("/error")
    @GetMapping("/hello1")
    public ResponseEntity<Void> hello1(){
        System.out.println("hello1");
        return ResponseEntity.ok().build();
    }

}
