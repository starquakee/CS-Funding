package com.cs304.csfunding.controller;

import com.cs304.csfunding.api.Result;
import com.cs304.csfunding.api.Result;
import com.cs304.csfunding.api.User_ResearchGroupDTO;
import com.cs304.csfunding.service.User_ResearchGroupService;
import com.cs304.csfunding.service.ResearchGroupService;
import com.cs304.csfunding.util.HttpContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class User_ResearchGroupController {
    @Autowired
    private User_ResearchGroupService user_researchGroupService;
    @Autowired
    private ResearchGroupService researchGroupService;


    @PostMapping(value = "/add-user_group")
    public Result addUserResearchGroup(@RequestBody User_ResearchGroupDTO user_researchGroupDTO) {
        return new Result(user_researchGroupService.testInsert(user_researchGroupDTO));
    }

    @PostMapping("/delete-user_group")
    public Result removeUserGroup(@RequestBody User_ResearchGroupDTO user_researchGroupDTO) {
        return new Result(user_researchGroupService.delete(user_researchGroupDTO));
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

    @GetMapping("/user-by-researchGroup")
    public Result getUserByGroup(@RequestParam int gid){
        return new Result(user_researchGroupService.queryUserByGroup(gid));
    }

    @GetMapping("/all-user-group")
    public Result getAllUserByGroup(@RequestParam int gid){
        Map<String, Object> res = new HashMap<>();
        res.put("in", user_researchGroupService.queryUserByGroup(gid));
        res.put("not", user_researchGroupService.queryUserNotInGroup(gid));
        return new Result(res);
    }
}
