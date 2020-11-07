package com.rz.springboot.rest.testing.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2")
public class HomeControllerV2 {

    @GetMapping("/")
    public @ResponseBody String greeting() {
        return "Hello, World!";
    }
}
