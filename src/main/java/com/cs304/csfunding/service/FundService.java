package com.cs304.csfunding.service;

import com.cs304.csfunding.api.FundDTO;
import com.cs304.csfunding.api.Result;
import com.cs304.csfunding.entity.Fund;
import com.cs304.csfunding.mapper.FundMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
@Transactional
public class FundService {
    private final FundMapper fundMapper;

    public FundService(FundMapper fundMapper) {
        this.fundMapper = fundMapper;
    }

    public String testInsert(FundDTO fundDTO) {
        Fund ff = new Fund();
        ff.setFundNumber(fundDTO.getFundNumber());
        ff.setFundName(fundDTO.getFundName());
        ff.setBalance(fundDTO.getBalance());
        ff.setSum(fundDTO.getSum());
        ff.setRemainDays(fundDTO.getRemainDays());
        ff.setStartTime(fundDTO.getStartTime());
        ff.setEndTime(fundDTO.getEndTime());
        fundMapper.addFund(ff);
        return "";
    }

    public List<Fund> testQueryAll(){
        return fundMapper.getAllFund();
    }

    public Fund queryByID(int uuid) {
        return fundMapper.getFundByID(uuid);
    }

    public List<Fund> queryVague(String FundNumber,String FundName, String researchGroupId) {
        return fundMapper.getFundVague(FundNumber,FundName,researchGroupId);
    }

    public void testDelete(int uuid) {
        fundMapper.deleteFundByID(uuid);
    }

    public Result testModify(FundDTO fundDTO) {
        Fund fund = new Fund();
        fund.setUuid(fundDTO.getUuid());
        fund.setFundNumber(fundDTO.getFundNumber());
        fund.setFundName(fundDTO.getFundName());
        fund.setBalance(fundDTO.getBalance());
        fund.setSum(fundDTO.getSum());
        fund.setRemainDays(fundDTO.getRemainDays());
        fund.setStartTime(fundDTO.getStartTime());
        fund.setEndTime(fundDTO.getEndTime());
        fundMapper.modifyFund(fund);
        return new Result(200,"success",fund);
    }
}
