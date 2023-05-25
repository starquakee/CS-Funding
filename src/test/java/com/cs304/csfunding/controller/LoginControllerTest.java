package com.cs304.csfunding.controller;

import com.cs304.csfunding.api.LoginDTO;
import com.cs304.csfunding.api.Result;
import com.cs304.csfunding.service.LoginService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;

import static org.mockito.Mockito.when;

public class LoginControllerTest {

    private MockMvc mockMvc;

    @Mock
    private LoginService loginService;
    @InjectMocks
    private LoginController loginController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
    }

    @Test
    public void testLogin() throws Exception {
        LoginDTO loginDTO = new LoginDTO(); // Create a LoginDTO object with test data
        loginDTO.setName("admin");
        loginDTO.setKey("123");

        Result expectedResult = new Result(200, "success", new HashMap<String,Object>()); // Create the expected result

        when(loginService.login(loginDTO)).thenReturn(expectedResult);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(loginDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(expectedResult.getCode()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(expectedResult.getMessage()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty());
    }

    @Test
    public void testLoginFail() throws Exception {
        LoginDTO loginDTO = new LoginDTO(); // Create a LoginDTO object with test data
        loginDTO.setName("admin_wrong");
        loginDTO.setKey("123");

        Result expectedResult = new Result(400, "登录失败",""); // Create the expected result

        when(loginService.login(loginDTO)).thenReturn(expectedResult);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(loginDTO)))

                .andExpect(MockMvcResultMatchers.status().isOk())

                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("登录失败"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("400"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty());
    }

    // Helper method to convert an object to JSON string
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
