package com.cs304.csfunding.service;

import com.cs304.csfunding.api.RegisterDTO;
import com.cs304.csfunding.api.UserDTO;
import com.cs304.csfunding.entity.User;
import com.cs304.csfunding.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

@Service
@Transactional
public class UserService {
    private final UserMapper userMapper;
    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final Map<String, String> codeMap = new HashMap<>();

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public boolean checkReg(String reg, String code){
        return codeMap.containsKey(reg) && code.equals(codeMap.get(reg));
    }

    public void addUser(RegisterDTO registerDTO){
        User user = new User();
        user.setID(registerDTO.getUserId());
        user.setName(registerDTO.getName());
        user.setKey(registerDTO.getRegister_key());
        user.setPhoneNum("123456");
        user.setIsAdmin(false);
        userMapper.addUser(user);
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

    public List<User> queryAll() {
        return userMapper.getAllUser();
    }

    public List<User> testQueryAllAdmin() {
        return userMapper.getAllAdmin();
    }

    public User queryByID(String id) {
        return userMapper.findUserById(id);
    }

    public User queryByPhoneNum(String phoneNum) {
        return userMapper.findUserByPhoneNum(phoneNum);
    }

    public User queryByUuid(int uuid) {
        return userMapper.findUserByUuid(uuid);
    }

    public void addMailCode(String reg, String code) {
        codeMap.put(reg, code);
        this.removeMailRegTimer(reg);
        logger.info("Function done");
    }

    private void removeMailRegTimer(String reg) {
        new Thread(() -> {
            try {
                Thread.sleep(600000);
            } catch (Exception e) {
                logger.error(e.toString());
            }
            codeMap.remove(reg);
        }).start();
    }

}
