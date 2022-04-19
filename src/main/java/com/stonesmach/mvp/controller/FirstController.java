package com.stonesmach.mvp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class FirstController {

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String kakashka) {
        return String.format("Hello %s!", kakashka);
    }
}
