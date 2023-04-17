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
        return new Result(200, "订单查找成功", applyService.testQueryAll());
    }

    @GetMapping("/selectapplybyfundid")
    public Result getApplyByFundID(int fundID){
        return new Result(200, "通过FundID查找申请成功", applyService.testQueryByFundID(fundID));
    }

    @GetMapping("/selectapplybyresearchgroupid")
    public Result getApplyByResearchGroupID(int researchGroupID){
        return new Result(200, "通过ResearchGroupID查找申请成功", applyService.testQueryByResearchGroupID(researchGroupID));
    }

    @GetMapping("/selectsortapplybyuserid")
    public Result getSortApplyByUserID(int userID){
        return new Result(200, "通过UserID查找最近申请成功", applyService.testQueryByUserID(userID));
    }
}
