package com.example.user_service;

import com.example.user_service.controller.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturn201WhenValidEmailProvided() throws Exception {
        mockMvc.perform(post("/api/users")
                .contentType("application/json")
                .content("""
                        {"email": "user@example.com"}
                        """))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldReturn404WhenInvalidEmailProvided() throws Exception {
        mockMvc.perform(post("/api/users")
                        .contentType("application/json")
                        .content("""
                        {"email": "not-an-email"}
                        """))
                .andExpect(status().isBadRequest());
    }
}
