package com.cs304.csfunding.controller;
import com.cs304.csfunding.api.ApplyDTO;
import com.cs304.csfunding.api.Result;
import com.cs304.csfunding.entity.Apply;
import com.cs304.csfunding.entity.Fund;
import com.cs304.csfunding.entity.ResearchGroup;
import com.cs304.csfunding.entity.User;
import com.cs304.csfunding.service.*;
import com.cs304.csfunding.util.HttpContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ApplyController {

    @Autowired
    private ApplyService applyService;
    @Autowired
    private UserService userService;
    @Autowired
    private ResearchGroupService researchGroupService;
    @Autowired
    private ResearchGroup_FundService researchGroupFundService;
    @Autowired
    private FundService fundService;


    @PostMapping(value = "/register/testapply")
    public String testAddApply(@RequestBody ApplyDTO applyDTO) {
        return applyService.testInsert(applyDTO);
    }

    @PostMapping(value = "/register/apply")
    public Result AddApply(@RequestBody ApplyDTO applyDTO) {
        //int uuid = HttpContextUtil.getRequestUuid();
        int uuid = applyDTO.getUserID();
        User user = userService.queryByUuid(uuid);
        List<ResearchGroup> rg = researchGroupService.testQueryByUser(user.getUuid()); //user's research group
        List<Integer> rg_uuid = new ArrayList<>();
        for (ResearchGroup researchGroup : rg) {
            rg_uuid.add(researchGroup.getUuid());
        }
        int fund_rg = researchGroupFundService.testQueryByFund(applyDTO.getFundID()).get(0); //fund's research group
        Fund fund = fundService.queryByID(applyDTO.getFundID());
        //filter
        if(!rg_uuid.contains(applyDTO.getResearchGroupID())) {
            return new Result(403, "user doesn't belong to the research group", null);
        }
        if(!rg_uuid.contains(fund_rg)){
            return new Result(403,"fund provide no access to user", null);
        }
        if(applyDTO.getMoney()>fund.getSum()){
            return new Result(403,"exceed fund budget", null);
        }
        //insert
        applyService.testInsert(applyDTO);
        return new Result(200,"OK",null);
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
