package com.cs304.csfunding.controller;
import com.cs304.csfunding.api.Result;
import com.cs304.csfunding.api.User_ResearchGroupDTO;
import com.cs304.csfunding.service.User_ResearchGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class User_ResearchGroupController {
    @Autowired
    private User_ResearchGroupService user_researchGroupService;


    @PostMapping(value = "/register/user_researchgroup")
    public Result testAddUserResearchGroup(@RequestBody User_ResearchGroupDTO user_researchGroupDTO) {
        System.out.println(user_researchGroupDTO.getUserUUID());
        System.out.println(user_researchGroupDTO.getResearchGroupUUID());
        return new Result(user_researchGroupService.testInsert(user_researchGroupDTO));
    }

    @GetMapping("/selectresearchgroupbyuser")
    public Result getResearchGroupByUser(int user_UUID){
        return new Result(user_researchGroupService.testQueryByUser(user_UUID));
    }
}
