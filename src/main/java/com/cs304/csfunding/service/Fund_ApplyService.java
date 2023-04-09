package com.cs304.csfunding.service;

import com.cs304.csfunding.api.Fund_ApplyDTO;
import com.cs304.csfunding.entity.Fund_Apply;
import com.cs304.csfunding.mapper.Fund_ApplyMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class Fund_ApplyService {

    private final Fund_ApplyMapper fund_applyMapper;

    public Fund_ApplyService(Fund_ApplyMapper fund_applyMapper) {
        this.fund_applyMapper = fund_applyMapper;
    }

    public String testInsert(Fund_ApplyDTO fund_applyDTO) {
        Fund_Apply fund_apply = new Fund_Apply();
        fund_apply.setFund_UUID(fund_applyDTO.getFundUUID());
        fund_apply.setApply_UUID(fund_applyDTO.getApplyUUID());
        fund_applyMapper.addFund_Apply(fund_apply);
        return "";
    }

    public List<Integer> testQueryByFund(int fund){
        return fund_applyMapper.findApplyByFund(fund);
    }



}
