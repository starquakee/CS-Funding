package com.cs304.csfunding.service;

import com.cs304.csfunding.api.ResearchGroup_FundDTO;
import com.cs304.csfunding.entity.ResearchGroup_Fund;
import com.cs304.csfunding.mapper.ResearchGroup_FundMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ResearchGroup_FundService {

    private final ResearchGroup_FundMapper researchGroup_fundMapper;

    public ResearchGroup_FundService(ResearchGroup_FundMapper researchGroup_fundMapper) {
        this.researchGroup_fundMapper = researchGroup_fundMapper;
    }

    public String testInsert(ResearchGroup_FundDTO researchGroup_fundDTO) {
        ResearchGroup_Fund researchGroup_fund = new ResearchGroup_Fund();
        researchGroup_fund.setFund_UUID(researchGroup_fundDTO.getFundUUID());
        researchGroup_fund.setResearchGroup_UUID(researchGroup_fundDTO.getResearchGroupUUID());
        researchGroup_fundMapper.addResearchGroup_Fund(researchGroup_fund);
        return "";
    }

    public List<Integer> testQueryByResearchGroup(int researchGroup){
        return researchGroup_fundMapper.findFundByResearchGroup(researchGroup);
    }



}
