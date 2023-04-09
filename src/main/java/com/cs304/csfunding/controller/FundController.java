package com.cs304.csfunding.controller;
import com.cs304.csfunding.api.FundDTO;
import com.cs304.csfunding.entity.Fund;
import com.cs304.csfunding.service.FundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FundController {

    @Autowired
    private FundService fundService;


    @PostMapping(value = "/register/fund")
    public String testAddFund(@RequestBody FundDTO fundDTO) {
        return fundService.testInsert(fundDTO);
    }

    @GetMapping("/getallfunds")
    public List<Fund> getAllFund(){
        return fundService.testQueryAll();
    }


}
