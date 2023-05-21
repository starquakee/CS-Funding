package com.cs304.csfunding.controller;
import com.cs304.csfunding.api.ResearchGroup_FundDTO;
import com.cs304.csfunding.api.Result;
import com.cs304.csfunding.service.ResearchGroup_FundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ResearchGroup_FundController {
    @Autowired
    private ResearchGroup_FundService researchGroup_fundService;


    @PostMapping(value = "/register/research-group_fund")
    public Result testAddResearchGroupFund(@RequestBody ResearchGroup_FundDTO researchGroup_fundDTO) {
        return new Result(researchGroup_fundService.testInsert(researchGroup_fundDTO));
    }

    @GetMapping("/fund-research-group")
    public Result getFundByResearchGroup(int gid){
        return new Result(researchGroup_fundService.testQueryByResearchGroup(gid));
    }
}
