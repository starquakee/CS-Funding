package com.cs304.csfunding.controller;
import com.cs304.csfunding.api.*;
import com.cs304.csfunding.entity.Apply;
import com.cs304.csfunding.entity.Fund;
import com.cs304.csfunding.entity.ResearchGroup;
import com.cs304.csfunding.entity.User;
import com.cs304.csfunding.service.*;
import com.cs304.csfunding.util.HttpContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ApplyController {

    @Autowired
    private ApplyService applyService;
    @Autowired
    private UserService userService;
    @Autowired
    private  NoticeService noticeService;
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
        int uuid = HttpContextUtil.getRequestUuid();
//        int uuid = applyDTO.getUserID();
        applyDTO.setUserID(uuid);
        User user = userService.queryByUuid(uuid);
        applyDTO.setState("submit");
        applyDTO.setContentSummary("");
        List<ResearchGroup> rg = researchGroupService.testQueryByUser(user.getUuid()); //user's research group
        List<Integer> rg_uuid = new ArrayList<>();

        if(rg.isEmpty()){
            return new Result(410, "user belongs to no group", null);
        }

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
        //update correspond fund
//        FundDTO fd = new FundDTO();
//        fd.setBalance(fund.getBalance());
//        fd.setSum(fund.getSum()-applyDTO.getMoney());
//        fd.setUuid(fund.getUuid());
//        fd.setEndTime(fund.getEndTime());
//        fd.setFundNumber(fund.getFundNumber());
//        fd.setFundName(fund.getFundName());
//        fd.setRemainDays(fund.getRemainDays());
//        fd.setStartTime(fund.getStartTime());
//        fundService.testModify(fd);
        return new Result(200,"OK",null);
    }

    @PostMapping(value = "/register/resubmit")
    public Result ResubmitApply(@RequestBody ApplyDTO applyDTO) {
        int uuid = HttpContextUtil.getRequestUuid();
        applyDTO.setUserID(uuid);
//        int uuid = applyDTO.getUserID();
        User user = userService.queryByUuid(uuid);
        applyDTO.setState("submit");
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
        applyService.testUpdateReSubmitted(applyDTO.getUUID());
        applyService.testInsert(applyDTO);
        return new Result(200,"OK",null);
    }

    @PostMapping(value="/testjudge")
    public Result testJudgeApply(@RequestBody InspectDTO inspectDTO){
        //change state by name
        if (inspectDTO.isPass()) {
            applyService.testJudgeByID("pass", inspectDTO.getRemark(), inspectDTO.getAid());
            List<Apply> applies = applyService.testQueryByID(inspectDTO.getAid());
            Apply apply = applies.get(0);
            Fund fund = fundService.queryByID(apply.getFundID());
            FundDTO fd = new FundDTO();
            fd.setBalance(fund.getBalance());
            fd.setSum(fund.getSum()-apply.getMoney());
            fd.setUuid(fund.getUuid());
            fd.setEndTime(fund.getEndTime());
            fd.setFundNumber(fund.getFundNumber());
            fd.setFundName(fund.getFundName());
            fd.setRemainDays(fund.getRemainDays());
            fd.setStartTime(fund.getStartTime());
            fundService.testModify(fd);

            NoticeDTO nd = new NoticeDTO();
            nd.setContent(apply.getName()+" is passed");
            nd.setNoticeFrom(HttpContextUtil.getRequestUuid());
            nd.setNoticeTo(apply.getUserID());
            noticeService.testInsert(nd);
        }else {
            applyService.testJudgeByID("fail", inspectDTO.getRemark(), inspectDTO.getAid());
            List<Apply> applies = applyService.testQueryByID(inspectDTO.getAid());
            Apply apply = applies.get(0);
            NoticeDTO nd = new NoticeDTO();
            nd.setContent(apply.getName() + " failed to pass");
            nd.setNoticeFrom(HttpContextUtil.getRequestUuid());
            nd.setNoticeTo(apply.getUserID());
            noticeService.testInsert(nd);
        }
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

    @GetMapping("/all-apply-fund")
    public Result getApplyByFundID(@RequestParam int fundID){
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

    @GetMapping("/apply-userid")
    public Result getSortApplyByUserID(int userID){
        List<ApplyVO> applies = applyService.testQueryByUserID(userID);
        if (applies == null) {
            return new Result(404, "latest applies not found", null);
        } else {
            return new Result(applies);
        }
    }

    @GetMapping("/my-apply")
    public Result getMyApply(){
        int my_id = HttpContextUtil.getRequestUuid();
        return getSortApplyByUserID(my_id);
    }
}
