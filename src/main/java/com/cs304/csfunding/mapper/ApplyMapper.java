package com.cs304.csfunding.mapper;

import com.cs304.csfunding.entity.Apply;
import com.cs304.csfunding.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ApplyMapper {

    @Insert("insert into apply(Name, Money, Type1, Type2, FundID, ResearchGroupID, State, ContentSummary, remark) values " +
            "(#{Name}, #{Money}, #{Type1}, #{Type2}, #{FundID}, #{ResearchGroupID}, #{State}, #{ContentSummary}, #{remark})")
    void addApply(Apply apply);

    @Select("select * from apply")
    List<Apply> getAllApply();

    @Select("select * from apply where FundID=#{FundID}")
    List<Apply> findApplyByFundID(@Param("FundID") int fundID);

    @Select("select * from apply where ResearchGroupID=#{ResearchGroupID}")
    List<Apply> findApplyByResearchGroupID(@Param("ResearchGroupID") int researchGroupID);


}
