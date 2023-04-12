package com.cs304.csfunding.service;

import com.cs304.csfunding.api.ApplyDTO;
import com.cs304.csfunding.entity.Apply;
import com.cs304.csfunding.mapper.ApplyMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ApplyService {
    private final ApplyMapper applyMapper;

    public ApplyService(ApplyMapper applyMapper) {
        this.applyMapper = applyMapper;
    }

    public String testInsert(ApplyDTO applyDTO) {
        Apply apply = new Apply();
        apply.setName(applyDTO.getName());
        apply.setMoney(applyDTO.getMoney());
        apply.setType1(applyDTO.getType1());
        apply.setType2(applyDTO.getType2());
        apply.setFundID(applyDTO.getFundID());
        apply.setResearchGroupID(applyDTO.getResearchGroupID());
        apply.setUserID(applyDTO.getUserID());
        apply.setState(applyDTO.getState());
        apply.setContentSummary(applyDTO.getContentSummary());
        apply.setRemark(applyDTO.getRemark());
        apply.setTime(Long.toString(System.currentTimeMillis()));
        applyMapper.addApply(apply);
        return "";
    }

    public List<Apply> testQueryAll(){
        return applyMapper.getAllApply();
    }

    public List<Apply> testQueryByFundID(int fundID){
        return applyMapper.findApplyByFundID(fundID);
    }

    public List<Apply> testQueryByResearchGroupID(int researchGroupID){
        return applyMapper.findApplyByResearchGroupID(researchGroupID);
    }

    public List<Apply> testQueryByUserID(int userID){
        return applyMapper.findApplyByUserID(userID);
    }

}
