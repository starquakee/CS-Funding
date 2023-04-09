package com.cs304.csfunding.service;

import com.cs304.csfunding.api.FundDTO;
import com.cs304.csfunding.entity.Fund;
import com.cs304.csfunding.mapper.FundMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class FundService {
    private final FundMapper fundMapper;

    public FundService(FundMapper fundMapper) {
        this.fundMapper = fundMapper;
    }

    public String testInsert(FundDTO fundDTO) {
        Fund fund = new Fund();
        fund.setFundNumber(fundDTO.getFundNumber());
        fund.setFundName(fundDTO.getFundName());
        fund.setSum(fundDTO.getSum());
        fund.setBalance(fundDTO.getBalance());
        fund.setStartTime(new Date());
        fund.setEndTime(new Date());
        fund.setRemainDays(fundDTO.getRemainDays());
        fundMapper.addFund(fund);
        return "";
    }

    public List<Fund> testQueryAll(){
        return fundMapper.getAllFund();
    }


}
