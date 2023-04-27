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

    public List<User> queryAll(){
        return userMapper.getAllUser();
    }
    
    public List<User> testQueryAllAdmin(){
        return userMapper.getAllAdmin();
    }

    public User queryByID(String id){
        return userMapper.findUserById(id);
    }

    public User queryByPhoneNum(String phoneNum){
        return userMapper.findUserByPhoneNum(phoneNum);
    }

    public User queryByUuid(int uuid){
        return userMapper.findUserByUuid(uuid);
    }

}
