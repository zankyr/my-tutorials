package com.rz.springboot.rest.testing.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HomeControllerV2Test {
    @Autowired
    private HomeControllerV2 homeControllerV2;

    @Test
    public void contextLoad(){
        assertThat(homeControllerV2).isNotNull();
    }

}
