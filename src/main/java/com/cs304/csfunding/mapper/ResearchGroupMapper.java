package com.cs304.csfunding.mapper;

import com.cs304.csfunding.entity.ResearchGroup;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ResearchGroupMapper {

    @Insert("insert into researchgroup(Teacher, AllFund) values (#{Teacher}, #{AllFund})")
    void addResearchGroup(ResearchGroup researchGroup);

    @Select("select * from researchgroup")
    List<ResearchGroup> getAllResearchGroup();

    @Select("select * from researchgroup where Teacher like CONCAT('%', #{Teacher}, '%')")
    List<ResearchGroup> getAllResearchGroupByName(String Teacher);

    @Select("select r.* from user_researchgroup u_r join researchgroup r on u_r.ResearchGroup_UUID=r.UUID where u_r.User_UUID=#{UUID}")
    List<ResearchGroup> getResearchGroupByUser(@Param("UUID") int UUID);

    @Select("select * from researchgroup where `UUID`=${UUID}")
    ResearchGroup getResearchGroup(int UUID);

    @Update("update researchgroup set `AllFund`=#{fund} where `UUID`=#{gid}")
    void modifyGroupFundCnt(int gid, float fund);


}
