package com.cs304.csfunding;

import com.cs304.csfunding.api.ApplyDTO;
import com.cs304.csfunding.controller.ApplyController;
import com.cs304.csfunding.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CsFundingApplicationTests {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ApplyController applyController;

    @Test
    void contextLoads() {
        System.out.println(userMapper.findUserById("tesf"));
    }


}
