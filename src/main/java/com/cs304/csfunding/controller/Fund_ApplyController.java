package com.cs304.csfunding.controller;
import com.cs304.csfunding.api.Fund_ApplyDTO;
import com.cs304.csfunding.api.Result;
import com.cs304.csfunding.service.Fund_ApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Fund_ApplyController {
    @Autowired
    private Fund_ApplyService fund_applyService;


    @PostMapping(value = "/register/fund_apply")
    public Result testAddFundApply(@RequestBody Fund_ApplyDTO fund_applyDTO) {
        System.out.println(fund_applyDTO);
        return new Result(fund_applyService.testInsert(fund_applyDTO));
    }

    @GetMapping("/apply-by-fund")
    public Result getApplyByFund(@RequestParam int fid){
        return new Result(fund_applyService.testQueryByFund(fid));
    }
}
