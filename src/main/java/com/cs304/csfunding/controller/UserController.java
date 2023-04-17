package com.cs304.csfunding.controller;

import com.cs304.csfunding.api.Result;
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
    public Result getAllUser(){
        return new Result(200, "查找所有用户成功", userService.testQueryAll());
    }

    @GetMapping("/selectuserbyid")
    public Result getUserByID(String id){
        return new Result(200, "通过id查找用户成功", userService.testQueryByID(id));
    }

    @GetMapping("/selectuserbyphonenum")
    public Result getUserByPhoneNum(String id){
        return new Result(200, "通过电话查找所有用户成功", userService.testQueryByPhoneNum(id));
    }
}
