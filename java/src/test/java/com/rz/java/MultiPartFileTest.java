package com.rz.java;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(MultiPartFileController.class)
public class MultiPartFileTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void upload_ShouldReturn200() throws Exception {
        MockMultipartFile mockMultipartFile =
                new MockMultipartFile(
                        "file", // Should be same name as the param in the method
                        "fileName",
                        "multipart/form-data",
                        "test data".getBytes()
                );

        mockMvc.perform(
                fileUpload("/api/v1/upload").file(mockMultipartFile)
        )
                .andExpect(status().isOk());
    }
}
