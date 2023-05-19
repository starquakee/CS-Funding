package com.cs304.csfunding.controller;

import com.cs304.csfunding.api.Result;
import com.cs304.csfunding.api.User_ResearchGroupDTO;
import com.cs304.csfunding.service.User_ResearchGroupService;
import com.cs304.csfunding.service.ResearchGroupService;
import com.cs304.csfunding.util.HttpContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class User_ResearchGroupController {
    @Autowired
    private User_ResearchGroupService user_researchGroupService;
    @Autowired
    private ResearchGroupService researchGroupService;


    @PostMapping(value = "/register/user_researchgroup")
    public String testAddUserResearchGroup(@RequestBody User_ResearchGroupDTO user_researchGroupDTO) {
        System.out.println(user_researchGroupDTO.getUserUUID());
        System.out.println(user_researchGroupDTO.getResearchGroupUUID());
        return user_researchGroupService.testInsert(user_researchGroupDTO);
    }

    @GetMapping("/selectresearchgroupbyuser")
    public Result getResearchGroupByUser(@RequestParam int uid) {
        return new Result(user_researchGroupService.testQueryByUser(uid));
    }

//    @GetMapping("/user-group-data")
//    public Result getGroupDataByUser(@RequestParam int uid) {
//        List<Integer> groups = user_researchGroupService.testQueryByUser(uid);
//        for (int gid : groups) {
//            researchGroupService.testQueryByUser()
//        }
//    }

    @GetMapping("/current-group-id")
    public Result getCurrentGroup() {
        return getResearchGroupByUser(HttpContextUtil.getRequestUuid());
    }
}
