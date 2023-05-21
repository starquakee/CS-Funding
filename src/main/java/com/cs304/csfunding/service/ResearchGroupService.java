package com.cs304.csfunding.service;

import com.cs304.csfunding.api.ResearchGroupDTO;
import com.cs304.csfunding.entity.ResearchGroup;
import com.cs304.csfunding.mapper.ResearchGroupMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ResearchGroupService {
    private final ResearchGroupMapper researchGroupMapper;

    public ResearchGroupService(ResearchGroupMapper researchGroupMapper) {
        this.researchGroupMapper = researchGroupMapper;
    }

    public String testInsert(ResearchGroupDTO researchGroupDTO) {
        ResearchGroup researchGroup = new ResearchGroup();
        researchGroup.setTeacher(researchGroupDTO.getTeacher());
        researchGroup.setAllFund(researchGroupDTO.getAllFund());
        researchGroupMapper.addResearchGroup(researchGroup);
        return "";
    }

    public List<ResearchGroup> testQueryAll(){
        return researchGroupMapper.getAllResearchGroup();
    }

    public List<ResearchGroup> testQueryByName(String teacherName){
        return researchGroupMapper.getAllResearchGroupByName(teacherName);
    }

    public List<ResearchGroup> testQueryByUser(int UserID){
        return researchGroupMapper.getResearchGroupByUser(UserID);
    }

    public ResearchGroup queryResearchGroupById(int gid){
        return researchGroupMapper.getResearchGroup(gid);
    }

}
