package com.cs304.csfunding.service.impl;

import com.cs304.csfunding.api.LoginDTO;
import com.cs304.csfunding.api.Result;
import com.cs304.csfunding.entity.User;
import com.cs304.csfunding.mapper.UserMapper;
import com.cs304.csfunding.service.LoginService;
import com.cs304.csfunding.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Result login(LoginDTO loginDTO) {
        if (StringUtils.isEmpty(loginDTO.getName())) {
            return new Result(400, "账号不能为空", "");
        }
        if (StringUtils.isEmpty(loginDTO.getKey())) {
            return new Result(400, "密码不能为空", "");
        }
        //通过登录名查询用户
        User user = userMapper.findUserById(loginDTO.getName());
        //比较密码

        if (user != null && user.getKey().equals(loginDTO.getKey())) {
            String token = JwtUtil.createToken(user);
            Map<String, Object> loginResult = new HashMap<>();
            loginResult.put("user", user);
            loginResult.put("token", token);
            return new Result(200, "success", loginResult);
        }
        return new Result(400, "登录失败", "");
    }
}
