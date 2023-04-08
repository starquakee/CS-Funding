package com.cs304.csfunding.controller;
import com.cs304.csfunding.api.ResearchGroup_FundDTO;
import com.cs304.csfunding.service.ResearchGroup_FundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResearchGroup_FundController {
    @Autowired
    private ResearchGroup_FundService researchGroup_fundService;


    @PostMapping(value = "/register/researchgroup_fund")
    public String testAddResearchGroupFund(@RequestBody ResearchGroup_FundDTO researchGroup_fundDTO) {
        return researchGroup_fundService.testInsert(researchGroup_fundDTO);
    }
}
