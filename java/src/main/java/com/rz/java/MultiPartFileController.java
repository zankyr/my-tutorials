package com.rz.java;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class MultiPartFileController {

    @PostMapping("/api/v1/upload")
    void upload(@RequestParam("file") MultipartFile file) {


    }
}
