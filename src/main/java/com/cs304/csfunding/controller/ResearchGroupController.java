package com.cs304.csfunding.controller;

import com.cs304.csfunding.api.ResearchGroupDTO;
import com.cs304.csfunding.api.Result;
import com.cs304.csfunding.entity.Apply;
import com.cs304.csfunding.entity.ResearchGroup;
import com.cs304.csfunding.service.ResearchGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ResearchGroupController {

    @Autowired
    private ResearchGroupService researchGroupService;


    @PostMapping(value = "/register/researchgroup")
    public String testAddResearchGroup(@RequestBody ResearchGroupDTO researchGroupDTO) {
        System.out.println(researchGroupDTO);
        return researchGroupService.testInsert(researchGroupDTO);
    }

    @GetMapping("/getallresearchgroups")
    public Result getAllResearchGroup(){
        List<ResearchGroup> researchgroups = researchGroupService.testQueryAll();
        if (researchgroups == null) {
            return new Result(404, "researchgroups not found", null);
        } else {
            return new Result(researchgroups);
        }
    }

    @GetMapping("/getresearchgroupsbyuser")
    public Result getResearchGroupByUser(int UserID){
        List<ResearchGroup> researchgroups = researchGroupService.testQueryByUser(UserID);
        if (researchgroups == null) {
            return new Result(404, "researchgroups not found", null);
        } else {
            return new Result(researchgroups);
        }
    }


}
