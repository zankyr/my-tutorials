package com.rz;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping("/")
    public String getHello() {
        return "Greetings from Spring Boot!";
    }
}
