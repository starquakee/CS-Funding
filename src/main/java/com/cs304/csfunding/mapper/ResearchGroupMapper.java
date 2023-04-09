package com.cs304.csfunding.mapper;

import com.cs304.csfunding.entity.ResearchGroup;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ResearchGroupMapper {

    @Insert("insert into researchgroup(Teacher, AllFund) values (#{Teacher}, #{AllFund})")
    void addResearchGroup(ResearchGroup researchGroup);

    @Select("select * from researchgroup")
    List<ResearchGroup> getAllResearchGroup();




}
