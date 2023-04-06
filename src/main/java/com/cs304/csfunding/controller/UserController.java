package com.cs304.csfunding.controller;

import com.cs304.csfunding.api.UserDTO;
import com.cs304.csfunding.entity.User;
import com.cs304.csfunding.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping(value = "/register/user")
    public String testAddUser(@RequestBody UserDTO userDTO) {
       return userService.testInsert(userDTO);
    }

    @GetMapping("/getallusers")
    public List<User> getAllUser(){
        return userService.testQueryAll();
    }

    @GetMapping("/selectuserbyid")
    public User getUserByID(String id){
        return userService.testQueryByID(id);
    }

    @GetMapping("/selectuserbyphonenum")
    public User getUserByPhoneNum(String id){
        return userService.testQueryByPhoneNum(id);
    }
}
