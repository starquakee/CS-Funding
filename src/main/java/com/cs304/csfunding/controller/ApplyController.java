package com.cs304.csfunding.controller;

import com.cs304.csfunding.api.ApplyDTO;
import com.cs304.csfunding.entity.Apply;
import com.cs304.csfunding.entity.User;
import com.cs304.csfunding.service.ApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApplyController {

    @Autowired
    private ApplyService applyService;


    @PostMapping(value = "/register/apply")
    public String testAddApply(@RequestBody ApplyDTO applyDTO) {
        return applyService.testInsert(applyDTO);
    }

    @GetMapping("/getallapplys")
    public List<Apply> getAllApply(){
        return applyService.testQueryAll();
    }

    @GetMapping("/selectapplybyfundid")
    public List<Apply> getApplyByFundID(int fundID){
        return applyService.testQueryByFundID(fundID);
    }

    @GetMapping("/selectapplybyresearchgroupid")
    public List<Apply> getApplyByResearchGroupID(int researchGroupID){
        return applyService.testQueryByResearchGroupID(researchGroupID);
    }
}
