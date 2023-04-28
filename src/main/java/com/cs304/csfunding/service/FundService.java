package com.cs304.csfunding.service;

import com.cs304.csfunding.api.FundDTO;
import com.cs304.csfunding.entity.Fund;
import com.cs304.csfunding.mapper.FundMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Fund queryByID(int uuid) {return fundMapper.getFundByID(uuid);}
}
