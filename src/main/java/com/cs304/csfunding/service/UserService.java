package com.cs304.csfunding.service;

import com.cs304.csfunding.api.UserDTO;
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

    public String testInsert(UserDTO userDTO) {
        User user = new User();
        user.setID(Long.toString(System.currentTimeMillis()));
        user.setKey(userDTO.getKey());
        user.setPhoneNum(userDTO.getPhoneNum());
        user.setName(userDTO.getName());
        user.setIsAdmin(false);
        userMapper.addUser(user);
        return "";
    }

    public List<User> testQueryAll(){
        return userMapper.getAllUser();
    }

    public User testQueryByID(String id){
        return userMapper.findUserById(id);
    }

    public User testQueryByPhoneNum(String phoneNum){
        return userMapper.findUserByPhoneNum(phoneNum);
    }

}
