package com.cs304.csfunding.service;

import com.cs304.csfunding.entity.User;
import com.cs304.csfunding.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {
    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

//    List<User> getList() {
//        return IService.super.list();
//    }

//    String insertUser(){
//        try {
//
//        }
//    }

    public String testInsert() {
        User user = new User();
        user.setID(Long.toString(System.currentTimeMillis()));
        user.setKey(Long.toString(System.currentTimeMillis()));
        user.setIsAdmin(false);
        userMapper.addUser(user);
        return "";
    }

    public List<User> testQuery(){
        return userMapper.getAllUser();
    }

}
