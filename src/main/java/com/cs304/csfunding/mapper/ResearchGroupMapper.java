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

    @Select("select * from researchgroup where Teacher=CONCAT('%', #{Teacher}, '%')")
    List<ResearchGroup> getAllResearchGroupByName(@Param("Teacher") String Teacher);

    @Select("select r.* from user_researchgroup u_r join researchgroup r on u_r.ResearchGroup_UUID=r.UUID where u_r.User_UUID=#{UUID}")
    List<ResearchGroup> getResearchGroupByUser(@Param("UUID") int UUID);

    @Select("select * from researchgroup where `UUID`=${UUID}")
    ResearchGroup getResearchGroup(int UUID);


}
