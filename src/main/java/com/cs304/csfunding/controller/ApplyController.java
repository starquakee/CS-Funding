package com.cs304.csfunding.controller;
import com.cs304.csfunding.api.ApplyDTO;
import com.cs304.csfunding.api.Result;
import com.cs304.csfunding.entity.Apply;
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
    public Result getAllApply(){
        List<Apply> applies = applyService.testQueryAll();
        if (applies == null) {
            return new Result(404, "applies not found", null);
        } else {
            return new Result(applies);
        }
    }

    @GetMapping("/selectapplybyfundid")
    public Result getApplyByFundID(int fundID){
        List<Apply> applies = applyService.testQueryByFundID(fundID);
        if (applies == null) {
            return new Result(404, "applies not found", null);
        } else {
            return new Result(applies);
        }
    }

    @GetMapping("/selectapplybyresearchgroupid")
    public Result getApplyByResearchGroupID(int researchGroupID){
        List<Apply> applies = applyService.testQueryByResearchGroupID(researchGroupID);
        if (applies == null) {
            return new Result(404, "applies not found", null);
        } else {
            return new Result(applies);
        }
    }

    @GetMapping("/selectsortapplybyuserid")
    public Result getSortApplyByUserID(int userID){
        List<Apply> applies = applyService.testQueryByUserID(userID);
        if (applies == null) {
            return new Result(404, "latest applies not found", null);
        } else {
            return new Result(applies);
        }
    }
}
