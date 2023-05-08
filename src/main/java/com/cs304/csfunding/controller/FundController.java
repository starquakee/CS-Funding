package com.cs304.csfunding.controller;

import com.cs304.csfunding.api.FundDTO;
import com.cs304.csfunding.api.Result;
import com.cs304.csfunding.entity.Apply;
import com.cs304.csfunding.entity.Fund;
import com.cs304.csfunding.service.FundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FundController {

    @Autowired
    private FundService fundService;


    @PostMapping(value = "/register/fund")
    public String testAddFund(@RequestBody FundDTO fundDTO) {
        return fundService.testInsert(fundDTO);
    }

    @PostMapping(value = "/modify/fund")
    public Result testModifyFund(@RequestBody FundDTO fundDTO) {
        Fund fund = fundService.queryByID(fundDTO.getUuid());
        if (fund == null) {
            return new Result(404, "fund not found", null);
        } else {
            return fundService.testModify(fundDTO);
        }
    }

    @GetMapping("delete/fund")
    public Result testDeleteFund(@RequestParam int uuid) {
        Fund fund = fundService.queryByID(uuid);
        if (fund == null) {
            return new Result(404, "fund not found", null);
        } else {
            fundService.testDelete(uuid);
            return new Result(200,"success",null);
        }
    }

    @GetMapping("/getallfunds")
    public Result getAllFund(){
        List<Fund> funds = fundService.testQueryAll();
        if (funds == null) {
            return new Result(404, "funds not found", null);
        } else {
            return new Result(funds);
        }
    }

    @GetMapping("/get/fund")
    public Result testAddFund(@RequestParam int uuid) {
        Fund fund = fundService.queryByID(uuid);
        if (fund == null) {
            return new Result(404, "fund not found", null);
        } else {
            return new Result(200,"success",fund);
        }
    }

}
