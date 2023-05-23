package com.cs304.csfunding.service;

import com.cs304.csfunding.api.ApplyDTO;
import com.cs304.csfunding.api.ApplySearchDTO;
import com.cs304.csfunding.api.ApplyVO;
import com.cs304.csfunding.entity.Apply;
import com.cs304.csfunding.entity.Fund;
import com.cs304.csfunding.entity.ResearchGroup;
import com.cs304.csfunding.entity.User;
import com.cs304.csfunding.mapper.ApplyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ApplyService {
    private final ApplyMapper applyMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private FundService fundService;
    @Autowired
    private ResearchGroupService researchGroupService;
    @Autowired
    private ResearchGroup_FundService researchGroupFundService;


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

    public List<Apply> testQueryAll() {
        return applyMapper.getAllApply();
    }

    public List<Apply> testQueryByID(int UUID){
        return applyMapper.findApplyByID(UUID);
    }

    public List<Apply> testQueryByFundID(int fundID) {
        return applyMapper.findApplyByFundID(fundID);
    }

    public List<Apply> testQueryByResearchGroupID(int researchGroupID) {
        return applyMapper.findApplyByResearchGroupID(researchGroupID);
    }

    public List<ApplyVO> testQueryByUserID(int userID) {
        List<Apply> applies = applyMapper.findApplyByUserID(userID);
        return applies.stream().map(this::apply2VO).collect(Collectors.toList());
    }

    public List<ApplyVO> queryAllVO() {
        List<Apply> applies = applyMapper.getAllApply();
        return applies.stream().map(this::apply2VO).collect(Collectors.toList());
    }

    public List<ApplyVO> searchAllVO(ApplySearchDTO applySearchDTO) {
        List<Apply> applies = applyMapper.searchApply(-1, applySearchDTO.getResearchGroup(),
                applySearchDTO.getFundName(), applySearchDTO.getState());
        return applies.stream().map(this::apply2VO).collect(Collectors.toList());
    }

    public List<ApplyVO> searchUserApplyVO(ApplySearchDTO applySearchDTO, int uid){
        List<Apply> applies = applyMapper.searchApply(uid, applySearchDTO.getResearchGroup(),
                applySearchDTO.getFundName(), applySearchDTO.getState());
        return applies.stream().map(this::apply2VO).collect(Collectors.toList());
    }

    public void testJudgeByName(String state, String remark, String name) {
        applyMapper.updateApplyByName(state, remark, name);
    }

    public void testJudgeByID(String state, String remark, int uuid) {
        applyMapper.updateApplyByID(state, remark, uuid);
    }

    public void testUpdateReSubmitted(int uuid) {
        applyMapper.updateApplyByID("resubmitted", "", uuid);
    }

    public ApplyVO apply2VO(Apply apply) {
        ApplyVO vo = new ApplyVO();
        User applier = userService.queryByUuid(apply.getUserID());
        Fund fund = fundService.queryByID(apply.getFundID());
        ResearchGroup researchGroup = researchGroupService.queryResearchGroupById(apply.getResearchGroupID());
        vo.setApplyPerson(applier.getName());
        vo.setSummary(apply.getContentSummary());
        vo.setFundName(fund.getFundName());
        vo.setFundNumber(apply.getFundID());
        vo.setMoney(apply.getMoney());
        vo.setRemark(apply.getRemark());
        vo.setResearchGroup(researchGroup.getTeacher());
        vo.setState(apply.getState());
        vo.setType2(apply.getType2());
        vo.setType1(apply.getType1());
        vo.setApplyId(apply.getUuid());
        vo.setResearchGroupId(apply.getResearchGroupID());
        vo.setBalance(fund.getBalance());
        return vo;
    }

}
