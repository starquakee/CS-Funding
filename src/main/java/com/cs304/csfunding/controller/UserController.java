package com.cs304.csfunding.controller;

import com.cs304.csfunding.api.Result;
import com.cs304.csfunding.api.UserDTO;
import com.cs304.csfunding.entity.User;
import com.cs304.csfunding.service.UserService;
import com.cs304.csfunding.util.HttpContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping(value = "/register/user")
    public String testAddUser(@RequestBody UserDTO userDTO) {
        return userService.testInsert(userDTO);
    }

    @GetMapping("/all-users")
    public Result getAllUser() {
        return new Result(userService.queryAll());
    }

    @GetMapping("/getalladmins")
    public List<User> getAllAdmin(){
        return userService.testQueryAllAdmin();
    }

    @GetMapping("/user-id")
    public Result getUserByID(@RequestParam String id) {
        User user = userService.queryByID(id);
        if (user == null) {
            return new Result(404, "user not found", null);
        } else {
            return new Result(user);
        }

    }

    @GetMapping("/current-user")
    public Result getCurrentUser() {
        int uuid = HttpContextUtil.getRequestUuid();
        User user = userService.queryByUuid(uuid);
        if (user == null) {
            return new Result(404, "user not found", null);
        } else {
            return new Result(user);
        }
    }

    @GetMapping("/user-phone")
    public Result getUserByPhoneNum(String id) {
        User user = userService.queryByPhoneNum(id);
        if (user == null) {
            return new Result(404, "user not found", null);
        } else {
            return new Result(user);
        }
    }
}