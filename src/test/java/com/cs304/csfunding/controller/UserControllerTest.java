package com.cs304.csfunding.controller;

import com.cs304.csfunding.api.Result;
import com.cs304.csfunding.api.UserDTO;
import com.cs304.csfunding.entity.User;
import com.cs304.csfunding.service.UserService;
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

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

public class UserControllerTest {

    private MockMvc mockMvc;
    @Mock
    private UserService userService;
    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testAddUser() throws Exception {
        UserDTO userDTO = new UserDTO(); // Create a UserDTO object with test data

        when(userService.testInsert(userDTO)).thenReturn("");

        mockMvc.perform(MockMvcRequestBuilders.post("/add-user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(""));
    }

    @Test
    public void testGetAllUser() throws Exception {
        List<User> users = Arrays.asList(new User(), new User()); // Create a list of User objects with test data

        when(userService.queryAll()).thenReturn(users);

        mockMvc.perform(MockMvcRequestBuilders.get("/all-users"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("success"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.length()").value(2));
    }

    @Test
    public void testGetAllAdmin() throws Exception {
        List<User> admins = Arrays.asList(new User(), new User()); // Create a list of User objects with test data

        when(userService.testQueryAllAdmin()).thenReturn(admins);

        mockMvc.perform(MockMvcRequestBuilders.get("/getalladmins"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));
    }

    @Test
    public void testGetUserByID_existingUser() throws Exception {
        String id = "123";
        User user = new User(); // Create a User object with test data

        when(userService.queryByID(id)).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/user-id")
                        .param("id", id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("success"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isNotEmpty());
    }

    @Test
    public void testGetUserByID_nonExistingUser() throws Exception {
        String id = "123";

        when(userService.queryByID(id)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/user-id")
                        .param("id", id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(404))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("user not found"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty());
    }


    @Test
    public void testGetCurrentUser_nonExistingUser() throws Exception {
        int uuid = 123;

        when(userService.queryByUuid(uuid)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/current-user"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(404))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("user not found"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty());
    }

    @Test
    public void testGetUserByPhoneNum_existingUser() throws Exception {
        String phoneNum = "1234567890";
        User user = new User(); // Create a User object with test data

        when(userService.queryByPhoneNum(phoneNum)).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/user-phone")
                        .param("id", phoneNum))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("success"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isNotEmpty());
    }

    @Test
    public void testGetUserByPhoneNum_nonExistingUser() throws Exception {
        String phoneNum = "1234567890";

        when(userService.queryByPhoneNum(phoneNum)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/user-phone")
                        .param("id", phoneNum))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(404))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("user not found"))
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
