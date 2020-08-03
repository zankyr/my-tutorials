package com.rz.springboot.rest.testing.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HomeControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private HomeController homeController;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @BeforeEach
    void sanityCheck() {
        assertThat(homeController).isNotNull();
        assertThat(testRestTemplate).isNotNull();
    }

    @Test
    public void whenGreeting_thenReturnDefaultMessage() {
        assertThat(this.testRestTemplate
                .getForObject("http://localhost:" + port + "/", String.class)
        ).contains("Hello, World!");
    }


}
