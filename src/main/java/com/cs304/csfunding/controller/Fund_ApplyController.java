package com.cs304.csfunding.controller;
import com.cs304.csfunding.api.Fund_ApplyDTO;
import com.cs304.csfunding.service.Fund_ApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Fund_ApplyController {
    @Autowired
    private Fund_ApplyService fund_applyService;


    @PostMapping(value = "/register/fund_apply")
    public String testAddFundApply(@RequestBody Fund_ApplyDTO fund_applyDTO) {
        System.out.println(fund_applyDTO);
        return fund_applyService.testInsert(fund_applyDTO);
    }

    @GetMapping("/selectapplybyfund")
    public List<Integer> getApplyByFund(int fund_UUID){
        return fund_applyService.testQueryByFund(fund_UUID);
    }
}
