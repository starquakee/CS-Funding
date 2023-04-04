package com.cs304.csfunding.service.impl;

import com.cs304.csfunding.api.LoginDTO;
import com.cs304.csfunding.api.Result;
import com.cs304.csfunding.entity.User;
import com.cs304.csfunding.mapper.UserMapper;
import com.cs304.csfunding.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
            return new Result(200, "", user);
        }
        return new Result(400, "登录失败", "");
    }
}
