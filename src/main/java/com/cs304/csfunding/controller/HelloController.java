package com.cs304.csfunding.controller;

import com.cs304.csfunding.entity.User;
import com.cs304.csfunding.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HelloController {

    @Autowired
    private UserService userService;

    @GetMapping("/hello")
    public String getHello(){
        return "Hello World";
    }

    @GetMapping("/register")
    public String testAddUser() {
       return userService.testInsert();
    }

    @GetMapping("/getall")
    public List<User> getAllUser(){
        return userService.testQuery();
    }

//    @GetMapping("/test")
//    public String testQuery(){
//        return userService.testSelect();
//    }
}
