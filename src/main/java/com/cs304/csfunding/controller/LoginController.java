package com.cs304.csfunding.controller;

import com.cs304.csfunding.api.LoginDTO;
import com.cs304.csfunding.api.Result;
import com.cs304.csfunding.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    LoginService loginService;

    @GetMapping(value = "/api/login")
    public Result login(@RequestBody LoginDTO loginDTO){
        return loginService.login(loginDTO);
    }
}
